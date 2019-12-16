/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom52.java
 *
 * Created on 29/10/2012, 10:31:09 PM
 */   

package Compras.ZafCom52;
   
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Ventas.ZafVen21.ZafVen21_01;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * @author efloresa 
 * Listado de guías de remisión (Formato 1: Detallado por item).
 * 
 */
public class ZafCom52 extends JInternalFrame {

    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblModBE,objTblModDT, objTblModResumen;
    private ZafTblCelRenChk objTblCelRenChkBE;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafTblCelRenLbl objTblCelRenLblCol;
    private ZafVenCon vcoCli;    
    private ZafTblTot objTblTot;                        //JTable de totales.

    private ZafThreadGUI objThrGUI;

     // TABLA DE BODEGAS
    private static final int INT_TBL_LIN   = 0 ;
    private static final int INT_TBL_CHKBOD= 1 ;
    private static final int INT_TBL_CODBOD= 2 ;
    private static final int INT_TBL_NOMBOD= 3 ;
    
    // TABLA DE DATOS
    private static final int INT_TBLD_LIN           =0;
    private static final int INT_TBLD_CODEMP        =1;
    private static final int INT_TBLD_CODLOC        =2;
    private static final int INT_TBLD_CODTIPDOC     =3;
    private static final int INT_TBLD_DESTIPDOC     =4;
    private static final int INT_TBLD_CODDOC        =5;
    private static final int INT_TBLD_NUMDOC        =6;
    private static final int INT_TBLD_FECDOC        =7;
    private static final int INT_TBLD_TXDESTI       =8;
    private static final int INT_TBLD_PTOLLE        =9;
    private static final int INT_TBLD_FORRET        =10;    
    private static final int INT_TBLD_CODVEH        =11;
    private static final int INT_TBLD_DESVEH        =12;
    private static final int INT_TBLD_CODITM        =13;
    private static final int INT_TBLD_CODALT        =14;
    private static final int INT_TBLD_NOMITM        =15;
    private static final int INT_TBLD_UNIMED        =16;
    private static final int INT_TBLD_CODBOD        =17;
    private static final int INT_TBLD_NOMBOD        =18;
    private static final int INT_TBLD_CANITM        =19;
    private static final int INT_TBLD_PESUNIKGR     =20;
    private static final int INT_TBLD_PESTOTKGR     =21;
    private static final int INT_TBLD_BUTANX1       =22;    

    static final int INT_TBL_DAT_NUM_TOT_CES=2;                 //Número total de columnas estáticas.
    static final int INT_TBL_DAT_NUM_TOT_CDI=5;                 //Número total de columnas dinámicas.
    
    private static final int INT_TBLRES_LIN       =0;
    private static final int INT_TBLRES_DES       =1;
    
    private boolean blnMarTodCanTblBod=true;

    private Vector vecDat, vecReg;
    
    private String DATE_FORMAT = "yyyy-MM-dd";
    private String strTit = "Mensaje del Sistema Zafiro.";
    private String strVersion=" v. 1.0 ";
    private String strCodDest;
    private String strDesLarDest;
    
    private ZafVenCon objVenConVeh;
    private String strCodVeh;
    private String strDesLarVeh;
    
    private String strPrimerDiaMes;
    private String strUltimoDiaMes;

    private int intNumColCnfEstIni;//numero de columnas creadar inicialmente, con las que se crea la tabla en configurarFrm()
    private int intNumColAdiRes;//numero de columnas que se deben adicionar al modelo por vistos buenos   
    private int intNumColIniRes;//numero de columna desde donde empieza la columna adicionada por visto bueno
    private int intNumColFinRes;//numero de columna hasta donde termina la columna adicionada por visto bueno
    
    private final int intNumColDin=4;
    private final int interval=-3; // cuenta
    
    private ArrayList arlRegTitResumen, arlDatTitResumen;
    final int INT_ARL_TIT_COD_RES=0;
    final int INT_ARL_TIT_NOM_RES=1;
    
    private ArrayList arlRegRes, arlDatRes;
    final int INT_ARL_COD_RES=0;
    final int INT_ARL_NOM_RES=1;
    final int INT_ARL_COL_RES=2;
    final int INT_ARL_INI_MES_RES=3;
    final int INT_ARL_FIN_MES_RES=4;

    private String strFecIniMes;
    
    /** Creates new form ZafCom78 */
    public ZafCom52(ZafParSis objZafParsis) {
        try{
            this.objParSis = (ZafParSis) objZafParsis.clone();
            objUti = new ZafUtil();

            objTblCelRenLblCol = new ZafTblCelRenLbl();

            initComponents(); 
            vecDat=new Vector();
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            
            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 68);
            
            objSelFec.setFlechaDerechaSeleccionada(false);
            objSelFec.setFlechaIzquierdaSeleccionada(false);
            
            arlDatTitResumen=new ArrayList();
            arlDatRes=new ArrayList();
            
            this.setTitle(objParSis.getNombreMenu()+" "+ strVersion );
            lblTit.setText( objParSis.getNombreMenu() );
        }catch (CloneNotSupportedException e){ 
            objUti.mostrarMsgErr_F1(this, e); 
        }
    }

    private boolean configurarFormDT(){
        boolean blnres=false;
        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBLD_LIN,"");
        vecCab.add(INT_TBLD_CODEMP,"Cod.Emp");
        vecCab.add(INT_TBLD_CODLOC,"Cod.Loc");
        vecCab.add(INT_TBLD_CODTIPDOC,"Cod.Tip.Doc");
        vecCab.add(INT_TBLD_DESTIPDOC,"Tip.Doc");
        vecCab.add(INT_TBLD_CODDOC,"Cod.Doc");
        vecCab.add(INT_TBLD_NUMDOC,"Num.Doc.");
        vecCab.add(INT_TBLD_FECDOC,"Fec.Doc");
        vecCab.add(INT_TBLD_TXDESTI,"Destinatario");
        vecCab.add(INT_TBLD_PTOLLE,"Destino.");
        vecCab.add(INT_TBLD_FORRET,"For.Ret");        
        vecCab.add(INT_TBLD_CODVEH,"Cod. Veh.");
        vecCab.add(INT_TBLD_DESVEH,"Vehiculo");
        vecCab.add(INT_TBLD_CODITM,"Cod.Itm.");
        vecCab.add(INT_TBLD_CODALT,"Cod.Alt.");
        vecCab.add(INT_TBLD_NOMITM,"Nombre del Item.");
        vecCab.add(INT_TBLD_UNIMED,"Unidad");
        vecCab.add(INT_TBLD_CODBOD, "Cod.Bod.");
        vecCab.add(INT_TBLD_NOMBOD, "Bodega.");
        vecCab.add(INT_TBLD_CANITM, "Cantidad.");
        vecCab.add(INT_TBLD_PESUNIKGR,"Pes.Unit.");
        vecCab.add(INT_TBLD_PESTOTKGR,"Pes.Tot.");
        vecCab.add(INT_TBLD_BUTANX1,"Gui. Rem.");        
        
        objTblModDT=new ZafTblMod();
        objTblModDT.setHeader(vecCab);
        tblDat.setModel(objTblModDT);

         //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        objTblModDT.setColumnDataType(INT_TBLD_NUMDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblModDT.setColumnDataType(INT_TBLD_PESUNIKGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        objTblModDT.setColumnDataType(INT_TBLD_PESTOTKGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBLD_LIN);

        //Configurar JTable: Editor de búsqueda.
        ZafTblBus zafTblBus = new ZafTblBus(tblDat);

        ZafTblOrd zafTblOrd = new ZafTblOrd(tblDat);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcmAux=tblDat.getColumnModel();

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBLD_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBLD_DESTIPDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_NUMDOC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_FECDOC).setPreferredWidth(85);
        tcmAux.getColumn(INT_TBLD_TXDESTI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBLD_PTOLLE).setPreferredWidth(180);
        tcmAux.getColumn(INT_TBLD_FORRET).setPreferredWidth(60);

        tcmAux.getColumn(INT_TBLD_DESVEH).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_CODALT).setPreferredWidth(85);
        tcmAux.getColumn(INT_TBLD_NOMITM).setPreferredWidth(180);
        tcmAux.getColumn(INT_TBLD_UNIMED).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_NOMBOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBLD_CANITM).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_PESUNIKGR).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBLD_PESTOTKGR).setPreferredWidth(85);
        tcmAux.getColumn(INT_TBLD_BUTANX1).setPreferredWidth(20);
        
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBLD_CODEMP);
        arlColHid.add(""+INT_TBLD_CODLOC);
        arlColHid.add(""+INT_TBLD_CODTIPDOC);
        arlColHid.add(""+INT_TBLD_CODDOC);        
        arlColHid.add(""+INT_TBLD_CODVEH);
        arlColHid.add(""+INT_TBLD_CODITM);
        arlColHid.add(""+INT_TBLD_CODBOD);        
        
        objTblModDT.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        tcmAux.getColumn(INT_TBLD_DESTIPDOC).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBLD_NUMDOC).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBLD_FECDOC).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBLD_TXDESTI).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBLD_PTOLLE).setCellRenderer(objTblCelRenLblCol);
        tcmAux.getColumn(INT_TBLD_FORRET).setCellRenderer(objTblCelRenLblCol);

        //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
        int intCol[]={INT_TBLD_PESTOTKGR};
        objTblTot=new ZafTblTot(scpDat, spnTotal, tblDat, tblTotal, intCol);    
    
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBLD_BUTANX1);
        objTblModDT.setColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);

        tblDat.getTableHeader().setReorderingAllowed(false);        
        objTblCelRenButDG=new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBLD_BUTANX1).setCellRenderer(objTblCelRenButDG);
        objTblCelRenButDG.addTblCelRenListener(new ZafTblCelRenAdapter() {
            int intFilSel, intColSel;        
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                
            }
        });
        
        //Configurar JTable: Editor de celdas.
        objTblCelEdiButGenDG=new ZafTblCelEdiButGen();
        tcmAux.getColumn(INT_TBLD_BUTANX1).setCellEditor(objTblCelEdiButGenDG);
        objTblCelEdiButGenDG.addTableEditorListener(new ZafTableAdapter() {
            int intFilSel, intColSel;
            @Override
            public void beforeEdit(ZafTableEvent evt) { 
            
            }

            @Override
            public void afterEdit(ZafTableEvent evt) {
               intColSel=tblDat.getSelectedColumn();
               intFilSel=tblDat.getSelectedRow();
               if (intFilSel!=-1) {
                   switch (intColSel) { 
                     case INT_TBLD_BUTANX1:
                        mostrarGuiasRemision( objTblModDT.getValueAt(intFilSel, INT_TBLD_CODEMP).toString(), 
                                              objTblModDT.getValueAt(intFilSel, INT_TBLD_CODLOC).toString(), 
                                              objTblModDT.getValueAt(intFilSel, INT_TBLD_CODTIPDOC).toString(), 
                                              objTblModDT.getValueAt(intFilSel, INT_TBLD_CODDOC).toString() );
                      break;
                     default: break;
                   }
            }} 
        });
        
        objTblModDT.setModoOperacion(ZafTblMod.INT_TBL_EDI); 
        tcmAux=null;
        return blnres;
    }
    
    private void configurarFormResumen(){
        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBLRES_LIN,"");
        vecCab.add(INT_TBLRES_DES,"Descripcion");        
        
        objTblModResumen=new ZafTblMod();
        objTblModResumen.setHeader(vecCab);
        tblDatResumen.setModel(objTblModResumen);

         //Configurar JTable: Establecer tipo de selección.
        tblDatResumen.setRowSelectionAllowed(true);
        tblDatResumen.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblDatResumen, INT_TBLRES_LIN);

        //Configurar JTable: Editor de búsqueda.
        ZafTblBus zafTblBus = new ZafTblBus(tblDatResumen);

        ZafTblOrd zafTblOrd = new ZafTblOrd(tblDatResumen);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDatResumen);

        ZafMouMotAdaResumen objMouMotAda=new ZafMouMotAdaResumen();
        tblDatResumen.getTableHeader().addMouseMotionListener(objMouMotAda);

        tblDatResumen.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcmAux=tblDatResumen.getColumnModel();
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBLRES_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBLRES_DES).setPreferredWidth(170);

        objTblModResumen.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        intNumColCnfEstIni=objTblModResumen.getColumnCount();
        //intColModIni=objTblModResumen.getColumnCount();
        System.out.println("intNumColCnfEstIni: " + intNumColCnfEstIni);
        tcmAux=null;
    }
    
    private boolean configurarFormBE(){       
        boolean blnres=false;

        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LIN,"");
        vecCab.add(INT_TBL_CHKBOD," ");
        vecCab.add(INT_TBL_CODBOD,"Cód.Bod");
        vecCab.add(INT_TBL_NOMBOD,"Nom.Bod");

        objTblModBE=new ZafTblMod();
        objTblModBE.setHeader(vecCab);
        tblBod.setModel(objTblModBE);

        //Configurar JTable: Establecer la fila de cabecera.
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblBod, INT_TBL_LIN);

        tblBod.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcmAux=tblBod.getColumnModel();
        tblBod.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(380);
        tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(60);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKBOD);
        objTblModBE.setColumnasEditables(vecAux);
        vecAux=null;
        //Configurar JTable: Editor de la tabla.
        ZafTblEdi zafTblEdi = new ZafTblEdi(tblBod);

        objTblCelRenChkBE = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChkBE);
        objTblCelRenChkBE=null;

        objTblCelEdiChkBE = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChkBE);
        objTblCelEdiChkBE.addTableEditorListener(new ZafTableAdapter() {            
            @Override
            public void beforeEdit(ZafTableEvent evt){
                
            }
            @Override
            public void afterEdit(ZafTableEvent evt) {

            }
        });

        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBod.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tblDatMouseClickedBE(evt);
            }
        });

        objTblModBE.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        return blnres;
    }

    private void tblDatMouseClickedBE(MouseEvent evt){
        int i, intNumFil;
        try {
            intNumFil=tblBod.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_CHKBOD){
                if (blnMarTodCanTblBod){//Mostrar todas las columnas.                     
                    for (i=0; i<intNumFil; i++) { 
                        tblBod.setValueAt( false, i, INT_TBL_CHKBOD); 
                    } 
                    blnMarTodCanTblBod=false;
                }else{ //Ocultar todas las columnas. 
                    for (i=0; i<intNumFil; i++) { 
                        tblBod.setValueAt( true, i, INT_TBL_CHKBOD); 
                    } 
                    blnMarTodCanTblBod=true;
                 }
            }
        }catch(Exception e){ 
            objUti.mostrarMsgErr_F1(this, e); 
        }
    }

    public void Configura_ventana_consulta(){
        configurarFormBE();
        configurarFormDT();
        configurarFormResumen();
        cargarBod();      
    }
    
    private boolean configurarVenConDest() {
        boolean blnRes=true;
        String strSql;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_clides");
            arlCam.add("a.tx_nomclides");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");
            
            strSql = " select distinct a.co_clides, trim(a.tx_nomclides) as tx_nomclides "
                    + " from tbm_cabguirem as a "
                    + " where co_emp=" + objParSis.getCodigoEmpresa() + " " 
                    + " and co_loc=" +objParSis.getCodigoLocal() + " " 
                    + " and co_tipdoc=( select co_tipdoc from tbm_cabtipdoc where co_emp=a.co_emp and co_loc=a.co_loc and co_tipdoc=a.co_tipdoc ) "
                    + " and ( ne_numdoc is null or ne_numdoc = 0 ) " 
                    + " and st_reg='A' "
                    + " order by 2 ";
            
            vcoCli=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de Destinatarios", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, JLabel.RIGHT);
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        

    private boolean configurarVenConVehiculos() {
        boolean blnRes=true;
        String strSql;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_veh");
            arlCam.add("tx_pla");
            arlCam.add("tx_deslar");
            arlCam.add("tx_marca");
            arlCam.add("nd_pessopkgr");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Placa");
            arlAli.add("Descripcion");
            arlAli.add("Marca");
            arlAli.add("Peso Kgr");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("250");
            arlAncCol.add("100");
            arlAncCol.add("70");

            if (objParSis.getCodigoUsuario()==1) {
                strSql= " select b.co_veh, b.tx_pla, b.tx_deslar, upper(c.tx_deslar) as tx_marca, round(b.nd_pessopkgr,2) as nd_pessopkgr "
                      + " from tbr_vehLocprg as a "
                      + " inner join tbm_veh as b on (a.co_veh=b.co_veh) "
                      //+ " inner join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " left outer join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " " 
                      + " and a.co_loc=" +objParSis.getCodigoLocal() + " "
                      + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                      + " and a.st_reg not in ('E', 'I') "
                      + " and b.st_reg not in ('E', 'I') "
                      //+ " and c.st_reg not in ('E', 'I') "
                      + " order by 1 ";
            } else {
                strSql= " select b.co_veh, b.tx_pla, b.tx_deslar, upper(c.tx_deslar) as tx_marca, round(b.nd_pessopkgr,2) as nd_pessopkgr "
                      + " from tbr_vehLocPrgUsr as a "
                      + " inner join tbm_veh as b on (a.co_veh=b.co_veh) "
                      //+ " inner join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " left outer join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " " 
                      + " and a.co_loc=" +objParSis.getCodigoLocal() + " "
                      + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                      + " and a.co_usr=" + objParSis.getCodigoUsuario() + " "
                      + " and a.st_reg not in ('E', 'I') "
                      + " and b.st_reg not in ('E', 'I') "
                      //+ " and c.st_reg not in ('E', 'I')"
                      + " order by 1";
            }
            
            objVenConVeh=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSql, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenConVeh.setCampoBusqueda(1);
        }catch (Exception e) {  
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;
    }
    
    private boolean mostrarVenConDest(int intTipBus){
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(0);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodDest.setText(vcoCli.getValueAt(1));
                        txtDesDest.setText(vcoCli.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a.co_clides", txtCodDest.getText())) {
                        txtCodDest.setText(vcoCli.getValueAt(1));
                        txtDesDest.setText(vcoCli.getValueAt(2));
                    }else{
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodDest.setText(vcoCli.getValueAt(1));
                            txtDesDest.setText(vcoCli.getValueAt(2));
                        }else 
                            txtCodDest.setText(this.strCodDest);                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a.tx_nomclides", txtDesDest.getText())) {
                        txtCodDest.setText(vcoCli.getValueAt(1));
                        txtDesDest.setText(vcoCli.getValueAt(2));
                    }else {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodDest.setText(vcoCli.getValueAt(1));
                            txtDesDest.setText(vcoCli.getValueAt(2));
                        }else 
                            txtDesDest.setText(strDesLarDest);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public boolean mostrarVenConVeh(int intTipBus){        
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConVeh.setCampoBusqueda(0);
                    objVenConVeh.setVisible(true);
                    if (objVenConVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodVeh.setText(objVenConVeh.getValueAt(1));
                        txtDesVeh.setText(objVenConVeh.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConVeh.buscar("co_veh", txtCodVeh.getText())) {
                        txtCodVeh.setText(objVenConVeh.getValueAt(1));
                        txtDesVeh.setText(objVenConVeh.getValueAt(3));
                    }else{
                        objVenConVeh.setCampoBusqueda(0);
                        objVenConVeh.setCriterio1(11);
                        objVenConVeh.cargarDatos();
                        objVenConVeh.setVisible(true);
                        if (objVenConVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodVeh.setText(objVenConVeh.getValueAt(1));
                            txtDesVeh.setText(objVenConVeh.getValueAt(3));
                        }else 
                            txtCodVeh.setText(strCodVeh);                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConVeh.buscar("tx_deslar", txtDesVeh.getText())) {
                        txtCodVeh.setText(objVenConVeh.getValueAt(1));
                        txtDesVeh.setText(objVenConVeh.getValueAt(3));
                    }else {
                        objVenConVeh.setCampoBusqueda(2);
                        objVenConVeh.setCriterio1(11);
                        objVenConVeh.cargarDatos();
                        objVenConVeh.setVisible(true);
                        if (objVenConVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodVeh.setText(objVenConVeh.getValueAt(1));
                            txtDesVeh.setText(objVenConVeh.getValueAt(3));
                        }else 
                            txtDesVeh.setText(strDesLarVeh);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean cargarBod(){  
        Connection con=null; 
        Statement stm=null;
        ResultSet rst=null;
        boolean blnRes=true;
        String strSQL="";
        try {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
            if (con!=null){ 
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1) {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a2.co_bod, a2.tx_nom ";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    rst=stm.executeQuery(strSQL);
                }else {
                    //Armar la sentencia SQL.
                    strSQL="select  a1.co_bodgrp as co_bod, a2.tx_nom   from tbr_bodLocPrgUsr as a " +
                    " inner join tbr_bodEmpBodGrp as a1  on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) " +
                    " inner join tbm_bod as a2  on (a2.co_emp=a1.co_empgrp and a2.co_bod=a1.co_bodgrp ) " +
                    " where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" " +
                    " and a.co_usr="+objParSis.getCodigoUsuario()+" and  a.co_mnu="+objParSis.getCodigoMenu()+" " +
                    "  and a.tx_natBod IN  ('E') " +
                    " group by a1.co_bodgrp, a2.tx_nom  order by co_bodgrp ";
                    rst=stm.executeQuery(strSQL);
                }

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LIN,"");
                    vecReg.add(INT_TBL_CHKBOD, true);
                    vecReg.add(INT_TBL_CODBOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_NOMBOD,rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModBE.setData(vecDat);
                tblBod.setModel(objTblModBE);
                vecDat.clear();
            }
        }catch (SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
            try{
                if (rst != null) 
                    rst.close();
                rst=null;
                
                if (stm != null) 
                    stm.close();
                stm=null;
                
                if (con != null) 
                    con.close();
                con=null;
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }
       
    private void mostrarGuiasRemision(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
        String  strSql02="";
        try{
            strSql02=" select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
                    + " from tbr_detguirem as a2  "
                    + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
                    + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  ) "
                    + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
                    + " where a2.co_emp="+strCodEmp+" and a2.co_loc="+strCodLoc+" and a2.co_tipdoc="+strCodTipDoc+" and a2.co_doc="+strCodDoc+" and a4.st_reg='A' "
                    + " group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
                    + " order by  a4.ne_numdoc ";
            System.out.println("ZafCom52.mostrarGuiasRemision: " + strSql02);
            ZafVen21_01 obj1 = new ZafVen21_01(objParSis, this, strSql02 );
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
            obj1.show();
        }catch(Exception e) {   
            objUti.mostrarMsgErr_F1(this,e);  
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblVeh = new javax.swing.JLabel();
        txtDesVeh = new javax.swing.JTextField();
        txtCodVeh = new javax.swing.JTextField();
        butVeh = new javax.swing.JButton();
        lblDest = new javax.swing.JLabel();
        txtCodDest = new javax.swing.JTextField();
        txtDesDest = new javax.swing.JTextField();
        butDest = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodDest2 = new javax.swing.JTextField();
        txtDesDest2 = new javax.swing.JTextField();
        butDest2 = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        scpDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotal = new javax.swing.JScrollPane();
        tblTotal = new javax.swing.JTable();
        scpResumen = new javax.swing.JScrollPane();
        tblDatResumen = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader() {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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
                cerrar(evt);
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

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFilCab.setAutoscrolls(true);
        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setText("Todos las documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFilCab.add(optTod);
        optTod.setBounds(10, 160, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFilCab.add(optFil);
        optFil.setBounds(10, 180, 370, 20);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodega"));
        jPanel4.setPreferredSize(new java.awt.Dimension(294, 100));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblBod);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panFilCab.add(jPanel4);
        jPanel4.setBounds(10, 70, 630, 90);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Numero de documento"));
        jPanel3.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Desde:");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(30, 20, 50, 14);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        jPanel3.add(txtCodAltDes);
        txtCodAltDes.setBounds(80, 20, 100, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Hasta:");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(240, 20, 50, 14);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
        });
        jPanel3.add(txtCodAltHas);
        txtCodAltHas.setBounds(280, 20, 100, 19);

        panFilCab.add(jPanel3);
        jPanel3.setBounds(30, 260, 500, 50);
        jPanel3.getAccessibleContext().setAccessibleName("");

        lblVeh.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVeh.setText("Vehiculo:");
        panFilCab.add(lblVeh);
        lblVeh.setBounds(30, 230, 70, 14);

        txtDesVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesVehActionPerformed(evt);
            }
        });
        txtDesVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesVehFocusLost(evt);
            }
        });
        panFilCab.add(txtDesVeh);
        txtDesVeh.setBounds(140, 230, 340, 20);

        txtCodVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVehActionPerformed(evt);
            }
        });
        txtCodVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVehFocusLost(evt);
            }
        });
        panFilCab.add(txtCodVeh);
        txtCodVeh.setBounds(100, 230, 40, 20);

        butVeh.setText("...");
        butVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVehActionPerformed(evt);
            }
        });
        butVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                butVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                butVehFocusLost(evt);
            }
        });
        panFilCab.add(butVeh);
        butVeh.setBounds(490, 230, 20, 20);

        lblDest.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDest.setText("Destinatario:");
        panFilCab.add(lblDest);
        lblDest.setBounds(30, 210, 70, 14);

        txtCodDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDestActionPerformed(evt);
            }
        });
        txtCodDest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDestFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDestFocusLost(evt);
            }
        });
        panFilCab.add(txtCodDest);
        txtCodDest.setBounds(100, 210, 40, 20);

        txtDesDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesDestActionPerformed(evt);
            }
        });
        txtDesDest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesDestFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesDestFocusLost(evt);
            }
        });
        panFilCab.add(txtDesDest);
        txtDesDest.setBounds(140, 210, 340, 20);

        butDest.setText("...");
        butDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDestActionPerformed(evt);
            }
        });
        panFilCab.add(butDest);
        butDest.setBounds(490, 210, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Destinatario:");
        panFilCab.add(jLabel6);
        jLabel6.setBounds(30, 210, 70, 14);

        txtCodDest2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDest2ActionPerformed(evt);
            }
        });
        txtCodDest2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDest2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDest2FocusLost(evt);
            }
        });
        panFilCab.add(txtCodDest2);
        txtCodDest2.setBounds(100, 210, 40, 20);

        txtDesDest2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesDest2ActionPerformed(evt);
            }
        });
        txtDesDest2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesDest2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesDest2FocusLost(evt);
            }
        });
        panFilCab.add(txtDesDest2);
        txtDesDest2.setBounds(140, 210, 340, 20);

        butDest2.setText("...");
        butDest2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDest2ActionPerformed(evt);
            }
        });
        panFilCab.add(butDest2);
        butDest2.setBounds(490, 210, 20, 20);

        tabFrm.addTab("Filtro", panFilCab);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        scpDat.setViewportView(tblDat);

        panRpt.add(scpDat, java.awt.BorderLayout.CENTER);

        spnTotal.setPreferredSize(new java.awt.Dimension(320, 35));

        tblTotal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotal.setViewportView(tblTotal);

        panRpt.add(spnTotal, java.awt.BorderLayout.PAGE_END);

        tabFrm.addTab("Reporte", panRpt);

        tblDatResumen.setModel(new javax.swing.table.DefaultTableModel(
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
        scpResumen.setViewportView(tblDatResumen);

        tabFrm.addTab("Resumen", scpResumen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

        jPanel2.setLayout(new java.awt.BorderLayout());

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

        jPanel2.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel2.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-471)/2, 700, 471);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        consultar();
    }//GEN-LAST:event_butConActionPerformed

    private boolean validarDat(){
        boolean blnRes=true;
        int intEstBod=0;

        for(int x=0; x<tblBod.getRowCount();x++){ 
            if(tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true")){ 
                intEstBod=1; 
            }
        }

        if(intEstBod==0){ 
            MensajeInf("LA BODEGA ORIGEN ES OBLIGATORIO..."); 
            return false;
        }
        return blnRes;
    }

    private void MensajeInf(String strMensaje){
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     */
    private boolean cargarDetReg(String strFil){
        boolean blnRes=true;
        Connection conn;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql="", strEstConf="", strSqlAux="", strSqlAux01="";
        String strBodDes="";
        try{
            butCon.setText("Detener");
            conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn!=null){
                stmLoc=conn.createStatement();

                if(optFil.isSelected()){
                    if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                        strSqlAux01=" AND numguia BETWEEN "+txtCodAltDes.getText()+" AND "+txtCodAltHas.getText()+"  ";
                }

                for(int x=0; x<tblBod.getRowCount();x++){
                    if(tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true")){
                        if(!strBodDes.equals("")) strBodDes+=",";
                            strBodDes += tblBod.getValueAt( x, INT_TBL_CODBOD).toString();
                    }
                }

                strSql=" select * "
                     + " from ( "
                     + " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor, a1.tx_deslar, "
                     + " case when a.ne_numdoc is null then 0 else a.ne_numdoc end as numguia, "
                     + " a.fe_doc, a.tx_nomclides as destinatario, a.tx_dirclides as destino, a2.tx_deslar as forret, "
                     + " a5.co_veh, a5.tx_deslar as vehiculo, "
                     + " a3.co_itm, a3.tx_codalt, a3.tx_nomitm, a3.tx_unimed, a3.nd_can, "
                     + " a7.nd_pesitmkgr as pesitm, (a7.nd_pesitmkgr*abs(a3.nd_can)) as pestot, "
                     + " a6.co_bod, a6.co_bodGrp, a6.co_empGrp, a4.tx_nom as bodega "
                     + " from tbm_cabguirem as a "
                     + " inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar) "
                     + " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                     + " left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                     + " inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc) "
                     + " inner join tbm_bod as a4 on (a4.co_emp=a.co_emp and a4.co_bod=a.co_ptopar ) "
                     + " left join tbm_veh as a5 on (a5.co_veh=a.co_veh) "
                     + " left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                     + " where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                     + " and a.st_reg='A' "
                     + " and a.co_tipdoc in ( 101,102 ) " + strFil + " " + strSqlAux + " "
                     + " ) as z "
                     + " where numguia > 0 " + strSqlAux01+" "
                     + " group by co_emp, co_loc, co_tipdoc, co_doc, tx_descor, tx_deslar, fe_doc, destinatario, numguia, destino, forret, co_bod, co_bodGrp, co_empGrp, bodega, co_veh, vehiculo, co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, pesitm, pestot "
                     + " order by numguia ";

                System.out.println("ZafCom52.cargarDetReg: " + strSql );

                rstLoc=stmLoc.executeQuery(strSql);
                vecDat.clear();
                while (rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBLD_LIN,"");
                    vecReg.add(INT_TBLD_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBLD_CODLOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBLD_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                    vecReg.add(INT_TBLD_DESTIPDOC, rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBLD_CODDOC, rstLoc.getString("co_doc"));
                    vecReg.add(INT_TBLD_NUMDOC, rstLoc.getString("numguia"));
                    vecReg.add(INT_TBLD_FECDOC, rstLoc.getString("fe_doc"));
                    vecReg.add(INT_TBLD_TXDESTI, rstLoc.getString("destinatario"));
                    vecReg.add(INT_TBLD_PTOLLE, rstLoc.getString("destino"));
                    vecReg.add(INT_TBLD_FORRET, rstLoc.getString("forret"));

                    vecReg.add(INT_TBLD_CODVEH, rstLoc.getString("co_veh"));
                    vecReg.add(INT_TBLD_DESVEH, rstLoc.getString("vehiculo"));
                    vecReg.add(INT_TBLD_CODITM, rstLoc.getString("co_itm"));
                    vecReg.add(INT_TBLD_CODALT, rstLoc.getString("tx_codalt"));
                    vecReg.add(INT_TBLD_NOMITM, rstLoc.getString("tx_nomitm"));
                    vecReg.add(INT_TBLD_UNIMED, rstLoc.getString("tx_unimed"));
                    vecReg.add(INT_TBLD_CODBOD, rstLoc.getString("co_bod"));
                    vecReg.add(INT_TBLD_NOMBOD, rstLoc.getString("bodega"));
                    vecReg.add(INT_TBLD_CANITM, objUti.redondear(rstLoc.getString("nd_can"),2) );
                    vecReg.add(INT_TBLD_PESUNIKGR, objUti.redondear(objUti.parseDouble(rstLoc.getString("pesitm")),2) );
                    vecReg.add(INT_TBLD_PESTOTKGR, objUti.redondear(objUti.parseDouble(rstLoc.getString("pestot")),2) );
                    vecReg.add(INT_TBLD_BUTANX1, "");

                    vecDat.add(vecReg);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
                //Asignar vectores al modelo.
                objTblModDT.setData(vecDat);
                tblDat.setModel(objTblModDT);
                vecDat.clear();

                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                objTblTot.calcularTotales();
            }
        }catch (SQLException e){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }catch (Exception e){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;
    }
    
    private String sqlConFil(){
        String sqlFil="";
        switch (objSelFec.getTipoSeleccion()) {
            case 0: //Búsqueda por rangos
                sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 3: //Todo.
                break;
        }
        
        if (!txtCodDest.getText().equals("")){
            sqlFil+=" and a.co_clides = " + txtCodDest.getText() + " ";
        }
        
        if (!txtCodVeh.getText().equals(""))
            sqlFil+=" and a.co_veh = " + txtCodVeh.getText() + " ";
        
        return sqlFil;
     }

    private void exitform(){
        String strMsg;
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
            Runtime.getRuntime().gc();
            dispose();
        }

    }
            
    public void consultar(){
        if(validarDat())
            if (butCon.getText().equals("Consultar")) 
                if (objThrGUI==null) {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                } 
    }

    private boolean cargarDatResumen(){
        boolean blnRes = false;
        if (eliminaTodasColumnasAdicionadas() ){
            if (adicionaColumnasResumen() ){
                blnRes=true;
            }
        }
        return blnRes;
    }
    
    private boolean eliminaTodasColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            for (int i=(this.objTblModResumen.getColumnCount()-1); i>=(intNumColCnfEstIni); i--){
                objTblModResumen.removeColumn(this.tblDatResumen, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
            blnRes=false;
        }
        return blnRes;
    }
        
    /**
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
    private boolean adicionaColumnasResumen(){
        boolean blnRes=false;
        try{
            if(obtenerTituloColumnas()){
                if(numeroColumnasAdicionarResumen()){
                    if(generaColumnasResumen()){
                        if (cargarDetReg())
                            blnRes=true;                            
                    }
                }
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que adiciona las columnas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean generaColumnasResumen(){
        boolean blnRes=true;
        TableColumn tbc;
        try{
            TableColumnModel tcmAux=tblDatResumen.getColumnModel();
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDatResumen.getTableHeader();
            objTblHeaGrp.setHeight(16*3);

            int intConTmp=0;
            int intLeeDatArlTitCab=0;
            intNumColIniRes=intNumColCnfEstIni;
            System.out.println("intNumColIniRes: " + intNumColIniRes);

            for (int i=0; i<intNumColAdiRes; i++){
                tbc=new TableColumn(intNumColIniRes+i);
                tbc.setHeaderValue(" " + objUti.getStringValueAt(arlDatRes, i, INT_ARL_NOM_RES) + " ");
                objUti.setStringValueAt(arlDatRes, i, INT_ARL_COL_RES, String.valueOf((intNumColIniRes+i)));
                //Configurar JTable: Establecer el ancho de la columna.
                if (i%2 == 0)
                    tbc.setPreferredWidth(80);
                else
                    tbc.setPreferredWidth(42);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModResumen.addColumn(tblDatResumen, tbc);
                if(intConTmp==1){
                    ZafTblHeaColGrp objTblHeaColGrp=null;
                    objTblHeaColGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatTitResumen, intLeeDatArlTitCab, INT_ARL_TIT_NOM_RES));
                    objTblHeaColGrp.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

                    objTblHeaColGrp.add(tcmAux.getColumn((tblDatResumen.getColumnCount()-2)));
                    objTblHeaColGrp.add(tcmAux.getColumn((tblDatResumen.getColumnCount()-1)));
                    intConTmp=0;
                    intLeeDatArlTitCab++;
                }else{
                    intConTmp++;
                }
                tbc=null;
            }
            intNumColFinRes=objTblModResumen.getColumnCount();
            System.out.println("intNumColFinRes: " + intNumColFinRes);
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean numeroColumnasAdicionarResumen(){
        boolean blnRes=true;
        intNumColAdiRes=0;
        String strNomCol="";
        String strNomMes="";
        int intervalo= this.interval;
        arlDatRes.clear();
        try{
                intNumColAdiRes= (this.intNumColDin * 2);
                if (intNumColAdiRes==-1)
                    return false;

                for(int i=0; i<intNumColAdiRes; i++){
                    if ( i%2 == 0 ) { 
                        strNomCol="Peso";
                        strNomMes=obtenerPrimerUltimoDiaMes(intervalo);
                        intervalo+=1;
                    }
                    if ( i%2 != 0 ) strNomCol="%";
                    arlRegRes=new ArrayList();
                    arlRegRes.add(INT_ARL_COD_RES, "" + i );
                    arlRegRes.add(INT_ARL_NOM_RES, "" + strNomCol);
                    arlRegRes.add(INT_ARL_COL_RES, "");
                    arlRegRes.add(INT_ARL_INI_MES_RES, strPrimerDiaMes);
                    arlRegRes.add(INT_ARL_FIN_MES_RES, strUltimoDiaMes);
                    arlDatRes.add(arlRegRes);
                }
        }catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener información que será usada como títulos de las columnas adicionadas al modelo
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean obtenerTituloColumnas(){
        boolean blnRes=true;
        int intervalo=this.interval;
        
        arlDatTitResumen.clear();
        try{
            for (int i=0; i<intNumColDin; i++){
                String strNomMes=obtenerPrimerUltimoDiaMes(intervalo);
                arlRegTitResumen=new ArrayList();
                arlRegTitResumen.add(INT_ARL_TIT_COD_RES, "" + i );
                arlRegTitResumen.add(INT_ARL_TIT_NOM_RES, "" + strNomMes  );
                arlDatTitResumen.add(arlRegTitResumen);
                intervalo+=1;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private String obtenerPrimerUltimoDiaMes(int interval){
        String strMes = "";
        Calendar cal = Calendar.getInstance();
        
        //Date dateAux = objUti.parseDate(objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()), this.DATE_FORMAT);
        //cal.setTime(dateAux);        
        //System.out.println("Fecha Fin Seleccionada: " + cal.getTime());        
        
        cal.add(Calendar.MONTH, interval);        
        System.out.println("Fecha modificada: " + cal.getTime());
                
        strPrimerDiaMes = cal.get(Calendar.YEAR) + "-" + (((cal.get(Calendar.MONTH) + 1)<10)?"0"+(cal.get(Calendar.MONTH) + 1):(cal.get(Calendar.MONTH) + 1)) + "-" + ((cal.getActualMinimum(Calendar.DAY_OF_MONTH)<10)?"0" + cal.getActualMinimum(Calendar.DAY_OF_MONTH):cal.getActualMinimum(Calendar.DAY_OF_MONTH)) ;
        strUltimoDiaMes = cal.get(Calendar.YEAR) + "-" + (((cal.get(Calendar.MONTH) + 1)<10)?"0"+(cal.get(Calendar.MONTH) + 1):(cal.get(Calendar.MONTH) + 1)) + "-" + ((cal.getActualMaximum(Calendar.DAY_OF_MONTH)<10)?"0" + cal.getActualMaximum(Calendar.DAY_OF_MONTH):cal.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
        strMes = cal.getDisplayName(Calendar.MONTH, 1, Locale.getDefault()).toUpperCase() + "/" + cal.get(Calendar.YEAR);
        
        //System.out.println("Primer dia del mes: " + cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        //System.out.println("Ultimo dia del mes: " + cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("Primer dia del mes: " + strPrimerDiaMes );
        System.out.println("Ultimo dia del mes: " + strUltimoDiaMes );
        System.out.println("Nombre mes: " + strMes);
        
        return strMes;
    }
    
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        int intNumColSet=-1;
        Connection conn=null;
        Statement stmLoc=null;
        ResultSet rstLoc= null;
        String strBodDes="", strSql="";
        int intervalo=this.interval;
        strPrimerDiaMes=""; strUltimoDiaMes="";
        
        for(int x=0; x<tblBod.getRowCount();x++){
            if(tblBod.getValueAt(x, INT_TBL_CHKBOD).toString().equals("true")){
                if(!strBodDes.equals("")) strBodDes+=",";
                    strBodDes += tblBod.getValueAt( x, INT_TBL_CODBOD).toString();
            }
        }
        
        try{            
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conn.setAutoCommit(false);
            if (conn!=null){;
                stmLoc=conn.createStatement();

                for (int i=0; i<intNumColDin; i++){
                strPrimerDiaMes=""; strUltimoDiaMes="";
                String strNomMes=obtenerPrimerUltimoDiaMes(intervalo);

                strSql=" select descripcion, peso, porcentaje from "
                            + " ( " 
                            + " select  indice, descripcion from ( "
                            + " 	select 	( select 'Peso pendiente (Kg)' :: text as descripcion1 ) as descripcion, 1 as indice union all "
                            + " 	select 	( select 'Peso confirmado (Kg)' :: text as descripcion2 ) as descripcion, 2 as indice union all "
                            + " 	select 	( select 'Peso nunca confirmado (Kg)' :: text as descripcion3 ) as descripcion, 3 as indice union all "
                            + " 	select 	( select 'Peso total (Kg)' :: text as descripcion4 ) as descripcion, 4 as indice "
                            + " ) as v group by indice, descripcion "
                            + " ) as v, "
                            + " ( "
                            + " select indice, peso from ( "
                            + " select ( "
                            + " 		select round(sum((a7.nd_pesitmkgr*(abs(a3.nd_can) - (abs(a3.nd_cancon) + abs(a3.nd_cannunrec))))),2) as pesPen "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as peso, 1 as indice union all "
                            + " select ( "
                            + " 		select round(sum((a7.nd_pesitmkgr*abs(a3.nd_cancon))),2) as pesCon "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as peso, 2 as indice union all "
                            + " select ( "
                            + " 		select round(sum((a7.nd_pesitmkgr*abs(a3.nd_cannunrec))),2) as pesNunCon "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as peso, 3 as indice union all "
                            + " select ( "
                            + " 		select round(sum((a7.nd_pesitmkgr*abs(a3.nd_can))),2) as pesTot "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as peso, 4 as indice "
                            + " ) as x group by indice, peso "
                            + " ) as x, "
                            + " ( "
                            + " select indice, porcentaje from ( "
                            + " select (  "
                            + " 		select round(((sum((a7.nd_pesitmkgr*(abs(a3.nd_can) - (abs(a3.nd_cancon) + abs(a3.nd_cannunrec)))))*100)/sum((a7.nd_pesitmkgr*abs(a3.nd_can)))),2)  as porPen "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as porcentaje, 1 as indice union all "
                            + " select ( "
                            + " 		select round(((sum((a7.nd_pesitmkgr*abs(a3.nd_cancon)))*100)/sum((a7.nd_pesitmkgr*abs(a3.nd_can)))),2) as porCon "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as porcentaje, 2 as indice union all "
                            + " select ( "
                            + " 		select round(((sum((a7.nd_pesitmkgr*abs(a3.nd_cannunrec)))*100)/sum((a7.nd_pesitmkgr*abs(a3.nd_can)))),2) as porNunCon "
                            + " 		from tbm_cabguirem as a   "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 ) "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as porcentaje, 3 as indice union all "
                            + " select ( "
                            + " 		select round(((sum((a7.nd_pesitmkgr*abs(a3.nd_can)))*100)/sum((a7.nd_pesitmkgr*abs(a3.nd_can)))),2) as porTot "
                            + " 		from tbm_cabguirem as a "
                            + " 		inner join tbr_bodEmpBodGrp as a6 on (a6.co_emp=a.co_emp and a6.co_bod=a.co_ptopar ) "
                            + " 		inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            + " 		left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " 		inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc ) "
                            + " 		left join tbm_inv as a7 on (a7.co_emp=a3.co_emp and a7.co_itm=a3.co_itm ) "
                            + " 		where ( a6.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " and a6.co_bodGrp in ( " + strBodDes + " ) ) "
                            + " 		and a.st_reg='A' "
                            + " 		and a.fe_doc between " + objUti.codificar(this.strPrimerDiaMes) + " and " + objUti.codificar(this.strUltimoDiaMes) + " "
                            + " 		and a.co_tipdoc in ( 101,102 )  "
                            + " 		and (case when a.ne_numorddes is null then 0 else a.ne_numorddes end ) > 0 "
                            + " 	) as porcentaje, 4 as indice "
                            + " ) as y group by indice, porcentaje "
                            + " ) as y "
                            + " where v.indice = x.indice "
                            + " and v.indice = y.indice ";                        

                System.out.println("ZafCom52.cargarDetReg: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if (i==0) vecDat.clear();
                int iteracion=0;
                while (rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBLRES_LIN,"");
                    vecReg.add(INT_TBLRES_DES,  rstLoc.getString("descripcion"));
                    //adicionar las columnas de vistos buenos
                    for(int j=intNumColIniRes; j<intNumColFinRes;j++){
                        vecReg.add(j, null);
                    }

                    //cargar los datos en las columnas de visto bueno
                    for(int x=0; x<arlDatRes.size(); x++){
                        String strCodVisBueVal = objUti.getStringValueAt(arlDatRes, x, INT_ARL_COD_RES);
                        strFecIniMes=objUti.getStringValueAt(arlDatRes, x, INT_ARL_INI_MES_RES);
                        intNumColSet=objUti.getIntValueAt(arlDatRes, x, INT_ARL_COL_RES);
                        if (strPrimerDiaMes.equals(strFecIniMes)){
                            vecReg.setElementAt(objUti.redondear(objUti.parseDouble(rstLoc.getString("peso")),2), intNumColSet);
                            vecReg.setElementAt(objUti.redondear(objUti.parseDouble(rstLoc.getString("porcentaje")),2), (intNumColSet+1));
                            break;
                        } 
                    }
                    
                    if (i==0) { 
                        vecDat.add(vecReg); 
                    }else{
                        Vector aux = (Vector)vecDat.get(iteracion);
                        aux.setElementAt(vecReg.get(intNumColSet), intNumColSet);
                        aux.setElementAt(vecReg.get(intNumColSet+1), intNumColSet+1);
                    }
                    iteracion++;
                 }
                 intervalo+=1;
                 }
                 //Asignar vectores al modelo.
                 objTblModResumen.setData(vecDat);
                 tblDatResumen.setModel(objTblModResumen);
            }
        }catch(SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{
                if (rstLoc != null)
                    rstLoc.close();
                rstLoc=null;
                
                if (stmLoc != null)
                    stmLoc.close();
                stmLoc=null;
                
                if (conn != null)
                    conn.close();
                conn=null;                 
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitform();
}//GEN-LAST:event_butCerActionPerformed

    private void cerrar(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_cerrar
        // TODO add your handling code here:
        exitform();        
    }//GEN-LAST:event_cerrar

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        configurarVenConDest();
        configurarVenConVehiculos();    
    }//GEN-LAST:event_formInternalFrameOpened

    private void butVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVehActionPerformed
        // TODO add your handling code here:
        mostrarVenConVeh(0);
    }//GEN-LAST:event_butVehActionPerformed

    private void txtCodVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusLost
        // TODO add your handling code here:
        if (txtCodVeh.getText().equals("")) {
            txtCodVeh.setText("");
            //txtIdeCli.setText("");
            txtDesVeh.setText("");
        }else 
            mostrarVenConVeh(1);
    }//GEN-LAST:event_txtCodVehFocusLost

    private void txtCodVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusGained
        // TODO add your handling code here:
        strCodVeh=txtCodVeh.getText();
        txtCodVeh.selectAll();
    }//GEN-LAST:event_txtCodVehFocusGained

    private void txtCodVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVehActionPerformed
        // TODO add your handling code here:
        txtCodVeh.transferFocus();
    }//GEN-LAST:event_txtCodVehActionPerformed

    private void txtDesVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesVehFocusLost
        // TODO add your handling code here:
        if (txtDesVeh.getText().equals("")) {
            txtCodVeh.setText("");
            //txtIdeCli.setText("");
            txtDesVeh.setText("");
        }else 
            mostrarVenConVeh(3);
        
        if (txtCodVeh.getText().length() > 0){ 
            //optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesVehFocusLost

    private void txtDesVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesVehFocusGained
        // TODO add your handling code here:
        strDesLarVeh=txtDesVeh.getText();
        txtDesVeh.selectAll();
    }//GEN-LAST:event_txtDesVehFocusGained

    private void txtDesVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesVehActionPerformed
        // TODO add your handling code here:
        txtDesVeh.transferFocus();
    }//GEN-LAST:event_txtDesVehActionPerformed

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
            txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        txtCodAltDes.setText("");
        txtCodAltHas.setText("");
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDestActionPerformed
        // TODO add your handling code here:
        txtCodDest.transferFocus();  
    }//GEN-LAST:event_txtCodDestActionPerformed

    private void txtCodDestFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDestFocusGained
        // TODO add your handling code here:
        strCodDest=txtCodDest.getText();
        txtCodDest.selectAll();
    }//GEN-LAST:event_txtCodDestFocusGained

    private void txtCodDestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDestFocusLost
        // TODO add your handling code here:
        if (txtCodDest.getText().equals("")) {
            txtCodDest.setText("");
            txtDesDest.setText("");
        }else 
            mostrarVenConDest(1);
    }//GEN-LAST:event_txtCodDestFocusLost

    private void txtDesDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesDestActionPerformed
        // TODO add your handling code here:
        txtDesDest.transferFocus();
    }//GEN-LAST:event_txtDesDestActionPerformed

    private void txtDesDestFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDestFocusGained
        // TODO add your handling code here:
        strDesLarDest=txtDesDest.getText();
        txtDesDest.selectAll();
    }//GEN-LAST:event_txtDesDestFocusGained

    private void txtDesDestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDestFocusLost
        // TODO add your handling code here:
        if (txtDesDest.getText().equals("")) {
            txtCodDest.setText("");
            txtDesDest.setText("");
        }else 
            mostrarVenConDest(3);
        
        if (txtCodDest.getText().length() > 0){
            //optFil.setSelected(true);            
        }
    }//GEN-LAST:event_txtDesDestFocusLost

    private void butDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDestActionPerformed
        // TODO add your handling code here:
        this.mostrarVenConDest(0);
    }//GEN-LAST:event_butDestActionPerformed

    private void txtCodDest2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDest2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDest2ActionPerformed

    private void txtCodDest2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDest2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDest2FocusGained

    private void txtCodDest2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDest2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDest2FocusLost

    private void txtDesDest2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesDest2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesDest2ActionPerformed

    private void txtDesDest2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDest2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesDest2FocusGained

    private void txtDesDest2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesDest2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesDest2FocusLost

    private void butDest2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDest2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butDest2ActionPerformed

    private void butVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butVehFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_butVehFocusGained

    private void butVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butVehFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_butVehFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDest;
    private javax.swing.JButton butDest2;
    private javax.swing.JButton butVeh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDest;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVeh;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scpDat;
    private javax.swing.JScrollPane scpResumen;
    private javax.swing.JScrollPane spnTotal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatResumen;
    private javax.swing.JTable tblTotal;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodDest;
    private javax.swing.JTextField txtCodDest2;
    private javax.swing.JTextField txtCodVeh;
    private javax.swing.JTextField txtDesDest;
    private javax.swing.JTextField txtDesDest2;
    private javax.swing.JTextField txtDesVeh;
    // End of variables declaration//GEN-END:variables
    
    private class ZafThreadGUI extends Thread{
        @Override
        public void run(){
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if (!cargarDetReg( sqlConFil()))  {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
            }
            if ( !cargarDatResumen()){
                
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }else
                if (tblDatResumen.getRowCount()>0) {
                    tabFrm.setSelectedIndex(2);
                    tblDatResumen.setRowSelectionInterval(0, 0);
                    tblDatResumen.requestFocus();
                }                
            objThrGUI=null;
            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);
        }
    }
    
    private class ZafMouMotAda extends MouseMotionAdapter {
       @Override
       public void mouseMoved(MouseEvent evt) {
           int intCol=tblDat.columnAtPoint(evt.getPoint());
           String strMsg="";
           switch (intCol) {
               case INT_TBLD_LIN: strMsg=""; break;
               case INT_TBLD_CODEMP: strMsg="Codigo de empresa"; break;
               case INT_TBLD_CODLOC: strMsg="Codigo de local "; break;
               case INT_TBLD_CODTIPDOC: strMsg="Codigo de Tipo de Documento "; break;
               case INT_TBLD_DESTIPDOC: strMsg="Descripcion de tipo de Documento "; break;
               case INT_TBLD_CODDOC: strMsg="Codigo del Documento "; break;
               case INT_TBLD_NUMDOC: strMsg="Numero del Documento "; break;
               case INT_TBLD_FECDOC: strMsg="Fecha del Documento "; break;
               case INT_TBLD_TXDESTI: strMsg="Destinatario."; break; 
               case INT_TBLD_PTOLLE: strMsg="Destino (Punto de llegada)"; break; 
               case INT_TBLD_FORRET: strMsg="Forma de retiro "; break;
               case INT_TBLD_CODVEH: strMsg="Codigo del vehiculo "; break;
               case INT_TBLD_DESVEH: strMsg="Descripcion del vehiculo"; break;
               case INT_TBLD_CODITM: strMsg="Codigo de Item"; break;
               case INT_TBLD_CODALT: strMsg="Codigo alterno del itam"; break;
               case INT_TBLD_NOMITM: strMsg="Nombre del item"; break;
               case INT_TBLD_UNIMED: strMsg="Unidad de medida"; break;
               case INT_TBLD_CODBOD: strMsg="Codigo de bodega"; break;
               case INT_TBLD_NOMBOD: strMsg="Nombre de la bodega"; break;
               case INT_TBLD_CANITM: strMsg="Cantidad"; break;
               case INT_TBLD_PESUNIKGR: strMsg="Peso unitario (Kg) "; break;
               case INT_TBLD_PESTOTKGR: strMsg="Peso total (Kg) "; break;
               case INT_TBLD_BUTANX1: strMsg="Anexo 1"; break;
               default: strMsg=""; break;
           }
           tblDat.getTableHeader().setToolTipText(strMsg);
       }
    }
    
    private class ZafMouMotAdaResumen extends MouseMotionAdapter {
       @Override
       public void mouseMoved(MouseEvent evt) {
           int intCol=tblDatResumen.columnAtPoint(evt.getPoint());
           String strMsg="";
           switch (intCol) {
               case INT_TBLRES_LIN: strMsg=""; break;
               case INT_TBLRES_DES: strMsg="Descripcion"; break;
               default: strMsg=""; break;
           }
           tblDat.getTableHeader().setToolTipText(strMsg);
       }
    }

}

