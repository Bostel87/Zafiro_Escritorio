/**
 * ZafCxP09.java 
 * Created on Nov 12, 2010, 9:30:32 AM
 *
 */
package CxP.ZafCxP09;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.Vector; 
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * @author jayapata *
 */
public class ZafCxP09 extends javax.swing.JInternalFrame
{
    //Constantes: JTable
    private static final int INT_TBL_LINEA = 0;                            //NUMERO DE LINEAS
    private static final int INT_TBL_CODPRV = 1;                           //CODIGO DEL PROVEEDOR
    private static final int INT_TBL_BUTPRV = 2;                           //BOTON PARA BUSCAR PROVEEDOR
    private static final int INT_TBL_NOMPRV = 3;                           //NOMBRE DEL PROVEEDOR
    private static final int INT_TBL_NUMFAC = 4;                           //NUMERO DE FACTURA
    private static final int INT_TBL_FECFAC = 5;                           //FECHA DE FACTURA
    private static final int INT_TBL_VALIVA = 6;                           //VALOR IVA DE FACTURAS
    private static final int INT_TBL_VALFAC = 7;                           //VALOR DE FACTURAS
    private static final int INT_TBL_NUMSER = 8;                           //NUMERO  DE SERIE
    private static final int INT_TBL_NUMAUT = 9;                           //NUMERO DE AUTORIZACION
    private static final int INT_TBL_FECCAD = 10;                          //FECHA DE CADUCIDAD
    private static final int INT_TBL_BUTOC = 11;                           //BOTON PARA BUSCAR ORDENES DE COMPRA
    private static final int INT_TBL_TXTOBS = 12;                          //OBSERVACION
    private static final int INT_TBL_CODREG = 13;                          //CODIGO DE REGISTRO
    private static final int INT_TBL_DATFAC = 14;                          //DATOS DE LAS FACTURAS SELECCIONADAS
    private static final int INT_TBL_VALFACORI = 15;                       //VALOR DE FACTURAS ORIGEN
    private static final int INT_TBL_VAPLOC = 16;                          //VALOR APLICADO DE LAS ORDENES DE COMPRA
    private static final int INT_TBL_ESTCAMFAC = 17;                       //estado si ha cambiado o agregado facturas.
    private static final int INT_TBL_ESTAPL = 18;                          //Estado si esta aplica a factura del proveedor.
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceCon=0;    

    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODREG = 0;
    
    //Variables    
    private Connection con;
    private Statement stm;
    private ResultSet rst;      private Connection CONN_GLO;
    private Statement STM_GLO;
    private ResultSet RST_GLO;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafDatePicker dtpFecDoc;
    private ZafTblMod objTblMod;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtChq, objTblCelEdiTxtValChq, objTblCelEdiTxtValIva, objTblCelEdiTxtObs;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private java.util.Date datFecAux;
    private ZafTblPopMnu objTblPopMnu;
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoPrv;
    private MiToolBar objTooBar;
    private ZafRptSis objRptSis;
    private Vector vecCab, vecReg, vecDat;
    
    boolean blnHayCam = false;
    boolean blnConVenEme = false;
    
    StringBuffer stbFacSel;
    StringBuffer strSqlDetUpdCli;
    
    java.awt.Color colFonCol =new java.awt.Color(228, 228, 203);
    
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    javax.swing.JInternalFrame ifrFrm; //Hace referencia a this
    
    private String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    private String strCodRegGlo = "";
    private String strSqlTipDocAux = "";    
    private String strFormatoFecha = "d/m/y";
    private String strCodTipDocOpimpo="106";
    private String strCodTipDocOpcolo="246";

    private String strSQL, strAux;
    private String strVer = " v2.3 ";

    /** Crea una nueva instancia de la clase ZafCxP09. */
    public ZafCxP09(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();   
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())  //Empresa
            {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{ //Grupo
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }    

    public ZafCxP09 (ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, String codigoRegistro, int codigoMenu)
    {
        try 
        {
            //Inicializar objetos
            objParSis=(ZafParSis)obj.clone();
            
            //objParSis.setCodigoMenu(codigoMenu); 
            objParSis.setCodigoMenu(2136);  //2136: Recepción de las facturas de los proveedores...
            objParSis.setCodigoEmpresa(codigoEmpresa);
            objParSis.setCodigoLocal(codigoLocal);
            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);   
            strCodRegGlo = codigoRegistro;
            
            blnConVenEme = true;
            
            initComponents();
            if (!configurarFrm())
                exitForm();
            
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);            
        }
        catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 

    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti = new ZafUtil(); 
            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            objTooBar=new MiToolBar(this);
            if (objParSis.getCodigoMenu() == 1824  || objParSis.getCodigoMenu() == 1942) {
                objTooBar.setVisibleInsertar(false);
                objTooBar.setVisibleEliminar(false);
                objTooBar.setVisibleAnular(false);
            }       
            panBar.add(objTooBar);
            
            //Configurar ZafDatePicker:
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setBounds(585, 4, 100, 20);                
            panCab.add(dtpFecDoc);
            
            //dtpFecVenFacPrv=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            //dtpFecVenFacPrv.setText("");
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConPrv();
            
            //Configurar JTable
            configurarTblDat();
        } 
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }      
    
    private boolean configurarTblDat() 
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecDat=new Vector();
            vecCab.clear();            
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODPRV, "Cód.Prv.");
            vecCab.add(INT_TBL_BUTPRV, "..");
            vecCab.add(INT_TBL_NOMPRV, "Proveedor");
            vecCab.add(INT_TBL_NUMFAC, "Núm.Fac.");
            vecCab.add(INT_TBL_FECFAC, "Fec.Fac.");
            vecCab.add(INT_TBL_VALIVA, "Val.Iva.");
            vecCab.add(INT_TBL_VALFAC, "Val.Fac.");
            vecCab.add(INT_TBL_NUMSER, "Num.Ser.");
            vecCab.add(INT_TBL_NUMAUT, "Num.Aut");
            vecCab.add(INT_TBL_FECCAD, "Fec.Cad.");
            vecCab.add(INT_TBL_BUTOC, "...");
            vecCab.add(INT_TBL_TXTOBS, "Observación");
            vecCab.add(INT_TBL_CODREG, "Cód.Reg.");
            vecCab.add(INT_TBL_DATFAC, " ");
            vecCab.add(INT_TBL_VALFACORI, "");
            vecCab.add(INT_TBL_VAPLOC, "");
            vecCab.add(INT_TBL_ESTCAMFAC, "Est.Agr.Fac");
            vecCab.add(INT_TBL_ESTAPL, "");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_VALIVA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALFAC, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODPRV);
            arlAux.add("" + INT_TBL_NUMFAC);
            arlAux.add("" + INT_TBL_FECFAC);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;

            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODREG);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux = null;

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();  
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODPRV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTPRV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NOMPRV).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUMFAC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FECFAC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_VALIVA).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_VALFAC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_NUMSER).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECCAD).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_BUTOC).setPreferredWidth(20);            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);            

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            if (objParSis.getCodigoMenu() == 2136) { //Recepción de facturas de proveedores...
                vecAux.add("" + INT_TBL_CODPRV);
                vecAux.add("" + INT_TBL_BUTPRV);
                vecAux.add("" + INT_TBL_NUMFAC);
                vecAux.add("" + INT_TBL_FECFAC);
                vecAux.add("" + INT_TBL_VALIVA);
                vecAux.add("" + INT_TBL_VALFAC);
                vecAux.add("" + INT_TBL_NUMSER);
                vecAux.add("" + INT_TBL_NUMAUT);
                vecAux.add("" + INT_TBL_FECCAD);
            }
            vecAux.add("" + INT_TBL_BUTOC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODREG, tblDat);             
            objTblMod.addSystemHiddenColumn(INT_TBL_DATFAC, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_VALFACORI, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_VAPLOC, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_ESTAPL, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_ESTCAMFAC, tblDat);    
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_VALFAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALIVA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar: Formato fecha
            tcmAux.getColumn(INT_TBL_FECFAC).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_CODPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUMFAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FECFAC).setCellRenderer(objTblCelRenLbl);
            //tcmAux.getColumn(INT_TBL_VALFAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUMSER).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUMAUT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FECCAD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelEdiTxtObs = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_TXTOBS).setCellEditor(objTblCelEdiTxtObs);
            objTblCelEdiTxtObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil = -1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    String strEstApl = (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL) == null ? "N" : (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).equals("") ? "N" : objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).toString()));
                    //String strAsigBan=  (objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).toString()));
                    if ((strEstApl.equals("S"))) {
                        objTblCelEdiTxtObs.setCancelarEdicion(true);
                    }
                    //if((strAsigBan.equals("S")))
                    //   objTblCelEdiTxtObs.setCancelarEdicion(true);
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            objTblCelEdiTxtChq = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtChq.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_NUMFAC).setCellEditor(objTblCelEdiTxtChq);
            tcmAux.getColumn(INT_TBL_NUMSER).setCellEditor(objTblCelEdiTxtChq);
            tcmAux.getColumn(INT_TBL_NUMAUT).setCellEditor(objTblCelEdiTxtChq);
            tcmAux.getColumn(INT_TBL_FECCAD).setCellEditor(objTblCelEdiTxtChq);
            objTblCelEdiTxtChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil = -1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();
                    if (intFil >= 0) {
                        String strCodPrv = (objTblMod.getValueAt(intFil, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(intFil, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(intFil, INT_TBL_CODPRV).toString()));
                        String strEstApl = (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL) == null ? "N" : (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).equals("") ? "N" : objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).toString()));
                        //String strAsigBan=  (objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).toString()));
                        if ((strEstApl.equals("S"))) {
                            objTblCelEdiTxtChq.setCancelarEdicion(true);
                        }
                        //if((strAsigBan.equals("S")))
                        //  objTblCelEdiTxtChq.setCancelarEdicion(true);
                        if ((strCodPrv.trim().equals(""))) {
                            objTblCelEdiTxtChq.setCancelarEdicion(true);
                        }                       
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                //<editor-fold defaultstate="collapsed" desc="/* comentado */">                    
//                if(tblDat.getSelectedColumn()==INT_TBL_NUMCHQ){
//                    String strNumChq = (objTblMod.getValueAt(intFil, INT_TBL_NUMCHQ)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_NUMCHQ).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_NUMCHQ).toString()));
//                    String strCodBan = (objTblMod.getValueAt(intFil, INT_TBL_CODBAN)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_CODBAN).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_CODBAN).toString()));
//                    String strNumCta = (objTblMod.getValueAt(intFil, INT_TBL_NUMCTA)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_NUMCTA).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_NUMCTA).toString()));
//                    String strCodCli = (objTblMod.getValueAt(intFil, INT_TBL_CODCLI)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_CODCLI).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_CODCLI).toString()));
//                    if( !_getExitNumChqRep( strCodCli, strNumChq, strCodBan, strNumCta ) ){
//                        mostrarMsgInf("NUMERO DE CHEQUE YA EXISTE..1");
//                        objTblMod.setValueAt("", intFil, INT_TBL_NUMCHQ);
//                    }else{
//                        if(!_getVerficicaNumRepChqTbl( intFil,  strCodBan,  strNumCta,  strNumChq)){
//                            mostrarMsgInf("NUMERO DE CHEQUE YA EXISTE..2");
//                            objTblMod.setValueAt("", intFil, INT_TBL_NUMCHQ);
//                        }
//                    }
//                }
//                if(objTblMod.getSelectedColumn()==INT_TBL_NUMCTA){
//                    String strNumCta = (objTblMod.getValueAt(intFil, INT_TBL_NUMCTA)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_NUMCTA).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_NUMCTA).toString()));
//                    if(strNumCta.equals(""))
//                        objTblMod.setValueAt("",intFil, INT_TBL_NUMCHQ);
//                }
                //</editor-fold>
                }
            });

            //
            objTblCelEdiTxtValIva = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtValIva.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_VALIVA).setCellEditor(objTblCelEdiTxtValIva);
            objTblCelEdiTxtValIva.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil = -1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    if (intFil >= 0) {
                        String strEstApl = (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL) == null ? "N" : (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).equals("") ? "N" : objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).toString()));
                        String strCodPrv = (objTblMod.getValueAt(intFil, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(intFil, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(intFil, INT_TBL_CODPRV).toString()));
                        if ((strCodPrv.trim().equals(""))) {
                            objTblCelEdiTxtValIva.setCancelarEdicion(true);
                        }
                        if ((strEstApl.equals("S"))) {
                            objTblCelEdiTxtValIva.setCancelarEdicion(true);
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            //

            objTblCelEdiTxtValChq = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtValChq.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_VALFAC).setCellEditor(objTblCelEdiTxtValChq);
            objTblCelEdiTxtValChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil = -1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    if (intFil >= 0) {
                        String strEstApl = (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL) == null ? "N" : (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).equals("") ? "N" : objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).toString()));
                        //String strAsigBan=  (objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).toString()));
                        String strCodPrv = (objTblMod.getValueAt(intFil, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(intFil, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(intFil, INT_TBL_CODPRV).toString()));
                        if ((strCodPrv.trim().equals(""))) {
                            objTblCelEdiTxtValChq.setCancelarEdicion(true);
                        }
                        if ((strEstApl.equals("S"))) {
                            objTblCelEdiTxtValChq.setCancelarEdicion(true);
                        }
//                   if((strAsigBan.equals("S")))
//                    objTblCelEdiTxtValChq.setCancelarEdicion(true);
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    BigDecimal bgdValOC=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_VAPLOC)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_VAPLOC).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_VAPLOC).toString()));
                    BigDecimal bgdValFac=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_VALFAC).toString()));
                    BigDecimal bgdValFacOri=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_VALFACORI)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_VALFACORI).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_VALFACORI).toString()));
         
                    if (bgdValOC.compareTo(BigDecimal.ZERO) > 0) {
                        if (bgdValFac.compareTo(bgdValOC) < 0) {
                            mostrarMsgInf("NO PUEDE SER MENOR AL VALOR SELECCIONADO DE LA ORDENES DE COMPRA. ");
                            objTblMod.setValueAt("" + bgdValFacOri, intFil, INT_TBL_VALFAC);
                        } else {
                            objTblMod.setValueAt("" + bgdValFac, intFil, INT_TBL_VALFACORI);
                            calculaTotMonChq();
                        }
                    } else {
                        objTblMod.setValueAt("" + bgdValFac, intFil, INT_TBL_VALFACORI);
                        calculaTotMonChq();
                    }
                }
            });
            
            //Ventana de consulta: Proveedores
            if(objParSis.getCodigoMenu()==2136) //Recepción de facturas
            { 
                int intColVen[]=new int[5];
                intColVen[0] = 1;
                intColVen[1] = 3;
                intColVen[2] = 4;
                intColVen[3] = 5;
                intColVen[4] = 6;

                int intColTbl[]=new int[5];
                intColTbl[0] = INT_TBL_CODPRV;
                intColTbl[1] = INT_TBL_NOMPRV;
                intColTbl[2] = INT_TBL_NUMSER;
                intColTbl[3] = INT_TBL_NUMAUT;
                intColTbl[4] = INT_TBL_FECCAD;

                objTblCelEdiTxtVcoPrv = new ZafTblCelEdiTxtVco(tblDat, vcoPrv, intColVen, intColTbl);
                tcmAux.getColumn(INT_TBL_CODPRV).setCellEditor(objTblCelEdiTxtVcoPrv);
                tcmAux.getColumn(INT_TBL_NOMPRV).setCellEditor(objTblCelEdiTxtVcoPrv);
                objTblCelEdiTxtVcoPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    int intFil=-1;
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        intFil = tblDat.getSelectedRow();
                        String strEstApl = (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL) == null ? "N" : (objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).equals("") ? "N" : objTblMod.getValueAt(intFil, INT_TBL_ESTAPL).toString()));
                        String strExis = (objTblMod.getValueAt(intFil, INT_TBL_CODREG) == null ? "" : (objTblMod.getValueAt(intFil, INT_TBL_CODREG).equals("") ? "" : objTblMod.getValueAt(intFil, INT_TBL_CODREG).toString()));
                        // String strAsigBan=  (objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_ASIGBAN).toString()));

                        if ((strEstApl.equals("S"))) {
                            objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
                        }
                        if (!(strExis.equals(""))) {
                            objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
                        }

                        //if((strAsigBan.equals("S")))
                        //  objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
                    }
                    public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (tblDat.getSelectedColumn() == INT_TBL_NOMPRV) {
                            vcoPrv.setCampoBusqueda(2);
                        } else {
                            vcoPrv.setCampoBusqueda(0);
                        }
                        vcoPrv.setCriterio1(11);
                    }

                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objTblCelEdiTxtVcoPrv.isConsultaAceptada()) {
                            eventoVenConCli(intFil);
                        }
                    }
                });
            }

            objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTPRV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTOC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;

            new ButPrv(tblDat, INT_TBL_BUTPRV);
            new ButFacCom(tblDat, INT_TBL_BUTOC);

            objTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            objTblPopMnu.setInsertarFilasVisible(false);
            objTblPopMnu.setInsertarFilaVisible(false);

            tcmAux = null;
            setEditable(false);

            blnRes = true;
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODPRV:
                    strMsg = "Código del proveedor";
                    break;
                case INT_TBL_NOMPRV:
                    strMsg = "Nombre del proveedor";
                    break;
                case INT_TBL_NUMFAC:
                    strMsg = "Número de la factura del proveedor";
                    break;
                case INT_TBL_FECFAC:
                    strMsg = "Fecha de la factura del proveedor";
                    break;
                case INT_TBL_VALFAC:
                    strMsg = "valor de la factura del proveedor";
                    break;
                case INT_TBL_NUMSER:
                    strMsg = "Número de serie";
                    break;
                case INT_TBL_NUMAUT:
                    strMsg = "Número autorización del SRI";
                    break;
                case INT_TBL_FECCAD:
                    strMsg = "Fecha de caducidad";
                    break;
                case INT_TBL_TXTOBS:
                    strMsg = "Observación";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");

            //Armar la sentencia SQL.  
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL ="";
                strSQL+=" SELECT DISTINCT a.co_tipdoc, a.tx_descor,a.tx_deslar FROM tbr_tipdocprg as b ";
                strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc AS a ON (b.co_emp=a.co_emp AND b.co_loc=a.co_loc AND b.co_tipdoc=a.co_tipdoc)";
                strSQL+=" WHERE b.co_emp = " + objParSis.getCodigoEmpresa();
                strSQL+=" AND b.co_loc = " + objParSis.getCodigoLocal();
                strSQL+=" AND b.co_mnu = " + objParSis.getCodigoMenu();
            }
            else {
                strSQL ="";
                strSQL+=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar ";
                strSQL+=" FROM tbr_tipDocUsr AS a1 INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }

            strSqlTipDocAux = " SELECT co_tipdoc FROM (" + strSQL + " ) AS x ";

            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConPrv() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_numserfacprv");
            arlCam.add("a.tx_numautsrifacprv");
            arlCam.add("a.tx_feccadfacprv");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("RUC/CI");
            arlAli.add("Nom.Prv.");
            arlAli.add("Núm.Ser");
            arlAli.add("Núm.Aut");
            arlAli.add("Fec.Cad.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("130");
            arlAncCol.add("350");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");

            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_cli, a.tx_ide, a.tx_nom , a.tx_numserfacprv, a.tx_numautsrifacprv, a.tx_feccadfacprv";
            strSQL+=" FROM tbm_cli as a ";
            strSQL+=" WHERE a.st_reg IN('A') AND a.st_prv='S' ";
            strSQL+=" AND a.co_emp =" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a.tx_nom ";   
            
            if(objParSis.getCodigoUsuario() != 1){
                if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) {
                    strSQL ="";
                    strSQL+=" SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_numserfacprv, a.tx_numautsrifacprv, a.tx_feccadfacprv";
                    strSQL+=" FROM tbr_cliloc AS b ";
                    strSQL+=" INNER JOIN tbm_cli AS a ON (a.co_emp=b.co_emp AND a.co_cli=b.co_cli) ";
                    strSQL+=" WHERE a.st_reg IN ('A') AND  a.st_prv='S' ";
                    strSQL+=" AND b.co_emp =" + objParSis.getCodigoEmpresa() + " AND b.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" ORDER BY a.tx_nom ";
                }                
            }
            //System.out.println("ConfigurandoVentanaPrv: "+strSQL);

            vcoPrv = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private void eventoVenConCli(int intNumFil) 
    {
        try {
            int i = intNumFil - 1;
            for (int intNumFilDat = i; intNumFilDat >= 0; intNumFilDat--) 
            {
                if (intNumFilDat >= 0) 
                {
                    String strCodCli1 = (objTblMod.getValueAt(intNumFil, INT_TBL_CODPRV) == null ? "" : objTblMod.getValueAt(intNumFil, INT_TBL_CODPRV).toString());
                    String strCodCli2 = (objTblMod.getValueAt(intNumFilDat, INT_TBL_CODPRV) == null ? "" : objTblMod.getValueAt(intNumFilDat, INT_TBL_CODPRV).toString());
                    if (strCodCli1.equals(strCodCli2)) 
                    {
                        String strNumSerDoc1 = (objTblMod.getValueAt(intNumFil, INT_TBL_NUMSER) == null ? "" : objTblMod.getValueAt(intNumFil, INT_TBL_NUMSER).toString()).trim();
                        String strNumSerDoc2 = (objTblMod.getValueAt(intNumFilDat, INT_TBL_NUMSER) == null ? "" : objTblMod.getValueAt(intNumFilDat, INT_TBL_NUMSER).toString()).trim();

                        String strNumAutSri1 = (objTblMod.getValueAt(intNumFil, INT_TBL_NUMAUT) == null ? "" : objTblMod.getValueAt(intNumFil, INT_TBL_NUMAUT).toString()).trim();
                        String strNumAutSri2 = (objTblMod.getValueAt(intNumFilDat, INT_TBL_NUMAUT) == null ? "" : objTblMod.getValueAt(intNumFilDat, INT_TBL_NUMAUT).toString()).trim();

                        String strFecCabSri1 = (objTblMod.getValueAt(intNumFil, INT_TBL_FECCAD) == null ? "" : objTblMod.getValueAt(intNumFil, INT_TBL_FECCAD).toString()).trim();
                        String strFecCabSri2 = (objTblMod.getValueAt(intNumFilDat, INT_TBL_FECCAD) == null ? "" : objTblMod.getValueAt(intNumFilDat, INT_TBL_FECCAD).toString()).trim();

                        if (strNumSerDoc1.equals("")) {
                            objTblMod.setValueAt("" + strNumSerDoc2, intNumFil, INT_TBL_NUMSER);
                        } else if (!(strNumSerDoc1.equals(strNumSerDoc2))) {
                            objTblMod.setValueAt("" + strNumSerDoc2, intNumFil, INT_TBL_NUMSER);
                        }

                        if (strNumAutSri1.equals("")) {
                            objTblMod.setValueAt("" + strNumAutSri2, intNumFil, INT_TBL_NUMAUT);
                        } else if (!(strNumAutSri1.equals(strNumAutSri2))) {
                            objTblMod.setValueAt("" + strNumAutSri2, intNumFil, INT_TBL_NUMAUT);
                        }

                        if (strFecCabSri1.equals("")) {
                            objTblMod.setValueAt("" + strFecCabSri2, intNumFil, INT_TBL_FECCAD);
                        } else if (!(strFecCabSri1.equals(strFecCabSri2))) {
                            objTblMod.setValueAt("" + strFecCabSri2, intNumFil, INT_TBL_FECCAD);
                        }

                        break;
                    }
                } 
                else {
                    break;
                }
            }
        }
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);        }
    }    

    private void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }    
    
    private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) {
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }    
    
    /**
     * Función que calcula el monto total de los cheques ingresados
     * @return 
     */
    private void calculaTotMonChq(){
        BigDecimal bgdValFac=new BigDecimal("0");
        BigDecimal bdgMonChq=new BigDecimal("0");
        try{
            bdgMonChq=BigDecimal.ZERO;
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                if (!((objTblMod.getValueAt(i, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODPRV).toString())).equals(""))) {
                    bgdValFac=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALFAC).toString()));
                    bdgMonChq=bdgMonChq.add(bgdValFac);
                }
            }
            bdgMonChq=objUti.redondearBigDecimal(bdgMonChq, objParSis.getDecimalesMostrar());
            txtValDoc.setText("" + bdgMonChq);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }    

    private class ButPrv extends Librerias.ZafTableColBut.ZafTableColBut {
        public ButPrv(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx);
        }
        public void butCLick() {
            int intNumFil = tblDat.getSelectedRow();
            if (intNumFil >= 0) {
                String strEstApl = (objTblMod.getValueAt(intNumFil, INT_TBL_ESTAPL) == null ? "N" : (objTblMod.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("") ? "N" : objTblMod.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
                String strExis = (objTblMod.getValueAt(intNumFil, INT_TBL_CODREG) == null ? "" : (objTblMod.getValueAt(intNumFil, INT_TBL_CODREG).equals("") ? "" : objTblMod.getValueAt(intNumFil, INT_TBL_CODREG).toString()));

                if (strEstApl.equals("N")) {
                    if (strExis.equals("")) {
                        listaProveedores(intNumFil);
                    }
                }
            }
        }
    }

    private void listaProveedores(int intNumFil) {
        vcoPrv.setTitle("Listado de Proveedores");
        vcoPrv.show();
        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
            tblDat.setValueAt(vcoPrv.getValueAt(1), intNumFil, INT_TBL_CODPRV);
            tblDat.setValueAt(vcoPrv.getValueAt(3), intNumFil, INT_TBL_NOMPRV);
            eventoVenConCli(intNumFil);
        }
    }

    private class ButFacCom extends Librerias.ZafTableColBut.ZafTableColBut {
        public ButFacCom(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx);
        }
        public void butCLick() {
            int intNumFil = tblDat.getSelectedRow();
            String strCodPrv = (objTblMod.getValueAt(intNumFil, INT_TBL_CODPRV) == null ? "" : (objTblMod.getValueAt(intNumFil, INT_TBL_CODPRV).equals("") ? "" : objTblMod.getValueAt(intNumFil, INT_TBL_CODPRV).toString()));
            String strEstApl = "N"; // (objTblMod.getValueAt(intNumFil, INT_TBL_ESTAPL)==null?"N":(objTblMod.getValueAt(intNumFil, INT_TBL_ESTAPL).equals("")?"N":objTblMod.getValueAt(intNumFil, INT_TBL_ESTAPL).toString()));
            String strAsiBan = "N"; //  (objTblMod.getValueAt(intNumFil, INT_TBL_ASIGBAN)==null?"N":(objTblMod.getValueAt(intNumFil, INT_TBL_ASIGBAN).equals("")?"N":objTblMod.getValueAt(intNumFil, INT_TBL_ASIGBAN).toString()));

            if (strCodPrv.equals("")) {
                mostrarMsgInf("TIENE QUE ELEGIR UN PROVEEDOR.");
            }
            else {
                if (strEstApl.equals("N")) {
                    if (strAsiBan.equals("N")) {
                        listaFacComRec(intNumFil, strCodPrv);
                    }
                    else {
                        mostrarMsgInf("YA TIENE ASIGNADO BANCO, NO ES POSIBLE AGREGAR Ó QUITAR FACTURAS.");
                    }
                } else {
                    mostrarMsgInf("YA TIENE VALOR APLICADO, NO ES POSIBLE AGREGAR Ó QUITAR FACTURAS.");
                }
            }
        }
    }

    private void listaFacComRec(int intNumFil, String strCodPrv)
    {
        String strSql = "";
        String strSqlAuxEstRel, strSqlAuxValAsi;
        String strCodReg = (objTblMod.getValueAt(intNumFil, INT_TBL_CODREG)==null?"":(objTblMod.getValueAt(intNumFil, INT_TBL_CODREG).equals("")?"":objTblMod.getValueAt(intNumFil, INT_TBL_CODREG).toString()));
        if (!strCodReg.equals("")) {
            strSqlAuxEstRel="( SELECT x.co_emprel FROM  tbr_detrecdoccabmovinv AS x "
                           +"  WHERE x.st_reg not in ('E') AND x.co_emp=" + objParSis.getCodigoEmpresa() + ""
                           +"  AND x.co_loc=" + objParSis.getCodigoLocal() + " AND x.co_tipdoc=" + txtCodTipDoc.getText() + " "
                           +"  AND x.co_doc=" + txtCodDoc.getText() + "  AND x.co_reg=" + strCodReg + " "
                           +"  AND x.co_emprel=a.co_emp  AND x.co_locrel=a.co_loc AND x.co_tipdocrel=a.co_tipdoc AND x.co_docrel=a.co_doc "
                           +" ) AS estrel, ";

            strSqlAuxValAsi="( SELECT x.nd_valasi FROM  tbr_detrecdoccabmovinv AS x "
                           +"  WHERE x.st_reg NOT IN ('E') AND x.co_emp=" + objParSis.getCodigoEmpresa() +""
                           +"  AND x.co_loc=" + objParSis.getCodigoLocal() + " AND x.co_tipdoc=" + txtCodTipDoc.getText() + " "
                           +"  AND x.co_doc=" + txtCodDoc.getText() + "  AND x.co_reg=" + strCodReg + " "
                           +"  AND x.co_emprel=a.co_emp  AND x.co_locrel=a.co_loc AND x.co_tipdocrel=a.co_tipdoc AND x.co_docrel=a.co_doc "
                           +" ) AS ndvalasi, ";
        }
        else{
            strSqlAuxEstRel=" null AS estrel, ";
            strSqlAuxValAsi=" 0 AS ndvalasi, ";
        }

        int intEst = 0;
        stbFacSel = new StringBuffer();
        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
            if (i != intNumFil) {
                String strDatFac = (objTblMod.getValueAt(i, INT_TBL_DATFAC) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_DATFAC).equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_DATFAC).toString()));
                if (!strDatFac.equals("")) {
                    if (intEst == 1) {
                        stbFacSel.append(" UNION ALL ");
                    }
                    stbFacSel.append(strDatFac);
                    intEst = 1;
                }
            }
        }

        BigDecimal bgdValFacPrv=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(intNumFil, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(intNumFil, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(intNumFil, INT_TBL_VALFAC).toString())), objParSis.getDecimalesMostrar());
        String strDatFac=(objTblMod.getValueAt(intNumFil, INT_TBL_DATFAC)==null? "" :(objTblMod.getValueAt(intNumFil, INT_TBL_DATFAC).equals("")?"":objTblMod.getValueAt(intNumFil, INT_TBL_DATFAC).toString()));

        //Armar sentencia SQL
        strSql =" SELECT  * ";
        strSql+=" FROM";
        strSql+=" ( SELECT *, case when ndvalasi > 0 then 'S' else 'N' end as estvalasi";
        strSql+="           , ( (nd_tot-case when valasi is null then 0 else valasi end )  - case when valapltot is null then 0 else valapltot end  ) as ndvalpen ";//tony
        //strSql+="           , (nd_tot-(case when abono is null then 0 else abono end )*-1)   as ndvalpen  ";
        strSql+="   FROM ( SELECT x.*";
        if (!strDatFac.equals("")) {
            strSql+="      , CASE WHEN x1.coemp IS NULL THEN 'N' ELSE 'S' END AS est,  x1.valapl ";
        }
        else{
            strSql+="      , text('N') AS est, 0 AS valapl";        
        }
        
        strSql+="          FROM ( SELECT x.*  ";
        if (intEst == 0) {
            strSql+="            ,0 as valapltot ";
        }
        else{
            strSql+="            ,x1.valapltot ";
        }        
        strSql+="                 FROM ( SELECT  * ";
        strSql+="                        FROM ( SELECT  x.*, x1.valasi ";
        strSql+="                               FROM ( SELECT  x.*        ";
        strSql+="                                      FROM ";
        strSql+="                                      ( SELECT " + strSqlAuxValAsi + " " + strSqlAuxEstRel + "  a.co_emp, a.co_loc, a.co_tipdoc";
        strSql+="                                               ,  a2.tx_descor, a2.tx_deslar,  a.co_doc, a.ne_numdoc, a.fe_doc, a.nd_tot  ";
        strSql+="                                               , ( select sum(x.nd_abo) as abono from tbm_pagmovinv as x ";
        strSql+="                                                   where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc ";
        strSql+="                                                   and  x.co_doc= a.co_Doc and  x.st_reg in ('A','C')  ) as abono  ";
        //strSql += "                                               and  x.co_doc= a.co_Doc and  x.st_reg in ('A','C') and  x.nd_porret != 0  ) as abono  "; //tony
        strSql+="                                        FROM tbm_cabmovinv AS a ";
        strSql+="                                        INNER JOIN tbm_cabtipdoc AS a2 ON (a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) ";
        strSql+="                                        WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
        if (objParSis.getCodigoEmpresa() == 1 && objParSis.getCodigoLocal() == 12) {  //CodEmp 1 CodLoc 12 = TUVAL - Inmaconsa (005-003) => Local de Inmaconsa nuevo (al 22/Feb/2017)
            strSql+="                                    AND a.co_loc in (10,12) "; //Si es el local de Inmaconsa nuevo, entonces se va a agregar para que tambien traiga documentos del local de Inmaconsa cerrado (CodLoc 10).
        } else {
            strSql+="                                    AND a.co_loc=" + objParSis.getCodigoLocal();
        }        
        strSql+="                                        AND a.co_tipdoc IN ( select co_tipdoc  from tbr_tipdocdetprg where co_emp=" + objParSis.getCodigoEmpresa() ;
        strSql+="                                                             and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= " + objParSis.getCodigoMenu() ;
        strSql+="                                                             and co_tipDoc NOT IN ("+strCodTipDocOpimpo+","+strCodTipDocOpcolo+" ) ) "; //No muestre OPIMPO/OPCOLO para bloquear recepciones de facturas de proveedores.
        strSql+="                                        AND a.co_cli=" + strCodPrv + "  AND a.st_reg not in ('I','E') ";
        strSql+="                                        ORDER BY a.ne_numdoc ";
        strSql+="                                       ) AS x  "; // WHERE x.abono = 0 " +
        strSql+="                                ) AS x        ";
        strSql+="                                LEFT JOIN (  ";
        strSql+="                                        SELECT co_emprel , co_locrel, co_tipdocrel, co_docrel, sum(nd_valasi) as valasi FROM tbr_detrecdoccabmovinv ";
        strSql+="                                        WHERE st_reg='A' AND co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal();
        strSql+="                                        AND co_tipdoc=" + txtCodTipDoc.getText() + " ";
        
        if (!txtCodDoc.getText().equals("")) { strSql+=" AND co_doc != " + txtCodDoc.getText() + "  ";   }
        
        strSql+="                                        GROUP BY co_emprel, co_locrel, co_tipdocrel, co_docrel ";
        strSql+="                                ) AS  x1 ON (x1.co_emprel=x.co_emp AND x1.co_locrel=x.co_loc AND x1.co_tipdocrel=x.co_tipdoc AND x1.co_docrel=x.co_doc  )  ";
        //<editor-fold defaultstate="collapsed" desc="/* comentado */">
        // " select "+strSqlAuxEstRel+" a1.co_banchq, a1.tx_numchq, a1.nd_monchq, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.tx_descor,  " +
        // " a.ne_numdoc, a1.ne_diacre, a.fe_doc, a1.fe_ven, (a1.mo_pag *-1 ) as mo_pag, a1.nd_abo, (a1.mo_pag+a1.nd_abo)*-1 as valpen " +
        // " from tbm_cabmovinv as a  " +
        // " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  " +
        // " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc  ) " +
        // " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a1.co_tipdoc " +
        //    " IN ( select co_tipdoc  from tbr_tipdocdetprg where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_mnu= "+objParSis.getCodigoMenu()+" ) " +
        // " and a.co_cli="+strCodPrv+" and  a1.st_reg in ('A','C') " +
        // " AND a.st_reg not in ('I','E') and nd_porret = 0    and (a1.mo_pag+a1.nd_abo) < 0   order by a.ne_numdoc " +
        //</editor-fold>
        strSql+="                        ) AS x  ";
        //strSql+="                        WHERE CASE WHEN estrel IS NULL THEN tx_numchq is null  ELSE  tx_numchq like '%%' END ";
        strSql+="                        ) AS x  ";

        if (intEst == 1) {
            strSql+="                    LEFT JOIN (";
            strSql+="                        SELECT *  ";
            strSql+="                        FROM ( select coemp, coloc, cotipdoc, codoc, sum(valapl) as valapltot";
            strSql+="                               from (  " + stbFacSel.toString() + "  ) as x ";
            strSql+="                               group by coemp, coloc, cotipdoc, codoc ";
            strSql+="                        ) as x ";
            strSql+="                    ) AS x1 ";
            strSql+="                    ON (x1.coemp=x.co_emp and x1.coloc=x.co_loc and x1.cotipdoc=x.co_tipdoc and x1.codoc=x.co_doc  ) ";
            //strSql+="                    WHERE x1.coemp IS NULL ";
        }

        stbFacSel = null;

        strSql+="          ) AS x  ";
        if (!strDatFac.equals("")) {
            strSql+="          LEFT JOIN ( " + strDatFac + " ) AS x1 ";
            strSql+="          ON (x1.coemp=x.co_emp and x1.coloc=x.co_loc and x1.cotipdoc=x.co_tipdoc and x1.codoc=x.co_doc  ) ";
        }

        strSql+="   ) AS x ";
        strSql+=" ) AS x ";
        strSql+=" WHERE CASE WHEN ( valapl is null  or  valapl = 0 ) THEN  ndvalpen > 0 ELSE nd_tot >= 0  END ";
        strSql+=" AND fe_doc >= '2011-01-01'";
        strSql+=" AND nd_tot+abono>0.00 "; //tony
        strSql+=" ORDER BY ne_numdoc  ";
        System.out.println("ConsultaFacCom: "+strSql);
        
        ZafCxP09_01 obj = new ZafCxP09_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strSql, bgdValFacPrv, BigDecimal.ZERO, BigDecimal.ZERO);
        obj.show();

        if (obj.acepta()) {
            objTblMod.setValueAt(obj.GetCamSel(2), intNumFil, INT_TBL_TXTOBS);
            objTblMod.setValueAt(obj.GetCamSel(1), intNumFil, INT_TBL_DATFAC);
            objTblMod.setValueAt(obj.GetCamSel(3), intNumFil, INT_TBL_VAPLOC);
            objTblMod.setValueAt("S", intNumFil, INT_TBL_ESTCAMFAC);
        }
        obj.dispose();
        obj = null;
    }
    
    /**
     * Función que carga el tipo de documento predeterminado
     * @param intVal 
     */
    private void cargarTipDocPre(int intVal) 
    {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null)
            {
                stmLoc = conLoc.createStatement();
                if (objParSis.getCodigoUsuario() == 1) {
                    strSQL = " SELECT doc.co_tipdoc, doc.tx_deslar, doc.tx_descor "
                           + "      , CASE WHEN (doc.ne_ultdoc+1) IS NULL THEN 1 ELSE doc.ne_ultdoc+1 END AS numDoc "
                           + "      , doc.tx_natdoc, doc.st_meringegrfisbod "
                           + " FROM tbr_tipdocprg as menu "
                           + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                           + " WHERE menu.co_emp = " + objParSis.getCodigoEmpresa() +""
                           + " AND menu.co_loc = " + objParSis.getCodigoLocal() +""
                           + " AND menu.co_mnu = " + objParSis.getCodigoMenu()+""
                           + " AND menu.st_reg = 'S' ";
                } 
                else {
                    strSQL = " SELECT doc.co_tipdoc, doc.tx_deslar, doc.tx_descor"
                           + "      , CASE WHEN (doc.ne_ultdoc+1) IS NULL THEN 1 ELSE doc.ne_ultdoc+1 END AS numDoc "
                           + "      , doc.tx_natdoc, doc.st_meringegrfisbod "
                           + " FROM tbr_tipDocUsr as menu "
                           + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "
                           + " WHERE   menu.co_emp = " + objParSis.getCodigoEmpresa() + ""
                           + " AND menu.co_loc = " + objParSis.getCodigoLocal() + ""
                           + " AND menu.co_mnu = " + objParSis.getCodigoMenu() + ""
                           + " AND menu.co_usr=" + objParSis.getCodigoUsuario() + ""
                           + " AND menu.st_reg = 'S' ";

                }
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                    strCodTipDoc = txtCodTipDoc.getText();
                    if (intVal == 1) {
                        txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                    }
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFec = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenObs = new javax.swing.JPanel();
        panLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 64));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:"); // NOI18N
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 130, 20);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 4, 70, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 4, 240, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(440, 4, 20, 20);

        lblCodDoc.setText("Código del documento:"); // NOI18N
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 130, 20);

        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(130, 24, 70, 20);

        lblFec.setText("Fecha del documento:"); // NOI18N
        lblFec.setToolTipText("Fecha del documento");
        panCab.add(lblFec);
        lblFec.setBounds(460, 4, 130, 20);

        lblNumDoc.setText("Número de documento:"); // NOI18N
        lblNumDoc.setToolTipText("Número de documento");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(460, 24, 130, 20);

        txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(585, 24, 100, 20);

        lblValDoc.setText("Valor del documento:"); // NOI18N
        lblValDoc.setToolTipText("Valor del documento");
        panCab.add(lblValDoc);
        lblValDoc.setBounds(460, 44, 130, 20);

        txtValDoc.setBackground(objParSis.getColorCamposSistema());
        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.setText("0.00");
        panCab.add(txtValDoc);
        txtValDoc.setBounds(585, 44, 100, 20);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

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

        panDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        panLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        panLblObs.add(lblObs2);

        panGenObs.add(panLblObs, java.awt.BorderLayout.WEST);

        panTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panTxtObs.add(spnObs2);

        panGenObs.add(panTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDoc.getText(), 2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                BuscarTipDoc("a.tx_descor", txtDesCorTipDoc.getText(), 1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);

        } else {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                strCodTipDoc = vcoTipDoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        vcoTipDoc.setCampoBusqueda(1);
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing


    /**
     * Función para cerrar Formulario
     */
    private void exitForm() {

        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFec;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panLblObs;
    private javax.swing.JPanel panTxtObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables
   
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    

    /**
     * Método que elimina ceros a la izquierda dentro de una cadena de Caracteres.
     * @param str
     * @return 
     */
    private String eliminaCeros(String str) 
    {
        System.out.println("Antes>>>  " + str);
        if (str.length() > 0) {
            if (str.charAt(0) == '0') {
                return eliminaCeros(str.substring(1));
            }
        }
        System.out.println("Despues>>>  " + str);
        return str;
    }

    /**
     * Método que agrega ceros a la izquierda dentro de una cadena de Caracteres.
     * @param str
     * @return 
     */
    private String agregaCeros(String str) 
    {
        if (str.length() < 9) {
            str.format("%09s", str);
        }
        return str;
    }   
    
    /**
     * Método que elimina guiones a la izquierda dentro de una cadena de Caracteres.
     * @param str
     * @return 
     */    
    private String eliminaGuion(String str) 
    {
        if (str.length() > 0) {
            if (str.charAt(0) == '-') {
                return eliminaGuion(str.substring(1));
            }
        }
        return str;
    }    
    
    private void limpiarFrm() {
        strCodTipDoc = "";
        strDesCorTipDoc = "";
        strDesLarTipDoc = "";
        dtpFecDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtValDoc.setText("0.00");
        dtpFecDoc.setText("");
        txtNumDoc.setText("");
        txtCodDoc.setText("");
        txaObs1.setText("");
        txaObs2.setText("");
        objTblMod.removeAllRows();
    }

    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos. <BR>false: En el caso
     * contrario.
     */
    private boolean isCamVal() 
    {
        int intExiDatTbl = 0;
        String strMens = "FACTURA DEL PROVEEDOR", strFecCad;
        Date datFecFac, datFecCad;

        if (txtDesCorTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if (!dtpFecDoc.isFecha()) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("El campo << Fecha Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
            dtpFecDoc.requestFocus();
            return false;
        }
        if (txtNumDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
            txtNumDoc.requestFocus();
            return false;
        }

        //Validar que el "Número de Documento" no se repita.
        if(!validarIsExiNumDoc())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El número de documento <FONT COLOR=\"blue\">" + txtNumDoc.getText() + "</FONT> ya existe.<BR>Escriba otro número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            return false;                
        }        
        
        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) 
        {
            String strCodPrv = objTblMod.getValueAt(i, INT_TBL_CODPRV) == null ? "" : objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODPRV).toString();
            String strNumFac = objTblMod.getValueAt(i, INT_TBL_NUMFAC) == null ? "" : objTblMod.getValueAt(i, INT_TBL_NUMFAC).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMFAC).toString();
            String strNumSer = objTblMod.getValueAt(i, INT_TBL_NUMSER) == null ? "" : objTblMod.getValueAt(i, INT_TBL_NUMSER).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMSER).toString();
            String strNumAut = objTblMod.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString();
            
            if (!strCodPrv.equals("")) 
            {
                intExiDatTbl = 1;
                
                //Validar el campo "Núm.Fac."
                if (strNumFac.equals("")) {
                    mostrarMsgInf("DIGITE EL NUMERO  " + strMens + ". ");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_NUMFAC);
                    return false;
                }                    
                
                if (strNumSer.length() > 0 && strNumFac.length() > 0 && strNumFac.length() != 9) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe tener 9 digitos.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_NUMFAC);
                    return false;
                }

                if (strNumSer.length() > 0 && strNumFac.length() > 0 && objUti.isNumero(strNumFac) == false) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe ser numérico.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_NUMFAC);
                    return false;
                }

                BigDecimal bgdValFacPrv=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALFAC).toString())), objParSis.getDecimalesMostrar());
                if(bgdValFacPrv.compareTo(BigDecimal.ZERO)<=0)
                {
                    mostrarMsgInf("DIGITE EL VALOR  " + strMens + ". ");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_VALFAC);
                    return false;
                }
                
                if (((objTblMod.getValueAt(i, INT_TBL_FECFAC) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_FECFAC).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_FECFAC).toString())).equals(""))) {
                    mostrarMsgInf("DIGITE LA FECHA " + strMens + ". ");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_FECFAC);
                    return false;
                }
                
                //Validar el campo "Núm.Ser."
                if (strNumSer.equals("")) {
                    mostrarMsgInf("DIGITE EL NÚMERO DE SERIE ");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_NUMSER);
                    return false;
                }

                if (strNumSer.length() > 0) 
                {
                    if (strNumSer.length() != 7) {
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Num.Ser.</FONT> debe tener 7 caracteres con formato 999-999 (números separados con guion).<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                        tblDat.repaint();
                        tblDat.requestFocus();
                        tblDat.editCellAt(i, INT_TBL_NUMSER);
                        return false;
                    }                    
                    
                    String strAux1 = strNumSer.substring(0, 3); // Si la serie es 001-002, va a extraer "001"
                    String strAux2 = strNumSer.substring(4, 7); // Si la serie es 001-002, va a extraer "002"
                    boolean blnFoundGuion = true;                //Si encuentra guión en la serie

                    if (strNumSer.lastIndexOf("-") == -1) {
                        blnFoundGuion = false;
                    }

                    if (objUti.isNumero(strAux1) == false || objUti.isNumero(strAux2) == false || blnFoundGuion == false) {
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Num.Ser.</FONT> debe ser numérico con formato 999-999 (números separados con guion).<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                        tblDat.repaint();
                        tblDat.requestFocus();
                        tblDat.editCellAt(i, INT_TBL_NUMSER);
                        return false;
                    }
                }

                //Validar el campo "Núm.Aut.".                
                if (strNumAut.length() == 0) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Aut.</FONT> es obligatorio.<BR>Escriba un número de autorización que tenga 10, 37 o 49 dígitos y vuelva a intentarlo.</HTML>");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_NUMAUT);
                    return false;
                } 
                else if ((strNumAut.length() != 10) && (strNumAut.length() != 37) && (strNumAut.length() != 49)) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Aut.</FONT> tiene " + strNumAut.length() + " caracteres.<BR>Escriba un número de autorización que tenga 10, 37 o 49 dígitos y vuelva a intentarlo.</HTML>");
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(i, INT_TBL_NUMAUT);
                    return false;
                }
                
                //Validar el campo "Fec.Cad.".
                if (strNumAut.length() == 10)
                {   strFecCad = objTblMod.getValueAt(i, INT_TBL_FECCAD) == null ? "" : objTblMod.getValueAt(i, INT_TBL_FECCAD).toString();
                    
                    if (strFecCad.length() == 0)
                    {   mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fec.Cad.</FONT> es obligatorio.<BR>Escriba la fecha de caducidad y vuelva a intentarlo.</HTML>");
                        tblDat.repaint();
                        tblDat.requestFocus();
                        tblDat.editCellAt(i, INT_TBL_FECCAD);
                        return false;
                    }
                    
                    if (isFechaValida(strFecCad) == false)
                    {   strAux =  "<HTML>El campo <FONT COLOR=\"blue\">Fec.Cad.</FONT> no tiene un formato válido. O la fecha es inválida.<BR>";
                        strAux += "Verifique que la fecha esté en formato dd/mm/aaaa y vuelva a intentarlo.</HTML>";
                        mostrarMsgInf(strAux);
                        tblDat.repaint();
                        tblDat.requestFocus();
                        tblDat.editCellAt(i, INT_TBL_FECCAD);
                        return false;
                    }
                    
                    datFecFac = objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
                    datFecCad = objUti.parseDate(strFecCad, "dd/MM/yyyy");
                    
                    if (datFecCad.compareTo(datFecFac) < 0)
                    {   //No se debe permitir el ingreso de la fecha de caducidad.
                        strAux =  "<HTML>El campo <FONT COLOR=\"blue\">Fec.Cad.</FONT> no debe ser menor al campo <FONT COLOR=\"blue\">Fec.Fac.</FONT>.<BR>";
                        strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                        mostrarMsgInf(strAux);
                        tblDat.repaint();
                        tblDat.requestFocus();
                        tblDat.editCellAt(i, INT_TBL_FECCAD);
                        return false;
                    }
                } //if (strNumAut.length() == 10)
                
                if (validarExiFacPrv_TodasEmpresas(strCodPrv, strNumSer, strNumFac))
                {
                   return false;
                }

                if (objUti.parseString(objTblMod.getValueAt(i, 0)).equals("I")) {
                    strNumFac = strNumFac.trim();//Método que elimina caracteres blancos iniciales y finales de la cadena.
                    strNumSer = strNumFac.trim().replace("-", "");
                    strNumAut = strNumAut.trim();

                    if (objTooBar.getEstado() == 'n') {
                        if (validarExiFacPrv(strNumFac, strNumSer, strNumAut)) {
                            return false;
                        }
                    }
                }
            } //if (!strCodPrv.equals(""))
        }
        if (intExiDatTbl == 0) {
            mostrarMsgInf("NO HAY DATOS EN DETALLE INGRESE DATOS.");
            return false;
        }
        return true;
    }
    
    /**
     * Función que valida si el número de documento existe en el sistema.
     * @return true: Si número de documento no existe en el sistema.
     * false: Caso contrario.
     */
    private boolean validarIsExiNumDoc()            
    {
        boolean blnRes=true;
        try
        {
            if(objTooBar.getEstado()=='n')
            {
                if (!txtNumDoc.getText().equals(""))
                {
                    //Arma sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a1.ne_numdoc1 as ne_numdoc";
                    strSQL+=" FROM tbm_cabRecDoc AS a1 ";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a1.ne_numdoc1='" + txtNumDoc.getText().replaceAll("'", "''") + "'";
                    strSQL+=" AND a1.st_reg<>'E'";
                    if (objTooBar.getEstado()=='m')
                        strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                    if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                    {
                        //Número de documento ya existe.
                        blnRes=false;
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;      
    }    

    /* Función que permite Validar si la factura del proveedor ingresada en el Documento por Pagar existe en el sistema.
     * return true: Si existe Factura del Proveedor en el Sistema
     * return false: En el otro caso.
     */
    private boolean validarExiFacPrv(String numeroFacPrv, String numSerFacPrv, String numAutSri)
    {
	boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strMsg="", strMsgDet="";
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            String strNumFac = eliminaCeros(numeroFacPrv); //Elimina los 0 a la izquierda del número de factura ingresado.
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSQL = "";
                strSQL+=" SELECT a1.co_emp, a6.tx_nom as tx_nomEmp, a1.co_loc, a4.fe_doc, a2.co_tipdoccon, a5.tx_descor, a4.ne_numdoc";
                strSQL+="      , a2.tx_numChq as NumFac, a2.tx_numser, a4.co_cli, a4.tx_nomCli";
                strSQL+=" FROM tbm_cabrecdoc AS a1";
                strSQL+=" INNER JOIN tbm_detrecdoc AS a2  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_emp as a6 ON a1.co_emp=a6.co_emp";
                strSQL+=" INNER JOIN tbr_detRecDocCabMovInv as a3  ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" INNER JOIN tbm_CabMovInv as a4  ON a3.co_emp=a4.co_Emp AND a3.co_loc=a4.co_loc AND a3.co_tipdocrel=a4.co_tipdoc AND a3.co_docrel=a4.co_doc AND a2.co_cli=a4.co_cli";
                strSQL+=" INNER JOIN tbm_CabTipdoc as a5  ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a2.co_tipDocCon=a5.co_tipDoc";
                strSQL+=" WHERE  a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN ('I', 'E')  AND  a3.st_reg NOT IN('E','I')";
                //strSQL+=" AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.co_cli=" + txtCodPrv.getText() + " ;
                strSQL+=" AND  (CASE WHEN a2.tx_numSer IS NOT NULL THEN TRUE ELSE FALSE END) ";
                
                if ( (numAutSri.length()!=37) && (numAutSri.length()!=49) ){ //Preimpresas
                    strSQL+=" AND CAST( (CASE WHEN a2.tx_numChq IS NULL THEN '0' ELSE a2.tx_numChq END ) AS INTEGER) = CAST( " + strNumFac + " AS INTEGER) "; 
                    strSQL+=" AND CAST( (CASE WHEN a2.tx_numser IS NULL THEN '0' ELSE REPLACE (a2.tx_numser, '-','') END ) AS INTEGER) = CAST( " + numSerFacPrv + " AS INTEGER) ";
                    strSQL+=" AND (CASE WHEN a2.tx_numAutSri IS NULL THEN '0' ELSE REPLACE (a2.tx_numAutSri, '-','') END ) = '" + numAutSri + "' ";
                }
                else { //Electrónicas
                    strSQL+=" AND  "; 
                    //strSQL+=" ( ( CAST( (CASE WHEN a2.tx_numChq IS NULL THEN '0' ELSE a2.tx_numChq END ) AS INTEGER) = CAST( " + strNumFac + " AS INTEGER) "; 
                    //strSQL+="     AND CAST( (CASE WHEN a2.tx_numser IS NULL THEN '0' ELSE REPLACE (a2.tx_numser, '-','') END ) AS INTEGER) = CAST( " + numSerFacPrv + " AS INTEGER) ";
                    //strSQL+="   )  OR ";
                    strSQL+="  ( (CASE WHEN a2.tx_numAutSri IS NULL THEN '0' ELSE REPLACE (a2.tx_numAutSri, '-','') END ) = '" + numAutSri + "' ) ";
                    //strSQL+=" ) ";                
                }
                strSQL+=" GROUP BY a1.co_emp, a6.tx_nom, a1.co_loc, a4.fe_doc, a2.co_tipdoccon, a5.tx_descor, a4.ne_numdoc";
                strSQL+="        , a2.tx_numChq, a2.tx_numser, a2.tx_numAutSri, a4.co_cli, a4.tx_nomCli";
                //System.out.println("validarExiFacPrv:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                strMsgDet="";
                while (rstLoc.next()) 
                {
                    blnRes = true;
                    strMsgDet+="<tr><td align=\"center\"> "+rstLoc.getString("tx_nomEmp")+"</td>";  
                    strMsgDet+="<td align=\"center\"> "+rstLoc.getString("tx_descor")+"</td>";  
                    strMsgDet+="<td align=\"center\"> "+rstLoc.getString("ne_numdoc")+"</td>"; 
                    strMsgDet+="<td align=\"center\"> "+rstLoc.getString("fe_doc")+"</tr></td>"; 
                }
                
                if(blnRes){
                    strMsg ="<HTML>Los datos de la factura ya aparecen registrados en el sistema, ";
                    strMsg+="<BR>Verifique los siguientes documentos: <BR> ";
                    strMsg+="<BR><CENTER><TABLE BORDER=1><tr><td> Empresa </td><td> Tip.Doc. </td><td> Núm.Doc. </td><td> Fec.Doc. </td></tr>";
                    strMsg+=strMsgDet;
                    strMsg+="</TABLE></HTML>";
                    mostrarMsgInf(strMsg);
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /* Función que permite validar si la factura del proveedor ingresada en el Documento por Pagar existe a nivel de todas las empresas.
     * return true: Si existe Factura del Proveedor en el Sistema
     * return false: En el otro caso.
     */
    private boolean validarExiFacPrv_TodasEmpresas(String strCodPrv, String strNumSerFacPrv, String strNumFacPrv)
    {
	boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc1, stmLoc2;
        ResultSet rstLoc1, rstLoc2;
        String strMsg="", strMsgDet="";
        
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (conLoc != null)
            {   stmLoc1 = conLoc.createStatement();
                stmLoc2 = conLoc.createStatement();
                //Este query va a traer todas las empresas donde este creado el RUC del proveedor.
                strSQL =  "SELECT co_emp, co_cli, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, st_reg ";
                strSQL += "FROM tbm_cli ";
                strSQL += "WHERE st_reg = 'A' and tx_ide = (SELECT tx_ide FROM tbm_cli WHERE co_emp = " + objParSis.getCodigoEmpresa() + "\n";
                strSQL += "                                 and co_cli = " + strCodPrv + ") order by co_emp";
                rstLoc1 = stmLoc1.executeQuery(strSQL);
                strMsgDet="";
                
                while (rstLoc1.next() && blnRes == false) 
                {   //Este query va a verificar si existe o no el Num.Serie y el Num.Factura del proveedor para cada empresa.
                    strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_reg, co_cli, tx_numser, tx_numchq ";
                    strSQL += "FROM tbm_detrecdoc ";
                    strSQL += "WHERE st_reg = 'A' and co_emp = " + rstLoc1.getInt("co_emp") + " and co_tipDoc = " + txtCodTipDoc.getText();
                    strSQL += " and co_cli = " + rstLoc1.getInt("co_cli") + " and tx_numser = '" + strNumSerFacPrv + "' and tx_numchq = '";
                    strSQL += strNumFacPrv + "'";
                    
                    if (objTooBar.getEstado() == 'm')
                    {  //m = Modificar
                       strSQL += " and co_doc <> " + txtCodDoc.getText();
                    }
                    
                    rstLoc2 = stmLoc2.executeQuery(strSQL);
                    
                    if (rstLoc2.next())
                    {  blnRes = true;
                       strMsgDet += "<tr><td align=\"center\"> " + rstLoc1.getString("tx_nom") + "</td>";  
                       strMsgDet += "<td align=\"center\"> " + strNumSerFacPrv + "</td>";  
                       strMsgDet += "<td align=\"center\"> " + strNumFacPrv + "</tr></td>";
                       
                       strMsg =  "<HTML>Los datos de la factura ya aparecen registrados en el sistema.";
                       strMsg += "<BR>Verifique el siguiente documento: <BR> ";
                       strMsg += "<BR><CENTER><TABLE BORDER=1><tr><td> Empresa </td><td> Num.Ser. </td><td> Num.Fac. </td></tr>";
                       strMsg += strMsgDet;
                       strMsg += "</TABLE></HTML>";
                       mostrarMsgInf(strMsg);
                    }
                    rstLoc2.close();
                    rstLoc2 = null;
                }
                
                rstLoc1.close();
                rstLoc1 = null;
                stmLoc1.close();
                stmLoc1 = null;
                stmLoc2.close();
                stmLoc2 = null;
                conLoc.close();
                conLoc = null;
            } //if (conLoc != null)
        } //try
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion validarExiFacPrv_TodasEmpresas()
    
    /**
     * Función que verifica si el estado de registro está anulado.
     * @return true: Documento no está anulado
     */
    private boolean verificaEstRegAnu() 
    {
        boolean blnRes=true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try 
        {
            conLoc=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT st_reg FROM tbm_cabrecdoc ";
                strSQL+=" WHERE st_reg='I' ";
                strSQL+=" AND co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText() ;
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    blnRes = false;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc=null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
            
    /**
     * Funcion que permite verificar si el documento esta Impreso
     * @return True: Documento no ha sido impreso.
     * <BR>False: Caso contrario
     */
    private boolean verificaEstImpDoc() 
    {
        boolean blnRes = true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                if ((objParSis.getCodigoMenu() != 1824)) {
                    strSQL ="";
                    strSQL+=" SELECT st_imp FROM tbm_cabrecdoc";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND co_doc=" + txtCodDoc.getText();
                    strSQL+=" AND st_imp='S'";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        blnRes = false;                        
                    }
                    rstLoc.close();
                    rstLoc = null;
                }
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException ex) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, ex);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    


    private boolean validarRetEmi()
    {
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT co_reg FROM tbm_detrecdoc";
                strSQL+=" WHERE nd_valapl > 0 ";
                strSQL+=" AND co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    blnRes=false;                    
                }            
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;                
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }        
        return blnRes;
    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a
     * los objTooBars de tipo texto para poder determinar si su contenido a
     * cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes = true;
        //<editor-fold defaultstate="collapsed" desc="/* comentado */">
//        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
//        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
//        switch (mostrarMsgCon(strAux)) {
//            case 0: //YES_OPTION
//                switch (objTooBar.getEstado()) {
//                    case 'n': //Insertar
//                        blnRes=objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
////                        blnRes=objTooBar.modificar();
//                        break;
//                }
//                break;
//            case 1: //NO_OPTION
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//                blnRes=true;
//                break;
//            case 2: //CANCEL_OPTION
//                blnRes=false;
//                break;
//        }
//</editor-fold>
        return blnRes;
    }    
    
    // <editor-fold defaultstate="collapsed" desc="ToolBar">

    public class MiToolBar extends ZafToolBar {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }

        public void clickInicio()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickAnterior()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }
        
        public void clickSiguiente()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }  
        }        
               
        public void clickFin()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }        

        public void clickConsultar() {
            cargarTipDocPre(2);
        }

        public void clickInsertar() {
            try 
            {
                limpiarFrm();
                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);
                txtValDoc.setEditable(false);
                txtValDoc.setBackground(colBack);

                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;                
                
                setEditable(true);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setDataModelChanged(false);

                cargarTipDocPre(1);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        } 
        
        public void clickModificar() {
            try {
                setEditable(true);

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();

                txtValDoc.setEditable(false);
                txtValDoc.setBackground(colBack);

                bloquea(txtCodDoc, colBack, false);
                bloquea(txtDesCorTipDoc, colBack, false);
                bloquea(txtDesLarTipDoc, colBack, false);
                bloquea(txtValDoc, colBack, false);
                bloquea(txtNumDoc, colBack, false);

                if (objParSis.getCodigoMenu() == 1942) {
                    dtpFecDoc.setEnabled(false);
                }

                butTipDoc.setEnabled(false);
                objTblMod.setDataModelChanged(false);
                blnHayCam = false;

                if (!(objParSis.getCodigoMenu() == 1824)) {
                    if (!verificaEstImpDoc()) {
                        mostrarMsgInf("<HTML>No es posible modificar documento IMPRESO.</HTML>");
                        this.setEstado('w');
                    }
                }

            } 
            catch (Exception evt) {
                objUti.mostrarMsgErr_F1(this, evt);
            }
        }        

        public void clickVisPreliminar() {
        }
                
        public void clickImprimir() {
        }
        
        public void clickAnular() {
        }

        public void clickEliminar() {
        }

        public void clickAceptar() {
        }
        
        public void clickCancelar() {
        }               
        
        //******************************************************************************************************
        
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }             

        public boolean vistaPreliminar() 
        {
            return true;
        }

        public boolean imprimir() 
        {
            return true;
        }
        
        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        public boolean eliminar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objTblMod.isDataModelChanged())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        //******************************************************************************************************
        public boolean afterConsultar() {
            return true;
        }    
        
        public boolean afterInsertar() {
            this.setEstado('w');
            return true;
        }

        public boolean afterModificar() {
            this.setEstado('w');
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }        
        
        public boolean afterAceptar() {
            return true;
        }        

        public boolean afterCancelar() {
            return true;
        }


        //******************************************************************************************************
        public boolean beforeConsultar() {
            return true;
        }
        
        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeModificar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }

            if(!verificaEstRegAnu()){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }            
            
            //Verifica estado de impresión del documento
            if (!verificaEstImpDoc()) {
                mostrarMsgInf("<HTML>El documento ya está impreso.<BR>No es posible modificar documento.</HTML>");
                return false;     
            }            
            
            if (!isCamVal())
                return false;
            
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            if(!verificaEstRegAnu()){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            //Verifica estado de impresión del documento
            if (!verificaEstImpDoc()) {
                mostrarMsgInf("<HTML>El documento ya está impreso.<BR>No es posible anular documento.</HTML>");                
                return false;     
            }   
            
            if(!validarRetEmi()){
                mostrarMsgInf("<HTML>El documento tiene ya emitida la retención.<BR>No es posible anular documento.</HTML>");
                return false;
            }
            return true;
        }
        
        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }        

        public boolean beforeCancelar() {
            return true;
        }

        
        
    }

    //</editor-fold>
    

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.st_reg, a.fe_doc ";
                strSQL+=" FROM tbm_cabrecdoc AS a ";
                strSQL+=" LEFT OUTER JOIN tbr_detRecDocCabMovInv AS a1";
                strSQL+=" ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc) ";
                strSQL+=" WHERE a.st_reg NOT IN('E') ";
                strSQL+=" AND a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                /* Valida que no se presenten las recepciones que tienen OPIMPO, OPCOLO */
                strSQL+=" AND (CASE WHEN a1.co_tipDocRel IS NOT NULL ";
                strSQL+="      THEN CASE WHEN a1.co_tipDocRel IN ("+strCodTipDocOpimpo+","+strCodTipDocOpcolo+") THEN FALSE ELSE TRUE END";
                strSQL+="      ELSE TRUE ";
                strSQL+="      END)";
                
                //Filtro
                if (!txtCodTipDoc.getText().equals("")) 
                    strSQL+=" AND a.co_tipdoc=" + txtCodTipDoc.getText();
                else 
                    strSQL+=" AND a.co_tipdoc IN (" + strSqlTipDocAux + ") ";

                if (!txtCodDoc.getText().equals("")) 
                    strSQL+=" AND a.co_doc=" + txtCodDoc.getText();

                if (!txtNumDoc.getText().equals("")) 
                    strSQL+=" AND a.ne_numdoc1=" + txtNumDoc.getText();

                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                    

                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.st_reg, a.fe_doc";
                strSQL+=" ORDER BY a.fe_doc, a.ne_numDoc1, a.co_doc ";                
                System.out.println("consultarReg:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_doc"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    blnRes=true;
                }
            }  
            seleccionReg(strCodRegGlo);
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        boolean blnRes=true;
        int intPosRel;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc1 as ne_numDoc, a.nd_mondoc";
                strSQL+="      , a1.tx_descor, a1.tx_deslar, a.st_reg, a.tx_obs1, a.tx_obs2";
                strSQL+=" FROM tbm_cabrecdoc AS a ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a1 ON ( a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) ";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a.co_Doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);

                    txtValDoc.setText(""+ objUti.redondearBigDecimal((rst.getObject("nd_mondoc")==null?new BigDecimal(BigInteger.ZERO):(rst.getObject("nd_mondoc").equals("")?new BigDecimal(BigInteger.ZERO):rst.getBigDecimal("nd_mondoc").abs())), objParSis.getDecimalesMostrar()));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);

                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
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
    
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        ResultSet rstLoc;
        Statement stmLoc;
        BigDecimal bdgAux;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                
                strSQL ="";
                strSQL+=" SELECT CASE WHEN a.nd_valapl > 0 THEN 'S' ELSE  'N' END AS estApl";
                strSQL+="      , a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_cli, a1.tx_nom,  a.tx_numchq, a.fe_recchq";
                strSQL+="      , a.fe_venchq, a.nd_monchq, a.nd_valiva,  a.tx_obs1, a.nd_valapl";
                strSQL+="      , a.fe_asitipdoccon, a.st_asidocrel, a.st_regrep, a.st_reg";
                strSQL+="      , a.tx_numser, a.tx_numautsri, a.tx_feccad ";
                strSQL+=" FROM tbm_detrecdoc AS a ";
                strSQL+=" INNER JOIN tbm_cabrecdoc AS a2 ON a.co_Emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tiPDoc=a2.co_tipDoc AND a.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_cli AS a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli) ";
                //Se valida que solo presente los registros de detalle inactivo, cuando la recepción ha sido anulada, 
                //Cuando la recepción esta activa, valido que no muestre los detalles inactivos, solo los activos.
                strSQL+=" WHERE ( CASE WHEN a2.st_Reg NOT IN ('E', 'I')";
                strSQL+="         THEN  CASE WHEN a.st_Reg NOT IN ('E', 'I') THEN TRUE ELSE FALSE END ";
                strSQL+="         ELSE  CASE WHEN a.st_Reg NOT IN ('E') THEN TRUE ELSE FALSE END ";
                strSQL+="         END )";
                
                //strSQL+=" WHERE a.st_reg NOT IN ('E'/*, 'I'*/)"; //Se agrega el st_Reg ='I' para que no muestre detalles anulados. //Antes
                strSQL+=" AND a.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_tipdoc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a.co_Doc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ORDER BY a.co_reg ";     
                System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODPRV,   "" + rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    vecReg.add(INT_TBL_BUTPRV,   "");
                    vecReg.add(INT_TBL_NOMPRV,   "" + rst.getObject("tx_nom")==null?"":rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_NUMFAC,   "" + rst.getObject("tx_numchq")==null?"":rst.getString("tx_numchq"));
                    vecReg.add(INT_TBL_FECFAC,   objUti.formatearFecha(rst.getDate("fe_venchq"), "dd/MM/yyyy"));
                    bdgAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_valiva")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_valiva")) , objParSis.getDecimalesBaseDatos());
                    vecReg.add(INT_TBL_VALIVA,   "" + bdgAux);
                    bdgAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_monchq")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_monchq")) , objParSis.getDecimalesBaseDatos());
                    vecReg.add(INT_TBL_VALFAC,   "" + bdgAux);
                    vecReg.add(INT_TBL_NUMSER,   "" + rst.getObject("tx_numser")==null?"":rst.getString("tx_numser"));
                    vecReg.add(INT_TBL_NUMAUT,   "" + rst.getObject("tx_numautsri")==null?"":rst.getString("tx_numautsri"));
                    vecReg.add(INT_TBL_FECCAD,   "" + rst.getObject("tx_feccad")==null?"":rst.getString("tx_feccad"));
                    vecReg.add(INT_TBL_BUTOC,    "");
                    vecReg.add(INT_TBL_TXTOBS,   "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_CODREG,   "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));

                    String strDatFac = "";
                    int intCon = 0;
                    BigDecimal bdgCanAplFac = BigDecimal.ZERO;
                    strSQL ="";
                    strSQL+=" SELECT a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a.nd_valasi as valapl";
                    strSQL+=" FROM tbr_detRecDocCabMovInv AS a ";
                    strSQL+=" INNER JOIN tbm_cabmovinv AS a1 ON (a1.co_emp=a.co_emprel AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel) ";
                    strSQL+=" WHERE a.co_emp=" + rst.getInt("co_emp") + " AND a.co_loc=" + rst.getInt("co_loc");
                    strSQL+=" AND a.co_tipdoc=" + rst.getInt("co_tipdoc") + " AND a.co_doc= " + rst.getInt("co_doc")+" AND a.co_reg=" + rst.getInt("co_reg");
                    strSQL+=" AND a.st_reg NOT IN ('E')";
                    stmLoc=con.createStatement();
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while (rstLoc.next()) 
                    {
                        if (intCon == 1) {
                            strDatFac+=" UNION ALL ";
                        }
                        strDatFac+=" SELECT " + rstLoc.getInt("co_emprel") + " as coemp";
                        strDatFac+="      , " + rstLoc.getInt("co_locrel") + " as coloc";
                        strDatFac+="      , " + rstLoc.getInt("co_tipdocrel") + " as cotipdoc";
                        strDatFac+="      , " + rstLoc.getInt("co_docrel") + " as codoc";
                        strDatFac+="      , " + rstLoc.getBigDecimal("valapl") + " as valapl";
                        intCon = 1;
                        bdgCanAplFac=bdgCanAplFac.add(rstLoc.getBigDecimal("valapl"));
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    
                    vecReg.add(INT_TBL_DATFAC,    strDatFac);
                    vecReg.add(INT_TBL_VALFACORI, "" + objUti.redondearBigDecimal(rst.getBigDecimal("nd_monchq"), objParSis.getDecimalesBaseDatos()));
                    vecReg.add(INT_TBL_VAPLOC,    "" + bdgCanAplFac);
                    vecReg.add(INT_TBL_ESTCAMFAC, "N");
                    vecReg.add(INT_TBL_ESTAPL,    "" + rst.getObject("estApl")==null?"":rst.getString("estApl"));
                    vecDat.add(vecReg);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    private void seleccionReg(String strCodReg) {
        String strCodRegBus = "";
        try {
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                strCodRegBus = (objTblMod.getValueAt(i, INT_TBL_CODREG) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_CODREG).equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODREG).toString()));
                if (strCodRegBus.equals(strCodReg)) {
                    tblDat.changeSelection(i, 2, false, false);
                }
            }
        } 
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        //System.out.println("**************** INSERTAR ****************");
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                strSqlDetUpdCli = new StringBuffer();
                if (insertarCab()){
                    if (insertarDet()){
                        con.commit();
                        blnRes=true;   
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
                
                strSqlDetUpdCli = null;
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(){
        boolean blnRes=true;
        int intUltCodDoc=-1;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener el código del último registro.
                strSQL ="";
                strSQL+=" SELECT CASE WHEN MAX(a1.co_doc) IS NULL THEN 1 ELSE MAX(a1.co_doc+1) END AS co_doc";
                strSQL+=" FROM tbm_cabRecDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltCodDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltCodDoc==-1)
                    return false;
                txtCodDoc.setText("" + intUltCodDoc);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabrecdoc(";
                strSQL+="          co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc1, fe_doc, co_usrrec, st_imp";
                strSQL+="        , tx_obs1, tx_obs2, nd_mondoc, st_reg, fe_ing, co_usring, tx_coming, st_regrep)";
                strSQL+=" VALUES (";
                strSQL+="  " + objParSis.getCodigoEmpresa() + "";//co_emp
                strSQL+=", " + objParSis.getCodigoLocal() + "";  //co_loc
                strSQL+=", " + txtCodTipDoc.getText() + "";      //co_tipdoc
                strSQL+=", " + txtCodDoc.getText() + "";         //co_doc
                strSQL+=", " + txtNumDoc.getText() + "";         //ne_numdoc1
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//co_usrrec
                strSQL+=", 'N'"; //st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + ""; //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + ""; //tx_obs2
                strSQL+=", " + new BigDecimal(txtValDoc.getText()) + ""; //nd_mondoc
                strSQL+=", 'A'"; //st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+=", 'I'"; //st_regrep
                strSQL+=");";
                //Incrementa numeración
                strSQL+=" UPDATE tbm_cabTipDoc SET ne_ultdoc=" + txtNumDoc.getText();
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipdoc=" +  txtCodTipDoc.getText();
                strSQL+=" ;";                
                System.out.println("insertarCab: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)  { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet(){
        boolean blnRes=true;
        String strSQLUpd="";
        String strCodPrv="", strNumAutSri="", strNumSerDoc="", strFecCad="";
        String strDatFac="", strFecRec="";
        int intCodReg=1;
        int intConUpCli = 0;
        BigDecimal bgdValIva=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValFac=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValOC=new BigDecimal(BigInteger.ZERO);
        try{
            if (con!=null){
                stm=con.createStatement();
                
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    strCodPrv=objTblMod.getValueAt(i, INT_TBL_CODPRV)==null?"": (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_CODPRV).toString());
                    if(!strCodPrv.equals(""))
                    {
                        strSQLUpd="";
                        strNumAutSri=objTblMod.getValueAt(i, INT_TBL_NUMAUT)==null?"": (objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString());
                        strNumSerDoc=objTblMod.getValueAt(i, INT_TBL_NUMSER)==null?"": (objTblMod.getValueAt(i, INT_TBL_NUMSER).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_NUMSER).toString());
                        strFecCad=objTblMod.getValueAt(i, INT_TBL_FECCAD)==null?"": (objTblMod.getValueAt(i, INT_TBL_FECCAD).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_FECCAD).toString());
                        strFecRec=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                        
                        bgdValIva=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALIVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALIVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALIVA).toString())), objParSis.getDecimalesMostrar());
                        bgdValFac=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALFAC).toString())), objParSis.getDecimalesMostrar());
                        bgdValOC=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VAPLOC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString())), objParSis.getDecimalesMostrar());
                        
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_detRecDoc(";
                        strSQL+="             co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_cli,co_banChq,tx_numCtaChq";
                        strSQL+="           , tx_numChq,fe_recChq,fe_venChq,nd_monChq,tx_numSer,tx_numAutSRI";
                        strSQL+="           , tx_fecCad,tx_obs1,tx_obs2,st_reg,st_asiDocRel,nd_valTotAsi,nd_valIva";
                        strSQL+="           , nd_valApl,nd_valCon,st_regRep)";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                        strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                        strSQL+=", " + txtCodTipDoc.getText();      //co_tipDoc
                        strSQL+=", " + txtCodDoc.getText();         //co_doc
                        strSQL+=", " + intCodReg;   //co_reg
                        strSQL+=", " + strCodPrv;   //co_cli
                        strSQL+=", Null";//co_banChq
                        strSQL+=", Null";//tx_numCtaChq
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMFAC)) + "";//tx_numChq
                        strSQL+=", '" + strFecRec + "'";//fe_recChq
                        strSQL+=", '" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_FECFAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_venChq
                        strSQL+=", " + bgdValFac + ""; //nd_monChq                        
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMSER)) + "";//tx_numSer
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMAUT)) + "";//tx_numAutSRI
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_FECCAD)) + "";//tx_fecCad
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_TXTOBS)) + "";//tx_obs1
                        strSQL+=", Null";//tx_obs2
                        strSQL+=", 'A'"; //st_reg
                        strSQL+=", '"+((bgdValOC.compareTo(bgdValFac)==0)?"S":"N")+"'"; //st_asiDocRel
                        strSQL+=", " + bgdValOC + "";    //nd_valTotAsi
                        strSQL+=", " + bgdValIva + "";   //nd_valIva
                        strSQL+=", 0";  //nd_valApl
                        strSQL+=", 0";  //nd_valCon
                        strSQL+=", 'I'";//st_regRep
                        strSQL+=");";
                        strSQLUpd+=strSQL;

                        strDatFac=objTblMod.getValueAt(i, INT_TBL_DATFAC)==null?"": (objTblMod.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_DATFAC).toString());
                        if(!strDatFac.equals("")){
                            strSQL ="";
                            strSQL+=" INSERT INTO tbr_detRecDocCabMovInv(";
                            strSQL+="             co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel";
                            strSQL+="           , co_tipDocRel,co_docRel,nd_valAsi,st_reg,st_regRep)";
                            strSQL+=" SELECT ";
                            strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                            strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                            strSQL+=", " + txtCodTipDoc.getText();      //co_tipDoc
                            strSQL+=", " + txtCodDoc.getText();         //co_doc
                            strSQL+=", " + intCodReg;                   //co_reg
                            strSQL+=", coemp";     //co_empRel
                            strSQL+=", coloc";     //co_locRel
                            strSQL+=", cotipdoc";  //co_tipDocRel
                            strSQL+=", codoc";     //co_docRel
                            strSQL+=", valapl";    //nd_valAsi
                            strSQL+=", 'A'";       //st_reg
                            strSQL+=", 'I'";       //st_regRep
                            strSQL+=" FROM (" + strDatFac + ") AS x";  
                            strSQL+=" ;";
                            strSQLUpd+=strSQL;
                            
                            if (!updatePagMovInv(con, strDatFac, strFecRec, bgdValFac, bgdValIva, i)) {
                                blnRes = false;
                                break;
                            }
                        }
                        intCodReg++;
                        System.out.println("insertarDet: " + strSQLUpd);
                        stm.executeUpdate(strSQLUpd);
                        
                        ////////////////////////////////
                        if(objTooBar.getEstado()=='n') 
                        {
                            if (intConUpCli == 1) {
                                strSqlDetUpdCli.append(" UNION ALL ");
                            }
                            strSqlDetUpdCli.append(" SELECT " + objParSis.getCodigoEmpresa() + " as coemp, " + strCodPrv + " as cocli"
                                                 + ", text('" + strNumSerDoc + "')  as numser, text('" + strNumAutSri + "') as numautsri"
                                                 + ", text('" + strFecCad + "')  as feccadsri ");
                            intConUpCli = 1;   
                        }        
                        ////////////////////////////////
                    }                        
                }
                if (intConUpCli > 0) {
                    strSQL ="";
                    strSQL+=" UPDATE tbm_cli";
                    strSQL+=" SET tx_numserfacprv=x.numser, tx_numautsrifacprv=x.numautsri, tx_feccadfacprv=x.feccadsri ";
                    strSQL+=" FROM ( ";
                    strSQL+="        select x.*, x1.tx_numserfacprv, x1.tx_numautsrifacprv, x1.tx_feccadfacprv";
                    strSQL+="        from ( ";
                    strSQL+="               select * from ( " + strSqlDetUpdCli.toString() + " ) AS x ";
                    strSQL+="               group by coemp, cocli, numser, numautsri, feccadsri ";
                    strSQL+="        ) as x ";
                    strSQL+="        inner join tbm_cli as x1 on (x1.co_emp=x.coemp and x1.co_cli=x.cocli ) ";
                    strSQL+="        where ( x1.tx_numserfacprv is null or  x1.tx_numserfacprv != x.numser ) ";
                    strSQL+="        or ( x1.tx_numautsrifacprv is null or  x1.tx_numautsrifacprv != x.numautsri ) ";
                    strSQL+="        or ( x1.tx_feccadfacprv is null or  x1.tx_feccadfacprv != x.feccadsri ) ";
                    strSQL+=" ) AS x WHERE tbm_cli.co_emp=x.coemp and tbm_cli.co_cli=x.cocli  ";
                    strSQLUpd=strSQL;
                    System.out.println("insertarDet.actualizaCli: "+ strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                }
                stm.close();
                stm=null;    
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

    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (objParSis.getCodigoMenu() == 1942) { //Asignación de las ordenes de compra a las que aplican las facturas...
                    if (actualizarCab()) {
                        if (eliminarDet()) {
                            if (insertaNuevoDet()) {
                                if (actualizarDetAsiChq()) {
                                    con.commit();
                                    blnRes = true;
                                }
                                else 
                                    con.rollback();
                            }
                            else 
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else {
                    if (actualizarCab()) {
                        if (eliminarDet()) {
                            if (insertaNuevoDet()) {
                                if (actualizarDetRecChq()) {
                                    con.commit();
                                    blnRes = true;
                                }
                                else 
                                    con.rollback();
                            }
                            else 
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }        
                con.close();
                con=null;                
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null) {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabrecdoc";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strSQL+=", ne_numdoc1=" + txtNumDoc.getText();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comUltMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    private boolean eliminarDet() 
    {
        boolean blnRes=true;
        Statement stmLoc;
        try 
        {
            if (con != null) {
                stm=con.createStatement();
                ArrayList arlAux = objTblMod.getDataSavedBeforeRemoveRow();
                if (arlAux != null) {
                    for (int i = 0; i < arlAux.size(); i++) {
                        int intCodReg = objUti.getIntValueAt(arlAux, i, INT_ARR_CODREG);
                        strSQL ="";
                        strSQL+=" SELECT st_reg, tx_numchq FROM tbm_detrecdoc";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc= " + txtCodDoc.getText() + " AND co_reg=" + intCodReg;
                        strSQL+=" AND nd_valapl <= 0 ";
                        rst=stm.executeQuery(strSQL);
                        if (rst.next()) {
                            stmLoc=con.createStatement();
                            strSQL ="";
                            strSQL+=" UPDATE tbm_detrecdoc SET st_reg='E' ";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + " AND co_reg=" + intCodReg;
                            strSQL+=" ; ";
                            strSQL+=" UPDATE tbr_detrecdoccabmovinv SET st_reg='E' ";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + " AND co_reg=" + intCodReg;
                            strSQL+=" ; ";
                            strSQL+=" UPDATE tbm_pagmovinv SET nd_monchq=0";
                            strSQL+=" , fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null, tx_numctachq=null, st_pos='N', st_entsop='N' ";
                            strSQL+=" FROM (  ";
                            strSQL+="        SELECT co_emprel, co_locrel, co_tipdocrel, co_docrel FROM tbr_detrecdoccabmovinv  ";
                            strSQL+="        WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() ;
                            strSQL+="        AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + " and co_reg=" + intCodReg;
                            strSQL+=" ) AS x ";
                            strSQL+=" WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel";
                            strSQL+=" AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.tx_numchq='" + rst.getString("tx_numchq") + "'  ";
                            stmLoc.executeUpdate(strSQL);
                            stmLoc.close();
                            stmLoc=null;
                        }
                        else   {
                            mostrarMsgInf(" NO ELIMINADO... > ");
                        }
                        rst.close();
                        rst=null;
                    }
                }
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite insertar el detalle de un registro nuevo sobre la recepción de cheque existente.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertaNuevoDet(){
        boolean blnRes=true;
        String strSQLUpd="";
        String strCodPrv="", strNumAutSri="", strNumSerDoc="", strFecCad="";
        String strDatFac="", strFecRec="";
        int intCodReg=-1;
        int intConUpCli = 0;
        BigDecimal bgdValIva=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValFac=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValOC=new BigDecimal(BigInteger.ZERO);
        try{
            if (con!=null){
                stm=con.createStatement();
                
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    strCodPrv=objTblMod.getValueAt(i, INT_TBL_CODPRV)==null?"": (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_CODPRV).toString());
                    if(!strCodPrv.equals(""))
                    {
                        if (((objTblMod.getValueAt(i ,INT_TBL_CODREG)==null?"":(objTblMod.getValueAt(i, INT_TBL_CODREG).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_CODREG).toString())).equals(""))) 
                        {
                            strSQLUpd="";
                            strNumAutSri=objTblMod.getValueAt(i, INT_TBL_NUMAUT)==null?"": (objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString());
                            strNumSerDoc=objTblMod.getValueAt(i, INT_TBL_NUMSER)==null?"": (objTblMod.getValueAt(i, INT_TBL_NUMSER).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_NUMSER).toString());
                            strFecCad=objTblMod.getValueAt(i, INT_TBL_FECCAD)==null?"": (objTblMod.getValueAt(i, INT_TBL_FECCAD).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_FECCAD).toString());
                            strFecRec=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());

                            bgdValIva=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALIVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALIVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALIVA).toString())), objParSis.getDecimalesMostrar());
                            bgdValFac=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALFAC).toString())), objParSis.getDecimalesMostrar());
                            bgdValOC=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VAPLOC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString())), objParSis.getDecimalesMostrar());

                            intCodReg = _getObtenerMaxCodRegDet(con);
                            
                            strSQL ="";
                            strSQL+=" INSERT INTO tbm_detRecDoc(";
                            strSQL+="             co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_cli,co_banChq,tx_numCtaChq";
                            strSQL+="           , tx_numChq,fe_recChq,fe_venChq,nd_monChq,tx_numSer,tx_numAutSRI";
                            strSQL+="           , tx_fecCad,tx_obs1,tx_obs2,st_reg,st_asiDocRel,nd_valTotAsi,nd_valIva";
                            strSQL+="           , nd_valApl,nd_valCon,st_regRep)";
                            strSQL+=" VALUES (";
                            strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                            strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                            strSQL+=", " + txtCodTipDoc.getText();      //co_tipDoc
                            strSQL+=", " + txtCodDoc.getText();         //co_doc
                            strSQL+=", " + intCodReg;   //co_reg
                            strSQL+=", " + strCodPrv;   //co_cli
                            strSQL+=", Null";//co_banChq
                            strSQL+=", Null";//tx_numCtaChq
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMFAC)) + "";//tx_numChq
                            strSQL+=", '" + strFecRec + "'";//fe_recChq
                            strSQL+=", '" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_FECFAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_venChq
                            strSQL+=", " + bgdValFac + ""; //nd_monChq                        
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMSER)) + "";//tx_numSer
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMAUT)) + "";//tx_numAutSRI
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_FECCAD)) + "";//tx_fecCad
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_TXTOBS)) + "";//tx_obs1
                            strSQL+=", Null";//tx_obs2
                            strSQL+=", 'A'"; //st_reg
                            strSQL+=", '"+((bgdValOC.compareTo(bgdValFac)==0)?"S":"N")+"'"; //st_asiDocRel
                            strSQL+=", " + bgdValOC + "";    //nd_valTotAsi
                            strSQL+=", " + bgdValIva + "";   //nd_valIva
                            strSQL+=", 0";  //nd_valApl
                            strSQL+=", 0";  //nd_valCon
                            strSQL+=", 'I'";//st_regRep
                            strSQL+=");";
                            strSQLUpd+=strSQL;

                            strDatFac=objTblMod.getValueAt(i, INT_TBL_DATFAC)==null?"": (objTblMod.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_DATFAC).toString());
                            if(!strDatFac.equals("")){
                                strSQL ="";
                                strSQL+=" INSERT INTO tbr_detRecDocCabMovInv(";
                                strSQL+="             co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel";
                                strSQL+="           , co_tipDocRel,co_docRel,nd_valAsi,st_reg,st_regRep)";
                                strSQL+=" SELECT ";
                                strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                                strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                                strSQL+=", " + txtCodTipDoc.getText();      //co_tipDoc
                                strSQL+=", " + txtCodDoc.getText();         //co_doc
                                strSQL+=", " + intCodReg;                   //co_reg
                                strSQL+=", coemp";     //co_empRel
                                strSQL+=", coloc";     //co_locRel
                                strSQL+=", cotipdoc";  //co_tipDocRel
                                strSQL+=", codoc";     //co_docRel
                                strSQL+=", valapl";    //nd_valAsi
                                strSQL+=", 'A'";       //st_reg
                                strSQL+=", 'I'";       //st_regRep
                                strSQL+=" FROM (" + strDatFac + ") AS x";  
                                strSQL+=" ;";
                                strSQLUpd+=strSQL;

                                if (!updatePagMovInv(con, strDatFac, strFecRec, bgdValFac, bgdValIva, i)) {
                                    blnRes = false;
                                    break;
                                }
                            }
                            intCodReg++;
                            System.out.println("insertaNuevoDet: " + strSQLUpd);
                            stm.executeUpdate(strSQLUpd);

                            ////////////////////////////////
                            if(objTooBar.getEstado()=='n') 
                            {
                                if (intConUpCli == 1) {
                                    strSqlDetUpdCli.append(" UNION ALL ");
                                }
                                strSqlDetUpdCli.append(" SELECT " + objParSis.getCodigoEmpresa() + " as coemp, " + strCodPrv + " as cocli"
                                                     + ", text('" + strNumSerDoc + "')  as numser, text('" + strNumAutSri + "') as numautsri"
                                                     + ", text('" + strFecCad + "')  as feccadsri ");
                                intConUpCli = 1;   
                            }        
                            ////////////////////////////////
                        }
                        ////////////////////////////////
                    }                        
                }
                if (intConUpCli > 0) {
                    strSQL ="";
                    strSQL+=" UPDATE tbm_cli";
                    strSQL+=" SET tx_numserfacprv=x.numser, tx_numautsrifacprv=x.numautsri, tx_feccadfacprv=x.feccadsri ";
                    strSQL+=" FROM ( ";
                    strSQL+="        select x.*, x1.tx_numserfacprv, x1.tx_numautsrifacprv, x1.tx_feccadfacprv";
                    strSQL+="        from ( ";
                    strSQL+="               select * from ( " + strSqlDetUpdCli.toString() + " ) AS x ";
                    strSQL+="               group by coemp, cocli, numser, numautsri, feccadsri ";
                    strSQL+="        ) as x ";
                    strSQL+="        inner join tbm_cli as x1 on (x1.co_emp=x.coemp and x1.co_cli=x.cocli ) ";
                    strSQL+="        where ( x1.tx_numserfacprv is null or  x1.tx_numserfacprv != x.numser ) ";
                    strSQL+="        or ( x1.tx_numautsrifacprv is null or  x1.tx_numautsrifacprv != x.numautsri ) ";
                    strSQL+="        or ( x1.tx_feccadfacprv is null or  x1.tx_feccadfacprv != x.feccadsri ) ";
                    strSQL+=" ) AS x WHERE tbm_cli.co_emp=x.coemp and tbm_cli.co_cli=x.cocli  ";
                    strSQLUpd=strSQL;
                    System.out.println("insertaNuevoDet.actualizaCli: "+ strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                }
                stm.close();
                stm=null;    
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
    

    private boolean actualizarDetRecChq() 
    {
        boolean blnRes = true;
        String strSQLUpd = "";
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                String strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
                {
                    strSQLUpd="";
                    String strCodPrv=objTblMod.getValueAt(i, INT_TBL_CODPRV)==null?"": (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_CODPRV).toString());
                    if(!strCodPrv.equals(""))
                    {
                        String strCodReg = ((objTblMod.getValueAt(i, INT_TBL_CODREG) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_CODREG).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODREG).toString())));
                        String strNumAutSri = (objTblMod.getValueAt(i, INT_TBL_NUMAUT) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMAUT).toString()));
                        String strSecDoc = (objTblMod.getValueAt(i, INT_TBL_NUMSER) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_NUMSER).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_NUMSER).toString()));
                        String strFecCad = (objTblMod.getValueAt(i, INT_TBL_FECCAD) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_FECCAD).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_FECCAD).toString()));

                        if (!(strCodReg.equals(""))) 
                        {
                            BigDecimal bgdValIvaFac=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALIVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALIVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALIVA).toString())), objParSis.getDecimalesMostrar());
                            BigDecimal bgdValFac=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALFAC).toString())), objParSis.getDecimalesMostrar());
                            BigDecimal bgdValOC=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VAPLOC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString())), objParSis.getDecimalesMostrar());

                            strSQL ="";
                            strSQL+=" UPDATE tbm_detrecdoc ";
                            strSQL+=" SET tx_numser='" + strSecDoc + "'";
                            strSQL+="   , tx_numautsri='" + strNumAutSri + "'";
                            strSQL+="   , tx_feccad='" + strFecCad + "'";
                            strSQL+="   , tx_numchq="+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMFAC));
                            strSQL+="   , fe_recchq='" + strFecDoc + "'";
                            strSQL+="   , fe_venchq='" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_FECFAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                            strSQL+="   , nd_valiva=" + bgdValIvaFac + ", nd_monchq=" + bgdValFac + "";
                            strSQL+="   , tx_obs1=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_TXTOBS)) + "";//tx_obs1
                            strSQL+="   , st_asiDocRel='"+((bgdValOC.compareTo(bgdValFac)==0)?"S":"N")+"'";  
                            strSQL+="   , st_regrep='M' ";
                            strSQL+="   , nd_valtotasi=" + bgdValOC + " ";
                            strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                            strSQL+=" AND co_reg=" + strCodReg;
                            strSQL+=" ;";
                            strSQLUpd+=strSQL;

                            deleteChangeCtaCon(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                  , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                  , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                  , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                  , Integer.parseInt(strCodReg));

                            strSQL ="";
                            strSQL+=" UPDATE tbr_detrecdocCabmovinv SET st_reg='E', st_regrep='M'";
                            strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                            strSQL+=" AND co_reg=" + strCodReg;
                            strSQL+=" ;";
                            strSQLUpd+=strSQL;

                            strSQL ="";
                            strSQL+=" UPDATE tbm_pagmovinv ";
                            strSQL+=" SET nd_valiva=0, nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null";
                            strSQL+="   , tx_numctachq=null, st_pos='N', st_entsop='N', st_regrep='M' ";
                            strSQL+=" FROM ( ";
                            strSQL+="        SELECT  a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a1.tx_numchq ";
                            strSQL+="        FROM tbr_detrecdoccabmovinv AS a";
                            strSQL+="        INNER JOIN tbm_detrecdoc AS a1 ";
                            strSQL+="        ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc AND a1.co_reg=a.co_reg ) ";
                            strSQL+="        WHERE a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                            strSQL+="        AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                            strSQL+="        AND a.co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                            strSQL+="        AND a.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                            strSQL+="        AND a.co_reg=" + strCodReg + " ";
                            strSQL+=" ) AS x ";
                            strSQL+=" WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel ";
                            strSQL+=" AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.tx_numchq=x.tx_numchq ";
                            strSQL+=" ; ";
                            strSQLUpd+=strSQL;
                            stm.executeUpdate(strSQLUpd);

                            String strDatFac=objTblMod.getValueAt(i, INT_TBL_DATFAC)==null?"": (objTblMod.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_DATFAC).toString());
                            if(!strDatFac.equals(""))
                            {
                                if (!unificarPag(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                    , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                    , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                    , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                    , Integer.parseInt(strCodReg))) 
                                {
                                    blnRes = false;
                                    break;
                                }
                               
                                if (!updateInsertRelDetRecPagMov(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                              , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                              , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                              , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                              , Integer.parseInt(strCodReg), strDatFac))  
                                {     
                                    blnRes = false;
                                    break;
                                }

                                if (!updatePagMovInv(con, strDatFac, strFecDoc, bgdValFac, bgdValIvaFac, i)) {
                                    blnRes = false;
                                    break;
                                }
                            }
                        }
                    }
                } 
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean actualizarDetAsiChq() 
    {
        boolean blnRes = true;
        String strSQLUpd = "";        
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                String strFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
                {
                    strSQLUpd="";
                    String strCodPrv=objTblMod.getValueAt(i, INT_TBL_CODPRV)==null?"": (objTblMod.getValueAt(i, INT_TBL_CODPRV).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_CODPRV).toString());
                    if(!strCodPrv.equals(""))
                    {
                        String strCodReg = ((objTblMod.getValueAt(i, INT_TBL_CODREG) == null ? "" : (objTblMod.getValueAt(i, INT_TBL_CODREG).toString().equals("") ? "" : objTblMod.getValueAt(i, INT_TBL_CODREG).toString())));
                        String strEstCamFac= ((objTblMod.getValueAt(i, INT_TBL_ESTCAMFAC) == null ? "S" : (objTblMod.getValueAt(i, INT_TBL_ESTCAMFAC).toString().equals("") ? "S" : objTblMod.getValueAt(i, INT_TBL_ESTCAMFAC).toString())));

                        if ((strEstCamFac.equals("S"))) 
                        {
                            if (!(strCodReg.equals("")))
                            {
                                BigDecimal bgdValIvaFac=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALIVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALIVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALIVA).toString())), objParSis.getDecimalesMostrar());
                                BigDecimal bgdValFac=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALFAC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALFAC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALFAC).toString())), objParSis.getDecimalesMostrar());
                                BigDecimal bgdValOC=objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VAPLOC)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VAPLOC).toString())), objParSis.getDecimalesMostrar());
                                
                                strSQL ="";
                                strSQL+=" UPDATE tbm_detrecdoc ";
                                strSQL+=" SET tx_numchq="+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_NUMFAC));
                                strSQL+="   , fe_recchq='" + strFecDoc + "'";
                                strSQL+="   , fe_venchq='" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_FECFAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                                strSQL+="   , nd_valiva=" + bgdValIvaFac + ", nd_monchq=" + bgdValFac + "";
                                strSQL+="   , tx_obs1=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_TXTOBS)) + "";
                                strSQL+="   , st_asiDocRel='"+((bgdValOC.compareTo(bgdValFac)==0)?"S":"N")+"'";  
                                strSQL+="   , st_regrep='M' ";
                                strSQL+="   , nd_valtotasi=" + bgdValOC + " ";
                                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                strSQL+=" AND co_reg=" + strCodReg;
                                strSQL+=" ;";
                                strSQLUpd+=strSQL;                                
                                
                                deleteChangeCtaCon(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                      , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                      , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                      , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                      , Integer.parseInt(strCodReg));                                

                                strSQL ="";
                                strSQL+=" UPDATE tbr_detrecdocCabmovinv SET st_reg='E', st_regrep='M'";
                                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                strSQL+=" AND co_reg=" + strCodReg;
                                strSQL+=" ;";
                                strSQLUpd+=strSQL;
         
                                strSQL ="";
                                strSQL+=" UPDATE tbm_pagmovinv ";
                                strSQL+=" SET nd_valiva=0, nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null";
                                strSQL+="   , tx_numctachq=null, st_pos='N', st_entsop='N', st_regrep='M' ";
                                strSQL+=" FROM ( ";
                                strSQL+="        SELECT  a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a1.tx_numchq ";
                                strSQL+="        FROM tbr_detrecdoccabmovinv AS a";
                                strSQL+="        INNER JOIN tbm_detrecdoc AS a1 ";
                                strSQL+="        ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc AND a1.co_reg=a.co_reg ) ";
                                strSQL+="        WHERE a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                strSQL+="        AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                strSQL+="        AND a.co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                strSQL+="        AND a.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                strSQL+="        AND a.co_reg=" + strCodReg + " ";
                                strSQL+=" ) AS x ";
                                strSQL+=" WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel ";
                                strSQL+=" AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.tx_numchq=x.tx_numchq ";
                                strSQL+=" ; ";
                                strSQLUpd+=strSQL;
                                stm.executeUpdate(strSQLUpd);
                                
                                String strDatFac=objTblMod.getValueAt(i, INT_TBL_DATFAC)==null?"": (objTblMod.getValueAt(i, INT_TBL_DATFAC).toString().equals("")?"": objTblMod.getValueAt(i, INT_TBL_DATFAC).toString());
                                if(!strDatFac.equals(""))
                                {
                                    if (!unificarPag(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                        , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                        , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                        , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                        , Integer.parseInt(strCodReg))) 
                                    {
                                        blnRes = false;
                                        break;
                                    }

                                    if (!updateInsertRelDetRecPagMov(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                                        , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                                        , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                                        , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                                                        , Integer.parseInt(strCodReg), strDatFac))  
                                    {                                   
                                        blnRes = false;
                                        break;
                                    }

                                    if (!updatePagMovInv(con, strDatFac, strFecDoc, bgdValFac, bgdValIvaFac, i)) {
                                        blnRes = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean unificarPag(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc, int codRegDet) 
    {
        boolean blnRes = true;   
        ResultSet rstLoc;
        Statement stmLoc01, stmLoc02;
        int intCodReg = 0;
        try {
            if (conn != null) {
                stmLoc01 = conn.createStatement();
                strSQL ="";
                strSQL+=" SELECT x1.co_emp, x1.co_loc, x1.co_tipdoc, x1.co_doc, x1.nd_porret ";
                strSQL+="      , count(x1.nd_porret) AS cantidad, sum(x1.mo_pag) AS mopag, sum(x1.nd_basimp) AS basimp ";
                strSQL+=" FROM ( ";
                strSQL+="       SELECT a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a1.tx_numchq";
                strSQL+="       FROM tbr_detrecdoccabmovinv AS a ";
                strSQL+="       INNER JOIN tbm_detrecdoc AS a1";
                strSQL+="       ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc AND a1.co_reg=a.co_reg ) ";
                strSQL+="       WHERE a.co_emp=" + codEmp;
                strSQL+="       AND a.co_loc=" + codLoc;
                strSQL+="       AND a.co_tipdoc=" + codTipDoc;
                strSQL+="       AND a.co_doc=" + codDoc;
                strSQL+="       AND a.co_reg=" + codRegDet;
                strSQL+=" ) AS x ";
                strSQL+=" INNER JOIN tbm_pagmovinv AS x1";
                strSQL+=" ON (x1.co_emp=x.co_emprel AND x1.co_loc=x.co_locrel AND x1.co_tipdoc=x.co_tipdocrel AND x1.co_doc=x.co_docrel ) ";
                strSQL+=" WHERE x1.st_reg in ('C','A') AND x1.tx_numchq IS NULL AND x1.nd_porret > 0 ";
                strSQL+=" GROUP BY x1.co_emp, x1.co_loc, x1.co_tipdoc, x1.co_doc, x1.nd_porret ";
                System.out.println("unificandoPago: " + strSQL);
                rstLoc = stmLoc01.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    if (rstLoc.getInt("cantidad") > 1)
                    {
                        intCodReg = getMaxCodRegPagMov(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"));
                        
                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_pagmovinv ";
                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, co_tipret, nd_porret";
                        strSQL+=" , tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq ";
                        strSQL+=" , tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree ";
                        strSQL+=" , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp, tx_numAutSRI, tx_fecCad, tx_numSer ";
                        strSQL+=" ) ";
                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg + " , ne_diacre, fe_ven ,co_tipret, nd_porret";
                        strSQL+="      , tx_aplret, sum(mo_pag), ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq";
                        strSQL+="      , tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, 'C', st_regrep, fe_ree, co_usrree";
                        strSQL+="      , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, sum(nd_basimp), null, null, null";
                        strSQL+=" FROM tbm_pagmovinv ";
                        strSQL+=" WHERE co_emp=" + rstLoc.getInt("co_emp");
                        strSQL+=" AND co_loc=" + rstLoc.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc.getInt("co_tipdoc");
                        strSQL+=" AND co_doc= " + rstLoc.getInt("co_doc");
                        strSQL+=" AND nd_porret = " + rstLoc.getInt("nd_porret");
                        strSQL+=" AND st_reg IN ('C','A') AND tx_numchq IS NULL ";
                        strSQL+=" GROUP BY co_emp, co_loc, co_tipdoc, co_doc, ne_diacre, fe_ven ,co_tipret, nd_porret";
                        strSQL+="        , tx_aplret, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq";
                        strSQL+="        , tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree"; 
                        strSQL+="        , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri ";
                        strSQL+=" ;";
                        System.out.println("--> "+strSQL );
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;

                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" UPDATE tbm_pagmovinv SET st_reg=CASE WHEN st_reg IN ('A') THEN 'F' ELSE CASE WHEN st_reg IN ('C') THEN 'I' END END, st_regrep='M' ";
                        strSQL+=" WHERE co_emp=" + rstLoc.getInt("co_emp");
                        strSQL+=" AND co_loc=" + rstLoc.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc.getInt("co_tipdoc");
                        strSQL+=" AND co_doc= " + rstLoc.getInt("co_doc");
                        strSQL+=" AND nd_porret = " + rstLoc.getInt("nd_porret");
                        strSQL+=" AND st_reg IN ('C','A') AND tx_numchq IS NULL";
                        strSQL+=" AND co_reg != " + intCodReg;
                        strSQL+=" ;";
                        System.out.println("--> "+strSQL );
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc01.close();
                stmLoc01=null;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    /**
     * Actualiza informacion en la tabla tbr_detrecdocpagmovinv o caso
     * comtrario inserta los nuevos registros.
     *
     * @param conn coneccion de la base
     * @param intCodTipDoc codigo tipo docuemnto
     * @param intCodDoc codigo documento
     * @param strCodReg codigo del registro
     * @param strSQL Query del que actualiza o inserta..
     * @return true si se realizo con exito false si hay algun error
     */
    private boolean updateInsertRelDetRecPagMov(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc, int codReg, String sentenciaSQL) 
    {
        boolean blnRes=true;
        Statement stmLoc01, stmLoc02, stmLoc03;
        ResultSet rstLoc01, rstLoc02;
        try 
        {
            if (conn != null) 
            {
                stmLoc01=conn.createStatement();
                rstLoc01 = stmLoc01.executeQuery(sentenciaSQL);
                while (rstLoc01.next()) 
                {
                    stmLoc02=conn.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT * FROM tbr_detrecdoccabmovinv";
                    strSQL+=" WHERE co_emp=" + codEmp + " AND co_loc=" + codLoc;
                    strSQL+=" AND co_tipdoc=" + codTipDoc + " AND co_doc=" + codDoc + " AND co_reg=" + codReg;
                    strSQL+=" AND co_emprel=" + rstLoc01.getInt("coemp") +" AND co_locrel=" + rstLoc01.getInt("coloc");
                    strSQL+=" AND co_tipdocrel=" + rstLoc01.getInt("cotipdoc") + " AND co_docrel=" + rstLoc01.getInt("codoc") ;
                    System.out.println("actualizandoInsertandoRecepcionPago: "+strSQL);
                    rstLoc02 = stmLoc02.executeQuery(strSQL);
                    if (rstLoc02.next()) {
                        stmLoc03=conn.createStatement();
                        strSQL ="";
                        strSQL+=" UPDATE tbr_detrecdoccabmovinv ";
                        strSQL+=" SET st_reg='A', st_regrep='M', nd_valasi=" + rstLoc01.getDouble("valapl");
                        strSQL+=" WHERE co_emp=" + codEmp + " AND co_loc=" + codLoc ;
                        strSQL+=" AND co_tipdoc=" + codTipDoc + " AND co_doc=" + codDoc + " AND co_reg=" + codReg;
                        strSQL+=" AND co_emprel=" + rstLoc01.getInt("coemp") + " AND co_locrel=" + rstLoc01.getInt("coloc");
                        strSQL+=" AND co_tipdocrel=" + rstLoc01.getInt("cotipdoc") + " AND co_docrel=" + rstLoc01.getInt("codoc");
                        strSQL+=" ;";
                        System.out.println(" uno --> "+strSQL );
                        stmLoc03.executeUpdate(strSQL);
                        stmLoc03.close();
                        stmLoc03=null;
                    }
                    else {
                        stmLoc03=conn.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbr_detrecdoccabmovinv";
                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel";
                        strSQL+=" , co_locrel,co_tipdocrel, co_docrel, st_reg, st_regrep, nd_valasi ";
                        strSQL+=" ) ";
                        strSQL+=" SELECT " + codEmp + ", " + codLoc;
                        strSQL+="      , " + codTipDoc + ", " + codDoc + " ," + codReg ;
                        strSQL+="      , " + rstLoc01.getInt("coemp") + ", " + rstLoc01.getInt("coloc");
                        strSQL+="      , " + rstLoc01.getInt("cotipdoc") + ", " + rstLoc01.getInt("codoc");
                        strSQL+="      , 'A', 'I', " + rstLoc01.getDouble("valapl");
                        System.out.println(" dos --> "+strSQL );
                        stmLoc03.executeUpdate(strSQL);
                        stmLoc03.close();
                        stmLoc03=null;                        
                    }
                    rstLoc02.close();
                    rstLoc02 = null;
                    stmLoc02.close();
                    stmLoc02=null;
                }
                rstLoc01.close();
                rstLoc01=null;
                stmLoc01.close();
                stmLoc01=null;
                blnRes = true;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    private boolean updatePagMovInv(Connection conn, String datoFacPrv, String fechaDoc, BigDecimal valorFacPrv, BigDecimal valorIvaFacPrv, int numFil) 
    {
        boolean blnRes=true;
        ResultSet rstLoc;
        Statement stmLoc01, stmLoc02;
        String strSQLUpd="";
        try 
        {
            if (conn != null) {
                stmLoc01 = conn.createStatement();
                String strNumFac=(objTblMod.getValueAt(numFil, INT_TBL_NUMFAC)==null?"0":(objTblMod.getValueAt(numFil, INT_TBL_NUMFAC).toString().equals("")?"0":objTblMod.getValueAt(numFil, INT_TBL_NUMFAC).toString()));
                String strNumSer=(objTblMod.getValueAt(numFil, INT_TBL_NUMSER)==null?"0":(objTblMod.getValueAt(numFil, INT_TBL_NUMSER).toString().equals("")?"0":objTblMod.getValueAt(numFil, INT_TBL_NUMSER).toString()));
                String strNumAut=(objTblMod.getValueAt(numFil, INT_TBL_NUMAUT)==null?"0":(objTblMod.getValueAt(numFil, INT_TBL_NUMAUT).toString().equals("")?"0":objTblMod.getValueAt(numFil, INT_TBL_NUMAUT).toString()));
                String strFecCad=(objTblMod.getValueAt(numFil, INT_TBL_FECCAD)==null?"0":(objTblMod.getValueAt(numFil, INT_TBL_FECCAD).toString().equals("")?"0":objTblMod.getValueAt(numFil, INT_TBL_FECCAD).toString()));
                String strAuxFec=(objTblMod.getValueAt(numFil, INT_TBL_FECFAC)==null?"0":(objTblMod.getValueAt(numFil, INT_TBL_FECFAC).toString().equals("")?"0":objTblMod.getValueAt(numFil, INT_TBL_FECFAC).toString()));
                String strFecFac= objUti.formatearFecha(strAuxFec, "dd/MM/yyyy", "yyyy-MM-dd") ; 
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT x.*, a.nd_tot FROM ( "+ datoFacPrv +") AS x";
                strSQL+=" INNER JOIN tbm_cabmovinv AS a ON (x.coemp=a.co_emp AND x.coloc=a.co_loc AND x.cotipdoc=a.co_tipdoc AND x.codoc=a.co_doc ) ";
                System.out.println("consulta pagos a actualizar: "+strSQL);
                rstLoc=stmLoc01.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    strSQLUpd="";
                    if (rstLoc.getBigDecimal("nd_tot").compareTo(rstLoc.getBigDecimal("valapl")) ==0 ) 
                    { 
                        stmLoc02=conn.createStatement();
                        strSQL ="";
                        strSQL+=" UPDATE tbm_pagmovinv ";
                        strSQL+=" SET st_entSop='S'";
                        strSQL+="   , tx_numChq='" + strNumFac + "'";
                        strSQL+="   , fe_recChq='" + strFecFac + "' ";
                        strSQL+="   , fe_venChq='" + fechaDoc + "'";
                        strSQL+="   , nd_valiva=" + valorIvaFacPrv + " ,nd_monChq=" + valorFacPrv + "";
                        strSQL+="   , tx_numSer='" + strNumSer + "',tx_numAutSRI='" + strNumAut + "',tx_fecCad='" + strFecCad + "'";
                        strSQL+=" WHERE co_emp=" + rstLoc.getInt("coemp")+ " AND co_loc=" + rstLoc.getInt("coloc");
                        strSQL+=" AND co_tipdoc=" + rstLoc.getInt("cotipdoc") + " AND co_doc=" + rstLoc.getInt("codoc");
                        strSQL+=" ; ";
                        strSQLUpd+=strSQL;

                        //Rose: OPIMPO y OPCOLO
                        if(rstLoc.getString("cotipdoc").equals(strCodTipDocOpimpo)  || rstLoc.getString("cotipdoc").equals(strCodTipDocOpcolo) ) 
                        { 
                            /* Actualiza fecha de vencimiento: tbm_pagMovInv */
                            strSQL ="";
                            strSQL+=" UPDATE tbm_pagmovinv ";
                            strSQL+=" SET fe_ven=(cast('"+strFecFac+"' as date) + ne_diaCre) ";
                            strSQL+="   , fe_venChq=(cast('"+strFecFac+"' as date) ) ";  //Fecha de la factura.
                            //strSQL+="   , tx_obs1= 'Mod.Fec.Ven.' ||(CASE WHEN tx_obs1 IS NULL THEN '' ELSE (' '||tx_obs1) END)";
                            strSQL+=" WHERE co_emp=" + rstLoc.getInt("coemp") + "";
                            strSQL+=" AND co_loc=" + rstLoc.getInt("coloc")+" ";
                            strSQL+=" AND co_tipdoc=" + rstLoc.getInt("cotipdoc") + " ";
                            strSQL+=" AND co_doc=" + rstLoc.getInt("codoc") + " ";      
                            strSQL+=" AND (mo_pag+nd_abo) <>0 AND st_autPag IN ('N')";  //Solo actualiza cuando no han sido emitidas las retenciones , ni existe autorización de pagos.
                            strSQL+=" ; ";      
                            strSQLUpd+=strSQL;
                        }
                        System.out.println("actualizando pagos: "+strSQLUpd);
                        stmLoc02.executeUpdate(strSQLUpd);
                        stmLoc02.close();
                        stmLoc02=null;
                        
                        BigDecimal bgdValApl=rstLoc.getBigDecimal("valapl");
                        System.out.println("bgdValApl: "+bgdValApl);
                        //Actualiza pago
                        if (!addChangeCtaCon(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc"), rstLoc.getBigDecimal("valapl"))) {
                            blnRes = false;
                            break;
                        }
                    }
                    else {
                        //Reestructura pago
                        if (!reestructurarFactura(conn, rstLoc.getInt("coemp"), rstLoc.getInt("coloc"), rstLoc.getInt("cotipdoc"), rstLoc.getInt("codoc")
                                                      , numFil, rstLoc.getBigDecimal("valapl"), fechaDoc, valorFacPrv, rstLoc.getBigDecimal("valapl"), valorIvaFacPrv))
                        {
                            blnRes = false;
                            break;
                        }
                    }
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc01.close();
                stmLoc01 = null;
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }    
    
    /**
     * Función que realiza la reestructuración de un pago de la factura.
     * @param conn
     * @param codEmp
     * @param codLoc
     * @param codTipDoc
     * @param codDoc
     * @param valApl
     * @param numFil
     * @param strFecDoc
     * @param valFacPrv
     * @param valFacPrvApl
     * @param valIvaFacPrv
     * @return true: Si se pudo realizar el proceso de reestructuración
     * <BR> false: Caso contrario
     */
    private boolean reestructurarFactura(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc, int numFil
                                        , BigDecimal valApl, String fechaDoc, BigDecimal valFacPrv, BigDecimal valFacPrvApl, BigDecimal valIvaFacPrv) 
    {
        boolean blnRes = false;
        Statement stmLoc01, stmLoc02, stmLoc03;
        ResultSet rstLoc01, rstLoc02;
        int intCodReg = -1, intCodReg2 =-1, intCodRegPag = -1;
        try 
        {
            if (conn != null) 
            {                
                //<editor-fold defaultstate="collapsed" desc="/* Reestructuración 1 */">
                stmLoc01 = conn.createStatement();
                
                BigDecimal bgdDifBasImp=BigDecimal.ZERO;
                BigDecimal bgdBasImp=BigDecimal.ZERO;
                BigDecimal bgdPorRet=BigDecimal.ZERO;
                BigDecimal bgdDifPorRet=BigDecimal.ZERO;
                BigDecimal bgdPorRetTot=BigDecimal.ZERO;
                BigDecimal bgdDifPorRetTot=BigDecimal.ZERO;   
                
                String strNumFac=(objTblMod.getValueAt(numFil, INT_TBL_NUMFAC) == null ?"0":(objTblMod.getValueAt(numFil, INT_TBL_NUMFAC).toString().equals("") ?"0":objTblMod.getValueAt(numFil, INT_TBL_NUMFAC).toString()));
                String strNumSer=(objTblMod.getValueAt(numFil, INT_TBL_NUMSER) == null ?"0":(objTblMod.getValueAt(numFil, INT_TBL_NUMSER).toString().equals("") ?"0":objTblMod.getValueAt(numFil, INT_TBL_NUMSER).toString()));
                String strNumAut=(objTblMod.getValueAt(numFil, INT_TBL_NUMAUT) == null ?"0":(objTblMod.getValueAt(numFil, INT_TBL_NUMAUT).toString().equals("") ?"0":objTblMod.getValueAt(numFil, INT_TBL_NUMAUT).toString()));
                String strFecCad=(objTblMod.getValueAt(numFil, INT_TBL_FECCAD) == null ?"0":(objTblMod.getValueAt(numFil, INT_TBL_FECCAD).toString().equals("") ?"0":objTblMod.getValueAt(numFil, INT_TBL_FECCAD).toString()));

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT * ";
                strSQL+="  , CASE WHEN nd_abo != 0 THEN case when abs(nd_abo) <= nueporretiva then nd_abo else  (nd_abo + abs(nd_abo+nueporretiva))  end  ELSE 0 END AS abonue1  ";
                strSQL+="  , CASE WHEN nd_abo != 0 THEN case when abs(nd_abo) <= nueporretiva then   0   else  (nd_abo+nueporretiva)  end ELSE 0 END AS abodif2  ";
                strSQL+=" FROM ( ";
                strSQL+="        select *, round(  ( valiva * (nd_porret/100)) , 2 ) as nueporretiva ";
                strSQL+="        from (  ";
                strSQL+="               Select *, round(  ( difbasimp * (nd_porret/100)) , 2 ) as difnueporret ";
                strSQL+="                       , round(  ( difbasimpiva * (nd_porret/100)) , 2 ) as difnueporretiva  ";
                strSQL+="               from ( ";
                strSQL+="                     select *, (nd_basimp - basimpnue ) as difbasimp , (nd_basimp - valiva ) as difbasimpiva";
                strSQL+="                     from ( ";
                strSQL+="                           select a1.nd_abo, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_basimp, a2.tx_aplret, a1.nd_porret ";
                strSQL+="                                , case when a.nd_valiva > 0 ";
                strSQL+="                                  then  round(  (" + valApl + " / round( (1+(a.nd_poriva/100)),2)) , 2 ) ";
                strSQL+="                                  else round(  (" + valApl + ") , 2 ) end AS basimpnue ";
                strSQL+="                                , case when a.nd_valiva > 0 ";
                strSQL+="                                  then round(  ((" + valApl + " / (1+(a.nd_poriva/100))) * (a1.nd_porret/100)) , 2 )  ";
                strSQL+="                                  else round(  (" + valApl + " * (a1.nd_porret/100)) , 2 )  end  AS nueporret  ";
                strSQL+="                                , round( round(  (" + valApl + " / round( (1+(a.nd_poriva/100)),2)) , 2 ) * (a.nd_poriva/100) , 2 ) AS valiva ";
                strSQL+="                          from tbm_cabmovinv as a ";
                strSQL+="                          inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )";
                strSQL+="                          inner join  tbm_cabtipret as a2 on (a2.co_emp=a1.co_emp and a2.co_tipret=a1.co_tipret ) ";
                strSQL+="                          where a.co_emp=" + codEmp + " and a.co_loc=" + codLoc + " and a.co_tipdoc=" + codTipDoc + " and a.co_doc= " + codDoc;
                strSQL+="                          and a1.nd_porret != 0 and a1.st_reg in ('A','C') and tx_numchq is null";
                strSQL+="                     ) as x ";
                strSQL+="               ) as x ";
                strSQL+="        ) as x  ";
                strSQL+=" ) AS x  ";
                System.out.println("reestructurandoFactura**1--> "+strSQL );
                rstLoc01 = stmLoc01.executeQuery(strSQL);
                while (rstLoc01.next()) 
                {                    
                    bgdDifBasImp = BigDecimal.ZERO;
                    bgdBasImp = BigDecimal.ZERO;
                    bgdPorRet = BigDecimal.ZERO;
                    bgdDifPorRet = BigDecimal.ZERO;

                    if (rstLoc01.getString("tx_aplret").equals("S"))
                    {   //sobre subtotal
                        bgdDifBasImp = rstLoc01.getBigDecimal("difbasimp");
                        bgdBasImp = rstLoc01.getBigDecimal("basimpnue");
                        bgdPorRet = rstLoc01.getBigDecimal("nueporret");
                        bgdDifPorRet = rstLoc01.getBigDecimal("difnueporret");
                    }
                    if (rstLoc01.getString("tx_aplret").equals("I")) 
                    {   // sobre iva
                        bgdDifBasImp = rstLoc01.getBigDecimal("difbasimpiva");
                        bgdBasImp = rstLoc01.getBigDecimal("valiva");
                        bgdPorRet = rstLoc01.getBigDecimal("nueporretiva");
                        bgdDifPorRet = rstLoc01.getBigDecimal("difnueporretiva");
                    }

                    if(bgdDifBasImp.compareTo(BigDecimal.ZERO)>0) 
                    {
                        bgdPorRetTot=bgdPorRetTot.add(bgdPorRet);
                        intCodReg=getMaxCodRegPagMov(conn, rstLoc01.getInt("co_emp"), rstLoc01.getInt("co_loc"), rstLoc01.getInt("co_tipdoc"), rstLoc01.getInt("co_doc"));

                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_pagmovinv";
                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven";
                        strSQL+=" , co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra";
                        strSQL+=" , nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq";
                        strSQL+=" , fe_recchq, fe_venchq, nd_valiva, nd_monchq";
                        strSQL+=" , co_prochq, st_reg, st_regrep, fe_ree, co_usrree";
                        strSQL+=" , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri";
                        strSQL+=" , nd_basimp, tx_numAutSRI, tx_fecCad, tx_numSer";
                        strSQL+=" )";
                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg + " , ne_diacre, fe_ven";
                        strSQL+="      , co_tipret, nd_porret, tx_aplret, abs(" + bgdPorRet + "), ne_diagra";
                        strSQL+="      , " + rstLoc01.getDouble("abonue1") + " as nd_abo, st_sop, 'S', 'N', null, null, '" + strNumFac + "'";
                        strSQL+="      , '" + objUti.formatearFecha(tblDat.getValueAt(numFil, INT_TBL_FECFAC).toString(), "dd/MM/yyyy", "yyyy/MM/dd") + "'";
                        strSQL+="      , '" + fechaDoc + "', " + valIvaFacPrv + ", " + valFacPrv + "";
                        strSQL+="      , co_prochq, 'C', 'I', fe_ree, co_usrree";
                        strSQL+="      , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri";
                        strSQL+="      , " + bgdBasImp.abs() + ", '" + strNumAut + "', '" + strFecCad + "', '" + strNumSer + "' ";
                        strSQL+=" FROM tbm_pagmovinv ";
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + " AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg");
                        strSQL+=" ;";
                        System.out.println("1 --> "+strSQL );
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;

                        bgdDifPorRetTot=bgdDifPorRetTot.add(bgdDifPorRet);
                        intCodReg2=getMaxCodRegPagMov(conn, rstLoc01.getInt("co_emp"), rstLoc01.getInt("co_loc"), rstLoc01.getInt("co_tipdoc"), rstLoc01.getInt("co_doc"));

                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_pagmovinv";
                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven, co_tipret, nd_porret";
                        strSQL+=" , tx_aplret, mo_pag, ne_diagra, nd_abo";
                        strSQL+=" , st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq";
                        strSQL+=" , nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree";
                        strSQL+=" , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp";
                        strSQL+=" ) ";
                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg2 + " , ne_diacre, fe_ven, co_tipret, nd_porret";
                        strSQL+="      , tx_aplret, "+bgdDifPorRet.abs()+" , ne_diagra, " + rstLoc01.getDouble("abodif2")+" as nd_abo" ;
                        strSQL+="      , st_sop, 'N', 'N', null, null, null, null, null";
                        strSQL+="      , 0, co_prochq, 'C', 'M', fe_ree, co_usrree ";
                        strSQL+="      , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, " + bgdDifBasImp.abs();
                        strSQL+=" FROM tbm_pagmovinv ";
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp");
                        strSQL+=" AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc");
                        strSQL+=" AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg");
                        strSQL+=" ;";
                        System.out.println("2 --> "+strSQL );
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;
                        
                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" UPDATE tbm_pagmovinv";
                        strSQL+=" SET nd_abo=0, st_reg= CASE WHEN st_reg IN ('A') THEN 'F' ELSE CASE WHEN st_reg IN ('C') THEN 'I' END END, st_regrep='M' ";
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + " AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg") ;
                        strSQL+=" ;";
                        System.out.println("3 --> "+strSQL );
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;                        

                        /* SI HAY PAGO REALIZADO GENERE REGISTRO DE ESE PAGO Y CAMBIA DETPAG AL REGISTRO NUEVO*/
                        if (rstLoc01.getDouble("abonue1") != 0) 
                        {
                            stmLoc02 = conn.createStatement();
                            BigDecimal bgdSubAbo1 = rstLoc01.getBigDecimal("abonue1").abs();
                            strSQL ="";
                            strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg";
                            strSQL+="      , co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo ,abs(nd_abo) as abo  ";
                            strSQL+=" FROM tbm_detpag ";
                            strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_locpag=" + rstLoc01.getInt("co_loc");
                            strSQL+=" AND co_tipdocpag=" + rstLoc01.getInt("co_tipdoc") + " AND co_docpag=" + rstLoc01.getInt("co_doc") ;
                            strSQL+=" AND co_regpag=" + rstLoc01.getInt("co_reg");
                            rstLoc02 = stmLoc02.executeQuery(strSQL);
                            while (rstLoc02.next()) 
                            {
                                if(bgdSubAbo1.compareTo(BigDecimal.ZERO)!=0) {
                                    if (bgdSubAbo1 == rstLoc02.getBigDecimal("abo")) {
                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" UPDATE tbm_detpag SET co_regpag=" + intCodReg ;
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc");
                                        strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg");
                                        System.out.println("***1-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;
                                        bgdSubAbo1 = BigDecimal.ZERO;
                                    }
                                    else if (bgdSubAbo1.compareTo(rstLoc02.getBigDecimal("abo")) >0 ) 
                                    {
                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" UPDATE tbm_detpag SET co_regpag=" + intCodReg ;
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + "  AND co_doc=" + rstLoc02.getInt("co_doc");
                                        strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg") ;
                                        System.out.println("***2-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;                                        
                                        bgdSubAbo1=bgdSubAbo1.subtract(rstLoc02.getBigDecimal("abo"));
                                    }
                                    else if (bgdSubAbo1.compareTo(rstLoc02.getBigDecimal("abo")) <0 ) 
                                    {
                                        intCodRegPag = getMaxCodRegDetPag(conn, rstLoc02.getInt("co_emp"), rstLoc02.getInt("co_loc"), rstLoc02.getInt("co_tipdoc"), rstLoc02.getInt("co_doc"));
                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" INSERT INTO tbm_detpag";
                                        strSQL+=" (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag, co_regpag";
                                        strSQL+=" , nd_abo, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq";
                                        strSQL+=" , fe_recchq, fe_venchq, st_regrep, tx_codsri";
                                        strSQL+=" , st_reg, co_locreldepcliregdirban, co_tipdocreldepcliregdirban, co_docreldepcliregdirban, co_regreldepcliregdirban";
                                        strSQL+=" )";
                                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodRegPag + ", co_locpag, co_tipdocpag, co_docpag, " + intCodReg2;
                                        strSQL+="       , " + (rstLoc02.getBigDecimal("nd_abo").add(bgdSubAbo1)) + ", co_tipdoccon, co_banchq, tx_numctachq, tx_numchq";
                                        strSQL+="       , fe_recchq, fe_venchq, st_regrep, tx_codsri";
                                        strSQL+="       , st_reg, co_locreldepcliregdirban, co_tipdocreldepcliregdirban, co_docreldepcliregdirban, co_regreldepcliregdirban ";
                                        strSQL+=" FROM tbm_detpag";
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc");
                                        strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg") ;
                                        System.out.println("***3-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;

                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" UPDATE tbm_detpag SET co_regpag=" + intCodReg + ", nd_abo= " + bgdSubAbo1.multiply(new BigDecimal("-1")) + " ";
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc");
                                        strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg");
                                        System.out.println("***4-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;                                        
                                        bgdSubAbo1=bgdSubAbo1.subtract(bgdSubAbo1);
                                    }
                                } 
                                else {
                                    stmLoc03 = conn.createStatement();
                                    strSQL ="";
                                    strSQL+=" UPDATE tbm_detpag SET co_regpag=" + intCodReg2 ;
                                    strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                    strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc");
                                    strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg");
                                    System.out.println("***5-> "+ strSQL );
                                    stmLoc03.executeUpdate(strSQL);
                                    stmLoc03.close();
                                    stmLoc03=null;                                               
                                }
                            }
                            rstLoc02.close();
                            rstLoc02 = null;
                            stmLoc02.close();
                            stmLoc02=null;
                        }
                    }
                    else {
                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" UPDATE tbm_pagmovinv";
                        strSQL+=" SET st_entSop='S', tx_numChq='" + strNumFac+"'";
                        strSQL+="   , fe_recChq='" + objUti.formatearFecha(tblDat.getValueAt(numFil, INT_TBL_FECFAC).toString(), "dd/MM/yyyy", "yyyy/MM/dd") + "' ";
                        strSQL+="   , fe_venChq='" + fechaDoc  + "', nd_valiva=" + valIvaFacPrv+", nd_monChq=" + valFacPrv ;
                        strSQL+="   , tx_numSer='" + strNumSer + "', tx_numAutSRI='" + strNumAut + "', tx_fecCad='" + strFecCad + "'"; 
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + "  AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg");
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;
                    }
                }
                rstLoc01.close();
                rstLoc01 = null;
                stmLoc01.close();
                stmLoc01 = null;                
                
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="/* Reestructuración 2 */">
                stmLoc01 = conn.createStatement();
                
                int x = 1; int intNumPag = 0; 
                intCodRegPag =-1; intCodReg2 = -1;
                String strNumFacPag = "", strferecchq = "", strFecDocRes = "";
                bgdPorRetTot=objUti.redondearBigDecimal(bgdPorRetTot, 3);
                BigDecimal bgdValPag = objUti.redondearBigDecimal(valFacPrvApl.subtract(bgdPorRetTot), 3);                      
                BigDecimal bgdSubPago = BigDecimal.ZERO;
                BigDecimal bgdSubPagoDif = BigDecimal.ZERO;
                BigDecimal bgdTotSubPago = BigDecimal.ZERO;
                BigDecimal bgdTotSubPagodif = BigDecimal.ZERO;
                BigDecimal bgdTotPag=BigDecimal.ZERO;
                BigDecimal bgdValFacPrvRes=BigDecimal.ZERO;
                BigDecimal bgdValIvaFacPrvRes=BigDecimal.ZERO;
                BigDecimal bgdTotPagdif=BigDecimal.ZERO;
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT * ";
                strSQL+="      , CASE WHEN nd_abo != 0 THEN case when abs(nd_abo) <= subpago1 then nd_abo else  (nd_abo + abs(nd_abo+subpago1)) end  ELSE 0 END AS abosub1 ";
                strSQL+="      , CASE WHEN nd_abo != 0 THEN case when abs(nd_abo) <= subpago1 then   0   else  (nd_abo+subpago1)  end ELSE 0 END AS abosub2 ";
                strSQL+=" FROM (  select *, round((pago1/cantreg),2) as subpago1, round((pago2/cantreg),2) as subpago2 ";
                strSQL+="         from ( ";
                strSQL+="               select co_emp, co_loc, co_tipdoc, co_doc, co_reg, nd_abo, ne_diacre ";
                strSQL+="                     ,case when st_reg in ('A') then 'F' else case when st_reg in ('C') then 'I' end end as estreg ";
                strSQL+="                     ,( select count(x.co_reg) from tbm_pagmovinv as x ";
                strSQL+="                        where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc ";
                strSQL+="                        and x.tx_numchq is null and x.st_reg in ('A','C') and x.nd_porret = 0  ";
                strSQL+="                       )  as cantreg ";
                strSQL+="                     , " + bgdValPag + " as pago1 ";
                strSQL+="                     ,( select (SUM(mo_pag)-" + (  bgdValPag.add(bgdDifPorRetTot) ) + ") as pago2 from tbm_pagmovinv as x";
                strSQL+="                        where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc ";
                strSQL+="                        and x.tx_numchq is null and x.st_reg in ('A','C')";
                strSQL+="                       )  as pago2 ";
                strSQL+="               from tbm_pagmovinv as a ";
                strSQL+="               where a.co_emp=" + codEmp + " and a.co_loc=" + codLoc + " and a.co_tipdoc=" + codTipDoc + " and a.co_doc= " + codDoc;
                strSQL+="               and  a.tx_numchq is null and a.st_reg in ('A','C') and a.nd_porret = 0 "; 
                strSQL+="         ) as x ";
                strSQL+=" ) AS x  ";
                System.out.println("reestructurandoFactura**2--> "+strSQL );
                rstLoc01 = stmLoc01.executeQuery(strSQL);
                while (rstLoc01.next()) 
                {
                    if (rstLoc01.getDouble("pago2") > 0)
                    {
                        intNumPag = rstLoc01.getInt("cantreg");
                        intCodRegPag = -1;  intCodRegPag =-1;  intCodReg2 = -1;
                        bgdSubPago = rstLoc01.getBigDecimal("subpago1");
                        bgdTotPag = rstLoc01.getBigDecimal("pago1");
                        strNumFacPag = strNumFac;
                        strferecchq = objUti.formatearFecha(tblDat.getValueAt(numFil, INT_TBL_FECFAC).toString(), "dd/MM/yyyy", "yyyy/MM/dd");
                        strFecDocRes = fechaDoc;
                        bgdValFacPrvRes = valFacPrv;
                        bgdValIvaFacPrvRes = valIvaFacPrv;
                        bgdTotSubPago=bgdTotSubPago.add(bgdSubPago);

                        if (intNumPag == x) 
                        {
                            if (bgdTotPag.compareTo(bgdTotSubPago)==0) {
                                //System.out.println("Igual---------> ");
                            }
                            else if (bgdTotPag.compareTo(bgdTotSubPago) <0) {
                                //System.out.println("Menor---------> ");
                                bgdSubPago=bgdSubPago.add(bgdTotPag.subtract(bgdTotSubPago));
                            }
                            else if (bgdTotPag.compareTo(bgdTotSubPago) >0) {
                                // System.out.println("Mayor---------> ");
                                bgdSubPago=bgdSubPago.subtract(bgdTotSubPago.subtract(bgdTotPag));
                            }
                        }

                        intCodReg = getMaxCodRegPagMov(conn, rstLoc01.getInt("co_emp"), rstLoc01.getInt("co_loc"), rstLoc01.getInt("co_tipdoc"), rstLoc01.getInt("co_doc"));
                        
                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_pagmovinv";
                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven";
                        strSQL+="  , co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo";
                        strSQL+="  , st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq";
                        strSQL+="  , fe_recchq, fe_venchq, nd_valiva, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree ";
                        strSQL+="  , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp , tx_numSer, tx_numAutSRI, tx_fecCad ";
                        strSQL+=" ) ";
                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg + ", ne_diacre, fe_ven";
                        strSQL+="      , co_tipret, nd_porret, tx_aplret, " + bgdSubPago.abs() + ", ne_diagra, " + rstLoc01.getDouble("abosub1") + " as nd_abo";
                        strSQL+="      , st_sop, 'N', 'N', null, null, " + (strNumFacPag.equals("") ? null : "'" + strNumFacPag + "'") ;
                        strSQL+="      , " + (strferecchq.equals("") ? null : "'" + strferecchq + "'") + ", " + (strFecDocRes.equals("") ? null : "'" + strFecDocRes + "'") + "";
                        strSQL+="      , " + bgdValIvaFacPrvRes + ", " + bgdValFacPrvRes + ", co_prochq, 'C', 'M', fe_ree, co_usrree";
                        strSQL+="      , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, 0, '" + strNumSer + "', '" + strNumAut + "', '" + strFecCad + "' ";
                        strSQL+=" FROM tbm_pagmovinv ";
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + " AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg") ;
                        strSQL+=" ;";
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;

                        bgdSubPagoDif = rstLoc01.getBigDecimal("subpago2");
                        bgdTotPagdif = rstLoc01.getBigDecimal("pago2");
                        strNumFacPag = "";
                        strferecchq = "";
                        strFecDocRes = "";
                        bgdValFacPrvRes = BigDecimal.ZERO;
                        bgdValIvaFacPrvRes = BigDecimal.ZERO;
                        bgdTotSubPagodif=bgdTotSubPagodif.add(bgdSubPagoDif);

                        if (intNumPag == x)
                        {
                            if (bgdTotPagdif.compareTo(bgdTotSubPagodif) ==0) {
                                //System.out.println("Igual---------> ");
                            } 
                            else if (bgdTotPagdif.compareTo(bgdTotSubPagodif) < 0) {
                                //System.out.println("Menor---------> ");
                                bgdSubPagoDif=bgdSubPagoDif.add(bgdTotPagdif.subtract(bgdTotSubPagodif));
                            }
                            else if (bgdTotPagdif.compareTo(bgdTotSubPagodif) > 0) {
                                //System.out.println("Mayor---------> ");
                                bgdSubPagoDif=bgdSubPagoDif.subtract(bgdTotSubPagodif.subtract(bgdTotPagdif));
                            }
                        }

                        intCodReg2 = getMaxCodRegPagMov(conn, rstLoc01.getInt("co_emp"), rstLoc01.getInt("co_loc"), rstLoc01.getInt("co_tipdoc"), rstLoc01.getInt("co_doc"));
                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_pagmovinv";
                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg , ne_diacre, fe_ven ";
                        strSQL+="  , co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo";
                        strSQL+="  , st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq ";
                        strSQL+="  , fe_recchq, fe_venchq, nd_valiva, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree ";
                        strSQL+="  , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp , tx_numSer, tx_numAutSRI, tx_fecCad ";
                        strSQL+=" ) ";
                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodReg2 + " , ne_diacre, fe_ven";
                        strSQL+="      , co_tipret, nd_porret, tx_aplret, " + bgdSubPagoDif.abs() + ", ne_diagra, " + rstLoc01.getDouble("abosub2") + " as nd_abo";
                        strSQL+="      , st_sop, 'N', 'N', null, null, " + (strNumFacPag.equals("") ? null : "'" + strNumFacPag + "'") + "";
                        strSQL+="      , " + (strferecchq.equals("") ? null : "'" + strferecchq + "'") +", " + (strFecDocRes.equals("") ? null : "'" + strFecDocRes + "'") + "";
                        strSQL+="      , " + bgdValIvaFacPrvRes + ", " + bgdValFacPrvRes + ", co_prochq, 'C', 'M', fe_ree, co_usrree";
                        strSQL+="      , tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, 0, '" + strNumSer + "', '" + strNumAut + "', '" + strFecCad + "' ";
                        strSQL+=" FROM tbm_pagmovinv ";
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + " AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg");
                        strSQL+=" ; ";
                        strSQL+=" UPDATE tbm_pagmovinv SET st_reg = '" + rstLoc01.getString("estreg") + "' , nd_abo=0 ";
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + " AND co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg");
                        strSQL+=" ;";
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;

                        if (rstLoc01.getBigDecimal("abosub1").compareTo(BigDecimal.ZERO) != 0) 
                        {
                            BigDecimal bgdSubAbo1=rstLoc01.getBigDecimal("abosub1").abs();
                            stmLoc02 = conn.createStatement();
                            strSQL ="";
                            strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag, co_regpag, nd_abo ,abs(nd_abo) as abo  ";
                            strSQL+=" FROM tbm_detpag WHERE co_emp=" + rstLoc01.getInt("co_emp") + " AND co_locpag=" + rstLoc01.getInt("co_loc");
                            strSQL+=" AND co_tipdocpag=" + rstLoc01.getInt("co_tipdoc") + " AND co_docpag=" + rstLoc01.getInt("co_doc");
                            strSQL+=" AND co_regpag=" + rstLoc01.getInt("co_reg");
                            System.out.println("******: "+strSQL);
                            rstLoc02 = stmLoc02.executeQuery(strSQL); 
                            while (rstLoc02.next()) 
                            {
                                if(bgdSubAbo1.compareTo(BigDecimal.ZERO)!=0)
                                {
                                    if (bgdSubAbo1.compareTo(rstLoc02.getBigDecimal("abo")) ==0 )
                                    {
                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" UPDATE tbm_detpag SET co_regpag=" + intCodReg ;
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc") ;
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc");
                                        strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg");
                                        strSQL+=" ;";
                                        System.out.println("1-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;
                                        bgdSubAbo1 = BigDecimal.ZERO;
                                    }
                                    else if (bgdSubAbo1.compareTo(rstLoc02.getBigDecimal("abo")) > 0) 
                                    {
                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" UPDATE tbm_detpag SET co_regpag=" + intCodReg;
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + "  AND co_doc=" + rstLoc02.getInt("co_doc");
                                        strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg");
                                        strSQL+=" ;";
                                        System.out.println("2-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;                                        
                                        bgdSubAbo1=bgdSubAbo1.subtract(rstLoc02.getBigDecimal("abo"));
                                    }
                                    else if (bgdSubAbo1.compareTo(rstLoc02.getBigDecimal("abo")) < 0) 
                                    {
                                        intCodRegPag = getMaxCodRegDetPag(conn, rstLoc02.getInt("co_emp"), rstLoc02.getInt("co_loc"), rstLoc02.getInt("co_tipdoc"), rstLoc02.getInt("co_doc"));
                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" INSERT INTO tbm_detpag";
                                        strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag, co_regpag";
                                        strSQL+=" , nd_abo, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq";
                                        strSQL+=" , fe_recchq, fe_venchq, st_regrep, tx_codsri, st_reg";
                                        strSQL+=" , co_locreldepcliregdirban, co_tipdocreldepcliregdirban, co_docreldepcliregdirban, co_regreldepcliregdirban";
                                        strSQL+=" ) ";
                                        strSQL+=" SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intCodRegPag + ", co_locpag, co_tipdocpag, co_docpag, " + intCodReg2;
                                        strSQL+="      , " + (rstLoc02.getBigDecimal("nd_abo").add(bgdSubAbo1)) + ", co_tipdoccon, co_banchq, tx_numctachq, tx_numchq";
                                        strSQL+="      , fe_recchq, fe_venchq, st_regrep, tx_codsri, st_reg";
                                        strSQL+="      , co_locreldepcliregdirban, co_tipdocreldepcliregdirban, co_docreldepcliregdirban, co_regreldepcliregdirban ";
                                        strSQL+=" FROM tbm_detpag where co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc") ;
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc") + " AND co_reg=" + rstLoc02.getInt("co_reg");
                                        strSQL+=" ;";
                                        System.out.println("3-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;                                               

                                        stmLoc03 = conn.createStatement();
                                        strSQL ="";
                                        strSQL+=" UPDATE tbm_detpag set co_regpag=" + intCodReg + ", nd_abo= " + bgdSubAbo1.multiply(new BigDecimal("-1")) ;
                                        strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                        strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc") + " AND co_reg=" + rstLoc02.getInt("co_reg");
                                        strSQL+=" ;";
                                        System.out.println("4-> "+ strSQL );
                                        stmLoc03.executeUpdate(strSQL);
                                        stmLoc03.close();
                                        stmLoc03=null;   
                                        bgdSubAbo1=bgdSubAbo1.subtract(bgdSubAbo1);
                                    }
                                }
                                else {
                                    stmLoc03 = conn.createStatement();
                                    strSQL ="";
                                    strSQL+=" UPDATE tbm_detpag set co_regpag=" + intCodReg2;
                                    strSQL+=" WHERE co_emp=" + rstLoc02.getInt("co_emp") + " AND co_loc=" + rstLoc02.getInt("co_loc");
                                    strSQL+=" AND co_tipdoc=" + rstLoc02.getInt("co_tipdoc") + " AND co_doc=" + rstLoc02.getInt("co_doc");
                                    strSQL+=" AND co_reg=" + rstLoc02.getInt("co_reg");
                                    System.out.println("5-> "+ strSQL );
                                    stmLoc03.executeUpdate(strSQL);
                                    stmLoc03.close();
                                    stmLoc03=null;   
                                }
                            }
                            rstLoc02.close();
                            rstLoc02 = null;
                            stmLoc02.close();
                            stmLoc02=null;                            
                        }

                        if (rstLoc01.getDouble("abosub2") != 0) {
                            if (intCodRegPag > 0) {
                            }
                        }
                        x++;
                    } 
                    else 
                    {
                        stmLoc02 = conn.createStatement();
                        strSQL ="";
                        strSQL+=" UPDATE tbm_pagmovinv ";
                        strSQL+=" SET tx_numChq='" + strNumFac + "',fe_recChq='" + objUti.formatearFecha(tblDat.getValueAt(numFil, INT_TBL_FECFAC).toString(), "dd/MM/yyyy", "yyyy/MM/dd") + "' ";
                        strSQL+="   , fe_venChq='" + fechaDoc + "' ,nd_valiva=" + valIvaFacPrv + " ,nd_monChq=" + valFacPrv;
                        strSQL+="   , tx_numSer='" + strNumSer + "',tx_numAutSRI='" + strNumAut + "',tx_fecCad='" + strFecCad + "' "; 
                        strSQL+=" WHERE co_emp=" + rstLoc01.getInt("co_emp") + " and co_loc=" + rstLoc01.getInt("co_loc");
                        strSQL+=" AND co_tipdoc=" + rstLoc01.getInt("co_tipdoc") + "  and co_doc=" + rstLoc01.getInt("co_doc");
                        strSQL+=" AND co_reg=" + rstLoc01.getInt("co_reg");
                        stmLoc02.executeUpdate(strSQL);
                        stmLoc02.close();
                        stmLoc02=null;
                    }
                }
                rstLoc01.close();
                rstLoc01 = null;
                stmLoc01.close();
                stmLoc01 = null;     
                //</editor-fold>
                
                blnRes = true;

                if (!addChangeCtaCon(conn, codEmp, codLoc, codTipDoc, codDoc, valFacPrvApl)) {
                    blnRes = false;
                }
            }
        }
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    /**
     * Obtiene el maximo registro de la tabla tbm_pagmovinv + 1
     * @param conn
     * @param codEmp
     * @param codLoc
     * @param codTipDoc
     * @param codDoc
     * @return -1 si no se hay algun error.
     * <BR>caso contrario retorna el valor correcto
     */
    private int getMaxCodRegPagMov(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc) 
    {
        int intCodReg = -1;
        ResultSet rstLoc;
        Statement stmLoc;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT CASE WHEN MAX(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS co_reg";
                strSQL+=" FROM tbm_pagmovinv ";
                strSQL+=" WHERE co_emp=" + codEmp + " AND co_loc=" + codLoc;
                strSQL+=" AND co_tipdoc=" + codTipDoc + " AND co_doc= " + codDoc;
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodReg = rstLoc.getInt("co_reg");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return intCodReg;
    }

    /**
     * Obtiene el maximo registro de la tabla tbm_detpag + 1
     * @param conn
     * @param codEmp
     * @param codLoc
     * @param codTipDoc
     * @param codDoc
     * @return -1 si no se hay algun error
     * <BR> Caso contrario retorna el valor correcto
     */
    private int getMaxCodRegDetPag(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc) {
        int intCodReg = -1;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS co_reg";
                strSQL+=" FROM tbm_detpag ";
                strSQL+=" WHERE co_emp=" + codEmp + " AND co_loc=" + codLoc;
                strSQL+=" AND co_tipdoc=" + codTipDoc + " AND co_doc= " + codDoc + " ";
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodReg = rstLoc.getInt("co_reg");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return intCodReg;
    }
    
    private boolean addChangeCtaCon(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc, BigDecimal valAsi) {
        boolean blnRes = false;
        try {
            if (conn != null) {
                Librerias.ZafCtaTrans.ZafCtaTrans objCtaTrans = new Librerias.ZafCtaTrans.ZafCtaTrans(ifrFrm, objParSis);
                if (objCtaTrans.cambioCtaConIvaOC(conn, codEmp, codLoc, codTipDoc, codDoc, valAsi.doubleValue() )) {
                    if (objCtaTrans.cambioCtaConProOC(conn, codEmp, codLoc, codTipDoc, codDoc, valAsi.doubleValue())) {
                        if (objCtaTrans.agrecambioCtaConDevCom(conn, codEmp, codLoc, codTipDoc, codDoc)) {
                            blnRes = true;
                        }
                    }
                }
                objCtaTrans = null;
            }
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean deleteChangeCtaCon(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc, int codReg) 
    {
        boolean blnRes = true;
        Statement stmLoc;
        ResultSet rstLoc;
        try 
        {
            if (conn != null)
            {
                stmLoc = conn.createStatement();
                Librerias.ZafCtaTrans.ZafCtaTrans objCtaTrans = new Librerias.ZafCtaTrans.ZafCtaTrans(ifrFrm, objParSis);
                strSQL ="";
                strSQL+=" SELECT co_emprel, co_locrel, co_tipdocrel, co_docrel, nd_valasi";
                strSQL+=" FROM tbr_detrecdoccabmovinv ";
                strSQL+=" WHERE co_emp=" + codEmp + " AND co_loc=" + codLoc;
                strSQL+=" AND co_tipdoc=" + codTipDoc + " AND co_doc = " + codDoc;
                strSQL+=" AND co_reg= " + codReg + " AND st_reg='A' ";
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    if (objCtaTrans.quitarCtaConIvaFinOC(conn, rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel"), rstLoc.getDouble("nd_valasi"))) {
                        if (objCtaTrans.quitarCtaConProOC(conn, rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel"), rstLoc.getDouble("nd_valasi"))) {
                            if (objCtaTrans.quitarCtaConDevCom(conn, rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getInt("co_tipdocrel"), rstLoc.getInt("co_docrel"), rstLoc.getDouble("nd_valasi"))) {
                                blnRes = true;
                            } 
                            else {
                                blnRes = false;
                                break;
                            }
                        }
                        else {
                            blnRes = false;
                            break;
                        }
                    }
                    else {
                        blnRes = false;
                        break;
                    }
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                objCtaTrans = null;
            }
        } 
        catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    
    /**
     * Función que anula el registro seleccionado
     * @return 
     */
    private boolean anularReg() 
    {
        boolean blnRes = false;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" UPDATE tbm_cabrecdoc SET st_reg='I', st_regrep='M' ";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ;";

                strSQL+=" UPDATE tbm_detrecdoc SET st_reg='I', st_regrep='M' ";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                //strSQL+=" AND st_Reg NOT IN ('E')"; //Se agrega validación para que solo ponga st_reg IN ('I')
                                                      //, cuando son registros que han estado activos, para los eliminados seguirán en st_reg IN ('E')
                strSQL+=" ;";

                strSQL+=" UPDATE tbr_detrecdoccabmovinv SET st_reg='I', st_regrep='M' ";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ;";

                strSQL+=" UPDATE tbm_pagmovinv ";
                strSQL+=" SET nd_monchq=0, fe_venchq=null, fe_recchq=null, tx_numchq=null, co_banchq=null ";
                strSQL+="   , tx_numctachq=null, st_pos='N', st_entsop='N', st_regrep='M' ";
                strSQL+=" FROM ( ";
                strSQL+="        SELECT  a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a1.tx_numchq";
                strSQL+="        FROM tbr_detrecdoccabmovinv AS a";
                strSQL+="        INNER JOIN tbm_detrecdoc AS a1";
                strSQL+="        ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc AND a1.co_reg=a.co_reg ) ";
                strSQL+="        WHERE a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+="        AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+="        AND a.co_tipdoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+="        AND a.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ) AS x ";
                strSQL+=" WHERE tbm_pagmovinv.co_emp=x.co_emprel AND tbm_pagmovinv.co_loc=x.co_locrel AND tbm_pagmovinv.co_tipdoc=x.co_tipdocrel ";
                strSQL+=" AND tbm_pagmovinv.co_doc=x.co_docrel AND tbm_pagmovinv.tx_numchq=x.tx_numchq";
                strSQL+=" ; ";
                stm.executeUpdate(strSQL);
                blnRes = true;
                stm.close();
                stm = null;
                con.close();
                con=null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

   /**
   * Esta función se encarga de validar si una fecha es valida o no.
   * Retorna true si la fecha es valida.
   * Retorna false si la fecha no es valida.
   */
    private boolean isFechaValida(String strFecha)
    {  
        String strFormatoFecha = "dd/MM/yyyy";
        try
        {
           DateFormat df = new SimpleDateFormat(strFormatoFecha);
           df.setLenient(false);
           df.parse(strFecha);
           return true;
        } 
        catch (Exception e)
        {
           return false;
        }
    }
   
    private int _getObtenerMaxCodRegDet(java.sql.Connection conn) 
    {
        int intCodReg = -1;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSqlCodRegMax = "";
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSqlCodRegMax =" SELECT CASE WHEN (MAX(co_reg)+1) IS NULL THEN 1 ELSE (MAX(co_reg)+1) END AS codigoRegistro"
                                +" FROM tbm_detrecdoc "
                                +" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " "
                                +" AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc= " + txtCodDoc.getText() + " ";
                rstLoc = stmLoc.executeQuery(strSqlCodRegMax);
                if (rstLoc.next()) {
                    intCodReg = rstLoc.getInt("codigoRegistro");
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return intCodReg;
    }
   
   
   
   
}