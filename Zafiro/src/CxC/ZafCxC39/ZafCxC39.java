/*
 * ZafCxC39.java  
 *
 * Created on 7 de diciembre de 2007, 05:05 PM  ne_mod
 */

package CxC.ZafCxC39;  
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
  
/**
 *   
 * @author  jayapata 
 */  
public class ZafCxC39 extends javax.swing.JInternalFrame {
    
     ZafVenCon objVenConCli; //*****************
     ZafVenCon objVenConVen;
     ZafUtil objUti;
     Librerias.ZafParSis.ZafParSis objZafParSis;
     javax.swing.JInternalFrame jfrThis;
     private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModDocVen, objTblModSopChe, objTblModProCre;
     private Librerias.ZafColNumerada.ZafColNumerada objColNum, objColNumDocVen, objColNumSopChe, objColNumProCre;
     private ZafMouMotAda objMouMotAda;
     private ZafMouMotTit objZafMouMotTit;
     private ZafThreadGUI objThrGUI;
     private ZafTblTot objTblTotDocVen;  
     private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;  
     private String strCodPrv, strDesLarPrv;
     
     private ZafTblOrd objTblOrd; 
     private ZafTblBus objTblBus;
     
     final int INT_TBL_LIN=0;
     final int INT_TBL_COD=1;
     final int INT_TBL_IDE=2;
     final int INT_TBL_NOM=3;
     //final int INT_TBL_CRE=4;
     
     final int INT_TBL2_LINEA=0;
     final int INT_TBL2_CODLOC=1;
     final int INT_TBL2_CODTIPDOC=2;
     final int INT_TBL2_TIPDOC=3;
     final int INT_TBL2_CODDOC=4;
     final int INT_TBL2_CODREG=5;
     final int INT_TBL2_NUMDOC=6;
     final int INT_TBL2_FECDOC=7;
     final int INT_TBL2_DIACRE=8;
     final int INT_TBL2_FECVEN=9;
     final int INT_TBL2_RETE=10;
     final int INT_TBL2_VALDOC=11;
     final int INT_TBL2_ABONO=12;
     final int INT_TBL2_VALPEN=13;
      final int INT_TBL2_NUMCHQ=14;
      final int INT_TBL2_FECCHE=15;
      final int INT_TBL2_VALCHQ=16;
    
     
     
     final int INT_TBL3_LINEA=0;
     final int INT_TBL3_CODLOC=1;
     final int INT_TBL3_CODTIPDOC=2;
     final int INT_TBL3_TIPDOC=3;
     final int INT_TBL3_CODDOC=4;
     final int INT_TBL3_CODREG=5;
     final int INT_TBL3_NUMDOC=6;
     final int INT_TBL3_FECDOC=7;
     final int INT_TBL3_DIACRE=8;
     final int INT_TBL3_FECVEN=9;
     final int INT_TBL3_RETE=10;
     final int INT_TBL3_VALDOC=11;
     final int INT_TBL3_ABONO=12;
     final int INT_TBL3_VALPEN=13;
     
     
      final String VERSION = " ver 0.8 ";
       
      private String strCodCom, strDesLarCom;
      private boolean blnCon;   
      private Vector vecDat, vecCab, vecReg;
      String strTip="";
      String strConAuxCon="";
      
    /** Creates new form ZafCxC39 */
    public ZafCxC39(ZafParSis obj) {
       try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;
            this.setTitle("Listado de clientes con Credito Cerrado "+VERSION);
             
          objUti = new ZafUtil();
          initComponents();
            
          lblTit.setText( objZafParSis.getNombreMenu());
        
          
        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
      
    
      
    
    
  
    
   public void Configura_ventana_consulta(){
       
        configurarVenConClientes();  
        configurarVenConVendedor();
        
        ConfiguararTablaCli();
        ConfiguararTablaDocVen();
        configurarVenConSopChe();
        configurarVenConProCre();
        txtVenCod.setText(""+objZafParSis.getCodigoUsuario());
        
	///8/BuscarVendedor("a.co_usr", txtVenCod.getText(),0);
	 
	if (objVenConVen.buscar("a.co_usr", txtVenCod.getText() )) {
            txtVenCod.setText(objVenConVen.getValueAt(1));
            txtVenNom.setText(objVenConVen.getValueAt(2));
        }else txtVenCod.setText("");
	
          if(objZafParSis.getNombreMenu()==null){
             consultar();
          }
    }
    
      
        
       
     
    private boolean configurarVenConVendedor() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
            " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
            " where b.co_emp="+objZafParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";
            
            objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
  
    
    
    
    
    
     private boolean configurarVenConClientes() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.nd_maxdes");
            arlCam.add("a.nd_maruti");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.co_ven");
            arlCam.add("a.vendedor");
            arlCam.add("a.co_tipper");
            arlCam.add("a.ne_tipforpag");
            arlCam.add("a.nd_pes");
            arlCam.add("a.ne_diagra");
            arlCam.add("a.co_forpag");
            arlCam.add("a.tx_des");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("Desc");
            arlAli.add("Utili");
            arlAli.add("Ciudad");
            arlAli.add("co_vende");
            arlAli.add("Vendedor");
            arlAli.add("CoDTipPer");
            arlAli.add("TipoForPag");
            arlAli.add("Peso");
            arlAli.add("D.Gracias");
            arlAli.add("CodForPag");
            arlAli.add("Descri");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");           
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("40");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("40");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            
            strSQL="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper," +
            "  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des  " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
            " , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des  FROM tbm_cli as a  " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
            " WHERE a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.st_reg='A'  and a.st_cli='S' order by a.tx_nom  " +
            ") AS a";
            
	   if(!objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario())){
            
		 strSQL="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper," +
		"  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des  " +
		" FROM ( " +
		" select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
		" , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des, a.st_ivaven  " +
                " FROM tbr_cliloc AS a1 " +
		" INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
		" LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
		" LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
		" left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
		" WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  and a.st_cli='S' order by a.tx_nom  " +
		") AS a";
		 
	       strConAuxCon=" AND a1.co_loc="+objZafParSis.getCodigoLocal();	 
	   } 
            
            //System.out.println(" "+ strSQL );
            
            int intColOcu[]=new int[12];
            intColOcu[0]=6;
            intColOcu[1]=7;
            intColOcu[2]=8;
            intColOcu[3]=9;
            intColOcu[4]=10;
            intColOcu[5]=11; 
            intColOcu[6]=12;
            intColOcu[7]=13;
            intColOcu[8]=14;
            intColOcu[9]=15;
            intColOcu[10]=16;
            intColOcu[11]=17;
             
            
            
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
      
      
      private void ConfiguararTablaCli(){
            
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_COD,"Codigo");
            vecCab.add(INT_TBL_IDE,"Identificacion");
            vecCab.add(INT_TBL_NOM,"Nombre");
            //vecCab.add(INT_TBL_CRE,"Credito");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            tblDatCli.setModel(objTblMod);
            tblDatCli.setRowSelectionAllowed(true);
            tblDatCli.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new Librerias.ZafColNumerada.ZafColNumerada(tblDatCli,INT_TBL_LIN);
            
            tblDatCli.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDatCli.getColumnModel();
            
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_COD).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_IDE).setPreferredWidth(160);
            tcmAux.getColumn(INT_TBL_NOM).setPreferredWidth(365);
           
           // OcultarColumna(INT_TBL_CRE, tcmAux);
            
            tblDatCli.getTableHeader().setReorderingAllowed(false);
            objMouMotAda=new ZafMouMotAda();
            tblDatCli.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            
            
             objTblOrd=new ZafTblOrd(tblDatCli);
             objTblBus=new ZafTblBus(tblDatCli);
             
            
             //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDatCli.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            
            tcmAux=null;  
              
      }  
          
      
          
      private void ConfiguararTablaDocVen(){
            
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL2_LINEA,"");
            vecCab.add(INT_TBL2_CODLOC,"Cod.Loc");
            vecCab.add(INT_TBL2_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL2_TIPDOC," Tip.Doc ");
            vecCab.add(INT_TBL2_CODDOC," Cod.Doc");
            vecCab.add(INT_TBL2_CODREG," Cod.Reg");
            vecCab.add(INT_TBL2_NUMDOC,"Num.Doc.");
            vecCab.add(INT_TBL2_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL2_DIACRE,"Dia.Cre");
            vecCab.add(INT_TBL2_FECVEN,"Fec.Ven");
            vecCab.add(INT_TBL2_RETE,"%Ret.");
            vecCab.add(INT_TBL2_VALDOC,"Val.Doc");
            vecCab.add(INT_TBL2_ABONO,"Abono");
            vecCab.add(INT_TBL2_VALPEN,"Val.Pen");
             vecCab.add(INT_TBL2_NUMCHQ,"Num.Chq");
             vecCab.add(INT_TBL2_FECCHE,"Fec.Ven.Chq");
             vecCab.add(INT_TBL2_VALCHQ,"Val.Chq");
          
	    
	    
            
            objTblModDocVen=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModDocVen.setHeader(vecCab);
            
            tblDatDocven.setModel(objTblModDocVen);
            tblDatDocven.setRowSelectionAllowed(true);
            tblDatDocven.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNumDocVen=new Librerias.ZafColNumerada.ZafColNumerada(tblDatDocven,INT_TBL2_LINEA);
            
            
            objTblModDocVen.setColumnDataType(INT_TBL2_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL2_ABONO, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL2_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL2_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            
            tblDatDocven.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDatDocven.getColumnModel();
            
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL2_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL2_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_CODTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_CODDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_CODREG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL2_DIACRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_FECVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL2_RETE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL2_VALDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL2_ABONO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL2_VALPEN).setPreferredWidth(80);
            
            OcultarColumna(INT_TBL2_CODTIPDOC, tcmAux);
            OcultarColumna(INT_TBL2_CODDOC, tcmAux);
            OcultarColumna(INT_TBL2_CODREG, tcmAux);
            OcultarColumna(INT_TBL2_RETE, tcmAux);
               
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL2_VALDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL2_ABONO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL2_VALPEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL2_VALCHQ).setCellRenderer(objTblCelRenLbl);
	    objTblCelRenLbl=null;
            
              
            tblDatDocven.getTableHeader().setReorderingAllowed(false);
            objZafMouMotTit= new ZafMouMotTit(tblDatDocven);
            tblDatDocven.getTableHeader().addMouseMotionListener(objZafMouMotTit);
            
            
            
            int intCol[]={ INT_TBL2_VALDOC,  INT_TBL2_ABONO, INT_TBL2_VALPEN };
            objTblTotDocVen=new ZafTblTot(spnDocVen, spnDocVentot , tblDatDocven, tblTotDocVen, intCol);
            
              
            tcmAux=null;
              
      }
      
      
        public void OcultarColumna(int Columna, javax.swing.table.TableColumnModel tcmAux){
        try{
            tcmAux.getColumn(Columna).setWidth(0);
            tcmAux.getColumn(Columna).setMaxWidth(0);
            tcmAux.getColumn(Columna).setMinWidth(0);
            tcmAux.getColumn(Columna).setPreferredWidth(0);
            tcmAux.getColumn(Columna).setResizable(false);
            
        }catch(Exception e){ objUti.mostrarMsgErr_F1(this,e);  }
    }
    
      
      
      private void configurarVenConSopChe(){
            
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL3_LINEA,"");
            vecCab.add(INT_TBL3_CODLOC,"Cod.Loc");
            vecCab.add(INT_TBL3_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL3_TIPDOC," Tip.Doc ");
            vecCab.add(INT_TBL3_CODDOC," Cod.Doc");
            vecCab.add(INT_TBL3_CODREG," Cod.Reg");
            vecCab.add(INT_TBL3_NUMDOC,"Num.Doc.");
            vecCab.add(INT_TBL3_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL3_DIACRE,"Dia.Cre");
            vecCab.add(INT_TBL3_FECVEN,"Fec.Ven");
            vecCab.add(INT_TBL3_RETE,"%Ret.");
            vecCab.add(INT_TBL3_VALDOC,"Val.Doc");
            vecCab.add(INT_TBL3_ABONO,"Abono");
            vecCab.add(INT_TBL3_VALPEN,"Val.Pen");
          
            
            objTblModSopChe=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModSopChe.setHeader(vecCab);
            
            tblDatSopChe.setModel(objTblModSopChe);
            tblDatSopChe.setRowSelectionAllowed(true);
            tblDatSopChe.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNumSopChe=new Librerias.ZafColNumerada.ZafColNumerada(tblDatSopChe,INT_TBL2_LINEA);
            
            
            objTblModDocVen.setColumnDataType(INT_TBL3_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL3_ABONO, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL3_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            
            tblDatSopChe.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDatSopChe.getColumnModel();
            
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL3_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL3_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_CODTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_CODDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_CODREG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL3_DIACRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_FECVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL3_RETE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_VALDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL3_ABONO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL3_VALPEN).setPreferredWidth(80);
            
            OcultarColumna(INT_TBL2_CODTIPDOC, tcmAux);
            OcultarColumna(INT_TBL2_CODDOC, tcmAux);
            OcultarColumna(INT_TBL2_CODREG, tcmAux);
            OcultarColumna(INT_TBL2_RETE, tcmAux);
               
              
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL3_VALDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL3_ABONO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL3_VALPEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            tblDatSopChe.getTableHeader().setReorderingAllowed(false);
            objZafMouMotTit= new ZafMouMotTit(tblDatSopChe);
            tblDatSopChe.getTableHeader().addMouseMotionListener(objZafMouMotTit);
            
           
            
             //Configurar JTable: Establecer el ListSelectionListener.
          //  javax.swing.ListSelectionModel lsm=tblDatSopChe.getSelectionModel();
          // lsm.addListSelectionListener(new ZafLisSelLis());
            
            tcmAux=null;
              
      }
      
           
     
     private void configurarVenConProCre(){
            
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL3_LINEA,"");
            vecCab.add(INT_TBL3_CODLOC,"Cod.Loc");
            vecCab.add(INT_TBL3_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL3_TIPDOC," Tip.Doc ");
            vecCab.add(INT_TBL3_CODDOC," Cod.Doc");
            vecCab.add(INT_TBL3_CODREG," Cod.Reg");
            vecCab.add(INT_TBL3_NUMDOC,"Num.Doc.");
            vecCab.add(INT_TBL3_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL3_DIACRE,"Dia.Cre");
            vecCab.add(INT_TBL3_FECVEN,"Fec.Ven");
            vecCab.add(INT_TBL3_RETE,"%Ret.");
            vecCab.add(INT_TBL3_VALDOC,"Val.Doc");
            vecCab.add(INT_TBL3_ABONO,"Abono");
            vecCab.add(INT_TBL3_VALPEN,"Val.Pen");
          
               
            objTblModProCre=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModProCre.setHeader(vecCab);
            
            tbldatProCre.setModel(objTblModProCre);
            tbldatProCre.setRowSelectionAllowed(true);
            tbldatProCre.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNumProCre=new Librerias.ZafColNumerada.ZafColNumerada(tbldatProCre,INT_TBL2_LINEA);
            
              objTblModDocVen.setColumnDataType(INT_TBL3_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL3_ABONO, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModDocVen.setColumnDataType(INT_TBL3_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
           
            
            tbldatProCre.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tbldatProCre.getColumnModel();
            
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL3_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL3_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_CODTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_CODDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_CODREG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL3_DIACRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_FECVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL3_RETE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL3_VALDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL3_ABONO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL3_VALPEN).setPreferredWidth(80);
            
              
            OcultarColumna(INT_TBL2_CODTIPDOC, tcmAux);
            OcultarColumna(INT_TBL2_CODDOC, tcmAux);
            OcultarColumna(INT_TBL2_CODREG, tcmAux);
            OcultarColumna(INT_TBL2_RETE, tcmAux);
               
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL3_VALDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL3_ABONO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL3_VALPEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            tbldatProCre.getTableHeader().setReorderingAllowed(false);
            objZafMouMotTit= new ZafMouMotTit(tbldatProCre);
            tbldatProCre.getTableHeader().addMouseMotionListener(objZafMouMotTit);
           
            
             //Configurar JTable: Establecer el ListSelectionListener.
         //   javax.swing.ListSelectionModel lsm=tbldatProCre.getSelectionModel();
         //   lsm.addListSelectionListener(new ZafLisSelLis());
            
            tcmAux=null;
              
      }
      
             
     
     
     
      
    
      
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optGrp = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        txtVenNom = new javax.swing.JTextField();
        lblCom = new javax.swing.JLabel();
        txtVenCod = new javax.swing.JTextField();
        butVenCon = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        sppCreDeb = new javax.swing.JSplitPane();
        panCreDebReg = new javax.swing.JPanel();
        spnCreDeb = new javax.swing.JScrollPane();
        tblDatCli = new javax.swing.JTable();
        panCreDebMovReg = new javax.swing.JPanel();
        chkCreDebMosMovReg = new javax.swing.JCheckBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        spnDocVen = new javax.swing.JScrollPane();
        tblDatDocven = new javax.swing.JTable();
        spnDocVentot = new javax.swing.JScrollPane();
        tblTotDocVen = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatSopChe = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbldatProCre = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        lblTit.setText("jLabel1");
        jPanel2.add(lblTit);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(null);

        optGrp.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11));
        optTod.setSelected(true);
        optTod.setText("Todos los clientes");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        jPanel5.add(optTod);
        optTod.setBounds(4, 32, 400, 20);

        optGrp.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11));
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado");
        jPanel5.add(optFil);
        optFil.setBounds(4, 52, 400, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre de cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 11)));
        panNomCli.setLayout(null);

        lblNomCliDes.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomCliDes.setText("Desde:");
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 268, 20);

        lblNomCliHas.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomCliHas.setText("Hasta:");
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(336, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 268, 20);

        jPanel5.add(panNomCli);
        panNomCli.setBounds(24, 92, 660, 52);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        jPanel5.add(lblCli);
        lblCli.setBounds(24, 72, 120, 20);

        txtCliCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliCodActionPerformed(evt);
            }
        });
        txtCliCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliCodFocusLost(evt);
            }
        });
        jPanel5.add(txtCliCod);
        txtCliCod.setBounds(144, 72, 56, 20);

        txtCliNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliNomActionPerformed(evt);
            }
        });
        txtCliNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliNomFocusLost(evt);
            }
        });
        jPanel5.add(txtCliNom);
        txtCliNom.setBounds(200, 72, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        jPanel5.add(butCli);
        butCli.setBounds(660, 72, 20, 20);

        txtVenNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtVenNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVenNomActionPerformed(evt);
            }
        });
        txtVenNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVenNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVenNomFocusLost(evt);
            }
        });
        jPanel5.add(txtVenNom);
        txtVenNom.setBounds(116, 8, 204, 20);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCom.setText("Vendedor:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        jPanel5.add(lblCom);
        lblCom.setBounds(8, 8, 70, 15);

        txtVenCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtVenCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVenCodActionPerformed(evt);
            }
        });
        txtVenCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVenCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVenCodFocusLost(evt);
            }
        });
        jPanel5.add(txtVenCod);
        txtVenCod.setBounds(80, 8, 35, 20);

        butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVenCon.setText("...");
        butVenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butVenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenConActionPerformed(evt);
            }
        });
        jPanel5.add(butVenCon);
        butVenCon.setBounds(320, 8, 20, 20);

        tabFrm.addTab("Filtro", jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        sppCreDeb.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppCreDeb.setResizeWeight(0.8);
        sppCreDeb.setOneTouchExpandable(true);

        panCreDebReg.setPreferredSize(new java.awt.Dimension(354, 304));
        panCreDebReg.setRequestFocusEnabled(false);
        panCreDebReg.setLayout(new java.awt.BorderLayout());

        tblDatCli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnCreDeb.setViewportView(tblDatCli);

        panCreDebReg.add(spnCreDeb, java.awt.BorderLayout.CENTER);

        sppCreDeb.setTopComponent(panCreDebReg);

        panCreDebMovReg.setLayout(new java.awt.BorderLayout());

        chkCreDebMosMovReg.setText("Mostrar el movimiento del documento seleccionado");
        chkCreDebMosMovReg.setPreferredSize(new java.awt.Dimension(269, 20));
        chkCreDebMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCreDebMosMovRegActionPerformed(evt);
            }
        });
        panCreDebMovReg.add(chkCreDebMosMovReg, java.awt.BorderLayout.NORTH);

        jPanel7.setLayout(new java.awt.BorderLayout());

        tblDatDocven.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDocVen.setViewportView(tblDatDocven);

        jPanel7.add(spnDocVen, java.awt.BorderLayout.CENTER);

        spnDocVentot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotDocVen.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDocVentot.setViewportView(tblTotDocVen);

        jPanel7.add(spnDocVentot, java.awt.BorderLayout.SOUTH);

        jTabbedPane2.addTab("Documentos Vencidos", jPanel7);

        jPanel8.setLayout(new java.awt.BorderLayout());

        tblDatSopChe.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDatSopChe);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Documentos sin Soporte", jPanel8);

        jPanel9.setLayout(new java.awt.BorderLayout());

        tbldatProCre.setModel(new javax.swing.table.DefaultTableModel(
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
        tbldatProCre.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(tbldatProCre);

        jPanel9.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Otros Problemas de Credito", jPanel9);

        panCreDebMovReg.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        sppCreDeb.setBottomComponent(panCreDebMovReg);

        jPanel6.add(sppCreDeb, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel6);

        jPanel3.add(tabFrm, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        jPanel4.add(panBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel10.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel10.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel10.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel10.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel10, java.awt.BorderLayout.EAST);

        jPanel4.add(panBarEst, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    
    
         
    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
     }    
      
    
    
    
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:
          if (optTod.isSelected())
        {
            txtCliCod.setText("");
            txtCliNom.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        // TODO add your handling code here:
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCliHasFocusLost
  
    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        // TODO add your handling code here:
         if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        // TODO add your handling code here:
          txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        // TODO add your handling code here:
         txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained
       
    private void txtVenNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusLost
        // TODO add your handling code here:
        
          if (!txtVenNom.getText().equalsIgnoreCase(strDesLarCom)) {
            if (txtVenNom.getText().equals("")) {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else {
                BuscarVendedor("a.tx_nom",txtVenNom.getText(),1);
            }
        }
        else
            txtVenNom.setText(strDesLarCom);
          
            
    }//GEN-LAST:event_txtVenNomFocusLost

    private void txtVenCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusLost
        // TODO add your handling code here:
           if (!txtVenCod.getText().equalsIgnoreCase(strCodCom)) {
            if (txtVenCod.getText().equals("")) {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else {
                BuscarVendedor("a.co_usr",txtVenCod.getText(),0);
            }
        }
        else
            txtVenCod.setText(strCodCom);
           
    }//GEN-LAST:event_txtVenCodFocusLost

    private void txtVenNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusGained
        // TODO add your handling code here:
         strDesLarCom=txtVenNom.getText();
        txtVenNom.selectAll();
    }//GEN-LAST:event_txtVenNomFocusGained

    private void txtVenCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusGained
        // TODO add your handling code here:
         strCodCom=txtVenCod.getText();
          txtVenCod.selectAll();
    }//GEN-LAST:event_txtVenCodFocusGained

    private void txtVenNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenNomActionPerformed
        // TODO add your handling code here:
         txtVenNom.transferFocus();
    }//GEN-LAST:event_txtVenNomActionPerformed

    private void txtVenCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenCodActionPerformed
        // TODO add your handling code here:
         txtVenCod.transferFocus();
    }//GEN-LAST:event_txtVenCodActionPerformed

    private void butVenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenConActionPerformed
        // TODO add your handling code here:
          BuscarVendedor("a.co_usr","",0);
    }//GEN-LAST:event_butVenConActionPerformed

    
      public void BuscarVendedor(String campo,String strBusqueda,int tipo){
        // configurarVenConVendedor();  //************************
        objVenConVen.setTitle("Listado de vendedores");
        if (objVenConVen.buscar(campo, strBusqueda )) {
            txtVenCod.setText(objVenConVen.getValueAt(1));
            txtVenNom.setText(objVenConVen.getValueAt(2));
        }
        else
        {     objVenConVen.setCampoBusqueda(tipo);
              objVenConVen.cargarDatos();
              objVenConVen.show();
              if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
                  txtVenCod.setText(objVenConVen.getValueAt(1));
                  txtVenNom.setText(objVenConVen.getValueAt(2));
              }
              else{
                  txtVenCod.setText(strCodCom);
                  txtVenNom.setText(strDesLarCom);
              }
        }
    }
    
      
      
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        
       consultar();
         
         
    }//GEN-LAST:event_butConActionPerformed

    
    
    private void consultar(){
          if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
         
    }
    
    
    
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDatCli.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDatCli.setRowSelectionInterval(0, 0);
                tblDatCli.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
     
     
    
    
    
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        double dblSub, dblIva;
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objZafParSis.getCodigoEmpresa();
            intCodLoc=objZafParSis.getCodigoLocal();
            Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                Statement stm=con.createStatement();
                //Obtener la condición.
                String strAux="";
                if (txtCliCod.getText().length()>0)
                    strAux+=" AND cli.co_cli=" + txtCliCod.getText();
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(cli.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(cli.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
               
                    
		
		   String strAuxCom="";
                   if (!(txtVenCod.getText().equals(""))) {
                      strAuxCom = " AND ( a1.co_com is null or a1.co_com="+txtVenCod.getText()+" )"; 
                   }
		   
		
                //Obtener el número total de registros.
                String strSQL="";
                strSQL+="SELECT COUNT(*) ";
                strSQL+=" FROM (  ";
               // strSQL+=" select a1.co_cli, a1.tx_ide, a1.tx_nom from tbm_cli as a1 where a1.co_Emp="+intCodEmp;
             
            strSQL +="SELECT distinct(cocli), cli.tx_nom, cli.tx_ide FROM ( " +
            " select co_cli as cocli from ( " +
            " SELECT  distinct(cli.co_cli), cli.tx_nom,  cli.tx_ide FROM tbm_cabMovInv AS a1  " +
            "                INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  " +
            "                INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
            "                a1.co_tipDoc=a3.co_tipDoc)                " +
          //  "                INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli)   " +
            "  INNER JOIN tbr_cliloc AS loc ON (loc.co_emp=a1.co_emp and loc.co_loc=a1.co_loc and loc.co_cli=a1.co_cli)    " +
            "  INNER JOIN tbm_cli AS cli ON (cli.co_emp=loc.co_emp and cli.co_cli=loc.co_cli)     " +
           
            "                WHERE a1.co_emp="+intCodEmp+"  "+strConAuxCon+"  "+strAuxCom+"   and a3.ne_mod in (1,3)    AND (a2.mo_pag+a2.nd_abo)<>0    " +
            "                AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')   AND ( a2.nd_porret  = 0 or a2.nd_porret  is null )    " +
            "                AND a2.fe_ven+cli.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+"   AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0 " +
            " ) as x  " +
            "    UNION ALL   " +
        
	   " select co_cli  from (  " +
            " select * from (  " +
            " select co_cli, nd_moncre,   sum(valpen) as moncli from ( " +
            "       SELECT  " +
            "       cli.co_cli, cli.nd_moncre,  abs(a2.nd_abo) as nd_abo,  (abs(a2.mo_pag) - abs(a2.nd_abo)) as valpen  " +
            "       FROM tbm_cabMovInv AS a1    " +
            "       INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  " +
            "                INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
            "                a1.co_tipDoc=a3.co_tipDoc)   "+
            " INNER JOIN tbr_cliloc AS loc ON (loc.co_emp=a1.co_emp and loc.co_loc=a1.co_loc and loc.co_cli=a1.co_cli)   "+
            " INNER JOIN tbm_cli AS cli ON (cli.co_emp=loc.co_emp and cli.co_cli=loc.co_cli)   "+
		    
            "       WHERE a1.co_emp="+intCodEmp+"  "+strConAuxCon+" "+strAuxCom+"   and a3.ne_mod in (1,3)     " +
            "       AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')   AND ( a2.nd_porret  = 0 or a2.nd_porret  is null)    " +
            "       AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0    " +
            "       ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg   " +
            " ) as  x  group by  co_cli, nd_moncre  " +
            " ) as x  where  moncli > nd_moncre  " +
            " ) as x  " +
		    
		    
		    
	    "    UNION ALL   " +
	    " select co_cli   from ( " +
	    "  SELECT  distinct(cli.co_cli) as co_cli   FROM tbm_cabMovInv AS a1  " +
	    "    INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
		    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
		    " a1.co_tipDoc=a3.co_tipDoc)  " +
		    " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli) " +
		    "  WHERE a1.co_emp="+intCodEmp+"  "+strConAuxCon+" "+strAuxCom+" and a3.ne_mod=1  " +
		    "  AND (a2.mo_pag+a2.nd_abo)<>0    " +
		    " AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')  " +
		    "  AND a2.st_sop='S' AND a2.st_entsop='N'  AND a1.fe_doc+cli.ne_diagrachqfec <= "+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) as x  " +
			    
		    
            " ) AS  X1 " +
            " inner join tbm_cli as cli on ( X1.cocli = cli.co_cli )  where cli.co_emp="+intCodEmp+" ";
                 
                strSQL+=strAux;
                strSQL+=" ) AS b1";
                  
                System.out.println(" > " +  strSQL );
                  
                intNumTotReg=objUti.getNumeroRegistro(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                //Armar la sentencia SQL.
                
                //strSQL+=" select a1.co_cli, a1.tx_ide, a1.tx_nom from tbm_cli as a1 where a1.co_Emp="+intCodEmp;
            /*    strSQL="SELECT  distinct(cli.co_cli), cli.tx_nom,  cli.tx_ide FROM tbm_cabMovInv AS a1 " +
                " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   " +
                " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
                "  a1.co_tipDoc=a3.co_tipDoc)    and a1.co_tipdoc=1               " +
                " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli)  " +
                "  WHERE a1.co_emp="+intCodEmp+"  AND (a2.mo_pag+a2.nd_abo)<>0   " +
                "   AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')   AND a2.nd_porret  = 0 "+
                "  AND a2.fe_ven+cli.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+"   AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0  ";
             */
                
                     
  
                
            strSQL="SELECT distinct(cocli), cli.tx_nom, cli.tx_ide  FROM ( " +
            " select co_cli as cocli  from ( " +
            " SELECT  distinct(cli.co_cli), cli.tx_nom,  cli.tx_ide FROM tbm_cabMovInv AS a1  " +
            "                INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  " +
            "                INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
            "                a1.co_tipDoc=a3.co_tipDoc)                " +
           // "                INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli)   " +
            "  INNER JOIN tbr_cliloc AS loc ON (loc.co_emp=a1.co_emp and loc.co_loc=a1.co_loc and loc.co_cli=a1.co_cli)    " +
            "  INNER JOIN tbm_cli AS cli ON (cli.co_emp=loc.co_emp and cli.co_cli=loc.co_cli)     " +
            "                WHERE a1.co_emp="+intCodEmp+" "+strConAuxCon+" "+strAuxCom+" and a3.ne_mod in (1,3)  AND (a2.mo_pag+a2.nd_abo)<>0    " +
            "                AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')   AND ( a2.nd_porret  = 0 or a2.nd_porret  is null)  " +
            "                AND a2.fe_ven+cli.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+"   AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0 " +
            " ) as x  " +
            "    UNION ALL   " +
            " select co_cli   from (  " +
            " select * from (  " +
            " select co_cli, nd_moncre,   sum(valpen) as moncli from ( " +
            "       SELECT  " +
            "       cli.co_cli, cli.nd_moncre,  abs(a2.nd_abo) as nd_abo,  (abs(a2.mo_pag) - abs(a2.nd_abo)) as valpen  " +
            "       FROM tbm_cabMovInv AS a1    " +
            "       INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  " +
            " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
            " a1.co_tipDoc=a3.co_tipDoc)  " +
	    " INNER JOIN tbr_cliloc AS loc ON (loc.co_emp=a1.co_emp and loc.co_loc=a1.co_loc and loc.co_cli=a1.co_cli)   "+
            " INNER JOIN tbm_cli AS cli ON (cli.co_emp=loc.co_emp and cli.co_cli=loc.co_cli)   "+
            "       WHERE a1.co_emp="+intCodEmp+" "+strConAuxCon+"  "+strAuxCom+"  and a3.ne_mod in ( 1,3)    " +
            "       AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')  AND ( a2.nd_porret  = 0 or a2.nd_porret  is null)    " +
            "       AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0    " +
            "       ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg   " +
            " ) as  x  group by  co_cli, nd_moncre  " +
            " ) as x   where  moncli > nd_moncre  " +
            " ) as x  " +
		    
		    
		      "    UNION ALL   " +
	    " select co_cli   from ( " +
	    "  SELECT  distinct(cli.co_cli) as co_cli   FROM tbm_cabMovInv AS a1  " +
	    "    INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
		    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
		    " a1.co_tipDoc=a3.co_tipDoc)  " +
		    " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli) " +
		    "  WHERE a1.co_emp="+intCodEmp+"  "+strConAuxCon+" "+strAuxCom+" and a3.ne_mod = 1  " +
		    "  AND (a2.mo_pag+a2.nd_abo)<>0    " +
		    " AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')  " +
		    "  AND a2.st_sop='S' AND a2.st_entsop='N'  AND a1.fe_doc+cli.ne_diagrachqfec <= "+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) as x  " +
			    
		    
            " ) AS  X1 " +
            " inner join tbm_cli as cli on ( X1.cocli = cli.co_cli )  where cli.co_emp="+intCodEmp+" ";
                
                strSQL+=strAux;
                strSQL+=" ORDER BY cli.tx_nom";
               
		  
		System.out.println(" .. "+ strSQL );
		
                ResultSet rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LIN,"");
                        vecReg.add(INT_TBL_COD, rst.getString("cocli"));
                        vecReg.add(INT_TBL_IDE ,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_NOM, rst.getString("tx_nom"));
                        //vecReg.add(INT_TBL_CRE, rst.getString("cre"));
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDatCli.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDatCli.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatCli.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
      
        
     
      
      
      private boolean cargarMovDoc(){
         int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (tblDatCli.getSelectedRow()!=-1)
            {
                Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con!=null)
                {
           
                    intCodEmp=objZafParSis.getCodigoEmpresa();
                    intCodLoc=objZafParSis.getCodigoLocal();
                    String strAux="";
                    if (!(txtVenCod.getText().equals(""))) {
                      // strAux = " AND a1.co_com="+txtVenCod.getText();
		       strAux = " AND ( a1.co_com is null or a1.co_com="+txtVenCod.getText()+" )";  
                    }
                    
                    String sql ="SELECT a2.co_loc, a2.co_tipdoc, a3.tx_descor, a2.co_doc, a2.co_reg, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven, a2.nd_porret, abs(a2.mo_pag) as mo_pag, " +
                    " abs(a2.nd_abo) as nd_abo,  (abs(a2.mo_pag)-abs(a2.nd_abo)) as valpen " +
	            "  ,a2.tx_numchq ,a2.fe_venchq ,a2.nd_monchq "+
		    "  FROM tbm_cabMovInv AS a1  " +
                    " INNER join tbm_cli as cli ON (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli) " +
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc  " +
                    " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  " +
                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND " +
                    " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+intCodEmp+" AND a1.co_cli="+objTblMod.getValueAt(tblDatCli.getSelectedRow(), INT_TBL_COD)+" "+
                    " AND a1.st_reg IN ('A','R','C','F') AND  a3.ne_mod in (1,3)  AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0  and ( a2.nd_porret  = 0 or a2.nd_porret  is null )   " +
                    " AND a2.fe_ven+cli.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+"   "+strAux+"   ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg"; 
                    //  System.out.println(" OK .... "+ sql);
                       
                    Statement stm=con.createStatement();
                    ResultSet rst=stm.executeQuery(sql);
                    //Limpiar vector de datos.
                    vecDat.clear(); 
                    while (rst.next())
                        {
                                vecReg=new Vector();
                                    vecReg.add(INT_TBL2_LINEA,"");
                                    vecReg.add(INT_TBL2_CODLOC, rst.getString("co_loc") );
                                    vecReg.add(INT_TBL2_CODTIPDOC, rst.getString("co_tipdoc") );
                                    vecReg.add(INT_TBL2_TIPDOC, rst.getString("tx_descor") );
                                    vecReg.add(INT_TBL2_CODDOC, rst.getString("co_doc") );
                                    vecReg.add(INT_TBL2_CODREG, rst.getString("co_reg") );
                                    vecReg.add(INT_TBL2_NUMDOC, rst.getString("ne_numdoc") );
                                    vecReg.add(INT_TBL2_FECDOC, rst.getString("fe_doc") );
                                    vecReg.add(INT_TBL2_DIACRE, rst.getString("ne_diacre") );
                                    vecReg.add(INT_TBL2_FECVEN, rst.getString("fe_ven") );
                                    vecReg.add(INT_TBL2_RETE, rst.getString("nd_porret") );
                                    vecReg.add(INT_TBL2_VALDOC, rst.getString("mo_pag") );
                                    vecReg.add(INT_TBL2_ABONO, rst.getString("nd_abo") );
                                    vecReg.add(INT_TBL2_VALPEN, rst.getString("valpen") );
	
				    vecReg.add(INT_TBL2_NUMCHQ, rst.getString("tx_numchq") );
	                            vecReg.add(INT_TBL2_FECCHE, rst.getString("fe_venchq") );
	                            vecReg.add(INT_TBL2_VALCHQ, rst.getString("nd_monchq") );
				    
                                vecDat.add(vecReg);
                        
                        }
                     objTblModDocVen.setData(vecDat);
                     tblDatDocven.setModel(objTblModDocVen);
                     vecDat.clear();
                     
                    objTblTotDocVen.calcularTotales();    
                    //objTblTotDocVen.setValueAt( ""+dblValDoc ,0, 13 );
                    //objTblTotDocVen.setValueAt( ""+dblValPen ,0, 14 );
                    //objTblTotDocVen.setValueAt( ""+dblValPorVen ,0, 15 );
                    //objTblTotDocVen.setValueAt( ""+dblValVen ,0, 16 );  
                   
                    objTblTotDocVen.setValueAt("" +  objTblTotDocVen.getValueAt(0,INT_TBL2_VALPEN),0,0 ); 
                
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                
                   
                            
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
      }
       
    
      
      
        
      private boolean cargarSopChe(){
         int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (tblDatCli.getSelectedRow()!=-1)
            {
                Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con!=null)
                {
           
                   
                    intCodEmp=objZafParSis.getCodigoEmpresa();
                    intCodLoc=objZafParSis.getCodigoLocal();
                    String strAux="";
                    if (!(txtVenCod.getText().equals(""))) {
                      // strAux = " AND a1.co_com="+txtVenCod.getText(); 
			 strAux = " AND ( a1.co_com is null or a1.co_com="+txtVenCod.getText()+" )"; 
                    }
                         
                    String sql ="SELECT a2.co_loc, a2.co_tipdoc, a3.tx_descor, a2.co_doc, a2.co_reg, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven, a2.nd_porret, " +
                    " abs(a2.mo_pag) as mo_pag, abs(a2.nd_abo) as nd_abo,  (abs(a2.mo_pag)-abs(a2.nd_abo)) as valpen  FROM tbm_cabMovInv AS a1 " +
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
                    " a1.co_tipDoc=a3.co_tipDoc)                    " +
                    " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli) " +
                    " WHERE a1.co_emp="+intCodEmp+"  "+
                    " AND (a2.mo_pag+a2.nd_abo)<>0   AND a1.co_cli="+objTblMod.getValueAt(tblDatCli.getSelectedRow(), INT_TBL_COD)+" " +
                    " AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C') " +
                    " AND a2.st_sop='S' AND a2.st_entsop='N' "+strAux+" AND a1.fe_doc+cli.ne_diagrachqfec <= "+objZafParSis.getFuncionFechaHoraBaseDatos()+"";  
                    Statement stm=con.createStatement();
                    ResultSet rst=stm.executeQuery(sql);
                    //Limpiar vector de datos.
                    vecDat.clear(); 
                    while (rst.next())  
                        {
                                vecReg=new Vector();
                                    vecReg.add(INT_TBL2_LINEA,"");
                                    vecReg.add(INT_TBL2_CODLOC, rst.getString("co_loc") );
                                    vecReg.add(INT_TBL2_CODTIPDOC, rst.getString("co_tipdoc") );
                                    vecReg.add(INT_TBL2_TIPDOC, rst.getString("tx_descor") );
                                    vecReg.add(INT_TBL2_CODDOC, rst.getString("co_doc") );
                                    vecReg.add(INT_TBL2_CODREG, rst.getString("co_reg") );
                                    vecReg.add(INT_TBL2_NUMDOC, rst.getString("ne_numdoc") );
                                    vecReg.add(INT_TBL2_FECDOC, rst.getString("fe_doc") );
                                    vecReg.add(INT_TBL2_DIACRE, rst.getString("ne_diacre") );
                                    vecReg.add(INT_TBL2_FECVEN, rst.getString("fe_ven") );
                                    vecReg.add(INT_TBL2_RETE, rst.getString("nd_porret") );
                                    vecReg.add(INT_TBL2_VALDOC, rst.getString("mo_pag") );
                                    vecReg.add(INT_TBL2_ABONO, rst.getString("nd_abo") );
                                    vecReg.add(INT_TBL2_VALPEN, rst.getString("valpen") );
                                vecDat.add(vecReg);
                        }
                     objTblModSopChe.setData(vecDat);
                     tblDatSopChe.setModel(objTblModSopChe);
                     vecDat.clear();
                
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
      }
         
        
      
         
  
      
      private boolean cargarProCre(){
         int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (tblDatCli.getSelectedRow()!=-1)
            {
                Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con!=null)
                {
           
                    intCodEmp=objZafParSis.getCodigoEmpresa();
                    intCodLoc=objZafParSis.getCodigoLocal();
                     String strAux="";
                    if (!(txtVenCod.getText().equals(""))) {
                      // strAux = " AND a1.co_com="+txtVenCod.getText(); 
		       strAux = " AND ( a1.co_com is null or a1.co_com="+txtVenCod.getText()+" )"; 
                    }
                       
                     
                     
                     

                        String Strsql = "  SELECT * FROM ( " +
                        "  select  nd_moncre ,   sum(valpen) as velpen from (   " +
                        "  SELECT  cli.nd_moncre,   " +
                        "  abs(a2.mo_pag) as mo_pag, abs(a2.nd_abo) as nd_abo,  (abs(a2.mo_pag) - abs(a2.nd_abo)) as valpen  " +
                        "  FROM tbm_cabMovInv AS a1     " +
                        "  INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)    " +
                        "  INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND   a1.co_tipDoc=a3.co_tipDoc)            " +
                        "  INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli)    " +
                        "  WHERE a1.co_emp="+intCodEmp+" and a3.ne_mod in (1,3)  " +
			"  AND a1.co_cli="+objTblMod.getValueAt(tblDatCli.getSelectedRow(), INT_TBL_COD)+"   " +
                        "  AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')   AND ( a2.nd_porret  = 0 or a2.nd_porret  is null )    " +
			" AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0    " +
                        "  ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg  " +
                        "  ) as  x   group by  nd_moncre  ) as  x  where  velpen > nd_moncre  ";
                     Statement stm1=con.createStatement();
                     ResultSet rst1=stm1.executeQuery(Strsql);
                     if(rst1.next()){  
                     
                     
                     
                    String sql ="SELECT a2.co_loc, a2.co_tipdoc, a3.tx_descor, a2.co_doc, a2.co_reg, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven, a2.nd_porret, " +
                    " abs(a2.mo_pag) as mo_pag, abs(a2.nd_abo) as nd_abo,  (abs(a2.mo_pag) - abs(a2.nd_abo)) as valpen  FROM tbm_cabMovInv AS a1  " +
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  " +
                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND  " +
                    " a1.co_tipDoc=a3.co_tipDoc)                  " +
                    " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli)  " +
                    "  WHERE a1.co_emp="+intCodEmp+" and a3.ne_mod in (1,3) " +
                    "  AND (a2.mo_pag+a2.nd_abo)<>0   AND a1.co_cli="+objTblMod.getValueAt(tblDatCli.getSelectedRow(), INT_TBL_COD)+" " +
                    "  AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')  "+strAux+"  AND ( a2.nd_porret  = 0 or a2.nd_porret  is null )  "  +
                   // "  AND a2.fe_ven+cli.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+"   " +
                    "  AND  ((abs(a2.mo_pag) - abs(a2.nd_abo))) > 0 " +
                    "  ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg ";
                      
                   //   System.out.println(" OK ............. "+ sql );
                    Statement stm=con.createStatement();
                    ResultSet rst=stm.executeQuery(sql);
                    //Limpiar vector de datos.
                    vecDat.clear(); 
                    while (rst.next())  
                        {
                                vecReg=new Vector();
                                    vecReg.add(INT_TBL2_LINEA,"");
                                    vecReg.add(INT_TBL2_CODLOC, rst.getString("co_loc") );
                                    vecReg.add(INT_TBL2_CODTIPDOC, rst.getString("co_tipdoc") );
                                    vecReg.add(INT_TBL2_TIPDOC, rst.getString("tx_descor") );
                                    vecReg.add(INT_TBL2_CODDOC, rst.getString("co_doc") );
                                    vecReg.add(INT_TBL2_CODREG, rst.getString("co_reg") );
                                    vecReg.add(INT_TBL2_NUMDOC, rst.getString("ne_numdoc") );
                                    vecReg.add(INT_TBL2_FECDOC, rst.getString("fe_doc") );
                                    vecReg.add(INT_TBL2_DIACRE, rst.getString("ne_diacre") );
                                    vecReg.add(INT_TBL2_FECVEN, rst.getString("fe_ven") );
                                    vecReg.add(INT_TBL2_RETE, rst.getString("nd_porret") );
                                    vecReg.add(INT_TBL2_VALDOC, rst.getString("mo_pag") );
                                    vecReg.add(INT_TBL2_ABONO, rst.getString("nd_abo") );
                                    vecReg.add(INT_TBL2_VALPEN, rst.getString("valpen") );
                                vecDat.add(vecReg);
                        }
                     objTblModProCre.setData(vecDat);
                     tbldatProCre.setModel(objTblModProCre);
                     vecDat.clear();
                
                
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                   }
                    rst1.close();
                    stm1.close();
                    rst1=null;
                    stm1=null;
                    con.close();
                  
                    con=null;
                
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
      }
         
      
      
      
    
    private void txtCliNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusLost
        // TODO add your handling code here:
          if (!txtCliNom.getText().equalsIgnoreCase(strDesLarPrv)) {
            if (txtCliNom.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {  
                BuscarCliente("a.tx_nom",txtCliNom.getText(),1);
            }
        }
        else
            txtCliNom.setText(strDesLarPrv);
     
          
          //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCliCod.getText().length()>0)
        {
            optFil.setSelected(true);  
        }
           
                
            
    }//GEN-LAST:event_txtCliNomFocusLost

    private void txtCliCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusLost
        // TODO add your handling code here:
         if (!txtCliCod.getText().equalsIgnoreCase(strCodPrv)) {
            if (txtCliCod.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {
                BuscarCliente("a.co_cli",txtCliCod.getText(),0);
            }
        }
        else  txtCliCod.setText(strCodPrv);
        
         
         
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCliCod.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
         
    }//GEN-LAST:event_txtCliCodFocusLost

    
    
    
     
  public void BuscarCliente(String campo,String strBusqueda,int tipo){
      objVenConCli.setTitle("Listado de Clientes");
      if (objVenConCli.buscar(campo, strBusqueda )) {
          txtCliCod.setText(objVenConCli.getValueAt(1));
          txtCliNom.setText(objVenConCli.getValueAt(2));
         
      }
      else
      {     objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
              
            }
            else{
                txtCliCod.setText(strCodPrv);
                txtCliNom.setText(strDesLarPrv);
            }
      }
  }
  
  
  
    
    
    private void txtCliNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusGained
        // TODO add your handling code here:
          strDesLarPrv=txtCliNom.getText();
        txtCliNom.selectAll();
    }//GEN-LAST:event_txtCliNomFocusGained

    private void txtCliCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusGained
        // TODO add your handling code here:
        strCodPrv=txtCliCod.getText();
        txtCliCod.selectAll();
    }//GEN-LAST:event_txtCliCodFocusGained

    private void txtCliNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliNomActionPerformed
        // TODO add your handling code here:
          txtCliNom.transferFocus();
    }//GEN-LAST:event_txtCliNomActionPerformed

    private void txtCliCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCodActionPerformed
        // TODO add your handling code here:
         txtCliCod.transferFocus();
    }//GEN-LAST:event_txtCliCodActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
         objVenConCli.setTitle("Listado de Clientes");
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            txtCliCod.setText(objVenConCli.getValueAt(1));
            txtCliNom.setText(objVenConCli.getValueAt(2));
        }
        
        
         //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCliCod.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
        
    }//GEN-LAST:event_butCliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkCreDebMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCreDebMosMovRegActionPerformed
	// TODO add your handling code here:
	 
    	    if (chkCreDebMosMovReg.isSelected()){
                 
                    objTblModDocVen.removeAllRows();
                    objTblModSopChe.removeAllRows();
                    objTblModProCre.removeAllRows();
                    
                    cargarMovDoc();
                    cargarSopChe();
                    cargarProCre();
                   
                }
	
    }//GEN-LAST:event_chkCreDebMosMovRegActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVenCon;
    private javax.swing.JCheckBox chkCreDebMosMovReg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.ButtonGroup optGrp;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCreDebMovReg;
    private javax.swing.JPanel panCreDebReg;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnCreDeb;
    private javax.swing.JScrollPane spnDocVen;
    private javax.swing.JScrollPane spnDocVentot;
    private javax.swing.JSplitPane sppCreDeb;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatCli;
    private javax.swing.JTable tblDatDocven;
    private javax.swing.JTable tblDatSopChe;
    private javax.swing.JTable tblTotDocVen;
    private javax.swing.JTable tbldatProCre;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    // End of variables declaration//GEN-END:variables
    
    
      
     private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblDatCli.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LIN:
                    strMsg="";
                    break;
                case INT_TBL_COD:
                    strMsg="Codigo de Cliente";
                    break;
                case INT_TBL_IDE:
                    strMsg="Identificación del Cliente";
                    break;
                case INT_TBL_NOM:
                    strMsg="Nombre del Cliente";
                    break;
               
            }
            tblDatCli.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
    
      private class ZafMouMotTit extends java.awt.event.MouseMotionAdapter {
         javax.swing.JTable tblDatAux;
          public ZafMouMotTit(javax.swing.JTable tblDatDocven) {
            tblDatAux=tblDatDocven;
        }
            
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblDatAux.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL2_LINEA:
                    strMsg="";
                    break;
                case INT_TBL2_CODLOC:
                    strMsg="Codigo de Local";
                    break;
                case INT_TBL2_CODTIPDOC:
                    strMsg="Codigo de Tipo de Documento";
                    break;
                case INT_TBL2_TIPDOC:
                    strMsg="Tipo de Documento";
                    break;
                case INT_TBL2_CODDOC:
                    strMsg="Codigo de Documento";
                    break;
                case INT_TBL2_CODREG:
                    strMsg="Codigo de Registro";
                    break;
                case INT_TBL2_NUMDOC:
                    strMsg="Numero de Documento";
                    break;
                case INT_TBL2_FECDOC:
                    strMsg="Fecha de Documento";
                    break;
                case INT_TBL2_DIACRE:
                    strMsg="Dias de Credito";
                    break; 
                case INT_TBL2_FECVEN:
                    strMsg="Fecha de Vencimiento";
                    break;
                case INT_TBL2_RETE:
                    strMsg="Porsentaje de retención";
                    break;
                case INT_TBL2_VALDOC:
                    strMsg="Valor del Documento";
                    break;      
                case INT_TBL2_ABONO:
                    strMsg="Abono Realizado ";
                    break;
                case INT_TBL2_VALPEN:
                    strMsg="Valor Pendiente";
                    break;       
		    
               case INT_TBL2_NUMCHQ:
                    strMsg="Numero de Cheque.";
                    break;   	    
	       case INT_TBL2_FECCHE:
                    strMsg="Fecha de Vencimiento del Cheque.";
                    break;   	    
	       case INT_TBL2_VALCHQ:
                    strMsg="valor del Cheque.";
                    break;   	    
		
		    
            }
            tblDatAux.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     
    
      
      
      
       
       
      /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {  
                
                if (chkCreDebMosMovReg.isSelected()){
                 
                    objTblModDocVen.removeAllRows();
                    objTblModSopChe.removeAllRows();
                    objTblModProCre.removeAllRows();
                    
                    cargarMovDoc();
                    cargarSopChe();
                   // if(objTblMod.getValueAt(tblDatCli.getSelectedRow(), INT_TBL_CRE).toString().equals("S"))
                    cargarProCre();
                   
                }
                    
            }
        }
    }
     
     
     
    
    
}
