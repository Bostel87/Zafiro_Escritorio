/*
 * ZafMae14.java 
 *
 * Created on 21 de enero de 2005, 9:21
 * Clase que sirve para almacenar los registros de tipos de documentos
 */

package Maestros.ZafMae14;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod ;
import Librerias.ZafUtil.ZafCtaCtb;
import Librerias.ZafUtil.ZafTipDoc;
import java.util.ArrayList;

/**
 *
 * @author kcerezo
 */

public class ZafMae14 extends javax.swing.JInternalFrame 
{
    //Constantes de la tabla de detalle tipo de documento
    private final int INT_TBL_LINEA = 0;
    private final int INT_TBL_CTA = 1;
    private final int INT_TBL_BUT = 2;
    private final int INT_TBL_CODCTA = 3;
    private final int INT_TBL_DSC = 4;
    private final int INT_TBL_CHK_PRE = 5;
    private final int INT_TBL_NAT_CTA = 6;
    private final int INT_TBL_ESTADO = 7;
    private final int INT_TBL_PRETXT = 8;
    
    //Variables
    Connection conCab;
    Statement stmCab;
    ResultSet rstCab;                                 //Variable para manipular registro de la tabla en ejecución

    JOptionPane oppMsg;                               //Objeto de tipo OptionPane para presentar Mensajes
    private ToolBar objTooBar;                        //Objeto de tipo ToolBar para poder manipular la clase ZafToolBar
    private ZafUtil objUti;                           //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    private ZafParSis objZafParSis;                   //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
    private ZafColNumerada objColEnu, objColNum;      //Objeto que me permitira obtener la numeraciï¿½n de la primera fila del JTable  
    private ZafPopupMenu objPopMnu;                   // Para el menu emergente de la tabla
    private LisTxt objLisCmb;                         // Instancia de clase que detecta cambios
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                      //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;          //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;          //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;          //Editor: JTextField en celda.
    private ZafTblCelEdiBut objTblCelEdiBut;          //Editor: JButton en celda.   
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon;    //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon2;   //Editor: JTextField de consulta en celda.
    private ZafTblCelRenChk objTblCelRenChk;          //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;          //Editor: JButton en celda.    
    private ZafTipDoc objTipDoc;
    private ZafCtaCtb objCtaCtb;
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    
    int intIndSelected = 0;
    Vector vecCab, vecData;
    private boolean blnChangeData = false;
    boolean blnCmb = false;                           //Detecta si se realizaron cambios en el documento dentro de las caja de texto
    boolean blnCln = false;                           //Si los TextField estan vacios
    private int intPosChk = -1;                       //Variable que almacena la posicion de la fila en la tabla que tiene la cuenta predeterminada
    private ZafVenCon vcoTipDoc;                      //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                         //Ventana de consulta "Cuentas Contables".
    private String strBeforeValue, strAfterValue;
    String strSql;                                    //Variable de tipo cadena en la cual se almacenará el Query
    String strSqlAux;                                 //Variable de tipo cadena en la cual se almacenará el Query
    String strMsg;                                    //Variable de tipo cadena para enviar los mensajes por pantalla
    String strFil;                                    //EL filtro de la Consulta actual
    String strAux;                                    //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    private String strCodTipDoc, strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCodDeb, strCodHab, strDesCtaDeb, strDesCtaHab;  //Contenido del campo al obtener el foco.
    final String strTit = "Mensaje del Sistema Zafiro";               //Constante del titulo del mensaje
    private final String VERSION = " v0.4";                           //Version del Sistema Zafiro

    /**
     * Creates new form ZafMae14
     */
    public ZafMae14(ZafParSis obj) 
    {
        initComponents();
        addListenerCambios();
        try
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objTooBar = new ToolBar(this);
            objUti = new ZafUtil();
            objTipDoc = new ZafTipDoc(objZafParSis);
            objCtaCtb = new ZafCtaCtb(objZafParSis);
            oppMsg = new JOptionPane(); //Inicializando el objeto para los mensajes
            bgrOpc.add(optIng);         //Agregando al ButtonGroup el optIngreso, se hace esto para que un solo radio button por grupo sea seleccionado.
            bgrOpc.add(optEgr);         //Agregando al ButtonGroup el optEgreso
            txaObs1.setLineWrap(true);
            txaObs1.setWrapStyleWord(true);
            spnObs1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            spnObs1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            txtCod.setBackground(objZafParSis.getColorCamposSistema());
            txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            this.setTitle(objZafParSis.getNombreMenu() + VERSION);
            txtCodHab.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCodDeb.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtCod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            txtDesCorTipDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            this.getContentPane().add(objTooBar, "South");

            //Inicializar objetos
            strAux = objZafParSis.getNombreMenu();
            lblTit.setText("Maestro "+strAux);
            
            Configurar_tabla();
            configurarVenConTipDoc();
            configurarVenCta();
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean Configurar_tabla() 
    {
        boolean blnRes = false;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            Vector vecDat = new Vector();    //Almacena los datos
            Vector vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CTA, "Cod. Cuenta");
            vecCab.add(INT_TBL_BUT, "");
            vecCab.add(INT_TBL_CODCTA, "Cuenta");
            vecCab.add(INT_TBL_DSC, "Descripcion");
            vecCab.add(INT_TBL_CHK_PRE, "Predeterminado");
            vecCab.add(INT_TBL_NAT_CTA, "Uso [Debe/Haber]");
            vecCab.add(INT_TBL_ESTADO, "");
            vecCab.add(INT_TBL_PRETXT, "");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

            new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CTA);
            arlAux.add("" + INT_TBL_NAT_CTA);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;
            //Configurar ZafTblMod: Establecer las columnas eleminadas
            arlAux = new java.util.ArrayList();
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux = null;

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposSistema());

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_CTA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CODCTA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUT).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DSC).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_CHK_PRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NAT_CTA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_PRETXT).setWidth(0);
            tcmAux.getColumn(INT_TBL_PRETXT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_PRETXT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_PRETXT).setPreferredWidth(0);
            //Configurar JTable: Ocultar columnas del sistema.
            /**
             * Estado : V:vacio.- si al momento de carga no tenia codigo
             * asociado N:Nuevo.- Si fue modificado despues de la carga y estaba
             * vacio E:Existe.- Si al momento de carga tenia codigo asociado
             * M:Modificado.- Si fue modificado despues de la carga y estaba
             * existe
             */
            tcmAux.getColumn(INT_TBL_ESTADO).setWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setResizable(false);

            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CTA);
            vecAux.add("" + INT_TBL_BUT);
            vecAux.add("" + INT_TBL_CHK_PRE);
            vecAux.add("" + INT_TBL_NAT_CTA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK_PRE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHK_PRE).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intPosChk = tblDat.getSelectedRow();
                    for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                        tblDat.setValueAt(new Boolean(false), i, INT_TBL_CHK_PRE);
                    }
                    tblDat.setValueAt(new Boolean(true), intPosChk, INT_TBL_CHK_PRE);
                }
            });

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_CODCTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DSC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NAT_CTA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######", true, true);
            tcmAux.getColumn(INT_TBL_CTA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            String strSQL = "";
            strSQL = "  select co_cta,tx_codcta,tx_deslar from tbm_placta  where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and tx_tipcta='D' and st_reg='A'";
            int intColVen[] = new int[3];
            intColVen[0] = 1;
            intColVen[1] = 2;
            intColVen[2] = 3;
            int intColTbl[] = new int[3];
            intColTbl[0] = INT_TBL_CTA;
            intColTbl[1] = INT_TBL_CODCTA;
            intColTbl[2] = INT_TBL_DSC;
            objTblCelEdiTxtCon = new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon("Listado Cuentas Contables", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL, "co_cta,tx_codcta,tx_deslar", "Codigo,Numero Cta, Descripcion", intColVen, intColTbl);
            objTblCelEdiTxtCon.setIndiceCampoBusqueda(0);
            objTblCelEdiTxtCon.setCampoBusqueda("co_cta", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtCon.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_CTA).setCellEditor(objTblCelEdiTxtCon);
            objTblCelEdiTxtCon = null;

            objTblCelEdiBut = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut("Listado Cuentas Contables", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL, "co_cta,tx_codcta,tx_deslar", "Codigo,Numero Cta, Descripcion", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_BUT).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut = null;
            intColVen = null;
            intColTbl = null;

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_NAT_CTA).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()) != null) {
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString();
                    } else {
                        strBeforeValue = "";
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if (tblDat.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()) != null) {
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString();
                    } else {
                        strAfterValue = "";
                    }
                    tblDat.setValueAt(strAfterValue.toUpperCase(), tblDat.getSelectedRow(), INT_TBL_NAT_CTA);
                    if (!strAfterValue.toUpperCase().equals("D") && !strAfterValue.toUpperCase().equals("H")) {
                        tblDat.setValueAt(strBeforeValue.toUpperCase(), tblDat.getSelectedRow(), INT_TBL_NAT_CTA);
                    }
                    blnChangeData = true;
                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis = new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux = null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }
        else 
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    private void cargarCabTipoDoc(int TipoDoc) 
    {
        objTipDoc.cargarTipoDoc(TipoDoc, false);
        txtCod.setText("" + objTipDoc.getco_tipdoc());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());
    }

    private void MensajeInf(String strMsg) 
    {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        obj.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    private void FndCta(String strBusqueda, int TipoBusqueda) 
    {
        try 
        {
            Librerias.ZafConsulta.ZafConsulta objFnd
                    = new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
                            " Codigo,Numero Cta, Descripcion", "co_cta,tx_codcta,tx_deslar",
                            " select co_cta,tx_codcta,tx_deslar from tbm_placta "
                            + " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and tx_tipcta='D' and st_reg='A'", strBusqueda,
                            objZafParSis.getStringConexion(),
                            objZafParSis.getUsuarioBaseDatos(),
                            objZafParSis.getClaveBaseDatos()
                    );
            objFnd.setTitle("Listado Cuentas");
            switch (TipoBusqueda) {
                case 1:
                    objFnd.setSelectedCamBus(0);
                    if (!objFnd.buscar("co_cta = " + (strBusqueda.equals("") ? "0" : strBusqueda)));
                    objFnd.show();
                    break;
                case 2:
                    objFnd.setSelectedCamBus(2);
                    if (!objFnd.buscar("tx_deslar ='" + strBusqueda + "'")) {
                        objFnd.show();
                    }
                    break;
                case 4:
                    objFnd.setSelectedCamBus(0);
                    if (!objFnd.buscar("co_cta = " + (strBusqueda.equals("") ? "0" : strBusqueda))) {
                        objFnd.show();
                    }
                    break;
                case 5:
                    objFnd.setSelectedCamBus(2);
                    if (!objFnd.buscar("tx_deslar ='" + strBusqueda + "'")) {
                        objFnd.show();
                    }
                    break;
                default:
                    objFnd.show();
                    break;
            }
            if (!objFnd.GetCamSel(1).equals("")) {
                if (TipoBusqueda == 1 || TipoBusqueda == 2 || TipoBusqueda == 3) {
                    txtCodDeb.setText(objFnd.GetCamSel(1).toString());
                    txtDesCtaDeb.setText(objFnd.GetCamSel(3).toString());
                } else {
                    txtCodHab.setText(objFnd.GetCamSel(1).toString());
                    txtDesCtaHab.setText(objFnd.GetCamSel(3).toString());
                }
            } else {
                if (TipoBusqueda == 1 || TipoBusqueda == 2 || TipoBusqueda == 3) {
                    txtCodDeb.setText("");
                    txtDesCtaDeb.setText("");
                } else {
                    txtCodHab.setText("");
                    txtDesCtaHab.setText("");
                }
            }
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }

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
        switch (mostrarMsgCon(strAux))
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
                blnCmb=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
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
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                refrescaDatos();                
            }
            else
            {
                MensajeInf("Error al cargar registro");
                blnCmb=false;
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
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {                
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
                strSQL= " SELECT co_tipdoc,tx_obs1,st_reg " +
                        " FROM tbm_cabTipDoc" +
                        " WHERE  co_emp=" + rstCab.getString("co_emp") +
                        " AND co_loc="    + rstCab.getString("co_loc") +
                        " AND co_tipDoc=" + rstCab.getString("co_tipDoc") ;

                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    cargarCabTipoDoc(rst.getInt("co_tipdoc"));
                    txtDesLarTipDoc.setText(objTipDoc.gettx_deslar()); 
                    
                    if(objTipDoc.getco_ctadeb()==0)  txtCodDeb.setText("");
                    else  txtCodDeb.setText(""+ objTipDoc.getco_ctadeb() );
                    
                    if(objTipDoc.getco_ctahab()==0)  txtCodHab.setText("");
                    else txtCodHab.setText(""+objTipDoc.getco_ctahab());
                    
                    
                    txtDesCtaDeb.setText(objCtaCtb.getNomCta(objTipDoc.getco_ctadeb()));
                    txtDesCtaHab.setText(objCtaCtb.getNomCta(objTipDoc.getco_ctahab()));
                    txtDoc.setText(""+objTipDoc.getne_ultdoc());
                    txtNumLin.setText(""+objTipDoc.getne_numlin());
                    txtMod.setText(""+objTipDoc.getne_modulo());
                    if (objTipDoc.gettx_natdoc().equals("I"))
                        optIng.setSelected(true);
                    if (objTipDoc.gettx_natdoc().equals("E"))
                        optEgr.setSelected(true);
                    if (objTipDoc.gettx_natdoc().equals("A"))
                        optAmb.setSelected(true);
                    
                    if (objTipDoc.getst_iva().equals("S"))
                        chkIva.setSelected(true);
                    else
                        chkIva.setSelected(false);
                    if (objTipDoc.getst_des().equals("S"))
                        chkDes.setSelected(true);
                    else
                        chkDes.setSelected(false);
                    if (objTipDoc.getst_genpag().equals("S"))
                        chkPag.setSelected(true);
                    else
                        chkPag.setSelected(false);
                    if (objTipDoc.getst_calculacosto().equals("S"))
                        chkCos.setSelected(true);
                    else
                        chkCos.setSelected(false);
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    limpiarDoc();
                    objTooBar.setEstadoRegistro("Eliminado");
                }
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                blnCmb=false;
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
    private boolean validaCampos()
    {
        if (txtDesCorTipDoc.getText().equals("")){
            MensajeInf("<html>Debe ingresar <font color=\"blue\">Tipo de Documento</font> al Documento</html>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if (txtDesLarTipDoc.getText().equals("")){
            MensajeInf("<html>Debe ingresar <font color=\"blue\">Descripcion Tipo Documento</font> al Documento</html>");
            txtDesCorTipDoc.requestFocus();
            return false;                        
        }
        // if (txtCodDeb.getText().equals(""))
        // {   MensajeInf("<html>Debe ingresar <font color=\"blue\">Cuenta del Debe</font> al Documento</html>");
        //     txtCodDeb.requestFocus();
        //     return false;                                    
        // }
        // if (txtCodHab.getText().equals("")){
        //     MensajeInf("<html>Debe ingresar <font color=\"blue\">Cuenta del Haber</font> al Documento</html>");
        //     txtCodHab.requestFocus();
        //     return false;                                    
        // }
        objTblMod.removeEmptyRows();
        if (!objTblMod.isAllRowsComplete())
        {
            MensajeInf("<html>Debe ingresar <font color=\"blue\">Datos en cuentas relacionadas</font> al Documento</html>");            
            tabGen.setSelectedIndex(1);
            return false;                                                
        }
        return true;
    }
    private boolean anularDoc()
    {
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.PreparedStatement pstReg;
                    String strSQL="";
                    strSQL = " Update tbm_cabtipdoc set st_reg= 'I' where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " +objZafParSis.getCodigoLocal() +
                             " and co_tipdoc = " + txtCod.getText();
                    pstReg = con.prepareStatement(strSQL);
                    pstReg.executeUpdate();
                    con.commit();
                    con.close();
                }
            }catch(SQLException e){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, e);   
                return false;
            }
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;
    }
    
    public class ToolBar extends ZafToolBar 
    {
    
        public ToolBar (javax.swing.JInternalFrame ifrTmp) {
            super (ifrTmp, objZafParSis);
        }

         public boolean beforeInsertar()
        {
            return true;
        }
        
        public boolean beforeConsultar()
        {
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

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
       public boolean anular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if (!anularDoc())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnCmb=false;
            return true;
        }

        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnCmb)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public boolean eliminar()
        {
            try
            {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliminarDoc())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                }
                blnCmb=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar()
        {
            if (!validaCampos())
                return false;
            if (!guardarDoc())
                return false;
            blnCmb=false;
            return true;
        }

        public boolean modificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!validaCampos())
                return false;
            if (!modificarDoc())
                return false;
            blnCmb=false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnCmb)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarDoc();
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            blnCmb=false;
            return blnRes;
        }
     
        public void clickAceptar() {
        }
       
        public void clickAnular() {
        }
        
        public void clickCancelar() {
        }
        
        public void clickConsultar() {            
            txtCod.setEditable(true);
            txtCod.setEnabled(true);
            habilitarDoc();
        }
        
        public void clickEliminar() {
            deshabilitarDoc();
        }
        
        public void clickImprimir() {
        }
        
        public void clickInsertar() {
 try
            {
                if (blnCmb)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    rstCab=null;
                    stmCab.close();
                    conCab.close();
                    stmCab=null;
                    conCab=null;
                }
                limpiarDoc();
                habilitarDoc();
                cboEst.setSelectedIndex(0);
                txtCod.setEditable(false);
                butCod.setEnabled(false);
                optIng.setSelected(true);
                txtDesCorTipDoc.requestFocus();
                blnCmb = false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }        }
        
        public void clickModificar() {
            habilitarDoc();
            txtCod.setEnabled(false);
            butCod.setEnabled(false);
        }
        
        public void clickVisPreliminar() {
        }
        
        public boolean consultar() {
            return consultarDoc(filtrarSql());
        }
        
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
                
        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
    }
    
    
    /**
     * <PRE>Procedimiento que asigna la informacion respectiva al formulario
     * segÃºn los datos que rescate de la tabla a la que estamos haciendo
     * referencia dependiendo de lo que contiene la variable tipo Resulset
     * <B>--rstCab--</B></PRE>
     */    
    public void refrescaDatos()
    {     
      try
      { 
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            java.sql.Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );            
            int intCod = 0;
            try
            {
                if (con!=null){
                    strSql = " SELECT co_cta,tx_ubicta,st_reg FROM tbm_detTipDoc WHERE co_emp = " + objZafParSis.getCodigoEmpresa() +
                             " and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipDoc = " + objTipDoc.getco_tipdoc() +
                             " ORDER BY co_tipDoc"; 
                    stm = con.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSql);
                    Vector vecData = new Vector();
                    for(int i=0; rst.next(); i++)
                    {
                        Vector vecReg = new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CTA, rst.getString("co_cta"));
                        vecReg.add(INT_TBL_BUT,"");
                        vecReg.add(INT_TBL_CODCTA, objCtaCtb.getNumCtaCtb(rst.getInt("co_cta")));
                        vecReg.add(INT_TBL_DSC, objCtaCtb.getNomCta(rst.getInt("co_cta")));
                        if (rst.getString("st_reg").equals("S"))
                            vecReg.add(INT_TBL_CHK_PRE, new Boolean(true));
                        else
                            vecReg.add(INT_TBL_CHK_PRE, new Boolean(false));
                        vecReg.add(INT_TBL_NAT_CTA,rst.getString("tx_ubicta"));
                        vecReg.add(INT_TBL_ESTADO, "E");
                        vecReg.add(INT_TBL_PRETXT,"");
                        vecData.add(vecReg);                         
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);  
                    

                 }
                    blnCmb = false;
                    con.close();
            }catch(SQLException Evt){
              objUti.mostrarMsgErr_F1(this, Evt);
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
      }catch(Exception Evt){
        objUti.mostrarMsgErr_F1(this, Evt);
      }                       
    }
    
    
    /**
     * Función que me permite guardar los datos del Tipo de Documento
     * @return
     */
    public boolean guardarDoc()
    {
        //Asigno a la variable es valor segun lo que haya seleccionado el usuario en el combo
        char chrEst; 
        String chrOpt="";
        String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
        if (cboEst.getSelectedIndex()==0)
            chrEst= 'A';
        else
            chrEst= 'I';

        try
        {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
              if (con!=null)
              {
                java.sql.Statement stm = con.createStatement();
                java.sql.PreparedStatement pstReg;
                strSql = "";
                strSql = strSql + "SELECT MAX(co_tipDoc)+1 as ult_cod from tbm_cabTipDoc where co_emp = "+ objZafParSis.getCodigoEmpresa() + " and co_loc= "+ objZafParSis.getCodigoLocal();

                java.sql.ResultSet rst = stm.executeQuery(strSql);

                /**Bloque que almacena el numero de registro que toca guardar, caso contrario
                 *que no haya registros en la tabla automaticamente guarda en esta !*/
                int intNumReg;
                if (rst.next())
                    intNumReg=rst.getInt("ult_cod");
                else
                    intNumReg=1;                
                rst.close();
                
                /**Bloque que almacena en una variable de tipo char la opcion al que optIng
                 *estan haciendo referencia o el objeto que hayan marcado*/ 
                if (optIng.isSelected())
                    chrOpt = "I";
                if (optEgr.isSelected()) 
                    chrOpt = "E";
                 if (optAmb.isSelected()) 
                    chrOpt = "A";

                /**Haciendo el Insert en la tabla cabTipDoc*/
                strSql = "";
                strSql = strSql + "INSERT INTO tbm_cabTipDoc (co_emp, co_loc, co_tipDoc, tx_desCor, tx_desLar, co_ctaDeb, co_ctaHab";
                strSql = strSql + ", tx_natDoc, ne_ultDoc,st_iva,st_des,st_genpag,st_cosunical,ne_mod, tx_obs1, st_reg, co_usrIng,fe_ing" ;
                if (txtNumLin.getText().equals(""))
                    strSql = strSql + ")";
                else
                    strSql = strSql + ",ne_numlin)";
                strSql = strSql + " VALUES (" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " +  intNumReg  + "";
                strSql = strSql + ", " + ((strAux = txtDesCorTipDoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                strSql = strSql + ", " + ((strAux = txtDesLarTipDoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");

                if (txtCodDeb.getText().equals(""))
                    strSql = strSql + ", " + null + "";
                else{
                    strSql = strSql + ", " + txtCodDeb.getText().replaceAll("'", "''") + "";}
                
                if (txtCodHab.getText().equals(""))
                    strSql = strSql + ", " + null + "";
                else{
                    strSql = strSql + ", " + txtCodHab.getText().replaceAll("'", "''") + "";}
                
                strSql = strSql + ", '" + chrOpt + "'";
                
                if (txtDoc.getText().equals(""))
                    strSql = strSql + ", 1";
                else{
                    strSql = strSql + ", " + txtDoc.getText().replaceAll("'", "''") + "";}

                if (chkIva.isSelected())
                    strSql = strSql + ",'S' " ;
                else
                    strSql = strSql + ",'N' " ;
                if (chkDes.isSelected())
                    strSql = strSql + ",'S' " ;
                else
                    strSql = strSql + ",'N' " ;
                if (chkPag.isSelected())
                    strSql = strSql + ",'S' " ;
                else
                    strSql = strSql + ",'N' " ;
                if (chkCos.isSelected())
                    strSql = strSql + ",'S' " ;
                else
                    strSql = strSql + ",'N' " ;
                strSql = strSql + ", " + ((txtMod.getText().replaceAll("'", "''")).equals("")?0:Integer.parseInt(txtMod.getText().toString()));
                strSql = strSql + ", " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"''":"'" + strAux + "'");
                strSql = strSql + ", '" + chrEst + "', "+ objZafParSis.getCodigoUsuario() +", '"+ strFecSis +"'" ;
                if (txtNumLin.getText().equals(""))
                    strSql = strSql + ")";                
                else
                    strSql = strSql + "," + txtNumLin.getText() + ")";

                pstReg = con.prepareStatement(strSql);
                pstReg.executeUpdate();
                
                objTblMod.removeEmptyRows();
                for(int i=0; i<objTblMod.getRowCountTrue();i++){
                    if (tblDat.getValueAt(i,INT_TBL_CTA) != null) {                                             
                        strSql="";
                        strSql = strSql + " INSERT INTO tbm_detTipDoc (co_emp, co_loc, co_tipDoc, co_reg, co_cta,tx_ubicta, st_reg)";
                        strSql = strSql + " VALUES (" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " +  intNumReg + "";
                        strSql = strSql + ", " + (i + 1) + "";
                        strSql = strSql + ", " + ((tblDat.getValueAt(i, INT_TBL_CTA).toString().equals(""))?null:tblDat.getValueAt(i, INT_TBL_CTA)) + "";
                        strSql = strSql + ", '" + ((tblDat.getValueAt(i, INT_TBL_NAT_CTA).toString().equals(""))?null:tblDat.getValueAt(i, INT_TBL_NAT_CTA).toString()) + "'";
                        strSql = strSql + ", '" + ((tblDat.getValueAt(i,INT_TBL_CHK_PRE)==null || tblDat.getValueAt(i,INT_TBL_CHK_PRE).toString().equals("") || tblDat.getValueAt(i, INT_TBL_CHK_PRE).toString().equals("false") )? "N":"S")+ "')";                       
//                        System.out.println(strSql);
                        pstReg = con.prepareStatement(strSql);
                        pstReg.executeUpdate();
                    }
                }
                con.commit();
                stm.close();                
                con.close();

                limpiarDoc();

            }
           }catch(SQLException evt){
            con.rollback();
            con.close();
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
          }
        }
        catch(Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }
    
    
    /**Función que me permite modificar los datos del Tipo de Documento seleccionado*/
    public boolean modificarDoc()
    {
        char chrEst; 
        String chrOpt="";
        //Asigno a la variable es valor segun lo que haya seleccionado el usuario en el combo
        if (cboEst.getSelectedIndex()==0)
            chrEst= 'A';
        else
            chrEst= 'I';

        try
        {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false); 
            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
            try{
                if (con!=null)
                {
                    java.sql.PreparedStatement pstReg;

                   /**Bloque que almacena en una variable de tipo char la opcion al que estan haciendo referencia o el objeto que hayan marcado*/
                   if (optIng.isSelected())
                      chrOpt = "I";
                   if (optEgr.isSelected()) 
                      chrOpt = "E";
                   if (optAmb.isSelected()) 
                      chrOpt = "A";
                    
                    /**Haciendo la modificación en la tabla segun el codigo del usuario al que estemos haciendo referencia*/
                    strSql = "";
                    strSql = strSql + "UPDATE tbm_cabTipDoc SET tx_desCor = " + ((strAux = txtDesCorTipDoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", tx_desLar = " + ((strAux = txtDesLarTipDoc.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");

                    if (txtCodDeb.getText().equals(""))
                        strSql = strSql + ", co_ctaDeb = " + null + "";
                    else{
                        strSql = strSql + ", co_ctaDeb = " + txtCodDeb.getText().replaceAll("'", "''") + "";}

                    if (txtCodHab.getText().equals(""))
                        strSql = strSql + ", co_ctaHab = " + null + "";
                    else{
                        strSql = strSql + ", co_ctaHab = " + txtCodHab.getText().replaceAll("'", "''") + "";}

                    strSql = strSql + ", tx_natDoc = '" + chrOpt + "'";

                    if (txtDoc.getText().equals(""))
                        strSql = strSql + ", ne_ultDoc = 0";
                    else{
                        strSql = strSql + ", ne_ultDoc = " + txtDoc.getText().replaceAll("'", "''") + "";}
                
                    if (!txtNumLin.getText().equals(""))
                        strSql = strSql + ", ne_numlin = " + txtNumLin.getText().replaceAll("'", "''") + "";

                    if (chkIva.isSelected())
                        strSql = strSql + ",st_iva='S' " ;
                    else
                        strSql = strSql + ",st_iva='N' " ;
                    if (chkDes.isSelected())
                        strSql = strSql + ",st_des='S' " ;
                    else
                        strSql = strSql + ",st_des='N' " ;
                    if (chkPag.isSelected())
                        strSql = strSql + ",st_genpag='S' " ;
                    else
                        strSql = strSql + ",st_genpag='N' " ;
                    if (chkCos.isSelected())
                        strSql = strSql + ",st_cosunical='S' " ;
                    else
                        strSql = strSql + ",st_cosunical='N' " ;
                    strSql = strSql + ", ne_mod=" + ((txtMod.getText().replaceAll("'", "''")).equals("")?0:Integer.parseInt(txtMod.getText().toString()));                    
                    strSql = strSql + ", tx_obs1 = " + ((strAux = txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'");
                    strSql = strSql + ", st_reg = '" + chrEst + "'";
                    strSql = strSql + ", co_usrMod = " + objZafParSis.getCodigoUsuario() + "";
                    strSql = strSql + ", fe_ultmod = '" + strFecSis + "'";
                    strSql = strSql + " WHERE co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipDoc = "+ txtCod.getText().replaceAll("'", "''") +"";
                    //System.out.println(strSql);
                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();


                    /*Ejecutando delete de detalle*/
                    strSql= " DELETE FROM tbm_detTipDoc WHERE co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipDoc = "+ txtCod.getText().replaceAll("'", "''") +"";
   
                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();

                    objTblMod.removeEmptyRows();
                    for(int i=0; i<objTblMod.getRowCountTrue();i++)
                    {
                        if (tblDat.getValueAt(i,INT_TBL_CTA) != null) 
                        {                                             
                            strSql="";
                            strSql = strSql + " INSERT INTO tbm_detTipDoc (co_emp, co_loc, co_tipDoc, co_reg, co_cta,tx_ubicta, st_reg)";
                            strSql = strSql + " VALUES (" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " +  objTipDoc.getco_tipdoc() + "";
                            strSql = strSql + ", " + (i + 1) + "";
                            strSql = strSql + ", " + ((tblDat.getValueAt(i, INT_TBL_CTA).toString().equals(""))?null:tblDat.getValueAt(i, INT_TBL_CTA)) + "";
                            strSql = strSql + ", '" + ((tblDat.getValueAt(i, INT_TBL_NAT_CTA).toString().equals(""))?null:tblDat.getValueAt(i, INT_TBL_NAT_CTA).toString()) + "'";
                            strSql = strSql + ", '" + ((tblDat.getValueAt(i,INT_TBL_CHK_PRE)==null || tblDat.getValueAt(i,INT_TBL_CHK_PRE).toString().equals("") || tblDat.getValueAt(i, INT_TBL_CHK_PRE).toString().equals("false") )? "N":"S")+ "')";                       
   
                            pstReg = con.prepareStatement(strSql);
                            pstReg.executeUpdate();
                        }
                    }
                    con.commit();
                    con.close();
                }
            }catch(SQLException evt){
                con.rollback();
                con.close();
                System.out.println("Rollback");
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        } catch(Exception evt){
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
        }
        blnCmb = false;
        return true;
    }
    
    
    /**Función que permitirá eliminar un registro*/
    public boolean eliminarDoc()
    {
        try
        {
            java.sql.Connection  con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.PreparedStatement pstReg;

                    /*Ejecutando delete de detalle*/
                    strSql= "DELETE FROM tbm_detTipDoc WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " + objZafParSis.getCodigoEmpresa() + " and co_tipDoc = " + txtCod.getText().replaceAll("'", "''") + "";       

                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();

                    /**Haciendo el eliminado de la tabla segun el codigo al que estemos haciendo referencia*/
                    strSql = "";
                    strSql = "DELETE FROM tbm_cabTipDoc WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " + objZafParSis.getCodigoEmpresa() + " and co_tipDoc = " + txtCod.getText().replaceAll("'", "''") + "";       

                    pstReg = con.prepareStatement(strSql);
                    pstReg.executeUpdate();
                    
                    con.commit();
                    con.close();
                    limpiarDoc();
                    deshabilitarDoc();
                }
            }catch(SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(this, evt);
                return false;
            }
        }catch(Exception evt){
            objUti.mostrarMsgErr_F1(this, evt);
            return false;
        }
        blnCmb = false;
        return true;
    }
   
    
    private String filtrarSql()
    {
        String sqlFlt = "";
        //Agregando filtro por Codigo de Tipo de documento
        if(!txtCod.getText().equals(""))
            sqlFlt = sqlFlt + " and co_tipDoc = " + txtCod.getText().replaceAll("'", "''") + "";
        
        //Agregando filtro por Codigo alterno
        if(!txtDesCorTipDoc.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_desCor) LIKE '" + txtDesCorTipDoc.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "'";
        
        //Agregando filtro por Nombre
        if(!txtDesLarTipDoc.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_desLar) LIKE '" + txtDesLarTipDoc.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
        
        //Agregando filtro por Codigo de Cuenta Debe
        if(!txtCodDeb.getText().equals(""))
            sqlFlt = sqlFlt + " and co_ctaDeb = " + txtCodDeb.getText().replaceAll("'", "''") ;

        //Agregando filtro por Codigo de Cuenta Haber
        if(!txtCodHab.getText().equals(""))
            sqlFlt = sqlFlt + " and co_ctaHab = " + txtCodHab.getText().replaceAll("'", "''") ;
        
        //Agregando filtro por Ultimo documento ingresado
        if(!txtDoc.getText().equals(""))
            sqlFlt = sqlFlt + " and ne_ultDoc = " + txtDoc.getText().replaceAll("'", "''") ;
        
        //Agregando filtro por Observacion 1 
        if(!txaObs1.getText().equals(""))
            sqlFlt = sqlFlt + " and LOWER(tx_obs1) LIKE '" + txaObs1.getText().replaceAll("'", "''").toLowerCase() + "' ";
        
      return sqlFlt;
    } 
    
    
    private boolean consultarDoc(String strFlt)
    {
       strFil = strFlt;
       try
       {
                conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conCab!=null)
                {

                    stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                    
                    //Agregando el Sql de Consulta para el Maestro
                    strSql = "";
                    strSql = strSql + "SELECT co_emp,co_loc,co_tipdoc, tx_obs1,st_reg FROM tbm_cabTipDoc";
                    strSql = strSql + " WHERE co_emp = "+ objZafParSis.getCodigoEmpresa() + " and co_loc= "+ objZafParSis.getCodigoLocal();
                    strSql = strSql + strFil + " ORDER BY co_tipDoc";

                    rstCab = stmCab.executeQuery(strSql);

                    if(rstCab.next()){
                        rstCab.last();
                        objTooBar.setMenSis("Se encontraron " + rstCab.getRow()+ " registros.");
                        cargarReg();
                    } 
                    else{
                        objTooBar.setMenSis("Se encontraron 0 registros...");
                        strMsg="No se ha encontrado ningÃºn registro que cumpla el criterio de bÃºsqueda especificado.";
                        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);  
                        rstCab = null;
                        stmCab.close();
                        conCab.close();
                        stmCab=null;
                        conCab=null;
                        limpiarDoc();
                        return false;
                    }
                    blnCmb = false;                    
                }
       }
       catch(SQLException Evt)
       {
          objUti.mostrarMsgErr_F1(this, Evt);
          return false;
        }
        catch(Exception Evt)
        {
          objUti.mostrarMsgErr_F1(this, Evt);
          return false;
        }                       
    return true;     
    }
    
    
    /* Agrega el listener para detectar que hubo algun cambio en la caja de texto*/
    private void addListenerCambios()
    {
        objLisCmb = new LisTxt();
        txtCod.setText("");
        txtCod.getDocument().addDocumentListener(objLisCmb);
        txtDesCorTipDoc.getDocument().addDocumentListener(objLisCmb);
        txtDesLarTipDoc.getDocument().addDocumentListener(objLisCmb);
        txtCodDeb.getDocument().addDocumentListener(objLisCmb);
        txtDesCtaDeb.getDocument().addDocumentListener(objLisCmb);
        txtCodHab.getDocument().addDocumentListener(objLisCmb);
        txtDesCtaHab.getDocument().addDocumentListener(objLisCmb);
        txtNumLin.getDocument().addDocumentListener(objLisCmb);
        txtMod.getDocument().addDocumentListener(objLisCmb);
        txtDoc.getDocument().addDocumentListener(objLisCmb);
        txaObs1.getDocument().addDocumentListener(objLisCmb);
    }   
    
     /* Clase de tipo documenet listener para detectar los cambios en los textcomponent*/
    class LisTxt implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true;
        }
        
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnCmb = true; 
        }
    }
    
    /* Procedimiento que limpia todas las cajas de texto que existen en el frame*/
    public void limpiarDoc()
    { 
       txtCod.setText("");
       txtDesCorTipDoc.setText("");
       txtDesLarTipDoc.setText("");
       txtCodDeb.setText("");
       txtDesCtaDeb.setText("");
       txtCodHab.setText("");
       txtDesCtaHab.setText("");
       txtNumLin.setText("");
       optIng.setSelected(false);
       optEgr.setSelected(false);
       txtDoc.setText("");
       txaObs1.setText("");
       txtMod.setText("");
       tabGen.setSelectedIndex(0);
       objTblMod.removeAllRows();
 }
    
    
    /**Procedimiento que me permite habilitar las cajas de texto para que estas puedan ser manipuladas por el usuario*/
    public void habilitarDoc()
    {
       txtDesCorTipDoc.setEnabled(true);
       txtDesLarTipDoc.setEditable(true);
       txtCodDeb.setEnabled(true);
       txtDesCtaDeb.setEnabled(true);
       txtCodHab.setEnabled(true);
       txtDesCtaHab.setEnabled(true);
       optIng.setEnabled(true);
       optEgr.setEnabled(true);
       txtDoc.setEnabled(true);
       txaObs1.setEditable(true);
       txtMod.setEditable(true);
       txtNumLin.setEditable(true);
       cboEst.setEnabled(true); 
       optIng.setSelected(false);
       optEgr.setSelected(false);
       butCod.setEnabled(true);
       butCtaDeb.setEnabled(true);
       butCtaHab.setEnabled(true);
       chkCos.setEnabled(true);
       chkDes.setEnabled(true);
       chkIva.setEnabled(true);
       chkPag.setEnabled(true);
       setEditable(true);
    }
    
    
    /**Procedimiento que deshabilta las cajas de texto para que estas no puedan ser manipuladas por el usuario*/
    public void deshabilitarDoc()
    {
       txtDesCorTipDoc.setEnabled(false);
       txtDesLarTipDoc.setEditable(false);
       txtCodDeb.setEnabled(false);
       txtDesCtaDeb.setEnabled(false);
       txtCodHab.setEnabled(false);
       txtDesCtaHab.setEnabled(false);
       optIng.setEnabled(false);
       optEgr.setEnabled(false);
       txtDoc.setEnabled(false);
       txaObs1.setEditable(false);
       txtNumLin.setEditable(false);
       txtMod.setEditable(false);
       cboEst.setEnabled(false);
       optIng.setSelected(false);
       optEgr.setSelected(false);
       butCod.setEnabled(false);
       butCtaDeb.setEnabled(false);
       butCtaHab.setEnabled(false);
       chkCos.setEnabled(false);
       chkDes.setEnabled(false);
       chkIva.setEnabled(false);
       chkPag.setEnabled(false);
       setEditable(false);
    }                       
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrOpc = new javax.swing.ButtonGroup();
        panGen = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTab = new javax.swing.JPanel();
        lblCod = new javax.swing.JLabel();
        lblCodAlt = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        lblCtaDeb = new javax.swing.JLabel();
        lblCtaHab = new javax.swing.JLabel();
        lblDoc = new javax.swing.JLabel();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        optIng = new javax.swing.JRadioButton();
        optEgr = new javax.swing.JRadioButton();
        txtCod = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtDoc = new javax.swing.JTextField();
        txtCodDeb = new javax.swing.JTextField();
        txtDesCtaDeb = new javax.swing.JTextField();
        butCtaDeb = new javax.swing.JButton();
        txtCodHab = new javax.swing.JTextField();
        txtDesCtaHab = new javax.swing.JTextField();
        butCtaHab = new javax.swing.JButton();
        lblEst = new javax.swing.JLabel();
        cboEst = new javax.swing.JComboBox();
        butCod = new javax.swing.JButton();
        lblCtaHab1 = new javax.swing.JLabel();
        panConf = new javax.swing.JPanel();
        chkIva = new javax.swing.JCheckBox();
        chkDes = new javax.swing.JCheckBox();
        chkPag = new javax.swing.JCheckBox();
        chkCos = new javax.swing.JCheckBox();
        lblDoc1 = new javax.swing.JLabel();
        txtNumLin = new javax.swing.JTextField();
        lblDoc2 = new javax.swing.JLabel();
        txtMod = new javax.swing.JTextField();
        optAmb = new javax.swing.JRadioButton();
        panTab = new javax.swing.JPanel();
        spnTab = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();

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

        panGen.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setForeground(new java.awt.Color(10, 36, 106));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Maestro Tipos de documentos");
        panGen.add(lblTit, java.awt.BorderLayout.NORTH);
        lblTit.getAccessibleContext().setAccessibleName("");

        tabGen.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        panGenTab.setLayout(null);

        lblCod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCod.setText("Código");
        panGenTab.add(lblCod);
        lblCod.setBounds(12, 16, 140, 15);

        lblCodAlt.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAlt.setText("Tipo Documento");
        panGenTab.add(lblCodAlt);
        lblCodAlt.setBounds(12, 40, 140, 15);

        lblNom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNom.setText("Descripción Documento");
        panGenTab.add(lblNom);
        lblNom.setBounds(12, 64, 140, 15);

        lblCtaDeb.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCtaDeb.setText("Cuenta debe");
        panGenTab.add(lblCtaDeb);
        lblCtaDeb.setBounds(12, 88, 140, 15);

        lblCtaHab.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCtaHab.setText("Cuenta haber");
        panGenTab.add(lblCtaHab);
        lblCtaHab.setBounds(12, 112, 140, 15);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDoc.setText("Último documento ingresado");
        panGenTab.add(lblDoc);
        lblDoc.setBounds(12, 160, 140, 15);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observaciones:");
        panGenTab.add(lblObs1);
        lblObs1.setBounds(12, 250, 140, 15);

        txaObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txaObs1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txaObs1FocusLost(evt);
            }
        });
        spnObs1.setViewportView(txaObs1);
        txaObs1.getAccessibleContext().setAccessibleName("");

        panGenTab.add(spnObs1);
        spnObs1.setBounds(160, 250, 412, 52);

        bgrOpc.add(optIng);
        optIng.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        optIng.setText("Ingreso");
        optIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optIngActionPerformed(evt);
            }
        });
        panGenTab.add(optIng);
        optIng.setBounds(160, 130, 69, 23);

        bgrOpc.add(optEgr);
        optEgr.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        optEgr.setText("Egreso");
        optEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEgrActionPerformed(evt);
            }
        });
        panGenTab.add(optEgr);
        optEgr.setBounds(230, 130, 76, 23);

        txtCod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFocusLost(evt);
            }
        });
        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });
        panGenTab.add(txtCod);
        txtCod.setBounds(160, 10, 36, 20);
        txtCod.getAccessibleContext().setAccessibleName("");

        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenTab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(160, 35, 90, 20);
        txtDesCorTipDoc.getAccessibleContext().setAccessibleName("");

        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenTab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(160, 60, 196, 20);
        txtDesLarTipDoc.getAccessibleContext().setAccessibleName("");

        txtDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDocFocusLost(evt);
            }
        });
        txtDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocActionPerformed(evt);
            }
        });
        panGenTab.add(txtDoc);
        txtDoc.setBounds(160, 155, 100, 20);
        txtDoc.getAccessibleContext().setAccessibleName("");

        txtCodDeb.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodDeb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDebFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDebFocusLost(evt);
            }
        });
        txtCodDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDebActionPerformed(evt);
            }
        });
        panGenTab.add(txtCodDeb);
        txtCodDeb.setBounds(160, 85, 36, 20);
        txtCodDeb.getAccessibleContext().setAccessibleName("");

        txtDesCtaDeb.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCtaDeb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCtaDebFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCtaDebFocusLost(evt);
            }
        });
        txtDesCtaDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCtaDebActionPerformed(evt);
            }
        });
        panGenTab.add(txtDesCtaDeb);
        txtDesCtaDeb.setBounds(197, 85, 250, 20);

        butCtaDeb.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCtaDeb.setText("...");
        butCtaDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaDebActionPerformed(evt);
            }
        });
        panGenTab.add(butCtaDeb);
        butCtaDeb.setBounds(448, 85, 24, 20);

        txtCodHab.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodHab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodHabFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodHabFocusLost(evt);
            }
        });
        txtCodHab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodHabActionPerformed(evt);
            }
        });
        panGenTab.add(txtCodHab);
        txtCodHab.setBounds(160, 110, 36, 20);
        txtCodHab.getAccessibleContext().setAccessibleName("");

        txtDesCtaHab.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCtaHab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCtaHabFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCtaHabFocusLost(evt);
            }
        });
        txtDesCtaHab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCtaHabActionPerformed(evt);
            }
        });
        panGenTab.add(txtDesCtaHab);
        txtDesCtaHab.setBounds(197, 110, 250, 20);

        butCtaHab.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCtaHab.setText("...");
        butCtaHab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaHabActionPerformed(evt);
            }
        });
        panGenTab.add(butCtaHab);
        butCtaHab.setBounds(448, 110, 24, 20);

        lblEst.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEst.setText("Estado ");
        panGenTab.add(lblEst);
        lblEst.setBounds(12, 230, 140, 15);

        cboEst.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cboEst.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        cboEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstActionPerformed(evt);
            }
        });
        panGenTab.add(cboEst);
        cboEst.setBounds(160, 230, 120, 20);
        cboEst.getAccessibleContext().setAccessibleName("");

        butCod.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCod.setText("...");
        butCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodActionPerformed(evt);
            }
        });
        panGenTab.add(butCod);
        butCod.setBounds(200, 10, 24, 20);

        lblCtaHab1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCtaHab1.setText("Naturaleza del documento");
        panGenTab.add(lblCtaHab1);
        lblCtaHab1.setBounds(12, 136, 140, 15);

        panConf.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuracion"));
        panConf.setLayout(null);

        chkIva.setText("Maneja IVA");
        chkIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaActionPerformed(evt);
            }
        });
        panConf.add(chkIva);
        chkIva.setBounds(20, 30, 130, 23);

        chkDes.setText("Maneja Descuento");
        chkDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDesActionPerformed(evt);
            }
        });
        panConf.add(chkDes);
        chkDes.setBounds(20, 60, 130, 23);

        chkPag.setText("Genera Pagos");
        chkPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPagActionPerformed(evt);
            }
        });
        panConf.add(chkPag);
        chkPag.setBounds(20, 90, 130, 23);

        chkCos.setText("Calcula Costo");
        chkCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCosActionPerformed(evt);
            }
        });
        panConf.add(chkCos);
        chkCos.setBounds(20, 120, 130, 23);

        panGenTab.add(panConf);
        panConf.setBounds(490, 20, 170, 170);

        lblDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDoc1.setText("Lineas Imprimibles");
        panGenTab.add(lblDoc1);
        lblDoc1.setBounds(12, 180, 140, 15);

        txtNumLin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNumLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumLinActionPerformed(evt);
            }
        });
        panGenTab.add(txtNumLin);
        txtNumLin.setBounds(160, 180, 100, 20);
        txtNumLin.getAccessibleContext().setAccessibleName("");

        lblDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDoc2.setText("Modulo");
        panGenTab.add(lblDoc2);
        lblDoc2.setBounds(12, 205, 140, 15);

        txtMod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGenTab.add(txtMod);
        txtMod.setBounds(160, 205, 100, 20);
        txtMod.getAccessibleContext().setAccessibleName("");

        bgrOpc.add(optAmb);
        optAmb.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        optAmb.setLabel("Ambos");
        panGenTab.add(optAmb);
        optAmb.setBounds(308, 129, 76, 23);

        tabGen.addTab("General", panGenTab);

        panTab.setLayout(new java.awt.BorderLayout());

        spnTab.setBorder(null);
        spnTab.setForeground(new java.awt.Color(204, 204, 204));
        spnTab.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        tblDat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblDat.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, ""}
            },
            new String [] {
                "Línea", "Cod de Cta", "...", "Nombre de Cuenta", "Predeterminado", "Uso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tblDatFocusLost(evt);
            }
        });
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        spnTab.setViewportView(tblDat);

        panTab.add(spnTab, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Cuentas contables relacionadas", panTab);

        panGen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panGen, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDesActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkDesActionPerformed

    private void chkPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPagActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkPagActionPerformed

    private void chkIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkIvaActionPerformed

    private void chkCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCosActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_chkCosActionPerformed

    private void txtNumLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumLinActionPerformed
        if (!objUti.isNumero(txtNumLin.getText()))
        {
            MensajeInf("Error! Ingrese solo numeros");
            txtNumLin.requestFocus();
            txtNumLin.setText("10");
        }
    }//GEN-LAST:event_txtNumLinActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
        
    }//GEN-LAST:event_tblDatFocusGained

    private void tblDatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusLost

    }//GEN-LAST:event_tblDatFocusLost

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
   
    }//GEN-LAST:event_tblDatMouseClicked

    private void optEgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEgrActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_optEgrActionPerformed

    private void optIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optIngActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_optIngActionPerformed

    private void txtDesCtaHabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCtaHabFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCtaHab.getText().equalsIgnoreCase(strDesCtaHab))
        {
            if (txtDesCtaHab.getText().equals(""))
            {
                txtCodHab.setText("");
                txtDesCtaHab.setText("");
            }
            else 
            {
                mostrarVenConCtaHab(2);
            }
        }
        else 
        {
            txtDesCtaHab.setText(strDesCtaHab);
        }    
    }//GEN-LAST:event_txtDesCtaHabFocusLost

    private void txtDesCtaHabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCtaHabActionPerformed
        txtDesCtaHab.transferFocus();
    }//GEN-LAST:event_txtDesCtaHabActionPerformed

    private void txtCodHabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodHabFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodHab.getText().equalsIgnoreCase(strCodHab)) 
        {
            if (txtCodHab.getText().equals(""))
            {
                txtCodHab.setText("");
                txtDesCtaHab.setText("");
            }
            else 
            {
                mostrarVenConCtaHab(1);
            }
        } 
        else 
        {
            txtCodHab.setText(strCodHab);
        } 
    }//GEN-LAST:event_txtCodHabFocusLost

    private void txtCodHabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodHabActionPerformed
        txtCodHab.transferFocus();
    }//GEN-LAST:event_txtCodHabActionPerformed

    private void butCtaHabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaHabActionPerformed
        mostrarVenConCtaHab(0);
    }//GEN-LAST:event_butCtaHabActionPerformed

    private void cboEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstActionPerformed
        blnCmb = true;
    }//GEN-LAST:event_cboEstActionPerformed

    private void txaObs1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txaObs1FocusLost
  
    }//GEN-LAST:event_txaObs1FocusLost

    private void txtDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocFocusLost
     
    }//GEN-LAST:event_txtDocFocusLost

    private void txtDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocActionPerformed
        if (!objUti.isNumero(txtDoc.getText()))
        {
            MensajeInf("Error! Ingrese solo numeros");
            txtDoc.requestFocus();
            txtDoc.setText("1");
        }
    }//GEN-LAST:event_txtDocActionPerformed

    private void txtDesCtaDebFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCtaDebFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCtaDeb.getText().equalsIgnoreCase(strDesCtaDeb))
        {
            if (txtDesCtaDeb.getText().equals(""))
            {
                txtCodDeb.setText("");
                txtDesCtaDeb.setText("");
            }
            else 
            {
                mostrarVenConCtaDeb(2);
            }
        }
        else 
        {
            txtDesCtaDeb.setText(strDesCtaDeb);
        }  
    }//GEN-LAST:event_txtDesCtaDebFocusLost

    private void txtDesCtaDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCtaDebActionPerformed
        txtDesCtaDeb.transferFocus();
    }//GEN-LAST:event_txtDesCtaDebActionPerformed

    private void butCtaDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaDebActionPerformed
        mostrarVenConCtaDeb(0);
    }//GEN-LAST:event_butCtaDebActionPerformed

    private void txtCodDebFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDebFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodDeb.getText().equalsIgnoreCase(strCodDeb)) 
        {
            if (txtCodDeb.getText().equals(""))
            {
                txtCodDeb.setText("");
                txtDesCtaDeb.setText("");
            }
            else 
            {
                mostrarVenConCtaDeb(1);
            }
        } 
        else 
        {
            txtCodDeb.setText(strCodDeb);
        }
    }//GEN-LAST:event_txtCodDebFocusLost

    private void txtCodDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDebActionPerformed
         txtCodDeb.transferFocus();
    }//GEN-LAST:event_txtCodDebActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCod.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(3);
            }
        }
        else 
        {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();    
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCod.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        }
        else 
        {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butCodActionPerformed

    private void txtCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCod.getText().equalsIgnoreCase(strCodTipDoc))
        {
            if (txtCod.getText().equals("")) {

                txtCod.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        }
        else 
        {
            txtCod.setText(strCodTipDoc);
        }
    }//GEN-LAST:event_txtCodFocusLost

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        txtCod.transferFocus();
    }//GEN-LAST:event_txtCodActionPerformed

    /**Cerrando la aplicación*/
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="¿Esta seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusGained
        strCodTipDoc = txtCod.getText();
        txtCod.selectAll();
    }//GEN-LAST:event_txtCodFocusGained

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtCodDebFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDebFocusGained
        strCodDeb = txtCodDeb.getText();
        txtCodDeb.selectAll();
    }//GEN-LAST:event_txtCodDebFocusGained

    private void txtCodHabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodHabFocusGained
        strCodHab = txtCodHab.getText();
        txtCodHab.selectAll();
    }//GEN-LAST:event_txtCodHabFocusGained

    private void txtDesCtaDebFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCtaDebFocusGained
        strDesCtaDeb = txtDesCtaDeb.getText();
        txtDesCtaDeb.selectAll();
    }//GEN-LAST:event_txtDesCtaDebFocusGained

    private void txtDesCtaHabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCtaHabFocusGained
        strDesCtaHab = txtDesCtaHab.getText();
        txtDesCtaHab.selectAll();
    }//GEN-LAST:event_txtDesCtaHabFocusGained
 
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.tx_DesCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL += " SELECT a1.co_tipDoc, a1.tx_DesCor, a1.tx_desLar ";
            strSQL += " FROM tbm_CabTipDoc as a1 ";
            strSQL += " WHERE a1.co_Emp=" + objZafParSis.getCodigoEmpresa();
            strSQL += " AND a1.co_loc=" + objZafParSis.getCodigoLocal();
            strSQL += " ORDER BY co_tipDoc ";
            
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setCampoBusqueda(1);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(0);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                    {
                        txtCod.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCod.getText())) 
                    {
                        txtCod.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(0);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                        {
                            txtCod.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtCod.setText(strCodTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) 
                    {
                        txtCod.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    } 
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                        {
                            txtCod.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        } 
                        else 
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) 
                    {
                        txtCod.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    } 
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                        {
                            txtCod.setText(vcoTipDoc.getValueAt(1));
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
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas Contables".
     */
    private boolean configurarVenCta() 
    {
        boolean blnRes = true;
        try
        {  
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre Cuenta");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL += " SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar ";
            strSQL += " FROM tbm_plaCta as a1 ";
            strSQL += " WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
            strSQL += " AND a1.tx_tipCta='D' ";
            strSQL += " AND a1.st_reg='A' ";
            strSQL += " ORDER BY tx_codCta ";
            //Ocultar columnas.
//            int intColOcu[]=new int[1];
//            intColOcu[0]=2;
            //vcoCta = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Cuentas Contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            vcoCta = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Cuentas Contables", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
//            intColOcu=null;
            
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaDeb(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(0);
                    vcoCta.show();
                    if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                    {
                        txtCodDeb.setText(vcoCta.getValueAt(1));
                        txtDesCtaDeb.setText(vcoCta.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCta.buscar("a1.co_cta", txtCodDeb.getText())) 
                    {
                        txtCodDeb.setText(vcoCta.getValueAt(1));
                        txtDesCtaDeb.setText(vcoCta.getValueAt(3));
                    }
                    else 
                    {
                        vcoCta.setCampoBusqueda(0);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                        {
                            txtCodDeb.setText(vcoCta.getValueAt(1));
                            txtDesCtaDeb.setText(vcoCta.getValueAt(3));
                        }
                        else
                        {
                            txtCodDeb.setText(strCodDeb);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción corta".
                    if (vcoCta.buscar("a1.tx_desLar", txtDesCtaDeb.getText())) 
                    {
                        txtCodDeb.setText(vcoCta.getValueAt(1));
                        txtDesCtaDeb.setText(vcoCta.getValueAt(3));
                    } 
                    else
                    {
                        vcoCta.setCampoBusqueda(2);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                        {
                            txtCodDeb.setText(vcoCta.getValueAt(1));
                            txtDesCtaDeb.setText(vcoCta.getValueAt(3));
                        } 
                        else 
                        {
                            txtDesCtaDeb.setText(strDesCtaDeb);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaHab(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(0);
                    vcoCta.show();
                    if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                    {
                        txtCodHab.setText(vcoCta.getValueAt(1));
                        txtDesCtaHab.setText(vcoCta.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCta.buscar("a1.co_cta", txtCodHab.getText())) 
                    {
                        txtCodHab.setText(vcoCta.getValueAt(1));
                        txtDesCtaHab.setText(vcoCta.getValueAt(3));
                    }
                    else 
                    {
                        vcoCta.setCampoBusqueda(0);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                        {
                            txtCodHab.setText(vcoCta.getValueAt(1));
                            txtDesCtaHab.setText(vcoCta.getValueAt(3));
                        }
                        else
                        {
                            txtCodHab.setText(strCodHab);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción corta".
                    if (vcoCta.buscar("a1.tx_desLar", txtDesCtaHab.getText())) 
                    {
                        txtCodHab.setText(vcoCta.getValueAt(1));
                        txtDesCtaHab.setText(vcoCta.getValueAt(3));
                    } 
                    else
                    {
                        vcoCta.setCampoBusqueda(2);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                        {
                            txtCodHab.setText(vcoCta.getValueAt(1));
                            txtDesCtaHab.setText(vcoCta.getValueAt(3));
                        } 
                        else 
                        {
                            txtDesCtaHab.setText(strDesCtaHab);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrOpc;
    private javax.swing.JButton butCod;
    private javax.swing.JButton butCtaDeb;
    private javax.swing.JButton butCtaHab;
    private javax.swing.JComboBox cboEst;
    private javax.swing.JCheckBox chkCos;
    private javax.swing.JCheckBox chkDes;
    private javax.swing.JCheckBox chkIva;
    private javax.swing.JCheckBox chkPag;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblCodAlt;
    private javax.swing.JLabel lblCtaDeb;
    private javax.swing.JLabel lblCtaHab;
    private javax.swing.JLabel lblCtaHab1;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblDoc1;
    private javax.swing.JLabel lblDoc2;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optAmb;
    private javax.swing.JRadioButton optEgr;
    private javax.swing.JRadioButton optIng;
    private javax.swing.JPanel panConf;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenTab;
    private javax.swing.JPanel panTab;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnTab;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodDeb;
    private javax.swing.JTextField txtCodHab;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesCtaDeb;
    private javax.swing.JTextField txtDesCtaHab;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtMod;
    private javax.swing.JTextField txtNumLin;
    // End of variables declaration//GEN-END:variables

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
                case INT_TBL_CTA:
                    strMsg="Codigo Cuenta";
                    break;
                case INT_TBL_CODCTA:
                    strMsg="Cuenta";
                    break;
                case INT_TBL_DSC:
                    strMsg="Descripcion de la cuenta";
                    break;
                case INT_TBL_CHK_PRE:
                    strMsg="Cuenta Predeterminada";
                    break;
                case INT_TBL_NAT_CTA:
                    strMsg="Uso de la cuenta D/H";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }
    
}
