/*
 * ZafMae07.java
 *
 * Created on 11 de octubre de 2004, 11:07
 */
package Maestros.ZafMae07;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafValCedRuc.TuvalUtilitiesException;
import Librerias.ZafValCedRuc.VerificarId;
import Librerias.ZafValCedRuc.ZafRegCiv;
import Librerias.ZafValCedRuc.ZafSRIDatos;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_03;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;

/**
 * @author  jayapata
 * Maestro "Clientes/Proveedores"
 */
public class ZafMae07 extends javax.swing.JInternalFrame
{
    //Constantes JTable: Tabla de "Direcciones".
    static final int INT_TBL_DIR_LIN = 0;
    static final int INT_TBL_DIR_NMODDI = 1;
    static final int INT_TBL_DIR_DIR = 2;
    static final int INT_TBL_DIR_REF = 3;
    static final int INT_TBL_DIR_TEL1 = 4;
    static final int INT_TBL_DIR_TEL2 = 5;
    static final int INT_TBL_DIR_TEL3 = 6;
    static final int INT_TBL_DIR_OBS2 = 7;
    static final int INT_TBL_DIR_MODD = 8;
    static final int INT_TBL_DIR_CONT = 9;          /* José Mario 20/Oct/2015 */

    
    
    //Constantes JTable: Tabla de "Contactos".
    static final int INT_TBL_CON_LIN = 0;
    static final int INT_TBL_CON_NOMMOD = 1;
    static final int INT_TBL_CON_NOM = 2;
    static final int INT_TBL_CON_CAR = 3;
    static final int INT_TBL_CON_TEL1 = 4;
    static final int INT_TBL_CON_TEL2 = 5;
    static final int INT_TBL_CON_TEL3 = 6;
    static final int INT_TBL_CON_MA1 = 7;
    static final int INT_TBL_CON_MA2 = 8;
    static final int INT_TBL_CON_OBS = 9;
    static final int INT_TBL_CON_MODC = 10;
    static final int INT_TBL_CON_LOCC = 11;
    
    //Constantes JTable: Tabla de "Beneficiarios".
    static final int INT_TBL_BEN_LIN = 0;
    static final int INT_TBL_BEN_COD = 1;
    static final int INT_TBL_BEN_NOM = 2;
    static final int INT_TBL_BEN_PRE = 3;
    static final int INT_TBL_BEN_ACT = 4;
    static final int INT_TBL_BEN_INA = 5;    

    //Constantes JTable: Tabla de Observación.
    static final int INT_TBL_CON_OBSLIN = 0;
    static final int INT_TBL_CON_OBSNMOD = 1;
    static final int INT_TBL_CON_OBSCOD = 2;
    static final int INT_TBL_CON_OBSMOD = 3;
    static final int INT_TBL_CON_OBSFEC = 4;
    static final int INT_TBL_CON_OBSOBS = 5;
    static final int INT_TBL_CON_LOC = 6;
    static final int INT_TBL_CON_OBSBUT = 7;
    
    //Constantes JTable: Tabla de Clientes por Local.
    static final int INT_TBL_LOC_LIN = 0;
    static final int INT_TBL_LOC_CHK = 1;
    static final int INT_TBL_LOC_COD = 2;
    static final int INT_TBL_LOC_NOM = 3;
    static final int INT_TBL_LOC_ESTCAR= 4; //El estado con el cual se cargo la primera vez el INT_TBL_LOC_CHK (Se usa en modificaciones)    
    
    //Constantes JTable: Tabla de Accionistas
    static final int INT_TBL_ACCLIN = 0;
    static final int INT_TBL_ACCCOD = 1;
    static final int INT_TBL_ACCNOM = 2;

    //Constantes del ArrayList Elementos Eliminados de "Observacion"
    final int INT_ARR_OBSLOC = 0;
    final int INT_ARR_OBSREG = 1;
    final int INT_ARR_OBSMOD = 2;

    //Constantes del ArrayList Elementos Eliminados de "Beneficiarios"
    final int INT_ARR_CODBEN = 0;
    
    //ArrayList: Consultar clienes.
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_CLI=1;   
    private int intIndiceCon=0;       

    //Variables
    private Connection con, conRemGlo = null, conn = null;  //Variable para conexion a la Base de Datos
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;                          //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.
    private ZafPerUsr objPerUsr;
    private MiToolBar objTooBar;                          //Objeto de tipo objTooBar para poder manipular la clase ZafToolBar
    private ZafUtil objUti;                               //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    private ZafTblMod objTblModCon, objTblModDir, objTblModAcc, objTblModBen, objTblModObs, objTblModCliLoc;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkP, objTblCelRenChkA, objTblCelRenChkI;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkP, objTblCelEdiChkA, objTblCelEdiChkI;        //Editor: JButton en celda.
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiTxt objTblCelEdiTxtCom;
    private ZafObtConCen objObtConCen;
    private ZafColNumerada objColNum;
    private ZafDatePicker txtFecDoc;
    private LisTxtTabGen objLisCamTabGen;                         //Instancia de clase que detecta cambios
    private ZafVenCon vcoTipPer; 
    private ZafVenCon vcoCiu; 
    private ZafVenCon vcoPar; 
    private ZafVenCon vcoVen; 
    private ZafVenCon vcoZon; 
    private ZafVenCon vcoGrp; 
    private ZafVenCon vcoCliLib; 
    private VerificarId validadIDRuc;
    
    //Componentes 
    JOptionPane oppMsg;                                   //Objeto de tipo OptionPane para presentar Mensajes
    JInternalFrame jfrThis;                               //Variable que se refiere al JInternalFrame
    JTextField txtCodTipPer = new JTextField();
    JTextField txtDesCorTipPer = new JTextField();
    JTextField txtIdeAux = new JTextField();
    JLabel lblObsVen = new JLabel();
    JScrollPane scroObsVen = new JScrollPane();
    JTextArea txtArObsVen = new JTextArea();
    JLabel lblobsinv = new JLabel();
    JScrollPane scroObsInv = new JScrollPane();
    JTextArea txtArObsInv = new JTextArea();
    JLabel lblObscxc = new JLabel();
    JScrollPane scroObscxc = new JScrollPane();
    JTextArea txtArObscxc = new JTextArea();
    JLabel lblObscxcBurCre = new JLabel();
    JScrollPane scroObscxcBurCre = new JScrollPane();
    JTextArea txtArObscxcBurCre = new JTextArea();
    JLabel lblObscxp = new JLabel();
    JScrollPane scroObscxp = new JScrollPane();
    JTextArea txtArObscxp = new JTextArea();
    JLabel lblObsObs1 = new JLabel();
    JScrollPane scroObsObs1 = new JScrollPane();
    JTextArea txtArObsObs1 = new JTextArea();
    JLabel lblObsObs2 = new JLabel();
    JScrollPane scroObsObs2 = new JScrollPane();
    JTextArea txtArObsObs2 = new JTextArea();

    //Booleans para "Tab"
    boolean blnCln = false;                                  //Si los TextField estan vacios
    boolean blnHayCam = false;                               //Detecta que se hizo cambios en el documento
    boolean blnHayCamTabGen = false;                         //Detecta que se hizo cambios en el documento
    boolean blnHayCamTblCon = false;                         //Detecta que se hizo cambios en el documento
    boolean blnHayCamTblDir = false;                         //Detecta que se hizo cambios en el documento
    boolean blnHayCamTblObs = false;                         //Detecta que se hizo cambios en el documento
    boolean blnHayCamTblBen = false;                         //Detecta que se hizo cambios en el documento
    boolean blnHayCamTblAcc = false;                         //Detecta que se hizo cambios en el documento
    
    private Vector vecDat, vecCab;
    private char chrIde;                                  //Variable para guardar el tipo de identidad
    
    private int intCodMnu = 0, intCodMod = 0, intConUniCli = 0;
    private int INTCODREGCEN = 0;
    
    private StringBuffer stbDatModInsCli;    
    private String strCodTipPer = "", strNomTipPer = "";
    private String strCodCiu = "", strCiudad = "";
    private String strCodGrp = "", strNomGrp = "";
    private String strCodPar = "", strNomPar = "";     
    private String strCodVen = "", strNomVen = "";
    private String strCodZon = "", strNomZon = "";
    private String strSex = "", strEstCiv = "", strOriIng = "", strEstRegCli = "", strFilCli = "";
    private String strSQL, strSql, strAux, strMsg;
    
    private final String strVer = " v4.0.1 ";

    /** Creates new form ZafMae07 */
    public ZafMae07(Librerias.ZafParSis.ZafParSis obj)
    {
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();
            initComponents();
            txtArObsVen.setLineWrap(true);
            txtArObsInv.setLineWrap(true);
            txtArObscxc.setLineWrap(true);
            txtArObscxcBurCre.setLineWrap(true);
            txtArObscxp.setLineWrap(true);
            txtArObsObs1.setLineWrap(true);
            txtArObsObs2.setLineWrap(true);
            validadIDRuc = new VerificarId();
            oppMsg=new JOptionPane();
            objTooBar = new MiToolBar(this);
            panBar.add(objTooBar);
            intCodMnu = objParSis.getCodigoMenu();
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objParSis);
            INTCODREGCEN=objObtConCen.intCodReg;
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            cboDiaMesMaxEmiFac.addItem("");
            for(int i=1; i<=31; i++)
            {
                cboDiaMesMaxEmiFac.addItem(""+i );
            }
            jfrThis=this;
            blnHayCam=false;
            this.setBounds(10,10, 700, 450);
            txtFecDoc = new ZafDatePicker(((JFrame)jfrThis.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            PanDatPro.add(txtFecDoc);
            txtFecDoc.setBounds(120, 108, 180, 20);
            objPerUsr=new ZafPerUsr(obj);
            switch (intCodMnu) 
            {
                case 828: intCodMod=1;  strFilCli=" and a.st_cli='S' ";  chkCli.setSelected(true);   cargarModVen(); break;  // MODULO VENTAS
                case 873: intCodMod=2;  strFilCli=" and a.st_prv='S' ";  chkPro.setSelected(true);   cargarModInv(); break;  // MODULO INVENTARIO
                case 913: intCodMod=3;  strFilCli=" and a.st_cli='S' ";  chkCli.setSelected(true);   cargarModCxC(); break;  // MODULO CXC
                case 1053: intCodMod=4; strFilCli=" and a.st_prv='S' ";  chkPro.setSelected(true);   cargarModCxP(); break;  // MODULO CXP
                default: intCodMod=6;   cargarModMae();  break;  // MODULO MAESTRO
            }
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafMae07(ZafParSis obj, String codigoCliente) 
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();

            initComponents();

            jfrThis = this;

            oppMsg = new JOptionPane();
            objTooBar = new MiToolBar(this);
            this.getContentPane().add(objTooBar, "South");

            validadIDRuc = new VerificarId();
            txtFecDoc = new ZafDatePicker(((JFrame) jfrThis.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            PanDatPro.add(txtFecDoc);
            txtFecDoc.setBounds(120, 108, 180, 20);

            cboDiaMesMaxEmiFac.addItem("");
            for (int i = 1; i <= 31; i++) {
                cboDiaMesMaxEmiFac.addItem("" + i);
            }

            intCodMnu = 913;
            switch (intCodMnu) 
            {
                case 828:
                    intCodMod = 1;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModVen();
                    break;  // MODULO VENTAS
                case 873:
                    intCodMod = 2;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModInv();
                    break;  // MODULO INVENTARIO
                case 913:
                    intCodMod = 3;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModCxC();
                    break;  // MODULO CXC
                case 1053:
                    intCodMod = 4;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModCxP();
                    break;  // MODULO CXP
                default:
                    intCodMod = 6;
                    cargarModMae();
                    break;  // MODULO MAESTRO
            }
            txtCodCli.setText(codigoCliente);
            intConUniCli = 1;
        }
        catch (CloneNotSupportedException e) {    objUti.mostrarMsgErr_F1(this, e);    }
    }

    public ZafMae07(ZafParSis obj, String codigoCliente, int intNumMnu) 
    {
        try 
        {
            this.objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();

            initComponents();
            jfrThis = this;

            validadIDRuc = new VerificarId();
            oppMsg = new JOptionPane();
            objTooBar = new MiToolBar(this);
            objPerUsr = new ZafPerUsr(obj);
            this.getContentPane().add(objTooBar, "South");

            txtFecDoc = new ZafDatePicker(((JFrame) jfrThis.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            PanDatPro.add(txtFecDoc);
            txtFecDoc.setBounds(120, 108, 180, 20);

            cboDiaMesMaxEmiFac.addItem("");
            for (int i = 1; i <= 31; i++) {
                cboDiaMesMaxEmiFac.addItem("" + i);
            }

            intCodMnu = intNumMnu;
            switch (intCodMnu) 
            {
                case 828:
                    intCodMod = 1;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModVen();
                    break;  // MODULO VENTAS
                case 873:
                    intCodMod = 2;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModInv();
                    break;  // MODULO INVENTARIO
                case 913:
                    intCodMod = 3;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModCxC();
                    break;  // MODULO CXC
                case 1053:
                    intCodMod = 4;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModCxP();
                    break;  // MODULO CXP
                default:
                    intCodMod = 6;
                    cargarModMae();
                    break;  // MODULO MAESTRO
            }

            txtCodCli.setText(codigoCliente);
            intConUniCli = 1;

            objTooBar.setEstado('w');
        }
        catch (CloneNotSupportedException e) {    objUti.mostrarMsgErr_F1(this, e);    }
    }

    public ZafMae07(ZafParSis obj, Integer codigoCliente, Integer intNumMnu)
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(obj);
            initComponents();

            jfrThis = this;

            oppMsg = new JOptionPane();
            objTooBar = new MiToolBar(this);
            this.getContentPane().add(objTooBar, "South");

            validadIDRuc = new VerificarId();

            txtFecDoc = new ZafDatePicker(((JFrame) jfrThis.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            PanDatPro.add(txtFecDoc);
            txtFecDoc.setBounds(120, 108, 180, 20);

            cboDiaMesMaxEmiFac.addItem("");
            for (int i = 1; i <= 31; i++) {
                cboDiaMesMaxEmiFac.addItem("" + i);
            }

            intCodMnu = intNumMnu.intValue();
            switch (intCodMnu) 
            {
                case 828:
                    intCodMod = 1;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModVen();
                    break;  // MODULO VENTAS
                case 873:
                    intCodMod = 2;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModInv();
                    break;  // MODULO INVENTARIO
                case 913:
                    intCodMod = 3;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModCxC();
                    break;  // MODULO CXC
                case 1053:
                    intCodMod = 4;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModCxP();
                    break;  // MODULO CXP
                default:
                    intCodMod = 6;
                    cargarModMae();
                    break;  // MODULO MAESTRO
            }

            txtCodCli.setText(String.valueOf(codigoCliente.intValue()));
            intConUniCli = 1;

            objTooBar.setEstado('w');
        } 
        catch (CloneNotSupportedException e) {   objUti.mostrarMsgErr_F1(this, e);     }
    }

    public ZafMae07(ZafParSis obj, Integer codEmpresa, Integer codigoCliente, Integer intNumMnu) 
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(obj);
            this.setTitle(objParSis.getNombreMenu() + strVer);

            initComponents();

            jfrThis = this;

            oppMsg = new JOptionPane();
            objTooBar = new MiToolBar(this);

            validadIDRuc = new VerificarId();

            txtFecDoc = new ZafDatePicker(((JFrame) jfrThis.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            PanDatPro.add(txtFecDoc);
            txtFecDoc.setBounds(120, 108, 180, 20);

            cboDiaMesMaxEmiFac.addItem("");
            for (int i = 1; i <= 31; i++) {
                cboDiaMesMaxEmiFac.addItem("" + i);
            }

            intCodMnu = intNumMnu.intValue();
            switch (intCodMnu) 
            {
                case 828:
                    intCodMod = 1;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModVen();
                    break;  // MODULO VENTAS
                case 873:
                    intCodMod = 2;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModInv();
                    break;  // MODULO INVENTARIO
                case 913:
                    intCodMod = 3;
                    strFilCli = " and a.st_cli='S' ";
                    chkCli.setSelected(true);
                    cargarModCxC();
                    break;  // MODULO CXC
                case 1053:
                    intCodMod = 4;
                    strFilCli = " and a.st_prv='S' ";
                    chkPro.setSelected(true);
                    cargarModCxP();
                    break;  // MODULO CXP
                default:
                    intCodMod = 6;
                    cargarModMae();
                    break;  // MODULO MAESTRO
            }

            txtCodCli.setText(String.valueOf(codigoCliente.intValue()));
            intConUniCli = 1;

            objTooBar.setEstado('w');
        } 
        catch (CloneNotSupportedException e) {   objUti.mostrarMsgErr_F1(this, e);    }
    }

    private void cargarModVen() 
    {
        tabGen.removeTabAt(1);
        tabGen.removeTabAt(1);
        tabGen.removeTabAt(3);
        tabGen.removeTabAt(4);
        tabGen.removeTabAt(4);
        tabGen.removeTabAt(4);
        tabGen.removeTabAt(4);
        panObsObs.setLayout(new java.awt.GridLayout(3, 0, -400, 0));
        lblObsVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObsVen.setText("Ventas :");
        panObsObs.add(lblObsVen);
        scroObsVen.setViewportView(txtArObsVen);
        panObsObs.add(scroObsVen);
    }

    private void cargarModInv() 
    {
        tabGen.removeTabAt(1);
        tabGen.removeTabAt(1);
        tabGen.removeTabAt(3);
        tabGen.removeTabAt(4);
        tabGen.removeTabAt(4);
        tabGen.removeTabAt(4);
        tabGen.removeTabAt(4);
        panObsObs.setLayout(new java.awt.GridLayout(3, 0, -400, 0));
        lblobsinv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblobsinv.setText("Inventario : ");
        panObsObs.add(lblobsinv);
        scroObsInv.setViewportView(txtArObsInv);
        panObsObs.add(scroObsInv);
    }

    private void cargarModCxC() 
    {
        tabGen.removeTabAt(5);
        tabGen.removeTabAt(6);
        panDatCabVen.setVisible(false);
        panObsObs.setLayout(new java.awt.GridLayout(3, 0, -400, 0));
        lblObscxc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObscxc.setText("C x C :");
        panObsObs.add(lblObscxc);
        scroObscxc.setViewportView(txtArObscxc);
        panObsObs.add(scroObscxc);
        lblObscxcBurCre.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObscxcBurCre.setText("Buró de Crédito :");
        panObsObs.add(lblObscxcBurCre);
        scroObscxcBurCre.setViewportView(txtArObscxcBurCre);
        panObsObs.add(scroObscxcBurCre);
    }

    private void cargarModCxP()
    {
        tabGen.removeTabAt(1);
        tabGen.removeTabAt(6);
        tabGen.removeTabAt(6);
        panObsObs.setLayout(new java.awt.GridLayout(3, 0, -400, 0));
        lblObscxp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObscxp.setText("C x P :");
        panObsObs.add(lblObscxp);
        scroObscxp.setViewportView(txtArObscxp);
        panObsObs.add(scroObscxp);
    }

    private void cargarModMae()
    {
        panObsObs.setLayout(new java.awt.GridLayout(7, 0, -400, 0));

        lblObsVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObsVen.setText("Ventas :");
        panObsObs.add(lblObsVen);
        scroObsVen.setViewportView(txtArObsVen);
        panObsObs.add(scroObsVen);

        lblobsinv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblobsinv.setText("Inventario : ");
        panObsObs.add(lblobsinv);
        scroObsInv.setViewportView(txtArObsInv);
        panObsObs.add(scroObsInv);

        lblObscxc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObscxc.setText("C x C :");
        panObsObs.add(lblObscxc);
        scroObscxc.setViewportView(txtArObscxc);
        panObsObs.add(scroObscxc);

        lblObscxcBurCre.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObscxcBurCre.setText("Buró de Crédito :");
        panObsObs.add(lblObscxcBurCre);
        scroObscxcBurCre.setViewportView(txtArObscxcBurCre);
        panObsObs.add(scroObscxcBurCre);

        lblObscxp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObscxp.setText("C x P :");
        panObsObs.add(lblObscxp);
        scroObscxp.setViewportView(txtArObscxp);
        panObsObs.add(scroObscxp);

        lblObsObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObsObs1.setText("Observaci\u00f3n 1:");
        panObsObs.add(lblObsObs1);
        scroObsObs1.setViewportView(txtArObsObs1);
        panObsObs.add(scroObsObs1);
        lblObsObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObsObs2.setText("Observaci\u00f3n 2 :");
        panObsObs.add(lblObsObs2);
        scroObsObs2.setViewportView(txtArObsObs2);
        panObsObs.add(scroObsObs2);
    }

    public void Configura_ventana_consulta() 
    { 
        configurarVenConTipPer();
        configurarVenConCiudad();
        configurarVenConVen();
        configurarVenConZonCiu();
        configurarVenConGrpCli();
        ConfigurarTabla();

        if (intConUniCli == 1)
        {
            consultarReg(FilSql());
            objTooBar.afterConsultar();
        }
    }

    private boolean configurarVenConTipPer() 
    {
        boolean blnRes = true;
        try
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipper");
            arlCam.add("a.tx_deslar");
            //arlCam.add("a.tx_descor");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción.");
            //arlAli.add("Desp.Corta");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //arlAncCol.add("30");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_tipper, a.tx_deslar FROM tbm_tipper as a Where a.co_emp =" + objParSis.getCodigoEmpresa() + " ORDER BY a.co_tipper";
            vcoTipPer = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }

    private boolean configurarVenConCiudad() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_ciu");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_ciu, a.tx_deslar FROM tbm_ciu as a ORDER BY a.tx_deslar";
            vcoCiu = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        }
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
        
    private boolean configurarVenConPar() 
    {
        boolean blnRes = true;
        String strSQL = "";
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_Par");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.co_ciu");
            arlCam.add("b.ciu");
            
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Par.");
            arlAli.add("Parroquia");
            arlAli.add("Cód.Ciu.");
            arlAli.add("Ciudad");
            
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("220");
            arlAncCol.add("50");
            arlAncCol.add("200");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_par, a.tx_deslar, b.co_ciu, b.tx_desLar as ciu ";
            strSQL+=" FROM tbm_par as a ";
            strSQL+=" INNER JOIN tbm_ciu as b ON a.co_ciu=b.co_ciu ";
            if (txtCodCiu.getText().length()>0)  {
                strSQL+=" WHERE a.co_ciu=" + txtCodCiu.getText();
            }
            strSQL+= " ORDER BY b.tx_desLar, a.tx_deslar";
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=3;  //Código Ciudad
            
            vcoPar = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } 
        catch (Exception e) {   blnRes = false;      objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    private boolean configurarVenConVen() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())   {
                strSQL="";
                strSQL+=" SELECT a.co_usr, a.tx_usr, a.tx_nom";
                strSQL+=" FROM tbm_usr AS a";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a.co_usr=a2.co_usr)";
                strSQL+=" WHERE a.st_reg='A' AND (a2.st_ven='S' OR a2.st_com='S')";
                strSQL+=" GROUP BY a.co_usr, a.tx_usr, a.tx_nom";
                strSQL+=" ORDER BY a.tx_nom";
            }
            else  {
                strSQL="";
                strSQL+=" SELECT a.co_usr, a.tx_usr, a.tx_nom";
                strSQL+=" FROM tbm_usr AS a";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a.co_usr=a2.co_usr)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.st_reg='A' AND (a2.st_ven='S' OR a2.st_com='S')";
                strSQL+=" ORDER BY a.tx_nom";
            }
            vcoVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } 
        catch (Exception e) {    blnRes = false;        objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    private boolean configurarVenConZonCiu() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_reg");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+="SELECT a.co_reg, a.tx_deslar FROM tbm_var as a where a.co_grp=6 and a.st_reg='A' ORDER BY a.co_reg";
            vcoZon = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } 
        catch (Exception e) {     blnRes = false;    objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    private boolean configurarVenConGrpCli() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_grp");
            arlCam.add("a.tx_nom");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_grp, a.tx_nom FROM tbm_grpcli as a WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
            vcoGrp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } 
        catch (Exception e) {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private boolean configurarVenConCliLib() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            arlAli.add("Direccion.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("143");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_cli,a.tx_nom, a.tx_dir FROM tbr_cliloc as b";
            strSQL+=" INNER JOIN tbm_cli as a on (a.co_emp=b.co_emp and a.co_cli=b.co_cli) ";
            strSQL+=" WHERE a.st_reg='T' " + strFilCli + " AND b.co_emp=" + objParSis.getCodigoEmpresa() + " AND b.co_loc=" + objParSis.getCodigoLocal();
            vcoCliLib = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } 
        catch (Exception e) {      blnRes = false;       objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    public void mostrarZona(String campo,String strBusqueda,int tipo)
    {
        vcoZon.setTitle("Listado de Zonas de Ciudad.");
        if (vcoZon.buscar(campo, strBusqueda)) 
        {
            txtCodZon.setText(vcoZon.getValueAt(1));
            txtZon.setText(vcoZon.getValueAt(2));
            strCodZon = vcoZon.getValueAt(1);
            strNomZon = vcoZon.getValueAt(2);
        }
        else
        {
            vcoZon.setCampoBusqueda(tipo);
            vcoZon.cargarDatos();
            vcoZon.show();
            if (vcoZon.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodZon.setText(vcoZon.getValueAt(1));
                txtZon.setText(vcoZon.getValueAt(2));
                strCodZon = vcoZon.getValueAt(1);
                strNomZon = vcoZon.getValueAt(2);
            } 
            else 
            {
                txtCodZon.setText(strCodZon);
                txtZon.setText(strNomZon);
            }
        }
    }

    public void mostrarGrupo(String campo, String strBusqueda, int tipo) 
    {
        vcoGrp.setTitle("Listado de Grupo.");
        if (vcoGrp.buscar(campo, strBusqueda))
        {
            txtCodGrp.setText(vcoGrp.getValueAt(1));
            txtGrp.setText(vcoGrp.getValueAt(2));
            strCodGrp = vcoGrp.getValueAt(1);
            strNomGrp = vcoGrp.getValueAt(2);
        }
        else 
        {
            vcoGrp.setCampoBusqueda(tipo);
            vcoGrp.cargarDatos();
            vcoGrp.show();
            if (vcoGrp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodGrp.setText(vcoGrp.getValueAt(1));
                txtGrp.setText(vcoGrp.getValueAt(2));
                strCodGrp = vcoGrp.getValueAt(1);
                strNomGrp = vcoGrp.getValueAt(2);
            } 
            else
            {
                txtCodGrp.setText(strCodGrp);
                txtGrp.setText(strNomGrp);
            }
        }
    }

    public void mostrarTipoPersona(String campo, String strBusqueda, int tipo) 
    {
        vcoTipPer.setTitle("Listado de Tipos de Persona");
        if (vcoTipPer.buscar(campo, strBusqueda))
        {
            txtCodTipPer.setText(vcoTipPer.getValueAt(1));
            txtTipPer.setText(vcoTipPer.getValueAt(2));
            // txtDesCorTipPer.setText(vcoTipPer.getValueAt(3));
            strCodTipPer = vcoTipPer.getValueAt(1);
            strNomTipPer = vcoTipPer.getValueAt(2);
        } 
        else 
        {
            vcoTipPer.setCampoBusqueda(tipo);
            vcoTipPer.cargarDatos();
            vcoTipPer.show();
            if (vcoTipPer.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
            {
                txtCodTipPer.setText(vcoTipPer.getValueAt(1));
                txtTipPer.setText(vcoTipPer.getValueAt(2));
                // txtDesCorTipPer.setText(vcoTipPer.getValueAt(3));
                strCodTipPer = vcoTipPer.getValueAt(1);
                strNomTipPer = vcoTipPer.getValueAt(2);
            }
            else 
            {
                txtCodTipPer.setText(strCodTipPer);
                txtTipPer.setText(strNomTipPer);
            }
        }
    }

    public void mostrarCiudad(String campo, String strBusqueda, int tipo) 
    {
        vcoCiu.setTitle("Listado de Ciudades");
        if (vcoCiu.buscar(campo, strBusqueda)) 
        {
            txtCodCiu.setText(vcoCiu.getValueAt(1));
            txtCiu.setText(vcoCiu.getValueAt(2));
            strCodCiu =  vcoCiu.getValueAt(1);
            strCiudad =  vcoCiu.getValueAt(2);
        }
        else 
        {
            vcoCiu.setCampoBusqueda(tipo);
            vcoCiu.setCriterio1(11); 
            vcoCiu.cargarDatos();
            vcoCiu.show();
            if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
            {
                txtCodCiu.setText(vcoCiu.getValueAt(1));
                txtCiu.setText(vcoCiu.getValueAt(2));
                strCodCiu =  vcoCiu.getValueAt(1);
                strCiudad =  vcoCiu.getValueAt(2);
            } 
            else 
            {
                txtCodCiu.setText(strCodCiu);
                txtCiu.setText(strCiudad);
            }
        } 
    }

    public void mostrarParroquia(String campo, String strBusqueda, int tipo)
    {
        vcoPar.setTitle("Listado de Parroquias");
        if (vcoPar.buscar(campo, strBusqueda)) 
        {
            txtCodPar.setText(vcoPar.getValueAt(1));
            txtPar.setText(vcoPar.getValueAt(2));
            txtCodCiu.setText(vcoPar.getValueAt(3));
            txtCiu.setText(vcoPar.getValueAt(4));
            strCodPar = vcoPar.getValueAt(1);
            strNomPar = vcoPar.getValueAt(2);
            strCodCiu = vcoPar.getValueAt(3);
            strCiudad = vcoPar.getValueAt(4);
        }
        else 
        {
            vcoPar.setCampoBusqueda(tipo);
            vcoPar.setCriterio1(11); 
            vcoPar.cargarDatos();
            vcoPar.show();
            if (vcoPar.getSelectedButton() == vcoPar.INT_BUT_ACE) 
            {
                txtCodPar.setText(vcoPar.getValueAt(1));
                txtPar.setText(vcoPar.getValueAt(2));
                txtCodCiu.setText(vcoPar.getValueAt(3));
                txtCiu.setText(vcoPar.getValueAt(4));
                strCodPar = vcoPar.getValueAt(1);
                strNomPar = vcoPar.getValueAt(2);
                strCodCiu = vcoPar.getValueAt(3);
                strCiudad = vcoPar.getValueAt(4);
            } 
            else 
            {
                txtCodPar.setText(strCodPar);
                txtPar.setText(strNomPar);
                txtCodCiu.setText(strCodCiu);
                txtCiu.setText(strCiudad);
            }
        }  
    }

    public void mostrarVendedor(String campo, String strBusqueda, int tipo)
    {
        vcoVen.setTitle("Listado de Vendedores");
        if (vcoVen.buscar(campo, strBusqueda)) 
        {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
            strCodVen = vcoVen.getValueAt(1);
            strNomVen = vcoVen.getValueAt(2);
        } 
        else 
        {
            vcoVen.setCampoBusqueda(tipo);
            vcoVen.cargarDatos();
            vcoVen.show();
            if (vcoVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodVen.setText(vcoVen.getValueAt(1));
                txtNomVen.setText(vcoVen.getValueAt(2));
                strCodVen = vcoVen.getValueAt(1);
                strNomVen = vcoVen.getValueAt(2);
            }
            else 
            {
                txtCodVen.setText(strCodVen);
                txtNomVen.setText(strNomVen);
            }
        }
    }
    
    private void ConfigurarTabla()
    {
        ConfigurarTblContactos();
        ConfigurarTblDirecciones();
        ConfigurarTblAccionistas();
        ConfigurarTblBeneficiario();
        ConfigurarTblObservacion();
        ConfigurarTblClientesLocal();
        configurarVenConCliLib();
        configurarTblVentas(); 
        addListenerCamTabGen();
        blnHayCam = false;
     }

    private void ConfigurarTblContactos() 
    {
        vecDat = new Vector();    //Almacena los datos
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_CON_LIN, "");
        vecCab.add(INT_TBL_CON_NOMMOD, "Nom.Mod.");
        vecCab.add(INT_TBL_CON_NOM, "Nombre");
        vecCab.add(INT_TBL_CON_CAR, "Cargo");
        vecCab.add(INT_TBL_CON_TEL1, "Teléfono 1");
        vecCab.add(INT_TBL_CON_TEL2, "Teléfono 2");
        vecCab.add(INT_TBL_CON_TEL3, "Teléfono 3");
        vecCab.add(INT_TBL_CON_MA1, "email 1");
        vecCab.add(INT_TBL_CON_MA2, "email 2");
        vecCab.add(INT_TBL_CON_OBS, "Observación");
        vecCab.add(INT_TBL_CON_MODC, "Modulo");
        vecCab.add(INT_TBL_CON_LOCC, "Cod.Loc.");
        objTblModCon = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModCon.setHeader(vecCab);
        tblCon.setModel(objTblModCon);
        tblCon.setRowSelectionAllowed(false);
        tblCon.setCellSelectionEnabled(true);
        tblCon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblCon, INT_TBL_CON_LIN);

        tblCon.getModel().addTableModelListener(new LisCambioTblCon());
        tblCon.getTableHeader().setReorderingAllowed(false);
        tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblCon.getColumnModel();

        tcmAux.getColumn(INT_TBL_CON_LIN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_CON_NOMMOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CON_NOM).setPreferredWidth(95);
        tcmAux.getColumn(INT_TBL_CON_CAR).setPreferredWidth(85);
        tcmAux.getColumn(INT_TBL_CON_TEL1).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_CON_TEL2).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_CON_TEL3).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_CON_MA1).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CON_MA2).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CON_OBS).setPreferredWidth(90);

        if ((intCodMnu == 828) || (intCodMnu == 873) || (intCodMnu == 913) || (intCodMnu == 1053)) 
        {
            tcmAux.getColumn(INT_TBL_CON_NOMMOD).setWidth(0);
            tcmAux.getColumn(INT_TBL_CON_NOMMOD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CON_NOMMOD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CON_NOMMOD).setPreferredWidth(0);
        }

        tcmAux.getColumn(INT_TBL_CON_MODC).setWidth(0);
        tcmAux.getColumn(INT_TBL_CON_MODC).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_CON_MODC).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_CON_MODC).setPreferredWidth(0);

        tcmAux.getColumn(INT_TBL_CON_LOCC).setWidth(0);
        tcmAux.getColumn(INT_TBL_CON_LOCC).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_CON_LOCC).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_CON_LOCC).setPreferredWidth(0);

        //Configurar JTable: Establecer columnas editables.
        //Jose Marin: solo modificables si tienen los permisos o entra por el maestro de clientes
        if (objPerUsr.isOpcionEnabled(2993) || objPerUsr.isOpcionEnabled(3003) || objPerUsr.isOpcionEnabled(2997) || objPerUsr.isOpcionEnabled(3010) || intCodMod == 6) 
        {
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CON_NOM);
            vecAux.add("" + INT_TBL_CON_CAR);
            vecAux.add("" + INT_TBL_CON_TEL1);
            vecAux.add("" + INT_TBL_CON_TEL2);
            vecAux.add("" + INT_TBL_CON_TEL3);
            vecAux.add("" + INT_TBL_CON_MA1);
            vecAux.add("" + INT_TBL_CON_MA2);
            vecAux.add("" + INT_TBL_CON_OBS);
            objTblModCon.setColumnasEditables(vecAux);
            vecAux = null;
        }

        tblCon.getTableHeader().setReorderingAllowed(false);
    
        ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCon);

        objTblCelEdiTxtCom = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblCon);
        tcmAux.getColumn(INT_TBL_CON_NOM).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_CAR).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_TEL1).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_TEL2).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_TEL3).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_MA1).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_MA2).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_CON_OBS).setCellEditor(objTblCelEdiTxtCom);
        objTblCelEdiTxtCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });
        objTblCelEdiTxtCom = null;

        tcmAux = null;
        setEditableCon(true);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblCon);
    }

    class LisCambioTblCon implements javax.swing.event.TableModelListener
    {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e){
            if(objTblModCon.isDataModelChanged()){
               blnHayCamTblCon = true;
            }
        }
    }

    public void setEditableCon(boolean editable) 
    {
        if (editable == true) {
            objTblModCon.setModoOperacion(ZafTblMod.INT_TBL_INS);
        } else {
            objTblModCon.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }    
    
    private void configurarTblVentas()
    {
        //Jose Marin: solo modificables si tienen los permisos o entra por el maestro de clientes
        if (objPerUsr.isOpcionEnabled(3826) || intCodMod == 6) {
            cboDiaMesMaxEmiFac.setEnabled(true);
            cboDiaSemEmiFac.setEnabled(true);
        } 
        else  {
            cboDiaMesMaxEmiFac.setEnabled(false);
            cboDiaSemEmiFac.setEnabled(false);
        }
    }
    
    private void ConfigurarTblDirecciones() 
    {
        vecDat = new Vector();    //Almacena los datos
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_DIR_LIN, "");
        vecCab.add(INT_TBL_DIR_NMODDI, "Nom.Mod.");
        vecCab.add(INT_TBL_DIR_DIR, "Dirección");
        vecCab.add(INT_TBL_DIR_REF, "Referencia");
        vecCab.add(INT_TBL_DIR_TEL1, "Teléfono 1");
        vecCab.add(INT_TBL_DIR_TEL2, "Teléfono 2");
        vecCab.add(INT_TBL_DIR_TEL3, "Teléfono 3");
        vecCab.add(INT_TBL_DIR_OBS2, "Observación");
        vecCab.add(INT_TBL_DIR_MODD, "Modulo");
        vecCab.add(INT_TBL_DIR_CONT, "CONTROL.");  /* José Mario 20/Oct/2015 */

        objTblModDir = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModDir.setHeader(vecCab);
        tblDir.setModel(objTblModDir);
        tblDir.setRowSelectionAllowed(false);
        tblDir.setCellSelectionEnabled(true);
        tblDir.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // ZafColNumerada zafColNumerada = new ZafColNumerada(tblDir,INT_TBL_CON_LIN);
        //Configurar JTable: Establecer la fila de cabecera.
        objColNum = new ZafColNumerada(tblDir, INT_TBL_DIR_LIN);

        tblDir.getModel().addTableModelListener(new LisCambioTblDir());
        tblDir.getTableHeader().setReorderingAllowed(false);

        tblDir.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblDir.getColumnModel();

        tcmAux.getColumn(INT_TBL_DIR_LIN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DIR_NMODDI).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DIR_DIR).setPreferredWidth(152);
        tcmAux.getColumn(INT_TBL_DIR_REF).setPreferredWidth(130);
        tcmAux.getColumn(INT_TBL_DIR_TEL1).setPreferredWidth(83);
        tcmAux.getColumn(INT_TBL_DIR_TEL2).setPreferredWidth(83);
        tcmAux.getColumn(INT_TBL_DIR_TEL3).setPreferredWidth(83);
        tcmAux.getColumn(INT_TBL_DIR_OBS2).setPreferredWidth(110);
        tcmAux.getColumn(INT_TBL_DIR_CONT).setPreferredWidth(50);  /* José Mario 20/Oct/2015 */


        if ((intCodMnu == 828) || (intCodMnu == 873) || (intCodMnu == 913) || (intCodMnu == 1053))
        {
            tcmAux.getColumn(INT_TBL_DIR_NMODDI).setWidth(0);
            tcmAux.getColumn(INT_TBL_DIR_NMODDI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DIR_NMODDI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DIR_NMODDI).setPreferredWidth(0);
        }

        if (intCodMnu == 828)
        {
            objTblModDir.setModoOperacion(objTblModDir.INT_TBL_INS);
        }

        tcmAux.getColumn(INT_TBL_DIR_MODD).setWidth(0);
        tcmAux.getColumn(INT_TBL_DIR_MODD).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_DIR_MODD).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_DIR_MODD).setPreferredWidth(0);

        objTblModDir.addSystemHiddenColumn(INT_TBL_DIR_CONT, tblDir);  /* José Mario 20/Oct/2015 */
        //Configurar JTable: Establecer columnas editables.
        //Jose Marin: solo modificables si tienen los permisos o entra por el maestro de clientes

        if (objPerUsr.isOpcionEnabled(2992) || objPerUsr.isOpcionEnabled(3002) || objPerUsr.isOpcionEnabled(2996) || objPerUsr.isOpcionEnabled(3009) || intCodMod == 6) 
        {
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DIR_DIR);
            vecAux.add("" + INT_TBL_DIR_REF);
            vecAux.add("" + INT_TBL_DIR_TEL1);
            vecAux.add("" + INT_TBL_DIR_TEL2);
            vecAux.add("" + INT_TBL_DIR_TEL3);
            vecAux.add("" + INT_TBL_DIR_OBS2);
            objTblModDir.setColumnasEditables(vecAux);
            vecAux = null;
        }

        objTblCelEdiTxtCom = new ZafTblCelEdiTxt(tblDir);
        tcmAux.getColumn(INT_TBL_DIR_DIR).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_DIR_REF).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_DIR_TEL1).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_DIR_TEL2).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_DIR_TEL3).setCellEditor(objTblCelEdiTxtCom);
        tcmAux.getColumn(INT_TBL_DIR_OBS2).setCellEditor(objTblCelEdiTxtCom);
        objTblCelEdiTxtCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            String strEst;
            String strDir, strRef, strTel1, strTel2, strTel3, strObs;

            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
            {
                if (!(objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_CONT) == null))   /* José Mario 20/Oct/2015 */
                {    
                    mostrarMsgInf("No se permite modificar Direcciones");
                    strDir = objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_DIR).toString() == null ? "" : objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_DIR).toString();
                    strRef = objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_REF).toString() == null ? "" : objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_REF).toString();
                    strTel1 = objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_TEL1).toString() == null ? "" : objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_TEL1).toString();
                    strTel2 = objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_TEL2).toString() == null ? "" : objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_TEL2).toString();
                    strTel3 = objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_TEL3).toString() == null ? "" : objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_TEL3).toString();
                    strObs = objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_OBS2).toString() == null ? "" : objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_OBS2).toString();
                }
            }

            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if (!(objTblModDir.getValueAt(tblDir.getSelectedRow(), INT_TBL_DIR_CONT) == null)) {
                    mostrarMsgInf("No se permite modificar Direcciones");
                    objTblModDir.setValueAt(strDir, tblDir.getSelectedRow(), INT_TBL_DIR_DIR);
                    objTblModDir.setValueAt(strRef, tblDir.getSelectedRow(), INT_TBL_DIR_REF);
                    objTblModDir.setValueAt(strTel1, tblDir.getSelectedRow(), INT_TBL_DIR_TEL1);
                    objTblModDir.setValueAt(strTel2, tblDir.getSelectedRow(), INT_TBL_DIR_TEL2);
                    objTblModDir.setValueAt(strTel3, tblDir.getSelectedRow(), INT_TBL_DIR_TEL3);
                    objTblModDir.setValueAt(strObs, tblDir.getSelectedRow(), INT_TBL_DIR_OBS2); /* José Mario 20/Oct/2015 */
                }
            }
        });
        objTblCelEdiTxtCom = null;

        ZafTblEdi zafTblEdi = new ZafTblEdi(tblDir);
        tcmAux = null;
        setEditableDir(true);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDir);
    }

    class LisCambioTblDir implements javax.swing.event.TableModelListener {

        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            if (objTblModDir.isDataModelChanged()) {
                blnHayCamTblDir = true;
            }
        }
    }

    public void setEditableDir(boolean editable)
    {
        if (editable == true) {
            objTblModDir.setModoOperacion(ZafTblMod.INT_TBL_INS);
        } else {
            objTblModDir.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }
    
    private void ConfigurarTblAccionistas() 
    {
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_ACCLIN, "");
        vecCab.add(INT_TBL_ACCCOD, " ");
        vecCab.add(INT_TBL_ACCNOM, "Nombre");

        objTblModAcc = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModAcc.setHeader(vecCab);
        tblAcc.setModel(objTblModAcc);
        tblAcc.setRowSelectionAllowed(false);
        tblAcc.setCellSelectionEnabled(true);
        tblAcc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblAcc, INT_TBL_ACCLIN);

        tblAcc.getModel().addTableModelListener(new LisCambioTblAcc());
        tblAcc.getTableHeader().setReorderingAllowed(false);

        tblAcc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblAcc.getColumnModel();

        tcmAux.getColumn(INT_TBL_ACCLIN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_ACCCOD).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_ACCNOM).setPreferredWidth(620);

        tcmAux.getColumn(INT_TBL_ACCCOD).setWidth(0);
        tcmAux.getColumn(INT_TBL_ACCCOD).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_ACCCOD).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_ACCCOD).setPreferredWidth(0);

        //Configurar JTable: Establecer columnas editables.
        //Jose Marin: Bloquear sin acceso a PROPIETARIO (3000) o es maestro
        if (objPerUsr.isOpcionEnabled(3000)) {
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_ACCNOM);

            objTblModAcc.setColumnasEditables(vecAux);
            vecAux = null;
        }

        objTblCelEdiTxtCom = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblAcc);
        tcmAux.getColumn(INT_TBL_ACCNOM).setCellEditor(objTblCelEdiTxtCom);
        objTblCelEdiTxtCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });
        objTblCelEdiTxtCom = null;

        ZafTblEdi zafTblEdi = new ZafTblEdi(tblAcc);
        tcmAux = null;
        objTblModAcc.setModoOperacion(ZafTblMod.INT_TBL_INS);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblAcc);
    }

    class LisCambioTblAcc implements javax.swing.event.TableModelListener {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            if (objTblModAcc.isDataModelChanged()) {
                blnHayCamTblAcc = true;
            }
        }
    }

    private void ConfigurarTblBeneficiario() 
    {
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_BEN_LIN, "");
        vecCab.add(INT_TBL_BEN_COD, "Cód.Ben.");
        vecCab.add(INT_TBL_BEN_NOM, "Beneficiario");
        vecCab.add(INT_TBL_BEN_PRE, "Pre.");
        vecCab.add(INT_TBL_BEN_ACT, "Activo");
        vecCab.add(INT_TBL_BEN_INA, "Inactivo");

        objTblModBen = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModBen.setHeader(vecCab);
        tblBenChq.setModel(objTblModBen);
        tblBenChq.setRowSelectionAllowed(true);
        tblBenChq.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblBenChq.getTableHeader().setReorderingAllowed(false);              

        ZafColNumerada zafColNumerada = new ZafColNumerada(tblBenChq, INT_TBL_BEN_LIN);
        tblBenChq.getModel().addTableModelListener(new LisCambioTblBen());

        tblBenChq.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblBenChq.getColumnModel();
        
        tcmAux.getColumn(INT_TBL_BEN_LIN).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_BEN_COD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BEN_NOM).setPreferredWidth(400);
        tcmAux.getColumn(INT_TBL_BEN_PRE).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_BEN_ACT).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_BEN_INA).setPreferredWidth(50);

        //Configurar JTable: Establecer columnas editables.
        //Jose Marin: solo modificables si tienen los permisos o entra por el maestro declientes
        if (objPerUsr.isOpcionEnabled(3011) || intCodMod == 6) 
        {
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_BEN_NOM);
            vecAux.add("" + INT_TBL_BEN_PRE);
            vecAux.add("" + INT_TBL_BEN_ACT);
            vecAux.add("" + INT_TBL_BEN_INA);
            objTblModBen.setColumnasEditables(vecAux);
            vecAux = null;
        }

        ArrayList arlAux = new ArrayList();
        //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
        arlAux = new java.util.ArrayList();
        arlAux.add("" + INT_TBL_BEN_COD);
        objTblModBen.setColsSaveBeforeRemoveRow(arlAux);
        arlAux = null;

        ZafTblEdi zafTblEdi = new ZafTblEdi(tblBenChq);

        objTblCelRenChkP = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        objTblCelRenChkA = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        objTblCelRenChkI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();

        tcmAux.getColumn(INT_TBL_BEN_PRE).setCellRenderer(objTblCelRenChkP);
        tcmAux.getColumn(INT_TBL_BEN_ACT).setCellRenderer(objTblCelRenChkA);
        tcmAux.getColumn(INT_TBL_BEN_INA).setCellRenderer(objTblCelRenChkI);

        objTblCelEdiChkP = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_BEN_PRE).setCellEditor(objTblCelEdiChkP);

        objTblCelEdiChkA = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_BEN_ACT).setCellEditor(objTblCelEdiChkA);

        objTblCelEdiChkI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_BEN_INA).setCellEditor(objTblCelEdiChkI);

        objTblCelEdiChkP.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                //  int intNumFil = tblDat.getSelectedRow();
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblBenChq.getSelectedRow();
                for (int x = 0; x < tblBenChq.getRowCount(); x++) {
                    tblBenChq.setValueAt(false, x, INT_TBL_BEN_PRE);
                }
                tblBenChq.setValueAt(true, intNumFil, INT_TBL_BEN_PRE);
                tblBenChq.setValueAt(false, intNumFil, INT_TBL_BEN_ACT);
                tblBenChq.setValueAt(false, intNumFil, INT_TBL_BEN_INA);
            }
        });

        objTblCelEdiChkA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                //  int intNumFil = tblDat.getSelectedRow();
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblBenChq.getSelectedRow();
                tblBenChq.setValueAt(false, intNumFil, INT_TBL_BEN_PRE);
                tblBenChq.setValueAt(true, intNumFil, INT_TBL_BEN_ACT);
                tblBenChq.setValueAt(false, intNumFil, INT_TBL_BEN_INA);
            }
        });

        objTblCelEdiChkI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                //  int intNumFil = tblDat.getSelectedRow();
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblBenChq.getSelectedRow();
                tblBenChq.setValueAt(false, intNumFil, INT_TBL_BEN_PRE);
                tblBenChq.setValueAt(false, intNumFil, INT_TBL_BEN_ACT);
                tblBenChq.setValueAt(true, intNumFil, INT_TBL_BEN_INA);
            }
        });

        objTblCelEdiTxtCom = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblBenChq);
        tcmAux.getColumn(INT_TBL_BEN_NOM).setCellEditor(objTblCelEdiTxtCom);
        objTblCelEdiTxtCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });
        objTblCelEdiTxtCom = null;

        tcmAux = null;
        objTblModBen.setModoOperacion(ZafTblMod.INT_TBL_INS);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblBenChq);

    }

    class LisCambioTblBen implements javax.swing.event.TableModelListener {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            if (objTblModBen.isDataModelChanged()) {
                blnHayCamTblBen = true;
            }
        }
    }

    private void ConfigurarTblObservacion() 
    {
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_CON_OBSLIN, "");
        vecCab.add(INT_TBL_CON_OBSNMOD, "Nom.Mod.");
        vecCab.add(INT_TBL_CON_OBSCOD, "Cod.Reg. ");
        vecCab.add(INT_TBL_CON_OBSMOD, "Mod.");
        vecCab.add(INT_TBL_CON_OBSFEC, "Fecha.");
        vecCab.add(INT_TBL_CON_OBSOBS, "Observación.");
        vecCab.add(INT_TBL_CON_LOC, "Cod.Loc");
        vecCab.add(INT_TBL_CON_OBSBUT, "...");

        objTblModObs = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModObs.setHeader(vecCab);
        tblObs.setModel(objTblModObs);
        tblObs.setRowSelectionAllowed(false);
        tblObs.setCellSelectionEnabled(true);
        tblObs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblObs, INT_TBL_ACCLIN);

        tblObs.getModel().addTableModelListener(new LisCambioTblObs());
        tblObs.getTableHeader().setReorderingAllowed(false);

        tblObs.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblObs.getColumnModel();

        tcmAux.getColumn(INT_TBL_CON_OBSLIN).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_CON_OBSNMOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CON_OBSCOD).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_CON_OBSMOD).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_CON_OBSFEC).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_CON_OBSOBS).setPreferredWidth(480);
        tcmAux.getColumn(INT_TBL_CON_OBSBUT).setPreferredWidth(20);

        if ((intCodMnu == 828) || (intCodMnu == 873) || (intCodMnu == 913) || (intCodMnu == 1053)) 
        {
            tcmAux.getColumn(INT_TBL_CON_OBSNMOD).setWidth(0);
            tcmAux.getColumn(INT_TBL_CON_OBSNMOD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CON_OBSNMOD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CON_OBSNMOD).setPreferredWidth(0);
        }

        tcmAux.getColumn(INT_TBL_CON_OBSCOD).setWidth(0);
        tcmAux.getColumn(INT_TBL_CON_OBSCOD).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_CON_OBSCOD).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_CON_OBSCOD).setPreferredWidth(0);

        tcmAux.getColumn(INT_TBL_CON_OBSMOD).setWidth(0);
        tcmAux.getColumn(INT_TBL_CON_OBSMOD).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_CON_OBSMOD).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_CON_OBSMOD).setPreferredWidth(0);

        tcmAux.getColumn(INT_TBL_CON_LOC).setWidth(0);
        tcmAux.getColumn(INT_TBL_CON_LOC).setMaxWidth(0);
        tcmAux.getColumn(INT_TBL_CON_LOC).setMinWidth(0);
        tcmAux.getColumn(INT_TBL_CON_LOC).setPreferredWidth(0);

        //Configurar JTable: Establecer columnas editables.
        //Jose Marin: Si el usuario tiene la opcion "MODIFICAR OBSERVACION"
        if (objPerUsr.isOpcionEnabled(2994) || objPerUsr.isOpcionEnabled(2998) || objPerUsr.isOpcionEnabled(3004) || objPerUsr.isOpcionEnabled(3012) || intCodMod == 6) 
        {
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_CON_OBSOBS);
            vecAux.add("" + INT_TBL_CON_OBSBUT);
            objTblModObs.setColumnasEditables(vecAux);
            vecAux = null;
        }

        //Configurar objTblModObs: Establecer las columnas ELIMINADAS
        java.util.ArrayList arlAux = new java.util.ArrayList();
        arlAux.add("" + INT_TBL_CON_LOC);
        arlAux.add("" + INT_TBL_CON_OBSCOD);
        arlAux.add("" + INT_TBL_CON_OBSMOD);
        objTblModObs.setColsSaveBeforeRemoveRow(arlAux);
        arlAux = null;

        objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_CON_OBSBUT).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut = null;

        ButObs butObs = new ButObs(tblObs, INT_TBL_CON_OBSBUT); //*****

        ZafTblEdi zafTblEdi = new ZafTblEdi(tblObs);

        objTblCelEdiTxtCom = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblObs);
        tcmAux.getColumn(INT_TBL_CON_OBSOBS).setCellEditor(objTblCelEdiTxtCom);
        objTblCelEdiTxtCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            String strObs = "";
//                @Override

            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                String strTmp = "";
                strObs = objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSOBS) == null ? "" : objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSOBS).toString();
                strTmp = objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSFEC) == null ? "" : objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSFEC).toString();
            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                String strTmp = "";
                strTmp = objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSFEC) == null ? "" : objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSFEC).toString();
                //System.out.println("strTmp: " + strTmp);
                if (strTmp.length() > 0) {
                    objTblModObs.setValueAt(strObs, tblObs.getSelectedRow(), INT_TBL_CON_OBSOBS);
                }
            }
        });
        objTblCelEdiTxtCom = null;

        Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblObs);
        if (intCodMod != 6) {
            ZafTblPopMn.setEliminarFilaVisible(false);
        }

        tcmAux = null;
        objTblModObs.setModoOperacion(ZafTblMod.INT_TBL_INS);
    }

    class LisCambioTblObs implements javax.swing.event.TableModelListener {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            if (objTblModObs.isDataModelChanged()) {
                blnHayCamTblObs = true;
            }
        }
    }

    private class ButObs extends Librerias.ZafTableColBut.ZafTableColBut_uni 
    {
        public ButObs(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx, "Observación.");
        }
        @Override
        public void butCLick() {
            int intCol = tblObs.getSelectedRow();
            String strTmp = "";
            String strObs = (tblObs.getValueAt(intCol, INT_TBL_CON_OBSOBS) == null ? "" : tblObs.getValueAt(intCol, INT_TBL_CON_OBSOBS).toString());
            strTmp = objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSFEC) == null ? "" : objTblModObs.getValueAt(tblObs.getSelectedRow(), INT_TBL_CON_OBSFEC).toString();
            if (strTmp.equals("")) {
                llamarVenObs(strObs, intCol);
            }
        }
    }

    private void llamarVenObs(String strObs, int intCol) 
    {
        ZafMae07_01 obj1 = new ZafMae07_01(javax.swing.JOptionPane.getFrameForComponent(this), true, strObs);
        obj1.show();
        if (obj1.getAceptar()) {
            tblObs.setValueAt(obj1.getObser(), intCol, INT_TBL_CON_OBSOBS);
        }
        obj1 = null;
    }

    private void ConfigurarTblClientesLocal()
    {
        vecDat = new Vector();    //Almacena los datos
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_LOC_LIN, "");
        vecCab.add(INT_TBL_LOC_CHK, " ");
        vecCab.add(INT_TBL_LOC_COD, "Cod.Loc.");
        vecCab.add(INT_TBL_LOC_NOM, "Local");
        vecCab.add(INT_TBL_LOC_ESTCAR, " ");

        objTblModCliLoc = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModCliLoc.setHeader(vecCab);
        tblCliLoc.setModel(objTblModCliLoc);
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblCliLoc, INT_TBL_ACCLIN);

        tblCliLoc.getModel().addTableModelListener(new LisCambioTblCliLoc());
        tblCliLoc.getTableHeader().setReorderingAllowed(false);

        tblCliLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblCliLoc.getColumnModel();
        //Configurar JTable: Establecer tipo de selección.
        tblCliLoc.setRowSelectionAllowed(true);
        tblCliLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_LOC_COD).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBL_LOC_NOM).setPreferredWidth(500);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_LOC_CHK);

        objTblModCliLoc.setColumnasEditables(vecAux);
        vecAux = null;
        
         ArrayList arlColHid=new ArrayList();
         arlColHid.add(""+INT_TBL_LOC_ESTCAR);
         objTblModCliLoc.setSystemHiddenColumns(arlColHid, tblCliLoc);
         arlColHid=null;

        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
        objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            @Override
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

            }

            @Override
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

            }
        });

        objTblCelRenChk = null;
        objTblCelEdiChk = null;
        ZafTblEdi zafTblEdi = new ZafTblEdi(tblCliLoc);
        tcmAux = null;
        objTblModCliLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblCliLoc);

        cargarLoc();
    }

    private void cargarLoc() 
    {
        Connection conLoc;
        Statement stmLoc; 
        ResultSet rstLoc;
        try 
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            strSQL=" SELECT co_loc, tx_nom FROM tbm_loc WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and st_reg not in('I')";
            stmLoc = conLoc.createStatement();
            rstLoc = stmLoc.executeQuery(strSQL);
            Vector vecData = new Vector();
            while (rstLoc.next()) {
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LOC_LIN, "");
                vecReg.add(INT_TBL_LOC_CHK, "");
                vecReg.add(INT_TBL_LOC_COD, rstLoc.getString("co_loc"));
                vecReg.add(INT_TBL_LOC_NOM, rstLoc.getString("tx_nom"));
                vecReg.add(INT_TBL_LOC_ESTCAR, "");
                vecData.add(vecReg);
            }
            objTblModCliLoc.setData(vecData);
            tblCliLoc.setModel(objTblModCliLoc);
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
            conLoc.close();
            conLoc = null;
        } 
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
    
    class LisCambioTblCliLoc implements javax.swing.event.TableModelListener {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            if (objTblModCliLoc.isDataModelChanged()) {
                blnHayCam = true;
            }
        }
    }
    
    class LisTxtTabGen implements javax.swing.event.DocumentListener {
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCamTabGen = true;
        }
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCamTabGen = true;
        }
        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCamTabGen = true;
        }
    }

    /* Clase de tipo DocumentListener para detectar los cambios en los textcomponent*/
    private void addListenerCamTabGen() 
    {
        objLisCamTabGen = new LisTxtTabGen();
        txtTipPer.getDocument().addDocumentListener(objLisCamTabGen);
        txtNomCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtDirCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtTelCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtFaxCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtWebCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtEmaCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtCasCli.getDocument().addDocumentListener(objLisCamTabGen);
        txtIde.getDocument().addDocumentListener(objLisCamTabGen);
        txtRepleg.getDocument().addDocumentListener(objLisCamTabGen);
        txtCiu.getDocument().addDocumentListener(objLisCamTabGen);
        txtPar.getDocument().addDocumentListener(objLisCamTabGen);
        txtTelCli1.getDocument().addDocumentListener(objLisCamTabGen);
        txtTelCli2.getDocument().addDocumentListener(objLisCamTabGen);
        txtTelCli3.getDocument().addDocumentListener(objLisCamTabGen);
        txtNomVen.getDocument().addDocumentListener(objLisCamTabGen);
        txtGrp.getDocument().addDocumentListener(objLisCamTabGen);
        txtZon.getDocument().addDocumentListener(objLisCamTabGen);
        txtCedPro.getDocument().addDocumentListener(objLisCamTabGen);
        txtNomPro.getDocument().addDocumentListener(objLisCamTabGen);
        txtDomPro.getDocument().addDocumentListener(objLisCamTabGen);
        txtTelPro.getDocument().addDocumentListener(objLisCamTabGen);
        txtNacPro.getDocument().addDocumentListener(objLisCamTabGen);
        txtActPro.getDocument().addDocumentListener(objLisCamTabGen);
        txaObsPro.getDocument().addDocumentListener(objLisCamTabGen);
    }
    
    private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack) 
    {
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(false);
        txtFiel.setBackground(colBack);
    }

    private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) 
    {
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    private void bloqueaObs(javax.swing.JTextArea txtAre, java.awt.Color colBack) 
    {
        txtAre.setEditable(false);
        txtAre.setBackground(colBack);
    }
        
    
    /**
     * Procedimiento que limpia todas las cajas de texto que existen en el frame
     */
    public void limpiarFrm()
    {
        txtCodCli.setText("");
        txtNomCli.setText("");
        txtDirCli.setText("");
        txtTelCli.setText("");
        txtTelCli1.setText("");
        txtTelCli2.setText("");
        txtTelCli3.setText("");
        txtFaxCli.setText("");
        txtCasCli.setText("");
        txtIde.setText("");
        txtWebCli.setText("");
        txtEmaCli.setText("");
        chkCieCre.setSelected(false);
        cboSex.setSelectedIndex(0);
        cboEstCiv.setSelectedIndex(0);

        txtCodTipPer.setText("");
        txtTipPer.setText("");
        txtFecDoc.setHoy();

        txtCedPro.setText("");
        txtNomPro.setText("");
        txtDomPro.setText("");
        txtTelPro.setText("");
        txtNacPro.setText("");

        txtActPro.setText("");
        txaObsPro.setText("");

        txtCorEleFacEle1.setText("");
        txtCorEleFacEle2.setText("");
        optFacElePen.setSelected(false);
        optFacEleNo.setSelected(false);
        optFacEleSi.setSelected(false);

        cboOriIng.setSelectedIndex(0);
        switch (intCodMnu)
        {
            case 828:
                chkPro.setSelected(false);
                chkCli.setSelected(true);
                break;  // MODULO VENTAS
            case 873:
                chkPro.setSelected(true);
                chkCli.setSelected(false);
                break;  // MODULO INVENTARIO
            case 913:
                chkIvaCom.setEnabled(false);
                chkPro.setSelected(false);
                chkCli.setSelected(true);
                break;  // MODULO CXC
            case 1053:
                chkIvaVen.setEnabled(false);
                chkPro.setSelected(true);
                chkCli.setSelected(false);
                break;  // MODULO CXP
        }

        chkIvaCom.setSelected(true);
        chkIvaVen.setSelected(true);
        chkIngNomCot.setSelected(false);

        java.awt.Color colBack = txtTelCli.getBackground();
        bloquea(txtTelCli, colBack);
        bloquea(txtMonCre, colBack);
        bloquea(txtCodCre, colBack);
        bloquea(txtDesCre, colBack);

        txtCiu.setText("");
        txtPar.setText("");   // limpiando texto de parroquia
        txtNomVen.setText("");
        txtCodVen.setText("");
        txtCodCiu.setText("");
        txtCodPar.setText("");   // limpiando codigo de parroquia
        txtCodZon.setText("");
        txtZon.setText("");
        txtCodGrp.setText("");
        txtGrp.setText("");
        txtRepleg.setText("");
        txtRefDirCli.setText("");

        txtFecIng.setText("");
        txtFecUltMod.setText("");
        txtCodUsrIng.setText("");
        txtCodUsrMod.setText("");
        txtNomUsrIng.setText("");
        txtNomUsrMod.setText("");

        txtArObsVen.setText("");
        txtArObsInv.setText("");
        txtArObscxc.setText("");
        txtArObscxcBurCre.setText("");
        txtArObscxp.setText("");
        txtArObsObs1.setText("");
        txtArObsObs2.setText("");

        //Detalle
        objTblModCon.removeAllRows();
        objTblModDir.removeAllRows();
        objTblModBen.removeAllRows();
        objTblModAcc.removeAllRows();
        objTblModObs.removeAllRows();

        for (int i = 0; i < tblCliLoc.getRowCount(); i++) {
            tblCliLoc.setValueAt("false", i, INT_TBL_LOC_CHK);
            tblCliLoc.setValueAt("false", i, INT_TBL_LOC_ESTCAR);
        }

        spiMaxPorDes.setValue(new Integer(0));
        spiDiaGraDocVen.setValue(new Integer(0));
        spiDiaGraSinSop.setValue(new Integer(0));
        spiMarUti.setValue(new Integer(0));
        spiNumMaxVenCon.setValue(new Integer(0));
    }
    
    private boolean isOpcionHabilitada()
    {
        boolean blnRes = false;
        int intCodMenu = objParSis.getCodigoMenu();
        switch (intCodMenu) 
        {
            case 828: // MODULO VENTAS
                switch (tabGen.getSelectedIndex() ){
                    case 0: //TAB GENERAL
                        blnRes=objPerUsr.isOpcionEnabled(2991); break;
                    case 1: //TAB DIRECCIONES
                        blnRes=objPerUsr.isOpcionEnabled(2992); break;
                    case 2: //TAB CONTACTOS
                        blnRes=objPerUsr.isOpcionEnabled(2993); break;
                    case 3: //TAB OBSERVACIONES
                        blnRes=objPerUsr.isOpcionEnabled(2994); break;
                    default:
                        break;
                }
                break;
            case 873: // MODULO INVENTARIO
                switch (tabGen.getSelectedIndex() ){
                    case 0: //TAB GENERAL
                        blnRes=objPerUsr.isOpcionEnabled(2995); break;
                    case 1: //TAB DIRECCIONES
                        blnRes=objPerUsr.isOpcionEnabled(2996); break;
                    case 2: //TAB CONTACTOS
                        blnRes=objPerUsr.isOpcionEnabled(2997); break;
                    case 3: //TAB OBSERVACIONES
                        blnRes=objPerUsr.isOpcionEnabled(2998); break;
                    default:
                        break;
                }
                break;
            case 913: // MODULO CXC
                switch (tabGen.getSelectedIndex() ){
                    case 0: //TAB GENERAL
                        blnRes=objPerUsr.isOpcionEnabled(2999); break;
                    case 1: //TAB PROPIETARIO
                        blnRes=objPerUsr.isOpcionEnabled(3000); break;
                    case 2: //TAB CREDITO
                        blnRes=objPerUsr.isOpcionEnabled(3001); break;
                    case 3: //TAB DIRECCIONES
                        blnRes=objPerUsr.isOpcionEnabled(3002); break;
                    case 4: //TAB CONTACTOS
                        blnRes=objPerUsr.isOpcionEnabled(3003); break;
                    case 5: //TAB OBSERVACIONES
                        blnRes=objPerUsr.isOpcionEnabled(3004); break;
                    case 6: //TAB VENTAS
                        blnRes=true; break;
                    case 7: //TAB IMPUESTOS
                        blnRes=objPerUsr.isOpcionEnabled(3005); break;
                    case 8: //TAB FACTURACION ELECTROINCA
                        blnRes=objPerUsr.isOpcionEnabled(3977); break; 
                    case 9: //TAB AUDITORIA
                        blnRes=objPerUsr.isOpcionEnabled(3006); break;
                    default:
                        break;
                }
                break;
            case 1053: // MODULO CXP
                switch (tabGen.getSelectedIndex() ){
                    case 0: //TAB GENERAL
                        blnRes=objPerUsr.isOpcionEnabled(3007); break;
                    case 1: //TAB CREDITO
                        blnRes=objPerUsr.isOpcionEnabled(3008); break;
                    case 2: //TAB DIRECCIONES
                        blnRes=objPerUsr.isOpcionEnabled(3009); break;
                    case 3: //TAB CONTACTOS
                        blnRes=objPerUsr.isOpcionEnabled(3010); break;
                    case 4: //TAB BENEFICIARIO
                        blnRes=objPerUsr.isOpcionEnabled(3011); break;
                    case 5: //TAB OBSERVACIONES
                        blnRes=objPerUsr.isOpcionEnabled(3012); break;
                    case 6: //TAB IMPUESTOS
                        blnRes=objPerUsr.isOpcionEnabled(3013); break;
                    case 7: //TAB FACTURACION ELECTRONICA
                        blnRes=objPerUsr.isOpcionEnabled(3978); break;    
                    case 8: //TAB AUDITORIA
                        blnRes=objPerUsr.isOpcionEnabled(3014); break;
                    default:
                        break;
                }
                break;
            default: // MODULO MAESTRO
                break;
        }
        return blnRes;
    }    

    private Connection getConexion()
    {
        try
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conn.setAutoCommit(false);
        }
        catch(SQLException e){
            conn=null;
        }
        catch(Exception e){
            conn=null;
        }
        return conn;
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpCorFacEle = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblCodCli = new javax.swing.JLabel();
        lblTipIde = new javax.swing.JLabel();
        lblIde = new javax.swing.JLabel();
        lblNomCli = new javax.swing.JLabel();
        lblDirCli = new javax.swing.JLabel();
        lblTelCli = new javax.swing.JLabel();
        lblFaxCli = new javax.swing.JLabel();
        lblCasCli = new javax.swing.JLabel();
        lblWebCli = new javax.swing.JLabel();
        lblEmaCli = new javax.swing.JLabel();
        lblTipPer = new javax.swing.JLabel();
        lblCiu = new javax.swing.JLabel();
        lblZonCiu = new javax.swing.JLabel();
        lblDirRef = new javax.swing.JLabel();
        lblTelCli1 = new javax.swing.JLabel();
        lblTelCli2 = new javax.swing.JLabel();
        lblTelCli3 = new javax.swing.JLabel();
        lblVen = new javax.swing.JLabel();
        lblGrp = new javax.swing.JLabel();
        lblEst = new javax.swing.JLabel();
        lblRepLeg = new javax.swing.JLabel();
        lblEstCli = new javax.swing.JLabel();
        lblSex = new javax.swing.JLabel();
        lblPar = new javax.swing.JLabel();
        lblEstCiv = new javax.swing.JLabel();
        lblOriIng = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtTipPer = new javax.swing.JTextField();
        txtIde = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        txtDirCli = new javax.swing.JTextField();
        txtRefDirCli = new javax.swing.JTextField();
        txtRepleg = new javax.swing.JTextField();
        txtTelCli = new javax.swing.JTextField();
        txtWebCli = new javax.swing.JTextField();
        txtEmaCli = new javax.swing.JTextField();
        txtTelCli1 = new javax.swing.JTextField();
        txtTelCli2 = new javax.swing.JTextField();
        txtTelCli3 = new javax.swing.JTextField();
        txtFaxCli = new javax.swing.JTextField();
        txtCasCli = new javax.swing.JTextField();
        txtCodCiu = new javax.swing.JTextField();
        txtCiu = new javax.swing.JTextField();
        txtCodPar = new javax.swing.JTextField();
        txtPar = new javax.swing.JTextField();
        txtCodZon = new javax.swing.JTextField();
        txtZon = new javax.swing.JTextField();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        txtCodGrp = new javax.swing.JTextField();
        txtGrp = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        butTipPer = new javax.swing.JButton();
        butCiu = new javax.swing.JButton();
        butPar = new javax.swing.JButton();
        butZon = new javax.swing.JButton();
        butVen = new javax.swing.JButton();
        butGrp = new javax.swing.JButton();
        cboTipIde = new javax.swing.JComboBox();
        cboEst = new javax.swing.JComboBox();
        cboSex = new javax.swing.JComboBox();
        cboEstCiv = new javax.swing.JComboBox();
        cboOriIng = new javax.swing.JComboBox();
        chkCli = new javax.swing.JCheckBox();
        chkPro = new javax.swing.JCheckBox();
        panPro = new javax.swing.JPanel();
        PanDatPro = new javax.swing.JPanel();
        lblCedPro = new javax.swing.JLabel();
        txtCedPro = new javax.swing.JTextField();
        lblNomPro = new javax.swing.JLabel();
        txtNomPro = new javax.swing.JTextField();
        lblDomPro = new javax.swing.JLabel();
        txtDomPro = new javax.swing.JTextField();
        lblTelPro = new javax.swing.JLabel();
        txtTelPro = new javax.swing.JTextField();
        lblNacPro = new javax.swing.JLabel();
        txtNacPro = new javax.swing.JTextField();
        lblConPro = new javax.swing.JLabel();
        lblActPro = new javax.swing.JLabel();
        txtActPro = new javax.swing.JTextField();
        lblObsPro = new javax.swing.JLabel();
        spnObsPro = new javax.swing.JScrollPane();
        txaObsPro = new javax.swing.JTextArea();
        panAcc = new javax.swing.JPanel();
        spnAcc = new javax.swing.JScrollPane();
        tblAcc = new javax.swing.JTable();
        panCre = new javax.swing.JPanel();
        lblMonCre = new javax.swing.JLabel();
        txtMonCre = new javax.swing.JTextField();
        lblForPagCre = new javax.swing.JLabel();
        txtCodCre = new javax.swing.JTextField();
        txtDesCre = new javax.swing.JTextField();
        chkCieCre = new javax.swing.JCheckBox();
        panDir = new javax.swing.JPanel();
        panDatDir = new javax.swing.JPanel();
        spnDir = new javax.swing.JScrollPane();
        tblDir = new javax.swing.JTable();
        panCon = new javax.swing.JPanel();
        spnDatCon = new javax.swing.JScrollPane();
        tblCon = new javax.swing.JTable();
        panBen = new javax.swing.JPanel();
        spnDatBen = new javax.swing.JScrollPane();
        tblBenChq = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panDatObs = new javax.swing.JPanel();
        spnDatObs = new javax.swing.JScrollPane();
        tblObs = new javax.swing.JTable();
        panObsObs = new javax.swing.JPanel();
        panCliLoc = new javax.swing.JPanel();
        spnDatCliLoc = new javax.swing.JScrollPane();
        tblCliLoc = new javax.swing.JTable();
        panVen = new javax.swing.JPanel();
        panDatCabVen = new javax.swing.JPanel();
        lblMaxPorDes = new javax.swing.JLabel();
        spiMaxPorDes = new javax.swing.JSpinner();
        lblDiaGraDocVen = new javax.swing.JLabel();
        spiDiaGraDocVen = new javax.swing.JSpinner();
        lblDiaGraDocSinSop = new javax.swing.JLabel();
        spiDiaGraSinSop = new javax.swing.JSpinner();
        lblMaruti = new javax.swing.JLabel();
        spiMarUti = new javax.swing.JSpinner();
        lblNumMaxVenCon = new javax.swing.JLabel();
        spiNumMaxVenCon = new javax.swing.JSpinner();
        chkIngNomCot = new javax.swing.JCheckBox();
        panDatDetVen = new javax.swing.JPanel();
        panDetVen = new javax.swing.JPanel();
        lblDiaMesMaxEmiFac = new javax.swing.JLabel();
        cboDiaMesMaxEmiFac = new javax.swing.JComboBox();
        lblDiaSemEmiFac = new javax.swing.JLabel();
        cboDiaSemEmiFac = new javax.swing.JComboBox();
        panImp = new javax.swing.JPanel();
        chkIvaVen = new javax.swing.JCheckBox();
        chkIvaCom = new javax.swing.JCheckBox();
        panFacEle = new javax.swing.JPanel();
        panFacEleCorEle = new javax.swing.JPanel();
        lblCliTieCorEleFacEle = new javax.swing.JLabel();
        optFacElePen = new javax.swing.JRadioButton();
        optFacEleSi = new javax.swing.JRadioButton();
        optFacEleNo = new javax.swing.JRadioButton();
        lblCorEleFacEle1 = new javax.swing.JLabel();
        txtCorEleFacEle1 = new javax.swing.JTextField();
        lblCorEleFacEle2 = new javax.swing.JLabel();
        txtCorEleFacEle2 = new javax.swing.JTextField();
        panAud = new javax.swing.JPanel();
        lblFecIng = new javax.swing.JLabel();
        txtFecIng = new javax.swing.JLabel();
        lblFecUltMod = new javax.swing.JLabel();
        txtFecUltMod = new javax.swing.JLabel();
        lblUsrIng = new javax.swing.JLabel();
        txtCodUsrIng = new javax.swing.JLabel();
        txtNomUsrIng = new javax.swing.JLabel();
        lblUsrUltMod = new javax.swing.JLabel();
        txtCodUsrMod = new javax.swing.JLabel();
        txtNomUsrMod = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setName(""); // NOI18N
        setOpaque(true);
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

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana"); // NOI18N
        lblTit.setName(""); // NOI18N
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        tabGen.setPreferredSize(new java.awt.Dimension(469, 699));

        panGen.setLayout(null);

        lblCodCli.setText("Código:"); // NOI18N
        lblCodCli.setToolTipText("Código del cliente/proveedor"); // NOI18N
        panGen.add(lblCodCli);
        lblCodCli.setBounds(4, 4, 120, 20);

        lblTipIde.setText("Tipo de identificación:"); // NOI18N
        lblTipIde.setToolTipText("Tipo de identificación");
        panGen.add(lblTipIde);
        lblTipIde.setBounds(4, 44, 120, 20);

        lblIde.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblIde.setText("Identificación:"); // NOI18N
        panGen.add(lblIde);
        lblIde.setBounds(266, 44, 90, 20);

        lblNomCli.setText("Nombre:"); // NOI18N
        lblNomCli.setToolTipText("Nombre"); // NOI18N
        panGen.add(lblNomCli);
        lblNomCli.setBounds(4, 64, 120, 20);

        lblDirCli.setText("Dirección:"); // NOI18N
        lblDirCli.setToolTipText("Dirección"); // NOI18N
        panGen.add(lblDirCli);
        lblDirCli.setBounds(4, 84, 120, 20);

        lblTelCli.setText("Teléfonos:"); // NOI18N
        lblTelCli.setToolTipText("Telefonos del cliente/proveedor"); // NOI18N
        panGen.add(lblTelCli);
        lblTelCli.setBounds(368, 124, 120, 20);

        lblFaxCli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFaxCli.setText("Fax:"); // NOI18N
        lblFaxCli.setToolTipText("Fax del cliente/proveedor"); // NOI18N
        panGen.add(lblFaxCli);
        lblFaxCli.setBounds(464, 204, 80, 20);

        lblCasCli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCasCli.setText("Casilla:"); // NOI18N
        lblCasCli.setToolTipText("Casilla del cliente/proveedor"); // NOI18N
        panGen.add(lblCasCli);
        lblCasCli.setBounds(464, 224, 80, 20);

        lblWebCli.setText("Web:"); // NOI18N
        lblWebCli.setToolTipText("Sitio web del cliente/proveedor"); // NOI18N
        panGen.add(lblWebCli);
        lblWebCli.setBounds(4, 144, 120, 20);

        lblEmaCli.setText("email:"); // NOI18N
        lblEmaCli.setToolTipText("Correo electrónico del cliente/proveedor"); // NOI18N
        panGen.add(lblEmaCli);
        lblEmaCli.setBounds(4, 164, 120, 20);

        lblTipPer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTipPer.setText("Tipo de persona:"); // NOI18N
        lblTipPer.setToolTipText("Tipo de persona"); // NOI18N
        panGen.add(lblTipPer);
        lblTipPer.setBounds(4, 24, 120, 20);

        lblCiu.setText("Ciudad:"); // NOI18N
        lblCiu.setToolTipText("Ciudad");
        panGen.add(lblCiu);
        lblCiu.setBounds(4, 184, 120, 20);

        lblZonCiu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblZonCiu.setText("Zona:"); // NOI18N
        lblZonCiu.setToolTipText("Zona");
        panGen.add(lblZonCiu);
        lblZonCiu.setBounds(4, 224, 120, 20);

        lblDirRef.setText("Referencia de dirección:"); // NOI18N
        lblDirRef.setToolTipText("Referencia de dirección"); // NOI18N
        panGen.add(lblDirRef);
        lblDirRef.setBounds(4, 104, 120, 20);

        lblTelCli1.setText("Teléfono1:"); // NOI18N
        lblTelCli1.setToolTipText("Teléfono 1"); // NOI18N
        panGen.add(lblTelCli1);
        lblTelCli1.setBounds(464, 144, 80, 20);

        lblTelCli2.setText("Teléfono2:"); // NOI18N
        lblTelCli2.setToolTipText("Teléfono 2"); // NOI18N
        panGen.add(lblTelCli2);
        lblTelCli2.setBounds(464, 164, 80, 20);

        lblTelCli3.setText("Teléfono3:"); // NOI18N
        lblTelCli3.setToolTipText("Teléfono 3"); // NOI18N
        panGen.add(lblTelCli3);
        lblTelCli3.setBounds(464, 184, 80, 20);

        lblVen.setText("Vendedor:"); // NOI18N
        lblVen.setToolTipText("Vendedor");
        panGen.add(lblVen);
        lblVen.setBounds(4, 244, 120, 20);

        lblGrp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGrp.setText("Grupo:"); // NOI18N
        lblGrp.setToolTipText("Grupo");
        panGen.add(lblGrp);
        lblGrp.setBounds(4, 264, 120, 20);

        lblEst.setText("Estado:"); // NOI18N
        lblEst.setToolTipText("Estado");
        panGen.add(lblEst);
        lblEst.setBounds(464, 244, 80, 20);

        lblRepLeg.setText("Representante legal:"); // NOI18N
        lblRepLeg.setToolTipText("Representante legal"); // NOI18N
        panGen.add(lblRepLeg);
        lblRepLeg.setBounds(4, 124, 120, 20);
        panGen.add(lblEstCli);
        lblEstCli.setBounds(380, 280, 270, 20);

        lblSex.setText("Sexo:");
        panGen.add(lblSex);
        lblSex.setBounds(482, 24, 80, 20);

        lblPar.setText("Parroquia:"); // NOI18N
        lblPar.setToolTipText("Parroquia");
        panGen.add(lblPar);
        lblPar.setBounds(4, 204, 120, 20);

        lblEstCiv.setText("Estado civil:");
        panGen.add(lblEstCiv);
        lblEstCiv.setBounds(482, 44, 80, 20);

        lblOriIng.setText("Origen de ingresos:");
        panGen.add(lblOriIng);
        lblOriIng.setBounds(4, 284, 120, 20);

        txtCodCli.setEditable(false);
        txtCodCli.setBackground(objParSis.getColorCamposSistema());
        panGen.add(txtCodCli);
        txtCodCli.setBounds(124, 4, 100, 20);

        txtTipPer.setBackground(objParSis.getColorCamposObligatorios());
        txtTipPer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTipPerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipPerFocusLost(evt);
            }
        });
        txtTipPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipPerActionPerformed(evt);
            }
        });
        panGen.add(txtTipPer);
        txtTipPer.setBounds(124, 24, 240, 20);

        txtIde.setBackground(objParSis.getColorCamposObligatorios());
        txtIde.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeActionPerformed(evt);
            }
        });
        panGen.add(txtIde);
        txtIde.setBounds(357, 44, 120, 20);

        txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panGen.add(txtNomCli);
        txtNomCli.setBounds(124, 64, 540, 20);

        txtDirCli.setBackground(objParSis.getColorCamposObligatorios());
        txtDirCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDirCliFocusLost(evt);
            }
        });
        panGen.add(txtDirCli);
        txtDirCli.setBounds(124, 84, 540, 20);
        panGen.add(txtRefDirCli);
        txtRefDirCli.setBounds(124, 104, 540, 20);
        panGen.add(txtRepleg);
        txtRepleg.setBounds(124, 124, 240, 20);

        txtTelCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelCliFocusLost(evt);
            }
        });
        panGen.add(txtTelCli);
        txtTelCli.setBounds(444, 124, 220, 20);

        txtWebCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtWebCliFocusLost(evt);
            }
        });
        panGen.add(txtWebCli);
        txtWebCli.setBounds(124, 144, 316, 20);

        txtEmaCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmaCliFocusLost(evt);
            }
        });
        panGen.add(txtEmaCli);
        txtEmaCli.setBounds(124, 164, 316, 20);
        panGen.add(txtTelCli1);
        txtTelCli1.setBounds(544, 144, 120, 20);
        panGen.add(txtTelCli2);
        txtTelCli2.setBounds(544, 164, 120, 20);
        panGen.add(txtTelCli3);
        txtTelCli3.setBounds(544, 184, 120, 20);

        txtFaxCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFaxCliFocusLost(evt);
            }
        });
        panGen.add(txtFaxCli);
        txtFaxCli.setBounds(544, 204, 120, 20);

        txtCasCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCasCliFocusLost(evt);
            }
        });
        panGen.add(txtCasCli);
        txtCasCli.setBounds(544, 224, 120, 20);

        txtCodCiu.setBackground(objParSis.getColorCamposObligatorios());
        txtCodCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusLost(evt);
            }
        });
        txtCodCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCiuActionPerformed(evt);
            }
        });
        panGen.add(txtCodCiu);
        txtCodCiu.setBounds(124, 184, 40, 20);

        txtCiu.setBackground(objParSis.getColorCamposObligatorios());
        txtCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCiuFocusLost(evt);
            }
        });
        txtCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiuActionPerformed(evt);
            }
        });
        panGen.add(txtCiu);
        txtCiu.setBounds(164, 184, 276, 20);

        txtCodPar.setBackground(objParSis.getColorCamposObligatorios());
        txtCodPar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodParFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodParFocusLost(evt);
            }
        });
        txtCodPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodParActionPerformed(evt);
            }
        });
        panGen.add(txtCodPar);
        txtCodPar.setBounds(124, 204, 40, 20);

        txtPar.setBackground(objParSis.getColorCamposObligatorios());
        txtPar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtParFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtParFocusLost(evt);
            }
        });
        txtPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtParActionPerformed(evt);
            }
        });
        panGen.add(txtPar);
        txtPar.setBounds(164, 204, 276, 20);

        txtCodZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodZonActionPerformed(evt);
            }
        });
        txtCodZon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodZonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodZonFocusLost(evt);
            }
        });
        panGen.add(txtCodZon);
        txtCodZon.setBounds(124, 224, 40, 20);

        txtZon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtZonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtZonFocusLost(evt);
            }
        });
        txtZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtZonActionPerformed(evt);
            }
        });
        panGen.add(txtZon);
        txtZon.setBounds(164, 224, 276, 20);

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
        panGen.add(txtCodVen);
        txtCodVen.setBounds(124, 244, 40, 20);

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
        panGen.add(txtNomVen);
        txtNomVen.setBounds(164, 244, 276, 20);

        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });
        panGen.add(txtCodGrp);
        txtCodGrp.setBounds(124, 264, 40, 20);

        txtGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGrpFocusLost(evt);
            }
        });
        txtGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrpActionPerformed(evt);
            }
        });
        panGen.add(txtGrp);
        txtGrp.setBounds(164, 264, 276, 20);

        butCli.setText("..."); // NOI18N
        butCli.setEnabled(false);
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panGen.add(butCli);
        butCli.setBounds(224, 4, 20, 20);

        butTipPer.setLabel("..."); // NOI18N
        butTipPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipPerActionPerformed(evt);
            }
        });
        panGen.add(butTipPer);
        butTipPer.setBounds(364, 24, 20, 20);

        butCiu.setLabel("..."); // NOI18N
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panGen.add(butCiu);
        butCiu.setBounds(440, 184, 20, 20);

        butPar.setLabel("..."); // NOI18N
        butPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butParActionPerformed(evt);
            }
        });
        panGen.add(butPar);
        butPar.setBounds(440, 204, 20, 20);

        butZon.setLabel("..."); // NOI18N
        butZon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butZonActionPerformed(evt);
            }
        });
        panGen.add(butZon);
        butZon.setBounds(440, 224, 20, 20);

        butVen.setLabel("..."); // NOI18N
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGen.add(butVen);
        butVen.setBounds(440, 244, 20, 20);

        butGrp.setLabel("..."); // NOI18N
        butGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpActionPerformed(evt);
            }
        });
        panGen.add(butGrp);
        butGrp.setBounds(440, 264, 20, 20);

        cboTipIde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RUC", "Cedula", "Pasaporte", "Consumidor Final", "Identificación del Exterior" }));
        cboTipIde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipIdeActionPerformed(evt);
            }
        });
        panGen.add(cboTipIde);
        cboTipIde.setBounds(124, 44, 140, 20);

        cboEst.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));
        panGen.add(cboEst);
        cboEst.setBounds(544, 244, 120, 20);

        cboSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Aplica", "Masculino", "Femenino", " " }));
        panGen.add(cboSex);
        cboSex.setBounds(564, 24, 100, 20);

        cboEstCiv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Aplica", "Soltero", "Casado", "Divorciado", "Viudo", "Union libre", " " }));
        panGen.add(cboEstCiv);
        cboEstCiv.setBounds(564, 44, 100, 20);

        cboOriIng.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Aplica", "Empleado público", "Empleado privado", "Independiente", "Ama de casa o estudiante", "Rentista", "Jubilado", "Remesas del exterior" }));
        panGen.add(cboOriIng);
        cboOriIng.setBounds(124, 284, 200, 20);

        chkCli.setText("Es cliente"); // NOI18N
        chkCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliActionPerformed(evt);
            }
        });
        panGen.add(chkCli);
        chkCli.setBounds(478, 4, 90, 20);

        chkPro.setText("Es proveedor"); // NOI18N
        chkPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkProActionPerformed(evt);
            }
        });
        panGen.add(chkPro);
        chkPro.setBounds(564, 4, 100, 20);

        tabGen.addTab("General", panGen);

        panPro.setLayout(new java.awt.BorderLayout());

        PanDatPro.setPreferredSize(new java.awt.Dimension(20, 200));
        PanDatPro.setLayout(null);

        lblCedPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCedPro.setText("Cédula / Pasaporte :"); // NOI18N
        PanDatPro.add(lblCedPro);
        lblCedPro.setBounds(8, 8, 116, 15);
        PanDatPro.add(txtCedPro);
        txtCedPro.setBounds(120, 8, 180, 20);

        lblNomPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNomPro.setText("Nombre :"); // NOI18N
        PanDatPro.add(lblNomPro);
        lblNomPro.setBounds(8, 28, 116, 15);
        PanDatPro.add(txtNomPro);
        txtNomPro.setBounds(120, 28, 456, 20);

        lblDomPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDomPro.setText("Domicilio :"); // NOI18N
        PanDatPro.add(lblDomPro);
        lblDomPro.setBounds(8, 52, 116, 15);
        PanDatPro.add(txtDomPro);
        txtDomPro.setBounds(120, 48, 456, 20);

        lblTelPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTelPro.setText("Teléfono :"); // NOI18N
        PanDatPro.add(lblTelPro);
        lblTelPro.setBounds(8, 72, 116, 15);
        PanDatPro.add(txtTelPro);
        txtTelPro.setBounds(120, 68, 180, 20);

        lblNacPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNacPro.setText("Nacionalidad :"); // NOI18N
        PanDatPro.add(lblNacPro);
        lblNacPro.setBounds(8, 92, 116, 15);
        PanDatPro.add(txtNacPro);
        txtNacPro.setBounds(120, 88, 180, 20);

        lblConPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblConPro.setText("Constitución :"); // NOI18N
        lblConPro.setToolTipText("Fecha de constitución de la Empresa.");
        PanDatPro.add(lblConPro);
        lblConPro.setBounds(8, 112, 116, 15);

        lblActPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblActPro.setText("Actividad :"); // NOI18N
        PanDatPro.add(lblActPro);
        lblActPro.setBounds(8, 132, 116, 15);
        PanDatPro.add(txtActPro);
        txtActPro.setBounds(120, 128, 460, 20);

        lblObsPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObsPro.setText("Observación :"); // NOI18N
        PanDatPro.add(lblObsPro);
        lblObsPro.setBounds(8, 156, 116, 15);

        txaObsPro.setLineWrap(true);
        spnObsPro.setViewportView(txaObsPro);

        PanDatPro.add(spnObsPro);
        spnObsPro.setBounds(120, 152, 460, 44);

        panPro.add(PanDatPro, java.awt.BorderLayout.NORTH);

        panAcc.setBorder(javax.swing.BorderFactory.createTitledBorder("Accionistas"));
        panAcc.setAutoscrolls(true);
        panAcc.setLayout(new java.awt.BorderLayout());

        tblAcc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnAcc.setViewportView(tblAcc);

        panAcc.add(spnAcc, java.awt.BorderLayout.CENTER);

        panPro.add(panAcc, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Propietario", panPro);

        panCre.setLayout(null);

        lblMonCre.setText("Monto :"); // NOI18N
        panCre.add(lblMonCre);
        lblMonCre.setBounds(4, 12, 120, 20);
        panCre.add(txtMonCre);
        txtMonCre.setBounds(124, 12, 80, 20);

        lblForPagCre.setText("Forma de pago :"); // NOI18N
        panCre.add(lblForPagCre);
        lblForPagCre.setBounds(4, 32, 120, 20);
        panCre.add(txtCodCre);
        txtCodCre.setBounds(124, 32, 40, 20);
        panCre.add(txtDesCre);
        txtDesCre.setBounds(164, 32, 288, 20);

        chkCieCre.setText(" Cerrar crédito"); // NOI18N
        chkCieCre.setToolTipText("Cerrar crédito");
        chkCieCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCieCreActionPerformed(evt);
            }
        });
        panCre.add(chkCieCre);
        chkCieCre.setBounds(4, 64, 200, 20);

        tabGen.addTab("Cr\u00e9dito", panCre);

        panDir.setLayout(new java.awt.BorderLayout());

        panDatDir.setLayout(new java.awt.BorderLayout());

        tblDir.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDir.setViewportView(tblDir);

        panDatDir.add(spnDir, java.awt.BorderLayout.CENTER);

        panDir.add(panDatDir, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Direcciones", panDir);

        panCon.setLayout(new java.awt.BorderLayout());

        spnDatCon.setAutoscrolls(true);

        tblCon.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatCon.setViewportView(tblCon);

        panCon.add(spnDatCon, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Contactos", panCon);

        panBen.setLayout(new java.awt.BorderLayout());

        tblBenChq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        spnDatBen.setViewportView(tblBenChq);

        panBen.add(spnDatBen, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Beneficiario", panBen);

        panObs.setLayout(new java.awt.BorderLayout());

        panDatObs.setPreferredSize(new java.awt.Dimension(10, 100));
        panDatObs.setLayout(new java.awt.BorderLayout());

        tblObs.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatObs.setViewportView(tblObs);

        panDatObs.add(spnDatObs, java.awt.BorderLayout.CENTER);

        panObs.add(panDatObs, java.awt.BorderLayout.NORTH);

        panObsObs.setLayout(new java.awt.GridLayout(6, 0, -400, 0));
        panObs.add(panObsObs, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Observaciones", panObs);

        panCliLoc.setLayout(new java.awt.BorderLayout());

        tblCliLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatCliLoc.setViewportView(tblCliLoc);

        panCliLoc.add(spnDatCliLoc, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Clientes por Local", panCliLoc);

        panVen.setLayout(new java.awt.BorderLayout());

        panDatCabVen.setPreferredSize(new java.awt.Dimension(100, 130));
        panDatCabVen.setLayout(null);

        lblMaxPorDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMaxPorDes.setText("Máximo % de descuento :"); // NOI18N
        panDatCabVen.add(lblMaxPorDes);
        lblMaxPorDes.setBounds(12, 12, 220, 15);

        spiMaxPorDes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiMaxPorDesStateChanged(evt);
            }
        });
        panDatCabVen.add(spiMaxPorDes);
        spiMaxPorDes.setBounds(244, 12, 64, 20);

        lblDiaGraDocVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDiaGraDocVen.setText("Días de gracia (Documentos vencidos) :"); // NOI18N
        panDatCabVen.add(lblDiaGraDocVen);
        lblDiaGraDocVen.setBounds(12, 40, 220, 15);

        spiDiaGraDocVen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiDiaGraDocVenStateChanged(evt);
            }
        });
        panDatCabVen.add(spiDiaGraDocVen);
        spiDiaGraDocVen.setBounds(244, 36, 64, 20);

        lblDiaGraDocSinSop.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDiaGraDocSinSop.setText("Días de gracia (Documentos sin soporte) :"); // NOI18N
        panDatCabVen.add(lblDiaGraDocSinSop);
        lblDiaGraDocSinSop.setBounds(12, 64, 220, 15);

        spiDiaGraSinSop.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiDiaGraSinSopStateChanged(evt);
            }
        });
        panDatCabVen.add(spiDiaGraSinSop);
        spiDiaGraSinSop.setBounds(244, 60, 64, 20);

        lblMaruti.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMaruti.setText("Margen de utilidad : "); // NOI18N
        panDatCabVen.add(lblMaruti);
        lblMaruti.setBounds(330, 10, 220, 15);

        spiMarUti.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiMarUtiStateChanged(evt);
            }
        });
        panDatCabVen.add(spiMarUti);
        spiMarUti.setBounds(560, 10, 64, 20);

        lblNumMaxVenCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumMaxVenCon.setText("Número máximo de ventas de contado :"); // NOI18N
        panDatCabVen.add(lblNumMaxVenCon);
        lblNumMaxVenCon.setBounds(330, 40, 220, 15);

        spiNumMaxVenCon.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiNumMaxVenConStateChanged(evt);
            }
        });
        panDatCabVen.add(spiNumMaxVenCon);
        spiNumMaxVenCon.setBounds(560, 30, 64, 20);

        chkIngNomCot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkIngNomCot.setText("Permitir ingresar nombre de cliente en la cotizaciones de venta.");
        panDatCabVen.add(chkIngNomCot);
        chkIngNomCot.setBounds(10, 90, 460, 23);

        panVen.add(panDatCabVen, java.awt.BorderLayout.NORTH);

        panDatDetVen.setPreferredSize(new java.awt.Dimension(100, 160));
        panDatDetVen.setLayout(new java.awt.BorderLayout());

        panDetVen.setPreferredSize(new java.awt.Dimension(100, 70));
        panDetVen.setLayout(null);

        lblDiaMesMaxEmiFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDiaMesMaxEmiFac.setText("Día del mes máximo de emisión de facturas:");
        panDetVen.add(lblDiaMesMaxEmiFac);
        lblDiaMesMaxEmiFac.setBounds(10, 10, 260, 20);

        panDetVen.add(cboDiaMesMaxEmiFac);
        cboDiaMesMaxEmiFac.setBounds(230, 10, 140, 20);

        lblDiaSemEmiFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDiaSemEmiFac.setText("Día de la semana de emisión de facturas:");
        panDetVen.add(lblDiaSemEmiFac);
        lblDiaSemEmiFac.setBounds(10, 30, 230, 20);

        cboDiaSemEmiFac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }));
        panDetVen.add(cboDiaSemEmiFac);
        cboDiaSemEmiFac.setBounds(230, 30, 140, 20);

        panDatDetVen.add(panDetVen, java.awt.BorderLayout.CENTER);

        panVen.add(panDatDetVen, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Ventas", panVen);

        panImp.setLayout(null);

        chkIvaVen.setSelected(true);
        chkIvaVen.setText("Cobrar IVA en Ventas.");
        chkIvaVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaVenActionPerformed(evt);
            }
        });
        panImp.add(chkIvaVen);
        chkIvaVen.setBounds(20, 20, 280, 23);

        chkIvaCom.setSelected(true);
        chkIvaCom.setText("Pagar IVA en Compras.");
        chkIvaCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIvaComActionPerformed(evt);
            }
        });
        panImp.add(chkIvaCom);
        chkIvaCom.setBounds(20, 50, 300, 23);

        tabGen.addTab("Impuestos", panImp);

        panFacEle.setLayout(new java.awt.BorderLayout());

        panFacEleCorEle.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panFacEleCorEle.setMaximumSize(new java.awt.Dimension(320, 80));
        panFacEleCorEle.setMinimumSize(new java.awt.Dimension(0, 0));
        panFacEleCorEle.setPreferredSize(new java.awt.Dimension(100, 100));
        panFacEleCorEle.setLayout(null);

        lblCliTieCorEleFacEle.setText("Proporciona correo electrónico:");
        panFacEleCorEle.add(lblCliTieCorEleFacEle);
        lblCliTieCorEleFacEle.setBounds(10, 22, 200, 14);

        grpCorFacEle.add(optFacElePen);
        optFacElePen.setSelected(true);
        optFacElePen.setText("Pendiente");
        panFacEleCorEle.add(optFacElePen);
        optFacElePen.setBounds(220, 20, 90, 23);

        grpCorFacEle.add(optFacEleSi);
        optFacEleSi.setText("Si");
        panFacEleCorEle.add(optFacEleSi);
        optFacEleSi.setBounds(320, 20, 50, 23);

        grpCorFacEle.add(optFacEleNo);
        optFacEleNo.setText("No");
        panFacEleCorEle.add(optFacEleNo);
        optFacEleNo.setBounds(380, 20, 50, 23);

        lblCorEleFacEle1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCorEleFacEle1.setText("Correo 1:"); // NOI18N
        panFacEleCorEle.add(lblCorEleFacEle1);
        lblCorEleFacEle1.setBounds(10, 50, 50, 15);

        txtCorEleFacEle1.setAutoscrolls(false);
        txtCorEleFacEle1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorEleFacEle1FocusLost(evt);
            }
        });
        panFacEleCorEle.add(txtCorEleFacEle1);
        txtCorEleFacEle1.setBounds(60, 50, 320, 20);

        lblCorEleFacEle2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCorEleFacEle2.setText("Correo 2:"); // NOI18N
        panFacEleCorEle.add(lblCorEleFacEle2);
        lblCorEleFacEle2.setBounds(10, 70, 50, 15);

        txtCorEleFacEle2.setAutoscrolls(false);
        txtCorEleFacEle2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorEleFacEle2FocusLost(evt);
            }
        });
        panFacEleCorEle.add(txtCorEleFacEle2);
        txtCorEleFacEle2.setBounds(60, 70, 320, 20);

        panFacEle.add(panFacEleCorEle, java.awt.BorderLayout.NORTH);

        tabGen.addTab("Facturación Electrónica", panFacEle);

        panAud.setLayout(null);

        lblFecIng.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecIng.setText("Fecha de Ingreso :"); // NOI18N
        panAud.add(lblFecIng);
        lblFecIng.setBounds(12, 20, 156, 15);

        txtFecIng.setBackground(new java.awt.Color(255, 255, 255));
        txtFecIng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecIng.setOpaque(true);
        panAud.add(txtFecIng);
        txtFecIng.setBounds(176, 16, 180, 20);

        lblFecUltMod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecUltMod.setText("Fecha de ultima modificación : "); // NOI18N
        panAud.add(lblFecUltMod);
        lblFecUltMod.setBounds(12, 44, 156, 15);

        txtFecUltMod.setBackground(new java.awt.Color(255, 255, 255));
        txtFecUltMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecUltMod.setOpaque(true);
        panAud.add(txtFecUltMod);
        txtFecUltMod.setBounds(176, 38, 180, 20);

        lblUsrIng.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblUsrIng.setText("Usuario de Ingreso :"); // NOI18N
        panAud.add(lblUsrIng);
        lblUsrIng.setBounds(12, 64, 156, 15);

        txtCodUsrIng.setBackground(new java.awt.Color(255, 255, 255));
        txtCodUsrIng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodUsrIng.setOpaque(true);
        panAud.add(txtCodUsrIng);
        txtCodUsrIng.setBounds(176, 61, 40, 20);

        txtNomUsrIng.setBackground(new java.awt.Color(255, 255, 255));
        txtNomUsrIng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNomUsrIng.setOpaque(true);
        panAud.add(txtNomUsrIng);
        txtNomUsrIng.setBounds(218, 61, 300, 20);

        lblUsrUltMod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblUsrUltMod.setText("Usuario de ultima modificación : "); // NOI18N
        panAud.add(lblUsrUltMod);
        lblUsrUltMod.setBounds(12, 88, 156, 15);

        txtCodUsrMod.setBackground(new java.awt.Color(255, 255, 255));
        txtCodUsrMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodUsrMod.setOpaque(true);
        panAud.add(txtCodUsrMod);
        txtCodUsrMod.setBounds(176, 84, 40, 20);

        txtNomUsrMod.setBackground(new java.awt.Color(255, 255, 255));
        txtNomUsrMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNomUsrMod.setOpaque(true);
        panAud.add(txtNomUsrMod);
        txtNomUsrMod.setBounds(218, 84, 300, 20);

        tabGen.addTab("Auditoria", panAud);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtZonFocusLost
         if (!txtZon.getText().equalsIgnoreCase(strNomZon)) 
         {
            if (txtZon.getText().equals(""))      {
                txtZon.setText("");
                txtCodZon.setText("");
            }
            else    {
                mostrarZona("a.tx_deslar",txtZon.getText(),1);
            }
        }
        else
            txtZon.setText(strNomZon);
    }//GEN-LAST:event_txtZonFocusLost

    private void txtZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtZonFocusGained
        strNomZon = txtZon.getText();
        txtZon.selectAll();
    }//GEN-LAST:event_txtZonFocusGained

    private void txtZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtZonActionPerformed
        txtZon.transferFocus();
    }//GEN-LAST:event_txtZonActionPerformed

    private void txtTipPerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipPerFocusGained
        strNomTipPer = txtTipPer.getText();
        txtTipPer.selectAll();
    }//GEN-LAST:event_txtTipPerFocusGained

    private void txtTipPerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipPerFocusLost
        if (!txtTipPer.getText().equalsIgnoreCase(strNomTipPer)) 
        {
            if (txtTipPer.getText().equals(""))     {
                txtTipPer.setText("");
                txtCodTipPer.setText("");
                txtDesCorTipPer.setText("");
            } 
            else       {
                mostrarTipoPersona("a.tx_deslar", txtTipPer.getText(), 1);
            }
        }
        else
            txtTipPer.setText(strNomTipPer);
    }//GEN-LAST:event_txtTipPerFocusLost

    private void txtCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusLost
        configurarVenConPar();
        if (!txtCiu.getText().equalsIgnoreCase(strCiudad))
        {
            txtCodPar.setText("");
            txtPar.setText("");
            if (txtCiu.getText().equals(""))    {
                txtCodCiu.setText("");
                txtCiu.setText("");                
            }
            else  {
                mostrarCiudad("a.tx_deslar", txtCiu.getText(), 1);
            }
        }
        else
            txtCiu.setText(strCiudad);

    }//GEN-LAST:event_txtCiuFocusLost

    private void txtCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusGained
        strCiudad = txtCiu.getText();
        txtCiu.selectAll();
    }//GEN-LAST:event_txtCiuFocusGained

    private void txtCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiuActionPerformed
        txtCiu.transferFocus();
    }//GEN-LAST:event_txtCiuActionPerformed

    private void cboTipIdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipIdeActionPerformed
        blnHayCam = true;
    }//GEN-LAST:event_cboTipIdeActionPerformed

    private void chkProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkProActionPerformed
        blnHayCam = true;
        switch (intCodMnu) 
        {
            case 873: chkPro.setSelected(true);  break;  // MODULO INVENTARIO
            case 1053:  chkPro.setSelected(true);  break;  // MODULO CXP
        }
    }//GEN-LAST:event_chkProActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //if (txtNomCli.getText().length()>80)
        //{
        //  tabGen.setSelectedIndex(0);
        //  mostrarMsgInf("El campo <<Nombre>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
        //  txtNomCli.setText("");
        //  txtNomCli.requestFocus();
        //}
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtDirCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirCliFocusLost
        //if (txtDirCli.getText().length()>120)
        //{
        //  tabGen.setSelectedIndex(0);
        //  mostrarMsgInf("El campo <<Direcciòn>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
        //  txtDirCli.setText("");
        //  txtDirCli.requestFocus();
        //}
    }//GEN-LAST:event_txtDirCliFocusLost

    private void txtTelCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelCliFocusLost
        if (txtTelCli.getText().length() > 60)
        {
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("El campo <<Telèfono>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
            txtTelCli.setText("");
            txtTelCli.requestFocus();
        }
    }//GEN-LAST:event_txtTelCliFocusLost

    private void txtEmaCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmaCliFocusLost
        // if (txtEmaCli.getText().length()>30 )
        //{
        //  tabGen.setSelectedIndex(0);
        //  mostrarMsgInf("El campo <<Correo electronico>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
        //  txtEmaCli.setText("");
        //  txtEmaCli.requestFocus();
        //}
    }//GEN-LAST:event_txtEmaCliFocusLost

    private void txtFaxCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFaxCliFocusLost
        if (txtFaxCli.getText().length()>30 )
        {
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("El campo <<Fax>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
            txtFaxCli.setText("");
            txtFaxCli.requestFocus();
        }
    }//GEN-LAST:event_txtFaxCliFocusLost

    private void txtWebCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtWebCliFocusLost
        //if (txtWebCli.getText().length()>40 )
        //{
        //  tabGen.setSelectedIndex(0);
        //  mostrarMsgInf("El campo <<Web>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
        //  txtWebCli.setText("");
        //  txtWebCli.requestFocus();
        //}
    }//GEN-LAST:event_txtWebCliFocusLost

    private void txtCasCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCasCliFocusLost
        if (txtCasCli.getText().length()>10 )
        {
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("El campo <<Casilla>> ha sobrepasado el limite.\nDigite caracteres validos y vuelva a intentarlo.");
            txtCasCli.setText("");
            txtCasCli.requestFocus();
        }
    }//GEN-LAST:event_txtCasCliFocusLost

    private void txtIdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeActionPerformed
        /*
         aux =5 variable ingresada y quemada para que no ingrese el sistemas a validad por este metodo la identificacion del cliente/proveedor
         dado que en este metodo se esta usando para la validacion la pagina de consulta del sri para RUC y del registro civil para CEDULA
         pero dichas paginas ya salieron de funcionamiento, por este motivo se creo una clase en Librerias.ZafValCedRuc llamada ValidacionId
         esta clase es temporal hasta que se encuentre la manera que poner consultar atraves de alguna pagina para que nos proporcione si es correcta
         el nuemro de identificacion digitado y de paso poder traer los datos principales del cliente y presentarlos automaticamente --- Angie B.
        */
        int aux = 5;

        if (aux == 0) 
        {
            try 
            {
                //if (!objUti.isNumero(txtIde.getText())){
                VerificarId verId = new VerificarId();
                if (!validarExisteCedRuc()) 
                {
                    if (txtIde.getText().length() == 10) 
                    {
                        boolean blnExisteId = verId.verificarCedRegCiv(txtIde.getText());
                        if(blnExisteId){
                            ZafRegCiv zafRegCiv = verId.getRegistroCivil();
                            if(zafRegCiv!=null)
                            {
                                String[] strArrNomApe = zafRegCiv.getStrNomCed().split(" "); //info automaticamente se ingresa
                                if(strArrNomApe!=null)
                                {
                                    if(strArrNomApe.length==4)
                                    {
                                        txtNomCli.setText(strArrNomApe[2] + " " + strArrNomApe[3] + " " + strArrNomApe[0] + " " + strArrNomApe[1]);
                                        txtDirCli.requestFocus();
                                    }
                                    if(strArrNomApe.length>=5 || strArrNomApe.length<=3)
                                    {
                                        String strNomApe = "";
                                        for(int i = 0; i < strArrNomApe.length; i++)
                                        {
                                            strNomApe += strArrNomApe[i].toString() + " ";
                                        }

                                        String strTit, strMsg;
                                        strMsg ="Introduzca Datos:";
                                        strTit="Mensaje del sistema Zafiro";

                                        JPanel panel = new JPanel(new GridLayout(3,2));
                                        JLabel lblNomApe = new JLabel("Apellidos/Nombres:",JLabel.RIGHT);
                                        JTextField txtNomApe = new JTextField(strNomApe);
                                        panel.add(lblNomApe);
                                        panel.add(txtNomApe);
                                        JLabel lblApe = new JLabel("Apellidos:",JLabel.RIGHT);
                                        JTextField txtApe = new JTextField();
                                        panel.add(lblApe);
                                        panel.add(txtApe);
                                        JLabel lblNom = new JLabel("Nombres:",JLabel.RIGHT);
                                        JTextField txtNom = new JTextField();
                                        panel.add(lblNom);
                                        panel.add(txtNom);

                                        if(javax.swing.JOptionPane.showConfirmDialog(this,panel,strMsg,javax.swing.JOptionPane.OK_CANCEL_OPTION,javax.swing.JOptionPane.PLAIN_MESSAGE)==javax.swing.JOptionPane.OK_OPTION)
                                        {
                                            txtNomCli.setText(txtNom.getText() + " " + txtApe.getText());
                                        }else{
                                            txtNomCli.requestFocus();
                                        }
                                    }
                                }
                            }else{
                                boolean blnExisteIdNumVer = verId.verificarCed(txtIde.getText());
                                if(blnExisteIdNumVer){
                                    txtNomCli.requestFocus();
                                }
                            }
                        }
                    }else if(txtIde.getText().length()==13){

                        boolean blnExisteId = verId.verificarRUCSRI(txtIde.getText());
                        if(blnExisteId){
                            ZafSRIDatos zafSRIDatos = verId.getSRIDatos();
                            txtNomCli.setText(zafSRIDatos.getStrRazSoc());
                            txtDirCli.requestFocus();
                        }else{
                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "RUC ingresado no existe!!!";
                            JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "";
                    if(txtIde.getText().length()==10){
                        strMen+= "Número de Cédula ya existe!!!";
                    }else if(txtIde.getText().length()==13){
                        strMen+= "RUC ingresado ya existe!!!";
                    }
                    JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    txtIde.setText("");
                    txtIde.requestFocus();
                }
            } 
            catch (TuvalUtilitiesException ex) 
            {
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = ex.getMessage();
                JOptionPane.showMessageDialog(this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                txtIde.setText("");
                txtIde.requestFocus();
            }
            catch (ArrayIndexOutOfBoundsException ex) 
            {
                ex.printStackTrace();
                //objUti.mostrarMsgErr_F1(this, ex);
                txtNomCli.requestFocus();
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
    }//GEN-LAST:event_txtIdeActionPerformed

    private void chkCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliActionPerformed
        blnHayCam = true;

        switch (intCodMnu) 
        {
            case 828:
                chkCli.setSelected(true);
                break;  // MODULO VENTAS
            case 913:
                chkCli.setSelected(true);
                break;  // MODULO CXC
        }
    }//GEN-LAST:event_chkCliActionPerformed

    private void butTipPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipPerActionPerformed
        vcoTipPer.setTitle("Listado de Tipo de Personas");
        vcoTipPer.setCampoBusqueda(1);
	vcoTipPer.cargarDatos();
        vcoTipPer.show();
        if (vcoTipPer.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
        {
            txtCodTipPer.setText(vcoTipPer.getValueAt(1));
            txtTipPer.setText(vcoTipPer.getValueAt(2));
            //txtDesCorTipPer.setText(vcoTipPer.getValueAt(3));
            strCodTipPer =  vcoTipPer.getValueAt(1);
            strNomTipPer =  vcoTipPer.getValueAt(2);
        }
    }//GEN-LAST:event_butTipPerActionPerformed

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
        //Realiza búsqueda de ciudades.
        vcoCiu.setTitle("Listado de Ciudades");
        vcoCiu.setCampoBusqueda(1);
        vcoCiu.show();
        if (vcoCiu.getSelectedButton()==vcoCiu.INT_BUT_ACE)
        {
            txtCodCiu.setText(vcoCiu.getValueAt(1));
            txtCiu.setText(vcoCiu.getValueAt(2));
            strCodCiu = vcoCiu.getValueAt(1);
            strCiudad = vcoCiu.getValueAt(2);
            //Limpiar las parroquias si se ha seleccionado una ciudad.
            txtPar.setText("");
            txtCodPar.setText("");
        }
    }//GEN-LAST:event_butCiuActionPerformed

    private void butZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butZonActionPerformed
        vcoZon.setTitle("Listado de Zonas de la Ciudad");
        vcoZon.setCampoBusqueda(1);
        vcoZon.show();
        if (vcoZon.getSelectedButton()==ZafVenCon.INT_BUT_ACE) 
        {
            txtCodZon.setText(vcoZon.getValueAt(1));
            txtZon.setText(vcoZon.getValueAt(2));
            strCodZon =  vcoZon.getValueAt(1);
            strNomZon =  vcoZon.getValueAt(2);
        }
    }//GEN-LAST:event_butZonActionPerformed

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

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
                mostrarVendedor("a.tx_nom",txtNomVen.getText(),1);
            }
        }
        else
            txtNomVen.setText(strNomVen);
    }//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        vcoVen.setTitle("Listado de Vendedores");
        vcoVen.setCampoBusqueda(1);
	vcoVen.cargarDatos();
        vcoVen.show();
        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) 
        {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
            strCodVen =  vcoVen.getValueAt(1);
            strNomVen =  vcoVen.getValueAt(2);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

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
                mostrarVendedor("a.co_usr",txtCodVen.getText(),0);
            }
        }
        else
            txtCodVen.setText(strCodVen);
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtTipPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipPerActionPerformed
        txtTipPer.transferFocus();
    }//GEN-LAST:event_txtTipPerActionPerformed


    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg = "¿Está seguro que desea cerrar este programa?";
        String strTit="Mensaje del sistema Zafiro";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
        {
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpActionPerformed
        vcoGrp.setTitle("Listado de Grupo.");
        vcoGrp.setCampoBusqueda(1);
        vcoGrp.show();
        if (vcoGrp.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
        {
            txtCodGrp.setText(vcoGrp.getValueAt(1));
            txtGrp.setText(vcoGrp.getValueAt(2));
            strCodGrp = vcoGrp.getValueAt(1);
            strNomGrp = vcoGrp.getValueAt(2);
        }
    }//GEN-LAST:event_butGrpActionPerformed

    private void txtGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusGained
        strNomGrp = txtGrp.getText();
        txtGrp.selectAll();
    }//GEN-LAST:event_txtGrpFocusGained

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        strCodGrp = txtCodGrp.getText();
        txtCodGrp.selectAll();
    }//GEN-LAST:event_txtCodGrpFocusGained

    private void txtCodCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusGained
        strCodCiu = txtCodCiu.getText();
        txtCodCiu.selectAll();
    }//GEN-LAST:event_txtCodCiuFocusGained

    private void txtCodCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusLost
        configurarVenConPar();
        if (!txtCodCiu.getText().equalsIgnoreCase(strCodCiu))
        {
            txtPar.setText("");
            txtCodPar.setText("");
            
            if (txtCodCiu.getText().equals("")) 
            {
                txtCodCiu.setText("");
                txtCiu.setText("");
                txtCodPar.setText("");
                txtPar.setText("");
            }
            else
            {
                mostrarCiudad("a.co_ciu", txtCodCiu.getText(), 0);
            }
        } 
        else 
        {
            txtCodCiu.setText(strCodCiu);
        }

    }//GEN-LAST:event_txtCodCiuFocusLost

    private void txtGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrpActionPerformed
        txtGrp.transferFocus();
    }//GEN-LAST:event_txtGrpActionPerformed

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        txtCodGrp.transferFocus();
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGrpFocusLost
        if (!txtGrp.getText().equalsIgnoreCase(strNomGrp)) 
        {
            if (txtGrp.getText().equals(""))
            {
                txtGrp.setText("");
                txtCodGrp.setText("");
            } 
            else 
            {
                mostrarGrupo("a.tx_nom", txtGrp.getText(), 2);
            }
        }
        else 
        {
            txtGrp.setText(strNomGrp);
        }
    }//GEN-LAST:event_txtGrpFocusLost

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp)) 
        {
            if (txtCodGrp.getText().equals("")) 
            {
                txtGrp.setText("");
                txtCodGrp.setText("");
            } 
            else 
            {
                mostrarGrupo("a.co_grp", txtCodGrp.getText(), 1);
            }
        } 
        else 
        {
            txtCodGrp.setText(strCodGrp);
        }

    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodZonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodZonFocusGained
        strCodZon = txtCodZon.getText();
        txtCodZon.selectAll();
    }//GEN-LAST:event_txtCodZonFocusGained

    private void txtCodZonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodZonFocusLost
        if (!txtCodZon.getText().equalsIgnoreCase(strCodZon)) 
        {
            if (txtCodZon.getText().equals("")) 
            {
                txtZon.setText("");
                txtCodZon.setText("");
            } 
            else 
            {
                mostrarZona("a.co_reg", txtCodZon.getText(), 0);
            }
        }
        else 
        {
            txtCodZon.setText(strCodZon);
        }
    }//GEN-LAST:event_txtCodZonFocusLost

    private void txtCodZonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodZonActionPerformed
        txtCodZon.transferFocus();
    }//GEN-LAST:event_txtCodZonActionPerformed

    private void txtCodCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCiuActionPerformed
        txtCodCiu.transferFocus();
    }//GEN-LAST:event_txtCodCiuActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        try 
        {
            String strSQL =" SELECT a.co_cli,a.tx_nom, a.tx_dir FROM tbr_cliloc as b"
                          +" INNER JOIN tbm_cli as a on (a.co_emp=b.co_emp AND a.co_cli=b.co_cli) "
                          +" WHERE b.co_emp=" + objParSis.getCodigoEmpresa() +" AND b.co_loc=" + objParSis.getCodigoLocal()+" "+ strFilCli ;
                          //+" AND a.st_reg='T' " ;
            ZafMae07_02 obj = new ZafMae07_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            obj.cargaTexto(strSQL);
            obj.show();
            if (obj.acepta()) 
            {
                txtCodCli.setText(obj.GetCamSel(1));
                objTooBar.consultar();
                //objTooBar.setEstado('m');
                butCli.setEnabled(false);
            }
            obj.dispose();
            obj = null;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
}//GEN-LAST:event_butCliActionPerformed

    private void chkCieCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCieCreActionPerformed
        blnHayCamTabGen = true;
    }//GEN-LAST:event_chkCieCreActionPerformed

    private void spiMaxPorDesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiMaxPorDesStateChanged
        blnHayCamTabGen = true;
    }//GEN-LAST:event_spiMaxPorDesStateChanged

    private void chkIvaVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaVenActionPerformed
	blnHayCamTabGen = true;
        //Se agrega validación para que ningún usuario pueda modificar Iva en Venta/Compras.Sólo admin podrá modificar, solicitado por Ing.Eddye Lino.
        if(objParSis.getCodigoUsuario()!=1){
            if(chkIvaVen.isSelected()){
                chkIvaVen.setSelected(false);
            }
            else {
                chkIvaVen.setSelected(true);
            }            
            mostrarMsgInf("<HTML>Solo usuario admin puede marcar/desmarcar: Iva en ventas/Iva en compras para el cliente</HTML>");
        }
    }//GEN-LAST:event_chkIvaVenActionPerformed

    private void chkIvaComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaComActionPerformed
	blnHayCamTabGen = true;
        //Se agrega validación para que ningún usuario pueda modificar Iva en Venta/Compras.Sólo admin podrá modificar, solicitado por Ing.Eddye Lino.
        if(objParSis.getCodigoUsuario()!=1){
            if(chkIvaCom.isSelected()){
                chkIvaCom.setSelected(false);
            }
            else {
                chkIvaCom.setSelected(true);
            }
            mostrarMsgInf("<HTML>Solo usuario admin puede marcar/desmarcar: Iva en ventas/Iva en compras para el cliente</HTML>");
        }        
    }//GEN-LAST:event_chkIvaComActionPerformed

    private void spiDiaGraDocVenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiDiaGraDocVenStateChanged
	blnHayCamTabGen = true;
    }//GEN-LAST:event_spiDiaGraDocVenStateChanged

    private void spiDiaGraSinSopStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiDiaGraSinSopStateChanged
	blnHayCamTabGen = true;
    }//GEN-LAST:event_spiDiaGraSinSopStateChanged

    private void spiMarUtiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiMarUtiStateChanged
	blnHayCamTabGen = true;
    }//GEN-LAST:event_spiMarUtiStateChanged

    private void spiNumMaxVenConStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiNumMaxVenConStateChanged
	blnHayCamTabGen = true;
    }//GEN-LAST:event_spiNumMaxVenConStateChanged

    private void txtCorEleFacEle1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorEleFacEle1FocusLost
        if (txtCorEleFacEle1.getText().length() > 0) 
        {
            optFacEleSi.setSelected(true); /* José Marín M - 12/Nov/2014 */
        }
    }//GEN-LAST:event_txtCorEleFacEle1FocusLost

    private void txtCorEleFacEle2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorEleFacEle2FocusLost
        if (txtCorEleFacEle2.getText().length() > 0) 
        {
            optFacEleSi.setSelected(true); /* José Marín M - 12/Nov/2014 */
        }
    }//GEN-LAST:event_txtCorEleFacEle2FocusLost

    private void txtCodParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodParActionPerformed
        configurarVenConPar(); 
        txtCodPar.transferFocus();
    }//GEN-LAST:event_txtCodParActionPerformed

    private void txtCodParFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodParFocusGained
        strCodPar = txtCodPar.getText();
        txtCodPar.selectAll(); 
    }//GEN-LAST:event_txtCodParFocusGained

    private void txtCodParFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodParFocusLost
        if (!txtCodPar.getText().equalsIgnoreCase(strCodPar))
        {
            if (txtCodPar.getText().equals("")) 
            {
                txtCodPar.setText("");
                txtPar.setText("");
            }
            else
            {
                mostrarParroquia("a.co_Par", txtCodPar.getText(), 0);
            }
        } 
        else 
        {
            txtCodPar.setText(strCodPar);
        }   
    }//GEN-LAST:event_txtCodParFocusLost

    private void txtParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtParActionPerformed
        configurarVenConPar();
        txtPar.transferFocus();
    }//GEN-LAST:event_txtParActionPerformed

    private void txtParFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtParFocusGained
        strNomPar = txtPar.getText();
        txtPar.selectAll();
    }//GEN-LAST:event_txtParFocusGained

    private void txtParFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtParFocusLost
        if (!txtPar.getText().equalsIgnoreCase(strNomPar)) 
        {
            if (txtPar.getText().equals("")) 
            {
                txtCodPar.setText("");
                txtPar.setText("");
            }
            else 
            {
                mostrarParroquia("a.tx_deslar", txtPar.getText(), 1);
            }
        }
        else
            txtPar.setText(strNomPar);
    }//GEN-LAST:event_txtParFocusLost

    private void butParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butParActionPerformed
        configurarVenConPar();
        vcoPar.setTitle("Listado de Parroquias");
        vcoPar.setCampoBusqueda(1);
        vcoPar.show();
        if (vcoPar.getSelectedButton()==vcoPar.INT_BUT_ACE) 
        {
            txtCodPar.setText(vcoPar.getValueAt(1));
            txtPar.setText(vcoPar.getValueAt(2));
            txtCodCiu.setText(vcoPar.getValueAt(3));
            txtCiu.setText(vcoPar.getValueAt(4));
            strCodPar = vcoPar.getValueAt(1);
            strNomPar = vcoPar.getValueAt(2);
            strCodCiu = vcoPar.getValueAt(3);
            strCiudad = vcoPar.getValueAt(4);
        }
    }//GEN-LAST:event_butParActionPerformed

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podróa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No . El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgYesNo(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podróa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    } 
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No . El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private void mostrarMsgOK(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.OK_OPTION);
    }


    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los
     * objTooBars de tipo texto para poder determinar si su contenido a cambiado
     * o no.
     */
    private boolean isRegPro() 
    {
        boolean blnRes = true;
        String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            blnRes = objTooBar.insertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            blnRes = objTooBar.modificar();
                        }
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam = false;
                blnRes = true;
                break;
            case 2: //CANCEL_OPTION
                blnRes = false;
                break;
        }

        return blnRes;
    }

    
    private boolean abrirConRem()
    {
        boolean blnRes=false;
        try
        {
            int intIndEmp=INTCODREGCEN;
            if(intIndEmp != 0){
                conRemGlo=DriverManager.getConnection(objParSis.getStringConexion(intIndEmp), objParSis.getUsuarioBaseDatos(intIndEmp), objParSis.getClaveBaseDatos(intIndEmp));
                conRemGlo.setAutoCommit(false);
            }
            blnRes=true;
        }
        catch (java.sql.SQLException e) 
        {
            mostrarMsgAdv("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            return false;
        }
        return blnRes;
    }
    
    //Validaciones Varias.
    public boolean isCamVal() 
    {
        String strAux;
        try 
        {
            if (txtCodTipPer.getText().equals(""))  {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("El campo <<Còdigo de Tipo de Persona >> es obligatorio.\nEscriba el tipo para los datos del Cliente  y vuelva a intentarlo.");
                txtTipPer.requestFocus();
                return false;
            }

            if (txtIde.getText().equals(""))   {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("El campo <<Nº de Identificación>> es obligatorio.\nEscriba el Nº de Identificación para el Cliente y vuelva a intentarlo.");
                txtIde.requestFocus();
                return false;
            }

            if (txtIde.getText().length() > 0 && cboTipIde.getSelectedIndex() == 0)   {
                if (txtIde.getText().length() != 13)     {
                    tabGen.setSelectedIndex(0);
                    mostrarMsgInf("El campo <<Nº de Identificación >> debe contener 13 digitos.\nEscriba el Nº de Identificación para el Cliente y vuelva a intentarlo.");
                    txtIde.selectAll();
                    txtIde.requestFocus();
                    return false;
                }
            }

            if (txtIde.getText().length() > 0)    {
                if (txtIde.getText().length() != 10 && cboTipIde.getSelectedIndex() == 1)      {
                    tabGen.setSelectedIndex(0);
                    mostrarMsgInf("El campo <<Nº de Identificación>> debe contener 10 digitos.\nEscriba el Nº de Identificación para el Cliente/Proveedor y vuelva a intentarlo.");
                    txtIde.selectAll();
                    txtIde.requestFocus();
                    return false;
                }
            }

            if ((!chkCli.isSelected()) && (!chkPro.isSelected())) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("Seleccione si es Cliente o Proveedor.\nSeleccione  uno es importante para los datos del Cliente/Proveedor y vuelva a intentarlo.");
                return false;
            }

            if (txtNomCli.getText().trim().equals("") || txtIde.getText().trim().length() < 0)  {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("El campo <<Nombre de Cliente>> es obligatorio.\nEscriba el Nombre  para el Cliente/Proveedor y vuelva a intentarlo.");
                txtNomCli.requestFocus();
                return false;
            }
            
            if (objTooBar.getEstado() == 'n') //Estado 'n' = Modo Insertar
            {  
                if (validarExisteCedRuc())  //Validacion para verificar si el Ruc/Cedula/Pasaporte existe en la base de datos
                { 
                    String strMen = "";
                    if (cboTipIde.getSelectedIndex() == 0)
                       strMen = "RUC ya existe";
                    else if (cboTipIde.getSelectedIndex() == 1)
                       strMen = "Número de cédula ya existe";
                    else if (cboTipIde.getSelectedIndex() == 2)
                       strMen = "Pasaporte ya existe";
                    else if (cboTipIde.getSelectedIndex() == 3)
                       strMen = "Consumidor Final ya existe";
                    else if (cboTipIde.getSelectedIndex() == 4)
                       strMen = "Identificación del Exterior ya existe";
                    
                    mostrarMsgInf(strMen);
                    txtIde.setText("");
                    txtIde.requestFocus();
                    return false;
                }
            }
            
            if (cboTipIde.getSelectedIndex() == 0 || cboTipIde.getSelectedIndex() == 1)
            {  
                ZafMae07_03 objMae07_03 = new ZafMae07_03();
                String strId = txtIde.getText(); //Indice 0 = RUC; 1 = Cedula
                try
                {
                   boolean blnValCed = objMae07_03.ValidacionId(strId);  //Validar si el Ruc/Cedula esta correcto
                }
                catch (Exception Evt)
                {  
                     mostrarMsgInf("El <<Numero de Identificacion>> es incorrecto.\nEscriba el Numero de Identificacion correcto del Cliente y vuelva a intentarlo.");
                     return false;
                }
            }
            
            String strCorEle = txtCorEleFacEle1.getText();
            if (strCorEle.length() > 0) 
            {
                strAux = validarCorEleFacEle(txtCorEleFacEle1.getText(), "Correo 1");
                if (!strAux.equals("true")) {
                    mostrarMsgInf("<HTML>" + strAux + "\nVerifique y vuelva a intentarlo.");
                    txtCorEleFacEle1.requestFocus();
                    return false;
                }
            }

            String strCorEleOtr = txtCorEleFacEle2.getText();
            if (strCorEleOtr.length() > 0)
            {
                strAux = validarCorEleFacEle(txtCorEleFacEle2.getText(), "Correo 2");
                if (!strAux.equals("true")) {
                    mostrarMsgInf("<HTML>" + strAux + "\nVerifique y vuelva a intentarlo.");
                    txtCorEleFacEle2.requestFocus();
                    return false;
                }
            }

            //Rose: Validaciones para Dinardap
            if( !( (objParSis.getCodigoUsuario()==1) || (objParSis.getCodigoUsuario()==122) ) )//Admin y Fruiz
            {
                if (!(objParSis.getCodigoMenu()== 873 || objParSis.getCodigoMenu()== 1053) )
                {
                    if (chkCli.isSelected()) 
                    {
                        if (cboTipIde.getSelectedIndex() == 0 || cboTipIde.getSelectedIndex() == 1)//Ruc y Cedula
                        {
                            if (txtDirCli.getText().trim().equals("") || txtDirCli.getText().trim().length() < 0) 
                            {
                                tabGen.setSelectedIndex(0);
                                mostrarMsgInf("El campo << Dirección >> es obligatorio.\nEscriba una dirección para el Cliente/Proveedor y vuelva a intentarlo.");
                                txtDirCli.requestFocus();
                                return false;
                            }

                            if (txtCodCiu.getText().equals("")) 
                            {
                                tabGen.setSelectedIndex(0);
                                mostrarMsgInf("El campo << Ciudad >> es obligatorio.\nSeleccione una es importante para los datos del Cliente/Proveedor y vuelva a intentarlo.");
                                txtCodCiu.requestFocus();
                                return false;
                            }

                            if (txtCodPar.getText().equals(""))
                            {
                                tabGen.setSelectedIndex(0);
                                mostrarMsgInf("El campo << Parroquia >> es obligatorio.\nSeleccione una es importante para los datos del Cliente/Proveedor y vuelva a intentarlo.");
                                txtCodPar.requestFocus();
                                return false;
                            }
                        }

                        if (txtCodTipPer.getText().equals("1") || txtCodTipPer.getText().equals("4")) //Persona Natural
                        {
                            if (cboSex.getSelectedIndex() == 0) 
                            {
                                tabGen.setSelectedIndex(0);
                                mostrarMsgInf("El campo << Sexo >> es obligatorio.\nSeleccione uno es importante para los datos del Cliente/Proveedor y vuelva a intentarlo.");
                                cboSex.requestFocus();
                                return false;
                            }

                            if (cboEstCiv.getSelectedIndex() == 0) 
                            {
                                tabGen.setSelectedIndex(0);
                                mostrarMsgInf("El campo << Estado Civil >> es obligatorio.\nSeleccione uno es importante para los datos del Cliente/Proveedor y vuelva a intentarlo.");
                                cboEstCiv.requestFocus();
                                return false;
                            }

                            if (cboOriIng.getSelectedIndex() == 0) 
                            {
                                tabGen.setSelectedIndex(0);
                                mostrarMsgInf("El campo << Origen de Ingresos >> es obligatorio.\nSeleccione uno es importante para los datos del Cliente/Proveedor y vuelva a intentarlo.");
                                cboOriIng.requestFocus();
                                return false;
                            }
                        }
                    }
                }
            }
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        return true;
    } 
    
    private boolean validarExistenDocAbi() 
    {
        boolean blnRes = false;
        try
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a3.co_cli, a3.tx_ide, a3.tx_nom, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal ";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc ";
                strSQL+=" AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a3.st_cli='S' AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')";
                strSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                strSQL+=" GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom HAVING SUM(a2.mo_pag+a2.nd_abo)<>0 ";
                strSQL+=" ORDER BY a3.tx_nom";
                //System.out.println("validarExistenDocAbi: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (!rst.next()) {
                    blnRes = true;
                }
            } 
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private String validarCorEleFacEle(String strCorEleFacEle, String strDesCam) 
    {
        String strRes = "true";
        char[] arrCad;
        int i, j;
        boolean blnCarArrobaFound, blnConRevCarArr;
        try 
        {
            if (strCorEleFacEle.lastIndexOf("ñ") != -1 || strCorEleFacEle.lastIndexOf("Ñ") != -1) 
            {
                strRes = "En la pestaña <FONT COLOR=\"blue\">Facturación Electrónica</FONT> el campo <FONT COLOR=\"blue\">" + strDesCam;
                strRes += "</FONT> no debe contener la letra ñ.";
                return strRes;
            }

            if (strCorEleFacEle.lastIndexOf("á") != -1 || strCorEleFacEle.lastIndexOf("Á") != -1
                    || strCorEleFacEle.lastIndexOf("é") != -1 || strCorEleFacEle.lastIndexOf("É") != -1
                    || strCorEleFacEle.lastIndexOf("í") != -1 || strCorEleFacEle.lastIndexOf("Í") != -1
                    || strCorEleFacEle.lastIndexOf("ó") != -1 || strCorEleFacEle.lastIndexOf("Ó") != -1
                    || strCorEleFacEle.lastIndexOf("ú") != -1 || strCorEleFacEle.lastIndexOf("Ú") != -1) 
            {
                strRes = "En la pestaña <FONT COLOR=\"blue\">Facturación Electrónica</FONT> el campo <FONT COLOR=\"blue\">" + strDesCam;
                strRes += "</FONT> no debe contener ninguna letra tildada.";
                return strRes;
            }

            if (strCorEleFacEle.lastIndexOf(".") == -1) 
            {
                strRes = "En la pestaña <FONT COLOR=\"blue\">Facturación Electrónica</FONT> el campo <FONT COLOR=\"blue\">" + strDesCam;
                strRes += "</FONT> debe tener el caracter punto.";
                return strRes;
            }

            arrCad = strCorEleFacEle.toCharArray();
            j = 0;
            blnCarArrobaFound = false;
            blnConRevCarArr = true; // blnConRevCarArr = Significa "Continuar Revision caracter Arroba"

            for (i = 0; i < arrCad.length; i++)
            {   
                if (arrCad[i] == ' ') 
                {
                    strRes = "En la pestaña <FONT COLOR=\"blue\">Facturación Electrónica</FONT> el campo <FONT COLOR=\"blue\">" + strDesCam;
                    strRes += "</FONT> no debe tener espacio en blanco.";
                    return strRes;
                }

                if (arrCad[i] == '@') {
                    j++;
                    blnCarArrobaFound = true;
                }

                if (j > 1) {
                    strRes = "En la pestaña <FONT COLOR=\"blue\">Facturación Electrónica</FONT> el campo <FONT COLOR=\"blue\">" + strDesCam;
                    strRes += "</FONT> debe tener un solo caracter arroba.";
                    return strRes;
                }
            } 

            if (j == 0) {
                strRes = "En la pestaña <FONT COLOR=\"blue\">Facturación Electrónica</FONT> el campo <FONT COLOR=\"blue\">" + strDesCam;
                strRes += "</FONT> debe tener el caracter arroba.";
                return strRes;
            }
        } 
        catch (Exception e) {
            strRes = "Error en la funcion validarCorEleCliFacEle. " + e.toString();
        }

        return strRes;
    }

    private boolean validarExisteCedRuc() 
    {
        boolean blnRes = false;
        Connection conLoc=null;
        ResultSet rstLoc=null;
        Statement stmLoc=null;
        try 
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
                strSQL=" SELECT * FROM tbm_cli WHERE tx_ide = " + objUti.codificar(txtIde.getText()) + " AND co_emp = " + objParSis.getCodigoEmpresa();
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                   blnRes = true;
                }
            }
        }
        catch (SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
        catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
        finally
        {
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            try{conLoc.close();conLoc=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }   
            


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanDatPro;
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butGrp;
    private javax.swing.JButton butPar;
    private javax.swing.JButton butTipPer;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butZon;
    private javax.swing.JComboBox cboDiaMesMaxEmiFac;
    private javax.swing.JComboBox cboDiaSemEmiFac;
    private javax.swing.JComboBox cboEst;
    private javax.swing.JComboBox cboEstCiv;
    private javax.swing.JComboBox cboOriIng;
    private javax.swing.JComboBox cboSex;
    private javax.swing.JComboBox cboTipIde;
    private javax.swing.JCheckBox chkCieCre;
    private javax.swing.JCheckBox chkCli;
    private javax.swing.JCheckBox chkIngNomCot;
    private javax.swing.JCheckBox chkIvaCom;
    private javax.swing.JCheckBox chkIvaVen;
    private javax.swing.JCheckBox chkPro;
    private javax.swing.ButtonGroup grpCorFacEle;
    private javax.swing.JLabel lblActPro;
    private javax.swing.JLabel lblCasCli;
    private javax.swing.JLabel lblCedPro;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblCliTieCorEleFacEle;
    private javax.swing.JLabel lblCodCli;
    private javax.swing.JLabel lblConPro;
    private javax.swing.JLabel lblCorEleFacEle1;
    private javax.swing.JLabel lblCorEleFacEle2;
    private javax.swing.JLabel lblDiaGraDocSinSop;
    private javax.swing.JLabel lblDiaGraDocVen;
    private javax.swing.JLabel lblDiaMesMaxEmiFac;
    private javax.swing.JLabel lblDiaSemEmiFac;
    private javax.swing.JLabel lblDirCli;
    private javax.swing.JLabel lblDirRef;
    private javax.swing.JLabel lblDomPro;
    private javax.swing.JLabel lblEmaCli;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblEstCiv;
    private javax.swing.JLabel lblEstCli;
    private javax.swing.JLabel lblFaxCli;
    private javax.swing.JLabel lblFecIng;
    private javax.swing.JLabel lblFecUltMod;
    private javax.swing.JLabel lblForPagCre;
    private javax.swing.JLabel lblGrp;
    private javax.swing.JLabel lblIde;
    private javax.swing.JLabel lblMaruti;
    private javax.swing.JLabel lblMaxPorDes;
    private javax.swing.JLabel lblMonCre;
    private javax.swing.JLabel lblNacPro;
    private javax.swing.JLabel lblNomCli;
    private javax.swing.JLabel lblNomPro;
    private javax.swing.JLabel lblNumMaxVenCon;
    private javax.swing.JLabel lblObsPro;
    private javax.swing.JLabel lblOriIng;
    private javax.swing.JLabel lblPar;
    private javax.swing.JLabel lblRepLeg;
    private javax.swing.JLabel lblSex;
    private javax.swing.JLabel lblTelCli;
    private javax.swing.JLabel lblTelCli1;
    private javax.swing.JLabel lblTelCli2;
    private javax.swing.JLabel lblTelCli3;
    private javax.swing.JLabel lblTelPro;
    private javax.swing.JLabel lblTipIde;
    private javax.swing.JLabel lblTipPer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUsrIng;
    private javax.swing.JLabel lblUsrUltMod;
    private javax.swing.JLabel lblVen;
    private javax.swing.JLabel lblWebCli;
    private javax.swing.JLabel lblZonCiu;
    private javax.swing.JRadioButton optFacEleNo;
    private javax.swing.JRadioButton optFacElePen;
    private javax.swing.JRadioButton optFacEleSi;
    private javax.swing.JPanel panAcc;
    private javax.swing.JPanel panAud;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBen;
    private javax.swing.JPanel panCliLoc;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panCre;
    private javax.swing.JPanel panDatCabVen;
    private javax.swing.JPanel panDatDetVen;
    private javax.swing.JPanel panDatDir;
    private javax.swing.JPanel panDatObs;
    private javax.swing.JPanel panDetVen;
    private javax.swing.JPanel panDir;
    private javax.swing.JPanel panFacEle;
    private javax.swing.JPanel panFacEleCorEle;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panImp;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObsObs;
    private javax.swing.JPanel panPro;
    private javax.swing.JPanel panVen;
    private javax.swing.JSpinner spiDiaGraDocVen;
    private javax.swing.JSpinner spiDiaGraSinSop;
    private javax.swing.JSpinner spiMarUti;
    private javax.swing.JSpinner spiMaxPorDes;
    private javax.swing.JSpinner spiNumMaxVenCon;
    private javax.swing.JScrollPane spnAcc;
    private javax.swing.JScrollPane spnDatBen;
    private javax.swing.JScrollPane spnDatCliLoc;
    private javax.swing.JScrollPane spnDatCon;
    private javax.swing.JScrollPane spnDatObs;
    private javax.swing.JScrollPane spnDir;
    private javax.swing.JScrollPane spnObsPro;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblAcc;
    private javax.swing.JTable tblBenChq;
    private javax.swing.JTable tblCliLoc;
    private javax.swing.JTable tblCon;
    private javax.swing.JTable tblDir;
    private javax.swing.JTable tblObs;
    private javax.swing.JTextArea txaObsPro;
    private javax.swing.JTextField txtActPro;
    private javax.swing.JTextField txtCasCli;
    private javax.swing.JTextField txtCedPro;
    private javax.swing.JTextField txtCiu;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodCre;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtCodPar;
    private javax.swing.JLabel txtCodUsrIng;
    private javax.swing.JLabel txtCodUsrMod;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtCodZon;
    private javax.swing.JTextField txtCorEleFacEle1;
    private javax.swing.JTextField txtCorEleFacEle2;
    private javax.swing.JTextField txtDesCre;
    private javax.swing.JTextField txtDirCli;
    private javax.swing.JTextField txtDomPro;
    private javax.swing.JTextField txtEmaCli;
    private javax.swing.JTextField txtFaxCli;
    private javax.swing.JLabel txtFecIng;
    private javax.swing.JLabel txtFecUltMod;
    private javax.swing.JTextField txtGrp;
    private javax.swing.JTextField txtIde;
    private javax.swing.JTextField txtMonCre;
    private javax.swing.JTextField txtNacPro;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomPro;
    private javax.swing.JLabel txtNomUsrIng;
    private javax.swing.JLabel txtNomUsrMod;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtPar;
    private javax.swing.JTextField txtRefDirCli;
    private javax.swing.JTextField txtRepleg;
    private javax.swing.JTextField txtTelCli;
    private javax.swing.JTextField txtTelCli1;
    private javax.swing.JTextField txtTelCli2;
    private javax.swing.JTextField txtTelCli3;
    private javax.swing.JTextField txtTelPro;
    private javax.swing.JTextField txtTipPer;
    private javax.swing.JTextField txtWebCli;
    private javax.swing.JTextField txtZon;
    // End of variables declaration//GEN-END:variables

    
    public class MiToolBar extends ZafToolBar {

        public MiToolBar(javax.swing.JInternalFrame ifrtem) {
            super(ifrtem, objParSis);
        }

        @Override
        public void clickInicio() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        } 
        
        @Override
        public void clickAnterior() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ( blnHayCam ){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }

        @Override
         public void clickSiguiente() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }

        @Override
        public void clickFin() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam ) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }     

        @Override
        public void clickConsultar()  {
            limpiarFrm();
        }

        @Override
        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
            
                txtCodCli.setEditable(false);
                butCli.setEnabled(false);
                txtIde.requestFocus();
                limpiarFrm();
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public void clickModificar()
        {
            txtCodCli.setEditable(false);
            java.awt.Color colBack = txtTelCli.getBackground();
            bloquea(txtTelCli, colBack);

            //Se encarga de bloquear o desbloquear los campos correspondientes si el estado del cliente A= Bloquea, caso contrario desbloquea.
            //Esto es solo para los módulos de ventas e inventario.
            if (intCodMod == 1 || intCodMod == 2 || intCodMod == 3 || intCodMod == 4)
            {
                if (strEstRegCli.equals("A"))
                   bloquearModVenPro(false);
            }
            bloquea(txtMonCre, colBack);
            bloquea(txtCodCre, colBack);
            bloquea(txtDesCre, colBack);

            switch (intCodMnu) 
            {
                case 913:  chkIvaCom.setEnabled(false);   break;  // MODULO CXC
                case 1053: chkIvaVen.setEnabled(false);   break;  // MODULO CXP
            }

            blnHayCamTabGen = false;
            blnHayCamTblCon = false;
            blnHayCamTblDir = false;
            blnHayCamTblObs = false;
            blnHayCamTblBen = false;
            blnHayCamTblAcc = false;
        }
        
        @Override
        public void clickAnular() {
        }
        
        @Override
        public void clickEliminar() {
        }
        
        @Override
        public void clickImprimir() {
        }    

        @Override
        public void clickVisPreliminar() {
        }
        
        @Override
        public void clickAceptar() {
        }
        
        @Override
        public boolean aceptar() {
            return true;
        }
        
        @Override
        public void clickCancelar() {
        }        
        
        @Override
        public boolean consultar()  {
            return consultarReg(FilSql());
        }      
        
        @Override
        public boolean insertar() {
            if (!insertarReg())
                return false;
            blnHayCam=false;
            return true;
        }

        @Override
        public boolean modificar() 
        {
            boolean blnRes = true;
            switch (intCodMnu) 
            {
                case 828:   if (!actualizarRegVen())  blnRes = false;   break;  // MODULO VENTAS
                case 873:   if (!actualizarRegInv())  blnRes = false;   break;  // MODULO INVENTARIO
                case 913:   if (!actualizarRegCxC())  blnRes = false;   break;  // MODULO CXC
                case 1053:  if (!actualizarRegCxP())  blnRes = false;   break;  // MODULO CXP
                default:    if (!actualizarReg())     blnRes = false;   break;  // MODULO MAESTRO
            }
            blnHayCam = false;
            return blnRes;
        }
        
        @Override
        public boolean anular() 
        {
            String strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if (!validarExistenDocAbi()) {
                mostrarMsgInf("El Cliente tiene documentos abiertos.\nNo es posible anular un cliente con documentos abiertos.");
                return false;
            }
            if (!anularReg()) {
                return false;
            }
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam = false;
            return true;
        }

        @Override
        public boolean eliminar() {
            try{
                String strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceCon<arlDatCon.size()-1){
                    intIndiceCon++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                  
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
            return true;
        }        


        @Override
        public boolean imprimir() {
            return true;
        }

        @Override
        public boolean vistaPreliminar() {
            return true;
        }
        
        @Override
        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }        

        @Override
        public boolean beforeConsultar() {
            return true;
        }
        
        @Override
        public boolean beforeInsertar() {
            if (!isCamVal()) 
                return false;             
            return true;
        }        

        @Override
        public boolean beforeModificar() {
            String strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))  {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }

            if (!isCamVal()) 
                return false;            
            return true;
        }

        @Override
        public boolean beforeEliminar() {
            return true;
        }

        @Override
        public boolean beforeAnular() {
            return true;
        }

        @Override
        public boolean beforeImprimir() {
            return true;
        }

        @Override
        public boolean beforeVistaPreliminar() {
            return true;
        }

        @Override
        public boolean beforeAceptar() {
            return true;
        }

        @Override
        public boolean beforeCancelar() {
            return true;
        }
        
        @Override
        public boolean afterConsultar() {
            return true;
        }
        
        @Override
        public boolean afterInsertar() {
            this.setEstado('w');
            return true;
        }

        @Override
        public boolean afterModificar() {
            blnHayCamTabGen=false;
            blnHayCamTblCon=false;
            blnHayCamTblDir=false;
            blnHayCamTblObs=false;
            blnHayCamTblBen=false;
            blnHayCamTblAcc=false;
            return true;
        }        

        @Override
        public boolean afterAnular() {
            return true;
        }
        
        @Override
        public boolean afterEliminar() {
            return true;
        }
        
        @Override
        public boolean afterImprimir() {
            return true;
        }
        
        @Override
        public boolean afterVistaPreliminar() {
            return true;
        }        
        
        @Override
        public boolean afterAceptar() {
            return true;
        }
        
        @Override
        public boolean afterCancelar() {
            return true;
        }       

        
      
    }

//**************************************************************************************   
    
    private String FilSql() 
    {
        String sqlFil = "";
        
        //Agregando filtro por codigo de empresa
        strAux = txtCodCli.getText();
        if (!txtCodCli.getText().equals("")) {
            sqlFil +=  "AND  a.co_cli = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";
        }
        //Agregando filtro por nombre
        strAux = txtNomCli.getText();
        if (!txtNomCli.getText().equals("")) {
            sqlFil +=  " AND LOWER( a.tx_nom) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
        }
        //Agregando filtro por Direccion
        strAux = txtDirCli.getText();
        if (!txtDirCli.getText().equals("")) {
            sqlFil += " AND LOWER (a.tx_dir) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
        }
        //Agregando filtro por Grupo
        strAux = txtCodGrp.getText();
        if (!txtCodGrp.getText().equals("")) {
            sqlFil += " AND a.co_grp = " + strAux + " ";
        }
        //Agregando filtro por Identificacion
        strAux = txtIde.getText();
        if (!txtIde.getText().equals("")) {
            sqlFil +=  "  AND LOWER (a.tx_ide) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
        }
        //Agregando filtro por ciudad del cliente                       //***** pedido de usuario 19/06/2015 para consulta de los clientes en Esmeraldas
        strAux = txtCodCiu.getText();
        if (!txtCodCiu.getText().equals("")) {
            sqlFil += "AND  a.co_ciu = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";
        }
        //Agregando filtro por parroquia del cliente 
        strAux = txtCodCiu.getText();
        if (!txtCodPar.getText().equals("")) {
            sqlFil += "AND  a.co_par = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";
        }
        //Agregando filtro por tipo de persona.                    
        strAux = txtCodTipPer.getText();
        if (!txtCodTipPer.getText().equals("")) {
            sqlFil += "AND  a.co_tipper = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";
        }
        //Agregando filtro por vendedor.                    
        strAux = txtCodVen.getText();
        if (!txtCodVen.getText().equals("")) {
            sqlFil += "AND  a.co_ven = " + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "";
        }
        return sqlFil;
    }    
    
    private boolean consultarReg(String strFiltro) 
    {
        try
        {
            if (!abrirConRem()) 
                return false;

            if (INTCODREGCEN == 0)   {
                conRemGlo = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            
            if (conRemGlo != null) 
            {
                stm = conRemGlo.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                            
                //Armar sentencia SQL                
                strSQL =" ";
                strSQL+=" SELECT * FROM tbm_cli as a ";
                strSQL+=" WHERE a.co_emp = " + objParSis.getCodigoEmpresa() + " " + strFilCli + "  AND a.st_reg NOT IN ('E') ";
                strSQL+=" "+strFiltro+"";
                strSQL+=" ORDER BY a.co_cli";
                //System.out.println("Por empresa: "+strSQL);
                if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL ="";
                    strSQL+=" SELECT * FROM tbr_cliloc as b ";
                    strSQL+=" INNER JOIN tbm_cli as a ON (a.co_emp=b.co_emp and a.co_cli=b.co_cli) ";
                    strSQL+=" WHERE b.co_emp =" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND b.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" "+strFilCli + "";
                    strSQL+=" AND a.st_reg NOT IN ('E') ";
                    strSQL+=" "+strFiltro + "";
                    strSQL+=" ORDER BY a.co_cli";
                    //System.out.println("Por Local: "+strSQL);
                }
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_CLI,   rst.getInt("co_cli"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                conRemGlo.close();
                conRemGlo=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
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
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        return true;
    }

    /**
     * Esta función permite cargar el registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() 
    {
        boolean blnRes = true;
        int intConEst = 0; // variable de control de estado de consulta
        try
        {
            if (!abrirConRem())  
                return false;

            if (INTCODREGCEN == 0)  {
                conRemGlo = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }

            if (conRemGlo != null) 
            {
                switch (intCodMnu) 
                {
                    case 828: // MODULO VENTAS
                        if (cargarRegTabMnu(conRemGlo)) {
                            intConEst = 1;
                        }
                        break;  
                    case 873: // MODULO INVENTARIO
                        if (cargarRegTabMnu(conRemGlo)) {
                            intConEst = 1;
                        }
                        break;  
                    case 913: // MODULO CXC
                        if (cargarRegTabMnu(conRemGlo)) {
                            intConEst = 1;
                        }
                        break;  
                    case 1053: // MODULO CXP
                        if (cargarRegTabMnu(conRemGlo)) {
                            intConEst = 1;
                        }
                        break;  
                    default: // MODULO MAESTRO
                        if (cargarRegTabMae(conRemGlo)) {
                            intConEst = 1;
                        }
                        break;  
                }

                //Se encarga de Bloquear o desbloquear los campos correspondientes si el estado del cliente A= Bloquea, caso contrario desbloquea.
                //Esto es solo para los Módulos de Ventas e Inventario.
                if (intCodMod == 1 || intCodMod == 2)   {
                    if (strEstRegCli.equals("A")) {    bloquearModVenPro(false);       } 
                    else   {    bloquearModVenPro(true);           }
                }

                if (intConEst == 0)  {
                    mostrarMsgInf("Error al cargar registro");
                }

                blnHayCam = false;
                conRemGlo.close();
                conRemGlo = null;
            }
        } 
        catch (java.sql.SQLException e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e); } 
        catch (Exception e) {     blnRes = false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
        
    private void bloquearModVenPro(boolean blnEst) 
    {
        /* VENTAS E INVENTARIO */
        java.awt.Color colBack = txtTelCli.getBackground();
        //2991=VentasGeneral ; 2999=clientesGeneral ;  2995=Inventario General ; 3007=Proveedores general
        if (objPerUsr.isOpcionEnabled(2991) || objPerUsr.isOpcionEnabled(2995) || objPerUsr.isOpcionEnabled(2999) || objPerUsr.isOpcionEnabled(3007)) 
        {
        }
        else 
        {
            bloquea(txtNomCli, colBack, blnEst);
            bloquea(txtDirCli, colBack, blnEst);
            bloquea(txtFaxCli, colBack, blnEst);
            bloquea(txtIde, colBack, blnEst);
            bloquea(txtCasCli, colBack, blnEst);
            bloquea(txtEmaCli, colBack, blnEst);
            bloquea(txtCiu, colBack, blnEst);
            bloquea(txtPar, colBack, blnEst);
            bloquea(txtWebCli, colBack, blnEst);
            bloquea(txtRepleg, colBack, blnEst);
            bloquea(txtCodGrp, colBack, blnEst);
            bloquea(txtCodCiu, colBack, blnEst);
            bloquea(txtCodPar, colBack, blnEst);
            bloquea(txtCodZon, colBack, blnEst);
            bloquea(txtTipPer, colBack, blnEst);
            bloquea(txtRefDirCli, colBack, blnEst);
            bloquea(txtCodVen, colBack, blnEst);
            bloquea(txtNomVen, colBack, blnEst);
            bloquea(txtTelCli1, colBack, blnEst);
            bloquea(txtTelCli2, colBack, blnEst);
            bloquea(txtTelCli3, colBack, blnEst);
            bloquea(txtCodGrp, colBack, blnEst);
            bloquea(txtGrp, colBack, blnEst);
            bloquea(txtZon, colBack, blnEst);
            butTipPer.setEnabled(blnEst);
            butCiu.setEnabled(blnEst);
            butPar.setEnabled(blnEst);
            butZon.setEnabled(blnEst);
            butVen.setEnabled(blnEst);
            butCli.setEnabled(blnEst);
            butGrp.setEnabled(blnEst);
            cboTipIde.setEnabled(blnEst);
            cboEst.setEnabled(blnEst);
            cboSex.setEnabled(blnEst);
            cboEstCiv.setEnabled(blnEst);
            cboOriIng.setEnabled(blnEst);
            chkPro.setEnabled(blnEst);
            chkCli.setEnabled(blnEst);
        }
    }
    
//**************************************************************************************       
    private boolean cargarRegTabMnu(Connection conn)
    {
        boolean blnRes = false;
        try
        {
            if (conn != null) 
            {
                if (cargarTabGen(conn)) {
                    if (cargarTabPro(conn)) {
                        if (cargarTabCre(conn)) {
                            if (cargarTabDirPar(conn)) {
                                if (cargarTabConPar(conn)) {
                                    if (cargarTabBen(conn)) {
                                        if (cargarTabObsPar(conn)) {
                                            if (cargarTabCliLoc(conn)) {
                                                if (cargarTabVen(conn)) {
                                                    if (cargarTabImp(conn)) {
                                                        if (cargarTabFacEle(conn)) {
                                                            if (cargarTabAud(conn)) 
                                                            {
                                                                blnHayCamTabGen = false;
                                                                blnHayCamTblCon = false;
                                                                blnHayCamTblDir = false;
                                                                blnHayCamTblObs = false;
                                                                blnHayCamTblBen = false;
                                                                blnHayCamTblAcc = false;
                                                                blnRes = true;
                                                            }
                                                            else  blnRes = false;   
                                                        } else  blnRes = false;  
                                                    } else  blnRes = false;  
                                                } else  blnRes = false; 
                                            } else  blnRes = false; 
                                        } else  blnRes = false; 
                                    } else  blnRes = false;
                                } else  blnRes = false; 
                            } else  blnRes = false; 
                        } else  blnRes = false; 
                    } else  blnRes = false; 
                } else  blnRes = false; 
            }
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarRegTabMae(Connection conn) 
    {
        boolean blnRes = false;
        try 
        {
            if (conn != null) 
            {
                if (cargarTabGen(conn)) {
                    if (cargarTabPro(conn)) {
                        if (cargarTabCre(conn)) {
                            if (cargarTabDir(conn)) {
                                if (cargarTabCon(conn)) {
                                    if (cargarTabBen(conn)) {
                                        if (cargarTabObs(conn)) {
                                            if (cargarTabCliLoc(conn)) {
                                                if (cargarTabVen(conn)) {
                                                    if (cargarTabImp(conn)) {
                                                        if (cargarTabFacEle(conn)) {
                                                            if (cargarTabAud(conn)) 
                                                            {
                                                                blnRes = true;
                                                            } else blnRes = false;
                                                        } else  blnRes = false;
                                                    } else blnRes = false;
                                                } else  blnRes = false;
                                            } else  blnRes = false;
                                        } else   blnRes = false;
                                    } else   blnRes = false;
                                } else    blnRes = false;
                            } else  blnRes = false;
                        } else blnRes = false;
                    } else  blnRes = false;
                } else blnRes = false;
            }
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
  }

    private boolean cargarTabGen(Connection conExt)
    {
        boolean blnRes = true;
        int intPosRel;
        try 
        {
            if (conExt != null)
            {
                strSQL = " SELECT a.tx_cas, a.st_reg, a.tx_refubi, a.st_regrep, a.co_cli,a.st_cli,a.st_prv,a.tx_tipide,a.tx_ide,a.tx_nom,a.tx_dir,a.tx_tel,a.tx_fax,a.tx_dirweb "
                       + "      , a.tx_corele, a.tx_tipper, a.co_ciu,a.co_par,a.co_zon,a.co_ven,a.co_tipper,a.tx_obs1, b.tx_deslar,  b.tx_descor as descotipper , c.tx_deslar as ciudad"
                       + "      , d.tx_nom as vendedor, e.tx_deslar as zona,p.tx_deslar as parroquia, a.tx_tel1, a.tx_tel2, a.tx_tel3, a.tx_repleg, a.st_sex, a.st_estciv "
                       + "      , a.st_oriing, g.co_grp, g.tx_nom as txnomgrp, cliloc.co_ven as codclivendedor "
                       + " FROM tbm_cli as a "
                       + " LEFT JOIN tbm_tipper as b ON (b.co_emp=a.co_emp AND b.co_tipper=a.co_tipper) "
                       + " LEFT JOIN tbm_ciu as c ON (c.co_ciu = a.co_ciu)"
                       + " LEFT JOIN tbm_par as p ON (p.co_par = a.co_par)"
                       + " LEFT JOIN tbm_grpcli as g ON (g.co_emp=a.co_emp and g.co_grp=a.co_grp )"
                       + " LEFT JOIN tbm_var as e ON (e.co_grp=6 and e.st_reg='A' and e.co_reg=a.co_zon)"
                       + " LEFT JOIN tbr_cliloc as cliloc on (a.co_cli=cliloc.co_cli and a.co_emp=cliloc.co_emp and cliloc.co_loc="+objParSis.getCodigoLocal()+")"
                       + " LEFT JOIN tbm_usr as d ON (d.co_usr=cliloc.co_ven ) "                       
                       + " WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + " AND a.co_cli=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_CLI) + " AND a.st_reg NOT IN ('E')";
                //System.out.println("cargarTabGen: " + strSQL);
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtCodCli.setText(((rst.getString("co_cli") == null) ? "" : rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nom") == null) ? "" : rst.getString("tx_nom")));
                    txtDirCli.setText(((rst.getString("tx_dir") == null) ? "" : rst.getString("tx_dir")));
                    txtTelCli.setText(((rst.getString("tx_tel") == null) ? "" : rst.getString("tx_tel")));
                    txtFaxCli.setText(((rst.getString("tx_fax") == null) ? "" : rst.getString("tx_fax")));
                    txtCasCli.setText(((rst.getString("tx_cas") == null) ? "" : rst.getString("tx_cas")));
                    txtWebCli.setText(((rst.getString("tx_dirweb") == null) ? "" : rst.getString("tx_dirweb")));
                    txtEmaCli.setText(((rst.getString("tx_corele") == null) ? "" : rst.getString("tx_corele")));
                    txtRefDirCli.setText(((rst.getString("tx_refubi") == null) ? "" : rst.getString("tx_refubi")));
                    txtTelCli1.setText(((rst.getString("tx_tel1") == null) ? "" : rst.getString("tx_tel1")));
                    txtTelCli2.setText(((rst.getString("tx_tel2") == null) ? "" : rst.getString("tx_tel2")));
                    txtTelCli3.setText(((rst.getString("tx_tel3") == null) ? "" : rst.getString("tx_tel3")));
                    
                    txtCodTipPer.setText(((rst.getString("co_tipper") == null) ? "" : rst.getString("co_tipper")));
                    txtTipPer.setText(((rst.getString("tx_deslar") == null) ? "" : rst.getString("tx_deslar")));
                    strCodTipPer = txtCodTipPer.getText();
                    strNomTipPer = txtTipPer.getText();
                    
                    txtIde.setText(((rst.getString("tx_ide") == null) ? "" : rst.getString("tx_ide")));
                    txtIdeAux.setText(((rst.getString("tx_ide") == null) ? "" : rst.getString("tx_ide")));

                    txtCodCiu.setText(((rst.getString("co_ciu") == null) ? "" : rst.getString("co_ciu")));
                    txtCiu.setText(((rst.getString("ciudad") == null) ? "" : rst.getString("ciudad")));
                    strCodCiu = txtCodCiu.getText();
                    strCiudad = txtCiu.getText();

                    txtCodPar.setText(((rst.getString("co_par") == null) ? "" : rst.getString("co_par")));
                    txtPar.setText(((rst.getString("parroquia") == null) ? "" : rst.getString("parroquia")));
                    strCodPar = txtCodPar.getText();
                    strNomPar = txtPar.getText();
                    
                    txtCodVen.setText(((rst.getString("codclivendedor") == null) ? "" : rst.getString("codclivendedor")));
                    txtNomVen.setText(((rst.getString("vendedor") == null) ? "" : rst.getString("vendedor")));
                    strCodVen = txtCodVen.getText();
                    strNomVen = txtNomVen.getText();

                    txtCodZon.setText(((rst.getString("co_zon") == null) ? "" : rst.getString("co_zon")));
                    txtZon.setText(((rst.getString("zona") == null) ? "" : rst.getString("zona")));
                    strCodZon = txtCodZon.getText();
                    strNomZon = txtZon.getText();

                    txtRepleg.setText(rst.getString("tx_repleg"));
                    txtCodGrp.setText(rst.getString("co_grp"));
                    txtGrp.setText(rst.getString("txnomgrp"));
                    
                    //Indica si es cliente
                    String strIsCli = rst.getString("st_cli");
                    if (strIsCli.equals("S")) {  chkCli.setSelected(true);  } else {  chkCli.setSelected(false);   } 
                    
                    //Indica si es proveedor
                    String strIsPrv = rst.getString("st_prv");
                    if (strIsPrv.equals("S")) {  chkPro.setSelected(true);  } else {  chkPro.setSelected(false);   }                     

                    //Combo: Tipo de identificación
                    String strIde = rst.getString("tx_tipide");
                    if (strIde.equals("R"))      {   cboTipIde.setSelectedIndex(0);  } 
                    else if (strIde.equals("C")) {   cboTipIde.setSelectedIndex(1);  } 
                    else if (strIde.equals("P")) {   cboTipIde.setSelectedIndex(2);  } 
                    else if (strIde.equals("F")) {   cboTipIde.setSelectedIndex(3);  }
                    else if (strIde.equals("E")) {   cboTipIde.setSelectedIndex(4);  }
                    
                    //Combo: Origen de ingresos
                    String strOrIng = rst.getString("st_oriIng");
                    if (strOrIng == null) {
                        cboOriIng.setSelectedIndex(0);
                    } else if (strOrIng.equals("N")) {
                        cboOriIng.setSelectedIndex(0);
                    } else if (strOrIng.equals("B")) {
                        cboOriIng.setSelectedIndex(1);
                    } else if (strOrIng.equals("V")) {
                        cboOriIng.setSelectedIndex(2);
                    } else if (strOrIng.equals("I")) {
                        cboOriIng.setSelectedIndex(3);
                    } else if (strOrIng.equals("A")) {
                        cboOriIng.setSelectedIndex(4);
                    } else if (strOrIng.equals("R")) {
                        cboOriIng.setSelectedIndex(5);
                    } else if (strOrIng.equals("H")) {
                        cboOriIng.setSelectedIndex(6);
                    } else {
                        cboOriIng.setSelectedIndex(7);
                    }

                    //Combo: Sexo
                    strSex = rst.getString("st_sex");
                    if (strSex != null && strSex.equals("M")) {   cboSex.setSelectedIndex(1); } 
                    else if (strSex != null && strSex.equals("F")) {  cboSex.setSelectedIndex(2); } 
                    else {   cboSex.setSelectedIndex(0);}

                    //Combo: Estado Civil
                    strEstCiv = rst.getString("st_estciv");  // datos combo de estado civil
                    strAux = "";
                    if (strEstCiv != null && strEstCiv.equals("N")) {
                        strAux = "Null";
                        cboEstCiv.setSelectedIndex(0);
                    } else if (strEstCiv != null && strEstCiv.equals("S")) {
                        strAux = "Soltero";
                        cboEstCiv.setSelectedIndex(1);
                    } else if (strEstCiv != null && strEstCiv.equals("C")) {
                        strAux = "Casado";
                        cboEstCiv.setSelectedIndex(2);
                    } else if (strEstCiv != null && strEstCiv.equals("D")) {
                        strAux = "Divorciado";
                        cboEstCiv.setSelectedIndex(3);
                    } else if (strEstCiv != null && strEstCiv.equals("V")) {
                        strAux = "Viudo";
                        cboEstCiv.setSelectedIndex(4);
                    } else if (strEstCiv != null && strEstCiv.equals("U")) {
                        strAux = "Union Libre";
                        cboEstCiv.setSelectedIndex(5);
                    } else {
                        cboEstCiv.setSelectedIndex(0);
                    }                    

                    //Combo: Estado de registro del cliente
                    strEstRegCli = rst.getString("st_reg");
                    if (strEstRegCli.equals("I")) {    cboEst.setSelectedIndex(1);    } else {    cboEst.setSelectedIndex(0);      }
                    
                    //Mensaje de cliente si está dado de alta o no.
                    if (strEstRegCli.equals("N")) {  lblEstCli.setText("Cli/Prov Nuevo Tiene que ser dado de Alta."); }  else {  lblEstCli.setText("");   }
                    
                    //Estado de registro del cliente
                    strAux="";
                    if (strEstRegCli.equals("A")) {  strAux = "Activo";    } 
                    else if (strEstRegCli.equals("I")) {   strAux = "Anulado";   } 
                    else {  strAux = "Otro";  }
                    objTooBar.setEstadoRegistro(strAux);
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;

                //Mostrar la posición relativa del registro.
                intPosRel = intIndiceCon+1;
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );   
                blnHayCam=false;
            }
        }
        catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean cargarTabPro(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            if (conExt != null) 
            {
                strSQL = "SELECT  tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro, fe_conemp, tx_tipactemp , tx_obspro FROM  tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + "  and st_reg not in('E')  and co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                 while(rst.next())
                 {
                    txtCedPro.setText( rst.getString("tx_idepro") );
                    txtNomPro.setText( rst.getString("tx_nompro") );
                    txtDomPro.setText( rst.getString("tx_dirpro") );
                    txtTelPro.setText( rst.getString("tx_telpro") );
                    txtNacPro.setText( rst.getString("tx_nacpro") );

		    if(rst.getDate("fe_conemp")==null){
			txtFecDoc.setHoy();
		    }
                    else  {
			java.util.Date dateObj = rst.getDate("fe_conemp");
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(dateObj);
			txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR) );
		    }
                    txtActPro.setText( rst.getString("tx_tipactemp") );
                    txaObsPro.setText( rst.getString("tx_obspro") );
                 }

                strSQL = "SELECT  co_reg, tx_nom  FROM tbm_accempcli WHERE co_emp=" + objParSis.getCodigoEmpresa() + "  AND  co_cli=" + txtCodCli.getText();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_ACCLIN, "");
                    vecReg.add(INT_TBL_ACCCOD, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_ACCNOM, rst.getString("tx_nom"));
                    vecData.add(vecReg);
                }
                objTblModAcc.setData(vecData);
                tblAcc.setModel(objTblModAcc);

                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    private boolean cargarTabCre(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            if (conExt != null) 
            {
                strSQL =" SELECT round(a.nd_moncre,2) as nd_moncre , a.co_forpag, p.tx_des,  a.st_cieretpen, ne_diamesmaxemifacven, ne_diasememifacven FROM tbm_cli as a "
                      + " LEFT JOIN tbm_cabforpag as p on (p.co_emp=a.co_emp and p.co_forpag=a.co_forpag) WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "  AND a.st_reg not in('E')  AND  a.co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    txtMonCre.setText(rst.getString("nd_moncre"));
                    txtCodCre.setText(rst.getString("co_forpag"));
                    txtDesCre.setText(rst.getString("tx_des"));
                    
                    if (rst.getString("st_cieretpen").equals("S")) {
                        chkCieCre.setSelected(true);
                    } else {
                        chkCieCre.setSelected(false);
                    }

                    if (rst.getString("ne_diamesmaxemifacven") != null) {
                        cboDiaMesMaxEmiFac.setSelectedIndex(rst.getInt("ne_diamesmaxemifacven"));
                    } else {
                        cboDiaMesMaxEmiFac.setSelectedIndex(0);
                    }

                    if (rst.getString("ne_diasememifacven") != null) {
                        cboDiaSemEmiFac.setSelectedIndex(rst.getInt("ne_diasememifacven"));
                    } else {
                        cboDiaSemEmiFac.setSelectedIndex(0);
                    }
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabDirPar(Connection conExt)
    {
        boolean blnRes = true;
        String strNomMod = "";
        try 
        {
            if (conExt != null) {
                strSQL = " SELECT ne_mod, tx_dir, tx_refubi, tx_tel1, tx_tel2, tx_tel3, tx_obs1 FROM tbm_dircli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText() + " AND ne_mod=" + intCodMod;
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DIR_LIN, "");

                    switch (rst.getInt("ne_mod")) 
                    {
                        case 1:
                            strNomMod = "MOD.VENTAS";
                            break;  // MODULO VENTAS
                        case 2:
                            strNomMod = "MOD.INVENTARIO";
                            break;  // MODULO INVENTARIO
                        case 3:
                            strNomMod = "MOD.CXC";
                            break;  // MODULO CXC
                        case 4:
                            strNomMod = "MOD.CXP";
                            break;  // MODULO CXP
                        case 6:
                            strNomMod = "MOD.MAESTRO";
                            break;  // MODULO MAESTRO
                    }

                    vecReg.add(INT_TBL_DIR_NMODDI, strNomMod);
                    vecReg.add(INT_TBL_DIR_DIR, rst.getString("tx_dir"));
                    vecReg.add(INT_TBL_DIR_REF, rst.getString("tx_refubi"));
                    vecReg.add(INT_TBL_DIR_TEL1, rst.getString("tx_tel1"));
                    vecReg.add(INT_TBL_DIR_TEL2, rst.getString("tx_tel2"));
                    vecReg.add(INT_TBL_DIR_TEL3, rst.getString("tx_tel3"));
                    vecReg.add(INT_TBL_DIR_OBS2, rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_DIR_MODD, rst.getString("ne_mod"));
                    vecReg.add(INT_TBL_DIR_CONT, "I");  /* José Mario 20/Oct/2015 */

                    vecData.add(vecReg);
                }
                objTblModDir.setData(vecData);
                tblDir.setModel(objTblModDir);
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabConPar(Connection conExt) 
    {
        boolean blnRes = true;
        String strNomMod = "";
        try 
        {
            if (conExt != null)
            {
                strSQL = " SELECT  ne_mod, co_loc,  co_reg, tx_nom, tx_car, tx_tel1, tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1 FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText() + " and ne_mod=" + intCodMod;
                if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) 
                {
                    strSQL = "SELECT  ne_mod, co_loc,  co_reg, tx_nom, tx_car, tx_tel1, tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1 FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and  co_cli=" + txtCodCli.getText() + " and ne_mod=" + intCodMod;
                }
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_CON_LIN, "");

                    switch (rst.getInt("ne_mod")) 
                    {
                        case 1:
                            strNomMod = "MOD.VENTAS";
                            break;  // MODULO VENTAS
                        case 2:
                            strNomMod = "MOD.INVENTARIO";
                            break;  // MODULO INVENTARIO
                        case 3:
                            strNomMod = "MOD.CXC";
                            break;  // MODULO CXC
                        case 4:
                            strNomMod = "MOD.CXP";
                            break;  // MODULO CXP
                        case 6:
                            strNomMod = "MOD.MAESTRO";
                            break;  // MODULO MAESTRO
                    }

                    vecReg.add(INT_TBL_CON_NOMMOD, strNomMod);
                    vecReg.add(INT_TBL_CON_NOM, rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_CON_CAR, rst.getString("tx_car"));
                    vecReg.add(INT_TBL_CON_TEL1, rst.getString("tx_tel1"));
                    vecReg.add(INT_TBL_CON_TEL2, rst.getString("tx_tel2"));
                    vecReg.add(INT_TBL_CON_TEL3, rst.getString("tx_tel3"));
                    vecReg.add(INT_TBL_CON_MA1, rst.getString("tx_corele1"));
                    vecReg.add(INT_TBL_CON_MA2, rst.getString("tx_corele2"));
                    vecReg.add(INT_TBL_CON_OBS, rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_CON_MODC, rst.getString("ne_mod"));
                    vecReg.add(INT_TBL_CON_LOCC, rst.getString("co_loc"));
                    vecData.add(vecReg);
                }
                objTblModCon.setData(vecData);
                tblCon.setModel(objTblModCon);
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabDir(Connection conExt) 
    {
        boolean blnRes = true;
        String strNomMod = "";
        try 
        {
            if (conExt != null) 
            {
                strSQL = " SELECT ne_mod, tx_dir, tx_refubi, tx_tel1, tx_tel2, tx_tel3, tx_obs1 FROM tbm_dircli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DIR_LIN, "");

                    switch (rst.getInt("ne_mod"))
                    {
                        case 1:
                            strNomMod = "MOD.VENTAS";
                            break;  // MODULO VENTAS
                        case 2:
                            strNomMod = "MOD.INVENTARIO";
                            break;  // MODULO INVENTARIO
                        case 3:
                            strNomMod = "MOD.CXC";
                            break;  // MODULO CXC
                        case 4:
                            strNomMod = "MOD.CXP";
                            break;  // MODULO CXP
                        case 6:
                            strNomMod = "MOD.MAESTRO";
                            break;  // MODULO MAESTRO
                    }

                    vecReg.add(INT_TBL_DIR_NMODDI, strNomMod);
                    vecReg.add(INT_TBL_DIR_DIR, rst.getString("tx_dir"));
                    vecReg.add(INT_TBL_DIR_REF, rst.getString("tx_refubi"));
                    vecReg.add(INT_TBL_DIR_TEL1, rst.getString("tx_tel1"));
                    vecReg.add(INT_TBL_DIR_TEL2, rst.getString("tx_tel2"));
                    vecReg.add(INT_TBL_DIR_TEL3, rst.getString("tx_tel3"));
                    vecReg.add(INT_TBL_DIR_OBS2, rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_DIR_MODD, rst.getString("ne_mod"));
                    vecReg.add(INT_TBL_DIR_CONT, "I");   /* José Mario 20/Oct/2015 */

                    vecData.add(vecReg);
                }
                objTblModDir.setData(vecData);
                tblDir.setModel(objTblModDir);
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabCon(Connection conExt) 
    {
        boolean blnRes = true;
        String strNomMod = "";
        try 
        {
            if (conExt != null)
            {
                strSQL = " SELECT  ne_mod, co_loc, co_reg, tx_nom, tx_car, tx_tel1, tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1 FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_CON_LIN, "");

                    switch (rst.getInt("ne_mod"))
                    {
                        case 1:
                            strNomMod = "MOD.VENTAS";
                            break;  // MODULO VENTAS
                        case 2:
                            strNomMod = "MOD.INVENTARIO";
                            break;  // MODULO INVENTARIO
                        case 3:
                            strNomMod = "MOD.CXC";
                            break;  // MODULO CXC
                        case 4:
                            strNomMod = "MOD.CXP";
                            break;  // MODULO CXP
                        case 6:
                            strNomMod = "MOD.MAESTRO";
                            break;  // MODULO MAESTRO
                    }
                    vecReg.add(INT_TBL_CON_NOMMOD, strNomMod);
                    vecReg.add(INT_TBL_CON_NOM, rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_CON_CAR, rst.getString("tx_car"));
                    vecReg.add(INT_TBL_CON_TEL1, rst.getString("tx_tel1"));
                    vecReg.add(INT_TBL_CON_TEL2, rst.getString("tx_tel2"));
                    vecReg.add(INT_TBL_CON_TEL3, rst.getString("tx_tel3"));
                    vecReg.add(INT_TBL_CON_MA1, rst.getString("tx_corele1"));
                    vecReg.add(INT_TBL_CON_MA2, rst.getString("tx_corele2"));
                    vecReg.add(INT_TBL_CON_OBS, rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_CON_MODC, rst.getString("ne_mod"));
                    vecReg.add(INT_TBL_CON_LOCC, rst.getString("co_loc"));
                    vecData.add(vecReg);
                }
                objTblModCon.setData(vecData);
                tblCon.setModel(objTblModCon);
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabBen(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            if (conExt != null)
            {
                strSQL ="";
                strSQL+=" SELECT co_reg,tx_benchq,st_Reg ";
                strSQL+=" FROM tbm_benchq ";
                strSQL+=" WHERE st_reg not in('E') AND co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                strSQL+=" ORDER BY co_reg";
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();

                    vecReg.add(INT_TBL_BEN_LIN, "");
                    vecReg.add(INT_TBL_BEN_COD, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_BEN_NOM, rst.getString("tx_benchq"));

                    Boolean blnIvaP = rst.getString("st_reg").equals("P") ? true : false;
                    Boolean blnIvaA = rst.getString("st_reg").equals("A") ? true : false;
                    Boolean blnIvaI = rst.getString("st_reg").equals("I") ? true : false;

                    vecReg.add(INT_TBL_BEN_PRE, blnIvaP);
                    vecReg.add(INT_TBL_BEN_ACT, blnIvaA);
                    vecReg.add(INT_TBL_BEN_INA, blnIvaI);

                    vecData.add(vecReg);
                }
                objTblModBen.setData(vecData);
                tblBenChq.setModel(objTblModBen);
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabObs(Connection conExt)
    {
        boolean blnRes = true;
        String strNomMod = "";
        try 
        {
            if (conExt != null) 
            {
                strSQL = "SELECT  ne_mod, co_reg, fe_ing, tx_obs1, co_loc FROM tbm_obscli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText() + " ORDER BY fe_ing ";
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_CON_OBSLIN, "");

                    switch (rst.getInt("ne_mod")) 
                    {
                        case 1:
                            strNomMod = "MOD.VENTAS";
                            break;  // MODULO VENTAS
                        case 2:
                            strNomMod = "MOD.INVENTARIO";
                            break;  // MODULO INVENTARIO
                        case 3:
                            strNomMod = "MOD.CXC";
                            break;  // MODULO CXC
                        case 4:
                            strNomMod = "MOD.CXP";
                            break;  // MODULO CXP
                        case 6:
                            strNomMod = "MOD.MAESTRO";
                            break;  // MODULO MAESTRO
                    }

                    vecReg.add(INT_TBL_CON_OBSNMOD, strNomMod);
                    vecReg.add(INT_TBL_CON_OBSCOD, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_CON_OBSMOD, rst.getString("ne_mod"));
                    vecReg.add(INT_TBL_CON_OBSFEC, rst.getString("fe_ing"));
                    vecReg.add(INT_TBL_CON_OBSOBS, rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_CON_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CON_OBSBUT, "...");
                    vecData.add(vecReg);
                }
                objTblModObs.setData(vecData);
                tblObs.setModel(objTblModObs);

                strSQL = "SELECT tx_obsven, tx_obsinv, tx_obscxc, tx_obsinfburcre,  tx_obscxp, tx_obs1, tx_obs2 FROM tbm_cli  WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    txtArObsVen.setText(rst.getString("tx_obsven"));
                    txtArObsInv.setText(rst.getString("tx_obsinv"));
                    txtArObscxc.setText(rst.getString("tx_obscxc"));
                    txtArObscxcBurCre.setText(rst.getString("tx_obsinfburcre"));
                    txtArObscxp.setText(rst.getString("tx_obscxp"));
                    txtArObsObs1.setText(rst.getString("tx_obs1"));
                    txtArObsObs2.setText(rst.getString("tx_obs2"));
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabObsPar(Connection conExt) 
    {
        boolean blnRes = true;
        String strNomMod = "";
        try 
        {
            if (conExt != null) 
            {
                strSQL=" SELECT ne_mod, co_reg, fe_ing, tx_obs1,co_loc FROM tbm_obscli WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cli="+txtCodCli.getText()+" AND ne_mod="+intCodMod+" ORDER BY fe_ing ";
                if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                    strSQL=" SELECT  ne_mod, co_reg, fe_ing, tx_obs1, co_loc FROM tbm_obscli WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND co_cli="+txtCodCli.getText()+" AND ne_mod="+intCodMod+" ORDER BY fe_ing ";
                }
                stm=conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                Vector vecData = new Vector();
                while (rst.next()) 
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_CON_OBSLIN, "");
                    switch (rst.getInt("ne_mod"))
                    {
                        case 1:
                            strNomMod = "MOD.VENTAS";
                            break;  // MODULO VENTAS
                        case 2:
                            strNomMod = "MOD.INVENTARIO";
                            break;  // MODULO INVENTARIO
                        case 3:
                            strNomMod = "MOD.CXC";
                            break;  // MODULO CXC
                        case 4:
                            strNomMod = "MOD.CXP";
                            break;  // MODULO CXP
                        case 6:
                            strNomMod = "MOD.MAESTRO";
                            break;  // MODULO MAESTRO
                    }
                    vecReg.add(INT_TBL_CON_OBSNMOD, strNomMod);
                    vecReg.add(INT_TBL_CON_OBSCOD, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_CON_OBSMOD, rst.getString("ne_mod"));
                    vecReg.add(INT_TBL_CON_OBSFEC, rst.getString("fe_ing"));
                    vecReg.add(INT_TBL_CON_OBSOBS, rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_CON_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CON_OBSBUT, "...");
                    vecData.add(vecReg);
                }
                objTblModObs.setData(vecData);
                tblObs.setModel(objTblModObs);
                strSQL =" SELECT tx_obsven, tx_obsinv, tx_obscxc, tx_obsinfburcre, tx_obscxp, tx_obs1, tx_obs2 FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    txtArObsVen.setText(rst.getString("tx_obsven"));
                    txtArObsInv.setText(rst.getString("tx_obsinv"));
                    txtArObscxc.setText(rst.getString("tx_obscxc"));
                    txtArObscxcBurCre.setText(rst.getString("tx_obsinfburcre"));
                    txtArObscxp.setText(rst.getString("tx_obscxp"));
                    txtArObsObs1.setText(rst.getString("tx_obs1"));
                    txtArObsObs2.setText(rst.getString("tx_obs2"));
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarTabCliLoc(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            strSQL =" SELECT a.co_loc, a.tx_nom, CASE WHEN a.co_loc=b.co_loc THEN 'S' ELSE 'N' END as loc FROM tbm_loc as a "
                   +" LEFT JOIN tbr_cliloc as b on (b.co_emp=a.co_emp AND b.co_loc=a.co_loc AND b.co_cli=" + txtCodCli.getText() + " ) "
                   +" WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + " AND st_reg not in('I')";
            stm = conExt.createStatement();
            rst = stm.executeQuery(strSQL);
            Vector vecData = new Vector();
            while (rst.next()) 
            {
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LOC_LIN, "");
                vecReg.add(INT_TBL_LOC_CHK, ((rst.getString("loc").equals("S")) ? true : false));
                vecReg.add(INT_TBL_LOC_COD, rst.getString("co_loc"));
                vecReg.add(INT_TBL_LOC_NOM, rst.getString("tx_nom"));
                vecReg.add(INT_TBL_LOC_ESTCAR, ((rst.getString("loc").equals("S")) ? true : false));
                vecData.add(vecReg);
            }
            objTblModCliLoc.setData(vecData);
            tblCliLoc.setModel(objTblModCliLoc);
            rst.close();
            rst = null;
            stm.close();
            stm = null;
        } 
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        return blnRes;
    }

    private boolean cargarTabVen(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            if (conExt != null) 
            {
                strSQL =" SELECT nd_maxdes, ne_diagra, ne_diagrachqfec, nd_maruti, ne_nummaxvencon, st_peringnomclicotven, tx_corEleFacEle, tx_corEleFacEle2, st_proCorEleFacEle FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) {
                    spiMaxPorDes.setValue(new Double(rst.getDouble("nd_maxdes")));
                    spiDiaGraDocVen.setValue(new Integer(rst.getInt("ne_diagra")));
                    spiDiaGraSinSop.setValue(new Integer(rst.getInt("ne_diagrachqfec")));
                    spiMarUti.setValue(new Double(rst.getDouble("nd_maruti")));
                    spiNumMaxVenCon.setValue(new Integer(rst.getInt("ne_nummaxvencon")));
                    chkIngNomCot.setSelected((rst.getString("st_peringnomclicotven").equals("S") ? true : false));
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Funcion de permite cargar datos de impuesto del cliente/proveedor
     * @param conn : recive la coneccion de la base
     * @return true en caso de haber cargado sin problema y false cuando hay
     * problemas.
     */
    private boolean cargarTabImp(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            if (conExt != null) 
            {
                chkIvaVen.setSelected(false);
                chkIvaCom.setSelected(false);

                strSQL = " SELECT st_ivaven, st_ivacom FROM tbm_cli  WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    if (rst.getString("st_ivaven").equals("S")) {
                        chkIvaVen.setSelected(true);
                    }
                    if (rst.getString("st_ivacom").equals("S")) {
                        chkIvaCom.setSelected(true);
                    }
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite cargar Tab de Auditoría.
     * Se consulta datos de auditoría. Si existe la información el primer registro del cliente de tbh_cli
     * para saber el usuario que lo ingreso, caso contrario se toma la información tbm_cli
     * @param conn
     * @return 
     */
    private boolean cargarTabAud(Connection conExt) 
    {
        boolean blnRes = true;
        try 
        {
            if (conExt != null) 
            {
                strSQL = " SELECT a.fe_ing, a.fe_ultmod, b.co_usr as co_usring, b.tx_nom as tx_noming, b1.co_usr as co_usrmod, b1.tx_nom as tx_nommod "
                       + " FROM tbm_cli as a "
                       + " LEFT JOIN tbh_cli as c on (c.co_emp=a.co_emp and c.co_cli = a.co_cli and c.fe_ing = (select min(d.fe_ing) as fe_ing from tbh_cli as d where c.co_emp=d.co_emp and c.co_cli = d.co_cli)) "
                       + " LEFT JOIN tbm_usr as b on (b.co_usr= case when c.co_usring is null then a.co_usring else c.co_usring end) "
                       + " LEFT JOIN tbm_usr as b1 on (b1.co_usr=a.co_usrmod) "
                       + " WHERE a.co_emp=" + objParSis.getCodigoEmpresa()
                       + " AND a.co_cli=" + txtCodCli.getText();

                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    txtFecIng.setText(rst.getString("fe_ing"));
                    txtFecUltMod.setText(rst.getString("fe_ultmod"));
                    txtCodUsrIng.setText(rst.getString("co_usring"));
                    txtCodUsrMod.setText(rst.getString("co_usrmod"));
                    txtNomUsrIng.setText(rst.getString("tx_noming"));
                    txtNomUsrMod.setText(rst.getString("tx_nommod"));
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean cargarTabFacEle(Connection conExt)  
    { 
        boolean blnRes = true;
        try 
        {
            if (conExt != null) 
            {
                strSQL = "SELECT tx_corEleFacEle, tx_corEleFacEle2, st_proCorEleFacEle FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                stm = conExt.createStatement();
                rst = stm.executeQuery(strSQL);
                while (rst.next())
                {
                    txtCorEleFacEle1.setText((rst.getString("tx_corEleFacEle") == null ? "" : (rst.getString("tx_corEleFacEle")))); //José Marín FacEle 14/Oct/2014
                    txtCorEleFacEle2.setText((rst.getString("tx_corEleFacEle2") == null ? "" : (rst.getString("tx_corEleFacEle2"))));  //José Marín FacEle 7/Nov/2014
                    if (rst.getString("st_proCorEleFacEle") == null) {
                        optFacElePen.setSelected(true);
                    }
                    else if (rst.getString("st_proCorEleFacEle").equals("S") ){
                        optFacEleSi.setSelected(true);
                    }
                    else if (rst.getString("st_proCorEleFacEle").equals("N") ){
                        optFacEleNo.setSelected(true);
                    }
                }
                rst.close();
                rst=null;
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
    
//**************************************************************************************    
    
    private boolean insertarReg() 
    {
        boolean blnRes = false;
        Connection conIns;
        int intCodCli = 1;
        try 
        {
            stbDatModInsCli = new StringBuffer();
            conIns = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conIns != null) 
            {
                conIns.setAutoCommit(false);
                stm = conIns.createStatement();

                strSQL = "SELECT count(*) as valor FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and  tx_ide=" + ((strAux = txtIde.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + "";
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    if (rst.getInt(1) > 0) 
                    {
                        mostrarMsgInf("El No. Identificación ya existe no es posible insertar.");
                        txtIde.requestFocus();
                        tabGen.setSelectedIndex(0);
                        conIns.close();
                        conIns = null;
                        return false;
                    }
                }

                strSQL = "SELECT case when MAX(co_cli) is null then 1 else MAX(co_cli)+1 end as co_cli FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa();
                rst = stm.executeQuery(strSQL);
                if (rst.next())  {
                    intCodCli = rst.getInt("co_cli");
                }
                
                rst.close();
                rst = null;
                stm.close();
                stm = null;                

                if (insertarTabGen(conIns, intCodCli)) {
                    if (insertarTabDir(conIns, intCodCli)) {
                        if (insertarTabCon(conIns, intCodCli)) {
                            if (insertarTabBen(conIns, intCodCli)) {
                                if (insertarTabObs(conIns, intCodCli)) {
                                    if (insertarTabLoc(conIns, intCodCli)) {
                                        if (insertarTabFacEle(conIns, intCodCli)) {
                                            if (insertarTabPro(conIns, intCodCli)) {
                                                if (insertarTabImp(conIns, intCodCli)) 
                                                {
                                                    conIns.commit();
                                                    txtCodCli.setText("" + intCodCli);
                                                    blnRes = true;
                                                }else conIns.rollback();
                                            }else conIns.rollback();
                                        }else conIns.rollback();
                                    }else conIns.rollback();
                                }else conIns.rollback();
                            }else conIns.rollback();
                        }else conIns.rollback();
                    }else conIns.rollback();
                } else conIns.rollback();

                conIns.close();
                conIns = null;
            }
            stbDatModInsCli=null;
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean insertarTabGen(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        String strTelCompleto = "";
        String strFecSis = "";
        String strValNomClie = "";
        char chrEstCli, chrEstPro;
        String stroriing = "N";
        String strCodForParPre = null;
        String strMaxDesPre = "0";
        String strMarUtiPre = "0";

        try 
        {
            if (conIns != null) 
            {
                if (chkCli.isSelected()) {
                    chrEstCli = 'S';
                } else {
                    chrEstCli = 'N';
                }

                if (chkPro.isSelected()) {
                    chrEstPro = 'S';
                } else {
                    chrEstPro = 'N';
                }

                if (cboTipIde.getSelectedIndex() == 0) {
                    chrIde = 'R';
                } else if (cboTipIde.getSelectedIndex() == 1) {
                    chrIde = 'C';
                } else if (cboTipIde.getSelectedIndex() == 2) { 
                    chrIde = 'P';
                } else if (cboTipIde.getSelectedIndex() == 3) {
                    chrIde = 'F';    
                } else if (cboTipIde.getSelectedIndex() == 4) {
                    chrIde = 'E';      
                }

                stm = conIns.createStatement();

                strSql =" SELECT co_forpagpre, nd_maxdespre, nd_marutipre "
                       +" FROM tbm_loc WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal();
                rst = stm.executeQuery(strSql);
                if (rst.next())
                {
                    strCodForParPre = rst.getString("co_forpagpre");
                    strMaxDesPre = rst.getString("nd_maxdespre");
                    strMarUtiPre = rst.getString("nd_marutipre");
                }
                rst.close();
                rst = null;

                spiMaxPorDes.setValue(new Double(Double.parseDouble(strMaxDesPre)));
                spiMarUti.setValue(new Double(Double.parseDouble(strMarUtiPre)));

                String strParEmp = "", strEstReg = "A", StrRegSex = "", StrRegEstCiv = "";

                if (chkCli.isSelected()) {
                    strParEmp = "6";
                }
                if (chkPro.isSelected()) {
                    strParEmp = "7";
                }

                StrRegSex = cboSex.getSelectedItem().toString();
                if (StrRegSex == "Masculino") {
                    StrRegSex = "M";
                } else if (StrRegSex == "Femenino") {
                    StrRegSex = "F";
                } else {

                    StrRegSex = "N";
                }

                StrRegEstCiv = cboEstCiv.getSelectedItem().toString();
                //case identificar que estado civil tiene el cli/prov
                switch (cboEstCiv.getSelectedIndex()) 
                {
                    case 0:   StrRegEstCiv = "N";    break;
                    case 1:   StrRegEstCiv = "S";    break;
                    case 2:   StrRegEstCiv = "C";    break;
                    case 3:   StrRegEstCiv = "D";    break;
                    case 4:   StrRegEstCiv = "V";    break;
                    case 5:   StrRegEstCiv = "U";    break;
                }

                if (cboOriIng.getSelectedIndex() == 0) {
                    stroriing = "N";
                } else if (cboOriIng.getSelectedIndex() == 1) {
                    stroriing = "B";
                } else if (cboOriIng.getSelectedIndex() == 2) {
                    stroriing = "V";
                } else if (cboOriIng.getSelectedIndex() == 3) {
                    stroriing = "I";
                } else if (cboOriIng.getSelectedIndex() == 4) {
                    stroriing = "A";
                } else if (cboOriIng.getSelectedIndex() == 5) {
                    stroriing = "R";
                } else if (cboOriIng.getSelectedIndex() == 6) {
                    stroriing = "H";
                } else if (cboOriIng.getSelectedIndex() == 7) {
                    stroriing = "M";
                }

                strSql = " SELECT * FROM tbr_paremp WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_par=" + strParEmp + " AND nd_par1=2";
                rst = stm.executeQuery(strSql);
                if (rst.next())  {
                    strEstReg = "N";
                }
                rst.close();
                rst = null;

                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                strTelCompleto = txtTelCli1.getText() + " " + txtTelCli2.getText() + " " + txtTelCli3.getText();

                strSql  =" INSERT INTO tbm_cli (co_emp, co_cli, st_cli, st_prv, tx_tipIde, tx_ide, tx_nom, tx_dir,tx_refubi, tx_tel "
                        +"  , tx_fax, tx_cas, tx_dirweb, tx_corele, co_tipper, co_ciu,co_par, co_zon, st_reg, st_sex, st_estciv, st_oriing, co_usring "
                        +"  , fe_ing, tx_tel1, tx_tel2 , tx_tel3, tx_repleg, co_grp "
                        +"  , tx_obsven, tx_obsinv, tx_obscxc, tx_obscxp, tx_obs1, tx_obs2, tx_obsinfburcre, co_forpag , nd_maruti, nd_maxdes ) "
                        +" VALUES(" + objParSis.getCodigoEmpresa() + "," + intcodCli + ",'" + chrEstCli + "','" + chrEstPro + "','" + chrIde + "'"
                        +" , " + ((strAux = txtIde.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                        +" , " + ((strValNomClie = (objUti.remplazarEspacios(txtNomCli.getText())).replaceAll("'", "''")).equals("") ? "Null" : "'" + strValNomClie + "'") + ""
                        +" , " + ((strAux = txtDirCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                        +" , " + ((strAux = txtRefDirCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") +""
                        +" ,'" + strTelCompleto + "'"
                        +" , " + ((strAux = txtFaxCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                        +" , " + ((strAux = txtCasCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                        +" , " + ((strAux = txtWebCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                        +" , " + ((strAux = txtEmaCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                        +" , " + (txtCodTipPer.getText().trim().equals("") ? null : txtCodTipPer.getText()) + ""
                        +" , " + (txtCodCiu.getText().trim().equals("") ? null : txtCodCiu.getText()) + ""
                        +" , " + (txtCodPar.getText().trim().equals("") ? null : txtCodPar.getText()) +""
                        +" , " + (txtCodZon.getText().trim().equals("") ? null : txtCodZon.getText()) + ""
                        +" , '"+  strEstReg + "' , '" + StrRegSex + "' , '" + StrRegEstCiv + "' " 
                        +" , '"+ stroriing + "' ," + objParSis.getCodigoUsuario() + ",'" + strFecSis + "'"
                        +" , '"+ txtTelCli1.getText() + "','" + txtTelCli2.getText() + "','" + txtTelCli3.getText() + "'"
                        +" , "+ ((strAux = txtRepleg.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + " "
                        +" , "+ (txtCodGrp.getText().trim().equals("") ? null : txtCodGrp.getText()) + ""
                        +" , "+ objUti.codificar(txtArObsVen.getText()) + ", " + objUti.codificar(txtArObsInv.getText()) +""
                        +" , "+ objUti.codificar(txtArObscxc.getText()) + ", " + objUti.codificar(txtArObscxp.getText()) + ""
                        +" , "+ objUti.codificar(txtArObsObs1.getText()) + ", " + objUti.codificar(txtArObsObs2.getText()) +""
                        +" , "+ objUti.codificar(txtArObscxcBurCre.getText()) +""
                        +" , "+ strCodForParPre + " , " + strMarUtiPre + " , " + strMaxDesPre + " "
                        +" )";
                stm.executeUpdate(strSql);

                strEstRegCli = "N";
                stm.close();
                stm = null;
                txtTelCli.setText(strTelCompleto);
                txtIdeAux.setText(txtIde.getText());
            }
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            evt.printStackTrace();
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean insertarTabDir(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        String strFecSis = "";
        int intTipDir = 0;
        try 
        {
            if (conIns != null) 
            {
                stm = conIns.createStatement();
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                for (int i = 0; i < tblDir.getRowCount(); i++) 
                {
                    if(tblDir.getValueAt(i,INT_TBL_DIR_DIR)!=null)
                    {
                        if (i>0)
                            stbins.append(" UNION ALL ");
                            stbins.append("SELECT "+objParSis.getCodigoEmpresa()+","+intcodCli+","+intCodMod+","+(i+1)+","+
                            "'"+((tblDir.getValueAt(i,INT_TBL_DIR_DIR)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_DIR).toString())+"'," +
                            "'"+((tblDir.getValueAt(i,INT_TBL_DIR_REF)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_REF).toString())+"'," +
                            "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL1)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL1).toString())+"'," +
                            "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL2)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL2).toString())+"'," +
                            "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL3)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL3).toString())+"'," +
                            "'"+((tblDir.getValueAt(i,INT_TBL_DIR_OBS2)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_OBS2).toString())+"'," +
                            "'A',date('"+strFecSis+"'),"+objParSis.getCodigoUsuario()+",'I'");
                            intTipDir=1;
                    }
                }
                if (intTipDir == 1)   {
                    strSql =" INSERT INTO tbm_dircli(co_emp, co_cli, ne_mod, co_reg, tx_dir, tx_refubi, tx_tel1, tx_tel2,tx_tel3, tx_obs1, st_reg, fe_ing, co_usring, st_regrep) "
                           +" " + stbins.toString() + " ";
                    stm.executeUpdate(strSql);
                }
                stbins = null;

                stm.close();
                stm = null;
            }
        }
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean insertarTabCon(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        String strFecSis = "";
        int intTipCon = 0;
        try 
        {
            if (conIns != null) 
            {
                stm = conIns.createStatement();
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                for (int i = 0; i < tblCon.getRowCount(); i++) {
                    if ((tblCon.getValueAt(i,INT_TBL_CON_NOM)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_NOM).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_CAR)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_CAR).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL1)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL1).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL2)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL2).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL3)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL3).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_MA1)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_MA1).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_MA2)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_MA2).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_OBS)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_OBS).toString().equals("")?false:true)) ){

                    if (intTipCon>0)
                          stbins.append(" ; ");
                          stbins.append(" INSERT INTO tbm_concli(co_emp, co_loc, co_cli,ne_mod,co_reg,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3" +
                                        "                       ,tx_corele1,tx_corele2,st_reg,tx_obs1,co_usring,fe_ing,st_regrep) " +
                                        " SELECT "+objParSis.getCodigoEmpresa()+", "+objParSis.getCodigoLocal()+
                                        " , "+intcodCli+","+intCodMod+","+(i+1)+""+
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_NOM)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_NOM).toString())+"'" +
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_CAR)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_CAR).toString())+"'" +
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL1)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL1).toString())+"'" +
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL2)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL2).toString())+"'" +
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL3)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL3).toString())+"'" +
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_MA1)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_MA1).toString())+"'" +
                                        " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_MA2)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_MA2).toString())+"'" +
                                        " ,'A','"+((tblCon.getValueAt(i,INT_TBL_CON_OBS)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_OBS).toString())+"'" +
                                        " ,"+objParSis.getCodigoUsuario()+",'"+strFecSis+"'"+
                                        " ,'I' ");
                          intTipCon=1;
                    }
                }
                if (intTipCon == 1)    {
                    stm.executeUpdate(stbins.toString());
                    stbins = null;
                }
                stm.close();
                stm = null;
            }
      }catch (SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
       catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
     return blnRes;
    }

    private boolean insertarTabBen(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        String strest = "";
        String strEst = "P";
        int intReg = 0, x = 0;
        try 
        {
            if (conIns != null)
            {
                stm = conIns.createStatement();

                for (x = 0; x < tblBenChq.getRowCount(); x++) 
                {
                    if (tblBenChq.getValueAt(x, INT_TBL_BEN_NOM) != null) 
                    {
                        strest = "A";
                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_PRE) != null)  {
                            if (tblBenChq.getValueAt(x, INT_TBL_BEN_PRE).toString().equalsIgnoreCase("true"))  {
                                strest = "P";
                                strEst = "A";
                            }
                        }

                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_ACT) != null)     {
                            if (tblBenChq.getValueAt(x, INT_TBL_BEN_ACT).toString().equalsIgnoreCase("true")) {
                                strest = "A";
                            }
                        }
                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_INA) != null)   {
                            if (tblBenChq.getValueAt(x, INT_TBL_BEN_INA).toString().equalsIgnoreCase("true"))  {
                                strest = "I";
                            }
                        }

                        intReg = x + 1;
                        strSQL = "INSERT INTO tbm_benchq(co_emp,co_cli,co_reg,tx_benchq,st_reg, st_regrep) VALUES"
                                + "(" + objParSis.getCodigoEmpresa() + "," + intcodCli + "," + intReg + ",'";
                        strSQL += (tblBenChq.getValueAt(x, INT_TBL_BEN_NOM) == null) ? " " : tblBenChq.getValueAt(x, INT_TBL_BEN_NOM).toString() + "',";
                        strSQL += " '" + strest + "' ,'I')";
                        stm.executeUpdate(strSQL);

                    }
                }
                intReg = x;
                strSQL=" INSERT INTO tbm_benchq(co_emp,co_cli,co_reg,tx_benchq,st_reg,st_regrep) "
                      +" VALUES(" + objParSis.getCodigoEmpresa() + "," + intcodCli + "," + intReg + ",'";
                strSQL+= txtNomCli.getText() + "',";
                strSQL+= " '" + strEst + "','I' )";
                stm.executeUpdate(strSQL);

                stm.close();
                stm = null;
            }
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean insertarTabObs(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        Statement stmLoc;
        int intTipCon = 0;
        try
        {
            if (conIns != null) {
                stmLoc = conIns.createStatement();

                for (int i = 0; i < tblObs.getRowCount(); i++) 
                {
                    if (tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) != null) 
                    {
                      if (i>0)
                            stbins.append(" ; ");
                            stbins.append(" INSERT INTO tbm_obscli(co_emp, co_loc,  co_cli, ne_mod, co_reg, tx_obs1, co_usring, fe_ing, tx_coming, st_regrep ) " +
                                          " SELECT "+objParSis.getCodigoEmpresa()+" " +
                                          " , "+objParSis.getCodigoLocal()+" "+
                                          " , "+intcodCli+" "+
                                          " , "+intCodMod+","+(i+1)+""+
                                          " , '"+((tblObs.getValueAt(i,INT_TBL_CON_OBSOBS)==null)?"":tblObs.getValueAt(i,INT_TBL_CON_OBSOBS).toString())+"', "+objParSis.getCodigoUsuario()+"  " +
                                          " , "+objParSis.getFuncionFechaHoraBaseDatos()+""+
                                          " , '"+objParSis.getNombreComputadoraConDirIP()+"'"+
                                          " , 'I'" );
                            intTipCon=1;
                    }
                }
                if (intTipCon == 1) {
                    stmLoc.executeUpdate(stbins.toString());
                    stbins = null;
                }
                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
        catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
        return blnRes;
    }

    private boolean insertarTabLoc(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        Statement stmLoc;
        int intTipDir = 0;
        try
        {
            if (conIns != null) 
            {
                stmLoc = conIns.createStatement();
                strSQL = " INSERT INTO tbr_cliloc(CO_EMP, CO_LOC, CO_CLI, ST_REGREP,CO_VEN,FE_ULTMOD,CO_USRMOD) "
                       + " VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + "," + intcodCli + ",'I',9,current_timestamp,"+objParSis.getCodigoUsuario()+");";  
                stmLoc.executeUpdate(strSQL);

                for (int i = 0; i < tblCliLoc.getRowCount(); i++) 
                {
                    if (tblCliLoc.getValueAt(i, INT_TBL_LOC_CHK).toString().equals("true")) {
                        if (Integer.parseInt(tblCliLoc.getValueAt(i, INT_TBL_LOC_COD).toString()) != objParSis.getCodigoLocal()) {
                            if (!tblCliLoc.getValueAt(i, INT_TBL_LOC_ESTCAR).toString().equals(tblCliLoc.getValueAt(i, INT_TBL_LOC_CHK).toString()))  //SI NO ES IGUAL AL ESTADO ORIGINAL QUE GRABE
                            { 
                                if (intTipDir > 0) {     stbins.append(" UNION ALL ");                     }
                                stbins.append("SELECT " + objParSis.getCodigoEmpresa() + "," + tblCliLoc.getValueAt(i, INT_TBL_LOC_COD).toString() + ""
                                            + " ," + intcodCli + ",'I',9, current_timestamp,"+objParSis.getCodigoUsuario());                                        
                                intTipDir = 1;
                            }
                        }
                    }
                }
                if (intTipDir == 1) {
                    strSQL = "INSERT INTO tbr_cliloc(CO_EMP, CO_LOC, CO_CLI, ST_REGREP,FE_ULTMOD,CO_USRMOD) " + stbins.toString()+";";
                    stmLoc.executeUpdate(strSQL);
                }
                stbins = null;

                stmLoc.close();
                stmLoc = null;
            }
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean insertarTabPro(Connection conIns, int intcodCli)
    {
        boolean blnRes=true;
        StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
        Statement stmLoc;
        int intTipPro=0;
        try
        {
            if(conIns!=null){
                stmLoc = conIns.createStatement();
                String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                strSQL = " UPDATE tbm_cli SET " +
                         "   tx_idepro='"+txtCedPro.getText()+"' " +
                         " , tx_nompro='"+txtNomPro.getText()+"' " +
                         " , tx_dirpro='"+txtDomPro.getText()+"' " +
                         " , tx_telpro='"+txtTelPro.getText()+"' " +
                         " , tx_nacpro='"+txtNacPro.getText()+"' " +
                         " , fe_conemp='"+strFecha+"' " +
                         " , tx_tipactemp='"+txtActPro.getText()+"' " +
                         " , tx_obspro='"+txaObsPro.getText()+"' " +
                         " WHERE CO_EMP="+objParSis.getCodigoEmpresa()+" AND CO_CLI="+intcodCli;
                stmLoc.executeUpdate(strSQL);

                for (int i = 0; i < tblAcc.getRowCount(); i++){
                    if(tblAcc.getValueAt(i,INT_TBL_ACCNOM)!=null){
                        if (i>0) { stbins.append(" ; "); }
                            stbins.append(" INSERT INTO tbm_accempcli(co_emp, co_cli, co_reg, tx_nom ) " +
                                          " SELECT "+objParSis.getCodigoEmpresa()+",  "+intcodCli+","+(i+1)+" "+
                                          ", '"+((tblAcc.getValueAt(i,INT_TBL_ACCNOM)==null)?"":tblAcc.getValueAt(i,INT_TBL_ACCNOM).toString())+"'");
                            intTipPro=1;
                    }
                }
                if(intTipPro==1){
                    stmLoc.executeUpdate( stbins.toString() );
                    stbins=null;
                }
                stbins=null;

                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
        catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
        return blnRes;
    }
    
    private boolean insertarTabFacEle(Connection conIns, int intcodCli) 
    {
        boolean blnRes = true;
        try 
        {
            if (conIns != null) 
            {
                stm = conIns.createStatement();
                strSQL =" UPDATE tbm_cli SET tx_corEleFacEle=" + objUti.codificar(txtCorEleFacEle1.getText(), 0) + " "; 
                strSQL+=" ,tx_corEleFacEle2=" + objUti.codificar(txtCorEleFacEle2.getText(), 0) + " "; 
                
                if (optFacElePen.isSelected() == true) {
                    strSQL+=" ,st_proCorEleFacEle=null";
                } else if (optFacEleSi.isSelected() == true) {
                    strSQL+=" ,st_proCorEleFacEle='S'";
                } else {
                    strSQL+=" ,st_proCorEleFacEle='N'";
                }
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + intcodCli + " ;";
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } 
        catch (SQLException evt) {
            blnRes = false;
             objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        catch (Exception evt){
             blnRes=false;
             objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }    
          
//**************************************************************************************               
    private boolean actualizarReg() 
    {
        boolean blnRes = false;
        java.sql.Connection conMod;
        int intTipSel = 0;
        try
        {
            stbDatModInsCli = new StringBuffer();

            if (!abrirConRem()) 
                return false;

            conMod = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conMod != null) 
            {
                conMod.setAutoCommit(false);

                if (!(txtIde.getText().trim().equals(txtIdeAux.getText().trim()))) {
                    if (INTCODREGCEN != 0) {
                        if (verificaIde(conRemGlo)) 
                        {
                            txtIde.requestFocus();
                            tabGen.setSelectedIndex(0);
                            intTipSel = 1;
                        }
                    } 
                    else 
                    {
                        if (verificaIde(conMod)) 
                        {
                            txtIde.requestFocus();
                            tabGen.setSelectedIndex(0);
                            intTipSel = 1;
                        }
                    }
                }
                if (intTipSel == 0) {
                    if (actualizarTabGen(conMod)) {
//                        if (actualizarTabGenCodVen(conMod)) {
                            if (actualizarTabCre(conMod)) {
                                if (actualizarTabDir(conMod)) {
                                    if (actualizarTabCon(conMod)) {
                                        if (actualizarTabBen(conMod)) {
                                            if (actualizarTabObs(conMod)) {
                                                if (actualizarTabLoc(conMod)) {
                                                    if (actualizarTabPro(conMod)) {
                                                        if (actualizarTabVen(conMod, Integer.parseInt(txtCodCli.getText()))){
                                                            if (actualizarTabImp(conMod, Integer.parseInt(txtCodCli.getText()))) {
                                                                if (actualizarTabFacEle(conMod, Integer.parseInt(txtCodCli.getText()))) {
                                                                    if (actualizaRegCli(conMod))
                                                                    {
                                                                        if (INTCODREGCEN != 0) 
                                                                        {
                                                                            conRemGlo.commit();
                                                                        }
                                                                        conMod.commit();
                                                                        blnRes = true;
                                                                    }else { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                                                }else { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                                            }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                                        }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                                    }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                                }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                            }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                        }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                    }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                                }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                            }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
//                        }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                    }else  { conMod.rollback(); if(INTCODREGCEN!=0)  conRemGlo.rollback(); }
                } 
                else
                {
                    strMsg = "El Nº de Identificación <<" + txtIde.getText() + ">> ya existe.\nEscriba otro Nº de Identificación y vuelva a intentarlo. \n¿Desea asociar el cliente que existe a este local.? ";
                    if(!( mostrarMsgYesNo(strMsg) == 1 )) 
                    {
                         if (INTCODREGCEN != 0) 
                         {
                            if (agregarCliLocRem(conRemGlo, conMod)) 
                            {
                                conRemGlo.commit();
                                conMod.commit();
                                blnRes = true;
                                mostrarMsgOK("EL CLIENTE HA SIDO AGREGADO A ESTE LOCAL BUSQUELA POR EL IDE");
                            } 
                            else    {
                                conRemGlo.rollback();
                            }
                        }
                         else
                         {
                            if (agregarCliLoc(conMod)) 
                            {
                                conMod.commit();
                                blnRes = true;
                                mostrarMsgOK("EL CLIENTE HA SIDO AGREGADO A ESTE LOCAL BUSQUELA POR EL IDE");
                            } 
                            else       {
                                conMod.rollback();
                            }
                        }
                    }
                }

                if (blnRes)      {
                    cargarTabObs(conMod);
                }

                if (INTCODREGCEN != 0) 
                {
                    conRemGlo.close();
                    conRemGlo = null;
                }
                conMod.close();
                conMod = null;
            }
            stbDatModInsCli = null;
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarRegCxP()
    {
        boolean blnRes=false;
        int intTipSel=0;
        try
        {
            stbDatModInsCli=new StringBuffer();
            getConexion();
            if(conn!=null)
            {
                if(!(txtIde.getText().trim().equals(txtIdeAux.getText().trim())))
                {
                    if(verificaIde(conn))
                    {
                        txtIde.requestFocus();
                        tabGen.setSelectedIndex(0);
                        intTipSel=1;
                    }
                }

                if(intTipSel==0)
                {
                    switch (tabGen.getSelectedIndex() )
                    {
                        case 0: //TAB GENERAL
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabGen(conn);
                            break;
                        case 1: //TAB CREDITO
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabCre(conn);
                            break;
                        case 2: //TAB DIRECCIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabDirPar(conn);
                            break;
                        case 3: //TAB CONTACTOS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabConPar(conn);
                            break;
                        case 4: //TAB BENEFICIARIO
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabBen(conn);
                            break;
                        case 5: //TAB OBSERVACIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabObsPar(conn);
                            break;
                        case 6: //TAB IMPUESTOS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabImp(conn, Integer.parseInt(txtCodCli.getText() ) );
                            break;
                        case 7: //TAB FACTURACION ELECTRONICA
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabFacEle(conn, Integer.parseInt(txtCodCli.getText() ) );
                            break;
                        case 8: //TAB AUDITORIA
                            isOpcionHabilitada();
                            break;
                        default:
                            break;
                    }
//                        blnRes=actualizarTabVen(conn, Integer.parseInt(txtCodCli.getText())); //Jota
                    if (blnRes)
                        if(actualizaRegCli(conn)){
                            blnRes=true;
                            conn.commit();
                        }else
                            conn.rollback();
                    else {
                        mostrarMsgAdv("No tiene permisos para modificar informacion de la opcion: " + tabGen.getTitleAt(tabGen.getSelectedIndex()).toUpperCase());
                        conn.rollback();
                    }
                }else{
                    strMsg="El Nº de Identificación <<" + txtIde.getText() + ">> ya existe.\nEscriba otro Nº de Identificación y vuelva a intentarlo. \n¿Desea asociar el cliente que existe a este local.? ";
                    if(!( mostrarMsgYesNo(strMsg) == 1 )) {
                        if(agregarCliLoc(conn)){
                            conn.commit();
                            blnRes=true;
                            mostrarMsgOK("EL CLIENTE HA SIDO AGREGADO A ESTE LOCAL BUSQUELA POR EL IDE");
                        } else
                            conn.rollback();
                    }
                }

                if(blnRes)
                    cargarTabObsPar(conn);
            }
            stbDatModInsCli=null;
        }catch (SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }finally{
            try{
                if (conn != null)
                    conn.close();
                conn=null;
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }        

    private boolean actualizarRegCxC() 
    {
        boolean blnRes=false;
        int intTipSel=0;
        try
        {
            stbDatModInsCli=new StringBuffer();
            getConexion();
            if (conn != null){
                if(!(txtIde.getText().trim().equals(txtIdeAux.getText().trim()))){
                    if(verificaIde(conn)){
                        txtIde.requestFocus();
                        tabGen.setSelectedIndex(0);
                        intTipSel=1;
                    }
                }

                if(intTipSel==0){
                    switch (tabGen.getSelectedIndex() ){
                        case 0: //TAB GENERAL
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabGen(conn);
//                                blnRes=actualizarTabGenCodVen(conn);
                            break;
                        case 1: //TAB PROPIETARIO
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabPro(conn);
                            break;
                        case 2: //TAB CREDITO
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabCre(conn);
                            break;
                        case 3: //TAB DIRECCIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabDirPar(conn);
                            break;
                        case 4: //TAB CONTACTOS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabConPar(conn);
                            break;
                        case 5: //TAB OBSERVACIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabObsPar(conn);
                            break;
                        case 6: //TAB VENTAS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabVen(conn, Integer.parseInt(txtCodCli.getText()));
                            break;
                        case 7: //TAB IMPUESTOS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabImp(conn, Integer.parseInt(txtCodCli.getText()));
                            break;
                        case 8: //TAB FACTURACION ELECTRONICA
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabFacEle(conn, Integer.parseInt(txtCodCli.getText()));
                            break;
                        case 9: //TAB AUDITORIA
                            isOpcionHabilitada();
                            break;
                        default:
                            break;
                    }

                    if (blnRes)
                        if(actualizaRegCli(conn)){
                            blnRes=true;
                            conn.commit();
                        }else
                            conn.rollback();
                    else {
                        mostrarMsgAdv("No tiene permisos para modificar informacion de la opcion: " + tabGen.getTitleAt(tabGen.getSelectedIndex()).toUpperCase());
                        conn.rollback();
                    }
                }else{
                    strMsg="El Nº de Identificación <<" + txtIde.getText() + ">> ya existe.\nEscriba otro Nº de Identificación y vuelva a intentarlo. \n¿Desea asociar el cliente que existe a este local.? ";
                    if(( mostrarMsgYesNo(strMsg) == JOptionPane.OK_OPTION )) {
                        if(agregarCliLoc(conn)){
                            conn.commit();
                            blnRes=true;
                            mostrarMsgOK("EL CLIENTE HA SIDO AGREGADO A ESTE LOCAL BUSQUELA POR EL IDE");
                        } else
                            conn.rollback();
                    }
                }

                if(blnRes)
                    cargarTabObsPar(conn);
            }
            stbDatModInsCli=null;
        }
        catch (SQLException evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        catch (Exception evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        finally{
            try{
                if (conn != null )
                    conn.close();
                conn=null;
            }catch(Throwable e){     e.printStackTrace();      }
        }
        return blnRes;
    }

    private boolean actualizarRegVen() 
    {
        boolean blnRes=false;
        int intTipSel=0;
        try
        {
            stbDatModInsCli=new StringBuffer();
            getConexion();
            if(conn != null)
            {
                if(!(txtIde.getText().trim().equals(txtIdeAux.getText().trim()))){
                    if(verificaIde(conn)){
                        txtIde.requestFocus();
                        tabGen.setSelectedIndex(0);
                        intTipSel=1;
                    }
                }

                if(intTipSel==0){
                    switch (tabGen.getSelectedIndex() ){
                        case 0: //TAB GENERAL
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabGen(conn);
                            break;
                        case 1: //TAB DIRECCIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabDirPar(conn);
                            break;
                        case 2: //TAB CONTACTOS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabConPar(conn);
                            break;
                        case 3: //TAB OBSERVACIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabObsPar(conn);
                            break;
                        default:
                            break;
                    }

                    if (blnRes) {
                        if(actualizaRegCli(conn)){
                            conn.commit();
                            blnRes=true;
                        }
                        else{
                            conn.rollback();
                        }
                    }
                    else {
                        mostrarMsgAdv("No tiene permisos para modificar informacion de la opcion: " + tabGen.getTitleAt(tabGen.getSelectedIndex()).toUpperCase());
                        conn.rollback();
                    }
                }else{
                    strMsg="El Nº de Identificación <<" + txtIde.getText() + ">> ya existe.\nEscriba otro Nº de Identificación y vuelva a intentarlo. \n¿Desea asociar el cliente que existe a este local.? ";
                    if(!( mostrarMsgYesNo(strMsg) == 1 )) {
                        if(agregarCliLoc(conn)){
                            conn.commit();
                            blnRes=true;
                            mostrarMsgOK("EL CLIENTE HA SIDO AGREGADO A ESTE LOCAL BUSQUELA POR EL IDE");
                        } else
                            conn.rollback();
                    }
                }

                if(blnRes)
                    cargarTabObsPar(conn);
            }
            stbDatModInsCli=null;
        }
        catch (SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        finally{
            try{
                if (conn != null)
                    conn.close();
                conn=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }

    private boolean actualizarRegInv() 
    {
        boolean blnRes = false;
        int intTipSel = 0;
        try 
        {
            stbDatModInsCli=new StringBuffer();
            getConexion();
            if(conn != null)
            {
                if(!(txtIde.getText().trim().equals(txtIdeAux.getText().trim()))){
                    if(verificaIde(conn)){
                        txtIde.requestFocus();
                        tabGen.setSelectedIndex(0);
                        intTipSel=1;
                    }
                }

                if(intTipSel==0){
                    switch (tabGen.getSelectedIndex() ){
                        case 0: //TAB GENERAL
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabGen(conn);
                            break;
                        case 1: //TAB DIRECCIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabDirPar(conn);
                            break;
                        case 2: //TAB CONTACTOS
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabConPar(conn);
                            break;
                        case 3: //TAB OBSERVACIONES
                            if (isOpcionHabilitada())
                                blnRes=actualizarTabObsPar(conn);
                            break;
                        default:
                            break;
                    }

                    if (blnRes)
                        if(actualizaRegCli(conn)){
                            conn.commit();
                            blnRes=true;
                        }else
                            conn.rollback();
                    else {
                        mostrarMsgAdv("No tiene permisos para modificar informacion de la opcion: " + tabGen.getTitleAt(tabGen.getSelectedIndex()).toUpperCase());
                        conn.rollback();
                    }
                }else{
                    strMsg="El Nº de Identificación <<" + txtIde.getText() + ">> ya existe.\nEscriba otro Nº de Identificación y vuelva a intentarlo. \n¿Desea asociar el cliente que existe a este local.? ";
                    if(!( mostrarMsgYesNo(strMsg) == 1 )) {
                        if(agregarCliLoc(conn)){
                            conn.commit();
                            blnRes=true;
                            mostrarMsgOK("EL CLIENTE HA SIDO AGREGADO A ESTE LOCAL BUSQUELA POR EL IDE");
                        } else
                            conn.rollback();
                    }
                }

                if(blnRes)
                    cargarTabObsPar(conn);
            }
            stbDatModInsCli=null;
        }
        catch (SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        finally{
            try{
                if (conn != null)
                    conn.close();
                conn=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }

    private boolean actualizaRegCli(Connection conMod) 
    {
        boolean blnRes = false;
        Statement stmLoc;
        try 
        {
            if (conMod != null) 
            {
                stmLoc = conMod.createStatement();
                System.out.println("actualizaRegCli: "+stbDatModInsCli.toString());
                stmLoc.executeUpdate(stbDatModInsCli.toString());
                stmLoc.close();
                stmLoc = null;

                if (INTCODREGCEN != 0) 
                {
                    Statement stmLoc01 = conRemGlo.createStatement();
                    stmLoc01.executeUpdate(stbDatModInsCli.toString());
                    stmLoc01.close();
                    stmLoc01 = null;
                }
                blnRes = true;
            }
        }
        catch (SQLException evt) {     blnRes = false;   objUti.mostrarMsgErr_F1(jfrThis, evt);     } 
        catch (Exception evt) {   blnRes = false;   objUti.mostrarMsgErr_F1(jfrThis, evt);    }
        return blnRes;
    }

    private boolean actualizarTabGen(Connection conMod) 
    {
        boolean blnRes = true;
        char chrSelEstCiv = 'S';
        char chrSelSex = 'M';
        char chrSelOriIng = 'N';
        char chrEstCli = 'N', chrEstPro = 'N' ;
        String strEst = "";
        String strTelCompleto = "";
        String strSql = "", strFecSis = "";
        try 
        {
            if (conMod != null) 
            {
                if (chkCli.isSelected()) { chrEstCli = 'S';   }
                if (chkPro.isSelected()) { chrEstPro = 'S';   }

                //Estado del registro
                strEst= ( (cboEst.getSelectedIndex()==0)?"A": "I");

                //Sexo
                strSex= ( (cboSex.getSelectedIndex()==0)?"N":(cboSex.getSelectedIndex()==1)?"M": "F");

                //Estado Civil
                strEstCiv= ( (cboEstCiv.getSelectedIndex()==0)?"N":(cboEstCiv.getSelectedIndex()==1)?"S"
                           : (cboEstCiv.getSelectedIndex()==2)?"C":(cboEstCiv.getSelectedIndex()==3)?"D"
                           : (cboEstCiv.getSelectedIndex()==4)?"V":(cboEstCiv.getSelectedIndex()==5)?"U":"");

                //Origen de ingresos
                strOriIng= ( (cboOriIng.getSelectedIndex()==0)?"N":(cboOriIng.getSelectedIndex()==1)?"B"
                           : (cboOriIng.getSelectedIndex()==2)?"V":(cboOriIng.getSelectedIndex()==3)?"I"
                           : (cboOriIng.getSelectedIndex()==4)?"A":(cboOriIng.getSelectedIndex()==5)?"R"
                           : (cboOriIng.getSelectedIndex()==6)?"H":(cboOriIng.getSelectedIndex()==7)?"M":"");

                //Tipo de Identidad
                chrIde= ( (cboTipIde.getSelectedIndex()==0)?'R':(cboTipIde.getSelectedIndex()==1)?'C'
                        : (cboTipIde.getSelectedIndex()==2)?'P':(cboTipIde.getSelectedIndex()==3)?'F'
                        : (cboTipIde.getSelectedIndex()==4)?'E':' ');         

                //Estado Civil: Char
                chrSelEstCiv= ( (cboEstCiv.getSelectedIndex()==0)?'N':(cboEstCiv.getSelectedIndex()==1)?'S'
                              : (cboEstCiv.getSelectedIndex()==2)?'C':(cboEstCiv.getSelectedIndex()==3)?'D'
                              : (cboEstCiv.getSelectedIndex()==4)?'V':(cboEstCiv.getSelectedIndex()==5)?'U':' ');                    

                //Sexo: Char
                chrSelSex= ( (cboSex.getSelectedIndex()==0)?'N':(cboSex.getSelectedIndex()==1)?'M'
                           : (cboSex.getSelectedIndex()==2)?'F':'N');                        

                //Origen de ingresos
                chrSelOriIng= ( (cboOriIng.getSelectedIndex()==0)?'N':(cboOriIng.getSelectedIndex()==1)?'B'
                           : (cboOriIng.getSelectedIndex()==2)?'V':(cboOriIng.getSelectedIndex()==3)?'I'
                           : (cboOriIng.getSelectedIndex()==4)?'A':(cboOriIng.getSelectedIndex()==5)?'R'
                           : (cboOriIng.getSelectedIndex()==6)?'H':(cboOriIng.getSelectedIndex()==7)?'M':' ');                        


                strEstRegCli = _getObtenerEstRegCli(conMod, txtCodCli.getText());

                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                strTelCompleto = txtTelCli1.getText() + " " + txtTelCli2.getText() + " " + txtTelCli3.getText();

                //Insertar Histórico
                //if (blnHayCamTabGen)  //Se comenta para que siempre actualice el histórico
                //{
                    strSql =  " INSERT INTO tbh_cli(  co_emp, co_cli, co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir, "
                            + " tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu, "
                            + " co_par,co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag, "
                            + " nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing, "
                            + " fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti, "
                            + " st_regrep, st_cieretpen, ne_diagrachqfec, tx_tel1, tx_tel2, tx_tel3, "
                            + " tx_repleg,  tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro, "
                            + " fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon,  tx_obsven, "
                            + " tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat, fe_ultactdat, st_sex, st_estciv, st_oriing,  "
                            + " tx_obsinfburcre,  st_ivacom, st_ivaven, fe_his, co_usrhis, st_peringnomclicotven )  "
                            + " "
                            + " SELECT co_emp, co_cli, "
                            + "  ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis  from tbh_cli where co_emp=" + objParSis.getCodigoEmpresa() + "),"
                            + " st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir, "
                            + " tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu, "
                            + " co_par,co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag,  "
                            + " nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing,  "
                            + " fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti,  "
                            + " st_regrep, st_cieretpen, ne_diagrachqfec, tx_tel1, tx_tel2, tx_tel3,  "
                            + " tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro,  "
                            + " fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven,  "
                            + " tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat, fe_ultactdat, st_sex, st_estciv, st_oriing, "
                            + " tx_obsinfburcre, st_ivacom, st_ivaven, '" + strFecSis + "' , " + objParSis.getCodigoUsuario() + " , st_peringnomclicotven "
                            + " FROM tbm_cli WHERE  co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    //System.out.println("actualizarTabGen: " + strSql);
                    stbDatModInsCli.append(" ; " + strSql);
                //}

                if (blnHayCamTblCon) 
                {
                    strSql =  " INSERT INTO tbh_concli(co_emp, co_loc, co_cli, ne_mod, co_reg, co_his, tx_nom, tx_car "
                            + "                      , tx_tel1, tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1, st_reg"
                            + "                      , fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep, fe_his, co_usrhis ) "
                            + " "
                            + " SELECT co_emp, co_loc, co_cli, ne_mod, co_reg"
                            + "      , ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis  from tbh_concli where co_emp=" + objParSis.getCodigoEmpresa() + " ) "
                            + "      , tx_nom, tx_car, tx_tel1, tx_tel2, tx_tel3, tx_corele1, tx_corele2, tx_obs1, st_reg  "
                            + "      , fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep , '" + strFecSis + "' , " + objParSis.getCodigoUsuario() + " "
                            + " FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    stbDatModInsCli.append(" ; " + strSql);
                }

                if (blnHayCamTblDir) 
                {
                    strSql =  " INSERT INTO tbh_dircli(co_emp, co_cli, ne_mod, co_reg, co_his, tx_dir, tx_refubi, tx_tel1, "
                            + "                      , tx_tel2, tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring "
                            + "                      , co_usrmod, st_regrep, fe_his, co_usrhis  ) "
                            + " "
                            + " SELECT co_emp, co_cli, ne_mod, co_reg "
                            + "     , ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis  from tbh_dircli where co_emp=" + objParSis.getCodigoEmpresa() + " )  "
                            + "     , tx_dir, tx_refubi, tx_tel1, tx_tel2 "
                            + "     , tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod "
                            + "     , st_regrep, '" + strFecSis + "' , " + objParSis.getCodigoUsuario() + " "
                            + " FROM tbm_dircli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    stbDatModInsCli.append(" ; " + strSql);
                }

                if (blnHayCamTblObs) {
                    strSql =  " INSERT INTO tbh_obscli( co_emp, co_loc, co_cli, ne_mod, co_reg, co_his, fe_ing, tx_obs1, st_regrep, fe_his, co_usrhis, fe_ultmod, co_usring, co_usrmod, tx_coming, tx_comultmod )  "
                            + " "
                            + " SELECT co_emp, co_loc, co_cli, ne_mod, co_reg "
                            + "      , ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis  from tbh_obscli where co_emp=" + objParSis.getCodigoEmpresa() + " ) "
                            + "      , fe_ing, tx_obs1, st_regrep, '" + strFecSis + "' , " + objParSis.getCodigoUsuario() + " , fe_ultmod, co_usring, co_usrmod, tx_coming, tx_comultmod "
                            + " FROM tbm_obscli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    stbDatModInsCli.append(" ; " + strSql);
                }

                if (blnHayCamTblBen)
                {
                    strSql =  " INSERT INTO tbh_benchq( co_emp, co_cli, co_reg, co_his, tx_benchq, st_reg, st_regrep, fe_his, co_usrhis ) "
                            + " "
                            + " SELECT co_emp, co_cli, co_reg "
                            + "     , ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis  from tbh_benchq where co_emp=" + objParSis.getCodigoEmpresa() + " ) "
                            + "     , tx_benchq, st_reg, st_regrep, '" + strFecSis + "' , " + objParSis.getCodigoUsuario() + " "
                            + " FROM tbm_benchq WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    stbDatModInsCli.append(" ; " + strSql);
                }

                if (blnHayCamTblAcc) 
                {
                    strSql =  " INSERT INTO tbh_accempcli(co_emp, co_cli, co_reg, tx_nom, co_his, fe_his, co_usrhis) "
                            + " "
                            + " SELECT co_emp, co_cli, co_reg, tx_nom  "
                            + "     , ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis from tbh_accempcli where co_emp=" + objParSis.getCodigoEmpresa() + " ) "
                            + "     , '" + strFecSis + "' , " + objParSis.getCodigoUsuario() + " "
                            + " FROM tbm_accempcli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    stbDatModInsCli.append(" ; " + strSql);
                }

                //Actualizar datos del cliente
                strSql =" UPDATE tbm_cli SET "
                       +"   st_cli='" + chrEstCli + "' "
                       +" , st_prv='" + chrEstPro + "' "
                       +" , tx_tipIde='" + chrIde + "' "
                       +" , tx_ide=" + ((strAux = txtIde.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_nom=" + ((strAux = (objUti.remplazarEspacios(txtNomCli.getText())).replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_dir=" + ((strAux = txtDirCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_refubi=" + ((strAux = txtRefDirCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_tel='" + strTelCompleto + "'"  
                       +" , tx_fax=" + ((strAux = txtFaxCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_cas=" + ((strAux = txtCasCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_dirweb=" + ((strAux = txtWebCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , tx_corele=" + ((strAux = txtEmaCli.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , co_tipper=" + (txtCodTipPer.getText().equals("") ? null : txtCodTipPer.getText()) + ""
                       +" , co_ciu=" + (txtCodCiu.getText().trim().equals("") ? null : txtCodCiu.getText()) + ""
                       +" , co_par=" + (txtCodPar.getText().trim().equals("") ? null : txtCodPar.getText()) + ""
                       +" , co_zon=" + (txtCodZon.getText().trim().equals("") ? null : txtCodZon.getText()) + ""
                       +" , co_ven=" + (txtCodVen.getText().trim().equals("") ? null : txtCodVen.getText()) + ""
                       +" , co_usrmod=" + objParSis.getCodigoUsuario() + ""
                       +" , fe_ultmod='" + strFecSis + "'"
                       +" , tx_tel1='" + txtTelCli1.getText() + "'"
                       +" , tx_tel2='" + txtTelCli2.getText() + "'"
                       +" , tx_tel3='" + txtTelCli3.getText() + "'"
                       +" , tx_repleg=" + ((strAux = txtRepleg.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ""
                       +" , co_grp=" + (txtCodGrp.getText().trim().equals("") ? null : txtCodGrp.getText()) + " "
                       +" , st_reg='" + (strEstRegCli.equals("T") ? "N" : (strEst.equals("I")|| strEst.equals("A") ? strEst : strEstRegCli)) + "'"
                       +" , st_sex='" + chrSelSex + "'"
                       +" , st_estciv='" + chrSelEstCiv + "'"
                       +" , st_oriing= '" + chrSelOriIng + "'"
                       +" , tx_obsven=" + objUti.codificar(txtArObsVen.getText()) + ""
                       +" , tx_obsinv=" + objUti.codificar(txtArObsInv.getText()) + ""
                       +" , tx_obscxc=" + objUti.codificar(txtArObscxc.getText()) + ""
                       +" , tx_obsinfburcre=" + objUti.codificar(txtArObscxcBurCre.getText()) + ""
                       +" , tx_obscxp=" + objUti.codificar(txtArObscxp.getText()) + ""
                       +" , tx_obs1=" + objUti.codificar(txtArObsObs1.getText()) + ""
                       +" , tx_obs2=" + objUti.codificar(txtArObsObs2.getText()) + ""
                       +" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                stbDatModInsCli.append(" ; " + strSql);

                strEstRegCli = (strEstRegCli.equals("T") ? "N" : (strEst.equals("I") ? strEst : strEstRegCli));
                txtTelCli.setText(strTelCompleto);
                txtIdeAux.setText(txtIde.getText());
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }  
    
    private String _getObtenerEstRegCli(Connection conExt, String strCodCli) 
    {
        Statement stmLoc;
        ResultSet rstLoc;
        String strEst = "N";
        try
        {
            if (conExt != null)
            {
                stmLoc = conExt.createStatement();
                strSQL ="";
                strSQL+=" SELECT st_reg FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + "  AND co_cli=" + strCodCli + " ";
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    strEst = rstLoc.getString("st_reg");
                }
                rstLoc.close();
                rstLoc = null;

                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (SQLException evt) {
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        catch (Exception evt) {
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return strEst;
    }
    
    private boolean actualizarTabCre(Connection conMod) 
    {
        boolean blnRes = true;
        char chrCieCre = 'N';
        String strFecSis = "";
        try 
        {
            if (chkCieCre.isSelected())  {  chrCieCre = 'S';         } 
            else {      chrCieCre = 'N';         }

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            strSQL = " UPDATE tbm_cli SET st_cieRetPen = '" + chrCieCre + "', ne_diamesmaxemifacven=" + cboDiaMesMaxEmiFac.getSelectedIndex() + ""
                   + " , ne_diasememifacven=" + cboDiaSemEmiFac.getSelectedIndex() + "  "
                   + " ,co_usrmod=" + objParSis.getCodigoUsuario() + ",fe_ultmod='" + strFecSis + "' "
                   + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
            stbDatModInsCli.append(" ; " + strSQL);
        }
        catch (Exception evt) {
            blnRes = true;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabDir(Connection conMod)
    {
        boolean blnRes = true;
        int intTipDir = 0;
        String strFecSis = "";
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        try 
        {
            if (conMod != null) {
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                for(int i=0; i<tblDir.getRowCount(); i++)
                {
                   if(tblDir.getValueAt(i,INT_TBL_DIR_DIR)!=null)
                   {
                      if (i>0)
                        stbins.append(" UNION ALL ");
                        stbins.append("SELECT "+objParSis.getCodigoEmpresa()+","+ txtCodCli.getText()+", " +
                                      " "+(tblDir.getValueAt(i,INT_TBL_DIR_MODD)==null?intCodMod:Integer.parseInt(tblDir.getValueAt(i,INT_TBL_DIR_MODD).toString()))+", " +
                                      " "+(i+1)+","+
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_DIR)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_DIR).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_REF)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_REF).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL1)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL1).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL2)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL2).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL3)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL3).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_OBS2)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_OBS2).toString())+"'," +
                                      "'A',date('"+strFecSis+"'),date('"+strFecSis+"'),"+objParSis.getCodigoUsuario()+","+objParSis.getCodigoUsuario()+",'I'");
                       intTipDir=1;
                   }
                }
               if(intTipDir==1)
               {
                    strSQL = " DELETE FROM tbm_dircli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli =" + txtCodCli.getText() + ""
                           + " ; INSERT INTO tbm_dircli(co_emp, co_cli, ne_mod, co_reg, tx_dir, tx_refubi, tx_tel1, tx_tel2,tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep) "
                           + " " + stbins.toString() + " ";
                    stbDatModInsCli.append(" ; " + strSQL);
                }
                stbins = null;
            }
        }
        catch (Exception evt) {
            blnRes = true;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabCon(Connection conMod) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        String strFecSis = "";
        int intTipCon = 0;
        try 
        {
            if (conMod != null) 
            {
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                for (int i = 0; i < tblCon.getRowCount(); i++) 
                {
                    if ((tblCon.getValueAt(i,INT_TBL_CON_NOM)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_NOM).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_CAR)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_CAR).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL1)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL1).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL2)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL2).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL3)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL3).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_MA1)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_MA1).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_MA2)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_MA2).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_OBS)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_OBS).toString().equals("")?false:true)) )
                    {

                        if (intTipCon>0) {  stbins.append(" ; ");  }

                        stbins.append("INSERT INTO tbm_concli(co_emp, co_loc, co_cli,ne_mod,co_reg,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3," +
                                      " tx_corele1,tx_corele2,st_reg,tx_obs1,co_usring,co_usrmod,fe_ing,fe_ultmod,st_regrep) " +
                                      " SELECT "+objParSis.getCodigoEmpresa()+"" +
                                      ","+(tblCon.getValueAt(i,INT_TBL_CON_LOCC)==null?objParSis.getCodigoLocal():Integer.parseInt(tblCon.getValueAt(i,INT_TBL_CON_LOCC).toString()))+" "+
                                      ","+txtCodCli.getText()+", "+
                                      " "+(tblCon.getValueAt(i,INT_TBL_CON_MODC)==null?intCodMod:Integer.parseInt(tblCon.getValueAt(i,INT_TBL_CON_MODC).toString()))+" "+
                                      ","+(i+1)+","+
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_NOM)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_NOM).toString())+"'," +
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_CAR)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_CAR).toString())+"'," +
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL1)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL1).toString())+"'," +
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL2)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL2).toString())+"'," +
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL3)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL3).toString())+"'," +
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_MA1)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_MA1).toString())+"'," +
                                      "'"+((tblCon.getValueAt(i,INT_TBL_CON_MA2)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_MA2).toString())+"'," +
                                      "'A','"+((tblCon.getValueAt(i,INT_TBL_CON_OBS)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_OBS).toString())+"'," +
                                      ""+objParSis.getCodigoUsuario()+","+objParSis.getCodigoUsuario()+",'"+strFecSis+"','"+strFecSis+"'"+
                                      ",'I' ");
                        intTipCon=1;
                    }
                }
                if (intTipCon == 1) 
                {
                    strSQL = " DELETE FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli =" + txtCodCli.getText() + " ; "
                            + " " + stbins.toString() + " ";
                    stbDatModInsCli.append(" ; " + strSQL);
                    stbins = null;
                }
            }
        }
        catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
        return blnRes;
    }

    private boolean actualizarTabDirPar(Connection conMod) 
    {
        boolean blnRes = true;
        int intTipDir = 0;
        String strFecSis = "";
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        try 
        {
            if (conMod != null) {
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                for (int i = 0; i < tblDir.getRowCount(); i++) {
                    if (tblDir.getValueAt(i, INT_TBL_DIR_DIR) != null) 
                    {
                        if (i>0) {  stbins.append(" UNION ALL ");  }
                        
                        stbins.append(" SELECT "+objParSis.getCodigoEmpresa()+","+ txtCodCli.getText()+", " +
                                      " "+(tblDir.getValueAt(i,INT_TBL_DIR_MODD)==null?intCodMod:Integer.parseInt(tblDir.getValueAt(i,INT_TBL_DIR_MODD).toString()))+", " +
                                      " "+(i+1)+","+
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_DIR)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_DIR).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_REF)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_REF).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL1)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL1).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL2)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL2).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_TEL3)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_TEL3).toString())+"'," +
                                      "'"+((tblDir.getValueAt(i,INT_TBL_DIR_OBS2)==null)?"":tblDir.getValueAt(i,INT_TBL_DIR_OBS2).toString())+"'," +
                                      "'A',date('"+strFecSis+"'),date('"+strFecSis+"'),"+objParSis.getCodigoUsuario()+","+objParSis.getCodigoUsuario()+",'I'");
                       intTipDir=1;
                    }
                }
               if(intTipDir==1)
               {
                   strSQL =" DELETE FROM tbm_dircli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli =" + txtCodCli.getText() + " and ne_mod=" + intCodMod + " "
                          +" ; INSERT INTO tbm_dircli(co_emp, co_cli, ne_mod, co_reg, tx_dir, tx_refubi, tx_tel1, tx_tel2, tx_tel3, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep) "
                          +" " + stbins.toString() + " ";
                   stbDatModInsCli.append(" ; " + strSQL);
                }
                stbins = null;
            }
        }
        catch (Exception evt) {
            blnRes = true;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabConPar(Connection conMod) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        String strFecSis = "";
        int intTipCon = 0;
        try 
        {
            if (conMod != null) 
            {
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                for (int i = 0; i < tblCon.getRowCount(); i++) 
                {
                    if( (tblCon.getValueAt(i,INT_TBL_CON_NOM)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_NOM).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_CAR)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_CAR).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL1)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL1).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL2)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL2).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_TEL3)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_TEL3).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_MA1)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_MA1).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_MA2)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_MA2).toString().equals("")?false:true)) ||
                        (tblCon.getValueAt(i,INT_TBL_CON_OBS)==null?false:( tblCon.getValueAt(i,INT_TBL_CON_OBS).toString().equals("")?false:true)) )
                    {

                    if (intTipCon>0)
                            stbins.append(" ; ");
                            stbins.append(" INSERT INTO tbm_concli(co_emp, co_loc, co_cli,ne_mod,co_reg,tx_nom,tx_car,tx_tel1,tx_tel2,tx_tel3 " +
                                          "                      , tx_corele1,tx_corele2,st_reg,tx_obs1,co_usring,co_usrmod,fe_ing,fe_ultmod,st_regrep) " +
                                          "  SELECT "+objParSis.getCodigoEmpresa()+" " +
                                          " ,"+(tblCon.getValueAt(i,INT_TBL_CON_LOCC)==null?objParSis.getCodigoLocal():Integer.parseInt(tblCon.getValueAt(i,INT_TBL_CON_LOCC).toString()))+" "+
                                          " ,"+txtCodCli.getText()+" "+
                                          " ,"+(tblCon.getValueAt(i,INT_TBL_CON_MODC)==null?intCodMod:Integer.parseInt(tblCon.getValueAt(i,INT_TBL_CON_MODC).toString()))+" "+
                                          " ,"+(i+1)+""+
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_NOM)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_NOM).toString())+"'" +
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_CAR)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_CAR).toString())+"'" +
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL1)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL1).toString())+"'" +
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL2)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL2).toString())+"'" +
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_TEL3)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_TEL3).toString())+"'" +
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_MA1)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_MA1).toString())+"'" +
                                          " ,'"+((tblCon.getValueAt(i,INT_TBL_CON_MA2)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_MA2).toString())+"'" +
                                          " ,'A','"+((tblCon.getValueAt(i,INT_TBL_CON_OBS)==null)?"":tblCon.getValueAt(i,INT_TBL_CON_OBS).toString())+"'" +
                                          " ,"+objParSis.getCodigoUsuario()+","+objParSis.getCodigoUsuario()+",'"+strFecSis+"','"+strFecSis+"'"+
                                          " ,'I' ");
                          intTipCon=1;
                    }
                }
                if (intTipCon == 1) 
                {
                    strSQL = " DELETE FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli =" + txtCodCli.getText() + " AND ne_mod=" + intCodMod + " ;  " + stbins.toString() + " ";
                    if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) 
                    {
                        strSQL = " DELETE FROM tbm_concli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_cli =" + txtCodCli.getText() + " AND ne_mod=" + intCodMod + " ;  " + stbins.toString();
                    }
                    stbDatModInsCli.append(" ; " + strSQL);
                    stbins = null;
                }
            }
        }
        catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
        return blnRes;
    }

    private boolean actualizarTabBen(Connection conMod) 
    {
        boolean blnRes = true, blnExiNomBen;
        String strNomBenTbl, strNomBen_BenChq;
        Statement stmLoc;
        int intSec = 1, intCodReg_BenChq;
        try 
        {
            if (conMod != null) {
                stm = conMod.createStatement();
                stmLoc = conMod.createStatement();
                
                for (int x = 0; x < tblBenChq.getRowCount(); x++)
                {   if (tblBenChq.getValueAt(x, INT_TBL_BEN_NOM) != null)
                    {   String strest = "A";
                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_PRE) != null) 
                        {   if (tblBenChq.getValueAt(x, INT_TBL_BEN_PRE).toString().equalsIgnoreCase("true")) 
                            {
                                strest = "P";
                            }
                        }
                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_ACT) != null) 
                        {   if (tblBenChq.getValueAt(x, INT_TBL_BEN_ACT).toString().equalsIgnoreCase("true")) 
                            {
                                strest = "A";
                            }
                        }
                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_INA) != null) 
                        {   if (tblBenChq.getValueAt(x, INT_TBL_BEN_INA).toString().equalsIgnoreCase("true")) 
                            {
                                strest = "I";
                            }
                        }

                        if (tblBenChq.getValueAt(x, INT_TBL_BEN_LIN) != null) 
                        {   if (tblBenChq.getValueAt(x, INT_TBL_BEN_LIN).toString().equalsIgnoreCase("M")) 
                            {   strSQL = " UPDATE tbm_benchq SET tx_benchq='" + tblBenChq.getValueAt(x, INT_TBL_BEN_NOM).toString() + "' "
                                       + " ,st_reg='" + strest + "' , st_regrep='M'  WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText() + " and co_reg=" + tblBenChq.getValueAt(x, INT_TBL_BEN_COD).toString();
                                stbDatModInsCli.append(" ; " + strSQL);
                            }

                            if (tblBenChq.getValueAt(x, INT_TBL_BEN_LIN).toString().equalsIgnoreCase("I")) 
                            {   strSQL = " SELECT max(co_reg)+1  FROM tbm_benchq  WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText();
                                rst = stm.executeQuery(strSQL);
                                if (rst.next()) 
                                {   if (rst.getString(1) != null) 
                                    {
                                        intSec = rst.getInt(1);
                                    }
                                }
                                rst.close();

                                strSQL =" INSERT INTO tbm_benchq(co_emp,co_cli,co_reg,tx_benchq,st_reg, st_regrep) ";
                                strSQL+=" VALUES (" + objParSis.getCodigoEmpresa() + "," + txtCodCli.getText() + "," + intSec + ",'";
                                strSQL+= (tblBenChq.getValueAt(x, INT_TBL_BEN_NOM) == null) ? " " : tblBenChq.getValueAt(x, INT_TBL_BEN_NOM).toString() + "'";
                                strSQL+=" ,'" + strest + "', 'I' )";
                                stbDatModInsCli.append(" ; " + strSQL);
                            }
                        }
                    } //if (tblBenChq.getValueAt(x, INT_TBL_BEN_NOM) != null)
                } //for (int x = 0; x < tblBenChq.getRowCount(); x++)

                //--- Inicio bloque comentado el 24/Oct/2018 ---
                /*
                strSQL =" SELECT co_emp, co_cli, co_reg, tx_benchq, st_reg ";
                strSQL+=" FROM tbm_benChq ";
                strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_cli = " + txtCodCli.getText() + " ";
                strSQL+=" ORDER BY co_reg";
                rst = stm.executeQuery(strSQL);

                while (rst.next())
                {  
                    intCodReg_BenChq = rst.getInt("co_reg");
                    strNomBen_BenChq = rst.getString("tx_benchq");
                    blnExiNomBen = false;

                    for (int x = 0; x < tblBenChq.getRowCount(); x++)
                    {  strNomBenTbl = (tblBenChq.getValueAt(x, INT_TBL_BEN_NOM) == null)? "" :tblBenChq.getValueAt(x, INT_TBL_BEN_NOM).toString();
                       if (!strNomBenTbl.equals(""))
                       {  if (strNomBen_BenChq.equals(strNomBenTbl))
                          {  //El nom_beneficiario del JTable si fue encontrado en tbm_benchq.tx_benchq.
                             blnExiNomBen = true;
                             break;
                          }
                       }
                    }

                    if (blnExiNomBen == false)
                    {  //Como no se encontro el nom_beneficiario del JTable en tbm_benchq.tx_benchq, se debe realizar un Delete en dicha tabla.
                        strSQL =" DELETE FROM tbm_benChq ";
                        strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_cli = " + txtCodCli.getText();
                        strSQL+=" AND co_reg = " + intCodReg_BenChq;
                        stmLoc.executeUpdate(strSQL);
                    }
                }
                */
                //--- Fin bloque comentado el 24/Oct/2018 ------
                stm.close();
                stm = null;
                stmLoc.close();
                stmLoc = null;
            }
        }
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabObs(Connection conMod) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        int intTipCon = 0;
        try
        {
            if (conMod != null) 
            {
                for (int i = 0; i < tblObs.getRowCount(); i++) 
                {
                    if (tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) != null) {
                        if (tblObs.getValueAt(i, INT_TBL_CON_OBSCOD) != null) {
                            strSQL = " UPDATE tbm_obscli SET tx_obs1='" + ((tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) == null) ? "" : tblObs.getValueAt(i, INT_TBL_CON_OBSOBS).toString()) + "' "
                                   + " ,st_regrep='M', co_usrmod=" + objParSis.getCodigoUsuario() + " "
                                   + " ,fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", tx_comultmod='" + objParSis.getNombreComputadoraConDirIP() + "' "
                                   + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "  "
                                   + " AND co_loc=" + tblObs.getValueAt(i, INT_TBL_CON_LOC).toString() + "  "
                                   + " AND co_reg=" + tblObs.getValueAt(i, INT_TBL_CON_OBSCOD).toString() + "  "
                                   + " AND ne_mod=" + tblObs.getValueAt(i, INT_TBL_CON_OBSMOD).toString() + "  "
                                   + " AND co_cli=" + txtCodCli.getText();
                            stbDatModInsCli.append(" ; " + strSQL);
                        } 
                        else 
                        {
                            if (intTipCon > 0) {   stbins.append(" ; ");   }
                            stbins.append(" INSERT INTO tbm_obscli(co_emp, co_loc,  co_cli, ne_mod, co_reg, tx_obs1, co_usring, fe_ing, tx_coming , st_regrep ) "
                                        + " SELECT " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodCli.getText() + ", " + intCodMod + " "
                                        + " ,( select case when max(co_reg)+1 is null then 1 else max(co_reg)+1 end as coreg  from tbm_obscli where co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText() + " )  "
                                        + " ,'" + ((tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) == null) ? "" : tblObs.getValueAt(i, INT_TBL_CON_OBSOBS).toString()) + "' "
                                        + " ," + objParSis.getCodigoUsuario() + ", " + objParSis.getFuncionFechaHoraBaseDatos() + " , '" + objParSis.getNombreComputadoraConDirIP() + "', 'M'");
                            intTipCon = 1;
                        }
                    }
                }
                // Elimina registro de la tabla de observacion
                java.util.ArrayList arlAux = objTblModObs.getDataSavedBeforeRemoveRow();
                if (arlAux != null) 
                {
                    for (int i = 0; i < arlAux.size(); i++) 
                    {
                        int intCodLoc = objUti.getIntValueAt(arlAux, i, INT_ARR_OBSLOC);
                        int intCodreg = objUti.getIntValueAt(arlAux, i, INT_ARR_OBSREG);
                        int intCodMod = objUti.getIntValueAt(arlAux, i, INT_ARR_OBSMOD);
                        if (intTipCon > 0) {   stbins.append(" ; ");     }
                        stbins.append(" DELETE FROM tbm_obscli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + intCodLoc + " "
                                    + " AND co_reg=" + intCodreg + " AND ne_mod=" + intCodMod + " AND co_cli=" + txtCodCli.getText() + " ");
                        intTipCon = 1;
                    }
                }

                if (intTipCon == 1)    {
                    stbDatModInsCli.append(" ; " + stbins.toString());
                    stbins = null;
                }
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabObsPar(Connection conMod) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        int intTipCon = 0;
        try 
        {
            if (conMod != null) 
            {
                for (int i = 0; i < tblObs.getRowCount(); i++) 
                {
                    if (tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) != null) {
                        if (tblObs.getValueAt(i, INT_TBL_CON_OBSCOD) != null) {
                            strSQL = "UPDATE tbm_obscli SET tx_obs1='" + ((tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) == null) ? "" : tblObs.getValueAt(i, INT_TBL_CON_OBSOBS).toString()) + "' "
                                   + " ,st_regrep='M', co_usrmod=" + objParSis.getCodigoUsuario() + " "
                                   + " ,fe_ultmod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", tx_comultmod='" + objParSis.getNombreComputadoraConDirIP() + "' "
                                   + " WHERE co_emp=" + objParSis.getCodigoEmpresa() +""
                                   + " AND co_loc=" + tblObs.getValueAt(i, INT_TBL_CON_LOC).toString() + ""
                                   + " AND co_reg=" + tblObs.getValueAt(i, INT_TBL_CON_OBSCOD).toString() + ""
                                   + " AND ne_mod=" + tblObs.getValueAt(i, INT_TBL_CON_OBSMOD).toString() + "" 
                                   + " AND co_cli=" + txtCodCli.getText();
                            stbDatModInsCli.append(" ; " + strSQL);
                        } else {
                            if (intTipCon > 0) {
                                stbins.append(" ; ");
                            }
                            stbins.append(" INSERT INTO tbm_obscli(co_emp, co_loc,  co_cli, ne_mod, co_reg, tx_obs1, co_usring, fe_ing, tx_coming , st_regrep ) "
                                        + " SELECT " + objParSis.getCodigoEmpresa() + ", " + objParSis.getCodigoLocal() + ", " + txtCodCli.getText() + ", " + intCodMod + " "
                                        + " ,( select case when max(co_reg)+1 is null then 1 else max(co_reg)+1 end as coreg  from tbm_obscli where co_emp=" + objParSis.getCodigoEmpresa() + " and co_cli=" + txtCodCli.getText() + " )  "
                                        + " ,'" + ((tblObs.getValueAt(i, INT_TBL_CON_OBSOBS) == null) ? "" : tblObs.getValueAt(i, INT_TBL_CON_OBSOBS).toString()) + "' "
                                        + " ," + objParSis.getCodigoUsuario() + ", " + objParSis.getFuncionFechaHoraBaseDatos() + " , '" + objParSis.getNombreComputadoraConDirIP() + "', 'M'");
                            intTipCon = 1;
                        }
                    }
                }
                // Elimina registro de la tabla de observacion
                java.util.ArrayList arlAux = objTblModObs.getDataSavedBeforeRemoveRow();
                if (arlAux != null)
                {
                    for (int i = 0; i < arlAux.size(); i++) 
                    {
                        int intCodLoc = objUti.getIntValueAt(arlAux, i, INT_ARR_OBSLOC);
                        int intCodreg = objUti.getIntValueAt(arlAux, i, INT_ARR_OBSREG);
                        int intCodMod = objUti.getIntValueAt(arlAux, i, INT_ARR_OBSMOD);

                        if (intTipCon > 0) {
                            stbins.append(" ; ");
                        }
                        stbins.append("DELETE FROM tbm_obscli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND  co_loc=" + intCodLoc + " "
                                + "  AND  co_reg=" + intCodreg + "  AND ne_mod=" + intCodMod + " AND  co_cli=" + txtCodCli.getText() + " ");
                        intTipCon = 1;
                    }
                }

                if (intTipCon == 1) {
                    stbDatModInsCli.append(" ; " + stbins.toString());
                    stbins = null;
                }

                strSQL = " UPDATE tbm_cli SET "
                       + " tx_obsven=" + objUti.codificar(txtArObsVen.getText()) + " "
                       + " ,tx_obsinv=" + objUti.codificar(txtArObsInv.getText()) + " "
                       + " ,tx_obscxc=" + objUti.codificar(txtArObscxc.getText()) + " "
                       + " ,tx_obsinfburcre=" + objUti.codificar(txtArObscxcBurCre.getText()) + " "
                       + " ,tx_obscxp=" + objUti.codificar(txtArObscxp.getText()) + " "
                       + " ,tx_obs1=" + objUti.codificar(txtArObsObs1.getText()) + " "
                       + " ,tx_obs2=" + objUti.codificar(txtArObsObs2.getText()) + " "
                       + " WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                stbDatModInsCli.append(" ; " + strSQL);
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabLoc(Connection conMod) 
    {
        boolean blnRes = true;
        boolean blnChkAct = false;
        
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        int intTipDir = 0;
        String  strSqlIns="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            if (conMod != null) {
                stmLoc = conMod.createStatement();
                for (int i = 0; i < tblCliLoc.getRowCount(); i++){
                    if (tblCliLoc.getValueAt(i, INT_TBL_LOC_CHK).toString().equals("true")) {
                        strSql="";
                        strSql+=" SELECT * FROM tbr_cliloc WHERE CO_EMP=" + objParSis.getCodigoEmpresa() + " AND CO_LOC="+ tblCliLoc.getValueAt(i, INT_TBL_LOC_COD).toString() +" AND CO_CLI=" + txtCodCli.getText() + "; ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if(rstLoc.next()){

                        }
                        else{
                            strSqlIns+=" INSERT INTO tbr_cliloc (CO_EMP, CO_LOC, CO_CLI, CO_VEN, ST_REGREP, FE_ULTMOD,CO_USRMOD) ";
                            strSqlIns+=" VALUES (" + objParSis.getCodigoEmpresa() + ","+ tblCliLoc.getValueAt(i, INT_TBL_LOC_COD).toString() +"," + txtCodCli.getText() + ",9,'I', ";
                            strSqlIns+="  CURRENT_TIMESTAMP, "+objParSis.getCodigoUsuario()+" ); ";
                        } 
                        blnChkAct=true;
                    } 
                }
                if (blnChkAct) {
                    
                    stbDatModInsCli.append(" ; " + strSqlIns);
                }
                
                stbins = null;
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabPro(Connection conMod) 
    {
        boolean blnRes = true;
        StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
        int intTipPro = 0;
        try 
        {
            if (conMod != null) 
            {
                String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                strSQL = " UPDATE tbm_cli SET "
                        + " tx_idepro='" + txtCedPro.getText() + "', "
                        + " tx_nompro='" + txtNomPro.getText() + "',"
                        + " tx_dirpro='" + txtDomPro.getText() + "', "
                        + " tx_telpro='" + txtTelPro.getText() + "', "
                        + " tx_nacpro='" + txtNacPro.getText() + "', "
                        + " fe_conemp='" + strFecha + "', "
                        + " tx_tipactemp='" + txtActPro.getText() + "' ,"
                        + " tx_obspro='" + txaObsPro.getText() + "'"
                        + " WHERE CO_EMP=" + objParSis.getCodigoEmpresa() + " AND CO_CLI=" + txtCodCli.getText();
                stbDatModInsCli.append(" ; " + strSQL);

                for (int i = 0; i < tblAcc.getRowCount(); i++) 
                {
                    if (tblAcc.getValueAt(i, INT_TBL_ACCNOM) != null) {
                        if (i > 0) {   stbins.append(" ; ");  }
                        stbins.append(" INSERT INTO tbm_accempcli(co_emp, co_cli, co_reg, tx_nom ) "
                                    + " SELECT " + objParSis.getCodigoEmpresa() + ",  " + txtCodCli.getText() + "," + (i + 1) + ", "
                                    + "'" + ((tblAcc.getValueAt(i, INT_TBL_ACCNOM) == null) ? "" : tblAcc.getValueAt(i, INT_TBL_ACCNOM).toString()) + "'");
                        intTipPro = 1;
                    }
                }
                if (intTipPro == 1) 
                {
                    strSQL = " DELETE FROM tbm_accempcli WHERE CO_EMP=" + objParSis.getCodigoEmpresa() + " AND CO_CLI=" + txtCodCli.getText() + " ; " + stbins.toString();
                    stbDatModInsCli.append(" ; " + strSQL);
                    stbins = null;
                }
                stbins = null;
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean actualizarTabVen(Connection conMod, int intCodCli) 
    {
        boolean blnRes = true;
        try
        {
            if (conMod != null) 
            {
                strSQL =" UPDATE tbm_cli SET nd_maxdes=" + spiMaxPorDes.getModel().getValue().toString() + " ";
                strSQL+=" ,ne_diagra=" + spiDiaGraDocVen.getModel().getValue().toString() + " ";
                strSQL+=" ,ne_diagrachqfec=" + spiDiaGraSinSop.getModel().getValue().toString() + " ";
                strSQL+=" ,nd_maruti=" + spiMarUti.getModel().getValue().toString() + " ";
                strSQL+=" ,ne_nummaxvencon=" + spiNumMaxVenCon.getModel().getValue().toString() + " ";
                strSQL+=" ,st_peringnomclicotven='" + (chkIngNomCot.isSelected() ? "S" : "N") + "' ";
                strSQL+=" ,ne_diaMesMaxEmiFacVen=" +cboDiaMesMaxEmiFac.getSelectedIndex();
                strSQL+=" ,ne_diaSemEmiFacVen=" +cboDiaSemEmiFac.getSelectedIndex();
                strSQL+=" WHERE CO_EMP=" + objParSis.getCodigoEmpresa() + " AND CO_CLI=" + intCodCli + " ;";
                stbDatModInsCli.append(" ; " + strSQL);
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }
//
//    private boolean actualizarTabGenCodVen(Connection conMod) 
//    {
//        boolean blnRes = true;
//        try 
//        {
//            if (conMod != null) 
//            {
//                if (txtCodVen.getText()!="") {
//                    strSQL =" UPDATE tbr_cliloc";
//                    strSQL+=" SET co_ven=" + (txtCodVen.getText().trim().equals("") ? null : txtCodVen.getText());
//                    strSQL+=" , fe_ultmod=current_timestamp";
//                    strSQL+=" , co_usrmod=" + objParSis.getCodigoUsuario();
//                    strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresa();
//                    strSQL+=" AND co_loc="+ objParSis.getCodigoLocal();
//                    strSQL+=" AND co_cli="+ txtCodCli.getText();
//                    stbDatModInsCli.append(" ; " + strSQL);
//                }
//            }
//        }
//        catch (Exception evt) {
//            blnRes = true;
//            objUti.mostrarMsgErr_F1(jfrThis, evt);
//        }
//        return blnRes;
//    }    

    private boolean actualizarTabImp(Connection conMod, int intCodCli) 
    {
        boolean blnRes = true;
        try 
        {
            if (conMod != null){
                if(objParSis.getCodigoUsuario()==1) {
                    strSQL = "UPDATE tbm_cli SET st_ivaven='" + (chkIvaVen.isSelected() ? 'S' : 'N') + "', st_ivacom='" + (chkIvaCom.isSelected() ? 'S' : 'N') + "' WHERE CO_EMP=" + objParSis.getCodigoEmpresa() + " AND CO_CLI=" + intCodCli;
                    stbDatModInsCli.append(" ; " + strSQL);
                }            
                else{ //Solo usuario admin puede marcar/desmarcar: Iva en ventas/Iva en compras para el cliente
                    blnRes = false;
                }
            }
        }
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean insertarTabImp(Connection conIns, int intCodCli) 
    {
        boolean blnRes = true;
        try
        {
            if (conIns != null)
            {
                stm = conIns.createStatement();
                strSQL = "UPDATE tbm_cli SET st_ivaven='" + (chkIvaVen.isSelected() ? 'S' : 'N') + "', st_ivacom='" + (chkIvaCom.isSelected() ? 'S' : 'N') + "' WHERE CO_EMP=" + objParSis.getCodigoEmpresa() + " AND CO_CLI=" + intCodCli;
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }    
    
    private boolean actualizarTabFacEle(Connection conMod, int intCodCli) 
    { 
        boolean blnRes = true;
        String strFecSis = "";
        try 
        {
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            if (conMod != null)
            {
                strSQL =" UPDATE tbm_cli SET tx_corEleFacEle=" + objUti.codificar(txtCorEleFacEle1.getText(), 0) + " "; 
                strSQL+=" ,tx_corEleFacEle2=" + objUti.codificar(txtCorEleFacEle2.getText(), 0) + " "; 
                strSQL+=" ,co_usrmod="+objParSis.getCodigoUsuario()+" ,fe_ultmod='"+strFecSis+"' "; 

                if (optFacElePen.isSelected() == true) {
                    strSQL+=" ,st_proCorEleFacEle=null";
                } else if (optFacEleSi.isSelected() == true) {
                    strSQL+=" ,st_proCorEleFacEle='S'";
                } else {
                    strSQL+=" ,st_proCorEleFacEle='N'";
                }
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + intCodCli + " ;";
                stbDatModInsCli.append(" ; " + strSQL);
            }
        }
        catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt);  }
        return blnRes;
    }   
    
    private boolean verificaIde(Connection conn)
    {
        boolean blnRes = false;
        Statement stmLoc;
        ResultSet rstLoc;
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                strSQL=" SELECT co_cli FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND tx_ide=" + ((strAux = txtIde.getText().replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + "";
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean agregarCliLoc(Connection conval) 
    {
        boolean blnRes = false;
        try 
        {
            Statement stm = conval.createStatement();
            Statement stmLoc = conval.createStatement();
            ResultSet rst, rstLoc;
            //Armar sentencia SQL
            strSQL ="";
            strSQL+=" SELECT cli.co_cli, cli.tx_ide";
            strSQL+=" FROM tbm_cli AS cli";
            strSQL+=" WHERE cli.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND cli.tx_ide='" + txtIde.getText().replaceAll("'", "''") + "'";
            rst = stm.executeQuery(strSQL);
            if (rst.next()) 
            {
                strSQL=" SELECT co_cli FROM tbr_cliloc  WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND CO_LOC=" + objParSis.getCodigoLocal() + " AND CO_CLI=" + rst.getInt("co_cli");
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    mostrarMsgInf("Este Cliente ya Existe corrija el No Identificación y vuelva a intentarlo.");
                    txtIde.selectAll();
                    txtIde.requestFocus();
                    blnRes = false;
                } 
                else 
                {
                    strSQL ="";
                    strSQL+=" INSERT INTO tbr_cliloc(co_emp,co_loc, co_cli, st_regrep) ";
                    strSQL+=" VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + "," + rst.getString("co_cli") + ",'I')";
                    stm.executeUpdate(strSQL);
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
            }
            rst.close();
            rst = null;
            stm.close();
            stm = null;
            stmLoc.close();
            stmLoc = null;
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }

    private boolean agregarCliLocRem(Connection conval, Connection conExt)
    {
        boolean blnRes = false;
        try
        {
            Statement stm = conval.createStatement();
            Statement stmLoc = conval.createStatement();
            Statement stmLoc01 = conExt.createStatement();
            ResultSet rst, rstLoc;
            
            //Armar sentencia SQL
            strSQL ="";
            strSQL+=" SELECT cli.co_cli, cli.tx_ide";
            strSQL+=" FROM tbm_cli AS cli";
            strSQL+=" WHERE cli.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND cli.tx_ide='" + txtIde.getText().replaceAll("'", "''") + "'";
            rst = stm.executeQuery(strSQL);
            if (rst.next()) 
            {
                strSQL=" SELECT co_cli FROM tbr_cliloc WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND CO_LOC=" + objParSis.getCodigoLocal() + " AND CO_CLI=" + rst.getInt("co_cli");
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    mostrarMsgInf("Este Cliente ya Existe corriga el No Identificación y vuelva a intentarlo.");
                    txtIde.selectAll();
                    txtIde.requestFocus();
                    blnRes = false;
                } 
                else 
                {
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_cliloc(co_emp,co_loc, co_cli, st_regrep) ";
                    strSQL+=" VALUES(" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + "," + rst.getString("co_cli") + ",'I')";
                    stm.executeUpdate(strSQL);
                    stmLoc01.executeUpdate(strSQL);
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
            }
            rst.close();
            rst = null;
            stm.close();
            stm = null;

            stmLoc.close();
            stmLoc = null;
            stmLoc01.close();
            stmLoc01 = null;
        } 
        catch (SQLException evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        } 
        catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jfrThis, evt);
        }
        return blnRes;
    }
           
//**************************************************************************************       
    private boolean eliminarReg() 
    {
        try
        {
            java.sql.Connection con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try
            {
                if (con != null)
                {
                    con.setAutoCommit(false);
                    java.sql.PreparedStatement pstReg;

                    strSQL=" UPDATE tbm_benchq SET st_reg='I',st_regrep='M' WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cli="+txtCodCli.getText();
                    pstReg = con.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    strSQL=" UPDATE tbm_cli SET st_reg='E',st_regrep='M' WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cli =" + txtCodCli.getText();
                    pstReg = con.prepareStatement(strSQL);
                    pstReg.executeUpdate();
                    con.commit();
                    con.close();
                    limpiarFrm();
                }
            }
            catch (SQLException evt){
                con.rollback();
                con.close();
                objUti.mostrarMsgErr_F1(jfrThis, evt);
                return false;
            }
        }
        catch (Exception evt){
            objUti.mostrarMsgErr_F1(jfrThis, evt);
            return false;
        }
        return true;
    }
    
    private boolean anularReg() 
    {
        try 
        {
            Connection conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            try 
            {
                if (conLoc != null)
                {
                    conLoc.setAutoCommit(false);
                    java.sql.PreparedStatement pstReg;
                    strSQL ="";
                    strSQL+=" UPDATE tbm_cli SET st_reg='I' ";
                    strSQL+=" WHERE co_emp = " + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND co_cli=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_CLI);
                    pstReg = conLoc.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    strSQL=" UPDATE tbm_benchq SET st_reg='I', st_regrep='M' WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_cli=" + txtCodCli.getText();
                    pstReg = conLoc.prepareStatement(strSQL);
                    pstReg.executeUpdate();

                    conLoc.commit();
                    conLoc.close();
                }
            }
            catch (SQLException e) {
                conLoc.rollback();
                conLoc.close();
                objUti.mostrarMsgErr_F1(this, e);
                return false;
            }
        } 
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;
    }  
    
//**************************************************************************************    
    


        
        
        
        
    
}

