/*
 * ZafCxC19.java
 * "Historial de transacciones del cliente"
 * Created on Abril 20, 2011
 */
package CxC.ZafCxC19;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafHeaRenLbl;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author  ilino
 */
public class ZafCxC19 extends javax.swing.JInternalFrame
{
    //Constantes: JTable "TblDat"
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_EMP=1;
    private static final int INT_TBL_DAT_COD_LOC=2;
    private static final int INT_TBL_DAT_COD_TIP_DOC=3;
    private static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    private static final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    private static final int INT_TBL_DAT_COD_DOC=6;
    private static final int INT_TBL_DAT_NUM_DOC_UNO=7;
    private static final int INT_TBL_DAT_NUM_DOC_DOS=8;
    private static final int INT_TBL_DAT_FEC_DOC=9;
    private static final int INT_TBL_DAT_COD_FOR_PAG=10;
    private static final int INT_TBL_DAT_FOR_PAG=11;
    private static final int INT_TBL_DAT_CRE=12;
    private static final int INT_TBL_DAT_DEB=13;
    private static final int INT_TBL_DAT_SAL=14;
    private static final int INT_TBL_DAT_NUM_FIL=15;
    private static final int INT_TBL_DAT_NUM_TOT_POS=16;
    
    //JTable: Tabla de "TblTipDoc"
    private final int INT_TBL_TIPDOC_LIN=0;
    private final int INT_TBL_TIPDOC_CHKSEL=1;
    private final int INT_TBL_TIPDOC_CODTIP=2;
    private final int INT_TBL_TIPDOC_DESCOR=3;
    private final int INT_TBL_TIPDOC_DESLAR=4;

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModTipDoc;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafSelFec objSelFec;
    private ZafMouMotAda objMouMotAda;
    private ZafTblEdi objTblEdi;
    private ZafTblBus objTblBus;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Render: Editar JCheckBox en JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAdaDat, objMouMotAdaTipDoc;   //ToolTipText en TableHeader.
    private ZafTblOrd objTblOrd;
    private ZafCom objCom;                                      //Objeto Comparator utilizado para realizar el ordenamiento.
    private ZafTblTot objTblTot;
    private ZafPerUsr objPerUsr;
    private ZafTblFilCab objTblFilCab;
    private ZafHeaRenLbl objHeaRenLbl;
    private ZafVenCon vcoCli, vcoEmp, vcoLoc;
    private ZafThreadGUI objThrGUI;
    private java.util.Date datFecAux;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;
    private boolean blnHayCam;     //Determina si hay cambios en el formulario.
    private boolean blnMarTodChkTblTipDoc=true;                 //Marcar todas las casillas de verificación del JTable de Tipos de documentos.     
    private String strCodEmp, strCodLoc, strNomEmp, strNomLoc;
    private String strCodCli, strDesLarCli, strIdeCli, strDirCli;
    private String strSQL, strAux, strTipDoc;
    
    /** Creates new form ZafCxC19 */
    public ZafCxC19(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            if (!configurarFrm())
                exitForm(null);
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * Este constructor crea una instancia de la clase ZafCxC19 y al mismo tiempo carga.
     * automáticamente el historial de Transacciones del cliente especificado de acuerdo al código.
     * de la empresa y el código del cliente.
     * @param obj El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa.
     * @param codigoCliente El código del cliente.
     */
    public ZafCxC19(ZafParSis obj, Integer codigoEmpresa, Integer codigoCliente)
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            objParSis.setCodigoEmpresa(codigoEmpresa.intValue());
            objParSis.setCodigoMenu(324);//el 324 es el codigo de menu de Historial de transacciones de clientes...CxC.ZafCxC19.ZafCxC19
            initComponents();
            if (!configurarFrm())
                exitForm(null);

            butCon.setVisible(true);
            butCon.setEnabled(true);
            butCer.setVisible(true);
            butCer.setEnabled(true);

            txtCodCli.setText(""+codigoCliente);
            mostrarVenConCli(1);

            blnCon=true;
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * Este constructor crea una instancia de la clase ZafCxC19 y al mismo tiempo carga.
     * automáticamente el historial de Transacciones del cliente especificado de acuerdo a la identificación del cliente.
     * @param obj El objeto ZafParSis.
     * @param identificacionCliente La identificación del cliente.
     */
    public ZafCxC19(ZafParSis obj, String identificacionCliente)
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            objParSis.setCodigoEmpresa(objParSis.getCodigoEmpresaGrupo());
            objParSis.setCodigoMenu(324);//el 324 es el codigo de menu de Historial de transacciones de clientes...CxC.ZafCxC19.ZafCxC19
            initComponents();
            if (!configurarFrm())
                exitForm(null);

            butCon.setVisible(true);
            butCon.setEnabled(true);
            butCer.setVisible(true);
            butCer.setEnabled(true);

            txtRucCli.setText(identificacionCliente);
            mostrarVenConCli(3);
            blnCon=true;
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try{
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.8");
            lblTit.setText(strAux);
            //Inicializar Objetos
            objUti=new ZafUtil();           
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            panFilFec.add(objSelFec);
            objSelFec.setBounds(4, 0, 472, 68);
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha del documento");            

            configurarVenConCli();
            configurarVenConEmp();
            configurarVenConLoc();
            configurarTblDat();
            configurarTblTipDoc();
            cargarTipDoc();   //Carga Tipos de Documentos
                        
            butCon.setVisible(false);
            butCon.setEnabled(false);
            butCer.setVisible(false);
            butCer.setEnabled(false);
            //butOrd.setVisible(false);

            lblEmp.setVisible(true);
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            txtRucCli.setVisible(false);

            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
            }
            else{
                txtCodCli.setEditable(false);
            }

            if(objParSis.getCodigoMenu()==324){
                if(objPerUsr.isOpcionEnabled(678)){//consultar
                    butCon.setVisible(true);
                    butCon.setEnabled(true);
                }
                if(objPerUsr.isOpcionEnabled(679)){//cerrar
                    butCer.setVisible(true);
                    butCer.setEnabled(true);
                }
            }           
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblDat()
    {
        boolean blnRes=true;           
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");//sin iva
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");//sin iva
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");//sin iva
            vecCab.add(INT_TBL_DAT_NUM_DOC_UNO,"Núm.Doc.1");//sin iva
            vecCab.add(INT_TBL_DAT_NUM_DOC_DOS,"Núm.Doc.2");//sin iva
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//sin iva
            vecCab.add(INT_TBL_DAT_COD_FOR_PAG,"Cód.For.Pag.");//sin iva
            vecCab.add(INT_TBL_DAT_FOR_PAG,"Forma de Pago");//sin iva
            vecCab.add(INT_TBL_DAT_CRE,"Créditos");//sin iva
            vecCab.add(INT_TBL_DAT_DEB,"Débitos");//sin iva
            vecCab.add(INT_TBL_DAT_SAL,"Saldo");//sin iva
            vecCab.add(INT_TBL_DAT_NUM_FIL,"#Fil");//sin iva
            vecCab.add(INT_TBL_DAT_NUM_TOT_POS,"Núm.Tot.Pos.");//sin iva

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //objColNum=new ZafColNumerada(tblCabTot,INT_TBL_DAT_CAB_LIN);     
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_UNO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_DOS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_FOR_PAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FOR_PAG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_SAL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FIL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NUM_TOT_POS).setPreferredWidth(70);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_TOT_POS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_UNO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Ocultar columnas del sistema.
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC_DOS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_FOR_PAG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_FIL, tblDat);

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CRE, INT_TBL_DAT_DEB,INT_TBL_DAT_SAL};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);   

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
  
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC_UNO:
                    strMsg="Número de documento 1";
                    break;
                case INT_TBL_DAT_NUM_DOC_DOS:
                    strMsg="Número de documento 2";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_COD_FOR_PAG:
                    strMsg="Código de la forma de pago";
                    break;
                case INT_TBL_DAT_FOR_PAG:
                    strMsg="Forma de pago";
                    break;
                case INT_TBL_DAT_CRE:
                    strMsg="Creditos";
                    break;
                case INT_TBL_DAT_DEB:
                    strMsg="Débitos";
                    break;
                case INT_TBL_DAT_SAL:
                    strMsg="Saldo";
                    break;
                case INT_TBL_DAT_NUM_FIL:
                    strMsg="Número de fila";
                    break;
                case INT_TBL_DAT_NUM_TOT_POS:
                    strMsg="Número total de postergaciones de cheques";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
     /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_TIPDOC_LIN, "");
            vecCab.add(INT_TBL_TIPDOC_CHKSEL, " ");
            vecCab.add(INT_TBL_TIPDOC_CODTIP, "Código");
            vecCab.add(INT_TBL_TIPDOC_DESCOR, "Tip.Doc.");
            vecCab.add(INT_TBL_TIPDOC_DESLAR, "Tipo documento");
            
            objTblModTipDoc = new ZafTblMod();
            objTblModTipDoc.setHeader(vecCab);
            tblDatTipDoc.setModel(objTblModTipDoc);

            //Configurar JTable: Establecer tipo de selección.
            tblDatTipDoc.setRowSelectionAllowed(true);
            tblDatTipDoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatTipDoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatTipDoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatTipDoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_TIPDOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TIPDOC_CHKSEL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_TIPDOC_CODTIP).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_TIPDOC_DESCOR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_TIPDOC_DESLAR).setPreferredWidth(221);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_TIPDOC_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatTipDoc, INT_TBL_LOC_LIN);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatTipDoc.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatTipDoc);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaTipDoc =new ZafMouMotAda();
            tblDatTipDoc.getTableHeader().addMouseMotionListener(objMouMotAdaTipDoc);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatTipDoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatTipDoc);
            tcmAux.getColumn(INT_TBL_TIPDOC_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatTipDoc);
            
            tblDatTipDoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaTipDoc());
            tblDatTipDoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblTipDocMouseClicked(evt);
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_TIPDOC_CHKSEL);
            objTblModTipDoc.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatTipDoc);
            tcmAux.getColumn(INT_TBL_TIPDOC_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_TIPDOC_CHKSEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_TIPDOC_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_TIPDOC_CODTIP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblModTipDoc.setModoOperacion(objTblModTipDoc.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    /**
     * ToolTips de Jtable: Tipos de documentos.
     */ 
    private class ZafMouMotAdaTipDoc extends MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDatTipDoc.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_TIPDOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_TIPDOC_CODTIP:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_TIPDOC_DESCOR:
                    strMsg="Descripción Corta Tipo de documento";
                    break;
                case INT_TBL_TIPDOC_DESLAR:
                    strMsg="Descripción Larga Tipo de documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatTipDoc.getTableHeader().setToolTipText(strMsg);
        }
    }        
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica el Local seleccionado en el el JTable de Locales.
     */
    private void tblTipDocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try 
        {
            intNumFil=objTblModTipDoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDatTipDoc.columnAtPoint(evt.getPoint())==INT_TBL_TIPDOC_CHKSEL)
            {
                if (blnMarTodChkTblTipDoc)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModTipDoc.setChecked(true, i, INT_TBL_TIPDOC_CHKSEL);
                    }
                    blnMarTodChkTblTipDoc=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModTipDoc.setChecked(false, i, INT_TBL_TIPDOC_CHKSEL);
                    }
                    blnMarTodChkTblTipDoc=true;
                }
            }
        }
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);   }
    }
    
    private void cargarTipDoc() 
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try 
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stm=conn.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc ";
                strSQL+=" FROM tbm_cabTipDoc as a1 ";
                strSQL+=" WHERE a1.ne_mod IN(1,3,5) ";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" ORDER BY a1.tx_desCor";
                
                /*if (objParSis.getCodigoUsuario()==1)
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc ";
                    strSQL+=" FROM tbr_tipdocprg as a ";
                    strSQL+=" INNER JOIN tbm_cabTipDoc as a1 ON (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipDoc=a1.co_tipDoc)";
                    strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" ORDER BY a1.tx_desCor";
                }
                else
                {
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" ORDER BY a1.tx_desCor";
                }*/                

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_TIPDOC_LIN,"");
                    vecReg.add(INT_TBL_TIPDOC_CHKSEL,true);
                    vecReg.add(INT_TBL_TIPDOC_CODTIP, rst.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_TIPDOC_DESCOR, rst.getString("tx_desCor") );
                    vecReg.add(INT_TBL_TIPDOC_DESLAR, rst.getString("tx_desLar") );
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                conn.close();
                rst=null;
                stm=null;
                conn=null;
                //Asignar vectores al modelo.
                objTblModTipDoc.setData(vecDat);
                tblDatTipDoc.setModel(objTblModTipDoc);
                blnMarTodChkTblTipDoc=false;
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }
    
 
    private boolean configurarVenConCli(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.

            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+=" SELECT 0 AS co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    strSQL+=" AND a1.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" GROUP BY a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
            }
            else{
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+=" SELECT 0 AS co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    strSQL+=" AND a1.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" GROUP BY a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else{
                    if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                        strSQL="";
                        strSQL+=" SELECT a2.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                        strSQL+=" FROM tbr_cliLoc AS a1 INNER JOIN tbm_cli AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a2.st_cli='S' AND st_reg IN('A')";
                        strSQL+=" ORDER BY a2.tx_nom";
                    }
                    else{
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                        strSQL+=" FROM tbm_cli AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.st_cli='S' AND st_reg IN('A')";
                        strSQL+=" ORDER BY a1.tx_nom";
                    }
                }
            }

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Clientes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");

            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" INNER JOIN tbr_usremp AS a2 ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
            strSQL+=" AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                strSQL+=" AND a1.co_emp IN(" + objParSis.getCodigoEmpresa() + ")";

            strSQL+=" ORDER BY a1.tx_nom";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConLoc(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Loc.");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("414");

            strSQL="";
            strSQL+=" SELECT a1.co_loc, a1.tx_nom";
            strSQL+=" FROM tbm_loc AS a1";
            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" INNER JOIN tbr_locusr AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
            }
            strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
            strSQL+=" AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                strSQL+=" AND a1.co_emp IN(" + objParSis.getCodigoEmpresa() + ")";
            
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     private boolean mostrarVenConCli(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    else{
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                        }
                        else{
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre de cliente".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    else{
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                        }
                        else{
                            txtNomCli.setText(strDesLarCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Cédula de Identidad".
                    if (vcoCli.buscar("a1.tx_ide", txtRucCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    else{
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                        }
                        else{
                            txtRucCli.setText(strIdeCli);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   

    private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoCli.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConLoc(int intTipBus){
        boolean blnRes=true;
        String strConAdd="";
        try{
            if(!txtCodEmp.getText().equals("")){
                strConAdd=" AND a1.co_emp=" + txtCodEmp.getText() + "";
                strConAdd+=" GROUP BY a1.co_loc, a1.tx_nom";
                strConAdd+=" ORDER BY a1.co_loc";
                vcoLoc.setCondicionesSQL(strConAdd);
                switch (intTipBus){
                    case 0: //Mostrar la ventana de consulta.
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        break;
                    case 1: //Búsqueda directa por "Número de cuenta".
                        if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText())){
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else{
                            vcoLoc.setCampoBusqueda(0);
                            vcoLoc.setCriterio1(11);
                            vcoLoc.cargarDatos();
                            vcoLoc.show();
                            if (vcoCli.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                                txtCodLoc.setText(vcoLoc.getValueAt(1));
                                txtNomLoc.setText(vcoLoc.getValueAt(2));
                            }
                            else{
                                txtCodLoc.setText(strCodLoc);
                            }
                        }
                        break;
                    case 2: //Búsqueda directa por "Descripción larga".
                        if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText())){
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else{
                            vcoLoc.setCampoBusqueda(2);
                            vcoLoc.setCriterio1(11);
                            vcoLoc.cargarDatos();
                            vcoLoc.show();
                            if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                            {
                                txtCodLoc.setText(vcoLoc.getValueAt(1));
                                txtNomLoc.setText(vcoLoc.getValueAt(2));
                            }
                            else{
                                txtNomLoc.setText(strNomLoc);
                            }
                        }
                        break;
                }
            }
            else{
                mostrarMsgInf("<HTML>Se debe ingresar una empresa para poder escoger un local.<BR>Verifique y vuelva a intentarlo.</HTML>");
                blnRes=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFilFec = new javax.swing.JPanel();
        panFilDet = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        txtRucCli = new javax.swing.JTextField();
        panTipDoc = new javax.swing.JPanel();
        spnTipDoc = new javax.swing.JScrollPane();
        tblDatTipDoc = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butOrd = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panFilFec.setPreferredSize(new java.awt.Dimension(0, 80));
        panFilFec.setLayout(new java.awt.BorderLayout());
        panFil.add(panFilFec, java.awt.BorderLayout.NORTH);

        panFilDet.setPreferredSize(new java.awt.Dimension(10, 172));
        panFilDet.setLayout(null);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFilDet.add(lblCli);
        lblCli.setBounds(10, 10, 80, 20);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFilDet.add(txtCodCli);
        txtCodCli.setBounds(90, 10, 76, 20);

        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        panFilDet.add(txtNomCli);
        txtNomCli.setBounds(166, 10, 364, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFilDet.add(butCli);
        butCli.setBounds(530, 10, 20, 20);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Cliente");
        panFilDet.add(lblEmp);
        lblEmp.setBounds(10, 30, 80, 20);

        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFilDet.add(txtCodEmp);
        txtCodEmp.setBounds(90, 30, 76, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFilDet.add(txtNomEmp);
        txtNomEmp.setBounds(166, 30, 364, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFilDet.add(butEmp);
        butEmp.setBounds(530, 30, 20, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Cliente");
        panFilDet.add(lblLoc);
        lblLoc.setBounds(10, 50, 80, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFilDet.add(txtCodLoc);
        txtCodLoc.setBounds(90, 50, 76, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFilDet.add(txtNomLoc);
        txtNomLoc.setBounds(166, 50, 364, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFilDet.add(butLoc);
        butLoc.setBounds(530, 50, 20, 20);
        panFilDet.add(txtRucCli);
        txtRucCli.setBounds(555, 10, 120, 20);

        panTipDoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipos de documentos"));
        panTipDoc.setLayout(new java.awt.BorderLayout());

        tblDatTipDoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTipDoc.setViewportView(tblDatTipDoc);

        panTipDoc.add(spnTipDoc, java.awt.BorderLayout.CENTER);

        panFilDet.add(panTipDoc);
        panTipDoc.setBounds(10, 90, 540, 110);

        panFil.add(panFilDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

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
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 52));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butOrd.setText("Ordenar");
        butOrd.setPreferredSize(new java.awt.Dimension(92, 25));
        butOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOrdActionPerformed(evt);
            }
        });
        panBot.add(butOrd);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        strCodCli=txtCodCli.getText();
        mostrarVenConCli(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtNomCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)){
            if (txtCodCli.getText().equals("")){
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConCli(1);
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodEmp.getText().equals("")){
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodEmp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)){
            if (txtCodLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConLoc(1);
        }
        else
            txtCodLoc.setText(strCodLoc);
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
            txtNomLoc.setText(strNomLoc);
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        strCodLoc=txtCodLoc.getText();
        vcoLoc.limpiar();
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal())
        {
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
    }//GEN-LAST:event_butConActionPerformed

    private void butOrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOrdActionPerformed
        ordenarRegistrosAscendente();
    }//GEN-LAST:event_butOrdActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butOrd;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilDet;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTipDoc;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTipDoc;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatTipDoc;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtRucCli;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
    * Esta clase implementa la interface "Comparator" la cual es utilizada para indicar
    * el modo que se utilizará para comparar objetos.
    */
    private class ZafCom implements java.util.Comparator{
        private int intCol;
        private boolean blnAsc;
        public ZafCom(){
            intCol=-1;
            blnAsc=true;
        }

        /**
         * Este constructor establece la columna que se debe ordenar ascendentemente/descendentemente.
         * @param columna La columna a ordenar.
         * @param ascendente Puede tomar los siguientes valores:
         * <BR>true: Para que la columna se ordene ascendentemente.
         * <BR>false: Para que la columna se ordene descendentemente.
         */
        public ZafCom(int columna, boolean ascendente){
            intCol=columna;
            blnAsc=ascendente;
        }

        /**
         * Esta función establece la columna que se debe ordenar ascendentemente/descendentemente.
         * @param columna La columna a ordenar.
         * @param ascendente Puede tomar los siguientes valores:
         * <BR>true: Para que la columna se ordene ascendentemente.
         * <BR>false: Para que la columna se ordene descendentemente.
         */
        public void setColumnaOrdenar(int columna, boolean ascendente){
            intCol=columna;
            blnAsc=ascendente;
        }

        /**
         * Esta función compara los objetos que recibe y devuelve el resultado de la comparación.
         * @param o1 El objeto a comparar.
         * @param o2 El otro objeto a comparar.
         * @return Puede tomar los siguientes valores:
         * <UL>
         * <LI>-1: Si o1<o2.
         * <LI>0: Si o1=o2.
         * <LI>1: Si o1>o2.
         * </UL>
         * Nota.- Se intercambia el -1 con el 1 para cambiar de ascendente a descendente.
         */
        public int compare(Object o1, Object o2){
            int intRes=0;
            double dblNum1, dblNum2;
            Vector vecFil1, vecFil2;
            vecFil1=(Vector)o1;
            vecFil2=(Vector)o2;
            Object objCel1, objCel2;
            objCel1=vecFil1.get(intCol);
            objCel2=vecFil2.get(intCol);
            //Comparar el contenido de las celdas.
            if (objCel1==null && objCel2==null){
                intRes=0;
            }
            else if (objCel1==null){
                if (blnAsc)
                    intRes=-1;
                else
                    intRes=1;
            }
            else if (objCel2==null){
                if (blnAsc)
                    intRes=1;
                else
                    intRes=-1;
            }
            else if (isNumero(objCel1.toString()) && isNumero(objCel2.toString())){
                //Comparar los números como números caso contrario serán considerados como cadena.
                dblNum1=Double.parseDouble(objCel1.toString());
                dblNum2=Double.parseDouble(objCel2.toString());
                if (blnAsc){
                    if (dblNum1<dblNum2)
                        intRes=-1;
                    else if (dblNum1==dblNum2)
                        intRes=0;
                    else
                        intRes=1;
                }
                else{
                    if (dblNum1<dblNum2)
                        intRes=1;
                    else if (dblNum1==dblNum2)
                        intRes=0;
                    else
                        intRes=-1;
                }
            }
            else if (objCel1 instanceof String){
                //Comparar cadenas.
                if (blnAsc)
                    intRes=objCel1.toString().compareToIgnoreCase(objCel2.toString());
                else
                    intRes=objCel2.toString().compareToIgnoreCase(objCel1.toString());
            }
            else if (objCel1 instanceof Comparable){
                if (blnAsc)
                    intRes=((Comparable)objCel1).compareTo(objCel2);
                else
                    intRes=((Comparable)objCel2).compareTo(objCel1);
            }
            return intRes;
        }
    }

    /**
     * Esta función determina si la cadena que se recibe como argumento es un valor numérico o no.
     * @param numero La cadena que contiene el número que se desea evaluar.
     * @return true: Si el valor es un número.
     * <BR>false: En el caso contrario.
     */
    private boolean isNumero(String numero)
    {
        double dblNum;
        boolean blnRes=true;
        try
        {
            dblNum=Double.parseDouble(numero);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtNomCli.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
            txtNomCli.requestFocus();
            return false;
        }
        return true;
    }    

    /**
     * Función que permite obtener los filtros de búsquedas
     * @return 
     */
    private String sqlFil() 
    {
        String sqlFil= "";
        boolean blnTipDoc=false;
        
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
            if(txtCodEmp.getText().length()>0)
                sqlFil+=" AND a1.co_emp IN(" + txtCodEmp.getText() + ")";
            if(txtCodLoc.getText().length()>0)
                sqlFil+=" AND a1.co_loc IN(" + txtCodLoc.getText() + ")";
            sqlFil+=" AND b1.tx_ide='" + txtRucCli.getText() + "'";
        }
        else {
            sqlFil+="  AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            if(objParSis.getCodigoUsuario()!=1){
                if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                    sqlFil+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                }
            }
            sqlFil+="  AND b1.co_cli=" + txtCodCli.getText() + "";
        }
        
        //<editor-fold defaultstate="collapsed" desc="/* Filtro: Tipo de documento. */">
        strTipDoc="";
        for (int j = 0; j < tblDatTipDoc.getRowCount(); j++) 
        {
            if (tblDatTipDoc.getValueAt(j, INT_TBL_TIPDOC_CHKSEL).toString().equals("true")) 
            {
                if(blnTipDoc){ 
                    strTipDoc+=" ,";
                }
                strTipDoc+= tblDatTipDoc.getValueAt(j, INT_TBL_TIPDOC_CODTIP).toString() ;
                blnTipDoc=true;
            }
        }
        //</editor-fold>
        
        return sqlFil;
    }    
     
    private class ZafThreadGUI extends Thread{
        public void run(){
            if (isCamVal()){
                //Limpiar objetos.
                objTblMod.removeAllRows();
                if (!cargarReg()){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0){
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
                objThrGUI=null;
            }
        }
    }

    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(cargarDetReg(sqlFil())){
                    if(objSelFec.isCheckBoxChecked()){
                        switch (objSelFec.getTipoSeleccion()){
                            case 0: //Básqueda por rangos
                                if(cargarSaldoAnterior(sqlFil())){
                                    if(cargarSaldoSiguiente(sqlFil())){
                                        if(calcularColumnaSaldo()){
                                            if(calcularTotales()){
                                                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                                            }
                                        }
                                    }
                                }
                                break;
                            case 1: //Fechas menores o iguales que "Hasta".
                                objTblMod.insertRow(0);
                                objTblMod.setValueAt("SALDO ANTERIOR: ", 0, INT_TBL_DAT_DES_COR_TIP_DOC);
                                objTblMod.setValueAt("0", 0, INT_TBL_DAT_SAL);
                                objTblMod.setValueAt("0", 0, INT_TBL_DAT_NUM_FIL);
                                if(cargarSaldoSiguiente(sqlFil())){
                                    if(calcularColumnaSaldo()){
                                        if(calcularTotales()){
                                            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                                        }
                                    }
                                }
                                break;
                            case 2: //Fechas mayores o iguales que "Desde".
                                if(cargarSaldoAnterior(sqlFil())){
                                    if(calcularColumnaSaldo()){
                                        if(calcularTotales()){
                                            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                                        }
                                    }
                                }
                                break;
                            case 3: //Todo.
                                break;
                        }
                    }
                    else{
                        objTblMod.insertRow(0);
                        objTblMod.setValueAt("SALDO ANTERIOR: ", 0, INT_TBL_DAT_DES_COR_TIP_DOC);
                        objTblMod.setValueAt("0", 0, INT_TBL_DAT_SAL);
                        objTblMod.setValueAt("0", 0, INT_TBL_DAT_NUM_FIL);

                        if(calcularColumnaSaldo()){
                            if(calcularTotales()){
                                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                            }
                        }
                    }
                }
                con.close();
                con=null;
            }

        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetReg(String strFil){
        boolean blnRes=true;
        String strFilFec="";
        strAux="";
        int i;
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
           
            if (con!=null){
                stm=con.createStatement();
                //Filtro: Fecha
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Básqueda por rangos
                        if(objSelFec.isCheckBoxChecked())
                            strFilFec+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        if(objSelFec.isCheckBoxChecked())
                            strFilFec+=" AND a1.fe_doc <= '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        if(objSelFec.isCheckBoxChecked())
                            strFilFec+=" AND a1.fe_doc >= '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 3: //Todo.
                        break;
                }
              
                //MOVIMIENTOS ACTUALES SEGUN FECHA SELECCIONADA
                strSQL="";
                strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.tx_desCor, z.tx_desLar, z.co_doc, z.ne_numDoc1, z.ne_numDoc2, z.fe_doc, z.co_forPag,z.tx_des";
                strSQL+="      , z.co_cli, SUM(z.credito) AS credito, SUM(z.debito) AS debito, z.ne_numTotPos";
                strSQL+=" FROM(";
                strSQL+="     ( SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.tx_desCor, y.tx_desLar, y.co_doc, y.ne_numDoc1, y.ne_numDoc2, y.fe_doc";
                strSQL+=" 	     , y.co_forPag, y.tx_des, y.co_cli, SUM(y.credito) AS credito, SUM(y.debito) AS debito, y.ne_numTotPos ";
                strSQL+=" 	FROM( SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1, x.ne_numDoc2, x.fe_doc, x.tx_desCor, x.tx_desLar";
                strSQL+=" 		   , x.co_forPag, x.tx_des, x.co_cli, x.tx_nomCli, CASE WHEN SUM(nd_tot)<0 THEN SUM(nd_tot) END AS credito";
                strSQL+=" 		   , CASE WHEN SUM(nd_tot)>0 THEN SUM(nd_tot) END AS debito, x.ne_numTotPos ";
                strSQL+="              FROM( SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1, x.ne_numDoc2";
                strSQL+="                         , x.fe_doc, x.nd_tot, x.co_cli, x.tx_ide, x.tx_nomCli	";
                strSQL+=" 	                  , x.tx_desCor, x.tx_desLar, x.co_forPag, x.tx_des, x.co_reg, COUNT(y.tx_numChq) AS ne_numTotPos";
                strSQL+=" 	             FROM( SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc AS ne_numDoc1";
                strSQL+=" 			        , CAST('' AS character varying) AS ne_numDoc2, a1.fe_doc, a1.nd_tot, a1.co_cli, b1.tx_ide, a1.tx_nomCli";
                strSQL+=" 			        , a2.tx_desCor, a2.tx_desLar, '' || a3.co_forPag AS co_forPag, a3.tx_des, CAST('0' AS SMALLINT) AS co_reg";
                strSQL+=" 			   FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 			          INNER JOIN tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL+=" 			   LEFT OUTER JOIN tbm_cabforpag AS a3 ON a1.co_emp=a3.co_emp AND a1.co_forpag=a3.co_forpag";
                strSQL+=" 			   WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=""+strFil;
                strSQL+=""+strFilFec;
                strSQL+=" 			   AND a2.ne_mod  IN(1,3,5) AND a1.st_reg NOT IN('I','E')";
                strSQL+="                          GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.nd_tot";
                strSQL+="                              , a1.co_cli, b1.tx_ide, a1.tx_nomCli, a2.tx_desCor, a2.tx_desLar, a3.co_forPag, a3.tx_des";
                strSQL+=" 	             ) AS x";
                strSQL+="                    LEFT OUTER JOIN(      ";
                strSQL+="             	          SELECT a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a3.co_pos, a2.tx_numChq";
                strSQL+="                         FROM tbm_cabRecDoc AS a1 ";
                strSQL+="                         INNER JOIN ( ";
                strSQL+="                              tbm_detRecDoc AS a2 INNER JOIN tbr_detRecDocpagMovInv AS a6";
                strSQL+="                              ON a2.co_emp=a6.co_emp AND a2.co_loc=a6.co_loc AND a2.co_tipDoc=a6.co_tipDoc AND a2.co_doc=a6.co_doc AND a2.co_reg=a6.co_reg";
                strSQL+="                              INNER JOIN tbm_pagMovInv AS a7 ON a6.co_empRel=a7.co_emp AND a6.co_locRel=a7.co_loc AND a6.co_tipDocRel=a7.co_tipDoc AND a6.co_docRel=a7.co_doc";
                strSQL+="                              INNER JOIN tbm_cabMovInv AS a9 ON a9.co_emp=a7.co_emp AND a9.co_loc=a7.co_loc AND a9.co_tipDoc=a7.co_tipDoc AND a9.co_doc=a7.co_doc";
                strSQL+="                         ) ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                         INNER JOIN tbm_posChqRecDoc AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+="                         AND a9.st_reg not in ('E','I') AND a7.st_reg  in ('A','C')  AND a3.st_solpos IN('A')";
                strSQL+="                         GROUP BY a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a3.co_pos, a2.tx_numChq";
                strSQL+="                         ORDER BY a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a3.co_pos";
                strSQL+="                    ) AS y ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_doc=y.co_doc";
                strSQL+="                    GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1, x.ne_numDoc2, x.fe_doc, x.nd_tot, x.co_cli";
                strSQL+="                           , x.tx_ide, x.tx_nomCli, x.tx_desCor, x.tx_desLar, x.co_forPag, x.tx_des, x.co_reg";
                ///////////////////////////////////////////////////////////////////////////
                strSQL+=" 		  UNION";
                ///////////////////////////////////////////////////////////////////////////
                //inicio nuevo
                strSQL+="                     SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1, x.ne_numDoc2, x.fe_doc, x.nd_abo";
                strSQL+=" 	                   , x.co_cli, x.tx_ide, x.tx_nom AS tx_nomCli, y.tx_desCor, y.tx_desLar, '' AS co_forPag,'' AS tx_des";
                strSQL+=" 	                   , x.co_reg, CAST('0' AS SMALLINT) AS ne_numTotPos";
                strSQL+="                     FROM(";
                strSQL+=" 	                    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_abo, b1.co_cli, b1.tx_ide, b1.tx_nom";
                strSQL+=" 	                         , a1.ne_numDoc1, '' || a1.ne_numDoc2 AS ne_numDoc2, a1.fe_doc, a3.co_reg";
                strSQL+=" 	                    FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a3";
                strSQL+=" 	                    ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" 	                    INNER JOIN tbm_pagMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_locPag=a4.co_loc AND a3.co_tipDocPag=a4.co_tipDoc";
                strSQL+="                           AND a3.co_docPag=a4.co_doc AND a3.co_regPag=a4.co_reg";
                strSQL+="	                    INNER JOIN (tbm_cabMovInv AS a5 INNER JOIN tbm_cabtipDoc AS a2 ON a5.co_emp=a2.co_emp AND a5.co_loc=a2.co_loc AND a5.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	                    ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc";
                strSQL+=" 	                    INNER JOIN tbm_cli AS b1 ON a5.co_emp=b1.co_emp AND a5.co_cli=b1.co_cli";
                strSQL+=" 			    WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=""+strFil;
                strSQL+=""+strFilFec;
                strSQL+="	                    AND a2.ne_mod IN(1, 3) AND a1.st_reg NOT IN('I','E') AND a1.st_reg NOT IN('I','E') AND a4.st_reg IN('A','C') AND a3.st_reg NOT IN('I','E')";
                strSQL+=" 	                    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_abo, b1.co_cli, b1.tx_ide, a1.ne_numDoc1, a1.ne_numDoc2,b1.tx_nom, a1.fe_doc, a3.co_reg";
                strSQL+="                     )AS x";
                strSQL+="                     INNER JOIN(";
                strSQL+="                           SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.tx_desCor, a.tx_desLar";
                strSQL+=" 	                    FROM tbm_cabTipDoc AS a";
                strSQL+="                     ) AS y ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc";
                //fin nuevo
                strSQL+="              ) AS x";
                strSQL+=" 		GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1, x.ne_numDoc2, x.fe_doc";
                strSQL+=" 		, x.co_forPag, x.tx_des	, x.co_cli, x.tx_nomCli, x.tx_desCor, x.tx_desLar, x.ne_numTotPos";
                strSQL+=" 	) AS y";
                strSQL+=" 	GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.tx_desCor, y.tx_desLar, y.co_doc, y.ne_numDoc1, y.ne_numDoc2, y.fe_doc, y.co_forPag,y.tx_des, y.co_cli, y.ne_numTotPos";
                strSQL+=" 	";
                strSQL+="     )";
                strSQL+=" ) AS z";
                strSQL+=" WHERE z.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=" AND z.co_tipDoc in ("+strTipDoc+")";
                strSQL+=" GROUP BY z.co_emp, z.co_loc, z.co_tipDoc, z.tx_desCor, z.tx_desLar, z.co_doc, z.ne_numDoc1, z.ne_numDoc2, z.co_cli, z.fe_doc, z.co_forPag,z.tx_des, z.ne_numTotPos";
                strSQL+=" HAVING SUM(CASE WHEN z.credito IS NULL THEN 0 ELSE z.credito END) + SUM(CASE WHEN z.debito IS NULL THEN 0 ELSE z.debito END)  <> 0";
                strSQL+=" ORDER BY z.fe_doc, z.ne_numDoc1";
                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;
                int j=0;
                
                while (rst.next()){
                    j++;
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC_UNO,     rst.getString("ne_numDoc1"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC_DOS,     rst.getString("ne_numDoc2"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_FOR_PAG,     rst.getString("co_forPag"));
                    vecReg.add(INT_TBL_DAT_FOR_PAG,         rst.getString("tx_des"));
                    vecReg.add(INT_TBL_DAT_CRE,             rst.getString("credito"));
                    vecReg.add(INT_TBL_DAT_DEB,             rst.getString("debito"));
                    vecReg.add(INT_TBL_DAT_SAL,             "");
                    vecReg.add(INT_TBL_DAT_NUM_FIL,         ""+j);
                    vecReg.add(INT_TBL_DAT_NUM_TOT_POS,     rst.getString("ne_numTotPos"));
                    vecDat.add(vecReg);
                    i++;
                    pgrSis.setValue(i);
                }
                //System.out.println("I CONTIENE: " + i);

                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTblTot.calcularTotales();
                //lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
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
 
    private boolean cargarSaldoAnterior(String strFil){
        boolean blnRes=true;
        String strFilFec="";
        BigDecimal bdeSalAnt=new BigDecimal(BigInteger.ZERO);
        try{
            if(con!=null){
                stm=con.createStatement();
                //Filtro: Fecha            
                strFilFec+=" AND a1.fe_doc < '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
               
                //Armar la sentencia SQL.
                //--SALDO ANTERIOR
                strSQL="";
                strSQL+=" SELECT SUM(z.credito) AS credito, SUM(z.debito) AS debito";
                strSQL+="      , SUM(CASE WHEN z.credito IS NULL THEN 0 ELSE z.credito END) + SUM(CASE WHEN z.debito IS NULL THEN 0 ELSE z.debito END) AS saldo ";
                strSQL+=" FROM(( SELECT y.co_emp, y.co_cli, SUM(y.credito) AS credito, SUM(y.debito) AS debito";
                strSQL+="	 FROM(	SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_cli";
                strSQL+="	             , CASE WHEN SUM(nd_tot)<0 THEN SUM(nd_tot) END AS credito";
                strSQL+=" 		     , CASE WHEN SUM(nd_tot)>0 THEN SUM(nd_tot) END AS debito ";
                strSQL+=" 		 FROM(  SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_tot, a1.co_cli, CAST('0' AS SMALLINT)";
                strSQL+=" 			FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+="                              INNER JOIN tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL+=" 			LEFT OUTER JOIN tbm_cabforpag AS a3 ON a1.co_emp=a3.co_emp AND a1.co_forpag=a3.co_forpag";
                strSQL+=" 	                WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=" 			AND a2.co_tipDoc in ("+strTipDoc+")";
                strSQL+=""+strFil;
                strSQL+=""+strFilFec;
                strSQL+=" 			AND a2.ne_mod IN(1,3,5) AND a1.st_reg NOT IN('I','E')";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_tot, a1.co_cli";
                ///////////////////////////////////////////////////////////////////////////
                strSQL+=" 	     UNION";
                ///////////////////////////////////////////////////////////////////////////
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_abo, b1.co_cli, a3.co_reg";
                strSQL+=" 	                FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a3";
                strSQL+=" 	                ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" 	                INNER JOIN tbm_pagMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_locPag=a4.co_loc AND a3.co_tipDocPag=a4.co_tipDoc";
                strSQL+="                       AND a3.co_docPag=a4.co_doc AND a3.co_regPag=a4.co_reg";
                strSQL+=" 	                INNER JOIN (tbm_cabMovInv AS a5 INNER JOIN tbm_cabtipDoc AS a2 ON a5.co_emp=a2.co_emp AND a5.co_loc=a2.co_loc AND a5.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	                ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc";
                strSQL+=" 	                INNER JOIN tbm_cli AS b1 ON a5.co_emp=b1.co_emp AND a5.co_cli=b1.co_cli";
                strSQL+=" 			WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=" 			AND a2.co_tipDoc in ("+strTipDoc+")";
                strSQL+=""+strFil;
                strSQL+=""+strFilFec;
                strSQL+=" 	                AND a2.ne_mod IN(1,3) AND a1.st_reg NOT IN('I','E') AND a5.st_reg NOT IN('I','E') AND a4.st_reg IN('A','C') AND a3.st_reg NOT IN('I','E')";
                strSQL+=" 	                GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_abo, b1.co_cli, a3.co_reg";
                strSQL+=" 		 ) AS x";
                strSQL+=" 		 GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_cli";
                strSQL+="	 ) AS y";
                strSQL+=" 	 GROUP BY y.co_emp, y.co_cli";
                strSQL+="  )";
                strSQL+=" ) AS z";
                strSQL+=" HAVING SUM(CASE WHEN z.credito IS NULL THEN 0 ELSE z.credito END) + SUM(CASE WHEN z.debito IS NULL THEN 0 ELSE z.debito END)  <> 0";
                //System.out.println("saldo anterior: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    bdeSalAnt=new BigDecimal(rst.getObject("saldo")==null?"0":rst.getString("saldo"));
                }
                objTblMod.insertRow(0);
                objTblMod.setValueAt("SALDO ANTERIOR: ", 0, INT_TBL_DAT_DES_COR_TIP_DOC);
                objTblMod.setValueAt(bdeSalAnt, 0, INT_TBL_DAT_SAL);
                objTblMod.setValueAt("0", 0, INT_TBL_DAT_NUM_FIL);

                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarSaldoSiguiente(String strFil){
        boolean blnRes=true;
        String strFilFec="";
        //intExiSalSig=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                //Filtro: Fecha
                strFilFec+=" AND a1.fe_doc > '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                //Armar la sentencia SQL.
                //--SALDO ANTERIOR
                strSQL="";
                strSQL+=" SELECT SUM(z.credito) AS credito, SUM(z.debito) AS debito";
                strSQL+="     , SUM(CASE WHEN z.credito IS NULL THEN 0 ELSE z.credito END) + SUM(CASE WHEN z.debito IS NULL THEN 0 ELSE z.debito END) AS saldo ";
                strSQL+=" FROM(( SELECT y.co_emp, y.co_cli, SUM(y.credito) AS credito, SUM(y.debito) AS debito";
                strSQL+="	 FROM( SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_cli";
                strSQL+="		    , CASE WHEN SUM(nd_tot)<0 THEN SUM(nd_tot) END AS credito";
                strSQL+=" 		    , CASE WHEN SUM(nd_tot)>0 THEN SUM(nd_tot) END AS debito ";
                strSQL+=" 	       FROM( SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_tot, a1.co_cli, CAST('0' AS SMALLINT)";
                strSQL+=" 		     FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+="                           INNER JOIN tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL+=" 		     LEFT OUTER JOIN tbm_cabforpag AS a3 ON a1.co_emp=a3.co_emp AND a1.co_forpag=a3.co_forpag";
                strSQL+=" 		     WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=" 	             AND a2.co_tipDoc in ("+strTipDoc+")";
                strSQL+=""+strFil;
                strSQL+=""+strFilFec;
                strSQL+=" 		     AND a2.ne_mod IN(1,3,5) AND a1.st_reg NOT IN('I','E')";
                strSQL+=" 		     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_tot, a1.co_cli";
                ///////////////////////////////////////////////////////////////////////////
                strSQL+=" 	     UNION"; 
                ///////////////////////////////////////////////////////////////////////////
                strSQL+=" 	             SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_abo, b1.co_cli	, a3.co_reg";
                strSQL+=" 	             FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a3";
                strSQL+=" 	             ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" 	             INNER JOIN tbm_pagMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_locPag=a4.co_loc AND a3.co_tipDocPag=a4.co_tipDoc";
                strSQL+="                    AND a3.co_docPag=a4.co_doc AND a3.co_regPag=a4.co_reg";
                strSQL+=" 	             INNER JOIN (tbm_cabMovInv AS a5 INNER JOIN tbm_cabtipDoc AS a2 ON a5.co_emp=a2.co_emp AND a5.co_loc=a2.co_loc AND a5.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	             ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc";
                strSQL+=" 	             INNER JOIN tbm_cli AS b1 ON a5.co_emp=b1.co_emp AND a5.co_cli=b1.co_cli";
                strSQL+=" 	             WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                strSQL+=" 	             AND a2.co_tipDoc in ("+strTipDoc+")";
                strSQL+=""+strFil;
                strSQL+=""+strFilFec;
                strSQL+=" 	             AND a2.ne_mod IN(1,3) AND a1.st_reg NOT IN('I','E') AND a5.st_reg NOT IN('I','E') AND a4.st_reg IN('A','C') AND a3.st_reg NOT IN('I','E')";
                strSQL+=" 	             GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_abo, b1.co_cli	, a3.co_reg";
                strSQL+=" 	       ) AS x";
                strSQL+=" 	       GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_cli";
                strSQL+="	 ) AS y";
                strSQL+=" 	 GROUP BY y.co_emp, y.co_cli";
                strSQL+="  )";
                strSQL+=" ) AS z";
                strSQL+=" HAVING SUM(CASE WHEN z.credito IS NULL THEN 0 ELSE z.credito END) + SUM(CASE WHEN z.debito IS NULL THEN 0 ELSE z.debito END)  <> 0";
                //System.out.println("saldo siguiente: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    //intExiSalSig=1;//si existe un registro de saldos siguientes
                    objTblMod.insertRow(objTblMod.getRowCountTrue());
                    objTblMod.setValueAt("SALDO SIGUIENTE: ", (objTblMod.getRowCountTrue()-1), INT_TBL_DAT_DES_COR_TIP_DOC);
                    //objTblMod.setValueAt(rst.getString("saldo"), (objTblMod.getRowCountTrue()-1), INT_TBL_DAT_SAL);
                    objTblMod.setValueAt(rst.getString("credito"), (objTblMod.getRowCountTrue()-1), INT_TBL_DAT_CRE);
                    objTblMod.setValueAt(rst.getString("debito"), (objTblMod.getRowCountTrue()-1), INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("9999999999", (objTblMod.getRowCountTrue()-1), INT_TBL_DAT_NUM_FIL);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularColumnaSaldo(){
        boolean blnRes=true;
        BigDecimal bdeSalAnt=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValCre=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValDeb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValAntCreDeb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValCreDeb=new BigDecimal(BigInteger.ZERO);
        try{
            bdeSalAnt=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_SAL)==null?"0":objTblMod.getValueAt(0, INT_TBL_DAT_SAL).toString());
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeValCre=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CRE)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CRE).toString());
                bdeValDeb=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_DEB)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_DEB).toString());
                bdeValCreDeb=bdeValCre.add(bdeValDeb);
                bdeValAntCreDeb=bdeSalAnt.add(bdeValCreDeb);
                objTblMod.setValueAt(bdeValAntCreDeb, i, INT_TBL_DAT_SAL);
                bdeSalAnt=bdeValAntCreDeb;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean calcularTotales(){
        boolean blnRes=true;
        BigDecimal bdeValCre=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValDeb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumCre=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumDeb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSal=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeValCre=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CRE)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CRE).toString());
                bdeValDeb=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_DEB)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_DEB).toString());
                bdeSumCre=bdeSumCre.add(bdeValCre);
                bdeSumDeb=bdeSumDeb.add(bdeValDeb);
                bdeSal=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL).toString());
            }
            objTblTot.setValueAt(bdeSumCre, 0, INT_TBL_DAT_CRE);
            objTblTot.setValueAt(bdeSumDeb, 0, INT_TBL_DAT_DEB);
            objTblTot.setValueAt(bdeSal   , 0, INT_TBL_DAT_SAL);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean ordenarRegistrosAscendente(){
        boolean blnRes=true;
        try{
            objCom=new ZafCom();
            objHeaRenLbl=new ZafHeaRenLbl(tblDat.getTableHeader().getDefaultRenderer());
            tblDat.getTableHeader().setDefaultRenderer(objHeaRenLbl);

            objHeaRenLbl.setTipoOrdenamiento(INT_TBL_DAT_NUM_FIL, 1);
            objCom.setColumnaOrdenar(INT_TBL_DAT_NUM_FIL,true);
            java.util.Collections.sort(objTblMod.getData(),objCom);
            objTblMod.fireTableDataChanged();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    
    
}