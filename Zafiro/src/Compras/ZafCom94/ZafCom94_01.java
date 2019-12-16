/*
 * ZafCom94_01
 * 
 * Created on 1 de octubre de 2008, 15:44
 */
package Compras.ZafCom94;

import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
/**
 *
 * @author ilino
 */
public class ZafCom94_01 extends javax.swing.JDialog
{
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafParSis objParSis;
    private ZafSelFec objSelFec;
    private ZafUtil objUti;
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_LOC=2;
    final int INT_TBL_DAT_COD_TIP_DOC=3;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    final int INT_TBL_DAT_COD_DOC=6;
    final int INT_TBL_DAT_NUM_DOC=7;
    final int INT_TBL_DAT_MOT_TRA=8;
    final int INT_TBL_DAT_FEC_DOC=9;
    //CLIENTE
    final int INT_TBL_DAT_COD_CLI_PRO=10;
    final int INT_TBL_DAT_NOM_CLI_PRO=11;
    final int INT_TBL_DAT_RUC_CLI_PRO=12;
    final int INT_TBL_DAT_DIR_CLI_PRO=13;
    //BODEGA A CONFIRMAR
    final int INT_TBL_DAT_COD_BOD_EMP=14;
    final int INT_TBL_DAT_COD_BOD_GRP=15;
    //EGRESO
    final int INT_TBL_DAT_COD_EMP_ORI=16;
    final int INT_TBL_DAT_COD_LOC_ORI=17;
    final int INT_TBL_DAT_COD_TIP_DOC_ORI=18;
    final int INT_TBL_DAT_COD_DOC_ORI=19;
    final int INT_TBL_DAT_NUM_DOC_ORI=20;
    //CONFIGURACION
    final int INT_TBL_DAT_COD_CNF_INV=21;
    final int INT_TBL_DAT_TIP_MOV=22;
    final int INT_TBL_DAT_CAM_VAL_CNF=23;
    final int INT_TBL_DAT_COD_SEG=24;
    

    private Vector vecReg, vecDat, vecCab;
    private ZafVenCon vcoTipDoc, vcoCli, vcoVen;

    private ZafDatePicker dtpFecDoc;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblFilCab objTblFilCab;
    
    private String strSQL, strAux;
    
    private String strCodTipDoc = "";
    private String strDesCorTipDoc = "";
    private String strDesLarTipDoc = "";
    
    private String strCodCli, strNomCli;
    private String strCodVen, strNomVen;
    
    private Connection con;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private int intCodEmp, intCodLoc, intCodTipDoc;
    private int intButPre;
    
    private ArrayList arlRegDocCnfSel, arlDatDocCnfSel;
    private static final int INT_TBL_ARL_COD_EMP=0;//OD
    private static final int INT_TBL_ARL_COD_LOC=1;//OD
    private static final int INT_TBL_ARL_COD_TIP_DOC=2;//OD
    private static final int INT_TBL_ARL_COD_DOC=3;//OD
    private static final int INT_TBL_ARL_DES_COR_TIP_DOC=4;//OD
    private static final int INT_TBL_ARL_DES_LAR_TIP_DOC=5;//OD    
    private static final int INT_TBL_ARL_COD_CLI_PRO=6;//OD   
    private static final int INT_TBL_ARL_NOM_CLI_PRO=7;//OD    
    private static final int INT_TBL_ARL_RUC_CLI_PRO=8;//OD   //Rose
    private static final int INT_TBL_ARL_NUM_DOC=9;//OD
    private static final int INT_TBL_ARL_FEC_DOC=10;//OD
    private static final int INT_TBL_ARL_COD_EMP_ORI=11;
    private static final int INT_TBL_ARL_COD_LOC_ORI=12;
    private static final int INT_TBL_ARL_COD_TIP_DOC_ORI=13;
    private static final int INT_TBL_ARL_COD_DOC_ORI=14;
    private static final int INT_TBL_ARL_COD_BOD_EMP=15;
    private static final int INT_TBL_ARL_COD_BOD_GRP=16;
    private static final int INT_TBL_ARL_TIP_MOV_DOC_ORI=17;
    private static final int INT_TBL_ARL_CAM_VAL_CNF=18;
    private static final int INT_TBL_ARL_COD_SEG=19;
    
    
    
    private ZafThreadGUI objThrGUI;
    private boolean blnCon;                     //true: Continua la ejecucián del hilo.
    private boolean blnDocEnc;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    
    private ArrayList arlRegDocSql, arlDatDocSql;
    private static final int INT_ARL_SQL_CNF_NOM_CAM_VAL=0;
    private static final int INT_ARL_SQL_CNF_COD_CNF=1;
    private static final int INT_ARL_SQL_CNF_CAM_ANT_EGR_DET_MOV_INV=2;
    private static final int INT_ARL_SQL_CNF_CAM_ANT_ING_DET_MOV_INV=3;
    
    
    /**
     * Creates new form
     */
    public ZafCom94_01(java.awt.Frame parent, boolean modal
            , Librerias.ZafParSis.ZafParSis ZafParSis, int codBodGrp, String nomBodGrp
            , int codEmp, int codLoc, int codTipDoc, String numeroDocCnf)
    {
        super(parent, modal);
        try{
            this.objParSis = (Librerias.ZafParSis.ZafParSis) ZafParSis.clone();            
            initComponents();
            this.setTitle(objParSis.getNombreMenu());
            lblTit.setText(objParSis.getNombreMenu());
            txtCodBodGrp.setText(""+codBodGrp);
            txtNomBodGrp.setText(nomBodGrp);
            intCodEmp=codEmp;
            intCodLoc=codLoc;
            intCodTipDoc=codTipDoc;
            configurarFrm();
            blnDocEnc=false;
            txtNumOrdDes.setText(numeroDocCnf);
            intButPre=0;//no se ha seleccionado el boton
            if(!cargarDocCnf()){                
                mostrarMsgInf("Fallo la carga de información");
            }
            
            arlDatDocCnfSel=new ArrayList();
            arlDatDocSql=new ArrayList();
        } 
        catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            vecDat=new Vector();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.1");
            lblTit.setText(strAux);

            //Configurar ZafSelFec
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panGenCab.add(objSelFec);
            objSelFec.setBounds(4, 30, 472, 72);
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(578, 4, 102, 20);
            
            configurarTblDat();

            configurarVenConTipDoc();
            configurarVenConCliPrv();
            configurarVenConVen();
            arlDatDocSql=new ArrayList();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
       
    /**
     * Función que permite configurar la tabla
     * @return 
     */
    private boolean configurarTblDat(){
        boolean blnRes = false;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cod.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cod.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC, "Des.Cor.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "Des.Lar.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cod.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Num.Doc.");
            vecCab.add(INT_TBL_DAT_MOT_TRA, "Mot.Tra.");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI_PRO, "Cod.Cli./Pro.");
            vecCab.add(INT_TBL_DAT_NOM_CLI_PRO, "Nom.Cli./Pro.");
            vecCab.add(INT_TBL_DAT_RUC_CLI_PRO, "Ruc.Cli./Pro.");
            vecCab.add(INT_TBL_DAT_DIR_CLI_PRO, "Dir.Cli./Pro.");
            vecCab.add(INT_TBL_DAT_COD_BOD_EMP, "Cod.Bod.Emp");
            vecCab.add(INT_TBL_DAT_COD_BOD_GRP, "Cod.Bod.Grp.");
            vecCab.add(INT_TBL_DAT_COD_EMP_ORI, "Cod.Emp.Ori.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ORI, "Cod.Loc.Ori.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ORI, "Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ORI, "Cod.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_ORI, "Num.Doc.Ori.");
            vecCab.add(INT_TBL_DAT_COD_CNF_INV, "Cod.Cnf.Inv.");
            vecCab.add(INT_TBL_DAT_TIP_MOV, "Tip.Mov.");
            vecCab.add(INT_TBL_DAT_CAM_VAL_CNF, "Cam.Val.Cnf.");
            vecCab.add(INT_TBL_DAT_COD_SEG, "Cód.Seg.");
            
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
//            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_DOC_ORI_CNF, objTblMod.INT_COL_DBL, new Integer(0), null);
//            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_TOT_CNF, objTblMod.INT_COL_DBL, new Integer(0), null);
//            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CNF_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
//            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC, objTblMod.INT_COL_DBL, new Integer(0), null);
//            //Configurar ZafTblMod: Establecer las columnas obligatorias.
//            java.util.ArrayList arlAux=new java.util.ArrayList();
//            arlAux.add("" + INT_TBL_DAT_COD_LET_ITM);
//            objTblMod.setColumnasObligatorias(arlAux);
//            arlAux=null;
//            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
//            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MOT_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI_PRO).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_RUC_CLI_PRO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIR_CLI_PRO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_EMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_GRP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ORI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ORI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ORI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ORI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_ORI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_CNF_INV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAM_VAL_CNF).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SEG).setPreferredWidth(30);
            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CNF_INV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_MOV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAM_VAL_CNF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SEG, tblDat);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            

        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite cargar información en la ventana de clientes/proveedores
     * @return true Si se pudo realizar la consulta
     * <BR> false Caso contrario
     */
    private boolean configurarVenConCliPrv(){
        boolean blnRes = true;
        try{
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Cli/Pro.");
            arlAli.add("Nom.Cli/Pro.");
            arlAli.add("Dir.Cli/Pro.");
            arlAli.add("Tel.Cli/Pro.");
            arlAli.add("Ide.Cli/Pro.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("180");
            arlAncCol.add("120");
            arlAncCol.add("80");
            arlAncCol.add("100");

            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a2.co_cli, a2.tx_nom, a2.tx_dir, a2.tx_tel, a2.tx_ide";
            strSQL+=" FROM tbr_cliloc AS a1 INNER JOIN tbm_cli AS a2";
            strSQL+=" ON(a2.co_emp=a1.co_emp AND a2.co_cli=a1.co_cli)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " ";
            strSQL+=" AND a2.st_reg NOT IN('I','T')";
            strSQL+=" ORDER BY a.tx_nom";

            vcoCli = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
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
    
    /**
     * Función que permite cargar información en la ventana de vendedor
     * @return true Si se pudo realizar la consulta
     * <BR> false Caso contrario
     */
    private boolean configurarVenConVen(){
        boolean blnRes = true;
        try{
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Ven.");
            arlAli.add("Nom.Ven.");
            
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a.co_usr, a.tx_nom  ";
            strSQL+=" FROM tbr_usremp AS b";
            strSQL+=" INNER JOIN tbm_usr AS a ON(a.co_usr=b.co_usr) ";
            strSQL+=" WHERE b.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND b.st_ven='S' AND a.st_reg NOT IN ('I')";
            strSQL+=" ORDER BY a.tx_nom";

            vcoVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
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
    
    
    /**
     * Función que permite cargar información en la ventana de tipo de documento
     * @return true Si se pudo realizar la consulta
     * <BR> false Caso contrario
     */
    private boolean configurarVenConTipDoc(){
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
            strSQL="";
            strSQL+=" SELECT a.co_tipdoc, a.tx_descor, a.tx_deslar";
            strSQL+=" FROM tbr_tipdocdetprg AS b";
            strSQL+=" INNER JOIN tbm_cabtipdoc AS a ON(b.co_emp=a.co_emp AND b.co_loc=a.co_loc AND b.co_tipdoc=a.co_tipdoc)";
            strSQL+=" WHERE b.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND b.co_loc = " + objParSis.getCodigoLocal() + "";
            strSQL+=" AND b.co_mnu = " + objParSis.getCodigoMenu();

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
    

//    public boolean acepta() {
//        return blnAcepta;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panRpt = new javax.swing.JPanel();
        tabFil = new javax.swing.JTabbedPane();
        panFilGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBodGrp = new javax.swing.JTextField();
        txtNomBodGrp = new javax.swing.JTextField();
        panGenFil = new javax.swing.JPanel();
        chkDocPenCon = new javax.swing.JCheckBox();
        chkDocConPar = new javax.swing.JCheckBox();
        chkDocConTot = new javax.swing.JCheckBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipdoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtCodTipDoc = new javax.swing.JTextField();
        lblNumOrdDes = new javax.swing.JLabel();
        txtNumOrdDes = new javax.swing.JTextField();
        panFilDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBot = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panRpt.setLayout(new java.awt.BorderLayout());

        panFilGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panFilGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(120, 100));
        panGenCab.setRequestFocusEnabled(false);
        panGenCab.setLayout(null);

        lblBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblBod.setText("Bodega:");
        panGenCab.add(lblBod);
        lblBod.setBounds(10, 10, 50, 15);

        txtCodBodGrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGenCab.add(txtCodBodGrp);
        txtCodBodGrp.setBounds(60, 6, 60, 20);

        txtNomBodGrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panGenCab.add(txtNomBodGrp);
        txtNomBodGrp.setBounds(120, 6, 290, 20);

        panFilGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenFil.setPreferredSize(new java.awt.Dimension(110, 240));
        panGenFil.setRequestFocusEnabled(false);
        panGenFil.setLayout(null);

        chkDocPenCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocPenCon.setSelected(true);
        chkDocPenCon.setText("Mostrar los documentos que están pendientes de confirmar");
        chkDocPenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        chkDocPenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocPenConActionPerformed(evt);
            }
        });
        panGenFil.add(chkDocPenCon);
        chkDocPenCon.setBounds(0, 6, 320, 20);

        chkDocConPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConPar.setSelected(true);
        chkDocConPar.setText("Mostrar los documentos que están confirmados parcialmente");
        chkDocConPar.setPreferredSize(new java.awt.Dimension(20, 20));
        chkDocConPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocConParActionPerformed(evt);
            }
        });
        panGenFil.add(chkDocConPar);
        chkDocConPar.setBounds(0, 24, 320, 20);

        chkDocConTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkDocConTot.setText("Mostrar los documentos que están confirmados totalmente");
        chkDocConTot.setPreferredSize(new java.awt.Dimension(20, 20));
        chkDocConTot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDocConTotActionPerformed(evt);
            }
        });
        panGenFil.add(chkDocConTot);
        chkDocConTot.setBounds(0, 44, 320, 20);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los Documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panGenFil.add(optTod);
        optTod.setBounds(0, 70, 320, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Solo los documentos que cumplan el criterio seleccionado");
        panGenFil.add(optFil);
        optFil.setBounds(0, 90, 320, 20);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipdoc.setText("Tipo de Documento:");
        panGenFil.add(lblTipdoc);
        lblTipdoc.setBounds(30, 110, 100, 20);

        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 110, 50, 20);

        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panGenFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(180, 110, 280, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("jButton2");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenFil.add(butTipDoc);
        butTipDoc.setBounds(460, 110, 20, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente/Proveedor:");
        panGenFil.add(lblCli);
        lblCli.setBounds(30, 130, 100, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panGenFil.add(txtCodCli);
        txtCodCli.setBounds(130, 130, 50, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panGenFil.add(txtNomCli);
        txtNomCli.setBounds(180, 130, 280, 20);

        butCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panGenFil.add(butCli);
        butCli.setBounds(460, 130, 20, 20);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen.setText("Vend / Comp:");
        panGenFil.add(lblVen);
        lblVen.setBounds(30, 150, 100, 20);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panGenFil.add(txtCodVen);
        txtCodVen.setBounds(130, 150, 50, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panGenFil.add(txtNomVen);
        txtNomVen.setBounds(180, 150, 280, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGenFil.add(butVen);
        butVen.setBounds(460, 150, 20, 20);
        panGenFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(110, 110, 20, 20);

        lblNumOrdDes.setText("Número OD:");
        panGenFil.add(lblNumOrdDes);
        lblNumOrdDes.setBounds(30, 180, 80, 14);
        panGenFil.add(txtNumOrdDes);
        txtNumOrdDes.setBounds(130, 174, 90, 20);

        panFilGen.add(panGenFil, java.awt.BorderLayout.CENTER);

        tabFil.addTab("General", panFilGen);

        panFilDat.setLayout(new java.awt.BorderLayout());

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
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panFilDat.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFil.addTab("Datos", panFilDat);

        panRpt.add(tabFil, java.awt.BorderLayout.CENTER);

        getContentPane().add(panRpt, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panSubBot.add(butCon);

        butLim.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butLim.setText("Limpiar");
        butLim.setPreferredSize(new java.awt.Dimension(79, 23));
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panSubBot.add(butLim);

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(79, 23));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(79, 23));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBot.add(panSubBot, java.awt.BorderLayout.EAST);

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

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(600, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start(); //              
                
            }            
        }
        else
        {
            blnCon=false;
        }
  
    }//GEN-LAST:event_butConActionPerformed

    
    
    
    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        objTblMod.removeAllRows();
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodCli.setText("");
        txtNomCli.setText("");
        txtCodVen.setText("");
        txtNomVen.setText("");        
        txtNumOrdDes.setText("");
    }//GEN-LAST:event_butLimActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
        if( tblDat.getSelectedRow()!=-1 ){
            intButPre=1;//se presiono aceptar            
        }
        else{
            intButPre=2;//se presiono aceptar pero se lo toma como cancelar porque no cambio nada en nincuna de las dos tablas
        }
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intButPre=2;//se presiono cancelar
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        strCodCli = "";
        strNomCli = "";
        strCodVen = "";
        strNomVen = "";

        txtCodCli.setText("");
        txtNomCli.setText("");
        txtCodVen.setText("");
        txtNomVen.setText("");

        strDesCorTipDoc = "";
        strDesLarTipDoc = "";
        strCodTipDoc = "";
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
    }//GEN-LAST:event_optTodActionPerformed

    private void chkDocConTotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocConTotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDocConTotActionPerformed

    private void chkDocPenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocPenConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDocPenConActionPerformed

    private void chkDocConParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDocConParActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDocConParActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen = txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if (txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } 
            else {
                mostrarVenConVen(0);
            }
        } 
        else{
            txtCodVen.setText(strCodVen);
        }
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli = txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            } else {
                mostrarVenConCli(0);
            }
        } 
        else{
            txtCodCli.setText(strCodCli);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

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
                mostrarVenConTipDoc(1);
            }
        } 
        else{
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

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
                mostrarVenConTipDoc(2);
            }
        } 
        else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strNomCli = txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        if (!txtNomCli.getText().equalsIgnoreCase(strNomCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            } else {
                mostrarVenConCli(1);
            }
        } 
        else {
            txtNomCli.setText(strNomCli);
        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen = txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen)) {
            if (txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            } else {
                mostrarVenConVen(1);
            }
        } 
        else {
            txtNomVen.setText(strNomVen);
        }
    }//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        vcoVen.setTitle("Listado de Clientes");
        vcoVen.setCampoBusqueda(1);
        vcoVen.show();
        if (vcoVen.getSelectedButton() == vcoVen.INT_BUT_ACE) {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        vcoCli.setTitle("Listado de Clientes");
        vcoCli.setCampoBusqueda(1);
        vcoCli.show();
        if (vcoCli.getSelectedButton() == vcoCli.INT_BUT_ACE) {
            txtCodCli.setText(vcoCli.getValueAt(1));
            txtNomCli.setText(vcoCli.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        vcoTipDoc.setCampoBusqueda(1);
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

            strCodTipDoc = vcoTipDoc.getValueAt(1);
            strDesCorTipDoc = vcoTipDoc.getValueAt(2);
            strDesLarTipDoc = vcoTipDoc.getValueAt(3);

            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            intButPre=2;//se presiono la X de cerrar formulario
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            if( tblDat.getSelectedRow()!=-1 ){
                intButPre=1;//se presiono aceptar            
            }
            else{
                intButPre=2;//se presiono aceptar pero se lo toma como cancelar porque no cambio nada en nincuna de las dos tablas
            }
            dispose();
        }
        
        
        
    }//GEN-LAST:event_tblDatKeyPressed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==2)
        {
            if( tblDat.getSelectedRow()!=-1 ){
                intButPre=1;//se presiono aceptar            
            }
            else{
                intButPre=2;//se presiono aceptar pero se lo toma como cancelar porque no cambio nada en nincuna de las dos tablas
            }
            dispose();
        }
    }//GEN-LAST:event_tblDatMouseClicked

    

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{            
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strCodTipDoc = vcoTipDoc.getValueAt(1);
                        strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                        strDesLarTipDoc = vcoTipDoc.getValueAt(3);
                        optFil.setSelected(true);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strCodTipDoc = vcoTipDoc.getValueAt(1);
                        strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                        strDesLarTipDoc = vcoTipDoc.getValueAt(3);
                        optFil.setSelected(true);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strCodTipDoc = vcoTipDoc.getValueAt(1);
                            strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                            strDesLarTipDoc = vcoTipDoc.getValueAt(3);
                            optFil.setSelected(true);
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strCodTipDoc = vcoTipDoc.getValueAt(1);
                        strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                        strDesLarTipDoc = vcoTipDoc.getValueAt(3);
                        optFil.setSelected(true);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strCodTipDoc = vcoTipDoc.getValueAt(1);
                            strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                            strDesLarTipDoc = vcoTipDoc.getValueAt(3);
                            optFil.setSelected(true);

                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strCodTipDoc = vcoTipDoc.getValueAt(1);
                        strDesCorTipDoc = vcoTipDoc.getValueAt(2);
                        strDesLarTipDoc = vcoTipDoc.getValueAt(3);
                        optFil.setSelected(true);
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(1);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(2));
                        optFil.setSelected(true);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoCli.buscar("a1.tx_desCor", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(2));
                        optFil.setSelected(true);
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(2));
                            optFil.setSelected(true);
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoCli.buscar("a1.tx_desLar", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(2));
                        optFil.setSelected(true);
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(2));
                            optFil.setSelected(true);
                        }
                        else
                        {
                            txtNomCli.setText(strNomCli);
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
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConVen(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(1);
                    vcoCli.show();
                    if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(2));
                        optFil.setSelected(true);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoVen.buscar("a1.tx_desCor", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(2));
                        optFil.setSelected(true);
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(1);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.show();
                        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(2));
                            optFil.setSelected(true);
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoVen.buscar("a1.tx_desLar", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(2));
                        optFil.setSelected(true);
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.show();
                        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(2));
                            optFil.setSelected(true);
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
    
    
    /**
     * Función que permite cerrar el formulario
     */
    private void cerrarFrm(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {
            System.gc();
            dispose();
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
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
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_MOT_TRA:
                    strMsg="Motivo de la transferencia";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CLI_PRO:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI_PRO:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_RUC_CLI_PRO:
                    strMsg="Ruc del cliente/proveedor";
                    break;
                case INT_TBL_DAT_DIR_CLI_PRO:
                    strMsg="Dirección del cliente/proveedor";
                    break;
                case INT_TBL_DAT_COD_BOD_EMP:
                    strMsg="Código de bodega de empresa";
                    break;
                case INT_TBL_DAT_COD_BOD_GRP:
                    strMsg="Código de bodega de grupo";
                    break;
                case INT_TBL_DAT_COD_EMP_ORI:
                    strMsg="Código de empresa del documento origen";
                    break;
                case INT_TBL_DAT_COD_LOC_ORI:
                    strMsg="Código de local del documento origen";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ORI:
                    strMsg="Código de tipo del documento origen";
                    break;
                case INT_TBL_DAT_COD_DOC_ORI:
                    strMsg="Código del documento origen";
                    break;
                case INT_TBL_DAT_NUM_DOC_ORI:
                    strMsg="Número del documento origen";
                    break;
                case INT_TBL_DAT_COD_CNF_INV:
                    strMsg="Código de configuración para confirmación del documento";
                    break;
                case INT_TBL_DAT_TIP_MOV:
                    strMsg="Tipo de movimiento del documento origen";
                    break;
                case INT_TBL_DAT_CAM_VAL_CNF:
                    strMsg="Campo a validar - campo antes, según tipo de confirmación";
                    break;
                case INT_TBL_DAT_COD_SEG:
                    strMsg="Código de seguimiento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    


    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLim;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkDocConPar;
    private javax.swing.JCheckBox chkDocConTot;
    private javax.swing.JCheckBox chkDocPenCon;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumOrdDes;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilDat;
    private javax.swing.JPanel panFilGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenFil;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFil;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodBodGrp;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodGrp;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtNumOrdDes;
    // End of variables declaration//GEN-END:variables


    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDocCnf(){
        boolean blnRes=true;
        //String strCodCnf="";
        vecDat.clear();
        int i=0;
        String strSQLUpd="";
        String strAuxTmp="";
        String strNomCamVal="";
        try{
            arlDatDocSql.clear();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strAux=txtNumOrdDes.getText();
                blnCon=true;
                if (!strAux.equals("")){
                    
                    if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) ){
                        strAuxTmp=" AND d2.ne_numOrdDes=" + strAux;
                    }
                    else if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) ){
                        strAuxTmp=" AND d2.ne_numDocOri=" + strAux;
                    }
                }
                
                strSQL="";
                strSQL+=" SELECT a1.co_cfg, a1.tx_tipMov, a1.tx_nom, a2.co_bod";
                if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) )
                    strSQL+=" , a1.tx_camAntEgrDetMovInv";
                else if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) )
                    strSQL+=" , a1.tx_camAntIngDetMovInv";
                
                strSQL+=" FROM tbm_cfgConInv AS a1 INNER JOIN tbm_cfgConInvTipDocBod AS a2";
                strSQL+=" ON a1.co_cfg=a2.co_cfg";
                strSQL+=" WHERE a2.co_emp=" + intCodEmp + "";
                strSQL+=" AND a2.co_loc=" + intCodLoc + "";
                strSQL+=" AND a2.co_tipDoc=" + intCodTipDoc + "";
                strSQL+=" AND a1.st_reg='A'";
                System.out.println("cargarDocCnf-1: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) )
                        strNomCamVal=rst.getString("tx_camAntEgrDetMovInv");
                    else if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) )
                        strNomCamVal=rst.getString("tx_camAntIngDetMovInv");
                    
                    arlRegDocSql=new ArrayList();
                    arlRegDocSql.add(INT_ARL_SQL_CNF_NOM_CAM_VAL, strNomCamVal);
                    arlRegDocSql.add(INT_ARL_SQL_CNF_COD_CNF, rst.getString("co_cfg"));
                    
                    
                    
                    
                    if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) )
                        arlRegDocSql.add(INT_ARL_SQL_CNF_CAM_ANT_EGR_DET_MOV_INV, rst.getString("tx_camAntEgrDetMovInv"));
                    else
                        arlRegDocSql.add(INT_ARL_SQL_CNF_CAM_ANT_EGR_DET_MOV_INV, "");
                    
                    if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) )
                        arlRegDocSql.add(INT_ARL_SQL_CNF_CAM_ANT_ING_DET_MOV_INV, rst.getString("tx_camAntIngDetMovInv"));
                    else
                        arlRegDocSql.add(INT_ARL_SQL_CNF_CAM_ANT_ING_DET_MOV_INV, "");
                            
                            
                            
                    
                    arlDatDocSql.add(arlRegDocSql);
                }
                
                if(arlDatDocSql.size()>0){
                    for(int a=0; a<arlDatDocSql.size(); a++){
                        
                        
                        strSQL="";
                        if(i!=0)
                            strSQL+=" UNION ";
                        else if(i==0)
                            i++;

                        if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) ){//EGRESO BODEGA       EGRESO DESPACHO
                            strSQL+="(";
                            strSQL+=" SELECT d1.co_cfg, d1.tx_tipmov, d1.tx_nom";
                            strSQL+=" , d1.tx_camantegrdetmovinv, d1.tx_camactegrdetmovinv";
                            strSQL+=" , d1.co_bodEmp, d1.co_bodGrp, d2.co_empOrdDes, d2.co_locOrdDes, d2.co_tipDocOrdDes, d2.tx_desCor, d2.tx_desLar, d2.co_docOrdDes, d2.ne_numOrdDes, d2.fe_doc";
                            strSQL+=" , d2.co_clides, d2.tx_rucclides, d2.tx_nomclides, d2.tx_dirclides, d2.tx_tipMov";
                            strSQL+=" , d2.co_empDocOri, d2.co_locDocOri, d2.co_tipDocDocOri, d2.co_docDocOri, d2.ne_numDocOri";
                            strSQL+=" , d2.co_seg";
                            strSQL+=" FROM(";
                            strSQL+=" 	SELECT a1.co_cfg, a1.tx_tipmov, a1.tx_nom, a1.tx_camantegrdetmovinv, a1.tx_camactegrdetmovinv";
                            strSQL+="         , a1.tx_camantingdetmovinv, a1.tx_camactingdetmovinv, a1.tx_camantegrinvbod";
                            strSQL+="         , a1.tx_camactegrinvbod, a1.tx_camantinginvbod, a1.tx_camactinginvbod, a2.co_bod AS co_bodEmp, a3.co_empGrp, a3.co_bodGrp";
                            strSQL+=" 	FROM tbm_cfgConInv AS a1 INNER JOIN (";
                            strSQL+=" 					     tbm_cfgconinvtipdocbod AS a2 INNER JOIN tbr_bodempbodgrp AS a3";
                            strSQL+=" 					     ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                            strSQL+=" 					     )";
                            strSQL+=" 	ON a1.co_cfg=a2.co_cfg";
                            strSQL+=" 	WHERE a1.st_reg='A' AND a3.co_bodGrp=" + txtCodBodGrp.getText() + "";
                            strSQL+=" 	AND a2.co_emp=" + intCodEmp + " AND a2.co_loc=" + intCodLoc + " AND a2.co_tipDoc=" + intCodTipDoc + "";
                            strSQL+=" ) AS d1";
                            strSQL+=" INNER JOIN(";                                               

                            strSQL+=" 		SELECT b1.st_merIngEgrFisBod, b1.tx_tipMov, b1.co_empRelCabSolTraInv, b1.co_locRelCabSolTraInv, b1.co_tipDocRelCabSolTraInv, b1.co_docRelCabSolTraInv";
                            strSQL+=" 		, b1.co_regDocOri, b1.co_itm, b1.nd_can, b1.nd_canEgrBod, b1.nd_canDesEntBod, b1.nd_canDesEntCli, b1.nd_canTra, b1.nd_canRev, b1.nd_CanPen";
                            strSQL+=" 		, b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_bod, b1.co_empDocOri, b1.co_locDocOri, b1.co_tipDocDocOri, b1.co_docDocOri, b1.ne_numDocOri";
                            strSQL+=" 		, b3.co_empOrdDes, b3.co_locOrdDes, b3.co_tipDocOrdDes, b3.co_docOrdDes, b3.ne_numOrdDes, b3.tx_desCor, b3.tx_desLar, b3.fe_doc";
                            strSQL+=" 		, b3.co_cliDes, b3.tx_rucclides, b3.tx_nomclides, b3.tx_dirclides";
                            strSQL+="           , b1.co_seg";
                            strSQL+=" 		FROM(";
                            strSQL+=" 			SELECT a2.st_merIngEgrFisBod, a1.tx_tipMov";
                            strSQL+=" 			, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                            strSQL+=" 			, a2.co_reg AS co_regDocOri, a2.co_itm, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen";
                            strSQL+=" 			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_bod";
                            strSQL+=" 			, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri";
                            strSQL+=" 			, a3.co_seg";
                            strSQL+=" 			FROM (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                            strSQL+="                         ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                            strSQL+="                         AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv";
                            strSQL+="                         AND a1.co_doc=a3.co_docRelCabMovInv";
                            strSQL+=" 			)";
                            strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                            strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+=" 			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S' AND a1.st_genOrdDes='S' AND a1.st_ordDesGen='S'";
                            strSQL+=" 			AND a2.nd_canPen<>0 AND (a1.st_conInv IS NULL OR a1.st_conInv IN('P','E'))";
                            strSQL+=" 			ORDER BY a1.ne_numDoc";
                            strSQL+=" 		) AS b1";
                            strSQL+="  		INNER JOIN(";
                            strSQL+="                      	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom AS tx_nomBod";
                            strSQL+="                       	FROM(";
                            strSQL+="                       		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                            strSQL+="                       		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                            strSQL+="                       		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                            strSQL+="                       		WHERE a1.st_reg='A'";
                            strSQL+="                                AND a2.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                            strSQL+="                                AND a2.co_bodGrp=" + txtCodBodGrp.getText() + "";
                            strSQL+="                       	) AS b1";
                            strSQL+="                       	INNER JOIN(";
                            strSQL+="                       		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                            strSQL+="                       		FROM tbr_bodEmpBodGrp AS a2";
                            strSQL+="                       	) AS b2";
                            strSQL+="                       	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                            strSQL+="  			GROUP BY b1.co_empGrp, b1.co_bodGrp, b2.co_emp, b2.co_bod, b1.tx_nom";
                            strSQL+="  		) AS b2";
                            strSQL+="  		ON b1.co_empDocOri=b2.co_emp AND b1.co_bod=b2.co_bod";
                            strSQL+=" 		LEFT OUTER JOIN(";
                            strSQL+="  			SELECT a1.co_emp AS co_empOrdDes, a1.co_loc AS co_locOrdDes, a1.co_tipDoc AS co_tipDocOrdDes, a3.tx_desCor";
                            strSQL+="  			, a3.tx_desLar, a1.co_doc AS co_docOrdDes, a1.ne_numOrdDes, a1.fe_doc";
                            strSQL+="  			, a1.co_clides, a1.tx_rucclides, a1.tx_nomclides, a1.tx_dirclides";
                            strSQL+="  			, a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel";
                            strSQL+="  			, a1.co_ptopar AS co_bod";
                            strSQL+="  			FROM ( tbm_cabGuiRem AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                            strSQL+="  			INNER JOIN tbm_detGuiRem AS a2";
                            strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+="  			AND a1.ne_numdoc=0 AND a1.ne_numOrdDes IS NOT NULL AND a1.st_reg='A' AND (a1.st_conInv IS NULL OR a1.st_conInv IN('P','E'))";
                            strSQL+=" 		) AS b3";
                            strSQL+=" 		ON b1.co_empDocOri=b3.co_empRel AND b1.co_locDocOri=b3.co_locRel AND b1.co_tipDocDocOri=b3.co_tipDocRel AND b1.co_docDocOri=b3.co_docRel AND b1.co_regDocOri=b3.co_regRel";
                            strSQL+=" ) AS d2";                        
                            strSQL+=" ON d1.tx_tipMov=d2.tx_tipMov";
                            strSQL+=" WHERE d1.co_cfg IN(" + objUti.getStringValueAt(arlDatDocSql, a, INT_ARL_SQL_CNF_COD_CNF) + ")";//rstCab.getString("co_cfg")
                            strSQL+=" AND d2." + objUti.getStringValueAt(arlDatDocSql, a, INT_ARL_SQL_CNF_CAM_ANT_EGR_DET_MOV_INV) + "<>0";//rstCab.getString("tx_camAntEgrDetMovInv")

                            if(strAux.length()>0)
                                strSQL+="  			AND d2.ne_numOrdDes=" + strAux + "";

                            strSQL+="" + strAuxTmp;
                            strSQL+=" GROUP BY d1.co_cfg, d1.tx_tipmov, d1.tx_nom";
                            strSQL+=" , d1.tx_camantegrdetmovinv, d1.tx_camactegrdetmovinv	 /*EGRESO*/";
                            strSQL+=" , d1.co_bodEmp, d1.co_bodGrp, d2.co_empOrdDes, d2.co_locOrdDes, d2.co_tipDocOrdDes, d2.tx_desCor, d2.tx_desLar, d2.co_docOrdDes, d2.ne_numOrdDes, d2.fe_doc";
                            strSQL+=" , d2.co_clides, d2.tx_rucclides, d2.tx_nomclides, d2.tx_dirclides, d2.tx_tipMov";
                            strSQL+=" , d2.co_empDocOri, d2.co_locDocOri, d2.co_tipDocDocOri, d2.co_docDocOri, d2.ne_numDocOri";
                            strSQL+=" , d2.co_seg";
                            strSQL+=" ORDER BY d2.ne_numOrdDes";
                            strSQL+=")";
                        }
                        else if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) ){//INGRESO DESPACHO     INGRESO BODEGA
                            strSQL+="(";
                            strSQL+=" SELECT d1.co_cfg, d1.tx_tipmov, d1.tx_nom";
                            strSQL+=" , d1.tx_camAntIngDetMovInv, d1.tx_camActIngDetMovInv";
                            strSQL+=" , d1.co_bodEmp, d1.co_bodGrp, d2.tx_desCor, d2.tx_desLar, d2.fe_doc";
                            strSQL+=" , null AS co_clides, null AS tx_rucclides, null AS tx_nomclides, null AS tx_dirclides, d2.tx_tipMov";
                            strSQL+=" , d2.co_empDocOri, d2.co_locDocOri, d2.co_tipDocDocOri, d2.co_docDocOri, d2.ne_numDocOri";
                            strSQL+=" , d2.co_seg";
                            strSQL+="  FROM(";
                            strSQL+="  	SELECT a1.co_cfg, a1.tx_tipmov, a1.tx_nom, a1.tx_camAntEgrDetMovInv, a1.tx_camActEgrDetMovInv";
                            strSQL+="          , a1.tx_camAntIngDetMovInv, a1.tx_camActIngDetmovInv, a1.tx_camAntEgrInvBod";
                            strSQL+="          , a1.tx_camActEgrInvBod, a1.tx_camAntIngInvBod, a1.tx_camactinginvbod, a2.co_bod AS co_bodEmp, a3.co_empGrp, a3.co_bodGrp";
                            strSQL+=" 	FROM tbm_cfgConInv AS a1 INNER JOIN (";
                            strSQL+=" 					     tbm_cfgconinvtipdocbod AS a2 INNER JOIN tbr_bodempbodgrp AS a3";
                            strSQL+=" 					     ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                            strSQL+=" 					     )";
                            strSQL+=" 	ON a1.co_cfg=a2.co_cfg";
                            strSQL+="  	WHERE a1.st_reg='A' AND a3.co_bodGrp=" + txtCodBodGrp.getText() + "";
                            strSQL+="  	AND a2.co_emp=" + intCodEmp + " AND a2.co_loc=" + intCodLoc + " AND a2.co_tipDoc=" + intCodTipDoc + "";
                            strSQL+=" ) AS d1";
                            strSQL+="  INNER JOIN(";                       
                            strSQL+="  	SELECT b1.st_merIngEgrFisBod, b1.tx_tipMov, b1.co_empRelCabSolTraInv, b1.co_locRelCabSolTraInv";
                            strSQL+="         , b1.co_tipDocRelCabSolTraInv, b1.co_docRelCabSolTraInv";
                            strSQL+=" 	, b1.co_itm, b1.nd_can, b1.nd_canEgrBod, b1.nd_canDesEntBod, b1.nd_canDesEntCli, b1.nd_canTra, b1.nd_canRev, b1.nd_CanPen";
                            strSQL+=" 	, b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_desCor, b1.tx_desLar";
                            strSQL+=" 	, b1.co_empDocOri, b1.co_locDocOri, b1.co_tipDocDocOri, b1.co_docDocOri, b1.ne_numDocOri, b1.co_regDocOri, b1.nd_canIngBod";
                            strSQL+=" 	, b1.co_empEgr, b1.co_locEgr, b1.co_tipDocEgr, b1.co_docEgr, b1.co_regEgr";
                            strSQL+=" 	, b1.nd_canTraEgr, b1.co_seg";
                            strSQL+="  	FROM(";
                            strSQL+="  		SELECT e1.st_merIngEgrFisBod, e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv";
                            strSQL+="                , e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                            strSQL+=" 		, e1.co_itm, e1.nd_can, e1.nd_canEgrBod, e1.nd_canDesEntBod, e1.nd_canDesEntCli, e1.nd_canTra, e1.nd_canRev, e1.nd_CanPen";
                            strSQL+="  		, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar";
                            strSQL+="  		, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocDocOri, e1.co_docDocOri, e1.ne_numDocOri, e1.co_regDocOri, e1.nd_canIngBod";
                            strSQL+="  		, e2.co_empEgr, e2.co_locEgr, e2.co_tipDocEgr, e2.co_docEgr, e2.co_regEgr";
                            strSQL+="  		, e3.nd_canTra AS nd_canTraEgr, e3.co_seg";
                            strSQL+="  		FROM(";
                            strSQL+=" 			SELECT a2.st_merIngEgrFisBod, a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                            strSQL+="  			, a2.co_reg AS co_regDocOri, a2.co_itm, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen";
                            strSQL+="  			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.tx_desCor, a3.tx_desLar, a2.nd_canIngBod";
                            strSQL+="  			, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri";
                            strSQL+="  			FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                            strSQL+="                       INNER JOIN (tbm_detMovInv AS a2 INNER JOIN tbr_bodEmpBodGrp AS a4 ON a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod AND a4.co_bodGrp=" + txtCodBodGrp.getText() + ")";
                            strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+="  			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                            strSQL+="  			AND a2.nd_canPen<>0";
                            strSQL+="  			ORDER BY a1.ne_numDoc";
                            strSQL+="  		) AS e1";
                            strSQL+="  		INNER JOIN(";
                            strSQL+="  			SELECT a1.co_emp AS co_empEgr, a1.co_loc AS co_locEgr, a1.co_tipDoc AS co_tipDocEgr, a1.co_doc AS co_docEgr, a1.co_reg AS co_regEgr";
                            strSQL+="  			, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.co_regRel";
                            strSQL+="  			FROM tbr_detMovInv AS a1";
                            strSQL+="  		) AS e2";
                            strSQL+="  		ON e1.co_empDocOri=e2.co_empRel AND e1.co_locDocOri=e2.co_locRel AND e1.co_tipDocDocOri=e2.co_tipDocRel AND e1.co_docDocOri=e2.co_docRel AND e1.co_regDocOri=e2.co_regRel";
                            strSQL+="  		INNER JOIN(";                        
                            strSQL+="  			SELECT a2.st_merIngEgrFisBod, a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                            strSQL+="  			, a2.co_reg, a2.co_itm, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen";
                            strSQL+="  			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.co_seg";
                            strSQL+="  			FROM (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                            strSQL+=" 				ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                            strSQL+=" 				)";
                            strSQL+="  			INNER JOIN tbm_detMovInv AS a2";
                            strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+="  			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                            if(objParSis.getCodigoMenu()==4000)
                                strSQL+="  			AND a2.nd_canTra<>0";
                            else if(objParSis.getCodigoMenu()==4010)
                                strSQL+="  			AND (CASE WHEN a1.tx_tipMov='R' THEN a2.nd_canTra<>0 ELSE  a2.nd_canTra=0 END)";



                            strSQL+="  			ORDER BY a1.ne_numDoc";
                            strSQL+="  		) AS e3";
                            strSQL+=" 		ON e2.co_empEgr=e3.co_emp AND e2.co_locEgr=e3.co_loc AND e2.co_tipDocEgr=e3.co_tipDoc AND e2.co_docEgr=e3.co_doc AND e2.co_regEgr=e3.co_reg";
                            strSQL+="  	) AS b1";
                            strSQL+="  	GROUP BY b1.st_merIngEgrFisBod, b1.tx_tipMov, b1.co_empRelCabSolTraInv, b1.co_locRelCabSolTraInv";
                            strSQL+="         , b1.co_tipDocRelCabSolTraInv, b1.co_docRelCabSolTraInv";
                            strSQL+=" 	, b1.co_itm, b1.nd_can, b1.nd_canEgrBod, b1.nd_canDesEntBod, b1.nd_canDesEntCli, b1.nd_canTra, b1.nd_canRev, b1.nd_CanPen";
                            strSQL+=" 	, b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_desCor, b1.tx_desLar";
                            strSQL+=" 	, b1.co_empDocOri, b1.co_locDocOri, b1.co_tipDocDocOri, b1.co_docDocOri, b1.ne_numDocOri, b1.co_regDocOri, b1.nd_canIngBod";
                            strSQL+=" 	, b1.co_empEgr, b1.co_locEgr, b1.co_tipDocEgr, b1.co_docEgr, b1.co_regEgr";
                            strSQL+=" 	, b1.nd_canTraEgr, b1.co_seg";
                            strSQL+=" ) AS d2";
                            strSQL+=" ON d1.tx_tipMov=d2.tx_tipMov";
                            strSQL+=" WHERE d1.co_cfg IN(" + objUti.getStringValueAt(arlDatDocSql, a, INT_ARL_SQL_CNF_COD_CNF) + ")";//rstCab.getString("co_cfg")
                            strSQL+="" + strAuxTmp;
                            strSQL+=" GROUP BY d1.co_cfg, d1.tx_tipmov, d1.tx_nom, d1.tx_camAntIngDetMovInv, d1.tx_camActIngDetMovInv";
                            strSQL+=" , d1.co_bodEmp, d1.co_bodGrp, d2.tx_desCor, d2.tx_desLar, d2.fe_doc, d2.tx_tipMov";
                            strSQL+=" , d2.co_empDocOri, d2.co_locDocOri, d2.co_tipDocDocOri, d2.co_docDocOri, d2.ne_numDocOri";
                            //strSQL+=" , d2." + objUti.getStringValueAt(arlDatDocSql, a, INT_ARL_SQL_CNF_CAM_ANT_ING_DET_MOV_INV) + "";//rstCab.getString("tx_camAntIngDetMovInv")
                            strSQL+=" , d2.co_seg";
                            strSQL+=" ORDER BY d2.ne_numDocOri";
                            strSQL+=")";
                            
                            strSQL+=" UNION";
                            strSQL+=" (";
                            strSQL+=" SELECT d1.co_cfg, d1.tx_tipmov, d1.tx_nom";
                            strSQL+=" , d1.tx_camAntIngDetMovInv, d1.tx_camActIngDetMovInv";
                            strSQL+=" , d1.co_bodEmp, d1.co_bodGrp, d2.tx_desCor, d2.tx_desLar, d2.fe_doc";
                            strSQL+=" , null AS co_clides, null AS tx_rucclides, null AS tx_nomclides, null AS tx_dirclides, d2.tx_tipMov";
                            strSQL+=" , d2.co_empDocOri, d2.co_locDocOri, d2.co_tipDocDocOri, d2.co_docDocOri, d2.ne_numDocOri";
                            strSQL+=" , d2.co_seg";
                            strSQL+="  FROM(";
                            strSQL+="  	SELECT a1.co_cfg, a1.tx_tipmov, a1.tx_nom, a1.tx_camAntEgrDetMovInv, a1.tx_camActEgrDetMovInv";
                            strSQL+="           , a1.tx_camAntIngDetMovInv, a1.tx_camActIngDetmovInv, a1.tx_camAntEgrInvBod";
                            strSQL+="           , a1.tx_camActEgrInvBod, a1.tx_camAntIngInvBod, a1.tx_camactinginvbod, a2.co_bod AS co_bodEmp, a3.co_empGrp, a3.co_bodGrp";
                            strSQL+="  	FROM tbm_cfgConInv AS a1 INNER JOIN (";
                            strSQL+="  					     tbm_cfgconinvtipdocbod AS a2 INNER JOIN tbr_bodempbodgrp AS a3";
                            strSQL+="  					     ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                            strSQL+="  					     )";
                            strSQL+="  	ON a1.co_cfg=a2.co_cfg";
                            strSQL+="   	WHERE a1.st_reg='A' AND a3.co_bodGrp=" + txtCodBodGrp.getText() + "";
                            strSQL+="  	AND a2.co_emp=" + intCodEmp + " AND a2.co_loc=" + intCodLoc + " AND a2.co_tipDoc=" + intCodTipDoc + "";
                            strSQL+=" ) AS d1";
                            strSQL+=" INNER JOIN(";
                            strSQL+="   		SELECT e1.st_merIngEgrFisBod, e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv";
                            strSQL+="                 , e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                            strSQL+="   		, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar";
                            strSQL+="   		, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocDocOri, e1.co_docDocOri, e1.ne_numDocOri";
                            strSQL+=" 		, e1.co_seg";
                            strSQL+="   		FROM(";
                            strSQL+="  			SELECT a2.st_merIngEgrFisBod, a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                            strSQL+="   			, a2.co_reg AS co_regDocOri, a2.co_itm, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen";
                            strSQL+="   			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.tx_desCor, a3.tx_desLar, a2.nd_canIngBod";
                            strSQL+="   			, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri";
                            strSQL+=" 			, a5.co_seg, a6.co_empRel";
                            strSQL+="   			FROM ((tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a5";
                            strSQL+=" 				ON a1.co_emp=a5.co_empRelCabMovInv AND a1.co_loc=a5.co_locRelCabMovInv AND a1.co_tipDoc=a5.co_tipDocRelCabMovInv AND a1.co_doc=a5.co_docRelCabMovInv";
                            strSQL+=" 				)";
                            strSQL+="   			INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                            strSQL+="                         INNER JOIN ((tbm_detMovInv AS a2";
                            strSQL+=" 					LEFT OUTER JOIN tbr_detMovInv AS a6";
                            strSQL+=" 					ON a2.co_emp=a6.co_empRel AND a2.co_loc=a6.co_locRel AND a2.co_tipDoc=a6.co_tipDocRel";
                            strSQL+=" 					AND a2.co_doc=a6.co_docRel AND a2.co_reg=a6.co_regRel";
                            strSQL+="                         )";
                            strSQL+=" 			INNER JOIN tbr_bodEmpBodGrp AS a4 ON a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod AND a4.co_bodGrp=" + txtCodBodGrp.getText() + ")";
                            strSQL+="   			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+="                           WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S' AND (a1.st_conInv IS NULL OR a1.st_conInv IN('P','E'))";
                            strSQL+="   			AND a2.nd_canPen<>0 AND a2.nd_can>0 AND a6.co_empRel IS NULL";
                            strSQL+="   			ORDER BY a1.ne_numDoc";
                            strSQL+="   		) AS e1";
                            strSQL+="   		GROUP BY e1.st_merIngEgrFisBod, e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv";
                            strSQL+="                 , e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                            strSQL+="   		, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar";
                            strSQL+="   		, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocDocOri, e1.co_docDocOri, e1.ne_numDocOri";
                            strSQL+=" 		, e1.co_seg";
                            strSQL+=" 		ORDER BY e1.co_tipDocDocOri";
                            strSQL+=" ) AS d2";
                            strSQL+=" ON d1.tx_tipMov=d2.tx_tipMov";
                            strSQL+=" WHERE d1.co_cfg IN(" + objUti.getStringValueAt(arlDatDocSql, a, INT_ARL_SQL_CNF_COD_CNF) + ")";//rstCab.getString("co_cfg")
                            strSQL+="" + strAuxTmp;
                            strSQL+=" GROUP BY d1.co_cfg, d1.tx_tipmov, d1.tx_nom, d1.tx_camAntIngDetMovInv, d1.tx_camActIngDetMovInv";
                            strSQL+=" , d1.co_bodEmp, d1.co_bodGrp, d2.tx_desCor, d2.tx_desLar, d2.fe_doc, d2.tx_tipMov";
                            strSQL+=" , d2.co_empDocOri, d2.co_locDocOri, d2.co_tipDocDocOri, d2.co_docDocOri, d2.ne_numDocOri";
                            //strSQL+=" , d2." + objUti.getStringValueAt(arlDatDocSql, a, INT_ARL_SQL_CNF_CAM_ANT_ING_DET_MOV_INV) + "";//rstCab.getString("tx_camAntIngDetMovInv")
                            strSQL+=" , d2.co_seg";
                            strSQL+=" ORDER BY d2.ne_numDocOri";
                            strSQL+=")";
                        }
                        strSQLUpd+=strSQL;
                        
                        
                        
                    }
                }
               
                    
                System.out.println("cargarDocCnf-2: " + strSQLUpd);
                rst=stm.executeQuery(strSQLUpd);
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,             "");
                        if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) ){//egreso
                            vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_empOrdDes"));
                            vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_locOrdDes"));
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDocOrdDes"));
                        }
                        else if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) ){//ingreso
                            vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_empDocOri"));
                            vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_locDocOri"));
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDocDocOri"));
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_COD_EMP,         "");
                            vecReg.add(INT_TBL_DAT_COD_LOC,         "");
                            vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "");
                        }
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                        if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) ){//egreso
                            vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_docOrdDes"));
                            vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numOrdDes"));
                        }
                        else if( (objParSis.getCodigoMenu()==4000) ||  (objParSis.getCodigoMenu()==4010) ){//ingreso
                            vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_docDocOri"));
                            vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDocOri"));
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_COD_DOC,         "");
                            vecReg.add(INT_TBL_DAT_NUM_DOC,         "");
                        }

                        vecReg.add(INT_TBL_DAT_MOT_TRA,         "");
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI_PRO,     rst.getObject("co_clides"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI_PRO,     rst.getObject("tx_nomclides"));
                        vecReg.add(INT_TBL_DAT_RUC_CLI_PRO,     rst.getObject("tx_rucclides"));
                        vecReg.add(INT_TBL_DAT_DIR_CLI_PRO,     rst.getObject("tx_dirclides"));
                        vecReg.add(INT_TBL_DAT_COD_BOD_EMP,     rst.getString("co_bodEmp"));
                        vecReg.add(INT_TBL_DAT_COD_BOD_GRP,     rst.getString("co_bodGrp"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_ORI,     rst.getString("co_empDocOri"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ORI,     rst.getString("co_locDocOri"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ORI, rst.getString("co_tipDocDocOri"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ORI,     rst.getString("co_docDocOri"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC_ORI,     rst.getString("ne_numDocOri"));
                        vecReg.add(INT_TBL_DAT_COD_CNF_INV,     rst.getString("co_cfg"));
                        vecReg.add(INT_TBL_DAT_TIP_MOV,         rst.getString("tx_tipmov"));
                        vecReg.add(INT_TBL_DAT_CAM_VAL_CNF,     strNomCamVal);
                        vecReg.add(INT_TBL_DAT_COD_SEG,         (rst.getObject("co_seg")==null?"-1":(rst.getString("co_seg").equals("")?"-1":rst.getString("co_seg"))));
                        
                        vecDat.add(vecReg);
                    }
                    
                    if(!txtNumOrdDes.getText().equals("")){
                        blnDocEnc=true;
                    }

                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Establecer el foco en el JTable sálo cuando haya datos.
                if (tblDat.getRowCount()>0){
                    tabFil.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                    if(blnDocEnc){
                        if( tblDat.getSelectedRow()!=-1 ){
                            intButPre=1;//se presiono aceptar            
                        }
                    }
                    else
                        intButPre=0;
                }
                
                pgrSis.setValue(0);
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                
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

    public int getBotonPresionado() {
        return intButPre;
    }

    public void setBotonPresionado(int intButPre) {
        this.intButPre = intButPre;
    }
    
    
    /**
     * Función que permite enviar los datos del documento seleccionado a la clase ZafCom94
     * @return ArrayList Arreglo con los datos del documento seleccionado
     */
    public void setDocCnfSel(){
        arlDatDocCnfSel.clear();
        try{            
            arlRegDocCnfSel=new ArrayList();
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_EMP, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_LOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_TIP_DOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_DOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC));
            arlRegDocCnfSel.add(INT_TBL_ARL_DES_COR_TIP_DOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_DES_COR_TIP_DOC));
            arlRegDocCnfSel.add(INT_TBL_ARL_DES_LAR_TIP_DOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_DES_LAR_TIP_DOC));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_CLI_PRO, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI_PRO));
            arlRegDocCnfSel.add(INT_TBL_ARL_NOM_CLI_PRO, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NOM_CLI_PRO));            
            arlRegDocCnfSel.add(INT_TBL_ARL_RUC_CLI_PRO, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_RUC_CLI_PRO));
            arlRegDocCnfSel.add(INT_TBL_ARL_NUM_DOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_DOC));            
            arlRegDocCnfSel.add(INT_TBL_ARL_FEC_DOC, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FEC_DOC));            
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_EMP_ORI, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP_ORI));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_LOC_ORI, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC_ORI));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_TIP_DOC_ORI, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC_ORI));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_DOC_ORI, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC_ORI));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_BOD_EMP, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_EMP));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_BOD_GRP, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_GRP));
            arlRegDocCnfSel.add(INT_TBL_ARL_TIP_MOV_DOC_ORI, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_TIP_MOV));
            arlRegDocCnfSel.add(INT_TBL_ARL_CAM_VAL_CNF, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAM_VAL_CNF));
            arlRegDocCnfSel.add(INT_TBL_ARL_COD_SEG, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SEG));
            
            arlDatDocCnfSel.add(arlRegDocCnfSel);            
            System.out.println("arlDatDocCnfSel: " + arlDatDocCnfSel.toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    public ArrayList getDocCnfSel(){
        return arlDatDocCnfSel;
    }
    

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    
 

    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDocCnf())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFil.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }

    public boolean isBlnDocEnc() {
        return blnDocEnc;
    }

    public void setBlnDocEnc(boolean blnDocEnc) {
        this.blnDocEnc = blnDocEnc;
    }
    

    
    

}
