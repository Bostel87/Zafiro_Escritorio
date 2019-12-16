/*
 * ZafCom36.java
 *
 */
package Compras.ZafCom36;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.math.BigDecimal;
import Librerias.ZafSelFec.ZafSelFec;
/**
 *
 * @author Ingrid Lino
 */
public class ZafCom36 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_REG=2;
    static final int INT_TBL_DAT_COD_ITM=3;
    static final int INT_TBL_DAT_COD_ITM_MAE=4;
    static final int INT_TBL_DAT_COD_USR=5;
    static final int INT_TBL_DAT_TXT_USR=6;
    static final int INT_TBL_DAT_NOM_USR=7;    
    static final int INT_TBL_DAT_STK_SIS=8;
    static final int INT_TBL_DAT_CAN_ING_BOD=9;
    static final int INT_TBL_DAT_CAN_EGR_BOD=10;
    static final int INT_TBL_DAT_STK_CON=11;
    static final int INT_TBL_DAT_DIF_CON=12;
    static final int INT_TBL_DAT_FEC_HOR_SOL_CON=13;
    static final int INT_TBL_DAT_FEC_HOR_REA_CON=14;    
    static final int INT_TBL_DAT_OBS=15;
   
    //Variables generales.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafColNumerada objColNum;
    private ZafTblBus objTblBus;    
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PopUpMenú en JTable.
    private ZafThreadGUI objThrGUI;
    private ZafVenCon vcoBod;                           //Ventana de consulta "Bodega".
    private ZafVenCon vcoItm;                           //Ventana de consulta "Item".
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafSelFec objSelFec;
    
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.    
    private String strSQL, strAux;
    private String strCodBod, strNomBod;                //Contenido del campo al obtener el foco.
    private String strCodAlt, strCodAlt2, strNomItm;    //Contenido del campo al obtener el foco.    
    
    /** Crea una nueva instancia de la clase ZafCom36. */
    public ZafCom36(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    public ZafCom36(ZafParSis obj, String codigoBodega, String codigoItem){
        try{
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            txtCodBod.setText(codigoBodega);
            txtCodItm.setText(codigoItem);
            if (!configurarFrm())
                exitForm();            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3.2");
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtCodItm.setBackground(objParSis.getColorCamposObligatorios());
            txtCodAltItm.setBackground(objParSis.getColorCamposObligatorios());
            txtCodAlt2.setBackground(objParSis.getColorCamposObligatorios());
            txtNomItm.setBackground(objParSis.getColorCamposObligatorios());
            txtCodItm.setVisible(false);
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha de conteo");
            objSelFec.setBounds(4, 66, 472, 72);
            panFilFec.add(objSelFec);
            
            //Configurar los JTables.
            configurarTblDat();
            configurarVenConBod();
            configurarVenConItm();
            
            if(! txtCodBod.getText().equals("")){
                if(cargarDetReg()){
                    inhabilitarCampos();
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
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(16);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,             "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_REG,             "Cód.Reg.");
            vecCab.add(INT_TBL_DAT_COD_ITM,             "Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,         "Cód.Itm.Mae");
            vecCab.add(INT_TBL_DAT_COD_USR,             "Cód.Usr.");
            vecCab.add(INT_TBL_DAT_TXT_USR,             "Usuario");
            vecCab.add(INT_TBL_DAT_NOM_USR,             "Nom.Usr.");            
            vecCab.add(INT_TBL_DAT_STK_SIS,             "Stk.Sis.");
            vecCab.add(INT_TBL_DAT_CAN_ING_BOD,         "Can.Ing.Bod.");
            vecCab.add(INT_TBL_DAT_CAN_EGR_BOD,         "Can.Egr.Bod.");
            vecCab.add(INT_TBL_DAT_STK_CON,             "Can.Con.");
            vecCab.add(INT_TBL_DAT_DIF_CON,             "Dif.");
            vecCab.add(INT_TBL_DAT_FEC_HOR_SOL_CON,     "Fec.Sol.Con");
            vecCab.add(INT_TBL_DAT_FEC_HOR_REA_CON,     "Fec.Con.");            
            vecCab.add(INT_TBL_DAT_OBS,                 "Obs.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
                       
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TXT_USR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(100);            
            tcmAux.getColumn(INT_TBL_DAT_STK_SIS).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_BOD).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR_BOD).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_DIF_CON).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_FEC_HOR_SOL_CON).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_FEC_HOR_REA_CON).setPreferredWidth(90);            
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TXT_USR).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_USR, tblDat);            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Editor de ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer formato.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_SIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIF_CON).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);       
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;          

        }
        catch(Exception e){
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
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
   private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código de registro";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código de item";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código de item maestro";
                    break;
                case INT_TBL_DAT_COD_USR:
                    strMsg="Código de usuario responsable de conteo";
                    break;
                case INT_TBL_DAT_TXT_USR:
                    strMsg="Usuario responsable de conteo";
                    break;
                case INT_TBL_DAT_NOM_USR:
                    strMsg="Nombre de usuario responsable de conteo";
                    break;                    
                    case INT_TBL_DAT_STK_SIS:
                    strMsg="Stock de sistema al momento de hacer el conteo";
                    break;
                case INT_TBL_DAT_CAN_ING_BOD:
                    strMsg="Cantidad por ingresar a bodega";
                    break;
                case INT_TBL_DAT_CAN_EGR_BOD:
                    strMsg="Cantidad por egresar a bodega";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock según conteo";
                    break;
                case INT_TBL_DAT_DIF_CON:
                    strMsg="Diferencia";
                    break;
                case INT_TBL_DAT_FEC_HOR_SOL_CON:
                    strMsg="Fecha y hora de solicitud de conteo";
                    break;
                case INT_TBL_DAT_FEC_HOR_REA_CON:
                    strMsg="Fecha y hora de realización de conteo";
                    break;  
                case INT_TBL_DAT_OBS:
                    strMsg="Observación de conteo";
                    break;                        
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Bodegas".
     */
    private boolean configurarVenConBod() 
    {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_bod, a1.tx_nom";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" ORDER BY a1.co_bod, a1.tx_nom";

            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } 
        catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConBod(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoBod.setCampoBusqueda(2);
                    vcoBod.show();
                    if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código de Bodega".
                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre de Bodega".
                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtNomBod.setText(strNomBod);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Uni.Med.");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("350");
            arlAncCol.add("60");
            
            //Ocultar Columnas
            int intColOcu[]=new int[2];
            intColOcu[0]=1;  // Cód.Itm.   
            intColOcu[1]=6;  // Unidad   
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
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
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                        txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                        txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                        txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                        txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                            txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                            txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                        }
                        else
                        {
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Código Alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                        txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                        txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                            txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                            txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                        }
                        else{
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                        txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                        txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                            txtDesCorUniMed.setText(vcoItm.getValueAt(5));
                            txtDesLarUniMed.setText(vcoItm.getValueAt(6));   
                        }
                        else{
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;    
                    
            }
        }
        catch (Exception e)  {    blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
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
        panFilCab = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        lblUniMed = new javax.swing.JLabel();
        txtDesCorUniMed = new javax.swing.JTextField();
        txtDesLarUniMed = new javax.swing.JTextField();
        panFilFec = new javax.swing.JPanel();
        panFilVar = new javax.swing.JPanel();
        chkIncCanIngEgr = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        lblTit.setPreferredSize(new java.awt.Dimension(39, 15));
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(464, 625));
        panFil.setLayout(new java.awt.BorderLayout());

        panFilCab.setPreferredSize(new java.awt.Dimension(80, 80));
        panFilCab.setLayout(null);

        lblBod.setText("Bodega:");
        lblBod.setToolTipText("");
        panFilCab.add(lblBod);
        lblBod.setBounds(4, 2, 60, 20);

        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panFilCab.add(txtCodBod);
        txtCodBod.setBounds(62, 2, 76, 20);

        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panFilCab.add(txtNomBod);
        txtNomBod.setBounds(139, 2, 371, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panFilCab.add(butBod);
        butBod.setBounds(510, 2, 20, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("");
        panFilCab.add(lblItm);
        lblItm.setBounds(4, 23, 70, 20);

        txtCodItm.setEditable(false);
        txtCodItm.setEnabled(false);
        panFilCab.add(txtCodItm);
        txtCodItm.setBounds(39, 23, 20, 20);

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
        panFilCab.add(txtCodAltItm);
        txtCodAltItm.setBounds(62, 23, 76, 20);

        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        panFilCab.add(txtCodAlt2);
        txtCodAlt2.setBounds(139, 23, 80, 20);

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
        panFilCab.add(txtNomItm);
        txtNomItm.setBounds(220, 23, 290, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFilCab.add(butItm);
        butItm.setBounds(510, 23, 20, 20);

        lblUniMed.setText("Unidad:");
        lblUniMed.setToolTipText("");
        panFilCab.add(lblUniMed);
        lblUniMed.setBounds(4, 44, 70, 20);

        txtDesCorUniMed.setEditable(false);
        panFilCab.add(txtDesCorUniMed);
        txtDesCorUniMed.setBounds(62, 44, 76, 20);

        txtDesLarUniMed.setEditable(false);
        panFilCab.add(txtDesLarUniMed);
        txtDesLarUniMed.setBounds(139, 44, 370, 20);

        panFil.add(panFilCab, java.awt.BorderLayout.NORTH);

        panFilFec.setName(""); // NOI18N
        panFilFec.setPreferredSize(new java.awt.Dimension(80, 80));
        panFilFec.setLayout(new java.awt.BorderLayout());
        panFil.add(panFilFec, java.awt.BorderLayout.CENTER);

        panFilVar.setPreferredSize(new java.awt.Dimension(180, 180));
        panFilVar.setLayout(null);

        chkIncCanIngEgr.setText("Utilizar en el cálculo las cantidades que están por ingresar/egresar");
        chkIncCanIngEgr.setPreferredSize(new java.awt.Dimension(81, 16));
        panFilVar.add(chkIncCanIngEgr);
        chkIncCanIngEgr.setBounds(0, 10, 440, 20);

        panFil.add(panFilVar, java.awt.BorderLayout.PAGE_END);

        tabFrm.addTab("Filtro", panFil);
        panFil.getAccessibleContext().setAccessibleParent(tabFrm);

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

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout());

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

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e){
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if(isCamVal()){
            consultar();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
                txtDesCorUniMed.setText("");
                txtDesLarUniMed.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
        {
            txtCodAlt2.setText(strCodAlt2);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        objTblMod.removeAllRows();
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
                txtDesCorUniMed.setText("");
                txtDesLarUniMed.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
            objTblMod.removeAllRows();
        }
        else
        txtNomItm.setText(strNomItm);
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAltItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
                txtDesCorUniMed.setText("");
                txtDesLarUniMed.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
            objTblMod.removeAllRows();
        }
        else
        txtCodAltItm.setText(strCodAlt);
    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt=txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } else {
                mostrarVenConBod(2);
            }
            objTblMod.removeAllRows();
        } else
        txtNomBod.setText(strNomBod);
    }//GEN-LAST:event_txtNomBodFocusLost

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod = txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        mostrarVenConBod(0);
        objTblMod.removeAllRows();
    }//GEN-LAST:event_butBodActionPerformed

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } else {
                mostrarVenConBod(1);
            }
            objTblMod.removeAllRows();
        } else
        txtCodBod.setText(strCodBod);
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod = txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkIncCanIngEgr;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUniMed;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFilVar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDesCorUniMed;
    private javax.swing.JTextField txtDesLarUniMed;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomItm;
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notif�quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
    private boolean inhabilitarCampos(){
        boolean blnRes=false;
        try{
            txtCodBod.setEditable(false);
            txtNomBod.setEditable(false);
            butBod.setEnabled(false);
            txtCodItm.setEditable(false);
            txtCodAltItm.setEditable(false);
            txtNomItm.setEditable(false);
            butItm.setEnabled(false);
            butCon.setEnabled(false);
            butCer.setEnabled(false);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        //Validar la "Bodega".
        if (txtCodBod.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }
        if (txtCodItm.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Item</FONT> es obligatorio.<BR>Escriba o seleccione un item y vuelva a intentarlo.</HTML>");
            txtNomItm.requestFocus();
            return false;
        }
        return true;
    }      

    private void consultar()
    {
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    }      
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar �sta clase
     * ya que si no sólo se apreciar�a los cambios cuando ha terminado todo el proceso.
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
        boolean blnRes=true;
        BigDecimal bdeStk=new BigDecimal(0);
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Filtro: Fecha
                strAux="";
                if(objSelFec.isCheckBoxChecked()){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAux+=" AND a1.fe_reaCon BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_reaCon<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_reaCon>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT x.*, (CASE WHEN x.fe_sol IS NOT NULL THEN x.fe_sol ELSE ' ' END) AS fe_solCon ";
                strSQL+=" FROM (";
                strSQL+="    SELECT a1.co_emp, a1.co_itm, b1.co_itmMae, a1.co_reg, a2.co_usr, a2.tx_usr, a2.tx_nom";
                strSQL+="         , CAST(  a1.fe_solCon AS CHARACTER VARYING) AS fe_sol ";
                strSQL+="         , a1.fe_reaCon, a1.nd_stkSis, a1.nd_canIngBod, a1.nd_canEgrBod, a1.nd_stkCon, a1.tx_obs1 ";

                if(chkIncCanIngEgr.isSelected())
                    strSQL+="     , ((a1.nd_stkSis-a1.nd_canIngBod+a1.nd_canEgrBod) - a1.nd_stkCon) AS nd_dif";
                else
                    strSQL+="     , (a1.nd_stkSis - a1.nd_stkCon) AS nd_dif";
                
                strSQL+="    FROM (tbh_conInv AS a1 INNER JOIN tbm_equInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_itm=b1.co_itm)";
                strSQL+="    INNER JOIN tbm_usr AS a2 ON a1.co_usrResCon=a2.co_usr";
                strSQL+="    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="    AND a1.st_conRea='S'";
                strSQL+="    "+strAux;
                
                if(! txtCodBod.getText().toString().equals(""))
                    strSQL+=" AND a1.co_bod=" + txtCodBod.getText()  + "";
                if(!  txtCodItm.getText().toString().equals(""))
                    strSQL+=" AND a1.co_itm=" + txtCodItm.getText()  + "";   
                strSQL+=" ) as x";
                strSQL+=" ORDER BY x.fe_reaCon";
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
                        vecReg.add(INT_TBL_DAT_LIN,             "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_REG,         "" + rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,     "" + rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_USR,         "" + rst.getString("co_usr"));
                        vecReg.add(INT_TBL_DAT_TXT_USR,         "" + rst.getString("tx_usr"));
                        vecReg.add(INT_TBL_DAT_NOM_USR,         "" + rst.getString("tx_nom"));                        
                        vecReg.add(INT_TBL_DAT_STK_SIS,         "" + rst.getString("nd_stkSis"));
                        vecReg.add(INT_TBL_DAT_CAN_ING_BOD,     "" + rst.getString("nd_canIngBod"));
                        vecReg.add(INT_TBL_DAT_CAN_EGR_BOD,     "" + rst.getString("nd_canEgrBod"));
                        vecReg.add(INT_TBL_DAT_STK_CON,         "" + rst.getString("nd_stkCon"));
                        vecReg.add(INT_TBL_DAT_DIF_CON,         "");
                        vecReg.add(INT_TBL_DAT_FEC_HOR_SOL_CON, "" + rst.getString("fe_solCon"));
                        vecReg.add(INT_TBL_DAT_FEC_HOR_REA_CON, "" + rst.getString("fe_reaCon"));                        
                        vecReg.add(INT_TBL_DAT_OBS,             "" + rst.getString("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        
                        bdeStk=rst.getBigDecimal("nd_dif");
                        if(bdeStk.compareTo(new BigDecimal(0))>0){
                            bdeStk=bdeStk.abs().multiply(new BigDecimal(-1));
                        }
                        else if(bdeStk.compareTo(new BigDecimal(0))<0){
                            bdeStk=bdeStk.abs();
                        }    
                        vecReg.setElementAt(bdeStk, INT_TBL_DAT_DIF_CON);
                        vecDat.add(vecReg);
                    }
                    else  {
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
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                if(!chkIncCanIngEgr.isSelected()){
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_EGR_BOD, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_BOD, tblDat);
                }
                else{
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_EGR_BOD, tblDat);
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_ING_BOD, tblDat);
                }                
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    
    
    
}
