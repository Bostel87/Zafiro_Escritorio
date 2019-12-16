/*
 * ZafVen34.java
 *
 * Created on 08 de Diciembre del 2015, 14:45
 */

package Ventas.ZafVen34;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafLocPrgUsr;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author  Rosa Zambrano
 */
public class ZafVen34 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                       //Linea
    private static final int INT_TBL_DAT_COD_EMP = 1;                   //Código de Empresa.
    private static final int INT_TBL_DAT_COD_LOC = 2;                   //Código de Local.
    private static final int INT_TBL_DAT_NOM_LOC = 3;                   //Nombre de Local.
    private static final int INT_TBL_DAT_COD_TIP_DOC = 4;               //Codigo Tipo de Documento.
    private static final int INT_TBL_DAT_DES_COR_TIP_DOC = 5;           //Descripción Corta Tipo de Documento.
    private static final int INT_TBL_DAT_DES_LAR_TIP_DOC = 6;           //Descripción Larga Tipo de Documento.
    private static final int INT_TBL_DAT_COD_DOC = 7;                   //Código del documento.
    private static final int INT_TBL_DAT_NUM_DOC = 8;                   //Número del documento.
    private static final int INT_TBL_DAT_FEC_DOC = 9;                   //Fecha del documento.
    
    private static final int INT_TBL_DAT_COD_CLI = 10;                   //Código del cliente/proveedor.
    private static final int INT_TBL_DAT_IDE_CLI = 11;                  //Identificación del cliente/proveedor.
    private static final int INT_TBL_DAT_NOM_CLI = 12;                  //Nombre del cliente/proveedor.
    private static final int INT_TBL_DAT_COD_FOR_PAG_CLI = 13;          //Código Forma de pago del cliente/proveedor.
    private static final int INT_TBL_DAT_NOM_FOR_PAG_CLI = 14;          //Nombre de la Forma de pago del cliente/proveedor.
    private static final int INT_TBL_DAT_COD_ITM = 15;                  //Código del ítem.
    private static final int INT_TBL_DAT_COD_ALT_ITM = 16;              //Código alterno del ítem.
    private static final int INT_TBL_DAT_NOM_ITM = 17;                  //Nombre del ítem.
    private static final int INT_TBL_DAT_DES_COR_UNI_MED = 18;          //Descripción Corta de Unidad de Medida.
    private static final int INT_TBL_DAT_CAN_MOV_ITM = 19;              //Cantidad Item.
    private static final int INT_TBL_DAT_COS_UNI_ITM = 20;              //Costo Unitario Item.
    private static final int INT_TBL_DAT_PRE_UNI_ITM = 21;              //Precio Unitario Item.
    private static final int INT_TBL_DAT_POR_DES_ITM = 22;              //Porcentaje de Descuento Item.
    private static final int INT_TBL_DAT_EST_ITM_SER = 23;              //Item es de servicio.
    private static final int INT_TBL_DAT_COS_TOT_ITM = 24;              //Costo Total.
    private static final int INT_TBL_DAT_VAL_TOT_ITM = 25;              //Valor Total.
    private static final int INT_TBL_DAT_PES_UNI_ACT = 26;              //Peso Unitario Actual.
    private static final int INT_TBL_DAT_COS_UNI_ACT = 27;              //Costo Unitario Actual
    
    private static final int INT_TBL_DAT_EST_REG_DOC = 28;              //Estado de Registro del documento.
    
    private static final int INT_TBL_DAT_COD_USR_ING = 29;                   
    private static final int INT_TBL_DAT_NOM_USR_ING = 30;                   
    private static final int INT_TBL_DAT_COD_USR_VEN = 31;                   
    private static final int INT_TBL_DAT_NOM_USR_VEN = 32;                    
    private static final int INT_TBL_DAT_COD_USR_CONF = 33;                   
    private static final int INT_TBL_DAT_NOM_USR_CONF = 34;                   
    private static final int INT_TBL_DAT_COD_USR_NEXT = 35;                   
    private static final int INT_TBL_DAT_NOM_USR_NEXT = 36;         
    
    //JTable: Tabla de Locales.
    private final int INT_TBL_LOC_LIN=0;
    private final int INT_TBL_LOC_CHKSEL=1;
    private final int INT_TBL_LOC_CODEMP=2;
    private final int INT_TBL_LOC_NOMEMP=3;
    private final int INT_TBL_LOC_CODLOC=4;
    private final int INT_TBL_LOC_NOMLOC=5;
    
    //JTable: Tabla de Tipos de documentos.
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
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Check Locales.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblMod objTblModDat, objTblModLoc, objTblModTipDoc;              //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat, objMouMotAdaLoc, objMouMotAdaTipDoc;  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafVenCon vcoItm;                                   //Ventana de consulta.
    private ZafSelFec objSelFec;                                //Selector de Fecha.
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnMarTodChkTblLoc=true;                    //Marcar todas las casillas de verificación del JTable de Locales.                 
    private boolean blnMarTodChkTblTipDoc=true;                 //Marcar todas las casillas de verificación del JTable de Tipos de documentos.                 
    private Vector vecDat, vecCab, vecReg, vecEstReg;
    private String strSQL, strAux;
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strCodCli, strIdeCli, strNomCli;             //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private String strCodAlt, strNomItm;                        //Contenido del campo al obtener el foco.
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen34(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()) 
                exitForm();
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenChk = new ZafTblCelRenChk();
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.7 ");
            lblTit.setText(strAux);
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setTitulo("Fecha del documento");
            panFecDoc.add(objSelFec);
            objSelFec.setBounds(6, 4, 490, 75);
            
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);      
            
            //Por Grupo no se realiza la búsqueda de clientes con código.
            if(objParSis.getCodigoEmpresa()== objParSis.getCodigoEmpresaGrupo())
            {
                txtCodCli.setEnabled(false);
            }
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3517)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3518)) 
            {
                butCer.setVisible(false);
            }
    	    
            //Configurar las ZafVenCon.
            configurarVenConCli();
            configurarVenConVen();
            configurarVenConItm();
            
            //Configurar los JTables.
            configurarTblLoc();
            configurarTblTipDoc();
            configurarTblDat();
            
            //Configurar Combos.
            configurarComboEstReg();  
            
            //Carga Locales
            cargarTblLoc();
            
            //Carga Tipos de Documentos
            cargarTblTipDoc();
            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_IDE_CLI,"Identificación");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_COD_FOR_PAG_CLI,"Cód.For.Pag.");
            vecCab.add(INT_TBL_DAT_NOM_FOR_PAG_CLI,"Forma de pago");
            
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Ítem");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_CAN_MOV_ITM,"Cantidad");
            vecCab.add(INT_TBL_DAT_COS_UNI_ITM,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_PRE_UNI_ITM,"Pre.Uni.");
            vecCab.add(INT_TBL_DAT_POR_DES_ITM,"% Des.");
            vecCab.add(INT_TBL_DAT_EST_ITM_SER,"Servicio");
            vecCab.add(INT_TBL_DAT_COS_TOT_ITM,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_ITM,"Total");
            vecCab.add(INT_TBL_DAT_PES_UNI_ACT,"Pes.Uni.(Kg)");
            vecCab.add(INT_TBL_DAT_COS_UNI_ACT,"Cos.Act.");
            vecCab.add(INT_TBL_DAT_EST_REG_DOC,"Est.Doc.");
            
            vecCab.add(INT_TBL_DAT_COD_USR_ING,"Cod.Usr.Ing.");
            vecCab.add(INT_TBL_DAT_NOM_USR_ING,"Nom.Usr.Ing.");
            vecCab.add(INT_TBL_DAT_COD_USR_VEN,"Cod.Usr.Ven.");
            vecCab.add(INT_TBL_DAT_NOM_USR_VEN,"Nom.Usr.Ven.");
            vecCab.add(INT_TBL_DAT_COD_USR_CONF,"Cod.Usr.Conf.");
            vecCab.add(INT_TBL_DAT_NOM_USR_CONF,"Nom.Usr.Conf.");
            vecCab.add(INT_TBL_DAT_COD_USR_NEXT,"Cod.Usr.Prox.");
            vecCab.add(INT_TBL_DAT_NOM_USR_NEXT,"Nom.Usr.Prox.");            

            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_COD_FOR_PAG_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_FOR_PAG_CLI).setPreferredWidth(95);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(105);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_EST_ITM_SER).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_ITM).setPreferredWidth(77);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ITM).setPreferredWidth(77);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ACT).setPreferredWidth(72);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ACT).setPreferredWidth(72);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG_DOC).setPreferredWidth(50);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_ING).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_VEN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_CONF).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR_CONF).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_NEXT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR_NEXT).setPreferredWidth(60);            
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_FOR_PAG_CLI, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MOV_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_ITM_SER).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_POR_DES_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
         
    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("A: Activo");
            cboEstReg.addItem("I: Anulado");
            cboEstReg.setSelectedIndex(1); 
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }

     /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN, "");
            vecCab.add(INT_TBL_LOC_CHKSEL, " ");
            vecCab.add(INT_TBL_LOC_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_LOC_NOMEMP, "Empresa");
            vecCab.add(INT_TBL_LOC_CODLOC, "Cod.Loc");
            vecCab.add(INT_TBL_LOC_NOMLOC, "Local");
            
            objTblModLoc = new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblDatLoc.setModel(objTblModLoc);

            //Configurar JTable: Establecer tipo de selección.
            tblDatLoc.setRowSelectionAllowed(true);
            tblDatLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOMEMP).setPreferredWidth(221);
            tcmAux.getColumn(INT_TBL_LOC_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOMLOC).setPreferredWidth(221);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatLoc, INT_TBL_LOC_LIN);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatLoc.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatLoc);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaLoc =new ZafMouMotAda();
            tblDatLoc.getTableHeader().addMouseMotionListener(objMouMotAdaLoc);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdLoc=new ZafTblOrd(tblDatLoc);
            
            tblDatLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            tblDatLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_LOC_CHKSEL);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_CODLOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblModLoc.setModoOperacion(objTblModLoc.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica el Local seleccionado en el el JTable de Locales.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try 
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDatLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHKSEL)
            {
                if (blnMarTodChkTblLoc)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHKSEL);
                    }
                    blnMarTodChkTblLoc=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHKSEL);
                    }
                    blnMarTodChkTblLoc=true;
                }
            }
        }
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);   }
    }
    
    
    private void cargarTblLoc() 
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intTipLoc=1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try 
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stm=conn.createStatement();
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Arma la sentencia.
                    strSQL ="";
                    if (objParSis.getCodigoUsuario() == 1) 
                    {
                        strSQL+=" SELECT a.co_emp, b.co_loc, a.tx_nom as empresa, b.tx_nom as local ";
                        strSQL+=" FROM tbm_emp as a ";
                        strSQL+=" INNER JOIN tbm_loc as b  ON (b.co_emp=a.co_emp) ";
                        strSQL+=" WHERE a.co_emp <> "+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND a.st_reg != 'I' ";         
                        strSQL+=" ORDER BY a.co_emp, b.co_loc ";
                    }
                    else
                    {
                        strSQL+=" SELECT a.co_emp, a.co_loc ,a1.tx_nom as empresa , a.tx_nom as local ";
                        strSQL+=" FROM tbm_loc as a ";
                        strSQL+=" INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp) ";
                        strSQL+=" INNER JOIN tbr_locPrgUsr as a2 ON (a.co_Emp=a2.co_empRel AND a.co_loc=a2.co_locRel) ";
                        strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" ORDER BY  a2.co_empRel, a2.co_locRel ";
                    }
                }
                else
                {
                    //Arma la sentencia.
                    strSQL ="";
                    strSQL+=" SELECT a.co_emp, b.co_loc, a.tx_nom as empresa, b.tx_nom as local ";
                    strSQL+=" FROM tbm_emp as a ";
                    strSQL+=" INNER JOIN tbm_loc as b  ON (b.co_emp=a.co_emp) ";
                    strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                    //Valida si el usuario tiene acceso a locales.
                    if (objParSis.getCodigoUsuario() != 1) 
                    {
                        if ((objLocPrgUsr.validaLocUsr())) 
                        {
                            strSQL += " AND b.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                        } 
                        else 
                        {
                            strSQL += " AND b.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                        }
                    }
                    strSQL+=" AND a.st_reg != 'I' ";
                    strSQL+=" ORDER BY a.co_emp, b.co_loc ";
                }
                //System.out.println("cargarTblLoc: "+strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHKSEL,true);
                    vecReg.add(INT_TBL_LOC_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_LOC_NOMEMP, rst.getString("empresa") );
                    vecReg.add(INT_TBL_LOC_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_LOC_NOMLOC, rst.getString("local") );
                    vecDat.add(vecReg);
                 }
                 rst.close();
                 stm.close();
                 conn.close();
                 rst=null;
                 stm=null;
                 conn=null;
                 //Asignar vectores al modelo.
                 objTblModLoc.setData(vecDat);
                 tblDatLoc.setModel(objTblModLoc);
                 blnMarTodChkTblLoc=false;
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
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
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setResizable(false);

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
            objTblOrdLoc=new ZafTblOrd(tblDatTipDoc);
            
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
    
    private void cargarTblTipDoc() 
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
                if (objParSis.getCodigoUsuario()==1)
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
                }                

                System.out.println("cargarTblTipDoc: "+strSQL);
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
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        int intTipLoc=1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try
        {
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
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
            {
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario() == 1)
                {
                    strSQL = "";
                    strSQL += " SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir ";
                    strSQL += " FROM tbm_cli as a1";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.st_reg='A' ";
                    strSQL += " AND a1.st_cli='S'";
                    strSQL += " ORDER BY a1.tx_nom";
                } 
                else 
                {
                    strSQL = "";
                    strSQL += " SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir ";
                    strSQL += " FROM tbm_cli AS a1";
                    strSQL += " INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    
                    //Valida si el usuario tiene acceso a locales.
                    if ((objLocPrgUsr.validaLocUsr())) 
                    {
                        strSQL += " AND a2.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                    }
                    else 
                    {
                        strSQL += " AND a2.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                    }
                    strSQL += " AND a1.st_reg='A' ";
                    strSQL += " AND a1.st_cli='S'";
                    strSQL += " ORDER BY a1.tx_nom";
                }
            }
            else 
            {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL+=" SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir";
                strSQL+=" FROM ";
                strSQL+=" ( ";
                strSQL+="   select b2.co_emp, ' ' as co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir "; 
                strSQL+="   from ";
                strSQL+="   ( "; 
                strSQL+="     select a2.co_emp, MAX(a2.co_cli) as co_cli, a2.tx_ide ";  
                strSQL+="     from ( select MIN(co_emp) as co_emp, tx_ide from tbm_cli group by tx_ide  ) as a1 ";
                strSQL+="     inner join tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.tx_ide=a2.tx_ide) ";     
                strSQL+="     group by a2.co_emp, a2.tx_ide ";
                strSQL+="   ) as b1 ";
                strSQL+="   inner join tbm_cli as b2 on (b1.co_emp=b2.co_emp and b1.co_cli=b2.co_cli) ";
                strSQL+="   where b2.co_Emp not in (select  co_Emp from tbm_Emp where st_reg='I') ";
                strSQL+="   order by b2.tx_nom ";  
                strSQL+=" ) AS a ";
            }   

            //System.out.println("configurarVenConCli: "+strSQL);
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)  {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()
    {
        boolean blnRes=true;
        int intTipLoc=1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");
            
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) 
            {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) 
                {
                    strSQL =" ";
                    strSQL+=" SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                    strSQL+=" FROM tbr_locPrgUsr as a1 ";
                    strSQL+=" INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                    strSQL+=" INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                    strSQL+=" INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                    strSQL+=" WHERE a3.st_ven='S' AND a2.st_reg='A' AND a3.co_emp>0";
                    strSQL+=" GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                    strSQL+=" ORDER BY a2.tx_nom";
                }
                else 
                {
                    strSQL = "";
                    strSQL+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                    strSQL+=" FROM tbm_usr AS a1";
                    strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                    strSQL+=" INNER JOIN tbr_locusr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usr)";
                    strSQL+=" WHERE a2.st_ven='S' AND a1.st_reg='A' AND a2.co_emp>0 ";
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom ";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
            } 
            else 
            {
                strSQL =" ";
                strSQL+=" SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a1 ";
                strSQL+=" INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                strSQL+=" INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                strSQL+=" INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                strSQL+=" WHERE a3.st_ven='S' AND a2.st_reg='A' AND a3.co_emp>0";
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                //Valida si el usuario tiene acceso a locales.
                if ((objLocPrgUsr.validaLocUsr())) 
                {
                    strSQL+=" AND a4.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                } 
                else
                {
                    strSQL+=" AND a4.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                }
                strSQL += " GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                strSQL += " ORDER BY a2.tx_nom";
            }
            //System.out.println("configurarVenConVen: " + strSQL);
            
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)  {     blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm() 
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL += " FROM tbm_inv AS a1";
            strSQL += " LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            //strSQL += " AND (   (UPPER(a1.tx_codAlt) LIKE '%I') OR  (UPPER(a1.tx_codAlt) LIKE '%S')  ) AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
            strSQL += " ORDER BY a1.tx_codAlt";
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            
            vcoItm = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        } 
        catch (Exception e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);    }
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strNomCli);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)   {   blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
       
    
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)   {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus)  
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.show();
                    if (vcoItm.getSelectedButton() == vcoItm.INT_BUT_ACE) 
                    {
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText())) 
                    {
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    } 
                    else 
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton() == vcoItm.INT_BUT_ACE) 
                        {
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        } 
                        else 
                        {
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    } 
                    else 
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton() == vcoItm.INT_BUT_ACE) 
                        {
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        } 
        catch (Exception e) {     blnRes = false;     objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
   
    

    

    /**
     * ToolTips de Jtable: Datos.
     */
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código  de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
               case INT_TBL_DAT_NOM_LOC:
                    strMsg="Nombre del local";
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
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                 case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
               case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_IDE_CLI:
                    strMsg="Identificación del cliente";
                    break;   
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_COD_FOR_PAG_CLI:
                    strMsg="Código de la forma de pago";
                    break; 
                case INT_TBL_DAT_NOM_FOR_PAG_CLI:
                    strMsg="Forma de pago";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CAN_MOV_ITM:
                    strMsg="Cantidad";
                    break;   
                case INT_TBL_DAT_COS_UNI_ITM:
                    strMsg="Costo unitario";
                    break;                      
                case INT_TBL_DAT_PRE_UNI_ITM:
                    strMsg="Precio unitario";
                    break;   
                case INT_TBL_DAT_POR_DES_ITM:
                    strMsg="Porcentaje de descuento";
                    break;  
                case INT_TBL_DAT_EST_ITM_SER:
                    strMsg="Indica si el item es de servicio";
                    break;                         
                case INT_TBL_DAT_COS_TOT_ITM:
                    strMsg="Costo Total";
                    break;    
                case INT_TBL_DAT_VAL_TOT_ITM:
                    strMsg="Total";
                    break;                      
                case INT_TBL_DAT_PES_UNI_ACT:
                    strMsg="Peso unitario del item en Kilogramos (Peso actual)";
                    break;  
                case INT_TBL_DAT_COS_UNI_ACT:
                    strMsg="Costo Unitario (Costo Actual)";
                    break;       
                case INT_TBL_DAT_EST_REG_DOC:
                    strMsg="Estado del documento";
                    break;     

                 case INT_TBL_DAT_NOM_USR_ING:
                    strMsg="Usuario de Ingreso";
                    break;
                 case INT_TBL_DAT_NOM_USR_VEN:
                    strMsg="Vendedor configurado en la Factura";
                    break;
                  case INT_TBL_DAT_NOM_USR_CONF:
                    strMsg="Configuración Actual";
                    break;
                 case INT_TBL_DAT_NOM_USR_NEXT:
                    strMsg="Configuracón proximo mes";
                    break;                        
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * ToolTips de Jtable: Locales.
     */ 
    private class ZafMouMotAdaLoc extends MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDatLoc.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_CODEMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOMEMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_CODLOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOMLOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatLoc.getTableHeader().setToolTipText(strMsg);
        }
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
        panFecDoc = new javax.swing.JPanel();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblDatLoc = new javax.swing.JTable();
        panTipDoc = new javax.swing.JPanel();
        spnTipDoc = new javax.swing.JScrollPane();
        tblDatTipDoc = new javax.swing.JTable();
        chkVenCli = new javax.swing.JCheckBox();
        chkVenRel = new javax.swing.JCheckBox();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblItm = new javax.swing.JLabel();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblDesItm = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblHasItm = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 80));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panGen.add(panFecDoc, java.awt.BorderLayout.NORTH);

        spnFil.setPreferredSize(new java.awt.Dimension(0, 400));
        spnFil.setRequestFocusEnabled(false);

        panFil.setPreferredSize(new java.awt.Dimension(0, 450));
        panFil.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 10, 400, 20);

        optFil.setText("Sólo los  documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 30, 400, 20);
        optFil.getAccessibleContext().setAccessibleParent(spnFil);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Locales"));
        panLoc.setLayout(new java.awt.BorderLayout());

        tblDatLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblDatLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        panFil.add(panLoc);
        panLoc.setBounds(10, 60, 653, 123);

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

        panFil.add(panTipDoc);
        panTipDoc.setBounds(10, 190, 360, 110);
        panTipDoc.getAccessibleContext().setAccessibleName("Tipos de documentos");

        chkVenCli.setSelected(true);
        chkVenCli.setText("Ventas a Clientes");
        chkVenCli.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkVenCliStateChanged(evt);
            }
        });
        panFil.add(chkVenCli);
        chkVenCli.setBounds(370, 200, 200, 20);

        chkVenRel.setText("Ventas a empresas relacionadas");
        chkVenRel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkVenRelStateChanged(evt);
            }
        });
        panFil.add(chkVenRel);
        chkVenRel.setBounds(370, 220, 290, 20);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(10, 310, 140, 20);

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
        panFil.add(txtCodCli);
        txtCodCli.setBounds(160, 310, 60, 20);

        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        panFil.add(txtIdeCli);
        txtIdeCli.setBounds(220, 310, 110, 20);

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
        panFil.add(txtNomCli);
        txtNomCli.setBounds(330, 310, 310, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 310, 20, 20);

        lblVen.setText("Vendedor/Comprador:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(10, 330, 140, 20);

        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(160, 330, 84, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(244, 330, 396, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 330, 20, 20);

        lblItm.setText("Item:");
        panFil.add(lblItm);
        lblItm.setBounds(10, 350, 120, 20);

        txtCodAltItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusLost(evt);
            }
        });
        txtCodAltItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmActionPerformed(evt);
            }
        });
        panFil.add(txtCodAltItm);
        txtCodAltItm.setBounds(160, 350, 84, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(244, 350, 396, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 350, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmDesHas.setLayout(null);

        lblDesItm.setText("Desde:");
        panItmDesHas.add(lblDesItm);
        lblDesItm.setBounds(12, 20, 60, 16);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(60, 20, 100, 20);

        lblHasItm.setText("Hasta:");
        panItmDesHas.add(lblHasItm);
        lblHasItm.setBounds(190, 20, 60, 16);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(250, 20, 100, 20);

        panFil.add(panItmDesHas);
        panItmDesHas.setBounds(20, 380, 370, 48);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmTer.setLayout(null);

        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(12, 20, 110, 16);

        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(130, 20, 92, 20);

        panFil.add(panItmTer);
        panItmTer.setBounds(420, 380, 230, 48);

        lblEstReg.setText("Estado del documento:");
        lblEstReg.setToolTipText("Estado del registro:");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(380, 250, 140, 16);

        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panFil.add(cboEstReg);
        cboEstReg.setBounds(520, 250, 130, 20);

        spnFil.setViewportView(panFil);
        panFil.getAccessibleContext().setAccessibleParent(spnFil);

        panGen.add(spnFil, java.awt.BorderLayout.CENTER);
        spnFil.getAccessibleContext().setAccessibleParent(tabFrm);

        tabFrm.addTab("Filtro", panGen);

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

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

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            
            cboEstReg.setSelectedIndex(0);
            optFil.setSelected(false);
        }
        else
            optFil.setSelected(true);
            
    }//GEN-LAST:event_optTodStateChanged

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        if (cboEstReg.getSelectedIndex() > 0) 
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
        {
            optTod.setSelected(false);
        }
        else
            optTod.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
        else
            optFil.setSelected(true);
    }//GEN-LAST:event_optTodActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
                blnCon = true;
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else 
            {
                blnCon = false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strNomCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strNomCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtNomCli.setText(strNomCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) 
        {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } 
            else
            {
                mostrarVenConVen(1);
            }
        } 
        else
        {
            txtCodVen.setText(strCodVen);
        }
        
        if (txtCodVen.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen)) 
        {
            if (txtNomVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } 
            else
            {
                mostrarVenConVen(2);
            }
        } 
        else
        {
            txtNomVen.setText(strNomVen);
        }
        
        if (txtNomVen.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }  
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt = txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAltItm.getText().equals(""))
            {
                txtCodAltItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
        {
            txtCodAltItm.setText(strCodAlt);
        }

        if (txtCodAltItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm = txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodAltItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
        {
            txtNomItm.setText(strNomItm);
        }

        if (txtNomItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if (txtCodAltItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        if (txtCodAltItmDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItm.setText("");
            txtNomItm.setText("");
            if (txtCodAltItmHas.getText().length() == 0)
            {
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
            }
        }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        if (txtCodAltItmHas.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItm.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItm.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli = txtIdeCli.getText();
        txtIdeCli.selectAll();
    }//GEN-LAST:event_txtIdeCliFocusGained

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli))
        {
            if (txtIdeCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
        txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtIdeCli.getText().length()>0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtIdeCliFocusLost

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
    }//GEN-LAST:event_txtIdeCliActionPerformed

    private void chkVenCliStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkVenCliStateChanged
        if(!chkVenCli.isSelected()) 
            chkVenRel.setSelected(true);
    }//GEN-LAST:event_chkVenCliStateChanged

    private void chkVenRelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkVenRelStateChanged
        if(!chkVenRel.isSelected())  
            chkVenCli.setSelected(true);
    }//GEN-LAST:event_chkVenRelStateChanged
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butVen;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JCheckBox chkVenCli;
    private javax.swing.JCheckBox chkVenRel;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDesItm;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblHasItm;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTipDoc;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTipDoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatLoc;
    private javax.swing.JTable tblDatTipDoc;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estaría informado en todo momento
     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        @Override
        public void run()
        {
            if (!cargarDetReg(sqlConFil()))
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    
    private void exitForm() 
    {
        dispose();
    } 
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblLoc, intNumFilTblTipDoc, i, j;
        //Validar que esté seleccionado al menos un local.
        intNumFilTblLoc=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblLoc; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHKSEL))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un Local.<BR>Seleccione un Local y vuelva a intentarlo.</HTML>");
            tblDatLoc.requestFocus();
            return false;
        }
        
        //Validar que esté seleccionado al menos un Tipo de documento.
        intNumFilTblTipDoc=objTblModTipDoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblTipDoc; j++)
        {
            if (objTblModTipDoc.isChecked(j, INT_TBL_TIPDOC_CHKSEL))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un Tipo de documento.<BR>Seleccione un Tipo de documento y vuelva a intentarlo.</HTML>");
            tblDatTipDoc.requestFocus();
            return false;
        }
        
        return true;
    }
    
    
    private String sqlConFil() 
    {
        String sqlFil = "";
        String strLoc="", strTipDoc="", strTerItm="";
        boolean blnLoc=false, blnTipDoc=false, blnTerItm=false;

        //<editor-fold defaultstate="collapsed" desc="/* Filtro: Tipo de documento. */">
        for (int j = 0; j < tblDatTipDoc.getRowCount(); j++) 
        {
            if (tblDatTipDoc.getValueAt(j, INT_TBL_TIPDOC_CHKSEL).toString().equals("true")) 
            {
                if(!blnTipDoc){ //Si es Primera vez que ingresa.
                    strTipDoc+=" a.co_tipDoc in ( ";
                }
                else{
                    strTipDoc+=", ";
                }
                strTipDoc+= tblDatTipDoc.getValueAt(j, INT_TBL_TIPDOC_CODTIP).toString() ;
                blnTipDoc=true;
            }
        }
        if (blnTipDoc)  {
            strTipDoc+= " ) ";
            sqlFil+= strTipDoc;
        }
        //</editor-fold>
        
        //Fecha del Documento.
        if(objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil += " AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil += " AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil += " AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
        
        //Locales Seleccionados.
        for (int j = 0; j < tblDatLoc.getRowCount(); j++) 
        {
            if (tblDatLoc.getValueAt(j, INT_TBL_LOC_CHKSEL).toString().equals("true")) 
            {
                if(!blnLoc) //Primera vez que ingresa.
                {
                    strLoc+=" AND ( ";
                }
                else 
                {
                    strLoc+=" OR ";
                }
                strLoc+=" (a.co_emp=" + tblDatLoc.getValueAt(j, INT_TBL_LOC_CODEMP).toString() + " and a.co_loc=" + tblDatLoc.getValueAt(j, INT_TBL_LOC_CODLOC).toString() + ") ";
                blnLoc=true;
            }
        }
        if (blnLoc)
        {
            strLoc+= " ) ";
            sqlFil+= strLoc;
        }
        
        //Filtro: Relacionadas.     
        if (! (chkVenRel.isSelected() && chkVenCli.isSelected()) ) 
        {
            if (!chkVenCli.isSelected()) {
                sqlFil += " AND a4.co_empGrp IS NOT NULL ";
            }   
            if (!chkVenRel.isSelected()) {
              sqlFil += " AND a4.co_empGrp IS NULL ";//OKKK
            }   
        }   
        
        //Otros Filtros
        if (txtIdeCli.getText().length()>0){
            sqlFil+=" AND trim(a4.tx_ide)='"+txtIdeCli.getText()+"'";
        }
        
        if (txtCodVen.getText().length() > 0) {
            sqlFil += " AND a.co_com = " + txtCodVen.getText().replaceAll("'", "''") + "";
        }
        
        if (cboEstReg.getSelectedIndex() > 0)  {
            sqlFil += " AND a.st_reg = '" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
        }
        
        if (txtCodAltItm.getText().length()>0)
            sqlFil+=" AND a1.tx_codalt='"+txtCodAltItm.getText()+"'";
        if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
            sqlFil+=" AND ((LOWER(a1.tx_codalt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codalt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
        
        //Items Terminan Con.
        if (!txtCodAltItmTer.getText().equals("")) 
        {
            //blnTerItm=false;
            String[] result = txtCodAltItmTer.getText().split(",");
            strTerItm = " AND  ( ";
            for (int x = 0; x < result.length; x++)
            {
                if (blnTerItm) 
                {
                    strTerItm += " or ";
                }
                strTerItm += "  upper(a1.tx_codalt) like '%" + result[x].toString().toUpperCase() + "'";
                blnTerItm=true;
            }
            strTerItm+= " ) ";
            sqlFil+=strTerItm;
        }

        return sqlFil;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a3.tx_nom as tx_nomLoc, a.co_tipDoc, a2.tx_desCor as desCorTipDoc, a2.tx_desLar as desLarTipDoc, ";
                strSQL+="        a.co_doc, a.ne_numDoc, a.fe_doc, a.co_cli, a4.tx_ide, a.tx_nomCli, a.co_forPag, a.tx_desForPag, ";
                strSQL+="        a1.co_itm, a1.tx_CodAlt, a1.tx_nomItm, a1.tx_uniMed, abs(a1.nd_Can) AS nd_can, a1.nd_cosUni, a1.nd_cosTot, a1.nd_preUni, a1.nd_porDes, ";
                strSQL+="        a1.nd_tot, a.st_reg, a5.nd_cosUni AS nd_cosAct, a5.nd_pesItmKgr as nd_pesItm, a5.st_Ser, ";
                strSQL+="        a.co_usrIng,a6.tx_nom as tx_nomUsrIng, a.co_com,a7.tx_nom as tx_nomVen, a8.co_ven,a88.tx_nom as tx_nomVenConf,  ";
                strSQL+="        a9.co_usrVen as co_venNext,a10.tx_nom as tx_nomVenNext  ";
                strSQL+=" FROM tbm_CabMovInv as a ";
                strSQL+=" INNER JOIN tbm_CabTipDoc as a2 ON (a.co_Emp=a2.co_Emp and a.co_loc=a2.co_loc and a.co_tipDoc=a2.co_tipDoc) ";
                strSQL+=" INNER JOIN tbm_DetMovInv as a1 ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipDoc=a1.co_TipDoc and a.co_doc=a1.co_doc) ";
                strSQL+=" INNER JOIN tbm_loc as a3 ON (a.co_emp=a3.co_emp and a.co_loc=a3.co_loc) ";
                strSQL+=" INNER JOIN tbm_Cli as a4 ON (a.co_emp=a4.co_emp and a.co_cli=a4.co_cli) ";
                strSQL+=" INNER JOIN tbm_inv as a5 ON (a1.co_emp=a5.co_emp AND a1.co_itm=a5.co_itm)  ";
                
                strSQL+=" LEFT OUTER JOIN tbm_usr as a6 ON (a.co_usrIng=a6.co_usr) ";
                strSQL+=" LEFT OUTER JOIN tbm_usr as a7 ON (a.co_com=a7.co_usr) ";
                strSQL+=" LEFT OUTER JOIN tbr_cliLoc as a8 ON (a.co_emp=a8.co_emp AND a.co_loc=a8.co_loc AND a.co_cli=a8.co_cli) ";
                strSQL+=" LEFT OUTER JOIN tbm_usr as a88 ON (a8.co_Ven=a88.co_usr)";
                strSQL+=" LEFT OUTER JOIN (  ";
                strSQL+="               SELECT a1.co_emp, a1.co_loc,a1.co_cli, a1.co_cfg, a1.co_usrVen, a1.tx_obs1, a1.fe_ing, a1.co_usrIng, a1.tx_comIng \n";
                strSQL+="               FROM tbm_preCfgCliLoc as a1 \n";
                strSQL+="               INNER JOIN ( \n";
                strSQL+="                   SELECT a3.co_emp, a3.co_loc, a3.co_cli, MAX(a3.co_cfg) as co_cfg   \n";
                strSQL+="                   FROM tbm_preCfgCliLoc as a3   \n";
                strSQL+="                   GROUP BY  a3.co_emp, a3.co_loc, a3.co_cli \n";
                strSQL+="               ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cli=a2.co_cli AND a1.co_cfg=a2.co_cfg) \n";
                strSQL+=" ) as a9 ON (a.co_emp=a9.co_emp AND a.co_loc=a9.co_loc AND a.co_cli=a9.co_cli) ";
                strSQL+=" LEFT OUTER JOIN tbm_usr as a10 ON (a9.co_usrVen=a10.co_usr)";
                
                strSQL+=" WHERE "+strFil;
                strSQL+=" ORDER BY a.co_emp, a.co_loc, a.co_tipDoc, a.fe_doc, a.ne_numDoc ";
                System.out.println("cargarDetReg: "+strSQL);   
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_NOM_LOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,rst.getString("desCorTipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,rst.getString("desLarTipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));                        
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_COD_FOR_PAG_CLI,rst.getString("co_forPag"));
                        vecReg.add(INT_TBL_DAT_NOM_FOR_PAG_CLI,rst.getString("tx_desForPag"));
                        
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("tx_CodAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,rst.getString("tx_uniMed"));
                        
                        vecReg.add(INT_TBL_DAT_CAN_MOV_ITM,rst.getString("nd_can"));
                        vecReg.add(INT_TBL_DAT_COS_UNI_ITM,rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DAT_PRE_UNI_ITM,rst.getString("nd_preUni"));
                        vecReg.add(INT_TBL_DAT_POR_DES_ITM,rst.getString("nd_porDes"));
                        vecReg.add(INT_TBL_DAT_EST_ITM_SER,rst.getString("st_Ser"));
                        vecReg.add(INT_TBL_DAT_COS_TOT_ITM,rst.getString("nd_cosTot"));
                        vecReg.add(INT_TBL_DAT_VAL_TOT_ITM,rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_PES_UNI_ACT,rst.getString("nd_pesItm"));
                        vecReg.add(INT_TBL_DAT_COS_UNI_ACT,rst.getString("nd_cosAct"));
                        vecReg.add(INT_TBL_DAT_EST_REG_DOC,rst.getString("st_reg"));
                        
                        vecReg.add(INT_TBL_DAT_COD_USR_ING,rst.getString("co_usrIng"));
                        vecReg.add(INT_TBL_DAT_NOM_USR_ING,rst.getString("tx_nomUsrIng"));
                        vecReg.add(INT_TBL_DAT_COD_USR_VEN,rst.getString("co_com"));
                        vecReg.add(INT_TBL_DAT_NOM_USR_VEN,rst.getString("tx_nomVen"));
                        vecReg.add(INT_TBL_DAT_COD_USR_CONF,rst.getString("co_ven"));
                        vecReg.add(INT_TBL_DAT_NOM_USR_CONF,rst.getString("tx_nomVenConf"));
                        vecReg.add(INT_TBL_DAT_COD_USR_NEXT,rst.getString("co_venNext")==null?"":rst.getString("co_venNext") );
                        vecReg.add(INT_TBL_DAT_NOM_USR_NEXT,rst.getString("tx_nomVenNext")==null?"":rst.getString("tx_nomVenNext") );                        
                        
                        vecDat.add(vecReg);
                    }
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
}


