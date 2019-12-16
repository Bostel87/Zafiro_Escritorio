/*
 * ZafCom94.java
 *
 * Created on July 26, 2016, 11:47 PM
 */

package Compras.ZafCom94;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import GenOD.ZafGenGuiRemPryTra;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafGenFacAut.ZafGenFacAut;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.awt.event.MouseEvent; //Bostel

/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom94 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN=0;                       //Línea
    private static final int INT_TBL_DAT_CHK=1;                       //Checkbox
    private static final int INT_TBL_DAT_COD_ITM_MAE=2;               //Codigo de item maestro
    private static final int INT_TBL_DAT_COD_ITM_GRP=3;               //Codigo de item grupo
    private static final int INT_TBL_DAT_COD_ITM_EMP=4;               //Codigo de item empresa
    private static final int INT_TBL_DAT_COD_ALT_ITM=5;               //Código alterno del item
    private static final int INT_TBL_DAT_COD_LET_ITM=6;               //Código en letras del item
    private static final int INT_TBL_DAT_NOM_ITM=7;                   //Nombre del item
    private static final int INT_TBL_DAT_UNI_MED_ITM=8;               //Unidad de medida item
    private static final int INT_TBL_DAT_CAN_DOC_ORI_CNF=9;           //Cantidad del documento a confirmar
    private static final int INT_TBL_DAT_CAN_TOT_CNF=10;              //Cantidad total confirmada
    private static final int INT_TBL_DAT_CAN_PUE_CNF=11;              //Cantidad que va a confirmar el usuario
    private static final int INT_TBL_DAT_CAN_CNF_USR=12;              //Cantidad que va a confirmar el usuario
    private static final int INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC=13;  //Cantidad que va a confirmar el usuario - nunca entregado/nunca recibido
    private static final int INT_TBL_DAT_OBS=14;                      //Observación
    private static final int INT_TBL_DAT_EST_ING_EGR_MER_BOD=15;      //Estado del item de ingreso/egreso a bodega       
    private static final int INT_TBL_DAT_COD_EMP_ORD_DES=16;
    private static final int INT_TBL_DAT_COD_LOC_ORD_DES=17;
    private static final int INT_TBL_DAT_COD_TIP_DOC_ORD_DES=18;
    private static final int INT_TBL_DAT_COD_DOC_ORD_DES=19;
    private static final int INT_TBL_DAT_COD_REG_ORD_DES=20;
    private static final int INT_TBL_DAT_COD_EMP_DOC_ORI=21;
    private static final int INT_TBL_DAT_COD_LOC_DOC_ORI=22;
    private static final int INT_TBL_DAT_COD_TIP_DOC_ORI=23;
    private static final int INT_TBL_DAT_COD_DOC_ORI=24;
    private static final int INT_TBL_DAT_COD_REG_DOC_ORI=25;
    private static final int INT_TBL_DAT_CAN_PUE_CNF_TIE_EJE=26;
    
    private ArrayList arlRegTbl, arlDatTbl;//se usa para cargar los datos del documento seleccionado, carga todos los items, cuando el el usuario digita codigoLetras, se saca de este arreglo y carga en la tabla
    private static final int INT_ARL_TBL_DAT_COD_ITM_MAE=0;              //Codigo de item maestro
    private static final int INT_ARL_TBL_DAT_COD_ITM_GRP=1;              //Codigo de item grupo
    private static final int INT_ARL_TBL_DAT_COD_ITM_EMP=2;              //Codigo de item empresa
    private static final int INT_ARL_TBL_DAT_COD_ALT_ITM=3;              //Código alterno del item
    private static final int INT_ARL_TBL_DAT_COD_LET_ITM=4;              //Código en letras del item
    private static final int INT_ARL_TBL_DAT_NOM_ITM=5;                  //Nombre del item
    private static final int INT_ARL_TBL_DAT_UNI_MED_ITM=6;              //Unidad de medida item
    private static final int INT_ARL_TBL_DAT_CAN_DOC_ORI_CNF=7;          //Cantidad del documento a confirmar
    private static final int INT_ARL_TBL_DAT_CAN_TOT_CNF=8;              //Cantidad total confirmada
    private static final int INT_ARL_TBL_DAT_CAN_PUE_CNF=9;              //Cantidad que va a confirmar el usuario
    private static final int INT_ARL_TBL_DAT_CAN_CNF_USR=10;             //Cantidad que va a confirmar el usuario
    private static final int INT_ARL_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC=11; //Cantidad que va a confirmar el usuario - nunca entregado/nunca recibido
    private static final int INT_ARL_TBL_DAT_OBS=12;                     //Observación
    private static final int INT_ARL_TBL_DAT_EST_ING_EGR_MER_BOD=13;     //Estado del item de ingreso/egreso a bodega       
    private static final int INT_ARL_TBL_DAT_COD_EMP_ORD_DES=14;
    private static final int INT_ARL_TBL_DAT_COD_LOC_ORD_DES=15;
    private static final int INT_ARL_TBL_DAT_COD_TIP_DOC_ORD_DES=16;
    private static final int INT_ARL_TBL_DAT_COD_DOC_ORD_DES=17;
    private static final int INT_ARL_TBL_DAT_COD_REG_ORD_DES=18;
    private static final int INT_ARL_TBL_DAT_COD_EMP_DOC_ORI=19;
    private static final int INT_ARL_TBL_DAT_COD_LOC_DOC_ORI=20;
    private static final int INT_ARL_TBL_DAT_COD_TIP_DOC_ORI=21;
    private static final int INT_ARL_TBL_DAT_COD_DOC_ORI=22;
    private static final int INT_ARL_TBL_DAT_COD_REG_DOC_ORI=23;
    private static final int INT_ARL_TBL_DAT_EST_ITM_SEL_USR_CNF=24;
    
    //Lo que se obtiene de ZafCom94_01 - arreglo
    private ArrayList arlDatDocCnfSel;
    private static final int INT_TBL_ARL_COD_EMP=0;//OD
    private static final int INT_TBL_ARL_COD_LOC=1;//OD
    private static final int INT_TBL_ARL_COD_TIP_DOC=2;//OD
    private static final int INT_TBL_ARL_COD_DOC=3;//OD
    private static final int INT_TBL_ARL_DES_COR_TIP_DOC=4;//OD
    private static final int INT_TBL_ARL_DES_LAR_TIP_DOC=5;//OD    
    private static final int INT_TBL_ARL_COD_CLI_PRO=6;//OD   
    private static final int INT_TBL_ARL_NOM_CLI_PRO=7;//OD    
    private static final int INT_TBL_ARL_RUC_CLI_PRO=8;//OD    
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
    
    //Lo que se envia a la clase de ZafCnfDoc
    private ArrayList arlRegCnf, arlDatCnf;
    private static final int INT_ARL_DAT_COD_EMP_CNF=0;
    private static final int INT_ARL_DAT_COD_LOC_CNF=1;
    private static final int INT_ARL_DAT_COD_TIP_DOC_CNF=2;
    private static final int INT_ARL_DAT_COD_DOC_CNF=3;
    private static final int INT_ARL_DAT_COD_REG_CNF=4;
    private static final int INT_ARL_DAT_COD_ITM_GRP_CNF=5;
    private static final int INT_ARL_DAT_COD_ITM_EMP_CNF=6;
    private static final int INT_ARL_DAT_COD_ITM_MAE_CNF=7;
    private static final int INT_ARL_DAT_CAN_ITM_CNF=8;
    private static final int INT_ARL_DAT_COD_EMP_ORI=9;
    private static final int INT_ARL_DAT_COD_LOC_ORI=10;
    private static final int INT_ARL_DAT_COD_TIP_DOC_ORI=11;
    private static final int INT_ARL_DAT_COD_DOC_ORI=12;
    private static final int INT_ARL_DAT_COD_REG_ORI=13;    
    
    //ArrayList para consultar.
    private ArrayList arlDatConCnf, arlRegConCnf;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceConCnf=0;
    
    //ArrayList para Confirmación.
    private ArrayList arlRegCanPenCnf, arlDatCanPenCnf;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_MAE=0;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_GRP=1;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_EMP=2;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_LET=3;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_CAN_PEN_CNF=4;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_EST_CNF=5;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_EST_PRO=6;
    private static final int INT_ARL_AFT_INS_CAN_PEN_CNF_COD_REG_ITM=7;
    
    //Códigos de Menú de Confirmación.
    private static final int INT_COD_MNU_COINBO=4010;
    private static final int INT_COD_MNU_COINDE=4000;
    private static final int INT_COD_MNU_COEGBO=4041;
    private static final int INT_COD_MNU_COEGDE=4051;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafCnfDoc objCnfDoc;
    private ZafSegMovInv objSegMovInv;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblMod objTblMod;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtObs, objTblCelEdiTxtItmLet;            //Editor: JTextField en celda.
    private MiToolBar objTooBar;
    private ZafDocLis objDocLis;
    private ZafTblPopMnu objTblPopMnu;
    private ZafVenCon vcoVeh, vcoCho;
    private ZafVenCon vcoTipDoc, vcoBod;
    private ZafGenGuiRemPryTra objGenGuiRem;
    private ZafGenFacAut objGenFacAut;
    private GenOD.ZafGuiRem objImpGuiRem;
    private ZafCfgSer objCfgSer;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafCom94_01 objCom94_01;
    private ZafRptSis objRptSis;
    private ZafDatePicker dtpFecDoc;                     //Fecha del documento.
    private java.util.Date datFecAux;
    private Date datFecDoc;   
    private boolean blnMarTodCanTblDat = true;          //Coloca cantidades en celdas Bostel
    private Vector vecDat, vecCab, vecReg, vecAux;
       
    private boolean blnHayCam;
    private boolean blnChkBoxCanSel;//true si es por cancelación para saber que ese item debe ser procesado
    private boolean blnPruebas=false;      // blnPruebas= false para pasar a produccion.
    
    //Lo que se obtiene de ZafCom94_01 - variables
    private int intCodEmpDocCnf;
    private int intCodLocDocCnf;
    private int intCodTipDocCnf;
    private int intCodDocCnf;
    private int intCodEmpDocOri;
    private int intCodLocDocOri;
    private int intCodTipDocOri;
    private int intCodDocOri;
    private int intCodBodEmp;
    private int intCodBodGrp;
    
    //solicitud
    private int intCodEmpSol;
    private int intCodLocSol;
    private int intCodTipDocSol;
    private int intCodDocSol;
    
    private int intCodEmp, intCodLoc;
    private int intCodSegRef;
    private int intSig=1;   
    
    private String strSQL, strAux;
    private String strDesCorVeh, strDesLarVeh, strPesVeh;
    private String strDesCorCho, strCodCho, strDesLarCho;   
    private String strNumDoc;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;
    private String strTipMovDocOri;//E,I,V,R
    private String strCodBod, strNomBod;
    private String strCodLocPre;
    private String strTipMovCnfTot;//I:Ingresos, E:Egresos
    private String strCamValCnf;
    
    private String strVer=" v0.1.31.7";
    
    /** Creates new form ZafCom94 */
    public ZafCom94(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
                initComponents();
                configurarFrm();
                agregarDocLis();
                objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                objTooBar.setVisibleModificar(false);
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleEliminar(false);
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                arlDatDocCnfSel=new ArrayList();
                arlDatCnf=new ArrayList();
                arlDatCanPenCnf=new ArrayList();
                objSegMovInv=new ZafSegMovInv(objParSis, this);
                objGenGuiRem=new ZafGenGuiRemPryTra();
                arlDatTbl=new ArrayList();
                
                if( (objParSis.getCodigoMenu()==4000) || (objParSis.getCodigoMenu()==4010) ){//Ingreso
                    strTipMovCnfTot="I";
                }
                else if( (objParSis.getCodigoMenu()==4051) || (objParSis.getCodigoMenu()==4041) ){//Egreso
                    strTipMovCnfTot="E";
                }
                blnChkBoxCanSel=false;
                
                objGenFacAut=new ZafGenFacAut(objParSis, this);
                objCom94_01=null;
                
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public ZafCom94(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento) {
        try{
            objParSis=(ZafParSis)obj.clone();
            //objParSis.setCodigoMenu();
            initComponents();
            configurarFrm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            blnChkBoxCanSel=false;
            objCom94_01=null;
            arlDatCanPenCnf=new ArrayList();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }


    
    public ZafCom94(ZafParSis obj, int codEmpCnf, int codLocCnf, int codTipDocCnf, int codDocCnf
                    , int codEmpOri, int codLocOri, int codTipDocOri, int codDocOri, String tipMovDocOri
                    , int codBodEmp, int codBodGrp, String tipCnfIngEgr
                    , int codEmpSol, int codLocSol, int codTipDocSol, int codDocSol
                    , ArrayList arreglo) {
        try{
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            configurarFrm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            txtCodTipDoc.setText(""+codTipDocCnf);
            objTooBar.setEstado('n');
            objTooBar.clickInsertar();
            objTooBar.beforeInsertar();
            objTooBar.insertar();
            objTooBar.afterInsertar();            
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            strTipMovCnfTot=tipCnfIngEgr;
            
            intCodEmpDocCnf=codEmpCnf;
            intCodLocDocCnf=codLocCnf;
            intCodTipDocCnf=codTipDocCnf;
            intCodDocCnf=codDocCnf;
            intCodEmpDocOri=codEmpOri;
            intCodLocDocOri=codLocOri;
            intCodTipDocOri=codTipDocOri;
            intCodDocOri=codDocOri;
            intCodBodEmp=codBodEmp;
            intCodBodGrp=codBodGrp;
            strTipMovDocOri=tipMovDocOri;
            
            intCodEmpSol=codEmpSol;
            intCodLocSol=codLocSol;
            intCodTipDocSol=codTipDocSol;
            intCodDocSol=codDocSol;
            arlDatTbl=new ArrayList();
            arlDatTbl.clear();
            blnChkBoxCanSel=true;
            arlDatTbl=arreglo;
            objCom94_01=null;
            arlDatCanPenCnf=new ArrayList();
            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    public ZafCom94(ZafParSis obj, String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, String codigoDocumento, String natDoc, int codigoBodGrp, int intCodMnuConf) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            configurarFrm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            intCodEmpDocCnf=Integer.parseInt(codigoEmpresa);
            intCodLocDocCnf=Integer.parseInt(codigoLocal);
            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            strTipMovCnfTot=natDoc;
            txtCodBodGrp.setText(""+codigoBodGrp);
            objParSis.setCodigoMenu(intCodMnuConf);
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);

            blnChkBoxCanSel=false;
            objCom94_01=null;
            arlDatCanPenCnf=new ArrayList();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGrl = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblPrv4 = new javax.swing.JLabel();
        txtCodBodGrp = new javax.swing.JTextField();
        txtNomBodGrp = new javax.swing.JTextField();
        butBodGrp = new javax.swing.JButton();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panDocCnf = new javax.swing.JPanel();
        panCabDatDocCnf = new javax.swing.JPanel();
        lblTipdoc = new javax.swing.JLabel();
        lblComPro = new javax.swing.JLabel();
        lblVenCom = new javax.swing.JLabel();
        txtCodForRet = new javax.swing.JTextField();
        txtCodVenCom = new javax.swing.JTextField();
        txtDesCorTipDocCon = new javax.swing.JTextField();
        txtDesLarTipDocCon = new javax.swing.JTextField();
        txtCodCliPro = new javax.swing.JTextField();
        txtNomCliPro = new javax.swing.JTextField();
        txtNomVenCom = new javax.swing.JTextField();
        txtDesForRet = new javax.swing.JTextField();
        lblNumGuiDet = new javax.swing.JLabel();
        lblFecDocCon = new javax.swing.JLabel();
        txtNumDocCon = new javax.swing.JTextField();
        txtFecDocCon = new javax.swing.JTextField();
        butBusNumDocCon = new javax.swing.JButton();
        txtCodDocCon = new javax.swing.JTextField();
        lblCodDocCon1 = new javax.swing.JLabel();
        butBusOrdDes = new javax.swing.JButton();
        lblNumDocCon1 = new javax.swing.JLabel();
        txtNumOrdDes = new javax.swing.JTextField();
        lblNumGuiDes = new javax.swing.JLabel();
        lblForRet1 = new javax.swing.JLabel();
        txtMotTra = new javax.swing.JTextField();
        lblMotTra = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panDatDes = new javax.swing.JPanel();
        optEnv = new javax.swing.JRadioButton();
        lblVeh = new javax.swing.JLabel();
        txtCodVeh = new javax.swing.JTextField();
        txtPesVeh = new javax.swing.JTextField();
        txtDesCorVeh = new javax.swing.JTextField();
        txtDesLarVeh = new javax.swing.JTextField();
        butVeh = new javax.swing.JButton();
        lblCho = new javax.swing.JLabel();
        txtCodCho = new javax.swing.JTextField();
        txtDesCorCho = new javax.swing.JTextField();
        txtDesLarCho = new javax.swing.JTextField();
        butCho = new javax.swing.JButton();
        optRet = new javax.swing.JRadioButton();
        lblPla = new javax.swing.JLabel();
        optVehCli = new javax.swing.JRadioButton();
        optVehTra = new javax.swing.JRadioButton();
        txtPlaVehTra = new javax.swing.JTextField();
        txtIdeTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        lblIde = new javax.swing.JLabel();
        lblRso = new javax.swing.JLabel();
        txtRucCliPro = new javax.swing.JTextField();
        panBar = new javax.swing.JPanel();

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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);

        panGrl.setAutoscrolls(true);
        panGrl.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panCab.setLayout(null);

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
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 4, 78, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 125, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(130, 44, 79, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(98, 4, 32, 20);

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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(208, 4, 230, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(4, 44, 125, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 125, 20);

        lblNumDoc1.setText("Número de documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(460, 24, 125, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(584, 24, 100, 20);

        lblPrv4.setText("Bodega:");
        lblPrv4.setToolTipText("Proveedor");
        panCab.add(lblPrv4);
        lblPrv4.setBounds(4, 24, 125, 20);

        txtCodBodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodGrpFocusLost(evt);
            }
        });
        txtCodBodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodGrpActionPerformed(evt);
            }
        });
        panCab.add(txtCodBodGrp);
        txtCodBodGrp.setBounds(130, 24, 78, 20);

        txtNomBodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodGrpFocusLost(evt);
            }
        });
        txtNomBodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodGrpActionPerformed(evt);
            }
        });
        panCab.add(txtNomBodGrp);
        txtNomBodGrp.setBounds(208, 24, 230, 20);

        butBodGrp.setText("...");
        butBodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodGrpActionPerformed(evt);
            }
        });
        panCab.add(butBodGrp);
        butBodGrp.setBounds(438, 24, 20, 20);

        panGrl.add(panCab, java.awt.BorderLayout.NORTH);

        panObs.setPreferredSize(new java.awt.Dimension(100, 62));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGrl.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGrl);

        panDocCnf.setLayout(new java.awt.BorderLayout());

        panCabDatDocCnf.setPreferredSize(new java.awt.Dimension(100, 120));
        panCabDatDocCnf.setLayout(null);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipdoc.setText("Tipo de Documento:");
        panCabDatDocCnf.add(lblTipdoc);
        lblTipdoc.setBounds(10, 6, 120, 20);

        lblComPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblComPro.setText("Cliente/Proveedor:");
        panCabDatDocCnf.add(lblComPro);
        lblComPro.setBounds(10, 26, 120, 20);

        lblVenCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVenCom.setText("Vendedor/Comprador:");
        panCabDatDocCnf.add(lblVenCom);
        lblVenCom.setBounds(10, 46, 120, 20);

        txtCodForRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodForRet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodForRetKeyPressed(evt);
            }
        });
        panCabDatDocCnf.add(txtCodForRet);
        txtCodForRet.setBounds(130, 66, 60, 20);

        txtCodVenCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabDatDocCnf.add(txtCodVenCom);
        txtCodVenCom.setBounds(130, 46, 60, 20);

        txtDesCorTipDocCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCorTipDocCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocConActionPerformed(evt);
            }
        });
        panCabDatDocCnf.add(txtDesCorTipDocCon);
        txtDesCorTipDocCon.setBounds(130, 6, 60, 20);

        txtDesLarTipDocCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabDatDocCnf.add(txtDesLarTipDocCon);
        txtDesLarTipDocCon.setBounds(190, 6, 250, 20);

        txtCodCliPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabDatDocCnf.add(txtCodCliPro);
        txtCodCliPro.setBounds(130, 26, 60, 20);

        txtNomCliPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabDatDocCnf.add(txtNomCliPro);
        txtNomCliPro.setBounds(190, 26, 250, 20);

        txtNomVenCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabDatDocCnf.add(txtNomVenCom);
        txtNomVenCom.setBounds(190, 46, 250, 20);

        txtDesForRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCabDatDocCnf.add(txtDesForRet);
        txtDesForRet.setBounds(190, 66, 250, 20);

        lblNumGuiDet.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblNumGuiDet.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumGuiDet.setRequestFocusEnabled(false);
        lblNumGuiDet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panCabDatDocCnf.add(lblNumGuiDet);
        lblNumGuiDet.setBounds(130, 88, 310, 18);

        lblFecDocCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDocCon.setText("Fecha del Documento:");
        panCabDatDocCnf.add(lblFecDocCon);
        lblFecDocCon.setBounds(450, 6, 120, 20);
        panCabDatDocCnf.add(txtNumDocCon);
        txtNumDocCon.setBounds(570, 26, 90, 20);
        panCabDatDocCnf.add(txtFecDocCon);
        txtFecDocCon.setBounds(570, 6, 90, 20);

        butBusNumDocCon.setText("jButton1");
        butBusNumDocCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusNumDocCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusNumDocConActionPerformed(evt);
            }
        });
        panCabDatDocCnf.add(butBusNumDocCon);
        butBusNumDocCon.setBounds(660, 26, 20, 20);
        panCabDatDocCnf.add(txtCodDocCon);
        txtCodDocCon.setBounds(570, 46, 90, 20);

        lblCodDocCon1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDocCon1.setText("Código del Documento:");
        panCabDatDocCnf.add(lblCodDocCon1);
        lblCodDocCon1.setBounds(450, 46, 120, 20);

        butBusOrdDes.setText("jButton1");
        butBusOrdDes.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusOrdDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusOrdDesActionPerformed(evt);
            }
        });
        butBusOrdDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                butBusOrdDesFocusGained(evt);
            }
        });
        panCabDatDocCnf.add(butBusOrdDes);
        butBusOrdDes.setBounds(660, 67, 20, 20);

        lblNumDocCon1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDocCon1.setText("Número de Documento:");
        panCabDatDocCnf.add(lblNumDocCon1);
        lblNumDocCon1.setBounds(450, 26, 120, 20);

        txtNumOrdDes.setToolTipText("Campo inhabilitado - De click en el botón para cargar la información");
        txtNumOrdDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumOrdDesFocusGained(evt);
            }
        });
        txtNumOrdDes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumOrdDesKeyPressed(evt);
            }
        });
        panCabDatDocCnf.add(txtNumOrdDes);
        txtNumOrdDes.setBounds(570, 67, 90, 20);

        lblNumGuiDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumGuiDes.setText("# Documento confirmar:");
        panCabDatDocCnf.add(lblNumGuiDes);
        lblNumGuiDes.setBounds(450, 67, 120, 20);

        lblForRet1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblForRet1.setText("Forma de retiro:");
        panCabDatDocCnf.add(lblForRet1);
        lblForRet1.setBounds(10, 66, 120, 20);

        txtMotTra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMotTra.setForeground(new java.awt.Color(255, 0, 0));
        txtMotTra.setBorder(null);
        panCabDatDocCnf.add(txtMotTra);
        txtMotTra.setBounds(570, 88, 110, 18);

        lblMotTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMotTra.setText("Motivo transacción:");
        panCabDatDocCnf.add(lblMotTra);
        lblMotTra.setBounds(450, 86, 120, 20);

        panDocCnf.add(panCabDatDocCnf, java.awt.BorderLayout.NORTH);

        panDet.setAutoscrolls(true);
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
        jScrollPane1.setViewportView(tblDat);

        panDet.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panDocCnf.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Datos documento confirmar", panDocCnf);

        panDatDes.setLayout(null);

        optEnv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optEnv.setLabel("La mercadería se envía al cliente en uno de nuestros vehículos");
        optEnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEnvActionPerformed(evt);
            }
        });
        panDatDes.add(optEnv);
        optEnv.setBounds(10, 10, 400, 20);

        lblVeh.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVeh.setText("Vehículo:");
        panDatDes.add(lblVeh);
        lblVeh.setBounds(30, 40, 60, 20);
        panDatDes.add(txtCodVeh);
        txtCodVeh.setBounds(90, 40, 20, 20);
        panDatDes.add(txtPesVeh);
        txtPesVeh.setBounds(110, 40, 40, 20);

        txtDesCorVeh.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCorVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorVehActionPerformed(evt);
            }
        });
        txtDesCorVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorVehFocusLost(evt);
            }
        });
        panDatDes.add(txtDesCorVeh);
        txtDesCorVeh.setBounds(150, 40, 70, 20);

        txtDesLarVeh.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtDesLarVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarVehFocusLost(evt);
            }
        });
        txtDesLarVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarVehActionPerformed(evt);
            }
        });
        panDatDes.add(txtDesLarVeh);
        txtDesLarVeh.setBounds(220, 40, 420, 20);

        butVeh.setText("...");
        butVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVehActionPerformed(evt);
            }
        });
        panDatDes.add(butVeh);
        butVeh.setBounds(640, 40, 20, 20);

        lblCho.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCho.setText("Chofer:");
        panDatDes.add(lblCho);
        lblCho.setBounds(30, 60, 60, 20);
        panDatDes.add(txtCodCho);
        txtCodCho.setBounds(90, 60, 60, 20);

        txtDesCorCho.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesCorCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorChoActionPerformed(evt);
            }
        });
        txtDesCorCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorChoFocusLost(evt);
            }
        });
        panDatDes.add(txtDesCorCho);
        txtDesCorCho.setBounds(150, 60, 70, 20);

        txtDesLarCho.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesLarCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarChoActionPerformed(evt);
            }
        });
        txtDesLarCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarChoFocusLost(evt);
            }
        });
        panDatDes.add(txtDesLarCho);
        txtDesLarCho.setBounds(220, 60, 420, 20);

        butCho.setText("...");
        butCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butChoActionPerformed(evt);
            }
        });
        panDatDes.add(butCho);
        butCho.setBounds(640, 60, 20, 20);

        optRet.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optRet.setText("El cliente retira la mercadería"); // NOI18N
        optRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optRetActionPerformed(evt);
            }
        });
        panDatDes.add(optRet);
        optRet.setBounds(10, 90, 400, 20);

        lblPla.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPla.setText("Placa:");
        panDatDes.add(lblPla);
        lblPla.setBounds(40, 190, 100, 20);

        optVehCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optVehCli.setText("Vehículo propio");
        optVehCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optVehCliActionPerformed(evt);
            }
        });
        panDatDes.add(optVehCli);
        optVehCli.setBounds(30, 120, 180, 20);

        optVehTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optVehTra.setText("Transportista");
        optVehTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optVehTraActionPerformed(evt);
            }
        });
        panDatDes.add(optVehTra);
        optVehTra.setBounds(210, 120, 148, 20);

        txtPlaVehTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlaVehTraActionPerformed(evt);
            }
        });
        panDatDes.add(txtPlaVehTra);
        txtPlaVehTra.setBounds(150, 190, 100, 20);

        txtIdeTra.setToolTipText("Identificación del cliente/proveedor");
        txtIdeTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeTraActionPerformed(evt);
            }
        });
        txtIdeTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeTraFocusGained(evt);
            }
        });
        panDatDes.add(txtIdeTra);
        txtIdeTra.setBounds(150, 150, 150, 20);

        txtNomTra.setToolTipText("Nombre del cliente/proveedor");
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
        });
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panDatDes.add(txtNomTra);
        txtNomTra.setBounds(150, 170, 330, 20);

        lblIde.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIde.setText("Identificación:");
        panDatDes.add(lblIde);
        lblIde.setBounds(40, 150, 100, 20);

        lblRso.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRso.setText("Razón social:");
        panDatDes.add(lblRso);
        lblRso.setBounds(40, 170, 100, 20);
        panDatDes.add(txtRucCliPro);
        txtRucCliPro.setBounds(360, 148, 170, 20);

        tabFrm.addTab("Datos despacho", panDatDes);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
}//GEN-LAST:event_exitForm

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc=txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
        if (txtDesCorTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(1);
        }
    } else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
        if (txtDesLarTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(2);
        }
    } else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
    strNumDoc=txtNumDoc.getText();
    txtNumDoc.selectAll();
}//GEN-LAST:event_txtNumDocFocusGained

private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNumDoc.getText().equalsIgnoreCase(strNumDoc)) {
    }
}//GEN-LAST:event_txtNumDocFocusLost

private void txtCodBodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodGrpActionPerformed
    txtCodBodGrp.transferFocus();
}//GEN-LAST:event_txtCodBodGrpActionPerformed

private void txtCodBodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodGrpFocusGained
        strCodBod=txtCodBodGrp.getText();
        txtCodBodGrp.selectAll();
}//GEN-LAST:event_txtCodBodGrpFocusGained

private void txtCodBodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodGrpFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBodGrp.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBodGrp.getText().equals("")) {
                txtCodBodGrp.setText("");
                txtNomBodGrp.setText("");
            } else {
                mostrarVenConBod(1);
            }
        } else
            txtCodBodGrp.setText(strCodBod);
}//GEN-LAST:event_txtCodBodGrpFocusLost

private void txtNomBodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodGrpActionPerformed
    txtNomBodGrp.transferFocus();
}//GEN-LAST:event_txtNomBodGrpActionPerformed

private void txtNomBodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodGrpFocusGained
        strNomBod=txtNomBodGrp.getText();
        txtNomBodGrp.selectAll();
}//GEN-LAST:event_txtNomBodGrpFocusGained

private void txtNomBodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodGrpFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomBodGrp.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBodGrp.getText().equals("")) {
                txtCodBodGrp.setText("");
                txtNomBodGrp.setText("");
            } else {
                mostrarVenConBod(2);
            }
        }
        else
            txtNomBodGrp.setText(strNomBod);
}//GEN-LAST:event_txtNomBodGrpFocusLost

private void butBodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodGrpActionPerformed
    mostrarVenConBod(0);
}//GEN-LAST:event_butBodGrpActionPerformed

    private void optEnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEnvActionPerformed
        if (optEnv.isSelected()) {
            optRet.setSelected(false);
            optVehCli.setSelected(false);
            optVehTra.setSelected(false);            
            txtIdeTra.setText("");
            txtIdeTra.setEditable(false);
            txtNomTra.setText("");
            txtNomTra.setEditable(false);
            txtPlaVehTra.setText("");
            txtPlaVehTra.setEditable(false);
            //del bloque seleccionado - igual se setean
            txtCodVeh.setText("");
            txtCodVeh.setEditable(true);
            txtPesVeh.setText("");
            txtPesVeh.setEditable(true);
            txtDesCorVeh.setText("");
            txtDesCorVeh.setEditable(true);
            txtDesLarVeh.setText("");
            txtDesLarVeh.setEditable(true);
            butVeh.setEnabled(true);
            txtCodCho.setText("");
            txtCodCho.setEditable(true);
            txtDesCorCho.setText("");
            txtDesCorCho.setEditable(true);
            txtDesLarCho.setText("");
            txtDesLarCho.setEditable(true);
            butCho.setEnabled(true);
        }        
    }//GEN-LAST:event_optEnvActionPerformed

    
    private void optRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optRetActionPerformed
        if (optRet.isSelected()) {
            optEnv.setSelected(false);
            txtCodVeh.setText("");
            txtCodVeh.setEditable(false);
            txtPesVeh.setText("");
            txtPesVeh.setEditable(false);
            txtDesCorVeh.setText("");
            txtDesCorVeh.setEditable(false);
            txtDesLarVeh.setText("");
            txtDesLarVeh.setEditable(false);
            butVeh.setEnabled(false);
            txtCodCho.setText("");
            txtCodCho.setEditable(false);
            txtDesCorCho.setText("");
            txtDesCorCho.setEditable(false);
            txtDesCorCho.setText("");
            txtDesCorCho.setEditable(false);
            txtDesLarCho.setText("");
            txtDesLarCho.setEditable(false);
            txtDesLarCho.setText("");
            txtDesLarCho.setEditable(false);
            
            butCho.setEnabled(false);
            //del bloque seleccionado - igual se setean
            optVehCli.setSelected(false);
            optVehTra.setSelected(false);
            
            txtIdeTra.setText("");
            txtIdeTra.setEditable(true);
            txtNomTra.setText("");
            txtNomTra.setEditable(true);
            txtPlaVehTra.setText("");
            txtPlaVehTra.setEditable(true);
        }
    }//GEN-LAST:event_optRetActionPerformed

    private void txtDesCorVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorVehFocusGained
        strDesCorVeh = txtDesCorVeh.getText();
        txtDesCorVeh.selectAll();
    }//GEN-LAST:event_txtDesCorVehFocusGained

    private void txtDesCorVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorVehFocusLost
        if (!txtDesCorVeh.getText().equalsIgnoreCase(strDesCorVeh)) {
            if (txtDesCorVeh.getText().equals("")) {
                txtCodVeh.setText("");
                txtDesCorVeh.setText("");
                txtDesLarVeh.setText("");
                txtPesVeh.setText("");
                txtCodCho.setText("");
                txtDesCorCho.setText("");
                txtDesLarCho.setText("");
            } 
            else{
                mostrarVenConVeh(1);
            }
        } 
        else{
            txtDesCorVeh.setText(strDesCorVeh);
        }
    }//GEN-LAST:event_txtDesCorVehFocusLost

    private void txtDesCorVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorVehActionPerformed
        txtDesCorVeh.transferFocus();
    }//GEN-LAST:event_txtDesCorVehActionPerformed

    private void txtDesLarVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVehFocusGained
        strDesLarVeh = txtDesLarVeh.getText();
        txtDesLarVeh.selectAll();
    }//GEN-LAST:event_txtDesLarVehFocusGained

    private void txtDesLarVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVehFocusLost
        if (!txtDesLarVeh.getText().equalsIgnoreCase(strDesLarVeh)) {
            if (txtDesLarVeh.getText().equals("")) {
                txtCodVeh.setText("");
                txtDesCorVeh.setText("");
                txtDesLarVeh.setText("");
                txtPesVeh.setText("");
                txtCodCho.setText("");
                txtDesCorCho.setText("");
                txtDesLarCho.setText("");
            } 
            else{
                mostrarVenConVeh(2);
            }
        } 
        else{
            txtDesLarVeh.setText(strDesLarVeh);
        }
    }//GEN-LAST:event_txtDesLarVehFocusLost

    private void txtDesLarVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarVehActionPerformed
        txtDesLarVeh.transferFocus();
    }//GEN-LAST:event_txtDesLarVehActionPerformed

    private void butVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVehActionPerformed
        vcoVeh.setTitle("Listado de Vehículos");
        vcoVeh.setCampoBusqueda(1);
        vcoVeh.show();
        if (vcoVeh.getSelectedButton() == vcoVeh.INT_BUT_ACE) {
            txtCodVeh.setText(vcoVeh.getValueAt(1));
            txtDesCorVeh.setText(vcoVeh.getValueAt(2));
            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
            txtPesVeh.setText(vcoVeh.getValueAt(4));
            txtCodCho.setText(vcoVeh.getValueAt(5));
            txtDesCorCho.setText(vcoVeh.getValueAt(6));
            txtDesLarCho.setText(vcoVeh.getValueAt(7));
            strDesCorVeh = vcoVeh.getValueAt(1);
            strPesVeh = vcoVeh.getValueAt(4);
            strCodCho = vcoVeh.getValueAt(5);
            optEnv.setSelected(true);
        }
    }//GEN-LAST:event_butVehActionPerformed

    private void txtDesCorChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorChoFocusGained
        strDesCorCho = txtDesCorCho.getText();
        txtDesCorCho.selectAll();
    }//GEN-LAST:event_txtDesCorChoFocusGained

    private void txtDesCorChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorChoFocusLost
        if (!txtDesCorCho.getText().equalsIgnoreCase(strDesCorCho)) {
            if (txtDesCorCho.getText().equals("")) {
                txtCodCho.setText("");
                txtDesCorCho.setText("");
                txtDesLarCho.setText("");
            } else {
                mostrarVenConCho(1);
            }
        } else {
            txtDesCorCho.setText(strDesCorCho);
        }
    }//GEN-LAST:event_txtDesCorChoFocusLost

    private void txtDesCorChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorChoActionPerformed
        txtDesCorCho.transferFocus();
    }//GEN-LAST:event_txtDesCorChoActionPerformed

    private void txtDesLarChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarChoFocusGained
        strDesLarCho = txtDesLarCho.getText();
        txtDesLarCho.selectAll();
    }//GEN-LAST:event_txtDesLarChoFocusGained

    private void txtDesLarChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarChoFocusLost
        if (!txtDesLarCho.getText().equalsIgnoreCase(strDesLarCho)) {
            if (txtDesLarCho.getText().equals("")) {
                txtCodCho.setText("");
                txtDesCorCho.setText("");
                txtDesLarCho.setText("");
            } else {
                mostrarVenConCho(2);
            }
        } else {
            txtDesLarCho.setText(strDesLarCho);
        }
    }//GEN-LAST:event_txtDesLarChoFocusLost

    private void txtDesLarChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarChoActionPerformed
        txtDesLarCho.transferFocus();
    }//GEN-LAST:event_txtDesLarChoActionPerformed

    private void butChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butChoActionPerformed
        vcoCho.setTitle("Listado de Choferes");
        vcoCho.setCampoBusqueda(2);
        vcoCho.show();
        if (vcoCho.getSelectedButton() == vcoCho.INT_BUT_ACE) {
            txtCodCho.setText(vcoCho.getValueAt(1));
            txtDesCorCho.setText(vcoCho.getValueAt(2));
            txtDesLarCho.setText(vcoCho.getValueAt(3));
            optEnv.setSelected(true);
        }
    }//GEN-LAST:event_butChoActionPerformed

    private void optVehCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optVehCliActionPerformed
        if (optVehCli.isSelected()) 
        {
            txtDesCorVeh.setText("");
            txtDesLarVeh.setText("");
            txtDesCorCho.setText("");
            txtDesLarCho.setText("");
            txtNomTra.setText(txtNomCliPro.getText());
            txtPlaVehTra.setText("");
            optRet.setSelected(true);
            optEnv.setSelected(false);
            txtDesCorVeh.setEditable(false);
            txtDesLarVeh.setEditable(false);
            butVeh.setEnabled(false);
            txtDesCorCho.setEditable(false);
            txtDesLarCho.setEditable(false);
            butCho.setEnabled(false);
            txtIdeTra.setText(txtRucCliPro.getText());            
            txtIdeTra.setEditable(false);
            txtNomTra.setEditable(false);
            txtPlaVehTra.setEditable(true);
            txtCodVeh.setEditable(false);
            txtPesVeh.setEditable(false);
            txtCodCho.setEditable(false);
            optVehTra.setSelected(false);
        }
    }//GEN-LAST:event_optVehCliActionPerformed

    private void optVehTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optVehTraActionPerformed
        if (optVehTra.isSelected()) {
            txtDesCorVeh.setText("");
            txtDesLarVeh.setText("");
            txtDesCorCho.setText("");
            txtDesLarCho.setText("");
            txtIdeTra.setText("");
            txtNomTra.setText("");
            txtPlaVehTra.setText("");
            optRet.setSelected(true);
            optEnv.setSelected(false);
            txtDesCorVeh.setEditable(false);
            txtDesLarVeh.setEditable(false);
            butVeh.setEnabled(false);
            txtDesCorCho.setEditable(false);
            txtDesLarCho.setEditable(false);
            butCho.setEnabled(false);
            txtIdeTra.setEditable(true);
            txtNomTra.setEditable(true);
            txtPlaVehTra.setEditable(true);
            optVehCli.setSelected(false);
        }
    }//GEN-LAST:event_optVehTraActionPerformed

    private void txtIdeTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeTraFocusGained
        txtIdeTra.selectAll();
    }//GEN-LAST:event_txtIdeTraFocusGained

    private void txtIdeTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeTraActionPerformed
        if (!objUti.isNumero(txtIdeTra.getText())) {
            mostrarMsgInf("Ingrese solo valores numericos");
            txtIdeTra.setText("");
            txtIdeTra.requestFocus();
        }
        txtIdeTra.transferFocus();
    }//GEN-LAST:event_txtIdeTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtCodForRetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodForRetKeyPressed

    }//GEN-LAST:event_txtCodForRetKeyPressed

    private void txtDesCorTipDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocConActionPerformed

    }//GEN-LAST:event_txtDesCorTipDocConActionPerformed

    private void butBusNumDocConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusNumDocConActionPerformed
        if (txtCodBodGrp.getText().equals(""))
        {
            mostrarMsgInf("Seleccione la bodega antes consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodBodGrp.requestFocus();
        }
        else{
//            cargarVenBusDoc("", txtCodBod.getText(), txtNomBod.getText());
        }
    }//GEN-LAST:event_butBusNumDocConActionPerformed

    private void butBusOrdDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusOrdDesActionPerformed
        if (txtCodBodGrp.getText().equals("")) {
            mostrarMsgInf("Seleccione la bodega antes de consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodBodGrp.requestFocus();
        } 
        else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeAllRows();
            callDocPenCnf();
        }
    }//GEN-LAST:event_butBusOrdDesActionPerformed

    private void txtPlaVehTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlaVehTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlaVehTraActionPerformed

    private void txtNumOrdDesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumOrdDesKeyPressed
        if(objTooBar.getEstado()=='n'){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeAllRows();

            if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) {
                if (txtCodBodGrp.getText().equals("")) {
                    mostrarMsgInf("Seleccione la bodega antes de consultar un documento. ");
                    tabFrm.setSelectedIndex(0);
                    txtCodBodGrp.requestFocus();
                } 
                else{
                    //cargarVenBusDoc("", txtCodBod.getText(), txtNomBod.getText());
                    callDocPenCnf();
                }
            }
        }
    }//GEN-LAST:event_txtNumOrdDesKeyPressed

    private void txtNumOrdDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumOrdDesFocusGained
        txtNumOrdDes.requestFocus();
        txtNumOrdDes.selectAll();
    }//GEN-LAST:event_txtNumOrdDesFocusGained

    private void butBusOrdDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butBusOrdDesFocusGained
//        if(txtNumOrdDes.getText().trim().length()>0)
//            txtNumOrdDes.setText("");
    }//GEN-LAST:event_butBusOrdDesFocusGained



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBodGrp;
    private javax.swing.JButton butBusNumDocCon;
    private javax.swing.JButton butBusOrdDes;
    private javax.swing.JButton butCho;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVeh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCho;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCodDocCon1;
    private javax.swing.JLabel lblComPro;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDocCon;
    private javax.swing.JLabel lblForRet1;
    private javax.swing.JLabel lblIde;
    private javax.swing.JLabel lblMotTra;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumDocCon1;
    private javax.swing.JLabel lblNumGuiDes;
    private javax.swing.JLabel lblNumGuiDet;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPla;
    private javax.swing.JLabel lblPrv4;
    private javax.swing.JLabel lblRso;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVeh;
    private javax.swing.JLabel lblVenCom;
    private javax.swing.JRadioButton optEnv;
    private javax.swing.JRadioButton optRet;
    private javax.swing.JRadioButton optVehCli;
    private javax.swing.JRadioButton optVehTra;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabDatDocCnf;
    private javax.swing.JPanel panDatDes;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDocCnf;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodGrp;
    private javax.swing.JTextField txtCodCho;
    private javax.swing.JTextField txtCodCliPro;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodDocCon;
    private javax.swing.JTextField txtCodForRet;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVeh;
    private javax.swing.JTextField txtCodVenCom;
    private javax.swing.JTextField txtDesCorCho;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesCorTipDocCon;
    private javax.swing.JTextField txtDesCorVeh;
    private javax.swing.JTextField txtDesForRet;
    private javax.swing.JTextField txtDesLarCho;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarTipDocCon;
    private javax.swing.JTextField txtDesLarVeh;
    private javax.swing.JTextField txtFecDocCon;
    private javax.swing.JTextField txtIdeTra;
    private javax.swing.JTextField txtMotTra;
    private javax.swing.JTextField txtNomBodGrp;
    private javax.swing.JTextField txtNomCliPro;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtNomVenCom;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumDocCon;
    private javax.swing.JTextField txtNumOrdDes;
    private javax.swing.JTextField txtPesVeh;
    private javax.swing.JTextField txtPlaVehTra;
    private javax.swing.JTextField txtRucCliPro;
    // End of variables declaration//GEN-END:variables


    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            vecDat=new Vector();    //Almacena los datos
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);            
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(584, 4, 100, 20);
            txtCodBodGrp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodGrp.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodVeh.setVisible(false);
            txtPesVeh.setVisible(false);
            txtCodCho.setVisible(false);
            txtRucCliPro.setVisible(false);
            
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblDat);
            
            configurarTblDat();
            configurarVenConTipDoc();
            configurarVenConBod();
            configurarVenConVeh();
            configurarVenConCho();
            
            dtpFecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                }
            });
            
            txtCodTipDoc.addFocusListener(new java.awt.event.FocusAdapter(){
                public void focusLost(java.awt.event.FocusEvent evt){
                }
            });
            
            //Configurar Programas por Código de Menu. 
            if(objParSis.getCodigoMenu()!=INT_COD_MNU_COEGDE )
            {
                tabFrm.remove(panDatDes);
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
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP,"Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP,"Cód.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM,"Cód.Let.Itm.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nom.Itm.");
            vecCab.add(INT_TBL_DAT_UNI_MED_ITM,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_CAN_DOC_ORI_CNF,"Can.Doc.");
            vecCab.add(INT_TBL_DAT_CAN_TOT_CNF,"Can.Cnf.");
            vecCab.add(INT_TBL_DAT_CAN_PUE_CNF,"Can.Pen.Cnf.");
            vecCab.add(INT_TBL_DAT_CAN_CNF_USR,"Can.Cnf.Usr.");
            vecCab.add(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC,"Can.Cnf.Usr.Nun.Ent.");
            vecCab.add(INT_TBL_DAT_OBS,"Obs.");
            vecCab.add(INT_TBL_DAT_EST_ING_EGR_MER_BOD,"Est.Itm.Ing.Egr.Bod.");            
            vecCab.add(INT_TBL_DAT_COD_EMP_ORD_DES,"Cód.Emp.Ord.Des.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ORD_DES,"Cód.Loc.Ord.Des.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ORD_DES,"Cód.Tip.Doc.Ord.Des.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ORD_DES,"Cód.Doc.Ord.Des.");
            vecCab.add(INT_TBL_DAT_COD_REG_ORD_DES,"Cód.Reg.Ord.Des.");            
            vecCab.add(INT_TBL_DAT_COD_EMP_DOC_ORI,"Cód.Emp.Doc.Ori");
            vecCab.add(INT_TBL_DAT_COD_LOC_DOC_ORI,"Cód.Loc.Doc.Ori");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ORI,"Cód.Tip.Doc.Ori");
            vecCab.add(INT_TBL_DAT_COD_DOC_ORI,"Cód.Doc.Ori");
            vecCab.add(INT_TBL_DAT_COD_REG_DOC_ORI,"Cód.Reg.Ori");
            vecCab.add(INT_TBL_DAT_CAN_PUE_CNF_TIE_EJE,"Can.Pue.Cnf.Tie.Eje");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_DOC_ORI_CNF, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_TOT_CNF, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_PUE_CNF, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CNF_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_LET_ITM);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            //objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
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
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DOC_ORI_CNF).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_CNF).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PUE_CNF).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CNF_USR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_ING_EGR_MER_BOD).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ORD_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ORD_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ORD_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ORD_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ORD_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_DOC_ORI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_DOC_ORI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ORI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ORI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_DOC_ORI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PUE_CNF_TIE_EJE).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ORD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ORD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ORD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ORD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ORD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_DOC_ORI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PUE_CNF_TIE_EJE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_CNF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ING_EGR_MER_BOD, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_LET_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN_CNF_USR);
            vecAux.add("" + INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC);
            vecAux.add("" + INT_TBL_DAT_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DOC_ORI_CNF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_CNF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PUE_CNF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CNF_USR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked()){
                    }
                }
            });
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CNF_USR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeCanPueCnf=BigDecimal.ZERO;
                BigDecimal bdeCanCnfUsr=BigDecimal.ZERO;
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    bdeCanPueCnf=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_PUE_CNF)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_PUE_CNF).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_PUE_CNF).toString()));
                }
                //Aqui
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiTxt.getText().equals("")){
                        objTblMod.setChecked(false, intFilSel, INT_TBL_DAT_CHK);
                        //objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_COD_LET_ITM);  //Rose: Uso objTblEdi 
                    }
                    else{
                        bdeCanCnfUsr=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CNF_USR)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CNF_USR).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CNF_USR).toString()));
                        if(bdeCanPueCnf.compareTo(bdeCanCnfUsr)>=0){
                            objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);
                            //Se ubica en la siguiente fila.
                            //objTblEdi.seleccionarCelda(intFilSel+1, INT_TBL_DAT_COD_LET_ITM);  //Rose: Uso objTblEdi 
                        }
                        else{
                            objTblMod.setChecked(false, intFilSel, INT_TBL_DAT_CHK);
                            objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_CNF_USR);
                            mostrarMsgInf("<HTML>La cantidad ingresada es superior a la permitida<BR>Verifique la cantida y vuelva a ingresarla</HTML>");
                            //Se dirige a la columna para ingresar cantidad a confirmar.
                            //objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_CNF_USR);  //Rose: Uso objTblEdi 
                        }
                    }
                }
            });
            objTblCelEdiTxtObs=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setCellEditor(objTblCelEdiTxtObs);
            objTblCelEdiTxtObs=null;
            
            objTblCelEdiTxtItmLet=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellEditor(objTblCelEdiTxtItmLet);
            objTblCelEdiTxtItmLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strCodItmLet="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strCodItmLet=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LET_ITM)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LET_ITM).toString();
                    strCodItmLet=strCodItmLet.toUpperCase();
                    if(objTblCelEdiTxtItmLet.getText().equals("")){
                        objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                    }
                    else{
                        if(setDatModel(strCodItmLet, intFil)){
                            objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                            objTblMod.insertRow();    //Rose: Comentar para Uso objTblEdi .
                        }
                        else
                            objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                    }
                }
            });
            
            txtDesCorTipDocCon.setEditable(false);
            txtDesLarTipDocCon.setEditable(false);
            txtCodCliPro.setEditable(false);
            txtNomCliPro.setEditable(false);
            txtCodVenCom.setEditable(false);
            txtNomVenCom.setEditable(false);
            txtCodForRet.setEditable(false);
            txtDesForRet.setEditable(false);
            txtFecDocCon.setEditable(false);
            txtNumDocCon.setEditable(false);
            txtCodDocCon.setEditable(false);
            txtMotTra.setEditable(false);
            
            //Libero los objetos auxiliares.
            
//            objTblCelRenChk = null;
//            objTblCelEdiChk = null;
//            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
//            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer los listener para el TableHeader. Bostel
            
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
            tcmAux=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    // bostel
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        
        try {
          
            intNumFil = tblDat.getRowCount();
            //Hace que solo sirva para Egresos de Despacho
             if(objParSis.getCodigoMenu()==4051 || objParSis.getCodigoMenu()== 4041)

            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_DAT_CAN_CNF_USR) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        //recorre las filas cojer el dato de can_pue_cnf
                        String strPenConf = (tblDat.getValueAt(i, INT_TBL_DAT_CAN_PUE_CNF) == null ? "" : tblDat.getValueAt(i, INT_TBL_DAT_CAN_PUE_CNF).toString());
                        
                                if(strPenConf.equals("")){
                                   strPenConf.valueOf(0);
                                }else{
                                if(Double.parseDouble(strPenConf) > 0){
                                //Inserta las mismas cantdidades que esten pendientes de confirmar
                                tblDat.setValueAt(strPenConf, i, INT_TBL_DAT_CAN_CNF_USR);
                                //Se marca el checkbox sin esto no toma en cuanta que se haya modificado la celda
                                objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
                                }
                                }                            
                    }
                   
                    blnMarTodCanTblDat = false;
                }else if(blnMarTodCanTblDat == false) {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        //Pone columnas en 0 null
                        tblDat.setValueAt("0", i, INT_TBL_DAT_CAN_CNF_USR);
                        //Desmarca el checkbox
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodCanTblDat = true;
                    
                }
                } 
//            else {
//                    //Ocultar todas las columnas.
//                    for (i = 0; i < intNumFil; i++) {
//                        //Pone columnas en 0 null
//                        tblDat.setValueAt("0", i, INT_TBL_DAT_CAN_CNF_USR);
//                        //Desmarca el checkbox
//                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
//                    }
//                    blnMarTodCanTblDat = true;
//                    
//                }

           
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
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
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBodGrp.getDocument().addDocumentListener(objDocLis);
        txtNomBodGrp.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
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
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg="Código del item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg="Código del item de empresa";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno de item";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg="Código en letras de item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED_ITM:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CAN_DOC_ORI_CNF:
                    strMsg="Cantidad de documento a confirmar";
                    break;
                case INT_TBL_DAT_CAN_TOT_CNF:
                    strMsg="Cantidad total confirmada";
                    break;
                case INT_TBL_DAT_CAN_PUE_CNF:
                    strMsg="Cantidad que se puede confirmar en esta instancia";
                    break;
                case INT_TBL_DAT_CAN_CNF_USR:
                    strMsg="Cantidad a confirmar por el usuario";
                    break;
                case INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC:
                    strMsg="Cantidad a confirmar por el usuario(Nunca entregada/recibida)";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación";
                    break;
                case INT_TBL_DAT_EST_ING_EGR_MER_BOD:
                    strMsg="Estado del ingreso/egreso de mercadería a bodega del item";
                    break;
                case INT_TBL_DAT_COD_EMP_ORD_DES:
                    strMsg="Código de empresa de la orden de despacho";
                    break;
                case INT_TBL_DAT_COD_LOC_ORD_DES:
                    strMsg="Código de local de la orden de despacho";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ORD_DES:
                    strMsg="Código de tipo de documento de la orden de despacho";
                    break;
                case INT_TBL_DAT_COD_DOC_ORD_DES:
                    strMsg="Código de documento de la orden de despacho";
                    break;
                case INT_TBL_DAT_COD_REG_ORD_DES:
                    strMsg="Código de registro de la orden de despacho";
                    break;
                case INT_TBL_DAT_COD_EMP_DOC_ORI:
                    strMsg="Código de empresa del egreso/ingreso";
                    break;
                case INT_TBL_DAT_COD_LOC_DOC_ORI:
                    strMsg="Código de local del egreso/ingreso";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ORI:
                    strMsg="Código de tipo de documento del egreso/ingreso";
                    break;
                case INT_TBL_DAT_COD_DOC_ORI:
                    strMsg="Código de documento del egreso/ingreso";
                    break;
                case INT_TBL_DAT_COD_REG_DOC_ORI:
                    strMsg="Código de registro del egreso/ingreso";
                    break;
                case INT_TBL_DAT_CAN_PUE_CNF_TIE_EJE:
                    strMsg="Cantidad puede confirmar en tiempo de ejecución";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
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

        public void clickInicio() {
            try{
                if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() {
            try{
                if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try{
                 if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf < arlDatConCnf.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickFin() {
            try{
                 if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf<arlDatConCnf.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf=arlDatConCnf.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf=arlDatConCnf.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickInsertar() {
            try{
                if ((blnHayCam)  ){
                    isRegPro();
                }

                limpiarFrm();
                mostrarTipDocPre();
                mostrarBodPre();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();

                //Inicializar las variables que indican cambios.
                blnHayCam=false;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {

        }

        public void clickModificar() {
            txtCodTipDoc.setEditable(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            dtpFecDoc.setEnabled(false);
            txtNumDoc.setEditable(false);
            txtCodDoc.setEditable(false);
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar() {
            return true;
        }

        public boolean eliminar() {
            return true;
        }

        public boolean anular() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null){
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
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

        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            
            return true;
        }

        public boolean beforeConsultar() {
            if(txtCodBodGrp.getText().equals("")){
                mostrarMsgInf("<HTML>Ingrese la bodega respectiva para poder realizar la operación</HTML>");
                return false;
            }
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAnular() {
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

        public boolean afterInsertar(){
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');
            
            if(objParSis.getCodigoMenu()==4051){ //Confirmación de egreso - despacho
                if(imprimirGuiRem()){
                    System.out.println("Impresión de Guía de Remisión");
                }
            }
            
            if(objParSis.getCodigoMenu()==4000){ //Confirmación de ingreso - despacho
                if(validarIsBodImpOrdAlm(Integer.parseInt(txtCodBodGrp.getText()))) {
                    if(generarRpt(0))
                        System.out.println("Se imprimio Orden de Almacenamiento (Bodegas con 1 area) y Orden de Ingreso (Bodegas con 2 areas) OK");
                }
                
                if(imprimirOrdDesRes())
                    System.out.println("Se imprimio Orden de Despacho Reservas");
            }

            if(objParSis.getCodigoMenu()==4010){ //Confirmación de ingreso - bodega
                if(validarIsBodImpOrdAlm(Integer.parseInt(txtCodBodGrp.getText()))) {
                    if(generarRpt(0))
                        System.out.println("Se imprimio Orden de Almacenamiento.");
                }
            }      
            
            if(objParSis.getCodigoMenu()==4041){ //Confirmación de egreso - bodega
                if(generarRpt(0))
                    System.out.println("Se imprimio Orden de Egreso.");
            }
            
            consultarReg();

            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
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


  
    

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        blnMarTodCanTblDat=true; //Bostel
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        blnMarTodCanTblDat=true;//Bostel
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnMarTodCanTblDat=true;//Bostel
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                blnMarTodCanTblDat=true;//Bostel
                break;
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");            
            txtCodBodGrp.setText("");
            txtNomBodGrp.setText("");            
            dtpFecDoc.setText("");
            txtNumDoc.setText("");
            txtCodDoc.setText("");
            txtCodDoc.setEditable(false);
            txaObs1.setText("");
            txaObs2.setText("");
            
            txtDesCorTipDocCon.setText("");
            txtDesLarTipDocCon.setText("");
            txtCodCliPro.setText("");
            txtNomCliPro.setText("");
            txtCodVenCom.setText("");
            txtNomVenCom.setText("");
            txtCodForRet.setText("");
            txtDesForRet.setText("");
            txtFecDocCon.setText("");
            txtNumDocCon.setText("");
            txtCodDocCon.setText("");
            txtMotTra.setText("");
            txtNumOrdDes.setText("");
            objTblMod.removeAllRows();
            
            txtDesCorTipDocCon.setEditable(false);
            txtDesLarTipDocCon.setEditable(false);
            txtCodCliPro.setEditable(false);
            txtNomCliPro.setEditable(false);
            txtCodVenCom.setEditable(false);
            txtNomVenCom.setEditable(false);
            txtCodForRet.setEditable(false);
            txtDesForRet.setEditable(false);
            txtFecDocCon.setEditable(false);
            txtNumDocCon.setEditable(false);
            txtCodDocCon.setEditable(false);
            txtMotTra.setEditable(false);
            
            optEnv.setSelected(false);
            txtCodVeh.setText("");
            txtPesVeh.setText("");
            txtDesCorVeh.setText("");
            txtDesLarVeh.setText("");
            txtCodCho.setText("");
            txtDesCorCho.setText("");
            txtDesLarCho.setText("");
            optRet.setSelected(false);
            optVehCli.setSelected(false);
            optVehTra.setSelected(false);
            txtIdeTra.setText("");
            txtNomTra.setText("");
            txtPlaVehTra.setText("");
            txtRucCliPro.setText("");
            //borrar
            if( (objParSis.getCodigoMenu()==4041) ||  (objParSis.getCodigoMenu()==4051) )
                txtNumOrdDes.setEditable(false);
            else
                txtNumOrdDes.setEditable(true);
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(insertar_tbmCabIngEgrMerBod()){
                    if(insertar_tbmDetIngEgrMerBod()){
                        if(isValItmCanPenCnfPar()){
                            if(actualizar_tbmCabTipDoc()){
                                if(actualizaCampos_tbmDetMovInv()){
                                    if(objSegMovInv.setSegMovInvCompra(con, intCodSegRef, 7, intCodEmpDocCnf, intCodLocDocCnf, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                                        if(objParSis.getCodigoMenu()==4051){//Confirmacion de egresos - despacho
                                            if(objGenGuiRem.generarGuiRem(con, intCodEmpDocCnf, intCodLocDocCnf, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){//genera la GR
                                                if(objCnfDoc.isCnfParTotDocEgr(con, intCodEmpDocOri, intCodLocDocOri, intCodTipDocOri, intCodDocOri, strTipMovCnfTot)){
                                                    if(objCnfDoc.isCnfTotDocOri(con, intCodSegRef, strTipMovCnfTot, intCodEmpDocOri, intCodLocDocOri, intCodTipDocOri, intCodDocOri)){
                                                        if(objCnfDoc.setCanPendiente(con, intCodSegRef)){
                                                            System.out.println("Ok - varias instancias");
                                                            blnMarTodCanTblDat= true;//Bostel
                                                            con.commit();
                                                            blnRes=true;
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
                                            else{
                                                System.out.println("Fallo");
                                                con.rollback();
                                            }
                                        }
                                        else if(objParSis.getCodigoMenu()==4000){//confirmación de ingreso - despacho
                                            if(objCnfDoc.isDocIngPenCnf(con, intCodSegRef, strTipMovCnfTot)){
                                                if(objCnfDoc.isCnfTotDocOri(con, intCodSegRef, strTipMovCnfTot, intCodEmpDocOri, intCodLocDocOri, intCodTipDocOri, intCodDocOri)){
                                                    if(objCnfDoc.isProCnfTotActEstCot(con, intCodSegRef,objParSis.getCodigoMenu())){
                                                        if(objCnfDoc.isProCnfTotActEstSol(con, intCodSegRef)){
                                                            if(objCnfDoc.setCanPendiente(con, intCodSegRef)){
//                                                                if(objCnfDoc.isDocResDesAntFac(con, intCodSegRef,intCodEmpDocCnf,intCodLocDocCnf,Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText()) ) ){
                                                                    System.out.println("Ok - una instancias");
                                                                    con.commit();
                                                                    blnRes=true;
//                                                                }else{
//                                                                    System.err.println("ERROR!! Reservas Confirmacion Ingreso Despacho ante de facturar");
//                                                                    con.rollback();
//                                                                }
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
                                            else
                                                con.rollback();
                                        }
                                        else if( (objParSis.getCodigoMenu()==4010) || (objParSis.getCodigoMenu()==4041) ){//Confirmación de ingreso a bodega / Confirmación de egreso de bodega
                                            if(objCnfDoc.isCnfTotDocOri(con, intCodSegRef, strTipMovCnfTot, intCodEmpDocOri, intCodLocDocOri, intCodTipDocOri, intCodDocOri)){
                                                if(objCnfDoc.isProCnfTotActEstCot(con, intCodSegRef,objParSis.getCodigoMenu())){
                                                    if(objCnfDoc.isProCnfTotActEstSol(con, intCodSegRef)){
                                                        if(objCnfDoc.setCanPendiente(con, intCodSegRef)){
                                                            System.out.println("Ok - una instancias");
                                                            con.commit();
                                                            blnRes=true;
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
                                        else{//cancelacion
                                            con.commit();
                                            blnRes=true;
                                        }
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
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabIngEgrMerBod(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                 
                if(arlDatDocCnfSel.size()>0){
                    //Obtener el código del último registro.
                    strSQL="";
                    strSQL+=" SELECT MAX(a1.co_doc)";
                    strSQL+=" FROM tbm_cabIngEgrMerBod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpDocCnf + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocDocCnf + "";
                    strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                    System.out.println("insertar_tbmCabIngEgrMerBod.ÚltimoCodigo: " + strSQL);
                    intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltReg==-1)
                        return false;
                    intUltReg++;
                    txtCodDoc.setText("" + intUltReg);
                    
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_cabIngEgrMerBod(";
                    strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod,";
                    strSQL+=" co_locrel, co_tipdocrel, co_docrel, co_locrelguirem, co_tipdocrelguirem, co_docrelguirem,";
                    strSQL+=" co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_tipcon,";
                    strSQL+=" tx_fordes, tx_fordescliret, co_veh, co_cho, tx_idetra, tx_nomtra, tx_plavehtra";
                    strSQL+=" )";
                    strSQL+=" ( SELECT";
                    strSQL+="  " + intCodEmpDocCnf + "";//co_emp
                    strSQL+=", " + intCodLocDocCnf + "";//co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + "";//co_tipdoc
                    strSQL+=", " + txtCodDoc.getText() + "";//co_doc
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                    strSQL+=", " + txtNumDoc.getText() + "";//ne_numdoc
                    strSQL+=", " + intCodBodEmp + "";//co_bod
                    if( strTipMovCnfTot.equals("I") ){//Ingreso
                        strSQL+=", " + intCodLocDocCnf + "";//co_locrel
                        strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrel
                        strSQL+=", " + intCodDocCnf + "";//co_docrel
                        strSQL+=", Null";//co_locrelguirem
                        strSQL+=", Null";//co_tipdocrelguirem
                        strSQL+=", Null";//co_docrelguirem
                    }
                    else if( strTipMovCnfTot.equals("E") ){//Egreso
                        strSQL+=", Null";//co_locrel
                        strSQL+=", Null";//co_tipdocrel
                        strSQL+=", Null";//co_docrel
                        strSQL+=", " + intCodLocDocCnf + "";//co_locrelguirem
                        strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrelguirem
                        strSQL+=", " + intCodDocCnf + "";//co_docrelguirem
                    }
                    strSQL+=", " + objParSis.getCodigoMenu() + "";//co_mnu
                    strSQL+=", 'N'";//st_imp
                    strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                    strSQL+=", 'A'";//st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'";//fe_ing
                    strSQL+=", Null";//fe_ultmod
                    strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                    strSQL+=", Null";//co_usrmod
                    strSQL+=", Null";//tx_tipcon
                    strSQL+=", Null";//tx_fordes
                    strSQL+=", Null";//tx_fordescliret
                    if(optEnv.isSelected()){
                        strSQL+=", " + txtCodVeh.getText() + "";//co_veh
                        strSQL+=", " + txtCodCho.getText() + "";//co_cho
                        strSQL+=", " + objUti.codificar(txtDesCorCho.getText()) + "";//tx_idetra
                        strSQL+=", " + objUti.codificar(txtDesLarCho.getText()) + "";//tx_nomtra
                        strSQL+=", " + objUti.codificar(txtDesCorVeh.getText()) + "";//tx_plavehtra
                    }
                    else if(optRet.isSelected()){
                        strSQL+=", Null";//co_veh
                        strSQL+=", Null";//co_cho
                        strSQL+=", " + objUti.codificar(txtIdeTra.getText()) + "";//tx_idetra
                        strSQL+=", " + objUti.codificar(txtNomTra.getText()) + "";//tx_nomtra
                        strSQL+=", " + objUti.codificar(txtPlaVehTra.getText()) + "";//tx_plavehtra
                    }
                    else{
                        strSQL+=", Null";//co_veh
                        strSQL+=", Null";//co_cho
                        strSQL+=", Null";//tx_idetra
                        strSQL+=", Null";//tx_nomtra
                        strSQL+=", Null";//tx_plavehtra
                    }
                    
                    strSQL+=" );";
                    System.out.println("tbm_cabIngEgrMerBod: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
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
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @param tipoOperacion  <HTML> Tipo de confirmación que se debe realizar
     *              <BR>  0: Actualiza en campo "nd_can"
     *              <BR>  1: Actualiza en campo "nd_cannunrec"
     *              <BR>  2: Actualiza en campo "nd_canmalest"
     *          </HTML>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetIngEgrMerBod(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=0;
        BigDecimal bdeCanUsr=BigDecimal.ZERO;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        
                        if(isValItmCanSufCnf(con, intCodEmpDocOri, intCodLocDocOri, intCodTipDocOri, intCodDocOri, Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_DOC_ORI).toString())
                                            , intCodEmpDocCnf, intCodLocDocCnf, intCodTipDocCnf, intCodDocCnf, Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_ORD_DES).toString())
                                            , objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM).toString(), new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString())
                                            )){
                         //insertar detalle
                            j++;
                            strSQL="";
                            strSQL+=" INSERT INTO tbm_detingegrmerbod(";
                            strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel,co_regrel,";
                            strSQL+="   co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_regrelguirem,";
                            strSQL+="   co_itm, co_bod, nd_can, tx_obs1,";
                            strSQL+="   nd_canmalest, st_solproreamermalest, st_tipproreamermalest, nd_canmalestaut,";
                            strSQL+="   nd_canmalestden, tx_obsautproreamermalest, fe_ingautproreamermalest,";
                            strSQL+="   co_usringautproreamermalest, tx_comingautproreamermalest, nd_cannunrec,";
                            strSQL+="   st_solproreamernunrec, nd_cannunrecaut, nd_cannunrecden, tx_obsautproreamernunrec,";
                            strSQL+="   fe_ingautproreamernunrec, co_usringautproreamernunrec, tx_comingautproreamernunrec";
                            strSQL+=")";
                            strSQL+="( SELECT";
                            strSQL+=" " + intCodEmpDocCnf + "";//co_emp
                            strSQL+="," + intCodLocDocCnf + "";//co_loc
                            strSQL+="," + txtCodTipDoc.getText() + "";//co_tipdoc
                            strSQL+="," + txtCodDoc.getText() + "";//co_doc
                            strSQL+="," + j + "";//co_reg

                            if( strTipMovCnfTot.equals("I") ){//Ingreso
                                strSQL+=", " + intCodLocDocCnf + "";//co_locrel
                                strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrel
                                strSQL+=", " + intCodDocCnf + "";//co_docrel
                                strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_ORD_DES) + "";//co_regrel
                                strSQL+=", Null";//co_locrelguirem
                                strSQL+=", Null";//co_tipdocrelguirem
                                strSQL+=", Null";//co_docrelguirem
                                strSQL+=", Null";//co_regrelguirem
                            }
                            else if( strTipMovCnfTot.equals("E") ){//Egreso
                                strSQL+=", Null";//co_locrel
                                strSQL+=", null";//co_tipdocrel
                                strSQL+=", Null";//co_docrel
                                strSQL+=", Null";//co_regrel                        
                                strSQL+=", " + intCodLocDocCnf + "";//co_locrelguirem
                                strSQL+=", " + intCodTipDocCnf + "";//co_tipdocrelguirem
                                strSQL+=", " + intCodDocCnf + "";//co_docrelguirem
                                strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_ORD_DES) + "";//co_regrelguirem
                            }
                            strSQL+="," + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";//co_itm
                            strSQL+="," + intCodBodEmp + "";//co_bod

                            bdeCanUsr=objUti.redondearBigDecimal((objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString())), objParSis.getDecimalesMostrar());

                            if( strTipMovCnfTot.equals("I") ){//Ingreso
                                strSQL+="," + bdeCanUsr  + "";//nd_can
                            }
                            else if( strTipMovCnfTot.equals("E") ){//Egreso
                                strSQL+=", (-" + bdeCanUsr + ")";//nd_can
                            }

                            strSQL+="," + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + "";//tx_obs1
                            strSQL+=", Null";//nd_canmalest
                            strSQL+=", Null";//st_solproreamermalest
                            strSQL+=", Null";//st_tipproreamermalest
                            strSQL+=", Null";//nd_canmalestaut
                            strSQL+=", Null";//nd_canmalestden
                            strSQL+=", Null";//tx_obsautproreamermalest
                            strSQL+=", Null";//fe_ingautproreamermalest
                            strSQL+=", Null";//co_usringautproreamermalest
                            strSQL+=", Null";//tx_comingautproreamermalest

                            if( strTipMovCnfTot.equals("I") ){//Ingreso
                                strSQL+=", 0";//nd_cannunrec
                            }
                            else if( strTipMovCnfTot.equals("E") ){//Egreso
                                strSQL+=", 0";//nd_cannunrec
                            }

                            strSQL+=", Null";//st_solproreamernunrec
                            strSQL+=", Null";//nd_cannunrecaut
                            strSQL+=", Null";//nd_cannunrecden
                            strSQL+=", Null";//tx_obsautproreamernunrec
                            strSQL+=", Null";//fe_ingautproreamernunrec
                            strSQL+=", Null";//co_usringautproreamernunrec
                            strSQL+=", Null";//tx_comingautproreamernunrec
                            strSQL+=")";
                            strSQL+=";";
                            strSQLIns+=strSQL;
                        }
                        else{
                            blnRes=false;
                            break;
                        }
                    }

                }
                System.out.println("insertar_tbmDetIngEgrMerBodmp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
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
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDoc(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                System.out.println("actualizarCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta función actualiza en la tabla "tbm_detMovInv".
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizaCampos_tbmDetMovInv(){
        boolean blnRes=false;
        int j=0;
        BigDecimal bdeCanUsr=BigDecimal.ZERO;
        //String strTipCnf="";//indica si es confirmación de ingreso o confirmación de egreso
        try{
            if (con!=null){
                arlDatCnf.clear();
                objCnfDoc=new ZafCnfDoc(objParSis, Integer.parseInt(txtCodBodGrp.getText()), this);
                
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        j++;
                        bdeCanUsr=objUti.redondearBigDecimal((objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString())), objParSis.getDecimalesMostrar());
                        
                        arlRegCnf=new ArrayList();
                        arlRegCnf.add(INT_ARL_DAT_COD_EMP_CNF, intCodEmpDocCnf);
                        arlRegCnf.add(INT_ARL_DAT_COD_LOC_CNF, intCodLocDocCnf);
                        arlRegCnf.add(INT_ARL_DAT_COD_TIP_DOC_CNF, txtCodTipDoc.getText());
                        arlRegCnf.add(INT_ARL_DAT_COD_DOC_CNF, txtCodDoc.getText());
                        arlRegCnf.add(INT_ARL_DAT_COD_REG_CNF, j);
                        arlRegCnf.add(INT_ARL_DAT_COD_ITM_GRP_CNF, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP));
                        arlRegCnf.add(INT_ARL_DAT_COD_ITM_EMP_CNF, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP));
                        arlRegCnf.add(INT_ARL_DAT_COD_ITM_MAE_CNF, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                        if( strTipMovCnfTot.equals("I") ){//Ingreso
                            bdeCanUsr=bdeCanUsr;//nd_can
                        }
                        else if( strTipMovCnfTot.equals("E") ){//Egreso
                            bdeCanUsr=bdeCanUsr.multiply(new BigDecimal("-1"));//nd_can
                        }
                        else{
                            bdeCanUsr=BigDecimal.ZERO;//nd_can
                        }
                        arlRegCnf.add(INT_ARL_DAT_CAN_ITM_CNF, bdeCanUsr);
                        arlRegCnf.add(INT_ARL_DAT_COD_EMP_ORI, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_DOC_ORI));
                        arlRegCnf.add(INT_ARL_DAT_COD_LOC_ORI, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_DOC_ORI));
                        arlRegCnf.add(INT_ARL_DAT_COD_TIP_DOC_ORI, objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_ORI));
                        arlRegCnf.add(INT_ARL_DAT_COD_DOC_ORI, objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_ORI));
                        arlRegCnf.add(INT_ARL_DAT_COD_REG_ORI, objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_DOC_ORI));
                        arlDatCnf.add(arlRegCnf);
                        

                    }
                }
                
//                System.out.println("***strTipMovDocOri: " + strTipMovDocOri);
//                System.out.println("***arlDatCnf: " + arlDatCnf.toString());

                if( (strTipMovDocOri.equals("E")) || (strTipMovDocOri.equals("I")) ){
                    if(objCnfDoc.setCnfIngresoEgreso(con, arlDatCnf, strTipMovDocOri, intCodBodEmp))
                        blnRes=true;
                }
                else if( (strTipMovDocOri.equals("R")) ){
                    if(objCnfDoc.setCnfRelacionada(con, arlDatCnf, strTipMovDocOri, strTipMovCnfTot))
                        blnRes=true;
                }
                else if( (strTipMovDocOri.equals("V")) ){
                    if(objCnfDoc.setCnfVtaRelacionada(con, arlDatCnf, strTipMovDocOri, strTipMovCnfTot))
                        blnRes=true;
                }
                else
                    System.out.println("Por cancelación ????");
                
                
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
                //Validar que sálo se muestre los documentos asignados al programa.
                if( strTipMovCnfTot.equals("E") ){//egresos
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp AS co_empCnf, a1.co_loc AS co_locCnf, a1.co_tipDoc AS co_tipDocCnf, a1.co_doc AS co_docCnf";
                    strSQL+="      , a5.tx_desCor AS tx_desCorCnf, a5.tx_desLar AS tx_desLarCnf";
                    strSQL+="      , a1.ne_numDoc AS ne_numDocCnf, a1.fe_doc AS fe_docCnf, a1.co_bod, a4.tx_nom AS tx_nomBod, a1.tx_obs1, a1.tx_obs2, a1.co_mnu";
                    strSQL+="      , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra";
                    strSQL+="      , a3.co_emp AS co_empGuiRem, a3.co_loc AS co_locGuiRem, a3.co_tipDoc AS co_tipDocGuiRem, a3.co_doc AS co_docGuiRem";
                    strSQL+="      , CASE WHEN a3.ne_numDoc=0 THEN a3.ne_numOrdDes ELSE a3.ne_numDoc END AS ne_numDoc, a3.fe_doc AS fe_docGuiRem";
                    strSQL+="      , a6.tx_desCor AS tx_desCorGuiRem, a6.tx_desLar AS tx_desLarGuiRem";
                    strSQL+="      , a3.tx_ptoPar, a3.co_cliDes, a3.tx_rucCliDes, a3.tx_nomCliDes, a3.tx_dirCliDes, a3.tx_telCliDes, a3.tx_ciuCliDes";
                    strSQL+=" FROM (((((tbm_cabIngEgrMerBod AS a1 LEFT OUTER JOIN tbm_tra AS a9 ON a1.co_cho=a9.co_tra) LEFT OUTER JOIN tbm_veh AS a8 ON a8.co_veh=a1.co_veh)";
                    strSQL+="          INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                    strSQL+="          INNER JOIN tbm_bod AS a4 ON a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod)";
                    strSQL+="          INNER JOIN ((tbm_cabGuiRem AS a3 LEFT OUTER JOIN tbm_var AS a7 ON a3.co_forRet=a7.co_reg)";
                    strSQL+="          INNER JOIN tbm_cabTipDoc AS a6 ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_tipDoc=a6.co_tipDoc)";
                    strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_locRelGuiRem=a3.co_loc AND a1.co_tipDocRelGuiRem=a3.co_tipDoc AND a1.co_docRelGuiRem=a3.co_doc)";
                    strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a10 ON a1.co_emp=a10.co_emp AND a1.co_bod=a10.co_bod";
                    strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a10.co_bodGrp=" + txtCodBodGrp.getText() + "";
                    
                    if( (intCodEmpDocCnf!=0) &&  (intCodLocDocCnf!=0) ) {
                        strSQL+=" AND a1.co_emp=" + intCodEmpDocCnf + "";
                        strSQL+=" AND a1.co_loc=" + intCodLocDocCnf + "";
                    }                    
                    
                    strAux=txtCodTipDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.co_tipDoc = " + strAux + "";

                    strAux=txtCodDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                    if (dtpFecDoc.isFecha())
                        strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAux=txtNumDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                    
                    //Búsqueda por Documento Origen
                    strAux=txtNumDocCon.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND (CASE WHEN a3.ne_numDoc =0 THEN a3.ne_numOrdDes ELSE a3.ne_numDoc END) IN (" + strAux.replaceAll("'", "''") + ")";

                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar";
                    strSQL+="        , a1.ne_numDoc, a1.fe_doc, a1.co_bod, a4.tx_nom, a1.tx_obs1, a1.tx_obs2";
                    strSQL+="        , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra";
                    strSQL+="        , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.ne_numDoc, a3.fe_doc";
                    strSQL+="        , a6.tx_desCor, a6.tx_desLar, a1.co_mnu";
                    strSQL+="        , a3.tx_ptoPar, a3.co_cliDes, a3.tx_rucCliDes, a3.tx_nomCliDes, a3.tx_dirCliDes, a3.tx_telCliDes, a3.tx_ciuCliDes";
                    strSQL+=" ORDER BY a1.fe_doc, a1.ne_numDoc";
                }
                else if( strTipMovCnfTot.equals("I") ){//ingreso
                    strSQL="";
                    strSQL+=" SELECT b2.co_empBod, b2.co_bodBod, b1.co_empCnf, b1.co_locCnf, b1.co_tipDocCnf, b1.co_docCnf, b1.tx_desCorCnf, b1.tx_desLarCnf";
                    strSQL+="      , b1.ne_numDocCnf, b1.fe_docCnf, b1.co_bod, b1.tx_nomBod, b1.tx_obs1, b1.tx_obs2, b1.co_mnu";
                    strSQL+="      , b1.co_veh, b1.co_cho, b1.tx_ideTra, b1.tx_nomTra, b1.tx_plaVehTra";
                    strSQL+="      , b1.co_empGuiRem, b1.co_locGuiRem, b1.co_tipDocGuiRem, b1.co_docGuiRem, b1.ne_numDoc, b1.fe_docGuiRem";
                    strSQL+="      , b1.tx_desCorGuiRem, b1.tx_desLarGuiRem";
                    strSQL+="      , b1.tx_ptoPar, b1.co_cli, b1.tx_ruc, b1.tx_nomCli, b1.tx_dirCli, b1.tx_telCli, b1.tx_ciuCli";
                    strSQL+=" FROM(";
                    strSQL+="       SELECT a1.co_emp AS co_empCnf, a1.co_loc AS co_locCnf, a1.co_tipDoc AS co_tipDocCnf, a1.co_doc AS co_docCnf, a5.tx_desCor AS tx_desCorCnf, a5.tx_desLar AS tx_desLarCnf";
                    strSQL+="            , a1.ne_numDoc AS ne_numDocCnf, a1.fe_doc AS fe_docCnf, a1.co_bod, a4.tx_nom AS tx_nomBod, a1.tx_obs1, a1.tx_obs2, a1.co_mnu";
                    strSQL+="            , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra";
                    strSQL+="            , a3.co_emp AS co_empGuiRem, a3.co_loc AS co_locGuiRem, a3.co_tipDoc AS co_tipDocGuiRem, a3.co_doc AS co_docGuiRem, a3.ne_numDoc, a3.fe_doc AS fe_docGuiRem";
                    strSQL+="            , a6.tx_desCor AS tx_desCorGuiRem, a6.tx_desLar AS tx_desLarGuiRem";
                    strSQL+="            , a3.tx_ptoPar, a3.co_cli, a3.tx_ruc, a3.tx_nomCli, a3.tx_dirCli, a3.tx_telCli, a3.tx_ciuCli";
                    strSQL+="       FROM (((tbm_cabIngEgrMerBod AS a1";
                    strSQL+="               INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                    strSQL+="               INNER JOIN tbm_bod AS a4 ON a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod)";
                    strSQL+="               INNER JOIN (tbm_cabMovInv AS a3";
                    strSQL+="               INNER JOIN tbm_cabTipDoc AS a6 ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_tipDoc=a6.co_tipDoc)";
                    strSQL+="               ON a1.co_emp=a3.co_emp AND a1.co_locRel=a3.co_loc AND a1.co_tipDocRel=a3.co_tipDoc AND a1.co_docRel=a3.co_doc)";
                    strSQL+="       INNER JOIN tbr_bodEmpBodGrp AS a10 ON a1.co_emp=a10.co_emp AND a1.co_bod=a10.co_bod";
                    strSQL+="       WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+="       AND a10.co_bodGrp=" + txtCodBodGrp.getText() + "";
                    
                    if( (intCodEmpDocCnf!=0) &&  (intCodLocDocCnf!=0) ) {
                        strSQL+=" AND a1.co_emp=" + intCodEmpDocCnf + "";
                        strSQL+=" AND a1.co_loc=" + intCodLocDocCnf + "";
                    }                   
                    
                    strAux=txtCodTipDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+="       AND a1.co_tipDoc = " + strAux + "";

                    strAux=txtCodDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+="       AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                    if (dtpFecDoc.isFecha())
                        strSQL+="       AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    strAux=txtNumDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+="       AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                       
                    //Búsqueda por Documento Origen
                    strAux=txtNumDocCon.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND (CASE WHEN a3.ne_numDoc =0 THEN a3.ne_numOrdDes ELSE a3.ne_numDoc END) IN (" + strAux.replaceAll("'", "''") + ")";
                    
                    strSQL+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar";
                    strSQL+="              , a1.ne_numDoc, a1.fe_doc, a1.co_bod, a4.tx_nom, a1.tx_obs1, a1.tx_obs2";
                    strSQL+="              , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra";
                    strSQL+="              , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.ne_numDoc, a3.fe_doc";
                    strSQL+="              , a6.tx_desCor, a6.tx_desLar, a1.co_mnu";
                    strSQL+="              , a3.tx_ptoPar, a3.co_cli, a3.tx_ruc, a3.tx_nomCli, a3.tx_dirCli, a3.tx_telCli, a3.tx_ciuCli";
                    strSQL+="       ORDER BY a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" ) AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT b1.co_empGrp, b1.co_bodGrp, b2.co_emp AS co_empBod, b2.co_bod AS co_bodBod, b1.tx_nom AS tx_nomBod";
                    strSQL+="  	FROM(";
                    strSQL+="  		SELECT a2.co_empGrp, a2.co_bodGrp, a1.tx_nom";
                    strSQL+="  		FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                    strSQL+="  		ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                    strSQL+="  		WHERE a1.st_reg='A'";
                    strSQL+="           AND a2.co_empGrp=" + objParSis.getCodigoEmpresaGrupo()+ "";
                    strSQL+="           AND a2.co_bodGrp=" + txtCodBodGrp.getText() + "";
                    strSQL+="  	) AS b1";
                    strSQL+="  	INNER JOIN(";
                    strSQL+="  		SELECT a2.co_empGrp, a2.co_bodGrp, a2.co_emp, a2.co_bod";
                    strSQL+="  		FROM tbr_bodEmpBodGrp AS a2";
                    strSQL+="  	) AS b2";
                    strSQL+="  	ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                    strSQL+=" ) AS b2";
                    strSQL+=" ON b1.co_empCnf=b2.co_empBod AND b1.co_bod=b2.co_bodBod";
                    strSQL+=" GROUP BY b2.co_empBod, b2.co_bodBod, b1.co_empCnf, b1.co_locCnf, b1.co_tipDocCnf, b1.co_docCnf, b1.tx_desCorCnf, b1.tx_desLarCnf";
                    strSQL+="        , b1.ne_numDocCnf, b1.fe_docCnf, b1.co_bod, b1.tx_nomBod, b1.tx_obs1, b1.tx_obs2, b1.co_mnu";
                    strSQL+="        , b1.co_veh, b1.co_cho, b1.tx_ideTra, b1.tx_nomTra, b1.tx_plaVehTra";
                    strSQL+="        , b1.co_empGuiRem, b1.co_locGuiRem, b1.co_tipDocGuiRem, b1.co_docGuiRem, b1.ne_numDoc, b1.fe_docGuiRem";
                    strSQL+="        , b1.tx_desCorGuiRem, b1.tx_desLarGuiRem";
                    strSQL+="       , b1.tx_ptoPar, b1.co_cli, b1.tx_ruc, b1.tx_nomCli, b1.tx_dirCli, b1.tx_telCli, b1.tx_ciuCli";
                    strSQL+=" ORDER BY b1.fe_docCnf, b1.ne_numDoc";
                }

                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConCnf = new ArrayList();
                while(rst.next()){
                    arlRegConCnf = new ArrayList();
                    arlRegConCnf.add(INT_ARL_CON_COD_EMP,rst.getInt("co_empCnf"));
                    arlRegConCnf.add(INT_ARL_CON_COD_LOC,rst.getInt("co_locCnf"));
                    arlRegConCnf.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDocCnf"));
                    arlRegConCnf.add(INT_ARL_CON_COD_DOC,rst.getInt("co_docCnf"));
                    arlDatConCnf.add(arlRegConCnf);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConCnf.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConCnf.size()) + " registros");
                    intIndiceConCnf=arlDatConCnf.size()-1;
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
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if(cargarDetReg()){
                    blnRes=true;
                }
            }
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
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                if( strTipMovCnfTot.equals("E") ){//egresos
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp AS co_empCnf, a1.co_loc AS co_locCnf, a1.co_tipDoc AS co_tipDocCnf, a1.co_doc AS co_docCnf, a1.st_reg";
                    strSQL+="      , a5.tx_desCor AS tx_desCorCnf, a5.tx_desLar AS tx_desLarCnf";
                    strSQL+="      , a1.ne_numDoc AS ne_numDocCnf, a1.fe_doc AS fe_docCnf, a1.co_bod, a4.tx_nom AS tx_nomBod, a1.tx_obs1, a1.tx_obs2, a1.co_mnu";
                    strSQL+="      , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra, a8.tx_desLar AS tx_desLarVeh";
                    strSQL+="      , a3.co_emp AS co_empGuiRem, a3.co_loc AS co_locGuiRem, a3.co_tipDoc AS co_tipDocGuiRem, a3.co_doc AS co_docGuiRem";
                    strSQL+="      , CASE WHEN a3.ne_numDoc=0 THEN a3.ne_numOrdDes ELSE a3.ne_numDoc END AS ne_numGuiRem, a3.fe_doc AS fe_docGuiRem";
                    strSQL+="      , a6.tx_desCor AS tx_desCorGuiRem, a6.tx_desLar AS tx_desLarGuiRem";
                    strSQL+="      , a3.tx_ptoPar, a3.co_cliDes, a3.tx_rucCliDes, a3.tx_nomCliDes, a3.tx_dirCliDes, a3.tx_telCliDes, a3.tx_ciuCliDes";
                    strSQL+=" FROM (((((tbm_cabIngEgrMerBod AS a1 LEFT OUTER JOIN tbm_tra AS a9 ON a1.co_cho=a9.co_tra) LEFT OUTER JOIN tbm_veh AS a8 ON a8.co_veh=a1.co_veh)";
                    strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" 		INNER JOIN tbm_bod AS a4 ON a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod)";
                    strSQL+=" 			INNER JOIN ((tbm_cabGuiRem AS a3 LEFT OUTER JOIN tbm_var AS a7 ON a3.co_forRet=a7.co_reg)";
                    strSQL+=" 				INNER JOIN tbm_cabTipDoc AS a6 ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_tipDoc=a6.co_tipDoc)";
                    strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_locRelGuiRem=a3.co_loc AND a1.co_tipDocRelGuiRem=a3.co_tipDoc AND a1.co_docRelGuiRem=a3.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipDoc = " + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC)+ "";
                    strSQL+=" AND a1.co_doc = " + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar, a1.st_reg";
                    strSQL+="        , a1.ne_numDoc, a1.fe_doc, a1.co_bod, a4.tx_nom, a1.tx_obs1, a1.tx_obs2";
                    strSQL+="        , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra";
                    strSQL+="        , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.ne_numDoc, a3.fe_doc";
                    strSQL+="        , a6.tx_desCor, a6.tx_desLar, a1.co_mnu, a8.tx_desLar";
                    strSQL+="        , a3.tx_ptoPar, a3.co_cliDes, a3.tx_rucCliDes, a3.tx_nomCliDes, a3.tx_dirCliDes, a3.tx_telCliDes, a3.tx_ciuCliDes";
                    strSQL+=" ORDER BY a1.ne_numDoc, a1.fe_doc";
                }
                else if( strTipMovCnfTot.equals("I") ){//ingreso
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp AS co_empCnf, a1.co_loc AS co_locCnf, a1.co_tipDoc AS co_tipDocCnf, a1.co_doc AS co_docCnf, a5.tx_desCor AS tx_desCorCnf, a5.tx_desLar AS tx_desLarCnf";
                    strSQL+="      , a1.ne_numDoc AS ne_numDocCnf, a1.fe_doc AS fe_docCnf, a1.co_bod, a4.tx_nom AS tx_nomBod, a1.tx_obs1, a1.tx_obs2, a1.co_mnu";
                    strSQL+="      , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra, a1.st_reg";
                    strSQL+="      , a3.co_emp AS co_empGuiRem, a3.co_loc AS co_locGuiRem, a3.co_tipDoc AS co_tipDocGuiRem, a3.co_doc AS co_docGuiRem";
                    strSQL+="      , CASE WHEN a3.ne_numDoc=0 THEN a3.ne_numOrdDes ELSE a3.ne_numDoc END AS ne_numGuiRem, a3.fe_doc AS fe_docGuiRem";
                    strSQL+="      , a6.tx_desCor AS tx_desCorGuiRem, a6.tx_desLar AS tx_desLarGuiRem";
                    strSQL+="      , a3.tx_ptoPar, a3.co_cli AS co_cliDes, a3.tx_ruc, a3.tx_nomCli AS tx_nomCliDes, a3.tx_dirCli, a3.tx_telCli, a3.tx_ciuCli";
                    strSQL+="      , Null AS tx_desLarVeh";
                    strSQL+=" FROM (((tbm_cabIngEgrMerBod AS a1";
                    strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" 		INNER JOIN tbm_bod AS a4 ON a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod)";
                    strSQL+=" 			INNER JOIN (tbm_cabMovInv AS a3";
                    strSQL+=" 				INNER JOIN tbm_cabTipDoc AS a6 ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_tipDoc=a6.co_tipDoc)";
                    strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_locRel=a3.co_loc AND a1.co_tipDocRel=a3.co_tipDoc AND a1.co_docRel=a3.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipDoc = " + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+=" AND a1.co_doc = " + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar";
                    strSQL+="        , a1.ne_numDoc, a1.fe_doc, a1.co_bod, a4.tx_nom, a1.tx_obs1, a1.tx_obs2";
                    strSQL+="        , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra";
                    strSQL+="        , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.ne_numDoc, a3.fe_doc";
                    strSQL+="        , a6.tx_desCor, a6.tx_desLar, a1.co_mnu, a1.st_reg";
                    strSQL+="        , a3.tx_ptoPar, a3.co_cli, a3.tx_ruc, a3.tx_nomCli, a3.tx_dirCli, a3.tx_telCli, a3.tx_ciuCli";
                    strSQL+=" ORDER BY a1.ne_numDoc, a1.fe_doc";
                }
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDocCnf");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCorCnf");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLarCnf");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_bod");
                    txtCodBodGrp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomBod");
                    txtNomBodGrp.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_docCnf"),"dd/MM/yyyy"));
                    datFecDoc=rst.getDate("fe_docCnf");
                    strAux=rst.getString("ne_numDocCnf");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_docCnf");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_veh");
                    txtCodVeh.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_plaVehTra");
                    txtDesCorVeh.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLarVeh");
                    txtDesLarVeh.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_ideTra");
                    txtDesCorCho.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomTra");
                    txtDesLarCho.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCorGuiRem");
                    txtDesCorTipDocCon.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLarGuiRem");
                    txtDesLarTipDocCon.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cliDes");
                    txtCodCliPro.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomCliDes");
                    txtNomCliPro.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("fe_docGuiRem");
                    txtFecDocCon.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numGuiRem");
                    txtNumDocCon.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("co_docGuiRem");
                    txtCodDocCon.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }

            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceConCnf+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConCnf.size()) );
            
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                objTooBar.setMenSis("Cargando...");
                if( strTipMovCnfTot.equals("E") ){//egresos
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp AS co_empCnf, a1.co_loc AS co_locCnf, a1.co_tipDoc AS co_tipDocCnf, a1.co_doc AS co_docCnf, a2.co_reg AS co_regCnf";
                    strSQL+="      , a5.tx_desCor AS tx_desCorCnf, a5.tx_desLar AS tx_desLarCnf, a1.ne_numDoc AS ne_numDocCnf, a1.fe_doc AS fe_docCnf";
                    strSQL+="      , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra, a2.nd_can AS nd_canCnf, a2.tx_obs1";
                    strSQL+="      , a3.co_emp AS co_empGuiRem, a3.co_loc AS co_locGuiRem, a3.co_tipDoc AS co_tipDocGuiRem, a3.co_doc AS co_docGuiRem, a3.ne_numDoc, a3.fe_doc AS fe_docGuiRem";
                    strSQL+="      , a6.tx_desCor AS tx_desCorGuiRem, a6.tx_desLar AS tx_desLarGuiRem, a10.co_itm, a10.tx_codAlt";
                    strSQL+="      , (CASE WHEN a12.tx_codAlt2 IS NULL THEN a10.tx_codAlt ELSE a12.tx_codAlt2 END) AS tx_codAlt2";
                    strSQL+="      , a10.tx_nomItm, a10.tx_uniMed, a10.nd_can AS nd_canOrdDes, a10.st_merEgrFisBod AS st_merIngEgrFisBod, a10.co_reg AS co_regOrdDes";
                    strSQL+=" FROM (";
                    strSQL+="        (";
                    strSQL+=" 		( (tbm_cabIngEgrMerBod AS a1 LEFT OUTER JOIN tbm_tra AS a9 ON a1.co_cho=a9.co_tra";
                    strSQL+=" 		   ) LEFT OUTER JOIN tbm_veh AS a8 ON a1.co_veh=a8.co_veh";
                    strSQL+=" 		 ) INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc";
                    strSQL+=" 	     )";
                    strSQL+=" )";
                    strSQL+=" INNER JOIN (tbm_detIngEgrMerBod AS a2 INNER JOIN tbm_invBod AS a11 ON a2.co_emp=a11.co_emp AND a2.co_bod=a11.co_bod AND a2.co_itm=a11.co_itm";
                    strSQL+="             INNER JOIN tbm_inv AS a12 ON a11.co_emp=a12.co_emp AND a11.co_itm=a12.co_itm)";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbm_detGuiRem AS a10";
                    strSQL+=" ON a2.co_emp=a10.co_emp AND a2.co_locRelGuiRem=a10.co_loc AND a2.co_tipDocRelGuiRem=a10.co_tipDoc AND a2.co_docRelGuiRem=a10.co_doc AND a2.co_regRelGuiRem=a10.co_reg";
                    strSQL+=" 			INNER JOIN ((tbm_cabGuiRem AS a3 LEFT OUTER JOIN tbm_var AS a7 ON a3.co_forRet=a7.co_reg)";
                    strSQL+=" 				INNER JOIN tbm_cabTipDoc AS a6 ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_tipDoc=a6.co_tipDoc)";
                    strSQL+=" ON a10.co_emp=a3.co_emp AND a10.co_loc=a3.co_loc AND a10.co_tipDoc=a3.co_tipDoc AND a10.co_doc=a3.co_doc";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" AND a1.st_reg NOT IN('E')";
                    strSQL+=" ORDER BY a2.co_reg";
                }
                else if( strTipMovCnfTot.equals("I") ){//ingresos
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp AS co_empCnf, a1.co_loc AS co_locCnf, a1.co_tipDoc AS co_tipDocCnf, a1.co_doc AS co_docCnf";
                    strSQL+="      , a5.tx_desCor AS tx_desCorCnf, a5.tx_desLar AS tx_desLarCnf, a1.ne_numDoc AS ne_numDocCnf, a1.fe_doc AS fe_docCnf";
                    strSQL+="      , a1.co_veh, a1.co_cho, a1.tx_ideTra, a1.tx_nomTra, a1.tx_plaVehTra, a2.nd_can AS nd_canCnf, a2.tx_obs1";
                    strSQL+="      , a3.co_emp AS co_empGuiRem, a3.co_loc AS co_locGuiRem, a3.co_tipDoc AS co_tipDocGuiRem, a3.co_doc AS co_docGuiRem, a3.ne_numDoc, a3.fe_doc AS fe_docGuiRem";
                    strSQL+="      , a6.tx_desCor AS tx_desCorGuiRem, a6.tx_desLar AS tx_desLarGuiRem";
                    strSQL+="      , a10.co_itm, a10.tx_codAlt, a10.tx_nomItm, a10.tx_uniMed, a10.nd_can AS nd_canOrdDes";
                    strSQL+="      , (CASE WHEN a12.tx_codAlt2 IS NULL THEN a10.tx_codAlt ELSE a12.tx_codAlt2 END) AS tx_codAlt2";
                    strSQL+="      , a2.co_reg, a10.st_meringegrfisbod, a10.co_reg AS co_regOrdDes";
                    strSQL+=" FROM((tbm_cabIngEgrMerBod AS a1	INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc))";
                    strSQL+=" INNER JOIN (tbm_detIngEgrMerBod AS a2 INNER JOIN tbm_invBod AS a11 ON a2.co_emp=a11.co_emp AND a2.co_bod=a11.co_bod AND a2.co_itm=a11.co_itm";
                    strSQL+=" 					INNER JOIN tbm_inv AS a12 ON a11.co_emp=a12.co_emp AND a11.co_itm=a12.co_itm)";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbm_detMovInv AS a10";
                    strSQL+=" ON a2.co_emp=a10.co_emp AND a2.co_locRel=a10.co_loc AND a2.co_tipDocRel=a10.co_tipDoc AND a2.co_docRel=a10.co_doc AND a2.co_regRel=a10.co_reg";
                    strSQL+=" INNER JOIN (tbm_cabMovInv AS a3 INNER JOIN tbm_cabTipDoc AS a6 ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_tipDoc=a6.co_tipDoc)";
                    strSQL+=" ON a10.co_emp=a3.co_emp AND a10.co_loc=a3.co_loc AND a10.co_tipDoc=a3.co_tipDoc AND a10.co_doc=a3.co_doc";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" AND a1.st_reg NOT IN('E')";
                    strSQL+=" ORDER BY a2.co_reg";
                }
                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,                     "");
                    vecReg.add(INT_TBL_DAT_CHK,                     new Boolean(true));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,             "");
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,             "");
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,             rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,             rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_LET_ITM,             rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,                 rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED_ITM,             rst.getString("tx_uniMed"));
                    vecReg.add(INT_TBL_DAT_CAN_DOC_ORI_CNF,         "");
                    vecReg.add(INT_TBL_DAT_CAN_TOT_CNF,             "");
                    vecReg.add(INT_TBL_DAT_CAN_PUE_CNF,             "");
                    vecReg.add(INT_TBL_DAT_CAN_CNF_USR,             rst.getBigDecimal("nd_canCnf").abs());
                    vecReg.add(INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC, "");
                    vecReg.add(INT_TBL_DAT_OBS,                     rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_DAT_EST_ING_EGR_MER_BOD,     rst.getString("st_merIngEgrFisBod"));
                    vecReg.add(INT_TBL_DAT_COD_EMP_ORD_DES,         rst.getString("co_empGuiRem"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_ORD_DES,         rst.getString("co_locGuiRem"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ORD_DES,     rst.getString("co_tipDocGuiRem"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_ORD_DES,         rst.getString("co_docGuiRem"));
                    vecReg.add(INT_TBL_DAT_COD_REG_ORD_DES,         rst.getString("co_regOrdDes"));
                    vecReg.add(INT_TBL_DAT_COD_EMP_DOC_ORI,         "");
                    vecReg.add(INT_TBL_DAT_COD_LOC_DOC_ORI,         "");
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ORI,         "");
                    vecReg.add(INT_TBL_DAT_COD_DOC_ORI,             "");
                    vecReg.add(INT_TBL_DAT_COD_REG_DOC_ORI,         "");
                    vecReg.add(INT_TBL_DAT_CAN_PUE_CNF_TIE_EJE,     "");
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
                objTooBar.setMenSis("Listo");
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
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        if (txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        if(objTooBar.getEstado()=='n'){
            if(!objTblMod.isDataModelChanged()){
                tabFrm.setSelectedIndex(1);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblMod.getRowCountTrue()<=0){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.removeEmptyRows();
                tabFrm.setSelectedIndex(1);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
            
            int intNumFilSel=0;
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    intNumFilSel++;
                }
            }
            if(intNumFilSel<=0){
                tabFrm.setSelectedIndex(1);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
            
            
            
        }

        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=canChangeDate();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    System.out.println("POR YES");
                                    return true;
                                case 1: //NO_OPTION
                                    System.out.println("POR NO");
                                    return false;
                                case 2: //CANCEL_OPTION
                                    System.out.println("POR CANCEL");
                                    return false;
                            }
                            datFecAux=null;
                        }
                    }
                    break;
                case 1://no puede cambiarla para nada
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
//        if(isExisteItemRepetido()){
//            tabFrm.setSelectedIndex(1);
//            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
//            return false;  
//        }
        
        if( (objParSis.getCodigoMenu()==4051) ){//sólo cuando es egreso de despacho se adiciona datos del chofer y camión
            if( (!optEnv.isSelected()) && (!optRet.isSelected()) ){
                tabFrm.setSelectedIndex(2);
                mostrarMsgInf("<HTML>Ingrese los datos del despacho<BR>Verifique y vuelva a intentarlo.</HTML>");
                return false;
            }
            else{
                if(optEnv.isSelected()){
                    if( (txtCodVeh.getText().equals("")) || (txtCodCho.getText().equals("")) ){
                        tabFrm.setSelectedIndex(2);
                        mostrarMsgInf("<HTML>Ingrese los datos del despacho<BR>Verifique y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }
                else if(optRet.isSelected()){
                    if( (txtIdeTra.getText().equals("")) || (txtNomTra.getText().equals("")) || (txtPlaVehTra.getText().equals("")) ){
                        tabFrm.setSelectedIndex(2);
                        mostrarMsgInf("<HTML>Ingrese los datos del despacho<BR>Verifique y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }
            }
        }
        
        
//        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//            if(verificaItmPndCnf()){
//                
//            }
//        }


        
        
        return true;
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
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                }

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
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

    /**
     * Esta función muestra la bodega predeterminada del usuario.
     * @return true: Si se pudo mostrar la bodega predeterminada.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarBodPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += " SELECT a3.co_bodGrp as co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " INNER JOIN tbr_bodTipDocPrgUsr AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod ";
                strSQL += " INNER JOIN tbr_bodEmpBodGrp as a3 ";
                strSQL += " ON (a3.co_emp=a2.co_emp and a3.co_bod=a2.co_bod) ";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL += " AND a2.st_reg IN('P')";

                if((objParSis.getCodigoMenu()==INT_COD_MNU_COINBO)  || (objParSis.getCodigoMenu()==INT_COD_MNU_COINDE))
                {
                    strSQL += " AND a2.tx_natBod IN('I','A')";
                }
                else if((objParSis.getCodigoMenu()==INT_COD_MNU_COEGBO)  || (objParSis.getCodigoMenu()==INT_COD_MNU_COEGDE))
                {
                    strSQL += " AND a2.tx_natBod IN('E','A')";
                }
                //System.out.println("mostrarBodPre: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodBodGrp.setText(rst.getString("co_bod"));
                    txtNomBodGrp.setText(rst.getString("tx_nom"));
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
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
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

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                        
                        txtCodBodGrp.setText("");
                        txtNomBodGrp.setText("");
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
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
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                            
                            txtCodBodGrp.setText("");
                            txtNomBodGrp.setText("");
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
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                            
                            txtCodBodGrp.setText("");
                            txtNomBodGrp.setText("");

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

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }


    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt="", strNomRpt="", strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        strAux="";
        String strSQLRep="", strSQLSubRep="", strNomPrnSer="", strSubRepDir="";
        int intCodBod = 0;
        String strUsu="";
        
        javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet;
        JasperPrint reportGuiaRem;
        javax.print.attribute.standard.PrinterName printerName;
        javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet;
        net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp;
        Connection conRpt;
        
        try
        {
            conRpt=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conRpt!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                                      
                    if(objTooBar.getEstado()=='c' || objTooBar.getEstado()=='w'  || objTooBar.getEstado()=='j')
                    {
                        if( intCodEmpDocCnf== 0 )
                        {
                            intCodEmpDocCnf = objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP);
                        }
                        if( intCodLocDocCnf== 0 )
                        {
                            intCodLocDocCnf = objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC);
                        }
                    }       
                    
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){

                            datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            if (datFecAux == null) {
                                return false;
                            }
                            strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                            datFecAux = null;
                            
                            //Creacion del mapa de parametros
                            java.util.Map mapPar = new java.util.HashMap();

                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                
                                /////////////////////////////////////////////////////
                                case 480://Orden de Egreso
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    stm = conRpt.createStatement();
                                    
                                    strSQLRep =" SELECT a1.tx_DesCor, a1.tx_DesLar, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.fe_doc,";
                                    strSQLRep+="        a.co_bod as CodBodEmp, b.tx_nom as NomBod, a.st_imp, a.co_usrIng, a2.tx_nom as NomUsr, a.tx_obs1,  bg.co_bodGrp as CodBodGrp,";
                                    strSQLRep+="        a4.tx_DesCor ||'# '|| a3.ne_numOrdDes  as tx_datDocOri, CAST ('EGRESO' AS CHARACTER VARYING) as tx_titulo "; 
                                    strSQLRep+=" FROM tbm_cabingegrmerbod as a "; 
                                    strSQLRep+=" INNER JOIN tbm_cabTipDoc as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc) "; 
                                    strSQLRep+=" INNER JOIN tbm_usr as a2 ON (a2.co_usr=a.co_usrIng) "; 
                                    strSQLRep+=" INNER JOIN tbm_bod as b ON (b.co_emp=a.co_emp AND b.co_bod=a.co_bod ) "; 
                                    strSQLRep+=" INNER JOIN tbr_bodEmpBodGrp AS bg ON (bg.co_emp=a.co_emp AND bg.co_bod=a.co_bod)   "; 
                                    strSQLRep+=" INNER JOIN tbm_cabGuiRem as a3 ON (a3.co_Emp=a.co_Emp AND a3.co_loc=a.co_locRelGuiRem AND a3.co_tipDoc=a.co_tipDocRelGuiRem  AND a3.co_doc=a.co_docRelGuiRem)  "; 
                                    strSQLRep+=" INNER JOIN tbm_cabTipDoc as a4 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc)   "; 
                                    strSQLRep+=" WHERE a.co_emp=" +intCodEmpDocCnf;
                                    strSQLRep+=" AND a.co_loc=" +intCodLocDocCnf;
                                    strSQLRep+=" AND a.co_tipDoc=" +txtCodTipDoc.getText() ;
                                    strSQLRep+=" AND a.co_doc=" +txtCodDoc.getText(); 

                                    strSQLSubRep ="";
                                    strSQLSubRep+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_itm, a.tx_codAlt";
                                    strSQLSubRep+="     , (CASE WHEN a.tx_codAlt2 IS NULL THEN a.tx_codAlt ELSE a.tx_codAlt2 END) as tx_codAlt2, a.tx_nomItm,  a.UniMed, a.nd_can";
                                    strSQLSubRep+=" FROM (";
                                    strSQLSubRep+="      SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_itm, inv.tx_codAlt, ";
                                    strSQLSubRep+="            (substring(inv.tx_codAlt2 from 1 for 1 ) || ' ' || substring(inv.tx_codAlt2 from 2 for 1 )|| ' ' || substring(inv.tx_codAlt2 from 3 for 1 )) as tx_codAlt2 , ";   
                                    strSQLSubRep+="             inv.tx_nomItm,  a1.tx_DesCor as UniMed, abs (a.nd_can) as nd_can ";
                                    strSQLSubRep+="      FROM tbm_detingegrmerbod as a ";
                                    strSQLSubRep+="      INNER JOIN tbm_inv as inv ON (inv.co_emp=a.co_Emp AND inv.co_itm=a.co_itm) ";
                                    strSQLSubRep+="      LEFT OUTER JOIN tbm_var as a1 ON (a1.co_reg=inv.co_uni)  ";
                                    strSQLSubRep+="      WHERE a.co_emp=" +intCodEmpDocCnf;
                                    strSQLSubRep+="      AND a.co_loc=" +intCodLocDocCnf;
                                    strSQLSubRep+="      AND a.co_tipDoc=" +txtCodTipDoc.getText() ;
                                    strSQLSubRep+="      AND a.co_doc=" +txtCodDoc.getText();       
                                    strSQLSubRep+=" ) as a ";
                                    strSQLSubRep+=" ORDER BY a.co_Reg ";    

                                    System.out.println("imprimirOrdenEgreso-strSQLRep:  " + strSQLRep);
                                    System.out.println("imprimirOrdenEgreso-strSQLSubRep:  " + strSQLSubRep);
                                    rst=stm.executeQuery(strSQLRep);
                                    if (rst.next()){
                                        intCodBod=rst.getInt("CodBodGrp");
                                    }
                                    if (conRpt!=null){
                                        strNomPrnSer = getPrintServer(blnPruebas,intCodBod );
                                    }
                                    System.out.println("strNomPrnSer-OrdenEgreso: "+strNomPrnSer);

                                    //Inicializar los parametros que se van a pasar al reporte.                                    
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strFecImp",  strFecHorSer );
                                    mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - " + strNomRpt + "  ");
                                    break;
                                    
                                /////////////////////////////////////////////////////
                                case 481://Orden de Ingreso
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    stm = conRpt.createStatement();

                                    strSQLRep =" SELECT a1.tx_DesCor, a1.tx_DesLar, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.fe_doc,";
                                    strSQLRep+="        a.co_bod as CodBodEmp, b.tx_nom as NomBod, a.st_imp, a.co_usrIng, a2.tx_nom as NomUsr, a.tx_obs1,  bg.co_bodGrp as CodBodGrp,";
                                    strSQLRep+="        a4.tx_DesCor ||'# '|| a3.ne_numDoc  as tx_datDocOri, ";
                                    if(objParSis.getCodigoMenu()==4000)
                                       strSQLRep+="        CAST ('INGRESO (DESPACHO)' AS CHARACTER VARYING) as tx_titulo "; 
                                    else
                                       strSQLRep+="        CAST ('INGRESO' AS CHARACTER VARYING) as tx_titulo "; 
                                    strSQLRep+=" FROM tbm_cabingegrmerbod as a "; 
                                    strSQLRep+=" INNER JOIN tbm_cabTipDoc as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc) "; 
                                    strSQLRep+=" INNER JOIN tbm_usr as a2 ON (a2.co_usr=a.co_usrIng) "; 
                                    strSQLRep+=" INNER JOIN tbm_bod as b ON (b.co_emp=a.co_emp AND b.co_bod=a.co_bod ) "; 
                                    strSQLRep+=" INNER JOIN tbr_bodEmpBodGrp AS bg ON (bg.co_emp=a.co_emp AND bg.co_bod=a.co_bod)   "; 
                                    strSQLRep+=" INNER JOIN tbm_cabMovInv as a3 ON (a3.co_Emp=a.co_Emp AND a3.co_loc=a.co_locRel AND a3.co_tipDoc=a.co_tipDocRel AND a3.co_doc=a.co_docRel) "; 
                                    strSQLRep+=" INNER JOIN tbm_cabTipDoc as a4 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc)   "; 
                                    strSQLRep+=" WHERE a.co_emp=" +intCodEmpDocCnf;
                                    strSQLRep+=" AND a.co_loc=" +intCodLocDocCnf;
                                    strSQLRep+=" AND a.co_tipDoc=" +txtCodTipDoc.getText() ;
                                    strSQLRep+=" AND a.co_doc=" +txtCodDoc.getText(); 
                                    
                                    strSQLSubRep ="";
                                    strSQLSubRep+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_itm, a.tx_codAlt";
                                    strSQLSubRep+="     , (CASE WHEN a.tx_codAlt2 IS NULL THEN a.tx_codAlt ELSE a.tx_codAlt2 END) as tx_codAlt2, a.tx_nomItm,  a.UniMed, a.nd_can";
                                    strSQLSubRep+=" FROM (";
                                    strSQLSubRep+="      SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_itm, inv.tx_codAlt, ";
                                    strSQLSubRep+="            (substring(inv.tx_codAlt2 from 1 for 1 ) || ' ' || substring(inv.tx_codAlt2 from 2 for 1 )|| ' ' || substring(inv.tx_codAlt2 from 3 for 1 )) as tx_codAlt2 , ";   
                                    strSQLSubRep+="             inv.tx_nomItm,  a1.tx_DesCor as UniMed, abs (a.nd_can) as nd_can ";
                                    strSQLSubRep+="      FROM tbm_detingegrmerbod as a ";
                                    strSQLSubRep+="      INNER JOIN tbm_inv as inv ON (inv.co_emp=a.co_Emp AND inv.co_itm=a.co_itm) ";
                                    strSQLSubRep+="      LEFT OUTER JOIN tbm_var as a1 ON (a1.co_reg=inv.co_uni)  ";
                                    strSQLSubRep+="      WHERE a.co_emp=" +intCodEmpDocCnf;
                                    strSQLSubRep+="      AND a.co_loc=" +intCodLocDocCnf;
                                    strSQLSubRep+="      AND a.co_tipDoc=" +txtCodTipDoc.getText() ;
                                    strSQLSubRep+="      AND a.co_doc=" +txtCodDoc.getText();       
                                    strSQLSubRep+=" ) as a ";
                                    strSQLSubRep+=" ORDER BY a.co_Reg ";

                                    System.out.println("imprimirOrdenIngreso-strSQLRep:  " + strSQLRep);
                                    System.out.println("imprimirOrdenIngreso-strSQLSubRep:  " + strSQLSubRep);
                                    rst=stm.executeQuery(strSQLRep);
                                    if (rst.next()){
                                        intCodBod=rst.getInt("CodBodGrp");
                                    }
                                    if (conRpt!=null){
                                        strNomPrnSer = getPrintServer(blnPruebas,intCodBod );
                                    }
                                    System.out.println("strNomPrnSer-OrdenIngreso: "+strNomPrnSer);

                                    //Inicializar los parametros que se van a pasar al reporte.                                    
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strFecImp",  strFecHorSer );
                                    mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - " + strNomRpt + "  ");
                                    break;
                                    
                                /////////////////////////////////////////////////////    
                                case 476://Orden de Almacenamiento
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    stm=conRpt.createStatement();
                                    strSQLRep ="";
                                    strSQLRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, ";
                                    strSQLRep+="        CASE WHEN a5.st_impord='S' THEN a4.tx_codAlt2 ELSE '' END AS tx_codAlt, ";
                                    strSQLRep+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm, ";
                                    strSQLRep+="        a2.nd_can,a5.tx_ubi,a6.tx_desLar as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc, ";
                                    strSQLRep+="        a9.ne_numDoc,a3.tx_desLar as tx_desTipDoc, b1.co_usr, b1.tx_usr, b1.tx_nom AS tx_nomUsrIng";
                                    strSQLRep+=" FROM (tbm_cabIngEgrMerBod  as a1 INNER JOIN tbm_usr AS b1 ON a1.co_usrIng=b1.co_usr)";
                                    strSQLRep+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc)";
                                    strSQLRep+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc)";
                                    strSQLRep+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                                    strSQLRep+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm)";
                                    strSQLRep+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg)";
                                    strSQLRep+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp)";
                                    strSQLRep+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc)";
                                    strSQLRep+=" INNER JOIN tbm_cabMovInv as a9 ON (a1.co_emp=a9.co_emp AND a1.co_locRel=a9.co_loc AND a1.co_tipDocRel=a9.co_tipDoc AND a1.co_docRel=a9.co_doc)";
                                    strSQLRep+=" WHERE a1.co_emp=" + intCodEmpDocCnf + "";
                                    strSQLRep+=" AND a1.co_loc=" + intCodLocDocCnf + "";
                                    strSQLRep+=" AND a1.co_tipDoc = " + txtCodTipDoc.getText() + "";
                                    strSQLRep+=" AND a1.co_doc = " + txtCodDoc.getText() + "";
                                    strSQLRep+=" AND a2.nd_can NOT IN(0)";
                                    
                                    System.out.println("imprimirOrdenAlmacenamiento: " + strSQLRep);
                                    
                                    rst=stm.executeQuery(strSQLRep);
                                    if(rst.next()){
                                        strUsu=rst.getString("tx_usr");
                                    }
                                                                        
                                    if (conRpt!=null){
                                        strNomPrnSer=getPrintServer(blnPruebas, Integer.parseInt(txtCodBodGrp.getText()) );
                                    }
                                    System.out.println("strNomPrnSer-OrdenAlmacenamiento: "+strNomPrnSer);
                                    System.out.println("strSQLRep.......: "+strSQLRep);
                                    System.out.println("strCamAudRpt.......: "+strUsu+"    strFecHorSer: "+strFecHorSer);
                                    System.out.println("strNomRpt.....: "+strNomRpt);
                                    System.out.println("strRutRpt....: "+strRutRpt);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strCamAudRpt", "" + strUsu + "    " + strFecHorSer + "");
                                    break;
                                    
                                /////////////////////////////////////////////////////    
                                case 460://Orden de Almacenamiento (Nuevo)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    stm=conRpt.createStatement();
                                    strSQLRep ="";
                                    strSQLRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc as co_doctra, a1.ne_numDoc as ne_numdocTra, a1.fe_doc as fe_doctra, a4.tx_desCor as tx_descortra";
                                    strSQLRep+="      , a3.co_emp AS co_empSolTraInv, a3.co_loc AS co_locSolTraInv, a3.co_tipDoc AS co_tipDocSolTraInv";
                                    strSQLRep+="      , a3.ne_numDoc AS ne_numDocSolTraInv, a5.tx_desCor AS tx_desCorSolTraInv, a3.fe_doc AS fe_docSolTraInv";
                                    strSQLRep+="      , a3.fe_aut, a10.co_usrIng, a6.tx_usr, a6.tx_nom AS tx_nomUsr";
                                    strSQLRep+="      ,'Orden de Almacenamiento' AS tx_nomRpt, a7.co_bod AS co_bodOrg, a8.tx_nom AS tx_nomBodOrg";
                                    strSQLRep+=" FROM (";
                                    strSQLRep+="        (tbm_cabMovInv AS a1 INNER JOIN tbm_loc AS a2 ON a1.co_emp=a2.co_emp";
                                    strSQLRep+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc";
                                    strSQLRep+=" 	INNER JOIN tbm_cabIngEgrMerBod as a10 ON (a10.co_emp=a1.co_emp AND a10.co_locRel=a1.co_loc AND a10.co_tipDocRel=a1.co_tipDoc AND a10.co_docRel=a1.co_doc)";	
                                    strSQLRep+="     	LEFT OUTER JOIN tbm_usr AS a6 ON a10.co_usrIng=a6.co_usr";
                                    strSQLRep+="        )";
                                    strSQLRep+="        INNER JOIN (tbm_detMovInv AS a7 INNER JOIN tbm_bod AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod)";
                                    strSQLRep+="        ON a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc AND a1.co_doc=a7.co_doc)";
                                    strSQLRep+=" LEFT OUTER JOIN ((tbm_cabSolTraInv AS a3 INNER JOIN tbm_cabTipDoc AS a5 ON a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipDoc=a5.co_tipDoc))";
                                    strSQLRep+=" ON a1.co_empRelCabSolTraInv=a3.co_emp AND a1.co_locRelCabSolTraInv=a3.co_loc AND a1.co_tipDocRelCabSolTraInv=a3.co_tipDoc AND a1.co_docRelCabSolTraInv=a3.co_doc";
                                    strSQLRep+=" WHERE a1.st_reg='A' AND a7.nd_can>0 "; 
                                    strSQLRep+=" AND  a10.co_emp=" + intCodEmpDocCnf + "";
                                    strSQLRep+=" AND a10.co_loc= "+ intCodLocDocCnf + "";
                                    strSQLRep+=" AND a10.co_tipDoc="+ txtCodTipDoc.getText() + "";
                                    strSQLRep+=" AND a10.co_doc="+ txtCodDoc.getText() + "";
                                    strSQLRep+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                                    strSQLRep+="        , a4.tx_desCor, a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.ne_numDoc";
                                    strSQLRep+="        , a5.tx_desCor, a3.fe_doc, a3.fe_aut, a10.co_usrIng, a6.tx_usr, a6.tx_nom, a7.co_bod, a8.tx_nom";
                                    
                                    strSQLSubRep ="";
                                    strSQLSubRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.tx_codAlt2, ABS(a1.nd_can) AS nd_can, a1.tx_uniMed, a2.tx_ubi";
                                    strSQLSubRep+=" FROM tbm_detMovInv AS a1 INNER JOIN tbm_invBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod AND a1.co_itm=a2.co_itm";
                                    strSQLSubRep+=" INNER JOIN tbm_detIngEgrMerBod as a3 ON (a3.co_emp=a1.co_emp AND a3.co_locRel=a1.co_loc AND a3.co_tipDocRel=a1.co_tipDoc AND a3.co_docRel=a1.co_doc AND a3.co_regRel=a1.co_reg)";
                                    strSQLSubRep+=" WHERE a1.nd_can>0 ";
                                    strSQLSubRep+=" AND  a3.co_emp=" + intCodEmpDocCnf + "";
                                    strSQLSubRep+=" AND a3.co_loc=" + intCodLocDocCnf + "";
                                    strSQLSubRep+=" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + "";
                                    strSQLSubRep+=" AND a3.co_doc=" + txtCodDoc.getText() + "";
                                    strSQLSubRep+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.tx_codAlt2, a1.nd_can, a1.tx_uniMed, a2.tx_ubi";
                                    strSQLSubRep+=" ORDER BY a1.co_reg";
                              
                                    System.out.println("imprimirOrdenAlmacenamiento.Nuevo.Cabecera: " + strSQLRep);
                                    System.out.println("imprimirOrdenAlmacenamiento.Nuevo.Detalle: " + strSQLSubRep);
                                       
                                    rst=stm.executeQuery(strSQLRep);
                                    if (rst.next()){
                                        intCodBod=rst.getInt("co_bodOrg");
                                    }
                                    if (conRpt!=null){
                                        strNomPrnSer = getPrintServer(blnPruebas, intCodBod );
                                    }
                                    System.out.println("strNomPrnSer-OrdenAlmacenamiento.Nuevo: "+strNomPrnSer);
                                    
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strFecImp",  strFecHorSer );
                                    mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - " + strNomRpt + "  ");
                                    break;                                    

                                default:
                                    break;    

                            }
                            
                            if(intTipRpt==2) //Vista Preliminar
                            {
                                if (objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt)) 
                                {
                                    System.out.println("Se genero reporte de Confirmaciones");
                                }
                            }
                            else  //Impresión Automática.
                            {
                                System.out.println("Impresión Automática");
                                objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                                reportGuiaRem =JasperFillManager.fillReport((strRutRpt+strNomRpt), mapPar,  conRpt);
                                printerName=new javax.print.attribute.standard.PrinterName(strNomPrnSer , null);
                                printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                                printServiceAttributeSet.add(printerName);
                                objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                                objJRPSerExp.exportReport();
                            }
                            stm.close();
                            stm=null;
                            rst.close();
                            rst=null;
                        }
                    }
                }
                conRpt.close();
                conRpt=null;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() {
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
                if(objParSis.getCodigoUsuario()==1){
                    strSQL = "";
                    strSQL += " SELECT a1.co_bod, a1.tx_nom";
                    strSQL += " FROM tbm_bod AS a1";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+ "";
                    strSQL += " AND a1.st_reg='A'";
                    strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
                }
                else{
                    strSQL = "";
                    strSQL += " SELECT a3.co_bodGrp as co_bod, a1.tx_nom";
                    strSQL += " FROM tbm_bod AS a1";
                    strSQL += " INNER JOIN tbr_bodTipDocPrgUsr AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod ";
                    strSQL += " INNER JOIN tbr_bodEmpBodGrp as a3 ";
                    strSQL += " ON (a3.co_emp=a2.co_emp and a3.co_bod=a2.co_bod) ";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL += " AND a1.st_reg='A'";
                    strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL += " AND a2.st_reg IN('A','P')";
                    
                    if((objParSis.getCodigoMenu()==INT_COD_MNU_COINBO)  || (objParSis.getCodigoMenu()==INT_COD_MNU_COINDE))
                    {
                        strSQL += " AND a2.tx_natBod IN('I','A')";
                    }
                    else if((objParSis.getCodigoMenu()==INT_COD_MNU_COEGBO)  || (objParSis.getCodigoMenu()==INT_COD_MNU_COEGDE))
                    {
                        strSQL += " AND a2.tx_natBod IN('E','A')";
                    }

                }

                vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega", strSQL, arlCam, arlAli, arlAncCol);
                arlCam=null;
                arlAli=null;
                arlAncCol=null;
                //Configurar columnas.
                vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConBod(int intTipBus) {
        boolean blnRes = true;
        try {
                vcoBod.limpiar();
                if(objParSis.getCodigoUsuario()!=1){
                    String strMe="";
                    strMe += " AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strMe += " ORDER BY a1.co_bod, a1.tx_nom";
                    vcoBod.setCondicionesSQL(strMe);
                }
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.

                        vcoBod.setCampoBusqueda(2);
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBodGrp.setText(vcoBod.getValueAt(1));
                            txtNomBodGrp.setText(vcoBod.getValueAt(2));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBod.buscar("a1.co_bod", txtCodBodGrp.getText())) {
                            txtCodBodGrp.setText(vcoBod.getValueAt(1));
                            txtNomBodGrp.setText(vcoBod.getValueAt(2));
                        } else {
                            vcoBod.setCampoBusqueda(0);
                            vcoBod.setCriterio1(11);
                            vcoBod.cargarDatos();
                            vcoBod.show();
                            if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                                txtCodBodGrp.setText(vcoBod.getValueAt(1));
                                txtNomBodGrp.setText(vcoBod.getValueAt(2));
                            } else {
                                txtCodBodGrp.setText(strCodBod);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBod.buscar("a1.tx_nom", txtNomBodGrp.getText())) {
                            txtCodBodGrp.setText(vcoBod.getValueAt(1));
                            txtNomBodGrp.setText(vcoBod.getValueAt(2));
                        } else {
                            vcoBod.setCampoBusqueda(1);
                            vcoBod.setCriterio1(11);
                            vcoBod.cargarDatos();
                            vcoBod.show();
                            if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                                txtCodBodGrp.setText(vcoBod.getValueAt(1));
                                txtNomBodGrp.setText(vcoBod.getValueAt(2));
                            } else {
                                txtNomBodGrp.setText(strNomBod);
                            }
                        }
                        break;
                        
                        
                    case 3: //Búsqueda directa por "Código".
                        if (vcoBod.buscar("a1.co_bod", txtCodBodGrp.getText())) {
                            txtCodBodGrp.setText(vcoBod.getValueAt(1));
                            txtNomBodGrp.setText(vcoBod.getValueAt(2));
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
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veeces
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemRepetido(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblMod.getRowCountTrue(); j++){
                    if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_ITM_EMP, j, INT_TBL_DAT_COD_ITM_EMP)){
                        blnRes=true;
                        break;
                    }

                }
                if(blnRes)
                    break;
            }
            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vehículos".
     *
     */
    private boolean configurarVenConVeh(){
        boolean blnRes = true;
        try{
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_veh");
            arlCam.add("a1.tx_pla");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.nd_pesSopKgr");
            arlCam.add("a2.co_tra");
            arlCam.add("a2.tx_ide");
            arlCam.add("a2.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Placa");
            arlAli.add("Vehículo");
            arlAli.add("Peso (Kg)");
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_veh, a1.tx_pla, a1.tx_desLar, round(a1.nd_pessopkgr,2) AS nd_pessopkgr, a2.co_tra, a2.tx_ide, tx_nom || ' ' || tx_ape AS tx_nom ";
            strSQL += " FROM tbm_veh AS a1 LEFT JOIN tbm_tra AS a2 ON (a1.co_cho = a2.co_tra)";
            strSQL += " WHERE a1.st_reg = 'A' AND a1.tx_tipveh = 'C' order by a1.tx_desLar";
            //Ocultar columnas.
            int intColOcu[] = new int[4];
            intColOcu[0] = 1;
            intColOcu[1] = 5;
            intColOcu[2] = 6;
            intColOcu[3] = 7;
            vcoVeh = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vehículos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoVeh.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
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
    private boolean mostrarVenConVeh(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoVeh.setCampoBusqueda(1);
                    vcoVeh.show();
                    if (vcoVeh.getSelectedButton()==vcoVeh.INT_BUT_ACE){
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtDesCorVeh.setText(vcoVeh.getValueAt(2));
                        txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                        txtPesVeh.setText(vcoVeh.getValueAt(4));
                        txtCodCho.setText(vcoVeh.getValueAt(5));
                        txtDesCorCho.setText(vcoVeh.getValueAt(6));
                        txtDesLarCho.setText(vcoVeh.getValueAt(7));
                        optEnv.setSelected(true);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoVeh.buscar("a1.tx_pla", txtDesCorVeh.getText()))//Se cambio a tx_pla Bostel
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtDesCorVeh.setText(vcoVeh.getValueAt(2));
                        txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                        txtPesVeh.setText(vcoVeh.getValueAt(4));
                        txtCodCho.setText(vcoVeh.getValueAt(5));
                        txtDesCorCho.setText(vcoVeh.getValueAt(6));
                        txtDesLarCho.setText(vcoVeh.getValueAt(7));
                        optEnv.setSelected(true);
                    }
                    else
                    {
                        vcoVeh.setCampoBusqueda(1);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.show();
                        if (vcoVeh.getSelectedButton()==vcoVeh.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                            txtDesCorVeh.setText(vcoVeh.getValueAt(2));
                            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                            txtPesVeh.setText(vcoVeh.getValueAt(4));
                            txtCodCho.setText(vcoVeh.getValueAt(5));
                            txtDesCorCho.setText(vcoVeh.getValueAt(6));
                            txtDesLarCho.setText(vcoVeh.getValueAt(7));
                            optEnv.setSelected(true);
                        }
                        else
                        {
                            txtDesCorVeh.setText(strDesCorVeh);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoVeh.buscar("a1.tx_desLar", txtDesLarVeh.getText()))
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtDesCorVeh.setText(vcoVeh.getValueAt(2));
                        txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                        txtPesVeh.setText(vcoVeh.getValueAt(4));
                        txtCodCho.setText(vcoVeh.getValueAt(5));
                        txtDesCorCho.setText(vcoVeh.getValueAt(6));
                        txtDesLarCho.setText(vcoVeh.getValueAt(7));
                        optEnv.setSelected(true);
                    }
                    else
                    {
                        vcoVeh.setCampoBusqueda(2);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.show();
                        if (vcoVeh.getSelectedButton()==vcoVeh.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                            txtDesCorVeh.setText(vcoVeh.getValueAt(2));
                            txtDesLarVeh.setText(vcoVeh.getValueAt(3));
                            txtPesVeh.setText(vcoVeh.getValueAt(4));
                            txtCodCho.setText(vcoVeh.getValueAt(5));
                            txtDesCorCho.setText(vcoVeh.getValueAt(6));
                            txtDesLarCho.setText(vcoVeh.getValueAt(7));
                            optEnv.setSelected(true);
                        }
                        else
                        {
                            txtDesLarVeh.setText(strDesLarVeh);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Choferes".
     *
     */
    private boolean configurarVenConCho()
    {
        boolean blnRes = true;
        try{
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_tra, a1.tx_ide, a1.tx_nom || ' ' || a1.tx_ape AS tx_nom ";
            strSQL += " FROM tbm_tra AS a1";
            strSQL += " INNER JOIN tbm_traEmp AS a2 ON (a2.co_tra = a1.co_tra)";
            strSQL += " INNER JOIN tbm_carLab AS a3 ON (a3.co_car = a2.co_car)";
            strSQL += " INNER JOIN tbm_carLabPre AS a4 ON (a4.co_carpre = a3.co_carpre)"; //Indica solo las personas que tengan cargo Chofer/Empacador Cargador.
            strSQL += " WHERE a1.st_reg = 'A'  ";
            strSQL += " AND a2.st_reg in ('I', 'A')"; //Presenta los trabajadores inactivos/activos de la tabla tbm_traEmp
            strSQL += " ORDER BY a1.tx_nom || ' ' || a1.tx_ape";
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 1;
            vcoCho = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Choferes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
        } 
        catch (Exception e){
            blnRes = false;  
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
    private boolean mostrarVenConCho(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoCho.setCampoBusqueda(1);
                    vcoCho.show();
                    if (vcoCho.getSelectedButton()==vcoCho.INT_BUT_ACE){
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtDesCorCho.setText(vcoCho.getValueAt(2));
                        txtDesLarCho.setText(vcoCho.getValueAt(3));
                        optEnv.setSelected(true);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoCho.buscar("a1.tx_ide", txtDesCorCho.getText())) // se cambio a tx_ide Bostel
                    {
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtDesCorCho.setText(vcoCho.getValueAt(2));
                        txtDesLarCho.setText(vcoCho.getValueAt(3));
                        optEnv.setSelected(true);
                    }
                    else
                    {
                        vcoCho.setCampoBusqueda(1);
                        vcoCho.setCriterio1(11);
                        vcoCho.cargarDatos();
                        vcoCho.show();
                        if (vcoCho.getSelectedButton()==vcoCho.INT_BUT_ACE)
                        {
                            txtCodCho.setText(vcoCho.getValueAt(1));
                            txtDesCorCho.setText(vcoCho.getValueAt(2));
                            txtDesLarCho.setText(vcoCho.getValueAt(3));
                            optEnv.setSelected(true);
                        }
                        else
                        {
                            txtDesCorCho.setText(strDesCorCho);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoCho.buscar("a1.tx_nom", txtDesLarCho.getText())) //Se cambio a tx_nom Bostel
                    {
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtDesCorCho.setText(vcoCho.getValueAt(2));
                        txtDesLarCho.setText(vcoCho.getValueAt(3));
                        optEnv.setSelected(true);
                    }
                    else
                    {
                        vcoCho.setCampoBusqueda(2);
                        vcoCho.setCriterio1(11);
                        vcoCho.cargarDatos();
                        vcoCho.show();
                        if (vcoCho.getSelectedButton()==vcoCho.INT_BUT_ACE)
                        {
                            txtCodCho.setText(vcoCho.getValueAt(1));
                            txtDesCorCho.setText(vcoCho.getValueAt(2));
                            txtDesLarCho.setText(vcoCho.getValueAt(3));
                            optEnv.setSelected(true);
                        }
                        else
                        {
                            txtDesLarCho.setText(strDesLarCho);
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

    
    private boolean callDocPenCnf(){
        boolean blnRes=true;
        objCom94_01=new ZafCom94_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis
            , Integer.parseInt(txtCodBodGrp.getText()), txtNomBodGrp.getText()
            , objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumOrdDes.getText()
        );
        if(objCom94_01.isBlnDocEnc()){//si el documento digitado se encontro
            objCom94_01.setVisible(false);
        }
        else{
            objCom94_01.setVisible(true);
        }
        
        if(objCom94_01.getBotonPresionado()==1){//se presiono el boton ACEPTAR
            //Se coloca la informacion en los arreglos
            objCom94_01.setDocCnfSel();//devuelve los datos de la cabecera - POR EL MOMENTO NO LE VEO NINGUNA FINALIDAD, PERO LO REFERENCIO POR SIAK
            arlDatDocCnfSel=objCom94_01.getDocCnfSel();
            //System.out.println("arlDatDocCnfSel: " + arlDatDocCnfSel.toString());
            cargarDocCnfArl();
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }
        objCom94_01=null;
        return blnRes;
    }
    
    
    
    
    
    
    /**
     * Función que permite cambiar el estado de confirmación de la solicitud parcial
     * @param con
     * @param codigoSeguimiento
     * @return 
     */
    private boolean actualizar_tbmCabSolTraInv(Connection con
                                                , int codigoEmpresaSolicitud, int codigoLocalSolicitud, int codigoTipoDocumentoSolicitud, int codigoDocumentoSolicitud){
        boolean blnRes=true;
        int intCodSeg=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                System.out.println("codigoEmpresaSolicitud: " + codigoEmpresaSolicitud);
                System.out.println("codigoLocalSolicitud: " + codigoLocalSolicitud);
                System.out.println("codigoTipoDocumentoSolicitud: " + codigoTipoDocumentoSolicitud);
                System.out.println("codigoDocumentoSolicitud: " + codigoDocumentoSolicitud);
                
                strSQL="";
                strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emprelcabsoltrainv=" + codigoEmpresaSolicitud + "";
                strSQL+=" AND a1.co_locrelcabsoltrainv=" + codigoLocalSolicitud + "";
                strSQL+=" AND a1.co_tipdocrelcabsoltrainv=" + codigoTipoDocumentoSolicitud + "";
                strSQL+=" AND a1.co_docrelcabsoltrainv=" + codigoDocumentoSolicitud + "";
                strSQL+=" GROUP BY a1.co_seg";
                System.out.println("SELECT - actualizar_tbmCabSolTraInv: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodSeg=rst.getInt("co_seg");
                
                strSQL="";
                strSQL+=" UPDATE tbm_cabSolTraInv";
                strSQL+=" SET st_reg='E' FROM(";
                strSQL+=" 	SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabSegMovInv AS a3 INNER JOIN tbm_cabSolTraInv AS a1";
                strSQL+=" 	ON a3.co_empRelCabSolTraInv=a1.co_emp AND a3.co_locRelCabSolTraInv=a1.co_loc";
                strSQL+=" 	AND a3.co_tipDocRelcabSolTraInv=a1.co_tipDoc AND a3.co_docRelCabSolTraInv=a1.co_doc";
                strSQL+=" 	WHERE a3.co_seg=" + intCodSeg + "";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE tbm_cabSolTraInv.co_emp=b1.co_emp AND tbm_cabSolTraInv.co_loc=b1.co_loc";
                strSQL+=" AND tbm_cabSolTraInv.co_tipDoc=b1.co_tipDoc AND tbm_cabSolTraInv.co_doc=b1.co_doc";
                strSQL+=";";
                System.out.println("UPDATE - actualizar_tbmCabSolTraInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    
    private class ZafThreadEnvReqSerGenFac extends Thread{
        String strDirIp="";
        int intPrt=0;

        public ZafThreadEnvReqSerGenFac(String strIp, int intPor){
            strDirIp=strIp;
            intPrt= intPor;
        }

      public void run(){
        try{
             java.net.Socket objSck = new java.net.Socket(strDirIp, intPrt);
             java.io.DataOutputStream dos = new java.io.DataOutputStream(objSck.getOutputStream());
             dos.writeInt(1);
             dos.writeUTF("");
             dos.close();
             objSck.close();
        }
        catch (java.net.ConnectException e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (java.io.IOException e){
            objUti.mostrarMsgErr_F1(null, e);
        }
      }
  }
   
    
    private boolean cargarDocCnfArl(){
        boolean blnRes=true;
        BigDecimal bdeCanDoc=BigDecimal.ZERO;
        BigDecimal bdeCanCnf=BigDecimal.ZERO;//para confirmación de despacho (cuando solo se tiene bodega y no despacho también)
        BigDecimal bdeCanEgrBod=BigDecimal.ZERO;//para confirmación de bodega 
        BigDecimal bdeCanDesEntCli=BigDecimal.ZERO;//para cantidad en despacho para entregar a cliente
        BigDecimal bdeCanDesEntBod=BigDecimal.ZERO;//para cantidad en despacho para entregar a bodega        
        BigDecimal bdeCanTraEgr=BigDecimal.ZERO;//para cantidad que se puede confirmar por ingreso según lo que está en tránsito(según lo que salió del punto remoto)
        
        BigDecimal bdeCamValCnf=BigDecimal.ZERO;//
        String strTerItmCnfIng="";
        
        BigDecimal bdeCanPndCnf=BigDecimal.ZERO;//
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                arlDatTbl.clear();
                arlDatCanPenCnf.clear();
                
                
                
                if(arlDatDocCnfSel.size()>0){
                    System.out.println("*arlDatDocCnfSel: " + arlDatDocCnfSel.toString());
                    
                    txtDesCorTipDocCon.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_DES_COR_TIP_DOC));
                    txtDesLarTipDocCon.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_DES_COR_TIP_DOC));
                    txtCodCliPro.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_CLI_PRO));
                    txtNomCliPro.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_NOM_CLI_PRO));
                    txtRucCliPro.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_RUC_CLI_PRO));
                    txtFecDocCon.setText(objUti.formatearFecha(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_FEC_DOC),"yyyy-MM-dd", "dd/MM/yyyy"));
                    txtNumOrdDes.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_NUM_DOC));
                    txtCodDocCon.setText(objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_DOC));
                    
                    intCodEmpDocCnf=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_EMP);
                    intCodLocDocCnf=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_LOC);
                    intCodTipDocCnf=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_TIP_DOC);
                    intCodDocCnf=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_DOC);
                    intCodEmpDocOri=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_EMP_ORI);
                    intCodLocDocOri=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_LOC_ORI);
                    intCodTipDocOri=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_TIP_DOC_ORI);
                    intCodDocOri=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_DOC_ORI);
                    intCodBodEmp=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_BOD_EMP);
                    intCodBodGrp=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_BOD_GRP);
                    strTipMovDocOri=objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_TIP_MOV_DOC_ORI);
                    strCamValCnf=objUti.getStringValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_CAM_VAL_CNF);
                    intCodSegRef=objUti.getIntValueAt(arlDatDocCnfSel, 0, INT_TBL_ARL_COD_SEG);
                             
                    System.out.println("*strCamValCnf: " + strCamValCnf);
                    System.out.println("*intCodSegRef: " + intCodSegRef);
                    
                    if( strTipMovCnfTot.equals("E") ){//egresos
                        strSQL="";
                        strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.tx_desCor, c1.tx_desLar, c1.co_doc, c1.ne_numOrdDes, c1.fe_doc";
                        strSQL+=" , c1.co_clides, c1.tx_rucclides, c1.tx_nomclides, c1.tx_dirclides";
                        strSQL+=" , c1.co_reg, c1.co_empRel, c1.co_locRel, c1.co_tipDocRel, c1.co_docRel, c1.co_regRel";
                        strSQL+=" , c2.tx_tipMov, c2.co_empRelCabSolTraInv, c2.co_locRelCabSolTraInv, c2.co_tipDocRelCabSolTraInv, c2.co_docRelCabSolTraInv";
                        strSQL+=" , c2.co_empDocOri, c2.co_locDocOri, c2.co_tipDocOri, c2.co_docDocOri, c2.ne_numDocOri, c2.co_regDocOri";
                        strSQL+=" , c2.co_itm, c2.tx_codAlt,";                        
                        strSQL+=" (CASE WHEN c2.tx_codAlt2 IS NULL THEN c2.tx_codAlt ELSE (CASE WHEN c2.tx_codAlt2='' THEN c2.tx_codAlt ELSE c2.tx_codAlt2 END) END) AS tx_codAlt2";
                        strSQL+=" , c2.tx_nomItm, c2.tx_uniMed, ABS(c2.nd_can) AS nd_can, c2.nd_canEgrBod, c2.nd_canDesEntBod";
                        strSQL+=" , c2.nd_canDesEntCli, c2.nd_canTra, 0 AS nd_canTraEgr,  c2.nd_canRev, c2.nd_CanPen, c2.nd_canCon, c2.nd_canNunRec, c2.st_merIngEgrFisBod, c2.tx_nomBodOrgdes";
                        strSQL+=" , c3.co_itmMae, c3.co_itmGrp";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                        strSQL+=" 	, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri, a2.co_reg AS co_regDocOri";
                        strSQL+=" 	, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod";
                        strSQL+=" 	, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen, a2.nd_canCon, a2.nd_canNunRec, a2.st_merIngEgrFisBod, a2.tx_nomBodOrgdes";
                        strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 	WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S' AND a1.st_genOrdDes='S' AND a1.st_ordDesGen='S'";
                        strSQL+=" 	AND a2.nd_canPen<>0";
                        strSQL+="       AND a1.co_emp=" + intCodEmpDocOri + "";
                        strSQL+="       AND a1.co_loc=" + intCodLocDocOri + "";
                        strSQL+="       AND a1.co_tipDoc=" + intCodTipDocOri + "";
                        strSQL+="       AND a1.co_doc=" + intCodDocOri + "";//EGRESO
                        strSQL+=" 	ORDER BY a1.ne_numDoc";
                        strSQL+=" ) AS c2";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT b1.co_itmMae, b1.co_emp, b1.co_itmEmp, b2.co_itmGrp";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a2.co_itmMae, a2.co_emp, a2.co_itm AS co_itmEmp";
                        strSQL+=" 		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+=" 	) AS b1";
                        strSQL+=" 	INNER JOIN(";
                        strSQL+=" 		SELECT a2.co_itmMae, a2.co_itm AS co_itmGrp";
                        strSQL+=" 		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+=" 		WHERE a2.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                        strSQL+=" 	) AS b2";
                        strSQL+=" 	ON b1.co_itmMae=b2.co_itmMae";
                        strSQL+=" ) AS c3";
                        strSQL+=" ON c2.co_empDocOri=c3.co_emp AND c2.co_itm=c3.co_itmEmp";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a1.ne_numOrdDes, a1.fe_doc";
                        strSQL+=" 	, a1.co_clides, a1.tx_rucclides, a1.tx_nomclides, a1.tx_dirclides";
                        strSQL+=" 	, a2.co_reg, a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                        strSQL+=" 	FROM (tbm_cabGuiRem AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" 	INNER JOIN tbm_detGuiRem AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 	WHERE a1.st_reg='A'";
                        //borrar
                        strSQL+="       AND a1.ne_numOrdDes=" + txtNumOrdDes.getText() + "";
                        strSQL+=" ) AS c1";
                        strSQL+=" ON c1.co_empRel=c2.co_empDocOri AND c1.co_locRel=c2.co_locDocOri AND c1.co_tipDocRel=c2.co_tipDocOri AND c1.co_docRel=c2.co_docDocOri AND c1.co_regRel=c2.co_regDocOri";
//                        strSQL+=" WHERE c2.co_empDocOri=" + intCodEmpDocOri + "";
//                        strSQL+=" AND c2.co_locDocOri=" + intCodLocDocOri + "";
//                        strSQL+=" AND c2.co_tipDocOri=" + intCodTipDocOri + "";
//                        strSQL+=" AND c2.co_docDocOri=" + intCodDocOri + "";//EGRESO
                        strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.tx_desCor, c1.tx_desLar, c1.co_doc, c1.ne_numOrdDes, c1.fe_doc";
                        strSQL+=" , c1.co_clides, c1.tx_rucclides, c1.tx_nomclides, c1.tx_dirclides";
                        strSQL+=" , c1.co_reg, c1.co_empRel, c1.co_locRel, c1.co_tipDocRel, c1.co_docRel, c1.co_regRel";
                        strSQL+=" , c2.tx_tipMov, c2.co_empRelCabSolTraInv, c2.co_locRelCabSolTraInv, c2.co_tipDocRelCabSolTraInv, c2.co_docRelCabSolTraInv";
                        strSQL+=" , c2.co_empDocOri, c2.co_locDocOri, c2.co_tipDocOri, c2.co_docDocOri, c2.ne_numDocOri, c2.co_regDocOri";
                        strSQL+=" , c2.co_itm, c2.tx_codAlt, c2.tx_codAlt2, c2.tx_nomItm, c2.tx_uniMed, c2.nd_can, c2.nd_canEgrBod, c2.nd_canDesEntBod";
                        strSQL+=" , c2.nd_canDesEntCli, c2.nd_canTra, c2.nd_canRev, c2.nd_CanPen, c2.nd_canCon, c2.nd_canNunRec, c2.st_merIngEgrFisBod, c2.tx_nomBodOrgdes";
                        strSQL+=" , c3.co_itmMae, c3.co_itmGrp";
                        strSQL+=" ORDER BY c1.co_reg";
                    }
                    else if( strTipMovCnfTot.equals("I") ){//ingresos
                        strSQL="";
                        strSQL+="(";
                        strSQL+=" SELECT c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                        strSQL+=" , Null AS co_clides, Null AS tx_rucclides, Null AS tx_nomclides, Null AS tx_dirclides";
                        strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                        strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                        strSQL+=" , c1.co_empDocOri AS co_emp, c1.co_locDocOri AS co_loc, c1.co_tipDocOri AS co_tipDoc, c1.co_docDocOri AS co_doc, c1.co_regDocOri AS co_reg";
                        strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, ABS(c1.nd_can) AS nd_can, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                        strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                        if(objParSis.getCodigoMenu()==4000)
                            strSQL+=" , c1.nd_canTraEgr";
                        else if(objParSis.getCodigoMenu()==4010)
                            strSQL+=" , Null AS nd_canTraEgr";
                        else
                            strSQL+=" , Null AS nd_canTraEgr";
                        
                        strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                        strSQL+=" , c3.co_itmMae, c3.co_itmGrp, Null AS st_terItm";
                        strSQL+=" FROM(";
                        strSQL+=" 		SELECT e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv, e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                        strSQL+=" 		, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocOri, e1.co_docDocOri, e1.ne_numDocOri, e1.co_regDocOri";
                        strSQL+=" 		, e1.co_itm, e1.tx_codAlt, e1.tx_codAlt2, e1.tx_nomItm, e1.tx_uniMed, e1.nd_can, e1.nd_canEgrBod, e1.nd_canDesEntBod";
                        strSQL+=" 		, e1.nd_canDesEntCli, e1.nd_canTra, e1.nd_canRev, e1.nd_CanPen, e1.nd_canCon, e1.nd_canNunRec, e1.st_merIngEgrFisBod, e1.tx_nomBodOrgdes";
                        strSQL+=" 		, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar, e1.nd_canIngBod";
                        strSQL+="  		, e2.co_empEgr, e2.co_locEgr, e2.co_tipDocEgr, e2.co_docEgr, e2.co_regEgr";
                        if(objParSis.getCodigoMenu()==4000)
                            strSQL+="  		, e3.nd_canTra AS nd_canTraEgr";
                        else if(objParSis.getCodigoMenu()==4010)
                            strSQL+="  		, Null AS nd_canTraEgr";
                        else
                            strSQL+="  		, Null AS nd_canTraEgr";
                        
                        strSQL+="  		FROM(";
                        strSQL+=" 			SELECT a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                        strSQL+=" 			, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri, a2.co_reg AS co_regDocOri";
                        strSQL+=" 			, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod";
                        strSQL+=" 			, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen, a2.nd_canCon, a2.nd_canNunRec, a2.st_merIngEgrFisBod, a2.tx_nomBodOrgdes";
                        strSQL+=" 			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.tx_desCor, a3.tx_desLar, a2.nd_canIngBod";
                        strSQL+=" 			FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                        strSQL+=" 			AND a2.nd_canPen<>0";
                        strSQL+=" 			ORDER BY a1.ne_numDoc";
                        strSQL+="  		) AS e1";
                        strSQL+="  		INNER JOIN(";
                        strSQL+="  			SELECT a1.co_emp AS co_empEgr, a1.co_loc AS co_locEgr, a1.co_tipDoc AS co_tipDocEgr, a1.co_doc AS co_docEgr, a1.co_reg AS co_regEgr";
                        strSQL+="  			, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.co_regRel";
                        strSQL+="  			FROM tbr_detMovInv AS a1";
                        strSQL+="  		) AS e2";
                        strSQL+="  		ON e1.co_empDocOri=e2.co_empRel AND e1.co_locDocOri=e2.co_locRel AND e1.co_tipDocOri=e2.co_tipDocRel AND e1.co_docDocOri=e2.co_docRel AND e1.co_regDocOri=e2.co_regRel";
                        if(objParSis.getCodigoMenu()==4000){
                            strSQL+="  		INNER JOIN(";
                            strSQL+="  			SELECT a2.st_merIngEgrFisBod, a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                            strSQL+="  			, a2.co_reg, a2.co_itm, a2.nd_can, a2.nd_canEgrBod, a2.nd_canIngBod, a2.nd_canDesEntBod, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen";
                            strSQL+="  			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                            strSQL+="  			FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                            strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                            strSQL+="  			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                            strSQL+="  			AND a2.nd_canTra<>0";
                            strSQL+="  			ORDER BY a1.ne_numDoc";
                            strSQL+="  		) AS e3";
                            strSQL+=" 		ON e2.co_empEgr=e3.co_emp AND e2.co_locEgr=e3.co_loc AND e2.co_tipDocEgr=e3.co_tipDoc AND e2.co_docEgr=e3.co_doc AND e2.co_regEgr=e3.co_reg ";
                        }
                        strSQL+=" ) AS c1";
                        strSQL+="  INNER JOIN(";
                        strSQL+="  	SELECT b1.co_itmMae, b1.co_emp, b1.co_itmEmp, b2.co_itmGrp";
                        strSQL+="  	FROM(";
                        strSQL+="  		SELECT a2.co_itmMae, a2.co_emp, a2.co_itm AS co_itmEmp";
                        strSQL+="  		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+="  	) AS b1";
                        strSQL+="  	INNER JOIN(";
                        strSQL+="  		SELECT a2.co_itmMae, a2.co_itm AS co_itmGrp";
                        strSQL+="  		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+="  		WHERE a2.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                        strSQL+="  	) AS b2";
                        strSQL+="  	ON b1.co_itmMae=b2.co_itmMae";
                        strSQL+=" ) AS c3";
                        strSQL+=" ON c1.co_empDocOri=c3.co_emp AND c1.co_itm=c3.co_itmEmp";
                        strSQL+=" WHERE c1.co_empDocOri=" + intCodEmpDocCnf + "";//INBOPR
                        strSQL+=" AND c1.co_locDocOri=" + intCodLocDocCnf + "";
                        strSQL+=" AND c1.co_tipDocOri=" + intCodTipDocCnf + "";
                        strSQL+=" AND c1.co_docDocOri=" + intCodDocCnf + "";
                        strSQL+=" GROUP BY c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                        strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                        strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                        strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                        strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                        if(objParSis.getCodigoMenu()==4000)
                            strSQL+=" , c1.nd_canTraEgr";
                        strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                        strSQL+=" , c3.co_itmMae, c3.co_itmGrp, c1.nd_can";
                        strSQL+=" ORDER BY c1.co_regDocOri";
                        strSQL+=")";
                        
                        strSQL+=" UNION";
                        strSQL+="(";//codigos L
                        strSQL+=" SELECT c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                        strSQL+=" , Null AS co_clides, Null AS tx_rucclides, Null AS tx_nomclides, Null AS tx_dirclides";
                        strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                        strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                        strSQL+=" , c1.co_empDocOri AS co_emp, c1.co_locDocOri AS co_loc, c1.co_tipDocOri AS co_tipDoc, c1.co_docDocOri AS co_doc, c1.co_regDocOri AS co_reg";
                        strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt AS tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, ABS(c1.nd_can) AS nd_can, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                        strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                        strSQL+=" , Null AS nd_canTraEgr";                        
                        strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                        strSQL+=" , c3.co_itmMae, c3.co_itmGrp, 'L' AS st_terItm";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv, e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                        strSQL+=" 	, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocOri, e1.co_docDocOri, e1.ne_numDocOri, e1.co_regDocOri";
                        strSQL+=" 	, e1.co_itm, e1.tx_codAlt, e1.tx_codAlt2, e1.tx_nomItm, e1.tx_uniMed, e1.nd_can, e1.nd_canEgrBod, e1.nd_canDesEntBod";
                        strSQL+=" 	, e1.nd_canDesEntCli, e1.nd_canTra, e1.nd_canRev, e1.nd_CanPen, e1.nd_canCon, e1.nd_canNunRec, e1.st_merIngEgrFisBod, e1.tx_nomBodOrgdes";
                        strSQL+=" 	, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar, e1.nd_canIngBod";
                        strSQL+=" 	FROM(";
                        strSQL+=" 		SELECT a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                        strSQL+=" 		, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri, a2.co_reg AS co_regDocOri";
                        strSQL+=" 		, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod";
                        strSQL+=" 		, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen, a2.nd_canCon, a2.nd_canNunRec, a2.st_merIngEgrFisBod, a2.tx_nomBodOrgdes";
                        strSQL+=" 		, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.tx_desCor, a3.tx_desLar, a2.nd_canIngBod";
                        strSQL+=" 		FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" 		INNER JOIN (tbm_detMovInv AS a2";
                        strSQL+=" 				LEFT OUTER JOIN tbr_detMovInv AS a6";
                        strSQL+=" 				ON a2.co_emp=a6.co_empRel AND a2.co_loc=a6.co_locRel AND a2.co_tipDoc=a6.co_tipDocRel";
                        strSQL+=" 				AND a2.co_doc=a6.co_docRel AND a2.co_reg=a6.co_regRel";
                        strSQL+=" 			   )";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" 		WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                        strSQL+=" 		AND a2.nd_canPen<>0";
                        strSQL+=" 		ORDER BY a1.ne_numDoc";
                        strSQL+=" 	) AS e1";
                        strSQL+=" ) AS c1";
                        strSQL+="   INNER JOIN(";
                        strSQL+="   	SELECT b1.co_itmMae, b1.co_emp, b1.co_itmEmp, b2.co_itmGrp";
                        strSQL+="   	FROM(";
                        strSQL+="   		SELECT a2.co_itmMae, a2.co_emp, a2.co_itm AS co_itmEmp";
                        strSQL+="   		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+="   	) AS b1";
                        strSQL+="   	INNER JOIN(";
                        strSQL+="   		SELECT a2.co_itmMae, a2.co_itm AS co_itmGrp";
                        strSQL+="   		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+="   		WHERE a2.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                        strSQL+="   	) AS b2";
                        strSQL+="   	ON b1.co_itmMae=b2.co_itmMae";
                        strSQL+=" ) AS c3";
                        strSQL+="  ON c1.co_empDocOri=c3.co_emp AND c1.co_itm=c3.co_itmEmp";
                        strSQL+=" WHERE c1.co_empDocOri=" + intCodEmpDocCnf + "";//INBOPR
                        strSQL+=" AND c1.co_locDocOri=" + intCodLocDocCnf + "";
                        strSQL+=" AND c1.co_tipDocOri=" + intCodTipDocCnf + "";
                        strSQL+=" AND c1.co_docDocOri=" + intCodDocCnf + "";
                        strSQL+=" GROUP BY c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                        strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                        strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                        strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                        strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                        strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                        strSQL+=" , c3.co_itmMae, c3.co_itmGrp, c1.nd_can";
                        strSQL+=" ORDER BY c1.co_regDocOri";
                        strSQL+=")";
                    }

                    System.out.println("cargarDocCnf: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbl=new ArrayList();
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_ITM_MAE,             rst.getString("co_itmMae"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_ITM_GRP,             rst.getString("co_itmGrp"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_ITM_EMP,             rst.getString("co_itm"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_ALT_ITM,             rst.getString("tx_codAlt"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_LET_ITM,             rst.getString("tx_codAlt2"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_NOM_ITM,                 rst.getString("tx_nomItm"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_UNI_MED_ITM,             rst.getString("tx_uniMed"));
                       
                        bdeCanDoc=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                        bdeCanCnf=new BigDecimal(rst.getObject("nd_canCon")==null?"0":(rst.getString("nd_canCon").equals("")?"0":rst.getString("nd_canCon"))).abs();
                        
                        bdeCanEgrBod=new BigDecimal(rst.getObject("nd_canEgrBod")==null?"0":(rst.getString("nd_canEgrBod").equals("")?"0":rst.getString("nd_canEgrBod"))).abs();
                        bdeCanDesEntCli=new BigDecimal(rst.getObject("nd_canDesEntCli")==null?"0":(rst.getString("nd_canDesEntCli").equals("")?"0":rst.getString("nd_canDesEntCli"))).abs();
                        bdeCanDesEntBod=new BigDecimal(rst.getObject("nd_canDesEntBod")==null?"0":(rst.getString("nd_canDesEntBod").equals("")?"0":rst.getString("nd_canDesEntBod"))).abs();                        
                        bdeCanTraEgr=new BigDecimal(rst.getObject("nd_canTraEgr")==null?"0":(rst.getString("nd_canTraEgr").equals("")?"0":rst.getString("nd_canTraEgr"))).abs();
                                                
                        if(strTipMovDocOri.equals("V")){
                            if(objParSis.getCodigoMenu()!=4000)//conf. despacho
                                bdeCamValCnf=new BigDecimal(rst.getObject("" + strCamValCnf + "")==null?"0":(rst.getString("" + strCamValCnf + "").equals("")?"0":rst.getString("" + strCamValCnf + ""))).abs();
                        }
                        else{
                            bdeCamValCnf=new BigDecimal(rst.getObject("" + strCamValCnf + "")==null?"0":(rst.getString("" + strCamValCnf + "").equals("")?"0":rst.getString("" + strCamValCnf + ""))).abs();
                        }
                        
                        System.out.println("strCamValCnf: " + strCamValCnf);
                        System.out.println("bdeCamValCnf: " + bdeCamValCnf);
                        
                        arlRegTbl.add(INT_ARL_TBL_DAT_CAN_DOC_ORI_CNF,         bdeCanDoc);
                        arlRegTbl.add(INT_ARL_TBL_DAT_CAN_TOT_CNF,             bdeCanCnf);
                        
                        System.out.println("strTipMovDocOri: " + strTipMovDocOri);
                        System.out.println("menu: " + objParSis.getCodigoMenu());
                        
                        if(objParSis.getCodigoMenu()==4000)
                            strTerItmCnfIng=rst.getObject("st_terItm")==null?"":rst.getString("st_terItm");
                        
                        if(strTipMovDocOri.equals("V")){
                            if(objParSis.getCodigoMenu()==4000){//conf. despacho  bdeCanPndCnf
                                arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCanTraEgr);//
                                bdeCanPndCnf=bdeCanTraEgr;
                            }
                            else{
                                arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCamValCnf);//egresos
                                bdeCanPndCnf=bdeCamValCnf;
                            }
                        }
                        else{
                            if(objParSis.getCodigoMenu()==4000){//conf. despacho
                                if(strTerItmCnfIng.equals("L")){
                                    arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCamValCnf);
                                    bdeCanPndCnf=bdeCamValCnf;
                                }//
                                else{
                                    arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCanTraEgr);
                                    bdeCanPndCnf=bdeCanTraEgr;
                                }//
                            }
                            else{
                                arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCamValCnf);
                                bdeCanPndCnf=bdeCamValCnf;
                            }//egresos                                
                        }

                        arlRegTbl.add(INT_ARL_TBL_DAT_CAN_CNF_USR,             "");
                        arlRegTbl.add(INT_ARL_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC, "");
                        arlRegTbl.add(INT_ARL_TBL_DAT_OBS,                     "");
                        arlRegTbl.add(INT_ARL_TBL_DAT_EST_ING_EGR_MER_BOD,     rst.getString("st_merIngEgrFisBod"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_EMP_ORD_DES,         rst.getString("co_emp"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_LOC_ORD_DES,         rst.getString("co_loc"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_TIP_DOC_ORD_DES,     rst.getString("co_tipDoc"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_DOC_ORD_DES,         rst.getString("co_doc"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_REG_ORD_DES,         rst.getString("co_reg"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_EMP_DOC_ORI,         rst.getString("co_empDocOri"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_LOC_DOC_ORI,         rst.getString("co_locDocOri"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_TIP_DOC_ORI,         rst.getString("co_tipDocOri"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_DOC_ORI,             rst.getString("co_docDocOri"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_COD_REG_DOC_ORI,         rst.getString("co_regDocOri"));
                        arlRegTbl.add(INT_ARL_TBL_DAT_EST_ITM_SEL_USR_CNF,     "");
                        
                        //solicitud
                        intCodEmpSol=rst.getInt("co_empRelCabSolTraInv");
                        intCodLocSol=rst.getInt("co_locRelCabSolTraInv");
                        intCodTipDocSol=rst.getInt("co_tipDocRelCabSolTraInv");
                        intCodDocSol=rst.getInt("co_docRelCabSolTraInv");
                        
                        System.out.println("**intCodEmpSol: " + intCodEmpSol);
                        System.out.println("**intCodLocSol: " + intCodLocSol);
                        System.out.println("**intCodTipDocSol: " + intCodTipDocSol);
                        System.out.println("**intCodDocSol: " + intCodDocSol);
                        
                        arlDatTbl.add(arlRegTbl);
                        
                        
                        //NUEVO - VALIDACION
                        arlRegCanPenCnf=new ArrayList();
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_MAE, rst.getString("co_itmMae"));
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_GRP, rst.getString("co_itmGrp"));
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_EMP, rst.getString("co_itm"));
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_COD_ITM_LET, rst.getString("tx_codAlt2"));
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_CAN_PEN_CNF, bdeCanPndCnf);
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_EST_CNF, "");
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_EST_PRO, "");
                        arlRegCanPenCnf.add(INT_ARL_AFT_INS_CAN_PEN_CNF_COD_REG_ITM, rst.getString("co_regDocOri"));
                        arlDatCanPenCnf.add(arlRegCanPenCnf);
                        
                        
                        System.out.println("**arlDatCanPenCnf: " + arlDatCanPenCnf.toString());
                        
                        
                        
                    }
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                System.out.println("arlDatTbl: " + arlDatTbl.toString());             
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
     * Función que permite cargar la información en el modelo de datos según el item seleccionado
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean setDatModel(String codigoItemLetra, int filaSeleccionada){
        boolean blnRes=true;
        String strCodItmLetSel="", strCodItmLetArl="";
        boolean blnItmIsTru=false;
        String strEstItmUsaArl="";
        int intFilSel=filaSeleccionada;
        try{
            strCodItmLetSel=codigoItemLetra;
            
            if(arlDatTbl.size()>0){
                for(int i=0; i<arlDatTbl.size(); i++){
                    strCodItmLetArl=objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_LET_ITM);
                    strEstItmUsaArl=objUti.getObjectValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_EST_ITM_SEL_USR_CNF)==null?"":objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_EST_ITM_SEL_USR_CNF);
                    if(!strEstItmUsaArl.equals("E")){
                        if(strCodItmLetSel.equals(strCodItmLetArl)){
                            System.out.println("ITEM ENCONTRADO");
                            if(blnChkBoxCanSel)
                                objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK);//solo por cancelacion
                            
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_ITM_MAE), intFilSel, INT_TBL_DAT_COD_ITM_MAE);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_ITM_GRP), intFilSel, INT_TBL_DAT_COD_ITM_GRP);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_ITM_EMP), intFilSel, INT_TBL_DAT_COD_ITM_EMP);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_ALT_ITM), intFilSel, INT_TBL_DAT_COD_ALT_ITM);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_LET_ITM), intFilSel, INT_TBL_DAT_COD_LET_ITM);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_NOM_ITM), intFilSel, INT_TBL_DAT_NOM_ITM);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_UNI_MED_ITM), intFilSel, INT_TBL_DAT_UNI_MED_ITM);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_CAN_DOC_ORI_CNF), intFilSel, INT_TBL_DAT_CAN_DOC_ORI_CNF);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_CAN_TOT_CNF), intFilSel, INT_TBL_DAT_CAN_TOT_CNF);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_CAN_PUE_CNF), intFilSel, INT_TBL_DAT_CAN_PUE_CNF);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_CAN_CNF_USR), intFilSel, INT_TBL_DAT_CAN_CNF_USR);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC), intFilSel, INT_TBL_DAT_CAN_CNF_USR_NUN_ENT_REC);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_OBS), intFilSel, INT_TBL_DAT_OBS);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_EST_ING_EGR_MER_BOD), intFilSel, INT_TBL_DAT_EST_ING_EGR_MER_BOD);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_EMP_ORD_DES), intFilSel, INT_TBL_DAT_COD_EMP_ORD_DES);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_LOC_ORD_DES), intFilSel, INT_TBL_DAT_COD_LOC_ORD_DES);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_TIP_DOC_ORD_DES), intFilSel, INT_TBL_DAT_COD_TIP_DOC_ORD_DES);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_DOC_ORD_DES), intFilSel, INT_TBL_DAT_COD_DOC_ORD_DES);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_REG_ORD_DES), intFilSel, INT_TBL_DAT_COD_REG_ORD_DES);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_EMP_DOC_ORI), intFilSel, INT_TBL_DAT_COD_EMP_DOC_ORI);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_LOC_DOC_ORI), intFilSel, INT_TBL_DAT_COD_LOC_DOC_ORI);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_TIP_DOC_ORI), intFilSel, INT_TBL_DAT_COD_TIP_DOC_ORI);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_DOC_ORI), intFilSel, INT_TBL_DAT_COD_DOC_ORI);
                            objTblMod.setValueAt(objUti.getStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_COD_REG_DOC_ORI), intFilSel, INT_TBL_DAT_COD_REG_DOC_ORI);
                            objUti.setStringValueAt(arlDatTbl, i, INT_ARL_TBL_DAT_EST_ITM_SEL_USR_CNF, "E");
                            blnItmIsTru=true;
                            //objTblEdi.seleccionarCelda(intFilSel, INT_ARL_TBL_DAT_CAN_CNF_USR);    //Rose: Uso objTblEdi 
                            break;
                        }
                    }
                }
                if(!blnItmIsTru){
                    mostrarMsgInf("<HTML>El item ingresado no ha sido encontrado.<BR>Verifique y vuelva a intentarlo.</HTML>");
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.changeSelection(0, INT_TBL_DAT_COD_LET_ITM, true, true);
                    //objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_COD_LET_ITM);  //Rose: Uso objTblEdi 
                    tblDat.requestFocus();                  
                    blnRes=false;
                }
            }        
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
        
        
    }

    
    private boolean isChnEstCotVenGenFac(){
        boolean blnRes=false;
        int intCodSeg=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emprelcabsoltrainv=" + intCodEmpSol + "";
                strSQL+=" AND a1.co_locrelcabsoltrainv=" + intCodLocSol + "";
                strSQL+=" AND a1.co_tipdocrelcabsoltrainv=" + intCodTipDocSol + "";
                strSQL+=" AND a1.co_docrelcabsoltrainv=" + intCodDocSol + "";
                strSQL+=" GROUP BY a1.co_seg";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodSeg=rst.getInt("co_seg");
                
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.st_reg";
                strSQL+=" FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabCotVen AS a1";
                strSQL+=" ON a3.co_empRelCabCotVen=a1.co_emp AND a3.co_locRelCabCotVen=a1.co_loc AND a3.co_cotRelCabCotVen=a1.co_cot";
                strSQL+=" WHERE a3.co_seg=" + intCodSeg + " AND a1.st_reg='L'";
                strSQL+=";";
                System.out.println("isChnEstCotVenGenFac: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    blnRes=true;
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que obtiene el nombre del print server.
     * @param blnPruebas
     * @param intCodBodGrp
     * @return 
     */
    private String getPrintServer(Boolean blnPruebas, int intCodBodGrp) 
    {
        String strNomPrnSer = "";
        if(blnPruebas)
        {
            strNomPrnSer="laser_sistemass";
        }
        else
        {
            switch (intCodBodGrp) 
            {
                case 1://California
                    strNomPrnSer="od_califormia";
                    break;
                case 2://Vía Daule
                    strNomPrnSer="od_dimulti";
                    break;
                case 3://Quito Norte
                    strNomPrnSer="od_quito";
                    break;
                case 5://Manta
                    strNomPrnSer="od_manta";
                    break;
                case 6://Importaciones
                    strNomPrnSer="printGuiRemBodImp";
                    break;
                case 11://Santo Domingo
                    strNomPrnSer="od_stodgo";
                    break;
                case 15://Inmaconsa
                    if(objParSis.getCodigoMenu()== 4000 && objParSis.getCodigoUsuario()==151)
                        strNomPrnSer="guias_inmaconsa";  // Area de Despacho
                    else
                        strNomPrnSer="od_inmaconsa";     //Area de Bodega
                    break;
                case 28://Cuenca
                    strNomPrnSer="od_cuenca";
                    break;
                 case 32://Duran
                    strNomPrnSer="od_duran";
                    break;
                default:
                    break;
            }
        }
        return strNomPrnSer;
    }
    
    
    /**
     * Esta función obtiene el tipo de modificación de fecha que el usurio puede realizar en el tipo de documento
     * <BR>Nota.- Los valores que puede tener son:
     * <UL>
     * <LI>1: No se puede modificar la fecha el tipo de documento
     * <LI>2: Se puede modificar la fecha hacia atrás
     * <LI>3: Se puede modificar la fecha hacia adelante
     * <LI>4: Se puede modificar la fecha
     * </UL>
     * @return intTipModFec: El tipo de modificación de fecha que el usuario puede realizar en el tipo de documento
     */
    private int canChangeDate(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModFec=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModFec=rstChaDat.getInt("ne_tipresmodfecdoc");
                    }
                    
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;

            }
        }
        
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
        
    }    

    /**
     * Función que permite conocer si el item tiene cantidad suficiente para poder confirmar
     * @param conexion
     * @param intCodigoEmpresaEgreso
     * @param intCodigoLocalEgreso
     * @param intCodigoTipoDocumentoEgreso
     * @param intCodigoDocumentoEgreso
     * @param intCodigoRegistroEgreso
     * @param intCodigoEmpresaIngreso
     * @param intCodigoLocalIngreso
     * @param intCodigoTipoDocumentoIngreso
     * @param intCodigoDocumentoIngreso
     * @param intCodigoRegistroIngreso
     * @param strCodigoItemLetra
     * @param bdeCantidadConfirmar
     * @return 
     */
    private boolean isValItmCanSufCnf(Connection conexion, int intCodigoEmpresaEgreso, int intCodigoLocalEgreso, int intCodigoTipoDocumentoEgreso, int intCodigoDocumentoEgreso, int intCodigoRegistroEgreso
                                      , int intCodigoEmpresaIngreso, int intCodigoLocalIngreso, int intCodigoTipoDocumentoIngreso, int intCodigoDocumentoIngreso, int intCodigoRegistroIngreso
                                      , String strCodigoItemLetra, BigDecimal bdeCantidadConfirmar){
        boolean blnRes=false;
        BigDecimal bdeCanTraEgr=BigDecimal.ZERO;//para cantidad que se puede confirmar por ingreso según lo que está en tránsito(según lo que salió del punto remoto)
        BigDecimal bdeCamValCnf=BigDecimal.ZERO;//
        String strTerItmCnfIng="";
        BigDecimal bdeValCanPueCnf=BigDecimal.ZERO;//
        String strTit="", strMsg="";
        Statement stmValItmCanSufCnf;
        ResultSet rstValItmCanSufCnf;
        try{
            if(conexion!=null){
                stmValItmCanSufCnf=conexion.createStatement();
                             
                if( strTipMovCnfTot.equals("E") ){//egresos
                    strSQL="";
                    strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.tx_desCor, c1.tx_desLar, c1.co_doc, c1.ne_numOrdDes, c1.fe_doc";
                    strSQL+=" , c1.co_clides, c1.tx_rucclides, c1.tx_nomclides, c1.tx_dirclides";
                    strSQL+=" , c1.co_reg, c1.co_empRel, c1.co_locRel, c1.co_tipDocRel, c1.co_docRel, c1.co_regRel";
                    strSQL+=" , c2.tx_tipMov, c2.co_empRelCabSolTraInv, c2.co_locRelCabSolTraInv, c2.co_tipDocRelCabSolTraInv, c2.co_docRelCabSolTraInv";
                    strSQL+=" , c2.co_empDocOri, c2.co_locDocOri, c2.co_tipDocOri, c2.co_docDocOri, c2.ne_numDocOri, c2.co_regDocOri";
                    strSQL+=" , c2.co_itm, c2.tx_codAlt,";                        
                    strSQL+=" (CASE WHEN c2.tx_codAlt2 IS NULL THEN c2.tx_codAlt ELSE (CASE WHEN c2.tx_codAlt2='' THEN c2.tx_codAlt ELSE c2.tx_codAlt2 END) END) AS tx_codAlt2";
                    strSQL+=" , c2.tx_nomItm, c2.tx_uniMed, ABS(c2.nd_can) AS nd_can, c2.nd_canEgrBod, c2.nd_canDesEntBod";
                    strSQL+=" , c2.nd_canDesEntCli, c2.nd_canTra, 0 AS nd_canTraEgr,  c2.nd_canRev, c2.nd_CanPen, c2.nd_canCon, c2.nd_canNunRec, c2.st_merIngEgrFisBod, c2.tx_nomBodOrgdes";
                    strSQL+=" , c3.co_itmMae, c3.co_itmGrp";
                    strSQL+=" FROM(";
                    strSQL+=" 	SELECT a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                    strSQL+=" 	, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri, a2.co_reg AS co_regDocOri";
                    strSQL+=" 	, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod";
                    strSQL+=" 	, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen, a2.nd_canCon, a2.nd_canNunRec, a2.st_merIngEgrFisBod, a2.tx_nomBodOrgdes";
                    strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 	WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S' AND a1.st_genOrdDes='S' AND a1.st_ordDesGen='S'";
                    strSQL+=" 	AND a2.nd_canPen<>0";
                    strSQL+="       AND a1.co_emp=" + intCodigoEmpresaEgreso + "";
                    strSQL+="       AND a1.co_loc=" + intCodigoLocalEgreso + "";
                    strSQL+="       AND a1.co_tipDoc=" + intCodigoTipoDocumentoEgreso + "";
                    strSQL+="       AND a1.co_doc=" + intCodigoDocumentoEgreso + "";//EGRESO
                    strSQL+="       AND a2.co_reg=" + intCodigoRegistroEgreso + "";//EGRESO
                    strSQL+=" 	ORDER BY a1.ne_numDoc";
                    strSQL+=" ) AS c2";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT b1.co_itmMae, b1.co_emp, b1.co_itmEmp, b2.co_itmGrp";
                    strSQL+=" 	FROM(";
                    strSQL+=" 		SELECT a2.co_itmMae, a2.co_emp, a2.co_itm AS co_itmEmp";
                    strSQL+=" 		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+=" 	) AS b1";
                    strSQL+=" 	INNER JOIN(";
                    strSQL+=" 		SELECT a2.co_itmMae, a2.co_itm AS co_itmGrp";
                    strSQL+=" 		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+=" 		WHERE a2.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" 	) AS b2";
                    strSQL+=" 	ON b1.co_itmMae=b2.co_itmMae";
                    strSQL+=" ) AS c3";
                    strSQL+=" ON c2.co_empDocOri=c3.co_emp AND c2.co_itm=c3.co_itmEmp";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a1.ne_numOrdDes, a1.fe_doc";
                    strSQL+=" 	, a1.co_clides, a1.tx_rucclides, a1.tx_nomclides, a1.tx_dirclides";
                    strSQL+=" 	, a2.co_reg, a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                    strSQL+=" 	FROM (tbm_cabGuiRem AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                    strSQL+=" 	INNER JOIN tbm_detGuiRem AS a2";
                    strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 	WHERE a1.st_reg='A'";
                    strSQL+=" ) AS c1";
                    strSQL+=" ON c1.co_empRel=c2.co_empDocOri AND c1.co_locRel=c2.co_locDocOri AND c1.co_tipDocRel=c2.co_tipDocOri AND c1.co_docRel=c2.co_docDocOri AND c1.co_regRel=c2.co_regDocOri";
                    strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.tx_desCor, c1.tx_desLar, c1.co_doc, c1.ne_numOrdDes, c1.fe_doc";
                    strSQL+=" , c1.co_clides, c1.tx_rucclides, c1.tx_nomclides, c1.tx_dirclides";
                    strSQL+=" , c1.co_reg, c1.co_empRel, c1.co_locRel, c1.co_tipDocRel, c1.co_docRel, c1.co_regRel";
                    strSQL+=" , c2.tx_tipMov, c2.co_empRelCabSolTraInv, c2.co_locRelCabSolTraInv, c2.co_tipDocRelCabSolTraInv, c2.co_docRelCabSolTraInv";
                    strSQL+=" , c2.co_empDocOri, c2.co_locDocOri, c2.co_tipDocOri, c2.co_docDocOri, c2.ne_numDocOri, c2.co_regDocOri";
                    strSQL+=" , c2.co_itm, c2.tx_codAlt, c2.tx_codAlt2, c2.tx_nomItm, c2.tx_uniMed, c2.nd_can, c2.nd_canEgrBod, c2.nd_canDesEntBod";
                    strSQL+=" , c2.nd_canDesEntCli, c2.nd_canTra, c2.nd_canRev, c2.nd_CanPen, c2.nd_canCon, c2.nd_canNunRec, c2.st_merIngEgrFisBod, c2.tx_nomBodOrgdes";
                    strSQL+=" , c3.co_itmMae, c3.co_itmGrp";
                    strSQL+=" ORDER BY c1.co_reg";
                }
                else if( strTipMovCnfTot.equals("I") ){//ingresos
                    strSQL="";
                    strSQL+="(";
                    strSQL+=" SELECT c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                    strSQL+=" , Null AS co_clides, Null AS tx_rucclides, Null AS tx_nomclides, Null AS tx_dirclides";
                    strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                    strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                    strSQL+=" , c1.co_empDocOri AS co_emp, c1.co_locDocOri AS co_loc, c1.co_tipDocOri AS co_tipDoc, c1.co_docDocOri AS co_doc, c1.co_regDocOri AS co_reg";
                    strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, ABS(c1.nd_can) AS nd_can, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                    strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                    if(objParSis.getCodigoMenu()==4000)
                        strSQL+=" , c1.nd_canTraEgr";
                    else if(objParSis.getCodigoMenu()==4010)
                        strSQL+=" , Null AS nd_canTraEgr";
                    else
                        strSQL+=" , Null AS nd_canTraEgr";

                    strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                    strSQL+=" , c3.co_itmMae, c3.co_itmGrp, Null AS st_terItm";
                    strSQL+=" FROM(";
                    strSQL+=" 		SELECT e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv, e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                    strSQL+=" 		, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocOri, e1.co_docDocOri, e1.ne_numDocOri, e1.co_regDocOri";
                    strSQL+=" 		, e1.co_itm, e1.tx_codAlt, e1.tx_codAlt2, e1.tx_nomItm, e1.tx_uniMed, e1.nd_can, e1.nd_canEgrBod, e1.nd_canDesEntBod";
                    strSQL+=" 		, e1.nd_canDesEntCli, e1.nd_canTra, e1.nd_canRev, e1.nd_CanPen, e1.nd_canCon, e1.nd_canNunRec, e1.st_merIngEgrFisBod, e1.tx_nomBodOrgdes";
                    strSQL+=" 		, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar, e1.nd_canIngBod";
                    strSQL+="  		, e2.co_empEgr, e2.co_locEgr, e2.co_tipDocEgr, e2.co_docEgr, e2.co_regEgr";
                    if(objParSis.getCodigoMenu()==4000)
                        strSQL+="  		, e3.nd_canTra AS nd_canTraEgr";
                    else if(objParSis.getCodigoMenu()==4010)
                        strSQL+="  		, Null AS nd_canTraEgr";
                    else
                        strSQL+="  		, Null AS nd_canTraEgr";

                    strSQL+="  		FROM(";
                    strSQL+=" 			SELECT a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                    strSQL+=" 			, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri, a2.co_reg AS co_regDocOri";
                    strSQL+=" 			, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod";
                    strSQL+=" 			, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen, a2.nd_canCon, a2.nd_canNunRec, a2.st_merIngEgrFisBod, a2.tx_nomBodOrgdes";
                    strSQL+=" 			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.tx_desCor, a3.tx_desLar, a2.nd_canIngBod";
                    strSQL+=" 			FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                    strSQL+=" 			INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                    strSQL+=" 			AND a2.nd_canPen<>0";
                    strSQL+=" 			ORDER BY a1.ne_numDoc";
                    strSQL+="  		) AS e1";
                    strSQL+="  		INNER JOIN(";
                    strSQL+="  			SELECT a1.co_emp AS co_empEgr, a1.co_loc AS co_locEgr, a1.co_tipDoc AS co_tipDocEgr, a1.co_doc AS co_docEgr, a1.co_reg AS co_regEgr";
                    strSQL+="  			, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.co_regRel";
                    strSQL+="  			FROM tbr_detMovInv AS a1";
                    strSQL+="  		) AS e2";
                    strSQL+="  		ON e1.co_empDocOri=e2.co_empRel AND e1.co_locDocOri=e2.co_locRel AND e1.co_tipDocOri=e2.co_tipDocRel AND e1.co_docDocOri=e2.co_docRel AND e1.co_regDocOri=e2.co_regRel";
                    if(objParSis.getCodigoMenu()==4000){
                        strSQL+="  		INNER JOIN(";
                        strSQL+="  			SELECT a2.st_merIngEgrFisBod, a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                        strSQL+="  			, a2.co_reg, a2.co_itm, a2.nd_can, a2.nd_canEgrBod, a2.nd_canIngBod, a2.nd_canDesEntBod, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen";
                        strSQL+="  			, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                        strSQL+="  			FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="  			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="  			WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                        strSQL+="  			AND a2.nd_canTra<>0";
                        strSQL+="  			ORDER BY a1.ne_numDoc";
                        strSQL+="  		) AS e3";
                        strSQL+=" 		ON e2.co_empEgr=e3.co_emp AND e2.co_locEgr=e3.co_loc AND e2.co_tipDocEgr=e3.co_tipDoc AND e2.co_docEgr=e3.co_doc AND e2.co_regEgr=e3.co_reg ";
                    }
                    strSQL+=" ) AS c1";
                    strSQL+="  INNER JOIN(";
                    strSQL+="  	SELECT b1.co_itmMae, b1.co_emp, b1.co_itmEmp, b2.co_itmGrp";
                    strSQL+="  	FROM(";
                    strSQL+="  		SELECT a2.co_itmMae, a2.co_emp, a2.co_itm AS co_itmEmp";
                    strSQL+="  		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="  	) AS b1";
                    strSQL+="  	INNER JOIN(";
                    strSQL+="  		SELECT a2.co_itmMae, a2.co_itm AS co_itmGrp";
                    strSQL+="  		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="  		WHERE a2.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+="  	) AS b2";
                    strSQL+="  	ON b1.co_itmMae=b2.co_itmMae";
                    strSQL+=" ) AS c3";
                    strSQL+=" ON c1.co_empDocOri=c3.co_emp AND c1.co_itm=c3.co_itmEmp";
                    strSQL+=" WHERE c1.co_empDocOri=" + intCodigoEmpresaIngreso + "";//INBOPR
                    strSQL+=" AND c1.co_locDocOri=" + intCodigoLocalIngreso + "";
                    strSQL+=" AND c1.co_tipDocOri=" + intCodigoTipoDocumentoIngreso + "";
                    strSQL+=" AND c1.co_docDocOri=" + intCodigoDocumentoIngreso + "";
                    strSQL+=" AND c1.co_regDocOri=" + intCodigoRegistroIngreso + "";
                    strSQL+=" GROUP BY c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                    strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                    strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                    strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                    strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                    if(objParSis.getCodigoMenu()==4000)
                        strSQL+=" , c1.nd_canTraEgr";
                    strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                    strSQL+=" , c3.co_itmMae, c3.co_itmGrp, c1.nd_can";
                    strSQL+=" ORDER BY c1.co_regDocOri";
                    strSQL+=")";

                    strSQL+=" UNION";
                    strSQL+="(";//codigos L
                    strSQL+=" SELECT c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                    strSQL+=" , Null AS co_clides, Null AS tx_rucclides, Null AS tx_nomclides, Null AS tx_dirclides";
                    strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                    strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                    strSQL+=" , c1.co_empDocOri AS co_emp, c1.co_locDocOri AS co_loc, c1.co_tipDocOri AS co_tipDoc, c1.co_docDocOri AS co_doc, c1.co_regDocOri AS co_reg";
                    strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt AS tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, ABS(c1.nd_can) AS nd_can, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                    strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                    strSQL+=" , Null AS nd_canTraEgr";                        
                    strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                    strSQL+=" , c3.co_itmMae, c3.co_itmGrp, 'L' AS st_terItm";
                    strSQL+=" FROM(";
                    strSQL+=" 	SELECT e1.tx_tipMov, e1.co_empRelCabSolTraInv, e1.co_locRelCabSolTraInv, e1.co_tipDocRelCabSolTraInv, e1.co_docRelCabSolTraInv";
                    strSQL+=" 	, e1.co_empDocOri, e1.co_locDocOri, e1.co_tipDocOri, e1.co_docDocOri, e1.ne_numDocOri, e1.co_regDocOri";
                    strSQL+=" 	, e1.co_itm, e1.tx_codAlt, e1.tx_codAlt2, e1.tx_nomItm, e1.tx_uniMed, e1.nd_can, e1.nd_canEgrBod, e1.nd_canDesEntBod";
                    strSQL+=" 	, e1.nd_canDesEntCli, e1.nd_canTra, e1.nd_canRev, e1.nd_CanPen, e1.nd_canCon, e1.nd_canNunRec, e1.st_merIngEgrFisBod, e1.tx_nomBodOrgdes";
                    strSQL+=" 	, e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.fe_doc, e1.tx_desCor, e1.tx_desLar, e1.nd_canIngBod";
                    strSQL+=" 	FROM(";
                    strSQL+=" 		SELECT a1.tx_tipMov, a1.co_empRelCabSolTraInv, a1.co_locRelCabSolTraInv, a1.co_tipDocRelCabSolTraInv, a1.co_docRelCabSolTraInv";
                    strSQL+=" 		, a1.co_emp AS co_empDocOri, a1.co_loc AS co_locDocOri, a1.co_tipDoc AS co_tipDocOri, a1.co_doc AS co_docDocOri, a1.ne_numDoc AS ne_numDocOri, a2.co_reg AS co_regDocOri";
                    strSQL+=" 		, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_canEgrBod, a2.nd_canDesEntBod";
                    strSQL+=" 		, a2.nd_canDesEntCli, a2.nd_canTra, a2.nd_canRev, a2.nd_CanPen, a2.nd_canCon, a2.nd_canNunRec, a2.st_merIngEgrFisBod, a2.tx_nomBodOrgdes";
                    strSQL+=" 		, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a3.tx_desCor, a3.tx_desLar, a2.nd_canIngBod";
                    strSQL+=" 		FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                    strSQL+=" 		INNER JOIN (tbm_detMovInv AS a2";
                    strSQL+=" 				LEFT OUTER JOIN tbr_detMovInv AS a6";
                    strSQL+=" 				ON a2.co_emp=a6.co_empRel AND a2.co_loc=a6.co_locRel AND a2.co_tipDoc=a6.co_tipDocRel";
                    strSQL+=" 				AND a2.co_doc=a6.co_docRel AND a2.co_reg=a6.co_regRel";
                    strSQL+=" 			   )";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		WHERE a1.st_reg='A' AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S'";
                    strSQL+=" 		AND a2.nd_canPen<>0";
                    strSQL+=" 		ORDER BY a1.ne_numDoc";
                    strSQL+=" 	) AS e1";
                    strSQL+=" ) AS c1";
                    strSQL+="   INNER JOIN(";
                    strSQL+="   	SELECT b1.co_itmMae, b1.co_emp, b1.co_itmEmp, b2.co_itmGrp";
                    strSQL+="   	FROM(";
                    strSQL+="   		SELECT a2.co_itmMae, a2.co_emp, a2.co_itm AS co_itmEmp";
                    strSQL+="   		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="   	) AS b1";
                    strSQL+="   	INNER JOIN(";
                    strSQL+="   		SELECT a2.co_itmMae, a2.co_itm AS co_itmGrp";
                    strSQL+="   		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="   		WHERE a2.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+="   	) AS b2";
                    strSQL+="   	ON b1.co_itmMae=b2.co_itmMae";
                    strSQL+=" ) AS c3";
                    strSQL+="  ON c1.co_empDocOri=c3.co_emp AND c1.co_itm=c3.co_itmEmp";
                    strSQL+=" WHERE c1.co_empDocOri=" + intCodigoEmpresaIngreso + "";//INBOPR
                    strSQL+=" AND c1.co_locDocOri=" + intCodigoLocalIngreso + "";
                    strSQL+=" AND c1.co_tipDocOri=" + intCodigoTipoDocumentoIngreso + "";
                    strSQL+=" AND c1.co_docDocOri=" + intCodigoDocumentoIngreso + "";
                    strSQL+=" AND c1.co_regDocOri=" + intCodigoRegistroIngreso + "";
                    strSQL+=" GROUP BY c1.tx_desCor, c1.tx_desLar, c1.fe_doc";
                    strSQL+=" , c1.tx_tipMov, c1.co_empRelCabSolTraInv, c1.co_locRelCabSolTraInv, c1.co_tipDocRelCabSolTraInv, c1.co_docRelCabSolTraInv";
                    strSQL+=" , c1.co_empDocOri, c1.co_locDocOri, c1.co_tipDocOri, c1.co_docDocOri, c1.ne_numDocOri, c1.co_regDocOri";
                    strSQL+=" , c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_uniMed, c1.nd_canEgrBod, c1.nd_canDesEntBod";
                    strSQL+=" , c1.nd_canDesEntCli, c1.nd_canTra, c1.nd_canIngBod";
                    strSQL+=" , c1.nd_canRev, c1.nd_CanPen, c1.nd_canCon, c1.nd_canNunRec, c1.st_merIngEgrFisBod, c1.tx_nomBodOrgdes";
                    strSQL+=" , c3.co_itmMae, c3.co_itmGrp, c1.nd_can";
                    strSQL+=" ORDER BY c1.co_regDocOri";
                    strSQL+=")";
                }
                System.out.println("isValItmNotCanSufCnf: " + strSQL);
                rstValItmCanSufCnf=stmValItmCanSufCnf.executeQuery(strSQL);
                while(rstValItmCanSufCnf.next()){
                    bdeValCanPueCnf=BigDecimal.ZERO;
                    bdeCanTraEgr=new BigDecimal(rstValItmCanSufCnf.getObject("nd_canTraEgr")==null?"0":(rstValItmCanSufCnf.getString("nd_canTraEgr").equals("")?"0":rstValItmCanSufCnf.getString("nd_canTraEgr"))).abs();
                    if(strTipMovDocOri.equals("V")){
                        if(objParSis.getCodigoMenu()!=4000){//conf. despacho
                            bdeCamValCnf=new BigDecimal(rstValItmCanSufCnf.getObject("" + strCamValCnf + "")==null?"0":(rstValItmCanSufCnf.getString("" + strCamValCnf + "").equals("")?"0":rstValItmCanSufCnf.getString("" + strCamValCnf + ""))).abs();
                        }
                    }
                    else
                        bdeCamValCnf=new BigDecimal(rstValItmCanSufCnf.getObject("" + strCamValCnf + "")==null?"0":(rstValItmCanSufCnf.getString("" + strCamValCnf + "").equals("")?"0":rstValItmCanSufCnf.getString("" + strCamValCnf + ""))).abs();

                    if(objParSis.getCodigoMenu()==4000)
                        strTerItmCnfIng=rstValItmCanSufCnf.getObject("st_terItm")==null?"":rstValItmCanSufCnf.getString("st_terItm");

                    if(strTipMovDocOri.equals("V")){
                        if(objParSis.getCodigoMenu()==4000)//conf. despacho
                            bdeValCanPueCnf=bdeCanTraEgr;//arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCanTraEgr);//
                        else
                            bdeValCanPueCnf=bdeCamValCnf;//arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCamValCnf);//egresos
                    }
                    else{
                        if(objParSis.getCodigoMenu()==4000)//conf. despacho
                            if(strTerItmCnfIng.equals("L"))
                                bdeValCanPueCnf=bdeCamValCnf;//arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCamValCnf);//
                            else
                                bdeValCanPueCnf=bdeCanTraEgr;//arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCanTraEgr);//
                        else{
                            bdeValCanPueCnf=bdeCamValCnf;//arlRegTbl.add(INT_ARL_TBL_DAT_CAN_PUE_CNF, bdeCamValCnf);//egresos                                
                        }
                    }
                    
                    if(bdeValCanPueCnf.compareTo(bdeCantidadConfirmar)>=0){
                        blnRes=true;
                    }
                }
                
                if(blnRes==false){
                    strMsg="";
                    strMsg+="<HTML>Mensaje del sistema Zafiro"; 
                    strMsg+="<body>";
                    strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";

                    strMsg+="<tr bgcolor='#F2E0F7'>";
                    strMsg+="	<th> Item </th>";
                    strMsg+="	<th> Cantidad insuficiente </th>";
                    strMsg+="</tr>";

                    strMsg+="<tr bgcolor='#FF4000'>";
                    strMsg+="	<td>" + strCodigoItemLetra + "</td>";
                    strMsg+="	<td align=right>" + (bdeValCanPueCnf.subtract(bdeCantidadConfirmar)) + "</td>";
                    strMsg+="</tr>";

                    strMsg+="</TABLE>";
                    strMsg+="</BODY>";
                    strMsg+="<BR><FONT COLOR=\"red\">Tenga presente que si existen items con cantidades excedidas, no podrá guardar el documento</FONT>";
                    strMsg+="</HTML>";
                    
                    mostrarMsgInf(strMsg);

                }
                
                rstValItmCanSufCnf.close();
                rstValItmCanSufCnf=null;
                stmValItmCanSufCnf.close();
                stmValItmCanSufCnf=null;
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
     * Función que permite presentar items confirmados parcialmente
     * @return true Si se presentó correctamente la información
     * <BR> false Caso contrario
     */
    private boolean isValItmCanPenCnfPar(){
        boolean blnRes=true;
        BigDecimal bdeValCanPueCnf=BigDecimal.ZERO;//
        BigDecimal bdeValCanUsr=BigDecimal.ZERO;//
        String strMsg="";
        String strCab="";
        String strCue="";
        String strPie="";
        
        try{
            strCab="";
            strCab+="<HTML>Mensaje del sistema Zafiro"; 
            strCab+="<body>";
            strCab+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";

            strCab+="<tr bgcolor='#F2E0F7'>";
            strCab+="	<th colspan='2'> Pendiente confirmar </th>";
            strCab+="</tr>";                
                    
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    bdeValCanPueCnf=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PUE_CNF)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PUE_CNF).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PUE_CNF).toString()));
                    bdeValCanUsr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_CNF_USR).toString()));
                    
                    if((bdeValCanPueCnf.compareTo(bdeValCanUsr)>0)){
                        strCue+="<tr bgcolor='#D0F5A9'>";
                        strCue+="	<td>" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strCue+="	<td align=right>" + (bdeValCanPueCnf.subtract(bdeValCanUsr)) + "</td>";
                        strCue+="</tr>";
                    }
                }
            }
            strPie+="</TABLE>";
            strPie+="</BODY>";
            strPie+="<BR><FONT COLOR=\"red\">Tenga presente que si existen items con cantidades excedidas, no podrá guardar el documento</FONT>";
            strPie+="</HTML>";

            if(strCue.length()>0)
                mostrarMsgInf(strCab+strCue+strPie);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
        
    /**
     * Función que valida si se imprime orden de almacenamiento en la bodega indicada.
     * @return 
     */
    private boolean validarIsBodImpOrdAlm(int intCodBodGrp){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT co_bodOrg AS co_bod FROM tbm_cfgBod ";
                strSQL+=" WHERE st_reg='A' AND st_conIng='S' AND tx_ingGen='A'";
                strSQL+=" AND co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND co_bodDes="+intCodBodGrp;
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    blnRes=true;
                }
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
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
     * Función que envía a imprimir guía de remisión.
     * @return 
     */
    private boolean imprimirGuiRem(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                objImpGuiRem=objGenGuiRem.getObjGuiRem();
                
                //<editor-fold defaultstate="collapsed" desc="/* Descomentar en caso de querer habilitar Modo Offline solo para Dimulti */">
                //22Nov2017: Dennis solicitó trabajar con Modo Offline en la empresa Dimulti. Para las demas empresas contingencia.
                /*if(objImpGuiRem.getIntCodEmp()==4) {  //Dimulti
                    objGenGuiRem.iniciarCntGuiRem(objCnfDoc.getIpModOffLinGuiRem(con), objCnfDoc.getPueSerModOffLinGuiRem(con));
                }
                else{
                    objGenGuiRem.iniciarCntGuiRem(objCnfDoc.getIpConGuiRem(con), objCnfDoc.getPueSerConGuiRem(con));
                }*/
                
                objGenGuiRem.iniciarCntGuiRem(objCnfDoc.getIpConGuiRem(con), objCnfDoc.getPueSerConGuiRem(con));
                //</editor-fold>
                
                objGenGuiRem.imprimirGuiRem(objImpGuiRem.getIntCodEmp(), objImpGuiRem.getIntCodLoc(), objImpGuiRem.getIntCodTipDoc(), objImpGuiRem.getIntCodDoc(), Integer.parseInt(txtCodBodGrp.getText()), objCnfDoc.getIpImpOrdGui(con));
                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Función que envía a imprimir orden de despacho de reservas.
     * @return 
     */
    private boolean imprimirOrdDesRes(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                objCnfDoc.imprimirEgresoReservas(con, intCodSegRef);
                objCnfDoc.imprimirOrdDes(con, intCodSegRef);
                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    
    
}