

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package CxC.ZafCxC78; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
                 
/**     
 * @author  Jota
 */
public class ZafCxC78_01 extends javax.swing.JDialog {
     
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafUtil objUti;
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODCLI=1;      //CODIGO DEL ITEM 
    final int INT_TBL_CLIENTE=2;      //CODIGO DE LA EMPRESA
    final int INT_TBL_CODFORPAG=3;      //CODIGO DE LA BODEGA
    final int INT_TBL_FORPAG=4;      //NOMBRE DE LA EMPRESA
    final int INT_TBL_VALCOB=5;      //NOMBRE DE LA BODEGA
    
    private String strTit="Mensaje del Sistema Zafiro";
    
    public boolean blnAcepta = false; 
    private Vector vecDat, vecCab, vecReg;
    private ArrayList arlDat;
    private ArrayList arlReg;
 
    private String strTitu="Listado de valores cobrados por cliente y forma de pago";
    private String strVersion=" v0.01";
    
     
     private String strNomEmp, strCodEmp,strCodLoc, strNomLoc;
      
    private ZafTblOrd objTblOrd;
   
    
   private java.awt.Frame Frame;
   private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
   private ZafVenCon vcoLoc;                                   //Ventana de consulta. "Local"

   private String strFecha;
   
    public ZafCxC78_01() {
    }
    
  
    /** Creates new form pantalla dialogo */
    public ZafCxC78_01( Frame parent,   ZafParSis ZafParSis,  int CodEmp, int CodLoc, String strFecDoc) {
        super(parent, true);
        try{ 
            Frame=parent;
            this.objParSis = ZafParSis;
            objUti = new ZafUtil();

            objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            initComponents();
            strFecha = strFecDoc;
            configurarFrm(CodEmp, CodLoc,strFecDoc);       

            this.setTitle( strTitu );
            lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
    private void configurarFrm( int CodEmp, int CodLoc, String strFecDoc){
        configuraTbl();     
        configurarVenConEmp();
        cargarDat(CodEmp,CodLoc , strFecDoc);
    } 
   
    /**
     *  SELECT b4.ne_tipForPag, SUM(b3.nd_aboModCxC) --0=Negociación;1=Contado;2=Cheque;3=Crédito;4=Tarjeta de crédito
     *  QUERY PARA OBTENER EL VALOR PAGADO CON 24=INCA; 183=INCA2; 18=DEPO; 39=DEPO2; 78=DEPOC; 79=DEPOD; 174=CVDCDB; 25=RETE;  
     *                                          26=RFIV30; 27=RFIV70; 73=RETE2%; 84=RFIV100; 195=COBTCM; 216=COBTCD; 247=RFIV10; 248=RFIV20.
     * @param conExt
     * @param intCodEmp
     * @param intCodLoc
     * @return 
     */

 
    private boolean cargarDat( int CodEmp, int CodLoc, String strFecDoc){
        boolean blnRes=false;
        Statement stmLoc;
        ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            pgrSis.setIndeterminate(true);
            bntConsultar.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            vecDat=new Vector();
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                strSql="";
                strSql+=" SELECT b1.co_cli, b5.tx_nom, b1.co_forPag, b4.tx_des, SUM(b3.nd_aboModCxC) as nd_aboModCxC \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag \n";
                strSql+="       FROM tbm_cabMovInv AS a1 \n";
                strSql+="       INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                strSql+="                                          a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                strSql+="       INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSql+="       INNER JOIN tbr_locPrg as a4 ON (a1.co_emp=a4.co_empRel AND a1.co_loc=a4.co_locRel) \n";
               
                strSql+="       WHERE a1.co_tipDoc IN (1, 228) AND   \n";
                if(CodEmp>0){
                    strSql+="       a1.co_emp="+CodEmp+" AND";
                }
                if(CodLoc>0){
                    strSql+="        a1.co_loc="+CodLoc+" AND ";
                }
                strSql+="            a4.co_emp="+objParSis.getCodigoEmpresa()+" AND a4.co_loc="+objParSis.getCodigoLocal()+" AND  \n";
                strSql+="            a4.co_mnu="+objParSis.getCodigoMenu()+" AND a4.st_reg IN ('A','P') AND  \n";
                strSql+="            a1.st_reg IN ('A','R','C','F') AND a1.fe_doc>='2012/07/01' AND a3.st_ser<>'O'  \n";	
                strSql+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag  \n";
                strSql+=" ) AS b1	 \n";
                strSql+=" INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc) \n";
                strSql+=" INNER JOIN (	 \n";
                strSql+="       SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC \n";
                strSql+="       FROM tbm_cabPag AS a1 \n";
                strSql+="       INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strSql+="       INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc) \n";
                strSql+="       WHERE   \n";
                if(CodEmp>0){
                    strSql+="               a1.co_emp="+CodEmp+" AND";
                }
                strSql+="             a1.co_tipDoc IN (24, 183, 287, 18, 39, 78, 79, 174, 25, 26, 27, 73, 84, 247, 248, 195, 216) AND \n";
                strSql+="             a1.st_reg='A' "+strFecDoc+" AND a2.st_reg='A' AND a3.st_reg IN ('A','R','C','F')   \n";
                strSql+="       GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag \n";
                strSql+=" ) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag) \n";
                strSql+=" INNER JOIN tbm_cabForPag AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_forPag=b4.co_forPag) \n";
                strSql+=" INNER JOIN tbm_cli AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_cli=b5.co_cli) \n";
                strSql+=" WHERE b2.st_reg IN ('A','C') AND b5.co_empGrp IS NULL AND \n";
                strSql+="        ((b5.co_emp=1 AND b5.co_cli<>3938) OR (b5.co_emp=2 AND b5.co_cli<>3080) OR (b5.co_emp=4 AND b5.co_cli<>3370)) \n";
                strSql+=" GROUP BY b1.co_cli, b5.tx_nom, b1.co_forPag, b4.tx_des	 \n";
                strSql+=" ORDER BY b5.tx_nom \n";
                rstLoc=stmLoc.executeQuery(strSql);
                lblMsgSis.setText("Cargando datos...");
                while(rstLoc.next()){ 
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODCLI,rstLoc.getString("co_cli"));
                    vecReg.add(INT_TBL_CLIENTE,rstLoc.getString("tx_nom"));
                    vecReg.add(INT_TBL_CODFORPAG,rstLoc.getString("co_forPag"));
                    vecReg.add(INT_TBL_FORPAG,rstLoc.getString("tx_des"));
                    vecReg.add(INT_TBL_VALCOB,rstLoc.getString("nd_aboModCxC"));
                    vecDat.add(vecReg);
                }  
                rstLoc.close();
                rstLoc=null;
                objTblMod.setData(vecDat);
                tblDat .setModel(objTblMod);      
                objTblTot.calcularTotales(); 
                stmLoc.close();
                stmLoc=null;
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    
                bntConsultar.setText("Consultar");
                 
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);

              blnRes=true;
              conLoc.close();
              conLoc=null;
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
     return blnRes;          
}   



   
 
private void MensajeInf(String strMensaje){
 //JOptionPane obj =new JOptionPane();
 String strTit="Mensaje del sistema Zafiro";
 JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
}

    
    
  
  

  
private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODCLI,"Cód.Cli.");
            vecCab.add(INT_TBL_CLIENTE,"Cliente");
            vecCab.add(INT_TBL_CODFORPAG,"Cód.For.Pag.");
            vecCab.add(INT_TBL_FORPAG,"Forma de Pago");
            vecCab.add(INT_TBL_VALCOB,"Val.Cob.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);


            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CLIENTE).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_CODFORPAG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FORPAG).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_VALCOB).setPreferredWidth(100);
              
            objTblCelRenLbl3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl3.setTipoFormato(objTblCelRenLbl3.INT_FOR_NUM);
            objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblMod.setColumnDataType(INT_TBL_VALCOB, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            tcmAux.getColumn(INT_TBL_VALCOB).setCellRenderer(objTblCelRenLbl3);
           
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODCLI);
            arlColHid.add(""+INT_TBL_CODFORPAG);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
             
            int intCol[]={ INT_TBL_VALCOB};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
             
            
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }

private ZafTblTot objTblTot;                                //JTable de totales.

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpInsGenFac = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblEmp = new javax.swing.JLabel();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panTbl = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        bntConsultar = new javax.swing.JButton();
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
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 48));
        panTit.setLayout(null);

        lblTit.setText("titulo");
        panTit.add(lblTit);
        lblTit.setBounds(210, 7, 300, 14);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panFil.setMinimumSize(new java.awt.Dimension(150, 40));
        panFil.setPreferredSize(new java.awt.Dimension(100, 60));
        panFil.setLayout(null);

        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(80, 3, 56, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(138, 3, 440, 20);

        butEmp.setText("...");
        butEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                butEmpFocusLost(evt);
            }
        });
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(580, 3, 20, 20);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Empresa");
        panFil.add(lblEmp);
        lblEmp.setBounds(24, 3, 120, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Local");
        panFil.add(lblLoc);
        lblLoc.setBounds(30, 26, 120, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(80, 26, 56, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(138, 26, 440, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(580, 26, 20, 20);

        panTabGen.add(panFil, java.awt.BorderLayout.NORTH);

        panTbl.setLayout(new java.awt.BorderLayout());

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

        panTbl.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(452, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTot.setViewportView(tblTot);

        panTbl.add(spnTot, java.awt.BorderLayout.SOUTH);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        bntConsultar.setText("Consultar");
        bntConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntConsultarActionPerformed(evt);
            }
        });
        panSubBot.add(bntConsultar);

        butCan.setText("Cerrar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(641, 470));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
         
}//GEN-LAST:event_formWindowOpened

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
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
        
        if(txtCodEmp.getText().length()>0){
            configurarVenConLoc();
        }
        
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
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
        
        if(txtCodEmp.getText().length()>0){
            configurarVenConLoc();
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
         
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(1);
            }
        }
        else
        txtCodLoc.setText(strCodLoc);
        
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
        txtNomLoc.setText(strNomLoc);
         
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        if(txtCodEmp.getText().length()>0){
            mostrarVenConLoc(0);
        }
        else{
            mostrarMsgErr("Debe seleccionar una empresa");
        }
        
         
    }//GEN-LAST:event_butLocActionPerformed

    private void butEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butEmpFocusLost
        // TODO add your handling code here:
        if(txtCodEmp.getText().length()>0){
            configurarVenConLoc();
        }
        
    }//GEN-LAST:event_butEmpFocusLost

    private void bntConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntConsultarActionPerformed
        // TODO add your handling code here:
        int CodEmp=-1, CodLoc=-1;
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
            if(txtCodEmp.getText().length()>0){
                CodEmp = Integer.parseInt(txtCodEmp.getText().toString());
            }
        }
        else{
            CodEmp = objParSis.getCodigoEmpresa();
        }
        
        if(txtCodLoc.getText().length()>0){
            CodLoc = Integer.parseInt(txtCodLoc.getText().toString());
        }
        
        cargarDat(CodEmp, CodLoc,strFecha );
         
    }//GEN-LAST:event_bntConsultarActionPerformed
 
    private String strSql;
      
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
            blnAcepta=false;
            dispose();
        }
    }
    

   
    public boolean acepta(){
      return blnAcepta;
    }
 
    
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntConsultar;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butLoc;
    private javax.swing.ButtonGroup grpInsGenFac;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables

    



     private class ZafMouMotAda extends MouseMotionAdapter
    {
         @Override
        public void mouseMoved(MouseEvent evt)
        {
        int intCol=tblDat.columnAtPoint(evt.getPoint());
        String strMsg="";
        switch (intCol)
        {
            case INT_TBL_LINEA:
                strMsg="";
            break;
            
            case INT_TBL_CODCLI:
                strMsg="";
            break;
            case INT_TBL_CLIENTE:
                strMsg="Nombre del cliente";
            break; 
            case INT_TBL_FORPAG:
                strMsg="Forma de Pago";
            break;
            case INT_TBL_VALCOB:
                strMsg="Valor cobrado";
            break;
             
            default:
                strMsg=" ";
            break;
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }    
 

    
     /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Empresas".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp";
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nomLoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");
            
            if(objParSis.getCodigoEmpresa()==0){
                if(objParSis.getCodigoUsuario()==1){    
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS tx_nomLoc \n";
                    strSQL+=" FROM tbm_emp AS a1 \n";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp) \n";
                    strSQL+=" INNER JOIN tbr_locPrg as a3 ON (a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel) \n";
                    strSQL+=" WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND  ";
                    strSQL+="       a3.co_mnu="+objParSis.getCodigoMenu()+" AND a3.st_reg IN ('A','P')  \n";
                    if(txtCodEmp.getText().length()>0){
                        strSQL+=" AND a3.co_empRel="+txtCodEmp.getText()+" \n";
                    }
                    strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom  \n";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc \n";
                }
                else{    
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS tx_nomLoc \n";
                    strSQL+=" FROM tbm_emp AS a1 \n";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp) \n";
                    strSQL+=" INNER JOIN tbr_locPrg as a3 ON (a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel) \n";
                    strSQL+=" WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND  ";
                    strSQL+="       a3.co_mnu="+objParSis.getCodigoMenu()+" AND a3.st_reg IN ('A','P')  \n";
                    if(txtCodEmp.getText().length()>0){
                        strSQL+=" AND a3.co_empRel="+txtCodEmp.getText()+" \n";
                    }
                    strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom  \n";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc \n";
                }
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS tx_nomLoc \n";
                strSQL+=" FROM tbm_emp AS a1 \n";
                strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp) \n";
                strSQL+=" INNER JOIN tbr_locPrg as a3 ON (a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel) \n";
                strSQL+=" WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND  ";
                strSQL+="       a3.co_mnu="+objParSis.getCodigoMenu()+" AND a3.st_reg IN ('A','P')  \n";
                if(txtCodEmp.getText().length()>0){
                    strSQL+=" AND a3.co_empRel="+txtCodEmp.getText()+" \n";
                }
                strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom  \n";
                strSQL+=" ORDER BY a1.co_emp, a2.co_loc \n";
            }
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.setVisible(true);
                    if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nomLoc", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
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
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
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
    
    
}

