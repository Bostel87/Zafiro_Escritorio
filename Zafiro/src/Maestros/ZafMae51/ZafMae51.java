/*
 * ZafVen25.java
 *
 * Created on 06 de Agosto de 2008 st_recMerDev
 */ 

package Maestros.ZafMae51;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
   
/**
 *
 * @author  jayapata
 */
public class ZafMae51 extends javax.swing.JInternalFrame {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    mitoolbar objTooBar;
    ZafUtil objUti; /**/
    String strAux="";
    java.sql.ResultSet rstCab;
    java.sql.Statement STM_GLO;
    java.sql.Connection CONN_GLO=null;
    ZafVenCon objVenConGrp, objVenConPrv;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod  objTblModPrv;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut  objTblCelRenButPrv;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblCelRenChkP,objTblCelRenChkA, objTblCelRenChkI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblCelEdiChkP,objTblCelEdiChkA, objTblCelEdiChkI;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;


    String strCodGrp="";
    String strDesGrp="";

   // DATOS DE LA TABLA DE PROVEEDORES
   final int INT_TBL_PRVLIN=0;
   final int INT_TBL_PRVCODCLI=1;
   final int INT_TBL_PRVBUTCLI=2;
   final int INT_TBL_PRVNOMCLI=3;
   final int INT_TBL_PRVACT=4;
   final int INT_TBL_PRVINA=5;
   final int INT_TBL_PRVPRE=6;
   final int INT_TBL_CODREGPRV=7;
   //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODPRV   = 0;
   //****************************

    /** Creates new form ZafCom33 */
    public ZafMae51(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
	    
         this.setTitle(objZafParSis.getNombreMenu()+"  v 0.5 "); /**/
         lblTit.setText(objZafParSis.getNombreMenu());  /**/

         if( objZafParSis.getTipGrpClaInvPreUsr()=='P')
           radPub.setSelected(true);

         if( objZafParSis.getTipGrpClaInvPreUsr()=='R')
           radPri.setSelected(true);

         

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");
            
	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }
    
    
 

private void ConfigurarTblProveedores() {
  try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
    vecCab.add(INT_TBL_PRVLIN,"");
    vecCab.add(INT_TBL_PRVCODCLI,"Cod.Cli");
    vecCab.add(INT_TBL_PRVBUTCLI,"..");
    vecCab.add(INT_TBL_PRVNOMCLI,"Nom.Cli.");
    vecCab.add(INT_TBL_PRVACT,"Activo");
    vecCab.add(INT_TBL_PRVINA,"Inactivo");
    vecCab.add(INT_TBL_PRVPRE,"Predeterminado");
    vecCab.add(INT_TBL_CODREGPRV,"Cod.Reg.Prv");

    objTblModPrv=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModPrv.setHeader(vecCab);
    tblPrv.setModel(objTblModPrv);
    tblPrv.setRowSelectionAllowed(false);
    tblPrv.setCellSelectionEnabled(true);
    tblPrv.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    new Librerias.ZafColNumerada.ZafColNumerada(tblPrv,INT_TBL_PRVLIN);

    //tblPrv.getModel().addTableModelListener(new LisCambioTblAcc());
    tblPrv.getTableHeader().setReorderingAllowed(false);

    tblPrv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblPrv.getColumnModel();

    tcmAux.getColumn(INT_TBL_PRVLIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_PRVCODCLI).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_PRVBUTCLI).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_PRVNOMCLI).setPreferredWidth(390);
    tcmAux.getColumn(INT_TBL_PRVACT).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PRVINA).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PRVPRE).setPreferredWidth(50);


    objTblCelRenChkP = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblCelRenChkA = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblCelRenChkI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();

    tcmAux.getColumn(INT_TBL_PRVPRE).setCellRenderer(objTblCelRenChkP);
    tcmAux.getColumn(INT_TBL_PRVACT).setCellRenderer(objTblCelRenChkA);
    tcmAux.getColumn(INT_TBL_PRVINA).setCellRenderer(objTblCelRenChkI);

    objTblCelEdiChkP = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PRVPRE).setCellEditor(objTblCelEdiChkP);

    objTblCelEdiChkA = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PRVACT).setCellEditor(objTblCelEdiChkA);

    objTblCelEdiChkI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PRVINA).setCellEditor(objTblCelEdiChkI);

    objTblCelEdiChkP.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblPrv.getSelectedRow();
            for(int x=0; x<tblPrv.getRowCount(); x++){
                tblPrv.setValueAt(new Boolean(false), x, INT_TBL_PRVPRE);
             }
            tblPrv.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVPRE);
            tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVACT);
            tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVINA);

        }
    });


      objTblCelEdiChkA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblPrv.getSelectedRow();
              tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVPRE);
              tblPrv.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVACT);
              tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVINA);
         }
    });



            objTblCelEdiChkI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblPrv.getSelectedRow();
                      tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVPRE);
                      tblPrv.setValueAt(new Boolean(false), intNumFil, INT_TBL_PRVACT);
                      tblPrv.setValueAt(new Boolean(true), intNumFil, INT_TBL_PRVINA);

                }
            });



    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_PRVCODCLI);
    vecAux.add("" + INT_TBL_PRVBUTCLI);
    vecAux.add("" + INT_TBL_PRVPRE);
    vecAux.add("" + INT_TBL_PRVACT);
    vecAux.add("" + INT_TBL_PRVINA);
    objTblModPrv.setColumnasEditables(vecAux);
    vecAux=null;

    java.util.ArrayList arlAux=new java.util.ArrayList();
    //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
    arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL_CODREGPRV);
    objTblModPrv.setColsSaveBeforeRemoveRow(arlAux);
    arlAux=null;

      ArrayList arlColHid=new ArrayList();
      arlColHid.add(""+INT_TBL_CODREGPRV);
      objTblModPrv.setSystemHiddenColumns(arlColHid, tblPrv);
      arlColHid=null;

    int intColVen3[]=new int[2];
    intColVen3[0]=1;
    intColVen3[1]=2;
    int intColTbl3[]=new int[2];
    intColTbl3[0]=INT_TBL_PRVCODCLI;
    intColTbl3[1]=INT_TBL_PRVNOMCLI;
    objTblCelEdiTxtVcoPrv=new ZafTblCelEdiTxtVco(tblPrv, objVenConPrv, intColVen3, intColTbl3);  //********
    tcmAux.getColumn(INT_TBL_PRVCODCLI).setCellEditor(objTblCelEdiTxtVcoPrv);  //******
    objTblCelEdiTxtVcoPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblPrv.getSelectedRow();
            tblPrv.setValueAt(new Boolean(true),intNumFil, INT_TBL_PRVACT);
       }
    });

    objTblCelRenButPrv=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_PRVBUTCLI).setCellRenderer(objTblCelRenButPrv);
    objTblCelRenButPrv=null;
    new ButPrv(tblPrv, INT_TBL_PRVBUTCLI);   //*****



    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblPrv);
    tcmAux=null;
    objTblModPrv.setModoOperacion(objTblModPrv.INT_TBL_INS);

    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblPrv);

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
}


private class ButPrv extends Librerias.ZafTableColBut.ZafTableColBut_uni{
 public ButPrv(javax.swing.JTable tbl, int intIdx){
 super(tbl,intIdx, "Cotización.");
}
public void butCLick() {
   int intNumFil = tblPrv.getSelectedRow();

   if(intNumFil >= 0 )
    listaProveedor(intNumFil );

}
}

private void listaProveedor(int intNumFil){
 objVenConPrv.setTitle("Listado de Proveedores");
 objVenConPrv.show();
 if (objVenConPrv.getSelectedButton()==objVenConPrv.INT_BUT_ACE) {
    tblPrv.setValueAt(objVenConPrv.getValueAt(1),intNumFil ,INT_TBL_PRVCODCLI);
    tblPrv.setValueAt(objVenConPrv.getValueAt(2),intNumFil, INT_TBL_PRVNOMCLI);
    tblPrv.setValueAt(new Boolean(true),intNumFil, INT_TBL_PRVACT);
 }
}


private boolean configurarVenConProveedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a1.co_cli");
        arlCam.add("a1.tx_nom");
        arlCam.add("a1.tx_dir");
        arlCam.add("a1.tx_ide");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Prv.");
        arlAli.add("Dirección");
        arlAli.add("RUC/CI");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("190");
        arlAncCol.add("220");
        arlAncCol.add("80");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL+="select a1.co_cli,a1.tx_nom,a1.tx_dir,a1.tx_ide  FROM " +
        " tbr_cliloc AS a " +
        " INNER JOIN tbm_cli as a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli)   " +
        " where a1.st_reg in('A','N')  AND   a1.st_prv = 'S' and a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" order by a1.tx_nom";

        objVenConPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }
    catch (Exception e) {
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}

private boolean configurarVenConGrp() {
 boolean blnRes=true;
 try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("co_grp");
    arlCam.add("tx_descor");
    arlCam.add("tx_deslar");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Cód.Grp");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("110");
    arlAncCol.add("350");

  //Armar la sentencia SQL.   a7.nd_stkTot,
  String Str_Sql="";
  Str_Sql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A' ";
  objVenConGrp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
  arlCam=null;
  arlAli=null;
  arlAncCol=null;

 }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

    
  


public void abrirCon(){
try{
    CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
}
catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}




public void CerrarCon(){
try{
    CONN_GLO.close();
    CONN_GLO=null;
}
catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}


    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpRdaBut = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblCodDoc1 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblCodDoc2 = new javax.swing.JLabel();
        lblCodDoc4 = new javax.swing.JLabel();
        txtDesLar = new javax.swing.JTextField();
        txtDesCor = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtCodGrp = new javax.swing.JTextField();
        txtDesGrp = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        lbltipgrp = new javax.swing.JLabel();
        radPub = new javax.swing.JRadioButton();
        radPri = new javax.swing.JRadioButton();
        panPrv = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrv = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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
                formInternalFrameOpened(evt);
            }
        });

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(null);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panGenTabGen.add(lblObs1);
        lblObs1.setBounds(10, 130, 92, 20);

        lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc1.setText("Código:"); // NOI18N
        panGenTabGen.add(lblCodDoc1);
        lblCodDoc1.setBounds(10, 20, 60, 20);

        txtCod.setBackground(objZafParSis.getColorCamposSistema());
        panGenTabGen.add(txtCod);
        txtCod.setBounds(110, 20, 90, 20);

        lblCodDoc2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc2.setText("Descripción Larga:"); // NOI18N
        panGenTabGen.add(lblCodDoc2);
        lblCodDoc2.setBounds(10, 100, 100, 30);

        lblCodDoc4.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc4.setText("Descripción Corta:"); // NOI18N
        panGenTabGen.add(lblCodDoc4);
        lblCodDoc4.setBounds(10, 80, 120, 20);

        txtDesLar.setBackground( objZafParSis.getColorCamposObligatorios());
        panGenTabGen.add(txtDesLar);
        txtDesLar.setBounds(110, 100, 400, 20);
        panGenTabGen.add(txtDesCor);
        txtDesCor.setBounds(110, 80, 90, 20);

        txtObs.setLineWrap(true);
        jScrollPane5.setViewportView(txtObs);

        panGenTabGen.add(jScrollPane5);
        jScrollPane5.setBounds(110, 120, 400, 44);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Grupo:"); // NOI18N
        panGenTabGen.add(jLabel2);
        jLabel2.setBounds(10, 60, 90, 20);

        txtCodGrp.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panGenTabGen.add(txtCodGrp);
        txtCodGrp.setBounds(110, 60, 70, 20);

        txtDesGrp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesGrpActionPerformed(evt);
            }
        });
        txtDesGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesGrpFocusLost(evt);
            }
        });
        panGenTabGen.add(txtDesGrp);
        txtDesGrp.setBounds(180, 60, 310, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panGenTabGen.add(butSol);
        butSol.setBounds(490, 60, 20, 20);

        lbltipgrp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lbltipgrp.setText("Tipo de grupo:");
        panGenTabGen.add(lbltipgrp);
        lbltipgrp.setBounds(10, 40, 100, 20);

        grpRdaBut.add(radPub);
        radPub.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPub.setText("Público");
        radPub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPubActionPerformed(evt);
            }
        });
        panGenTabGen.add(radPub);
        radPub.setBounds(110, 40, 70, 20);

        grpRdaBut.add(radPri);
        radPri.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPri.setText("Privado");
        radPri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPriActionPerformed(evt);
            }
        });
        panGenTabGen.add(radPri);
        radPri.setBounds(180, 40, 110, 20);

        tabGen.addTab("General", panGenTabGen);

        panPrv.setLayout(new java.awt.BorderLayout());

        tblPrv.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblPrv);

        panPrv.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Proveedores", panPrv);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
	// TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
	// TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
           // cerrarObj();
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	// TODO add your handling code here:
	   configurarVenConGrp();
       configurarVenConProveedor();

       ConfigurarTblProveedores();
       
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        // TODO add your handling code here:
        txtCodGrp.transferFocus();
}//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
        strCodGrp=txtCodGrp.getText();
        txtCodGrp.selectAll();
}//GEN-LAST:event_txtCodGrpFocusGained

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        // TODO add your handling code here:
        if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp)) {
            if(txtCodGrp.getText().equals("")) {
                txtCodGrp.setText("");
                txtDesGrp.setText("");
            }else
                BuscarGrp("co_grp",txtCodGrp.getText(),0);
        }else
            txtCodGrp.setText(strCodGrp);
}//GEN-LAST:event_txtCodGrpFocusLost

    private void txtDesGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesGrpActionPerformed
        // TODO add your handling code here:
        txtDesGrp.transferFocus();
}//GEN-LAST:event_txtDesGrpActionPerformed

    private void txtDesGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesGrpFocusGained
        // TODO add your handling code here:
        strDesGrp=txtDesGrp.getText();
        txtDesGrp.selectAll();
}//GEN-LAST:event_txtDesGrpFocusGained

    private void txtDesGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesGrpFocusLost
        // TODO add your handling code here:
        if (!txtDesGrp.getText().equalsIgnoreCase(strDesGrp)) {
            if(txtDesGrp.getText().equals("")) {
                txtCodGrp.setText("");
                txtDesGrp.setText("");
            }else
                BuscarGrp("tx_deslar",txtDesGrp.getText(),2);
        }else
            txtDesGrp.setText(strDesGrp);
}//GEN-LAST:event_txtDesGrpFocusLost

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
        // TODO add your handling code here:

      String strSql="";
      if(radPub.isSelected())
        strSql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A'" +
        " and tx_tipgrp='P' ";

     if(radPri.isSelected())
       strSql="SELECT a1.co_grp, a1.tx_descor, a1.tx_deslar FROM tbr_grpclainvusr as a " +
       " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
       " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.st_reg='A' and a.co_usr="+objZafParSis.getCodigoUsuario()+" ";

        objVenConGrp.setTitle("Listado de Clientes");
        objVenConGrp.setSentenciaSQL(strSql);
        objVenConGrp.setCampoBusqueda(1);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
        }
}//GEN-LAST:event_butSolActionPerformed

    private void radPubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPubActionPerformed
        // TODO add your handling code here:
         txtCodGrp.setText("");
         txtDesGrp.setText("");

    }//GEN-LAST:event_radPubActionPerformed

    private void radPriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPriActionPerformed
        // TODO add your handling code here:
        txtCodGrp.setText("");
         txtDesGrp.setText("");
    }//GEN-LAST:event_radPriActionPerformed



 public void BuscarGrp(String campo,String strBusqueda,int tipo){
  objVenConGrp.setTitle("Listado de Vendedores");

  String strSql="";
   if(radPub.isSelected())
      strSql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A'" +
      " and tx_tipgrp='P' ";

  if(radPri.isSelected())
     strSql="SELECT a1.co_grp, a1.tx_descor, a1.tx_deslar FROM tbr_grpclainvusr as a " +
     " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.st_reg='A' and a.co_usr="+objZafParSis.getCodigoUsuario()+" ";

  objVenConGrp.setSentenciaSQL(strSql);
  objVenConGrp.cargarDatos();
  
  if(objVenConGrp.buscar(campo, strBusqueda )) {
      txtCodGrp.setText(objVenConGrp.getValueAt(1));
      txtDesGrp.setText(objVenConGrp.getValueAt(3));
  }else{
        objVenConGrp.setCampoBusqueda(tipo);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
        }else{
            txtCodGrp.setText(strCodGrp);
            txtDesGrp.setText(strDesGrp);
  }}}



private void bloquea(javax.swing.JTextField txtFiel,  boolean blnEst){
    java.awt.Color colBack = txtFiel.getBackground();
    txtFiel.setEditable(blnEst);
    txtFiel.setBackground(colBack);
}



    
  
    
    
 
     
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butSol;
    private javax.swing.ButtonGroup grpRdaBut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblCodDoc1;
    private javax.swing.JLabel lblCodDoc2;
    private javax.swing.JLabel lblCodDoc4;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lbltipgrp;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrv;
    private javax.swing.JRadioButton radPri;
    private javax.swing.JRadioButton radPub;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblPrv;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtDesCor;
    private javax.swing.JTextField txtDesGrp;
    private javax.swing.JTextField txtDesLar;
    private javax.swing.JTextArea txtObs;
    // End of variables declaration//GEN-END:variables
    
      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
      
      

public class mitoolbar extends ZafToolBar{
 public mitoolbar(javax.swing.JInternalFrame jfrThis){
    super(jfrThis, objZafParSis);
 }

	
public boolean anular() {
  boolean blnRes=false;
  java.sql.Connection  conn;
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       
        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            blnRes=true;
        }
        if (strAux.equals("Anulado")) {
            MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
            blnRes=true;
        }
        

        if(anularReg(conn)){
            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Anulado");
            
        }else conn.rollback();
        
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

	
        
        
private boolean anularReg(java.sql.Connection conn){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmEmp;
  java.sql.ResultSet rstEmp;
  String strSql=""; 
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

     stmEmp=conn.createStatement();

    strSql="SELECT co_emp FROM tbm_emp   ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){
 
         strSql="UPDATE tbm_clainv SET st_regrep='M',  st_reg='I', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrmod="+objZafParSis.getCodigoUsuario()+" " +
         " WHERE co_emp="+rstEmp.getInt("co_emp")+" AND  co_cla="+txtCod.getText()+" ";
         strSql+=" ; UPDATE tbr_prvclainv SET st_regrep='M',  st_reg='I' WHERE   co_cla="+txtCod.getText()+" ";
         stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;

   stmLoc.close();
   stmLoc=null;
   stmEmp.close();
   stmEmp=null;

     blnRes=true;
  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

        
     
	
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }
        
	
	
public void clickAnterior() {
 try{
  if(rstCab != null ) {
     abrirCon();
     if(!rstCab.isFirst()) {
        
               rstCab.previous();
               refrescaDatos(CONN_GLO, rstCab);
       }
     CerrarCon();
  }} catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}
        


        
        public void clickAnular() {

        }
        
        
        public void clickConsultar() {
            clnTextos();
         
	}
        
   
        
        
        
public void clickFin(){
 try{
    if(rstCab != null ){
     abrirCon();
      if(!rstCab.isLast()){
            rstCab.last();
            refrescaDatos(CONN_GLO, rstCab);
      }
    CerrarCon();
   }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}
        

 



public void clickInicio(){
 try{
   if(rstCab != null ){
     abrirCon();
     if(!rstCab.isFirst()) {
            rstCab.first();
            refrescaDatos(CONN_GLO, rstCab);
       }
    CerrarCon();
  }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
}




public void clickInsertar() {
 try{
    clnTextos();

    java.awt.Color colBack;
    colBack = txtCod.getBackground();
    txtCod.setEditable(false);
    txtCod.setBackground(colBack);
    colBack=null;

      if(rstCab!=null) {
          rstCab.close();
          rstCab=null;

          if(STM_GLO!=null){
            STM_GLO.close();
            STM_GLO=null;
          }
      }

   }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
}

        
        
        
        public void setEstadoBotonMakeFac(){
            switch(getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }
          
        
        
public void clickSiguiente(){ 
 try{
   if(rstCab != null ){
      abrirCon();
      if(!rstCab.isLast()) {
               rstCab.next();
               refrescaDatos( CONN_GLO, rstCab);
       }
      CerrarCon();
  }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}

        


public void clickEliminar() {

}
      
       
        
        
public boolean eliminar() {
  boolean blnRes=false;
  java.sql.Connection  conn;
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       
        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            blnRes=true;
        }
        
      if(!blnRes){ 
        if(eliminarReg(conn)){
            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Eliminado");
          
        }else conn.rollback();
       }else blnRes=false;
        
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

        
          
        
private boolean eliminarReg(java.sql.Connection conn){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmEmp;
  java.sql.ResultSet rstEmp;
  String strSql=""; 
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();
       stmEmp=conn.createStatement();

    strSql="SELECT co_emp FROM tbm_emp  ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){
        strSql="UPDATE tbm_clainv SET st_regrep='M', st_reg='E', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrmod="+objZafParSis.getCodigoUsuario()+" " +
        " WHERE co_emp="+rstEmp.getInt("co_emp")+" AND co_cla="+txtCod.getText()+" ";
        strSql+=" ; UPDATE tbr_prvclainv SET st_regrep='M',  st_reg='I' WHERE   co_cla="+txtCod.getText()+" ";
        stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;
    stmLoc.close();
    stmLoc=null;
    stmEmp.close();
    stmEmp=null;

     blnRes=true;
  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

        
         
  

 private boolean validaCampos(){
  if(txtDesCor.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Descripción Corta >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtDesCor.requestFocus();
    return false;
    }

  if(txtDesLar.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Descripción Larga >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtDesLar.requestFocus();
    return false;
    }

   if(txtCodGrp.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Código de Grupo >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodGrp.requestFocus();
    return false;
    }
             

        

   return true;  
 }
        
 
 
 
 
        
        
public boolean insertar() {
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  int intCodReg=0;
  String strSql="";
  try{
    if(validaCampos()){
    
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);
        stmLoc=conn.createStatement();
        strSql="SELECT CASE WHEN max(co_cla)+1 IS NULL THEN 1 ELSE max(co_cla)+1 END AS cocla FROM tbm_clainv WHERE co_emp="+objZafParSis.getCodigoEmpresa();
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           intCodReg=rstLoc.getInt("cocla");
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;

         if(insertarReg(conn, intCodReg)){
          if(insertarPrvCla(conn, intCodReg)){
            conn.commit();
            blnRes=true;
            cargarTabPrv(conn, intCodReg );
         }else conn.rollback();
        }else conn.rollback();

       conn.close();
       conn=null;
     }
     
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


 
        
        
private boolean insertarReg(java.sql.Connection conn, int intCodReg){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmEmp;
 java.sql.ResultSet rstEmp;
 String strSql="";
 try{
    if(conn!=null){
       stmLoc=conn.createStatement();

    stmEmp=conn.createStatement();
        
    strSql="SELECT co_emp FROM tbm_emp  ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

        strSql="INSERT INTO tbm_clainv(co_emp, co_cla, tx_descor, tx_deslar, co_grp, tx_obs1, st_reg, fe_ing, co_usring, st_regrep ) " +
        " VALUES("+rstEmp.getInt("co_emp")+", "+intCodReg+", '"+txtDesCor.getText()+"', '"+txtDesLar.getText()+"', " +
        " "+(txtCodGrp.getText().equals("")?null:txtCodGrp.getText())+", '"+txtObs.getText()+"','A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", 'I' ) ";
        stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;

    stmLoc.close();
    stmLoc=null;
    stmEmp.close();
    stmEmp=null;
        
     blnRes=true;
     txtCod.setText(""+intCodReg);
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

public boolean insertarPrvCla(java.sql.Connection conn, int intCodCla ){
   boolean blnRes=false;
   java.sql.Statement stmLoc;
   String strSql="";
   String strEst="";
   try{
      stmLoc=conn.createStatement();

       for(int i=0; i<tblPrv.getRowCount(); i++){
        if(tblPrv.getValueAt(i, INT_TBL_PRVCODCLI) != null){
         if(!tblPrv.getValueAt(i, INT_TBL_PRVCODCLI).toString().equals("")){

               strEst="A";
                if(tblPrv.getValueAt( i, INT_TBL_PRVPRE)!=null){
                  if(tblPrv.getValueAt( i, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
                         strEst="P";
                }
                if(tblPrv.getValueAt( i, INT_TBL_PRVACT)!=null){
                   if(tblPrv.getValueAt( i, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
                        strEst="A";
                }
                if(tblPrv.getValueAt( i, INT_TBL_PRVINA)!=null){
                  if(tblPrv.getValueAt( i, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
                         strEst="I";
                }

               strSql+=" INSERT INTO tbr_prvclainv(co_emp, co_cla, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
               " ("+objZafParSis.getCodigoEmpresa()+","+ intCodCla +", "+tblPrv.getValueAt(i, INT_TBL_PRVCODCLI).toString()+" "+
               " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";

       }}}

      if(!strSql.equals(""))
          stmLoc.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

   }catch(java.sql.SQLException Evt){ blnRes=false; MensajeInf("Error. Al Insertar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente.");  }
    catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}
       


private void MensajeInf(String strMensaje){
    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
}


public boolean modificar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    
      strAux=objTooBar.getEstadoRegistro();
      if(strAux.equals("Eliminado")) {
          MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
          return false;
      }
      if(strAux.equals("Anulado")) {
          MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
          return false;
      }

              
    if(validaCampos()){
    
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);
      
         if(modificarCab(conn)){
          if(actualizarTabPrv(conn, txtCod.getText())){
            conn.commit();
            blnRes=true;
            cargarTabPrv(conn, Integer.parseInt( txtCod.getText() ) );
          }else conn.rollback();
         }else conn.rollback();
      
      
       conn.close();
       conn=null;
     }
     
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

       
private boolean modificarCab(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmEmp;
 java.sql.ResultSet rstEmp;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

     stmEmp=conn.createStatement();
        
    strSql="SELECT co_emp FROM tbm_emp   ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

         strSql="UPDATE tbm_clainv SET  st_regrep='M', tx_descor='"+txtDesCor.getText()+"', tx_deslar='"+txtDesLar.getText()+"' " +
         " ,co_grp="+(txtCodGrp.getText().equals("")?null:txtCodGrp.getText())+", tx_obs1='"+txtObs.getText()+"', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrmod="+objZafParSis.getCodigoUsuario()+" " +
         " WHERE co_emp="+rstEmp.getInt("co_emp")+" AND  co_cla="+txtCod.getText()+" ";
         stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;
    
   stmLoc.close();
   stmLoc=null;
   stmEmp.close();
   stmEmp=null;

    blnRes=true;  
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

public boolean actualizarTabPrv(java.sql.Connection conMod, String strCodCla ){
 boolean blnRes=true;
 java.sql.Statement stm;
 String strSql="";
 String strEst="A";
 String strSqlPrv="";
 try{
 if(conMod!=null){
 stm=conMod.createStatement();

  strSql="DELETE FROM tbr_prvclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cla="+strCodCla+" and co_grp="+txtCodGrp.getText();
  stm.executeUpdate(strSql);
 
 for(int x=0; x<tblPrv.getRowCount(); x++){
  if(tblPrv.getValueAt(x, INT_TBL_PRVCODCLI) != null){
   if(!tblPrv.getValueAt(x, INT_TBL_PRVCODCLI).toString().equals("")){

    strEst="A";
    if(tblPrv.getValueAt( x, INT_TBL_PRVPRE)!=null){
     if(tblPrv.getValueAt( x, INT_TBL_PRVPRE).toString().equalsIgnoreCase("true"))
        strEst="P";
    }
    if(tblPrv.getValueAt( x, INT_TBL_PRVACT)!=null){
     if(tblPrv.getValueAt( x, INT_TBL_PRVACT).toString().equalsIgnoreCase("true"))
           strEst="A";
    }
    if(tblPrv.getValueAt( x, INT_TBL_PRVINA)!=null){
        if(tblPrv.getValueAt( x, INT_TBL_PRVINA).toString().equalsIgnoreCase("true"))
           strEst="I";
    }
  
      strSqlPrv+=" INSERT INTO tbr_prvclainv(co_emp, co_grp,  co_cla, co_prv ,st_reg, st_regrep, fe_ing ) VALUES" +
      " ("+objZafParSis.getCodigoEmpresa()+","+txtCodGrp.getText()+", "+ strCodCla +", "+tblPrv.getValueAt(x, INT_TBL_PRVCODCLI).toString()+" "+
      " ,'"+strEst+"', 'I', "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ) ; ";
   
 }}}

 if(!strSqlPrv.equals(""))
   stm.executeUpdate(strSqlPrv);


 stm.close();
 stm=null;
}}catch(java.sql.SQLException Evt){ blnRes=false; MensajeInf("Error. Al Insertar Proveedores posible duplicidad de datos.\nVerifique los datos he intente nuevamente.");  }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt);  }
 return blnRes;
}




     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }
    
   
      
        
 public void  clnTextos(){
      txtCod.setText("");
      txtDesCor.setText("");
      txtDesLar.setText("");
      txtObs.setText("");
      txtCodGrp.setText("");
      txtDesGrp.setText("");
      objTblModPrv.removeAllRows();

 }

    
        
      
        public boolean cancelar() {
            boolean blnRes=true;
            
            try {
//                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
//                        if (!isRegPro())
//                            return false;
//                    }
                if (rstCab!=null) {
                    rstCab.close();
                    rstCab=null;
                     if(STM_GLO!=null){
                        STM_GLO.close();
                        STM_GLO=null;
                      }
                }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
             
            return blnRes;
        }
        
        
        
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterCancelar() {
            
            return true;
        }
        
        public boolean afterConsultar() {
          
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            this.setEstado('w');
         
            return true;
        }
        
        public boolean afterModificar() {
          
           this.setEstado('w');
           
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        
        
        
        
   
        
          /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
        
        
        
        
        
        
          /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
        
          
        
        
        
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
           return _consultar(FilSql());
        }
        
        
        
          
        
private boolean _consultar(String strFil){
  boolean blnRes=false;
  String strSql="";
   try{
     
        abrirCon();
        if(CONN_GLO!=null) {
            STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );


           strSql="select a.co_emp, a.co_cla from tbm_clainv as a " +
           " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
           " inner join tbr_grpclainvusr as a2 on (a2.co_emp=a1.co_emp and a2.co_grp=a1.co_grp) " +
           " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.st_reg NOT IN('E') and a2.co_usr="+objZafParSis.getCodigoUsuario()+" " +
           "  "+strFil+" ORDER BY a.co_cla ";


           /*strSql="SELECT a.co_emp, a.co_cla  FROM tbm_clainv AS a " +
           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.st_reg NOT IN('E') " +
           " "+strFil+" ORDER BY a.co_cla ";
           */
           
           rstCab=STM_GLO.executeQuery(strSql);
           if(rstCab.next()){
              rstCab.last();
              setMenSis("Se encontraron " + rstCab.getRow() + " registros");
              refrescaDatos(CONN_GLO, rstCab);
              blnRes=true;
           }else{
                setMenSis("0 Registros encontrados");
                clnTextos();
           } 

      CerrarCon();
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}
    
   


private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatCla){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       if(conn!=null){
          stmLoc=conn.createStatement();

          strSql="SELECT a.co_cla, a.tx_descor, a.tx_deslar, a.co_grp, a1.tx_deslar as desgrp, a.tx_obs1, a.st_reg, a1.tx_tipgrp FROM tbm_clainv as a" +
          " LEFT JOIN tbm_grpclainv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_grp=a.co_grp) " +
          " WHERE a.co_emp="+rstDatCla.getString("co_emp")+" AND a.co_cla="+rstDatCla.getString("co_cla");
          rstLoc=stmLoc.executeQuery(strSql);
          if(rstLoc.next()){

            strAux=rstLoc.getString("st_reg");
            txtCod.setText(rstLoc.getString("co_cla"));
            txtDesCor.setText(rstLoc.getString("tx_descor"));
            txtDesLar.setText(rstLoc.getString("tx_deslar"));
            txtCodGrp.setText(rstLoc.getString("co_grp"));
            txtDesGrp.setText(rstLoc.getString("desgrp"));
            txtObs.setText(rstLoc.getString("tx_Obs1"));

           if(rstLoc.getString("tx_tipgrp").equals("P")) radPub.setSelected(true);
           if(rstLoc.getString("tx_tipgrp").equals("R")) radPri.setSelected(true);


           if(strAux.equals("A"))
             strAux="Activo";
           else if (strAux.equals("I"))
             strAux="Anulado";
           else if (strAux.equals("E"))
             strAux="Eliminado";
           else
               strAux="Otro";
            objTooBar.setEstadoRegistro(strAux);

            cargarTabPrv(conn,rstLoc.getInt("co_cla"));
            
          } 
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;

          int intPosRel=rstDatCla.getRow();
          rstDatCla.last();
          objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatCla.getRow());
          rstDatCla.absolute(intPosRel);
         
  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

private boolean cargarTabPrv(java.sql.Connection conn, int intCodCla ){
 boolean blnRes=true;
 java.sql.ResultSet rst;
 java.sql.Statement stm;
 String strSql="";
 try{
   if(conn!=null){
     stm=conn.createStatement();
     strSql="SELECT a.co_prv, a1.tx_nom, a.st_reg  FROM tbr_prvclainv AS a " +
     " INNER JOIN tbm_cli AS a1 ON(a1.co_emp=a.co_emp AND a1.co_cli=a.co_prv) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cla="+intCodCla+"   ORDER BY a.fe_ing ";
     rst = stm.executeQuery(strSql);
     Vector vecData = new Vector();
     while(rst.next()){
         java.util.Vector vecReg = new java.util.Vector();

         vecReg.add(INT_TBL_PRVLIN, "");
         vecReg.add(INT_TBL_PRVCODCLI, rst.getString("co_prv") );
         vecReg.add(INT_TBL_PRVBUTCLI,"··");
         vecReg.add(INT_TBL_PRVNOMCLI, rst.getString("tx_nom") );

         Boolean blnIvaP = new Boolean(rst.getString("st_reg").equals("P"));
         Boolean blnIvaA = new Boolean(rst.getString("st_reg").equals("A"));
         Boolean blnIvaI = new Boolean(rst.getString("st_reg").equals("I"));

         vecReg.add(INT_TBL_PRVACT, blnIvaA);
         vecReg.add(INT_TBL_PRVINA, blnIvaI);
         vecReg.add(INT_TBL_PRVPRE, blnIvaP);
         vecReg.add(INT_TBL_CODREGPRV, null  );
        vecData.add(vecReg);
     }
     objTblModPrv.setData(vecData);
     tblPrv.setModel(objTblModPrv);
    rst.close();
    rst=null;
    stm.close();
    stm=null;

 }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}
      
          

private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion

    if(!txtCod.getText().equals(""))
        sqlFiltro = sqlFiltro + " and a.co_cla="+txtCod.getText();
 

    return sqlFiltro ;
}

        
        
    

public void clickModificar(){
 
    java.awt.Color colBack;
    colBack = txtCod.getBackground();
    txtCod.setEditable(false);
    txtCod.setBackground(colBack);
    colBack=null;


 this.setEnabledConsultar(false);

}



        
        
        
        
       
       
        //******************************************************************************************************
        
        public boolean vistaPreliminar(){
           
            return true;
        }
        
        
        
        
        
        
        
        
        
        public boolean imprimir(){
            
            return true;
        }
        
        
        //******************************************************************************************************
        
        
        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }
        
        public void clickCancelar(){
         
        }
        
        public void cierraConnections(){
            
        }
        
        
        public boolean beforeAceptar() {
            return true;
        }
        public boolean beforeAnular() {
            return true;
        }
        public boolean beforeCancelar() {
            return true;
        }
        public boolean beforeConsultar() {
            return true;
        }
        public boolean beforeEliminar() {
            return true;
        }
        public boolean beforeImprimir() {
          
            return true;
        }
        public boolean beforeInsertar() {
            return true;
        }
        public boolean beforeModificar() {
            return true;
        }
        public boolean beforeVistaPreliminar() {
       
            return true;
        }
        
      
        
        
    }
    
  
      
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
    
    
}
