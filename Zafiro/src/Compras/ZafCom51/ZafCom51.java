/*
 * ZafCom51.java  
 *
 * Created on Jan 25, 2011, 10:06:36 AM
 */
package Compras.ZafCom51;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
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
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jayapata KARDEX FISICO DE INVENTARIO PARA BODEGUEROS.
 *
 */
public class ZafCom51 extends JInternalFrame 
{
    //Constantes: Columnas del JTable.
    //Tabla de Datos.
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_COD_SEG = 1;
    private static final int INT_TBL_DAT_COD_EMP = 2;
    private static final int INT_TBL_DAT_COD_LOC = 3;
    private static final int INT_TBL_DAT_COD_TIP_DOC = 4;
    private static final int INT_TBL_DAT_COD_DOC = 5;
    private static final int INT_TBL_DAT_COD_REG = 6;
    private static final int INT_TBL_DAT_DES_COR_TIPDOC = 7;
    private static final int INT_TBL_DAT_DES_LAR_TIPDOC = 8;
    private static final int INT_TBL_DAT_NUM_DOC = 9;
    private static final int INT_TBL_DAT_NUM_GUI = 10;
    private static final int INT_TBL_DAT_ORD_DES = 11;
    private static final int INT_TBL_DAT_NUM_SOLDEV = 12;
    private static final int INT_TBL_DAT_FEC_DOC = 13;
    private static final int INT_TBL_DAT_BUT_DOC_ASO = 14;
    private static final int INT_TBL_DAT_BOD_ING = 15;
    private static final int INT_TBL_DAT_BOD_EGR = 16;
    private static final int INT_TBL_DAT_CAN_ING = 17;
    private static final int INT_TBL_DAT_CAN_EGR = 18;
    private static final int INT_TBL_DAT_SAL_UNI = 19;
    private static final int INT_TBL_DAT_COD_CLI = 20;
    private static final int INT_TBL_DAT_NOM_CLI = 21;
    private static final int INT_TBL_DAT_USR_ING = 22;                    //Solo para transferencia trainv
    
    //Tabla de Bodega.
    private static final int INT_TBL_BOD_LIN = 0;                         //Línea.
    private static final int INT_TBL_BOD_CHK = 1;                         //Casilla de verificación.
    private static final int INT_TBL_BOD_COD_BOD = 2;                     //Código de la bodega.
    private static final int INT_TBL_BOD_NOM_BOD = 3;                     //Nombre de la bodega.
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafVenCon vcoItm;
    private ZafTblMod objTblMod, objTblModBod;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafThreadGUI objThrGUI;
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafTblEdi objTblEdi;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblFilCab objTblFilCab;
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblHeaColGrp objTblHeaColGrpDoc, objTblHeaColGrpBod, objTblHeaColGrpCan, objTblHeaColGrpAud;

    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnMarTodChkTblBod = true;
    JTextField txtCodItmMae = new JTextField();
    
    String strCodBod = "", strNomBod = "";
    String strCodItm="", strCodAlt="", strCodLetItm="",  strNomItm="";
    String strVersion = " v3.6";
    String strAux, strSQL;

    /** Crea una nueva instancia de la clase . */
    public ZafCom51(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
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
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenLbl2 = new ZafTblCelRenLbl();
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                       
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(2200))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(2201))
            {
                butCer.setVisible(false);
            }

            //Configurar objetos.
            txtCodItmMae.setVisible(false);
                     
            //Configurar las ZafVenCon.
            configurarVenConItm();
                        
            //Configurar los JTables
            configurarTblDat();
            configurarTblBod();
            cargarBod();
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
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_SEG, "Cód.Seg");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cod.Emp");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cod.Loc");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cod.Tip.Doc");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cod.Doc");
            vecCab.add(INT_TBL_DAT_COD_REG, "Cod.Reg");
            vecCab.add(INT_TBL_DAT_DES_COR_TIPDOC,"Tip.Doc");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIPDOC,"Tipo Documento");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Núm.Doc");
            vecCab.add(INT_TBL_DAT_NUM_GUI, "Núm.Gui");
            vecCab.add(INT_TBL_DAT_ORD_DES, "Núm.Ord.Des");
            vecCab.add(INT_TBL_DAT_NUM_SOLDEV, "Núm.Sol.Dev.");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Doc");
            vecCab.add(INT_TBL_DAT_BUT_DOC_ASO, "..");
            vecCab.add(INT_TBL_DAT_BOD_ING, "Ingreso");
            vecCab.add(INT_TBL_DAT_BOD_EGR, "Egreso");
            vecCab.add(INT_TBL_DAT_CAN_ING, "Ingreso");
            vecCab.add(INT_TBL_DAT_CAN_EGR, "Egreso");
            vecCab.add(INT_TBL_DAT_SAL_UNI, "Sal.Uni");
            vecCab.add(INT_TBL_DAT_COD_CLI, "Cód.Cli");
            vecCab.add(INT_TBL_DAT_NOM_CLI, "Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_USR_ING, "Usr.Ing");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ING, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_EGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_SAL_UNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
           //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDat.getColumnModel();

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_SEG).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIPDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIPDOC).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_GUI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ORD_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SOLDEV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC_ASO).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SAL_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_BOD_ING).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BOD_EGR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_USR_ING).setPreferredWidth(80);

            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            objPerUsr = new ZafPerUsr(this.objParSis);

            if(!objPerUsr.isOpcionEnabled(2702)){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_TIPDOC, tblDat);
            }
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_GUI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);

            //Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            objTblHeaColGrpDoc = new ZafTblHeaColGrp("   ");
            objTblHeaColGrpDoc.setHeight(16);
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIPDOC));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIPDOC));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_NUM_GUI));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_ORD_DES));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_NUM_SOLDEV));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_FEC_DOC));
            objTblHeaColGrpDoc.add(tcmAux.getColumn(INT_TBL_DAT_BUT_DOC_ASO));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDoc);
            objTblHeaColGrpDoc = null;

            objTblHeaColGrpBod = new ZafTblHeaColGrp(" BODEGAS  ");
            objTblHeaColGrpBod.setHeight(16);
            objTblHeaColGrpBod.add(tcmAux.getColumn(INT_TBL_DAT_BOD_ING));
            objTblHeaColGrpBod.add(tcmAux.getColumn(INT_TBL_DAT_BOD_EGR));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpBod);
            objTblHeaColGrpBod = null;

            objTblHeaColGrpCan = new ZafTblHeaColGrp(" CANTIDADES  ");
            objTblHeaColGrpCan.setHeight(16);
            objTblHeaColGrpCan.add(tcmAux.getColumn(INT_TBL_DAT_CAN_ING));
            objTblHeaColGrpCan.add(tcmAux.getColumn(INT_TBL_DAT_CAN_EGR));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCan);
            objTblHeaColGrpCan = null;

            objTblHeaColGrpAud = new ZafTblHeaColGrp("   ");
            objTblHeaColGrpAud.setHeight(16);
            objTblHeaColGrpAud.add(tcmAux.getColumn(INT_TBL_DAT_SAL_UNI));
            objTblHeaColGrpAud.add(tcmAux.getColumn(INT_TBL_DAT_COD_CLI));
            objTblHeaColGrpAud.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CLI));
            objTblHeaColGrpAud.add(tcmAux.getColumn(INT_TBL_DAT_USR_ING));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAud);
            objTblHeaColGrpAud = null;
            
            //Formato Columnas
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_UNI).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //<editor-fold defaultstate="collapsed" desc="/* comentado */">
                    //Mostrar de color gris las columnas impares.
                    //int intCell=objTblCelRenLbl.getRowRender();
                    //String strCan = (tblDat.getValueAt(intCell, INT_TBL_DAT_CAN_EGR)==null?"0":tblDat.getValueAt(intCell, INT_TBL_DAT_CAN_EGR).toString());
                    //double dblCan = objUti.redondear( (strCan==null?"0":(strCan.equals("")?"0":strCan)) , 4 );
                    //if( dblCan > 0  ){
                    //    objTblCelRenLbl.setBackground( new java.awt.Color(255, 172, 165) );
                    //    objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                    //}else {
                    //    objTblCelRenLbl.setBackground(java.awt.Color.WHITE);
                    //    objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                    //}
                    //</editor-fold>
                }
            });
        

            /**
             * ***********************************************************************
             */
            tcmAux.getColumn(INT_TBL_DAT_COD_SEG).setCellRenderer(objTblCelRenLbl2); 
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIPDOC).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIPDOC).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_NUM_GUI).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SOLDEV).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl2);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setCellRenderer(objTblCelRenLbl2);

            objTblCelRenLbl2.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //<editor-fold defaultstate="collapsed" desc="/* comentado */">
                    //Mostrar de color gris las columnas impares.
                    //int intCell=objTblCelRenLbl2.getRowRender();
                    //String strCan = (tblDat.getValueAt(intCell, INT_TBL_DAT_CAN_EGR)==null?"0":tblDat.getValueAt(intCell, INT_TBL_DAT_CAN_EGR).toString());
                    //double dblCan = objUti.redondear( (strCan==null?"0":(strCan.equals("")?"0":strCan)) , 4 );
                    //if( dblCan > 0  ){
                    //    objTblCelRenLbl2.setBackground( new java.awt.Color(255, 172, 165) );
                    //    objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    //}else {
                    //    objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                    //    objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    //}
                    //</editor-fold>
                }
            });

            /**
             * ***********************************************************************
             */
            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC_ASO).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    switch (objTblCelRenBut.getColumnRender()) {
                        case INT_TBL_DAT_BUT_DOC_ASO:
                            double dblCodTipDoc = objUti.parseDouble(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_COD_TIP_DOC) == null ? "0" : objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_COD_TIP_DOC).toString());
                            String strCan = (objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CAN_EGR) == null ? "0" : objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CAN_EGR).toString());
                            double dblCan = objUti.redondear((strCan == null ? "0" : (strCan.equals("") ? "0" : strCan)), 4);

                            if (dblCan > 0 || (dblCodTipDoc == 46) || (dblCodTipDoc == 172) || (dblCodTipDoc == 153) || (dblCodTipDoc == 2) || (dblCodTipDoc == 3) || (dblCodTipDoc == 4) || (dblCodTipDoc == 205)) {
                                objTblCelRenBut.setText("...");
                            } else {
                                objTblCelRenBut.setText("");
                            }
                            break;
                    }
                }
            });

            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC_ASO).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUT_DOC_ASO:
                                String strCan = (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR).toString());
                                double dblCan = objUti.redondear((strCan == null ? "0" : (strCan.equals("") ? "0" : strCan)), 4);
                                if (dblCan <= 0) {
                                    if (objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING).toString()), 2) == 0) {
                                        objTblCelEdiButGen.setCancelarEdicion(true);
                                    }
                                }
                                break;
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUT_DOC_ASO:
                                double dblCodTipDoc = objUti.parseDouble(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_COD_TIP_DOC) == null ? "0" : objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_COD_TIP_DOC).toString());
                                String strCan = (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR).toString());
                                double dblCan = objUti.redondear((strCan == null ? "0" : (strCan.equals("") ? "0" : strCan)), 4);
                                int intCodReg = Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_REG).toString());
                                if (dblCan > 0 || (dblCodTipDoc == 46) || (dblCodTipDoc == 172) || (dblCodTipDoc == 153) || (dblCodTipDoc == 2) || (dblCodTipDoc == 3) || (dblCodTipDoc == 4) || (dblCodTipDoc == 205)) {
                                    mostrarGuiasRemision(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC).toString(), intCodReg);
                                }
                                break;
                        }
                    }
                }
            });
                        
            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            //Columnas Editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_DOC_ASO);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
                  
            //Sumatorias totales
            int intCol[] = {INT_TBL_DAT_CAN_ING, INT_TBL_DAT_CAN_EGR, INT_TBL_DAT_SAL_UNI};
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_DES_COR_TIPDOC:
                    strMsg = "Descripción corta del tipo documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIPDOC:
                    strMsg = "Descripción larga del tipo documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_DAT_NUM_GUI:
                    strMsg = "Número de guía remisión ";
                    break;
                case INT_TBL_DAT_ORD_DES:
                    strMsg = "Número de orden de despacho ";
                    break;
                case INT_TBL_DAT_NUM_SOLDEV:
                    strMsg = "Número de solicitud de devolución";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_DAT_BUT_DOC_ASO:
                    strMsg = "Presenta los documentos asociados a la OD ";
                    break;                    
                case INT_TBL_DAT_BOD_ING:
                    strMsg = "Bodega de ingreso";
                    break;
                case INT_TBL_DAT_BOD_EGR:
                    strMsg = "Bodega de egreso";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg = "Cantidad de ingreso";
                    break;
                case INT_TBL_DAT_CAN_EGR:
                    strMsg = "Cantidad de egreso";
                    break;
                case INT_TBL_DAT_SAL_UNI:
                    strMsg = "Saldo en unidades";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg = "Código de cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg = "Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_USR_ING:
                    strMsg = "Usuario que ingreso el documento";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
        
    /**
     * Esta función configura el JTable "tblBod".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN, "");
            vecCab.add(INT_TBL_BOD_CHK, "");
            vecCab.add(INT_TBL_BOD_COD_BOD, "Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD, "Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod = new ZafTblMod();
            objTblModBod.setHeader(vecCab);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            //objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux = tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            // objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
            // objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenLbl3 = new ZafTblCelRenLbl();
            objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl3);
            objTblCelRenLbl3 = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;

            //Configurar JTable: Establecer el ListSelectionListener.
            // javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
            // lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux = null;
        } 
        catch (Exception e) {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
       
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBod extends MouseMotionAdapter 
    {

        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblBod.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_BOD_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg = "Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg = "Nombre de la bodega";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }

    public void cargarBod() 
    {
        Connection conn;
        Statement stmLoc;
        ResultSet rstLoc;
        //String strSql = "";
        try 
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario() == 1)
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    strSQL =" SELECT co_bod, tx_nom ";
                    strSQL+=" FROM ( ";
                    strSQL+="        select a2.co_bod, a2.tx_nom ";
                    strSQL+="        from tbm_emp AS a1 ";
                    strSQL+="        inner join tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL+="        where a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+="        order by a1.co_emp, a2.co_bod";
                    strSQL+=" ) as a ";
                }
                else 
                {
                    if (objParSis.getCodigoEmpresaGrupo() == objParSis.getCodigoEmpresa()) 
                    {
                        strSQL =" SELECT co_bod, tx_nom "
                               +" FROM ( "
                               +"        select a1.co_bod, a1.tx_nom from tbr_bodlocprgusr as a "
                               +"        inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) "
                               +"        where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " "
                               +"        and a.co_usr=" + objParSis.getCodigoUsuario() + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                               +"        and a.tx_natbod = 'A' "
                               +" ) as a";
                    }
                    else
                    {
                        strSQL =" SELECT co_bod, tx_nom "
                               +" FROM ( "
                               +"       select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                               +"       inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                               +"       inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                               +"       where a.co_emp=" + objParSis.getCodigoEmpresa() + " and a.co_loc=" + objParSis.getCodigoLocal() + " "
                               +"       and a.co_usr=" + objParSis.getCodigoUsuario() + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                               +"       and a.tx_natbod = 'A' "
                               +" ) as a";
                    }
                }

                //Limpiar vector de datos.
                vecDat.clear();

                //Obtener los registros.
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BOD_LIN, "");
                    vecReg.add(INT_TBL_BOD_CHK, true);
                    vecReg.add(INT_TBL_BOD_COD_BOD, rstLoc.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD, rstLoc.getString("tx_nom"));
                    vecDat.add(vecReg);
                }

                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                blnMarTodChkTblBod = false;

                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conn.close();
                conn = null;
            }
        } 
        catch (SQLException e) {   objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception Evt) {    objUti.mostrarMsgErr_F1(this, Evt);  }
    }

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try 
        {
            intNumFil = objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBod.columnAtPoint(evt.getPoint()) == INT_TBL_BOD_CHK) {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod = false;
                }
                else 
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod = true;
                }
            }
        } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
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
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itmMae"); 
            arlCam.add("d1.co_itm"); 
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Let.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSQL =" SELECT a.*, a1.co_itmMae ";
            strSQL+=" FROM( ";
            strSQL+="      SELECT a.co_emp, a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a1.tx_desCor ";
            strSQL+="      FROM tbm_inv as a LEFT JOIN tbm_var as a1 ON (a1.co_reg=a.co_uni) ";
            strSQL+="      WHERE  a.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+="      AND a.st_reg NOT IN ('U','T') ";  
            strSQL+=" )AS a ";
            strSQL+=" INNER JOIN tbm_equInv AS a1 ON a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm";
            strSQL+=" ORDER BY a.tx_codalt ";
            //System.out.println("configurarVenConItm: "+strSQL);
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);  //tx_codAlt2
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.CENTER);  //tx_desCor
            vcoItm.setCampoBusqueda(1);   //Antes co_itm //Ahora co_itmMae
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    private boolean mostrarVenConItm(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodLetItm);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(4);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        txtCodAlt2 = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        panRptReg = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panRptCanIngEgr = new javax.swing.JPanel();
        panRptCanIng = new javax.swing.JPanel();
        lblCanIng = new javax.swing.JLabel();
        txtCanIng = new javax.swing.JTextField();
        butCanIng = new javax.swing.JButton();
        lblEsp = new javax.swing.JLabel();
        lblResSol = new javax.swing.JLabel();
        txtCanResAut = new javax.swing.JTextField();
        butResSol = new javax.swing.JButton();
        lblEsp1 = new javax.swing.JLabel();
        panRptCanEgr = new javax.swing.JPanel();
        lblCanEgr = new javax.swing.JLabel();
        txtCanEgr = new javax.swing.JTextField();
        butCanEgr = new javax.swing.JButton();
        lblEsp2 = new javax.swing.JLabel();
        lblResVen = new javax.swing.JLabel();
        txtCanResVen = new javax.swing.JTextField();
        butCanRes = new javax.swing.JButton();
        lblEsp3 = new javax.swing.JLabel();
        lblDis = new javax.swing.JLabel();
        txtCanDis = new javax.swing.JTextField();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblItm.setText("Item:");
        panFil.add(lblItm);
        lblItm.setBounds(10, 10, 40, 20);

        txtCodAlt.setBackground(objParSis.getColorCamposObligatorios());
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(90, 10, 90, 20);

        txtNomItm.setBackground(objParSis.getColorCamposObligatorios());
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
        txtNomItm.setBounds(243, 10, 399, 20);

        butItm.setText(".."); // NOI18N
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 10, 20, 20);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(0, 40, 660, 92);

        txtCodAlt2.setBackground(objParSis.getColorCamposObligatorios());
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
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(180, 10, 63, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        panRptReg.setLayout(new java.awt.BorderLayout());

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

        panRptReg.add(spnTot, java.awt.BorderLayout.SOUTH);

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

        panRptReg.add(spnDat, java.awt.BorderLayout.CENTER);

        panRpt.add(panRptReg, java.awt.BorderLayout.CENTER);

        panRptCanIngEgr.setPreferredSize(new java.awt.Dimension(10, 40));
        panRptCanIngEgr.setLayout(new java.awt.GridLayout(2, 1));

        panRptCanIng.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        lblCanIng.setText("Cantidad por ingresar:");
        lblCanIng.setMaximumSize(new java.awt.Dimension(106, 14));
        lblCanIng.setMinimumSize(new java.awt.Dimension(106, 14));
        lblCanIng.setPreferredSize(new java.awt.Dimension(140, 20));
        panRptCanIng.add(lblCanIng);

        txtCanIng.setEditable(false);
        txtCanIng.setBackground(objParSis.getColorCamposSistema());
        txtCanIng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanIng.setPreferredSize(new java.awt.Dimension(90, 20));
        panRptCanIng.add(txtCanIng);

        butCanIng.setText("..");
        butCanIng.setPreferredSize(new java.awt.Dimension(25, 20));
        butCanIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanIngActionPerformed(evt);
            }
        });
        panRptCanIng.add(butCanIng);

        lblEsp.setPreferredSize(new java.awt.Dimension(10, 5));
        panRptCanIng.add(lblEsp);

        lblResSol.setText("Res.Aut.:");
        lblResSol.setToolTipText("Reserva solicitada y autorizada");
        lblResSol.setPreferredSize(new java.awt.Dimension(75, 14));
        panRptCanIng.add(lblResSol);

        txtCanResAut.setEditable(false);
        txtCanResAut.setBackground(objParSis.getColorCamposSistema());
        txtCanResAut.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanResAut.setToolTipText("Cantidad en Reserva (Solicitada y Autorizada)");
        txtCanResAut.setPreferredSize(new java.awt.Dimension(90, 20));
        panRptCanIng.add(txtCanResAut);

        butResSol.setText("..");
        butResSol.setPreferredSize(new java.awt.Dimension(25, 20));
        butResSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butResSolActionPerformed(evt);
            }
        });
        panRptCanIng.add(butResSol);

        lblEsp1.setPreferredSize(new java.awt.Dimension(175, 5));
        panRptCanIng.add(lblEsp1);

        panRptCanIngEgr.add(panRptCanIng);

        panRptCanEgr.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        lblCanEgr.setText("Cantidad por egresar:");
        lblCanEgr.setPreferredSize(new java.awt.Dimension(140, 20));
        panRptCanEgr.add(lblCanEgr);

        txtCanEgr.setEditable(false);
        txtCanEgr.setBackground(objParSis.getColorCamposSistema());
        txtCanEgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanEgr.setPreferredSize(new java.awt.Dimension(90, 20));
        panRptCanEgr.add(txtCanEgr);

        butCanEgr.setText("...");
        butCanEgr.setPreferredSize(new java.awt.Dimension(25, 20));
        butCanEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanEgrActionPerformed(evt);
            }
        });
        panRptCanEgr.add(butCanEgr);

        lblEsp2.setPreferredSize(new java.awt.Dimension(10, 5));
        panRptCanEgr.add(lblEsp2);

        lblResVen.setText("Res.Ven.:");
        lblResVen.setToolTipText("Reserva vendida");
        lblResVen.setPreferredSize(new java.awt.Dimension(75, 14));
        panRptCanEgr.add(lblResVen);

        txtCanResVen.setEditable(false);
        txtCanResVen.setBackground(objParSis.getColorCamposSistema());
        txtCanResVen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanResVen.setToolTipText("Cantidad en Reserva (Vendida)");
        txtCanResVen.setPreferredSize(new java.awt.Dimension(90, 20));
        panRptCanEgr.add(txtCanResVen);

        butCanRes.setText("..");
        butCanRes.setPreferredSize(new java.awt.Dimension(25, 20));
        butCanRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanResActionPerformed(evt);
            }
        });
        panRptCanEgr.add(butCanRes);

        lblEsp3.setPreferredSize(new java.awt.Dimension(10, 5));
        panRptCanEgr.add(lblEsp3);

        lblDis.setText("Disponible:");
        lblDis.setToolTipText("Cantidad Disponible");
        lblDis.setPreferredSize(new java.awt.Dimension(75, 14));
        panRptCanEgr.add(lblDis);

        txtCanDis.setEditable(false);
        txtCanDis.setBackground(objParSis.getColorCamposSistema());
        txtCanDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanDis.setToolTipText("Cantidad disponible");
        txtCanDis.setPreferredSize(new java.awt.Dimension(90, 20));
        panRptCanEgr.add(txtCanDis);

        panRptCanIngEgr.add(panRptCanEgr);

        panRpt.add(panRptCanIngEgr, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

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

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal())
        {
            if (butCon.getText().equals("Consultar")) 
            {
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
                objThrGUI = null;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
        
}//GEN-LAST:event_butCerActionPerformed
       
    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
}//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
}//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItmMae.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(1);
            }
        } 
        else
          txtCodAlt.setText(strCodAlt);
}//GEN-LAST:event_txtCodAltFocusLost


    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
}//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
}//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItmMae.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
           txtNomItm.setText(strNomItm);
}//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
}//GEN-LAST:event_butItmActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butCanEgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanEgrActionPerformed
        mostrarDocumentosEgresos();
    }//GEN-LAST:event_butCanEgrActionPerformed

    private void butCanIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanIngActionPerformed
        mostrarDocumentosIngresos();
    }//GEN-LAST:event_butCanIngActionPerformed
    
    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodLetItm=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodLetItm))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItmMae.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodAlt2.setText(strCodLetItm);
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void butResSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butResSolActionPerformed
        mostrarDocumentosReservasAutorizadas();
    }//GEN-LAST:event_butResSolActionPerformed

    private void butCanResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanResActionPerformed
        mostrarDocumentosReservasVendidas();
    }//GEN-LAST:event_butCanResActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCanEgr;
    private javax.swing.JButton butCanIng;
    private javax.swing.JButton butCanRes;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butResSol;
    private javax.swing.JLabel lblCanEgr;
    private javax.swing.JLabel lblCanIng;
    private javax.swing.JLabel lblDis;
    private javax.swing.JLabel lblEsp;
    private javax.swing.JLabel lblEsp1;
    private javax.swing.JLabel lblEsp2;
    private javax.swing.JLabel lblEsp3;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblResSol;
    private javax.swing.JLabel lblResVen;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptCanEgr;
    private javax.swing.JPanel panRptCanIng;
    private javax.swing.JPanel panRptCanIngEgr;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCanDis;
    private javax.swing.JTextField txtCanEgr;
    private javax.swing.JTextField txtCanIng;
    private javax.swing.JTextField txtCanResAut;
    private javax.swing.JTextField txtCanResVen;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgInf(String strMensaje) 
    {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exitForm() 
    {
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }
    
    private boolean isCamVal() 
    {
        if (txtCodItmMae.getText().equals("")) 
        {
            mostrarMsgInf("<HTML>El ingreso del ítem es obligatorio.</HTML>");
            tabFrm.setSelectedIndex(0);
            txtCodAlt.requestFocus();
            return false;
        }

        //Validar que esté seleccionado al menos una bodega.
        int intNumFilTblBod, i=0, j;
        intNumFilTblBod=objTblModBod.getRowCountTrue();
        for (j=0; j<intNumFilTblBod; j++)
        {
            if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            mostrarMsgInf("<HTML>Debe seleccionar al menos una Bodega.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tabFrm.setSelectedIndex(0);
            tblBod.requestFocus();
            return false;
        }

        return true;
    }
    
    private String getBodegasFiltro()
    {
        String strAuxBod = "";
        for (int j = 0; j < tblBod.getRowCount(); j++) 
        {
            if (tblBod.getValueAt(j, INT_TBL_BOD_CHK) != null) {
                if (tblBod.getValueAt(j, INT_TBL_BOD_CHK).toString().equals("true")) {
                    if (strAuxBod.equals("")) {
                        strAuxBod += tblBod.getValueAt(j, INT_TBL_BOD_COD_BOD).toString();
                    } else {
                        strAuxBod += "," + tblBod.getValueAt(j, INT_TBL_BOD_COD_BOD).toString();
                    }
                }
            }
        }
        return strAuxBod;
    }
    
    private class ZafThreadGUI extends Thread 
    {
        @Override
        public void run() 
        {
            pgrSis.setIndeterminate(true);
            if (!cargarDetReg()) 
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) 
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }

            pgrSis.setIndeterminate(false);

            objThrGUI = null;
        }
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        boolean blnIsCosenco=false;
        BigDecimal bgdCan, bgdSalUni;
        
        //Saber si la empresa que ingreso es COSENCO
        blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
        
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL =" SELECT Seg.co_Seg, * FROM (  \n";
                strSQL+=" SELECT * FROM (  \n";
                strSQL+="    SELECT usr.tx_usr, a2.st_meringegrfisbod, a1.ne_secgrp, a1.ne_secEmp, a2.co_reg, cli.co_empgrp \n";
                strSQL+="         , a1.co_emp, a1.st_reg, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nomBod, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar  \n";
                strSQL+="         , a2.co_doc, a1.ne_numDoc, a1.ne_numGui, a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a1.co_cli, a1.tx_nomCli, a8.ne_numdoc as tx_numDocSolDev \n";
                strSQL+="         , CASE WHEN cli.co_empgrp in ( 0 ) THEN a2.tx_obs1 ELSE ('' || a1.ne_numgui ) END as tx_numGuiRem  \n";
                strSQL+="         , CASE WHEN cli.co_empgrp in ( 0 ) THEN a2.tx_obs1 ELSE ('' || a1.ne_numorddes ) END as ne_numOrdDes  \n";
                strSQL+="         , CASE WHEN cli.co_empgrp in ( 0 ) THEN 0  ELSE 1 END as co_empgrp2\n ";
                strSQL+="         , CASE WHEN cli.co_empgrp in ( 0 ) THEN a2.tx_nombodorgdes ELSE '' END  as tx_nomBod3 \n";
                strSQL+="         , CASE WHEN a1.co_tipdoc in (46, 172, 153) THEN a2.tx_nombodorgdes ELSE '' END  as tx_nomBod2 \n";
                strSQL+="         , CASE WHEN a1.fe_doc >= '2010-01-01' THEN case when a1.co_tipdoc in (153) then case when a2.st_meringegrfisbod in ('S') \n";
                strSQL+="           then 'S' else 'N' end else 'S' end else 'S' END as presenta1 \n";
                strSQL+="         , CASE WHEN a1.fe_doc >= '2010-01-01' THEN  \n";
                strSQL+="           CASE WHEN a1.co_emp in (1) then case when a1.co_cli in (select co_cliEmpOrg  from  tbm_cfgEmpRel as d  where d.co_empOrg=1 and d.co_EmpDes!=1 ) then case when a2.st_meringegrfisbod in ('S') then 'S' else 'N' end else 'S' end \n";
                strSQL+="           else case when a1.co_emp in (2) then case when a1.co_cli in (select co_cliEmpOrg  from  tbm_cfgEmpRel as d  where d.co_empOrg=2 and d.co_EmpDes!=2) then case when a2.st_meringegrfisbod in ('S') then 'S' else 'N' end else 'S' end \n";
                strSQL+="           else case when a1.co_emp in (4) then case when a1.co_cli in (select co_cliEmpOrg  from  tbm_cfgEmpRel as d  where d.co_empOrg=4 and d.co_EmpDes!=4) then case when a2.st_meringegrfisbod in ('S') then 'S' else 'N' end else 'S' end else  'S' end end end \n";        
                strSQL+="           ELSE 'S' END  as presenta2 \n";
                strSQL+="    FROM tbm_cabMovInv AS a1  \n";
                strSQL+="    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strSQL+="    LEFT JOIN tbm_cli as cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli ) \n";
                strSQL+="    INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc) \n";
                strSQL+="    INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod) \n";
                strSQL+="    INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc) \n";
                strSQL+="    INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)  \n";
                strSQL+="    INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_bod=a7.co_bod) \n";
                strSQL+="    LEFT JOIN tbm_cabsoldevven as a8 ON (a8.co_emp=a1.co_emp and a8.co_loc=a1.co_locrelsoldevven and a8.co_tipdoc=a1.co_tipdocrelsoldevven and a8.co_doc=a1.co_docrelsoldevven ) \n";
                strSQL+="    LEFT JOIN tbm_usr as usr ON (usr.co_usr=a1.co_usring ) \n";
                strSQL+="    WHERE a6.co_itmMae=" + txtCodItmMae.getText() + "  \n";
                strSQL+="    AND a1.st_reg NOT IN ('E','I','O') AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C') \n";
                strSQL+="    AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="    AND ( a7.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + " AND a7.co_bodGrp in ( " + getBodegasFiltro() + " )  ) ) as x \n";
                if (blnIsCosenco) 
                {
                    strSQL+=" WHERE  presenta1 IN ('S', 'N') AND presenta2 IN ('S', 'N') \n";
                }
                else 
                {
                    strSQL+=" WHERE  presenta1 IN ('S') AND presenta2 IN ('S') \n";
                }
                strSQL+=" ORDER BY fe_doc, ne_secgrp, co_reg \n";
                strSQL+=" ) as z \n";
                strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as Seg \n";
                strSQL+=" ON (Seg.co_empRelCabMovInv=z.co_emp AND Seg.co_locRelCabMovInv=z.co_loc AND Seg.co_tipDocRelCabMovInv=z.co_tipDoc AND Seg.co_DocRelCabMovInv=z.co_Doc )  \n";
                strSQL+=" ORDER BY z.fe_doc, z.ne_secgrp, z.co_reg \n";
                System.out.println("ZafCom51.cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                
                vecDat.clear();
                bgdSalUni = BigDecimal.ZERO;
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_SEG, rst.getString("co_seg"));
                    vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIPDOC, rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIPDOC, rst.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_DAT_NUM_GUI, rst.getString("tx_numGuiRem"));  // rst.getString("ne_numgui"));
                    vecReg.add(INT_TBL_DAT_ORD_DES, rst.getString("ne_numOrdDes"));
                    vecReg.add(INT_TBL_DAT_NUM_SOLDEV, rst.getString("tx_numDocSolDev"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_BUT_DOC_ASO, "");
                    
                    bgdCan = rst.getBigDecimal("nd_can");
                    
                    if (bgdCan.compareTo(BigDecimal.ZERO) >= 0) 
                    {
                        vecReg.add(INT_TBL_DAT_BOD_ING, rst.getString("a4_tx_nomBod"));

                        if ((rst.getInt("co_tipdoc") == 46) || (rst.getInt("co_tipdoc") == 172) || (rst.getInt("co_tipdoc") == 153)) 
                        {
                            if (rst.getString("tx_nomBod2")!=null && rst.getString("a4_tx_nomBod")!=null && rst.getString("tx_nomBod2").equals(rst.getString("a4_tx_nomBod"))) 
                            {
                                vecReg.add(INT_TBL_DAT_BOD_EGR, "");
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_BOD_EGR, rst.getString("tx_nomBod2"));
                            }
                        }
                        else 
                        {
                            if (rst.getInt("co_empgrp2") == 0) 
                            {
                                if (rst.getString("tx_nomBod3")!=null && rst.getString("a4_tx_nomBod")!=null &&  rst.getString("tx_nomBod3").equals(rst.getString("a4_tx_nomBod"))) 
                                {
                                   vecReg.add(INT_TBL_DAT_BOD_EGR, ""); 
                                }
                                else{
                                    vecReg.add(INT_TBL_DAT_BOD_EGR, rst.getString("tx_nomBod3"));
                                }
                            } 
                            else 
                            {
                                vecReg.add(INT_TBL_DAT_BOD_EGR, "");
                            }
                        }
                        vecReg.add(INT_TBL_DAT_CAN_ING, "" + bgdCan);
                        vecReg.add(INT_TBL_DAT_CAN_EGR, null);
                    }
                    else 
                    {
                        if ((rst.getInt("co_tipdoc") == 46) || (rst.getInt("co_tipdoc") == 172) || (rst.getInt("co_tipdoc") == 153) ) 
                        {
                            if (rst.getString("tx_nomBod2")!=null && rst.getString("tx_nomBod2")!=null && rst.getString("tx_nomBod2").equals(rst.getString("a4_tx_nomBod"))) 
                            {
                                vecReg.add(INT_TBL_DAT_BOD_ING, "");
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_BOD_ING, rst.getString("tx_nomBod2"));
                            }
                        } 
                        else 
                        {
                            if (rst.getInt("co_empgrp2") == 0) 
                            {
                                if (rst.getString("tx_nomBod3")!=null && rst.getString("a4_tx_nomBod")!=null && rst.getString("tx_nomBod3").equals(rst.getString("a4_tx_nomBod"))) 
                                {
                                    vecReg.add(INT_TBL_DAT_BOD_ING, "");
                                }
                                else{
                                    vecReg.add(INT_TBL_DAT_BOD_ING, rst.getString("tx_nomBod3"));
                                }
                            } 
                            else 
                            {
                                vecReg.add(INT_TBL_DAT_BOD_ING, "");
                            }
                        }
                        vecReg.add(INT_TBL_DAT_BOD_EGR, rst.getString("a4_tx_nomBod"));
                        vecReg.add(INT_TBL_DAT_CAN_ING, null);
                        vecReg.add(INT_TBL_DAT_CAN_EGR, "" + bgdCan.abs());
                    }

                    strAux = rst.getString("st_reg");
                    if ("ARCF".indexOf(strAux) != -1) 
                    {
                        bgdSalUni = bgdSalUni.add(bgdCan);
                    }
                    vecReg.add(INT_TBL_DAT_SAL_UNI, bgdSalUni);
                    vecReg.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                    vecReg.add(INT_TBL_DAT_NOM_CLI, rst.getString("tx_nomcli"));

                    if (rst.getInt("co_tipdoc") == 46 || rst.getInt("co_tipdoc") == 172 || rst.getInt("co_tipdoc") == 204 || rst.getInt("co_tipdoc") == 234) 
                    {
                        vecReg.add(INT_TBL_DAT_USR_ING, rst.getString("tx_usr"));
                    } 
                    else 
                    {
                        vecReg.add(INT_TBL_DAT_USR_ING, "");
                    }
                    vecDat.add(vecReg);
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
                con.close();
                con = null;
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTblTot.calcularTotales();
                objTblTot.setValueAt("" + (objUti.parseDouble(objTblTot.getValueAt(INT_TBL_DAT_LIN, INT_TBL_DAT_CAN_ING)) - objUti.parseDouble(objTblTot.getValueAt(INT_TBL_DAT_LIN, INT_TBL_DAT_CAN_EGR))), INT_TBL_DAT_LIN, INT_TBL_DAT_SAL_UNI);
                cargarCantidadesTotales();
                lblTit.setText(txtCodAlt.getText() + " - "+txtCodAlt2.getText()+" : " + txtNomItm.getText());
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                System.gc();
            }
        } 
        catch (java.sql.SQLException e) {  blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    private boolean cargarCantidadesTotales()
    {
        boolean blnRes=true;
        BigDecimal bgdAux=new BigDecimal("0");
        try
        {
            System.gc();
            //SubDetalle
            txtCanIng.setText("");
            txtCanEgr.setText("");
            txtCanResAut.setText("");
            txtCanResVen.setText("");
            txtCanDis.setText("");          
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();

                //Cantidades Can.Res.Ven / Can.Dis
                strSQL =" SELECT SUM(x.CanDis) AS nd_canDis, SUM(x.CanResVen) AS nd_canResVen  \n";
                strSQL+=" FROM  \n";
                strSQL+=" ( \n";
                strSQL+="     SELECT CASE WHEN a1.nd_canDis  IS NULL THEN 0 ELSE a1.nd_canDis  END  AS CanDis\n";
                strSQL+="          , CASE WHEN a1.nd_canResVen  IS NULL THEN 0 ELSE a1.nd_canResVen  END  AS CanResVen \n";
                strSQL+="     FROM tbm_invBod AS a1  \n";
                strSQL+="     INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)  \n";
                strSQL+="     INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod) \n"; 
                strSQL+="     WHERE a1.co_emp<>"+objParSis.getCodigoEmpresaGrupo()+"\n";
                strSQL+="     AND a2.co_itmMae="+txtCodItmMae.getText()+"\n";
                strSQL+="     AND ( a3.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a3.co_bodGrp IN ( "+getBodegasFiltro()+" )  ) \n";
                strSQL+=" ) as x \n";
                System.out.println("ZafCom51.cargarDetReg2.Cantidades-tbm_invBod: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {                   
                    bgdAux= BigDecimal.valueOf(rst.getDouble("nd_canResVen"));
                    txtCanDis.setText(""+objUti.formatearNumero(rst.getString("nd_canDis"), objParSis.getFormatoNumero(), true));
                    txtCanResVen.setText(""+bgdAux);
                }
                rst.close();
                rst = null;
                
                //Cantidad Reserva Solicitada y Autorizada.
                strSQL =" SELECT a3.co_itmMae, SUM(CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END) AS nd_canAutRes \n";     
                strSQL+=" FROM tbm_cabCotVen as a1 \n";
                strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)\n";
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm \n";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a4 ON a4.co_Emp=a1.co_emp AND a4.co_bod=a2.co_bod \n";
                strSQL+=" WHERE a1.st_solResInv <> 'C' AND a1.st_autSolResInv = 'A' \n";
                strSQL+=" AND a1.co_tipSolResInv IS NOT NULL \n";
                strSQL+=" AND a1.st_reg IN ('A','U','E') \n";
                strSQL+=" AND a3.co_itmMae="+txtCodItmMae.getText()+"\n";
                strSQL+=" AND a4.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
                strSQL+=" GROUP BY a3.co_itmMae \n";
                System.out.println("CantidadSolicitudReserva: "+strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtCanResAut.setText(""+objUti.formatearNumero(rst.getString("nd_canAutRes"), objParSis.getFormatoNumero(), true));
                }
                rst.close();
                rst=null;
                  
                //Cantidad Reserva Vendida (Confirmado Ingreso en Despacho)
                strSQL =" SELECT a2.co_itmMae, (("+bgdAux+") + SUM (CASE WHEN a1.nd_canDesEntCli IS NULL THEN 0 ELSE a1.nd_canDesEntCli END) ) AS nd_CanResDes\n";
                strSQL+=" FROM tbm_cabMovInv as a\n";
                strSQL+=" INNER JOIN tbm_detMovInv AS a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc)\n";
                strSQL+=" INNER JOIN tbm_equInv as a2 ON (a.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm )\n";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)\n";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a.co_emp=a4.co_emp AND a.co_loc=a4.co_loc AND a.co_tipDoc=a4.co_tipDoc ) \n";            
                strSQL+=" WHERE a.st_reg IN ('A') AND a1.nd_can>0 AND a.tx_tipMov IS NOT NULL  AND a1.st_merIngEgrFisBod IN ('S')\n";
                strSQL+=" AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END ) NOT IN ('F')\n";       
                strSQL+=" AND (CASE WHEN a1.nd_canDesEntCli IS NULL THEN 0 ELSE a1.nd_canDesEntCli END) >0 \n";
                strSQL+=" AND a2.co_itmMae="+txtCodItmMae.getText()+"\n";
                strSQL+=" AND a3.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";       
                strSQL+=" GROUP BY a2.co_itmMae \n"; 
                System.out.println("CantidadReservaVendidaConfirmadaDespacho: "+strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtCanResVen.setText(""+objUti.formatearNumero(rst.getDouble("nd_CanResDes"), objParSis.getFormatoNumero(), true));
                }
                rst.close();
                rst=null;

                //Cantidad Total por Ingresar
                strSQL =" SELECT a2.co_itmMae, (SUM(a2.nd_canIngBod) ) as nd_CanPenIng \n";
                strSQL+=" FROM ( \n";
                strSQL+="   SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.st_conInv, a.tx_tipMov, a.st_reg \n";
                strSQL+="   FROM tbm_cabMovInv as a  \n";
                strSQL+="   WHERE a.st_reg IN ('A') AND a.tx_tipMov IS NOT NULL AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
                strSQL+=" ) AS a1  \n"; 
                strSQL+=" INNER JOIN  \n";
                strSQL+=" ( \n";
                strSQL+="   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg, a.co_itm, a.co_bod, a1.co_itmMae \n";		
                strSQL+="        , a.st_merIngEgrFisBod, a.tx_codAlt, a.tx_codAlt2, a.nd_can, a2.co_bodGrp \n";
                strSQL+="        , CASE WHEN a.nd_canIngBod IS NULL THEN 0 ELSE (a.nd_canIngBod) END as nd_canIngBod   \n";
                strSQL+="   FROM tbm_detMovInv AS a \n";
                strSQL+="   INNER JOIN tbm_cabSegMovInv as a3 ON (a.co_emp=a3.co_empRelCabMovInv AND a.co_loc=a3.co_locRelCabMovInv AND a.co_tipDoc=a3.co_tipDocRelCabMovInv AND a.co_doc=a3.co_docRelCabMovInv)\n"; 
                strSQL+="   INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) \n";
                strSQL+="   INNER JOIN tbr_bodEmpBodGrp AS a2 ON a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod \n";
                strSQL+="   WHERE a.nd_can>0 AND a.st_merIngEgrFisBod IN ('S') \n";
                strSQL+="   AND a1.co_itmMae="+txtCodItmMae.getText()+"\n";
                strSQL+="   AND a2.co_bodGrp IN ( "+getBodegasFiltro()+" )\n";
                strSQL+=" ) AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc) \n";
                strSQL+=" GROUP BY a2.co_itmMae \n";
                System.out.println("CantidadPorIngresar: "+strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtCanIng.setText(""+objUti.formatearNumero(rst.getString("nd_CanPenIng"), objParSis.getFormatoNumero(), true));
                }
                rst.close();
                rst = null;
                
                //Cantidad Total por Egresar
                strSQL =" SELECT a2.co_itmMae, (SUM(a2.nd_canEgrBod) + SUM(a2.nd_CanEgrDes) ) as nd_CanPenEgr \n";
                strSQL+=" FROM ( \n";
                strSQL+="   SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.st_conInv, a.tx_tipMov, a.st_reg \n";
                strSQL+="   FROM tbm_cabMovInv as a  \n";
                strSQL+="   WHERE a.st_reg IN ('A') AND a.tx_tipMov IS NOT NULL AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
                strSQL+=" ) AS a1  \n"; 
                strSQL+=" INNER JOIN  \n";
                strSQL+=" ( \n";
                strSQL+="   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg, a.co_itm, a.co_bod, a1.co_itmMae \n";		
                strSQL+="        , a.st_merIngEgrFisBod, a.tx_codAlt, a.tx_codAlt2, a.nd_can, a2.co_bodGrp \n";
                strSQL+="        , CASE WHEN a.nd_canEgrBod IS NULL THEN 0 ELSE (a.nd_canEgrBod*-1) END as nd_canEgrBod   \n";
                strSQL+="        , CASE WHEN a.nd_CanDesEntCli IS NULL THEN 0 ELSE (a.nd_CanDesEntCli*-1) END as nd_CanEgrDes  \n"; 
                strSQL+="   FROM tbm_detMovInv AS a \n";
                strSQL+="   LEFT OUTER JOIN tbm_cabSegMovInv as a3 ON (a.co_emp=a3.co_empRelCabMovInv AND a.co_loc=a3.co_locRelCabMovInv AND a.co_tipDoc=a3.co_tipDocRelCabMovInv AND a.co_doc=a3.co_docRelCabMovInv)\n"; 
                strSQL+="   INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) \n";
                strSQL+="   INNER JOIN tbr_bodEmpBodGrp AS a2 ON a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod \n";
                strSQL+="   WHERE a.nd_can<0 AND a.st_merIngEgrFisBod IN ('S') \n";
                strSQL+="   AND a1.co_itmMae="+txtCodItmMae.getText()+"\n";
                strSQL+="   AND a2.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
                strSQL+=" ) AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc) \n";
                strSQL+=" GROUP BY a2.co_itmMae \n";
                System.out.println("CantidadPorEgresar: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCanEgr.setText(""+objUti.formatearNumero(rst.getString("nd_CanPenEgr"), objParSis.getFormatoNumero(), true));
                }     
                rst.close();
                rst = null;
          
                stm.close();
                stm=null;
                con.close();
                con =null;
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
    
    private void mostrarDocumentosIngresos()
    {
        /* INGRESOS */
        strSQL =" SELECT a2.co_seg, a2.co_bodGrp, a2.co_itmMae, a1.fe_doc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, 0 as ne_numOrdDes \n";
        strSQL+="      , a1.tx_desCor, a1.tx_desLar, a1.co_cli, a1.tx_nomcli, a2.nd_Can, a2.nd_canCon, a2.nd_canNunRec, a2.nd_canPenTot \n";     
        strSQL+="      , CASE WHEN a1.tx_tipMov NOT IN ('I','V') THEN a2.nd_canIngBod ELSE 0 END as nd_canPenBod \n";
        strSQL+="      , CASE WHEN a1.tx_tipMov IN ('I','V') THEN a2.nd_canIngBod ELSE 0 END as nd_canPenDes \n";     
        strSQL+="  FROM ( \n";
        strSQL+="    SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.st_conInv, a.tx_tipMov, a.st_reg \n"; 
        strSQL+=" 	  , a.co_cli, a.tx_nomCli, a1.tx_descor, a1.tx_deslar \n"; 
        strSQL+="    FROM tbm_cabMovInv as a \n";
        strSQL+="    INNER JOIN tbm_cabTipDoc as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc \n";
        strSQL+="    WHERE a.st_reg IN ('A') AND a.tx_tipMov IS NOT NULL AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
        strSQL+="  ) AS a1 \n";   
        strSQL+="  INNER JOIN \n"; 
        strSQL+="  ( \n";
        strSQL+="    SELECT a3.co_Seg, a2.co_bodGrp, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg, a.co_itm, a.co_bod, a1.co_itmMae \n"; 		
        strSQL+=" 	  , a.st_merIngEgrFisBod, a.tx_codAlt, a.tx_codAlt2, a.nd_can, a.nd_canCon, a.nd_canNunRec \n";
        strSQL+="         , CASE WHEN a.nd_canPen IS NULL THEN 0 ELSE a.nd_canPen END as nd_canPenTot \n";
        strSQL+=" 	  , CASE WHEN a.nd_canIngBod IS NULL THEN 0 ELSE (a.nd_canIngBod) END as nd_canIngBod \n";
        strSQL+="    FROM tbm_detMovInv AS a \n"; 
        strSQL+="    INNER JOIN tbm_cabSegMovInv as a3 ON (a.co_emp=a3.co_empRelCabMovInv AND a.co_loc=a3.co_locRelCabMovInv AND a.co_tipDoc=a3.co_tipDocRelCabMovInv AND a.co_doc=a3.co_docRelCabMovInv)\n"; 
        strSQL+="    INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) \n";
        strSQL+="    INNER JOIN tbr_bodEmpBodGrp AS a2 ON a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod \n";
        strSQL+="    WHERE a.nd_can>0 AND a.st_merIngEgrFisBod IN ('S') \n";
        strSQL+="    AND a1.co_itmMae="+txtCodItmMae.getText()+"\n";
        strSQL+="    AND a2.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
        strSQL+=" ) AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc) \n";
        strSQL+=" WHERE (a2.nd_canIngBod>0 ";
        if(objParSis.getCodigoUsuario()==1){
            strSQL+=" OR a2.nd_canPenTot>0 ";
        }
        strSQL+=" )\n";
        //System.out.println("ZafCom51.mostrarDocumentosIngresos: " + strSQL);
        ZafCom51_01 obj = new ZafCom51_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strSQL, false);
        obj.show();
        obj = null;
    }
    
    private void mostrarDocumentosEgresos()
    {        
        /* EGRESOS */
        strSQL =" SELECT a2.co_Seg, a2.co_bodGrp, a2.co_itmMae, a1.fe_doc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a3.ne_numOrdDes \n";
        strSQL+="       , a1.tx_desCor, a1.tx_desLar, a1.co_cli, a1.tx_nomcli \n";
        strSQL+="       , a2.nd_Can, a2.nd_canCon, a2.nd_canNunRec, a2.nd_canPenTot \n";     
        strSQL+="       , a2.nd_canEgrBod as nd_canPenBod, a2.nd_canEgrDes AS nd_canPenDes \n";     
        strSQL+=" FROM ( \n";
        strSQL+="     SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.st_conInv, a.tx_tipMov, a.st_reg \n"; 
        strSQL+=" 	  , a.co_cli, a.tx_nomCli, a1.tx_descor, a1.tx_deslar \n"; 
        strSQL+="     FROM tbm_cabMovInv as a \n";
        strSQL+="     INNER JOIN tbm_cabTipDoc as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc \n"; 
        strSQL+="     WHERE a.st_reg IN ('A') AND a.tx_tipMov IS NOT NULL AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
        strSQL+=" ) AS a1  \n";  
        strSQL+=" INNER JOIN \n"; 
        strSQL+=" ( \n";
        strSQL+="     SELECT a3.co_Seg, a2.co_bodGrp, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg, a.co_itm, a.co_bod, a1.co_itmMae \n";  		
        strSQL+=" 	  , a.st_merIngEgrFisBod, a.tx_codAlt, a.tx_codAlt2, a.nd_can, a.nd_canCon, a.nd_canNunRec \n";
        strSQL+=" 	  , CASE WHEN a.nd_canPen IS NULL THEN 0 ELSE (a.nd_canPen*-1) END as nd_canPenTot \n";
        strSQL+=" 	  , CASE WHEN a.nd_canEgrBod IS NULL THEN 0 ELSE (a.nd_canEgrBod*-1) END as nd_canEgrBod \n";
        strSQL+=" 	  , CASE WHEN a.nd_canDesEntCli IS NULL THEN 0 ELSE (a.nd_canDesEntCli*-1) END as nd_canEgrDes\n";
        strSQL+="     FROM tbm_detMovInv AS a  \n";
        strSQL+="     LEFT OUTER JOIN tbm_cabSegMovInv as a3 ON (a.co_emp=a3.co_empRelCabMovInv AND a.co_loc=a3.co_locRelCabMovInv AND a.co_tipDoc=a3.co_tipDocRelCabMovInv AND a.co_doc=a3.co_docRelCabMovInv)\n"; 
        strSQL+="     INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) \n";
        strSQL+="     INNER JOIN tbr_bodEmpBodGrp AS a2 ON a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod \n";
        strSQL+="     WHERE a.nd_can<0 AND a.st_merIngEgrFisBod IN ('S') \n";
        strSQL+="     AND a1.co_itmMae="+txtCodItmMae.getText()+"\n";
        strSQL+="     AND a2.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
        strSQL+=" ) AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc) \n";
        strSQL+=" INNER JOIN \n";
        strSQL+=" ( \n";
        strSQL+="     SELECT a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.co_regRel, ('OD' ||'#'|| a.ne_numOrdDes) AS ne_numOrdDes\n";
        strSQL+="     FROM tbm_cabGuiRem AS a  \n";
        strSQL+="     INNER JOIN tbm_detGuiRem AS a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc\n";
        strSQL+="     WHERE a.st_reg IN ('A') /*AND a.ne_numOrdDes>0*/ \n";
        strSQL+=" ) AS a3 ON a3.co_empRel=a1.co_emp AND a3.co_locRel=a1.co_loc AND a3.co_tipDocRel=a1.co_tipDoc AND a3.co_docRel=a1.co_doc AND a3.co_regRel=a2.co_reg\n";
        strSQL+=" WHERE (a2.nd_canEgrBod>0 OR a2.nd_canPenTot>0)\n";
        //System.out.println("ZafCom51.mostrarDocumentosEgresos: " + strSQL);
        ZafCom51_01 obj = new ZafCom51_01(JOptionPane.getFrameForComponent(this), true, objParSis, strSQL, true);
        obj.show();
        obj = null;
    }
    
    private void mostrarDocumentosReservasAutorizadas()
    {        
        strSQL =" SELECT a1.co_Seg, a.fe_doc, a.tx_nomLoc, a.co_Emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.ne_numDoc, a.tx_DesCor, a.tx_desLar, a.co_cli, a.tx_nomCli, a.co_itmMae, a.nd_CanRes\n";
        strSQL+=" FROM (\n";
        strSQL+="       SELECT a1.fe_cot as fe_doc, a1.co_emp, a1.co_loc, 0 AS co_tipDoc, a5.tx_nom AS tx_nomLoc, CAST ('COTVEN' AS CHARACTER VARYING) AS tx_descor, CAST ('Cotizaciones de Venta' AS CHARACTER VARYING) AS tx_desLar\n";
        strSQL+="            , a1.co_cot AS co_doc, a1.co_cot AS ne_numDoc, a1.co_cli, a6.tx_nom as tx_nomCli, a3.co_itmMae, SUM(CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END) AS nd_CanRes \n";
        strSQL+="       FROM tbm_cabCotVen as a1 \n";
        strSQL+="       INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
        strSQL+="       INNER JOIN tbm_loc AS a5 ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc \n";
        strSQL+="       INNER JOIN tbm_equInv AS a3 ON a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm \n";
        strSQL+="       INNER JOIN tbm_cli AS a6 ON a6.co_emp=a1.co_emp AND a6.co_cli=a1.co_cli \n";
        strSQL+="       INNER JOIN tbr_bodEmpBodGrp AS a4 ON a4.co_Emp=a1.co_emp AND a4.co_bod=a2.co_bod \n";
        strSQL+="       WHERE a1.st_solResInv <> 'C' AND a1.st_autSolResInv = 'A' \n";
        strSQL+="       AND a1.co_tipSolResInv IS NOT NULL \n";
        strSQL+="       AND a1.st_reg IN ('A','U','E') \n";
        strSQL+="       AND a3.co_itmMae ="+txtCodItmMae.getText()+" \n";
        strSQL+="       AND a4.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
        strSQL+="       GROUP BY a1.fe_cot, a1.co_emp, a1.co_loc, a5.tx_nom, a1.co_cot, a1.co_cli, a6.tx_nom, a3.co_itmMae \n";
        strSQL+=" ) AS a ";
        strSQL+=" INNER JOIN tbm_cabSegMovInv as a1 ON (a.co_emp=a1.co_empRelCabCotVen AND a.co_loc=a1.co_locRelCabCotVen AND a.co_doc=a1.co_cotRelCabCotVen) \n";
        if(objParSis.getCodigoUsuario()!=1){
            strSQL+=" WHERE nd_canRes>0 \n";
        }
        strSQL+=" GROUP BY a1.co_Seg, a.fe_doc, a.tx_nomLoc, a.co_Emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.ne_numDoc, a.tx_DesCor, a.tx_desLar, a.co_cli, a.tx_nomCli, a.co_itmMae, a.nd_CanRes\n";
        strSQL+=" ORDER BY a.co_Emp, a.co_loc, a.co_Doc\n";
        
        System.out.println("ZafCom51.mostrarDocumentosReservasAutorizadas: " + strSQL);
        ZafCom51_03 obj = new ZafCom51_03(JOptionPane.getFrameForComponent(this), true, objParSis, strSQL);
        obj.show();
        obj = null;
    }
    
    private void mostrarDocumentosReservasVendidas()
    {   
        strSQL =" SELECT * FROM ( \n";
        strSQL+=" SELECT a1.co_Seg, a.fe_doc, a.tx_nomLoc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_DesCor, a.tx_desLar, a.co_cli, a.tx_nomCli, a.co_itmMae, SUM (a.nd_Can) AS nd_CanRes \n";
        strSQL+=" FROM( \n";
        strSQL+="      SELECT a1.fe_cot as fe_doc, a1.co_emp, a1.co_loc, 0 AS co_tipDoc, a5.tx_nom AS tx_nomLoc, 'COTVEN' AS tx_descor, 'Cotizaciones de Venta' AS tx_desLar, a1.co_cot AS co_doc \n";
        strSQL+="           , a1.co_cot AS ne_numDoc, a1.co_cli, a6.tx_nom as tx_nomCli, a3.co_itmMae, SUM(CASE WHEN a2.nd_canLoc IS NULL THEN 0 ELSE a2.nd_canLoc END) AS nd_can \n";            
        strSQL+="      FROM tbm_cabCotVen as a1 \n";
        strSQL+="      INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
        strSQL+="      INNER JOIN tbm_loc AS a5 ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc \n";
        strSQL+="      INNER JOIN tbm_equInv AS a3 ON a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm \n";
        strSQL+="      INNER JOIN tbm_cli AS a6 ON a6.co_emp=a1.co_emp AND a6.co_cli=a1.co_cli \n";
        strSQL+="      INNER JOIN tbr_bodEmpBodGrp AS a4 ON a4.co_Emp=a1.co_emp AND a4.co_bod=a2.co_bod \n";
        strSQL+="      WHERE a1.st_reg IN ('E','L') and tx_momGenFac='F' \n";
        strSQL+="      AND a3.co_itmMae="+txtCodItmMae.getText()+" \n";
        strSQL+="      AND a4.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
        strSQL+="      GROUP BY a1.fe_cot, a1.co_emp, a1.co_loc, a5.tx_nom, a1.co_cot, a2.co_reg, a2.tx_codAlt, a3.co_itmMae, a1.co_cli, a6.tx_nom  \n";
        strSQL+="   UNION ALL \n";
        strSQL+="       SELECT a3.fe_doc, a3.co_emp, a3.co_loc, a3.co_tipDoc, a4.tx_nom AS tx_nomLoc, a3.tx_descor, a3.tx_desLar \n";
        strSQL+="            , a3.co_doc, a3.ne_numDoc, a3.co_cli, a5.tx_nom AS tx_nomCli, a1.co_itmMae, a1.nd_Can \n"; 
        strSQL+="       FROM ( \n";
        strSQL+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_itm, a2.co_itmMae, (SUM(a2.nd_canConIng) ) as nd_Can \n";
        strSQL+="             FROM ( \n";
        strSQL+="                   SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.st_conInv, a.tx_tipMov, a.st_reg, a.tx_tipMov \n";
        strSQL+="                   FROM tbm_cabMovInv as a \n"; 
        strSQL+="                   INNER JOIN tbm_cabTipDoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc) \n";
        strSQL+="                   WHERE a.st_reg IN ('A') AND a.tx_tipMov IS NOT NULL AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
        strSQL+="             ) AS a1 \n";  
        strSQL+="             INNER JOIN \n"; 
        strSQL+="             ( \n";
        strSQL+="                   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg, a.co_itm, a.co_bod, a1.co_itmMae \n";	
        strSQL+="  	                 , a.st_merIngEgrFisBod, a.tx_codAlt, a.tx_codAlt2, a.nd_can, a2.co_bodGrp \n"; 
        strSQL+="  	                 , CASE WHEN a.nd_canDesEntCli IS NULL THEN 0 ELSE a.nd_canDesEntCli END AS nd_canConIng \n";
        strSQL+="                   FROM tbm_detMovInv AS a \n";
        strSQL+="                   INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) \n";
        strSQL+="                   INNER JOIN tbr_bodEmpBodGrp AS a2 ON a2.co_emp=a.co_emp AND a2.co_bod=a.co_bod \n";
        strSQL+="                   WHERE a.nd_can>0 AND a.st_merIngEgrFisBod IN ('S') \n";
        strSQL+="                   AND (CASE WHEN a.nd_canDesEntCli IS NULL THEN 0 ELSE a.nd_canDesEntCli END) >0 \n";
        strSQL+=" 		    AND a1.co_itmMae="+txtCodItmMae.getText()+" \n";
        strSQL+=" 		    AND a2.co_bodGrp IN ( "+getBodegasFiltro()+" ) \n";
        strSQL+="             ) AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc) \n"; 
        strSQL+="             GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_itm,  a2.co_itmMae \n";
        strSQL+="       ) AS a1 \n";
        strSQL+="       INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabMovInv AND a1.co_loc=a2.co_locRelCabMovInv AND \n";   
        strSQL+="  				              a1.co_tipDoc=a2.co_tipDocRelCabMovInv AND a1.co_doc=a2.co_docRelCabMovInv) \n"; 
        strSQL+="       INNER JOIN (  \n"; 
        strSQL+="             SELECT a1.co_seg, a2.co_emp, a2.co_loc, 0 AS co_tipDoc, a2.co_cot AS co_doc, a2.co_cot AS ne_numDoc, a2.fe_cot AS fe_doc, a2.co_cli \n";
        strSQL+="                  , a3.co_itm, CAST('COTVEN' AS CHARACTER VARYING) AS tx_descor, CAST('Cotizaciones de Venta' AS CHARACTER VARYING) AS tx_desLar \n";
        strSQL+="             FROM tbm_cabSegMovInv as a1 \n"; 
        strSQL+="             INNER JOIN tbm_cabCotVen as a2 ON (a1.co_empRelCabCotVen=a2.co_emp AND a1.co_locRelCabCotVen=a2.co_loc AND a1.co_cotRelCabCotVen=a2.co_cot) \n";  	
        strSQL+="             INNER JOIN tbm_detCotVen as a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_cot=a2.co_cot) \n";				       
        strSQL+="             WHERE a2.st_reg IN ('E','L') and a2.tx_momGenFac='F' \n";
        strSQL+="             GROUP BY a1.co_seg, a2.co_emp, a2.co_loc, a2.co_cot, a2.fe_cot, a2.co_cli, a3.co_itm \n";
        strSQL+="       ) AS a3 ON (a3.co_seg=a2.co_seg AND a3.co_emp=a1.co_emp AND a3.co_itm=a1.co_itm) \n"; 
        strSQL+="       INNER JOIN tbm_loc AS a4 ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc \n"; 
        strSQL+="       INNER JOIN tbm_cli AS a5 ON a5.co_emp=a3.co_emp AND a5.co_cli=a3.co_cli \n"; 
        strSQL+=" ) as a \n";
        strSQL+=" INNER JOIN tbm_cabSegMovInv as a1 ON (a.co_emp=a1.co_empRelCabCotVen AND a.co_loc=a1.co_locRelCabCotVen AND a.co_doc=a1.co_cotRelCabCotVen) \n";   
        strSQL+=" GROUP BY a1.co_Seg, a.fe_doc, a.tx_nomLoc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_DesCor, a.tx_desLar, a.co_cli, a.tx_nomCli, a.co_itmMae \n";
        strSQL+=" ) AS a ";
        if(objParSis.getCodigoUsuario()!=1){
            strSQL+=" WHERE nd_canRes>0 \n";
        }
        strSQL+=" ORDER BY a.co_emp, a.co_loc, a.co_doc\n";
        
        System.out.println("ZafCom51.mostrarDocumentosReservasVendidas: " + strSQL);
        ZafCom51_03 obj = new ZafCom51_03(JOptionPane.getFrameForComponent(this), true, objParSis, strSQL);
        obj.show();
        obj = null;
    }
    
    private void mostrarGuiasRemision(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, int intCodReg) 
    {
        String strSql01 = "", strSql02 = "";
        try 
        {
            strSql01 =" SELECT * FROM ( "
                     +"      select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc, abs(a1.nd_can) as cangui,  "
                     +"             case when a4.ne_numorddes is null then 0 else a4.ne_numorddes end as numdes "
                     +"      from tbm_detmovinv as  a "
                     +"      inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc and a1.co_regrel=a.co_reg ) "
                     +"      inner join tbm_cabguirem as a4 on (a4.co_emp=a1.co_emp and a4.co_loc=a1.co_loc and a4.co_tipdoc=a1.co_tipdoc and a4.co_doc=a1.co_doc  ) "
                     +"      inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
                     +"      where a.co_emp=" + strCodEmp + " and a.co_loc=" + strCodLoc + " and a.co_tipdoc=" + strCodTipDoc + " and a.co_doc=" + strCodDoc + " and a.co_reg=" + intCodReg + "  and a4.st_reg='A' "
                     +" ) as x  "
                     +" WHERE ne_numdoc > 0  ";

            strSql02 =" SELECT a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc, abs(a3.nd_can) as cangui "
                    + " FROM tbm_detmovinv as  a "
                     +" INNER JOIN tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc and a1.co_regrel=a.co_reg ) "
                     +" INNER JOIN tbr_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
                     +" INNER JOIN tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
                     +" INNER JOIN tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  ) "
                     +" INNER JOIN tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
                     +" WHERE a.co_emp=" + strCodEmp + " AND a.co_loc=" + strCodLoc + " AND a.co_tipdoc=" + strCodTipDoc + " AND a.co_doc=" + strCodDoc + " AND a.co_reg=" + intCodReg + "  AND a4.st_reg='A' ";

            System.out.println("ZafCom51.mostrarGuiasRemision: \n strSql01" + strSql01 + " \n strSql02:" + strSql02);

            ZafCom51_02 obj = new ZafCom51_02(objParSis, strSql01, strSql02, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc), intCodReg);
            this.getParent().add(obj, JLayeredPane.DEFAULT_LAYER);
            obj.show();

        } 
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);  }
    }
    
    
    
    
    
}

