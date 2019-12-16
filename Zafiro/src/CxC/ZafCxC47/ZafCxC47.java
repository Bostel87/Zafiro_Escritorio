/*
 * ZafCxC47.java
 *
 * Created on 21 de Noviembre de 2007, 14:26 PM 
 * ESTABLECER LAS SOLICITUDES DE CREDITO A ANALIZAR...
 * DESARROLLADO POR DARIO CARDENAS EL 21/NOV/2007
 * MODIFICADO EL DIA 20/FEB/2008
 */
package CxC.ZafCxC47;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;//para visualizar una ventana de programa desde una columna de botones
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;

/**
 *
 * @author  DARIO CARDENAS
 */
public class ZafCxC47 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                        //Lï¿½nea.
    final int INT_TBL_DAT_SELESTN=1;                    //Casilla de Seleccion para los estados de Analisis 'N'.
    final int INT_TBL_DAT_SELESTP=2;                    //Casilla de Seleccion para los estados de Analisis 'P'.
    final int INT_TBL_DAT_SELESTA=3;                    //Casilla de Seleccion para los estados de Analisis 'A'.
    final int INT_TBL_DAT_COD_EMP=4;                    //Cï¿½digo de la empresa.
    final int INT_TBL_DAT_COD_SOL=5;                    //Codigo de Solicitud.
    final int INT_TBL_DAT_FEC_SOL=6;                    //Fecha de Solicitud.
    final int INT_TBL_DAT_COD_CLI=7;                    //Codigo del cliente.
    final int INT_TBL_DAT_NOM_CLI=8;                    //Nombre del cliente.
    final int INT_TBL_DAT_EST_ANA=9;                    //Estado de Analisis de Solicitud de Credito.
    final int INT_TBL_DAT_BOT_PAN=10;                    //Boton de Panel de Control.
    
    
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
    final int INT_ARL_TBH_CLI_FE_ULT_ACT_DAT=57;
    final int INT_ARL_TBH_CLI_OBS_INF_BUR=58;
    final int INT_ARL_TBH_CLI_EST_IVA_COM=59;
    final int INT_ARL_TBH_CLI_EST_IVA_VEN=60;
    final int INT_ARL_TBH_CLI_MES_PRO_ACT_DAT=61;
    final int INT_ARL_TBH_CLI_FEC_HIS=62;
    final int INT_ARL_TBH_CLI_COD_USR_HIS=63;
    final int INT_ARL_TBH_CLI_COD_SOL_CRE=64;
    final int INT_ARL_TBH_CLI_COD_HIS_SOL=65;
    
    ArrayList arlRegTbhConCli, arlDatTbhConCli;
    final int INT_ARL_TBH_CON_CLI_COD_EMP=0;
    final int INT_ARL_TBH_CON_CLI_COD_LOC=1;
    final int INT_ARL_TBH_CON_CLI_COD_CLI=2;
    final int INT_ARL_TBH_CON_CLI_MOD=3;
    final int INT_ARL_TBH_CON_CLI_COD_REG=4;
    final int INT_ARL_TBH_CON_CLI_COD_HIS=5;
    final int INT_ARL_TBH_CON_CLI_NOM=6;
    final int INT_ARL_TBH_CON_CLI_CAR=7;
    final int INT_ARL_TBH_CON_CLI_TEL_UNO=8;
    final int INT_ARL_TBH_CON_CLI_TEL_DOS=9;
    final int INT_ARL_TBH_CON_CLI_TEL_TRE=10;
    final int INT_ARL_TBH_CON_CLI_COR_ELE_UNO=11;
    final int INT_ARL_TBH_CON_CLI_COR_ELE_DOS=12;
    final int INT_ARL_TBH_CON_CLI_OBS_UNO=13;
    final int INT_ARL_TBH_CON_CLI_EST_REG=14;
    final int INT_ARL_TBH_CON_CLI_FEC_ING=15;
    final int INT_ARL_TBH_CON_CLI_FEC_ULT_MOD=16;
    final int INT_ARL_TBH_CON_CLI_COD_USR_ING=17;
    final int INT_ARL_TBH_CON_CLI_COD_USR_MOD=18;
    final int INT_ARL_TBH_CON_CLI_EST_REG_REP=19;
    final int INT_ARL_TBH_CON_CLI_FEC_HIS=20;
    final int INT_ARL_TBH_CON_CLI_COD_USR_HIS=21;
    final int INT_ARL_TBH_CON_CLI_COD_SOL_CRE=22;
    final int INT_ARL_TBH_CON_CLI_COD_HIS_SOL=23;
    
    ArrayList arlRegTbhObsCli, arlDatTbhObsCli;
    final int INT_ARL_TBH_OBS_CLI_COD_EMP=0;
    final int INT_ARL_TBH_OBS_CLI_COD_LOC=1;
    final int INT_ARL_TBH_OBS_CLI_COD_CLI=2;
    final int INT_ARL_TBH_OBS_CLI_NUM_MOD=3;
    final int INT_ARL_TBH_OBS_CLI_COD_REG=4;
    final int INT_ARL_TBH_OBS_CLI_COD_HIS=5;
    final int INT_ARL_TBH_OBS_CLI_FEC_ING=6;
    final int INT_ARL_TBH_OBS_CLI_OBS_UNO=7;
    final int INT_ARL_TBH_OBS_CLI_EST_REG_REP=8;
    final int INT_ARL_TBH_OBS_CLI_FEC_HIS=9;
    final int INT_ARL_TBH_OBS_CLI_COD_USR_HIS=10;
    final int INT_ARL_TBH_OBS_CLI_COD_SOL_CRE=11;
    final int INT_ARL_TBH_OBS_CLI_COD_HIS_SOL=12;
    
    ArrayList arlRegTbhAccEmpCli, arlDatTbhAccEmpCli;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_EMP=0;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_CLI=1;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_REG=2;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_HIS=3;
    final int INT_ARL_TBH_ACC_EMP_CLI_NOM=4;
    final int INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS=5;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS=6;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_SOL_CRE=7;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_HIS_SOL=8;
    
    
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
   // private java.util.Date datFecAux;
    private java.util.Date datFecSer, datFecVen, datFecAux;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblBus objTblBus;                        //Editor de bï¿½squeda.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strNomEmp,strCodTipDoc, strDesLarTipDoc,strCodEmpTipDoc, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, k=0, CODEMP=0;
    private ZafVenCon vcoCli, vcoEmp;                   //Ventana de consulta.
    private Vector vecAux, vecAuxDat;
    private ZafTblCelRenChk objTblCelRenChkMainEstN, objTblCelRenChkMainEstP, objTblCelRenChkMainEstA;
    private ZafTblCelEdiChk objTblCelEdiChkMainEstN, objTblCelEdiChkMainEstP, objTblCelEdiChkMainEstA;
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    
    private Vector vecDatSolCre, vecCabSolCre;
    private Vector vecDatRefCom, vecCabRefCom;
    private Vector vecDatRefBan, vecCabRefBan;
    private Vector vecDatDocDig, vecCabDocDig;
    private Vector vecDatCli, vecCabCli;
    private Vector vecDatConCli, vecCabConCli;
    private Vector vecDatObsCli, vecCabObsCli;
    private Vector vecDatAccEmpCli, vecCabAccEmpCli;
    private java.sql.Connection conSolCre, conRefCom, conRefBan, conDocDig, conCli, conConCli, conObsCli, conAccEmpCli;
    private java.sql.Statement stmSolCre, stmRefCom, stmRefBan, stmDocDig, stmCli, stmConCli, stmObsCli, stmAccEmpCli;
    private java.sql.ResultSet rstSolCre, rstRefCom, rstRefBan, rstDocDig, rstCli, rstConCli, rstObsCli, rstAccEmpCli;
    
    private String ESTANASOLCRE="";
    private int a=0, n=0, p=0, ta=0, tp=0, tn=0, intClickGua=0;
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC47(ZafParSis obj) 
    {
//        initComponents();
//        //Inicializar objetos.
//        objParSis=obj;
//        //butTipDoc.setVisible(false);
        try
        {  
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            
            //Inicializando variables de arreglo///
            arlDatTbhSolCre=new ArrayList();
            arlDatTbhRefComSolCre=new ArrayList();
            arlDatTbhRefBanSolCre=new ArrayList();
            arlDatTbhDocDigSolCre=new ArrayList();
            arlDatTbhCli=new ArrayList();
            arlDatTbhConCli=new ArrayList();
            arlDatTbhObsCli=new ArrayList();
            arlDatTbhAccEmpCli=new ArrayList();

            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblEstAn = new javax.swing.JLabel();
        cboEstAna = new javax.swing.JComboBox();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todas las Solicitudes de Credito");
        bgrFil.add(optTod);
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        panFil.add(optTod);
        optTod.setBounds(4, 34, 400, 20);

        optFil.setText("S\u00f3lo las Solicitudes que cumplan el criterio seleccionado");
        bgrFil.add(optFil);
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });

        panFil.add(optFil);
        optFil.setBounds(4, 55, 400, 20);

        panNomCli.setLayout(null);

        panNomCli.setBorder(new javax.swing.border.TitledBorder("Nombre de cliente"));
        lblNomCliDes.setText("Desde:");
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });

        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 268, 20);

        lblNomCliHas.setText("Hasta:");
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(336, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });

        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 268, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(20, 105, 660, 52);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(24, 80, 120, 20);

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

        panFil.add(txtCodCli);
        txtCodCli.setBounds(144, 80, 56, 20);

        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });

        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(200, 80, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });

        panFil.add(butCli);
        butCli.setBounds(660, 80, 20, 20);

        lblEstAn.setText("Estado de Analisis:");
        panFil.add(lblEstAn);
        lblEstAn.setBounds(30, 170, 130, 15);

        panFil.add(cboEstAna);
        cboEstAna.setBounds(160, 168, 160, 24);

        lblEmp.setText("Empresa:");
        panFil.add(lblEmp);
        lblEmp.setBounds(10, 10, 70, 15);

        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });

        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(70, 8, 30, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });

        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(100, 8, 200, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });

        panFil.add(butEmp);
        butEmp.setBounds(300, 8, 20, 20);

        tabFrm.addTab("Filtro", panFil);

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

        butGuardar.setText("Guardar");
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });

        panBot.add(butGuardar);

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

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        // TODO add your handling code here:
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(1);
            }
        }
        else
            txtCodEmp.setText(strCodEmp);        
        
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
        txtCodCli.requestFocus();
    }//GEN-LAST:event_optFilActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        // TODO add your handling code here:
        intClickGua++;
        
        System.out.println("---k: " + k + " ---ta: " + ta + " ---tp: " + tp + " ---tn: " + tn);
        
        if( k!=0 || tp!=0 )
        {
//            System.out.println("---ESTANASOLCRE--- " + ESTANASOLCRE);
            
//            actualizarReg();
            
            if(cargarReg())
            {
                if(insertarRegHis())
                {
                    if(actualizarSolCre())
                    {
                        mostrarMsgInf("La operacion GUARDAR se realizo con Exito.");
                    }
                    else
                        mostrarMsgErr("Ocurrio un error al realizar la operacion GUARDAR.\nIntente realizar la operaciï¿½n nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
                else
                    mostrarMsgErr("Ocurrio un error al realizar la operacion GUARDAR.\nIntente realizar la operaciï¿½n nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
            else
                mostrarMsgErr("Ocurrio un error al realizar la operacion GUARDAR.\nIntente realizar la operaciï¿½n nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
        }
        else
        {
            mostrarMsgInf("No existen Registros Seleccionados en la Columna de Est. P. \n Favor Seleccione un Registro...");
        }
        
        System.out.println("---TERMINO DE GUARDAR, luego se consultara los registros--- ");
        cargarDetReg();
    }//GEN-LAST:event_butGuardarActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected())
        {
//            txtCodEmp.setText("");
//            txtDesLarEmp.setText("");
//            txtCodTipDoc.setText("");
//            txtDesLarTipDoc.setText("");
//            txtCodEmpTipDoc.setText("");
//            txtDesLarEmpTipDoc.setText("");
        }

    }//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        configurarVenConCli();
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
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
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
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

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
        
        txtCodCli.setText("");
        txtDesLarCli.setText("");
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
            
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        k=0;
        p=0;
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
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
        Cerrar_Conexion();
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() 
    {
        dispose();
        Cerrar_Conexion();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGuardar;
    private javax.swing.JComboBox cboEstAna;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblEstAn;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomEmp;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        int intCodEmp=0, intCodEmpGrp=0;
        try
        {
            //Inicializar objetos.
//            objUti=new ZafUtil();
//            strAux=objParSis.getNombreMenu();
//            this.setTitle(strAux + " V 0.3 ");
//            lblTit.setText(strAux);
            
            //titulo de la ventana
            this.setTitle(objParSis.getNombreMenu()+ " V 0.4");
            lblTit.setText(""+objParSis.getNombreMenu());
            
            
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            CODEMP = intCodEmpGrp;
            System.out.println("---intCodEmp--- " + intCodEmp);
            System.out.println("---CODEMPGRP--- " + CODEMP);
            
            lblEmp.setVisible(false);
            txtCodEmp.setVisible(false);
            txtNomEmp.setVisible(false);
            butEmp.setVisible(false);
            
            
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtNomEmp.setVisible(true);
                butEmp.setVisible(true);
            }
            
            //Configurar Ventana de Consulta de Cliente//
            configurarVenConCli();
            
            //Configurar Ventana de Consulta de Empresa//
            configurarVenConEmp();
            
            //Configurar el combo "Estado de registro".//
            cboEstAna.addItem("Todos");
            cboEstAna.addItem("N = No Analizar Todavia");
            cboEstAna.addItem("P = Pendiente por Analizar");
            cboEstAna.addItem("A = Solicitud Analizada");
            cboEstAna.setSelectedIndex(0);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();
    
            vecCab.add(INT_TBL_DAT_LIN,"");///0
            vecCab.add(INT_TBL_DAT_SELESTN,"Est.N");///1
            vecCab.add(INT_TBL_DAT_SELESTP,"Est.P");///2
            vecCab.add(INT_TBL_DAT_SELESTA,"Est.A");///3
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");///4
            vecCab.add(INT_TBL_DAT_COD_SOL,"Cod.Sol.");///5
            vecCab.add(INT_TBL_DAT_FEC_SOL,"Fecha");///6
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cod.Cli.");///7
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");///8
            vecCab.add(INT_TBL_DAT_EST_ANA,"Estado");///9
            vecCab.add(INT_TBL_DAT_BOT_PAN,"...");///10
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            objTblEdi=new ZafTblEdi(tblDat);
            setEditable(true);
                        
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);///0
            tcmAux.getColumn(INT_TBL_DAT_SELESTN).setPreferredWidth(30);///1
            tcmAux.getColumn(INT_TBL_DAT_SELESTP).setPreferredWidth(30);///2
            tcmAux.getColumn(INT_TBL_DAT_SELESTA).setPreferredWidth(30);///3
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);///4
            tcmAux.getColumn(INT_TBL_DAT_COD_SOL).setPreferredWidth(50);///5
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setPreferredWidth(95);///6
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);///7
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(250);///8
            tcmAux.getColumn(INT_TBL_DAT_EST_ANA).setPreferredWidth(50);///9
            tcmAux.getColumn(INT_TBL_DAT_BOT_PAN).setPreferredWidth(35);///10

            //Configurar JTable: Ocultar columnas del sistema.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ANA).setResizable(false);
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAuxDat=new Vector();
            ///vecAux.add("" + INT_TBL_DAT_SELESTN);///1
            vecAux.add("" + INT_TBL_DAT_SELESTP);///2
            ///vecAux.add("" + INT_TBL_DAT_SELESTA);///3
            vecAux.add("" + INT_TBL_DAT_BOT_PAN);///7
            objTblMod.setColumnasEditables(vecAux);
            vecAuxDat = vecAux;
            vecAux=null;
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_EST_ANA).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            //metodo de renderizador de la columna de seleccion de estado 'N'//
            objTblCelRenChkMainEstN=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SELESTN).setCellRenderer(objTblCelRenChkMainEstN);
            objTblCelRenChkMainEstN=null;
            
            objTblCelEdiChkMainEstN=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SELESTN).setCellEditor(objTblCelEdiChkMainEstN);
            objTblCelEdiChkMainEstN.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    ///System.out.println("afterEdit - DESPUES -- SE SELECCIONO CELDA");
                    
                    if(objTblCelEdiChkMainEstN.isChecked())
                    {
//                        System.out.println("---if desde el chek main---");
                        n++;
                        System.out.println("---k reg.sel.EST N--- " + n);
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTP);
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTA);
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTN);
                        n--;
//                        System.out.println("---se desactivado k registros--- " + k);
                    }
                    k = n;
                    
                }
            });
            
            //metodo de renderizador de la columna de seleccion de estado 'P'//
            objTblCelRenChkMainEstP=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SELESTP).setCellRenderer(objTblCelRenChkMainEstP);
            objTblCelRenChkMainEstP=null;
            
            objTblCelEdiChkMainEstP=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SELESTP).setCellEditor(objTblCelEdiChkMainEstP);
            objTblCelEdiChkMainEstP.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    ///System.out.println("afterEdit - DESPUES -- SE SELECCIONO CELDA");
                    
                    if(objTblCelEdiChkMainEstP.isChecked())
                    {
//                        System.out.println("---if desde el chek main---");
                        p++;
                        System.out.println("---k reg.sel. EST P--- " + p);
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTN);
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTA);
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTP);
                        p--;
                        System.out.println("---se desactivado p registros--- " + p);
                        objTblMod.setValueAt(new Boolean(true), tblDat.getSelectedRow(), INT_TBL_DAT_SELESTN);
                    }
                    k = p;
                }
            });
            
            //metodo de renderizador de la columna de seleccion de estado 'A'//
            objTblCelRenChkMainEstA=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SELESTA).setCellRenderer(objTblCelRenChkMainEstA);
            objTblCelRenChkMainEstA=null;
            
            objTblCelEdiChkMainEstA=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SELESTA).setCellEditor(objTblCelEdiChkMainEstA);
            objTblCelEdiChkMainEstA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    ///System.out.println("afterEdit - DESPUES -- SE SELECCIONO CELDA");
                    
                    if(objTblCelEdiChkMainEstA.isChecked())
                    {
//                        System.out.println("---if desde el chek main---");
                        a++;
                        System.out.println("---k reg.sel. EST A--- " + a);
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTN);
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTP);
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SELESTA);
                        a--;
//                        System.out.println("---se desactivado k registros--- " + k);
                        objTblMod.setValueAt(new Boolean(true), tblDat.getSelectedRow(), INT_TBL_DAT_SELESTN);
                    }
                    k = a;
                }
            });
            
            //Renderizo para boton
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BOT_PAN).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BOT_PAN).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abrirFrm();
                }
            });
            
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
    
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    
    
    
    public void Cerrar_Conexion()
    {
        try
        {
            if(intClickGua > 0)
            {
                rstSolCre.close();
                stmSolCre.close();
                conSolCre.close();
                rstSolCre=null;
                stmSolCre=null;
                conSolCre=null;

                rstRefCom.close();
                stmRefCom.close();
                conRefCom.close();
                rstRefCom=null;
                stmRefCom=null;
                conRefCom=null;

                rstRefBan.close();
                stmRefBan.close();
                conRefBan.close();
                rstRefBan=null;
                stmRefBan=null;
                conRefBan=null;

                rstDocDig.close();
                stmDocDig.close();
                conDocDig.close();
                rstDocDig=null;
                stmDocDig=null;
                conDocDig=null;

                rstCli.close();
                stmCli.close();
                conCli.close();
                rstCli=null;
                stmCli=null;
                conCli=null;

                rstConCli.close();
                stmConCli.close();
                conConCli.close();
                rstConCli=null;
                stmConCli=null;
                conConCli=null;

                rstObsCli.close();
                stmObsCli.close();
                conObsCli.close();
                rstObsCli=null;
                stmObsCli=null;
                conObsCli=null;

                rstAccEmpCli.close();
                stmAccEmpCli.close();
                conAccEmpCli.close();
                rstAccEmpCli=null;
                stmAccEmpCli=null;
                conAccEmpCli=null;
            }
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
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
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Identificaciï¿½n");
            arlAli.add("Nombre");
            arlAli.add("Direcciï¿½n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//            {
//                if (txtCodEmp.getText().length()>0)
//                    strSQL+=" WHERE a1.co_emp = " + txtCodEmp.getText();
//                else
//                    strSQL+=" WHERE a1.co_emp IN (1,2,3,4)";
//            }
//            else
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            
//            strSQL+=" ORDER BY a1.tx_nom";
            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {            
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_cli='S'";

                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();

                    strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_cli='S'";

                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();

                    strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                }
            }
            else
            {
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.st_cli='S' ";

                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" AND a1.co_emp=" + CODEMP;
                    else
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();

                    strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                }
                else
                {
                    
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" FROM  tbr_cliloc AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                    if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp=" + CODEMP;
                    else
                    {
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    }
                    strSQL+=" AND a2.st_cli='S' AND a2.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp, a2.tx_nom";
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Empresa".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("Direccion");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_ruc, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_emp AS a1";
            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoEmp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        
        
        String strEstAnaSolCre="";
        
        boolean blnRes=true;
        
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
                
                ///System.out.println("---fecha--datFecAux "+ datFecAux);
                ///System.out.println("---fecha--strFecSis "+ strFecSis);

                //Obtener la condiciï¿½n.
                strAux="";
                
                //Condicion para filtro por empresa//
                if(intCodEmp==0)
                {
                    if (txtCodEmp.getText().length()>0)
                        strAux+=" AND a1.co_emp = " + txtCodEmp.getText();
                }
                else
                {
                    strAux+=" AND a1.co_emp = " + intCodEmp;                    
                }
                //Condicion para filtro por cliente//
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a1.co_cli = " + txtCodCli.getText();
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a2.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a2.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                //Condicion para Estado de Analisis//
                if(cboEstAna.getSelectedIndex()==0)
                {
                    ///strAux+=" AND a1.st_anasol NOT IN ('A')";
                    strAux+=" AND a1.st_anasol IN ('A', 'N', 'P')";
                }
                if(cboEstAna.getSelectedIndex()==1)
                {
                    strAux+=" AND a1.st_anasol IN ('N')";
                }
                if(cboEstAna.getSelectedIndex()==2)
                {
                    strAux+=" AND a1.st_anasol IN ('P')";
                }
                if(cboEstAna.getSelectedIndex()==3)
                {
                    strAux+=" AND a1.st_anasol IN ('A')";
                }

//                //Obtener el nï¿½mero total de registros.
//                strSQL="";
//                strSQL+="SELECT COUNT(*)";
//                strSQL+=" FROM (";
//                strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
//                strSQL+=" FROM tbm_solcre AS a1";
//                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
//                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp;
//                strSQL+=" WHERE a1.st_reg IN ('A')";                
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
//                strSQL+=" ) AS b1";
//                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                if (intNumTotReg==-1)
//                    return false;

//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
//                strSQL+=" FROM tbm_solcre AS a1";
//                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
//                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp;
//                strSQL+=" WHERE a1.st_reg IN ('A')";                
//                strSQL+=strAux;
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    //Obtener el nï¿½mero total de registros.
                    strSQL="";
                    strSQL+="SELECT COUNT(*)";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
                    strSQL+=" FROM tbm_solcre AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" WHERE a1.st_reg IN ('A')";
                    strSQL+=strAux;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
                    strSQL+=" ) AS b1";
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;
                    
                    
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
                    strSQL+=" FROM tbm_solcre AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" WHERE a1.st_reg IN ('A')";
                    strSQL+=strAux;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
                    System.out.println("---Query de cargarDetReg()--admin--: "+ strSQL);
                }
                else
                {
                    
                    //Obtener el nï¿½mero total de registros.
                    strSQL="";
                    strSQL+="SELECT COUNT(*)";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
                    strSQL+=" FROM tbm_solcre AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.st_reg IN ('A')";
                    strSQL+=" AND a3.co_loc = " + intCodLoc ;
                    strSQL+=strAux;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
                    strSQL+=" ) AS b1";
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;
                    
                    
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
                    strSQL+=" FROM tbm_solcre AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.st_reg IN ('A')";
                    strSQL+=" AND a3.co_loc = " + intCodLoc ;
                    strSQL+=strAux;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
                    System.out.println("---Query de cargarDetReg()--local--: "+ strSQL);
                }
                    
                
                
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");///0
                        vecReg.add(INT_TBL_DAT_SELESTN,"");///1
                        vecReg.add(INT_TBL_DAT_SELESTP,"");///2
                        vecReg.add(INT_TBL_DAT_SELESTA,"");///3
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));///4
                        vecReg.add(INT_TBL_DAT_COD_SOL,rst.getString("co_sol"));///5
                        vecReg.add(INT_TBL_DAT_FEC_SOL,rst.getString("fe_sol"));///6
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));///7
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));///8
                        vecReg.add(INT_TBL_DAT_EST_ANA,rst.getString("st_anasol"));///9
                        vecReg.add(INT_TBL_DAT_BOT_PAN,"");///10
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                        
                        strEstAnaSolCre = rst.getString("st_anasol");
                        
                        ESTANASOLCRE = strEstAnaSolCre;
                        
                        if(strEstAnaSolCre.equals("N"))
                        {
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_SELESTN);
                            tn++;
                        }
                        
                        if(strEstAnaSolCre.equals("P"))
                        {
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_SELESTP);
                            tp++;
                        }
                        
                        if(strEstAnaSolCre.equals("A"))
                        {
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_SELESTA);
                            ta++;
                        }
                    }
                    else
                    {
                        break;
                    }
                    
                }///fin del while///
                
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
                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sï¿½lo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
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
     * Esta funciï¿½n permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarTbmSolCre())
            {
                if(cargarTbmRefCom())
                {
                    if(cargarTbmRefBan())
                    {
                        if(cargarTbmDocDig())
                        {
                            if(cargarTbmCli())
                            {
                                if(cargarTbmConCli())
                                {
                                    if(cargarTbmObsCli())
                                    {
                                        if(cargarTbmAccEmpCli())
                                        {
                                            System.out.println("Termino de Ejecutarse Bien la funcion CargarReg()");
                                        }
                                        else
                                            blnRes=false;
                                    }
                                    else
                                        blnRes=false;
                                }
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                        }
                        else
                            blnRes=false;
                    }
                    else
                        blnRes=false;
                }
                else
                    blnRes=false;
            }
            else
                blnRes=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmSolCre()
    {
        int intPosRel, intCodHisCodCliUni=0;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0;
        String strCodCli="";
        
        //Limpiar el arreglo//
        arlDatTbhSolCre.clear();
        arlDatTbhSolCre=new ArrayList();
        
        try
        {
            conSolCre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conSolCre!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmSolCre=conSolCre.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_solcre";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCliUni==-1)
                            return false;

                        intCodHisCodCliUni++;

                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_sol, " + intCodHisCodCliUni + " AS co_his, a1.co_cli, a1.fe_sol, a1.co_forpag, a1.nd_moncre, a1.st_anasol, a1.fe_anasol, ";
                        strSQL+=" a1.co_usranasol, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_solcre as a1 ";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            //System.out.println("---cargarTbmSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_loc = " + intCodLoc + "";
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            System.out.println("---cargarTbmSolCre---local---: " + strSQL);
                        }

                        rstSolCre=stmSolCre.executeQuery(strSQL);

                        while (rstSolCre.next())
                        {     
                            arlRegTbhSolCre=new ArrayList();
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_EMP, "" + rstSolCre.getString("co_emp"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_SOL, "" + rstSolCre.getString("co_sol"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_HIS, "" + rstSolCre.getString("co_his"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_CLI, "" + rstSolCre.getString("co_cli"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_SOL, "" + rstSolCre.getString("fe_sol")==""?null:rstSolCre.getString("fe_sol"));                        
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_FOR_PAG, "" + rstSolCre.getString("co_forpag"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_MON_CRE, "" + rstSolCre.getString("nd_moncre"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_EST_ANA_SOL, "" + rstSolCre.getString("st_anasol"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL, "" + rstSolCre.getString("fe_anasol")==""?null:rstSolCre.getString("fe_anasol"));                        
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USU_ANA_SOL, "" + rstSolCre.getString("co_usranasol"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_OBS_UNO, "" + rstSolCre.getString("tx_obs1"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_OBS_DOS, "" + rstSolCre.getString("tx_obs2"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_EST_REG, "" + rstSolCre.getString("st_reg"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ING, "" + rstSolCre.getString("fe_ing")==""?null:rstSolCre.getString("fe_ing"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD, "" + rstSolCre.getString("fe_ultmod")==""?null:rstSolCre.getString("fe_ultmod"));                        
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_ING, "" + rstSolCre.getString("co_usring"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_MOD, "" + rstSolCre.getString("co_usrmod"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_FEC_HIS, "" + rstSolCre.getString("fe_his"));
                                arlRegTbhSolCre.add(INT_ARL_TBH_SOL_CRE_COD_USR_HIS, "" + rstSolCre.getString("co_usrhis"));
                                arlDatTbhSolCre.add(arlRegTbhSolCre);
                        }
                    }
//                    else
//                        System.out.println("---quiere guardar a estado 'N'---");
                }
                System.out.println("---cargarTbmSolCre---admin---: " + strSQL);
                System.out.println("---arreglo TbhSolCre--- " + arlDatTbhSolCre.size() + " reg-- " + arlDatTbhSolCre);
                
                ///limpiar y cerrar el resulset///
//                rstSolCre.close();
//                stmSolCre.close();
//                conSolCre.close();
//                rstSolCre=null;
//                stmSolCre=null;
//                conSolCre=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstSolCre.close();
//            stmSolCre.close();
//            conSolCre.close();
//            rstSolCre=null;
//            stmSolCre=null;
//            conSolCre=null;
            
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmRefCom()
    {
        int intPosRel;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0, intCodHisCodCli=0;
        
        //Limpiar el arreglo//
        arlDatTbhRefComSolCre.clear();
        arlDatTbhRefComSolCre=new ArrayList();
        
        try
        {
            conRefCom=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conRefCom!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    ///if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    ///if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmRefCom=conRefCom.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        ///strSQL+=" FROM tbh_RefComSolCre AS a1 INNER JOIN tbm_solCre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol) ";
                        strSQL+=" FROM tbh_solCre AS a1 ";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_cli= " + strCodCli + "";
                        
                        intCodHisCodCli=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCli==-1)
                            return false;

                        intCodHisCodCli++;

                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_sol, " + intCodHisCodCli + " AS co_his, a1.co_ref, a1.fe_ref, a1.tx_nom, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                        strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.ne_numpro, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_RefComSolCre as a1 ";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            ///strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            ///System.out.println("---cargarTbmRefComSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbm_solcre AS a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol)";
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a2.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            System.out.println("---cargarTbmRefComSolCre---local---: " + strSQL);
                        }

                        rstRefCom=stmRefCom.executeQuery(strSQL);
                        
                        while(rstRefCom.next())
                        {
                            arlRegTbhRefComSolCre=new ArrayList();
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_EMP, "" + rstRefCom.getString("co_emp"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_SOL, "" + rstRefCom.getString("co_sol"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_HIS, "" + rstRefCom.getString("co_his"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_REF, "" + rstRefCom.getString("co_ref"));
                            //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_REF, "" + rst.getString("fe_ref"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_REF, "" + rstRefCom.getString("fe_ref")==""?null:rstRefCom.getString("fe_ref"));

                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_NOM, "" + rstRefCom.getString("tx_nom"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_MAT_PRO_REF, "" + rstRefCom.getString("tx_matproref"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_TEL, "" + rstRefCom.getString("tx_tel"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_TIE, "" + rstRefCom.getString("tx_tie"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CUP_CRE, "" + rstRefCom.getString("tx_cupcre"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_PLA_CRE, "" + rstRefCom.getString("tx_placre"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FOR_PAG, "" + rstRefCom.getString("tx_forpag"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_EST_PRO, "" + rstRefCom.getString("st_pro"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CAL, "" + rstRefCom.getString("tx_cal"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_INF, "" + rstRefCom.getString("tx_inf"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_CAR_INF, "" + rstRefCom.getString("tx_carinf"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_OBS_UNO, "" + rstRefCom.getString("tx_obs1"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_EST_REG, "" + rstRefCom.getString("st_reg"));
                            //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ING, "" + rstRefCom.getString("fe_ing")==""?null:rstRefCom.getString("fe_ing"));

                            //arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD, "" + rstRefCom.getString("fe_ultmod")==""?null:rstRefCom.getString("fe_ultmod"));

                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_ING, "" + rstRefCom.getString("co_usring"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_MOD, "" + rstRefCom.getString("co_usrmod"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_FEC_HIS, "" + rstRefCom.getString("fe_his"));
                            arlRegTbhRefComSolCre.add(INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_HIS, "" + rstRefCom.getString("co_usrHis"));
                            arlDatTbhRefComSolCre.add(arlRegTbhRefComSolCre);
                        }
                    }
                }
                System.out.println("---cargarTbmRefComSolCre---admin---: " + strSQL);
                System.out.println("---arreglo TbhRefComSolCre--- " + arlDatTbhRefComSolCre.size() + " reg-- " + arlDatTbhRefComSolCre);
                
                ///limpiar y cerrar el resulset///
//                rstRefCom.close();
//                stmRefCom.close();
//                conRefCom.close();
//                rstRefCom=null;
//                stmRefCom=null;
//                conRefCom=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstRefCom.close();
//            stmRefCom.close();
//            conRefCom.close();
//            rstRefCom=null;
//            stmRefCom=null;
//            conRefCom=null;
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmRefBan()
    {
        int intPosRel;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0, intCodHisCodCli=0;
        
        //Limpiar el arreglo//
        arlDatTbhRefBanSolCre.clear();
        arlDatTbhRefBanSolCre=new ArrayList();
        
        
        try
        {
            conRefBan=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conRefBan!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmRefBan=conRefBan.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        ///strSQL+=" FROM tbh_RefBanSolCre AS a1 INNER JOIN tbm_solCre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol) ";
                        strSQL+=" FROM tbh_solCre AS a1 ";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_cli= " + strCodCli + "";
                        
                        intCodHisCodCli=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCli==-1)
                            return false;

                        intCodHisCodCli++;

                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_sol, " + intCodHisCodCli + " AS co_his, a1.co_ref, a1.fe_ref, a1.tx_nom, a1.tx_age, a1.tx_oficta, a1.tx_numcta, a1.tx_duecta, a1.co_ciuapecta, a1.fe_apecta, a1.tx_salprocta, a1.st_pro, a1.st_credir, ";
                        strSQL+=" a1.st_creind, a1.tx_moncredir, a1.tx_moncreind, a1.tx_inf, a1.tx_carinf, a1.tx_docdig, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.ne_numpro, a1.tx_obspro, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_RefBanSolCre as a1 ";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            ///strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            ///System.out.println("---cargarTbmRefBanSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbm_solcre AS a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol)";
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a2.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            System.out.println("---cargarTbmRefBanSolCre---local---: " + strSQL);
                        }

                        rstRefBan=stmRefBan.executeQuery(strSQL);
                        
                        while(rstRefBan.next())
                        {
                            arlRegTbhRefBanSolCre=new ArrayList();
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_EMP, "" + rstRefBan.getString("co_emp"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_SOL, "" + rstRefBan.getString("co_sol"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_HIS, "" + rstRefBan.getString("co_his"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_REF, "" + rstRefBan.getString("co_ref"));
                            //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_REF, "" + rst.getString("fe_ref"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_REF, "" + rstRefBan.getString("fe_ref")==""?null:rstRefBan.getString("fe_ref"));

                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NOM, "" + rstRefBan.getString("tx_nom"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_AGE, "" + rstRefBan.getString("tx_age"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OFI_CTA, "" + rstRefBan.getString("tx_oficta"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_CTA, "" + rstRefBan.getString("tx_numcta"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_DUE_CTA, "" + rstRefBan.getString("tx_duecta"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_CIU_APE_CTA, "" + rstRefBan.getString("co_ciuapecta"));
                            //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA, "" + rst.getString("fe_apecta"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_APE_CTA, "" + rstRefBan.getString("fe_apecta")==""?null:rstRefBan.getString("fe_apecta"));

                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_SAL_PRO_CTA, "" + rstRefBan.getString("tx_salprocta"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_PRO, "" + rstRefBan.getString("st_pro"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_DIR, "" + rstRefBan.getString("st_credir"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_CRE_IND, "" + rstRefBan.getString("st_creind"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_NUM_PRO, "" + rstRefBan.getString("ne_numpro"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_PRO, "" + rstRefBan.getString("tx_obspro"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_DIR, "" + rstRefBan.getString("tx_moncredir"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_MON_CRE_IND, "" + rstRefBan.getString("tx_moncreind"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_INF, "" + rstRefBan.getString("tx_inf"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_CAR_INF, "" + rstRefBan.getString("tx_carinf"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_DOC_DIG, "" + rstRefBan.getString("tx_docdig"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_OBS_UNO, "" + rstRefBan.getString("tx_obs1"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_EST_REG, "" + rstRefBan.getString("st_reg"));
                            //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ING, "" + rstRefBan.getString("fe_ing")==""?null:rstRefBan.getString("fe_ing"));

                            //arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD, "" + rstRefBan.getString("fe_ultmod")==""?null:rstRefBan.getString("fe_ultmod"));

                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_ING, "" + rstRefBan.getString("co_usring"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_MOD, "" + rstRefBan.getString("co_usrmod"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_HIS, "" + rstRefBan.getString("fe_his"));
                            arlRegTbhRefBanSolCre.add(INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_HIS, "" + rstRefBan.getString("co_usrHis"));
                            arlDatTbhRefBanSolCre.add(arlRegTbhRefBanSolCre);
                        }
                    }
                }
                System.out.println("---cargarTbmRefBanSolCre---admin---: " + strSQL);
                System.out.println("---arreglo TbhRefBanSolCre--- " + arlDatTbhRefBanSolCre.size() + " reg-- " + arlDatTbhRefBanSolCre);
                
                ///limpiar y cerrar el resulset///
//                rstRefBan.close();
//                stmRefBan.close();
//                conRefBan.close();
//                rstRefBan=null;
//                stmRefBan=null;
//                conRefBan=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstRefBan.close();
//            stmRefBan.close();
//            conRefBan.close();
//            rstRefBan=null;
//            stmRefBan=null;
//            conRefBan=null;
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmDocDig()
    {
        int intPosRel, intCodHisCodCliUni=0;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0;
        String strCodCli="";
        
        //Limpiar el arreglo//
        arlDatTbhDocDigSolCre.clear();
        arlDatTbhDocDigSolCre=new ArrayList();
        
        
        try
        {
            conDocDig=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conDocDig!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmDocDig=conDocDig.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        ///strSQL+=" FROM tbh_docdigsolcre AS a1 INNER JOIN tbm_solCre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol) ";
                        strSQL+=" FROM tbh_solCre AS a1 ";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_cli= " + strCodCli + "";
                        
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCliUni==-1)
                            return false;

                        intCodHisCodCliUni++;
                        
                        strSQL="";
                        strSQL+="SELECT a2.co_emp, a2.co_sol, " + intCodHisCodCliUni + " AS co_his, a2.co_doc, a2.tx_nom, a2.tx_ubi, a2.tx_obs1, a2.st_reg, a2.fe_ing, a2.fe_ultmod, a2.co_usring, a2.co_usrmod, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_solCre AS a1 INNER JOIN tbm_docdigsolcre AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_sol=a2.co_sol) ";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_sol ";
                            System.out.println("---cargarTbmDocDig---local---: " + strSQL);
                        }

                        rstDocDig=stmDocDig.executeQuery(strSQL);

                        while(rstDocDig.next())
                        {
                            arlRegTbhDocDigSolCre=new ArrayList();
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_EMP, "" + rstDocDig.getString("co_emp"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_SOL, "" + rstDocDig.getString("co_sol"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_HIS, "" + rstDocDig.getString("co_his"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_DOC, "" + rstDocDig.getString("co_doc"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_NOM, "" + rstDocDig.getString("tx_nom"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_UBI, "" + rstDocDig.getString("tx_ubi"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_OBS_UNO, "" + rstDocDig.getString("tx_obs1"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_EST_REG, "" + rstDocDig.getString("st_reg"));
                            //arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING, "" + rst.getString("fe_ing"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ING, "" + rstDocDig.getString("fe_ing")==""?null:rstDocDig.getString("fe_ing"));

                            //arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD, "" + rstDocDig.getString("fe_ultmod")==""?null:rstDocDig.getString("fe_ultmod"));

                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_ING, "" + rstDocDig.getString("co_usring"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_MOD, "" + rstDocDig.getString("co_usrmod"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_HIS, "" + rstDocDig.getString("fe_his"));
                            arlRegTbhDocDigSolCre.add(INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_HIS, "" + rstDocDig.getString("co_usrhis"));
                            arlDatTbhDocDigSolCre.add(arlRegTbhDocDigSolCre);
                        }
                    }
                }
                System.out.println("---cargarTbmDocDig---admin---: " + strSQL);
                System.out.println("---arreglo TbhDocDig--- " + arlDatTbhDocDigSolCre.size() + " reg-- " + arlDatTbhDocDigSolCre);
                
                ///limpiar y cerrar el resulset///
//                rstDocDig.close();
//                stmDocDig.close();
//                conDocDig.close();
//                rstDocDig=null;
//                stmDocDig=null;
//                conDocDig=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstDocDig.close();
//            stmDocDig.close();
//            conDocDig.close();
//            rstDocDig=null;
//            stmDocDig=null;
//            conDocDig=null;
            
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmCli()
    {
        int intPosRel, intCodHisCodCliUni=0, intCodHisSolCre=0;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0;
        String strCodCli="";
        
        //Limpiar el arreglo//
        arlDatTbhCli.clear();
        arlDatTbhCli=new ArrayList();
        
        try
        {
            conCli=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conCli!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmCli=conCli.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_cli";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCliUni==-1)
                            return false;

                        intCodHisCodCliUni++;
                        
                        
                        /*--Parte nueva para hallar el co_his de la tabla tbh_solCre */
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_solcre";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                        
                        intCodHisSolCre=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisSolCre==-1)
                            return false;
                        
                        System.out.println("---intCodHisSolCre---: " + intCodHisSolCre);
                        
                        intCodHisSolCre++;
/*                                             
co_emp,co_cli,st_cli,co_his, st_prv,tx_tipide,tx_ide,tx_nom,tx_dir,tx_tel,tx_fax,tx_cas,tx_dirweb,tx_corele,tx_tipper,co_ciu,co_zon,
tx_percon,tx_telcon,tx_corelecon,co_ven,co_grp,co_forpag,nd_moncre,ne_diagra,nd_maxdes,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultmod,
co_usring,co_usrmod,co_tipper,tx_refubi,nd_maruti,st_regrep,st_cieretpen,ne_diagrachqfec,tx_tel1,tx_tel2,tx_tel3,tx_repleg,tx_idepro,
tx_nompro,tx_dirpro,tx_telpro,tx_nacpro,fe_conemp,tx_tipactemp,tx_obspro,ne_nummaxvencon,tx_obsven,tx_obsinv,tx_obscxc,tx_obscxp,
fe_proactdat,fe_ultactdat,tx_obsinfburcre,st_ivacom,st_ivaven,ne_nummesproactdat, fe_his, co_usrhis, co_sol, co_hissol
*/                        

                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_cli, " + intCodHisCodCliUni + " AS co_his, a1.st_cli, a1.st_prv, a1.tx_tipide, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a1.tx_fax,";
                        strSQL+="a1.tx_cas, a1.tx_dirweb, a1.tx_corele, a1.tx_tipper, a1.co_ciu, a1.co_zon, a1.tx_percon, a1.tx_telcon, a1.tx_corelecon, a1.co_ven, a1.co_grp, a1.co_forpag, a1.nd_moncre, a1.ne_diagra, ";
                        strSQL+="a1.nd_maxdes, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.co_tipper, a1.tx_refubi, a1.nd_maruti, 'I' AS st_regrep, a1.st_cieretpen, ";
                        strSQL+="a1.ne_diagrachqfec, a1.tx_tel1, a1.tx_tel2, a1.tx_tel3, a1.tx_repleg, a1.tx_idepro, a1.tx_nompro, a1.tx_dirpro, a1.tx_telpro, a1.tx_nacpro, a1.fe_conemp, a1.tx_tipactemp, ";
                        strSQL+="a1.tx_obspro, a1.ne_nummaxvencon, a1.tx_obsven, a1.tx_obsinv, a1.tx_obscxc, a1.tx_obscxp, a1.fe_proactdat, a1.fe_ultactdat, a1.tx_obsinfburcre, a1.st_ivacom, a1.st_ivaven, a1.ne_nummesproactdat, ";
                        strSQL+="cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis, a2.co_sol, "+ intCodHisSolCre +" AS co_hissol ";
                        strSQL+=" FROM tbm_cli as a1";
                        strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            //System.out.println("---cargarTbmSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            System.out.println("---cargarTbmCli---local---: " + strSQL);
                        }

                        rstCli=stmCli.executeQuery(strSQL);

                        while(rstCli.next())
                        {                        
                                arlRegTbhCli=new ArrayList();
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_EMP, "" + rstCli.getString("co_emp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_CLI, "" + rstCli.getString("co_cli"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_HIS, "" + rstCli.getString("co_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_CLI, "" + rstCli.getString("st_cli"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_PRV, "" + rstCli.getString("st_prv"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_IDE, "" + rstCli.getString("tx_tipide"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IDE, "" + rstCli.getString("tx_ide"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NOM, "" + rstCli.getString("tx_nom"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR, "" + rstCli.getString("tx_dir"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL, "" + rstCli.getString("tx_tel"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FAX, "" + rstCli.getString("tx_fax"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_CAS, "" + rstCli.getString("tx_cas"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR_WEB, "" + rstCli.getString("tx_dirweb"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COR_ELE, "" + rstCli.getString("tx_corele"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_PER, "" + rstCli.getString("tx_tipper"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_CIU, "" + rstCli.getString("co_ciu"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_ZON, "" + rstCli.getString("co_zon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PER_CON, "" + rstCli.getString("tx_percon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_CON, "" + rstCli.getString("tx_telcon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COR_ELE_CON, "" + rstCli.getString("tx_corelecon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_VEN, "" + rstCli.getString("co_ven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_GRP, "" + rstCli.getString("co_ven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_FOR_PAG, "" + rstCli.getString("co_forpag"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MON_CRE, "" + rstCli.getString("nd_moncre"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIA_GRA, "" + rstCli.getString("ne_diagra"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MAX_DES, "" + rstCli.getString("nd_maxdes"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_UNO, "" + rstCli.getString("tx_obs1"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_DOS, "" + rstCli.getString("tx_obs2"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_REG, "" + rstCli.getString("st_reg"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ING, "" + rstCli.getString("fe_ing"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ING, "" + rstCli.getString("fe_ing")==""?null:rstCli.getString("fe_ing"));                                
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ULT_MOD, "" + rst.getString("fe_ultmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_ULT_MOD, "" + rstCli.getString("fe_ultmod")==""?null:rstCli.getString("fe_ultmod"));                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_ING, "" + rstCli.getString("co_usring"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_MOD, "" + rstCli.getString("co_usrmod"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_TIP_PER, "" + rstCli.getString("co_tipper"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_REF_UBI, "" + rstCli.getString("tx_refubi"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MAR_UTI, "" + rstCli.getString("nd_maruti"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_REP, "" + rstCli.getString("st_regrep"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_CIE_RET_PEN, "" + rstCli.getString("st_cieretpen"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIA_GRA_CHQ_FEC, "" + rstCli.getString("ne_diagrachqfec"));                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_UNO, "" + rstCli.getString("tx_tel1"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_DOS, "" + rstCli.getString("tx_tel2"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_TRE, "" + rstCli.getString("tx_tel3"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_REP_LEG, "" + rstCli.getString("tx_repleg"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_IDE_PRO, "" + rstCli.getString("tx_idepro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NOM_PRO, "" + rstCli.getString("tx_nompro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_DIR_PRO, "" + rstCli.getString("tx_dirpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TEL_PRO, "" + rstCli.getString("tx_telpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NAC_PRO, "" + rstCli.getString("tx_nacpro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_CON_EMP, "" + rstCli.getString("fe_conemp")==""?null:rstCli.getString("fe_conemp"));                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_TIP_ACT_EMP, "" + rstCli.getString("tx_tipactemp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_PRO, "" + rstCli.getString("tx_obspro"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_NUM_MAX_VEN_CON, "" + rstCli.getString("ne_nummaxvencon"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_VEN, "" + rstCli.getString("tx_obsven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_INV, "" + rstCli.getString("tx_obsinv"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_CXC, "" + rstCli.getString("tx_obscxc"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_CXP, "" + rstCli.getString("tx_obscxp"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_PRO_ACT_DAT, "" + rstCli.getString("fe_proactdat"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FE_ULT_ACT_DAT, "" + rstCli.getString("fe_ultactdat"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FE_ULT_ACT_DAT, "" + rstCli.getString("fe_ultactdat")==""?null:rstCli.getString("fe_ultactdat"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_OBS_INF_BUR, "" + rstCli.getString("tx_obsinfburcre"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_IVA_COM, "" + rstCli.getString("st_ivacom"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_EST_IVA_VEN, "" + rstCli.getString("st_ivaven"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_MES_PRO_ACT_DAT, "" + rstCli.getString("ne_nummesproactdat"));
                                //arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_HIS, "" + rstCli.getString("fe_his"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_FEC_HIS, "" + rstCli.getString("fe_his")==""?null:rstCli.getString("fe_his"));                                
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_USR_HIS, "" + rstCli.getString("co_usrhis"));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_SOL_CRE, "" + rstCli.getString("co_sol"));///original///
                                ///arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_SOL_CRE, "" + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL)));
                                arlRegTbhCli.add(INT_ARL_TBH_CLI_COD_HIS_SOL, "" + rstCli.getString("co_hissol"));
                                arlDatTbhCli.add(arlRegTbhCli);
                            }
                    }
                }
                System.out.println("---cargarTbmCli---admin---: " + strSQL);
                System.out.println("---arreglo TbhCli--- " + arlDatTbhCli.size() + " reg-- " + arlDatTbhCli);
                
                ///limpiar y cerrar el resulset///
//                rstCli.close();
//                stmCli.close();
//                conCli.close();
//                rstCli=null;
//                stmCli=null;
//                conCli=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstCli.close();
//            stmCli.close();
//            conCli.close();
//            rstCli=null;
//            stmCli=null;
//            conCli=null;
            
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmConCli()
    {
        int intPosRel, intCodHisCodCliUni=0;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0, intCodHisSolCre=0;
        String strCodCli="";
        
        //Limpiar el arreglo//
        arlDatTbhConCli.clear();
        arlDatTbhConCli=new ArrayList();
        
        try
        {
            conConCli=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conConCli!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmConCli=conConCli.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_concli";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCliUni==-1)
                            return false;

                        intCodHisCodCliUni++;
                        
                        /*--Parte nueva para hallar el co_his de la tabla tbh_solCre */
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_solcre";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                        
                        intCodHisSolCre=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisSolCre==-1)
                            return false;
                        
                        intCodHisSolCre++;

                        
/* tbm_ConCli: --co_emp,co_loc,co_cli,ne_mod,co_reg,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3,tx_corele1,tx_corele2,
tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod,st_regrep*/

/* tbh_ConCli: --co_emp,co_loc,co_cli,ne_mod,co_reg,co_his,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3,tx_corele1,
tx_corele2,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod,st_regrep,fe_his,co_usrhis,co_unicli,co_sol,co_hissol*/
                        
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a1.ne_mod,co_reg, " + intCodHisCodCliUni + " AS co_his, a1.tx_nom, a1.tx_car, a1.tx_tel1, a1.tx_tel2, a1.tx_tel3, a1.tx_corele1, ";
                        strSQL+=" a1.tx_corele2, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, 'I' AS st_regrep, a2.co_sol, "+ intCodHisSolCre +" AS co_hissol, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_concli as a1 ";
                        strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                        
                        

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            ///strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            //System.out.println("---cargarTbmSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            ///strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            System.out.println("---cargarTbmConCli---local---: " + strSQL);
                        }

                        rstConCli=stmConCli.executeQuery(strSQL);
/* tbm_ConCli: --co_emp,co_loc,co_cli,ne_mod,co_reg,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3,tx_corele1,tx_corele2,
tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod,st_regrep*/

/* tbh_ConCli: --co_emp,co_loc,co_cli,ne_mod,co_reg,co_his,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3,tx_corele1,
tx_corele2,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod,st_regrep,fe_his,co_usrhis,co_unicli,co_sol,co_hissol*/
    /*final int INT_ARL_TBH_CON_CLI_COD_EMP=0;
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
    final int INT_ARL_TBH_CON_CLI_EST_REG_REP=18;
    final int INT_ARL_TBH_CON_CLI_FEC_HIS=19;
    final int INT_ARL_TBH_CON_CLI_COD_USR_HIS=20;
    final int INT_ARL_TBH_CON_CLI_COD_SOL_CRE=21;
    final int INT_ARL_TBH_CON_CLI_COD_HIS_SOL=22;*/
                        
                        while(rstConCli.next())
                        {
                            arlRegTbhConCli=new ArrayList();
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_EMP, "" + rstConCli.getString("co_emp"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_LOC, "" + rstConCli.getString("co_loc"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_CLI, "" + rstConCli.getString("co_cli"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_MOD, "" + rstConCli.getString("ne_mod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_REG, "" + rstConCli.getString("co_reg"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_HIS, "" + rstConCli.getString("co_his"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_NOM, "" + rstConCli.getString("tx_nom"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_CAR, "" + rstConCli.getString("tx_car"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_UNO, "" + rstConCli.getString("tx_tel1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_DOS, "" + rstConCli.getString("tx_tel2"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_TEL_TRE, "" + rstConCli.getString("tx_tel3"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COR_ELE_UNO, "" + rstConCli.getString("tx_corele1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COR_ELE_DOS, "" + rstConCli.getString("tx_corele2"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_OBS_UNO, "" + rstConCli.getString("tx_obs1"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_EST_REG, "" + rstConCli.getString("st_reg"));
                            //arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ING, "" + rstConCli.getString("fe_ing"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ING, "" + rstConCli.getString("fe_ing")==""?null:rstConCli.getString("fe_ing"));

                            //arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ULT_MOD, "" + rstConCli.getString("fe_ultmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_ULT_MOD, "" + rstConCli.getString("fe_ultmod")==""?null:rstConCli.getString("fe_ultmod"));

                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_ING, "" + rstConCli.getString("co_usring"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_MOD, "" + rstConCli.getString("co_usrmod"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_EST_REG_REP, "" + rstConCli.getString("st_regrep"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_FEC_HIS, "" + rstConCli.getString("fe_his"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_USR_HIS, "" + rstConCli.getString("co_usrhis"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_SOL_CRE, "" + rstConCli.getString("co_sol"));
                            arlRegTbhConCli.add(INT_ARL_TBH_CON_CLI_COD_HIS_SOL, "" + rstConCli.getString("co_hissol"));
                            arlDatTbhConCli.add(arlRegTbhConCli);
                        }
                    }
                }
                System.out.println("---cargarTbmConCli---admin---: " + strSQL);
                System.out.println("---arreglo TbhConCli--- " + arlDatTbhConCli.size() + " reg-- " + arlDatTbhConCli);
                
                ///limpiar y cerrar el resulset///
//                rstConCli.close();
//                stmConCli.close();
//                conConCli.close();
//                rstConCli=null;
//                stmConCli=null;
//                conConCli=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstConCli.close();
//            stmConCli.close();
//            conConCli.close();
//            rstConCli=null;
//            stmConCli=null;
//            conConCli=null;
            
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmObsCli()
    {
        int intPosRel, intCodHisCodCliUni=0;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0, intCodHisSolCre=0;
        String strCodCli="";
        
        //Limpiar el arreglo//
        arlDatTbhObsCli.clear();
        arlDatTbhObsCli=new ArrayList();
        
        
        try
        {
            conObsCli=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conObsCli!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmObsCli=conObsCli.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_obscli";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCliUni==-1)
                            return false;

                        intCodHisCodCliUni++;
                        
                        /*--Parte nueva para hallar el co_his de la tabla tbh_solCre */
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_solcre";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                        
                        intCodHisSolCre=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisSolCre==-1)
                            return false;
                        
                        intCodHisSolCre++;
                        
/* tbm_ObsCli-- co_emp,co_loc,co_cli,ne_mod,co_reg,fe_ing,tx_obs1,st_regrep*/
/* tbh_ObsCli-- co_emp,co_loc,co_cli,ne_mod,co_reg,co_his,fe_ing,tx_obs1,st_regrep,fe_his,co_usrhis,co_unicli,co_sol,co_hissol*/   
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a1.ne_mod, a1.co_reg, " + intCodHisCodCliUni + " AS co_his,  ";
                        strSQL+=" a1.fe_ing, a1.tx_obs1, 'I' AS st_regrep, a2.co_sol, "+ intCodHisSolCre +" AS co_hissol, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_ObsCli as a1 ";
                        strSQL+=" INNER JOIN tbm_solCre as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            ///strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            ///strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            //System.out.println("---cargarTbmSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            ///strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            ///strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            System.out.println("---cargarTbmObsCli---local---: " + strSQL);
                        }

                        rstObsCli=stmObsCli.executeQuery(strSQL);
                        
/* tbm_ObsCli-- co_emp,co_loc,co_cli,ne_mod,co_reg,fe_ing,tx_obs1,st_regrep*/
/* tbh_ObsCli-- co_emp,co_loc,co_cli,ne_mod,co_reg,co_his,fe_ing,tx_obs1,st_regrep,fe_his,co_usrhis,co_unicli,co_sol,co_hissol*/    
    /*final int INT_ARL_TBH_OBS_CLI_COD_EMP=0;
    final int INT_ARL_TBH_OBS_CLI_COD_CLI=1;
    final int INT_ARL_TBH_OBS_CLI_NUM_MOD=2;
    final int INT_ARL_TBH_OBS_CLI_COD_REG=3;
    final int INT_ARL_TBH_OBS_CLI_COD_HIS=4;
    final int INT_ARL_TBH_OBS_CLI_FEC_ING=5;
    final int INT_ARL_TBH_OBS_CLI_OBS_UNO=6;
    final int INT_ARL_TBH_OBS_CLI_EST_REG_REP=7;
    final int INT_ARL_TBH_OBS_CLI_FEC_HIS=8;
    final int INT_ARL_TBH_OBS_CLI_COD_USR_HIS=8;
    final int INT_ARL_TBH_OBS_CLI_COD_SOL_CRE=10;
    final int INT_ARL_TBH_OBS_CLI_COD_HIS_SOL=11;*/
                        
                        
                        while(rstObsCli.next())
                        {
                            arlRegTbhObsCli=new ArrayList();
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_EMP, "" + rstObsCli.getString("co_emp"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_CLI, "" + rstObsCli.getString("co_cli"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_NUM_MOD, "" + rstObsCli.getString("ne_mod"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_REG, "" + rstObsCli.getString("co_reg"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_HIS, "" + rstObsCli.getString("co_his"));
                            //arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ING, "" + rstObsCli.getString("fe_ing"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_ING, "" + rstObsCli.getString("fe_ing")==""?null:rstObsCli.getString("fe_ing"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_OBS_UNO, "" + rstObsCli.getString("tx_obs1"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_EST_REG_REP, "" + rstObsCli.getString("st_regrep"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_FEC_HIS, "" + rstObsCli.getString("fe_his"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_USR_HIS, "" + rstObsCli.getString("co_usrhis"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_SOL_CRE, "" + rstObsCli.getString("co_sol"));
                            arlRegTbhObsCli.add(INT_ARL_TBH_OBS_CLI_COD_HIS_SOL, "" + rstObsCli.getString("co_hissol"));
                            arlDatTbhObsCli.add(arlRegTbhObsCli);
                        }
                    }
                }
                System.out.println("---cargarTbmObsCli---admin---: " + strSQL);
                System.out.println("---arreglo TbhObsCli--- " + arlDatTbhObsCli.size() + " reg-- " + arlDatTbhObsCli);
                
                ///limpiar y cerrar el resulset///
//                rstObsCli.close();
//                stmObsCli.close();
//                conObsCli.close();
//                rstObsCli=null;
//                stmObsCli=null;
//                conObsCli=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstObsCli.close();
//            stmObsCli.close();
//            conObsCli.close();
//            rstObsCli=null;
//            stmObsCli=null;
//            conObsCli=null;
            
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
     * Esta funcion permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTbmAccEmpCli()
    {
        int intPosRel, intCodHisCodCliUni=0;
        boolean blnRes=true;
        int intCodEmp=0, intCodLoc=0, intCodHisSolCre=0;
        String strCodCli="";
        
        //Limpiar el arreglo//
        arlDatTbhAccEmpCli.clear();
        arlDatTbhAccEmpCli=new ArrayList();
        
        try
        {
            conAccEmpCli=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            
            if (conAccEmpCli!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    String strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    //if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        stmAccEmpCli=conAccEmpCli.createStatement();
                        
                        strCodCli = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                        
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_accempcli";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        
                        intCodHisCodCliUni=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisCodCliUni==-1)
                            return false;

                        intCodHisCodCliUni++;
                        
                        /*--Parte nueva para hallar el co_his de la tabla tbh_solCre */
                        strSQL="";
                        strSQL+="SELECT MAX(co_his)";
                        strSQL+=" FROM tbh_solcre";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli= " + strCodCli + "";
                        strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                        
                        intCodHisSolCre=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                            
                        if (intCodHisSolCre==-1)
                            return false;
                        
                        intCodHisSolCre++;
                        
/* tbh_AccEmpCli--- co_emp,co_cli,co_reg,co_his,tx_nom,fe_his,co_usrhis,co_unicli,co_sol,co_hissol*/
/* tbm_AccEmpCli--- co_emp,co_cli,co_reg,tx_nom */
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_cli, a1.co_reg, " + intCodHisCodCliUni + " AS co_his, a1.tx_nom, a2.co_sol, "+ intCodHisSolCre +" AS co_hissol, ";
                        strSQL+=" cast('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS timestamp) AS fe_his, ";
                        strSQL+=" " + objParSis.getCodigoUsuario() + " AS co_usrHis ";
                        strSQL+=" FROM tbm_AccEmpCli as a1 ";
                        strSQL+=" INNER JOIN tbm_solCre as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";

                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            ///strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)); ///tblDat.getSelectedRow()
                            ///strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            ///strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            //System.out.println("---cargarTbmSolCre---admin---: " + strSQL);
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));
                            ///strSQL+=" AND a1.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a1.co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            strSQL+=" AND a2.co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            ///strSQL+=" AND a1.st_reg <> 'E'";
                            strSQL+=" ORDER BY a1.co_cli ";
                            System.out.println("---cargarTbmAccEmpCli---local---: " + strSQL);
                        }

                        rstAccEmpCli=stmAccEmpCli.executeQuery(strSQL);
                        
/* tbh_AccEmpCli--- co_emp,co_cli,co_reg,co_his,tx_nom,fe_his,co_usrhis,co_unicli,co_sol,co_hissol*/
/* tbm_AccEmpCli--- co_emp,co_cli,co_reg,tx_nom */    
    /*final int INT_ARL_TBH_ACC_EMP_CLI_COD_EMP=0;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_CLI=1;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_REG=2;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_HIS=3;
    final int INT_ARL_TBH_ACC_EMP_CLI_NOM=4;
    final int INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS=5;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS=6;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_SOL_CRE=7;
    final int INT_ARL_TBH_ACC_EMP_CLI_COD_HIS_SOL=8;*/
                        while(rstAccEmpCli.next())
                        {
                            arlRegTbhAccEmpCli=new ArrayList();
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_EMP, "" + rstAccEmpCli.getString("co_emp"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_CLI, "" + rstAccEmpCli.getString("co_cli"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_REG, "" + rstAccEmpCli.getString("co_reg"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_HIS, "" + rstAccEmpCli.getString("co_his"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_NOM, "" + rstAccEmpCli.getString("tx_nom"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS, "" + rstAccEmpCli.getString("fe_his"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS, "" + rstAccEmpCli.getString("co_usrhis"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_SOL_CRE, "" + rstAccEmpCli.getString("co_sol"));
                            arlRegTbhAccEmpCli.add(INT_ARL_TBH_ACC_EMP_CLI_COD_HIS_SOL, "" + rstAccEmpCli.getString("co_hissol"));
                            arlDatTbhAccEmpCli.add(arlRegTbhAccEmpCli);
                        }
                    }
                }
                System.out.println("---cargarTbmAccEmpCli---admin---: " + strSQL);
                System.out.println("---arreglo TbhAccEmpCli--- " + arlDatTbhAccEmpCli.size() + " reg-- " + arlDatTbhAccEmpCli);
                
                ///limpiar y cerrar el resulset///
//                rstAccEmpCli.close();
//                stmAccEmpCli.close();
//                conAccEmpCli.close();
//                rstAccEmpCli=null;
//                stmAccEmpCli=null;
//                conAccEmpCli=null;
            }
//            ///limpiar y cerrar el resulset///
//            rstAccEmpCli.close();
//            stmAccEmpCli.close();
//            conAccEmpCli.close();
//            rstAccEmpCli=null;
//            stmAccEmpCli=null;
//            conAccEmpCli=null;
            
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
    * Esta funciï¿½n permite insertar el registro seleccionado.
    * @return true: Si se pudo insertar el registro.
    * <BR>false: En el caso contrario.
    */
    private boolean insertarRegHis()
    {
        boolean blnRes=false;
        int codtipdoc=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {               
                if (insertar_TbhSolCre())
                {
                    ///con.commit();
                    if (insertar_TbhRefComSolCre())
                    {
                        ///con.commit();
                        if(insertar_TbhRefBanSolCre())
                        {
                            ///con.commit();
                            if(insertar_TbhDocDigSolCre())
                            {
                                ///con.commit();
                                if(insertar_TbhCli())
                                {
                                    ///con.commit();
                                    if(insertar_TbhConCli())
                                    {
                                        ///con.commit();
                                        if(insertar_TbhObsCli())
                                        {
                                            ///con.commit();
                                            if(insertar_TbhAccEmpCli())
                                            {
                                                con.commit();//ojo deberia estar despues de cada funcion
                                                blnRes=true;
                                                System.out.println("---YA SE EJECUTO LA FUNCION insertarRegHis() QUEDO OK...");
                                                System.out.println(" ");
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
                        else
                            con.rollback();
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
    
    /**
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhSolCre()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhSolCre()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhSolCre.size()--- " + arlDatTbhSolCre.size());
                
                for(i=0; i<arlDatTbhSolCre.size();i++)
                {
                    strSQL="";
                    strSQL+="INSERT INTO tbh_solcre(";
                    strSQL+="co_emp, co_sol, co_his, co_cli, fe_sol, co_forpag, nd_moncre,";
                    strSQL+="st_anasol, fe_anasol, co_usranasol, tx_obs1, tx_obs2, st_reg,";
                    strSQL+="fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli) ";
                    strSQL+="VALUES (";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_CLI),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_SOL),0) + "',";
                    String strFecSol=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_SOL);
                    System.out.println("---strFecSol: " + strFecSol);
                    
                    if( (strFecSol == null) || (strFecSol.equals("")) || strFecSol.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecSol, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_FOR_PAG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_MON_CRE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_EST_ANA_SOL),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL),2) + "',";
                    String strFecAnaSol=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ANA_SOL);
                    System.out.println("---strFecAnaSol: " + strFecAnaSol);
                    
                    if( (strFecAnaSol == null) || (strFecAnaSol.equals("")) || strFecAnaSol.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecAnaSol, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USU_ANA_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_OBS_DOS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_EST_REG),0) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ING),0) + "',";
                    String strFecIng=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ING);
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD),0) + ",";
                    String strFecUltMod=objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_ULT_MOD);
                    System.out.println("---strFecUltMod: " + strFecUltMod);
                    
                    if( (strFecUltMod == null) || (strFecUltMod.equals("")) || strFecUltMod.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+=" null "; /// + objUti.codificar(objUti.getStringValueAt(arlDatTbhSolCre, i, INT_ARL_TBH_SOL_CRE_COD_UNI_CLI),2) + "";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhRefComSolCre()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        String strFecRef="", strFecIng="", strFecUltMod="";
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhRefComSolCre()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhRefComSolCre.size()--- " + arlDatTbhRefComSolCre.size());
                
                for(i=0; i<arlDatTbhRefComSolCre.size();i++)
                {
                    strSQL="";
                    strSQL+="INSERT INTO tbh_refcomsolcre(";
                    strSQL+="co_emp, co_sol, co_his, co_ref, fe_ref, tx_nom, tx_matproref, ";
                    strSQL+="tx_tel, tx_tie, tx_cupcre, tx_placre, tx_forpag, st_pro, tx_cal, ";
                    strSQL+="tx_inf, tx_carinf, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, ";
                    strSQL+="co_usrmod, fe_his, co_usrhis, co_unicli) ";
                    strSQL+=" VALUES (";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_REF),2) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_REF),2) + ",";
                    strFecRef=objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_REF);
                    System.out.println("---strFecRef: " + strFecRef);
                    
                    if( (strFecRef == null) || (strFecRef.equals("")) || strFecRef.equals("null"))
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
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD),2) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_ULT_MOD);
                    System.out.println("---strFecUltMod: " + strFecUltMod);
                    
                    if( (strFecUltMod == null) || (strFecUltMod.equals("")) || strFecUltMod.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefComSolCre, i, INT_ARL_TBH_REF_COM_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+=" null ";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhRefComSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhRefBanSolCre()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        String strFecRef="", strFecIng="", strFecUltMod="", strFecApeCta="";
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhRefBanSolCre()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhRefBanSolCre.size()--- " + arlDatTbhRefBanSolCre.size());
                
                for(i=0; i<arlDatTbhRefBanSolCre.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbh_refbansolcre(";
                    strSQL+="co_emp, co_sol, co_his, co_ref, fe_ref, tx_nom, tx_age, tx_oficta, ";
                    strSQL+="tx_numcta, tx_duecta, co_ciuapecta, fe_apecta, tx_salprocta, ";
                    strSQL+="st_pro, st_credir, st_creind, ne_numpro, tx_obspro, tx_moncredir, ";
                    strSQL+="tx_moncreind, tx_inf, tx_carinf, tx_docdig, tx_obs1, st_reg, ";
                    strSQL+="fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli) ";
                    strSQL+=" VALUES (";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_SOL),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_REF),2) + ",";
                    //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_REF),2) + ",";
                    strFecRef=objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_REF);
                    System.out.println("---strFecRef: " + strFecRef);
                    
                    if( (strFecRef == null) || (strFecRef.equals("")) || strFecRef.equals("null"))
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
                    System.out.println("---strFecApeCta: " + strFecApeCta);
                    
                    if( (strFecApeCta == null) || (strFecApeCta.equals("")) || strFecApeCta.equals("null"))
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
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD),2) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_ULT_MOD);
                    System.out.println("---strFecUltMod: " + strFecUltMod);
                    
                    if( (strFecUltMod == null) || (strFecUltMod.equals("")) || strFecUltMod.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhRefBanSolCre, i, INT_ARL_TBH_REF_BAN_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+=" null ";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhRefBanSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhDocDigSolCre()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        String strFecIng="", strFecMod="";
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhSolCre()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhDocDigSolCre.size()--- " + arlDatTbhDocDigSolCre.size());
                
                for(i=0; i<arlDatTbhDocDigSolCre.size();i++)
                {
                    strSQL="";
                    strSQL+="INSERT INTO tbh_docdigsolcre(";
                    strSQL+="co_emp, co_sol, co_his, co_doc, tx_nom, tx_ubi, tx_obs1, st_reg,";
                    strSQL+="fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_unicli) ";
                    strSQL+=" VALUES (";
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
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD),2) + "',";
                    strFecMod=objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_ULT_MOD);
                    System.out.println("---strFecMod: " + strFecMod);
                    
                    if( (strFecMod == null) || (strFecMod.equals("")) || strFecMod.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_MOD),2) + ",";
                    strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_FEC_HIS),2) + "',";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhDocDigSolCre, i, INT_ARL_TBH_DOC_DIG_SOL_CRE_COD_USR_HIS),2) + ",";
                    strSQL+=" null ";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhDocDigSolCre: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhCli()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        String strFecIng="", strFecUltMod="", strFecConEmp="", strFecHis="", strFecUltActDat="";
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhSolCre()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhCli.size()--- " + arlDatTbhCli.size());
                
                for(i=0; i<arlDatTbhCli.size(); i++)
                {
/*tbm_cli:-- co_emp,co_cli,co_his,st_cli,st_prv,tx_tipide,tx_ide,tx_nom,tx_dir,tx_tel,tx_fax,tx_cas,tx_dirweb,tx_corele,tx_tipper,
co_ciu,co_zon,tx_percon,tx_telcon,tx_corelecon,co_ven,co_grp,co_forpag,nd_moncre,ne_diagra,nd_maxdes,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultmod,
co_usring,co_usrmod,co_tipper,tx_refubi,nd_maruti,st_regrep,st_cieretpen,ne_diagrachqfec,tx_tel1,tx_tel2,tx_tel3,tx_repleg,tx_idepro,
tx_nompro,tx_dirpro,tx_telpro,tx_nacpro,fe_conemp,tx_tipactemp,tx_obspro,ne_nummaxvencon,tx_obsven,tx_obsinv,tx_obscxc,tx_obscxp,
fe_proactdat,fe_ultactdat,tx_obsinfburcre,st_ivacom,st_ivaven,ne_nummesproactdat, fe_his, co_usrhis
*/ 
                    
/* tbh_cli:co_emp,co_cli,co_his,st_cli,st_prv,tx_tipide,tx_ide,tx_nom,tx_dir,tx_refubi,tx_tel1,tx_tel2,tx_tel3,tx_tel,tx_fax,tx_cas,tx_dirweb,
tx_corele,tx_tipper,co_tipper,co_ciu,co_zon,tx_percon,tx_telcon,tx_corelecon,co_ven,co_grp,co_forpag,nd_moncre,ne_diagra,ne_diagrachqfec,
nd_maxdes,nd_maruti,tx_repleg,st_cieretpen,tx_idepro,tx_nompro,tx_dirpro,tx_telpro,tx_nacpro,fe_conemp,tx_tipactemp,tx_obspro,
ne_nummaxvencon,tx_obsven,tx_obsinv,tx_obscxc,tx_obscxp,tx_obsinfburcre,fe_ultactdat,fe_proactdat,tx_obs1,tx_obs2,st_reg,
fe_ing,fe_ultmod,co_usring,co_usrmod,fe_his,co_usrhis,st_regrep,st_ivacom,st_ivaven,ne_nummesproactdat,co_sol,co_hissol */
                    
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cli(";
                    strSQL+="co_emp, co_cli, co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir, ";
                    strSQL+="tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu, ";
                    strSQL+="co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag, ";
                    strSQL+="nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing, ";
                    strSQL+="fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti, ";
                    strSQL+="st_regrep, st_cieretpen, ne_diagrachqfec,tx_tel1, tx_tel2, tx_tel3, ";
                    strSQL+="tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro, ";
                    strSQL+="fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven, ";
                    strSQL+="tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat, ";
                    strSQL+="fe_his, co_usrhis, fe_ultactdat, ";
                    strSQL+="tx_obsinfburcre, st_ivacom, st_ivaven, ne_nummesproactdat ";
                    strSQL+=", co_sol, co_hissol ";
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
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null") )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ULT_MOD) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_ULT_MOD) ;
                    System.out.println("---strFecUltMod: " + strFecUltMod);
                    
                    if( (strFecUltMod.equals(null)) || (strFecUltMod.equals("")) || strFecUltMod.equals("null"))
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
                    System.out.println("---strFecConEmp: " + strFecConEmp);
                    
                    if( (strFecConEmp == null) || (strFecConEmp.equals("")) || strFecConEmp.equals("null"))
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
                                        
                    String strFecProActDat=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PRO_ACT_DAT);
                    System.out.println("---strFecProActDat: " + strFecProActDat);
                    
                    if( (strFecProActDat == null) || (strFecProActDat.equals("")) || strFecProActDat.equals("null") )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_PRO_ACT_DAT) + "',";
                    
                    //strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_HIS) + "',";
                    strFecHis=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FEC_HIS);
                    System.out.println("---strFecHis: " + strFecHis);
                    
                    if( (strFecHis == null) || (strFecHis.equals("")) || strFecHis.equals("null") )
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecHis, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_USR_HIS) + ",";
                    
                    strFecUltActDat=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_FE_ULT_ACT_DAT);
                    System.out.println("---strFecUltActDat: " + strFecUltActDat);
                    
                    if( (strFecUltActDat == null) || (strFecUltActDat.equals("")) || strFecUltActDat.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltActDat, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    String strObsBurHis=objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_INF_BUR);
                    System.out.println("---strObsBurHis: " + strObsBurHis);
                    
                    if( (strObsBurHis == null) || (strObsBurHis.equals("")) || strObsBurHis.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_OBS_INF_BUR) + "',";
                    
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_IVA_COM) + "',";
                    strSQL+="'" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_EST_IVA_VEN) + "',";
                    strSQL+="" + objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_MES_PRO_ACT_DAT) + "";
                    strSQL+="," + objUti.codificar(objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_SOL_CRE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhCli, i, INT_ARL_TBH_CLI_COD_HIS_SOL),2) + "";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                            
                            
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhConCli()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        String strFecIng="", strFecUltMod="";
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhConCli()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhConCli.size()--- " + arlDatTbhConCli.size());
                
                for(i=0; i<arlDatTbhConCli.size();i++)
                {            
                    strSQL="";
                    strSQL+="INSERT INTO tbh_concli(";
                    strSQL+=" co_emp, co_loc, co_cli, ne_mod, co_reg, co_his, tx_nom, tx_car, tx_tel1,";
                    strSQL+=" tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1, st_reg, fe_ing,";
                    strSQL+="fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis, co_sol,co_hissol)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_LOC),2) + ",";
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
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_ULT_MOD),0) + "',";
                    strFecUltMod=objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_ULT_MOD);
                    System.out.println("---strFecUltMod: " + strFecUltMod);
                    
                    if( (strFecUltMod == null) || (strFecUltMod.equals("")) || strFecUltMod.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecUltMod, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";
                    
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_USR_ING),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_USR_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_SOL_CRE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhConCli, i, INT_ARL_TBH_CON_CLI_COD_HIS_SOL),2) + "";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhConCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhObsCli()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        String strFecIng="";
        
        try
        {
            if (con!=null)
            {
                //System.out.println("---entro por el if de con!=null de insertar_TbhObsCli()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhObsCli.size()--- " + arlDatTbhObsCli.size());
                
                for(i=0; i<arlDatTbhObsCli.size();i++)
                {                   
                    strSQL="";
                    strSQL+="INSERT INTO tbh_obscli(";
                    strSQL+=" co_emp, co_loc, co_cli, ne_mod, co_reg, co_his, fe_ing,";
                    strSQL+=" tx_obs1, fe_his, co_usrhis)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_LOC),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_NUM_MOD),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_HIS),2) + ",";
                    //strSQL+="'" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_ING),0) + "',";
                    strFecIng=objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_ING);
                    System.out.println("---strFecIng: " + strFecIng);
                    
                    if( (strFecIng == null) || (strFecIng.equals("")) || strFecIng.equals("null"))
                        strSQL+=" null,";
                    else
                        strSQL+="'" + objUti.formatearFecha(strFecIng, objParSis.getFormatoFechaBaseDatos(), objParSis.getFormatoFechaBaseDatos()) + "',";

                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_OBS_UNO),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_SOL_CRE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhObsCli, i, INT_ARL_TBH_OBS_CLI_COD_HIS_SOL),2) + "";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhObsCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_TbhAccEmpCli()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        double dblAbo1, dblvaldoc2;
        boolean blnRes=true;
        
        try
        {
            if (con!=null)
            {
                ///System.out.println("---entro por el if de con!=null de insertar_TbhAccEmpCli()--- ");
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                System.out.println("---arlDatTbhAccEmpCli.size()--- " + arlDatTbhAccEmpCli.size());
                
                for(i=0; i<arlDatTbhAccEmpCli.size();i++)
                {                  
                    strSQL="";
                    strSQL+="INSERT INTO tbh_accempcli(";
                    strSQL+=" co_emp, co_cli, co_reg, co_his, ";
                    strSQL+=" tx_nom, fe_his, co_usrhis, co_sol,co_hissol)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_EMP),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_CLI),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_REG),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_NOM),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_FEC_HIS),0) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_USR_HIS),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_SOL_CRE),2) + ",";
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatTbhAccEmpCli, i, INT_ARL_TBH_ACC_EMP_CLI_COD_HIS_SOL),2) + "";
                    strSQL+=")";
                    System.out.println("---SQL DE insertar_tbhAccEmpCli: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
    
    
    private boolean actualizarSolCre()
    {
        boolean blnRes=true;
        int i=0, x=0;
        String strEstAnaSol="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                ///System.out.println("---ENTRO EN LA FUNCION actualizarSolCre()---");
                ///System.out.println("---fecha--- strFecSis--- " + strFecSis);
                
                for (i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));

                    ///if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    ///if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTN)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) || (objTblMod.isChecked(i, INT_TBL_DAT_SELESTA)) )
                    if ( (objTblMod.isChecked(i, INT_TBL_DAT_SELESTP)) )
                    {
                        ///System.out.println("---en reg. sel. strEstAnaSol---: " + strEstAnaSol + " ---i = " + i);

                        if(strEstAnaSol.equals("N"))
                        {
                            strEstAnaSol = "P";

                            ///System.out.println("---el nuevo strEstAnaSol es ---" + strEstAnaSol);

                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_solcre";
                            strSQL+=" SET ";
                            strSQL+=" st_anasol = '" + strEstAnaSol + "'";
                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
                            strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            System.out.println("---SQL del update tbm_solcre---: " + strSQL);
                            stm.executeUpdate(strSQL);


                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_cli";
                            strSQL+=" SET ";
                            strSQL+=" fe_ultActDat = '" + strFecSis + "'";
                            ///strSQL+=" fe_ultActDat = '" + objUti.formatearFecha(datFecAux,"yyyy/MM/dd") + "'";
                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
                            strSQL+=" AND co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            System.out.println("---SQL del update tbm_cli---: " + strSQL);
                            stm.executeUpdate(strSQL);
                        }
                        
                        if(strEstAnaSol.equals("A"))
                        {
                            strEstAnaSol = "P";

                            ///System.out.println("---el nuevo strEstAnaSol es ---" + strEstAnaSol);

                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_solcre";
                            strSQL+=" SET ";
                            strSQL+=" st_anasol = '" + strEstAnaSol + "'";
                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
                            strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            System.out.println("---SQL del update tbm_solcre---: " + strSQL);
                            stm.executeUpdate(strSQL);


                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_cli";
                            strSQL+=" SET ";
                            strSQL+=" fe_ultActDat = '" + strFecSis + "'";
                            ///strSQL+=" fe_ultActDat = '" + objUti.formatearFecha(datFecAux,"yyyy/MM/dd") + "'";
                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
                            strSQL+=" AND co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
                            System.out.println("---SQL del update tbm_cli---: " + strSQL);
                            stm.executeUpdate(strSQL);
                        }
                    }
                    else
                    {
                        ///System.out.println("---else no hay reg seleccionado ---");
                        if(strEstAnaSol.equals("P"))
                        {
                            strEstAnaSol = "N";

                            ///System.out.println("---el nuevo strEstAnaSol es ---" + strEstAnaSol);

                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_solcre";
                            strSQL+=" SET ";
                            strSQL+=" st_anasol = '" + strEstAnaSol + "'";
                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
                            strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
                            System.out.println("---SQL del update tbm_solcre---: " + strSQL);
                            stm.executeUpdate(strSQL);
                        }
                    }
                }
                con.commit();
                blnRes=true;

                stm.close();
                stm=null;
            }
            else
            {
                System.out.println("---esta falso en con!=null---");
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
            if(tabFrm.getSelectedIndex()==1)
            {
                ///System.out.println("ABRIR_FRM Tab Propietarios - Accionistas...");
                //////////////PARA LLAMAR A LA VENTANA DE CONTACTOS ////////
                if(!(tblDat.getSelectedColumn()==-1))
                {
                    if(!(tblDat.getSelectedRow()==-1))
                    {
                        strAux="CxC.ZafCxC32.ZafCxC32";
                        if (!strAux.equals(""))
                            invocarClase(strAux);
                    }
                }
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
        int intcodemp=0;
        try
        {
            System.out.println("---FUNCION INVOCAR CLASE DESDE CXC47...");
            ///System.out.println("El estado CONSULTAR es... " + c);
            ///System.out.println("El estado MODIFICAR es... " + m);
            intcodemp = objParSis.getCodigoEmpresa();
            
            ////TAB DE REPORTES////
            if(tabFrm.getSelectedIndex()==1)
            {
                ///System.out.println("--1.-INVOCAR_CLASE Tab Propietarios - Accionistas...");
                //Obtener el constructor de la clase que se va a invocar.
                Class objVen=Class.forName(clase);
                Class objCla[]=new Class[4];
                objCla[0]=objParSis.getClass();         ///OBJ ZAFPARSIS///
                objCla[1]=new Integer(0).getClass();    ///INT CODEMP///
                objCla[2]=new Integer(0).getClass();    ///INT CODCLI///                
                objCla[3]=new Integer(0).getClass();    ///INT CODSOLCRE///
                
                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
                
                //Inicializar el constructor que se obtuvo.
                Object objObj[]=new Object[4];
                
                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
                if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL)==null )
                {
                    System.out.println("---ENTRO POR LA FILA VACIA DESDE CXC47...");
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(intcodemp);
                    objObj[2]=new Integer(Integer.parseInt(txtCodCli.getText()));
                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
                }
                else
                {
                    System.out.println("---ENTRO POR LA FILA CON DATOS DESDE CXC47...");
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_EMP).toString());
                    objObj[2]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());                    
                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
                }
                
                javax.swing.JInternalFrame ifrVen;
                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                ifrVen.show();
            }
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
    
    /**
     * Esta funciï¿½n muestra un mensaje informativo al usuario. Se podrï¿½a utilizar
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
     * Esta funciï¿½n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta funciï¿½n muestra un mensaje de error al usuario. Se podrï¿½a utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
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
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtNomEmp.setText(strNomEmp);
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
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
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
            //Establecer el foco en el JTable solo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
//            cargarReg();
            objThrGUI=null;
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
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
                case INT_TBL_DAT_SELESTN:
                    strMsg="Seleccion por Estado N";
                    break;
                case INT_TBL_DAT_SELESTP:
                    strMsg="Seleccion por Estado P";
                    break;
                case INT_TBL_DAT_SELESTA:
                    strMsg="Seleccion por Estado A";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_DAT_COD_SOL:
                    strMsg="Codigo de la Solicitud de Credito";
                    break;
                case INT_TBL_DAT_FEC_SOL:
                    strMsg="Fecha de la Solicitud de Credito";
                    break;                    
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Codigo del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_EST_ANA:
                    strMsg="Estado de Solicitud";
                    break;
                case INT_TBL_DAT_BOT_PAN:
                    strMsg="Boton para Visualizar el Panel de Control";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}