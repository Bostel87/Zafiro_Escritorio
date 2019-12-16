/*
 * ZafCom12.java
 * Created on 20 de agosto de 2005, 11:38 PM              
 */
package CxC.ZafCxC38;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;  
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafDate.ZafSelectDate;
import java.sql.Connection;
import java.sql.Statement;  
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.Date; 
import Librerias.ZafRptSis.ZafRptSis;  

//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.JasperManager;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperPrintManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.fill.JRBaseFiller;
//import net.sf.jasperreports.view.JRViewer;
//import net.sf.jasperreports.view.JasperViewer;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.util.JRLoader;
//import java.util.HashMap;
//import java.util.Map;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;

/**
 *
 * @author  Javier Ayapata  
 */
public class ZafCxC38 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_LINEA=0;                //Línea  
    final int INT_TBL_CODCLI=1;           
    final int INT_TBL_NOMCLI=2;            
    final int INT_TBL_CODLOC=3;         
    final int INT_TBL_CODTIPDOC=4;           
    final int INT_TBL_TIPDOC=5;           
    final int INT_TBL_CODDOC=6;              
    
    final int INT_TBL_NUMDOC=7;           
    final int INT_TBL_FECDOC=8;          
    final int INT_TBL_CODREG=9;            
    final int INT_TBL_DIAGRE=10;         
    final int INT_TBL_FECVEN=11;          
    final int INT_TBL_PORRET=12;              
   
    final int INT_TBL_VALDOC=13;         
    final int INT_TBL_VALPEN=14;           
    final int INT_TBL_PORVEN=15;          
    final int INT_TBL_VALVEN=16;           
    final int INT_TBL_DIAVEN=17;        
   
    
    //Variables generales.
    private ZafSelectDate objSelDat;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                //Editor: Editor del JTable.
    private ZafThreadGUICon objThrGUICon;
    
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;    //Render: Presentar JCheckBox en JTable.
    private ZafTblPopMnu objTblPopMnu;          //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con,conCab;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private ArrayList arlDat, arlReg;
    private boolean blnCon;                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    
     ZafVenCon objVenConTipdoc; //***************** 
     ZafVenCon objVenConloc; //***************** 
     ZafVenCon objVenConCli; //***************** 
     
     private Vector vecDatOrg, vecCabOrg, vecRegCom, vecRegPag, vecRegDif;
    
     Librerias.ZafUtil.ZafTipDoc             objTipDoc;
     javax.swing.JTextField txtCodtipdoc = new javax.swing.JTextField();
      private java.util.Date datFecAux;    
     
     private String titColumna[][];
     private String datoColumna[][];
     String strCodTipDoc="", strCodLoc="",strCodCli="", CodTipDoc="";
     String strNomTipDoc="", strNomLoc="",strNomCli=""; 
     java.util.Vector vecLoc;
     private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        
       String strTitCliPrv="";
       String strTitRpt="";      
       
      javax.swing.JInternalFrame jfrThis; //Hace referencia a this
      private ZafTblTot objTblTot;       
       
    /** Crea una nueva instancia de la clase ZafIndRpt. */
     public ZafCxC38(ZafParSis obj) 
    {
        objParSis=obj;
        initComponents();     
        //Inicializar objetos.
        
      
        vecDatOrg=new Vector();    //Almacena los datos
        vecCabOrg=new Vector();    //Almacena las cabeceras 
        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
      
            
         if(objParSis.getCodigoMenu()==1029){ 
             optTod.setText("Todos los Clientes");
             optCri.setText("Solo los clientes que cumplan el criterio seleccionado");
             lblItm2.setText("Cliente:");
             strTitCliPrv="Cliente:";
             strTitRpt="Listado de Documentos por Cobrar.";
            
         }else{ 
             optTod.setText("Todos los Proveedores");
             optCri.setText("Solo los Proveedores que cumplan el criterio seleccionado");
             lblItm2.setText("Proveedor:"); 
             strTitCliPrv="Proveedor:"; 
             strTitRpt="Listado de Documentos por Pagar.";
         }
        
            
          
        
    }
        
     
     
    
     public void Configura_ventana_consulta(){
            configurarVenConTipDoc();
            configurarVenConLoc();
            configurarVenConClientes();
    }
    
     
     
    private boolean configurarVenConClientes()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
         
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("440");
           
             
            //Armar la sentencia SQL.
            String  strSQL="";
           
            if(objParSis.getCodigoMenu()==1029){
                strSQL+="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper" +
                " FROM ( " +
                " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
                " FROM tbm_cli as a  " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
                " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
                " WHERE a.co_emp ="+objParSis.getCodigoEmpresa()+" and a.st_reg='A'  and a.st_cli='S' order by a.tx_nom  " +
                ") AS a";
            }else{
               
                  strSQL+="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper" +
                " FROM ( " +
                " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
                " FROM tbm_cli as a  " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
                " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
                " WHERE a.co_emp ="+objParSis.getCodigoEmpresa()+" and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom  " +
                ") AS a";
           
                  
             }
            
             
                       
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     
     
     
     private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Descripción");
              
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");
              
            //Armar la sentencia SQL.   a7.nd_stkTot,  
            String Str_Sql="";     
//                  Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
//                  " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
//                          " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
//                          " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
//                          " b.co_mnu ="+objParSis.getCodigoMenu();   
                 if(objParSis.getCodigoMenu()==1029)
                   Str_Sql="SELECT a.co_tipdoc, a.tx_descor,  a.tx_deslar FROM tbm_cabtipdoc AS a WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND  a.co_loc="+objParSis.getCodigoLocal()+"  AND  a.ne_mod in (1,3)";
                   else 
                      Str_Sql="SELECT a.co_tipdoc, a.tx_descor,  a.tx_deslar FROM tbm_cabtipdoc AS a WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND  a.co_loc="+objParSis.getCodigoLocal()+"  AND  a.ne_mod in (2,4)";
                  
                    
                   
             
                  
            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     
     
     
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_loc");
            arlCam.add("a.tx_nom");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
              
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("455");
            
            //Armar la sentencia SQL.   a7.nd_stkTot,  
            String Str_Sql="";     
            Str_Sql="select a.co_loc, a.tx_nom  from tbm_loc as a where a.co_emp= "+objParSis.getCodigoEmpresa()+ " and  st_reg='P' ";
                  
            objVenConloc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenConloc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
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
        PanCab = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtNomLarTipDoc = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        txtNomCorTipDoc = new javax.swing.JTextField();
        optTod = new javax.swing.JRadioButton();
        optCri = new javax.swing.JRadioButton();
        lblItm1 = new javax.swing.JLabel();
        txtNomLoc = new javax.swing.JTextField();
        butItm1 = new javax.swing.JButton();
        txtCodLoc = new javax.swing.JTextField();
        lblItm2 = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        butItm2 = new javax.swing.JButton();
        txtCodCli = new javax.swing.JTextField();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        chkMosRet = new javax.swing.JCheckBox();
        PanDat = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon1 = new javax.swing.JButton();
        butCon = new javax.swing.JButton();
        butCon2 = new javax.swing.JButton();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        PanCab.setPreferredSize(new java.awt.Dimension(0, 300));
        PanCab.setLayout(null);

        lblItm.setText("Tipo Doc:");
        lblItm.setToolTipText("Beneficiario");
        PanCab.add(lblItm);
        lblItm.setBounds(20, 36, 56, 20);

        txtNomLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNomLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLarTipDocActionPerformed(evt);
            }
        });
        txtNomLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLarTipDocFocusLost(evt);
            }
        });
        PanCab.add(txtNomLarTipDoc);
        txtNomLarTipDoc.setBounds(132, 36, 208, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        PanCab.add(butItm);
        butItm.setBounds(340, 36, 20, 20);

        txtNomCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNomCorTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtNomCorTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        txtNomCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCorTipDocActionPerformed(evt);
            }
        });
        txtNomCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCorTipDocFocusLost(evt);
            }
        });
        PanCab.add(txtNomCorTipDoc);
        txtNomCorTipDoc.setBounds(76, 36, 60, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los Clientes");
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
        PanCab.add(optTod);
        optTod.setBounds(20, 68, 372, 20);

        bgrFil.add(optCri);
        optCri.setText("Solo los clientes que cumplan el criterio seleccionado");
        PanCab.add(optCri);
        optCri.setBounds(20, 88, 400, 20);

        lblItm1.setText("Local:");
        lblItm1.setToolTipText("Beneficiario");
        PanCab.add(lblItm1);
        lblItm1.setBounds(20, 16, 56, 20);

        txtNomLoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        PanCab.add(txtNomLoc);
        txtNomLoc.setBounds(132, 16, 208, 20);

        butItm1.setText("...");
        butItm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItm1ActionPerformed(evt);
            }
        });
        PanCab.add(butItm1);
        butItm1.setBounds(340, 16, 20, 20);

        txtCodLoc.setBackground(objParSis.getColorCamposObligatorios());
        txtCodLoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodLoc.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        PanCab.add(txtCodLoc);
        txtCodLoc.setBounds(76, 16, 60, 20);

        lblItm2.setText("Cliente:");
        lblItm2.setToolTipText("Beneficiario");
        PanCab.add(lblItm2);
        lblItm2.setBounds(24, 112, 72, 20);

        txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
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
        PanCab.add(txtNomCli);
        txtNomCli.setBounds(160, 112, 208, 20);

        butItm2.setText("...");
        butItm2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItm2ActionPerformed(evt);
            }
        });
        PanCab.add(butItm2);
        butItm2.setBounds(368, 112, 20, 20);

        txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
        txtCodCli.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodCli.setPreferredSize(new java.awt.Dimension(25, 20));
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
        PanCab.add(txtCodCli);
        txtCodCli.setBounds(104, 112, 60, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre "));
        panNomCli.setLayout(null);

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
        txtCodAltDes.setBounds(56, 20, 188, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(252, 20, 44, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(288, 20, 208, 20);

        PanCab.add(panNomCli);
        panNomCli.setBounds(20, 132, 528, 52);

        chkMosRet.setText("Mostrar solo las Retenciones");
        PanCab.add(chkMosRet);
        chkMosRet.setBounds(450, 88, 220, 22);

        panFil.add(PanCab, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("General", panFil);

        PanDat.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

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

        jPanel2.add(spnDat, java.awt.BorderLayout.CENTER);

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

        jPanel2.add(spnTot, java.awt.BorderLayout.SOUTH);

        PanDat.add(jPanel2, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Datos", PanDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon1.setText("Imprimir");
        butCon1.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon1.setPreferredSize(new java.awt.Dimension(100, 25));
        butCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon1ActionPerformed(evt);
            }
        });
        panBot.add(butCon1);

        butCon.setText("Vista Preliminar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(100, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCon2.setText("Consultar");
        butCon2.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon2.setPreferredSize(new java.awt.Dimension(100, 25));
        butCon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon2ActionPerformed(evt);
            }
        });
        panBot.add(butCon2);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(100, 25));
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

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:  
          
        if(optTod.isSelected())
        {
            txtCodCli.setText("");
            txtNomCli.setText("");
            
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
         }
              
         
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        // TODO add your handling code here:
          if (txtCodAltHas.getText().length()>0)
              optCri.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        // TODO add your handling code here:
         if (txtCodAltDes.getText().length()>0)
        {
            optCri.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        // TODO add your handling code here:
         txtCodAltHas.selectAll(); 
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        // TODO add your handling code here:
         txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

     
      
    private void butCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon1ActionPerformed
        // TODO add your handling code here:
      if(validarCampos()){
              
        if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();  
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
      }
      
    }//GEN-LAST:event_butCon1ActionPerformed

    private void butCon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon2ActionPerformed
    // TODO add your handling code here:
      
      //if(validarCampos()){    
        if (butCon2.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUICon==null)
            {
                objThrGUICon=new ZafThreadGUICon();
                objThrGUICon.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    // }  
        
    }//GEN-LAST:event_butCon2ActionPerformed

    
    
        
    private class ZafThreadGUICon extends Thread
    {
        public void run()
        {  
             boolean blnRes=false;
             butCon2.setText("Detener");
             lblMsgSis.setText("Procesando datos...");
              pgrSis.setIndeterminate(true);
         
                if (consultarReg()){
                  
                   blnRes=true;
                 }
              
                if(blnRes==true){
                     lblMsgSis.setText("Listo");
                     pgrSis.setIndeterminate(false);
                     pgrSis.setValue(0);
                     butCon2.setText("Consultar");
                     tabFrm.setSelectedIndex(1);
                     
                }
                else{
                     lblMsgSis.setText("Error..");
                     pgrSis.setIndeterminate(false);
                     pgrSis.setValue(0);
                     butCon2.setText("Consultar");
                }
 
 
            objThrGUICon=null;
    }}
    
             
    
      
     
    
    private boolean consultarReg(){
        boolean blnRes=false;
        java.sql.Connection conn;
        
        int intCodMnu = objParSis.getCodigoMenu();
        
        String strFilAux="", strFil2Aux="";
        try{
           conn=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
           if(conn!=null){
           
               
                                        String desde1 = (txtCodAltDes.getText().equals(""))?"0":txtCodAltDes.getText().toUpperCase();
                                        String desde2 = (txtCodAltHas.getText().equals(""))?"0":txtCodAltHas.getText().toUpperCase();
                                        String desde3 = (txtCodAltHas.getText().equals(""))?"":txtCodAltHas.getText().toUpperCase();
                                        desde3 = "%"+desde3; 


                                          int intTod=1;
                                          int intCli=0;
                                          int intLoc=0;
                                          int intTipDoc=0;
                                          int intDes=1;
                                          int intEmp=1;


                                          if(!(txtCodLoc.getText().equals(""))) intLoc=1;
                                          if(!(txtCodtipdoc.getText().equals(""))) intTipDoc=1;
                                          if(!(txtCodCli.getText().equals(""))) intCli=1;

                                          if(optTod.isSelected()) {
                                             intCli=0;
                                             intDes=0;
                                          }
                                          
                                              ///FILTRO PARA MOSTRAR RETENCIONES EN CXC///
                                          if(intCodMnu == 1029)
                                          {
                                              if (chkMosRet.isSelected()) 
                                              {
                                                  strFilAux+=" AND (b1.nd_porRet > 0)";
                                                  strFil2Aux+=" AND (b.nd_porRet > 0)";
                                              }
                                              else
                                              {
                                                  strFilAux+=" AND (b1.nd_porRet is null OR b1.nd_porret = 0)";
                                                  strFil2Aux+=" AND (b.nd_porRet is null OR b.nd_porret = 0)";
                                              }
                                          }
                                              
                                          
                                          
                                    String fecdesde="2006-05-22",fechasta="2006-05-22";   
                                    int intval1=0;
                                    int intval3=0;
                                    String strFecFil="";
                                   if (objSelDat.chkIsSelected())
                                    {
                                        switch (objSelDat.getTypeSeletion())
                                        {
                                            case 1: //Búsqueda por rangos
                                                //strSQL =" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy/MM/dd") + "'";
                                                  fecdesde =   objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
                                                  fechasta =   objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
                                                  intval3=1;
                                                  strFecFil =" and fe_doc between '"+fecdesde+"' and '"+fechasta+"'";
                                                  
                                                  break;
                                            case 2: //Fechas mayores o iguales a "Desde".
                                                fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
                                                strFecFil =" and fe_doc >=  '"+fecdesde+"' ";
                                                intval3=2;
                                                break;
                                            case 3: //Fechas menores o iguales a "Hasta".
                                                fecdesde = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
                                                strFecFil =" and fe_doc <=  '"+fecdesde+"' ";
                                                intval3=3;
                                                break;
                                            case 4: //Todo.
                                                break;
                                        }  
                                    }
                  
                                          
                                          
                                          
            String strSql2 = "";
            
             if(objParSis.getCodigoMenu()==1029)
                   strSql2="SELECT a.co_tipdoc FROM tbm_cabtipdoc AS a WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND  a.co_loc="+objParSis.getCodigoLocal()+"  AND  a.ne_mod in (1,3)";
                   else 
                      strSql2="SELECT a.co_tipdoc FROM tbm_cabtipdoc AS a WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND  a.co_loc="+objParSis.getCodigoLocal()+"  AND  a.ne_mod in (2,4)";
                  
            
            
                      
            String sql=" select co_emp, co_cli,  tx_nom, sum(mo_pag) as mo_pag, sum(valor_pen) as valor_pen, sum(valor_por_ven) as valor_por_ven , sum(valor_ven) as valor_ven  from ( " +
            " SELECT current_date, empresa, local_emp,  co_cli, co_emp, co_loc, co_tipdoc, tx_nom, tx_dir  , mo_pag , valor_pen , " +
            "(" +
            " select  sum(valor_por_ven) as valor_por_ven from (" +
            " select case when (current_date-b1.fe_ven) < 0 then  abs(abs(b1.mo_pag)-abs(b1.nd_abo)) else null end  as valor_por_ven " +
            " from tbm_cabmovinv as a1" +
            " inner join tbm_pagmovinv as b1 on (b1.co_emp=a1.co_emp and b1.co_loc=a1.co_loc and b1.co_tipdoc=a1.co_tipdoc and b1.co_doc=a1.co_doc)" +
            " where a1.st_reg not in ('E','I') "+strFecFil+strFilAux+"  and  a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc  and a1.co_cli=a.co_cli and b1.st_reg in ('A','C') and  abs(b1.mo_pag)<>abs(b1.nd_abo)" +
            " ) as x ) as valor_por_ven" +
            ",( " +
            " select sum(valor_ven) as valor_ven from ( " +
            " select case when (current_date-b1.fe_ven) > 0 then  abs(abs(b1.mo_pag)-abs(b1.nd_abo)) else null end  as valor_ven " +
            " from tbm_cabmovinv as a1 " +
            " inner join tbm_pagmovinv as b1 on (b1.co_emp=a1.co_emp and b1.co_loc=a1.co_loc and b1.co_tipdoc=a1.co_tipdoc and b1.co_doc=a1.co_doc) " +
            " where a1.st_reg not in ('E','I') "+strFecFil+strFilAux+"  and   a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc  and a1.co_cli=a.co_cli and b1.st_reg in ('A','C')  and  abs(b1.mo_pag)<>abs(b1.nd_abo) " +
            " ) as x ) as valor_ven " +
            " from ( " +
            " select  distinct(a.co_cli), a.co_emp, a.co_loc, a.co_tipdoc, c.tx_nom, c.tx_dir  , emp.tx_nom as empresa, loc.tx_nom as local_emp " +
            ", abs(sum(b.mo_pag)) as mo_pag , abs(sum(abs((abs(b.mo_pag)-abs(b.nd_abo))))) as valor_pen " +
            " from tbm_cabmovinv as a  " +
            " inner join tbm_pagmovinv as b on (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc) " +
            " inner join tbm_cli as c on (c.co_emp=a.co_emp and c.co_cli=a.co_cli) " +
            " inner join tbm_emp as emp on (emp.co_emp=a.co_emp ) " +
            " inner join  tbm_loc as loc on (loc.co_emp=a.co_emp and loc.co_loc=a.co_loc) " +
            " where  a.st_reg not in ('E','I')   "+strFecFil+strFil2Aux+"  and  " +  
            " case when 1=1 then a.co_emp="+objParSis.getCodigoEmpresa()+" else a.co_emp like '%%' end   " +
            " and  " +  
            " case when 1="+intLoc+" then a.co_loc = "+((txtCodLoc.getText().equals(""))?"0":txtCodLoc.getText())+" else a.co_loc like '%%' end  " +
            " and  " +
            " case when 1="+intTipDoc+" then a.co_tipdoc = "+((txtCodtipdoc.getText().equals(""))?"0":txtCodtipdoc.getText())+" else a.co_tipdoc in ("+strSql2+")  end   " +
            " and  ";  
            
            
            
                
            
               
            
            if(!txtCodCli.getText().equals(""))
             sql +="  a.co_cli="+txtCodCli.getText()+"  and  ";
           
            sql +=" case when 1=1 then (upper(c.tx_nom) between '"+desde1+"' and '"+desde2+"' OR c.tx_nom like '"+desde3+"' )  else c.tx_nom like '%%' end  " +
              
            " and b.st_reg in ('A','C')  and  abs(b.mo_pag)<>abs(b.nd_abo) " +
            "  group by a.co_cli ,a.co_emp, a.co_loc, a.co_tipdoc , c.tx_nom, c.tx_dir , emp.tx_nom , loc.tx_nom ) as a " +
            "" +
            " ) as  x  group by co_emp, co_cli , tx_nom   order by tx_nom  " +
            " ";
             
            System.out.println("SENTENCIA SQL javier >> "+sql); 
            
            java.sql.Statement stmCab=conn.createStatement();
            java.sql.Statement stmCab2=conn.createStatement();
            java.sql.ResultSet rst2;
            java.sql.ResultSet rst = stmCab.executeQuery(sql);
                 
            double dblValDoc=0,dblValPen=0, dblValPorVen=0,dblValVen=0;
          
               
                  Vector vecData = new Vector();    
                   while(rst.next()){
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA," ");            
                        vecReg.add(INT_TBL_CODCLI, rst.getString("co_cli") );  
                        vecReg.add(INT_TBL_NOMCLI, rst.getString("tx_nom") );   
                        vecReg.add(INT_TBL_CODLOC, "" );  
                        vecReg.add(INT_TBL_CODTIPDOC, "" );  
                        vecReg.add(INT_TBL_TIPDOC, "");  
                        vecReg.add(INT_TBL_CODDOC, "");  
                        vecReg.add(INT_TBL_NUMDOC, "");  
                        vecReg.add(INT_TBL_FECDOC, "");  
                        vecReg.add(INT_TBL_CODREG, "");  
                        vecReg.add(INT_TBL_DIAGRE, "");  
                        vecReg.add(INT_TBL_FECVEN, "");  
                        vecReg.add(INT_TBL_PORRET, "");  
                        vecReg.add(INT_TBL_VALDOC, "");  
                        vecReg.add(INT_TBL_VALPEN, "");  
                        vecReg.add(INT_TBL_PORVEN, "");  
                        vecReg.add(INT_TBL_VALVEN, "");
                        vecReg.add(INT_TBL_DIAVEN, "");
                          
                        vecData.add(vecReg);         
                                    
                        sql=" SELECT * FROM ( " +   
                        " SELECT c.tx_descor, loc.tx_nom,  a.co_loc, a.co_tipdoc,  a.ne_numdoc, a.co_Doc,  b.co_reg,  a.tx_nomcli, b.fe_ven, a.fe_doc, b.ne_diacre, b.nd_porret,  abs(b.mo_pag) as mo_pag, abs(b.nd_abo) as nd_Abo , abs(abs(b.mo_pag)-abs(b.nd_abo)) as valor_pen " +
                        " ,case when (current_date-b.fe_ven) < 0 then  abs(abs(b.mo_pag)-abs(b.nd_abo)) else null end  as valor_por_ven " +
                        ",case when (current_date-b.fe_ven) > 0 then  abs(abs(b.mo_pag)-abs(b.nd_abo)) else null end  as valor_ven "+
                        " ,case when (current_date-b.fe_ven) > 0 then  (current_date-b.fe_ven)  else null end  as dias_ven "+
                        " from tbm_cabmovinv as a "+
                        " inner join  tbm_loc as loc on (loc.co_emp=a.co_emp and loc.co_loc=a.co_loc) "+
                        " inner join tbm_pagmovinv as b on (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc) "+
                        " inner join tbm_cabtipdoc as c on (c.co_emp=a.co_emp and c.co_loc=a.co_loc and c.co_tipdoc=a.co_tipdoc) "+
                        " where a.st_reg not in ('E','I')  "+strFecFil+strFil2Aux+"  and   a.co_emp="+rst.getString("co_emp")+" ";
                        
                        if(!(txtCodLoc.getText().equals(""))) 
                          sql = sql+" and a.co_loc="+txtCodLoc.getText()+"  ";
                       
                       
                         if(!(txtCodtipdoc.getText().equals(""))) 
                           sql = sql + "and a.co_tipdoc="+txtCodtipdoc.getText()+"  ";
                         else
                             sql = sql + "and a.co_tipdoc in ( "+strSql2+" ) ";
                        
                        sql = sql + "and a.co_cli="+rst.getString("co_cli")+" and b.st_reg in ('A','C') "+
                        " ) as x  where x.mo_pag<>x.nd_abo ";
                           
                        
                    //    System.out.println("SENTENCIA SQL >> "+sql); 
                           
                         rst2 = stmCab2.executeQuery(sql);
                         while(rst2.next()){
                                java.util.Vector vecReg2 = new java.util.Vector();
                                vecReg2.add(INT_TBL_LINEA," ");            
                                vecReg2.add(INT_TBL_CODCLI, "" );  
                                vecReg2.add(INT_TBL_NOMCLI, "" );   
                                vecReg2.add(INT_TBL_CODLOC,    rst2.getString("co_loc") );  
                                vecReg2.add(INT_TBL_CODTIPDOC, rst2.getString("co_tipdoc") );  
                                vecReg2.add(INT_TBL_TIPDOC, rst2.getString("tx_descor"));  
                                vecReg2.add(INT_TBL_CODDOC, rst2.getString("co_doc") );  
                                vecReg2.add(INT_TBL_NUMDOC, rst2.getString("ne_numdoc") );  
                                vecReg2.add(INT_TBL_FECDOC, rst2.getString("fe_doc") );  
                                vecReg2.add(INT_TBL_CODREG, rst2.getString("co_reg") );  
                                vecReg2.add(INT_TBL_DIAGRE, rst2.getString("ne_diacre") );  
                                vecReg2.add(INT_TBL_FECVEN, rst2.getString("fe_ven") );  
                                vecReg2.add(INT_TBL_PORRET, rst2.getString("nd_porret") );  
                                vecReg2.add(INT_TBL_VALDOC, rst2.getString("mo_pag") );  
                                vecReg2.add(INT_TBL_VALPEN, rst2.getString("valor_pen") );  
                                vecReg2.add(INT_TBL_PORVEN, rst2.getString("valor_por_ven") );  
                                vecReg2.add(INT_TBL_VALVEN, rst2.getString("valor_ven") ); 
                                vecReg2.add(INT_TBL_DIAVEN, rst2.getString("dias_ven") ); 

                                vecData.add(vecReg2);  
                         }
                                 
                                 
                        java.util.Vector vecReg3 = new java.util.Vector();
                        vecReg3.add(INT_TBL_LINEA," ");            
                        vecReg3.add(INT_TBL_CODCLI, "" );  
                        vecReg3.add(INT_TBL_NOMCLI, "" );   
                        vecReg3.add(INT_TBL_CODLOC, "" );  
                        vecReg3.add(INT_TBL_CODTIPDOC, "" );  
                        vecReg3.add(INT_TBL_TIPDOC, "");  
                        vecReg3.add(INT_TBL_CODDOC, "");  
                        vecReg3.add(INT_TBL_NUMDOC, "");  
                        vecReg3.add(INT_TBL_FECDOC, "");  
                        vecReg3.add(INT_TBL_CODREG, "");  
                        vecReg3.add(INT_TBL_DIAGRE, "");  
                        vecReg3.add(INT_TBL_FECVEN, "");  
                        vecReg3.add(INT_TBL_PORRET, "");  
                        vecReg3.add(INT_TBL_VALDOC, rst.getString("mo_pag") );  
                        vecReg3.add(INT_TBL_VALPEN, rst.getString("valor_pen") );  
                        vecReg3.add(INT_TBL_PORVEN, rst.getString("valor_por_ven") );  
                        vecReg3.add(INT_TBL_VALVEN, rst.getString("valor_ven") );  
                        vecReg3.add(INT_TBL_DIAVEN,"");
                        vecData.add(vecReg3);    
                  
                        dblValDoc=dblValDoc+rst.getDouble("mo_pag");
                        dblValPen+=rst.getDouble("valor_pen");
                        dblValPorVen+=rst.getDouble("valor_por_ven");
                        dblValVen+=rst.getDouble("valor_ven");
                          
                        
                   }
                   objTblMod.setData(vecData);
                   tblDat .setModel(objTblMod);   
                     
                   objTblTot.calcularTotales();    
                   objTblTot.setValueAt( ""+dblValDoc ,0, 13 );
                   objTblTot.setValueAt( ""+dblValPen ,0, 14 );
                   objTblTot.setValueAt( ""+dblValPorVen ,0, 15 );
                   objTblTot.setValueAt( ""+dblValVen ,0, 16 );  
                   
                  // objTblTot.setValueAt("" +  objTblTot.getValueAt(0,INT_TBL_VALVEN),0,0 ); 
                    
                   rst.close();
                   rst=null;
                   stmCab.close();
                   stmCab=null; 
                   conn.close();
                   conn=null;
                   blnRes=true;
           }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  } 
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  } 
        
        return blnRes;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        
           if (!txtNomCli.getText().equalsIgnoreCase(strNomCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                listaTipCli("a.co_cli",txtNomCli.getText(),2);
            }
        }
        else
            txtNomCli.setText(strNomCli);
        
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                listaTipCli("a.co_cli",txtCodCli.getText(),1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
         
         
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
         strNomCli=txtNomCli.getText();
         txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
         strCodCli=txtCodCli.getText();
         txtCodCli.selectAll();
         
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
         txtNomCli.transferFocus();  
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
         txtCodCli.transferFocus();  
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butItm2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItm2ActionPerformed
        // TODO add your handling code here:
        
              objVenConCli.setTitle("Listado de Clientes");         
              objVenConCli.setCampoBusqueda(1);
              objVenConCli.show();
            if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE)
            {
                txtCodCli.setText(objVenConCli.getValueAt(1));
                txtNomCli.setText(objVenConCli.getValueAt(2));
                strCodCli=objVenConCli.getValueAt(1);
                strNomCli=objVenConCli.getValueAt(2);
                 optCri.setSelected(true);
            } 
               
              
    }//GEN-LAST:event_butItm2ActionPerformed

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        // TODO add your handling code here:
        
          if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                listaTipLoc("a.tx_nom",txtNomLoc.getText(),2);  
            }
        }
        else
            txtNomLoc.setText(strNomLoc);
        
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        
          if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                listaTipLoc("a.co_loc",txtCodLoc.getText(),1);
            }
        }
        else
            txtCodLoc.setText(strCodLoc);
        
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        // TODO add your handling code here:
         strNomLoc=txtNomLoc.getText();
         txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    
    
    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
         strCodLoc=txtCodLoc.getText();
         txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    
    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        // TODO add your handling code here:
        txtNomLoc.transferFocus();  
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
          txtCodLoc.transferFocus();  
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void butItm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItm1ActionPerformed
        // TODO add your handling code here:
        // listaTipLoc("","",0);
              objVenConloc.setTitle("Listado de Clientes");         
              objVenConloc.setCampoBusqueda(1);
              objVenConloc.show();
            if (objVenConloc.getSelectedButton()==objVenConloc.INT_BUT_ACE)
            {
                 txtCodLoc.setText(objVenConloc.getValueAt(1));
                 txtNomLoc.setText(objVenConloc.getValueAt(2));
                 strCodLoc=objVenConloc.getValueAt(1);
                 strNomLoc=objVenConloc.getValueAt(2);  
              
            } 
              
              
    }//GEN-LAST:event_butItm1ActionPerformed

    private void txtNomLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLarTipDocFocusGained
        // TODO add your handling code here:
         strNomTipDoc=txtNomLarTipDoc.getText();
         txtNomLarTipDoc.selectAll();  
    }//GEN-LAST:event_txtNomLarTipDocFocusGained

    private void txtNomLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLarTipDocFocusLost
        // TODO add your handling code here:
          if (!txtNomLarTipDoc.getText().equalsIgnoreCase(strNomTipDoc))
        {
            if (txtNomLarTipDoc.getText().equals(""))
            {
                txtNomCorTipDoc.setText("");
                txtNomLarTipDoc.setText("");
            }
            else  
            {
                listaTipdoc("a.tx_deslar",txtNomLarTipDoc.getText(),2);
            }
        }
        else
            txtNomLarTipDoc.setText(strNomTipDoc);
          
    }//GEN-LAST:event_txtNomLarTipDocFocusLost

    private void txtNomCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCorTipDocFocusGained
        // TODO add your handling code here:
         strCodTipDoc=txtNomCorTipDoc.getText();
         txtNomCorTipDoc.selectAll();
        
    }//GEN-LAST:event_txtNomCorTipDocFocusGained

    private void txtNomCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCorTipDocFocusLost
        // TODO add your handling code here:
        if (!txtNomCorTipDoc.getText().equalsIgnoreCase(strCodTipDoc))
        {
            if (txtNomCorTipDoc.getText().equals(""))
            {
                txtCodtipdoc.setText("");
                txtNomCorTipDoc.setText("");
                txtNomLarTipDoc.setText("");
            }
            else
            {
                listaTipdoc("a.tx_descor",txtNomCorTipDoc.getText(),1);
            }
        }
        else
            txtNomCorTipDoc.setText(strCodTipDoc);  
       
    }//GEN-LAST:event_txtNomCorTipDocFocusLost

    private void txtNomLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLarTipDocActionPerformed
        // TODO add your handling code here:
        
         
          txtNomLarTipDoc.transferFocus();  
          
    }//GEN-LAST:event_txtNomLarTipDocActionPerformed

    private void txtNomCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCorTipDocActionPerformed
        // TODO add your handling code here:
         
          txtNomCorTipDoc.transferFocus();  
    }//GEN-LAST:event_txtNomCorTipDocActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        // TODO add your handling code here:
               listaTipdoc("","",0);
               
    }//GEN-LAST:event_butItmActionPerformed

      
    
//      private void listaTipCli(String campo,String strDesBusqueda ,int intTipo){
//              objVenConCli.setTitle("Listado de Clientes"); 
//                 switch (intTipo){
//                  case 0:
//                  break;
//                  case 1:
//                        objVenConCli.setCampoBusqueda(0);
//                        if (objVenConCli.buscar(campo, strDesBusqueda )){ }
//                  break;
//                  case 2:
//                        objVenConCli.setCampoBusqueda(1); 
//                        if (objVenConCli.buscar(campo, strDesBusqueda )){ }                
//                   break;
//                }
//                 objVenConCli.cargarDatos();
//                 objVenConCli.show();
//              if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE)
//              {
//               if(!objVenConCli.getValueAt(1).equals("")){
//                        txtCodCli.setText(objVenConCli.getValueAt(1));
//                        txtNomCli.setText(objVenConCli.getValueAt(2));
//                        strCodCli=objVenConCli.getValueAt(1);
//                        strNomCli=objVenConCli.getValueAt(2);
//                         optCri.setSelected(true);
//               }
//              }else {
//                   txtCodCli.setText(strCodCli);
//                   txtNomCli.setText(strNomCli);
//              }
//    }
//     
      
      
      
      
     public void listaTipCli(String campo,String strBusqueda,int tipo){
       objVenConCli.setTitle("Listado de Clientes"); 
        if (objVenConCli.buscar(campo, strBusqueda ))
        {
                        txtCodCli.setText(objVenConCli.getValueAt(1));
                        txtNomCli.setText(objVenConCli.getValueAt(2));
                        strCodCli=objVenConCli.getValueAt(1);
                        strNomCli=objVenConCli.getValueAt(2);
                        optCri.setSelected(true);
        }
        else  
        {     objVenConCli.setCampoBusqueda(tipo);
              objVenConCli.cargarDatos();
              objVenConCli.show();
             if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE)
            {
                   txtCodCli.setText(objVenConCli.getValueAt(1));
                   txtNomCli.setText(objVenConCli.getValueAt(2));
                   strCodCli=objVenConCli.getValueAt(1);
                   strNomCli=objVenConCli.getValueAt(2);
                   optCri.setSelected(true);
             }
              else{
                   txtCodCli.setText(strCodCli);
                   txtNomCli.setText(strNomCli);
                  }
        }
     }
    
  
    
  
  
            
      
    
     private void listaTipLoc(String campo,String strDesBusqueda ,int intTipo){
           objVenConloc.setTitle("Listado Tipos de Documentos"); 
                 switch (intTipo){
                  case 0:
                  break;
                  case 1:
                        objVenConloc.setCampoBusqueda(0);
                        if (objVenConloc.buscar(campo, strDesBusqueda )){ }
                  break;
                  case 2:
                        objVenConloc.setCampoBusqueda(1); 
                        if (objVenConloc.buscar(campo, strDesBusqueda )){ }                
                   break;
                }
                 objVenConloc.cargarDatos();
                 objVenConloc.show();
              if (objVenConloc.getSelectedButton()==objVenConloc.INT_BUT_ACE)
              {
               if(!objVenConloc.getValueAt(1).equals("")){
                      //cargarCabTipoLoc(Integer.parseInt(objVenConloc.getValueAt(1)),objVenConloc.getValueAt(2), objVenConloc.getValueAt(3) );            
                      txtCodLoc.setText(objVenConloc.getValueAt(1));
                      txtNomLoc.setText(objVenConloc.getValueAt(2)); 
                      strCodLoc=objVenConloc.getValueAt(1);
                      strNomLoc=objVenConloc.getValueAt(2);  
                     
               }
              }else {
                   txtCodLoc.setText(strCodLoc);
                   txtNomLoc.setText(strNomLoc);
              }
    }
      
     
     
          
     
         
     private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo){
           objVenConTipdoc.setTitle("Listado Tipos de Documentos"); 
                 switch (intTipo){
                  case 0:
                  break;
                  case 1:
                        objVenConTipdoc.setCampoBusqueda(1);
                        if (objVenConTipdoc.buscar(campo, strDesBusqueda )){ }
                  break;
                  case 2:
                        objVenConTipdoc.setCampoBusqueda(2); 
                        if (objVenConTipdoc.buscar(campo, strDesBusqueda )){ }                
                   break;
                }
                 objVenConTipdoc.cargarDatos();
                 objVenConTipdoc.show();
              if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
              {
               if(!objVenConTipdoc.getValueAt(1).equals("")){
                   cargarCabTipoDoc(objVenConTipdoc.getValueAt(1),objVenConTipdoc.getValueAt(2), objVenConTipdoc.getValueAt(3) );            
                  
               }
              }else {
                   txtCodtipdoc.setText(CodTipDoc);
                   txtNomCorTipDoc.setText(strCodTipDoc);
                   txtNomLarTipDoc.setText(strNomTipDoc);
              }
                 
    }
      
     
     
       
    
    private void cargarCabTipoDoc(String TipoDoc,String nomCor, String nomLar)
    {
      txtCodtipdoc.setText(TipoDoc);
      txtNomCorTipDoc.setText(nomCor);
      txtNomLarTipDoc.setText(nomLar);
      CodTipDoc= TipoDoc;
      strCodTipDoc=nomCor;
      strNomTipDoc=nomLar;
      
    }
         
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
         if (!configurarFrm())
            exitForm();
    }//GEN-LAST:event_formInternalFrameOpened

   
     
    private boolean validarCampos(){
        boolean blnres=true;
        
        if(txtCodLoc.getText().equals("")){
            MensajeInf("Escoja el Local.");
            return false;
        }
        if(txtNomCorTipDoc.getText().equals("")){
            MensajeInf("Escoja el tipo de documento.");
            return false;  
        }
        
        return blnres;
    }
    
       
   
     private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
       }
     
     
    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
     
      //  if(validarCampos()){
        
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
               
       // }    
          //  return true;
        
        
           /*
           Connection conIns;
        try
        {
             conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        try
        {
             if(conIns!=null){
                
                String DIRECCION_REPORTE="C://Zafiro//Reportes_impresos//cab.jrxml"; 
                String DIRECCION_REPORTE_DETALLE="C://Zafiro//Reportes_impresos//sub.jrxml"; 
               // String DIRECCION_REPORTE_DETALLE2="C://Zafiro//Reportes_impresos//sub2.jrxml"; 
               
                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
               // JasperDesign jasperDesign3 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE2);
                 
                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
              //  JasperReport subReport2 = JasperManager.compileReport(jasperDesign3);
                 
                    
//                // Second, create a map of parameters to pass to the report.
                String desde1 = (txtCodAltDes.getText().equals(""))?"0":txtCodAltDes.getText().toUpperCase();
                String desde2 = (txtCodAltHas.getText().equals(""))?"0":txtCodAltHas.getText().toUpperCase();
                String desde3 = (txtCodAltHas.getText().equals(""))?"":txtCodAltHas.getText().toUpperCase();
                desde3 = "%"+desde3;
              
                 
                  int intTod=1;
                  int intCli=0;
                  int intLoc=0;
                  int intTipDoc=0;
                  int intDes=1;
                  int intEmp=1;
                  
                  
                  if(!(txtCodLoc.getText().equals(""))) intLoc=1;
                  if(!(txtCodtipdoc.getText().equals(""))) intTipDoc=1;
                  if(!(txtCodCli.getText().equals(""))) intCli=1;
                  
                  if(optTod.isSelected()) {
                     intCli=0;
                     intLoc=0;
                     intTipDoc=0;
                     intDes=0;
                  }
                           
                    
                  System.out.println(" <<>> "+intCli );
                  
                  
                      
                  String strTitCliPrv="";    
                  if(objParSis.getCodigoMenu()==1029)  strTitCliPrv="Cliente:";
                   else strTitCliPrv="Proveedor:"; 
                        
                      
                   Map parameters = new HashMap();    
                   parameters.put("co_emp", new String(String.valueOf(objParSis.getCodigoEmpresa())) );
                   parameters.put("co_loc", txtCodLoc.getText() );
                   parameters.put("co_tipdoc", txtCodtipdoc.getText() );
                   parameters.put("co_cli", txtCodCli.getText() );
                   
                   parameters.put("intCli", new Integer(intCli) );
                   parameters.put("intLoc", new Integer(intLoc) );
                   parameters.put("intTipDoc", new Integer(intTipDoc) );
                   parameters.put("intDes", new Integer(intDes) );
                   parameters.put("intEmp", new Integer(intEmp) );
                       
                   parameters.put("CodCli", ((txtCodCli.getText().equals(""))?"0":txtCodCli.getText()) );
                   parameters.put("CodLoc", txtCodLoc.getText() );
                   parameters.put("CodEmp", String.valueOf(objParSis.getCodigoEmpresa())  );  
                   parameters.put("CodTipDoc", txtCodtipdoc.getText() );
                     
                   parameters.put("numdoc1", desde1 );
                   parameters.put("numdoc2", desde2 );
                   parameters.put("numdoc3", desde3 );
                  
                   parameters.put("TitCliPrv", strTitCliPrv );
                  
                   
                   parameters.put("SUBREPORT", subReport); 
                 //  parameters.put("SUBREPORT2", subReport2);  
                        
                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
                   
                  JasperViewer.viewReport(report, false);
            }
        }
        catch (JRException e)
        {
           System.out.println("Excepción: " + e.toString());
        }
          }

        catch(SQLException ex)
        {
            System.out.println("Error al conectarse a la base");
        }    
        
        
    */

         
    }//GEN-LAST:event_butConActionPerformed

     
    
    
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
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    //objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                   // objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                      System.out.println("Evento 2 ");
                   // objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   // objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
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

    
    
    
//    private class ZafThreadGUI extends Thread
//    {
//        public void run()
//        {
//          
//           butCon.setText("Detener"); 
//           lblMsgSis.setText("Procesando datos...");
//           pgrSis.setIndeterminate(true);
//           
//            objThrGUI=null;
//        }
//    }
    
     
    
     
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
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.show();
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:    
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.      
                                
                                
                                        String desde1 = (txtCodAltDes.getText().equals(""))?"0":txtCodAltDes.getText().toUpperCase();
                                        String desde2 = (txtCodAltHas.getText().equals(""))?"0":txtCodAltHas.getText().toUpperCase();
                                        String desde3 = (txtCodAltHas.getText().equals(""))?"":txtCodAltHas.getText().toUpperCase();
                                        desde3 = "%"+desde3; 

                            //********************************************************************************************************    
                                       String fecdesde="2006-05-22",fechasta="2006-05-22";   
                                       int intval1=0;
                                       int intval3=0;  
                                        String strFecFil="";
                                       if (objSelDat.chkIsSelected())
                                        {
                                            intval1=1;
                                            switch (objSelDat.getTypeSeletion())  
                                            {
                                                case 1: //Búsqueda por rangos
                                                    //strSQL =" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy/MM/dd") + "'";
                                                      fecdesde =   objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
                                                      fechasta =   objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
                                                      intval3=1;
                                                      strFecFil =" and fe_doc between '"+fecdesde+"' and '"+fechasta+"'";

                                                      break;
                                                case 2: //Fechas mayores o iguales a "Desde". 
                                                    fecdesde =  objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy","yyyy-MM-dd");
                                                    strFecFil =" and fe_doc >=  '"+fecdesde+"' ";
                                                    intval3=2;
                                                    break;
                                                case 3: //Fechas menores o iguales a "Hasta".
                                                    fecdesde = objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy","yyyy-MM-dd");
                                                    strFecFil =" and fe_doc <=  '"+fecdesde+"' ";
                                                    intval3=3;
                                                    break;
                                                case 4: //Todo.
                                                    break;
                                            }    
                                        }
                                //********************************************************************************************************    
                            
                                              
                                          int intTod=1;
                                          int intCli=0;
                                          int intLoc=0;
                                          int intTipDoc=0;
                                          int intDes=1;
                                          int intEmp=1;


                                          if(!(txtCodLoc.getText().equals(""))) intLoc=1;
                                          if(!(txtCodtipdoc.getText().equals(""))) intTipDoc=1;
                                          if(!(txtCodCli.getText().equals(""))) intCli=1;

                                          if(optTod.isSelected()) {
                                             intCli=0;
                                             intDes=0;
                                          }
                                             
                                                 
                                                 
                                                 
                             System.out.println(">>> "+ objParSis.getCodigoMenu() );
                                              
                                               
                                                 
                                java.util.Map parameters=new java.util.HashMap();    
                                parameters.put("SUBREPORT", strRutRpt);
                                parameters.put("co_emp", new String(String.valueOf(objParSis.getCodigoEmpresa())) );
                               parameters.put("co_loc", ((txtCodLoc.getText().equals(""))?"0":txtCodLoc.getText())  );
                               parameters.put("co_tipdoc", ((txtCodtipdoc.getText().equals(""))?"0":txtCodtipdoc.getText())   );
                               parameters.put("co_cli", ((txtCodCli.getText().equals(""))?"0":txtCodCli.getText())  );

                               parameters.put("intCli", new Integer(intCli) );  
                               parameters.put("intLoc", new Integer(intLoc) );
                               parameters.put("intTipDoc", new Integer(intTipDoc) );
                               parameters.put("intDes", new Integer(intDes) );
                               parameters.put("intEmp", new Integer(intEmp) );

                               parameters.put("CodCli", ((txtCodCli.getText().equals(""))?"0":txtCodCli.getText()) );
                               parameters.put("CodLoc", ((txtCodLoc.getText().equals(""))?"0":txtCodLoc.getText())   );
                               parameters.put("CodEmp", String.valueOf(objParSis.getCodigoEmpresa())  );  
                               parameters.put("CodTipDoc", ((txtCodtipdoc.getText().equals(""))?"0":txtCodtipdoc.getText())  );

                               parameters.put("numdoc1", desde1 );
                               parameters.put("numdoc2", desde2 );
                               parameters.put("numdoc3", desde3 );    

                               parameters.put("TitCliPrv", strTitCliPrv ); 
                               parameters.put("strTitRpt", strTitRpt ); 

                               parameters.put("intFec",  new Integer(intval1) ); 
                               parameters.put("intRanFec",  new Integer(intval3) ); 
                               parameters.put("fecdesde", fecdesde ); 
                               parameters.put("fechasta", fechasta ); 
                               parameters.put("CodMnu", new Integer(objParSis.getCodigoMenu()) ); 
                              
                               
                               
                                parameters.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, parameters, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanCab;
    private javax.swing.JPanel PanDat;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCon1;
    private javax.swing.JButton butCon2;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butItm1;
    private javax.swing.JButton butItm2;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItm1;
    private javax.swing.JLabel lblItm2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optCri;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCorTipDoc;
    private javax.swing.JTextField txtNomLarTipDoc;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */   
    private boolean configurarFrm()        
    {
        boolean blnRes=true;
        int intCodMnu = objParSis.getCodigoMenu();
        
        try
        {          
            objSelDat=new ZafSelectDate(new javax.swing.JFrame(),"d/m/y");
            PanCab.add(objSelDat);
            //objSelDat.setBounds(20, 30, 560, 100);    
            //objSelDat.setBounds(123, 30, 560, 100);    
            objSelDat.setBounds(25, 190, 560, 100);
            
            objSelDat.chkSetSelected(true);
            
            chkMosRet.setVisible(false);
            
            //PARA MOSTRAR FLTRO DE RETENCIONES//
            if(intCodMnu == 1029)
                chkMosRet.setVisible(true);
               
            //Inicializar objetos.  
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();    
            this.setTitle(strAux + " v 1.5 ");
            lblTit.setText(strAux);  
            
                objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
              
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA," ");            
            vecCab.add(INT_TBL_CODCLI,"Cod.Cli.");  
            vecCab.add(INT_TBL_NOMCLI,"Nombre");  
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");  
            vecCab.add(INT_TBL_CODTIPDOC,"Cod.Tip.Doc.");  
            vecCab.add(INT_TBL_TIPDOC,"Tip.Doc.");  
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");  
            
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");  
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");  
            vecCab.add(INT_TBL_CODREG,"Cod.Reg.");  
            vecCab.add(INT_TBL_DIAGRE,"Dia.Cre.");  
            
            vecCab.add(INT_TBL_FECVEN,"Fec.Ven.");  
            vecCab.add(INT_TBL_PORRET,"Por.Ret.");  
            vecCab.add(INT_TBL_VALDOC,"Val.Doc.");  
            vecCab.add(INT_TBL_VALPEN,"Val.Pen.");  
            vecCab.add(INT_TBL_PORVEN,"Por.Ven.");  
            vecCab.add(INT_TBL_VALVEN,"Val.Ven.");  
            vecCab.add(INT_TBL_DIAVEN,"Dia.Ven.");  
         
            
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();    
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            tblDat.setRowSelectionAllowed(false);
            tblDat.setCellSelectionEnabled(true);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDat.getTableHeader().setReorderingAllowed(false);       
           
            objColNum=new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);         
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(60);
    
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODREG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DIAGRE).setPreferredWidth(60);
           
            tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PORRET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
             
            tcmAux.getColumn(INT_TBL_PORVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DIAVEN).setPreferredWidth(50);    
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl(); 
            
//            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//            objTblCelRenLbl.setFormatoNumerico("######",true,true);
//            
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_PORRET).setCellRenderer(objTblCelRenLbl);        
            tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);            
            tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);            
            tcmAux.getColumn(INT_TBL_PORVEN).setCellRenderer(objTblCelRenLbl);            
            tcmAux.getColumn(INT_TBL_VALVEN).setCellRenderer(objTblCelRenLbl);            
         
            
            objTblCelRenLbl=null;  
            tcmAux=null;
            
            new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
              
              
            int intCol[]={ INT_TBL_VALDOC, INT_TBL_VALPEN,  INT_TBL_PORVEN, INT_TBL_VALVEN };
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
              
            
          //  setEditable(false);
            cargaTipoDocPredeterminado();
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes; 
    }

    
    
    
    
     private void cargaTipoDocPredeterminado()
    {
        objTipDoc.DocumentoPredeterminado();
        txtCodtipdoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomCorTipDoc.setText(objTipDoc.gettx_descor());
        txtNomLarTipDoc.setText(objTipDoc.gettx_deslar());
        strCodTipDoc=objTipDoc.gettx_descor();
        strNomTipDoc=objTipDoc.gettx_deslar();
    }
    
      
  
    public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
       
   }

    
}
