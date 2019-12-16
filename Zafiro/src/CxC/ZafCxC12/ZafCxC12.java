/*
 * ZafCxC12.java
 *
 * Created on December 13, 2007, 10:14 AM
 */

package CxC.ZafCxC12;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafJCE.ZafJCEAlgAES;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;


/**
 *
 * @author  ilino
 */
public class ZafCxC12 extends javax.swing.JInternalFrame implements Cloneable {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModUno, objTblModDos;
    private ZafTblEdi objTblEdiUno, objTblEdiDos;                              //Editor: Editor del JTable.
//    private ZafTblCelRenLbl objTblCelRenLblUno, objTblCelRenLblDos;            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtUno, objTblCelEdiTxtDos;            //Editor: JTextField en celda.
    private ZafMouMotAdaUno objMouMotAdaUno;                                       //ToolTipText en TableHeader.
    private ZafMouMotAdaDos objMouMotAdaDos;
    private ZafTblPopMnu objTblPopMnuUno, objTblPopMnuDos;                     //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafVenCon vcoPrv;                                                  //Ventana de consulta "Tipo de documento".
    private ZafColNumerada objColNumUno, objColNumDos;
    private ZafTblBus objTblBusUno, objTblBusDos;
    private ZafTblOrd objTblOrdUno, objTblOrdDos;
    
    private Connection conMai, con, conRem, conTmpRea;
    private Statement stmMai, stm, stmRem;
    private ResultSet rstMai, rst, rstRem;
    
    private String strSQL, strAux;
    private Vector vecDatUno, vecCabUno, vecRegUno;
    private Vector vecAuxUno;
    private Vector vecDatDos, vecCabDos, vecRegDos;
    private Vector vecAuxDos;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLisUno, objDocLisDos;
    private String strDesCli;                           //Contenido del campo al obtener el foco.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direcci�n del Beneficiario.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    
    private ZafThreadGUIUno objThrGUIUno;
    private ZafThreadGUIDos objThrGUIDos;
    private ZafThreadGUIPro objThrGUIPro;
    private ZafCxC12.ZafThreadGUIUniTmp objThrGUIUniTmp;
    
    private ZafTblCelEdiButGen objTblCelEdiButGenUnoAnxUno, objTblCelEdiButGenUnoAnxDos;
    private ZafTblCelRenBut    objTblCelRenButUnoAnxUno, objTblCelRenButUnoAnxDos;
    
    private ZafTblCelEdiButGen objTblCelEdiButGenDosAnxUno, objTblCelEdiButGenDosAnxDos;
    private ZafTblCelRenBut    objTblCelRenButDosAnxUno, objTblCelRenButDosAnxDos;
    
    /** Creates new form ZafCxC12 */
    
    //PARA LA PRIMERA TABLA
    final int INT_TBL_DAT_UNO_LIN=0;
    final int INT_TBL_DAT_UNO_COD=1;
    final int INT_TBL_DAT_UNO_IDE=2;
    final int INT_TBL_DAT_UNO_NOM=3;
    final int INT_TBL_DAT_UNO_DIR=4;
    final int INT_TBL_DAT_UNO_TLF=5;
    final int INT_TBL_DAT_UNO_CIU=6;
    final int INT_TBL_DAT_UNO_BUT_ANE_UNO=7;
    final int INT_TBL_DAT_UNO_BUT_ANE_DOS=8;
    final int INT_TBL_DAT_UNO_COD_UNI=9;
    final int INT_TBL_DAT_UNO_IDE_UNI=10;
    final int INT_TBL_DAT_UNO_NOM_UNI=11;
    final int INT_TBL_DAT_UNO_DIR_UNI=12;
    final int INT_TBL_DAT_UNO_TLF_UNI=13;
    final int INT_TBL_DAT_UNO_CIU_UNI=14;
        
    //PARA LA SEGUNDA TABLA
    final int INT_TBL_DAT_DOS_LIN=0;
    final int INT_TBL_DAT_DOS_COD=1;
    final int INT_TBL_DAT_DOS_IDE=2;
    final int INT_TBL_DAT_DOS_NOM=3;
    
    final int INT_TBL_DAT_DOS_DIR=4;
    final int INT_TBL_DAT_DOS_TLF=5;
    final int INT_TBL_DAT_DOS_CIU=6;
    
    final int INT_TBL_DAT_DOS_BUT_ANE_UNO=7;
    final int INT_TBL_DAT_DOS_BUT_ANE_DOS=8;
    
    //private int intMaxCodHis;
    
    //private String datFecAux;
    
    private ArrayList arlRegBenChq, arlDatBenChq;
    final int INT_ARL_BEN_CHQ_COD_EMP=0;
    final int INT_ARL_BEN_CHQ_COD_CLI=1;
    final int INT_ARL_BEN_CHQ_COD_REG=2;    
    
    
    //PARA LA CONEXION REMOTA
    private String strDrv="";
    private String strConIni="";
    private String strUsrIni="";
    private String strPswIni="";
    
    //VARIABLE Q SELECCIONA EL CODIGO DE GRUPO Q SE DEBE PROCESAR
    final int INT_COD_GRP_CNF=7;
    
    ArrayList arlRegCfgRegCnxRem, arlDatCfgRegCnxRem;
    final int INT_COL_CFG_COD_REG=0;
    final int INT_COL_CFG_COD_GRP=1;
    final int INT_COL_CFG_REG_ORI=2;
    final int INT_COL_CFG_REG_DES=3;
    final int INT_COL_CFG_FREC_ACT=4;
    final int INT_COL_CFG_EST_REG=5;
    
    private int intCodEmpLoc;
    private String strConLoc;
    private String strUsrLoc;
    private String strPswLoc;
    
    
    //El siguiente arraylist contendr� los datos que se almacenaron previamente en la tabla temporal tbt_cliuni
    ArrayList arlRegTmpTbl, arlDatTmpTbl;
    final int INT_ARL_TMP_COD_EMP=0;
    final int INT_ARL_TMP_COD_CLI=1;
    final int INT_ARL_TMP_RUC_CLI=2;
    final int INT_ARL_TMP_NOM_CLI=3;
    final int INT_ARL_TMP_DIR_CLI=4;
    final int INT_ARL_TMP_TLF_CLI=5;
    final int INT_ARL_TMP_CIU_CLI=6;    
    final int INT_ARL_TMP_COD_CLI_UNI=7;
    final int INT_ARL_TMP_RUC_CLI_UNI=8;
    final int INT_ARL_TMP_NOM_CLI_UNI=9;
    final int INT_ARL_TMP_DIR_CLI_UNI=10;
    final int INT_ARL_TMP_TLF_CLI_UNI=11;
    final int INT_ARL_TMP_CIU_CLI_UNI=12;
    
    
    
    
    
    
    
    
    
    
                            
                            
    ArrayList arlRegTbhCli, arlDatTbhCli;
    final int INT_ARL_TBH_CLI_COD_EMP=0;
    final int INT_ARL_TBH_CLI_COD_CLI=1;
    final int INT_ARL_TBH_CLI_COD_HIS=2;
    final int INT_ARL_TBH_CLI_EST_CLI=3;
    final int INT_ARL_TBH_CLI_EST_PRV=4;
    final int INT_ARL_TBH_CLI_TIP_IDE=5;
    final int INT_ARL_TBH_CLI_IDE=6;
    final int INT_ARL_TBH_CLI_NOM=7;
    final int INT_ARL_TBH_CLI_DIR=8;
    final int INT_ARL_TBH_CLI_TEL=9;
    final int INT_ARL_TBH_CLI_FAX=10;
    final int INT_ARL_TBH_CLI_CAS=11;
    final int INT_ARL_TBH_CLI_DIR_WEB=12;
    final int INT_ARL_TBH_CLI_COR_ELE=13;
    final int INT_ARL_TBH_CLI_TIP_PER=14;
    final int INT_ARL_TBH_CLI_COD_CIU=15;
    final int INT_ARL_TBH_CLI_COD_ZON=16;
    final int INT_ARL_TBH_CLI_PER_CON=17;
    final int INT_ARL_TBH_CLI_TEL_CON=18;
    final int INT_ARL_TBH_CLI_COR_ELE_CON=19;
    final int INT_ARL_TBH_CLI_COD_VEN=20;
    final int INT_ARL_TBH_CLI_COD_GRP=21;
    final int INT_ARL_TBH_CLI_COD_FOR_PAG=22;
    final int INT_ARL_TBH_CLI_MON_CRE=23;
    final int INT_ARL_TBH_CLI_DIA_GRA=24;
    final int INT_ARL_TBH_CLI_MAX_DES=25;
    final int INT_ARL_TBH_CLI_OBS_UNO=26;
    final int INT_ARL_TBH_CLI_OBS_DOS=27;
    final int INT_ARL_TBH_CLI_EST_REG=28;
    final int INT_ARL_TBH_CLI_FEC_ING=29;
    final int INT_ARL_TBH_CLI_FEC_ULT_MOD=30;
    final int INT_ARL_TBH_CLI_COD_USR_ING=31;
    final int INT_ARL_TBH_CLI_COD_USR_MOD=32;
    final int INT_ARL_TBH_CLI_COD_TIP_PER=33;
    final int INT_ARL_TBH_CLI_REF_UBI=34;
    final int INT_ARL_TBH_CLI_MAR_UTI=35;
    final int INT_ARL_TBH_CLI_EST_REP=36;
    final int INT_ARL_TBH_CLI_CIE_RET_PEN=37;
    final int INT_ARL_TBH_CLI_DIA_GRA_CHQ_FEC=38;
    final int INT_ARL_TBH_CLI_TEL_UNO=39;
    final int INT_ARL_TBH_CLI_TEL_DOS=40;
    final int INT_ARL_TBH_CLI_TEL_TRE=41;
    final int INT_ARL_TBH_CLI_REP_LEG=42;
    final int INT_ARL_TBH_CLI_IDE_PRO=43;
    final int INT_ARL_TBH_CLI_NOM_PRO=44;
    final int INT_ARL_TBH_CLI_DIR_PRO=45;
    final int INT_ARL_TBH_CLI_TEL_PRO=46;
    final int INT_ARL_TBH_CLI_NAC_PRO=47;
    final int INT_ARL_TBH_CLI_FEC_CON_EMP=48;
    final int INT_ARL_TBH_CLI_TIP_ACT_EMP=49;
    final int INT_ARL_TBH_CLI_OBS_PRO=50;
    final int INT_ARL_TBH_CLI_NUM_MAX_VEN_CON=51;
    final int INT_ARL_TBH_CLI_OBS_VEN=52;
    final int INT_ARL_TBH_CLI_OBS_INV=53;
    final int INT_ARL_TBH_CLI_OBS_CXC=54;
    final int INT_ARL_TBH_CLI_OBS_CXP=55;
    final int INT_ARL_TBH_CLI_PRO_ACT_DAT=56;
    final int INT_ARL_TBH_CLI_FEC_HIS=57;
    final int INT_ARL_TBH_CLI_COD_USR_HIS=58;
    final int INT_ARL_TBH_CLI_FE_ULT_ACT_DAT=59;
    //campos agregados posteriormente
    final int INT_ARL_TBH_CLI_OBS_INF_BUR_CRE=60;
    final int INT_ARL_TBH_CLI_NUM_MES_PRO_ACT_DAT=61;
    final int INT_ARL_TBH_CLI_IVA_COM=62;
    final int INT_ARL_TBH_CLI_IVA_VEN=63;
    final int INT_ARL_TBH_CLI_PER_ING_NOM_CLI_COT_VEN=64;
    
    
    
    ArrayList arlRegTbmUniCli, arlDatTbmUniCli;
    final int INT_ARL_TBH_UNI_CLI_COD_EMP=0;
    final int INT_ARL_TBH_UNI_CLI_COD_UNI=1;
    final int INT_ARL_TBH_UNI_CLI_COD_CLI=2;
    final int INT_ARL_TBH_UNI_CLI_COD_HIS=3;
    final int INT_ARL_TBH_UNI_CLI_COD_CLI_UNI=4;
    final int INT_ARL_TBH_UNI_CLI_COD_HIS_UNI=5;
    final int INT_ARL_TBH_UNI_CLI_FEC_UNI=6;
    final int INT_ARL_TBH_UNI_CLI_USR_UNI=7;

    ArrayList arlRegTbhBenChq, arlDatTbhBenChq;
    final int INT_ARL_TBH_BEN_CHQ_COD_EMP=0;
    final int INT_ARL_TBH_BEN_CHQ_COD_CLI=1;
    final int INT_ARL_TBH_BEN_CHQ_COD_REG=2;
    final int INT_ARL_TBH_BEN_CHQ_COD_HIS=3;
    final int INT_ARL_TBH_BEN_CHQ_BEN_CHQ=4;
    final int INT_ARL_TBH_BEN_CHQ_EST_REG=5;
    final int INT_ARL_TBH_BEN_CHQ_FEC_HIS=6;
    final int INT_ARL_TBH_BEN_CHQ_COD_USR_HIS=7;
    final int INT_ARL_TBH_BEN_CHQ_COD_UNI_CLI=8;
    final int INT_ARL_TBH_BEN_CHQ_EST_REG_REP=9;
        
    ArrayList arlRegTbhDirCli, arlDatTbhDirCli;
    final int INT_ARL_TBH_DIR_CLI_COD_EMP=0;
    final int INT_ARL_TBH_DIR_CLI_COD_CLI=1;
    final int INT_ARL_TBH_DIR_CLI_MOD=2;
    final int INT_ARL_TBH_DIR_CLI_COD_REG=3;
    final int INT_ARL_TBH_DIR_CLI_COD_HIS=4;
    final int INT_ARL_TBH_DIR_CLI_DIR=5;
    final int INT_ARL_TBH_DIR_CLI_REF_UBI=6;
    final int INT_ARL_TBH_DIR_CLI_TEL_UNO=7;
    final int INT_ARL_TBH_DIR_CLI_TEL_DOS=8;
    final int INT_ARL_TBH_DIR_CLI_TEL_TRE=9;
    final int INT_ARL_TBH_DIR_CLI_OBS_UNO=10;
    final int INT_ARL_TBH_DIR_CLI_EST_REG=11;
    final int INT_ARL_TBH_DIR_CLI_FEC_ING=12;
    final int INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD=13;
    final int INT_ARL_TBH_DIR_CLI_COD_USR_ING=14;
    final int INT_ARL_TBH_DIR_CLI_COD_USR_MOD=15;
    final int INT_ARL_TBH_DIR_CLI_FEC_HIS=16;
    final int INT_ARL_TBH_DIR_CLI_COD_USR_HIS=17;
    final int INT_ARL_TBH_DIR_CLI_COD_UNI_CLI=18;
    final int INT_ARL_TBH_DIR_CLI_EST_REG_REP=19;
    
    
    
    ArrayList arlRegTbhConCli, arlDatTbhConCli;
    final int INT_ARL_TBH_CON_CLI_COD_EMP=0;
    final int INT_ARL_TBH_CON_CLI_COD_CLI=1;
    final int INT_ARL_TBH_CON_CLI_MOD=2;
    final int INT_ARL_TBH_CON_CLI_COD_REG=3;
    final int INT_ARL_TBH_CON_CLI_COD_HIS=4;
    final int INT_ARL_TBH_CON_CLI_NOM=5;
    final int INT_ARL_TBH_CON_CLI_CAR=6;
    final int INT_ARL_TBH_CON_CLI_TEL_UNO=7;
    final int INT_ARL_TBH_CON_CLI_TEL_DOS=8;
    final int INT_ARL_TBH_CON_CLI_TEL_TRE=9;
    final int INT_ARL_TBH_CON_CLI_COR_ELE_UNO=10;
    final int INT_ARL_TBH_CON_CLI_COR_ELE_DOS=11;
    final int INT_ARL_TBH_CON_CLI_OBS_UNO=12;
    final int INT_ARL_TBH_CON_CLI_EST_REG=13;
    final int INT_ARL_TBH_CON_CLI_FEC_ING=14;
    final int INT_ARL_TBH_CON_CLI_FEC_ULT_MOD=15;
    final int INT_ARL_TBH_CON_CLI_COD_USR_ING=16;
    final int INT_ARL_TBH_CON_CLI_COD_USR_MOD=17;
    final int INT_ARL_TBH_CON_CLI_FEC_HIS=18;
    final int INT_ARL_TBH_CON_CLI_COD_USR_HIS=19;
    final int INT_ARL_TBH_CON_CLI_COD_UNI_CLI=20;
    final int INT_ARL_TBH_CON_CLI_COD_LOC=21;
    final int INT_ARL_TBH_CON_CLI_EST_REG_REP=22;
    
    
    
    
    ArrayList arlRegTbhForPagCli, arlDatTbhForPagCli;
    final int INT_ARL_TBH_FOR_PAG_COD_EMP=0;
    final int INT_ARL_TBH_FOR_PAG_COD_CLI=1;
    final int INT_ARL_TBH_FOR_PAG_COD_REG=2;
    final int INT_ARL_TBH_FOR_PAG_COD_HIS=3;
    final int INT_ARL_TBH_FOR_PAG_COD_FOR_PAG=4;
    final int INT_ARL_TBH_FOR_PAG_EST_REG=5;
    final int INT_ARL_TBH_FOR_PAG_EST_REG_REP=6;
    final int INT_ARL_TBH_FOR_PAG_FE_HIS=7;
    final int INT_ARL_TBH_FOR_PAG_COD_USR_HIS=8;
    final int INT_ARL_TBH_FOR_PAG_COD_UNI_CLI=9;
    
    
    ArrayList arlRegTbhObsCli, arlDatTbhObsCli;
    final int INT_ARL_TBH_OBS_CLI_COD_EMP=0;
    final int INT_ARL_TBH_OBS_CLI_COD_CLI=1;
    final int INT_ARL_TBH_OBS_CLI_COD_REG=2;
    final int INT_ARL_TBH_OBS_CLI_COD_HIS=3;
    final int INT_ARL_TBH_OBS_CLI_FEC_ING=4;
    final int INT_ARL_TBH_OBS_CLI_OBS_UNO=5;
    final int INT_ARL_TBH_OBS_CLI_FEC_HIS=6;
    final int INT_ARL_TBH_OBS_CLI_COD_USR_HIS=7;
    final int INT_ARL_TBH_OBS_CLI_COD_UNI_CLI=8;
    final int INT_ARL_TBH_OBS_CLI_MOD=9;
    
    final int INT_ARL_TBH_OBS_CLI_COD_LOC=10;
    final int INT_ARL_TBH_OBS_CLI_FEC_ULT_MOD=11;
    final int INT_ARL_TBH_OBS_CLI_COD_USR_ING=12;
    final int INT_ARL_TBH_OBS_CLI_COD_USR_MOD=13;
    final int INT_ARL_TBH_OBS_CLI_COM_ING=14;
    final int INT_ARL_TBH_OBS_CLI_COM_ULT_MOD=15;
    final int INT_ARL_TBH_OBS_CLI_EST_REG_REP=16;
    
    
    
    
    
    
    ArrayList arlRegTbhAccEmpCli, arlDatTbhAccEmpCli;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_EMP=0;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_CLI=1;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_REG=2;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_HIS=3;
    final int INT_ARL_TBH_ACC_EMP_CLI_NOM=4;
    final int INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS=5;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS=6;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_UNI_CLI=7;
    
    
    ArrayList arlRegTbhSolCre, arlDatTbhSolCre;
    final int INT_ARL_TBH_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBH_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBH_SOL_CRE_COD_HIS=2;
    final int INT_ARL_TBH_SOL_CRE_COD_CLI=3;
    final int INT_ARL_TBH_SOL_CRE_FEC_SOL=4;
    final int INT_ARL_TBH_SOL_CRE_COD_FOR_PAG=5;
    final int INT_ARL_TBH_SOL_CRE_MON_CRE=6;
    final int INT_ARL_TBH_SOL_CRE_EST_ANA_SOL=7;
    final int INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL=8;
    final int INT_ARL_TBH_SOL_CRE_COD_USU_ANA_SOL=9;
    final int INT_ARL_TBH_SOL_CRE_OBS_UNO=10;
    final int INT_ARL_TBH_SOL_CRE_OBS_DOS=11;
    final int INT_ARL_TBH_SOL_CRE_EST_REG=12;
    final int INT_ARL_TBH_SOL_CRE_FEC_ING=13;
    final int INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD=14;
    final int INT_ARL_TBH_SOL_CRE_COD_USR_ING=15;
    final int INT_ARL_TBH_SOL_CRE_COD_USR_MOD=16;
    final int INT_ARL_TBH_SOL_CRE_FEC_HIS=17;
    final int INT_ARL_TBH_SOL_CRE_COD_USR_HIS=18;
    final int INT_ARL_TBH_SOL_CRE_COD_UNI_CLI=19;
       
    
    ArrayList arlRegTbhCabCotVen, arlDatTbhCabCotVen;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_EMP=0;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_LOC=1;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_COT=2;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_HIS=3;
    final int INT_ARL_TBH_CAB_COT_VEN_FEC_COT=4;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_CLI=5;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_VEN=6;
    final int INT_ARL_TBH_CAB_COT_VEN_ATE=7;
    final int INT_ARL_TBH_CAB_COT_VEN_NUM_PED=8;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_FOR_PAG=9;
    final int INT_ARL_TBH_CAB_COT_VEN_SUB=10;
    final int INT_ARL_TBH_CAB_COT_VEN_POR_IVA=11;
    final int INT_ARL_TBH_CAB_COT_VEN_VAL_IVA=12;
    final int INT_ARL_TBH_CAB_COT_VEN_VAL_DES=13;
    final int INT_ARL_TBH_CAB_COT_VEN_TOT=14;
    final int INT_ARL_TBH_CAB_COT_VEN_VAL=15;
    final int INT_ARL_TBH_CAB_COT_VEN_OBS_UNO=16;
    final int INT_ARL_TBH_CAB_COT_VEN_OBS_DOS=17;
    final int INT_ARL_TBH_CAB_COT_VEN_EST_REG=18;
    final int INT_ARL_TBH_CAB_COT_VEN_FEC_ING=19;
    final int INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD=20;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_USR_ING=21;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_USR_MOD=22;
    final int INT_ARL_TBH_CAB_COT_VEN_OBS_SOL_AUT=23;
    final int INT_ARL_TBH_CAB_COT_VEN_OBS_AUT_SOL=24;
    final int INT_ARL_TBH_CAB_COT_VEN_AUT=25;
    final int INT_ARL_TBH_CAB_COT_VEN_FEC_HIS=26;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_USR_HIS=27;
    final int INT_ARL_TBH_CAB_COT_VEN_COD_UNI_CLI=28;
    
    final int INT_ARL_TBH_CAB_COT_VEN_NOM_CLI=29;
    final int INT_ARL_TBH_CAB_COT_VEN_EST_REG_REP=30;
    
    
    ArrayList arlRegTbhDetCotVen, arlDatTbhDetCotVen;
    final int INT_ARL_TBH_DET_COT_VEN_COD_EMP=0;
    final int INT_ARL_TBH_DET_COT_VEN_COD_LOC=1;
    final int INT_ARL_TBH_DET_COT_VEN_COD_COT=2;
    final int INT_ARL_TBH_DET_COT_VEN_COD_HIS=3;
    final int INT_ARL_TBH_DET_COT_VEN_COD_REG=4;
    final int INT_ARL_TBH_DET_COT_VEN_COD_ITM=5;
    final int INT_ARL_TBH_DET_COT_VEN_COD_ALT=6;
    final int INT_ARL_TBH_DET_COT_VEN_COD_ALT_DOS=7;
    final int INT_ARL_TBH_DET_COT_VEN_NOM_ITM=8;
    final int INT_ARL_TBH_DET_COT_VEN_COD_BOD=9;
    final int INT_ARL_TBH_DET_COT_VEN_CAN=10;
    final int INT_ARL_TBH_DET_COT_VEN_PRE_UNI=11;
    final int INT_ARL_TBH_DET_COT_VEN_POR_DES=12;
    final int INT_ARL_TBH_DET_COT_VEN_IVA_VEN=13;
    final int INT_ARL_TBH_DET_COT_VEN_PRE_COM=14;
    final int INT_ARL_TBH_DET_COT_VEN_POR_DES_PRE_COM=15;
    final int INT_ARL_TBH_DET_COT_VEN_COD_PRV=16;
    final int INT_ARL_TBH_DET_COT_VEN_FEC_HIS=17;
    final int INT_ARL_TBH_DET_COT_VEN_COD_USR_HIS=18;
    final int INT_ARL_TBH_DET_COT_VEN_COD_UNI_CLI=19;
    final int INT_ARL_TBH_DET_COT_VEN_EST_TRA_AUT_TOT=20;
    final int INT_ARL_TBH_DET_COT_VEN_EST_REG_REP=21;
    
    
    ArrayList arlRegTbhPagCotVen, arlDatTbhPagCotVen;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_EMP=0;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_LOC=1;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_COT=2;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_HIS=3;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_REG=4;
    final int INT_ARL_TBH_PAG_COT_VEN_DIA_CRE=5;
    final int INT_ARL_TBH_PAG_COT_VEN_FEC_VEN=6;
    final int INT_ARL_TBH_PAG_COT_VEN_POR_RET=7;
    final int INT_ARL_TBH_PAG_COT_VEN_MON_PAG=8;
    final int INT_ARL_TBH_PAG_COT_VEN_DIA_GRA=9;
    final int INT_ARL_TBH_PAG_COT_VEN_FEC_HIS=10;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_USR_HIS=11;
    final int INT_ARL_TBH_PAG_COT_VEN_COD_UNI_CLI=12;
    
    final int INT_ARL_TBH_PAG_COT_VEN_COD_TIP_RET=13;
    final int INT_ARL_TBH_PAG_COT_VEN_EST_REG_REP=14;
    
    ArrayList arlRegTbhCabCotCom, arlDatTbhCabCotCom;
    final int INT_ARL_TBH_CAB_COT_COM_COD_EMP=0;
    final int INT_ARL_TBH_CAB_COT_COM_COD_LOC=1;
    final int INT_ARL_TBH_CAB_COT_COM_COD_COT=2;
    final int INT_ARL_TBH_CAB_COT_COM_COD_HIS=3;
    final int INT_ARL_TBH_CAB_COT_COM_FEC_COT=4;
    final int INT_ARL_TBH_CAB_COT_COM_COD_PRV=5;
    final int INT_ARL_TBH_CAB_COT_COM_COD_COM=6;
    final int INT_ARL_TBH_CAB_COT_COM_ATE=7;
    final int INT_ARL_TBH_CAB_COT_COM_COD_FOR_PAG=8;
    final int INT_ARL_TBH_CAB_COT_COM_SUB=9;
    final int INT_ARL_TBH_CAB_COT_COM_POR_IVA=10;
    final int INT_ARL_TBH_CAB_COT_COM_VAL_IVA=11;
    final int INT_ARL_TBH_CAB_COT_COM_VAL_DES=12;
    final int INT_ARL_TBH_CAB_COT_COM_TOT=13;
    final int INT_ARL_TBH_CAB_COT_COM_OBS_UNO=14;
    final int INT_ARL_TBH_CAB_COT_COM_OBS_DOS=15;
    final int INT_ARL_TBH_CAB_COT_COM_EST_REG=16;
    final int INT_ARL_TBH_CAB_COT_COM_FEC_ING=17;
    final int INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD=18;
    final int INT_ARL_TBH_CAB_COT_COM_COD_USR_ING=19;
    final int INT_ARL_TBH_CAB_COT_COM_COD_USR_MOD=20;
    final int INT_ARL_TBH_CAB_COT_COM_OBS_SOL_AUT=21;
    final int INT_ARL_TBH_CAB_COT_COM_OBS_AUT_SOL=22;
    final int INT_ARL_TBH_CAB_COT_COM_AUT=23;
    final int INT_ARL_TBH_CAB_COT_COM_FEC_HIS=24;
    final int INT_ARL_TBH_CAB_COT_COM_COD_USR_HIS=25;
    final int INT_ARL_TBH_CAB_COT_COM_COD_UNI_CLI=26;
    final int INT_ARL_TBH_CAB_COT_COM_EST_REG_REP=27;
    
    
    ArrayList arlRegTbhDetCotCom, arlDatTbhDetCotCom;
    final int INT_ARL_TBH_DET_COT_COM_COD_EMP=0;
    final int INT_ARL_TBH_DET_COT_COM_COD_LOC=1;
    final int INT_ARL_TBH_DET_COT_COM_COD_COT=2;
    final int INT_ARL_TBH_DET_COT_COM_COD_HIS=3;
    final int INT_ARL_TBH_DET_COT_COM_COD_REG=4;
    final int INT_ARL_TBH_DET_COT_COM_COD_ITM=5;
    final int INT_ARL_TBH_DET_COT_COM_NOM_ITM=6;
    final int INT_ARL_TBH_DET_COT_COM_COD_BOD=7;
    final int INT_ARL_TBH_DET_COT_COM_CAN=8;
    final int INT_ARL_TBH_DET_COT_COM_COS_UNI=9;
    final int INT_ARL_TBH_DET_COT_COM_POR_DES=10;
    final int INT_ARL_TBH_DET_COT_COM_IVA_COM=11;
    final int INT_ARL_TBH_DET_COT_COM_FEC_HIS=12;
    final int INT_ARL_TBH_DET_COT_COM_COD_USR_HIS=13;
    final int INT_ARL_TBH_DET_COT_COM_COD_UNI_CLI=14;
    final int INT_ARL_TBH_DET_COT_COM_EST_REP=15;

    
    ArrayList arlRegTbhPagCotCom, arlDatTbhPagCotCom;
    final int INT_ARL_TBH_PAG_COT_COM_COD_EMP=0;
    final int INT_ARL_TBH_PAG_COT_COM_COD_LOC=1;
    final int INT_ARL_TBH_PAG_COT_COM_COD_COT=2;
    final int INT_ARL_TBH_PAG_COT_COM_COD_HIS=3;
    final int INT_ARL_TBH_PAG_COT_COM_COD_REG=4;
    final int INT_ARL_TBH_PAG_COT_COM_DIA_CRE=5;
    final int INT_ARL_TBH_PAG_COT_COM_FEC_VEN=6;
    final int INT_ARL_TBH_PAG_COT_COM_MON_PAG=7;
    final int INT_ARL_TBH_PAG_COT_COM_DIA_GRA=8;
    final int INT_ARL_TBH_PAG_COT_COM_FEC_HIS=9;
    final int INT_ARL_TBH_PAG_COT_COM_COD_USR_HIS=10;
    final int INT_ARL_TBH_PAG_COT_COM_COD_UNI_CLI=11;
    final int INT_ARL_TBH_PAG_COT_COM_EST_REG_REP=12;
    
    
    ArrayList arlRegTbhCabMovInv, arlDatTbhCabMovInv;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_EMP=0;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_LOC=1;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC=2;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_DOC=3;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_HIS=4;
    final int INT_ARL_TBH_CAB_MOV_INV_SEC_GRP=5;
    final int INT_ARL_TBH_CAB_MOV_INV_SEC_EMP=6;
    final int INT_ARL_TBH_CAB_MOV_INV_NUM_COT=7;
    final int INT_ARL_TBH_CAB_MOV_INV_NUM_DOC=8;
    final int INT_ARL_TBH_CAB_MOV_INV_SEC_DOC=9;
    final int INT_ARL_TBH_CAB_MOV_INV_NUM_PED=10;
    final int INT_ARL_TBH_CAB_MOV_INV_NUM_GUI=11;
    final int INT_ARL_TBH_CAB_MOV_INV_NUM_AUT_SRI=12;
    final int INT_ARL_TBH_CAB_MOV_INV_FEC_CAD=13;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_DIA=14;
    final int INT_ARL_TBH_CAB_MOV_INV_FEC_DOC=15;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_CLI=16;
    final int INT_ARL_TBH_CAB_MOV_INV_RUC=17;
    final int INT_ARL_TBH_CAB_MOV_INV_NOM_CLI=18;
    final int INT_ARL_TBH_CAB_MOV_INV_DIR_CLI=19;
    final int INT_ARL_TBH_CAB_MOV_INV_TEL_CLI=20;
    final int INT_ARL_TBH_CAB_MOV_INV_CIU_CLI=21;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_COM=22;
    final int INT_ARL_TBH_CAB_MOV_INV_NOM_VEN=23;
    final int INT_ARL_TBH_CAB_MOV_INV_ATE=24;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_FOR_PAG=25;
    final int INT_ARL_TBH_CAB_MOV_INV_DES_FOR_PAG=26;
    final int INT_ARL_TBH_CAB_MOV_INV_SUB=27;
    final int INT_ARL_TBH_CAB_MOV_INV_POR_IVA=28;
    final int INT_ARL_TBH_CAB_MOV_INV_VAL_IVA=29;
    final int INT_ARL_TBH_CAB_MOV_INV_TOT=30;
    final int INT_ARL_TBH_CAB_MOV_INV_PTO_PAR=31;
    final int INT_ARL_TBH_CAB_MOV_INV_TRA=32;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_MOT_TRA=33;
    final int INT_ARL_TBH_CAB_MOV_INV_DES_MOT_TRA=34;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_CTA=35;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_MOT_DOC=36;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_MNU=37;
    final int INT_ARL_TBH_CAB_MOV_INV_IMP=38;
    final int INT_ARL_TBH_CAB_MOV_INV_OBS_UNO=39;
    final int INT_ARL_TBH_CAB_MOV_INV_OBS_DOS=40;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_REG=41;
    final int INT_ARL_TBH_CAB_MOV_INV_FEC_ING=42;
    final int INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD=43;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_USR_ING=44;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_USR_MOD=45;
    final int INT_ARL_TBH_CAB_MOV_INV_FEC_CON=46;
    final int INT_ARL_TBH_CAB_MOV_INV_OBS_TRE=47;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_FOR_RET=48;
    final int INT_ARL_TBH_CAB_MOV_INV_VEH_RET=49;
    final int INT_ARL_TBH_CAB_MOV_INV_CHO_RET=50;
    final int INT_ARL_TBH_CAB_MOV_INV_OBS_SOL_AUT=51;
    final int INT_ARL_TBH_CAB_MOV_INV_OBS_AUT_SOL=52;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_AUT=53;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV=54;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV=55;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_REG_REP=56;
    final int INT_ARL_TBH_CAB_MOV_INV_NUM_DOC_REE=57;
    final int INT_ARL_TBH_CAB_MOV_INV_FE_HIS=58;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_USR_HIS=59;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_CLI_UNI=60;
    final int INT_ARL_TBH_CAB_MOV_INV_FEC_VEN=61;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_CRE_GUI_REM=62;
    final int INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV_TRA_AUT=63;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC_REL_SOL_DEV_VEN=64;
    final int INT_ARL_TBH_CAB_MOV_INV_COD_DOC_REL_SOL_DEV_VEN=65;
    
    
    
    ArrayList arlRegTbhDetMovInv, arlDatTbhDetMovInv;
    final int INT_ARL_TBH_DET_MOV_INV_COD_EMP=0;
    final int INT_ARL_TBH_DET_MOV_INV_COD_LOC=1;
    final int INT_ARL_TBH_DET_MOV_INV_COD_TIP_DOC=2;
    final int INT_ARL_TBH_DET_MOV_INV_COD_DOC=3;
    final int INT_ARL_TBH_DET_MOV_INV_COD_HIS=4;
    final int INT_ARL_TBH_DET_MOV_INV_COD_REG=5;
    final int INT_ARL_TBH_DET_MOV_INV_NUM_FIL=6;
    final int INT_ARL_TBH_DET_MOV_INV_COD_ITM=7;
    final int INT_ARL_TBH_DET_MOV_INV_COD_ALT=8;
    final int INT_ARL_TBH_DET_MOV_INV_COD_ALT_DOS=9;
    final int INT_ARL_TBH_DET_MOV_INV_NOM_ITM=10;
    final int INT_ARL_TBH_DET_MOV_INV_UNI_MED=11;
    final int INT_ARL_TBH_DET_MOV_INV_COD_BOD=12;
    final int INT_ARL_TBH_DET_MOV_INV_CAN=13;
    final int INT_ARL_TBH_DET_MOV_INV_CAN_ORG=14;
    final int INT_ARL_TBH_DET_MOV_INV_CAN_DEV=15;
    final int INT_ARL_TBH_DET_MOV_INV_COS_UNI=16;
    final int INT_ARL_TBH_DET_MOV_INV_PRE_UNI=17;
    final int INT_ARL_TBH_DET_MOV_INV_POR_DES=18;
    final int INT_ARL_TBH_DET_MOV_INV_IVA_COM=19;
    final int INT_ARL_TBH_DET_MOV_INV_TOT=20;
    final int INT_ARL_TBH_DET_MOV_INV_COS_TOT=21;
    final int INT_ARL_TBH_DET_MOV_INV_EXI=22;
    final int INT_ARL_TBH_DET_MOV_INV_VAL_EXI=23;
    final int INT_ARL_TBH_DET_MOV_INV_COS_PRO=24;
    final int INT_ARL_TBH_DET_MOV_INV_COS_UNI_GRP=25;
    final int INT_ARL_TBH_DET_MOV_INV_COS_TOT_GRP=26;
    final int INT_ARL_TBH_DET_MOV_INV_EXI_GRP=27;
    final int INT_ARL_TBH_DET_MOV_INV_VAL_EXI_GRP=28;
    final int INT_ARL_TBH_DET_MOV_INV_COS_PRO_GRP=29;
    final int INT_ARL_TBH_DET_MOV_INV_CAN_CON=30;
    final int INT_ARL_TBH_DET_MOV_INV_OBS_UNO=31;
    final int INT_ARL_TBH_DET_MOV_INV_USR_CON=32;
    final int INT_ARL_TBH_DET_MOV_INV_EST_REG_REP=33;
    final int INT_ARL_TBH_DET_MOV_INV_FEC_HIS=34;
    final int INT_ARL_TBH_DET_MOV_INV_COD__USR_HIS=35;
    final int INT_ARL_TBH_DET_MOV_INV_COD_UNI_CLI=36;
    final int INT_ARL_TBH_DET_MOV_INV_COD_ITM_ACT=37;
    
    
    
    
    ArrayList arlRegTbhPagMovInv, arlDatTbhPagMovInv;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_EMP=0;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_LOC=1;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_TIP_DOC=2;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_DOC=3;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_HIS=4;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_REG=5;
    final int INT_ARL_TBH_PAG_MOV_INV_DIA_CRE=6;
    final int INT_ARL_TBH_PAG_MOV_INV_FEC_VEN=7;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_TIP_RET=8;
    final int INT_ARL_TBH_PAG_MOV_INV_POR_RET=9;
    final int INT_ARL_TBH_PAG_MOV_INV_APL_RET=10;
    final int INT_ARL_TBH_PAG_MOV_INV_MON_PAG=11;
    final int INT_ARL_TBH_PAG_MOV_INV_DIA_GRA=12;
    final int INT_ARL_TBH_PAG_MOV_INV_ABO=13;
    final int INT_ARL_TBH_PAG_MOV_INV_EST_SOP=14;
    final int INT_ARL_TBH_PAG_MOV_INV_EST_ENT_SOP=15;
    final int INT_ARL_TBH_PAG_MOV_INV_EST_POS=16;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_BAN_CHQ=17;
    final int INT_ARL_TBH_PAG_MOV_INV_NUM_CTA_CHQ=18;
    final int INT_ARL_TBH_PAG_MOV_INV_NUM_CHQ=19;
    final int INT_ARL_TBH_PAG_MOV_INV_FEC_REC_CHQ=20;
    final int INT_ARL_TBH_PAG_MOV_INV_FEC_VEN_CHQ=21;
    final int INT_ARL_TBH_PAG_MOV_INV_MON_CHQ=22;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_PRO_CHQ=23;
    final int INT_ARL_TBH_PAG_MOV_INV_EST_REG=24;
    final int INT_ARL_TBH_PAG_MOV_INV_EST_REG_REP=25;
    final int INT_ARL_TBH_PAG_MOV_INV_FE_HIS=26;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_USR_HIS=27;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_UNI_CLI=28;
    final int INT_ARL_TBH_PAG_MOV_INV_FEC_REE=29;
    final int INT_ARL_TBH_PAG_MOV_INV_COD_USR_REE=30;
    final int INT_ARL_TBH_PAG_MOV_INV_COM_REE=31;
    
    
    
    ArrayList arlRegTbhCabPag, arlDatTbhCabPag;
    final int INT_ARL_TBH_CAB_PAG_COD_EMP=0;
    final int INT_ARL_TBH_CAB_PAG_COD_LOC=1;
    final int INT_ARL_TBH_CAB_PAG_COD_TIP_DOC=2;
    final int INT_ARL_TBH_CAB_PAG_COD_DOC=3;
    final int INT_ARL_TBH_CAB_PAG_COD_HIS=4;
    final int INT_ARL_TBH_CAB_PAG_FEC_DOC=5;
    final int INT_ARL_TBH_CAB_PAG_FEC_VEN=6;
    final int INT_ARL_TBH_CAB_PAG_NUM_DOC_UNO=7;
    final int INT_ARL_TBH_CAB_PAG_NUM_DOC_DOS=8;
    final int INT_ARL_TBH_CAB_PAG_COD_DIA=9;
    final int INT_ARL_TBH_CAB_PAG_COD_CTA=10;
    final int INT_ARL_TBH_CAB_PAG_COD_CLI=11;
    final int INT_ARL_TBH_CAB_PAG_RUC=12;
    final int INT_ARL_TBH_CAB_PAG_NOM_CLI=13;
    final int INT_ARL_TBH_CAB_PAG_DIR_CLI=14;
    final int INT_ARL_TBH_CAB_PAG_COD_BEN=15;
    final int INT_ARL_TBH_CAB_PAG_BEN_CHQ=16;
    final int INT_ARL_TBH_CAB_PAG_MON_DOC=17;
    final int INT_ARL_TBH_CAB_PAG_MON_DOC_PAG_UNO=18;
    final int INT_ARL_TBH_CAB_PAG_COD_MNU=19;
    final int INT_ARL_TBH_CAB_PAG_EST_IMP=20;
    final int INT_ARL_TBH_CAB_PAG_OBS_UNO=21;
    final int INT_ARL_TBH_CAB_PAG_OBS_DOS=22;
    final int INT_ARL_TBH_CAB_PAG_EST_REG=23;
    final int INT_ARL_TBH_CAB_PAG_FEC_ING=24;
    final int INT_ARL_TBH_CAB_PAG_FEC_ULT_MOD=25;
    final int INT_ARL_TBH_CAB_PAG_COD_USR_ING=26;
    final int INT_ARL_TBH_CAB_PAG_COD_USR_MOD=27;
    final int INT_ARL_TBH_CAB_PAG_OBS_SOL_AUT=28;
    final int INT_ARL_TBH_CAB_PAG_OBS_AUT_SOL=29;
    final int INT_ARL_TBH_CAB_PAG_EST_AUT=30;
    final int INT_ARL_TBH_CAB_PAG_FEC_HIS=31;
    final int INT_ARL_TBH_CAB_PAG_COD_USR_HIS=32;
    final int INT_ARL_TBH_CAB_PAG_COD_UNI_CLI=33;
    final int INT_ARL_TBH_CAB_PAG_EST_REG_REP=34;
    final int INT_ARL_TBH_CAB_PAG_EST_CON_DEP_BAN=35;

    
    
    ArrayList arlRegTbhDetPag, arlDatTbhDetPag;
    final int INT_ARL_TBH_DET_PAG_COD_EMP=0;
    final int INT_ARL_TBH_DET_PAG_COD_LOC=1;
    final int INT_ARL_TBH_DET_PAG_COD_TIP_DOC=2;
    final int INT_ARL_TBH_DET_PAG_COD_DOC=3;
    final int INT_ARL_TBH_DET_PAG_COD_HIS=4;
    final int INT_ARL_TBH_DET_PAG_COD_REG=5;
    final int INT_ARL_TBH_DET_PAG_COD_LOC_PAG=6;
    final int INT_ARL_TBH_DET_PAG_COD_TIP_DOC_PAG=7;
    final int INT_ARL_TBH_DET_PAG_COD_DOC_PAG=8;
    final int INT_ARL_TBH_DET_PAG_COD_REG_PAG=9;
    final int INT_ARL_TBH_DET_PAG_ABO=10;
    final int INT_ARL_TBH_DET_PAG_COD_TIP_DOC_CON=11;
    final int INT_ARL_TBH_DET_PAG_COD_BAN_CHQ=12;
    final int INT_ARL_TBH_DET_PAG_NUM_CTA_CHQ=13;
    final int INT_ARL_TBH_DET_PAG_NUM_CHQ=14;
    final int INT_ARL_TBH_DET_PAG_FEC_REC_CHQ=15;
    final int INT_ARL_TBH_DET_PAG_FEC_VEN_CHQ=16;
    final int INT_ARL_TBH_DET_PAG_COD_SRI=17;
    final int INT_ARL_TBH_DET_PAG_FEC_HIS=18;
    final int INT_ARL_TBH_DET_PAG_COD_USR_HIS=19;
    final int INT_ARL_TBH_DET_PAG_COD_UNI_CLI=20;
    final int INT_ARL_TBH_DET_PAG_EST_REG_REP=21;
    
    ArrayList arlRegTbhRefComSolCre, arlDatTbhRefComSolCre;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_HIS=2;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_REF=3;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_REF=4;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_NOM=5;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_MAT_PRO_REF=6;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_TEL=7;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_TIE=8;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_CUP_CRE=9;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_PLA_CRE=10;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_FOR_PAG=11;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_EST_PRO=12;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_CAL=13;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_INF=14;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_CAR_INF=15;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_OBS_UNO=16;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_EST_REG=17;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING=18;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD=19;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_ING=20;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_MOD=21;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_FEC_HIS=22;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_HIS=23;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_COD_UNI_CLI=24;
    final int INT_ARL_TBH_REF_COM_SOL_CRE_NUM_PRO=25;
    
    ArrayList arlRegTbhRefBanSolCre, arlDatTbhRefBanSolCre;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_HIS=2;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_REF=3;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_REF=4;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_NOM=5;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_AGE=6;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_OFI_CTA=7;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_CTA=8;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_DUE_CTA=9;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_CIU_APE_CTA=10;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA=11;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_SAL_PRO_CTA=12;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_PRO=13;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_DIR=14;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_IND=15;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_PRO=16;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_PRO=17;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_DIR=18;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_IND=19;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_INF=20;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_CAR_INF=21;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_DOC_DIG=22;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_UNO=23;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_EST_REG=24;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING=25;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD=26;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_ING=27;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_MOD=28;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_HIS=29;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_HIS=30;
    final int INT_ARL_TBH_REF_BAN_SOL_CRE_COD_UNI_CLI=31;
    
    ArrayList arlRegTbhDocDigSolCre, arlDatTbhDocDigSolCre;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_HIS=2;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_DOC=3;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_NOM=4;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_UBI=5;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_OBS_UNO=6;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_EST_REG=7;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING=8;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD=9;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_ING=10;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_MOD=11;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_HIS=12;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_HIS=13;
    final int INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_UNI_CLI=14;
    
    //PARA UPDATES 
    ArrayList arlRegSelUpdCabCotVen, arlDatSelUpdCabCotVen;
    final int INT_ARL_TBM_CAB_COT_VEN_COD_EMP=0;
    final int INT_ARL_TBM_CAB_COT_VEN_COD_LOC=1;
    final int INT_ARL_TBM_CAB_COT_VEN_COD_DOC=2;
    final int INT_ARL_TBM_CAB_COT_VEN_COD_CLI=3;
    
    ArrayList arlRegSelUpdDetCotVen, arlDatSelUpdDetCotVen;
    final int INT_ARL_TBM_DET_COT_VEN_COD_EMP=0;
    final int INT_ARL_TBM_DET_COT_VEN_COD_LOC=1;
    final int INT_ARL_TBM_DET_COT_VEN_COD_DOC=2;
    final int INT_ARL_TBM_DET_COT_VEN_COD_PRV=3;
    
    
    ArrayList arlRegSelUpdCabCotCom, arlDatSelUpdCabCotCom;
    final int INT_ARL_TBM_CAB_COT_COM_COD_EMP=0;
    final int INT_ARL_TBM_CAB_COT_COM_COD_LOC=1;
    final int INT_ARL_TBM_CAB_COT_COM_COD_DOC=2;
    final int INT_ARL_TBM_CAB_COT_COM_COD_PRV=3;
    
    ArrayList arlRegSelUpdCabMovInv, arlDatSelUpdCabMovInv;
    final int INT_ARL_TBM_CAB_MOV_INV_COD_EMP=0;
    final int INT_ARL_TBM_CAB_MOV_INV_COD_LOC=1;
    final int INT_ARL_TBM_CAB_MOV_INV_COD_TIP_DOC=2;
    final int INT_ARL_TBM_CAB_MOV_INV_COD_DOC=3;
    final int INT_ARL_TBM_CAB_MOV_INV_COD_CLI=4;


    ArrayList arlRegSelUpdCabGuiRem, arlDatSelUpdCabGuiRem;
    final int INT_ARL_TBM_CAB_GUI_REM_COD_EMP=0;
    final int INT_ARL_TBM_CAB_GUI_REM_COD_LOC=1;
    final int INT_ARL_TBM_CAB_GUI_REM_COD_TIP_DOC=2;
    final int INT_ARL_TBM_CAB_GUI_REM_COD_DOC=3;
    final int INT_ARL_TBM_CAB_GUI_REM_COD_CLI=4;
    final int INT_ARL_TBM_CAB_GUI_REM_RUC_CLI=5;
    final int INT_ARL_TBM_CAB_GUI_REM_NOM_CLI=6;
    final int INT_ARL_TBM_CAB_GUI_REM_DIR_CLI=7;
    final int INT_ARL_TBM_CAB_GUI_REM_TEL_CLI=8;
    final int INT_ARL_TBM_CAB_GUI_REM_CIU_CLI=9;
    

    
    ArrayList arlRegSelUpdBenChq, arlDatSelUpdBenChq;
    final int INT_ARL_TBM_BEN_CHQ_COD_EMP=0;
    final int INT_ARL_TBM_BEN_CHQ_COD_CLI=1;
    final int INT_ARL_TBM_BEN_CHQ_EST_REG=2;
    final int INT_ARL_TBM_BEN_CHQ_COD_REG=3;
    final int INT_ARL_TBM_BEN_CHQ_COD_CLI_UNI=4;
    final int INT_ARL_TBM_BEN_CHQ_COD_REG_FIL=5;
    
    
    ArrayList arlRegSelUpdCabPag, arlDatSelUpdCabPag;
    final int INT_ARL_TBM_CAB_PAG_COD_EMP=0;
    final int INT_ARL_TBM_CAB_PAG_COD_LOC=1;
    final int INT_ARL_TBM_CAB_PAG_COD_TIP_DOC=2;
    final int INT_ARL_TBM_CAB_PAG_COD_DOC=3;
    final int INT_ARL_TBM_CAB_PAG_COD_CLI=4;
    final int INT_ARL_TBM_CAB_PAG_COD_BEN_CHQ=5;
    
    ArrayList arlRegSelUpdDirCli, arlDatSelUpdDirCli;
    final int INT_ARL_TBM_DIR_CLI_COD_EMP=0;
    final int INT_ARL_TBM_DIR_CLI_COD_CLI=1;
    
    ArrayList arlRegSelUpdConCli, arlDatSelUpdConCli;
    final int INT_ARL_TBM_CON_CLI_COD_EMP=0;
    final int INT_ARL_TBM_CON_CLI_COD_CLI=1;

    ArrayList arlRegSelUpdForPagCli, arlDatSelUpdForPagCli;
    final int INT_ARL_TBM_FOR_PAG_CLI_COD_EMP=0;
    final int INT_ARL_TBM_FOR_PAG_CLI_COD_CLI=1;
    
    ArrayList arlRegSelUpdObsCli, arlDatSelUpdObsCli;
    final int INT_ARL_TBM_OBS_CLI_COD_EMP=0;
    final int INT_ARL_TBM_OBS_CLI_COD_CLI=1;

    ArrayList arlRegSelUpdAccEmpCli, arlDatSelUpdAccEmpCli;
    final int INT_ARL_TBM_ACC_EMP_CLI_COD_EMP=0;
    final int INT_ARL_TBM_ACC_EMP_CLI_COD_CLI=1;
    
    ArrayList arlRegSelUpdSolCre, arlDatSelUpdSolCre;
    final int INT_ARL_TBM_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBM_COL_CRE_COD_CLI=1;
    
    ArrayList arlRegSelUpdRefComSolCre, arlDatSelUpdRefComSolCre;
    final int INT_ARL_TBM_REF_COM_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBM_REF_COM_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBM_REF_COM_SOL_CRE_EST_REG=2;
    
    ArrayList arlRegSelUpdRefBanSolCre, arlDatSelUpdRefBanSolCre;
    final int INT_ARL_TBM_REF_BAN_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBM_REF_BAN_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBM_REF_BAN_SOL_CRE_EST_REG=2;
    
    ArrayList arlRegSelUpdDocDigSolCre, arlDatSelUpdDocDigSolCre;
    final int INT_ARL_TBM_DOC_DIG_SOL_CRE_COD_EMP=0;
    final int INT_ARL_TBM_DOC_DIG_SOL_CRE_COD_SOL=1;
    final int INT_ARL_TBM_DOC_DIG_SOL_CRE_EST_REG=2;
    
    ArrayList arlRegSelUpdCli, arlDatSelUpdCli;
    final int INT_ARL_TBM_CLI_COD_EMP=0;
    final int INT_ARL_TBM_CLI_COD_CLI=1;
    
    ArrayList arlRegSelUpdCliLoc, arlDatSelUpdCliLoc;
    final int INT_ARL_TBR_CLI_LOC_COD_EMP=0;
    final int INT_ARL_TBR_CLI_LOC_COD_CLI=1;
    
    private int intVarEjePrcRem;
    

    
    ArrayList arlRegSelUpdDetRecDoc, arlDatSelUpdDetRecDoc;
    final int INT_ARL_TBM_DET_REC_DOC_COD_EMP=0;
    final int INT_ARL_TBM_DET_REC_DOC_COD_LOC=1;
    final int INT_ARL_TBM_DET_REC_DOC_COD_TIPDOC=2;
    final int INT_ARL_TBM_DET_REC_DOC_COD_DOC=3;
    final int INT_ARL_TBM_DET_REC_DOC_COD_CLI=4;

    private ZafJCEAlgAES objJCEAlgAES;
    
    
    public ZafCxC12(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            
            this.objParSis=obj;
            objParSis=(ZafParSis)obj.clone();
            
            
//            strDrv=objParSis.getDriverConexionCentral();
//            strConIni=objParSis.getStringConexionCentral();
//            strUsrIni=objParSis.getUsuarioConexionCentral();
//            strPswIni=objParSis.getClaveConexionCentral();
            
            arlDatTbhCli=new ArrayList();
            arlDatTbmUniCli=new ArrayList();
            arlDatTbhBenChq=new ArrayList();
            arlDatTbhDirCli=new ArrayList();
            arlDatTbhConCli=new ArrayList();
            arlDatTbhForPagCli=new ArrayList();
            arlDatTbhObsCli=new ArrayList();
            arlDatTbhAccEmpCli=new ArrayList();
            arlDatTbhSolCre=new ArrayList();
            arlDatTbhCabCotVen=new ArrayList();
            arlDatTbhDetCotVen=new ArrayList();
            arlDatTbhPagCotVen=new ArrayList();
            arlDatTbhCabCotCom=new ArrayList();
            arlDatTbhDetCotCom=new ArrayList();
            arlDatTbhPagCotCom=new ArrayList();
            arlDatTbhCabMovInv=new ArrayList();
            arlDatTbhDetMovInv=new ArrayList();
            arlDatTbhPagMovInv=new ArrayList();
            arlDatTbhCabPag=new ArrayList();
            arlDatTbhDetPag=new ArrayList();
            arlDatTbhRefComSolCre=new ArrayList();
            arlDatTbhRefBanSolCre=new ArrayList();
            arlDatTbhDocDigSolCre=new ArrayList();
            arlDatSelUpdCabCotVen=new ArrayList();
            arlDatSelUpdDetCotVen=new ArrayList();
            arlDatSelUpdCabCotCom=new ArrayList();
            arlDatSelUpdCabMovInv=new ArrayList();
            arlDatSelUpdCabGuiRem=new ArrayList();






            arlDatSelUpdBenChq=new ArrayList();
            arlDatSelUpdCabPag=new ArrayList();
            arlDatSelUpdDirCli=new ArrayList();
            arlDatSelUpdConCli=new ArrayList();
            arlDatSelUpdForPagCli=new ArrayList();
            arlDatSelUpdObsCli=new ArrayList();
            arlDatSelUpdAccEmpCli=new ArrayList();
            arlDatSelUpdSolCre=new ArrayList();
            arlDatSelUpdRefComSolCre=new ArrayList();
            arlDatSelUpdRefBanSolCre=new ArrayList();
            arlDatSelUpdDocDigSolCre=new ArrayList();
            arlDatSelUpdCli=new ArrayList();
            arlDatSelUpdCliLoc=new ArrayList();
            arlDatSelUpdDetRecDoc=new ArrayList();



        configurarFrm();
            
            
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
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGrl = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        panTodReg = new javax.swing.JPanel();
        optTodCli = new javax.swing.JRadioButton();
        panFilCli = new javax.swing.JPanel();
        panFilSolCliSel = new javax.swing.JPanel();
        optSolCliSel = new javax.swing.JRadioButton();
        panSelCli = new javax.swing.JPanel();
        panSelCliBut = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        txtIdeCli = new javax.swing.JTextField();
        panCntSelCliDesHas = new javax.swing.JPanel();
        panEspSelCliDesHas = new javax.swing.JPanel();
        panSelCliDesHas = new javax.swing.JPanel();
        lblCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        panCliChk = new javax.swing.JPanel();
        chkMosRegRep = new javax.swing.JCheckBox();
        chkMosRegMov = new javax.swing.JCheckBox();
        panTbl = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panTblUno = new javax.swing.JPanel();
        spnDat1 = new javax.swing.JScrollPane();
        tblDat1 = new javax.swing.JTable();
        panTblBut1 = new javax.swing.JPanel();
        panConBut1 = new javax.swing.JPanel();
        butCon1 = new javax.swing.JButton();
        lblMsgSisUno = new javax.swing.JLabel();
        panButCen = new javax.swing.JPanel();
        butEnvDat = new javax.swing.JButton();
        butEnvDat1 = new javax.swing.JButton();
        panTblDos = new javax.swing.JPanel();
        spnDat2 = new javax.swing.JScrollPane();
        tblDat2 = new javax.swing.JTable();
        panBut2 = new javax.swing.JPanel();
        panButCon2 = new javax.swing.JPanel();
        butCon2 = new javax.swing.JButton();
        lblMsgSisDos = new javax.swing.JLabel();
        panSur = new javax.swing.JPanel();
        panButPro = new javax.swing.JPanel();
        butPrcRem = new javax.swing.JButton();
        butProce = new javax.swing.JButton();
        butCerrar = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        lblTit = new javax.swing.JLabel();

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
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        panGrl.setLayout(new java.awt.BorderLayout());

        panFil.setLayout(new java.awt.BorderLayout());

        panFil.setPreferredSize(new java.awt.Dimension(0, 130));
        panTodReg.setLayout(new java.awt.BorderLayout());

        panTodReg.setPreferredSize(new java.awt.Dimension(0, 20));
        optTodCli.setSelected(true);
        optTodCli.setText("Todos los clientes");
        optTodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodCliActionPerformed(evt);
            }
        });

        panTodReg.add(optTodCli, java.awt.BorderLayout.CENTER);

        panFil.add(panTodReg, java.awt.BorderLayout.NORTH);

        panFilCli.setLayout(new java.awt.BorderLayout());

        panFilCli.setPreferredSize(new java.awt.Dimension(10, 100));
        panFilSolCliSel.setLayout(new java.awt.BorderLayout());

        panFilSolCliSel.setPreferredSize(new java.awt.Dimension(10, 20));
        optSolCliSel.setText("S\u00f3lo los clientes que cumplan el criterio seleccionado");
        optSolCliSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optSolCliSelActionPerformed(evt);
            }
        });

        panFilSolCliSel.add(optSolCliSel, java.awt.BorderLayout.CENTER);

        panFilCli.add(panFilSolCliSel, java.awt.BorderLayout.NORTH);

        panSelCli.setLayout(new java.awt.BorderLayout());

        panSelCli.setPreferredSize(new java.awt.Dimension(10, 60));
        panSelCliBut.setLayout(null);

        panSelCliBut.setPreferredSize(new java.awt.Dimension(10, 20));
        lblPrv.setText("Cliente:");
        lblPrv.setToolTipText("Proveedor");
        panSelCliBut.add(lblPrv);
        lblPrv.setBounds(21, 0, 70, 20);

        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });

        panSelCliBut.add(txtCodPrv);
        txtCodPrv.setBounds(100, 0, 56, 20);

        txtDesLarPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarPrvActionPerformed(evt);
            }
        });
        txtDesLarPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusLost(evt);
            }
        });

        panSelCliBut.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(156, 0, 264, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });

        panSelCliBut.add(butPrv);
        butPrv.setBounds(420, 0, 20, 20);

        panSelCliBut.add(txtIdeCli);
        txtIdeCli.setBounds(480, 0, 160, 20);

        panSelCli.add(panSelCliBut, java.awt.BorderLayout.NORTH);

        panCntSelCliDesHas.setLayout(new java.awt.BorderLayout());

        panEspSelCliDesHas.setPreferredSize(new java.awt.Dimension(18, 10));
        panCntSelCliDesHas.add(panEspSelCliDesHas, java.awt.BorderLayout.WEST);

        panSelCliDesHas.setLayout(null);

        panSelCliDesHas.setBorder(new javax.swing.border.TitledBorder("Nombre del Cliente"));
        lblCliDes.setText("Desde:");
        panSelCliDesHas.add(lblCliDes);
        lblCliDes.setBounds(40, 16, 50, 15);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });

        panSelCliDesHas.add(txtNomCliDes);
        txtNomCliDes.setBounds(86, 14, 200, 20);

        lblCliHas.setText("Hasta:");
        panSelCliDesHas.add(lblCliHas);
        lblCliHas.setBounds(334, 16, 50, 15);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
        });

        panSelCliDesHas.add(txtNomCliHas);
        txtNomCliHas.setBounds(386, 14, 200, 20);

        panCntSelCliDesHas.add(panSelCliDesHas, java.awt.BorderLayout.CENTER);

        panSelCli.add(panCntSelCliDesHas, java.awt.BorderLayout.CENTER);

        panFilCli.add(panSelCli, java.awt.BorderLayout.CENTER);

        panCliChk.setLayout(null);

        panCliChk.setPreferredSize(new java.awt.Dimension(10, 32));
        chkMosRegRep.setSelected(true);
        chkMosRegRep.setText("Mostrar s\u00f3lo los clientes que est\u00e1n repetidos");
        chkMosRegRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosRegRepActionPerformed(evt);
            }
        });

        panCliChk.add(chkMosRegRep);
        chkMosRegRep.setBounds(20, 0, 380, 16);

        chkMosRegMov.setText("Mostrar s\u00f3lo los clientes que no han tenido movimientos");
        chkMosRegMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosRegMovActionPerformed(evt);
            }
        });

        panCliChk.add(chkMosRegMov);
        chkMosRegMov.setBounds(20, 15, 380, 16);

        panFilCli.add(panCliChk, java.awt.BorderLayout.SOUTH);

        panFil.add(panFilCli, java.awt.BorderLayout.CENTER);

        panGrl.add(panFil, java.awt.BorderLayout.NORTH);

        panTbl.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setResizeWeight(0.6);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(471, 200));
        panTblUno.setLayout(new java.awt.BorderLayout());

        tblDat1.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat1.setViewportView(tblDat1);

        panTblUno.add(spnDat1, java.awt.BorderLayout.CENTER);

        panTblBut1.setLayout(new java.awt.BorderLayout());

        panTblBut1.setPreferredSize(new java.awt.Dimension(91, 35));
        panConBut1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 38, 5));

        butCon1.setMnemonic('C');
        butCon1.setText("Consultar");
        butCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon1ActionPerformed(evt);
            }
        });

        panConBut1.add(butCon1);

        panTblBut1.add(panConBut1, java.awt.BorderLayout.EAST);

        lblMsgSisUno.setText("jLabel1");
        panTblBut1.add(lblMsgSisUno, java.awt.BorderLayout.CENTER);

        panTblUno.add(panTblBut1, java.awt.BorderLayout.SOUTH);

        panButCen.setLayout(null);

        panButCen.setPreferredSize(new java.awt.Dimension(40, 0));
        butEnvDat.setText("<<");
        butEnvDat.setBorder(new javax.swing.border.EtchedBorder());
        butEnvDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEnvDatActionPerformed(evt);
            }
        });

        panButCen.add(butEnvDat);
        butEnvDat.setBounds(4, 4, 32, 20);

        butEnvDat1.setText(">>");
        butEnvDat1.setBorder(new javax.swing.border.EtchedBorder());
        butEnvDat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEnvDat1ActionPerformed(evt);
            }
        });

        panButCen.add(butEnvDat1);
        butEnvDat1.setBounds(4, 50, 32, 20);

        panTblUno.add(panButCen, java.awt.BorderLayout.EAST);

        jSplitPane1.setLeftComponent(panTblUno);

        panTblDos.setLayout(new java.awt.BorderLayout());

        panTblDos.setPreferredSize(new java.awt.Dimension(454, 200));
        tblDat2.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat2.setViewportView(tblDat2);

        panTblDos.add(spnDat2, java.awt.BorderLayout.CENTER);

        panBut2.setLayout(new java.awt.BorderLayout());

        butCon2.setMnemonic('n');
        butCon2.setText("Consultar");
        butCon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon2ActionPerformed(evt);
            }
        });

        panButCon2.add(butCon2);

        panBut2.add(panButCon2, java.awt.BorderLayout.EAST);

        lblMsgSisDos.setText("jLabel1");
        panBut2.add(lblMsgSisDos, java.awt.BorderLayout.CENTER);

        panTblDos.add(panBut2, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(panTblDos);

        panTbl.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        panGrl.add(panTbl, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        butPrcRem.setText("Proceso Remoto");
        butPrcRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrcRemActionPerformed(evt);
            }
        });

        panButPro.add(butPrcRem);

        butProce.setMnemonic('P');
        butProce.setText("Procesar");
        butProce.setToolTipText("Procesar la Unificaci\u00f3n de Item");
        butProce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProceActionPerformed(evt);
            }
        });

        panButPro.add(butProce);

        butCerrar.setMnemonic('C');
        butCerrar.setText("Cerrar");
        butCerrar.setToolTipText("Cerrar ventana");
        butCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrarActionPerformed(evt);
            }
        });

        panButPro.add(butCerrar);

        panSur.add(panButPro, java.awt.BorderLayout.EAST);

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        panPrgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panGrl.add(panSur, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGrl);

        jPanel1.add(tabFrm, java.awt.BorderLayout.CENTER);

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        lblTit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblTit, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butPrcRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrcRemActionPerformed

        strDrv=objParSis.getDriverConexionCentral();
        strConIni=objParSis.getStringConexionCentral();
        strUsrIni=objParSis.getUsuarioConexionCentral();
        strPswIni=objParSis.getClaveConexionCentral();
        System.out.println("strConIni: " + strConIni);
        System.out.println("strUsrIni: " + strUsrIni);
        System.out.println("strPswIni: " + strPswIni);
        
        
        intVarEjePrcRem=1;
        
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>�Est� seguro que desea realizar esta operaci�n?<BR>";
        strMsg+="Una vez realizado el proceso de unificaci�n no ser� posible dar marcha atr�s.<BR>";
        strMsg+="Este proceso pasar� todos los movimientos del cliente a unificar<BR>";
        strMsg+="(cotizaciones, facturas, pagos, etc) al cliente con el cual se va a unificar<BR>";
        strMsg+="antes de elimiarlo completamente del sistema</HTML>";
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        if (butProce.getText().equals("Procesar")){
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                blnCon=true;
                lblMsgSis.setText("Realizando proceso de Unificaci�n de Clientes...");
                if (objThrGUIPro==null){
                    objThrGUIPro=new ZafThreadGUIPro();
                    objThrGUIPro.start();
                }
            }   
        }
        else{
            blnCon=false;
//            System.out.println("EN EL BOTON POR ELSE: ");
        }
    }//GEN-LAST:event_butPrcRemActionPerformed

    private void optTodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodCliActionPerformed
        // TODO add your handling code here:
        if(optSolCliSel.isSelected())
            optSolCliSel.setSelected(false);
        
        txtCodPrv.setText("");
        txtDesLarPrv.setText("");
        txtIdeCli.setText("");
        txtNomCliDes.setText("");
        txtNomCliHas.setText("");
        
    }//GEN-LAST:event_optTodCliActionPerformed

    private void optSolCliSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optSolCliSelActionPerformed
        // TODO add your handling code here:
        if(optTodCli.isSelected())
            optTodCli.setSelected(false);
    }//GEN-LAST:event_optSolCliSelActionPerformed

    private void butProceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProceActionPerformed

        strDrv=objParSis.getDriverConexionCentral();
        strConIni=objParSis.getStringConexionCentral();
        strUsrIni=objParSis.getUsuarioConexionCentral();
        strPswIni=objParSis.getClaveConexionCentral();
        
        
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>�Est� seguro que desea realizar esta operaci�n?<BR>";
        strMsg+="Una vez realizado el proceso de unificaci�n no ser� posible dar marcha atr�s.<BR>";
        strMsg+="Este proceso pasar� todos los movimientos del cliente a unificar<BR>";
        strMsg+="(cotizaciones, facturas, pagos, etc) al cliente con el cual se va a unificar<BR>";
        strMsg+="antes de elimiarlo completamente del sistema</HTML>";
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        if (butProce.getText().equals("Procesar")){
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                blnCon=true;
                lblMsgSis.setText("Realizando proceso de Unificaci�n de Clientes...");
                if (objThrGUIUniTmp==null){
                    objThrGUIUniTmp=new ZafThreadGUIUniTmp();
                    objThrGUIUniTmp.start();
                }
            }   
        }
        else{
            blnCon=false;
//            System.out.println("EN EL BOTON POR ELSE: ");
        }
    }//GEN-LAST:event_butProceActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        // TODO add your handling code here:
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="�Est� seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrarActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerrarActionPerformed

    
    /** Cerrar la aplicaci�n. */
    private void exitForm(){
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="�Est� seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    }
    
    
    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        // TODO add your handling code here:
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        // TODO add your handling code here:
        if(txtNomCliDes.getText().length()>0){
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
            txtIdeCli.setText("");
            txtNomCliHas.setText(txtNomCliDes.getText());
            optSolCliSel.setSelected(true);
            optTodCli.setSelected(false);
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void chkMosRegMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosRegMovActionPerformed
        // TODO add your handling code here:
        if(chkMosRegMov.isSelected())
            chkMosRegRep.setSelected(false);
    }//GEN-LAST:event_chkMosRegMovActionPerformed

    private void chkMosRegRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosRegRepActionPerformed
        // TODO add your handling code here:
        if(chkMosRegRep.isSelected())
            chkMosRegMov.setSelected(false);
    }//GEN-LAST:event_chkMosRegRepActionPerformed

    private void butEnvDat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEnvDat1ActionPerformed
        // TODO add your handling code here:
        int filaSelTablaUno=tblDat1.getSelectedRow();
        int filaSelTablaDos=tblDat2.getSelectedRow()==-1?0:tblDat2.getSelectedRow();
        
        if( !  (  filaSelTablaUno==-1 || filaSelTablaDos==-1  )  ){
            llevaInformacTblUnoTblDos(filaSelTablaUno, filaSelTablaDos);
        }
    }//GEN-LAST:event_butEnvDat1ActionPerformed

    private void butEnvDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEnvDatActionPerformed
        // TODO add your handling code here:
        int filaSelTablaUno=tblDat1.getSelectedRow();
        int filaSelTablaDos=tblDat2.getSelectedRow();
        
        if( !  (  filaSelTablaUno==-1 || filaSelTablaDos==-1  )  ){
            llevaInformacTblDosTblUno(filaSelTablaUno, filaSelTablaDos);        
        }
    }//GEN-LAST:event_butEnvDatActionPerformed

    private void butCon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon2ActionPerformed
        // TODO add your handling code here:
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        if (butCon2.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUIDos==null){
                objThrGUIDos=new ZafThreadGUIDos();
                objThrGUIDos.start();
            }            
        }
        else{
            blnCon=false;
        }
    }//GEN-LAST:event_butCon2ActionPerformed

    private void butCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon1ActionPerformed
        // TODO add your handling code here:
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        if (butCon1.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUIUno==null){
                objThrGUIUno=new ZafThreadGUIUno();
                objThrGUIUno.start();
            }            
        }
        else{
            blnCon=false;
        }
    }//GEN-LAST:event_butCon1ActionPerformed

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        // TODO add your handling code here:
        strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)){
            if (txtDesLarPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                txtIdeCli.setText("");
                objTblModUno.removeAllRows();
                objTblModDos.removeAllRows();
            }
            else{
                mostrarVenConPrv(2);
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
        
        if(txtDesLarPrv.getText().length()>0){
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
            optSolCliSel.setSelected(true);
            optTodCli.setSelected(false);
        }
    }//GEN-LAST:event_txtDesLarPrvFocusLost

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        // TODO add your handling code here:
        strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
    }//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        // TODO add your handling code here:
        txtDesLarPrv.transferFocus();
    }//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)){
            if (txtCodPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                txtIdeCli.setText("");
                objTblModUno.removeAllRows();
                objTblModDos.removeAllRows();
            }
            else{
                mostrarVenConPrv(1);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
        
        if(txtCodPrv.getText().length()>0){
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
            optSolCliSel.setSelected(true);
            optTodCli.setSelected(false);
        }
        
    }//GEN-LAST:event_txtCodPrvFocusLost

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        // TODO add your handling code here:
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
    }//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        // TODO add your handling code here:
        txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formInternalFrameOpened
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerrar;
    private javax.swing.JButton butCon1;
    private javax.swing.JButton butCon2;
    private javax.swing.JButton butEnvDat;
    private javax.swing.JButton butEnvDat1;
    private javax.swing.JButton butPrcRem;
    private javax.swing.JButton butProce;
    private javax.swing.JButton butPrv;
    private javax.swing.JCheckBox chkMosRegMov;
    private javax.swing.JCheckBox chkMosRegRep;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCliDes;
    private javax.swing.JLabel lblCliHas;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblMsgSisDos;
    private javax.swing.JLabel lblMsgSisUno;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optSolCliSel;
    private javax.swing.JRadioButton optTodCli;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut2;
    private javax.swing.JPanel panButCen;
    private javax.swing.JPanel panButCon2;
    private javax.swing.JPanel panButPro;
    private javax.swing.JPanel panCliChk;
    private javax.swing.JPanel panCntSelCliDesHas;
    private javax.swing.JPanel panConBut1;
    private javax.swing.JPanel panEspSelCliDesHas;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilCli;
    private javax.swing.JPanel panFilSolCliSel;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSelCli;
    private javax.swing.JPanel panSelCliBut;
    private javax.swing.JPanel panSelCliDesHas;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTblBut1;
    private javax.swing.JPanel panTblDos;
    private javax.swing.JPanel panTblUno;
    private javax.swing.JPanel panTodReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnDat2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat1;
    private javax.swing.JTable tblDat2;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    // End of variables declaration//GEN-END:variables
    
    
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            objUti=new ZafUtil();
            objJCEAlgAES=new ZafJCEAlgAES();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3.1");
            lblTit.setText(strAux);
            
            
            arlDatBenChq=new ArrayList();
            arlDatCfgRegCnxRem=new ArrayList();
            arlDatTmpTbl=new ArrayList();
            
            configurarTblUno();
            configurarTblDos();
            
            optTodCli.setSelected(true);
            optSolCliSel.setSelected(false);
            if(objParSis.getCodigoMenu()==462){
                lblTit.setText(strAux);
                optTodCli.setText("Todos los clientes");
                optSolCliSel.setText("Sólo los clientes que cumplan el criterio seleccionado");
                panSelCliDesHas.setBorder(new javax.swing.border.TitledBorder("Nombre del Cliente"));
                chkMosRegRep.setText("Mostrar sólo los clientes que est�n repetidos");
                chkMosRegMov.setText("Mostrar sólo los clientes que no han tenido movimientos");
            }
            else{
                lblTit.setText(strAux);
                optTodCli.setText("Todos los proveedores");
                optSolCliSel.setText("Sólo los proveedores que cumplan el criterio seleccionado");
                panSelCliDesHas.setBorder(new javax.swing.border.TitledBorder("Nombre del Proveedor"));
                chkMosRegRep.setText("Mostrar sólo los proveedores que están repetidos");
                chkMosRegMov.setText("Mostrar sólo los proveedores que no han tenido movimientos");
            }
            
            
            
//            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        
            configurarVenConPrv();
            txtIdeCli.setEditable(false);
            txtIdeCli.setVisible(false);
            
            lblMsgSis.setText("Listo");
            lblMsgSisUno.setText("Listo");
            lblMsgSisDos.setText("Listo");
            butProce.setEnabled(false);
            
            
            if(objParSis.getCodigoUsuario()!=1){
                butPrcRem.setVisible(false);
                butPrcRem.setEnabled(false);
            }
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarTblUno(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatUno=new Vector();    //Almacena los datos
            vecCabUno=new Vector(15);  //Almacena las cabeceras
            vecCabUno.clear();
            vecCabUno.add(INT_TBL_DAT_UNO_LIN,"");
            vecCabUno.add(INT_TBL_DAT_UNO_COD,"COD.CLI.");
            vecCabUno.add(INT_TBL_DAT_UNO_IDE,"IDENTIFICACION");
            vecCabUno.add(INT_TBL_DAT_UNO_NOM,"NOMBRE");
            
            vecCabUno.add(INT_TBL_DAT_UNO_DIR,"DIRECCION");
            vecCabUno.add(INT_TBL_DAT_UNO_TLF,"TELEFONO");
            vecCabUno.add(INT_TBL_DAT_UNO_CIU,"CIUDAD");
            
            vecCabUno.add(INT_TBL_DAT_UNO_BUT_ANE_UNO,"...");
            vecCabUno.add(INT_TBL_DAT_UNO_BUT_ANE_DOS,"...");
            vecCabUno.add(INT_TBL_DAT_UNO_COD_UNI,"COD.CLI.");
            vecCabUno.add(INT_TBL_DAT_UNO_IDE_UNI,"IDENTIFICACION");
            vecCabUno.add(INT_TBL_DAT_UNO_NOM_UNI,"NOMBRE");
            
            vecCabUno.add(INT_TBL_DAT_UNO_DIR_UNI,"DIRECCION");
            vecCabUno.add(INT_TBL_DAT_UNO_TLF_UNI,"TEL�FONO");
            vecCabUno.add(INT_TBL_DAT_UNO_CIU_UNI,"CIUDAD");
            
            objTblModUno=new ZafTblMod();
            objTblModUno.setHeader(vecCabUno);
            tblDat1.setModel(objTblModUno);
            
            
            //Configurar JTable: Establecer tipo de selecci�n.
            objColNumUno=new ZafColNumerada(tblDat1,INT_TBL_DAT_UNO_LIN);
            tblDat1.setRowSelectionAllowed(true);
            tblDat1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnuUno=new ZafTblPopMnu(tblDat1);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat1.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_UNO_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_UNO_COD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_UNO_IDE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_UNO_NOM).setPreferredWidth(120);
            
//            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_UNO_BUT_ANE_UNO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_UNO_BUT_ANE_DOS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_UNO_COD_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_UNO_IDE_UNI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_UNO_NOM_UNI).setPreferredWidth(120);
            
//            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR_UNI).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF_UNI).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU_UNI).setPreferredWidth(20);
            
            //Para ocultar las columnas en el JTable
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR_UNI).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR_UNI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR_UNI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR_UNI).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_DIR_UNI).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF_UNI).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF_UNI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF_UNI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF_UNI).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_TLF_UNI).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU_UNI).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU_UNI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU_UNI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU_UNI).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_UNO_CIU_UNI).setResizable(false);
            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat1.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaUno=new ZafCxC12.ZafMouMotAdaUno();
            tblDat1.getTableHeader().addMouseMotionListener(objMouMotAdaUno);
            //Configurar JTable: Editor de b�squeda.
            objTblBusUno=new ZafTblBus(tblDat1);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdUno=new ZafTblOrd(tblDat1);
            
            
            objTblModUno.setModoOperacion(objTblModUno.INT_TBL_EDI);
            
            //Configurar JTable: Establecer columnas editables.
            vecAuxUno=new Vector();
            vecAuxUno.add("" + INT_TBL_DAT_UNO_BUT_ANE_UNO);
            vecAuxUno.add("" + INT_TBL_DAT_UNO_BUT_ANE_DOS);
//            vecAuxUno.add("" + INT_TBL_DAT_UNO_COD_UNI);
//            vecAuxUno.add("" + INT_TBL_DAT_UNO_IDE_UNI);
//            vecAuxUno.add("" + INT_TBL_DAT_UNO_NOM_UNI);
            objTblModUno.setColumnasEditables(vecAuxUno);
            vecAuxUno=null;
            
            
            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE MAESTRO DE CLIENTES
            objTblCelRenButUnoAnxUno=new ZafTblCelRenBut();
            tblDat1.getColumnModel().getColumn(INT_TBL_DAT_UNO_BUT_ANE_UNO).setCellRenderer(objTblCelRenButUnoAnxUno);
            objTblCelEdiButGenUnoAnxUno=new ZafTblCelEdiButGen();
            tblDat1.getColumnModel().getColumn(INT_TBL_DAT_UNO_BUT_ANE_UNO).setCellEditor(objTblCelEdiButGenUnoAnxUno);
            objTblCelEdiButGenUnoAnxUno.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat1.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    callMaeClientesTblUno(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //PARA EL BOTON DE ANEXO DOS, QUE LLAMA A UNA VENTANA DE ESTAD�STICA DE CLIENTES
            objTblCelRenButUnoAnxDos=new ZafTblCelRenBut();
            tblDat1.getColumnModel().getColumn(INT_TBL_DAT_UNO_BUT_ANE_DOS).setCellRenderer(objTblCelRenButUnoAnxDos);
            objTblCelEdiButGenUnoAnxDos=new ZafTblCelEdiButGen();
            tblDat1.getColumnModel().getColumn(INT_TBL_DAT_UNO_BUT_ANE_DOS).setCellEditor(objTblCelEdiButGenUnoAnxDos);
            objTblCelEdiButGenUnoAnxDos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat1.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    callEstadisticasClientesTblUno(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean configurarTblDos(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatDos=new Vector();    //Almacena los datos
            vecCabDos=new Vector(6);  //Almacena las cabeceras
            vecCabDos.clear();
            vecCabDos.add(INT_TBL_DAT_DOS_LIN,"");
            vecCabDos.add(INT_TBL_DAT_DOS_COD,"COD.CLI.");
            vecCabDos.add(INT_TBL_DAT_DOS_IDE,"IDENTIFICACI�N");
            vecCabDos.add(INT_TBL_DAT_DOS_NOM,"NOMBRE");
            vecCabDos.add(INT_TBL_DAT_DOS_DIR,"DIRECCI�N");
            vecCabDos.add(INT_TBL_DAT_DOS_TLF,"TEL�FONO");
            vecCabDos.add(INT_TBL_DAT_DOS_CIU,"CIUDAD");            
            vecCabDos.add(INT_TBL_DAT_DOS_BUT_ANE_UNO,"...");
            vecCabDos.add(INT_TBL_DAT_DOS_BUT_ANE_DOS,"...");
            
            objTblModDos=new ZafTblMod();
            objTblModDos.setHeader(vecCabDos);
            tblDat2.setModel(objTblModDos);
            //Configurar JTable: Establecer tipo de selecci�n.
            objColNumDos=new ZafColNumerada(tblDat2,INT_TBL_DAT_DOS_LIN);
            tblDat2.setRowSelectionAllowed(true);
            tblDat2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnuDos=new ZafTblPopMnu(tblDat2);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat2.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_DOS_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DOS_COD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DOS_IDE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DOS_NOM).setPreferredWidth(120);
            
//            tcmAux.getColumn(INT_TBL_DAT_DOS_DIR).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_DOS_TLF).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_DOS_CIU).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_DOS_BUT_ANE_UNO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DOS_BUT_ANE_DOS).setPreferredWidth(30);
            
            //Para ocultar las columnas en el JTable
            tcmAux.getColumn(INT_TBL_DAT_DOS_DIR).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_DIR).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_DIR).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_DIR).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_DIR).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_DOS_TLF).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_TLF).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_TLF).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_TLF).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_TLF).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_DOS_CIU).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_CIU).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_CIU).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_CIU).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DOS_CIU).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat2.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDos=new ZafCxC12.ZafMouMotAdaDos();
            tblDat2.getTableHeader().addMouseMotionListener(objMouMotAdaDos);
            //Configurar JTable: Editor de b�squeda.
            objTblBusDos=new ZafTblBus(tblDat2);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdDos=new ZafTblOrd(tblDat2);
            
            objTblModDos.setModoOperacion(objTblModDos.INT_TBL_EDI);
            
            
            //Configurar JTable: Establecer columnas editables.
            vecAuxDos=new Vector();
            vecAuxDos.add("" + INT_TBL_DAT_DOS_BUT_ANE_UNO);
            vecAuxDos.add("" + INT_TBL_DAT_DOS_BUT_ANE_DOS);
            objTblModDos.setColumnasEditables(vecAuxDos);
            vecAuxDos=null;
    
            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE MAESTRO DE CLIENTES
            objTblCelRenButDosAnxUno=new ZafTblCelRenBut();
            tblDat2.getColumnModel().getColumn(INT_TBL_DAT_DOS_BUT_ANE_UNO).setCellRenderer(objTblCelRenButDosAnxUno);
            
            objTblCelEdiButGenDosAnxUno=new ZafTblCelEdiButGen();
            tblDat2.getColumnModel().getColumn(INT_TBL_DAT_DOS_BUT_ANE_UNO).setCellEditor(objTblCelEdiButGenDosAnxUno);
            objTblCelEdiButGenDosAnxUno.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat2.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    callMaeClientesTblDos(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //PARA EL BOTON DE ANEXO DOS, QUE LLAMA A UNA VENTANA DE ESTAD�STICA DE CLIENTES
            objTblCelRenButDosAnxDos=new ZafTblCelRenBut();
            tblDat2.getColumnModel().getColumn(INT_TBL_DAT_DOS_BUT_ANE_DOS).setCellRenderer(objTblCelRenButDosAnxDos);
            
            objTblCelEdiButGenDosAnxDos=new ZafTblCelEdiButGen();
            tblDat2.getColumnModel().getColumn(INT_TBL_DAT_DOS_BUT_ANE_DOS).setCellEditor(objTblCelEdiButGenDosAnxDos);
            objTblCelEdiButGenDosAnxDos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat2.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    callEstadisticasClientesTblDos(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
//            objTblCelRenButUnoAnxDos=new ZafTblCelRenBut();
//            tblDat2.getColumnModel().getColumn(INT_TBL_DAT_DOS_BUT_ANE_UNO).setCellRenderer(objTblCelRenButUnoAnxDos);
//            objTblCelEdiButGenUnoAnxDos=new ZafTblCelEdiButGen();
//            tblDat2.getColumnModel().getColumn(INT_TBL_DAT_DOS_BUT_ANE_UNO).setCellEditor(objTblCelEdiButGenUnoAnxDos);
//            objTblCelEdiButGenUnoAnxDos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                int intFilSel;
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
//                    intFilSel=tblDat2.getSelectedRow();
//                }
//                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    callMaeClientesTblDos(intFilSel);
//                }
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                }
//            });
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener{
        public void changedUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaUno extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat1.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_UNO_COD:
                    strMsg="Codigo del Cliente";
                    break;
                case INT_TBL_DAT_UNO_IDE:
                    strMsg="Identificaci�n del Cliente";
                    break;
                case INT_TBL_DAT_UNO_NOM:
                    strMsg="Nombre del Cliente";
                    break;
                    
                    
                case INT_TBL_DAT_UNO_DIR:
                    strMsg="Direcci�n del Cliente";
                    break;
                case INT_TBL_DAT_UNO_TLF:
                    strMsg="Tel�fono del Cliente";
                    break;
                case INT_TBL_DAT_UNO_CIU:
                    strMsg="Ciudad del Cliente";
                    break;
                case INT_TBL_DAT_UNO_BUT_ANE_UNO:
                    strMsg="...";
                    break;
                case INT_TBL_DAT_UNO_BUT_ANE_DOS:
                    strMsg="...";
                    break;
                case INT_TBL_DAT_UNO_COD_UNI:
                    strMsg="C�digo del Cliente Unificar";
                    break;
                case INT_TBL_DAT_UNO_IDE_UNI:
                    strMsg="Identificaci�n del cliente a unificar";
                    break;
                case INT_TBL_DAT_UNO_NOM_UNI:
                    strMsg="Nombre del cliente a unificar";
                    break;
                    
                case INT_TBL_DAT_UNO_DIR_UNI:
                    strMsg="Direcci�n del cliente a unificar";
                    break;
                case INT_TBL_DAT_UNO_TLF_UNI:
                    strMsg="Tel�fono del cliente a unificar";
                    break;
                case INT_TBL_DAT_UNO_CIU_UNI:
                    strMsg="Ciudad del cliente a unificar";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat1.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaDos extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat1.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_DOS_COD:
                    strMsg="Codigo del Cliente";
                    break;
                case INT_TBL_DAT_DOS_IDE:
                    strMsg="Identificaci�n del Cliente";
                    break;
                case INT_TBL_DAT_DOS_NOM:
                    strMsg="Nombre del Cliente";
                    break;
                    
                case INT_TBL_DAT_DOS_DIR:
                    strMsg="Direcci�n del Cliente";
                    break;
                case INT_TBL_DAT_DOS_TLF:
                    strMsg="Tel�fono del Cliente";
                    break;
                case INT_TBL_DAT_DOS_CIU:
                    strMsg="Ciudad del Cliente";
                    break;
                case INT_TBL_DAT_DOS_BUT_ANE_UNO:
                    strMsg="...";
                    break;
                case INT_TBL_DAT_DOS_BUT_ANE_DOS:
                    strMsg="...";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat1.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("C�digo");
            arlAli.add("Identificaci�n");
            arlAli.add("Nombre");
            arlAli.add("Direcci�n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            if(objParSis.getCodigoMenu()==462){
                strSQL+=" AND a1.st_cli='S'";
            }
            else{
                strSQL+=" AND a1.st_prv='S'";
            }
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE){
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtIdeCli.setText(strIdePrv);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText())){
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtIdeCli.setText(strIdePrv);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else{
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE){
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtIdeCli.setText(strIdePrv);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else{
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText())){
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtIdeCli.setText(strIdePrv);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else{
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE){
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtIdeCli.setText(strIdePrv);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else{
                            txtDesLarPrv.setText(strDesLarPrv);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private class ZafThreadGUIUno extends Thread{
        public void run(){
            //Limpiar objetos.
            objTblModUno.removeAllRows();
            objTblModDos.removeAllRows();
            if (!cargarDetRegUno()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon1.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat1.getRowCount()>0){
                tblDat1.setRowSelectionInterval(0, 0);
                tblDat1.requestFocus();
            }
            objThrGUIUno=null;
        }
    }

    
    private boolean cargarDetRegUno(){
        int intCodEmp, intCodLoc, intNumTotReg=-1, i;
        double dblSub, dblIva;
        boolean blnRes=true;
        String strFecDes="", strFecHas="";
        strAux="";
        String strAuxLoc="";
        try{
            butCon1.setText("Detener");
            lblMsgSisUno.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Obtener la condici�n.
                if (txtCodPrv.getText().length()>0)
                    strAux+=" AND tx_ide='" + txtIdeCli.getText() + "'";

                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0){
                    strAux+=" AND ((LOWER(tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                    strAuxLoc=" WHERE ((LOWER(tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                
                if(strAuxLoc.equals(""))
                    strAuxLoc+=" WHERE a1.st_reg NOT IN ('I','E')";
                else
                    strAuxLoc+=" AND a1.st_reg NOT IN ('I','E')";
                
                
                
                if(objParSis.getCodigoMenu()==462){
                    strAuxLoc+=" AND a1.st_cli='S'";
                }
                else{
                    strAuxLoc+=" AND a1.st_prv='S'";
                }
                
                
                
                if(chkMosRegRep.isSelected()){
                    strSQL="";
                    strSQL+="SELECT COUNT(*) FROM(";
                    strSQL+=" 	SELECT x.*FROM(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" 	FROM tbm_cli AS a1" + strAuxLoc + ") AS x";
                    strSQL+=" 	 INNER JOIN(";
                    strSQL+=" 		SELECT co_emp, tx_ide";
                    strSQL+=" 		FROM tbm_cli";
                    strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                    strSQL+=strAux;
//                    strSQL+=" 		GROUP BY co_emp, tx_ide";
//                    strSQL+=" 		HAVING COUNT(tx_ide)>1";
                    strSQL+=" 		ORDER BY tx_ide";
                    strSQL+=" 	) AS y";
                    strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                    strSQL+=" WHERE co_cli NOT IN(";
                    strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    
                    strSQL+=" AND co_cli NOT IN(";
                    strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    strSQL+=" 	ORDER BY x.tx_ide";
                    strSQL+=" ) AS z";
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;

                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" 	SELECT x.*FROM(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                    strSQL+=" 	FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                    strSQL+=" 	 INNER JOIN(";
                    strSQL+=" 		SELECT co_emp, tx_ide";
                    strSQL+=" 		FROM tbm_cli";
                    strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                    strSQL+=strAux;
//                    strSQL+=" 		GROUP BY co_emp, tx_ide";
//                    strSQL+=" 		HAVING COUNT(tx_ide)>1";
                    strSQL+=" 		ORDER BY tx_ide";
                    strSQL+=" 	) AS y";
                    strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                                        
                    strSQL+=" WHERE co_cli NOT IN(";
                    strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    strSQL+=" AND co_cli NOT IN(";
                    strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + "AND st_reg IN ('A','P'))";
                    
                    strSQL+=" 	ORDER BY x.tx_ide";
                    
                }
                else
                    if(chkMosRegMov.isSelected()){
                        strSQL="";
                        strSQL+="SELECT COUNT(*) FROM(";
                        strSQL+="SELECT *FROM(";
                        strSQL+=" (";
                        strSQL+=" 	SELECT x.*FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                        strSQL+=" 	FROM tbm_cli AS a1 " + strAuxLoc + ") AS x";
                        strSQL+=" 	 INNER JOIN(";
                        strSQL+=" 		SELECT co_emp, tx_ide";
                        strSQL+=" 		FROM tbm_cli";
                        strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                        strSQL+=strAux;
//                        strSQL+=" 		GROUP BY co_emp, tx_ide";
//                        strSQL+=" 		HAVING COUNT(tx_ide)>1";
                        strSQL+=" 		ORDER BY tx_ide";
                        strSQL+=" 	) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 	ORDER BY x.tx_ide";
                        strSQL+=" )";
                        strSQL+=" EXCEPT";
                        strSQL+=" (";
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabPag) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                        strSQL+=" 			FROM tbm_cli AS a1 " + strAuxLoc + ") AS x";
                        strSQL+=" 			 INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        strSQL+=" 	UNION";
                        //--2-- EN TBM_CABMOVINV  -  PAGOS A REALIZAR
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabMovInv) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                        strSQL+=" 			FROM tbm_cli AS a1" + strAuxLoc + ") AS x";
                        strSQL+=" 			INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        if(objParSis.getCodigoMenu()==462){
                            strSQL+=" 	UNION";
                            //--3-- EN TBM_CABCOTVEN  -  COTIZACIONES DE CLIENTES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_cli";
                            strSQL+=" 		FROM tbm_CabCotVen) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                            strSQL+=" 			FROM tbm_cli AS a1" + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        }
                        else{
                            strSQL+=" 	UNION";
                            //--4-- EN TBM_CABCOTCOM  -  COTIZACIONES DE PROVEEDORES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_prv";
                            strSQL+=" 		FROM tbm_CabCotCom) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                            strSQL+=" 			FROM tbm_cli AS a1" + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        }
                        strSQL+=" )";
                        strSQL+=" ORDER BY tx_ide, co_cli";
                        strSQL+=") AS k WHERE co_cli NOT IN(";
                        strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        strSQL+=" AND co_cli NOT IN(";
                        strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        
                        strSQL+=" ) AS q";

                        intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intNumTotReg==-1)
                            return false;

                        strSQL="";
                        strSQL+="SELECT *FROM(";
                        strSQL+=" (";
                        strSQL+=" 	SELECT x.*FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                        strSQL+=" 	FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                        strSQL+=" 	 INNER JOIN(";
                        strSQL+=" 		SELECT co_emp, tx_ide";
                        strSQL+=" 		FROM tbm_cli";
                        strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                        strSQL+=strAux;
//                        strSQL+=" 		GROUP BY co_emp, tx_ide";
//                        strSQL+=" 		HAVING COUNT(tx_ide)>1";
                        strSQL+=" 		ORDER BY tx_ide";
                        strSQL+=" 	) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 	ORDER BY x.tx_ide";
                        strSQL+=" )";
                        strSQL+=" EXCEPT";
                        strSQL+=" (";
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabPag) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                        strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                        strSQL+=" 			 INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        strSQL+=" 	UNION";
                        //--2-- EN TBM_CABMOVINV  -  PAGOS A REALIZAR
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabMovInv) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                        strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                        strSQL+=" 			INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        if(objParSis.getCodigoMenu()==462){
                            strSQL+=" 	UNION";
                            //--3-- EN TBM_CABCOTVEN  -  COTIZACIONES DE CLIENTES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_cli";
                            strSQL+=" 		FROM tbm_CabCotVen) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                            strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        }
                        else{
                            strSQL+=" 	UNION";
                            //--4-- EN TBM_CABCOTCOM  -  COTIZACIONES DE PROVEEDORES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_prv";
                            strSQL+=" 		FROM tbm_CabCotCom) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                            strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='A'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        }
                        strSQL+=" )";
                        strSQL+=" ORDER BY tx_ide, co_cli";
                        
                        strSQL+=") AS k WHERE co_cli NOT IN(";
                        strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        strSQL+=" AND co_cli NOT IN(";
                        strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        
                        
                        
                    }
                
                System.out.println("CARGARDETREGUNO: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatUno.clear();
                //Obtener los registros.
                lblMsgSisUno.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next()){
                    if (blnCon){
                        vecRegUno=new Vector();
                        vecRegUno.add(INT_TBL_DAT_UNO_LIN,            "");
                        vecRegUno.add(INT_TBL_DAT_UNO_COD,            "" +  rst.getString("co_cli"));
                        vecRegUno.add(INT_TBL_DAT_UNO_IDE,            "" +  rst.getString("tx_ide"));
                        vecRegUno.add(INT_TBL_DAT_UNO_NOM,            "" +  rst.getString("tx_nom"));
                        vecRegUno.add(INT_TBL_DAT_UNO_DIR,            "" + rst.getString("tx_dir"));
                        vecRegUno.add(INT_TBL_DAT_UNO_TLF,            "" + rst.getString("tx_tel"));
                        vecRegUno.add(INT_TBL_DAT_UNO_CIU,            "" + rst.getString("co_ciu"));
                        vecRegUno.add(INT_TBL_DAT_UNO_BUT_ANE_UNO,"");
                        vecRegUno.add(INT_TBL_DAT_UNO_BUT_ANE_DOS,"");
			vecRegUno.add(INT_TBL_DAT_UNO_COD_UNI,    "");
			vecRegUno.add(INT_TBL_DAT_UNO_IDE_UNI,    "");
                        vecRegUno.add(INT_TBL_DAT_UNO_NOM_UNI,    "");
                        vecRegUno.add(INT_TBL_DAT_UNO_DIR_UNI,"");
                        vecRegUno.add(INT_TBL_DAT_UNO_TLF_UNI,"");
                        vecRegUno.add(INT_TBL_DAT_UNO_CIU_UNI,"");
                        
                        vecDatUno.add(vecRegUno);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else{
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
                objTblModUno.setData(vecDatUno);
                tblDat1.setModel(objTblModUno);
                vecDatUno.clear();
                if (intNumTotReg==tblDat1.getRowCount())
                    lblMsgSisUno.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSisUno.setText("Se encontraron " + intNumTotReg + " registros pero s�lo se procesaron " + tblDat1.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon1.setText("Consultar");
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
 
    
    private class ZafThreadGUIDos extends Thread{
        public void run(){
            //Limpiar objetos.
            objTblModDos.removeAllRows();
            if (!cargarDetRegDos()){
                //Inicializar objetos si no se pudo cargar los datos.
                butCon2.setText("Consultar");
            }
            else{
                butProce.setEnabled(true);
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat2.getRowCount()>0){
                tblDat2.setRowSelectionInterval(0, 0);
                tblDat2.requestFocus();
            }
            objThrGUIDos=null;
        }
    }
    
    
    private class ZafThreadGUIPro extends Thread{
        public void run(){
            try{
               cargarDrv();
                if(cargarReg()){
                    mostrarMsgInf("<HTML>El proceso de Unificaci�n de Clientes se realiz� con �xito.</HTML>");
                    lblMsgSis.setText("Listo");

                }
                else{
                    mostrarMsgInf("<HTML>El proceso de Unificaci�n de Clientes no se pudo realizar.</HTML>");
                }
            }
            catch(Exception e){
                
            }
            
            objThrGUIPro=null;
//            butPrcRem.setEnabled(false);

            //Inicializar objetos si no se pudo cargar los datos.
            butPrcRem.setText("Proceso Remoto");
            
            
//            
//                    if (!procesar())
//                        mostrarMsgInf("<HTML>El proceso de Unificaci�n de Clientes no se pudo realizar.</HTML>");
//                    else{
//                        mostrarMsgInf("<HTML>El proceso de Unificaci�n de Clientes se realiz� con �xito.</HTML>");
//                        lblMsgSis.setText("Listo");
//                    }
//
//                    objThrGUIPro=null;
//                    butProce.setEnabled(false);
//
//                    //Inicializar objetos si no se pudo cargar los datos.
//                    butProce.setText("Procesar");
        }
    }
    
    
    /**La funci�n ZafThreadGUIPro almacena en una tabla temporal los datos necesarios para
     *luego poder realizar el proceso de unificaci�n de clientes en todas las compa��as
     */
    private class ZafThreadGUIUniTmp extends Thread{
        public void run(){
            String strLinMod="";
            String strCodCliUni="";
            int intConFilPro=0; //esta variable se incrementar� de acuerdo a si hay un registro por procesar, si es mayor a cero entonces realizar� procesos dentro del bot�n Procesar
            
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            
            for(int i=0; i<objTblModUno.getRowCountTrue(); i++){
                strLinMod=objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_LIN)==null?"":objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_LIN).toString();
                if(strLinMod.equals("M")){
                    strCodCliUni=objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_COD_UNI)==null?"":objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_COD_UNI).toString();
                    
                    if( !(  strCodCliUni.equals(""))  ){
                        intConFilPro++;
                    }
                }
            }
            
            if(intConFilPro>0){
                if (!guardarDatTmp())
                    mostrarMsgInf("<HTML>El proceso de Unificaci�n de Clientes no se pudo realizar.</HTML>");
                else{
                    mostrarMsgInf("<HTML>El proceso de Unificaci�n de Clientes se realiz� con �xito.</HTML>");
                    lblMsgSis.setText("Listo");
                }
                
                objThrGUIUniTmp=null;
                butProce.setEnabled(false);
            }
            else{
                mostrarMsgInf("<HTML>No se ha seleccionado clientes a unificar<BR>Verifique y vuelva a intentarlo.</HTML>");
                if(objThrGUIUniTmp==null)
                    System.out.println("EGKJEGKMERG");
                else
                    objThrGUIUniTmp=null;
            }
            
            //Inicializar objetos si no se pudo cargar los datos.
            butProce.setText("Procesar");
        }
    }
    
    
    
    private boolean guardarDatTmp(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(guardarTblTmp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
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
    
    private boolean guardarTblTmp(){
        boolean blnRes=true;
        String strLinMod="";
        String strCodCliUni="";
        
        try{
            if(con!=null){
                stm=con.createStatement();
                
                for(int i=0; i<objTblModUno.getRowCountTrue();i++){
                    
                    strLinMod=objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_LIN)==null?"":objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_LIN).toString();
                    strCodCliUni=objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_COD_UNI)==null?"":objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_COD_UNI).toString();
                    if( strLinMod.equals("M") ){
                        if(  !  (strCodCliUni.equals(""))  ){
                            strSQL="";
                            strSQL+="INSERT INTO tbt_cliUni(";
                            strSQL+=" co_emp, co_cli, co_cliUni,";
                            strSQL+=" st_reg, co_usring, fe_ing)";
                            strSQL+="VALUES(";
                            strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                            strSQL+="" + objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_COD) + ",";
                            strSQL+="" + objTblModUno.getValueAt(i, INT_TBL_DAT_UNO_COD_UNI) + ",";
                            strSQL+="'A',";
                            strSQL+="" + objParSis.getCodigoUsuario() + ",";
                            strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                            strSQL+=")";
//                            System.out.println("SQL INSERTA EN tbt_cliUni: " + strSQL);
                            stm.executeUpdate(strSQL);
                            
                        }
                    }
                }                
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
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
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
    
    private boolean cargarDetRegDos(){
        int intCodEmp, intCodLoc, intNumTotReg=-1, i;
        double dblSub, dblIva;
        boolean blnRes=true;
        String strFecDes="", strFecHas="";
        strAux="";
        String strAuxLoc="";
        try{
            butCon2.setText("Detener");
            lblMsgSisDos.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Obtener la condici�n.
                if (txtCodPrv.getText().length()>0)
                    strAux+=" AND tx_ide='" + txtIdeCli.getText() + "'";

                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0){
                    strAux+=" AND ((LOWER(tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                    strAuxLoc=" WHERE ((LOWER(tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                
                if(strAuxLoc.equals(""))
                    strAuxLoc+=" WHERE a1.st_reg IN ('I')";
                else
                    strAuxLoc+=" AND a1.st_reg IN ('I')";
                
                if(objParSis.getCodigoMenu()==462){
                    strAuxLoc+=" AND a1.st_cli='S'";
                }
                else{
                    strAuxLoc+=" AND a1.st_prv='S'";
                }
                
                
                if(chkMosRegRep.isSelected()){
                    strSQL="";
                    strSQL+="SELECT COUNT(*) FROM(";
                    strSQL+=" 	SELECT x.*FROM(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                    strSQL+=" 	FROM tbm_cli AS a1" + strAuxLoc + ") AS x";
                    strSQL+=" 	 INNER JOIN(";
                    strSQL+=" 		SELECT co_emp, tx_ide";
                    strSQL+=" 		FROM tbm_cli";
                    strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                    strSQL+=strAux;
//                    strSQL+=" 		GROUP BY co_emp, tx_ide";
//                    strSQL+=" 		HAVING COUNT(tx_ide)>1";
                    strSQL+=" 		ORDER BY tx_ide";
                    strSQL+=" 	) AS y";
                    strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                    strSQL+=" WHERE co_cli NOT IN(";
                    strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    
                    strSQL+=" AND co_cli NOT IN(";
                    strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    strSQL+=" 	ORDER BY x.tx_ide";
                    strSQL+=" ) AS z";
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;

                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" 	SELECT x.*FROM(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                    strSQL+=" 	FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                    strSQL+=" 	 INNER JOIN(";
                    strSQL+=" 		SELECT co_emp, tx_ide";
                    strSQL+=" 		FROM tbm_cli";
                    strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                    strSQL+=strAux;
//                    strSQL+=" 		GROUP BY co_emp, tx_ide";
//                    strSQL+=" 		HAVING COUNT(tx_ide)>1";
                    strSQL+=" 		ORDER BY tx_ide";
                    strSQL+=" 	) AS y";
                    strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                    
                    strSQL+=" WHERE co_cli NOT IN(";
                    strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    strSQL+=" AND co_cli NOT IN(";
                    strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    
                    strSQL+=" 	ORDER BY x.tx_ide";
                }
                else
                    if(chkMosRegMov.isSelected()){
                        strSQL="";
                        strSQL+="SELECT COUNT(*) FROM(";
                        strSQL+="SELECT *FROM(";
                        strSQL+=" (";
                        strSQL+=" 	SELECT x.*FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                        strSQL+=" 	FROM tbm_cli AS a1" + strAuxLoc + ") AS x";
                        strSQL+=" 	 INNER JOIN(";
                        strSQL+=" 		SELECT co_emp, tx_ide";
                        strSQL+=" 		FROM tbm_cli";
                        strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                        strSQL+=strAux;
//                        strSQL+=" 		GROUP BY co_emp, tx_ide";
//                        strSQL+=" 		HAVING COUNT(tx_ide)>1";
                        strSQL+=" 		ORDER BY tx_ide";
                        strSQL+=" 	) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 	ORDER BY x.tx_ide";
                        strSQL+=" )";
                        strSQL+=" EXCEPT";
                        strSQL+=" (";
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabPag) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                        strSQL+=" 			FROM tbm_cli AS a1 " + strAuxLoc + ") AS x";
                        strSQL+=" 			 INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        strSQL+=" 	UNION";
                        //--2-- EN TBM_CABMOVINV  -  PAGOS A REALIZAR
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabMovInv) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                        strSQL+=" 			FROM tbm_cli AS a1 " + strAuxLoc + ") AS x";
                        strSQL+=" 			INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        if(objParSis.getCodigoMenu()==462){
                            strSQL+=" 	UNION";
                            //--3-- EN TBM_CABCOTVEN  -  COTIZACIONES DE CLIENTES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_cli";
                            strSQL+=" 		FROM tbm_CabCotVen) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                            strSQL+=" 			FROM tbm_cli AS a1 " + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        }
                        else{
                            strSQL+=" 	UNION";
                            //--4-- EN TBM_CABCOTCOM  -  COTIZACIONES DE PROVEEDORES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_prv";
                            strSQL+=" 		FROM tbm_CabCotCom) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom";
                            strSQL+=" 			FROM tbm_cli AS a1 " + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom";
                        }


                        strSQL+=" )";
                        strSQL+=" ORDER BY tx_ide, co_cli";
                        
                        strSQL+=") AS k WHERE co_cli NOT IN(";
                        strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        strSQL+=" AND co_cli NOT IN(";
                        strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        
                        strSQL+=" ) AS q";

                        intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intNumTotReg==-1)
                            return false;

                        strSQL="";
                        strSQL+="SELECT *FROM(";
                        strSQL+=" (";
                        strSQL+=" 	SELECT x.*FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                        strSQL+=" 	FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                        strSQL+=" 	 INNER JOIN(";
                        strSQL+=" 		SELECT co_emp, tx_ide";
                        strSQL+=" 		FROM tbm_cli";
                        strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                        strSQL+=strAux;
//                        strSQL+=" 		GROUP BY co_emp, tx_ide";
//                        strSQL+=" 		HAVING COUNT(tx_ide)>1";
                        strSQL+=" 		ORDER BY tx_ide";
                        strSQL+=" 	) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 	ORDER BY x.tx_ide";
                        strSQL+=" )";
                        strSQL+=" EXCEPT";
                        strSQL+=" (";
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabPag) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                        strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                        strSQL+=" 			 INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        strSQL+=" 	UNION";
                        //--2-- EN TBM_CABMOVINV  -  PAGOS A REALIZAR
                        strSQL+=" 	SELECT y.* FROM(";
                        strSQL+=" 		SELECT co_emp, fe_doc, co_cli";
                        strSQL+=" 		FROM tbm_CabMovInv) AS x";
                        strSQL+=" 		INNER JOIN(";
                        strSQL+=" 			SELECT x.*FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                        strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                        strSQL+=" 			INNER JOIN(";
                        strSQL+=" 				SELECT co_emp, tx_ide";
                        strSQL+=" 				FROM tbm_cli";
                        strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                        strSQL+=strAux;
//                        strSQL+=" 				GROUP BY co_emp, tx_ide";
//                        strSQL+=" 				HAVING COUNT(tx_ide)>1";
                        strSQL+=" 				ORDER BY tx_ide";
                        strSQL+=" 			) AS y";
                        strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                        strSQL+=" 			ORDER BY x.tx_ide";
                        strSQL+=" 		) AS y";
                        strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        if(objParSis.getCodigoMenu()==462){
                            strSQL+=" 	UNION";
                            //--3-- EN TBM_CABCOTVEN  -  COTIZACIONES DE CLIENTES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_cli";
                            strSQL+=" 		FROM tbm_CabCotVen) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                            strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";                            
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        }
                        else{
                            strSQL+=" 	UNION";
                            //--4-- EN TBM_CABCOTCOM  -  COTIZACIONES DE PROVEEDORES
                            strSQL+=" 	SELECT y.* FROM(";
                            strSQL+=" 		SELECT co_emp, fe_cot, co_prv";
                            strSQL+=" 		FROM tbm_CabCotCom) AS x";
                            strSQL+=" 		INNER JOIN(";
                            strSQL+=" 			SELECT x.*FROM(";
                            strSQL+=" 			SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.co_ciu, a2.tx_descor, a2.tx_desLar";
                            strSQL+=" 			FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2 ON a1.co_ciu=a2.co_ciu " + strAuxLoc + ") AS x";
                            strSQL+=" 			 INNER JOIN(";
                            strSQL+=" 				SELECT co_emp, tx_ide";
                            strSQL+=" 				FROM tbm_cli";
                            strSQL+=" 				WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='I'";
                            strSQL+=strAux;
//                            strSQL+=" 				GROUP BY co_emp, tx_ide";
//                            strSQL+=" 				HAVING COUNT(tx_ide)>1";
                            strSQL+=" 				ORDER BY tx_ide";
                            strSQL+=" 			) AS y";
                            strSQL+=" 			ON x.co_emp=y.co_emp AND x.tx_ide=y.tx_ide";
                            strSQL+=" 			ORDER BY x.tx_ide";
                            strSQL+=" 		) AS y";
                            strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
                            strSQL+=" 	GROUP BY y.co_emp, y.co_cli, y.tx_ide, y.tx_nom, y.tx_dir, y.tx_tel, y.co_ciu, y.tx_descor, y.tx_desLar";
                        }
                        strSQL+=" )";
                        strSQL+=" ORDER BY tx_ide, co_cli";
                        
                        strSQL+=") AS k WHERE co_cli NOT IN(";
                        strSQL+=" SELECT co_cli FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                        strSQL+=" AND co_cli NOT IN(";
                        strSQL+=" SELECT co_cliUni FROM tbt_cliUni WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg IN ('A','P'))";
                    }
//                System.out.println("CARGARDETREGDOS: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatDos.clear();
                //Obtener los registros.
                lblMsgSisDos.setText("Cargando datos...");
                i=0;
                while (rst.next()){
                    if (blnCon){
                        vecRegDos=new Vector();
                        vecRegDos.add(INT_TBL_DAT_DOS_LIN,        "");
                        vecRegDos.add(INT_TBL_DAT_DOS_COD,        "" + rst.getString("co_cli"));
                        vecRegDos.add(INT_TBL_DAT_DOS_IDE,        "" + rst.getString("tx_ide"));
                        vecRegDos.add(INT_TBL_DAT_DOS_NOM,        "" + rst.getString("tx_nom"));
                        vecRegDos.add(INT_TBL_DAT_DOS_DIR,        "" + rst.getString("tx_dir"));
                        vecRegDos.add(INT_TBL_DAT_DOS_TLF,        "" + rst.getString("tx_tel"));
                        vecRegDos.add(INT_TBL_DAT_DOS_CIU,        "" + rst.getString("co_ciu"));
                        vecRegDos.add(INT_TBL_DAT_DOS_BUT_ANE_UNO,"");
                        vecRegDos.add(INT_TBL_DAT_DOS_BUT_ANE_DOS,"");                        
                        vecDatDos.add(vecRegDos);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else{
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
                objTblModDos.setData(vecDatDos);
                tblDat2.setModel(objTblModDos);
                vecDatDos.clear();
                if (intNumTotReg==tblDat2.getRowCount())
                    lblMsgSisDos.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSisDos.setText("Se encontraron " + intNumTotReg + " registros pero s�lo se procesaron " + tblDat2.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon2.setText("Consultar");
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
    
    private void callMaeClientesTblUno(int fila){
        int intFilSelTblUno=fila;
        Maestros.ZafMae07.ZafMae07 objMae_07Uno=new Maestros.ZafMae07.ZafMae07(objParSis, objTblModUno.getValueAt(intFilSelTblUno, INT_TBL_DAT_UNO_COD).toString());
        this.getParent().add(objMae_07Uno,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objMae_07Uno.show();
    }
    
    private void callMaeClientesTblDos(int fila){
        int intFilSelTblDos=fila;
        Maestros.ZafMae07.ZafMae07 objMae_07Dos=new Maestros.ZafMae07.ZafMae07(objParSis, objTblModDos.getValueAt(intFilSelTblDos, INT_TBL_DAT_DOS_COD).toString());
        this.getParent().add(objMae_07Dos,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objMae_07Dos.show();
    }
    
    
    //DEBERIA HACERSE UNA FUNCION Q ENVIE COMO PARAMETRO CODIGO DE CLIENTE, Y OBJETO ZAFPARSIS PARA EN ESE NUEVO FORMULARIO CARGAR ESTE QUERY
    private void callEstadisticasClientesTblUno(int fila){
        int intFilSelTblUno=fila;
        CxC.ZafCxC12.ZafCxC12_01 objCxC_12_01Uno=new CxC.ZafCxC12.ZafCxC12_01(objParSis, Integer.parseInt(objTblModUno.getValueAt(intFilSelTblUno, INT_TBL_DAT_UNO_COD).toString()), objTblModUno.getValueAt(intFilSelTblUno, INT_TBL_DAT_UNO_NOM).toString());
        this.getParent().add(objCxC_12_01Uno,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCxC_12_01Uno.show();
    }
    
    //DEBERIA HACERSE UNA FUNCION Q ENVIE COMO PARAMETRO CODIGO DE CLIENTE, Y OBJETO ZAFPARSIS PARA EN ESE NUEVO FORMULARIO CARGAR ESTE QUERY
    private void callEstadisticasClientesTblDos(int fila){
        int intFilSelTblDos=fila;
        CxC.ZafCxC12.ZafCxC12_01 objCxC_12_01Dos=new CxC.ZafCxC12.ZafCxC12_01(objParSis, Integer.parseInt(objTblModDos.getValueAt(intFilSelTblDos, INT_TBL_DAT_DOS_COD).toString()), objTblModDos.getValueAt(intFilSelTblDos, INT_TBL_DAT_DOS_NOM).toString());
        this.getParent().add(objCxC_12_01Dos,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCxC_12_01Dos.show();
    }
    
    
    private boolean llevaInformacTblDosTblUno(int filaTablaUno, int filaTablaDos){
        boolean blnRes=true;
        int intFilTblUno=filaTablaUno;
        int intFilTblDos=filaTablaDos;
        try{
            //SI LA FILA AUN ESTA VACIA PARA LOS CAMPOS A UNIFICAR
            if( objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_COD_UNI).toString().equals("")){
                objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_COD), intFilTblUno, INT_TBL_DAT_UNO_COD_UNI);
                objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_IDE), intFilTblUno, INT_TBL_DAT_UNO_IDE_UNI);
                objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_NOM), intFilTblUno, INT_TBL_DAT_UNO_NOM_UNI);
                
                //esto es nuevo
                objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_DIR), intFilTblUno, INT_TBL_DAT_UNO_DIR_UNI);
                objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_TLF), intFilTblUno, INT_TBL_DAT_UNO_TLF_UNI);
                objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_CIU), intFilTblUno, INT_TBL_DAT_UNO_CIU_UNI);
                
                objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_COD);
                objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_IDE);
                objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_NOM);
                
                objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_DIR);
                objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_TLF);
                objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_CIU);
                
                objTblModDos.removeEmptyRows();
            }
            else{
                //SI LA FILA YA CONTIENE DATOS EN LA TABLA PARA LOS CAMPOS A UNIFICAR
                String strTit, strMsg;
                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                strTit="Mensaje del sistema Zafiro";
                strMsg="�Est� seguro que desea realizar este proceso, ya existen datos a unificar?";
                
                String strCodCliUniTmp=objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_COD_UNI).toString();
                String strIdeCliUniTmp=objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_IDE_UNI).toString();
                String strNomCliUniTmp=objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_NOM_UNI).toString();
                
                String strDirCliUniTmp=objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_DIR_UNI).toString();
                String strTelCliUniTmp=objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_TLF_UNI).toString();
                String strCiuCliUniTmp=objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_CIU_UNI).toString();
                
                
                if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                    //pasa los datos a la tabla uno desde la tabla dos
                    objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_COD), intFilTblUno, INT_TBL_DAT_UNO_COD_UNI);
                    objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_IDE), intFilTblUno, INT_TBL_DAT_UNO_IDE_UNI);
                    objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_NOM), intFilTblUno, INT_TBL_DAT_UNO_NOM_UNI);
                    
                    //coloca blancos en la tabla dos, porq esos datos ya fueron pasados a la tabla uno
                    objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_DIR), intFilTblUno, INT_TBL_DAT_UNO_DIR_UNI);
                    objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_TLF), intFilTblUno, INT_TBL_DAT_UNO_TLF_UNI);
                    objTblModUno.setValueAt("" + objTblModDos.getValueAt(intFilTblDos, INT_TBL_DAT_DOS_CIU), intFilTblUno, INT_TBL_DAT_UNO_CIU_UNI);
                    
                    objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_COD);
                    objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_IDE);
                    objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_NOM);
                    
                    objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_DIR);
                    objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_TLF);
                    objTblModDos.setValueAt("", intFilTblDos, INT_TBL_DAT_DOS_CIU);
                    
                    objTblModDos.removeEmptyRows();
                    
                    objTblModDos.insertRow(intFilTblDos);
                    objTblModDos.setValueAt("" + strCodCliUniTmp, intFilTblDos, INT_TBL_DAT_DOS_COD);
                    objTblModDos.setValueAt("" + strIdeCliUniTmp, intFilTblDos, INT_TBL_DAT_DOS_IDE);
                    objTblModDos.setValueAt("" + strNomCliUniTmp, intFilTblDos, INT_TBL_DAT_DOS_NOM);
                    
                    objTblModDos.setValueAt("" + strDirCliUniTmp, intFilTblDos, INT_TBL_DAT_DOS_DIR);
                    objTblModDos.setValueAt("" + strTelCliUniTmp, intFilTblDos, INT_TBL_DAT_DOS_TLF);
                    objTblModDos.setValueAt("" + strCiuCliUniTmp, intFilTblDos, INT_TBL_DAT_DOS_CIU);
                    
                }
                else{
//                    System.out.println("NO HACE NADA!!!!! PORQ YA EXISTEN DATOS INGRESADOS");
                }
            }                        
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
    private boolean llevaInformacTblUnoTblDos(int filaTablaUno, int filaTablaDos){
        boolean blnRes=true;
        int intFilTblUno=filaTablaUno;
        int intFilTblDos=filaTablaDos;
        try{
            objTblModDos.insertRow(intFilTblDos);
            
            objTblModDos.setValueAt("" + objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_COD_UNI), intFilTblDos, INT_TBL_DAT_DOS_COD);
            objTblModDos.setValueAt("" + objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_IDE_UNI), intFilTblDos, INT_TBL_DAT_DOS_IDE);
            objTblModDos.setValueAt("" + objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_NOM_UNI), intFilTblDos, INT_TBL_DAT_DOS_NOM);
            
            objTblModDos.setValueAt("" + objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_DIR_UNI), intFilTblDos, INT_TBL_DAT_DOS_DIR);
            objTblModDos.setValueAt("" + objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_TLF_UNI), intFilTblDos, INT_TBL_DAT_DOS_TLF);
            objTblModDos.setValueAt("" + objTblModUno.getValueAt(intFilTblUno, INT_TBL_DAT_UNO_CIU_UNI), intFilTblDos, INT_TBL_DAT_DOS_CIU);
            
            objTblModUno.setValueAt("", intFilTblUno, INT_TBL_DAT_UNO_COD_UNI);
            objTblModUno.setValueAt("", intFilTblUno, INT_TBL_DAT_UNO_IDE_UNI);
            objTblModUno.setValueAt("", intFilTblUno, INT_TBL_DAT_UNO_NOM_UNI);
            
            objTblModUno.setValueAt("", intFilTblUno, INT_TBL_DAT_UNO_DIR_UNI);
            objTblModUno.setValueAt("", intFilTblUno, INT_TBL_DAT_UNO_TLF_UNI);
            objTblModUno.setValueAt("", intFilTblUno, INT_TBL_DAT_UNO_CIU_UNI);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
//    private boolean mostrarMsgConPro(){
//        boolean blnRes=true;
//        try{
//            // TODO add your handling code here:
//            String strTit, strMsg;
//            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
//            strTit="Mensaje del sistema Zafiro";
//            strMsg="<HTML>�Est� seguro que desea realizar esta operaci�n?";
//            strMsg+="<BR> Una vez realizado el proceso de unificaci�n no ser� posible dar marcha atr�s.";
//            strMsg+="<BR> Este proceso pasar� todos los movimientos del cliente a unificar";
//            strMsg+="<BR> (cotizaciones, facturas, pagos, etc.) al cliente con el cial se va a unificar";
//            strMsg+="<BR> antes de eliminarlo completamente del sistema";
//            strMsg+="</HTML>";
//
//            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
//                // se crea el objeto de tipo hilo
//                if(procesar())
//                    blnRes=true;
//                else
//                    blnRes=false;
//            }
//        }
//        catch(Exception e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    

//    public boolean procesar(){
//        if (!procesarReg())
//            return false;
//        return true;
//    }
    
    
    /**
     * Esta funci�n inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean procesarReg(Connection conTmp){
        boolean blnRes=false;
            try{
                strConIni=objParSis.getStringConexion();
                if(conTmp!= null){
                    conTmp.setAutoCommit(false);

                    if (inserta_tbhCli(conTmp)){
                        if (inserta_tbmUniCli(conTmp)){
                            if (inserta_tbhBenChq(conTmp)){
                                if (inserta_tbhDirCli(conTmp)){
                                    if (inserta_tbhConCli(conTmp)){
                                        if (inserta_tbhForPagCli(conTmp)){
                                            if (inserta_tbhObsCli(conTmp)){
                                                if (inserta_tbhAccEmpCli(conTmp)){
                                                    if (inserta_tbhSolCre(conTmp)){
                                                        if (inserta_tbhRefComSolCre(conTmp)){
                                                            if (inserta_tbhRefBanSolCre(conTmp)){
                                                                if (inserta_tbhDocDigSolCre(conTmp)){
                                                                    if (inserta_tbhCabCotVen(conTmp)){
                                                                        if (inserta_tbhDetCotVen(conTmp)){
                                                                            if (inserta_tbhPagCotVen(conTmp)){
                                                                                if (inserta_tbhCabCotCom(conTmp)){
                                                                                    if (inserta_tbhDetCotCom(conTmp)){
                                                                                        if (inserta_tbhPagCotCom(conTmp)){
                                                                                            if (inserta_tbhCabMovInv(conTmp)){
                                                                                                if (inserta_tbhDetMovInv(conTmp)){
                                                                                                    if (inserta_tbhPagMovInv(conTmp)){
                                                                                                        if (inserta_tbhCabPag(conTmp)){
                                                                                                            if (inserta_tbhDetPag(conTmp)){

                                                                                                                if (update_tbm_cabGuiRem(conTmp)){
                                                                                                                    if (update_tbmCabCotVen(conTmp)){
                                                                                                                        if (update_tbmDetCotVen(conTmp)){
                                                                                                                            if (update_tbmCabCotCom(conTmp)){
                                                                                                                                if (update_tbmCabMovInv(conTmp)){
                                                                                                                                    if (update_tbmCabPag(conTmp)){
                                                                                                                                        if(update_tbmBenChq(conTmp)){
                                                                                                                                            if (delete_tbmDirCli(conTmp)){
                                                                                                                                                if (delete_tbmConCli(conTmp)){
                                                                                                                                                    if (delete_tbmForPagCli(conTmp)){
                                                                                                                                                        if (delete_tbmObsCli(conTmp)){
                                                                                                                                                            if (delete_tbmAccEmpCli(conTmp)){
                                                                                                                                                                if (delete_tbmRefComSolCre(conTmp)){
                                                                                                                                                                    if (delete_tbmRefBanSolCre(conTmp)){
                                                                                                                                                                        if (delete_tbmdocDigSolCre(conTmp)){
                                                                                                                                                                            if (delete_tbmdocDigSolCre(conTmp)){
                                                                                                                                                                                if (delete_tbmSolCre(conTmp)){

                                                                                                                                                                                    if (update_tbmDetRecDoc(conTmp)){
                                                                                                                                                                                        if (delete_tbrCliLoc(conTmp)){
                                                                                                                                                                                            if (delete_tbmCli(conTmp)){
                                                                                                                                                                                                conTmp.commit();
                                                                                                                                                                                                blnRes=true;
                                                                                                                                                                                            }
                                                                                                                                                                                            else{
                                                                                                                                                                                                blnRes=false;
                                                                                                                                                                                                conTmp.rollback();
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                        else{
                                                                                                                                                                                            blnRes=false;
                                                                                                                                                                                            conTmp.rollback();
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                    else{
                                                                                                                                                                                        blnRes=false;
                                                                                                                                                                                        conTmp.rollback();
                                                                                                                                                                                    }




                                                                                                                                                                                }
                                                                                                                                                                                else{
                                                                                                                                                                                    blnRes=false;
                                                                                                                                                                                    conTmp.rollback();
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                            else{
                                                                                                                                                                                blnRes=false;
                                                                                                                                                                                conTmp.rollback();
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                        else{
                                                                                                                                                                            blnRes=false;
                                                                                                                                                                            conTmp.rollback();
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                    else{
                                                                                                                                                                        blnRes=false;
                                                                                                                                                                        conTmp.rollback();
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                                else{
                                                                                                                                                                    blnRes=false;
                                                                                                                                                                    conTmp.rollback();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                            else{
                                                                                                                                                                blnRes=false;
                                                                                                                                                                conTmp.rollback();
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        else{
                                                                                                                                                            blnRes=false;
                                                                                                                                                            conTmp.rollback();
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                    else{
                                                                                                                                                        blnRes=false;
                                                                                                                                                        conTmp.rollback();
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                else{
                                                                                                                                                    blnRes=false;
                                                                                                                                                    conTmp.rollback();
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            else{
                                                                                                                                                blnRes=false;
                                                                                                                                                conTmp.rollback();
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        else{
                                                                                                                                            blnRes=false;
                                                                                                                                            conTmp.rollback();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    else{
                                                                                                                                        blnRes=false;
                                                                                                                                        conTmp.rollback();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                else{
                                                                                                                                    blnRes=false;
                                                                                                                                    conTmp.rollback();
                                                                                                                                }
                                                                                                                            }
                                                                                                                            else{
                                                                                                                                blnRes=false;
                                                                                                                                conTmp.rollback();
                                                                                                                            }
                                                                                                                        }
                                                                                                                        else{
                                                                                                                            blnRes=false;
                                                                                                                            conTmp.rollback();
                                                                                                                        }
                                                                                                                    }
                                                                                                                    else{
                                                                                                                        blnRes=false;
                                                                                                                        conTmp.rollback();
                                                                                                                    }
                                                                                                                }
                                                                                                                else{
                                                                                                                    blnRes=false;
                                                                                                                    conTmp.rollback();
                                                                                                                }





                                                                                                            }
                                                                                                            else{
                                                                                                                blnRes=false;
                                                                                                                conTmp.rollback();
                                                                                                            }
                                                                                                        }
                                                                                                        else{
                                                                                                            blnRes=false;
                                                                                                            conTmp.rollback();
                                                                                                        }
                                                                                                    }
                                                                                                    else{
                                                                                                        blnRes=false;
                                                                                                        conTmp.rollback();
                                                                                                    }
                                                                                                }
                                                                                                else{
                                                                                                    blnRes=false;
                                                                                                    conTmp.rollback();
                                                                                                }
                                                                                            }
                                                                                            else{
                                                                                                blnRes=false;
                                                                                                conTmp.rollback();
                                                                                            }
                                                                                        }
                                                                                        else{
                                                                                            blnRes=false;
                                                                                            conTmp.rollback();
                                                                                        }
                                                                                    }
                                                                                    else{
                                                                                        blnRes=false;
                                                                                        conTmp.rollback();
                                                                                    }
                                                                                }
                                                                                else{
                                                                                    blnRes=false;
                                                                                    conTmp.rollback();
                                                                                }
                                                                            }
                                                                            else{
                                                                                blnRes=false;
                                                                                conTmp.rollback();
                                                                            }
                                                                        }
                                                                        else{
                                                                            blnRes=false;
                                                                            conTmp.rollback();
                                                                        }
                                                                    }
                                                                    else{
                                                                        blnRes=false;
                                                                        conTmp.rollback();
                                                                    }
                                                                }
                                                                else{
                                                                    blnRes=false;
                                                                    conTmp.rollback();
                                                                }
                                                            }
                                                            else{
                                                                blnRes=false;
                                                                conTmp.rollback();
                                                            }
                                                        }
                                                        else{
                                                            blnRes=false;
                                                            conTmp.rollback();
                                                        }
                                                    }
                                                    else{
                                                        blnRes=false;
                                                        conTmp.rollback();
                                                    }
                                                }
                                                else{
                                                    blnRes=false;
                                                    conTmp.rollback();
                                                }
                                            }
                                            else{
                                                blnRes=false;
                                                conTmp.rollback();
                                            }
                                        }
                                        else{
                                            blnRes=false;
                                            conTmp.rollback();
                                        }
                                    }
                                    else{
                                        blnRes=false;
                                        conTmp.rollback();
                                    }
                                }
                                else{
                                    blnRes=false;
                                    conTmp.rollback();
                                }
                            }
                            else{
                                blnRes=false;
                                conTmp.rollback();
                            }
                        }
                        else{
                            blnRes=false;
                            conTmp.rollback();
                        }
                    }
                    else{
                        conTmp.rollback();
                        blnRes=false;
                    }
                    conTmp.close();
                    conTmp=null;
                }
                                

            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        return blnRes;
    }
    
    
    private boolean consultarReg(Connection conTmp){
        boolean blnRes=false;
            try{
                conTmp.setAutoCommit(false);
                if( conTmp!= null){
                    
                    
                    
                    if (delete_tbtCli(conTmp)){
                        if (delete_tbtUniCli(conTmp)){
                            if (select_tbhCli(conTmp)){
                                if (inserta_tbtCli(conTmp)){
                                    if (select_tbmUniCli(conTmp)){
                                        if (inserta_tbtUniCli(conTmp)){
                                            if (select_tbhBenChq(conTmp)){
                                                if (select_tbhDirCli(conTmp)){
                                                    if (select_tbhConCli(conTmp)){
                                                        if (select_tbhForPagCli(conTmp)){
                                                            if (select_tbhObsCli(conTmp)){
                                                                if (select_tbhAccEmpCli(conTmp)){
                                                                    if (select_tbhSolCre(conTmp)){
                                                                        if (select_tbhRefComSolCre(conTmp)){
                                                                            if (select_tbhRefBanSolCre(conTmp)){
                                                                                if (select_tbhDocDigSolCre(conTmp)){
                                                                                    if (select_tbhCabCotVen(conTmp)){
                                                                                        if (select_tbhDetCotVen(conTmp)){
                                                                                            if (select_tbhPagCotVen(conTmp)){
                                                                                                if (select_tbhCabCotCom(conTmp)){
                                                                                                    if (select_tbhDetCotCom(conTmp)){
                                                                                                        if (select_tbhPagCotCom(conTmp)){
                                                                                                            if (select_tbhCabMovInv(conTmp)){
                                                                                                                if (select_tbhDetMovInv(conTmp)){
                                                                                                                    if (select_tbhPagMovInv(conTmp)){
                                                                                                                        if (select_tbhCabPag(conTmp)){
                                                                                                                            if (select_tbhDetPag(conTmp)){
                                                                                                                                if (select_update_tbmCabCotVen(conTmp)){
                                                                                                                                    if (select_update_tbmDetCotVen(conTmp)){
                                                                                                                                        if (select_update_tbmCabCotCom(conTmp)){
                                                                                                                                            if (select_update_tbmCabMovInv(conTmp)){
                                                                                                                                                if (select_update_tbmCabPag(conTmp)){
                                                                                                                                                    if(select_update_tbmBenChq(conTmp)){
                                                                                                                                                        if (select_delete_tbmDirCli(conTmp)){
                                                                                                                                                            if (select_delete_tbmConCli(conTmp)){
                                                                                                                                                                if (select_delete_tbmForPagCli(conTmp)){
                                                                                                                                                                    if (select_delete_tbmObsCli(conTmp)){
                                                                                                                                                                        if (select_delete_tbmAccEmpCli(conTmp)){
                                                                                                                                                                            if (select_delete_tbmRefComSolCre(conTmp)){
                                                                                                                                                                                if (select_delete_tbmRefBanSolCre(conTmp)){
                                                                                                                                                                                    if (select_delete_tbmdocDigSolCre(conTmp)){
                                                                                                                                                                                        if (select_delete_tbmdocDigSolCre(conTmp)){
                                                                                                                                                                                            if (select_delete_tbmSolCre(conTmp)){
                                                                                                                                                                                                if (select_update_tbmDetRecDoc(conTmp)){
                                                                                                                                                                                                    if (select_delete_tbmCli(conTmp)){
                                                                                                                                                                                                        if (select_delete_tbrCliLoc(conTmp)){
                                                                                                                                                                                                            if (select_update_tbmCabGuiRem(conTmp)){
                                                                                                                                                                                                                System.out.println("TERMINO LOS SELECTS");
                                                                                                                                                                                                                conTmp.commit();
                                                                                                                                                                                                                blnRes=true;
                                                                                                                                                                                                            }

                                                                                                                                                                                                        }
                                                                                                                                                                                                        else{
                                                                                                                                                                                                            blnRes=false;
                                                                                                                                                                                                            conTmp.rollback();
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }
                                                                                                                                                                                                    else{
                                                                                                                                                                                                        blnRes=false;
                                                                                                                                                                                                        conTmp.rollback();
                                                                                                                                                                                                    }


                                                                                                                                                                                                }
                                                                                                                                                                                                else{
                                                                                                                                                                                                    blnRes=false;
                                                                                                                                                                                                    conTmp.rollback();
                                                                                                                                                                                                }

                                                                                                                                                                                            }
                                                                                                                                                                                            else{
                                                                                                                                                                                                blnRes=false;
                                                                                                                                                                                                conTmp.rollback();
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                        else{
                                                                                                                                                                                            blnRes=false;
                                                                                                                                                                                            conTmp.rollback();
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                    else{
                                                                                                                                                                                        blnRes=false;
                                                                                                                                                                                        conTmp.rollback();
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                                else{
                                                                                                                                                                                    blnRes=false;
                                                                                                                                                                                    conTmp.rollback();
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                            else{
                                                                                                                                                                                blnRes=false;
                                                                                                                                                                                conTmp.rollback();
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                        else{
                                                                                                                                                                            blnRes=false;
                                                                                                                                                                            conTmp.rollback();
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                    else{
                                                                                                                                                                        blnRes=false;
                                                                                                                                                                        conTmp.rollback();
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                                else{
                                                                                                                                                                    blnRes=false;
                                                                                                                                                                    conTmp.rollback();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                            else{
                                                                                                                                                                blnRes=false;
                                                                                                                                                                conTmp.rollback();
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        else{
                                                                                                                                                            blnRes=false;
                                                                                                                                                            conTmp.rollback();
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                    else{
                                                                                                                                                        blnRes=false;
                                                                                                                                                        conTmp.rollback();
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                else{
                                                                                                                                                    blnRes=false;
                                                                                                                                                    conTmp.rollback();
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            else{
                                                                                                                                                blnRes=false;
                                                                                                                                                conTmp.rollback();
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        else{
                                                                                                                                            blnRes=false;
                                                                                                                                            conTmp.rollback();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    else{
                                                                                                                                        blnRes=false;
                                                                                                                                        conTmp.rollback();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                else{
                                                                                                                                    blnRes=false;
                                                                                                                                    conTmp.rollback();
                                                                                                                                }
                                                                                                                            }
                                                                                                                            else{
                                                                                                                                blnRes=false;
                                                                                                                                conTmp.rollback();
                                                                                                                            }
                                                                                                                        }
                                                                                                                        else{
                                                                                                                            blnRes=false;
                                                                                                                            conTmp.rollback();
                                                                                                                        }
                                                                                                                    }
                                                                                                                    else{
                                                                                                                        blnRes=false;
                                                                                                                        conTmp.rollback();
                                                                                                                    }
                                                                                                                }
                                                                                                                else{
                                                                                                                    blnRes=false;
                                                                                                                    conTmp.rollback();
                                                                                                                }
                                                                                                            }
                                                                                                            else{
                                                                                                                blnRes=false;
                                                                                                                conTmp.rollback();
                                                                                                            }
                                                                                                        }
                                                                                                        else{
                                                                                                            blnRes=false;
                                                                                                            conTmp.rollback();
                                                                                                        }
                                                                                                    }
                                                                                                    else{
                                                                                                        blnRes=false;
                                                                                                        conTmp.rollback();
                                                                                                    }
                                                                                                }
                                                                                                else{
                                                                                                    blnRes=false;
                                                                                                    conTmp.rollback();
                                                                                                }
                                                                                            }
                                                                                            else{
                                                                                                blnRes=false;
                                                                                                conTmp.rollback();
                                                                                            }
                                                                                        }
                                                                                        else{
                                                                                            blnRes=false;
                                                                                            conTmp.rollback();
                                                                                        }
                                                                                    }
                                                                                    else{
                                                                                        blnRes=false;
                                                                                        conTmp.rollback();
                                                                                    }
                                                                                }
                                                                                else{
                                                                                    blnRes=false;
                                                                                    conTmp.rollback();
                                                                                }
                                                                            }
                                                                            else{
                                                                                blnRes=false;
                                                                                conTmp.rollback();
                                                                            }
                                                                        }
                                                                        else{
                                                                            blnRes=false;
                                                                            conTmp.rollback();
                                                                        }
                                                                    }
                                                                    else{
                                                                        blnRes=false;
                                                                        conTmp.rollback();
                                                                    }
                                                                }
                                                                else{
                                                                    blnRes=false;
                                                                    conTmp.rollback();
                                                                }
                                                            }
                                                            else{
                                                                blnRes=false;
                                                                conTmp.rollback();
                                                            }
                                                        }
                                                        else{
                                                            blnRes=false;
                                                            conTmp.rollback();
                                                        }
                                                    }
                                                    else{
                                                        blnRes=false;
                                                        conTmp.rollback();
                                                    }
                                                }
                                                else{
                                                    blnRes=false;
                                                    conTmp.rollback();
                                                }
                                            }
                                            else{
                                                blnRes=false;
                                                conTmp.rollback();
                                            }
                                        }
                                        else{
                                            blnRes=false;
                                            conTmp.rollback();
                                        }





                                    }
                                    else{
                                        blnRes=false;
                                        conTmp.rollback();
                                    }
                                }
                                else{
                                    blnRes=false;
                                    conTmp.rollback();
                                }

                            }
                            else{
                                blnRes=false;
                                conTmp.rollback();
                            }
                            
                        }
                        else{
                            blnRes=false;
                            conTmp.rollback();
                        }
                    }
                    else{
                        blnRes=false;
                        conTmp.rollback();
                    }
                    
                    

                    conTmp.close();
                    conTmp=null;
                }
                                

            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        return blnRes;
    }
        
    
    

                            
    private boolean delete_tbtCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                strSQL="";
                strSQL+="DELETE FROM tbt_cli";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
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

                            
    private boolean delete_tbtUniCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                strSQL="";
                strSQL+="DELETE FROM tbt_uniCli";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
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
                            
                            
    
    
    
    
    private int getUltCodigoHistorico(String codigoClienteProveedor, Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliPrv=codigoClienteProveedor;
        int intMaxCodHis=-1;
        try{
            if(conTmpMai!=null){
                stm=conTmpMai.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(co_his) AS maxCodCli";
                strSQL+=" FROM tbh_cli";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_cli=" + strCodCliPrv + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intMaxCodHis=rst.getInt("maxCodCli");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
        return intMaxCodHis;
    }
    
    private boolean inserta_tbhCli(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecConEmp="";
        String strFecUltActDat="";
        String strFecIng="";
        String strFecUltMod="";
        String strFecHis="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhCli.size(); i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cli(";
                    strSQL+="           co_emp, co_cli, co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir,";
                    strSQL+=" 	    tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu,";
                    strSQL+=" 	    co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag,";
                    strSQL+="       nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing,";
                    strSQL+=" 	    fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti,";
                    strSQL+=" 	    st_regrep, st_cieretpen, ne_diagrachqfec,tx_tel1, tx_tel2, tx_tel3,";
                    strSQL+="           tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro,";
                    strSQL+=" 	    fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven,";
                    strSQL+=" 	    tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat,";
                    strSQL+="           fe_his, co_usrhis, fe_ultactdat";
    
                    strSQL+=" , tx_obsInfBurCre, ne_numMesProActDat, st_ivaCom, st_ivaVen, st_perIngNomCliCotVen";
                    
                    strSQL+=" )";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_EMP) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_CLI) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_HIS) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_CLI) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_PRV) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TIP_IDE) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IDE) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NOM) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIR) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FAX) + "',";                    
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_CAS) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIR_WEB) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COR_ELE) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TIP_PER) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_CIU) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_ZON) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PER_CON) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_CON) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COR_ELE_CON) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_VEN) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_GRP) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_FOR_PAG) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MON_CRE) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIA_GRA) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MAX_DES) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_UNO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_DOS) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_REG) + "',";
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ING) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ULT_MOD) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ULT_MOD) ;
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_ING) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_MOD) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_TIP_PER) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_REF_UBI) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MAR_UTI) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_REP) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_CIE_RET_PEN) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIA_GRA_CHQ_FEC) + "',";
                    
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_UNO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_DOS) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_TRE) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_REP_LEG) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IDE_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NOM_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIR_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NAC_PRO) + "',";
                    
                    strFecConEmp=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_CON_EMP);
                    if( (strFecConEmp.equals(null)) || (strFecConEmp.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecConEmp, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TIP_ACT_EMP) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_PRO) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NUM_MAX_VEN_CON) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_VEN) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_INV) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_CXC) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_CXP) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PRO_ACT_DAT) + ",";
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_HIS) + "',";
                    strFecHis=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_HIS);
                    if( (strFecHis.equals(null)) || (strFecHis.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecHis, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_HIS) + ",";
                    strFecUltActDat=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FE_ULT_ACT_DAT);
                    if( (strFecUltActDat.equals(null)) || (strFecUltActDat.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltActDat, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //campos adicionados
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_INF_BUR_CRE) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NUM_MES_PRO_ACT_DAT) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IVA_COM) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IVA_VEN) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PER_ING_NOM_CLI_COT_VEN) + "'";
                    

                    
                    
                    strSQL+=")";
                    System.out.println("SQL INSERT tbh_cli PARA strCodCliUni: " + strSQL);
                    stm.executeUpdate(strSQL);
                            
                            
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        int intMaxCodHisCliPrv=0;
        arlDatTbhCli.clear();
//        System.out.println("DATE CONTIENE EN select_tbhCli: " + datFecAux.toString());
        
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size(); i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                            intMaxCodHisCliPrv=getUltCodigoHistorico(strCodCliUni, conTmpMai);
                            intMaxCodHisCliPrv++;
                            stm=conTmpMai.createStatement();
                            strSQL="";
                            strSQL+="           SELECT co_emp, co_cli, " + intMaxCodHisCliPrv + " AS co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir,";
                            strSQL+="           tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu,";
                            strSQL+="           co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag,";
                            strSQL+="           nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing,";
                            strSQL+="           fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti,";
                            strSQL+="           'I' AS st_regrep, st_cieretpen, ne_diagrachqfec, tx_tel1, tx_tel2, tx_tel3,";
                            strSQL+="           tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro,";
                            strSQL+="           fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven,";
                            strSQL+="           tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat,";
                            strSQL+=" cast('"         + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                            strSQL+=""          + objParSis.getCodigoUsuario() + " AS co_usrhis,";
                            strSQL+="'"         + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), "yyyy-MM-dd") + "' AS fe_ultactdat";
                            //strSQL+="'"          + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), "yyyy-MM-dd") + "'";
                            
                            
                            strSQL+=" , tx_obsInfBurCre, ne_numMesProActDat, st_ivaCom, st_ivaVen, st_perIngNomCliCotVen";
                            strSQL+=" FROM tbm_cli";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_cli=" + strCodCliUni + "";
                            System.out.println("GIGI        SQL SELECT tbh_cli : " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegTbhCli=new ArrayList();
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_EMP, "" + rst.getString("co_emp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_CLI, "" + rst.getString("co_cli"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_HIS, "" + rst.getString("co_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_CLI, "" + rst.getString("st_cli"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_PRV, "" + rst.getString("st_prv"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_IDE, "" + rst.getString("tx_tipide"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IDE, "" + rst.getString("tx_ide"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NOM, "" + rst.getString("tx_nom"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR, "" + rst.getString("tx_dir"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL, "" + rst.getString("tx_tel"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FAX, "" + rst.getString("tx_fax"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_CAS, "" + rst.getString("tx_cas"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR_WEB, "" + rst.getString("tx_dirweb"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COR_ELE, "" + rst.getString("tx_corele"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_PER, "" + rst.getString("tx_tipper"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_CIU, "" + rst.getString("co_ciu"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_ZON, "" + rst.getString("co_zon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PER_CON, "" + rst.getString("tx_percon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_CON, "" + rst.getString("tx_telcon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COR_ELE_CON, "" + rst.getString("tx_corelecon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_VEN, "" + rst.getString("co_ven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_GRP, "" + rst.getString("co_grp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MON_CRE, "" + rst.getString("nd_moncre"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIA_GRA, "" + rst.getString("ne_diagra"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MAX_DES, "" + rst.getString("nd_maxdes"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_DOS, "" + rst.getString("tx_obs2"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_REG, "" + rst.getString("st_reg"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                                
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_ING, "" + rst.getString("co_usring"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_TIP_PER, "" + rst.getString("co_tipper"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_REF_UBI, "" + rst.getString("tx_refubi"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MAR_UTI, "" + rst.getString("nd_maruti"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_REP, "" + rst.getString("st_regrep"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_CIE_RET_PEN, "" + rst.getString("st_cieretpen"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIA_GRA_CHQ_FEC, "" + rst.getString("ne_diagrachqfec"));
                                
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_UNO, "" + rst.getString("tx_tel1"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_DOS, "" + rst.getString("tx_tel2"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_TRE, "" + rst.getString("tx_tel3"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_REP_LEG, "" + rst.getString("tx_repleg"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IDE_PRO, "" + rst.getString("tx_idepro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NOM_PRO, "" + rst.getString("tx_nompro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR_PRO, "" + rst.getString("tx_dirpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_PRO, "" + rst.getString("tx_telpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NAC_PRO, "" + rst.getString("tx_nacpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_CON_EMP, "" + rst.getString("fe_conemp")==""?null:rst.getString("fe_conemp"));
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_ACT_EMP, "" + rst.getString("tx_tipactemp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_PRO, "" + rst.getString("tx_obspro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NUM_MAX_VEN_CON, "" + rst.getString("ne_nummaxvencon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_VEN, "" + rst.getString("tx_obsven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_INV, "" + rst.getString("tx_obsinv"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_CXC, "" + rst.getString("tx_obscxc"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_CXP, "" + rst.getString("tx_obscxp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PRO_ACT_DAT, "" + rst.getString("fe_proactdat"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_HIS, "" + rst.getString("fe_his")==""?null:rst.getString("fe_his"));
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FE_ULT_ACT_DAT, "" + rst.getString("fe_ultactdat"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FE_ULT_ACT_DAT, "" + rst.getString("fe_ultactdat")==""?null:rst.getString("fe_ultactdat"));
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_INF_BUR_CRE, "" + rst.getString("tx_obsInfBurCre"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NUM_MES_PRO_ACT_DAT, "" + rst.getString("ne_numMesProActDat"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IVA_COM, "" + rst.getString("st_ivaCom"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IVA_VEN, "" + rst.getString("st_ivaVen"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PER_ING_NOM_CLI_COT_VEN, "" + rst.getString("st_perIngNomCliCotVen"));
                                arlDatTbhCli.add(arlRegTbhCli);
                            }
                            
                                                        
                            intMaxCodHisCliPrv=getUltCodigoHistorico(strCodCli, conTmpMai);
                            stm=conTmpMai.createStatement();
                            intMaxCodHisCliPrv++;
                            stm=conTmpMai.createStatement();
                            strSQL="";
                            strSQL+="           SELECT co_emp, co_cli, " + intMaxCodHisCliPrv + " AS co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir,";
                            strSQL+="           tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu,";
                            strSQL+="           co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag,";
                            strSQL+="           nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing,";
                            strSQL+="           fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti,";
                            strSQL+="           'I' AS st_regrep, st_cieretpen, ne_diagrachqfec, tx_tel1, tx_tel2, tx_tel3,";
                            strSQL+="           tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro,";
                            strSQL+="           fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven,";
                            strSQL+="           tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat,";
                            strSQL+=" cast('"         + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                            strSQL+=""          + objParSis.getCodigoUsuario() + " AS co_usrhis,";
                            strSQL+="'"         + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), "yyyy-MM-dd") + "' AS fe_ultactdat";
                            //strSQL+="'"          + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), "yyyy-MM-dd") + "'";
                            strSQL+=" , tx_obsInfBurCre, ne_numMesProActDat, st_ivaCom, st_ivaVen, st_perIngNomCliCotVen";
                            strSQL+=" FROM tbm_cli";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_cli=" + strCodCli + "";
//                            System.out.println("SQL SELECT tbh_cli PARA strCodCli: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegTbhCli=new ArrayList();
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_EMP, "" + rst.getString("co_emp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_CLI, "" + rst.getString("co_cli"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_HIS, "" + rst.getString("co_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_CLI, "" + rst.getString("st_cli"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_PRV, "" + rst.getString("st_prv"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_IDE, "" + rst.getString("tx_tipide"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IDE, "" + rst.getString("tx_ide"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NOM, "" + rst.getString("tx_nom"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR, "" + rst.getString("tx_dir"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL, "" + rst.getString("tx_tel"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FAX, "" + rst.getString("tx_fax"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_CAS, "" + rst.getString("tx_cas"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR_WEB, "" + rst.getString("tx_dirweb"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COR_ELE, "" + rst.getString("tx_corele"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_PER, "" + rst.getString("tx_tipper"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_CIU, "" + rst.getString("co_ciu"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_ZON, "" + rst.getString("co_zon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PER_CON, "" + rst.getString("tx_percon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_CON, "" + rst.getString("tx_telcon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COR_ELE_CON, "" + rst.getString("tx_corelecon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_VEN, "" + rst.getString("co_ven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_GRP, "" + rst.getString("co_grp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MON_CRE, "" + rst.getString("nd_moncre"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIA_GRA, "" + rst.getString("ne_diagra"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MAX_DES, "" + rst.getString("nd_maxdes"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_DOS, "" + rst.getString("tx_obs2"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_REG, "" + rst.getString("st_reg"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_ING, "" + rst.getString("co_usring"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_TIP_PER, "" + rst.getString("co_tipper"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_REF_UBI, "" + rst.getString("tx_refubi"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MAR_UTI, "" + rst.getString("nd_maruti"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_REP, "" + rst.getString("st_regrep"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_CIE_RET_PEN, "" + rst.getString("st_cieretpen"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIA_GRA_CHQ_FEC, "" + rst.getString("ne_diagrachqfec"));
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_UNO, "" + rst.getString("tx_tel1"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_DOS, "" + rst.getString("tx_tel2"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_TRE, "" + rst.getString("tx_tel3"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_REP_LEG, "" + rst.getString("tx_repleg"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IDE_PRO, "" + rst.getString("tx_idepro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NOM_PRO, "" + rst.getString("tx_nompro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR_PRO, "" + rst.getString("tx_dirpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_PRO, "" + rst.getString("tx_telpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NAC_PRO, "" + rst.getString("tx_nacpro"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_CON_EMP, "" + rst.getString("fe_conemp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_CON_EMP, "" + rst.getString("fe_conemp")==""?null:rst.getString("fe_conemp"));
                                
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_ACT_EMP, "" + rst.getString("tx_tipactemp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_PRO, "" + rst.getString("tx_obspro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NUM_MAX_VEN_CON, "" + rst.getString("ne_nummaxvencon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_VEN, "" + rst.getString("tx_obsven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_INV, "" + rst.getString("tx_obsinv"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_CXC, "" + rst.getString("tx_obscxc"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_CXP, "" + rst.getString("tx_obscxp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PRO_ACT_DAT, "" + rst.getString("fe_proactdat"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_HIS, "" + rst.getString("fe_his")==""?null:rst.getString("fe_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
//                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FE_ULT_ACT_DAT, "" + rst.getString("fe_ultactdat"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FE_ULT_ACT_DAT, "" + rst.getString("fe_ultactdat")==""?null:rst.getString("fe_ultactdat"));
                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_INF_BUR_CRE, "" + rst.getString("tx_obsInfBurCre"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NUM_MES_PRO_ACT_DAT, "" + rst.getString("ne_numMesProActDat"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IVA_COM, "" + rst.getString("st_ivaCom"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IVA_VEN, "" + rst.getString("st_ivaVen"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PER_ING_NOM_CLI_COT_VEN, "" + rst.getString("st_perIngNomCliCotVen"));
                                
                                arlDatTbhCli.add(arlRegTbhCli);
                            }
                }
                stm.close();
                stm=null;
                
//                System.out.println("DENTRO DE FUNCION arlDatTbhCli: " + arlDatTbhCli.toString());
                
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
    
    private boolean inserta_tbtCli(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecConEmp="";
        String strFecUltActDat="";
        String strFecIng="";
        String strFecUltMod="";
        String strFecHis="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhCli.size(); i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbt_cli(";
                    strSQL+="           co_emp, co_cli, co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir,";
                    strSQL+=" 	    tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu,";
                    strSQL+=" 	    co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag,";
                    strSQL+="       nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing,";
                    strSQL+=" 	    fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti,";
                    strSQL+=" 	    st_regrep, st_cieretpen, ne_diagrachqfec,tx_tel1, tx_tel2, tx_tel3,";
                    strSQL+="           tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro,";
                    strSQL+=" 	    fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven,";
                    strSQL+=" 	    tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat,";
                    strSQL+="           fe_his, co_usrhis, fe_ultactdat";
                    
                    strSQL+=" , tx_obsInfBurCre, ne_numMesProActDat, st_ivaCom, st_ivaVen, st_perIngNomCliCotVen";
                    
                    strSQL+=" )";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_EMP) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_CLI) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_HIS) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_CLI) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_PRV) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TIP_IDE) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IDE) + "',";//tx_ide
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NOM) + "',";//tx_nom
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIR) + "',";//tx_dir
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL) + "',";//tx_tel
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FAX) + "',";//tx_fax
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_CAS) + "',";//tx_cas
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIR_WEB) + "',";//tx_dirweb
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COR_ELE) + "',";//tx_corele
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TIP_PER) + "',";//tx_tipper
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_CIU) + ",";//co_ciu
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_ZON) + ",";//co_zon
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PER_CON) + "',";//tx_percon
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_CON) + "',";//tx_telcon
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COR_ELE_CON) + "',";//tx_corelecon
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_VEN) + ",";//co_ven
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_GRP) + ",";//co_grp
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_FOR_PAG) + ",";//co_forpag
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MON_CRE) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIA_GRA) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MAX_DES) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_UNO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_DOS) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_REG) + "',";
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ING) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ULT_MOD) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ULT_MOD) ;
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_ING) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_MOD) + ",";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_TIP_PER) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_REF_UBI) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MAR_UTI) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_REP) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_CIE_RET_PEN) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIA_GRA_CHQ_FEC) + "',";
                    
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_UNO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_DOS) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_TRE) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_REP_LEG) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IDE_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NOM_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_DIR_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TEL_PRO) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NAC_PRO) + "',";
                    
                    strFecConEmp=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_CON_EMP);
                    if( (strFecConEmp.equals(null)) || (strFecConEmp.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecConEmp, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_TIP_ACT_EMP) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_PRO) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NUM_MAX_VEN_CON) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_VEN) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_INV) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_CXC) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_CXP) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PRO_ACT_DAT) + ",";
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_HIS) + "',";
                    strFecHis=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_HIS);
                    if( (strFecHis.equals(null)) || (strFecHis.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecHis, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_HIS) + ",";
                    strFecUltActDat=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FE_ULT_ACT_DAT);
                    
                    if( (strFecUltActDat.equals(null)) || (strFecUltActDat.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltActDat, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //campos adicionados
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_INF_BUR_CRE) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_NUM_MES_PRO_ACT_DAT) + ",";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IVA_COM) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_IVA_VEN) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PER_ING_NOM_CLI_COT_VEN) + "'";
                    
                    
//                                        strSQL+="           fe_his, co_usrhis, fe_ultactdat";
                    
//                    strSQL+=" , tx_obsInfBurCre, ne_numMesProActDat, st_ivaCom, st_ivaVen, st_perIngNomCliCotVen";
                    
                    
                    strSQL+=")";
                    System.out.println("SQL INSERT tbt_cli: " + strSQL);
                    stm.executeUpdate(strSQL);
                            
                            
                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbmUniCli(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecUni="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbmUniCli.size();i++){
                            strSQL="";
                            strSQL+="INSERT INTO tbm_unicli(";
                            strSQL+=" co_emp, co_uni, co_cli, co_his, co_cliuni, co_hisuni, fe_uni,";
                            strSQL+=" co_usruni)";
                            strSQL+=" VALUES(";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_EMP),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_UNI),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_CLI),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_HIS),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_CLI_UNI),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_HIS_UNI),2) + ",";
                            //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_FEC_UNI),2) + "',";
                            strFecUni=objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_FEC_UNI);
                            if( (strFecUni.equals(null)) || (strFecUni.equals(""))  )
                                strSQL+=" null,";
                            else
                                strSQL+="'" + objUti.formatearFecha(strFecUni, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                            
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_USR_UNI),2) + "";
                            strSQL+=")";
                            System.out.println("SQL INSERTA EN tbm_uniCli: " + strSQL);
                            stm.executeUpdate(strSQL);
                            
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbmUniCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        int intUltReg;
        int intCodHis;
        arlDatTbmUniCli.clear();
        try{
            if( conTmpMai!= null){
                strSQL="";
                strSQL+="SELECT MAX(co_uni)";
                strSQL+=" FROM tbm_uniCli";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                intUltReg=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);
                
                if (intUltReg==-1)
                    return false;
                
                
                stm=conTmpMai.createStatement();
                
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    stm=conTmpMai.createStatement();
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    intUltReg++;
                    strSQL="";
                    strSQL+="SELECT x.co_emp, " + intUltReg + " AS co_uni, x.co_cli, x.co_his, y.co_cliUni, y.co_hisUni ";
                    strSQL+=", cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_uni, " + objParSis.getCodigoUsuario() + " AS co_usruni FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, MAX(co_his) AS co_his, cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'AS timestamp) ," + objParSis.getCodigoUsuario() + "";
                    strSQL+=" 	FROM tbt_cli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND co_cli=" + strCodCli + "";
                    strSQL+="	GROUP BY co_emp, co_cli) AS x";
                    strSQL+=" 	INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli AS co_cliUni, MAX(co_his) AS co_hisUni";
                    strSQL+=" 	FROM tbt_cli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND co_cli=" + strCodCliUni + "";
                    strSQL+=" 	GROUP BY co_emp, co_cli) AS y";
                    strSQL+=" ON x.co_emp=y.co_emp";
//                    System.out.println("SQL SELECT EN tbm_uniCli: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbmUniCli=new ArrayList();
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_COD_UNI, "" + rst.getString("co_uni"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_COD_CLI_UNI, "" + rst.getString("co_cliUni"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_COD_HIS_UNI, "" + rst.getString("co_hisUni"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_FEC_UNI, "" + rst.getString("fe_uni"));
                        arlRegTbmUniCli.add(INT_ARL_TBH_UNI_CLI_USR_UNI, "" + rst.getString("co_usruni"));
                        arlDatTbmUniCli.add(arlRegTbmUniCli);
//                        System.out.println("WHILE    arlDatTbmUniCli: "  + arlDatTbmUniCli.toString());
                    }
                }
                System.out.println("EN FUNCION arlDatTbmUniCli: "  + arlDatTbmUniCli.toString());
                stm.close();
                stm=null;
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

    
    private boolean inserta_tbtUniCli(Connection conTmpMai){
        boolean blnRes=true;
        String strFecUni="";
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTbmUniCli.size();i++){
                            strSQL="";
                            strSQL+="INSERT INTO tbt_unicli(";
                            strSQL+=" co_emp, co_uni, co_cli, co_his, co_cliuni, co_hisuni, fe_uni,";
                            strSQL+=" co_usruni)";
                            strSQL+=" VALUES(";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_EMP),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_UNI),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_CLI),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_HIS),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_CLI_UNI),2) + ",";
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_COD_HIS_UNI),2) + ",";
                            //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_FEC_UNI),2) + "',";
                            strFecUni=objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_FEC_UNI);
                            if( (strFecUni.equals(null)) || (strFecUni.equals(""))  )
                                strSQL+=" null,";
                            else
                                strSQL+="'" + objUti.formatearFecha(strFecUni, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                            
                            strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbmUniCli, i, INT_ARL_TBH_UNI_CLI_USR_UNI),2) + "";
                            strSQL+=")";
                            System.out.println("SQL INSERTA EN tbt_uniCli: " + strSQL);
                            stm.executeUpdate(strSQL);
                            
                }
                stm.close();
                stm=null;
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
    
    
    
    private boolean inserta_tbhBenChq(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                //este proceso se hace dos veces, el primero para el codigo con el q se va a unificar y el
                //otro es para el codigo con el q se desea unificar
                for(int i=0; i<arlDatTbhBenChq.size();i++){
                    
                    strSQL="";
                    strSQL+="INSERT INTO tbh_benchq(";
                    strSQL+=" co_emp, co_cli, co_reg, co_his, tx_benchq,";
                    strSQL+=" st_reg, fe_his, co_usrhis, co_unicli, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_COD_HIS),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_BEN_CHQ),2) + "',";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_EST_REG),2) + "',";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_COD_UNI_CLI),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhBenChq, i, INT_ARL_TBH_BEN_CHQ_EST_REG_REP),2) + "'";
                    strSQL+=")";
                    System.out.println("SQL DE INSERTAR EN tbh_benchq: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
   
    private boolean select_tbhBenChq(Connection conTmpMai){
        boolean blnRes=true;
        arlDatTbhBenChq.clear();
        String strCodCliUni="", strCodCli="";
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                //este proceso se hace dos veces, el primero para el codigo con el q se va a unificar y el
                //otro es para el codigo con el q se desea unificar
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_benchq";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    

                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCliUni + " AS co_his,";
                    strSQL+=" x.tx_benchq, x.st_reg, x.st_regRep, cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, " + "";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis, y.co_uniCli";
                    strSQL+=" FROM(";
                    strSQL+=" SELECT a1.co_emp, a1.co_cli, a1.co_reg, a1.tx_benchq, a1.st_reg, a1.st_regRep";
                    strSQL+=" FROM tbm_benchq AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni AS co_uniCli";
                    strSQL+=" 	FROM tbt_uniCli AS a1";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="       AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
//                    System.out.println("SQL DE INSERTAR PARA strCodCliUni EN tbh_benchq: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhBenChq=new ArrayList();
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_BEN_CHQ, "" + rst.getString("tx_benchq"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_EST_REG, "" + rst.getString("st_reg"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_EST_REG_REP, "" + rst.getString("st_regRep"));
                        arlDatTbhBenChq.add(arlRegTbhBenChq);
                    }
                    
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_benchq";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli=" + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    

                    ////////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCli + " AS co_his,";
                    strSQL+=" x.tx_benchq, x.st_reg, x.st_regRep, cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, " + "";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis, y.co_uniCli";
                    strSQL+=" FROM(";
                    strSQL+=" SELECT a1.co_emp, a1.co_cli, a1.co_reg, a1.tx_benchq, a1.st_reg, a1.st_regRep";
                    strSQL+=" FROM tbm_benchq AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni AS co_uniCli";
                    strSQL+=" 	FROM tbt_uniCli AS a1";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="       AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
//                    System.out.println("SQL DE INSERTAR PARA strCodCli EN tbh_benchq: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhBenChq=new ArrayList();
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_BEN_CHQ, "" + rst.getString("tx_benchq"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_EST_REG, "" + rst.getString("st_reg"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhBenChq.add(INT_ARL_TBH_BEN_CHQ_EST_REG_REP, "" + rst.getString("st_regRep"));
                        arlDatTbhBenChq.add(arlRegTbhBenChq);
                    }
                }
                stm.close();
                stm=null;
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
         
   
    private boolean inserta_tbhDirCli(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecIng="";
        String strFecUltMod="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhDirCli.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_dircli(";
                    strSQL+=" co_emp, co_cli, ne_mod, co_reg, co_his, tx_dir, tx_refubi, tx_tel1,";
                    strSQL+=" tx_tel2, tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring,";
                    strSQL+=" co_usrmod, fe_his, co_usrhis, co_unicli, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_DIR),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_REF_UBI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_TEL_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_TEL_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_TEL_TRE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD),0) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_COD_UNI_CLI),2) + ",";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDirCli, i, INT_ARL_TBH_DIR_CLI_EST_REG_REP),0) + "";
                    strSQL+=" )";
                    System.out.println("SQL DE INSERT tbh_dircli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhDirCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhDirCli.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);

                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_dirCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cli, x.ne_mod, x.co_reg, " + intCodHisCodCliUni + " AS co_his,";
                    strSQL+="        x.tx_dir, x.tx_refubi, x.tx_tel1, x.tx_tel2,";
                    strSQL+="        x.tx_tel3, x.tx_obs1, x.st_reg, x.fe_ing,";
                    strSQL+="        x.fe_ultmod, x.co_usring, x.co_usrmod, x.st_regRep,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                    strSQL+="        , y.co_uni AS co_uniCli";
                    strSQL+="        FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, ne_mod, co_reg, tx_dir, tx_refubi, tx_tel1, tx_tel2,";
                    strSQL+=" 	       tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regRep";
                    strSQL+=" 	       FROM tbm_dircli";
                    strSQL+="                WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="                AND co_cli=" + strCodCliUni +") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
//                    System.out.println("SQL DE INSERT tbh_dircli PARA strCodCliUni: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDirCli=new ArrayList();
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_MOD, "" + rst.getString("ne_mod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_DIR, "" + rst.getString("tx_dir"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_REF_UBI, "" + rst.getString("tx_refubi"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_TEL_UNO, "" + rst.getString("tx_tel1"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_TEL_DOS, "" + rst.getString("tx_tel2"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_TEL_TRE, "" + rst.getString("tx_tel3"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_EST_REG_REP, "" + rst.getString("st_regRep"));
                        
                        
                        arlDatTbhDirCli.add(arlRegTbhDirCli);
                    }                            
                            
                    ///////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_dirCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cli, x.ne_mod, x.co_reg, " + intCodHisCodCli + " AS co_his,";
                    strSQL+="        x.tx_dir, x.tx_refubi, x.tx_tel1, x.tx_tel2,";
                    strSQL+="        x.tx_tel3, x.tx_obs1, x.st_reg, x.fe_ing,";
                    strSQL+="        x.fe_ultmod, x.co_usring, x.co_usrmod, x.st_regRep,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' as timestamp) AS fe_his,";
                    strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                    strSQL+="        , y.co_uni AS co_uniCli";
                    strSQL+="        FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, ne_mod, co_reg, tx_dir, tx_refubi, tx_tel1, tx_tel2,";
                    strSQL+=" 	       tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regRep";
                    strSQL+=" 	       FROM tbm_dircli";
                    strSQL+="                WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="                AND co_cli=" + strCodCli +") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
//                    System.out.println("SQL DE INSERT tbh_dircli PARA strCodCli: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDirCli=new ArrayList();
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_MOD, "" + rst.getString("ne_mod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_DIR, "" + rst.getString("tx_dir"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_REF_UBI, "" + rst.getString("tx_refubi"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_TEL_UNO, "" + rst.getString("tx_tel1"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_TEL_DOS, "" + rst.getString("tx_tel2"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_TEL_TRE, "" + rst.getString("tx_tel3"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        //arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhDirCli.add(INT_ARL_TBH_DIR_CLI_EST_REG_REP, "" + rst.getString("st_regRep"));
                        arlDatTbhDirCli.add(arlRegTbhDirCli);
                    }
                }
                stm.close();
                stm=null;
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
      
    
    private boolean inserta_tbhConCli(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecIng="";
        String strFecUltMod="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhConCli.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_concli(";
                    strSQL+=" co_emp, co_cli, ne_mod, co_reg, co_his, tx_nom, tx_car, tx_tel1,";
                    strSQL+=" tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1, st_reg, fe_ing,";
                    strSQL+="fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli, co_loc, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_NOM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_CAR),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_TEL_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_TEL_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_TEL_TRE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COR_ELE_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COR_ELE_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_ULT_MOD),0) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_UNI_CLI),2) + ",";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_LOC),2) + ",";
                    
                            
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_EST_REG_REP),0) + "";
                    
                    strSQL+=")";
                    System.out.println("SQL DE inserta_tbhConCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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

    private boolean select_tbhConCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhConCli.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_concli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;

                        strSQL="";
                        strSQL+=" SELECT x.co_emp, x.co_cli, x.ne_mod, x.co_reg, " + intCodHisCodCliUni + " AS co_his , x.tx_nom, x.tx_car, x.tx_tel1, x.tx_tel2,";
                        strSQL+="        x.tx_tel3, x.tx_corele1, x.tx_corele2, x.tx_obs1, x.st_reg, x.fe_ing, x.fe_ultmod,";
                        strSQL+="        x.co_usring, x.co_usrmod, x.co_loc, x.st_regRep,";
                        strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' as timestamp) AS fe_his,";
                        strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrhis,";
                        strSQL+="        y.co_uni AS co_uniCli";
                        strSQL+="        FROM(";
                        strSQL+=" SELECT co_emp, co_cli, ne_mod, co_reg, tx_nom, tx_car, tx_tel1, tx_tel2,";
                        strSQL+="        tx_tel3, tx_corele1, tx_corele2, tx_obs1, st_reg, fe_ing, fe_ultmod,";
                        strSQL+="        co_usring, co_usrmod, co_loc, st_regRep";
                        strSQL+="        FROM tbm_concli";
                        strSQL+="        WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="        AND co_cli=" + strCodCliUni + ") AS x";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                        strSQL+=" 	FROM tbt_uniCli";
                        strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                        strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            arlRegTbhConCli=new ArrayList();
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_EMP, "" + rst.getString("co_emp"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_CLI, "" + rst.getString("co_cli"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_MOD, "" + rst.getString("ne_mod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_REG, "" + rst.getString("co_reg"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_HIS, "" + rst.getString("co_his"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_NOM, "" + rst.getString("tx_nom"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_CAR, "" + rst.getString("tx_car"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_UNO, "" + rst.getString("tx_tel1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_DOS, "" + rst.getString("tx_tel2"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_TRE, "" + rst.getString("tx_tel3"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COR_ELE_UNO, "" + rst.getString("tx_corele1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COR_ELE_DOS, "" + rst.getString("tx_corele2"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_EST_REG, "" + rst.getString("st_reg"));
                            //arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));

                            //arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));

                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_ING, "" + rst.getString("co_usring"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                            
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_LOC, "" + rst.getString("co_loc"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_EST_REG_REP, "" + rst.getString("st_regRep"));


                            
                            
                            arlDatTbhConCli.add(arlRegTbhConCli);
                        }
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_concli";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                        if (intCodHisCodCli==-1)
                            return false;
                        
                        intCodHisCodCli++;
                            
                        ///////////////////////////////////////////////////
                        strSQL="";
                        strSQL+=" SELECT x.co_emp, x.co_cli, x.ne_mod, x.co_reg, " + intCodHisCodCli + " AS co_his, x.tx_nom, x.tx_car, x.tx_tel1, x.tx_tel2,";
                        strSQL+="        x.tx_tel3, x.tx_corele1, x.tx_corele2, x.tx_obs1, x.st_reg, x.fe_ing, x.fe_ultmod,";
                        strSQL+="        x.co_usring, x.co_usrmod, x.co_loc, x.st_regRep,";
                        strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                        strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrhis,";
                        strSQL+="        y.co_uni AS co_uniCli";
                        strSQL+="        FROM(";
                        strSQL+=" SELECT co_emp, co_cli, ne_mod, co_reg, tx_nom, tx_car, tx_tel1, tx_tel2,";
                        strSQL+="        tx_tel3, tx_corele1, tx_corele2, tx_obs1, st_reg, fe_ing, fe_ultmod,";
                        strSQL+="        co_usring, co_usrmod, co_loc, st_regRep";
                        strSQL+="        FROM tbm_concli";
                        strSQL+="        WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="        AND co_cli=" + strCodCli + ") AS x";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                        strSQL+=" 	FROM tbt_uniCli";
                        strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                        strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            arlRegTbhConCli=new ArrayList();
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_EMP, "" + rst.getString("co_emp"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_CLI, "" + rst.getString("co_cli"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_MOD, "" + rst.getString("ne_mod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_REG, "" + rst.getString("co_reg"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_HIS, "" + rst.getString("co_his"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_NOM, "" + rst.getString("tx_nom"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_CAR, "" + rst.getString("tx_car"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_UNO, "" + rst.getString("tx_tel1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_DOS, "" + rst.getString("tx_tel2"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_TRE, "" + rst.getString("tx_tel3"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COR_ELE_UNO, "" + rst.getString("tx_corele1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COR_ELE_DOS, "" + rst.getString("tx_corele2"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_EST_REG, "" + rst.getString("st_reg"));
                            //arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                            //arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_ING, "" + rst.getString("co_usring"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_LOC, "" + rst.getString("co_loc"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_EST_REG_REP, "" + rst.getString("st_regRep"));

                            arlDatTbhConCli.add(arlRegTbhConCli);
                        }
                }
                stm.close();
                stm=null;
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

    
    private boolean inserta_tbhForPagCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhForPagCli.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_forpagcli(";
                    strSQL+="             co_emp, co_cli, co_reg, co_his, co_forpag, st_reg, st_regrep,";
                    strSQL+="             fe_his, co_usrhis, co_unicli)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_FOR_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_EST_REG),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_EST_REG_REP),0) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_FE_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhForPagCli, i, INT_ARL_TBH_FOR_PAG_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhForPagCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhForPagCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhForPagCli.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_forpagcli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;

                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCliUni + " AS co_his,";
                    strSQL+=" x.co_forPag, x.st_reg, x.st_regRep,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="" + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_reg, co_forpag, st_reg, st_regrep";
                    strSQL+=" 	FROM tbm_forpagcli";
                    strSQL+="         WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhForPagCli=new ArrayList();
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_EST_REG, "" + rst.getString("st_reg"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_FE_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhForPagCli.add(arlRegTbhForPagCli);
                    }
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_forpagcli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    ///////////////////////////////////////
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cli, x.co_reg, y.co_his,";
                    strSQL+=" x.co_forPag, x.st_reg, x.st_regRep,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="" + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_reg, co_forpag, st_reg, st_regrep";
                    strSQL+=" 	FROM tbm_forpagcli";
                    strSQL+="         WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhForPagCli=new ArrayList();
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_EST_REG, "" + rst.getString("st_reg"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_FE_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhForPagCli.add(INT_ARL_TBH_FOR_PAG_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhForPagCli.add(arlRegTbhForPagCli);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbhObsCli(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecIng="";

        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhObsCli.size();i++){
                   
                    strSQL="";
                    strSQL+="INSERT INTO tbh_obscli(";
                    strSQL+="             co_emp, co_cli, co_reg, co_his, fe_ing,";
                    strSQL+="             tx_obs1, fe_his, co_usrhis, co_unicli, ne_mod,";
                    strSQL+="             co_loc, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comUltMod, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_HIS),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_UNI_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_MOD),2) + ",";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_ULT_MOD),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COM_ING),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COM_ULT_MOD),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_EST_REG_REP),0) + "";
                    
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhObsCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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

    private boolean select_tbhObsCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhObsCli.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_obscli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCliUni + " AS co_his, x.fe_ing, x.tx_obs1,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli, x.ne_mod, x.co_loc, x.fe_ultMod, x.co_usrIng, x.co_usrMod, x.tx_comIng, x.tx_comUltMod, x.st_regRep FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_reg, fe_ing, tx_obs1, ne_mod";
                    strSQL+=" 	,co_loc, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comUltMod, st_regRep";
                    strSQL+=" 	FROM tbm_obscli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhObsCli=new ArrayList();
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_HIS, "" + rst.getString("co_his"));
                        //arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_MOD, "" + rst.getString("ne_mod"));
                        
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultMod"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_ING, "" + rst.getString("co_usrIng"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_MOD, "" + rst.getString("co_usrMod"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COM_ING, "" + rst.getString("tx_comIng"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COM_ULT_MOD, "" + rst.getString("tx_comUltMod"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_EST_REG_REP, "" + rst.getString("st_regRep"));
                        
                        
                        
                        arlDatTbhObsCli.add(arlRegTbhObsCli);
                    }    
                    //////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_obscli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCli + " AS co_his , x.fe_ing, x.tx_obs1,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli, x.ne_mod, x.co_loc, x.fe_ultMod, x.co_usrIng, x.co_usrMod, x.tx_comIng, x.tx_comUltMod, x.st_regRep FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_reg, fe_ing, tx_obs1, ne_mod";
                    strSQL+=" 	,co_loc, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comUltMod, st_regRep";
                    strSQL+=" 	FROM tbm_obscli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhObsCli=new ArrayList();
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_HIS, "" + rst.getString("co_his"));
                        //arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_MOD, "" + rst.getString("ne_mod"));
                        
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultMod"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_ING, "" + rst.getString("co_usrIng"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_MOD, "" + rst.getString("co_usrMod"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COM_ING, "" + rst.getString("tx_comIng"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COM_ULT_MOD, "" + rst.getString("tx_comUltMod"));
                        arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_EST_REG_REP, "" + rst.getString("st_regRep"));

                        arlDatTbhObsCli.add(arlRegTbhObsCli);
                    }    
                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbhAccEmpCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhAccEmpCli.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_accempcli(";
                    strSQL+="             co_emp, co_cli, co_reg, co_his,";
                    strSQL+="             tx_nom, fe_his, co_usrhis, co_unicli)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_NOM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhAccEmpCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhAccEmpCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhAccEmpCli.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_accempcli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCliUni + " AS co_his , x.tx_nom,";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="" + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_reg, tx_nom";
                    strSQL+=" 	FROM tbm_accempcli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhAccEmpCli=new ArrayList();
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhAccEmpCli.add(arlRegTbhAccEmpCli);
                    }
                            
                    //////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_accempcli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_cli, x.co_reg, " + intCodHisCodCli + " AS co_his , x.tx_nom,";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="" + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT co_emp, co_cli, co_reg, tx_nom";
                    strSQL+=" 	FROM tbm_accempcli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhAccEmpCli=new ArrayList();
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhAccEmpCli.add(arlRegTbhAccEmpCli);
                    }

                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbhSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecSol="";
        String strFecAnaSol="";
        String strFecIng="";
        String strFecUltMod="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhSolCre.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_solcre(";
                    strSQL+="             co_emp, co_sol, co_his, co_cli, fe_sol, co_forpag, nd_moncre,";
                    strSQL+="             st_anasol, fe_anasol, co_usranasol, tx_obs1, tx_obs2, st_reg,";
                    strSQL+="             fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli)";
                    strSQL+="     VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_CLI),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_SOL),0) + "',";
                    strFecSol=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_SOL);
                    if( (strFecSol.equals(null)) || (strFecSol.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecSol, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_FOR_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_MON_CRE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_EST_ANA_SOL),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL),2) + "',";
                    strFecAnaSol=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL);
                    if( (strFecAnaSol.equals(null)) || (strFecAnaSol.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecAnaSol, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USU_ANA_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_OBS_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD),0) + ",";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("SQL DE inserta_tbhSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                    
                        
                            
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhSolCre.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_solcre";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;

                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_sol, " + intCodHisCodCliUni + " AS co_his , x.co_cli, x.fe_sol, x.co_forPag,";
                    strSQL+=" 	x.nd_monCre, x.st_anaSol, x.fe_anaSol, x.co_usrAnaSol, x.tx_obs1, x.tx_obs2, x.st_reg,";
                    strSQL+=" 	x.fe_ing, x.fe_ultMod, x.co_usrIng, x.co_usrMod,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol,";
                    strSQL+="         fe_anasol, co_usranasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod,";
                    strSQL+="         co_usring, co_usrmod";
                    strSQL+=" 	FROM tbm_solcre";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhSolCre=new ArrayList();
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_CLI, "" + rst.getString("co_cli"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_SOL, "" + rst.getString("fe_sol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_SOL, "" + rst.getString("fe_sol")==""?null:rst.getString("fe_sol"));
                        
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_MON_CRE, "" + rst.getString("nd_moncre"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_EST_ANA_SOL, "" + rst.getString("st_anasol"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL, "" + rst.getString("fe_anasol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL, "" + rst.getString("fe_anasol")==""?null:rst.getString("fe_anasol"));
                        
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USU_ANA_SOL, "" + rst.getString("co_usranasol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhSolCre.add(arlRegTbhSolCre);
                    }

                    ////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(co_his)";
                    strSQL+=" FROM tbh_solcre";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_sol, " + intCodHisCodCli + " AS co_his, x.co_cli, x.fe_sol, x.co_forPag,";
                    strSQL+=" 	x.nd_monCre, x.st_anaSol, x.fe_anaSol, x.co_usrAnaSol, x.tx_obs1, x.tx_obs2, x.st_reg,";
                    strSQL+=" 	x.fe_ing, x.fe_ultMod, x.co_usrIng, x.co_usrMod,";
                    strSQL+="        cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="        " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT co_emp, co_sol, co_cli, fe_sol, co_forpag, nd_moncre, st_anasol,";
                    strSQL+="         fe_anasol, co_usranasol, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod,";
                    strSQL+="         co_usring, co_usrmod";
                    strSQL+=" 	FROM tbm_solcre";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhSolCre=new ArrayList();
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_CLI, "" + rst.getString("co_cli"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_SOL, "" + rst.getString("fe_sol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_SOL, "" + rst.getString("fe_sol")==""?null:rst.getString("fe_sol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_MON_CRE, "" + rst.getString("nd_moncre"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_EST_ANA_SOL, "" + rst.getString("st_anasol"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL, "" + rst.getString("fe_anasol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL, "" + rst.getString("fe_anasol")==""?null:rst.getString("fe_anasol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USU_ANA_SOL, "" + rst.getString("co_usranasol"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        //arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhSolCre.add(arlRegTbhSolCre);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbhCabCotVen(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecCot="";
        String strFecIng="";
        String strFecUltMod="";
        String strFecHis="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhCabCotVen.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cabcotven(";
                    strSQL+="             co_emp, co_loc, co_cot, co_his, fe_cot, co_cli, co_ven, tx_ate,";
                    strSQL+="             tx_numped, co_forpag, nd_sub, nd_poriva, nd_valiva, nd_valdes,";
                    strSQL+="             nd_tot, ne_val, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod,";
                    strSQL+="             co_usring, co_usrmod, tx_obssolaut, tx_obsautsol, st_aut,";
                    strSQL+="             fe_his, co_usrhis, co_unicli, tx_nomCli, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_HIS),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_COT),0) + "',";
                    strFecCot=objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_COT);
                    if( (strFecCot.equals(null)) || (strFecCot.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecCot, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_VEN),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_ATE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_NUM_PED),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_FOR_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_SUB),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_POR_IVA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_VAL_IVA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_VAL_DES),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_TOT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_VAL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_OBS_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD),0) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_OBS_SOL_AUT),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_OBS_AUT_SOL),0) + ",";
                    String strAut=objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_AUT);
                    if( (strAut.equals(null)) || (strAut.equals("null")) || (strAut.equals("Null"))   )
                        strAut="";
                        
                    strSQL+="'" + strAut + "',";
                    strFecHis=objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_FEC_HIS);
                    if( (strFecHis.equals(null)) || (strFecHis.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecHis, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_COD_UNI_CLI),2) + ",";

                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_NOM_CLI),1) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, i, INT_ARL_TBH_CAB_COT_VEN_EST_REG_REP),0) + "";
                    
                    
                    strSQL+=")";
                    System.out.println("SQL inserta_tbhCabCotVen " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhCabCotVen(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhCabCotVen.clear();
        String strAuxCodHis="";
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                            strSQL="";
                            strSQL+=" SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his,";
                            strSQL+="         x.fe_cot, x.co_cli, x.co_ven, x.tx_ate,";
                            strSQL+=" 	x.tx_numped, x.co_forpag, x.nd_sub, x.nd_poriva, x.nd_valiva, x.nd_valdes,";
                            strSQL+=" 	x.nd_tot, x.ne_val, x.tx_obs1, x.tx_obs2, x.st_reg, x.fe_ing, x.fe_ultmod,";
                            strSQL+=" 	x.co_usring, x.co_usrmod, x.tx_obssolaut, x.tx_obsautsol, x.st_aut, ";
                            strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                            strSQL+="         " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                            strSQL+=" 	y.co_uni AS co_uniCli, x.tx_nomCli, x.st_regRep FROM(";
                            strSQL+=" 	SELECT co_emp, co_loc, co_cot, fe_cot, co_cli, co_ven, tx_ate, co_forpag,";
                            strSQL+="         nd_sub, nd_poriva, nd_valdes, tx_obs1, tx_obs2, st_reg, fe_ing,";
                            strSQL+="         fe_ultmod, co_usring, co_usrmod, nd_tot, ne_val, nd_valiva, tx_numped,";
                            strSQL+="         st_regrep, tx_obssolaut, tx_obsautsol, st_aut, tx_nomCli";
                            strSQL+=" 	FROM tbm_cabcotven";
                            strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="         AND co_cli=" + strCodCliUni + ") AS x";
                            strSQL+=" INNER JOIN (";
                            strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                            strSQL+=" 	FROM tbt_uniCli";
                            strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                            strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                            System.out.println("SQL DE select_tbhCabCotVen  --1--:  " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegTbhCabCotVen=new ArrayList();
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_COT, "" + rst.getString("co_cot"));
                                
                                strAuxCodHis="";
                                strAuxCodHis+="SELECT MAX(co_his)";
                                strAuxCodHis+=" FROM tbh_cabcotven";
                                strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                                strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                                strAuxCodHis+=" AND co_cot= " + rst.getString("co_cot") + "";
                                intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                                if (intCodHisCodCliUni==-1)
                                    return false;

                                intCodHisCodCliUni++;
                                
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_HIS, "" + rst.getString("co_his"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_HIS, "" + intCodHisCodCliUni);
                                
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_COT, "" + rst.getString("fe_cot"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_COT, "" + rst.getString("fe_cot")==""?null:rst.getString("fe_cot"));
                                
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_CLI, "" + rst.getString("co_cli"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_VEN, "" + rst.getString("co_ven"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_ATE, "" + rst.getString("tx_ate"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_NUM_PED, "" + rst.getString("tx_numped"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_SUB, "" + rst.getString("nd_sub"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_POR_IVA, "" + rst.getString("nd_poriva"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_VAL_IVA, "" + rst.getString("nd_valiva"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_VAL_DES, "" + rst.getString("nd_valdes"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_TOT, "" + rst.getString("nd_tot"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_VAL, "" + rst.getString("ne_val"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_UNO, "" + rst.getString("tx_obs1"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_DOS, "" + rst.getString("tx_obs2"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_EST_REG, "" + rst.getString("st_reg"));
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ING, "" + rst.getString("fe_ing"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                                
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                                
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_USR_ING, "" + rst.getString("co_usring"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_AUT, "" + rst.getString("st_aut"));
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_HIS, "" + rst.getString("fe_his"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_HIS, "" + rst.getString("fe_his")==""?null:rst.getString("fe_his"));
                                
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                                
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_NOM_CLI, "" + rst.getString("tx_nomCli"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_EST_REG_REP, "" + rst.getString("st_regRep"));
                            
                                
                                arlDatTbhCabCotVen.add(arlRegTbhCabCotVen);
                            }
                            
                            
                            ////////////////////////////////////////////////////
                            strSQL="";
                            strSQL+=" SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his,";
                            strSQL+="         x.fe_cot, x.co_cli, x.co_ven, x.tx_ate,";
                            strSQL+=" 	x.tx_numped, x.co_forpag, x.nd_sub, x.nd_poriva, x.nd_valiva, x.nd_valdes,";
                            strSQL+=" 	x.nd_tot, x.ne_val, x.tx_obs1, x.tx_obs2, x.st_reg, x.fe_ing, x.fe_ultmod,";
                            strSQL+=" 	x.co_usring, x.co_usrmod, x.tx_obssolaut, x.tx_obsautsol, x.st_aut, ";
                            strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                            strSQL+="         " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                            strSQL+=" 	y.co_uni AS co_uniCli, x.tx_nomCli, x.st_regRep FROM(";
                            strSQL+=" 	SELECT co_emp, co_loc, co_cot, fe_cot, co_cli, co_ven, tx_ate, co_forpag,";
                            strSQL+="         nd_sub, nd_poriva, nd_valdes, tx_obs1, tx_obs2, st_reg, fe_ing,";
                            strSQL+="         fe_ultmod, co_usring, co_usrmod, nd_tot, ne_val, nd_valiva, tx_numped,";
                            strSQL+="         st_regrep, tx_obssolaut, tx_obsautsol, st_aut, tx_nomCli";
                            strSQL+=" 	FROM tbm_cabcotven";
                            strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="         AND co_cli=" + strCodCli + ") AS x";
                            strSQL+=" INNER JOIN (";
                            strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                            strSQL+=" 	FROM tbt_uniCli";
                            strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                            strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                            System.out.println("SQL DE select_tbhCabCotVen  --2--:  " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegTbhCabCotVen=new ArrayList();
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_COT, "" + rst.getString("co_cot"));
                                
                                strAuxCodHis="";
                                strAuxCodHis+="SELECT MAX(co_his)";
                                strAuxCodHis+=" FROM tbh_cabcotven";
                                strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                                strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                                strAuxCodHis+=" AND co_cot= " + rst.getString("co_cot") + "";
                                intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                                if (intCodHisCodCli==-1)
                                    return false;
                                
                                
                                intCodHisCodCli++;
                                
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_HIS, "" + rst.getString("co_his"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_HIS, "" + intCodHisCodCli);
                                
                                
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_COT, "" + rst.getString("fe_cot"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_COT, "" + rst.getString("fe_cot")==""?null:rst.getString("fe_cot"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_CLI, "" + rst.getString("co_cli"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_VEN, "" + rst.getString("co_ven"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_ATE, "" + rst.getString("tx_ate"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_NUM_PED, "" + rst.getString("tx_numped"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_SUB, "" + rst.getString("nd_sub"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_POR_IVA, "" + rst.getString("nd_poriva"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_VAL_IVA, "" + rst.getString("nd_valiva"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_VAL_DES, "" + rst.getString("nd_valdes"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_TOT, "" + rst.getString("nd_tot"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_VAL, "" + rst.getString("ne_val"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_UNO, "" + rst.getString("tx_obs1"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_DOS, "" + rst.getString("tx_obs2"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_EST_REG, "" + rst.getString("st_reg"));
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ING, "" + rst.getString("fe_ing"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_USR_ING, "" + rst.getString("co_usring"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_AUT, "" + rst.getString("st_aut"));
                                //arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_HIS, "" + rst.getString("fe_his"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_FEC_HIS, "" + rst.getString("fe_his")==""?null:rst.getString("fe_his"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_NOM_CLI, "" + rst.getString("tx_nomCli"));
                                arlRegTbhCabCotVen.add(INT_ARL_TBH_CAB_COT_VEN_EST_REG_REP, "" + rst.getString("st_regRep"));
                                arlDatTbhCabCotVen.add(arlRegTbhCabCotVen);
                            }
                }
                stm.close();
                stm=null;
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
    

    private boolean inserta_tbhDetCotVen(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhDetCotVen.size();i++){
                    strSQL="";
                    strSQL+=" INSERT INTO tbh_detcotven(";
                    strSQL+="             co_emp, co_loc, co_cot, co_his, co_reg, co_itm, tx_codalt, tx_codalt2,";
                    strSQL+="             tx_nomitm, co_bod, nd_can, nd_preuni, nd_pordes, st_ivaven, nd_precom,";
                    strSQL+="             nd_pordesprecom, co_prv, fe_his, co_usrhis, co_unicli, st_traAutTot, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_ITM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_ALT),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_ALT_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_NOM_ITM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_BOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_CAN),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_PRE_UNI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_POR_DES),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_IVA_VEN),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_PRE_COM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_POR_DES_PRE_COM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_PRV),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_COD_UNI_CLI),2) + ",";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_EST_TRA_AUT_TOT),0) + ",";
                    if( ! objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_EST_REG_REP).equals(""))
                        strSQL+="'" + objUti.getStringValueAt(arlDatTbhDetCotVen, i, INT_ARL_TBH_DET_COT_VEN_EST_REG_REP) + "'";
                    else
                        strSQL+="Null";
                    
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhDetCotVen: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhDetCotVen(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhDetCotVen.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                   
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.co_itm, x.tx_codAlt, x.tx_codAlt2,";
                    strSQL+=" 	x.tx_nomItm, x.co_bod, x.nd_can, x.nd_preUni, x.nd_porDes, x.st_ivaVen,";
                    strSQL+=" 	x.nd_preCom, x.nd_porDesPreCom, x.co_prv,";
                    strSQL+="         cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="         " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli, x.st_traAutTot, x.st_regRep FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm,";
                    strSQL+=" 	b.co_bod, b.nd_can, b.nd_preuni, b.nd_pordes, b.st_ivaven, b.nd_precom, b.co_prv,";
                    strSQL+=" 	b.tx_codalt2, b.st_regrep, b.nd_pordesprecom, a.co_cli, b.st_traAutTot";
                    strSQL+=" 	FROM tbm_cabCotVen  AS a INNER JOIN tbm_detCotVen AS b";
                    strSQL+=" 	ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+=" 	WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND a.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    
                    System.out.println("SQL DE select_tbhDetCotVen  --1--:  " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetCotVen=new ArrayList();
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_COT, "" + rst.getString("co_cot"));
                        
                        if(arlDatTbhCabCotVen.size()>0){
                            for(int f=0;f<arlDatTbhCabCotVen.size();f++){
                                
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_COT),2))  ){
                                            arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_HIS),2));
                                        }

                                    }
                                }
                            }
                        }
                        
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_ITM, "" + rst.getString("co_itm"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_ALT, "" + rst.getString("tx_codalt"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_ALT_DOS, "" + rst.getString("tx_codalt2"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_NOM_ITM, "" + rst.getString("tx_nomitm"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_BOD, "" + rst.getString("co_bod"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_CAN, "" + rst.getString("nd_can"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_PRE_UNI, "" + rst.getString("nd_preuni"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_POR_DES, "" + rst.getString("nd_pordes"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_IVA_VEN, "" + rst.getString("st_ivaven"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_PRE_COM, "" + rst.getString("nd_precom"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_POR_DES_PRE_COM, "" + rst.getString("nd_pordesprecom"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_PRV, "" + rst.getString("co_prv"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_EST_TRA_AUT_TOT, "" + rst.getString("st_traAutTot"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_EST_REG_REP, "" + rst.getString("st_regRep")==null?"":rst.getString("st_regRep"));
                        
                        arlDatTbhDetCotVen.add(arlRegTbhDetCotVen);
                    }
                            
                    /////////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.co_itm, x.tx_codAlt, x.tx_codAlt2,";
                    strSQL+=" 	x.tx_nomItm, x.co_bod, x.nd_can, x.nd_preUni, x.nd_porDes, x.st_ivaVen,";
                    strSQL+=" 	x.nd_preCom, x.nd_porDesPreCom, x.co_prv,";
                    strSQL+="         cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="         " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	y.co_uni AS co_uniCli, x.st_traAutTot, x.st_regRep FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm,";
                    strSQL+=" 	b.co_bod, b.nd_can, b.nd_preuni, b.nd_pordes, b.st_ivaven, b.nd_precom, b.co_prv,";
                    strSQL+=" 	b.tx_codalt2, b.st_regrep, b.nd_pordesprecom, a.co_cli, b.st_traAutTot";
                    strSQL+=" 	FROM tbm_cabCotVen  AS a INNER JOIN tbm_detCotVen AS b";
                    strSQL+=" 	ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+=" 	WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND a.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" 	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 	FROM tbt_uniCli";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    System.out.println("SQL DE select_tbhDetCotVen  --2--:  " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetCotVen=new ArrayList();
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabCotVen.size()>0){
                            for(int f=0;f<arlDatTbhCabCotVen.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_COT),2))  ){
                                            arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_HIS),2));
                                        }

                                    }
                                }
                            }
                        }
                        
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_ITM, "" + rst.getString("co_itm"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_ALT, "" + rst.getString("tx_codalt"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_ALT_DOS, "" + rst.getString("tx_codalt2"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_NOM_ITM, "" + rst.getString("tx_nomitm"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_BOD, "" + rst.getString("co_bod"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_CAN, "" + rst.getString("nd_can"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_PRE_UNI, "" + rst.getString("nd_preuni"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_POR_DES, "" + rst.getString("nd_pordes"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_IVA_VEN, "" + rst.getString("st_ivaven"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_PRE_COM, "" + rst.getString("nd_precom"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_POR_DES_PRE_COM, "" + rst.getString("nd_pordesprecom"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_PRV, "" + rst.getString("co_prv"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_EST_TRA_AUT_TOT, "" + rst.getString("st_traAutTot"));
                        arlRegTbhDetCotVen.add(INT_ARL_TBH_DET_COT_VEN_EST_REG_REP, "" + rst.getString("st_regRep")==null?"":rst.getString("st_regRep"));
                        arlDatTbhDetCotVen.add(arlRegTbhDetCotVen);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbhPagCotVen(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecVen="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhPagCotVen.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_pagcotven(";
                    strSQL+=" co_emp, co_loc, co_cot, co_his, co_reg, ne_diacre, fe_ven, nd_porret,";
                    strSQL+=" mo_pag, ne_diagra, fe_his, co_usrhis, co_unicli, co_tipRet, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_DIA_CRE),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_FEC_VEN),0) + "',";
                    strFecVen=objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_FEC_VEN);
                    if( (strFecVen.equals(null)) || (strFecVen.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecVen, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_POR_RET),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_MON_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_DIA_GRA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_UNI_CLI),2) + ",";

                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_COD_TIP_RET),2) + ",";
                    
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_EST_REG_REP),0) + "";
                    
                    
                    if( ! objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_EST_REG_REP).equals(""))
                        strSQL+="'" + objUti.getStringValueAt(arlDatTbhPagCotVen, i, INT_ARL_TBH_PAG_COT_VEN_EST_REG_REP) + "'";
                    else
                        strSQL+="Null";
                    
                    
                    
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhPagCotVen: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
        
    private boolean select_tbhPagCotVen(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhPagCotVen.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.ne_diacre, x.fe_ven, x.nd_porret,";
                    strSQL+=" x.mo_pag, x.ne_diagra,";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" y.co_uni AS co_uniCli, x.co_tipRet, x.st_regRep FROM(";
                    strSQL+=" SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.ne_diacre, b.fe_ven, b.nd_porret,";
                    strSQL+=" b.mo_pag, b.ne_diagra, b.st_regrep, b.co_tipret, a.co_cli";
                    strSQL+=" FROM tbm_cabCotVen  AS a INNER JOIN tbm_pagCotVen AS b";
                    strSQL+=" ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a.co_cli=" + strCodCliUni +") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+="  	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+="  	FROM tbt_uniCli";
                    strSQL+="  	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhPagCotVen=new ArrayList();
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_COT, "" + rst.getString("co_cot"));
                        
                        if(arlDatTbhCabCotVen.size()>0){
                            for(int f=0;f<arlDatTbhCabCotVen.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_COT),2))  ){
                                            arlRegTbhPagCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_HIS),2));
                                        }
//                                        else{
//                                            arlRegTbhPagCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS,  "");
//                                            break;
//                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        //arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_DIA_CRE, "" + rst.getString("ne_diacre"));
                        //arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_FEC_VEN, "" + rst.getString("fe_ven")==""?null:rst.getString("fe_ven"));
                        
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_POR_RET, "" + rst.getString("nd_porret"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_MON_PAG, "" + rst.getString("mo_pag"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_DIA_GRA, "" + rst.getString("ne_diagra"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_TIP_RET, "" + rst.getString("co_tipRet"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_EST_REG_REP, "" + rst.getString("st_regRep")==null?"":rst.getString("st_regRep"));
                        
                        arlDatTbhPagCotVen.add(arlRegTbhPagCotVen);
                    }
                            
                            
                            
                    ////////////////////////////////////////////////////////////
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.ne_diacre, x.fe_ven, x.nd_porret,";
                    strSQL+=" x.mo_pag, x.ne_diagra,";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" y.co_uni AS co_uniCli, x.co_tipRet, x.st_regRep FROM(";
                    strSQL+=" SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.ne_diacre, b.fe_ven, b.nd_porret,";
                    strSQL+=" b.mo_pag, b.ne_diagra, b.st_regrep, b.co_tipret, a.co_cli";
                    strSQL+=" FROM tbm_cabCotVen  AS a INNER JOIN tbm_pagCotVen AS b";
                    strSQL+=" ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a.co_cli=" + strCodCli +") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+="  	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+="  	FROM tbt_uniCli";
                    strSQL+="  	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhPagCotVen=new ArrayList();
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabCotVen.size()>0){
                            for(int f=0;f<arlDatTbhCabCotVen.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_COT),2))  ){
                                            arlRegTbhPagCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotVen, f, INT_ARL_TBH_CAB_COT_VEN_COD_HIS),2));
                                        }
//                                        else{
//                                            arlRegTbhPagCotVen.add(INT_ARL_TBH_DET_COT_VEN_COD_HIS,  "");
//                                            break;
//                                        }
                                    }
                                }
                            }
                        }
                        
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_DIA_CRE, "" + rst.getString("ne_diacre"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_POR_RET, "" + rst.getString("nd_porret"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_MON_PAG, "" + rst.getString("mo_pag"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_DIA_GRA, "" + rst.getString("ne_diagra"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_COD_TIP_RET, "" + rst.getString("co_tipRet"));
                        arlRegTbhPagCotVen.add(INT_ARL_TBH_PAG_COT_VEN_EST_REG_REP, "" + rst.getString("st_regRep")==null?"":rst.getString("st_regRep"));
                        arlDatTbhPagCotVen.add(arlRegTbhPagCotVen);
                    }
                }
                stm.close();
                stm=null;
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
      
    
    private boolean inserta_tbhCabCotCom(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecCot="";
        String strFecIng="";
        String strFecUltMod="";
        String strEstAut="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhCabCotCom.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cabcotcom(";
                    strSQL+=" co_emp, co_loc, co_cot, co_his, fe_cot, co_prv, co_com, tx_ate,";
                    strSQL+=" co_forpag, nd_sub, nd_poriva, nd_valiva, nd_valdes, nd_tot, tx_obs1,";
                    strSQL+=" tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_obssolaut,";
                    strSQL+=" tx_obsautsol, st_aut, fe_his, co_usrhis, co_unicli,";
                    strSQL+=" st_regrep)";
                    strSQL+=" VALUES(";
//                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_PAG_COT_VEN_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_HIS),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_COT),0) + "',";
                    strFecCot=objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_COT);
                    if( (strFecCot.equals(null)) || (strFecCot.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecCot, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_PRV),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_COM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_ATE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_FOR_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_SUB),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_POR_IVA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_VAL_IVA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_VAL_DES),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_TOT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_OBS_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD),0) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_OBS_SOL_AUT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_OBS_AUT_SOL),2) + ",";
                    
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_AUT),0) + ",";
                    strEstAut=objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_AUT);
                    if( (strEstAut.equals(null)) || (strEstAut.equals("")) || (strEstAut.equals("null"))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + strEstAut + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_COD_UNI_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, i, INT_ARL_TBH_CAB_COT_COM_EST_REG_REP),0) + "";
                    strSQL+=")";
                    System.out.println("SQL DE INSERTAR EN tbh_cabcotcom :" + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhCabCotCom(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhCabCotCom.clear();
        String strAuxCodHis="";
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.fe_cot, x.co_prv, x.co_com, x.tx_ate, x.co_forpag,";
                    strSQL+=" x.nd_sub, x.nd_poriva, x.nd_valiva, x.nd_valdes, x.nd_tot, x.tx_obs1, x.tx_obs2, x.st_reg, x.fe_ing,";
                    strSQL+=" x.fe_ultmod, x.co_usring, x.co_usrmod, x.tx_obssolaut,";
                    strSQL+=" x.tx_obsautsol,";
                    strSQL+=" x.st_aut, ";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" y.co_uni AS co_uniCli, x.st_regRep FROM(";
                    strSQL+="       SELECT b.co_emp, b.co_loc, b.co_cot, b.fe_cot, b.co_prv, b.co_com, b.tx_ate, b.co_forpag,";
                    strSQL+="       b.nd_sub, b.nd_poriva, b.nd_valdes, b.tx_obs1, b.tx_obs2, b.st_reg, b.fe_ing,";
                    strSQL+="       b.fe_ultmod, b.co_usring, b.co_usrmod, b.nd_tot, b.nd_valiva, b.st_regrep, b.tx_obsautsol,";
                    strSQL+="       b.tx_obssolaut, b.st_aut";
                    strSQL+="       FROM tbm_cabcotcom AS b";
                    strSQL+="       WHERE b.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="       AND b.co_prv=" + strCodCliUni + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+="  	SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+="  	FROM tbt_uniCli";
                    strSQL+="  	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="          AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+="  ON x.co_emp=y.co_emp AND x.co_prv=y.co_cliUni";
//                    System.out.println("SQL DE INSERTAR EN tbh_cabcotcom PARA strCodCliUni:" + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhCabCotCom=new ArrayList();
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_HIS, "" + rst.getString("co_his"));
                        
                        strAuxCodHis="";
                        strAuxCodHis+="SELECT MAX(co_his)";
                        strAuxCodHis+=" FROM tbh_cabcotcom";
                        strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                        strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                        strAuxCodHis+=" AND co_cot= " + rst.getString("co_cot") + "";
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                        if (intCodHisCodCliUni==-1)
                            return false;
                        
                        intCodHisCodCliUni++;
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_HIS, "" + intCodHisCodCliUni);
                        
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_COT, "" + rst.getString("fe_cot"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_COT, "" + rst.getString("fe_cot")==""?null:rst.getString("fe_cot"));
                        
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_PRV, "" + rst.getString("co_prv"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_COM, "" + rst.getString("co_com"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_ATE, "" + rst.getString("tx_ate"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_SUB, "" + rst.getString("nd_sub"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_POR_IVA, "" + rst.getString("nd_poriva"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_VAL_IVA, "" + rst.getString("nd_valiva"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_VAL_DES, "" + rst.getString("nd_valdes"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_TOT, "" + rst.getString("nd_tot"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                        
                        
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_AUT, "" + rst.getString("st_aut"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_AUT, "" + rst.getString("st_aut")==""?null:rst.getString("st_aut"));
                        
                        
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhCabCotCom.add(arlRegTbhCabCotCom);
                    }
                            
                            
                    /////////////////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.fe_cot, x.co_prv, x.co_com, x.tx_ate, x.co_forpag,";
                    strSQL+=" x.nd_sub, x.nd_poriva, x.nd_valiva, x.nd_valdes, x.nd_tot, x.tx_obs1, x.tx_obs2, x.st_reg, x.fe_ing,";
                    strSQL+=" x.fe_ultmod, x.co_usring, x.co_usrmod, x.tx_obssolaut,";
                    strSQL+=" x.tx_obsautsol,";
                    strSQL+=" x.st_aut, ";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" y.co_uni AS co_uniCli, x.st_regRep FROM(";
                    strSQL+="       SELECT b.co_emp, b.co_loc, b.co_cot, b.fe_cot, b.co_prv, b.co_com, b.tx_ate, b.co_forpag,";
                    strSQL+="       b.nd_sub, b.nd_poriva, b.nd_valdes, b.tx_obs1, b.tx_obs2, b.st_reg, b.fe_ing,";
                    strSQL+="       b.fe_ultmod, b.co_usring, b.co_usrmod, b.nd_tot, b.nd_valiva, b.st_regrep, b.tx_obsautsol,";
                    strSQL+="       b.tx_obssolaut, b.st_aut";
                    strSQL+="       FROM tbm_cabcotcom AS b";
                    strSQL+="       WHERE b.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="       AND b.co_prv=" + strCodCli + ") AS x";
                    strSQL+=" INNER JOIN (";
                    strSQL+="  	SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+="  	FROM tbt_uniCli";
                    strSQL+="  	WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="          AND co_cli=" + strCodCli + ") AS y";
                    strSQL+="  ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
//                    System.out.println("SQL DE INSERTAR EN tbh_cabcotcom PARA strCodCli:" + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhCabCotCom=new ArrayList();
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_HIS, "" + rst.getString("co_his"));
                        
                        strAuxCodHis="";
                        strAuxCodHis+="SELECT MAX(co_his)";
                        strAuxCodHis+=" FROM tbh_cabcotcom";
                        strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                        strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                        strAuxCodHis+=" AND co_cot= " + rst.getString("co_cot") + "";
                        intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                        if (intCodHisCodCli==-1)
                            return false;
                        
                        intCodHisCodCli++;
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_HIS, "" + intCodHisCodCli);
                        
                        
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_COT, "" + rst.getString("fe_cot"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_COT, "" + rst.getString("fe_cot")==""?null:rst.getString("fe_cot"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_PRV, "" + rst.getString("co_prv"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_COM, "" + rst.getString("co_com"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_ATE, "" + rst.getString("tx_ate"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_SUB, "" + rst.getString("nd_sub"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_POR_IVA, "" + rst.getString("nd_poriva"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_VAL_IVA, "" + rst.getString("nd_valiva"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_VAL_DES, "" + rst.getString("nd_valdes"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_TOT, "" + rst.getString("nd_tot"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        //arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_AUT, "" + rst.getString("st_aut"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhCabCotCom.add(INT_ARL_TBH_CAB_COT_COM_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhCabCotCom.add(arlRegTbhCabCotCom);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    private boolean inserta_tbhDetCotCom(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhDetCotCom.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_detcotcom(";
                    strSQL+=" co_emp, co_loc, co_cot, co_his, co_reg, co_itm, tx_nomitm, co_bod,";
                    strSQL+=" nd_can, nd_cosuni, nd_pordes, st_ivacom, fe_his, co_usrhis, co_unicli,";
                    strSQL+=" st_regrep)";
                    strSQL+=" VALUES(";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_ITM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_NOM_ITM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_BOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_CAN),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COS_UNI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_POR_DES),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_IVA_COM),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_COD_UNI_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetCotCom, i, INT_ARL_TBH_DET_COT_COM_EST_REP),0) + "";
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhDetCotCom: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
        
    private boolean select_tbhDetCotCom(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhDetCotCom.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.co_itm, x.tx_nomitm, x.co_bod,";
                    strSQL+="             x.nd_can, x.nd_cosuni, x.nd_pordes, x.st_ivacom,";
                    strSQL+="     cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="     " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="     y.co_uni AS co_uniCli, x.st_regRep FROM(";
                    strSQL+="           SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.co_itm, b.tx_nomitm, b.co_bod, b.nd_can,";
                    strSQL+="           b.nd_cosuni, b.nd_pordes, b.st_ivacom, b.st_regrep, a.co_prv";
                    strSQL+="           FROM tbm_cabCotCom  AS a INNER JOIN tbm_detCotCom AS b";
                    strSQL+="           ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+="           WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="           AND a.co_prv=" + strCodCliUni + ") AS x";
                    strSQL+="           INNER JOIN (";
                    strSQL+="               SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+="               FROM tbt_uniCli";
                    strSQL+="               WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="               AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_prv=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetCotCom=new ArrayList();
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabCotCom.size()>0){
                            for(int f=0;f<arlDatTbhCabCotCom.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_COT),2))  ){
                                            arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_HIS),2));
                                        }
//                                        else{
//                                            arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_HIS,  "");
//                                            break;
//                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_ITM, "" + rst.getString("co_itm"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_NOM_ITM, "" + rst.getString("tx_nomitm"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_BOD, "" + rst.getString("co_bod"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_CAN, "" + rst.getString("nd_can"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COS_UNI, "" + rst.getString("nd_cosuni"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_POR_DES, "" + rst.getString("nd_pordes"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_IVA_COM, "" + rst.getString("st_ivacom"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_EST_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhDetCotCom.add(arlRegTbhDetCotCom);
                    }

                    /////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.co_itm, x.tx_nomitm, x.co_bod,";
                    strSQL+="             x.nd_can, x.nd_cosuni, x.nd_pordes, x.st_ivacom,";
                    strSQL+="     cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="     " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="     y.co_uni AS co_uniCli, x.st_regRep FROM(";
                    strSQL+="           SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.co_itm, b.tx_nomitm, b.co_bod, b.nd_can,";
                    strSQL+="           b.nd_cosuni, b.nd_pordes, b.st_ivacom, b.st_regrep, a.co_prv";
                    strSQL+="           FROM tbm_cabCotCom  AS a INNER JOIN tbm_detCotCom AS b";
                    strSQL+="           ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+="           WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="           AND a.co_prv=" + strCodCli + ") AS x";
                    strSQL+="           INNER JOIN (";
                    strSQL+="               SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+="               FROM tbt_uniCli";
                    strSQL+="               WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="               AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetCotCom=new ArrayList();
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabCotCom.size()>0){
                            for(int f=0;f<arlDatTbhCabCotCom.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_COT),2))  ){
                                            arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_HIS),2));
                                        }
//                                        else{
//                                            arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_HIS,  "");
//                                            break;
//                                        }
                                    }
                                }
                            }
                        }
                        
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_ITM, "" + rst.getString("co_itm"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_NOM_ITM, "" + rst.getString("tx_nomitm"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_BOD, "" + rst.getString("co_bod"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_CAN, "" + rst.getString("nd_can"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COS_UNI, "" + rst.getString("nd_cosuni"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_POR_DES, "" + rst.getString("nd_pordes"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_IVA_COM, "" + rst.getString("st_ivacom"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhDetCotCom.add(INT_ARL_TBH_DET_COT_COM_EST_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhDetCotCom.add(arlRegTbhDetCotCom);
                    }
                }
                stm.close();
                stm=null;
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
     
    
    private boolean inserta_tbhPagCotCom(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecVen="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhPagCotCom.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_pagcotcom(";
                    strSQL+=" co_emp, co_loc, co_cot, co_his, co_reg, ne_diacre, fe_ven, mo_pag,";
                    strSQL+=" ne_diagra, fe_his, co_usrhis, co_unicli, st_regrep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_DIA_CRE),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_FEC_VEN),0) + "',";
                    strFecVen=objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_FEC_VEN);
                    if( (strFecVen.equals(null)) || (strFecVen.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecVen, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_MON_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_DIA_GRA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_COD_UNI_CLI),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagCotCom, i, INT_ARL_TBH_PAG_COT_COM_EST_REG_REP),2) + "'";
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhPagCotCom: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
        
    private boolean select_tbhPagCotCom(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhPagCotCom.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                   
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.ne_diacre, x.fe_ven, x.mo_pag,";
                    strSQL+=" x.ne_diagra,";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" y.co_uni AS co_uniCli, x.st_regrep FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.ne_diacre, b.fe_ven, b.mo_pag, b.ne_diagra,";
                    strSQL+=" 	b.st_regrep, a.co_prv";
                    strSQL+=" 	FROM tbm_cabCotCom  AS a INNER JOIN tbm_pagcotcom AS b";
                    strSQL+=" 	ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+=" 	WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 	AND a.co_prv=" + strCodCliUni + ") AS x";
                    strSQL+=" 	INNER JOIN (";
                    strSQL+=" 		SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 		FROM tbt_uniCli";
                    strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_prv=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhPagCotCom=new ArrayList();
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabCotCom.size()>0){
                            for(int f=0;f<arlDatTbhCabCotCom.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_COT),2))  ){
                                            arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_HIS),2));
                                        }
//                                        else{
//                                            arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_HIS,  "");
//                                            break;
//                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_DIA_CRE, "" + rst.getString("ne_diacre"));
                        //arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_FEC_VEN, "" + rst.getString("fe_ven")==""?null:rst.getString("fe_ven"));
                        
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_MON_PAG, "" + rst.getString("mo_pag"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_DIA_GRA, "" + rst.getString("ne_diagra"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhPagCotCom.add(arlRegTbhPagCotCom);
                    }
                            
                    ///////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_loc, x.co_cot, y.co_his, x.co_reg, x.ne_diacre, x.fe_ven, x.mo_pag,";
                    strSQL+=" x.ne_diagra,";
                    strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" y.co_uni AS co_uniCli, x.st_regrep FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_cot, b.co_reg, b.ne_diacre, b.fe_ven, b.mo_pag, b.ne_diagra,";
                    strSQL+=" 	b.st_regrep, a.co_prv";
                    strSQL+=" 	FROM tbm_cabCotCom  AS a INNER JOIN tbm_pagcotcom AS b";
                    strSQL+=" 	ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_cot=b.co_cot";
                    strSQL+=" 	WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 	AND a.co_prv=" + strCodCli + ") AS x";
                    strSQL+=" 	INNER JOIN (";
                    strSQL+=" 		SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 		FROM tbt_uniCli";
                    strSQL+=" 		WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_prv=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhPagCotCom=new ArrayList();
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_COT, "" + rst.getString("co_cot"));
                        //arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabCotCom.size()>0){
                            for(int f=0;f<arlDatTbhCabCotCom.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_LOC),2))  ){
                                        if( rst.getString("co_cot").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_COT),2))  ){
                                            arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabCotCom, f, INT_ARL_TBH_CAB_COT_COM_COD_HIS),2));
                                        }
//                                        else{
//                                            arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_HIS,  "");
//                                            break;
//                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_DIA_CRE, "" + rst.getString("ne_diacre"));
                        //arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_FEC_VEN, "" + rst.getString("fe_ven")==""?null:rst.getString("fe_ven"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_MON_PAG, "" + rst.getString("mo_pag"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_DIA_GRA, "" + rst.getString("ne_diagra"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhPagCotCom.add(INT_ARL_TBH_PAG_COT_COM_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhPagCotCom.add(arlRegTbhPagCotCom);
                    }
                }
                stm.close();
                stm=null;
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
        
    
    private boolean inserta_tbhCabMovInv(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecCad="";
        String strFecCon="";
        String strFecRecCon="";
        
        String strFecDoc="";
        String strFecIng="";
        String strFecUltMod="";
        String strEstAut="";
        String strImp="";
        String strRecDoc="";
        String strEstTipDev="";
        String strEstConInv="";
        String strEstAutIngEgrInvCon="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhCabMovInv.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cabmovinv(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_his, ne_secgrp, ne_secemp,";
                    strSQL+="             ne_numcot, ne_numdoc, tx_secdoc, tx_numped, ne_numgui, tx_numautsri,";
                    strSQL+="             tx_feccad, co_dia, fe_doc, co_cli, tx_ruc, tx_nomcli, tx_dircli,";
                    strSQL+="             tx_telcli, tx_ciucli, co_com, tx_nomven, tx_ate, co_forpag, tx_desforpag,";
                    strSQL+="             nd_sub, nd_poriva, nd_valiva, nd_tot, tx_ptopar, tx_tra, co_mottra,";
                    strSQL+="             tx_desmottra, co_cta, co_motdoc, co_mnu, st_imp, tx_obs1, tx_obs2,";
                    strSQL+="             st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, fe_con, tx_obs3,";
                    strSQL+="             co_forret, tx_vehret, tx_choret, ";
                    strSQL+="             tx_obssolaut, tx_obsautsol, st_aut,";
                    strSQL+="             st_tipdev, st_coninv, st_regrep,";
                    strSQL+="             ne_numdocree, fe_his, co_usrhis, co_unicli,";
                    strSQL+="             fe_ven";
                    strSQL+="             , st_creGuiRem, st_conInvTraAut)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_SEC_GRP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_SEC_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NUM_COT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NUM_DOC),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_SEC_DOC),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NUM_PED),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NUM_GUI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NUM_AUT_SRI),0) + ",";
//                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_CAD),0) + "',";
                    
                    strFecCad=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_CAD);
                    //System.out.println("SE CAE: " + strFecCad);
                    if( (strFecCad.equals(null)) || (strFecCad.equals("")) || (strFecCad.equals("null"))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + strFecCad + "',";
                    //strSQL+="'" + objUti.formatearFecha(strFecCad, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_DIA),2) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_DOC),0) + ",";
                    strFecDoc=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_DOC);
                    if( (strFecDoc.equals(null)) || (strFecDoc.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecDoc, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_RUC),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NOM_CLI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_DIR_CLI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_TEL_CLI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_CIU_CLI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_COM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NOM_VEN),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_ATE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_FOR_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_DES_FOR_PAG),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_SUB),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_POR_IVA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_VAL_IVA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_TOT),2) + ",";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_PTO_PAR),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_TRA),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_MOT_TRA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_DES_MOT_TRA),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_CTA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_MOT_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_MNU),2) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_IMP),0) + ",";
                    strImp=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_IMP);                    
                    
                    if( (strImp.equals(null)) || (strImp.equals("null")) || (strImp.equals("Null"))  )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strImp + "',";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_OBS_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_REG),0) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_ING),0) + ",";
                    strFecIng=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD),0) + ",";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_USR_MOD),2) + ",";
                    
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_CON),0) + ",";
                    strFecCon=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_CON);
                    
                    if( (strFecCon.equals(null)) || (strFecCon.equals(""))  ){
                        strSQL+=" Null,";
                    }
                    else{
                        strSQL+="'" + objUti.formatearFecha(strFecCon, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    }
                                        
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_OBS_TRE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_FOR_RET),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_VEH_RET),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_CHO_RET),0) + ",";
                                                           
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_OBS_SOL_AUT),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_OBS_AUT_SOL),0) + ",";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_AUT),0) + ",";
                    strEstAut=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_AUT);
                    if( (strEstAut.equals(null)) || (strEstAut.equals("Null"))  || (strEstAut.equals("null"))  )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strEstAut + "',";
                    
                   
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV),0) + ",";
                    strEstTipDev=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV);
                    if( (strEstTipDev.equals(null)) || (strEstTipDev.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + strEstTipDev + "',";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV),0) + ",";
                    strEstConInv=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV);
                    if( (strEstConInv.equals(null)) || (strEstConInv.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + strEstConInv + "',";
        
        
                    
                    String strRegRep=objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_REG_REP);
                    if( (strRegRep.equals(null)) || (strRegRep.equals("Null"))   || (strRegRep.equals("null"))  )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strRegRep + "',";
        
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_NUM_DOC_REE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FE_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_COD_CLI_UNI),2) + ",";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_FEC_VEN),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_CRE_GUI_REM),1) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, i, INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV_TRA_AUT),1) + "";
                                        
                    
                    strSQL+=")";
                    System.out.println("SQL DE inserta_tbhCabMovInv: "  + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
        
    private boolean select_tbhCabMovInv(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhCabMovInv.clear();
        String strAuxCodHis="";
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, y.co_his, x.ne_secgrp, x.ne_secemp,";
                    strSQL+="             x.ne_numcot, x.ne_numdoc, x.tx_secdoc, x.tx_numped, x.ne_numgui, x.tx_numautsri,";
                    strSQL+="             x.tx_feccad, x.co_dia, x.fe_doc, x.co_cli, x.tx_ruc, x.tx_nomcli, x.tx_dircli,";
                    strSQL+="             x.tx_telcli, x.tx_ciucli, x.co_com, x.tx_nomven, x.tx_ate, x.co_forpag, x.tx_desforpag,";
                    strSQL+="             x.nd_sub, x.nd_poriva, x.nd_valiva, x.nd_tot, x.tx_ptopar, x.tx_tra, x.co_mottra,";
                    strSQL+="             x.tx_desmottra, x.co_cta, x.co_motdoc, x.co_mnu, x.st_imp, x.tx_obs1, x.tx_obs2,";
                    strSQL+="             x.st_reg, x.fe_ing, x.fe_ultmod, x.co_usring, x.co_usrmod, x.fe_con, x.tx_obs3,";
                    strSQL+="             x.co_forret, x.tx_vehret, x.tx_choret,";
                    strSQL+="             x.tx_obssolaut, x.tx_obsautsol, x.st_aut,";
                    strSQL+="             x.st_tipdev, x.st_coninv, x.st_regrep,";
                    strSQL+="             x.ne_numdocree,";
                    strSQL+="             cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="             " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="             y.co_uni AS co_uniCli ";
                    strSQL+="             , x.fe_ven";
                    strSQL+="             , x.st_creGuiRem, x.st_conInvTraAut  FROM(";
                    strSQL+="                SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numcot,";
                    strSQL+="                a.ne_numdoc, a.ne_numgui, a.fe_doc, a.co_cli, a.tx_ruc, a.tx_nomcli, a.tx_dircli,";
                    strSQL+="                a.tx_telcli, a.tx_ciucli, a.co_com, a.tx_nomven, a.tx_ate, a.co_forpag, a.tx_desforpag,";
                    strSQL+="                a.nd_sub, a.nd_tot, a.nd_poriva, a.tx_ptopar, a.tx_tra, a.co_mottra, a.tx_desmottra,";
                    strSQL+="                a.co_cta, a.co_motdoc, a.tx_obs1, a.tx_obs2, a.st_reg, a.fe_ing, a.fe_ultmod,";
                    strSQL+="                a.co_usring, a.co_usrmod, a.fe_con, a.tx_obs3, a.co_forret, a.tx_numped,";
                    strSQL+="                a.co_dia, a.tx_vehret, a.tx_choret, a.ne_secgrp, a.ne_secemp, a.tx_numautsri,";
                    strSQL+="                a.tx_secdoc, a.tx_feccad, a.nd_valiva, ";
                    strSQL+="                a.co_mnu, a.st_tipdev,";
                    strSQL+="                a.st_regrep, a.st_coninv, a.ne_numdocree, a.st_imp, a.tx_obssolaut,";
                    strSQL+="                a.tx_obsautsol, a.st_aut";
                    strSQL+="                , a.fe_ven";
                    strSQL+="                , a.st_creGuiRem, a.st_conInvTraAut";
                    
                    
                    strSQL+="                FROM tbm_cabmovinv AS a";
                    strSQL+="                WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="                AND a.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+="                 INNER JOIN (";
                    strSQL+="                         SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+="                         FROM tbt_uniCli";
                    strSQL+="                         WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="                         AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+="                 ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
//                    System.out.println("select_tbhCabMovInv CONTIENE:  " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhCabMovInv=new ArrayList();
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_HIS, "" + rst.getString("co_his"));
                        
                        strAuxCodHis="";
                        strAuxCodHis+="SELECT MAX(co_his)";
                        strAuxCodHis+=" FROM tbh_cabmovinv";
                        strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                        strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                        strAuxCodHis+=" AND co_tipdoc= " + rst.getString("co_tipdoc") + "";
                        strAuxCodHis+=" AND co_doc= " + rst.getString("co_doc") + "";
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                        if (intCodHisCodCliUni==-1)
                            return false;
                        
                        intCodHisCodCliUni++;
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_HIS, "" + intCodHisCodCliUni);
                        
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SEC_GRP, "" + rst.getString("ne_secgrp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SEC_EMP, "" + rst.getString("ne_secemp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_COT, "" + rst.getString("ne_numcot"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_DOC, "" + rst.getString("ne_numdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SEC_DOC, "" + rst.getString("tx_secdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_PED, "" + rst.getString("tx_numped"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_GUI, "" + rst.getString("ne_numgui"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_AUT_SRI, "" + rst.getString("tx_numautsri"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CAD, "" + rst.getString("tx_feccad"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CAD, rst.getString("tx_feccad").equals("")?null:rst.getString("tx_feccad"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_DIA, "" + rst.getString("co_dia"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_DOC, "" + rst.getString("fe_doc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_DOC, rst.getString("fe_doc").equals("")?null:rst.getString("fe_doc"));
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_RUC, "" + rst.getString("tx_ruc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NOM_CLI, "" + rst.getString("tx_nomcli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_DIR_CLI, "" + rst.getString("tx_dircli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_TEL_CLI, "" + rst.getString("tx_telcli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_CIU_CLI, "" + rst.getString("tx_ciucli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_COM, "" + rst.getString("co_com"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NOM_VEN, "" + rst.getString("tx_nomven"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_ATE, "" + rst.getString("tx_ate"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_DES_FOR_PAG, "" + rst.getString("tx_desforpag"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SUB, "" + rst.getString("nd_sub"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_POR_IVA, "" + rst.getString("nd_poriva"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_VAL_IVA, "" + rst.getString("nd_valiva"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_TOT, "" + rst.getString("nd_tot"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_PTO_PAR, "" + rst.getString("tx_ptopar"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_TRA, "" + rst.getString("tx_tra"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_MOT_TRA, "" + rst.getString("co_mottra"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_DES_MOT_TRA, "" + rst.getString("tx_desmottra"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_CTA, "" + rst.getString("co_cta"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_MOT_DOC, "" + rst.getString("co_motdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_MNU, "" + rst.getString("co_mnu"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_IMP, "" + rst.getString("st_imp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_IMP, rst.getString("st_imp").equals("")?null:rst.getString("st_imp"));
                        
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ING, rst.getString("fe_ing").equals("")?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD, rst.getString("fe_ultmod").equals("")?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CON, "" + rst.getString("fe_con"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CON, rst.getString("fe_con").equals("")?null:rst.getString("fe_con"));
                        
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_TRE, "" + rst.getString("tx_obs3"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_FOR_RET, "" + rst.getString("co_forret"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_VEH_RET, "" + rst.getString("tx_vehret"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_CHO_RET, "" + rst.getString("tx_choret"));
                        

                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_AUT, "" + rst.getString("st_aut"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_AUT, rst.getString("st_aut").equals("")?null:rst.getString("st_aut"));
                        
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV, "" + rst.getString("st_tipdev"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV, rst.getString("st_tipdev").equals("")?null:rst.getString("st_tipdev"));
                                                
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV, "" + rst.getString("st_coninv"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV, rst.getString("st_coninv").equals("")?null:rst.getString("st_coninv"));
                        

                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_DOC_REE, "" + rst.getString("ne_numdocree"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FE_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_CLI_UNI, "" + rst.getString("co_unicli"));
                        
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CRE_GUI_REM, "" + rst.getString("st_creGuiRem"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV_TRA_AUT, "" + rst.getString("st_conInvTraAut"));
                        
                        
                        arlDatTbhCabMovInv.add(arlRegTbhCabMovInv);
                    }
                            
                                                        
                    /////////////////////////////////////////////
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, y.co_his, x.ne_secgrp, x.ne_secemp,";
                    strSQL+="             x.ne_numcot, x.ne_numdoc, x.tx_secdoc, x.tx_numped, x.ne_numgui, x.tx_numautsri,";
                    strSQL+="             x.tx_feccad, x.co_dia, x.fe_doc, x.co_cli, x.tx_ruc, x.tx_nomcli, x.tx_dircli,";
                    strSQL+="             x.tx_telcli, x.tx_ciucli, x.co_com, x.tx_nomven, x.tx_ate, x.co_forpag, x.tx_desforpag,";
                    strSQL+="             x.nd_sub, x.nd_poriva, x.nd_valiva, x.nd_tot, x.tx_ptopar, x.tx_tra, x.co_mottra,";
                    strSQL+="             x.tx_desmottra, x.co_cta, x.co_motdoc, x.co_mnu, x.st_imp, x.tx_obs1, x.tx_obs2,";
                    strSQL+="             x.st_reg, x.fe_ing, x.fe_ultmod, x.co_usring, x.co_usrmod, x.fe_con, x.tx_obs3,";
                    strSQL+="             x.co_forret, x.tx_vehret, x.tx_choret,";
                    strSQL+="             x.tx_obssolaut, x.tx_obsautsol, x.st_aut,";
                    strSQL+="             x.st_tipdev, x.st_coninv, x.st_regrep,";
                    strSQL+="             x.ne_numdocree,";
                    strSQL+="             cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="             " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="             y.co_uni AS co_uniCli ";
                    strSQL+="             , x.fe_ven";
                    strSQL+="             , x.st_creGuiRem, x.st_conInvTraAut FROM(";
                    
                    strSQL+="                SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numcot,";
                    strSQL+="                a.ne_numdoc, a.ne_numgui, a.fe_doc, a.co_cli, a.tx_ruc, a.tx_nomcli, a.tx_dircli,";
                    strSQL+="                a.tx_telcli, a.tx_ciucli, a.co_com, a.tx_nomven, a.tx_ate, a.co_forpag, a.tx_desforpag,";
                    strSQL+="                a.nd_sub, a.nd_tot, a.nd_poriva, a.tx_ptopar, a.tx_tra, a.co_mottra, a.tx_desmottra,";
                    strSQL+="                a.co_cta, a.co_motdoc, a.tx_obs1, a.tx_obs2, a.st_reg, a.fe_ing, a.fe_ultmod,";
                    strSQL+="                a.co_usring, a.co_usrmod, a.fe_con, a.tx_obs3, a.co_forret, a.tx_numped,";
                    strSQL+="                a.co_dia, a.tx_vehret, a.tx_choret, a.ne_secgrp, a.ne_secemp, a.tx_numautsri,";
                    strSQL+="                a.tx_secdoc, a.tx_feccad, a.nd_valiva, ";
                    strSQL+="                a.co_mnu, a.st_tipdev,";
                    strSQL+="                a.st_regrep, a.st_coninv, a.ne_numdocree, a.st_imp, a.tx_obssolaut,";
                    strSQL+="                a.tx_obsautsol, a.st_aut";
                    strSQL+="                , a.fe_ven";
                    strSQL+="                , a.st_creGuiRem, a.st_conInvTraAut";
                    
                    
                    strSQL+="                FROM tbm_cabmovinv AS a";
                    strSQL+="                WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="                AND a.co_cli=" + strCodCli + ") AS x";
                    strSQL+="                 INNER JOIN (";
                    strSQL+="                         SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+="                         FROM tbt_uniCli";
                    strSQL+="                         WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="                         AND co_cli=" + strCodCli + ") AS y";
                    strSQL+="                 ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhCabMovInv=new ArrayList();
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_HIS, "" + rst.getString("co_his"));
                        
                        strAuxCodHis="";
                        strAuxCodHis+="SELECT MAX(co_his)";
                        strAuxCodHis+=" FROM tbh_cabmovinv";
                        strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                        strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                        strAuxCodHis+=" AND co_tipdoc= " + rst.getString("co_tipdoc") + "";
                        strAuxCodHis+=" AND co_doc= " + rst.getString("co_doc") + "";
                        intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                        if (intCodHisCodCli==-1)
                            return false;
                        
                        intCodHisCodCli++;
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_HIS, "" + intCodHisCodCli);
                        
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SEC_GRP, "" + rst.getString("ne_secgrp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SEC_EMP, "" + rst.getString("ne_secemp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_COT, "" + rst.getString("ne_numcot"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_DOC, "" + rst.getString("ne_numdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SEC_DOC, "" + rst.getString("tx_secdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_PED, "" + rst.getString("tx_numped"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_GUI, "" + rst.getString("ne_numgui"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_AUT_SRI, "" + rst.getString("tx_numautsri"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CAD, "" + rst.getString("tx_feccad"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CAD, rst.getString("tx_feccad").equals("")?null:rst.getString("tx_feccad"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_DIA, "" + rst.getString("co_dia"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_DOC, "" + rst.getString("fe_doc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_DOC, rst.getString("fe_doc").equals("")?null:rst.getString("fe_doc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_RUC, "" + rst.getString("tx_ruc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NOM_CLI, "" + rst.getString("tx_nomcli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_DIR_CLI, "" + rst.getString("tx_dircli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_TEL_CLI, "" + rst.getString("tx_telcli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_CIU_CLI, "" + rst.getString("tx_ciucli"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_COM, "" + rst.getString("co_com"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NOM_VEN, "" + rst.getString("tx_nomven"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_ATE, "" + rst.getString("tx_ate"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_FOR_PAG, "" + rst.getString("co_forpag"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_DES_FOR_PAG, "" + rst.getString("tx_desforpag"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_SUB, "" + rst.getString("nd_sub"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_POR_IVA, "" + rst.getString("nd_poriva"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_VAL_IVA, "" + rst.getString("nd_valiva"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_TOT, "" + rst.getString("nd_tot"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_PTO_PAR, "" + rst.getString("tx_ptopar"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_TRA, "" + rst.getString("tx_tra"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_MOT_TRA, "" + rst.getString("co_mottra"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_DES_MOT_TRA, "" + rst.getString("tx_desmottra"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_CTA, "" + rst.getString("co_cta"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_MOT_DOC, "" + rst.getString("co_motdoc"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_MNU, "" + rst.getString("co_mnu"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_IMP, "" + rst.getString("st_imp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_IMP, rst.getString("st_imp").equals("")?null:rst.getString("st_imp"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ING, rst.getString("fe_ing").equals("")?null:rst.getString("fe_ing"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_ULT_MOD, rst.getString("fe_ultmod").equals("")?null:rst.getString("fe_ultmod"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CON, "" + rst.getString("fe_con"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_CON, rst.getString("fe_con").equals("")?null:rst.getString("fe_con"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_TRE, "" + rst.getString("tx_obs3"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_FOR_RET, "" + rst.getString("co_forret"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_VEH_RET, "" + rst.getString("tx_vehret"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_CHO_RET, "" + rst.getString("tx_choret"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_AUT, "" + rst.getString("st_aut"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_AUT, rst.getString("st_aut").equals("")?null:rst.getString("st_aut"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV, "" + rst.getString("st_tipdev"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_TIP_DEV, rst.getString("st_tipdev").equals("")?null:rst.getString("st_tipdev"));
                        //arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV, "" + rst.getString("st_coninv"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV, rst.getString("st_coninv").equals("")?null:rst.getString("st_coninv"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_NUM_DOC_REE, "" + rst.getString("ne_numdocree"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FE_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_COD_CLI_UNI, "" + rst.getString("co_unicli"));
                                                
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CRE_GUI_REM, "" + rst.getString("st_creGuiRem"));
                        arlRegTbhCabMovInv.add(INT_ARL_TBH_CAB_MOV_INV_EST_CON_INV_TRA_AUT, "" + rst.getString("st_conInvTraAut"));
                        
                        
                        arlDatTbhCabMovInv.add(arlRegTbhCabMovInv);
                    }
                }
                stm.close();
                stm=null;
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
      
    
    private boolean inserta_tbhDetMovInv(Connection conTmpLoc){
        boolean blnRes=true;
        String strEstReg="";
        String strEstRep="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhDetMovInv.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_detmovinv(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_his, co_reg, ne_numfil,";
                    strSQL+="             co_itm, tx_codalt, tx_codalt2, tx_nomitm, tx_unimed, co_bod,";
                    strSQL+="             nd_can, nd_canorg, nd_candev, nd_cosuni, nd_preuni, nd_pordes,";
                    strSQL+="             st_ivacom, nd_tot, nd_costot, nd_exi, nd_valexi, nd_cospro, nd_cosunigrp,";
                    strSQL+="             nd_costotgrp, nd_exigrp, nd_valexigrp, nd_cosprogrp, ";
                    strSQL+="             nd_cancon, tx_obs1, co_usrcon, st_regrep, fe_his, co_usrhis,";
                    strSQL+="             co_unicli, co_itmAct)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_TIP_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_NUM_FIL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_ITM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_ALT),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_ALT_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_NOM_ITM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_UNI_MED),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_BOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_CAN),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_CAN_ORG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_CAN_DEV),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COS_UNI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_PRE_UNI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_POR_DES),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_IVA_COM),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_TOT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COS_TOT),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_EXI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_VAL_EXI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COS_PRO),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COS_UNI_GRP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COS_TOT_GRP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_EXI_GRP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_VAL_EXI_GRP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COS_PRO_GRP),2) + ",";
     
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_CAN_CON),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_USR_CON),2) + ",";
                    
                                        
                    strEstRep=objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_EST_REG_REP);
                    if( (strEstRep.equals(null)) || (strEstRep.equals(""))  ){
                        strSQL+=" null,";
                    }
                    else{
                        strSQL+="'" + strEstRep + "',";
                    }
                    
                    
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD__USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_UNI_CLI),2) + ",";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetMovInv, i, INT_ARL_TBH_DET_MOV_INV_COD_ITM_ACT),2) + "";
                    

                    
                    strSQL+=")";
                    System.out.println("inserta_tbhDetMovInv CONTIENE: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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

    private boolean select_tbhDetMovInv(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhDetMovInv.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                                        
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, y.co_his, x.co_reg, x.ne_numfil,";
                    strSQL+="        x.co_itm, x.tx_codalt, x.tx_codalt2, x.tx_nomitm, x.tx_unimed, x.co_bod,";
                    strSQL+="        x.nd_can, x.nd_canorg, x.nd_candev, x.nd_cosuni, x.nd_preuni, x.nd_pordes,";
                    strSQL+="        x.st_ivacom, x.nd_tot, x.nd_costot, x.nd_exi, x.nd_valexi, x.nd_cospro, x.nd_cosunigrp,";
                    strSQL+="        x.nd_costotgrp, x.nd_exigrp, x.nd_valexigrp, x.nd_cosprogrp, ";
                    strSQL+="        x.nd_cancon, x.tx_obs1, x.co_usrcon, x.st_regrep,";
                    strSQL+="       cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="       " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="       y.co_uni AS co_uniCli, x.co_itmAct  FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_tipdoc, b.co_doc, b.co_reg, b.ne_numfil,";
                    strSQL+=" 	       b.co_itm, b.tx_codalt, b.tx_codalt2, b.tx_nomitm, b.tx_unimed, b.co_bod,";
                    strSQL+=" 	       b.nd_can, b.nd_canorg, b.nd_candev, b.nd_cosuni, b.nd_preuni, b.nd_pordes,";
                    strSQL+=" 	       b.st_ivacom, b.nd_tot, b.nd_costot, b.nd_exi, b.nd_valexi, b.nd_cospro, b.nd_cosunigrp,";
                    strSQL+=" 	       b.nd_costotgrp, b.nd_exigrp, b.nd_cosprogrp, ";
                    strSQL+=" 	       b.nd_cancon, b.tx_obs1, b.co_usrcon, b.st_regrep, a.co_cli, b.nd_valexigrp";
                    strSQL+=" 	       , b.co_itmAct";
                    strSQL+=" 		FROM tbm_cabMovInv  AS a INNER JOIN tbm_detMovInv AS b";
                    strSQL+=" 		ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.co_doc=b.co_doc";
                    strSQL+=" 		WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		AND a.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" 		INNER JOIN (";
                    strSQL+=" 			SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 			FROM tbt_uniCli";
                    strSQL+=" 			WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 			AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetMovInv=new ArrayList();
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                        if(arlDatTbhCabMovInv.size()>0){
                            for(int f=0;f<arlDatTbhCabMovInv.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_LOC),2))  ){
                                        if( rst.getString("co_tipdoc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC),2))  ){
                                            if( rst.getString("co_doc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_DOC),2))  ){
                                                arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_HIS),2));
                                            }

                                        }
                                    }
                                }
                            }
                        }                       
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_NUM_FIL, "" + rst.getString("ne_numfil"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ITM, "" + rst.getString("co_itm"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ALT, "" + rst.getString("tx_codalt"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ALT_DOS, "" + rst.getString("tx_codalt2"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_NOM_ITM, "" + rst.getString("tx_nomitm"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_UNI_MED, "" + rst.getString("tx_unimed"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_BOD, "" + rst.getString("co_bod"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN, "" + rst.getString("nd_can"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN_ORG, "" + rst.getString("nd_canorg"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN_DEV, "" + rst.getString("nd_candev"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_UNI, "" + rst.getString("nd_cosuni"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_PRE_UNI, "" + rst.getString("nd_preuni"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_POR_DES, "" + rst.getString("nd_pordes"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_IVA_COM, "" + rst.getString("st_ivacom"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_TOT, "" + rst.getString("nd_tot"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_TOT, "" + rst.getString("nd_costot"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_EXI, "" + rst.getString("nd_exi"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_VAL_EXI, "" + rst.getString("nd_valexi"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_PRO, "" + rst.getString("nd_cospro"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_UNI_GRP, "" + rst.getString("nd_cosunigrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_TOT_GRP, "" + rst.getString("nd_costotgrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_EXI_GRP, "" + rst.getString("nd_exigrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_VAL_EXI_GRP, "" + rst.getString("nd_valexigrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_PRO_GRP, "" + rst.getString("nd_cosprogrp"));
                                                
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN_CON, "" + rst.getString("nd_cancon"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_USR_CON, "" + rst.getString("co_usrcon"));
                                                
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_EST_REG_REP, "" + rst.getString("st_regrep")==null?null:(rst.getString("st_regrep")==""?null:rst.getString("st_regrep")));
                        
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD__USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ITM_ACT, "" + rst.getString("co_itmAct"));
                                                
                        arlDatTbhDetMovInv.add(arlRegTbhDetMovInv);
                    }

                    ////////////////////////////////////////////////////
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, y.co_his, x.co_reg, x.ne_numfil,";
                    strSQL+="        x.co_itm, x.tx_codalt, x.tx_codalt2, x.tx_nomitm, x.tx_unimed, x.co_bod,";
                    strSQL+="        x.nd_can, x.nd_canorg, x.nd_candev, x.nd_cosuni, x.nd_preuni, x.nd_pordes,";
                    strSQL+="        x.st_ivacom, x.nd_tot, x.nd_costot, x.nd_exi, x.nd_valexi, x.nd_cospro, x.nd_cosunigrp,";
                    strSQL+="        x.nd_costotgrp, x.nd_exigrp, x.nd_valexigrp, x.nd_cosprogrp, ";
                    strSQL+="        x.nd_cancon, x.tx_obs1, x.co_usrcon, x.st_regrep,";
                    strSQL+="       cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="       " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="       y.co_uni AS co_uniCli, x.co_itmAct FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_tipdoc, b.co_doc, b.co_reg, b.ne_numfil,";
                    strSQL+=" 	       b.co_itm, b.tx_codalt, b.tx_codalt2, b.tx_nomitm, b.tx_unimed, b.co_bod,";
                    strSQL+=" 	       b.nd_can, b.nd_canorg, b.nd_candev, b.nd_cosuni, b.nd_preuni, b.nd_pordes,";
                    strSQL+=" 	       b.st_ivacom, b.nd_tot, b.nd_costot, b.nd_exi, b.nd_valexi, b.nd_cospro, b.nd_cosunigrp,";
                    strSQL+=" 	       b.nd_costotgrp, b.nd_exigrp, b.nd_cosprogrp, ";
                    strSQL+=" 	       b.nd_cancon, b.tx_obs1, b.co_usrcon, b.st_regrep, a.co_cli, b.nd_valexigrp";
                    strSQL+=" 	       , b.co_itmAct";
                    strSQL+=" 		FROM tbm_cabMovInv  AS a INNER JOIN tbm_detMovInv AS b";
                    strSQL+=" 		ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.co_doc=b.co_doc";
                    strSQL+=" 		WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		AND a.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" 		INNER JOIN (";
                    strSQL+=" 			SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 			FROM tbt_uniCli";
                    strSQL+=" 			WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 			AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetMovInv=new ArrayList();
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                        //arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabMovInv.size()>0){
                            for(int f=0;f<arlDatTbhCabMovInv.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_LOC),2))  ){
                                        if( rst.getString("co_tipdoc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC),2))  ){
                                            if( rst.getString("co_doc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_DOC),2))  ){
                                                arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_HIS),2));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_NUM_FIL, "" + rst.getString("ne_numfil"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ITM, "" + rst.getString("co_itm"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ALT, "" + rst.getString("tx_codalt"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ALT_DOS, "" + rst.getString("tx_codalt2"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_NOM_ITM, "" + rst.getString("tx_nomitm"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_UNI_MED, "" + rst.getString("tx_unimed"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_BOD, "" + rst.getString("co_bod"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN, "" + rst.getString("nd_can"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN_ORG, "" + rst.getString("nd_canorg"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN_DEV, "" + rst.getString("nd_candev"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_UNI, "" + rst.getString("nd_cosuni"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_PRE_UNI, "" + rst.getString("nd_preuni"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_POR_DES, "" + rst.getString("nd_pordes"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_IVA_COM, "" + rst.getString("st_ivacom"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_TOT, "" + rst.getString("nd_tot"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_TOT, "" + rst.getString("nd_costot"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_EXI, "" + rst.getString("nd_exi"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_VAL_EXI, "" + rst.getString("nd_valexi"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_PRO, "" + rst.getString("nd_cospro"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_UNI_GRP, "" + rst.getString("nd_cosunigrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_TOT_GRP, "" + rst.getString("nd_costotgrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_EXI_GRP, "" + rst.getString("nd_exigrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_VAL_EXI_GRP, "" + rst.getString("nd_valexigrp"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COS_PRO_GRP, "" + rst.getString("nd_cosprogrp"));

                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_CAN_CON, "" + rst.getString("nd_cancon"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_USR_CON, "" + rst.getString("co_usrcon"));
                       
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_EST_REG_REP, "" + rst.getString("st_regrep")==null?null:(rst.getString("st_regrep")==""?null:rst.getString("st_regrep")));
                                                
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD__USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        
                        arlRegTbhDetMovInv.add(INT_ARL_TBH_DET_MOV_INV_COD_ITM_ACT, "" + rst.getString("co_itmAct"));
                        
                        arlDatTbhDetMovInv.add(arlRegTbhDetMovInv);
                    }
                }
                stm.close();
                stm=null;
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
   
    
    private boolean inserta_tbhPagMovInv(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecVen="";
        String strFecRecChq="";
        String strFecVenChq="";
        String strFecHis="";
        String strFecRee="";
        String strAplRet="";
        String strCodCtaAutPag="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhPagMovInv.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_pagmovinv(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_his, co_reg, ne_diacre,";
                    strSQL+="             fe_ven, co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo,";
                    strSQL+="             st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq,";
                    strSQL+="             fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep,";
                    strSQL+="             fe_his, co_usrhis, co_unicli, fe_ree, co_usrRee, tx_comRee)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_TIP_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_DIA_CRE),2) + ",";
                    strFecVen=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_FEC_VEN);
                    if( (strFecVen.equals(null)) || (strFecVen.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecVen, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_TIP_RET),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_POR_RET),2) + ",";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_APL_RET),0) + ",";
                    strAplRet=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_APL_RET);
                    if( (strAplRet.equals(null)) || (strAplRet.equals("")) || (strAplRet.equals("null"))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + strAplRet + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_MON_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_DIA_GRA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_ABO),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_EST_SOP),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_EST_ENT_SOP),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_EST_POS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_BAN_CHQ),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_NUM_CTA_CHQ),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_NUM_CHQ),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_FEC_REC_CHQ),2) + "',";
                    
                    strFecRecChq=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_FEC_REC_CHQ);
                    
                    if( (strFecRecChq.equals(null)) || (strFecRecChq.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecRecChq, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                                        
                    strFecVenChq=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_FEC_VEN_CHQ);
                    
                    if( (strFecVenChq.equals(null)) || (strFecVenChq.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecVenChq, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_MON_CHQ),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_PRO_CHQ),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_EST_REG),0) + ",";
                    
                    String strRegRep=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_EST_REG_REP);
                    if( (strRegRep.equals(null)) || (strRegRep.equals("null"))  || (strRegRep.equals("Null"))  )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strRegRep + "',";

                    
                    
                    
                    
                    strFecHis=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_FE_HIS);
                    
                    if( (strFecHis.equals(null)) || (strFecHis.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecHis, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_UNI_CLI),2) + ",";


                    strFecRee=objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_FEC_REE);
                    
                    if( (strFecRee.equals(null)) || (strFecRee.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecRee, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COD_USR_REE),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhPagMovInv, i, INT_ARL_TBH_PAG_MOV_INV_COM_REE),2) + "'";

                    
                    strSQL+=")";
                    System.out.println("inserta_tbhPagMovInv CONTIENE: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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

    private boolean select_tbhPagMovInv(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhPagMovInv.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, y.co_his, x.co_reg, x.ne_diacre,";
                    strSQL+="        x.fe_ven, x.co_tipret, x.nd_porret, x.tx_aplret, x.mo_pag, x.ne_diagra, x.nd_abo,";
                    strSQL+="        x.st_sop, x.st_entsop, x.st_pos, x.co_banchq, x.tx_numctachq, x.tx_numchq,";
                    strSQL+="        x.fe_recchq, x.fe_venchq, x.nd_monchq, x.co_prochq, x.st_reg, x.st_regrep, x.fe_ree, x.co_usrRee, x.tx_comRee,";
                    strSQL+="       cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="       " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="       y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_tipdoc, b.co_doc, b.co_reg, b.ne_diacre, b.fe_ven,";
                    strSQL+=" 	        b.co_tipret, b.nd_porret, b.tx_aplret, b.mo_pag, b.ne_diagra, b.nd_abo, b.st_sop,";
                    strSQL+=" 	        b.st_entsop, b.st_pos, b.co_banchq, b.tx_numctachq, b.tx_numchq, b.fe_recchq,";
                    strSQL+=" 	        b.fe_venchq, b.nd_monchq, b.co_prochq, b.st_reg, b.st_regrep, a.co_cli";
                    strSQL+=" 	        , b.fe_ree, b.co_usrRee, b.tx_comRee";
                    strSQL+=" 		FROM tbm_cabMovInv  AS a INNER JOIN tbm_pagmovinv AS b";
                    strSQL+=" 		ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.co_doc=b.co_doc";
                    strSQL+=" 		WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		AND a.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" 		INNER JOIN (";
                    strSQL+=" 			SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 			FROM tbt_uniCli";
                    strSQL+=" 			WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 			AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhPagMovInv=new ArrayList();
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                        if(arlDatTbhCabMovInv.size()>0){
                            for(int f=0;f<arlDatTbhCabMovInv.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_LOC),2))  ){
                                        if( rst.getString("co_tipdoc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC),2))  ){
                                            if( rst.getString("co_doc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_DOC),2))  ){
                                                arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_HIS),2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_DIA_CRE, "" + rst.getString("ne_diacre"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_VEN, "" + rst.getString("fe_ven")==""?null:rst.getString("fe_ven"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_TIP_RET, "" + rst.getString("co_tipret"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_POR_RET, "" + rst.getString("nd_porret"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_APL_RET, "" + rst.getString("tx_aplret")==""?null:rst.getString("tx_aplret"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_MON_PAG, "" + rst.getString("mo_pag"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_DIA_GRA, "" + rst.getString("ne_diagra"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_ABO, "" + rst.getString("nd_abo"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_SOP, "" + rst.getString("st_sop"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_ENT_SOP, "" + rst.getString("st_entsop"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_POS, "" + rst.getString("st_pos"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_BAN_CHQ, "" + rst.getString("co_banchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_NUM_CTA_CHQ, "" + rst.getString("tx_numctachq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_NUM_CHQ, "" + rst.getString("tx_numchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_REC_CHQ, "" + rst.getString("fe_recchq")==""?null:rst.getString("fe_recchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_VEN_CHQ, "" + rst.getString("fe_venchq")==""?null:rst.getString("fe_venchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_MON_CHQ, "" + rst.getString("nd_monchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_PRO_CHQ, "" + rst.getString("co_prochq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_REG, "" + rst.getString("st_reg"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FE_HIS, "" + rst.getString("fe_his")==""?null:rst.getString("fe_his"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_REE, "" + rst.getString("fe_ree")==""?null:rst.getString("fe_ree"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_USR_REE, "" + rst.getString("co_usrRee")==""?null:rst.getString("co_usrRee"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COM_REE, "" + rst.getString("tx_comRee")==""?null:rst.getString("tx_comRee"));
                        
                        arlDatTbhPagMovInv.add(arlRegTbhPagMovInv);
                    }
                    
                    /////////////////////////////////////
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, y.co_his, x.co_reg, x.ne_diacre,";
                    strSQL+="        x.fe_ven, x.co_tipret, x.nd_porret, x.tx_aplret, x.mo_pag, x.ne_diagra, x.nd_abo,";
                    strSQL+="        x.st_sop, x.st_entsop, x.st_pos, x.co_banchq, x.tx_numctachq, x.tx_numchq,";
                    strSQL+="        x.fe_recchq, x.fe_venchq, x.nd_monchq, x.co_prochq, x.st_reg, x.st_regrep, x.fe_ree, x.co_usrRee, x.tx_comRee,";
                    strSQL+="       cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+="       " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+="       y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_tipdoc, b.co_doc, b.co_reg, b.ne_diacre, b.fe_ven,";
                    strSQL+=" 	        b.co_tipret, b.nd_porret, b.tx_aplret, b.mo_pag, b.ne_diagra, b.nd_abo, b.st_sop,";
                    strSQL+=" 	        b.st_entsop, b.st_pos, b.co_banchq, b.tx_numctachq, b.tx_numchq, b.fe_recchq,";
                    strSQL+=" 	        b.fe_venchq, b.nd_monchq, b.co_prochq, b.st_reg, b.st_regrep, a.co_cli";
                    strSQL+=" 	        , b.fe_ree, b.co_usrRee, b.tx_comRee";
                    strSQL+=" 		FROM tbm_cabMovInv  AS a INNER JOIN tbm_pagmovinv AS b";
                    strSQL+=" 		ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.co_doc=b.co_doc";
                    strSQL+=" 		WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		AND a.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" 		INNER JOIN (";
                    strSQL+=" 			SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 			FROM tbt_uniCli";
                    strSQL+=" 			WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 			AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhPagMovInv=new ArrayList();
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                        //arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabMovInv.size()>0){
                            for(int f=0;f<arlDatTbhCabMovInv.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_LOC),2))  ){
                                        if( rst.getString("co_tipdoc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_TIP_DOC),2))  ){
                                            if( rst.getString("co_doc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_DOC),2))  ){
                                                arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabMovInv, f, INT_ARL_TBH_CAB_MOV_INV_COD_HIS),2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_DIA_CRE, "" + rst.getString("ne_diacre"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_VEN, "" + rst.getString("fe_ven")==""?null:rst.getString("fe_ven"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_TIP_RET, "" + rst.getString("co_tipret"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_POR_RET, "" + rst.getString("nd_porret"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_APL_RET, "" + rst.getString("tx_aplret"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_MON_PAG, "" + rst.getString("mo_pag"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_DIA_GRA, "" + rst.getString("ne_diagra"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_ABO, "" + rst.getString("nd_abo"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_SOP, "" + rst.getString("st_sop"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_ENT_SOP, "" + rst.getString("st_entsop"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_POS, "" + rst.getString("st_pos"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_BAN_CHQ, "" + rst.getString("co_banchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_NUM_CTA_CHQ, "" + rst.getString("tx_numctachq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_NUM_CHQ, "" + rst.getString("tx_numchq"));
                        //arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_REC_CHQ, "" + rst.getString("fe_recchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_REC_CHQ, "" + rst.getString("fe_recchq")==""?null:rst.getString("fe_recchq"));
                        //arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_VEN_CHQ, "" + rst.getString("fe_venchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_VEN_CHQ, "" + rst.getString("fe_venchq")==""?null:rst.getString("fe_venchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_MON_CHQ, "" + rst.getString("nd_monchq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_PRO_CHQ, "" + rst.getString("co_prochq"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_REG, "" + rst.getString("st_reg"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_EST_REG_REP, "" + rst.getString("st_regrep"));
                        //arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FE_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FE_HIS, "" + rst.getString("fe_his")==""?null:rst.getString("fe_his"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_FEC_REE, "" + rst.getString("fe_ree")==""?null:rst.getString("fe_ree"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COD_USR_REE, "" + rst.getString("co_usrRee")==""?null:rst.getString("co_usrRee"));
                        arlRegTbhPagMovInv.add(INT_ARL_TBH_PAG_MOV_INV_COM_REE, "" + rst.getString("tx_comRee")==""?null:rst.getString("tx_comRee"));

                        
                        arlDatTbhPagMovInv.add(arlRegTbhPagMovInv);
                    }
                }
                stm.close();
                stm=null;
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
       
  
    private boolean inserta_tbhCabPag(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecDoc="";
        String strFecVen="";
        String strFecIng="";
        String strFecUltMod="";
        String strEstAut="";
        String strEstImp="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhCabPag.size();i++){
                    
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cabpag(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_his, fe_doc, fe_ven, ne_numdoc1,";
                    strSQL+="             ne_numdoc2, co_dia, co_cta, co_cli, tx_ruc, tx_nomcli, tx_dircli,";
                    strSQL+="             co_ben, tx_benchq, nd_mondoc, tx_mondocpal, co_mnu, st_imp, tx_obs1,";
                    strSQL+="             tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_obssolaut,";
                    strSQL+="             tx_obsautsol, st_aut, fe_his, co_usrhis, co_unicli, st_regrep, st_conDepBan)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_TIP_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_HIS),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_DOC),2) + "',";
                    strFecDoc=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_DOC);
                    if( (strFecDoc.equals(null)) || (strFecDoc.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecDoc, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_VEN),2) + "',";
                    strFecVen=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_VEN);
                    if( (strFecVen.equals(null)) || (strFecVen.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecVen, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_NUM_DOC_UNO),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_NUM_DOC_DOS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_DIA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_CTA),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_RUC),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_NOM_CLI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_DIR_CLI),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_BEN),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_BEN_CHQ),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_MON_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_MON_DOC_PAG_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_MNU),2) + ",";
                    
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_IMP),0) + ",";
                    strEstImp=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_IMP);
                    if( (strEstImp.equals(null)) || (strEstImp.equals("Null")) || (strEstImp.equals("null")) )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strEstImp + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_OBS_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_ING),2) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_ULT_MOD),2) + ",";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_USR_MOD),2) + ",";
                    String strSolAut=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_OBS_SOL_AUT);
                    if( (strSolAut.equals(null)) || (strSolAut.equals("Null"))  || (strSolAut.equals("null")) )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strSolAut + "',";

                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_OBS_AUT_SOL),0) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_AUT),0) + ",";
                    strEstAut=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_AUT);
                    if( (strEstAut.equals(null)) || (strEstAut.equals("Null")) || (strEstAut.equals("null")) )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strEstAut + "',";
                    
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_COD_UNI_CLI),2) + ",";
                    String strRegRep=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_REG_REP);
                    if( (strRegRep.equals(null)) || (strRegRep.equals("Null"))  || (strRegRep.equals("null"))  )
                        strSQL+=" '',";
                    else
                        strSQL+="'" + strRegRep + "',";
                            
                            
                    String strConDepBan=objUti.getStringValueAt(arlDatTbhCabPag, i, INT_ARL_TBH_CAB_PAG_EST_CON_DEP_BAN);
                    if( (strConDepBan.equals(null)) || (strConDepBan.equals("Null"))  || (strConDepBan.equals("null"))  )
                        strSQL+=" ''";
                    else
                        strSQL+="'" + strConDepBan + "'";
                                       
                    strSQL+=")";
                    System.out.println("SQL DE INSERTA_TBHCABPAG : " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhCabPag(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhCabPag.clear();
        String strAuxCodHis="";
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, b1.co_his, a1.fe_doc, a1.fe_ven, a1.ne_numdoc1,";
                    strSQL+="             a1.ne_numdoc2, a1.co_dia, a1.co_cta, a1.co_cli, a1.tx_ruc, a1.tx_nomcli, a1.tx_dircli,";
                    strSQL+="             a1.co_ben, a1.tx_benchq, a1.nd_mondoc, a1.tx_mondocpal, a1.co_mnu, a1.st_imp, a1.tx_obs1,";
                    strSQL+="             a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.tx_obssolaut,";
                    strSQL+="             a1.tx_obsautsol, a1.st_aut, ";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a1.st_regrep, a1.st_conDepBan";
                    strSQL+="   FROM tbm_cabpag AS a1";
                    strSQL+="   INNER JOIN tbt_uniCli AS b1";
                    strSQL+="  ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cliUni";
                    strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCliUni + "";
                    strSQL+=" UNION";
                    strSQL+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, b1.co_his, a1.fe_doc, a1.fe_ven, a1.ne_numdoc1,";
                    strSQL+="             a1.ne_numdoc2, a1.co_dia, a1.co_cta, a1.co_cli, a1.tx_ruc, a1.tx_nomcli, a1.tx_dircli,";
                    strSQL+="             a1.co_ben, a1.tx_benchq, a1.nd_mondoc, a1.tx_mondocpal, a1.co_mnu, a1.st_imp, a1.tx_obs1,";
                    strSQL+="             a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.tx_obssolaut,";
                    strSQL+="             a1.tx_obsautsol, a1.st_aut, ";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a1.st_regrep, a1.st_conDepBan";
                    strSQL+="  FROM tbm_cabpag AS a1";
                    strSQL+="  INNER JOIN (tbm_detPag AS a2 INNER JOIN tbm_pagMovInv AS a3";
                    strSQL+="  ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                    strSQL+="  AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                    strSQL+="  INNER JOIN tbm_cabMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                    strSQL+="  AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a4.co_cli=" + strCodCliUni + "";
                    strSQL+="  )";
                    strSQL+="  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="  INNER JOIN tbt_uniCli AS b1";
                    strSQL+="  ON a1.co_emp=b1.co_emp AND a4.co_cli=b1.co_cliUni";
                    strSQL+="  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_ven, a1.ne_numdoc1,";
                    strSQL+="        a1.ne_numdoc2, a1.co_cta, a1.co_cli, a1.tx_ruc, a1.tx_nomcli, a1.tx_dircli, a1.nd_mondoc,";
                    strSQL+="        a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod,";
                    strSQL+="        a1.co_dia, a1.co_ben, a1.tx_benchq, a1.tx_mondocpal, a1.co_mnu, a1.st_regrep, a1.st_imp,";
                    strSQL+="        a1.tx_obssolaut, a1.tx_obsautsol, a1.st_aut, co_his, co_uni, a1.st_regrep, a1.st_conDepBan";
                    //strSQL+="  ORDER BY co_tipDoc, co_doc";
                    
                    
                    ///////////////////////////////////////////////////////////////////
                    strSQL+=" UNION";
                    strSQL+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, b1.co_his, a1.fe_doc, a1.fe_ven, a1.ne_numdoc1,";
                    strSQL+="             a1.ne_numdoc2, a1.co_dia, a1.co_cta, a1.co_cli, a1.tx_ruc, a1.tx_nomcli, a1.tx_dircli,";
                    strSQL+="             a1.co_ben, a1.tx_benchq, a1.nd_mondoc, a1.tx_mondocpal, a1.co_mnu, a1.st_imp, a1.tx_obs1,";
                    strSQL+="             a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.tx_obssolaut,";
                    strSQL+="             a1.tx_obsautsol, a1.st_aut, ";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a1.st_regrep, a1.st_conDepBan";
                    strSQL+="   FROM tbm_cabpag AS a1";
                    strSQL+="   INNER JOIN tbt_uniCli AS b1";
                    strSQL+="  ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli";
                    strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCli + "";
                    strSQL+=" UNION";
                    strSQL+="             SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, b1.co_his, a1.fe_doc, a1.fe_ven, a1.ne_numdoc1,";
                    strSQL+="             a1.ne_numdoc2, a1.co_dia, a1.co_cta, a1.co_cli, a1.tx_ruc, a1.tx_nomcli, a1.tx_dircli,";
                    strSQL+="             a1.co_ben, a1.tx_benchq, a1.nd_mondoc, a1.tx_mondocpal, a1.co_mnu, a1.st_imp, a1.tx_obs1,";
                    strSQL+="             a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.tx_obssolaut,";
                    strSQL+="             a1.tx_obsautsol, a1.st_aut, ";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a1.st_regrep, a1.st_conDepBan";
                    strSQL+="  FROM tbm_cabpag AS a1";
                    strSQL+="  INNER JOIN (tbm_detPag AS a2 INNER JOIN tbm_pagMovInv AS a3";
                    strSQL+="  ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                    strSQL+="  AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                    strSQL+="  INNER JOIN tbm_cabMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                    strSQL+="  AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a4.co_cli=" + strCodCli + "";
                    strSQL+="  )";
                    strSQL+="  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="  INNER JOIN tbt_uniCli AS b1";
                    strSQL+="  ON a1.co_emp=b1.co_emp AND a4.co_cli=b1.co_cli";
                    strSQL+="  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_ven, a1.ne_numdoc1,";
                    strSQL+="        a1.ne_numdoc2, a1.co_cta, a1.co_cli, a1.tx_ruc, a1.tx_nomcli, a1.tx_dircli, a1.nd_mondoc,";
                    strSQL+="        a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod,";
                    strSQL+="        a1.co_dia, a1.co_ben, a1.tx_benchq, a1.tx_mondocpal, a1.co_mnu, a1.st_regrep, a1.st_imp,";
                    strSQL+="        a1.tx_obssolaut, a1.tx_obsautsol, a1.st_aut, co_his, co_uni, a1.st_regrep, a1.st_conDepBan";
                    strSQL+="  ORDER BY co_tipDoc, co_doc";
                    
                    
                    System.out.println("FRAN SELECT 1-->SQL DE INSERTA_TBHCABPAG -1- : " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhCabPag=new ArrayList();
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_DOC, "" + rst.getString("co_doc"));
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_HIS, "" + rst.getString("co_his"));
                        
                        strAuxCodHis="";
                        strAuxCodHis+="SELECT MAX(co_his)";
                        strAuxCodHis+=" FROM tbh_cabpag";
                        strAuxCodHis+=" WHERE co_emp=" + rst.getString("co_emp");
                        strAuxCodHis+=" AND co_loc= " + rst.getString("co_loc") + "";
                        strAuxCodHis+=" AND co_tipdoc= " + rst.getString("co_tipdoc") + "";
                        strAuxCodHis+=" AND co_doc= " + rst.getString("co_doc") + "";
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strAuxCodHis);

                        if (intCodHisCodCliUni==-1)
                            return false;
                        
                        intCodHisCodCliUni++;
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_HIS, "" + intCodHisCodCliUni);
                        
                        
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_DOC, "" + rst.getString("fe_doc"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_DOC, "" + rst.getString("fe_doc")==""?null:rst.getString("fe_doc"));
                        
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_VEN, "" + rst.getString("fe_ven"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_VEN, "" + rst.getString("fe_ven")==""?null:rst.getString("fe_ven"));
                        
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_NUM_DOC_UNO, "" + rst.getString("ne_numdoc1"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_NUM_DOC_DOS, "" + rst.getString("ne_numdoc2"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_DIA, "" + rst.getString("co_dia"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_CTA, "" + rst.getString("co_cta"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_RUC, "" + rst.getString("tx_ruc"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_NOM_CLI, "" + rst.getString("tx_nomcli"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_DIR_CLI, "" + rst.getString("tx_dircli"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_BEN, "" + rst.getString("co_ben"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_BEN_CHQ, "" + rst.getString("tx_benchq"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_MON_DOC, "" + rst.getString("nd_mondoc"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_MON_DOC_PAG_UNO, "" + rst.getString("tx_mondocpal"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_MNU, "" + rst.getString("co_mnu"));
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_IMP, "" + rst.getString("st_imp"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_IMP, "" + rst.getString("st_imp")==""?null:rst.getString("st_imp"));
                        
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_OBS_DOS, "" + rst.getString("tx_obs2"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_OBS_SOL_AUT, "" + rst.getString("tx_obssolaut"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_OBS_AUT_SOL, "" + rst.getString("tx_obsautsol"));
                        //arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_AUT, "" + rst.getString("st_aut"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_AUT, "" + rst.getString("st_aut")==""?null:rst.getString("st_aut"));
                        
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_REG_REP, "" + rst.getString("st_regrep"));
                        
                        arlRegTbhCabPag.add(INT_ARL_TBH_CAB_PAG_EST_CON_DEP_BAN, "" + rst.getString("st_conDepBan"));
                        
                        
                        arlDatTbhCabPag.add(arlRegTbhCabPag);
                    }
                            

                }
                stm.close();
                stm=null;
                
//                System.out.println("FRAN---> arlDatTbhCabPag CONTIENE: " + arlDatTbhCabPag.toString());
                
                
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
    
    private boolean inserta_tbhDetPag(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecRecChq="";
        String strFecVenChq="";
        String strCodSri="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhDetPag.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_detpag(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_his, co_reg, co_locpag,";
                    strSQL+="             co_tipdocpag, co_docpag, co_regpag, nd_abo, co_tipdoccon, co_banchq,";
                    strSQL+="             tx_numctachq, tx_numchq, fe_recchq, fe_venchq, tx_codsri, fe_his,";
                    strSQL+="             co_usrhis, co_unicli, st_regRep)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_TIP_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_LOC_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_TIP_DOC_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_DOC_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_REG_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_ABO),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_TIP_DOC_CON),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_BAN_CHQ),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_NUM_CTA_CHQ),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_NUM_CHQ),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_FEC_REC_CHQ),2) + "',";
                    strFecRecChq=objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_FEC_REC_CHQ);
                    if( (strFecRecChq.equals(null)) || (strFecRecChq.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecRecChq, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_FEC_VEN_CHQ),2) + "',";
                    strFecVenChq=objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_FEC_VEN_CHQ);
                    if( (strFecVenChq.equals(null)) || (strFecVenChq.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecVenChq, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_SRI),0) + ",";
                    strCodSri=objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_SRI);
                    if( (strCodSri.equals(null)) || (strCodSri.equals("")) || (strCodSri.equals("null"))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + strCodSri + "',";
                    

                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_COD_UNI_CLI),2) + ",";
                    
                    String strRegRep=objUti.getStringValueAt(arlDatTbhDetPag, i, INT_ARL_TBH_DET_PAG_EST_REG_REP);
                    if( (strRegRep.equals(null)) || (strRegRep.equals("")) || (strRegRep.equals("null"))  )
                        strSQL+=" ''";
                    else
                        strSQL+="'" + strRegRep + "'";

                    
                    
                    strSQL+=")";
                    System.out.println("SQL inserta_tbhDetPag: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
        
    private boolean select_tbhDetPag(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhDetPag.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, b1.co_his, a2.co_reg, a2.co_locpag, a2.co_tipdocpag,";
                    strSQL+=" 	a2.co_docpag, a2.co_regpag, a2.nd_abo, a2.co_tipdoccon, a2.co_banchq, a2.tx_numctachq,";
                    strSQL+=" 	a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.tx_codsri,";
                    strSQL+=" 	cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a2.st_regrep";
                    strSQL+="   FROM (tbm_cabpag AS a1 INNER JOIN tbm_detPag AS a2 ON a1.co_emp=a2.co_emp";
                    strSQL+=" 	AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 	INNER JOIN tbt_uniCli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cliUni)";
                    strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCliUni + "";
                    strSQL+=" UNION";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, b1.co_his, a2.co_reg, a2.co_locpag, a2.co_tipdocpag,";
                    strSQL+=" 	a2.co_docpag, a2.co_regpag, a2.nd_abo, a2.co_tipdoccon, a2.co_banchq, a2.tx_numctachq,";
                    strSQL+=" 	a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.tx_codsri,";
                    strSQL+=" 	cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a2.st_regrep";
                    strSQL+="  FROM tbm_cabpag AS a1";
                    strSQL+="  INNER JOIN (tbm_detPag AS a2 INNER JOIN tbm_pagMovInv AS a3";
                    strSQL+="  ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                    strSQL+="  AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                    strSQL+="  INNER JOIN tbm_cabMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                    strSQL+="  AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a4.co_cli=" + strCodCliUni + "";
                    strSQL+="  )";
                    strSQL+="  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="  INNER JOIN tbt_uniCli AS b1";
                    strSQL+="  ON a1.co_emp=b1.co_emp AND a4.co_cli=b1.co_cliUni";
                    strSQL+="  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="  GROUP BY a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_locpag, a2.co_tipdocpag,";
                    strSQL+=" 	a2.co_docpag, a2.co_regpag, a2.nd_abo, a2.co_tipdoccon, a2.co_banchq, a2.tx_numctachq,";
                    strSQL+=" 	a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.st_regrep, a2.tx_codsri, a4.co_cli, b1.co_uni, b1.co_his";
                    //strSQL+="  ORDER BY co_tipDoc, co_doc";
                    strSQL+=" UNION";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, b1.co_his, a2.co_reg, a2.co_locpag, a2.co_tipdocpag,";
                    strSQL+=" 	a2.co_docpag, a2.co_regpag, a2.nd_abo, a2.co_tipdoccon, a2.co_banchq, a2.tx_numctachq,";
                    strSQL+=" 	a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.tx_codsri,";
                    strSQL+=" 	cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a2.st_regrep";
                    strSQL+="   FROM (tbm_cabpag AS a1 INNER JOIN tbm_detPag AS a2 ON a1.co_emp=a2.co_emp";
                    strSQL+=" 	AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 	INNER JOIN tbt_uniCli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                    strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCli + "";
                    strSQL+=" UNION";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, b1.co_his, a2.co_reg, a2.co_locpag, a2.co_tipdocpag,";
                    strSQL+=" 	a2.co_docpag, a2.co_regpag, a2.nd_abo, a2.co_tipdoccon, a2.co_banchq, a2.tx_numctachq,";
                    strSQL+=" 	a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.tx_codsri,";
                    strSQL+=" 	cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    b1.co_uni AS co_uniCli, a2.st_regrep";
                    strSQL+="  FROM tbm_cabpag AS a1";
                    strSQL+="  INNER JOIN (tbm_detPag AS a2 INNER JOIN tbm_pagMovInv AS a3";
                    strSQL+="  ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                    strSQL+="  AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                    strSQL+="  INNER JOIN tbm_cabMovInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                    strSQL+="  AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a4.co_cli=" + strCodCli + "";
                    strSQL+="  )";
                    strSQL+="  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="  INNER JOIN tbt_uniCli AS b1";
                    strSQL+="  ON a1.co_emp=b1.co_emp AND a4.co_cli=b1.co_cli";
                    strSQL+="  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="  GROUP BY a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_locpag, a2.co_tipdocpag,";
                    strSQL+=" 	a2.co_docpag, a2.co_regpag, a2.nd_abo, a2.co_tipdoccon, a2.co_banchq, a2.tx_numctachq,";
                    strSQL+=" 	a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.st_regrep, a2.tx_codsri, a4.co_cli, b1.co_uni, b1.co_his";
                    strSQL+="  ORDER BY co_tipDoc, co_doc";
                    
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDetPag=new ArrayList();
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_DOC, "" + rst.getString("co_doc"));
                        //arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_HIS, "" + rst.getString("co_his"));
                        if(arlDatTbhCabPag.size()>0){
                            for(int f=0;f<arlDatTbhCabPag.size();f++){
                                if( rst.getString("co_emp").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, f, INT_ARL_TBH_CAB_PAG_COD_EMP),2))  ){
                                    if( rst.getString("co_loc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, f, INT_ARL_TBH_CAB_PAG_COD_LOC),2))  ){
                                        if( rst.getString("co_tipdoc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, f, INT_ARL_TBH_CAB_PAG_COD_TIP_DOC),2))  ){
                                            if( rst.getString("co_doc").toString().equals(objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, f, INT_ARL_TBH_CAB_PAG_COD_DOC),2))  ){
                                                arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_HIS, "" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCabPag, f, INT_ARL_TBH_CAB_PAG_COD_HIS),2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        
                        
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_REG, "" + rst.getString("co_reg"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_LOC_PAG, "" + rst.getString("co_locpag"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_TIP_DOC_PAG, "" + rst.getString("co_tipdocpag"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_DOC_PAG, "" + rst.getString("co_docpag"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_REG_PAG, "" + rst.getString("co_regpag"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_ABO, "" + rst.getString("nd_abo"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_TIP_DOC_CON, "" + rst.getString("co_tipdoccon"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_BAN_CHQ, "" + rst.getString("co_banchq"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_NUM_CTA_CHQ, "" + rst.getString("tx_numctachq"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_NUM_CHQ, "" + rst.getString("tx_numchq"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_FEC_REC_CHQ, rst.getString("fe_recchq").equals("")?null:rst.getString("fe_recchq"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_FEC_VEN_CHQ, rst.getString("fe_venchq").equals("")?null:rst.getString("fe_venchq"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_SRI, rst.getString("tx_codsri").equals("")?null:rst.getString("tx_codsri"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        arlRegTbhDetPag.add(INT_ARL_TBH_DET_PAG_EST_REG_REP, "" + rst.getString("st_regrep"));
                        arlDatTbhDetPag.add(arlRegTbhDetPag);
                    }
                    
                }
                stm.close();
                stm=null;
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
   
    
    private boolean inserta_tbhRefComSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecRef="";
        String strFecIng="";
        String strFecUltMod="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhRefComSolCre.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_refcomsolcre(";
                    strSQL+="             co_emp, co_sol, co_his, co_ref, fe_ref, tx_nom, tx_matproref,";
                    strSQL+="             tx_tel, tx_tie, tx_cupcre, tx_placre, tx_forpag, st_pro, tx_cal,";
                    strSQL+="             tx_inf, tx_carinf, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring,";
                    strSQL+="             co_usrmod, fe_his, co_usrhis, co_unicli)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_REF),2) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_REF),2) + ",";
                    strFecRef=objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_REF);
                    if( (strFecRef.equals(null)) || (strFecRef.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecRef, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_NOM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_MAT_PRO_REF),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_TEL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_TIE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_CUP_CRE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_PLA_CRE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FOR_PAG),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_EST_PRO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_CAL),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_INF),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_CAR_INF),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING),2) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD),2) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("SQL DE inserta_tbhRefComSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
        
    private boolean select_tbhRefComSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhRefComSolCre.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_his) AS co_his";
                    strSQL+=" FROM tbh_refComSolCre AS a1 INNER JOIN tbm_solCre AS a2 ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_sol, " + intCodHisCodCliUni + " AS co_his, x.co_ref, x.fe_ref, x.tx_nom, x.tx_matproref,";
                    strSQL+="             x.tx_tel, x.tx_tie, x.tx_cupcre, x.tx_placre, x.tx_forpag, x.st_pro, x.tx_cal,";
                    strSQL+="             x.tx_inf, x.tx_carinf, x.tx_obs1, x.st_reg, x.fe_ing, x.fe_ultmod, x.co_usring,";
                    strSQL+="             x.co_usrmod,";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    y.co_uni AS co_uniCli, x.ne_numPro FROM(";
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_sol, a2.co_ref, a2.fe_ref, a2.tx_nom, a2.tx_matproref, a2.tx_tel,";
                    strSQL+=" 		     a2.tx_tie, a2.tx_cupcre, a2.tx_placre, a2.tx_forpag, a2.st_pro, a2.tx_cal, a2.tx_inf,";
                    strSQL+=" 		     a2.tx_carinf, a2.tx_obs1, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, a2.co_usrmod, a1.co_cli, a2.ne_numPro";
                    strSQL+=" 		  FROM tbm_solCre AS a1 INNER JOIN tbm_refcomsolcre AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" 		  INNER JOIN (";
                    strSQL+=" 			  SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 			  FROM tbt_uniCli";
                    strSQL+=" 			  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		          AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhRefComSolCre=new ArrayList();
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_REF, "" + rst.getString("co_ref"));
                        //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_REF, "" + rst.getString("fe_ref"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_REF, "" + rst.getString("fe_ref")==""?null:rst.getString("fe_ref"));
                        
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_MAT_PRO_REF, "" + rst.getString("tx_matproref"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_TEL, "" + rst.getString("tx_tel"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_TIE, "" + rst.getString("tx_tie"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CUP_CRE, "" + rst.getString("tx_cupcre"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_PLA_CRE, "" + rst.getString("tx_placre"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FOR_PAG, "" + rst.getString("tx_forpag"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_EST_PRO, "" + rst.getString("st_pro"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CAL, "" + rst.getString("tx_cal"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_INF, "" + rst.getString("tx_inf"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CAR_INF, "" + rst.getString("tx_carinf"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_NUM_PRO, "" + rst.getString("ne_numPro"));
                        
                        arlDatTbhRefComSolCre.add(arlRegTbhRefComSolCre);
                    }
                    
                    
                    
                    /////////////////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_his) AS co_his";
                    strSQL+=" FROM tbh_refComSolCre AS a1 INNER JOIN tbm_solCre AS a2 ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_sol, " + intCodHisCodCli + " AS co_his, x.co_ref, x.fe_ref, x.tx_nom, x.tx_matproref,";
                    strSQL+="             x.tx_tel, x.tx_tie, x.tx_cupcre, x.tx_placre, x.tx_forpag, x.st_pro, x.tx_cal,";
                    strSQL+="             x.tx_inf, x.tx_carinf, x.tx_obs1, x.st_reg, x.fe_ing, x.fe_ultmod, x.co_usring,";
                    strSQL+="             x.co_usrmod,";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    y.co_uni AS co_uniCli, x.ne_numPro FROM(";
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_sol, a2.co_ref, a2.fe_ref, a2.tx_nom, a2.tx_matproref, a2.tx_tel,";
                    strSQL+=" 		       a2.tx_tie, a2.tx_cupcre, a2.tx_placre, a2.tx_forpag, a2.st_pro, a2.tx_cal, a2.tx_inf,";
                    strSQL+=" 		       a2.tx_carinf, a2.tx_obs1, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, a2.co_usrmod, a1.co_cli, a2.ne_numPro";
                    strSQL+=" 		  FROM tbm_solCre AS a1 INNER JOIN tbm_refcomsolcre AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" 		  INNER JOIN (";
                    strSQL+=" 			  SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 			  FROM tbt_uniCli";
                    strSQL+=" 			  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		          AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhRefComSolCre=new ArrayList();
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_REF, "" + rst.getString("co_ref"));
                        //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_REF, "" + rst.getString("fe_ref"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_REF, "" + rst.getString("fe_ref")==""?null:rst.getString("fe_ref"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_MAT_PRO_REF, "" + rst.getString("tx_matproref"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_TEL, "" + rst.getString("tx_tel"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_TIE, "" + rst.getString("tx_tie"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CUP_CRE, "" + rst.getString("tx_cupcre"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_PLA_CRE, "" + rst.getString("tx_placre"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FOR_PAG, "" + rst.getString("tx_forpag"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_EST_PRO, "" + rst.getString("st_pro"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CAL, "" + rst.getString("tx_cal"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_INF, "" + rst.getString("tx_inf"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CAR_INF, "" + rst.getString("tx_carinf"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        
                        arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_NUM_PRO, "" + rst.getString("ne_numPro"));
                        arlDatTbhRefComSolCre.add(arlRegTbhRefComSolCre);
                    }
                }
                stm.close();
                stm=null;
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
       
    
    private boolean inserta_tbhRefBanSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecRef="";
        String strFecApeCta="";
        String strFecIng="";
        String strFecUltMod="";
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhRefBanSolCre.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_refbansolcre(";
                    strSQL+="             co_emp, co_sol, co_his, co_ref, fe_ref, tx_nom, tx_age, tx_oficta,";
                    strSQL+="             tx_numcta, tx_duecta, co_ciuapecta, fe_apecta, tx_salprocta,";
                    strSQL+="             st_pro, st_credir, st_creind, ne_numpro, tx_obspro, tx_moncredir,";
                    strSQL+="             tx_moncreind, tx_inf, tx_carinf, tx_docdig, tx_obs1, st_reg,";
                    strSQL+="             fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_REF),2) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_REF),2) + ",";
                    strFecRef=objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_REF);
                    if( (strFecRef.equals(null)) || (strFecRef.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecRef, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_NOM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_AGE),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_OFI_CTA),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_CTA),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_DUE_CTA),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_CIU_APE_CTA),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA),2) + "',";
                    strFecApeCta=objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA);
                    if( (strFecApeCta.equals(null)) || (strFecApeCta.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecApeCta, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_SAL_PRO_CTA),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_PRO),0) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_DIR),2) + "',";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_IND),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_PRO),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_PRO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_DIR),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_IND),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_INF),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_CAR_INF),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_DOC_DIG),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING),2) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD),2) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD);
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("SQL DE inserta_tbhRefBanSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhRefBanSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhRefBanSolCre.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_his) AS co_his";
                    strSQL+=" FROM tbh_refBanSolCre AS a1 INNER JOIN tbm_solCre AS a2 ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_sol, " + intCodHisCodCliUni + " AS co_his, x.co_ref, x.fe_ref, x.tx_nom, x.tx_age, x.tx_oficta,";
                    strSQL+="        x.tx_numcta, x.tx_duecta, x.co_ciuapecta, x.fe_apecta, x.tx_salprocta,";
                    strSQL+="        x.st_pro, x.st_credir, x.st_creind, x.ne_numpro, x.tx_obspro, x.tx_moncredir,";
                    strSQL+="        x.tx_moncreind, x.tx_inf, x.tx_carinf, x.tx_docdig, x.tx_obs1, x.st_reg,";
                    strSQL+="        x.fe_ing, x.fe_ultmod, x.co_usring, x.co_usrmod,";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_sol, a2.co_ref, a2.fe_ref, a2.tx_nom, a2.tx_age, a2.tx_oficta, a2.tx_numcta,";
                    strSQL+=" 		       a2.tx_duecta, a2.co_ciuapecta, a2.fe_apecta, a2.tx_salprocta, a2.st_pro, a2.st_credir,";
                    strSQL+=" 		       a2.st_creind, a2.tx_moncredir, a2.tx_moncreind, a2.tx_inf, a2.tx_carinf, a2.tx_docdig,";
                    strSQL+=" 		       a2.tx_obs1, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, a2.co_usrmod, a2.ne_numpro,";
                    strSQL+=" 		       a2.tx_obspro, a1.co_cli";
                    strSQL+=" 		  FROM tbm_solCre AS a1 INNER JOIN tbm_refbansolcre AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" 		  INNER JOIN (";
                    strSQL+=" 			  SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 			  FROM tbt_uniCli";
                    strSQL+=" 			  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		          AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";                            
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhRefBanSolCre=new ArrayList();
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_REF, "" + rst.getString("co_ref"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_REF, "" + rst.getString("fe_ref"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_REF, "" + rst.getString("fe_ref")==""?null:rst.getString("fe_ref"));
                        
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_AGE, "" + rst.getString("tx_age"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OFI_CTA, "" + rst.getString("tx_oficta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_CTA, "" + rst.getString("tx_numcta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_DUE_CTA, "" + rst.getString("tx_duecta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_CIU_APE_CTA, "" + rst.getString("co_ciuapecta"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA, "" + rst.getString("fe_apecta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA, "" + rst.getString("fe_apecta")==""?null:rst.getString("fe_apecta"));
                        
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_SAL_PRO_CTA, "" + rst.getString("tx_salprocta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_PRO, "" + rst.getString("st_pro"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_DIR, "" + rst.getString("st_credir"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_IND, "" + rst.getString("st_creind"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_PRO, "" + rst.getString("ne_numpro"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_PRO, "" + rst.getString("tx_obspro"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_DIR, "" + rst.getString("tx_moncredir"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_IND, "" + rst.getString("tx_moncreind"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_INF, "" + rst.getString("tx_inf"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_CAR_INF, "" + rst.getString("tx_carinf"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_DOC_DIG, "" + rst.getString("tx_docdig"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        arlDatTbhRefBanSolCre.add(arlRegTbhRefBanSolCre);
                    }
                            
                            
                    //////////////////////////////////////////////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_his) AS co_his";
                    strSQL+=" FROM tbh_refBanSolCre AS a1 INNER JOIN tbm_solCre AS a2 ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);

                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+=" SELECT x.co_emp, x.co_sol, " + intCodHisCodCli + " AS co_his, x.co_ref, x.fe_ref, x.tx_nom, x.tx_age, x.tx_oficta,";
                    strSQL+="        x.tx_numcta, x.tx_duecta, x.co_ciuapecta, x.fe_apecta, x.tx_salprocta,";
                    strSQL+="        x.st_pro, x.st_credir, x.st_creind, x.ne_numpro, x.tx_obspro, x.tx_moncredir,";
                    strSQL+="        x.tx_moncreind, x.tx_inf, x.tx_carinf, x.tx_docdig, x.tx_obs1, x.st_reg,";
                    strSQL+="        x.fe_ing, x.fe_ultmod, x.co_usring, x.co_usrmod,";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_sol, a2.co_ref, a2.fe_ref, a2.tx_nom, a2.tx_age, a2.tx_oficta, a2.tx_numcta,";
                    strSQL+=" 		       a2.tx_duecta, a2.co_ciuapecta, a2.fe_apecta, a2.tx_salprocta, a2.st_pro, a2.st_credir,";
                    strSQL+=" 		       a2.st_creind, a2.tx_moncredir, a2.tx_moncreind, a2.tx_inf, a2.tx_carinf, a2.tx_docdig,";
                    strSQL+=" 		       a2.tx_obs1, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, a2.co_usrmod, a2.ne_numpro,";
                    strSQL+=" 		       a2.tx_obspro, a1.co_cli";
                    strSQL+=" 		  FROM tbm_solCre AS a1 INNER JOIN tbm_refbansolcre AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" 		  INNER JOIN (";
                    strSQL+=" 			  SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 			  FROM tbt_uniCli";
                    strSQL+=" 			  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		          AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";                            
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhRefBanSolCre=new ArrayList();
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_REF, "" + rst.getString("co_ref"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_REF, "" + rst.getString("fe_ref"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_REF, "" + rst.getString("fe_ref")==""?null:rst.getString("fe_ref"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_AGE, "" + rst.getString("tx_age"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OFI_CTA, "" + rst.getString("tx_oficta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_CTA, "" + rst.getString("tx_numcta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_DUE_CTA, "" + rst.getString("tx_duecta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_CIU_APE_CTA, "" + rst.getString("co_ciuapecta"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA, "" + rst.getString("fe_apecta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA, "" + rst.getString("fe_apecta")==""?null:rst.getString("fe_apecta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_SAL_PRO_CTA, "" + rst.getString("tx_salprocta"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_PRO, "" + rst.getString("st_pro"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_DIR, "" + rst.getString("st_credir"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_IND, "" + rst.getString("st_creind"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_PRO, "" + rst.getString("ne_numpro"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_PRO, "" + rst.getString("tx_obspro"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_DIR, "" + rst.getString("tx_moncredir"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_IND, "" + rst.getString("tx_moncreind"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_INF, "" + rst.getString("tx_inf"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_CAR_INF, "" + rst.getString("tx_carinf"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_DOC_DIG, "" + rst.getString("tx_docdig"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrHis"));
                        arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_uniCli"));
                        arlDatTbhRefBanSolCre.add(arlRegTbhRefBanSolCre);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    
    private boolean inserta_tbhDocDigSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        String strFecIng="";
        String strFecMod="";
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatTbhDocDigSolCre.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_docdigsolcre(";
                    strSQL+="             co_emp, co_sol, co_his, co_doc, tx_nom, tx_ubi, tx_obs1, st_reg,";
                    strSQL+="             fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_DOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_NOM),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_UBI),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_OBS_UNO),2) + "',";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_EST_REG),2) + "'";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING),2) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING);
                    if( (strFecIng.equals(null)) || (strFecIng.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD),2) + "',";
                    strFecMod=objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD);
                    if( (strFecMod.equals(null)) || (strFecMod.equals(""))  )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("SQL INSERTA EN inserta_tbhDocDigSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_tbhDocDigSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatTbhDocDigSolCre.clear();
        int intCodHisCodCli;
        int intCodHisCodCliUni;
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT MAX(co_his) as co_his";
                    strSQL+=" FROM tbh_docdigsolcre AS a1 INNER JOIN tbm_solCre AS a2 ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cli= " + strCodCliUni + "";
                    intCodHisCodCliUni=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);
                    if (intCodHisCodCliUni==-1)
                        return false;
                    
                    intCodHisCodCliUni++;
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_sol, " + intCodHisCodCliUni + " AS co_his, x.co_doc, x.tx_nom, x.tx_ubi, x.tx_obs1, x.st_reg,";
                    strSQL+="        x.fe_ing, x.fe_ultmod, x.co_usring, x.co_usrmod,";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_sol, a2.co_doc, a2.tx_nom, a2.tx_ubi,";
                    strSQL+=" 			a2.tx_obs1, a2.st_reg, a2.fe_ing,";
                    strSQL+=" 			a2.fe_ultmod, a2.co_usring, a2.co_usrmod, a1.co_cli";
                    strSQL+=" 		  FROM tbm_solCre AS a1 INNER JOIN tbm_docdigsolcre AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCliUni + ") AS x";
                    strSQL+=" 		  INNER JOIN (";
                    strSQL+=" 			  SELECT co_emp, co_cliUni, co_his, co_uni";
                    strSQL+=" 			  FROM tbt_uniCli";
                    strSQL+=" 			  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		          AND co_cliUni=" + strCodCliUni + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cliUni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDocDigSolCre=new ArrayList();
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_DOC, "" + rst.getString("co_doc"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_UBI, "" + rst.getString("tx_ubi"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        
                        //arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhDocDigSolCre.add(arlRegTbhDocDigSolCre);
                    }                    
                    
                    //////////////////////////////////////////////////////////////////////////////////////////
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_his) AS co_his";
                    strSQL+=" FROM tbh_docdigsolcre AS a1 INNER JOIN tbm_solCre AS a2 ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cli= " + strCodCli + "";
                    intCodHisCodCli=objUti.getNumeroRegistro(this, strConLoc, strUsrLoc, strPswLoc, strSQL);
                    if (intCodHisCodCli==-1)
                        return false;
                    
                    intCodHisCodCli++;
                    strSQL="";
                    strSQL+="SELECT x.co_emp, x.co_sol, " + intCodHisCodCli + " AS co_his, x.co_doc, x.tx_nom, x.tx_ubi, x.tx_obs1, x.st_reg,";
                    strSQL+="        x.fe_ing, x.fe_ultmod, x.co_usring, x.co_usrmod,";
                    strSQL+=" 	    cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his,";
                    strSQL+=" 	    " + objParSis.getCodigoUsuario() + " AS co_usrHis,";
                    strSQL+=" 	    y.co_uni AS co_uniCli FROM(";
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_sol, a2.co_doc, a2.tx_nom, a2.tx_ubi,";
                    strSQL+=" 			a2.tx_obs1, a2.st_reg, a2.fe_ing,";
                    strSQL+=" 			a2.fe_ultmod, a2.co_usring, a2.co_usrmod, a1.co_cli";
                    strSQL+=" 		  FROM tbm_solCre AS a1 INNER JOIN tbm_docdigsolcre AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol";
                    strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_cli=" + strCodCli + ") AS x";
                    strSQL+=" 		  INNER JOIN (";
                    strSQL+=" 			  SELECT co_emp, co_cli, co_his, co_uni";
                    strSQL+=" 			  FROM tbt_uniCli";
                    strSQL+=" 			  WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 		          AND co_cli=" + strCodCli + ") AS y";
                    strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegTbhDocDigSolCre=new ArrayList();
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_HIS, "" + rst.getString("co_his"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_DOC, "" + rst.getString("co_doc"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_NOM, "" + rst.getString("tx_nom"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_UBI, "" + rst.getString("tx_ubi"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_OBS_UNO, "" + rst.getString("tx_obs1"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        //arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing")==""?null:rst.getString("fe_ing"));
                        //arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod")==""?null:rst.getString("fe_ultmod"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_ING, "" + rst.getString("co_usring"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_MOD, "" + rst.getString("co_usrmod"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_HIS, "" + rst.getString("fe_his"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_HIS, "" + rst.getString("co_usrhis"));
                        arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_UNI_CLI, "" + rst.getString("co_unicli"));
                        arlDatTbhDocDigSolCre.add(arlRegTbhDocDigSolCre);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    
    private boolean update_tbmCabCotVen(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCabCotVen.size();i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_cabcotven";
                    strSQL+=" SET co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotVen, i, INT_ARL_TBM_CAB_COT_VEN_COD_CLI),2) + "";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotVen, i, INT_ARL_TBM_CAB_COT_VEN_COD_EMP),2) + "";
                    strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotVen, i, INT_ARL_TBM_CAB_COT_VEN_COD_LOC),2) + "";
                    strSQL+=" AND co_cot=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotVen, i, INT_ARL_TBM_CAB_COT_VEN_COD_DOC),2) + "";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
     
    private boolean select_update_tbmCabCotVen(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatSelUpdCabCotVen.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT co_emp, co_loc, co_cot, " + strCodCli + " AS co_cli FROM tbm_cabcotven";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdCabCotVen=new ArrayList();
                        arlRegSelUpdCabCotVen.add(INT_ARL_TBM_CAB_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdCabCotVen.add(INT_ARL_TBM_CAB_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegSelUpdCabCotVen.add(INT_ARL_TBM_CAB_COT_VEN_COD_DOC, "" + rst.getString("co_cot"));
                        arlRegSelUpdCabCotVen.add(INT_ARL_TBM_CAB_COT_VEN_COD_CLI, "" + rst.getString("co_cli"));                        
                        arlDatSelUpdCabCotVen.add(arlRegSelUpdCabCotVen);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    private boolean select_update_tbmDetRecDoc(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatSelUpdDetRecDoc.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT co_emp, co_loc, co_tipDoc, co_doc, " + strCodCli + " AS co_cli FROM tbm_detRecDoc";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //strSQL+=" AND co_loc="  + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    System.out.println("select_update_tbmDetRecDoc: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdDetRecDoc=new ArrayList();
                        arlRegSelUpdDetRecDoc.add(INT_ARL_TBM_DET_REC_DOC_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdDetRecDoc.add(INT_ARL_TBM_DET_REC_DOC_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegSelUpdDetRecDoc.add(INT_ARL_TBM_DET_REC_DOC_COD_TIPDOC, "" + rst.getString("co_tipDoc"));
                        arlRegSelUpdDetRecDoc.add(INT_ARL_TBM_DET_REC_DOC_COD_DOC, "" + rst.getString("co_doc"));
                        arlRegSelUpdDetRecDoc.add(INT_ARL_TBM_DET_REC_DOC_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdDetRecDoc.add(arlRegSelUpdDetRecDoc);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    private boolean update_tbmDetRecDoc(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdDetRecDoc.size();i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_detRecDoc";
                    strSQL+=" SET co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetRecDoc, i, INT_ARL_TBM_DET_REC_DOC_COD_CLI),2) + "";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetRecDoc, i, INT_ARL_TBM_DET_REC_DOC_COD_EMP),2) + "";
                    strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetRecDoc, i, INT_ARL_TBM_DET_REC_DOC_COD_LOC),2) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetRecDoc, i, INT_ARL_TBM_DET_REC_DOC_COD_TIPDOC),2) + "";
                    strSQL+=" AND co_doc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetRecDoc, i, INT_ARL_TBM_DET_REC_DOC_COD_DOC),2) + "";
                    System.out.println("update_tbmDetRecDoc: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    
    
    
    
    
    
    private boolean update_tbmDetCotVen(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdDetCotVen.size();i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_detcotven";
                    strSQL+=" SET co_prv=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetCotVen, i, INT_ARL_TBM_DET_COT_VEN_COD_PRV),2) + "";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetCotVen, i, INT_ARL_TBM_DET_COT_VEN_COD_EMP),2) + "";
                    strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetCotVen, i, INT_ARL_TBM_DET_COT_VEN_COD_LOC),2) + "";
                    strSQL+=" AND co_cot=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDetCotVen, i, INT_ARL_TBM_DET_COT_VEN_COD_DOC),2) + "";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_update_tbmDetCotVen(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatSelUpdDetCotVen.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT co_emp, co_loc, co_cot, " + strCodCli + " AS co_prv FROM tbm_detcotven";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_prv=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdDetCotVen=new ArrayList();
                        arlRegSelUpdDetCotVen.add(INT_ARL_TBM_DET_COT_VEN_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdDetCotVen.add(INT_ARL_TBM_DET_COT_VEN_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegSelUpdDetCotVen.add(INT_ARL_TBM_DET_COT_VEN_COD_DOC, "" + rst.getString("co_cot"));
                        arlRegSelUpdDetCotVen.add(INT_ARL_TBM_DET_COT_VEN_COD_PRV, "" + rst.getString("co_prv"));
                        arlDatSelUpdDetCotVen.add(arlRegSelUpdDetCotVen);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    
    private boolean update_tbmCabCotCom(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCabCotCom.size();i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_cabcotcom";
                    strSQL+=" SET co_prv=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotCom, i, INT_ARL_TBM_CAB_COT_COM_COD_PRV),2) + "";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotCom, i, INT_ARL_TBM_CAB_COT_COM_COD_EMP),2) + "";
                    strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotCom, i, INT_ARL_TBM_CAB_COT_COM_COD_LOC),2) + "";
                    strSQL+=" AND co_cot=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabCotCom, i, INT_ARL_TBM_CAB_COT_COM_COD_DOC),2) + "";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_update_tbmCabCotCom(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strCodCli="";
        arlDatSelUpdCabCotCom.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strSQL="";
                    strSQL+="SELECT co_emp, co_loc, co_cot, " + strCodCli + " AS co_prv FROM tbm_cabcotcom";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_prv=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdCabCotCom=new ArrayList();
                        arlRegSelUpdCabCotCom.add(INT_ARL_TBM_CAB_COT_COM_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdCabCotCom.add(INT_ARL_TBM_CAB_COT_COM_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegSelUpdCabCotCom.add(INT_ARL_TBM_CAB_COT_COM_COD_DOC, "" + rst.getString("co_cot"));
                        arlRegSelUpdCabCotCom.add(INT_ARL_TBM_CAB_COT_COM_COD_PRV, "" + rst.getString("co_prv"));
                        arlDatSelUpdCabCotCom.add(arlRegSelUpdCabCotCom);
                    }
                }
                stm.close();
                stm=null;
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
    
    
    
   

    private boolean update_tbm_cabGuiRem(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCabGuiRem.size();i++){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabGuiRem";
                        strSQL+=" SET co_cliDes=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_COD_CLI),2) + "";
                        strSQL+=", tx_rucCliDes=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_RUC_CLI),0) + "";
                        strSQL+=", tx_nomCliDes=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_NOM_CLI),0) + "";
                        strSQL+=", tx_dirCliDes=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_DIR_CLI),0) + "";
                        strSQL+=", tx_telCliDes=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_TEL_CLI),0) + "";
                        strSQL+=", tx_ciuclides=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_CIU_CLI),0) + "";
                        strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_COD_EMP),2) + "";
                        strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_COD_LOC),2) + "";
                        strSQL+=" AND co_tipDoc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_COD_TIP_DOC),2) + "";
                        strSQL+=" AND co_doc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabGuiRem, i, INT_ARL_TBM_CAB_GUI_REM_COD_DOC),2) + "";
//                        System.out.println("SQL DE UPDATE tbm_cabMovInv: " + strSQL);
                        stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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



    private boolean select_update_tbmCabGuiRem(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="", strRucCli="", strNomCli="", strDirCli="", strTelCli="", strCiuCli="";
        arlDatSelUpdCabGuiRem.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    strRucCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_RUC_CLI);
                    strNomCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_NOM_CLI);
                    strDirCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_DIR_CLI);
                    strTelCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_TLF_CLI);
                    strCiuCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_CIU_CLI);
                        //PARA LOS DATOS Q SE DESEAN UNIFICAR (DATOS DE TABLA DOS)
                        strSQL="";
                        strSQL+="SELECT co_emp, co_loc, co_tipDoc, co_doc, " + strCodCli + " AS co_cliDes ";
                        strSQL+=", " + strRucCli + " AS tx_rucCliDes";
                        strSQL+=",'" + strNomCli + "' AS tx_nomCliDes";
                        strSQL+=",'" + strDirCli + "' AS tx_dirCliDes";
                        strSQL+=",'" + strTelCli + "' AS tx_telCliDes";
                        strSQL+=",'" + strCiuCli + "' AS tx_ciuclides";
                        strSQL+=" FROM tbm_cabGuiRem";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_cliDes=" + strCodCliUni + "";
                        System.out.println("nose: " + strSQL);
                        rst=stm.executeQuery(strSQL);



                        while(rst.next()){
                            arlRegSelUpdCabGuiRem=new ArrayList();
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_COD_EMP, "" + rst.getString("co_emp"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_COD_LOC, "" + rst.getString("co_loc"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_COD_TIP_DOC, "" + rst.getString("co_tipDoc"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_COD_DOC, "" + rst.getString("co_doc"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_COD_CLI, "" + rst.getString("co_cliDes"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_RUC_CLI, "" + rst.getString("tx_rucCliDes"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_NOM_CLI, "" + rst.getString("tx_nomCliDes"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_DIR_CLI, "" + rst.getString("tx_dirCliDes"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_TEL_CLI, "" + rst.getString("tx_telCliDes"));
                            arlRegSelUpdCabGuiRem.add(INT_ARL_TBM_CAB_GUI_REM_CIU_CLI, "" + rst.getString("tx_ciuclides"));

                            arlDatSelUpdCabGuiRem.add(arlRegSelUpdCabGuiRem);
                        }
                }
                stm.close();
                stm=null;
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





    private boolean select_update_tbmCabMovInv(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="", strRucCliUni="", strNomCliUni="", strDirCliUni="", strTelCliUni="", strCiuCliUni="";
        String strCodCli="", strRucCli="", strNomCli="", strDirCli="", strTelCli="", strCiuCli="";
        arlDatSelUpdCabMovInv.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                        //PARA LOS DATOS Q SE DESEAN UNIFICAR (DATOS DE TABLA DOS)
                        strSQL="";
                        strSQL+="SELECT co_emp, co_loc, co_tipDoc, co_doc, " + strCodCli + " AS co_cli FROM tbm_cabMovInv";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_cli=" + strCodCliUni + "";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            arlRegSelUpdCabMovInv=new ArrayList();
                            arlRegSelUpdCabMovInv.add(INT_ARL_TBM_CAB_MOV_INV_COD_EMP, "" + rst.getString("co_emp"));
                            arlRegSelUpdCabMovInv.add(INT_ARL_TBM_CAB_MOV_INV_COD_LOC, "" + rst.getString("co_loc"));
                            arlRegSelUpdCabMovInv.add(INT_ARL_TBM_CAB_MOV_INV_COD_TIP_DOC, "" + rst.getString("co_tipDoc"));
                            arlRegSelUpdCabMovInv.add(INT_ARL_TBM_CAB_MOV_INV_COD_DOC, "" + rst.getString("co_doc"));
                            arlRegSelUpdCabMovInv.add(INT_ARL_TBM_CAB_MOV_INV_COD_CLI, "" + rst.getString("co_cli"));
                            arlDatSelUpdCabMovInv.add(arlRegSelUpdCabMovInv);
                        }
                }
                stm.close();
                stm=null;
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
    


    private boolean update_tbmCabMovInv(Connection conTmpLoc){
        boolean blnRes=true;
        String strCodCliUni="", strRucCliUni="", strNomCliUni="", strDirCliUni="", strTelCliUni="", strCiuCliUni="";
        String strCodCli="", strRucCli="", strNomCli="", strDirCli="", strTelCli="", strCiuCli="";

        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCabMovInv.size();i++){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabMovInv";
                        strSQL+=" SET co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabMovInv, i, INT_ARL_TBM_CAB_MOV_INV_COD_CLI),2) + "";
                        strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabMovInv, i, INT_ARL_TBM_CAB_MOV_INV_COD_EMP),2) + "";
                        strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabMovInv, i, INT_ARL_TBM_CAB_MOV_INV_COD_LOC),2) + "";
                        strSQL+=" AND co_tipDoc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabMovInv, i, INT_ARL_TBM_CAB_MOV_INV_COD_TIP_DOC),2) + "";
                        strSQL+=" AND co_doc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabMovInv, i, INT_ARL_TBM_CAB_MOV_INV_COD_DOC),2) + "";
//                        System.out.println("SQL DE UPDATE tbm_cabMovInv: " + strSQL);
                        stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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




    
    private boolean update_tbmBenChq(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdBenChq.size();i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_benChq";
                    strSQL+=" SET co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdBenChq, i, INT_ARL_TBM_BEN_CHQ_COD_CLI),2) + "";
                    strSQL+=", st_reg='" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdBenChq, i, INT_ARL_TBM_BEN_CHQ_EST_REG),2) + "'";
                    strSQL+=", co_reg=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdBenChq, i, INT_ARL_TBM_BEN_CHQ_COD_REG),2) + "";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdBenChq, i, INT_ARL_TBM_BEN_CHQ_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdBenChq, i, INT_ARL_TBM_BEN_CHQ_COD_CLI_UNI),2) + "";
                    strSQL+=" AND co_reg=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdBenChq, i, INT_ARL_TBM_BEN_CHQ_COD_REG_FIL),2) + "";
//                    System.out.println("UPDATE DE ESTADO EN BENEFICIARIO: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
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
    
    private boolean select_update_tbmBenChq(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        int intMaxCodRegQur=0;
        arlDatSelUpdBenChq.clear();
        try{
            if( conTmpMai!= null){
                
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    stm=conTmpMai.createStatement();
                    arlDatBenChq.clear();
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                            arlDatBenChq.clear();
                            strSQL="";
                            strSQL+="SELECT co_emp, co_cli, co_reg, tx_benChq, st_reg";
                            strSQL+=" from tbm_benChq";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_cli=" + strCodCliUni + "";
                            strSQL+=" ORDER BY co_reg";
//                            System.out.println("SELECT TBM_BENCHQ: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegBenChq=new ArrayList();
                                arlRegBenChq.add(INT_ARL_BEN_CHQ_COD_EMP, "" + rst.getString("co_emp"));
                                arlRegBenChq.add(INT_ARL_BEN_CHQ_COD_CLI, "" + rst.getString("co_cli"));
                                arlRegBenChq.add(INT_ARL_BEN_CHQ_COD_REG, "" + rst.getString("co_reg"));
                                arlDatBenChq.add(arlRegBenChq);
                            }
                            strSQL="";
                            strSQL+="SELECT MAX(co_reg) AS maxCodReg";
                            strSQL+=" from tbm_benChq";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_cli=" + strCodCli + "";
//                            System.out.println("SQL DEL MAXIMO DE CODIGO REGISTRO BENEFICIARIO: " + strSQL);
                            rst=stm.executeQuery(strSQL );
                            if(rst.next()){
                                intMaxCodRegQur=rst.getInt("maxCodReg");
                            }
                            
                            for(int j=0;j<arlDatBenChq.size(); j++){

                                intMaxCodRegQur++;
                                strSQL="";
                                strSQL+="SELECT " + objUti.getIntValueAt(arlDatBenChq, j, INT_ARL_BEN_CHQ_COD_EMP) + " AS co_emp, " + strCodCli + " AS co_cli,";
                                strSQL+=" 'I' AS st_reg, " + intMaxCodRegQur + " AS co_reg, " + strCodCliUni + " AS co_cliUni,";
                                strSQL+=" " + objUti.getIntValueAt(arlDatBenChq, j, INT_ARL_BEN_CHQ_COD_REG) + " AS co_regFil FROM tbm_benChq";
                                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatBenChq, j, INT_ARL_BEN_CHQ_COD_EMP) + "";
                                strSQL+=" AND co_cli=" + strCodCliUni + "";
                                strSQL+=" AND co_reg=" + objUti.getIntValueAt(arlDatBenChq, j, INT_ARL_BEN_CHQ_COD_REG) + "";
                                rst=stm.executeQuery(strSQL);
                                while(rst.next()){
                                    arlRegSelUpdBenChq=new ArrayList();
                                    arlRegSelUpdBenChq.add(INT_ARL_TBM_BEN_CHQ_COD_EMP, "" + rst.getString("co_emp"));
                                    arlRegSelUpdBenChq.add(INT_ARL_TBM_BEN_CHQ_COD_CLI, "" + rst.getString("co_cli"));
                                    arlRegSelUpdBenChq.add(INT_ARL_TBM_BEN_CHQ_EST_REG, "" + rst.getString("st_reg"));
                                    arlRegSelUpdBenChq.add(INT_ARL_TBM_BEN_CHQ_COD_REG, "" + rst.getString("co_reg"));
                                    arlRegSelUpdBenChq.add(INT_ARL_TBM_BEN_CHQ_COD_CLI_UNI, "" + rst.getString("co_cliUni"));
                                    arlRegSelUpdBenChq.add(INT_ARL_TBM_BEN_CHQ_COD_REG_FIL, "" + rst.getString("co_regFil"));
                                    arlDatSelUpdBenChq.add(arlRegSelUpdBenChq);
                                }
                            }
                    stm.close();
                    stm=null;
                    rst.close();
                    rst=null;
                }
                
                
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
    
    
    private boolean update_tbmCabPag(Connection conTmpLoc){
        boolean blnRes=true;
        
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCabPag.size();i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_cabPag";
                    strSQL+=" SET co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabPag, i, INT_ARL_TBM_CAB_PAG_COD_CLI),2) + "";
                    strSQL+=", co_ben=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabPag, i, INT_ARL_TBM_CAB_PAG_COD_BEN_CHQ),2) + "";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabPag, i, INT_ARL_TBM_CAB_PAG_COD_EMP),2) + "";
                    strSQL+=" AND co_loc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabPag, i, INT_ARL_TBM_CAB_PAG_COD_LOC),2) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabPag, i, INT_ARL_TBM_CAB_PAG_COD_TIP_DOC),2) + "";
                    strSQL+=" AND co_doc=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCabPag, i, INT_ARL_TBM_CAB_PAG_COD_DOC),2) + "";
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_update_tbmCabPag(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdCabPag.clear();
        try{
            if( con!= null){
                stm=con.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_loc, co_tipDoc, co_doc, " + strCodCli + " AS co_cli, 1 AS co_ben FROM tbm_cabPag";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdCabPag=new ArrayList();
                        arlRegSelUpdCabPag.add(INT_ARL_TBM_CAB_PAG_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdCabPag.add(INT_ARL_TBM_CAB_PAG_COD_LOC, "" + rst.getString("co_loc"));
                        arlRegSelUpdCabPag.add(INT_ARL_TBM_CAB_PAG_COD_TIP_DOC, "" + rst.getString("co_tipDoc"));
                        arlRegSelUpdCabPag.add(INT_ARL_TBM_CAB_PAG_COD_DOC, "" + rst.getString("co_doc"));
                        arlRegSelUpdCabPag.add(INT_ARL_TBM_CAB_PAG_COD_CLI, "" + rst.getString("co_cli"));
                        arlRegSelUpdCabPag.add(INT_ARL_TBM_CAB_PAG_COD_BEN_CHQ, "" + rst.getString("co_ben"));
                        arlDatSelUpdCabPag.add(arlRegSelUpdCabPag);                        
                    }
                }
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
    
    
    
    private boolean delete_tbmDirCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdDirCli.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_dirCli";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDirCli, i, INT_ARL_TBM_DIR_CLI_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDirCli, i, INT_ARL_TBM_DIR_CLI_COD_CLI),2) + "";
                    stm.executeUpdate(strSQL);
                }
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

    private boolean select_delete_tbmDirCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdDirCli.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_dirCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";                    
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdDirCli=new ArrayList();
                        arlRegSelUpdDirCli.add(INT_ARL_TBM_DIR_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdDirCli.add(INT_ARL_TBM_DIR_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdDirCli.add(arlRegSelUpdDirCli);
                    }
                }
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

    
        
    private boolean delete_tbmConCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdConCli.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_conCli";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdConCli, i, INT_ARL_TBM_CON_CLI_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdConCli, i, INT_ARL_TBM_CON_CLI_COD_CLI),2) + "";
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbmConCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdConCli.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_conCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdConCli=new ArrayList();
                        arlRegSelUpdConCli.add(INT_ARL_TBM_CON_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdConCli.add(INT_ARL_TBM_CON_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdConCli.add(arlRegSelUpdConCli);
                    }
                }
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
    
        
    
    private boolean delete_tbmForPagCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdForPagCli.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_forPagCli";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdForPagCli, i, INT_ARL_TBM_FOR_PAG_CLI_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdForPagCli, i, INT_ARL_TBM_FOR_PAG_CLI_COD_CLI),2) + "";
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbmForPagCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdForPagCli.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_forPagCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdForPagCli=new ArrayList();
                        arlRegSelUpdForPagCli.add(INT_ARL_TBM_FOR_PAG_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdForPagCli.add(INT_ARL_TBM_FOR_PAG_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdForPagCli.add(arlRegSelUpdForPagCli);
                    }
                }
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
    
    
    
    private boolean delete_tbmObsCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdObsCli.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_obsCli";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdObsCli, i, INT_ARL_TBM_OBS_CLI_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdObsCli, i, INT_ARL_TBM_OBS_CLI_COD_CLI),2) + "";
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbmObsCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdObsCli.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_obsCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdObsCli=new ArrayList();
                        arlRegSelUpdObsCli.add(INT_ARL_TBM_OBS_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdObsCli.add(INT_ARL_TBM_OBS_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdObsCli.add(arlRegSelUpdObsCli);
                    }
                }
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
    
    
    
    
    private boolean delete_tbmAccEmpCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdAccEmpCli.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_accEmpCli";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdAccEmpCli, i, INT_ARL_TBM_ACC_EMP_CLI_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdAccEmpCli, i, INT_ARL_TBM_ACC_EMP_CLI_COD_CLI),2) + "";
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbmAccEmpCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdAccEmpCli.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_accEmpCli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdAccEmpCli=new ArrayList();
                        arlRegSelUpdAccEmpCli.add(INT_ARL_TBM_ACC_EMP_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdAccEmpCli.add(INT_ARL_TBM_ACC_EMP_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdAccEmpCli.add(arlRegSelUpdAccEmpCli);
                    }
                }
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
    
  
    private boolean delete_tbmSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        
        System.out.println("EN DELETE SOLICITUD CREDITO: " + arlDatSelUpdSolCre.toString());
        System.out.println("STRING DE CONEXION: " + strConLoc);
         
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdSolCre.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_solCre";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdSolCre, i, INT_ARL_TBM_SOL_CRE_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdSolCre, i, INT_ARL_TBM_COL_CRE_COD_CLI),2) + "";
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbmSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdSolCre.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_solCre";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdSolCre=new ArrayList();
                        arlRegSelUpdSolCre.add(INT_ARL_TBM_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdSolCre.add(INT_ARL_TBM_COL_CRE_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdSolCre.add(arlRegSelUpdSolCre);
                    }
                }
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

    
    private boolean delete_tbmRefComSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdRefComSolCre.size();i++){
                   
                    strSQL="";
                    strSQL+="UPDATE tbm_refComSolCre";
                    strSQL+=" set st_reg='D'";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdRefComSolCre, i, INT_ARL_TBM_REF_COM_SOL_CRE_COD_EMP),2) + "";
                    strSQL+=" AND co_sol=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdRefComSolCre, i, INT_ARL_TBM_REF_COM_SOL_CRE_COD_SOL),2) + "";
//                    System.out.println("SQL DE UPDATE PARA tbm_refComSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);

//                    strSQL="";
//                    strSQL+="DELETE FROM tbm_refComSolCre";
//                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdRefComSolCre, i, INT_ARL_TBM_REF_COM_SOL_CRE_COD_EMP),2) + " AND st_reg='D'";
//                    System.out.println("SQL DE DELETE PARA tbm_refComSolCre: " + strSQL);
//                    stm.executeUpdate(strSQL);
                }
                
                strSQL="";
                strSQL+="DELETE FROM tbm_refComSolCre";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='D'";
//                System.out.println("SQL DE DELETE PARA tbm_refComSolCre: " + strSQL);
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
    
    private boolean select_delete_tbmRefComSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdRefComSolCre.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+=" SELECT co_emp, co_sol,st_reg";
                    strSQL+=" FROM tbm_solCre";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + strCodCliUni + "";
//                    System.out.println("SQL DE UPDATE PARA tbm_refComSolCre: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdRefComSolCre=new ArrayList();
                        arlRegSelUpdRefComSolCre.add(INT_ARL_TBM_REF_COM_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdRefComSolCre.add(INT_ARL_TBM_REF_COM_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegSelUpdRefComSolCre.add(INT_ARL_TBM_REF_COM_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        arlDatSelUpdRefComSolCre.add(arlRegSelUpdRefComSolCre);
                    }

                }
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
    
    
    private boolean delete_tbmRefBanSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdRefBanSolCre.size();i++){
                    
                    strSQL="";
                    strSQL+="UPDATE tbm_refBanSolCre";
                    strSQL+=" set st_reg='D'";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdRefBanSolCre, i, INT_ARL_TBM_REF_BAN_SOL_CRE_COD_EMP),2) + "";
                    strSQL+=" AND co_sol=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdRefBanSolCre, i, INT_ARL_TBM_REF_BAN_SOL_CRE_COD_SOL),2) + "";
                    stm.executeUpdate(strSQL);
                    
//                    strSQL="";
//                    strSQL+="DELETE FROM tbm_refBanSolCre";
//                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='D'";
//                    stm.executeUpdate(strSQL);
                }
                
                strSQL="";
                strSQL+="DELETE FROM tbm_refBanSolCre";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='D'";
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
    
    private boolean select_delete_tbmRefBanSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdRefBanSolCre.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_sol,st_reg";
                    strSQL+=" FROM tbm_solCre";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + strCodCliUni + "";                    
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdRefBanSolCre=new ArrayList();
                        arlRegSelUpdRefBanSolCre.add(INT_ARL_TBM_REF_BAN_SOL_CRE_COD_EMP, "" +rst.getString("co_emp"));
                        arlRegSelUpdRefBanSolCre.add(INT_ARL_TBM_REF_BAN_SOL_CRE_COD_SOL, "" +rst.getString("co_sol"));
                        arlRegSelUpdRefBanSolCre.add(INT_ARL_TBM_REF_BAN_SOL_CRE_EST_REG, "" +rst.getString("st_reg"));
                        arlDatSelUpdRefBanSolCre.add(arlRegSelUpdRefBanSolCre);
                    }
                }
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
    
    
        
    private boolean delete_tbmdocDigSolCre(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdDocDigSolCre.size();i++){
                    
                    strSQL="";
                    strSQL+="UPDATE tbm_docDigsolCre";
                    strSQL+=" set st_reg='D'";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDocDigSolCre, i, INT_ARL_TBM_DOC_DIG_SOL_CRE_COD_EMP),2) + "";
                    strSQL+=" AND co_sol=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdDocDigSolCre, i, INT_ARL_TBM_DOC_DIG_SOL_CRE_COD_SOL),2) + "";
                    stm.executeUpdate(strSQL);                    
//                    strSQL="";
//                    strSQL+="DELETE FROM tbm_docDigsolCre";
//                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='D'";
//                    stm.executeUpdate(strSQL);
                }
                
                strSQL="";
                strSQL+="DELETE FROM tbm_docDigsolCre";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg='D'";
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
    
    private boolean select_delete_tbmdocDigSolCre(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdDocDigSolCre.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+=" 	SELECT co_emp, co_sol, st_reg";
                    strSQL+=" 	FROM tbm_solCre";
                    strSQL+=" 	WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + strCodCliUni + "";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdDocDigSolCre=new ArrayList();
                        arlRegSelUpdDocDigSolCre.add(INT_ARL_TBM_DOC_DIG_SOL_CRE_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdDocDigSolCre.add(INT_ARL_TBM_DOC_DIG_SOL_CRE_COD_SOL, "" + rst.getString("co_sol"));
                        arlRegSelUpdDocDigSolCre.add(INT_ARL_TBM_DOC_DIG_SOL_CRE_EST_REG, "" + rst.getString("st_reg"));
                        arlDatSelUpdDocDigSolCre.add(arlRegSelUpdDocDigSolCre);
                    }
                }
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
    
    
    
    
    private boolean delete_tbmCli(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCli.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbm_cli";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCli, i, INT_ARL_TBM_CLI_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCli, i, INT_ARL_TBM_CLI_COD_CLI),2) + "";
//                    System.out.println("SQL DE DELETE EN tbm_cli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbmCli(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdCli.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbm_cli";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + strCodCliUni + "";
//                    System.out.println("SQL DE DELETE EN tbm_cli: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdCli=new ArrayList();
                        arlRegSelUpdCli.add(INT_ARL_TBM_CLI_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdCli.add(INT_ARL_TBM_CLI_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdCli.add(arlRegSelUpdCli);
                    }
                }
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
    
    
    private boolean delete_tbrCliLoc(Connection conTmpLoc){
        boolean blnRes=true;
        try{
            if( conTmpLoc!= null){
                stm=conTmpLoc.createStatement();
                for(int i=0; i<arlDatSelUpdCliLoc.size();i++){
                    strSQL="";
                    strSQL+="DELETE FROM tbr_cliLoc";
                    strSQL+=" WHERE co_emp=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCliLoc, i, INT_ARL_TBR_CLI_LOC_COD_EMP),2) + "";
                    strSQL+=" AND co_cli=" + objUti.codificar(objUti.getStringValueAt(arlDatSelUpdCliLoc, i, INT_ARL_TBR_CLI_LOC_COD_CLI),2) + "";
//                    System.out.println("SQL DE DELETE EN tbr_cliLoc: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
    
    private boolean select_delete_tbrCliLoc(Connection conTmpMai){
        boolean blnRes=true;
        String strCodCliUni="";
        String strCodCli="";
        arlDatSelUpdCliLoc.clear();
        try{
            if( conTmpMai!= null){
                stm=conTmpMai.createStatement();
                for(int i=0; i<arlDatTmpTbl.size();i++){
                    strCodCliUni=objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI_UNI);
                    strCodCli   =objUti.getStringValueAt(arlDatTmpTbl, i, INT_ARL_TMP_COD_CLI);
                    
                    strSQL="";
                    strSQL+="SELECT co_emp, co_cli FROM tbr_cliLoc";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + strCodCliUni + "";
//                    System.out.println("SQL DE DELETE EN tbr_cliLoc: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegSelUpdCliLoc=new ArrayList();
                        arlRegSelUpdCliLoc.add(INT_ARL_TBR_CLI_LOC_COD_EMP, "" + rst.getString("co_emp"));
                        arlRegSelUpdCliLoc.add(INT_ARL_TBR_CLI_LOC_COD_CLI, "" + rst.getString("co_cli"));
                        arlDatSelUpdCliLoc.add(arlRegSelUpdCliLoc);
                    }
                }
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
    
    
    
    
    
    /**Permite cargar el driver de conexi�n local
     */
    private void cargarDrv(){
        try{
            Class.forName(strDrv);
        }
        catch(java.lang.ClassNotFoundException e) {
            objUti.mostrarMsgErr_F1(this, e); 
            System.err.println(e.getMessage());
        }         
    }
    
    
    private Connection conexDB(String conexion, String usuario, String clave){

        try{

            System.out.println("1- usuario: " + usuario);
            System.out.println("1- clave: " + clave);

            conTmpRea=java.sql.DriverManager.getConnection(conexion,usuario,clave);
//            conTmpRea=java.sql.DriverManager.getConnection(conexion,"postgres","**++serProSisZaf++**");
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            con=null;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return conTmpRea;
    }
    
    

    private boolean cargarReg(){
        boolean blnRes=false;
        arlDatCfgRegCnxRem.clear();
        boolean blnCarInfArl=false;
        
        lblMsgSis.setText("Procesando");
        String strTmpTitEmp="";

        arlDatCfgRegCnxRem.clear();
        arlDatTmpTbl.clear();

        try{
            conMai=conexDB(strConIni, strUsrIni, strPswIni);
            System.out.println("linea 12119");
            if(conMai!=null){
//                conMai.setAutoCommit(false);
                
                stmMai=conMai.createStatement();
                strSQL="";
                strSQL+="select ";
                strSQL+=" co_reg, co_grp, co_regorg, co_regdes, tx_freact, st_reg";
                strSQL+=" from tbm_cfgbasdatrep as a1";
                strSQL+=" where a1.co_grp=" + INT_COD_GRP_CNF + "  and a1.st_reg='A' AND co_regOrg=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" ORDER BY a1.co_regdes";
                System.out.println("tbm_cfgbasdatrep: " + strSQL);
                rstMai=stmMai.executeQuery(strSQL);

                for(int i=0;rstMai.next();i++){
                    arlRegCfgRegCnxRem=new ArrayList();
                    arlRegCfgRegCnxRem.add(INT_COL_CFG_COD_REG, rstMai.getString("co_reg"));                    
                    arlRegCfgRegCnxRem.add(INT_COL_CFG_COD_GRP, rstMai.getString("co_grp"));
                    arlRegCfgRegCnxRem.add(INT_COL_CFG_REG_ORI, rstMai.getString("co_regorg"));
                    arlRegCfgRegCnxRem.add(INT_COL_CFG_REG_DES, rstMai.getString("co_regdes"));                    
                    arlRegCfgRegCnxRem.add(INT_COL_CFG_FREC_ACT, rstMai.getString("tx_freact"));
                    arlRegCfgRegCnxRem.add(INT_COL_CFG_EST_REG, rstMai.getString("st_reg"));
                    arlDatCfgRegCnxRem.add(arlRegCfgRegCnxRem);
                }
//                System.out.println("El arlDatCfgRegCnxRem: "+arlDatCfgRegCnxRem.toString());//contiene la informacion de los servidores q se deben actualizar
                
                                              
                //PARA LEER TBM_BASDAT
                if(intVarEjePrcRem==1){
                    intVarEjePrcRem=0;
                    

                    System.out.println("ERROR : " + arlDatCfgRegCnxRem.toString());
//                for (int j=0;j<=0;j++){
                //for (int j=0;j<arlDatCfgRegCnxRem.size();j++){
                    strSQL="";
                    strSQL+="select ";
                    strSQL+=" co_reg, tx_drvcon, tx_strCon, tx_usrcon, tx_clacon, co_emp, tx_nom";
                    strSQL+=" from tbm_basdat as a2";
                    strSQL+=" where a2.co_reg=" + objUti.getStringValueAt(arlDatCfgRegCnxRem, 0, INT_COL_CFG_REG_ORI) + "";
                    strSQL+=" and st_reg='A'";
                    System.out.println("TBM_BASDAT: " + strSQL);
                    rstMai=stmMai.executeQuery(strSQL);
                    if(rstMai.next()){
                        strConLoc=rstMai.getString("tx_strCon");
                        strUsrLoc=rstMai.getString("tx_usrcon");
                        strPswLoc=rstMai.getString("tx_clacon");

                        System.out.println("strConIni: " + strConIni);
                        System.out.println("strUsrLoc: " + strUsrLoc);
                        System.out.println("strPswLoc: " + strPswLoc);



                        strUsrLoc=objJCEAlgAES.desencriptar("7132A142185AF67FE3543C1727153A14", "864C302EC53A4E15022519AD01726536",strUsrLoc);
                        strPswLoc=objJCEAlgAES.desencriptar("7132A142185AF67FE3543C1727153A14", "864C302EC53A4E15022519AD01726536", strPswLoc);


                        System.out.println("2- strConIni: " + strConIni);
                        System.out.println("2- strUsrLoc: " + strUsrLoc);
                        System.out.println("2- strPswLoc: " + strPswLoc);



                        con=conexDB(strConLoc, strUsrLoc, strPswLoc);
                        System.out.println("linea 12167");
                        
                        datFecAux=objUti.getFechaServidor(strConLoc, strUsrLoc, strPswLoc, objParSis.getQueryFechaHoraBaseDatos());
                        //datFecAux=objUti.getFechaServidor(strConLoc, "postgres", "**++serProSisZaf++**", objParSis.getQueryFechaHoraBaseDatos());

                        
                        
                        
                        if(con!=null){
                                stm=con.createStatement();
                                strSQL="";
                                strSQL+=" SELECT x.co_emp AS codEmp, x.co_cli AS codCli, x.tx_ide AS ideCli, x.tx_nom AS nomCli, x.tx_dir AS dirCli, x.tx_tel AS telCli, x.co_ciu AS ciuCli,";
                                strSQL+=" y.co_cliUni AS codCliUni, y.tx_ide AS ideCliUni, y.tx_nom AS nomCliUni, y.tx_dir AS dirCliUni, y.tx_tel AS telCliUni, y.co_ciu AS ciuCliUni";
                                strSQL+=" FROM(";
                                strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.co_cliUni, a2.tx_ide, a2.tx_nom, a2.tx_dir, a2.tx_tel, a2.co_ciu";
                                strSQL+=" 	FROM tbt_cliuni AS a1";
                                strSQL+=" 	INNER JOIN tbm_cli AS a2";
                                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.st_reg='A') AS x";
                                strSQL+=" INNER JOIN (";
                                strSQL+=" 	SELECT a1.co_emp, a1.co_cli, a1.co_cliUni, a2.tx_ide, a2.tx_nom, a2.tx_dir, a2.tx_tel, a2.co_ciu";
                                strSQL+=" 	FROM tbt_cliuni AS a1";
                                strSQL+=" 	INNER JOIN tbm_cli AS a2";
                                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.st_reg='A') AS y";
                                strSQL+=" ON x.co_emp=y.co_emp AND x.co_cli=y.co_cli";
//                                System.out.println("SQL DE consultarRegTblTmpUniCli: " + strSQL);
                                rst=stm.executeQuery(strSQL);
                                while(rst.next()){
                                    arlRegTmpTbl=new ArrayList();
                                    arlRegTmpTbl.add(INT_ARL_TMP_COD_EMP,     "" + rst.getString("codEmp"));
                                    //PARA LOS CLIENTES QUE VAN A PREVALECER
                                    arlRegTmpTbl.add(INT_ARL_TMP_COD_CLI,     "" + rst.getString("codCli"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_RUC_CLI,     "" + rst.getString("ideCli"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_NOM_CLI,     "" + rst.getString("nomCli"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_DIR_CLI,     "" + rst.getString("dirCli"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_TLF_CLI,     "" + rst.getString("telCli"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_CIU_CLI,     "" + rst.getString("ciuCli"));
                                    //PARA LOS CLIENTES QUE VAN A DESAPARECER
                                    arlRegTmpTbl.add(INT_ARL_TMP_COD_CLI_UNI, "" + rst.getString("codCliUni"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_RUC_CLI_UNI, "" + rst.getString("ideCliUni"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_NOM_CLI_UNI, "" + rst.getString("nomCliUni"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_DIR_CLI_UNI, "" + rst.getString("dirCliUni"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_TLF_CLI_UNI, "" + rst.getString("telCliUni"));
                                    arlRegTmpTbl.add(INT_ARL_TMP_CIU_CLI_UNI, "" + rst.getString("ciuCliUni"));
                                    arlDatTmpTbl.add(arlRegTmpTbl);
                                }

                                if(consultarReg(con)){
                                    blnCarInfArl=true;
                                }
                                con.close();
                                con=null;
                        }

                        
                        
                    }
                }// fin del proceso q extrae la informacion de la empresa que se desea procesar
                
                
                //aqui recien se va a hacer la conexion remota a cada servidor para empezar a replicar la informacion 
                if(blnCarInfArl){
                    for (int j=0;j<arlDatCfgRegCnxRem.size();j++){
                        strSQL="";
                        strSQL+="select ";
                        strSQL+=" co_reg, tx_drvcon, tx_strCon, tx_usrcon, tx_clacon, co_emp, tx_nom";
                        strSQL+=" from tbm_basdat as a2";
                        strSQL+=" where a2.co_reg="  + objUti.getStringValueAt(arlDatCfgRegCnxRem, j, INT_COL_CFG_REG_DES) + "";
//                        strSQL+=" and a2.co_regDes=" + objUti.getStringValueAt(arlDatCfgRegCnxRem, j, INT_COL_CFG_REG_DES) + "";
                        strSQL+=" and st_reg='A'";
                        rstMai=stmMai.executeQuery(strSQL);
                        while(rstMai.next()){
                            strConLoc=rstMai.getString("tx_strCon");
                            strUsrLoc=rstMai.getString("tx_usrcon");
                            strPswLoc=rstMai.getString("tx_clacon");
//                            System.out.println("STRING DE CONEXION: " + strConLoc);



                        strUsrLoc=objJCEAlgAES.desencriptar("7132A142185AF67FE3543C1727153A14", "864C302EC53A4E15022519AD01726536",strUsrLoc);
                        strPswLoc=objJCEAlgAES.desencriptar("7132A142185AF67FE3543C1727153A14", "864C302EC53A4E15022519AD01726536", strPswLoc);


                        System.out.println("2- strConIni: " + strConIni);
                        System.out.println("2- strUsrLoc: " + strUsrLoc);
                        System.out.println("2- strPswLoc: " + strPswLoc);


                            
                            
                            conRem=conexDB(strConLoc, strUsrLoc, strPswLoc);
                            System.out.println("linea 12247");
                            if(procesarReg(conRem)){
                                blnRes=true;
                            }
                        }
                    }
                }

                
                
            conMai.close();
            conMai=null;
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
        return blnRes;
    } 
    

    
}
