/*  
 *  ZafCom15.java
 *    
 *  Created on 31 de Julio de 2007, 10:10 PM
 */  
package Compras.ZafCom54;
     
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author  Javier Ayapata
 * RECEPCION DE GUIAS DE REMISION. 
 */

public class ZafCom54 extends javax.swing.JInternalFrame
{

    //Constantes: Columnas del JTable:
    /*final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODEMP =1;                    //Código del item (Sistema).
    final int INT_TBL_CODLOC =2;                    //Código del item (Sistema).
    final int INT_TBL_CODTIPDOC =3;                    //Código del item (Alterno).
    final int INT_TBL_DESTIPDOC =4;                    //Código del item (Alterno).
    final int INT_TBL_CODDOC =5;                    //Nombre del item.
    final int INT_TBL_NUMDOC=6;                    //Descripción corta de la unidad de medida.
    final int INT_TBL_NUMDOCORI=7;
    final int INT_TBL_FECDOC=8;                    //Stock consolidado.
    final int INT_TBL_CODCLI=9;                   //Precio de venta 1.
    final int INT_TBL_NOMCLI=10;                   //Precio de venta 1.
    final int INT_TBL_CHKREP=11;                   //Precio de venta 1.
    final int INT_TBL_OBSREP=12;                   //Precio de venta 1.
    final int INT_TBL_ESTCHK=13;                   //Precio de venta 1.*/
    
    private static final int INT_TBL_LIN            =0;                    //Línea
    private static final int INT_TBL_CODEMP         =1;                    
    private static final int INT_TBL_CODLOC         =2;                    
    private static final int INT_TBL_CODTIPDOC      =3;                    
    private static final int INT_TBL_DESTIPDOC      =4;                    
    private static final int INT_TBL_CODDOC         =5;                    
    private static final int INT_TBL_NUMDOC         =6;                    
    private static final int INT_TBL_FECDOC         =7;                    
    private static final int INT_TBL_NUMDOCORI      =8;
    private static final int INT_TBL_CODCLI         =9;                   
    private static final int INT_TBL_NOMCLI         =10;                   
    private static final int INT_TBL_CHKREP         =11;                   
    private static final int INT_TBL_OBSREP         =12;                   
    private static final int INT_TBL_ESTCHK         =13;                   
    private static final int INT_TBL_BUTOBS         =14;                   
    private static final int INT_TBL_CODVEH         =15;                   
    private static final int INT_TBL_BUTVEH         =16;                   
    private static final int INT_TBL_DESVEH         =17;                   
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblCelRenBut objTblCelRenBut;   
    private ZafTblCelEdiButGen objTblCelEdiButGen;    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafSelFec objSelFec;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    //private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    //private ZafDocLis objDocLis;
    
    private Connection con;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.

    ZafVenCon objVenConBodUsr; //*****************
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen;
    ZafVenCon objVenConVeh;
      
    String strCodBod="", strNomBod="";
    String strCodCli="";
    String strDesCli="";
    String strCodVen="";
    String strDesVen="";
    String strVersion=" v. 0.5 ";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom54(ZafParSis obj) {

        objParSis=obj;
        objTblCelEdiChk = new ZafTblCelEdiChk();
        objUti = new ZafUtil();
        initComponents();
        //Inicializar objetos.

        //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 5, 472, 72);    
            //objDocLis=new ZafDocLis();
            Configura_ventana_consulta();
            configurarFrm();
    }



private boolean configurarVenConBodUsr() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_bod");
    arlCam.add("a.tx_nom");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Nom.Bod");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("350");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String strSql="";
    /*strSql="SELECT co_bod, tx_nom FROM ( " +
    " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
    " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
    " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
    " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
    " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+"  " +
    " ) as a";*/
    if (objParSis.getCodigoUsuario() == 1) {
        strSql= " SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "+
                " FROM tbm_emp AS a1 "+
                " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "+
                " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" "+
                " ORDER BY a1.co_emp, a2.co_bod ) as a ";
    }else{
        if(objParSis.getCodigoEmpresaGrupo() == objParSis.getCodigoEmpresa() ){
             strSql=" SELECT co_bod, tx_nom FROM ( " +
                    " select a1.co_bod, a1.tx_nom from tbr_bodlocprgusr as a " +
                    " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
                    " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
                    " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+" " +
                    " and a.tx_natbod = 'I' " +
                    " ) as a";
        }else{
            strSql= " SELECT co_bod, tx_nom FROM ( " +
                    " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
                    " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
                    " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
                    " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
                    " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+" " +
                    " and a.tx_natbod = 'I' " +
                    " ) as a";
        }
    }
    
    objVenConBodUsr=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSql, arlCam, arlAli, arlAncCol);
    arlCam=null;
    arlAli=null;
    arlAncCol=null;

    //objVenConBodUsr.setConfiguracionColumna(1, JLabel.CENTER);
}
catch (Exception e) {
    blnRes=false;
    objUti.mostrarMsgErr_F1(this, e);
}
return blnRes;
}

private boolean configurarVenConClientes() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_cli");
        arlCam.add("a.tx_nom");
        arlCam.add("a.tx_dir");
        arlCam.add("a.tx_tel");
        arlCam.add("a.tx_ide");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Cli.");
        arlAli.add("Dirección");
        arlAli.add("Telefono");
        arlAli.add("RUC/CI");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("180");
        arlAncCol.add("120");
        arlAncCol.add("80");
        arlAncCol.add("100");

        //Armar la sentencia SQL.
        String  strSQL;
        //strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbm_cli as a " +
        //" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.st_cli='S' order by a.tx_nom ";

        /*strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 " +
        " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " +
        " WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" " +
        " AND  a.st_reg NOT IN('I','T')  order by a.tx_nom ";*/

        //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
        if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) {
            strSQL= " SELECT a1.co_cli, a1.tx_nom, a1.tx_dir, a1.tx_tel, a1.tx_ide " + 
                    " FROM tbm_cli AS a1 " +
                    " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                    " AND a1.st_reg='A' " +
                    " and a1.st_cli='S' " +
                    " ORDER BY a1.tx_nom";
        } else {
            strSQL= " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide " +
                    " from tbm_cli a, tbr_cliloc b, tbr_locprgusr c   " +
                    " where a.co_emp = b.co_emp " +
                    " and a.co_cli = b.co_cli " +
                    " and a.co_emp = c.co_emp " +
                    " and b.co_loc = c.co_loc " +
                    " and a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                    " and b.co_loc=" +objParSis.getCodigoLocal() + " " + 
                    " and c.co_usr=" + objParSis.getCodigoUsuario() + " " +
                    " and c.co_mnu=" + objParSis.getCodigoMenu() + " " +
                    " and a.st_reg='A' " +
                    " and a.st_cli='S' " + 
                    " order by 3 ";
        }
        
        //System.out.println("ZafCom54.configurarVenConClientes" + strSQL);
        
        objVenConCLi=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}
   
private boolean configurarVenConVendedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_usr");
        arlCam.add("a.tx_nom");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nombre.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("70");
        arlAncCol.add("470");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
        " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
        " where b.co_emp="+objParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }

    private boolean configurarVenConVehiculos() {
        boolean blnRes=true;
        String strSql;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_veh");
            arlCam.add("tx_pla");
            arlCam.add("tx_deslar");
            arlCam.add("tx_marca");
            arlCam.add("nd_pessopkgr");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Placa");
            arlAli.add("Descripcion");
            arlAli.add("Marca");
            arlAli.add("Peso Kgr");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("250");
            arlAncCol.add("100");
            arlAncCol.add("70");

            if (objParSis.getCodigoUsuario()==1) {
                strSql= " select b.co_veh, b.tx_pla, b.tx_deslar, upper(c.tx_deslar) as tx_marca, round(b.nd_pessopkgr,2) as nd_pessopkgr "
                      + " from tbr_vehLocprg as a "
                      + " inner join tbm_veh as b on (a.co_veh=b.co_veh) "
                      //+ " inner join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " left outer join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " " 
                      + " and a.co_loc=" +objParSis.getCodigoLocal() + " "
                      + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                      + " and a.st_reg not in ('E', 'I') "
                      + " and b.st_reg not in ('E', 'I') "
                      //+ " and c.st_reg not in ('E', 'I') "
                      + " ";
            } else {
                strSql= " select b.co_veh, b.tx_pla, b.tx_deslar, upper(c.tx_deslar) as tx_marca, round(b.nd_pessopkgr,2) as nd_pessopkgr "
                      + " from tbr_vehLocPrgUsr as a "
                      + " inner join tbm_veh as b on (a.co_veh=b.co_veh) "
                      //+ " inner join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " left outer join tbm_marveh as c on (c.co_mar=b.co_mar) "
                      + " where a.co_emp=" + objParSis.getCodigoEmpresa() + " " 
                      + " and a.co_loc=" +objParSis.getCodigoLocal() + " "
                      + " and a.co_mnu=" + objParSis.getCodigoMenu() + " "
                      + " and a.co_usr=" + objParSis.getCodigoUsuario() + " "
                      + " and a.st_reg not in ('E', 'I') "
                      + " and b.st_reg not in ('E', 'I') "
                      //+ " and c.st_reg not in ('E', 'I')"
                      + " ";
            }

            objVenConVeh=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSql, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenConVeh.setCampoBusqueda(1);
        }catch (Exception e) {  
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
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        lblCli1 = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        butcli = new javax.swing.JButton();
        chkMosDocRec = new javax.swing.JCheckBox();
        chkMosDocNoRec = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        burvis = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 100, 400, 20);

        bgrFil.add(optFil);
        optFil.setSelected(true);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 120, 400, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Número de documento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(51, 102, 255))); // NOI18N
        panNomCli.setLayout(null);

        lblCodAltDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 44, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(56, 20, 220, 20);

        lblCodAltHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(300, 20, 44, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(340, 20, 240, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(30, 180, 620, 52);

        lblBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblBod.setText("Bodega:");
        panFil.add(lblBod);
        lblBod.setBounds(10, 80, 70, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
        txtCodBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        panFil.add(txtCodBod);
        txtCodBod.setBounds(70, 80, 70, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        panFil.add(txtNomBod);
        txtNomBod.setBounds(140, 80, 230, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        panFil.add(butBusBod);
        butBusBod.setBounds(370, 80, 20, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente/Proveedor:");
        panFil.add(lblCli);
        lblCli.setBounds(30, 140, 100, 20);

        lblCli1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli1.setText("Vend / Comp:");
        panFil.add(lblCli1);
        lblCli1.setBounds(30, 160, 100, 20);

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
        panFil.add(txtCodVen);
        txtCodVen.setBounds(130, 160, 50, 20);

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
        panFil.add(txtCodCli);
        txtCodCli.setBounds(130, 140, 50, 20);

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
        panFil.add(txtNomCli);
        txtNomCli.setBounds(180, 140, 280, 20);

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
        panFil.add(txtNomVen);
        txtNomVen.setBounds(180, 160, 280, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(460, 160, 20, 20);

        butcli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butcli.setText("...");
        butcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcliActionPerformed(evt);
            }
        });
        panFil.add(butcli);
        butcli.setBounds(460, 140, 20, 20);

        chkMosDocRec.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosDocRec.setText("Mostrar los documentos que ya fueron recibidas.");
        chkMosDocRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocRecActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocRec);
        chkMosDocRec.setBounds(30, 250, 440, 22);

        chkMosDocNoRec.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMosDocNoRec.setSelected(true);
        chkMosDocNoRec.setText("Mostrar los documentos que aún no han sido recibidas.");
        chkMosDocNoRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocNoRecActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocNoRec);
        chkMosDocNoRec.setBounds(30, 230, 440, 22);

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

        burvis.setText("Vista Preliminar");
        burvis.setPreferredSize(new java.awt.Dimension(92, 25));
        burvis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                burvisActionPerformed(evt);
            }
        });
        panBot.add(burvis);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_optTodActionPerformed
        
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        configurarFrm();

    }//GEN-LAST:event_formInternalFrameOpened

    
    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");

            strCodCli="";  strDesCli="";
            strCodVen="";  strDesVen="";

            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");

            chkMosDocNoRec.setSelected(true);
            chkMosDocRec.setSelected(true);


        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        
       if(txtCodBod.getText().equals("")){
          MensajeInf("Seleccione la bodega antes consultar un documento. ");
          tabFrm.setSelectedIndex(0);
          txtCodBod.requestFocus();
       } else{


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
    
       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    


    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        // TODO add your handling code here:
        txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        // TODO add your handling code here:
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        // TODO add your handling code here:
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }else
                BuscarBod("a.co_bod",txtCodBod.getText(),0);
        }else
            txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        // TODO add your handling code here:
        txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        // TODO add your handling code here:
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        // TODO add your handling code here:
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }else
                BuscarBod("a.tx_nom",txtNomBod.getText(),1);
        }else
            txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

    private void butBusBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusBodActionPerformed
        // TODO add your handling code here:
        objVenConBodUsr.setTitle("Listado de Bodegas");
        objVenConBodUsr.setCampoBusqueda(1);
        objVenConBodUsr.show();
        if (objVenConBodUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodBod.setText(objVenConBodUsr.getValueAt(1));
            txtNomBod.setText(objVenConBodUsr.getValueAt(2));
            strCodBod=objVenConBodUsr.getValueAt(1);
            strNomBod=objVenConBodUsr.getValueAt(2);
        }
}//GEN-LAST:event_butBusBodActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        // TODO add your handling code here:
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        // TODO add your handling code here:
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
                BuscarVendedor("a.co_usr",txtCodVen.getText(),0);
        }else
            txtCodVen.setText(strCodVen);
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.co_cli",txtCodCli.getText(),0);
        }else
            txtCodCli.setText(strCodCli);
}//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
}//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesCli=txtNomCli.getText();
        txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
        }else
            txtNomCli.setText(strDesCli);
}//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        // TODO add your handling code here:
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        // TODO add your handling code here:
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
                BuscarVendedor("a.tx_nom",txtNomVen.getText(),1);
        }else
            txtNomVen.setText(strDesVen);
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // TODO add your handling code here:
        objVenConVen.setTitle("Listado de Clientes");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void butcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcliActionPerformed
        // TODO add your handling code here:
        objVenConCLi.setTitle("Listado de Clientes");
        objVenConCLi.setCampoBusqueda(1);
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butcliActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:

   if(chkMosDocNoRec.isSelected()){
     if(chkMosDocRec.isSelected()){
          chkMosDocRec.setSelected(false);
     }
       
   }else{
    if(!chkMosDocRec.isSelected()){
       chkMosDocNoRec.setSelected(true);
      }
   }


    }//GEN-LAST:event_optFilItemStateChanged

    private void chkMosDocNoRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocNoRecActionPerformed
        // TODO add your handling code here:

        if(chkMosDocNoRec.isSelected()){
           if(chkMosDocRec.isSelected())
               optTod.setSelected(true);
           else  optFil.setSelected(true);
        }else{
             optFil.setSelected(true);
             if(!chkMosDocRec.isSelected()){
                 chkMosDocRec.setSelected(true);
             }
        }

    }//GEN-LAST:event_chkMosDocNoRecActionPerformed

    private void chkMosDocRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocRecActionPerformed
        // TODO add your handling code here:

         if(chkMosDocRec.isSelected()){
           if(chkMosDocNoRec.isSelected())
               optTod.setSelected(true);
           else  optFil.setSelected(true);
        }else{
             optFil.setSelected(true);
             if(!chkMosDocNoRec.isSelected()){
                 chkMosDocNoRec.setSelected(true);
             }
        }


    }//GEN-LAST:event_chkMosDocRecActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:

    String strMsg = "¿Está Seguro que desea Guardar la Información ?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {

      guardarRecep();
      cargarDetReg( sqlConFil());
      lblMsgSis.setText("Listo");
      pgrSis.setValue(0);

    }


    }//GEN-LAST:event_butGuaActionPerformed

    private void burvisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_burvisActionPerformed
        // TODO add your handling code here:
        vistaPreliminar();
    }//GEN-LAST:event_burvisActionPerformed




public boolean vistaPreliminar(){
  Connection conIns;
  String strTipRep="0";
  String strTipnumdoc="0";
  String strDes="";
  String strHas="";
  String strTipCli="0";
  String strCodCliBus="";
  String strTipVen="0";
  String strCodVenBus="";
  String strTipFec="0";
  String strFecDes="";
  String strFecHas="";
  try{
    conIns =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
    if(conIns!=null){
        
        String DIRECCION_REPORTE="";

         if(System.getProperty("os.name").equals("Linux")){
             //DIRECCION_REPORTE="//zafiro//reportes_impresos//Rpt_recepcion_guia.jrxml";
             DIRECCION_REPORTE=objUti.getDirectorioSistema() + "//reportes_impresos//Rpt_recepcion_guia.jrxml";
         }else{
             //DIRECCION_REPORTE="C://zafiro//reportes_impresos//Rpt_recepcion_guia.jrxml";
             DIRECCION_REPORTE=objUti.getDirectorioSistema() + "//reportes_impresos//Rpt_recepcion_guia.jrxml";
         }

        if(chkMosDocNoRec.isSelected()) strTipRep="1";
        if(chkMosDocRec.isSelected())   strTipRep="2";
        if( chkMosDocNoRec.isSelected() && chkMosDocRec.isSelected()  ) strTipRep="3";

        if(txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
         strTipnumdoc="1";
         strDes=txtCodAltDes.getText().replaceAll("'", "''").toLowerCase();
         strHas=txtCodAltHas.getText().replaceAll("'", "''").toLowerCase();
        }

        if(optFil.isSelected()){
           if(!txtCodCli.getText().equals("")){
             strTipCli="1"; strCodCliBus= txtCodCli.getText();
           }if(!txtCodVen.getText().equals("")){
            strTipVen="1"; strCodVenBus= txtCodVen.getText();
        }}

        
       if(objSelFec.isCheckBoxChecked() ){
         switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        strTipFec="1";
                        strFecDes=objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        strFecHas=objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strTipFec="2";
                        strFecHas=objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strTipFec="3";
                        strFecDes=objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        break;
                    case 3: //Todo.
                        break;
        }}

         JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE);
         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map parameters = new HashMap();
            parameters.put("Tit", objParSis.getNombreMenu() );
            parameters.put("coempgrp", ""+objParSis.getCodigoEmpresaGrupo() );
            parameters.put("cobodgrp", txtCodBod.getText() );
            parameters.put("tiprecp", strTipRep );
            parameters.put("tipnumdoc", strTipnumdoc );
            parameters.put("numdes", strDes );
            parameters.put("numhas", strHas );
            parameters.put("cocli", strCodCliBus );
            parameters.put("coven", strCodVenBus );
            parameters.put("tipfec", strTipFec );
            parameters.put("fecdes", strFecDes );
            parameters.put("fechas", strFecHas );
            parameters.put("tipven", strTipVen );
            parameters.put("tipcli", strTipCli );

            JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
            JasperViewer.viewReport(report, false);

            conIns.close();
            conIns=null;
    
}}catch (JRException e) {  objUti.mostrarMsgErr_F1(this, e );    }
  catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
 return true;
}

 private boolean guardarRecep(){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
    conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
    if(conn!=null){
     conn.setAutoCommit(false);
  
     if(guardarRecepcion(conn)){
         conn.commit();
     }else conn.rollback();

     conn.close();
     conn=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
  return  blnRes;
}


 private boolean guardarRecepcion(java.sql.Connection conn ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  String strFecSis="";
  try{
    if(conn!=null){
     stmLoc=conn.createStatement();
     strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

     for(int i=0; i < tblDat.getRowCount(); i++ ){
      if(tblDat.getValueAt(i, INT_TBL_CHKREP).toString().equals("true")){
       if(tblDat.getValueAt(i, INT_TBL_ESTCHK).toString().equals("N")){

          /*strSql="INSERT INTO tbm_recguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg, fe_rec, tx_obsrec, co_usrrec, st_regrep )" +
          " VALUES("+tblDat.getValueAt(i, INT_TBL_CODEMP)+", "+tblDat.getValueAt(i, INT_TBL_CODLOC)+"" +
          ", "+tblDat.getValueAt(i, INT_TBL_CODTIPDOC)+", "+tblDat.getValueAt(i, INT_TBL_CODDOC)+", 1, '"+strFecSis+"'" +
          ", '"+tblDat.getValueAt(i, INT_TBL_OBSREP)+"', "+objParSis.getCodigoUsuario()+",'I'  )";*/

          strSql="INSERT INTO tbm_recguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg, fe_rec, tx_obsrec, co_usrrec, st_regrep )" +
          " VALUES("+tblDat.getValueAt(i, INT_TBL_CODEMP)+", "+tblDat.getValueAt(i, INT_TBL_CODLOC)+"" +
          ", "+tblDat.getValueAt(i, INT_TBL_CODTIPDOC)+", "+tblDat.getValueAt(i, INT_TBL_CODDOC)+", 1, '"+strFecSis+"'" +
          ", "+objUti.codificar(((tblDat.getValueAt(i, INT_TBL_OBSREP)==null)?"":tblDat.getValueAt(i, INT_TBL_OBSREP)))+", "+objParSis.getCodigoUsuario()+",'I' )";
          
          /*strSql+=" ; UPDATE  tbm_cabguirem SET ne_numvecrecdoc=1 WHERE co_emp="+tblDat.getValueAt(i, INT_TBL_CODEMP)+" AND " +
          " co_loc="+tblDat.getValueAt(i, INT_TBL_CODLOC)+" AND co_tipdoc="+tblDat.getValueAt(i, INT_TBL_CODTIPDOC)+" " +
          " AND  co_doc="+tblDat.getValueAt(i, INT_TBL_CODDOC)+" ";*/
          
          strSql+=" ; update tbm_cabguirem set ne_numvecrecdoc=1, fe_ultrecdoc=current_timestamp, co_veh= "+tblDat.getValueAt(i, INT_TBL_CODVEH)+" "
                  + " where co_emp="+tblDat.getValueAt(i, INT_TBL_CODEMP)+" "
                  + " and co_loc="+tblDat.getValueAt(i, INT_TBL_CODLOC)+" "
                  + " and co_tipdoc="+tblDat.getValueAt(i, INT_TBL_CODTIPDOC)+" "
                  + " and co_doc="+tblDat.getValueAt(i, INT_TBL_CODDOC)+" ";
          //System.out.println("-->"+strSql);
          stmLoc.executeUpdate(strSql);
      }}
    }

     stmLoc.close();
     stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
  return  blnRes;
}

 public void BuscarBod(String campo,String strBusqueda,int tipo){
  objVenConBodUsr.setTitle("Listado de Bodegas");
  if(objVenConBodUsr.buscar(campo, strBusqueda )) {
        txtCodBod.setText(objVenConBodUsr.getValueAt(1));
        txtNomBod.setText(objVenConBodUsr.getValueAt(2));
        strCodBod=objVenConBodUsr.getValueAt(1);
        strNomBod=objVenConBodUsr.getValueAt(2);
  }else{
        objVenConBodUsr.setCampoBusqueda(tipo);
        objVenConBodUsr.cargarDatos();
        objVenConBodUsr.show();
        if (objVenConBodUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodBod.setText(objVenConBodUsr.getValueAt(1));
           txtNomBod.setText(objVenConBodUsr.getValueAt(2));
           strCodBod=objVenConBodUsr.getValueAt(1);
           strNomBod=objVenConBodUsr.getValueAt(2);
        }else{
           txtCodBod.setText(strCodBod);
           txtNomBod.setText(strNomBod);
  }}}

 


public void BuscarCliente(String campo,String strBusqueda,int tipo){
  objVenConCLi.setTitle("Listado de Clientes");
  if(objVenConCLi.buscar(campo, strBusqueda )) {
      txtCodCli.setText(objVenConCLi.getValueAt(1));
      txtNomCli.setText(objVenConCLi.getValueAt(2));
      optFil.setSelected(true);
  }else{
        objVenConCLi.setCampoBusqueda(tipo);
        objVenConCLi.cargarDatos();
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodCli.setText(objVenConCLi.getValueAt(1));
           txtNomCli.setText(objVenConCLi.getValueAt(2));
           optFil.setSelected(true);
        }else{
            txtCodCli.setText(strCodCli);
            txtNomCli.setText(strDesCli);
  }}}


public void BuscarVendedor(String campo,String strBusqueda,int tipo){
  objVenConVen.setTitle("Listado de Vendedores");
  if(objVenConVen.buscar(campo, strBusqueda )) {
      txtCodVen.setText(objVenConVen.getValueAt(1));
      txtNomVen.setText(objVenConVen.getValueAt(2));
      optFil.setSelected(true);
  }else{
        objVenConVen.setCampoBusqueda(tipo);
        objVenConVen.cargarDatos();
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }else{
            txtCodVen.setText(strCodVen);
            txtNomVen.setText(strDesVen);
  }}}

    public void buscarVehiculo(String campo, String strBusqueda,int tipo){
        objVenConVeh.setTitle("Listado de Vehiculos");
        if(objVenConVeh.buscar(campo, strBusqueda )) {
            objTblMod.setValueAt(objVenConVeh.getValueAt(1), tblDat.getSelectedRow(), INT_TBL_CODVEH);
            objTblMod.setValueAt(objVenConVeh.getValueAt(3), tblDat.getSelectedRow(), INT_TBL_DESVEH);
        }else{
            objVenConVeh.setCampoBusqueda(tipo);
            objVenConVeh.cargarDatos();
            objVenConVeh.show();
            if (objVenConVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                objTblMod.setValueAt(objVenConVeh.getValueAt(1), tblDat.getSelectedRow(), INT_TBL_CODVEH);
                objTblMod.setValueAt(objVenConVeh.getValueAt(3), tblDat.getSelectedRow(), INT_TBL_DESVEH);
            }else{
                objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_CODVEH);
                objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DESVEH);
            }
        }
    }      
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton burvis;
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butcli;
    private javax.swing.JCheckBox chkMosDocNoRec;
    private javax.swing.JCheckBox chkMosDocRec;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCli1;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblMsgSis;
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
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables
       
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux+" "+ strVersion );
            lblTit.setText(strAux);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"Linea");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL_DESTIPDOC,"Tip.Doc");
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_NUMDOCORI,"Doc.Ori.");
            vecCab.add(INT_TBL_CODCLI,"Cod.Cli.");
            vecCab.add(INT_TBL_NOMCLI,"Cliente");
            vecCab.add(INT_TBL_CHKREP,"..");
            vecCab.add(INT_TBL_OBSREP,"Observacion");
            vecCab.add(INT_TBL_ESTCHK,"..");
            vecCab.add(INT_TBL_BUTOBS,"");
            vecCab.add(INT_TBL_CODVEH,"Cod.Veh.");
            vecCab.add(INT_TBL_BUTVEH,"");
            vecCab.add(INT_TBL_DESVEH,"Vehiculo");
            
           // vecCab.add(INT_TBL_DAT_TOT_COS,"Total.Costo");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux=tblDat.getColumnModel();
         
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_NUMDOCORI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_CHKREP).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_OBSREP).setPreferredWidth(125);
            tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODVEH).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTVEH).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DESVEH).setPreferredWidth(125);

	       //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHKREP);
            vecAux.add("" + INT_TBL_OBSREP);
            vecAux.add("" + INT_TBL_BUTOBS);
            vecAux.add("" + INT_TBL_CODVEH);
            vecAux.add("" + INT_TBL_BUTVEH);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_GEN);
            //objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CODVEH).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;            

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_OBSREP).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_CODVEH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intFil=tblDat.getSelectedRow();
                     if( tblDat.getValueAt( intFil, INT_TBL_ESTCHK).toString().equals("S") ){
                            objTblCelEdiTxt.setCancelarEdicion(true);
                     }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        switch(intColSel){
                            case INT_TBL_OBSREP: 
                                if( tblDat.getValueAt( intFilSel, INT_TBL_ESTCHK).toString().equals("S") ){ 
                                    objTblCelEdiTxt.setCancelarEdicion(true);
                                }else {
                                    String strCad=(tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString();
                                    String strCad1=(tblDat.getValueAt(intFilSel, INT_TBL_CODVEH)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_CODVEH).toString();
                                    //if ((tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)!=null) && !(tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString().equals(""))) {
                                    if (!strCad.equals("") || !strCad1.equals("")) {
                                        tblDat.setValueAt(true, intFilSel, INT_TBL_CHKREP); 
                                    }else{
                                        tblDat.setValueAt(false, intFilSel, INT_TBL_CHKREP);                             
                                    }
                                }
                                break;
                            case INT_TBL_CODVEH: 
                                if( tblDat.getValueAt( intFilSel, INT_TBL_ESTCHK).toString().equals("S") ){ 
                                    objTblCelEdiTxt.setCancelarEdicion(true);
                                }
                            default: break;
                        }
                    }                    
                   int intFil=tblDat.getSelectedRow();
                }
            });
            
	    ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKREP).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;            
            
            tcmAux.getColumn(INT_TBL_CHKREP).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                   int intFil=tblDat.getSelectedRow();
                   if( tblDat.getValueAt( intFil, INT_TBL_ESTCHK).toString().equals("S") ){
                       objTblCelEdiChk.setCancelarEdicion(true);
                   }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                  int intFil=tblDat.getSelectedRow();
                   if( tblDat.getValueAt( intFil, INT_TBL_ESTCHK).toString().equals("S") ){
                       objTblCelEdiChk.setCancelarEdicion(true);
                   }
                   
                }
            });
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTVEH).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) { 
                    intColSel=objTblCelRenBut.getColumnRender();
                    intFilSel=objTblCelRenBut.getRowRender();
                    switch (intColSel){
                        case INT_TBL_BUTVEH: 
                            if(tblDat.getValueAt(intFilSel, INT_TBL_ESTCHK).toString().equals("S")) 
                                objTblCelRenBut.setText(""); 
                            else 
                                objTblCelRenBut.setText("...");
                            break;
                        default: break;
                    }                  
                }                
            });

            int intColVen[]=new int[5];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            
            int intColTbl[]=new int[5];
            intColTbl[0]=INT_TBL_CODVEH;
            intColTbl[1]=0;
            intColTbl[2]=INT_TBL_DESVEH;
            intColTbl[3]=0;
            intColTbl[4]=0;
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, objVenConVeh, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_CODVEH).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt) { 
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        switch(intColSel){
                            case INT_TBL_CODVEH: 
                                if(tblDat.getValueAt(intFilSel, INT_TBL_ESTCHK).toString().equals("S")) 
                                    objTblCelEdiTxtVcoItm.setCancelarEdicion(true);
                                else
                                    objTblCelEdiTxtVcoItm.setCancelarEdicion(false);
                                break;
                            default: break;
                        }
                    }                    
                }
                @Override
                public void beforeConsultar(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if(tblDat.getValueAt(intFilSel, INT_TBL_ESTCHK).toString().equals("N")) {
                        objVenConVeh.setCampoBusqueda(0);
                        objVenConVeh.setCriterio1(11);                        
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        String strCad=(tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString();
                        String strCad1=(tblDat.getValueAt(intFilSel, INT_TBL_CODVEH)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_CODVEH).toString();
                        //if ((tblDat.getValueAt(intFilSel, INT_TBL_CODVEH)!=null) && !(tblDat.getValueAt(intFilSel, INT_TBL_CODVEH).toString().equals(""))) {
                        if (!strCad.equals("") || !strCad1.equals("")) {
                            tblDat.setValueAt(true, intFilSel, INT_TBL_CHKREP); 
                        }else{
                            tblDat.setValueAt(false, intFilSel, INT_TBL_CHKREP);                             
                        }
                    }
                }
            });              
           
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, objVenConVeh, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_BUTVEH).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt) { 
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        switch(intColSel){
                            case INT_TBL_BUTVEH: 
                                if(tblDat.getValueAt(intFilSel, INT_TBL_ESTCHK).toString().equals("S")) 
                                    objTblCelEdiButVcoItm.setCancelarEdicion(true);
                                else
                                    objTblCelEdiButVcoItm.setCancelarEdicion(false);
                                break;
                            default: break;
                        }
                    }                    
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        String strCad=(tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString();
                        String strCad1=(tblDat.getValueAt(intFilSel, INT_TBL_CODVEH)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_CODVEH).toString();
                        //if ((tblDat.getValueAt(intFilSel, INT_TBL_CODVEH)!=null) && !(tblDat.getValueAt(intFilSel, INT_TBL_CODVEH).toString().equals(""))) {
                        if (!strCad.equals("") || !strCad1.equals("")) {
                            tblDat.setValueAt(true, intFilSel, INT_TBL_CHKREP); 
                        }else{
                            tblDat.setValueAt(false, intFilSel, INT_TBL_CHKREP);                             
                        }
                    }
                }
            });
            
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellEditor(objTblCelEdiButGen);
            //tcmAux.getColumn(INT_TBL_BUTVEH).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {    
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt) { 
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        switch(intColSel){
                            case INT_TBL_BUTOBS: 
                                if(tblDat.getValueAt(intFilSel, INT_TBL_ESTCHK).toString().equals("S")) 
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                else
                                    objTblCelEdiButGen.setCancelarEdicion(false);
                                break;
                            default: break;
                        }
                    }                    
                }
                @Override
                public void afterEdit(ZafTableEvent evt) { 
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel != -1){
                        switch(intColSel){
                            case INT_TBL_BUTOBS:                                     
                                if(tblDat.getValueAt(intFilSel, INT_TBL_ESTCHK).toString().equals("N")) {
                                    String strObs = (tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)==null)?"": tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString();
                                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafCom54.this), true, strObs);
                                    zafMae07_01.setVisible(true);
                                    if (zafMae07_01.getAceptar()) 
                                        tblDat.setValueAt(zafMae07_01.getObser(), intFilSel, INT_TBL_OBSREP);                                    
                                    zafMae07_01=null; 
                                    
                                    String strCad=(tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString();
                                    String strCad1=(tblDat.getValueAt(intFilSel, INT_TBL_CODVEH)==null)?"":tblDat.getValueAt(intFilSel, INT_TBL_CODVEH).toString();
                                    //if ((tblDat.getValueAt(intFilSel, INT_TBL_OBSREP)!=null) && !(tblDat.getValueAt(intFilSel, INT_TBL_OBSREP).toString().equals(""))) {
                                    if (!strCad.equals("") || !strCad1.equals("")) {
                                        tblDat.setValueAt(true, intFilSel, INT_TBL_CHKREP); 
                                    }else{
                                        tblDat.setValueAt(false, intFilSel, INT_TBL_CHKREP);
                                    }
                                }
                                break;
                            default: break;
                        }
                    }                    
                }                
            });
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            ZafTblBus zafTblBus = new ZafTblBus(tblDat);
            //Libero los objetos auxiliares.
            ZafTblOrd zafTblOrd = new ZafTblOrd(tblDat);

              ArrayList arlColHid=new ArrayList();
              arlColHid.add(""+INT_TBL_CODTIPDOC);
              arlColHid.add(""+INT_TBL_CODDOC);
              arlColHid.add(""+INT_TBL_ESTCHK);
              objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
              arlColHid=null;

            tcmAux=null;
            setEditable(true);
            //objTblModLis=new ZafTblModLis();
            //objTblMod.addTableModelListener(objTblModLis);
            
        } catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

     public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }
     
      private void Configura_ventana_consulta(){
        configurarVenConBodUsr();
        configurarVenConClientes();
        configurarVenConVendedor();
        cargarBodPre();
        configurarVenConVehiculos();
    }     


private String sqlConFil(){
   String sqlFil="";
 
   if(chkMosDocNoRec.isSelected()) sqlFil="  AND a.ne_numvecrecdoc=0 ";
   if(chkMosDocRec.isSelected()) sqlFil="  AND a.ne_numvecrecdoc > 0 ";
   if( chkMosDocNoRec.isSelected() && chkMosDocRec.isSelected()  ) sqlFil="  AND a.ne_numvecrecdoc >= 0 ";

   if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
     sqlFil+=" AND ((LOWER(a.ne_numdoc) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.ne_numdoc) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

    if(optFil.isSelected()){

        if(!txtCodCli.getText().equals(""))
            sqlFil+=" AND a.co_clides="+txtCodCli.getText();

        if(!txtNomCli.getText().equals(""))
            sqlFil+=" AND a.tx_nomclides lIKE '"+txtNomCli.getText()+"'";

        if(!txtCodVen.getText().equals(""))
            sqlFil+=" AND a.co_ven="+txtCodVen.getText();
    }
  
       if(objSelFec.isCheckBoxChecked() ){
         switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }}
      return sqlFil;
    }

/**
* Esta función permite consultar los registros de acuerdo al criterio seleccionado.
* @return true: Si se pudo consultar los registros.
* <BR>false: En el caso contrario.
*/
private boolean cargarDetReg(String strFil){
 boolean blnRes=true;
 Statement stm;
 ResultSet rst;
 int intNumTotReg=0, i=0;
 String strSql="";
 try{
    butCon.setText("Detener");
    lblMsgSis.setText("Obteniendo datos...");
    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
    if (con!=null){
        stm=con.createStatement();
        //Obtener la condición.       

        String Str_Sql="";
        if(objParSis.getCodigoUsuario()==1){
           Str_Sql="Select a.co_tipdoc from tbr_tipdocprg as b " +
           " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                " where   b.co_emp="+objParSis.getCodigoEmpresa()+" and " +
                " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                " b.co_mnu = "+objParSis.getCodigoMenu();
         }else {
                Str_Sql ="SELECT a.co_tipdoc "+
                " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                " WHERE "+
                "  a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                " AND a1.co_usr=" + objParSis.getCodigoUsuario();
         }
        
       /*strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a7.tx_descor, a.co_doc , a.ne_numdoc, a.fe_doc, a.co_clides , a.tx_nomclides, a.ne_numvecrecdoc ,a8.tx_obsrec " +
       " ,CASE WHEN a8.co_emp IS NULL THEN 'N' ELSE 'S' END AS est , a.tx_datdocoriguirem  FROM tbm_cabguirem as a  " +
       " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
       " INNER JOIN tbm_cabtipdoc as a7 on (a7.co_emp = a.co_emp and a7.co_loc = a.co_loc and a7.co_tipdoc = a.co_tipdoc ) "+
       " LEFT JOIN tbm_recguirem AS a8 ON(a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc and a8.co_doc=a.co_doc) "+
       " WHERE  a.st_reg not in('E')   and a.co_tipdoc in ( "+Str_Sql+" )  " +
       " AND ( a6.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp=( "+txtCodBod.getText()+" ) ) " +
       " "+strFil+"    ";*/

       strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a7.tx_descor, a.co_doc , a.ne_numdoc, a.fe_doc, a.co_clides , a.tx_nomclides, a.ne_numvecrecdoc ,a8.tx_obsrec, "
               + " CASE WHEN a8.co_emp IS NULL THEN 'N' ELSE 'S' END AS est, a.tx_datdocoriguirem, a.co_veh, (select tx_deslar from tbm_veh where co_veh=a.co_veh and st_reg not in ('E', 'I')) as tx_deslarveh "
               + " FROM tbm_cabguirem as a  " +
       " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) " +
       " INNER JOIN tbm_cabtipdoc as a7 on (a7.co_emp = a.co_emp and a7.co_loc = a.co_loc and a7.co_tipdoc = a.co_tipdoc ) "+
       " LEFT JOIN tbm_recguirem AS a8 ON(a8.co_emp=a.co_emp and a8.co_loc=a.co_loc and a8.co_tipdoc=a.co_tipdoc and a8.co_doc=a.co_doc) "+
       " WHERE  a.st_reg not in('E')   and a.co_tipdoc in ( "+Str_Sql+" )  " +
       " AND ( a6.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a6.co_bodGrp=( "+txtCodBod.getText()+" ) ) " +
       " "+strFil+"    ";

        strSQL="SELECT COUNT(*) FROM ( "+strSql+" ) AS X ";
        intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
        if (intNumTotReg==-1)
           return false;

        strSQL="SELECT * FROM ( "+strSql+" ) AS X  ORDER BY ne_numdoc ";

        System.out.println("ZafCom54.cargarDetReg: Codigo de menu: "+ objParSis.getCodigoMenu() + " - " + strSQL );
        rst=stm.executeQuery(strSQL);
        vecDat.clear();
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
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp"));
            vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc"));
            vecReg.add(INT_TBL_CODTIPDOC, rst.getString("co_tipdoc"));
            vecReg.add(INT_TBL_DESTIPDOC,  rst.getString("tx_descor"));
            vecReg.add(INT_TBL_CODDOC, rst.getString("co_doc"));
            vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc"));
            vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc"));
            vecReg.add(INT_TBL_NUMDOCORI, rst.getString("tx_datdocoriguirem"));
            vecReg.add(INT_TBL_CODCLI, rst.getString("co_clides"));
            vecReg.add(INT_TBL_NOMCLI, rst.getString("tx_nomclides"));

            if( rst.getInt("ne_numvecrecdoc")>0 )
             vecReg.add(INT_TBL_CHKREP, true);
            else vecReg.add(INT_TBL_CHKREP, false);

            vecReg.add(INT_TBL_OBSREP, rst.getString("tx_obsrec"));
            vecReg.add(INT_TBL_ESTCHK, rst.getString("est"));
            
            vecReg.add(INT_TBL_BUTOBS, "");
            vecReg.add(INT_TBL_CODVEH, rst.getString("co_veh"));
            vecReg.add(INT_TBL_BUTVEH, "");
            vecReg.add(INT_TBL_DESVEH, rst.getString("tx_deslarveh"));

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


    if (intNumTotReg==tblDat.getRowCount())
        lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
    else
        lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
    pgrSis.setValue(0);
    butCon.setText("Consultar");
}
}
catch (SQLException e)
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






public void cargarBodPre(){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

        strSql="SELECT co_bod, tx_nom FROM ( " +
        " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
        " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
        " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
        " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
        " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+"  and a.st_reg='S' " +
        " ) as a";

        System.out.println(""+ strSql);
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
            txtCodBod.setText(rstLoc.getString("co_bod"));
            txtNomBod.setText(rstLoc.getString("tx_nom"));
            strCodBod=rstLoc.getString("co_bod");
            strNomBod=rstLoc.getString("tx_nom");
        }
        rstLoc.close();
        stmLoc.close();
        stmLoc=null;
        rstLoc=null;
        conn.close();
        conn=null;
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
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
        @Override
        public void run()
        {
    

             if (!cargarDetReg( sqlConFil()))
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LIN: strMsg=""; break;
                case INT_TBL_CODEMP: strMsg="Codigo de empresa."; break;
                case INT_TBL_CODLOC: strMsg="Codigo del local."; break;
                case INT_TBL_CODTIPDOC: strMsg="Codigo del tipo de documento."; break;
                case INT_TBL_DESTIPDOC: strMsg="Descripcion corta del Tipo de Documento."; break;
                case INT_TBL_CODDOC: strMsg="Codigo del documento."; break;
                case INT_TBL_NUMDOC: strMsg="Numero de Documento."; break;
                case INT_TBL_FECDOC: strMsg="Fecha del Documento."; break;
                case INT_TBL_NUMDOCORI: strMsg="Documento que origina la guia de remision."; break;
                case INT_TBL_CODCLI: strMsg="Codigo de Cliente."; break;
                case INT_TBL_NOMCLI: strMsg="Nombre de Cliente."; break;
                case INT_TBL_OBSREP: strMsg="Observacion de la recepcion."; break;
                case INT_TBL_CODVEH: strMsg="Codigo del vehiculo."; break;
                case INT_TBL_DESVEH: strMsg="Descripcion larga del vehiculo."; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
}

