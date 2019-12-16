
package Compras.ZafCom43;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  
import java.util.ArrayList;  
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;  
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import java.math.BigDecimal;

/**
 * 
 * @author jayapata
 */
public class ZafCom43 extends javax.swing.JInternalFrame
{
    //Constantes: JTable TblDat
    static final int INT_TBL_LINEA  = 0 ; 
    static final int INT_TBL_CODITM = 1; 
    static final int INT_TBL_ITMALT = 2;
    static final int INT_TBL_BUTITM = 3;
    static final int INT_TBL_DESITM = 4;
    static final int INT_TBL_UNIMED = 5;
    static final int INT_TBL_CODBOD = 6;
    static final int INT_TBL_BUTBOD = 7;
    static final int INT_TBL_NOMBOD = 8;
    static final int INT_TBL_CTABOD = 9;
    static final int INT_TBL_VALOR  = 10;            //Cantidad del movimiento (venta o compra)
    
    //Constantes: Asiento de Diario.
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceCon=0;    
       
    //ArrayList: Cuenta de Bodega
    private ArrayList arlDatCtaBod, arlRegCtaBod;
    private int INT_ARL_CTA_COD_CTA=0;
    private int INT_ARL_CTA_NUM_CTA=1;
    private int INT_ARL_CTA_NOM_CTA=2;
    
     //ArrayList para Agrupar datos de Bodegas
    private ArrayList arlDatBod, arlRegBod;
    private int INT_ARL_BOD_COD_CTA=0;
    private int INT_ARL_BOD_VALOR=1;
    
    //Variables
    private Connection con;  //Coneccion a la base donde se encuentra la Documento
    private Statement stm;   //Statement para la Documento 
    private ResultSet rst;   //Resultset que tendra los datos de la cabecera de la Documento
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    private ZafSegMovInv objSegMovInv;
    private ZafTblCelEdiButVco objTblCelEdiButVcoBod;  //Editor: JButton en celda (Bodega).
    private UltDocPrint objUltDocPrint;                //Para trabajar con la informacion de tipo de documento
    private ZafVenCon vcoItm; 
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoBod;
      
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.
    private ZafDocLis objDocLis;                    //Instancia de clase que detecta cambios
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm; //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBod; //Editor: JTextField de consulta en celda.
    private ZafDatePicker dtpFecDoc;
    private java.util.Date datFecAux;
    
    private MiToolBar objTooBar;
    private Vector vecDat,  vecCab,  vecReg;
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    
    private BigDecimal bgdSig=new BigDecimal("0");
    private int intCodCtaDeb=0;
    private int intCodCtaHab=0;
    private Object objCodSeg;
    
    private String strSQL, strAux;//EL filtro de la Consulta actual
    private String strBeforeValue, strAfterValue;
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strNatTipDoc="";
    private String strNumCtaDeb="", strNomCtaDeb="";
    private String strNumCtaHab="", strNomCtaHab="";
    
    private String strVersion = " v0.7";
    
        
    /** Crea una nueva instancia de la clase ZafCom43. */
    public ZafCom43(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()){}
                exitForm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
        
    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            //Título de la ventana.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objUltDocPrint = new UltDocPrint(objParSis);  
            objAsiDia = new ZafAsiDia(objParSis);
            objSegMovInv=new ZafSegMovInv(objParSis, this);
                        
            //Fecha del documento.
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setBounds(582, 4, 100, 20);
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
  
            //Campos 
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodTipDoc.setVisible(false); 
            
            //ToolBar
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            
            //***********************************************************************
            //Asiento de diario
            vecDatDia = new Vector();
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
               public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                  if (txtCodTipDoc.getText().equals(""))
                      objAsiDia.setCodigoTipoDocumento(-1);
                  else
                      objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
             });
            
            objAsiDia.setEditable(true);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //***********************************************************************
            pack();

            //Metodo para agregar o eliminar lineas con enter y con escape
            agregarDocLis();
            objUti.desactivarCom(this);
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConItm();
            configurarVenConBod();
            
            //Configurar los JTables.
            configurarTblDat();   
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");  
            vecCab.add(INT_TBL_CODITM,""); 
            vecCab.add(INT_TBL_ITMALT,"Cod.Itm.");
            vecCab.add(INT_TBL_BUTITM,"");
            vecCab.add(INT_TBL_DESITM,"Nombre del ítem");            
            vecCab.add(INT_TBL_UNIMED,"Uni.Med.");
            vecCab.add(INT_TBL_CODBOD,"Cód.Bod.");            
            vecCab.add(INT_TBL_BUTBOD,"");
            vecCab.add(INT_TBL_NOMBOD,"Bodega");
            vecCab.add(INT_TBL_CTABOD,"Cta.Bod.");
            vecCab.add(INT_TBL_VALOR, "Valor");
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_VALOR, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de bósqueda.
            objTblBus = new ZafTblBus(tblDat);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_ITMALT);
            vecAux.add("" + INT_TBL_BUTITM);
            vecAux.add("" + INT_TBL_CODBOD);
            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_VALOR);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_VALOR);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);         
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(265);
            tcmAux.getColumn(INT_TBL_UNIMED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(35);         
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(20);         
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(120);      
            tcmAux.getColumn(INT_TBL_CTABOD).setPreferredWidth(20);   
            tcmAux.getColumn(INT_TBL_VALOR).setPreferredWidth(80); 
                      
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CTABOD, tblDat);
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIMED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VALOR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_CODBOD;
            intColTbl[1]=INT_TBL_NOMBOD;
            intColTbl[2]=INT_TBL_CTABOD;

            objTblCelEdiTxtVcoBod=new ZafTblCelEdiTxtVco(tblDat, vcoBod, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellEditor(objTblCelEdiTxtVcoBod);
            objTblCelEdiTxtVcoBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBod.setCampoBusqueda(0);
                    vcoBod.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTot();
                    generarAsiDia();
                }
            });

            objTblCelEdiButVcoBod=new ZafTblCelEdiButVco(tblDat, vcoBod, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellEditor(objTblCelEdiButVcoBod);
            objTblCelEdiButVcoBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
               public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTot();
                    generarAsiDia();
                }
            });
            intColVen=null;
            intColTbl=null;
            
            ZafTblCelEdiTxt objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_VALOR).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
             public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
             }                
             public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 calcularTot();
                 generarAsiDia();
             }
            });
            objTblCelEdiTxt=null;
            
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            intColVen=new int[4];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=7; 
            
            intColTbl=new int[4];
            intColTbl[0]=INT_TBL_ITMALT;
            intColTbl[1]=INT_TBL_CODITM;
            intColTbl[2]=INT_TBL_DESITM;
            intColTbl[3]=INT_TBL_UNIMED;
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else
                        strAfterValue ="";
                }
            });
            
            new ButFndItm(tblDat, INT_TBL_BUTITM);   
            
            intColVen=null;
            intColTbl=null;
         
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux = null;
            
            setEditable(false);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {                   
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_ITMALT:
                    strMsg="Codigo del Item";
                    break;
                case INT_TBL_DESITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_CODBOD:
                    strMsg="Codigo Bodega";
                    break;
                case INT_TBL_NOMBOD:
                    strMsg="Nombre de la bodega";
                    break;
                case INT_TBL_CTABOD:
                    strMsg="Nombre de la bodega";
                    break;                                
                case INT_TBL_VALOR:
                    strMsg="Valor";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    } 
     
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("325");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT distinct a.co_tipdoc, a.tx_descor, a.tx_deslar, a.ne_ultDoc, a.tx_natDoc ";
                strSQL+=" FROM tbr_tipDocPrg AS a1 LEFT OUTER JOIN tbm_cabTipDoc as a";
                strSQL+=" ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc)" ;
                strSQL+=" WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() ;
                strSQL+=" AND a1.co_loc = " + objParSis.getCodigoLocal() ;
                strSQL+=" AND a1.co_mnu = " + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a.tx_desCor";
            }
            else 
            {     
                strSQL ="";
                strSQL+=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a.ne_ultDoc, a.tx_natDoc ";
                strSQL+=" FROM tbr_tipDocUsr AS a1 INNER JOIN  tbm_cabTipDoc AS a";
                strSQL+=" ON (a.co_emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a.tx_desCor";
            }
           
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=4;
            intColOcu[1]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_CosUni");
            arlCam.add("a7.st_ivaCom");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.tx_codAlt2");
            arlCam.add("a7.st_ser");
            arlCam.add("a7.proConf");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cód.Itm.");
            arlAli.add("Nombre del ítem");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Iva");
            arlAli.add("Uni.Med.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Itm.Ser."); 
            arlAli.add("IE.Fis.Bod."); 
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("210");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("35");
            arlAncCol.add("40");
            arlAncCol.add("64");
            arlAncCol.add("20");
            arlAncCol.add("20");
               
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a.tx_codalt, a.co_itm, a.tx_nomitm, a2.nd_stkact, a.nd_cosuni, a.st_ivacom, a1.tx_descor, a.tx_codalt as tx_codalt2, a.st_ser  ";
            strSQL+="      , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( ";
            strSQL+="                     select upper(trim(a1.tx_cad)) from tbr_bodLoc as a " ;
            strSQL+="                     inner join tbm_regInvMerNunIngEgrFisBod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " ;
            strSQL+="                     where a1.st_reg='A' and a.st_reg='P' ";
            strSQL+="                     and a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+"  ))";
            strSQL+="        ) THEN 'S' ELSE 'N' END AS proConf " ;
            strSQL+=" FROM tbm_inv AS a " ;
            strSQL+=" LEFT JOIN tbm_var AS a1 ON(a1.co_reg=a.co_uni and a1.co_grp=5 ) " ;
            strSQL+=" INNER JOIN tbm_invbod AS a2";
            strSQL+=" ON(a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm AND a2.co_bod = ( select co_bod from tbr_bodloc ";
            strSQL+="    where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and st_reg='P'  ) )";
            strSQL+=" WHERE a.st_reg not in ('T','I','E','U') ";
            strSQL+=" AND a.co_emp="+objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a.tx_codalt";
                
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=9;
              
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoItm.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
        }
        catch (Exception e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  } 
        return blnRes;
    }
    
    private boolean configurarVenConBod()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.co_cta");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Cód.Cta.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){//ADMIN
                strSQL ="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom, a2.co_cta";
                strSQL+=" FROM tbm_bod as a1 ";
                strSQL+=" INNER JOIN tbm_plaCta as a2 ON a1.co_emp=a2.co_emp AND a1.co_ctaExi=a2.co_cta";
                strSQL+=" WHERE a1.st_reg IN ('A','P')";
                strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" ORDER BY a1.co_bod ";
            }else{
                strSQL ="";
                strSQL+=" SELECT a2.co_bod, a2.tx_nom, a3.co_cta ";
                strSQL+=" FROM tbr_bodLocPrgUsr as a1 ";
                strSQL+=" INNER JOIN tbm_bod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
                strSQL+=" INNER JOIN tbm_plaCta as a3 ON a2.co_emp=a3.co_emp AND a2.co_ctaExi=a3.co_cta";
                strSQL+=" WHERE a1.st_reg IN ('A','P')";
                strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc="+objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu="+objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_usr="+ objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a2.co_bod ";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=3;
            
            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        strNatTipDoc=vcoTipDoc.getValueAt(5);
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        strNatTipDoc=vcoTipDoc.getValueAt(5);
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            strNatTipDoc=vcoTipDoc.getValueAt(5);
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        strNatTipDoc=vcoTipDoc.getValueAt(5);
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            strNatTipDoc=vcoTipDoc.getValueAt(5);
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
    
    private void mostrarVcoItm()
    {
        int intNumFil=tblDat.getSelectedRow();
        vcoItm.setTitle("Listado de Item"); 
        vcoItm.show();

        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
        {
            tblDat.setValueAt(vcoItm.getValueAt(1),intNumFil,INT_TBL_ITMALT);
            tblDat.setValueAt(vcoItm.getValueAt(2),intNumFil,INT_TBL_CODITM);
            tblDat.setValueAt(vcoItm.getValueAt(3),intNumFil,INT_TBL_DESITM);
            tblDat.setValueAt(vcoItm.getValueAt(7),intNumFil,INT_TBL_UNIMED);

            if(tblDat.getValueAt(intNumFil,INT_TBL_VALOR)==null){
                tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_VALOR); 
            }

            if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null){
                strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
            }
            else {
                strBeforeValue = "";
                strAfterValue ="";
            }
            calcularTot();
            generarAsiDia();
        }
    }
    
    private class ButFndItm extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndItm(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        public void butCLick() {
             mostrarVcoItm();
             tblDat.requestFocus();
             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_VALOR, false, false);
        }
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
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(700, 450));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                CerrarVentana(evt);
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

        tabFrm.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabFrm.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 68));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 125, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(115, 4, 10, 20);

        txtDesCorTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(125, 4, 90, 20);

        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(215, 4, 220, 20);

        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(435, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(457, 4, 125, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setInheritsPopupMenu(false);
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 127, 20);

        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(125, 24, 90, 20);

        lblNumDoc.setText("Número de documento:");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(457, 24, 125, 20);

        txtNumDoc.setMaximumSize(null);
        txtNumDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(582, 24, 100, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setPreferredSize(new java.awt.Dimension(60, 14));
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(457, 44, 125, 20);
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(582, 44, 100, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setPreferredSize(new java.awt.Dimension(452, 350));
        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
     
    
    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
    }//GEN-LAST:event_CerrarVentana

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus(); 
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
            {
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesLarTipDoc.setText("");
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    objTblMod.removeAllRows();          
                    objAsiDia.inicializar();
                    objAsiDia.setEditable(true);
                }
                else
                {
                    mostrarVenConTipDoc(1);
                }
            }
            else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    }//GEN-LAST:event_txtNumDocFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
            {
                if (txtDesLarTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesCorTipDoc.setText("");
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    objTblMod.removeAllRows();          
                    objAsiDia.inicializar();
                    objAsiDia.setEditable(true);
                }
                else
                {
                    mostrarVenConTipDoc(2);
                }
            }
            else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables
    
    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /* Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". 
     * Presenta las opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    
    
    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        String strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        }
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    } 
  
    /**
     * Esta función muestra un mensaje de validación de campos.
     * @param strNomCampo 
     */
    private void mostrarMsgValCam(String strNomCampo){
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
         
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux, true))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }       
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    // tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularTot();
                    generarAsiDia();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
    }       
        
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
    }
    
    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }    
        
    public void noEditable(boolean blnEditable)
    {
        java.awt.Color colBack = txtCodTipDoc.getBackground();
        txtCodDoc.setEditable(blnEditable);            
        txtValDoc.setEditable(blnEditable);
        txtValDoc.setBackground(colBack);        
    }
  
    public void limpiarFrm()
    {
        //Cabecera
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodDoc.setText("");
        txtNumDoc.setText("");
        dtpFecDoc.setText("");
        //Detalle
        objTblMod.removeAllRows();          
        //Diario
        objAsiDia.inicializar();
        objAsiDia.setEditable(true);
        //Pie de pagina
        txaObs1.setText("");
        txaObs2.setText("");
        txtValDoc.setText("0");
    }   
     
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" +  objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" +  objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n'){
                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    }
                    strNatTipDoc=rst.getString("tx_natDoc");
                    bgdSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?1:-1);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
  
    private void calcularTot()
    {
        double dblSub = 0; 
        try{
            for (int i=0;i<tblDat.getRowCount();i++){ 
                dblSub += Double.parseDouble( ( (tblDat.getValueAt(i, INT_TBL_VALOR)==null||tblDat.getValueAt(i, INT_TBL_VALOR).toString().equals(""))?"0.00":tblDat.getValueAt(i, INT_TBL_VALOR).toString() )  );  
            }
            dblSub= objUti.redondear(dblSub, objParSis.getDecimalesBaseDatos() );
            txtValDoc.setText( ""+ dblSub );
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e );
        }
    }
    
    private boolean isServicio(int codItm)
    {
        boolean blnIsSer=false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                //Armar sentencia SQL.                             
                strSQL=" SELECT st_ser FROM tbm_inv WHERE co_emp = " + objParSis.getCodigoEmpresa() + " AND co_itm = " + codItm;
                stmLoc = conLoc.createStatement();
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    blnIsSer =  (rstLoc.getString("st_ser").charAt(0)=='S')? true: false;
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
            }
        }
        catch(java.sql.SQLException Evt){ return false; }
        catch(Exception Evt){   return false; }
        return blnIsSer;
    }
    
     /**
     * Esta función permite costear los items de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean costearItm()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                switch (objTooBar.getEstado())
                {                
                    case 'n':
                        System.out.println("costearDocumento");
                        objUti.costearDocumento(this, objParSis, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    default:
                        break;
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
  
    private boolean isCamVal()
    {
        if(txtCodTipDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Tipo de documento");
            txtCodTipDoc.requestFocus();
            return false;
        }
               
        if(txtNumDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Número de documento");
            txtNumDoc.requestFocus();
            return false;
        }

        if(!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha del Documento");
            dtpFecDoc.requestFocus();
            return false;
        }

        if (!objAsiDia.isDiarioCuadrado()){
            mostrarMsgInf("Asiento descuadrado.");
            tabFrm.setSelectedIndex(2);
            return false;                                    
        }
        
        if (!objAsiDia.isDocumentoCuadrado(txtValDoc.getText())){
            mostrarMsgInf("Asiento y Total del documento esta descuadrado.");
            tabFrm.setSelectedIndex(1);
            return false;                                                       
        }
               
        for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++)
        {
            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null )
            {
                double dblCan = Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_VALOR).toString());
                if(dblCan <= 0.00 ){
                    mostrarMsgInf("<HTML>Es obligatorio ingresar un valor en el detalle.<BR>Escriba en el campo y vuelva a intentarlo.</BR></HTML>");
                    tblDat.repaint();    
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_VALOR);
                    return false; 
                }

                double dblBod = Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_CODBOD).toString());
                if(dblBod <= 0.00 ){
                    mostrarMsgInf("<HTML>Es obligatorio ingresar una bodega en el detalle.<BR>Escriba en el campo y vuelva a intentarlo.</BR></HTML>");
                    tblDat.repaint();    
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_CODBOD);
                    return false; 
                }
            }
        }
               
        if(!objAsiDia.isDocumentoCuadrado(txtValDoc.getText()))               
            return false;
               
        return true; 
    }    
  
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }
        
        public boolean beforeInsertar()
        {
            if(!isCamVal())
                return false;
            
            if( objUltDocPrint.getExisteDoc(Integer.parseInt( txtNumDoc.getText()) ,  Integer.parseInt(txtCodTipDoc.getText()) ) ){
                mostrarMsgInf("El número de documento ya existe.");
                txtNumDoc.requestFocus();
                txtNumDoc.selectAll();
                return false;
            }
            return true;
        }   
        public boolean beforeConsultar() {
            return true;
        } 
        
        public boolean beforeModificar()
        {
            return true;
        }    
        
        public boolean beforeEliminar() 
        {
            return true;
        }        
        
        public boolean beforeAnular() 
        {
            return true;
        }    
        
        public boolean beforeImprimir() {
            return true;
        } 
        public boolean beforeVistaPreliminar() {
            return true;
        }        
        public boolean beforeAceptar() {
            return true;
        }        
        public boolean beforeCancelar() {
            return true;
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
                        if (blnHayCam) {
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
                        //if (blnHayCam || objTblMod.isDataModelChanged()) {
                        if (blnHayCam) {
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

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }

                limpiarFrm();
                noEditable(false);                
                objAsiDia.setEditable(true);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecDoc.setEnabled(true);                                    
                mostrarTipDocPre();    
                txtNumDoc.requestFocus();
                txtNumDoc.setSelectionStart(0);
                txtNumDoc.setSelectionEnd(txtNumDoc.getText().length());
                setEditable(true);
                //objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickConsultar(){
            mostrarTipDocPre();
            objAsiDia.setEditable(false);
        }  
        
        public void clickModificar()
        {
        }   
                    
        public void clickEliminar(){
        }
                    
        public void clickAnular(){
        }

        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }
        public void clickAceptar(){
        }
        public void clickCancelar(){
        }
        
        public boolean insertar()
        {
            if(!insertarReg())
              return false;

            blnHayCam=false;
            return true;
        }
            
        public boolean consultar() {
            consultarReg();
            return true;
        }        
        
        public boolean modificar()
        {
            return true;
        }        

        public boolean eliminar()
        {                
            return true;
        }        
                
        public boolean anular()
        {
            return true;
        }

        public boolean imprimir() 
        {
            Librerias.ZafRpt.ZafRptXLS.ZafRptXLS objPRINT  = new Librerias.ZafRpt.ZafRptXLS.ZafRptXLS(objParSis);
            if(!txtCodTipDoc.getText().equals("") && !txtCodDoc.getText().equals("") ){
                objPRINT.GenerarDoc( Integer.parseInt(txtCodTipDoc.getText()) , Integer.parseInt(txtCodDoc.getText()));
            }                                     
            objPRINT = null;            
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }

        public boolean cancelar()
        {
            boolean blnRes=true;
            try{
                if ((blnHayCam) ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
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
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterInsertar() {            
            this.setEstado('w');
            consultarReg();            
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
                
        public boolean afterEliminar()
        {
            return true;
        }
        
        public boolean afterAnular() 
        {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        public boolean afterVistaPreliminar() {
            return true;
        } 
        public boolean afterAceptar() {
            return true;
        }        
        public boolean afterCancelar() {
            return true;
        }

   
    }    
    //Fin ToolBar 
      
    
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " ;                                                                           
                strSQL+=" FROM tbm_cabMovInv as a ";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc as a1 ON (a.co_emp = a1.co_emp AND a.co_loc = a1.co_loc AND a.co_tipdoc = a1.co_tipdoc) ";
                strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg as a2 ON (a2.co_emp = a1.co_emp AND a2.co_loc = a1.co_loc AND a2.co_tipdoc = a1.co_tipdoc)" ;
                strSQL+=" WHERE a.st_reg NOT IN('E')";
                strSQL+=" AND a.co_emp = " + objParSis.getCodigoEmpresa() ; 
                strSQL+=" AND a.co_loc = " + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu = " + objParSis.getCodigoMenu();
                /* Filtros */
                if(!txtCodTipDoc.getText().equals("")) { //Por Código Tipo de Documento
                    strSQL+=" AND a.co_tipdoc = " + txtCodTipDoc.getText(); 
                }
                if(!txtCodDoc.getText().equals("")) { //Por Código de Documento
                    strSQL+=" AND a.co_doc = " + txtCodDoc.getText();
                }
                if(!txtNumDoc.getText().equals("")) { //Por Número de Documento
                    strSQL+=" AND a.ne_numdoc = " + txtNumDoc.getText();
                }
                if(dtpFecDoc.isFecha()){ //Por Fecha
                    strSQL+=" AND a.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }  
                strSQL+=" ORDER BY a.ne_secEmp";
                //System.out.println("consultarReg: "+strSQL);
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
        
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if(cargarDetReg())
                {
                    if(objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)))
                    {
                        //if(objAsiDia.consultarDiarioCompleto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(), intCodDoc)){}
                        blnRes=true;
                    }
                }                
            }
            else
            {
                mostrarMsgInf("Error al cargar registro");
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    } 
    
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        int intPosRel;
        try
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+= " SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numdoc, a.fe_doc ";
                strSQL+= "      , a1.tx_desCor as tx_desCorTipDoc, a1.tx_desLar as tx_desLarTipDoc"; 
                strSQL+= "      , a.tx_obs1, a.tx_obs2, a.st_reg ";        
                strSQL+= " FROM tbm_cabMovInv as a " ;
                strSQL+= " INNER JOIN tbm_cabTipDoc as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc " ;
                strSQL+= " WHERE a.st_reg NOT IN ('E')";
                strSQL+= " AND a.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+= " AND a.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+= " AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+= " AND a.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                //System.out.println("cargarCabReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {    
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_desCorTipDoc");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                                      
                    strAux=rst.getString("tx_desLarTipDoc");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);                                        
                    
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    //Pie de pagina
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("I")){
                        strAux="Anulado";
                        objUti.desactivarCom(this);
                    }
                    else if (strAux.equals("E")){
                        strAux="Eliminado";
                        objUti.desactivarCom(this);
                    }
                    else {
                        strAux="Otro";
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(this);
                            noEditable(false);
                        }
                    }
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                           
                //Mostrar la posición relativa del registro.
                intPosRel = intIndiceCon+1;
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
                blnHayCam=false;
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
    
    public boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {      
            con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con != null)
            {
                stm=con.createStatement();
                //Detalle     
                strSQL ="";
                strSQL+=" SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod, a.nd_can, a.nd_canorg ";
                strSQL+="      , a.nd_cosuni, a.nd_preuni, a.nd_pordes, a.st_ivacom, a1.st_ser, a.co_itmact, a2.tx_nom as tx_nomBod, a2.co_ctaExi as co_ctaBod " ;
                strSQL+=" FROM tbm_detMovInv as a ";
                strSQL+=" INNER JOIN tbm_inv as a1 ON (a.co_emp=a1.co_emp AND a.co_itm=a1.co_itm)";
                strSQL+=" INNER JOIN tbm_bod as a2 ON (a.co_emp=a2.co_emp AND a.co_bod=a2.co_bod)";
                strSQL+=" WHERE a.co_emp = " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc = " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_tipdoc = "  + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a.co_doc = "  + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ORDER by a.co_reg";  
                //System.out.println("cargarDetReg: "+strSQL);
                rst= stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                while (rst.next())
                {   
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIMED, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_NOMBOD, rst.getString("tx_nomBod"));
                    vecReg.add(INT_TBL_CTABOD, rst.getString("co_ctaBod"));
                    vecReg.add(INT_TBL_VALOR, new Double(rst.getDouble("nd_cosuni")));
                    vecDat.add(vecReg);                
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
          
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                calcularTot();
            }
            blnHayCam = false; // Seteando que no se ha hecho cambios
        }//fin Try
       catch(SQLException Evt)
       {
           blnRes=false;
           objUti.mostrarMsgErr_F1(this, Evt);
       }
       catch(Exception Evt)
       {
           blnRes=false;
           objUti.mostrarMsgErr_F1(this, Evt);
       }    
       return blnRes;
    }    
    
    public boolean insertarReg()
    {   
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                con.setAutoCommit(false);
                if(insertarCab()){
                    if(insertarDet()){
                        if(objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )) {
                            if(objSegMovInv.setSegMovInvCompra(con, objCodSeg, 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                            {
                                costearItm();
                                con.commit();
                                blnRes=true;
                            }else con.rollback();  
                        }else con.rollback();   
                    }else con.rollback();   
               }else con.rollback();

               con.close();                                            
               con=null;
               objTblMod.clearDataSavedBeforeRemoveRow();
               objTblMod.initRowsState();
            }
        }
        catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;                  
    }  

    private boolean insertarCab()
    {
        boolean blnRes = true;
        int intSecEmp=0, intSecGrp=0, intUltReg;
        BigDecimal bgdValTot=new BigDecimal ("0");
        try
        {
            stm = con.createStatement();
            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            
            intSecEmp = objUltDocPrint.getNumSecDoc( con, objParSis.getCodigoEmpresa() );
            intSecGrp = objUltDocPrint.getNumSecDoc( con, objParSis.getCodigoEmpresaGrupo() );
            
            //Obtener el código del último registro.
            strSQL ="";
            strSQL+=" SELECT MAX(a1.co_doc)";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" WHERE a1.co_emp=" +objParSis.getCodigoEmpresa()+ "";
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
            strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
            intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
            if (intUltReg==-1)
                return false;
            intUltReg++;
            txtCodDoc.setText("" + intUltReg);

            //Armar sentencia SQL
            bgdValTot=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , 4);
            strSQL ="";
            strSQL+=" INSERT INTO tbm_cabMovInv( co_emp, co_loc, co_tipDoc, co_doc, ne_secemp, ne_secgrp";
            strSQL+="                          , fe_doc, ne_numDoc, tx_obs1, tx_obs2, nd_sub, nd_tot " ;
            strSQL+="                          , st_reg, st_conInv, st_regrep, st_tipdev, co_usrIng, fe_ing, tx_coming ) ";
            strSQL+=" VALUES(";
            strSQL+="  "+objParSis.getCodigoEmpresa(); //co_emp
            strSQL+=" ,"+objParSis.getCodigoLocal();   //co_loc
            strSQL+=" ,"+txtCodTipDoc.getText();       //co_tipDoc
            strSQL+=" ,"+txtCodDoc.getText();          //co_doc
            strSQL+=" ,"+intSecEmp; //ne_secemp
            strSQL+=" ,"+intSecGrp; //ne_secgrp
            strSQL+=" ,'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
            strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);//ne_numDoc
            strSQL+=", " + objUti.codificar(txaObs1.getText());//tx_obs1
            strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
            strSQL+=", " + bgdValTot.multiply(bgdSig); //nd_sub
            strSQL+=", " + bgdValTot.multiply(bgdSig); //nd_tot
            strSQL+=" ,'A' "; //st_reg
            strSQL+=" ,'F' "; //st_conInv
            strSQL+=" ,'I' "; //st_regrep
            strSQL+=" ,'C' "; //st_tipdev
            strSQL+=" ,"+objParSis.getCodigoUsuario(); //co_usrIng
            strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            strSQL+=" ,'" + strAux + "'"; //fe_ing
            strSQL+=" , " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()); //tx_comIng
            strSQL+=" );";
            //System.out.println("insertarCab: "+strSQL);

            stm.executeUpdate(strSQL);
            stm.close();
            stm=null;
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean insertarDet()
    {
        boolean blnRes = true;
        int intControl=0;
        StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
        BigDecimal bgdVal=new BigDecimal("0");
        try
        {
            for(int i=0; i<tblDat.getRowCount(); i++)
            { 
                bgdVal=new BigDecimal("0");
                if(tblDat.getValueAt(i,INT_TBL_CODITM) != null )
                { 
                    bgdVal=objUti.redondearBigDecimal(new BigDecimal( (tblDat.getValueAt(i,INT_TBL_VALOR)==null)?"0":tblDat.getValueAt(i, INT_TBL_VALOR).toString()),objParSis.getDecimalesBaseDatos());
                    intControl=1;
                    if(i>0) stbins.append(" UNION ALL ");
                    stbins.append(
                            " SELECT "+objParSis.getCodigoEmpresa()+  //co_emp
                            ", "+objParSis.getCodigoLocal()+          //co_loc
                            ", "+Integer.parseInt(txtCodTipDoc.getText())+  //co_tipdoc
                            ", "+Integer.parseInt(txtCodDoc.getText())+     //co_doc
                            ", "+(i+1)+ //co_reg
                            ", "+objTblMod.getValueAt(i,INT_TBL_CODITM) +  //co_itm
                            ", "+objTblMod.getValueAt(i,INT_TBL_CODITM) +  //co_itmAct
                            ", "+objUti.codificar(objTblMod.getValueAt(i,INT_TBL_ITMALT)) +  //tx_codAlt   
                            ", "+objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DESITM)) +  //tx_nomItm   
                            ", "+objUti.codificar(objTblMod.getValueAt(i,INT_TBL_UNIMED)) +  //tx_unimed
                            ", "+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+  //co_bod
                            ", 0"+ //nd_can
                            ", "+bgdVal+  //nd_cosUni
                            ", "+bgdVal+  //nd_cosUniGrp
                            ", "+bgdVal+  //nd_preUni
                            ", 0"+   //nd_porDes
                            ", 'S'"+ //st_ivaCom
                            ", Null"+ //st_reg
                            ", "+ bgdVal.multiply(bgdSig) + //nd_costot
                            ", "+ bgdVal.multiply(bgdSig) + //nd_costotgrp
                            ", "+ bgdVal.multiply(bgdSig) + //nd_tot
                            ", 'I'"+ //st_regrep
                            ", 'N'"+ //st_merIngEgrFisBod   
                            ",  0"+  //nd_cancon
                            " ");
                }
            }
            if(intControl > 0 )
            {
                strSQL ="";
                strSQL+=" INSERT INTO  tbm_detMovInv " ;
                strSQL+=" (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, co_itmact, tx_codAlt, tx_nomItm, tx_unimed, co_bod, nd_can, nd_cosUni";
                strSQL+=" , nd_cosUniGrp, nd_preUni, nd_porDes, st_ivaCom, st_reg, nd_costot, nd_costotgrp, nd_tot, st_regrep, st_meringegrfisbod, nd_cancon ) ";
                strSQL+=" "+stbins.toString();
                System.out.println("insertarDet: "+ strSQL);
                stm = con.createStatement();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
            else return false;
               
            stbins=null;
        }
        catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }    
    
    /**
     * Genera Asiento de Diario del documento
     * @return 
     */
    private boolean generarAsiDia()
    {  
        boolean blnRes=false;
        try
        {
            //Se inicializa el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
            if(getArrDatBod()){
                if(cargarCtaCon()){
                    if(cargarDetAsiDia()){
                        blnRes=true;
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta función permite obtener las bodegas ingresadas en el detalle.
     * @return las bodegas de manera agrupada, es decir,
     * si existen 10 registros con la misma bodega devuelve 1 solo registro en el arraylist.
     */
    private boolean getArrDatBod()
    {
        boolean blnRes=true;
        boolean blnAux=false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intCodCtaBod=0;
        BigDecimal bdeVal=new BigDecimal("0");
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                strSQL ="";
                for (int i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    intCodCtaBod=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_CTABOD)==null?"0":(objTblMod.getValueAt(i,INT_TBL_CTABOD).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_CTABOD).toString()));
                    bdeVal=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALOR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALOR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALOR).toString()));       
          
                    //Armar sentencia SQL
                    if(i==0) {
                        strSQL+=" SELECT a.co_ctaBod, SUM(a.nd_val) as nd_val FROM (";
                    }
                    strSQL+="     SELECT CAST("+intCodCtaBod+" AS INTEGER) AS co_ctaBod ";
                    strSQL+="          , ABS(" + bdeVal + ") AS nd_val";

                   if(i<objTblMod.getRowCountTrue()-1){
                       strSQL+=" UNION ALL";
                   }
                   else{
                       strSQL+=" ) as a WHERE (a.co_ctaBod<>0)";
                       strSQL+=" GROUP BY a.co_ctaBod";
                       strSQL+=" ORDER BY a.co_ctaBod ";
                   } 
                   blnAux=true;
                }
                
                if(blnAux)
                {
                    rstLoc=stmLoc.executeQuery(strSQL);
                    arlDatBod = new ArrayList();
                    while(rstLoc.next())
                    {
                        arlRegBod = new ArrayList();
                        arlRegBod.add(INT_ARL_BOD_COD_CTA, rstLoc.getInt("co_ctaBod"));
                        arlRegBod.add(INT_ARL_BOD_VALOR, rstLoc.getBigDecimal("nd_val"));
                        arlDatBod.add(arlRegBod);
                    }
                    rstLoc.close();
                    rstLoc=null;
                }
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /*
     * Función que carga las cuentas contables a utilizarse en el asiento de diario.
     * @return 
     */
    private boolean cargarCtaCon(){
        boolean blnRes=true;
        intCodCtaDeb=0;
        strNumCtaDeb="";
        strNomCtaDeb="";
        intCodCtaHab=0;
        strNumCtaHab="";
        strNomCtaHab="";

        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();               
        
                //Cuenta Debe 
                strSQL ="";
                strSQL+=" SELECT x.co_ctaDeb, x.tx_codCtaDeb, x.tx_nomCtaDeb";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaDeb, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaDeb, a2.tx_desLar AS tx_nomCtaDeb";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS x";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaDeb=rst.getInt("co_ctaDeb");
                    strNumCtaDeb=rst.getString("tx_codCtaDeb");
                    strNomCtaDeb=rst.getString("tx_nomCtaDeb");
                }
                
                //Cuenta Haber
                strSQL ="";
                strSQL+=" SELECT x.co_ctaHab, x.tx_codCtaHab, x.tx_nomCtaHab";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaHab, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaHab, a2.tx_desLar AS tx_nomCtaHab";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";  
                strSQL+=" 	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS x";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaHab=rst.getInt("co_ctaHab");
                    strNumCtaHab=rst.getString("tx_codCtaHab");
                    strNomCtaHab=rst.getString("tx_nomCtaHab");
                }
                
                //Cuenta de Bodega
                arlDatCtaBod=new ArrayList();
                for(int j=0; j<arlDatBod.size(); j++)
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_bod AS a";
                    strSQL+=" INNER JOIN tbm_plaCta as a1 ON a.co_emp=a1.co_emp AND a.co_ctaExi=a1.co_cta";
                    strSQL+=" WHERE a1.st_Reg IN ('A')";
                    strSQL+=" AND a.co_emp="+ objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_cta="+objUti.getStringValueAt(arlDatBod, j, INT_ARL_BOD_COD_CTA);     
                    strSQL+=" GROUP BY a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegCtaBod=new ArrayList();
                        arlRegCtaBod.add(INT_ARL_CTA_COD_CTA, rst.getString("co_cta"));
                        arlRegCtaBod.add(INT_ARL_CTA_NUM_CTA, rst.getString("tx_codCta"));
                        arlRegCtaBod.add(INT_ARL_CTA_NOM_CTA, rst.getString("tx_desLar"));
                        arlDatCtaBod.add(arlRegCtaBod);
                    }                            
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    

   /**
     * Función que carga valores que tendrá el asiento de diario del DxP.
     * @return 
     */
    private boolean cargarDetAsiDia()
    {
        boolean blnRes=true;
        BigDecimal bgdBod=new BigDecimal("0");
        String strCodCtaBod="", strCodCtaBodSel="";
        try
        {
            if(strNatTipDoc.equals("I"))
            {
                //Debe: Bodega
                for(int j=0; j<arlDatBod.size(); j++)
                {
                    strCodCtaBod=objUti.getStringValueAt(arlDatBod, j, INT_ARL_BOD_COD_CTA);
                    for(int k=0; k<arlDatCtaBod.size();k++)
                    {
                        strCodCtaBodSel=objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_COD_CTA);
                        if(strCodCtaBod.equals(strCodCtaBodSel))
                        {
                            bgdBod=objUti.getBigDecimalValueAt(arlDatBod, j, INT_ARL_BOD_VALOR);
                            if(bgdBod.compareTo(new BigDecimal("0"))>0)
                            {
                                vecRegDia=new Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_COD_CTA));
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_NUM_CTA));
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_NOM_CTA));
                                vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdBod);
                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }                            
                        }
                    }
                } 

                //Haber: Total
                if( new BigDecimal(txtValDoc.getText()).compareTo(BigDecimal.ZERO)>0  ){
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaHab);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaHab);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaHab);
                    vecRegDia.add(INT_VEC_DIA_DEB, "0" );
                    vecRegDia.add(INT_VEC_DIA_HAB, ""+ new BigDecimal(txtValDoc.getText()));
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
            }
            else
            {
                //Deber: Total
                if( new BigDecimal(txtValDoc.getText()).compareTo(BigDecimal.ZERO)>0  ){
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDeb);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaDeb);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaDeb);
                    vecRegDia.add(INT_VEC_DIA_DEB, ""+ new BigDecimal(txtValDoc.getText()) );
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
                
                //Haber: Bodega
                for(int j=0; j<arlDatBod.size(); j++)
                {
                    strCodCtaBod=objUti.getStringValueAt(arlDatBod, j, INT_ARL_BOD_COD_CTA);
                    for(int k=0; k<arlDatCtaBod.size();k++)
                    {
                        strCodCtaBodSel=objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_COD_CTA);
                        if(strCodCtaBod.equals(strCodCtaBodSel))
                        {
                            bgdBod=objUti.getBigDecimalValueAt(arlDatBod, j, INT_ARL_BOD_VALOR);
                            if(bgdBod.compareTo(new BigDecimal("0"))>0)
                            {
                                vecRegDia=new Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_COD_CTA));
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_NUM_CTA));
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaBod, k, INT_ARL_CTA_NOM_CTA));
                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdBod);
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }                            
                        }
                    }
                } 
            }
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
  

    
        
}

