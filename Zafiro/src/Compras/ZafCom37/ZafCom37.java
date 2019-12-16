/*
 * ZafCon37.java
 *
 *  Created on 16 de Agosto de 2010, 10:25 PM
 */
package Compras.ZafCom37;
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
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafPerUsr.ZafPerUsr;
import java.awt.Color;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom37 extends javax.swing.JInternalFrame
{
    //Constantes del JTable: TblDat
    //Columnas: Estáticas
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_EMP=1;
    private static final int INT_TBL_DAT_COD_REG=2;
    private static final int INT_TBL_DAT_COD_ITM_MAE=3;
    private static final int INT_TBL_DAT_COD_ITM=4;
    private static final int INT_TBL_DAT_COD_ALT_ITM=5;
    private static final int INT_TBL_DAT_COD_ALT_DOS=6;
    private static final int INT_TBL_DAT_NOM_ITM=7;
    private static final int INT_TBL_DAT_DES_COR_UNI_MED=8;
    private static final int INT_TBL_DAT_DES_LAR_UNI_MED=9;
    private static final int INT_TBL_DAT_UBI_ITM=10;
    
    //Columnas Dinámicas
    private static final int INT_TBL_DIN_STK_COR=0;
    private static final int INT_TBL_DIN_BUE_EST=1;
    private static final int INT_TBL_DIN_MAL_EST=2;
    private static final int INT_TBL_DIN_STK_SOB=3;
    private static final int INT_TBL_DIN_ING_BOD=4;
    private static final int INT_TBL_DIN_BUT_ING_BOD=5;
    private static final int INT_TBL_DIN_ING_DES=6;
    private static final int INT_TBL_DIN_BUT_ING_DES=7;
    private static final int INT_TBL_DIN_EGR_BOD=8;
    private static final int INT_TBL_DIN_BUT_EGR_BOD=9;
    private static final int INT_TBL_DIN_EGR_DES=10;
    private static final int INT_TBL_DIN_BUT_EGR_DES=11;    
    private static final int INT_TBL_DIN_DIF_STK=12;    
    private static final int INT_TBL_DIN_DIF_CNF=13;    
    private static final int INT_TBL_DIN_USR_CON=14;    
    private static final int INT_TBL_DIN_FEC_CON=15;    
    private static final int INT_TBL_DIN_COD_REG=16;    

    //ArrayList
    private ArrayList arlReg, arlDat;
    static final int INT_ARL_COD_EMP=0;
    static final int INT_ARL_COD_REG=1;
    static final int INT_ARL_COD_ITM_MAE=2;
    static final int INT_ARL_COD_ITM=3;
    static final int INT_ARL_COD_ALT_ITM=4;
    static final int INT_ARL_COD_LET_ITM=5;
    static final int INT_ARL_COD_USR_CON=6;
    static final int INT_ARL_TXT_USR_CON=7;
    static final int INT_ARL_NOM_USR_CON=8;
    static final int INT_ARL_FEC_SOL_CON=9;
    static final int INT_ARL_FEC_REA_CON=10;
    static final int INT_ARL_STK_SIS=11;
    static final int INT_ARL_CAN_ING_BOD=12;
    static final int INT_ARL_CAN_ING_DES=13;
    static final int INT_ARL_CAN_EGR_BOD=14;
    static final int INT_ARL_CAN_EGR_DES=15;
    static final int INT_ARL_CAN_CON_BUE_EST=16; 
    static final int INT_ARL_CAN_CON_MAL_EST=17;
    static final int INT_ARL_CAN_SOB=18;
    static final int INT_ARL_DIF_STK=19;
    static final int INT_ARL_DIF_CNF=20;
    static final int INT_ARL_OBS_CON=21;
    static final int INT_ARL_EST=22;

    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                             //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLblCon;              //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenButIngBod, objTblCelRenButIngDes,  objTblCelRenButEgrBod, objTblCelRenButEgrDes;
    private ZafTblCelEdiButGen objTblCelEdiButIngBod, objTblCelEdiButIngDes,  objTblCelEdiButEgrBod, objTblCelEdiButEgrDes;
    private ZafMouMotAda objMouMotAda;                       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                       //PopupMenu: Establecer PeopuMená en JTable.
    private ZafColNumerada objColNum;
    private ZafTblBus objTblBus;
    private ZafDocLis objDocLis;
    private ZafThreadGUI objThrGUI;
    private ZafSelFec objFecReaCon;
    private ZafPerUsr objPerUsr;
    private ZafTblOrd objTblOrd;                             //JTable de ordenamiento.
    private ZafVenCon vcoBod;                                //Ventana de consulta "Bodega".
    private ZafVenCon vcoItm;                                //Ventana de consulta "Item".
    private String strCodItmMae, strCodAlt, strCodLetItm, strNomItm;       //Contenido del campo al obtener el foco.
    private String strCodBod, strNomBod;                     //Contenido del campo al obtener el foco.
    private String strSQL, strAux, strFiltro, strFecReaCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                  //true: Continua la ejecución del hilo.
    private boolean blnHayCam;                               //Determina si hay cambios en el formulario.
    private int intNumColEst;                                //Numero de columnas estaticas
    private int intNumColEstCon, intNumColFinCon;
    private int intNumColAdiCon;                             //Numero de columnas Adicionales en el Jtable. Se refiere a una columna por Conteo.(cantidad de conteos que tiene el ítem.)
    private int intNumColDinCon;                             //Numero de columnas dinamicas dentro de cada conteo.
    
    private ZafCom37_01 objCom37_01;
    
    /**
     * Crea una nueva instancia de la clase ZafCom37.
     */
    public ZafCom37(ZafParSis obj)
    {
        try 
        {
            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            arlDat = new ArrayList();
            objPerUsr = new ZafPerUsr(objParSis);
        } 
        catch (CloneNotSupportedException e) {  this.setTitle(this.getTitle() + " [ERROR]"); }
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try 
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Título de la ventana.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.2");
            lblTit.setText(strAux);
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            
            //Configurar ZafSelFec.
            objFecReaCon=new ZafSelFec();
            objFecReaCon.setCheckBoxVisible(true);
            objFecReaCon.setCheckBoxChecked(false);
            objFecReaCon.setBounds(4, 4, 580, 74);
            objFecReaCon.setTitulo("Fecha de realización del conteo");
            panFil.add(objFecReaCon);

            //Configurar los JTables.
            configurarTblDat();
            
            configurarVenConBod();
            configurarVenConItm();

            //Permisos de Usuarios
            if(objParSis.getCodigoMenu()==2590)
            {
                if(!objPerUsr.isOpcionEnabled(2591))
                    butCon.setEnabled(false);
                if(!objPerUsr.isOpcionEnabled(2592))
                    butCer.setEnabled(false);
            }

            txtCodItm.setVisible(false);
        }
        catch(Exception e) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);    }
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
            vecCab=new Vector(10);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_DES_LAR_UNI_MED,"Uni.Med.Des.Lar.");
            vecCab.add(INT_TBL_DAT_UBI_ITM,"Ubicación");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_UNI_MED).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            //tcmAux.getColumn(INT_TBL_DAT_COD_REG).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_UNI_MED).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_UNI_MED, tblDat);
            
            objTblCelRenLblCon = new ZafTblCelRenLbl();
            objTblCelRenLblCon.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLblCon);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setCellRenderer(objTblCelRenLblCon);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Libero los objetos auxiliares.
            tcmAux=null;      

            intNumColEst=objTblMod.getColumnCount();
            
        }
        catch(Exception e)  {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
            
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener
    {
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e)
        {
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                }
            }
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }     
    
    /**
     * Esta función configura la "Ventana de consulta".
     * Muestra Listado de Bodegas.
     */
    private boolean configurarVenConBod() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Bod.");
            arlAli.add("Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }
            else
            {
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1 INNER JOIN tbr_bodLocPrgUsr AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }            
            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } 
        catch (Exception e) { blnRes = false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBod(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBod.setCampoBusqueda(1);
                    vcoBod.show();
                    if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) 
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) 
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } 
                    else
                    {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } 
                        else 
                        {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripción larga".
                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) 
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } 
                    else 
                    {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } 
                        else 
                        {
                            txtNomBod.setText(strNomBod);
                        }
                    }
                    break;
            }
        } 
        catch (Exception e) { blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" .
     * Muestra el Listado de Items.
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
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Alt.2.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            arlAli.add("Uni.Med.Des.Lar.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("40");
            arlAncCol.add("350");
            arlAncCol.add("50");
            arlAncCol.add("80");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND (   (UPPER(a1.tx_codAlt) LIKE '%I') OR  (UPPER(a1.tx_codAlt) LIKE '%S')  ) AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
            strSQL+=" ORDER BY a1.tx_codAlt";
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER); //tx_codAlt2
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER); 
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
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
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Código Alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodLetItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else{
                            txtCodLetItm.setText(strCodLetItm);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
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
    
    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        optTodItm = new javax.swing.JRadioButton();
        optItmSel = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtCodLetItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblItmDes = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblItmHas = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        chkMosItmSinCon = new javax.swing.JCheckBox();
        panRep = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        lblTit.setPreferredSize(new java.awt.Dimension(39, 11));
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblBod.setText("Bodega:");
        lblBod.setToolTipText("Bodega en la que se debe hacer el conteo");
        panFil.add(lblBod);
        lblBod.setBounds(14, 90, 70, 20);

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
        panFil.add(txtCodBod);
        txtCodBod.setBounds(90, 90, 60, 20);
        txtCodBod.getAccessibleContext().setAccessibleName("");
        txtCodBod.getAccessibleContext().setAccessibleDescription("");

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
        panFil.add(txtNomBod);
        txtNomBod.setBounds(150, 90, 270, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panFil.add(butBod);
        butBod.setBounds(420, 90, 20, 20);

        optTodItm.setSelected(true);
        optTodItm.setText("Todos los items");
        optTodItm.setPreferredSize(new java.awt.Dimension(93, 16));
        optTodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodItmActionPerformed(evt);
            }
        });
        panFil.add(optTodItm);
        optTodItm.setBounds(10, 120, 560, 14);

        optItmSel.setText("Sólo los items que cumplan el criterio seleccionado");
        optItmSel.setPreferredSize(new java.awt.Dimension(93, 16));
        optItmSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optItmSelActionPerformed(evt);
            }
        });
        panFil.add(optItmSel);
        optItmSel.setBounds(10, 140, 560, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(40, 170, 40, 20);

        txtCodItm.setEditable(false);
        txtCodItm.setEnabled(false);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(80, 170, 20, 20);

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
        txtCodAltItm.setBounds(100, 170, 80, 20);

        txtCodLetItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusLost(evt);
            }
        });
        txtCodLetItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLetItmActionPerformed(evt);
            }
        });
        panFil.add(txtCodLetItm);
        txtCodLetItm.setBounds(180, 170, 63, 20);

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
        txtNomItm.setBounds(243, 170, 369, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(611, 170, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmDesHas.setLayout(null);

        lblItmDes.setText("Desde:");
        panItmDesHas.add(lblItmDes);
        lblItmDes.setBounds(10, 20, 60, 14);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(60, 20, 90, 20);

        lblItmHas.setText("Hasta:");
        panItmDesHas.add(lblItmHas);
        lblItmHas.setBounds(160, 20, 50, 14);

        txtCodAltItmHas.setVerifyInputWhenFocusTarget(false);
        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(210, 20, 90, 20);

        panFil.add(panItmDesHas);
        panItmDesHas.setBounds(40, 210, 310, 50);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmTer.setLayout(null);

        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(20, 20, 90, 14);

        txtCodAltItmTer.setVerifyInputWhenFocusTarget(false);
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(110, 20, 100, 20);

        panFil.add(panItmTer);
        panItmTer.setBounds(350, 210, 220, 50);

        chkMosItmSinCon.setText("Mostrar items que no han sido contados");
        panFil.add(chkMosItmSinCon);
        chkMosItmSinCon.setBounds(10, 280, 530, 16);

        tabFrm.addTab("Filtro", panFil);
        panFil.getAccessibleContext().setAccessibleParent(tabFrm);

        panRep.setLayout(new java.awt.BorderLayout());

        panDat.setLayout(new java.awt.BorderLayout());

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

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panRep.add(panDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(639, 45));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(639, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setMnemonic('r');
        butCer.setText("Cerrar");
        butCer.setToolTipText("");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 15));
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

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
    mostrarVenConItm(0);
    if(txtNomItm.getText().length()>0)
    {
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
    }
}//GEN-LAST:event_butItmActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
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
}//GEN-LAST:event_butConActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
    exitForm(null);//GEN-LAST:event_butCerActionPerformed
}                                      

private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
    mostrarVenConBod(0);
    objTblMod.removeAllRows();
}//GEN-LAST:event_butBodActionPerformed

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
    strCodBod = txtCodBod.getText();
    txtCodBod.selectAll();

}//GEN-LAST:event_txtCodBodFocusGained

private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) 
    {
        if (txtCodBod.getText().equals("")) 
        {
            txtCodBod.setText("");
            txtNomBod.setText("");
        }
        else   {
            mostrarVenConBod(1);
        }
        objTblMod.removeAllRows();
    } 
    else  {
        txtCodBod.setText(strCodBod);
    }
}//GEN-LAST:event_txtCodBodFocusLost

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
    strNomBod = txtNomBod.getText();
    txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) 
    {
        if (txtNomBod.getText().equals(""))
        {
            txtCodBod.setText("");
            txtNomBod.setText("");
        }
        else    {
            mostrarVenConBod(2);
        }
        objTblMod.removeAllRows();
    } 
    else    {
        txtNomBod.setText(strNomBod);
    }
}//GEN-LAST:event_txtNomBodFocusLost

private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
    txtCodAltItm.transferFocus();
}//GEN-LAST:event_txtCodAltItmActionPerformed

private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
    strCodAlt=txtCodAltItm.getText();
    txtCodAltItm.selectAll();
}//GEN-LAST:event_txtCodAltItmFocusGained

private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt)) 
    {
        if (txtCodAltItm.getText().equals(""))
        {
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodLetItm.setText("");
            txtNomItm.setText("");
        } 
        else   {
            mostrarVenConItm(1);
        }
    }
    else {
        txtCodAltItm.setText(strCodAlt);
    }

    if (txtCodAltItm.getText().length() > 0) 
    {
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
    }
}//GEN-LAST:event_txtCodAltItmFocusLost

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
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodLetItm.setText("");
            txtNomItm.setText("");
        }
        else   {
            mostrarVenConItm(3);
        }
    } 
    else {
        txtNomItm.setText(strNomItm);
    }

    if (txtNomItm.getText().length() > 0) 
    {
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
    }
}//GEN-LAST:event_txtNomItmFocusLost

private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
    txtCodAltItmDes.selectAll();
}//GEN-LAST:event_txtCodAltItmDesFocusGained

private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
    if (txtCodAltItmDes.getText().length() > 0) 
    {
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtCodLetItm.setText("");
        txtNomItm.setText("");
        txtCodAltItmTer.setText("");

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
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
    }
}//GEN-LAST:event_txtCodAltItmHasFocusLost

private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
    txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
    if (txtCodAltItmTer.getText().length() > 0) 
    {
        optItmSel.setSelected(true);
        optTodItm.setSelected(false);
        txtCodAltItmHas.setText("");
        txtCodAltItmDes.setText("");
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtCodLetItm.setText("");
        txtNomItm.setText("");
    }
}//GEN-LAST:event_txtCodAltItmTerFocusLost

private void optTodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodItmActionPerformed
    if (optTodItm.isSelected()) 
    {
        optItmSel.setSelected(false);
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtCodLetItm.setText("");
        txtNomItm.setText("");
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
        objTblMod.removeAllRows();
    }
    else  {
        optItmSel.setSelected(true);
    }
}//GEN-LAST:event_optTodItmActionPerformed

private void optItmSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optItmSelActionPerformed
    if(optItmSel.isSelected())
        optTodItm.setSelected(false);
    else
        optTodItm.setSelected(true);
}//GEN-LAST:event_optItmSelActionPerformed

    private void txtCodLetItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusGained
        strCodLetItm=txtCodLetItm.getText();
        txtCodLetItm.selectAll();
    }//GEN-LAST:event_txtCodLetItmFocusGained

    private void txtCodLetItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLetItm.getText().equalsIgnoreCase(strCodLetItm)) 
        {
            if (txtCodLetItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else {
                mostrarVenConItm(2);
            }
        }
        else {
            txtCodLetItm.setText(strCodLetItm);
        }

        if (txtCodLetItm.getText().length() > 0) 
        {
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
        
        
    }//GEN-LAST:event_txtCodLetItmFocusLost

    private void txtCodLetItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLetItmActionPerformed
        txtCodLetItm.transferFocus();
    }//GEN-LAST:event_txtCodLetItmActionPerformed

    /**
     * Cerrar la aplicacián.
     */
    private void exitForm()
    {
        dispose();
    }   
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkMosItmSinCon;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmDes;
    private javax.swing.JLabel lblItmHas;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optItmSel;
    private javax.swing.JRadioButton optTodItm;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLetItm;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podrá
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta función permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private void limpiarFrm() 
    {
        txtCodBod.setText("");
        txtNomBod.setText("");
        optTodItm.setSelected(true);
        optItmSel.setSelected(false);
        txtCodItm.setText("");
        txtCodAltItm.setText("");
        txtNomItm.setText("");
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
        txtCodAltItmTer.setText("");
        objTblMod.removeAllRows();
    }

    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtCodItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItm.getDocument().addDocumentListener(objDocLis);
        txtNomItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmDes.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmHas.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmTer.getDocument().addDocumentListener(objDocLis);
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código de registro";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg="Unidad de Medida";
                    break;
                case INT_TBL_DAT_DES_LAR_UNI_MED:
                    strMsg="Unidad de Medida Descripción Larga";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg="Ubicación del ítem";
                    break;    
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sólo se apreciaráa los cambios cuando ha terminado todo el
     * proceso.
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
            if (tblDat.getRowCount() > 0) 
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }
   
    private boolean cargarDetReg()
    {
        boolean blnRes=false;
        try
        {
            if(isCamVal()) {
                if(eliminaColumnasAdicionadas()) {
                    if(obtenerFiltro())   {
                        if(obtenerColumnasAdicionar())   {
                            if(agregarColTblDat())  {                            
                                if(cargarTbmConInv()) {
                                    if(cargarConReaUsuFec()) {
                                        blnRes=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){  blnRes=false;  }
        return blnRes;
    }

     /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        //Validar el "Tipo de documento".
        if (txtCodBod.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaColumnasAdicionadas() 
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--)
            {
                objTblMod.removeColumn(tblDat, i);                
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);   blnRes=false;      }
        return blnRes;        
    }

    private boolean obtenerFiltro() 
    {
        boolean blnRes = true;
        try 
        {
            strFecReaCon="";
            if(objFecReaCon.isCheckBoxChecked())
            {
                switch (objFecReaCon.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strFecReaCon+=" AND CAST(a3.fe_reaCon AS date) BETWEEN '" + objUti.formatearFecha(objFecReaCon.getFechaDesde(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strFecReaCon+=" AND CAST(a3.fe_reaCon AS date)<='" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strFecReaCon+=" AND CAST(a3.fe_reaCon AS date)>='" + objUti.formatearFecha(objFecReaCon.getFechaDesde(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
            }

            strFiltro = "";
            if(!  txtCodBod.getText().toString().equals(""))
                strFiltro+=" AND a2.co_bod=" + txtCodBod.getText()  + "";

            if(!  txtCodItm.getText().toString().equals(""))
                strFiltro+=" AND a1.co_itm=" + txtCodItm.getText()  + "";

            if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                strFiltro+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

            if (txtCodAltItmTer.getText().length()>0){
                strFiltro+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
            }
        }
        catch (Exception e){    objUti.mostrarMsgErr_F1(this, e);  blnRes=false;  }
        return blnRes;
    }
    
    /**
     * Función que obtiene el número de columnas que se van a adicionar al
     * modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean obtenerColumnasAdicionar() 
    {
        boolean blnRes = true;
        intNumColAdiCon = 0;
        try 
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                stm=con.createStatement();               

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c1.co_itmMae, b1.tx_desCor, b1.tx_desLar ";
                strSQL+=" FROM ((tbm_inv AS a1 INNER JOIN tbm_var AS b1 ON a1.co_uni=b1.co_reg) ";
                strSQL+=" INNER JOIN tbm_equInv AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm) ";
                strSQL+=" INNER JOIN tbm_invBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm ";
                strSQL+=" INNER JOIN tbm_conInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm AND a2.co_bod=a3.co_bod ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="" + strFecReaCon;
                strSQL+="" + strFiltro;
                strSQL+=" AND a1.tx_codAlt NOT LIKE '%L'";
                strSQL+=" AND a3.st_conrea='S'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c1.co_itmMae, b1.tx_desCor, b1.tx_desLar ";
                strSQL+=" ORDER BY a1.tx_codAlt";
                rst=stm.executeQuery(strSQL);
                strCodItmMae="";
                for(int i=0; rst.next(); i++)
                {
                    if(i==0)
                        strCodItmMae+=" " + rst.getString("co_itmMae");
                    else
                        strCodItmMae+=", " + rst.getString("co_itmMae");                       
                }                
                rst.close();
                rst=null;

                strSQL="";
                strSQL+=" SELECT MAX(ne_numVecCon) AS ne_numColAdi FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a2.co_itmMae, COUNT(a1.co_reg) AS ne_numVecCon";
                strSQL+=" 	FROM tbm_conInv AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.co_bod=" + txtCodBod.getText() + "";
                strSQL+=" 	AND a1.st_conRea='S'";
                strSQL+="       AND co_itmMae IN(" + strCodItmMae + ")";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_itm, a2.co_itmMae";
                strSQL+=" 	ORDER BY ne_numVecCon";
                strSQL+=" ) AS a1";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumColAdiCon=rst.getInt("ne_numColAdi");
                rst.close();                
                rst=null;
                
                stm.close();
                stm=null;                   
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  blnRes=false; }
        catch (Exception e){    objUti.mostrarMsgErr_F1(this, e);  blnRes=false;  }
        return blnRes;
    }

    /**
     * Función que adiciona las columnas al modelo en tiempo de ejecución.
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat() 
    {
        boolean blnRes = true;
        javax.swing.table.TableColumn tbc;
        String strTitTbl = "";
        try 
        {
            vecAux = new Vector();

            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 3);
            
            ZafTblHeaColGrp objTblHeaColGrpCon = null;
            ZafTblHeaColGrp objTblHeaColSubGrp = null;

            intNumColEstCon = intNumColEst;
            intNumColDinCon=17;
            int p = 0, k = 0;

            java.awt.Color colFonColPar, colFonColImp;
            colFonColPar = new java.awt.Color(255, 221, 187); //Color Naranja.
            colFonColImp = new java.awt.Color(228, 228, 203); //Color Verde Agua.

            //<editor-fold defaultstate="collapsed" desc="/* Botones de Ingresos/Egresos */">
            //Botón Ingreso. "IB"
            objTblCelEdiButIngBod = new ZafTblCelEdiButGen();
            objTblCelEdiButIngBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                int intCodReg = -1; 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    //Se le suman 11 columnas a la columna seleccionada porque al sumarle me da la columna donde esta el codigo del registro.
                    intCodReg = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn() + 11 ) ) == null ? "0" : objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn() + 11 )  ).toString());
                    mostrarDocumentosPendientesConfirmarIngresosBodega(intCodReg);
                }
            });
            
            //Botón Ingreso. "ID"
            objTblCelEdiButIngDes = new ZafTblCelEdiButGen();
            objTblCelEdiButIngDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {
                int intCodReg = -1; 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    //Se le suman 9 columnas a la columna seleccionada porque al sumarle me da la columna donde esta el codigo del registro.
                    intCodReg = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()  + 9 ) ) == null ? "0" : objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()  + 9 )  ).toString());
                    mostrarDocumentosPendientesConfirmarIngresosDespacho(intCodReg);
                }
            });
            
            //Botón Egreso. "EB"
            objTblCelEdiButEgrBod = new ZafTblCelEdiButGen();
            objTblCelEdiButEgrBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intCodReg = -1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    //Se le suman 7 columnas a la columna seleccionada porque al sumarle me da la columna donde esta el codigo del registro.
                    intCodReg = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn() + 7)) == null ? "0" : objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()  + 7)).toString());
                    mostrarDocumentosPendientesConfirmarEgresosBodega(intCodReg);
                }
            });
            
            //Botón Egreso. "ED"
            objTblCelEdiButEgrDes = new ZafTblCelEdiButGen();
            objTblCelEdiButEgrDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intCodReg = -1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    //Se le suman 5 columnas a la columna seleccionada porque al sumarle me da la columna donde esta el codigo del registro.
                    intCodReg = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn() + 5)) == null ? "0" : objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()  + 5)).toString());
                    mostrarDocumentosPendientesConfirmarEgresosDespacho(intCodReg);
                }
            });
            //</editor-fold>
    
            for (int i = 0; i < (intNumColAdiCon*intNumColDinCon); i++)   
            {
                strTitTbl = (p==INT_TBL_DIN_STK_COR ? "Stk.Cor." : (p==INT_TBL_DIN_BUE_EST ? "Bue.Est." : (p==INT_TBL_DIN_MAL_EST ? "Mal.Est." : (p==INT_TBL_DIN_STK_SOB ? "Sob." 
                          : (p==INT_TBL_DIN_ING_BOD ? "Can.IB" : (p==INT_TBL_DIN_BUT_ING_BOD ? "IB" : (p==INT_TBL_DIN_ING_DES ? "Can.ID" : (p==INT_TBL_DIN_BUT_ING_DES ? "ID"
                          : (p==INT_TBL_DIN_EGR_BOD ? "Can.EB" : (p==INT_TBL_DIN_BUT_EGR_BOD ? "EB" : (p==INT_TBL_DIN_EGR_DES ? "Can.ED" : (p==INT_TBL_DIN_BUT_EGR_DES ? "ED"
                          : (p==INT_TBL_DIN_DIF_STK ? "Dif.Stk." : (p==INT_TBL_DIN_DIF_CNF ? "Dif.Cnf." : (p==INT_TBL_DIN_USR_CON ? "Usu.Con." : (p==INT_TBL_DIN_FEC_CON ? "Fec.Con." : (p==INT_TBL_DIN_COD_REG ? "Cód.Reg." : "")))))))))))))))));
                
                //Stk.Cor.; Can.Con.Bue.Est.; Can.Con.Mal.Est.; Sob.; Can.IB; "Can.ID" ; Can.EB; "Can.ED" ; Dif.Stk.; Dif.Cnf.
                if ((p==INT_TBL_DIN_STK_COR) || (p==INT_TBL_DIN_BUE_EST) || (p==INT_TBL_DIN_MAL_EST) || (p==INT_TBL_DIN_STK_SOB) 
                 || (p==INT_TBL_DIN_ING_BOD) || (p==INT_TBL_DIN_ING_DES) || (p==INT_TBL_DIN_EGR_BOD) || (p==INT_TBL_DIN_EGR_DES) 
                 || (p==INT_TBL_DIN_DIF_STK) || (p==INT_TBL_DIN_DIF_CNF))  
                {
                    //Configurar JTable: Establecer Formato Número.
                    objTblCelRenLblCon = new ZafTblCelRenLbl();
                    objTblCelRenLblCon.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                    objTblCelRenLblCon.setTipoFormato(objTblCelRenLblCon.INT_FOR_NUM);
                    if (p==INT_TBL_DIN_BUE_EST || (p==INT_TBL_DIN_MAL_EST)) //Can.Con.Bue.Est.; Can.Con.Mal.Est.
                        objTblCelRenLblCon.setFormatoNumerico(objParSis.getFormatoNumero(), true, true); //Muestra 0
                    else 
                        objTblCelRenLblCon.setFormatoNumerico(objParSis.getFormatoNumero(), false, true); //No Muestra 0
                    tbc = new javax.swing.table.TableColumn(intNumColEst + i);
                    tbc.setHeaderValue("" + strTitTbl + "");
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    if((p==INT_TBL_DIN_BUE_EST) )       { tbc.setPreferredWidth(50);  }  //Can.Con.Bue.Est.
                    else if ( p==INT_TBL_DIN_MAL_EST )               { tbc.setWidth(0);   tbc.setResizable(false);  tbc.setMaxWidth(0);  tbc.setMinWidth(0);  tbc.setPreferredWidth(0);   }  //Can.Con.Mal.Est.
                    else if ((p==INT_TBL_DIN_DIF_STK) || (p==INT_TBL_DIN_DIF_CNF))   { tbc.setPreferredWidth(65);  }  //Dif.Stk. ; Dif.Cnf.
                    else                           { tbc.setPreferredWidth(55);  }
                    
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblCon);
                }
                //Usu.Con.; Fec.Con.; Cód.Reg.
                else if((p==INT_TBL_DIN_USR_CON) || (p==INT_TBL_DIN_FEC_CON) || (p==INT_TBL_DIN_COD_REG) )
                {
                    //Configurar JTable: Establecer Formato Texto.
                    objTblCelRenLblCon = new ZafTblCelRenLbl();
                    objTblCelRenLblCon.setHorizontalAlignment(javax.swing.JLabel.LEFT);
                    objTblCelRenLblCon.setTipoFormato(objTblCelRenLblCon.INT_FOR_GEN);
                    tbc = new javax.swing.table.TableColumn(intNumColEst + i);
                    tbc.setHeaderValue("" + strTitTbl + "");
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    if(p==INT_TBL_DIN_FEC_CON)  tbc.setPreferredWidth(120); //Fec.Con. 
                    else tbc.setPreferredWidth(55);
          
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblCon);
                    
                    if((p==INT_TBL_DIN_COD_REG) ) //Cód.Reg; 
                    {
                        tbc.setWidth(0);
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);
                    }
                }
                //"I"; "E"
                else
                {                    
                    //Configurar JTable: Establecer Formato Botón.
                    tbc=new javax.swing.table.TableColumn(intNumColEst+i);
                    tbc.setHeaderValue("" + strTitTbl + "");
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(20);
                    //Botón Ingreso.
                    if( (p==INT_TBL_DIN_BUT_ING_BOD)  ) //"IB"  
                    {  
                        objTblCelRenButIngBod=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenButIngBod);
                        tbc.setCellEditor(objTblCelEdiButIngBod);
                    }
                    //Botón Ingreso.
                    if( (p==INT_TBL_DIN_BUT_ING_DES) ) //"ID"  
                    {  
                        objTblCelRenButIngDes=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenButIngDes);
                        tbc.setCellEditor(objTblCelEdiButIngDes);
                    }
                    //Botón Egreso.
                    if ( (p==INT_TBL_DIN_BUT_EGR_BOD) ) //"EB"
                    {
                        objTblCelRenButEgrBod=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenButEgrBod);
                        tbc.setCellEditor(objTblCelEdiButEgrBod);
                    }
                    //Botón Egreso.
                    if ( (p==INT_TBL_DIN_BUT_EGR_DES) ) //"ED"
                    {
                        objTblCelRenButEgrDes=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenButEgrDes);
                        tbc.setCellEditor(objTblCelEdiButEgrDes);
                    }
                    vecAux.add("" + (intNumColEst+i));
                }
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                //Agrupar SubGrupos
                if ( p==0 )
                {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("Cantidad");
                    objTblHeaColSubGrp.setHeight(16);
                }
                else if ( p==INT_TBL_DIN_ING_BOD )
                {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("Confirmación Ingresos");
                    objTblHeaColSubGrp.setHeight(16);
                }
                else if ( p==INT_TBL_DIN_EGR_BOD )
                {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("Confirmación Egresos");
                    objTblHeaColSubGrp.setHeight(16);
                }
                else if ( p==INT_TBL_DIN_DIF_STK )
                {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("Diferencias");
                    objTblHeaColSubGrp.setHeight(16);
                }
                else if (p==INT_TBL_DIN_USR_CON) 
                {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("");
                    objTblHeaColSubGrp.setHeight(16);
                }

                //Agrupar las columnas por conteo.
                if (p==0)
                {
                    k++;
                    objTblHeaColGrpCon=new ZafTblHeaColGrp("Conteo " + (k));
                    objTblHeaColGrpCon.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpCon);
                    objTblHeaColGrpCon.add(objTblHeaColSubGrp);
                }
                else if(p==INT_TBL_DIN_ING_BOD)
                {
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpCon);
                    objTblHeaColGrpCon.add(objTblHeaColSubGrp);
                }
                else if(p==INT_TBL_DIN_EGR_BOD)
                {
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpCon);
                    objTblHeaColGrpCon.add(objTblHeaColSubGrp);
                }
                else if(p==INT_TBL_DIN_DIF_STK)
                {
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpCon);
                    objTblHeaColGrpCon.add(objTblHeaColSubGrp);
                }
                else if(p==INT_TBL_DIN_USR_CON)
                {
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpCon);
                    objTblHeaColGrpCon.add(objTblHeaColSubGrp);
                }

                objTblHeaColSubGrp.add(tblDat.getColumnModel().getColumn(intNumColEst + i));
                 
                //Establecer color por Conteo.
                if(objTblCelRenLblCon!=null) 
                {
                    if((k % 2)==0)  objTblCelRenLblCon.setBackground(colFonColPar);
                    else objTblCelRenLblCon.setBackground(colFonColImp);
                }
                 
                //Cód.Reg.
                if(p==16)  
                    p=0; 
                else      
                    p++;

            }
            tbc=null;
            intNumColFinCon=objTblMod.getColumnCount();
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
        }
        catch(Exception e){   objUti.mostrarMsgErr_F1(this, e);   blnRes=false;  }
        return blnRes;
    }
  
    private boolean cargarTbmConInv() 
    {
        boolean blnRes = true;
        int i;
        try 
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2,  b1.tx_ubi, b1.tx_nomItm, b1.co_itmMae, b1.tx_desCor, b1.tx_desLar ";
                strSQL+="      , CASE WHEN b1.co_reg IS NULL THEN SUM(b2.nd_stkAct) ELSE 0 END AS nd_stkAct";
                strSQL+=" FROM(";
                strSQL+="      SELECT a1.co_emp, MAX(a3.co_reg) AS co_reg, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a5.tx_ubi, a1.tx_nomItm, c1.co_itmMae, b1.tx_desCor, b1.tx_desLar, a2.co_bod ";
                strSQL+="      FROM ((tbm_inv AS a1 INNER JOIN tbm_var AS b1 ON a1.co_uni=b1.co_reg) ";
                strSQL+="      INNER JOIN tbm_equInv AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm) ";
                strSQL+="      INNER JOIN tbm_invBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm ";
                if(chkMosItmSinCon.isSelected())
                    strSQL+="  LEFT OUTER JOIN tbm_conInv AS a3";
                else 
                    strSQL+="  INNER JOIN tbm_conInv AS a3";
                strSQL+="      ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm AND a2.co_bod=a3.co_bod";
                strSQL+="      " + strFecReaCon;
                strSQL+="      AND a3.st_conrea='S'";
                
                //Se agrega ubicación de inventario
                strSQL+="      LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg) ";
                strSQL+="      LEFT OUTER JOIN  ";
                strSQL+="      (  ";
                strSQL+="        SELECT  DISTINCT b1.co_itmMae, b2.tx_ubi  ";
                strSQL+="        FROM tbm_equInv AS b1  ";
                strSQL+="        INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)  ";
                strSQL+="        INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)  ";
                strSQL+="        WHERE b3.co_empgrp="+objParSis.getCodigoEmpresaGrupo()+" AND b3.co_bodgrp = "+txtCodBod.getText();
                strSQL+="      ) AS a5 ON (c1.co_itmMae=a5.co_itmMae) ";
                strSQL+="      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="      AND a1.tx_codAlt NOT LIKE '%L'";
                strSQL+="      " + strFiltro;
                strSQL+="      GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a5.tx_ubi, a1.tx_nomItm, c1.co_itmMae, b1.tx_desCor, b1.tx_desLar, a2.co_bod";
                strSQL+="      ORDER BY a1.tx_codAlt";
                strSQL+=" ) AS b1";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT x.*, y.co_empGrp, y.co_bodGrp FROM(";
                strSQL+=" 	     SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                strSQL+=" 	     FROM tbm_invBod AS a1 ";
                strSQL+=" 	     INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+=" 	) AS x";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 	     SELECT co_emp, co_bod, co_empGrp, co_bodGrp";
                strSQL+=" 	     FROM tbr_bodEmpBodGrp";
                strSQL+=" 	     WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	     AND co_bodGrp=" + txtCodBod.getText() + "";
                strSQL+=" 	     ORDER BY co_emp";
                strSQL+=" 	) AS y";
                strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+=" 	WHERE x.nd_stkAct<>0";
                strSQL+=" 	ORDER BY x.co_itmMae";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_empGrp AND b1.co_bod=b2.co_bodGrp AND b1.co_itmMae=b2.co_itmMae";
                strSQL+=" GROUP BY b1.co_emp, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_ubi, b1.tx_nomItm, b1.co_itmMae, b1.tx_desCor, b1.tx_desLar ";
                strSQL+=" ORDER BY b1.tx_codAlt";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;
                while(rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,                 "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,             "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_REG,             "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         "" + rst.getObject("co_itmMae")==null?"":rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,             "" + rst.getObject("co_itm")==null?"":rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         "" + rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,         "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,             "" + rst.getObject("tx_nomItm")==null?"":rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_UNI_MED,     "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_UBI_ITM,             "" + rst.getObject("tx_ubi")==null?"":rst.getString("tx_ubi"));
                        
                        //adicionar las columnas de vistos buenos
                        for(int j=intNumColEstCon; j<intNumColFinCon;j++)
                        {
                            if(j==intNumColEstCon)
                            {
                                vecReg.add(j,     "" + rst.getObject("nd_stkAct")==null?"0":rst.getString("nd_stkAct"));//nd_stkAct
                            }
                            else
                                vecReg.add(j,     null);
                        }
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
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e) {   blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    /**
     * Función que carga los conteos realizados por Usuario y Fecha de Conteo.
     * @return 
     */
    private boolean cargarConReaUsuFec() 
    {
        boolean blnRes = true;
        arlDat.clear();
        String strCodEmpTbl = "", strCodEmpArl = "";
        String strCodItmTbl = "", strCodItmArl = "";
        String strCodItmMaeTbl = "", strCodItmMaeArl = "";
        String strLinArl = "";
        String strCodRegTbl = "";
        try 
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                strAux="";
                if(!  txtCodBod.getText().toString().equals(""))
                    strAux+="   AND a1.co_bod=" + txtCodBod.getText()  + "";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";
                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a4.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a4.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                if (txtCodAltItmTer.getText().length()>0){
                    strAux+=getConSQLAdiCamTer("a4.tx_codAlt", txtCodAltItmTer.getText());
                }
                if(objFecReaCon.isCheckBoxChecked()){
                    strAux+=" AND CAST(a1.fe_reaCon AS date)<='" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                }

                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT x.*,  ((x.nd_canBueEst + x.nd_canMalEst)  -  x.nd_stkSis ) AS nd_difStk \n";
                strSQL+="     , ((x.nd_canBueEst + x.nd_canMalEst) - (x.nd_stkSis + (x.nd_canEgrBod+x.nd_canEgrDes) - (x.nd_canIngBod + x.nd_CanIngDes) ) ) AS nd_difCnf \n";
                strSQL+=" FROM \n";
                strSQL+=" ( \n";
                strSQL+="     SELECT a1.co_emp, a1.co_itm, a1.co_reg, a3.co_itmMae, a4.tx_codAlt, a4.tx_codAlt2, \n";           
                strSQL+="            a1.nd_stkSis,  a1.nd_stkcon as nd_canBueEst, a1.nd_canMalEst, a1.nd_cansob, \n";
                strSQL+="            a1.nd_canIngBod, CASE WHEN a1.nd_canTra IS NULL THEN 0 ELSE a1.nd_canTra END as nd_CanIngDes, \n";
                strSQL+="            a1.nd_canEgrBod, CASE WHEN a1.nd_canDesEntCli IS NULL THEN 0 ELSE a1.nd_canDesEntCli END as nd_canEgrDes, \n";
                strSQL+="            a1.co_usrResCon, usr.tx_usr, usr.tx_nom, a1.fe_solCon, a1.fe_reaCon,  a1.tx_obs1  \n";  
                strSQL+="     FROM (tbm_conInv AS a1 INNER JOIN tbm_usr AS usr ON a1.co_usrResCon=usr.co_usr)  \n";
                strSQL+="     INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm  \n";
                strSQL+="     INNER JOIN tbm_inv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm  \n";
                strSQL+="     WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="     AND st_conRea='S'"; 
                strSQL+="     " + strAux;
                strSQL+="     ORDER BY a1.fe_reaCon DESC \n";
                strSQL+=" ) as x \n";
                rst=stm.executeQuery(strSQL);
                while(rst.next())
                {
                    arlReg=new ArrayList();
                    arlReg.add(INT_ARL_COD_EMP,      "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    arlReg.add(INT_ARL_COD_REG,      "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                    arlReg.add(INT_ARL_COD_ITM_MAE,  "" + rst.getObject("co_itmMae")==null?"":rst.getString("co_itmMae"));
                    arlReg.add(INT_ARL_COD_ITM,      "" + rst.getObject("co_itm")==null?"":rst.getString("co_itm"));
                    arlReg.add(INT_ARL_COD_ALT_ITM,  "" + rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt"));
                    arlReg.add(INT_ARL_COD_LET_ITM,  "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2")); 
                    arlReg.add(INT_ARL_COD_USR_CON,  "" + rst.getObject("co_usrResCon")==null?"":rst.getString("co_usrResCon"));
                    arlReg.add(INT_ARL_TXT_USR_CON,  "" + rst.getObject("tx_usr")==null?"":rst.getString("tx_usr"));
                    arlReg.add(INT_ARL_NOM_USR_CON,  "" + rst.getObject("tx_nom")==null?"":rst.getString("tx_nom"));
                    arlReg.add(INT_ARL_FEC_SOL_CON,  "" + rst.getObject("fe_solCon")==null?"": rst.getString("fe_solCon"));
                    arlReg.add(INT_ARL_FEC_REA_CON,  "" + rst.getObject("fe_reaCon")==null?"": rst.getString("fe_reaCon"));
                    arlReg.add(INT_ARL_STK_SIS,      "" + rst.getObject("nd_stkSis")==null?"":rst.getString("nd_stkSis"));
                    arlReg.add(INT_ARL_CAN_ING_BOD,  "" + rst.getObject("nd_canIngBod")==null?"":rst.getString("nd_canIngBod"));
                    arlReg.add(INT_ARL_CAN_ING_DES,  "" + rst.getObject("nd_canIngDes")==null?"":rst.getString("nd_canIngDes"));
                    arlReg.add(INT_ARL_CAN_EGR_BOD,  "" + rst.getObject("nd_canEgrBod")==null?"":rst.getString("nd_canEgrBod"));
                    arlReg.add(INT_ARL_CAN_EGR_DES,  "" + rst.getObject("nd_canEgrDes")==null?"":rst.getString("nd_canEgrDes"));
                    
                    arlReg.add(INT_ARL_CAN_CON_BUE_EST,   "" + rst.getObject("nd_canBueEst")==null?"":rst.getString("nd_canBueEst"));
                    arlReg.add(INT_ARL_CAN_CON_MAL_EST,   "" + rst.getObject("nd_canMalEst")==null?"":rst.getString("nd_canMalEst")); 
                    
                    arlReg.add(INT_ARL_CAN_SOB,           "" + rst.getObject("nd_canSob")==null?"":rst.getString("nd_canSob"));
                    arlReg.add(INT_ARL_DIF_STK,           "" + rst.getObject("nd_difStk")==null?"":rst.getString("nd_difStk"));
                    arlReg.add(INT_ARL_DIF_CNF,           "" + rst.getObject("nd_difCnf")==null?"":rst.getString("nd_difCnf"));
                    
                    arlReg.add(INT_ARL_OBS_CON,           "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    arlReg.add(INT_ARL_EST,               "");
                    
                    arlDat.add(arlReg);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;

                int intPosCol=0;

                for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    strCodEmpTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                    strCodRegTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG).toString();
                    strCodItmMaeTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString();
                    strCodItmTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString();
                    
                    if( ! strCodRegTbl.equals(""))
                    {
                        intPosCol=0;

                        for(int j=0; j<arlDat.size(); j++)
                        {
                            strCodEmpArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_EMP);
                            strCodItmArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_ITM);
                            strCodItmMaeArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_ITM_MAE);

                            if( (strCodEmpTbl.equals(strCodEmpArl)) && (strCodItmTbl.equals(strCodItmArl)) && (strCodItmMaeTbl.equals(strCodItmMaeArl)) )
                            {
                                strLinArl=objUti.getStringValueAt(arlDat, j, INT_ARL_EST)==null?"":objUti.getStringValueAt(arlDat, j, INT_ARL_EST).toString();
                                if(strLinArl.equals(""))
                                {
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_STK_SIS), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                    
                                    //Cantidad Contada en Buen Estado.
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_CON_BUE_EST), i, (intPosCol+intNumColEstCon)); 
                                    intPosCol++;
                                    
                                    //Cantidad Contada en Mal Estado.
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_CON_MAL_EST), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;

                                    //Sobrante
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_SOB), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                    
                                    //Cantidad Ingreso Bodega
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_ING_BOD), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                    
                                    //IB
                                    intPosCol++;
                                    
                                    //Cantidad Ingreso Despacho
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_ING_DES), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                    
                                    //Ingreso D
                                    intPosCol++;
                                    
                                    //Cantidad Egreso Bodega
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_EGR_BOD), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                    
                                    //Egreso B
                                    intPosCol++;
                                    
                                    //Cantidad Egreso Despacho
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_CAN_EGR_DES), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                    
                                    //Egreso D
                                    intPosCol++;
                                    
                                    //Diferencia Cantidades.
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_DIF_STK), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;

                                    //Diferencia ingresos y egresos(confirmacion)
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_DIF_CNF), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;
                                   
                                    //Usuario de Conteo.
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_TXT_USR_CON), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;

                                    //Fecha de realización de conteo.
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_FEC_REA_CON), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;

                                    //Cód.Reg.
                                    objTblMod.setValueAt("" + objUti.getStringValueAt(arlDat, j, INT_ARL_COD_REG), i, (intPosCol+intNumColEstCon));
                                    intPosCol++;

                                    objUti.setStringValueAt(arlDat, j, INT_ARL_EST, "P");
                                }
                            }
                        }
                    }
                }
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                objTblMod.initRowsState();
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    //<editor-fold defaultstate="collapsed" desc="/* Botones */">
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDocumentosPendientesConfirmarIngresosBodega(int intCodigoRegistro)
    {
        boolean blnRes = true;
        try 
        {
            objCom37_01 = new ZafCom37_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "IB", intCodigoRegistro);
            objCom37_01.show();
            objCom37_01 = null;
        }
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }


    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDocumentosPendientesConfirmarEgresosBodega(int intCodigoRegistro) 
    {
        boolean blnRes = true;
        try 
        {
            objCom37_01 = new ZafCom37_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "EB", intCodigoRegistro);
            objCom37_01.show();
            objCom37_01 = null;
        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
     /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDocumentosPendientesConfirmarIngresosDespacho(int intCodigoRegistro)
    {
        boolean blnRes = true;
        try 
        {
            objCom37_01 = new ZafCom37_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "ID", intCodigoRegistro);
            objCom37_01.show();
            objCom37_01 = null;
        }
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }


    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDocumentosPendientesConfirmarEgresosDespacho(int intCodigoRegistro) 
    {
        boolean blnRes = true;
        try 
        {
            objCom37_01 = new ZafCom37_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "ED", intCodigoRegistro);
            objCom37_01.show();
            objCom37_01 = null;
        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    //</editor-fold>

    


}