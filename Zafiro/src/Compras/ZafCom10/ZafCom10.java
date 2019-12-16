/*
 * ZafCom10.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package Compras.ZafCom10;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom10 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                     //Código del local.
    static final int INT_TBL_DAT_COD_TIP_DOC=3;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=4;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=5;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=6;                     //Código del documento (Sistema).
    static final int INT_TBL_DAT_NUM_DOC=7;                     //Número del documento (Preimpreso).
    static final int INT_TBL_DAT_FEC_DOC=8;                     //Fecha del documento.
    static final int INT_TBL_DAT_COD_CLI=9;                     //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=10;                    //Nombre del cliente.
    static final int INT_TBL_DAT_COD_GRP_CLI=11;                //Código del grupo del cliente.
    static final int INT_TBL_DAT_NOM_GRP_CLI=12;                //Nombre del grupo del cliente.    
    static final int INT_TBL_DAT_COD_FOR_PAG=13;                //Código de la forma de pago.
    static final int INT_TBL_DAT_NOM_FOR_PAG=14;                //Nombre de la forma de pago.
    static final int INT_TBL_DAT_SUB_DOC=15;                    //Subtotal del documento.
    static final int INT_TBL_DAT_IVA_DOC=16;                    //IVA del documento.
    static final int INT_TBL_DAT_TOT_DOC=17;                    //Total del documento.
    static final int INT_TBL_DAT_EST_DOC=18;                    //Estado del documento.
    static final int INT_TBL_DAT_FEC_ING=19;                    //Fecha de ingreso.
    static final int INT_TBL_DAT_COD_USR_ING=20;                //Código del usuario de ingreso.
    static final int INT_TBL_DAT_USR_ING=21;                    //Usuario de ingreso.
    static final int INT_TBL_DAT_COM_ING=22;                    //Computadora de ingreso.
    static final int INT_TBL_DAT_FEC_MOD=23;                    //Fecha de última modificación.
    static final int INT_TBL_DAT_COD_USR_MOD=24;                //Código del usuario de última modificación.
    static final int INT_TBL_DAT_USR_MOD=25;                    //Usuario de última modificación.
    static final int INT_TBL_DAT_COM_MOD=26;                    //Computadora de última modificación.
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private Vector vecEstReg;                                   //Estado del registro.
    private Vector vecEstCon;                                   //Estado de confirmación.
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strCodCli, strIdeCli, strDesLarCli;          //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
   
    /**
     * Crea una nueva instancia de la clase ZafCom10.
     * @param obj El objeto ZafParSis.
     */
    public ZafCom10(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setTitulo("Fecha del documento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.11");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodTipDoc.setVisible(false);
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("A: Activo");
            cboEstReg.addItem("I: Anulado");
            cboEstReg.setSelectedIndex(0);
            //Configurar el combo "Estado de confirmación".
            vecEstCon=new Vector();
            vecEstCon.add("");
            vecEstCon.add("P");
            vecEstCon.add("E");
            vecEstCon.add("C");
            vecEstCon.add("D");
            cboEstCon.addItem("(Todos)");
            cboEstCon.addItem("P: Pendiente de confirmar");
            cboEstCon.addItem("E: En proceso de confirmación");
            cboEstCon.addItem("C: Confirmación completa de Inventario");
            cboEstCon.addItem("D: Diferencias de Inventario");
            cboEstCon.setSelectedIndex(0);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCli();
            configurarVenConVen();
            //Configurar los JTables.
            configurarTblLoc();
            configurarTblDat();
            cargarLoc();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(320);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Ordenar tabla
            objTblOrdLoc=new ZafTblOrd(tblLoc);
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
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
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(27);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Código");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_COD_GRP_CLI,"Cód.Grp.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_GRP_CLI,"Grupo Cliente");            
            vecCab.add(INT_TBL_DAT_COD_FOR_PAG,"Cód.For.Pag.");
            vecCab.add(INT_TBL_DAT_NOM_FOR_PAG,"Forma de pago");
            vecCab.add(INT_TBL_DAT_SUB_DOC,"Subtotal");
            vecCab.add(INT_TBL_DAT_IVA_DOC,"IVA");
            vecCab.add(INT_TBL_DAT_TOT_DOC,"Total");
            vecCab.add(INT_TBL_DAT_EST_DOC,"Est.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_ING,"Fec.Ing.");
            vecCab.add(INT_TBL_DAT_COD_USR_ING,"Cód.Usr.Ing.");
            vecCab.add(INT_TBL_DAT_USR_ING,"Usr.Ing.");
            vecCab.add(INT_TBL_DAT_COM_ING,"Com.Ing.");
            vecCab.add(INT_TBL_DAT_FEC_MOD,"Fec.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_COD_USR_MOD,"Cód.Usr.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_USR_MOD,"Usr.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_COM_MOD,"Com.Ult.Mod.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(157);
            tcmAux.getColumn(INT_TBL_DAT_COD_GRP_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_GRP_CLI).setPreferredWidth(100);            
            tcmAux.getColumn(INT_TBL_DAT_NOM_FOR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            }
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GRP_CLI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_FOR_PAG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_MOD, tblDat);
            switch (objParSis.getCodigoMenu())
            {
                case 852: //Listado de documentos de ventas...
                case 238: //Listado de documentos de inventario...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!(objPerUsr.isOpcionEnabled(2504) || objPerUsr.isOpcionEnabled(2505)))
                    {
                        //2504: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                        //2505: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_ING, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_ING, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_ING, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_MOD, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_MOD, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_MOD, tblDat);
                    }
                    break;
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_SUB_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IVA_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_SUB_DOC, INT_TBL_DAT_IVA_DOC, INT_TBL_DAT_TOT_DOC};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_COD_GRP_CLI:
                    strMsg="Código del grupo del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_GRP_CLI:
                    strMsg="Nombre del grupo del cliente/proveedor";
                    break;                    
                case INT_TBL_DAT_COD_FOR_PAG:
                    strMsg="Código de la forma de pago";
                    break;
                case INT_TBL_DAT_NOM_FOR_PAG:
                    strMsg="Forma de pago";
                    break;
                case INT_TBL_DAT_SUB_DOC:
                    strMsg="Subtotal del documento";
                    break;
                case INT_TBL_DAT_IVA_DOC:
                    strMsg="IVA";
                    break;
                case INT_TBL_DAT_TOT_DOC:
                    strMsg="Total del documento";
                    break;
                case INT_TBL_DAT_EST_DOC:
                    strMsg="Estado del documento";
                    break;
                case INT_TBL_DAT_FEC_ING:
                    strMsg="Fecha de ingreso";
                    break;
                case INT_TBL_DAT_COD_USR_ING:
                    strMsg="Código del usuario de ingreso";
                    break;
                case INT_TBL_DAT_USR_ING:
                    strMsg="Usuario de ingreso";
                    break;
                case INT_TBL_DAT_COM_ING:
                    strMsg="Computadora de ingreso";
                    break;
                case INT_TBL_DAT_FEC_MOD:
                    strMsg="Fecha de última modificación";
                    break;
                case INT_TBL_DAT_COD_USR_MOD:
                    strMsg="Código del usuario de última modificación";
                    break;
                case INT_TBL_DAT_USR_MOD:
                    strMsg="Usuario de última modificación";
                    break;
                case INT_TBL_DAT_COM_MOD:
                    strMsg="Computadora de última modificación";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    /**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    }
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc)";
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a3.co_mnu=" + objParSis.getCodigoMenu() + " AND a3.co_usr=" + objParSis.getCodigoUsuario();
                    }
                    else
                    {
                        strSQL+=" WHERE a3.co_emp=" + intCodEmp + " AND a3.co_mnu=" + objParSis.getCodigoMenu() + " AND a3.co_usr=" + objParSis.getCodigoUsuario();
                    }
                    strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
                vecDat.clear();
                blnMarTodChkTblEmp=false;
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else
            {
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a2.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" GROUP BY a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                    strSQL+=" ORDER BY a1.tx_desCor";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" ORDER BY a1.tx_desCor";
                }
            }
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
       }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("380");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
            if (objParSis.getCodigoUsuario()==1)
            {
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT ' ' AS co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_reg='A'";
                    switch (objParSis.getCodigoMenu())
                    {
                        case 238: //Compras: Listado de documentos de inventario (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_prv='S'";
                            break;
                        case 852: //Ventas: Listado de documentos de ventas (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_cli='S'";
                            break;
                    }
                    strSQL+=" GROUP BY a1.tx_ide, a1.tx_nom";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg='A'";
                    switch (objParSis.getCodigoMenu())
                    {
                        case 238: //Compras: Listado de documentos de inventario (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_prv='S'";
                            break;
                        case 852: //Ventas: Listado de documentos de ventas (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_cli='S'";
                            break;
                    }
                    strSQL+=" ORDER BY a1.tx_nom";
                }
            }
            else
            {
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT '' AS co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT co_emp, co_loc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" GROUP BY co_emp, co_loc";
                    strSQL+=" ORDER BY co_emp, co_loc";
                    strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc)";
                    strSQL+=" WHERE a1.st_reg='A'";
                    switch (objParSis.getCodigoMenu())
                    {
                        case 238: //Compras: Listado de documentos de inventario (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_prv='S'";
                            break;
                        case 852: //Ventas: Listado de documentos de ventas (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_cli='S'";
                            break;
                    }
                    strSQL+=" GROUP BY a1.tx_ide, a1.tx_nom";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT co_emp, co_loc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" GROUP BY co_emp, co_loc";
                    strSQL+=" ORDER BY co_emp, co_loc";
                    strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc)";
                    strSQL+=" WHERE a1.st_reg='A'";
                    switch (objParSis.getCodigoMenu())
                    {
                        case 238: //Compras: Listado de documentos de inventario (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_prv='S'";
                            break;
                        case 852: //Ventas: Listado de documentos de ventas (Formato 1: Cabecera)...
                            strSQL+=" AND a1.st_cli='S'";
                            break;
                    }
                    strSQL+=" GROUP BY a1.co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
            }
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()
    {
        boolean blnRes=true;
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
            arlAncCol.add("360");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL+=" FROM tbm_usr AS a1";
            strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        sppFil = new javax.swing.JSplitPane();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblEstCon = new javax.swing.JLabel();
        cboEstCon = new javax.swing.JComboBox();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtIdeCli = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
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
        setTitle("Título de la ventana");
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setBorder(null);

        sppFil.setBorder(null);
        sppFil.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppFil.setResizeWeight(0.14);
        sppFil.setToolTipText("");
        sppFil.setOneTouchExpandable(true);
        sppFil.setPreferredSize(new java.awt.Dimension(464, 360));

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setPreferredSize(new java.awt.Dimension(464, 60));
        panLoc.setLayout(new java.awt.BorderLayout());

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        sppFil.setTopComponent(panLoc);

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 76, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 96, 400, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(24, 116, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(112, 116, 32, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(144, 116, 56, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 116, 440, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(640, 116, 20, 20);

        lblEstReg.setText("Estado del documento:");
        lblEstReg.setToolTipText("Estado del documento");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(24, 176, 120, 20);
        panFil.add(cboEstReg);
        cboEstReg.setBounds(144, 176, 264, 20);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(24, 136, 120, 20);

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
        txtCodCli.setBounds(144, 136, 56, 20);

        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(300, 136, 340, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 136, 20, 20);

        lblEstCon.setText("Estado de confirmación:");
        lblEstCon.setToolTipText("Estado de confirmación");
        panFil.add(lblEstCon);
        lblEstCon.setBounds(24, 196, 120, 20);
        panFil.add(cboEstCon);
        cboEstCon.setBounds(144, 196, 264, 20);

        lblVen.setText("Vendedor/Comprador:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(24, 156, 120, 20);

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
        txtCodVen.setBounds(144, 156, 56, 20);

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
        txtNomVen.setBounds(200, 156, 440, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 156, 20, 20);

        txtIdeCli.setToolTipText("Identificación del cliente/proveedor");
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        panFil.add(txtIdeCli);
        txtIdeCli.setBounds(200, 136, 100, 20);

        sppFil.setBottomComponent(panFil);

        spnFil.setViewportView(sppFil);

        tabFrm.addTab("Filtro", spnFil);

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
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
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

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(3);
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
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

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
            cboEstReg.setSelectedIndex(0);
            cboEstCon.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (isCamVal())
        {
            //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
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

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
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
            txtCodVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
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
            txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
    }//GEN-LAST:event_txtIdeCliActionPerformed

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli=txtIdeCli.getText();
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
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtIdeCliFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JComboBox cboEstCon;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEstCon;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JSplitPane sppFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar que esté seleccionado al menos un local.
        if (!objTblModLoc.isCheckedAnyRow(INT_TBL_LOC_CHK))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Es obligatorio seleccionar al menos un local.<BR>Por favor corrija y vuelva a intentarlo.</HTML>");
            if (objTblModLoc.getRowCount()>0)
            {
                tblLoc.setRowSelectionInterval(0, 0);
                tblLoc.changeSelection(0, INT_TBL_LOC_CHK, false, false);
                tblLoc.requestFocus();
            }
            return false;
        }
        return true;
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
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
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFilTblLoc, i, j;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                //Obtener los locales a consultar.
                intNumFilTblLoc=objTblModLoc.getRowCountTrue();
                i=0;
                strAux="";
                for (j=0; j<intNumFilTblLoc; j++)
                {
                    if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                    {
                        if (i==0)
                            strAux+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        else
                            strAux+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        i++;
                    }
                }
                if (!strAux.equals(""))
                {
                    strConSQL+=" AND (" + strAux + ")";
                }
                if (txtCodTipDoc.getText().length()>0)
                    strConSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                if (txtCodCli.getText().trim().length()>0)
                    strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                else
                {
                    if (txtIdeCli.getText().length()>0)
                    {
                        strConSQL+=" AND a1.tx_ruc='" + txtIdeCli.getText() + "'";
                    }
                }
                if (txtCodVen.getText().length()>0)
                    strConSQL+=" AND a1.co_com=" + txtCodVen.getText();
                
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strConSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQL+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                if (cboEstReg.getSelectedIndex()>0)
                    strConSQL+=" AND a1.st_reg='" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
                if (cboEstCon.getSelectedIndex()>0)
                    strConSQL+=" AND a1.st_conInv='" + vecEstCon.get(cboEstCon.getSelectedIndex()) + "'";
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                strSQL+="      , a1.co_cli, a1.tx_nomCli, a7.co_grp as co_grpCli, a7.tx_nom AS tx_nomGrpCli, a1.co_forPag, a1.tx_desForPag, a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.st_reg";
                strSQL+="      , a1.fe_ing, a1.co_usrIng, a4.tx_usr AS a4_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a5.tx_usr AS a5_tx_usr, a1.tx_comMod";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()!=1) {
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                }
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a4 ON (a1.co_usrIng=a4.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_usrMod=a5.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a6 ON (a1.co_emp=a6.co_emp AND a1.co_cli=a6.co_cli)"; 
                strSQL+=" LEFT OUTER JOIN tbm_grpCli AS a7 ON (a6.co_emp=a7.co_emp AND a6.co_grp=a7.co_grp)"; /*Solicitado por Werner Campoverde: Mostrar Grupo de Clientes.*/
                strSQL+=" WHERE 1=1";
                strSQL+=strConSQL;
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()!=1)  {
                    strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                }
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.fe_doc, a1.ne_numDoc";
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
                        vecReg.add(INT_TBL_DAT_COD_EMP,      rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,      rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,  rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,  rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,  rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,      rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,      rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,      rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,      rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,      rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_COD_GRP_CLI,  rst.getString("co_grpCli"));
                        vecReg.add(INT_TBL_DAT_NOM_GRP_CLI,  rst.getString("tx_nomGrpCli"));
                        vecReg.add(INT_TBL_DAT_COD_FOR_PAG,  rst.getString("co_forPag"));
                        vecReg.add(INT_TBL_DAT_NOM_FOR_PAG,  rst.getString("tx_desForPag"));
                        vecReg.add(INT_TBL_DAT_SUB_DOC,      rst.getString("nd_sub"));
                        vecReg.add(INT_TBL_DAT_IVA_DOC,      rst.getString("nd_valIva"));
                        vecReg.add(INT_TBL_DAT_TOT_DOC,      rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_EST_DOC,      rst.getString("st_reg"));
                        switch (objParSis.getCodigoMenu())
                        {
                            case 852: //Listado de documentos de ventas...
                            case 238: //Listado de documentos de inventario...
                                //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                                if (objPerUsr.isOpcionEnabled(2504) || objPerUsr.isOpcionEnabled(2505))
                                {
                                    //2504: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                                    //2505: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                                    vecReg.add(INT_TBL_DAT_FEC_ING,      rst.getString("fe_ing"));
                                    vecReg.add(INT_TBL_DAT_COD_USR_ING,  rst.getString("co_usrIng"));
                                    vecReg.add(INT_TBL_DAT_USR_ING,      rst.getString("a4_tx_usr"));
                                    vecReg.add(INT_TBL_DAT_COM_ING,      rst.getString("tx_comIng"));
                                    vecReg.add(INT_TBL_DAT_FEC_MOD,      rst.getString("fe_ultMod"));
                                    vecReg.add(INT_TBL_DAT_COD_USR_MOD,  rst.getString("co_usrMod"));
                                    vecReg.add(INT_TBL_DAT_USR_MOD,      rst.getString("a5_tx_usr"));
                                    vecReg.add(INT_TBL_DAT_COM_MOD,      rst.getString("tx_comMod"));
                                }
                                else
                                {
                                    vecReg.add(INT_TBL_DAT_FEC_ING,     null);
                                    vecReg.add(INT_TBL_DAT_COD_USR_ING, null);
                                    vecReg.add(INT_TBL_DAT_USR_ING,     null);
                                    vecReg.add(INT_TBL_DAT_COM_ING,     null);
                                    vecReg.add(INT_TBL_DAT_FEC_MOD,     null);
                                    vecReg.add(INT_TBL_DAT_COD_USR_MOD, null);
                                    vecReg.add(INT_TBL_DAT_USR_MOD,     null);
                                    vecReg.add(INT_TBL_DAT_COM_MOD,     null);
                                }
                                break;
                        }
                        vecDat.add(vecReg);
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
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Calcular totales.
                objTblTot.calcularTotales();
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
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if (!((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow()==-1)))
            {
                strAux="Contabilidad.ZafCon01.ZafCon01";
                objParSis.setNombreMenu("Plan de cuentas...");
                if (!strAux.equals(""))
                    invocarClase(strAux);
            }       
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[2];
            objCla[0]=objParSis.getClass();
            objCla[1]=new Integer(0).getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[2];
            objObj[0]=objParSis;
//            objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SIS).toString());
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.setVisible(true);
        }
        catch (ClassNotFoundException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
}
