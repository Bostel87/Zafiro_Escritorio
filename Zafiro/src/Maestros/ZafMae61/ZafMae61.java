/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafMae61.java
 *
 * Created on Dec 18, 2010, 2:03:19 PM
 */

package Maestros.ZafMae61;

import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;

/**
 *
 * @author jayapata
 */
public class ZafMae61 extends javax.swing.JInternalFrame {
  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  private MiToolBar objTooBar;
   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
   private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
   private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
   private ZafTblCelRenCbo objTblCelRenCmbBox;
   private ZafTblCelEdiCbo objTblCelEdiCmbBox;
  String strVersion=" Ver 0.2 ";

  String strDesCodTipDoc="";
  String strDesLarTipDoc="";
  String strCodTipDoc="";
  String strUsr="";
  String strNomUsr="";
  String strCodUsr="";
  String strCodPrg="";
  String strNomPrg="";
  
  ZafVenCon objVenConProg;
  ZafVenCon objVenConTipdoc;
  ZafVenCon objVenConUsr;

  final int INT_TBL_LINEA=0;
  final int INT_TBL_CHKBOD=1;
  final int INT_TBL_CODBOD=2;
  final int INT_TBL_NOMBOD=3;
  final int INT_TBL_NATURA=4;
  final int INT_TBL_CHKPRE=5;

  javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  javax.swing.JTextField txtCodUsr= new javax.swing.JTextField();

  java.sql.Connection CONN_GLO=null;
  java.sql.Statement  STM_GLO=null;
  java.sql.ResultSet  rstCab=null;
  
    /** Creates new form ZafMae61 */
    public ZafMae61(Librerias.ZafParSis.ZafParSis obj) {
           try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/
	     objTooBar = new MiToolBar(this);
	     this.getContentPane().add(objTooBar,"South");

             objTooBar.setVisibleAnular(false);

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }


/**
 * Carga ventanas de consulta y configuracion de la tabla
 */
public void Configura_ventana_consulta(){
    configurarVenConTipDoc();
    configurarVenConProg();
    configurarVenConUsr();
    
    configurarForm();
}


 private boolean configurarVenConProg(){
   boolean blnRes=true;
   String strSql="";
   try{
       
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mnu");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Programa");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("494");
            //Armar la sentencia SQL.
            strSql="SELECT a1.co_mnu, a1.tx_nom "+
            " FROM tbm_mnuSis AS a1 "+
            " WHERE (a1.tx_tipMnu='C'or a1.tx_tipMnu='R') and a1.tx_nom!='' "+
            " AND a1.st_reg<>'E' AND a1.tx_acc IS NOT NULL "+
            " ORDER BY a1.tx_nom ";
            objVenConProg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de programas", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            objVenConProg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            objVenConProg.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



 private boolean configurarVenConTipDoc() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("b.co_tipdoc");
            arlCam.add("b.tx_descor");
            arlCam.add("b.tx_deslar");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            
            Str_Sql ="select b.co_tipdoc, b.tx_descor, b.tx_deslar from tbr_tipdocprg as a " +
            " inner join tbm_cabtipdoc as b on (b.co_emp=a.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc ) " +
            " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+"  and  a.co_mnu= "+objZafParSis.getCodigoMenu()+" ";


            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


private boolean configurarVenConUsr(){
 boolean blnRes=true;
 String strSql="";
 try{
    //Listado de campos.
    ArrayList arlCam=new ArrayList();
    arlCam.add("a1.co_usr");
    arlCam.add("a1.tx_usr");
    arlCam.add("a1.tx_nom");
    //Alias de los campos.
    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Usuario");
    arlAli.add("Nombre");
    //Ancho de las columnas.
    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("50");
    arlAncCol.add("80");
    arlAncCol.add("414");
    //Armar la sentencia SQL.

    strSql="select a1.co_usr, a1.tx_usr, a1.tx_nom   from tbr_tipdocusr as a " +
    " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) " +
    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
    " and a.co_tipdoc=null  and a.co_mnu=null and a1.st_reg='A'  " +
    " order by a1.tx_usr ";

    objVenConUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de usuarios", strSql, arlCam, arlAli, arlAncCol);
    arlCam=null;
    arlAli=null;
    arlAncCol=null;
    //Configurar columnas.
    objVenConUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
    }catch (Exception e)
     {
       blnRes=false;
       objUti.mostrarMsgErr_F1(this, e);
     }
    return blnRes;
}


private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_CHKBOD," ");
    vecCab.add(INT_TBL_CODBOD,"Cód.Bod");
    vecCab.add(INT_TBL_NOMBOD,"Nom.Bod");
    vecCab.add(INT_TBL_NATURA,"Naturaleza");
    vecCab.add(INT_TBL_CHKPRE,"Pre.");
   
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
   // objMouMotAda=new ZafMouMotAda();
  //  tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
 
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(350);
    tcmAux.getColumn(INT_TBL_NATURA).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_CHKPRE).setPreferredWidth(50);
   

    ////*****tblAnMe.getTableHeader().addMouseMotionListener(objTblMod);
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CHKBOD);
    vecAux.add("" + INT_TBL_NATURA);
    vecAux.add("" + INT_TBL_CHKPRE);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChk);
    tcmAux.getColumn(INT_TBL_CHKPRE).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;


    objTblCelRenCmbBox=new ZafTblCelRenCbo();
    tcmAux.getColumn(INT_TBL_NATURA).setCellRenderer(objTblCelRenCmbBox);

    objTblCelEdiCmbBox=new ZafTblCelEdiCbo(tblDat);
    tcmAux.getColumn(INT_TBL_NATURA).setCellEditor(objTblCelEdiCmbBox);
    objTblCelEdiCmbBox.addItem("");
    objTblCelEdiCmbBox.addItem("Ingreso"); // I
    objTblCelEdiCmbBox.addItem("Egreso");  // E
    objTblCelEdiCmbBox.addItem("Ingreso/Egreso"); // A
    
    objTblCelEdiCmbBox.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            for(int p=(tblDat.getSelectedRow()); p<=(tblDat.getSelectedRow());p++){
                if(! objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHKBOD)){
                    mostrarMsgInf("<HTML>No se puede escoger el nivel si el reporte no ha sido seleccionado.<BR>Seleccione el reporte y vuelva a escoger el nivel del reporte.<HTML>");
                    objTblCelEdiCmbBox.setSelectedIndex(-1);
                    break;
                }
            }
        }
    });


    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChk);
    tcmAux.getColumn(INT_TBL_CHKPRE).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intNunFil = tblDat.getSelectedRow();

           if(tblDat.getSelectedColumn() == INT_TBL_CHKPRE){

               if(!tblDat.getValueAt(intNunFil, INT_TBL_CHKBOD).toString().equals("true")){
                  mostrarMsgInf("NO SE PUEDE MARCAR COMO PREDETERMINADO SELECCIONE PRIMERO.."); 
                  tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHKPRE);
               }else{
                  for(int i=0; i<tblDat.getRowCount(); i++){
                    if(tblDat.getValueAt(intNunFil, INT_TBL_CHKBOD).toString().equals("true")){
                      if(intNunFil != i ){
                          tblDat.setValueAt( new Boolean(false), i, INT_TBL_CHKPRE);
                      }
                    } 
                   }
                  }

           }


           
//           if(tblDat.getValueAt(intNunFil, INT_TBL_CHK).toString().equals("true")){
//            String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
//            tblDat.setValueAt(strValPen, intNunFil, INT_TBL_VALAPL);
//           }else{
//                tblDat.setValueAt("0", intNunFil, INT_TBL_VALAPL);
//           }
//
//            subTot();
        }
    });

    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
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


   private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panCabRecChq = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomPrg = new javax.swing.JTextField();
        txtCodPrg = new javax.swing.JTextField();
        butProg = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtUsr = new javax.swing.JTextField();
        txtNomUsr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        panDetRecChq = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panNor = new javax.swing.JPanel();
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

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panCabRecChq.setPreferredSize(new java.awt.Dimension(100, 80));
        panCabRecChq.setLayout(null);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Programa:"); // NOI18N
        panCabRecChq.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        txtNomPrg.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrgActionPerformed(evt);
            }
        });
        txtNomPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusLost(evt);
            }
        });
        panCabRecChq.add(txtNomPrg);
        txtNomPrg.setBounds(190, 10, 230, 20);

        txtCodPrg.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrgActionPerformed(evt);
            }
        });
        txtCodPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusLost(evt);
            }
        });
        panCabRecChq.add(txtCodPrg);
        txtCodPrg.setBounds(120, 10, 70, 20);

        butProg.setText(".."); // NOI18N
        butProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProgActionPerformed(evt);
            }
        });
        panCabRecChq.add(butProg);
        butProg.setBounds(420, 10, 20, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Tipo de documento:"); // NOI18N
        panCabRecChq.add(jLabel4);
        jLabel4.setBounds(10, 30, 110, 20);

        txtDesCodTitpDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCodTitpDocActionPerformed(evt);
            }
        });
        txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusLost(evt);
            }
        });
        panCabRecChq.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(120, 30, 70, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panCabRecChq.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 30, 230, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabRecChq.add(butTipDoc);
        butTipDoc.setBounds(420, 30, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel5.setText("Usuario:"); // NOI18N
        panCabRecChq.add(jLabel5);
        jLabel5.setBounds(10, 50, 110, 20);

        txtUsr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrActionPerformed(evt);
            }
        });
        txtUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrFocusLost(evt);
            }
        });
        panCabRecChq.add(txtUsr);
        txtUsr.setBounds(120, 50, 70, 20);

        txtNomUsr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomUsrActionPerformed(evt);
            }
        });
        txtNomUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusLost(evt);
            }
        });
        panCabRecChq.add(txtNomUsr);
        txtNomUsr.setBounds(190, 50, 230, 20);

        butUsr.setText(".."); // NOI18N
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panCabRecChq.add(butUsr);
        butUsr.setBounds(420, 50, 20, 20);

        panGenTabGen.add(panCabRecChq, java.awt.BorderLayout.NORTH);

        panDetRecChq.setLayout(new java.awt.BorderLayout());

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

        panDetRecChq.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGenTabGen.add(panDetRecChq, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panGenTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrgActionPerformed
        // TODO add your handling code here:
        txtNomPrg.transferFocus();
}//GEN-LAST:event_txtNomPrgActionPerformed

    private void txtNomPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusGained
        // TODO add your handling code here:
        strNomPrg=txtNomPrg.getText();
        txtNomPrg.selectAll();
}//GEN-LAST:event_txtNomPrgFocusGained

    private void txtNomPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusLost
        // TODO add your handling code here:
        if (!txtNomPrg.getText().equalsIgnoreCase(strNomPrg)) {
            if (txtNomPrg.getText().equals("")) {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");

            }else
                BuscarProg("a.tx_nom",txtNomPrg.getText(),1);
        }else
            txtNomPrg.setText(strNomPrg);
}//GEN-LAST:event_txtNomPrgFocusLost

    private void txtCodPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrgActionPerformed
        // TODO add your handling code here:
        txtCodPrg.transferFocus();
}//GEN-LAST:event_txtCodPrgActionPerformed

    private void txtCodPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusGained
        // TODO add your handling code here:
        strCodPrg=txtCodPrg.getText();
        txtCodPrg.selectAll();
}//GEN-LAST:event_txtCodPrgFocusGained





public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
 if(!txtCodPrg.getText().equals("")){

   String  strSql="select b.co_tipdoc, b.tx_descor, b.tx_deslar from tbr_tipdocprg as a " +
   " inner join tbm_cabtipdoc as b on (b.co_emp=a.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc ) " +
   " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+"  and  a.co_mnu= "+txtCodPrg.getText()+" ";

  objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
  objVenConTipdoc.setSentenciaSQL(strSql);

  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);
      txtCodUsr.setText("");
      txtUsr.setText("");
      txtNomUsr.setText("");

  }else{
        objVenConTipdoc.setSentenciaSQL(strSql);
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
           txtCodUsr.setText("");
           txtUsr.setText("");
           txtNomUsr.setText("");

         }else{
           txtCodTipDoc.setText(strCodTipDoc);
           txtDesCodTitpDoc.setText(strDesCodTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}
  }else{
          this.mostrarMsgInf("TIENE QUE ESCOJER EL PROGRAMA PRIMERO...");
       }
 }




public void BuscarProg(String campo,String strBusqueda,int tipo){
  objVenConProg.setTitle("Listado Programa");
  if(objVenConProg.buscar(campo, strBusqueda )) {
      txtCodPrg.setText(objVenConProg.getValueAt(1));
      txtNomPrg.setText(objVenConProg.getValueAt(2));
      strCodPrg=objVenConProg.getValueAt(1);
      txtCodTipDoc.setText("");
      txtDesCodTitpDoc.setText("");
      txtDesLarTipDoc.setText("");
      txtCodUsr.setText("");
      txtUsr.setText("");
      txtNomUsr.setText("");

  }else{
        objVenConProg.setCampoBusqueda(tipo);
        objVenConProg.cargarDatos();
        objVenConProg.show();
        if (objVenConProg.getSelectedButton()==objVenConProg.INT_BUT_ACE) {
           txtCodPrg.setText(objVenConProg.getValueAt(1));
           txtNomPrg.setText(objVenConProg.getValueAt(2));
           strCodPrg=objVenConProg.getValueAt(1);
           txtCodTipDoc.setText("");
           txtDesCodTitpDoc.setText("");
           txtDesLarTipDoc.setText("");
           txtCodUsr.setText("");
           txtUsr.setText("");
           txtNomUsr.setText("");

         }else{
           txtCodPrg.setText(strCodPrg);
           txtNomPrg.setText(strNomPrg);
  }}}




public void BuscarUsr(String campo,String strBusqueda,int tipo){
  if(!txtCodPrg.getText().equals("")){

    String strSql="Select a1.co_usr, a1.tx_usr, a1.tx_nom   from tbr_tipdocusr as a " +
    " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) " +
    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
    " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_mnu="+txtCodPrg.getText()+" and a1.st_reg='A'  " +
    " order by a1.tx_usr ";

  objVenConUsr.setTitle("Listado de Usuario");
  objVenConUsr.setSentenciaSQL(strSql);

  if(objVenConUsr.buscar(campo, strBusqueda )) {
      txtCodUsr.setText(objVenConUsr.getValueAt(1));
      txtUsr.setText(objVenConUsr.getValueAt(2));
      txtNomUsr.setText(objVenConUsr.getValueAt(3));
      strCodUsr=objVenConUsr.getValueAt(1);

  }else{
        
        objVenConUsr.setCampoBusqueda(tipo);
        objVenConUsr.cargarDatos();
        objVenConUsr.show();
        if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
           txtCodUsr.setText(objVenConUsr.getValueAt(1));
           txtUsr.setText(objVenConUsr.getValueAt(2));
           txtNomUsr.setText(objVenConUsr.getValueAt(3));
           strCodUsr=objVenConUsr.getValueAt(1);
         }else{
           txtCodUsr.setText(strCodUsr);
           txtUsr.setText(strUsr);
           txtNomUsr.setText(strNomUsr);
  }}
   }else{
          this.mostrarMsgInf("TIENE QUE ESCOJER EL TIPO DE DOCUMENTO PRIMERO...");
       }
  }




    private void txtCodPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusLost
        // TODO add your handling code here:

        if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) {
            if (txtCodPrg.getText().equals("")) {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");

            }else
                BuscarProg("a.co_mnu",txtCodPrg.getText(),0);
        }else
            txtCodPrg.setText(strCodPrg);

}//GEN-LAST:event_txtCodPrgFocusLost

    private void butProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProgActionPerformed
        // TODO add your handling code here:
        objVenConProg.setTitle("Listado de Programa");
        objVenConProg.setCampoBusqueda(1);
        objVenConProg.show();
        if (objVenConProg.getSelectedButton()==objVenConProg.INT_BUT_ACE) {
            txtCodPrg.setText(objVenConProg.getValueAt(1));
            txtNomPrg.setText(objVenConProg.getValueAt(2));
            strCodPrg=objVenConProg.getValueAt(1);
            txtCodTipDoc.setText("");
            txtDesCodTitpDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodUsr.setText("");
            txtUsr.setText("");
            txtNomUsr.setText("");

        }
}//GEN-LAST:event_butProgActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        // TODO add your handling code here:
        txtDesCodTitpDoc.transferFocus();
    }//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        // TODO add your handling code here:
        strDesCodTipDoc=txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
    }//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        // TODO add your handling code here:
          if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCodTipDoc)) {
            if (txtDesCodTitpDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else
                BuscarTipDoc("a.tx_descor",txtDesCodTitpDoc.getText(),1);
        }else
            txtDesCodTitpDoc.setText(strDesCodTipDoc);
    }//GEN-LAST:event_txtDesCodTitpDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:

       if(!txtCodPrg.getText().equals("")){
        objVenConTipdoc.setTitle("Listado de Tipo Documento");

        String  strSql="select b.co_tipdoc, b.tx_descor, b.tx_deslar from tbr_tipdocprg as a " +
        " inner join tbm_cabtipdoc as b on (b.co_emp=a.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc ) " +
        " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+"  and  a.co_mnu= "+txtCodPrg.getText()+" ";


        objVenConTipdoc.setSentenciaSQL(strSql);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
            txtCodUsr.setText("");
            txtUsr.setText("");
            txtNomUsr.setText("");
        }
       }else{
          this.mostrarMsgInf("TIENE QUE ESCOJER EL PROGRAMA PRIMERO...");
       }

    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusGained
        // TODO add your handling code here:
        strUsr=txtUsr.getText();
        txtUsr.selectAll();
    }//GEN-LAST:event_txtUsrFocusGained

    private void txtUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusLost
        // TODO add your handling code here:
         if (!txtUsr.getText().equalsIgnoreCase(strUsr)) {
            if (txtUsr.getText().equals("")) {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else
                BuscarUsr("a1.tx_usr",txtUsr.getText(),1);
        }else
            txtUsr.setText(strUsr);
    }//GEN-LAST:event_txtUsrFocusLost




    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed
        // TODO add your handling code here:

     if(!txtCodTipDoc.getText().equals("")){
        objVenConUsr.setTitle("Listado de Usuario");

        String strSql="Select a1.co_usr, a1.tx_usr, a1.tx_nom   from tbr_tipdocusr as a " +
        " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) " +
        " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " and a.co_tipdoc="+txtCodTipDoc.getText()+"  and a.co_mnu="+txtCodPrg.getText()+" and a1.st_reg='A'  " +
        " order by a1.tx_usr ";

        objVenConUsr.setSentenciaSQL(strSql);
        objVenConUsr.cargarDatos();
        objVenConUsr.setCampoBusqueda(1);
        objVenConUsr.show();
        if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
            txtCodUsr.setText(objVenConUsr.getValueAt(1));
            txtUsr.setText(objVenConUsr.getValueAt(2));
            txtNomUsr.setText(objVenConUsr.getValueAt(3));
            strCodUsr=objVenConUsr.getValueAt(1);
        }
     }else{
          this.mostrarMsgInf("TIENE QUE ESCOJER EL TIPO DE DOCUMENTO PRIMERO...");
       }
       
    }//GEN-LAST:event_butUsrActionPerformed

    private void txtNomUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusLost
        // TODO add your handling code here:

          if (!txtNomUsr.getText().equalsIgnoreCase(strNomUsr)) {
            if (txtNomUsr.getText().equals("")) {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else{
                BuscarUsr("a1.tx_nom",txtNomUsr.getText(),2);
            }
        }else
            txtNomUsr.setText(strNomUsr);
    }//GEN-LAST:event_txtNomUsrFocusLost

    private void txtNomUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomUsrActionPerformed
        // TODO add your handling code here:
         txtNomUsr.transferFocus();
    }//GEN-LAST:event_txtNomUsrActionPerformed

    private void txtUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrActionPerformed
        // TODO add your handling code here:
        txtUsr.transferFocus();
    }//GEN-LAST:event_txtUsrActionPerformed

    private void txtNomUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusGained
        // TODO add your handling code here:
        strNomUsr=txtNomUsr.getText();
        txtNomUsr.selectAll();
    }//GEN-LAST:event_txtNomUsrFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:

        exitForm();

    }//GEN-LAST:event_formInternalFrameClosing


         /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butProg;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butUsr;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCabRecChq;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetRecChq;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrg;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomPrg;
    private javax.swing.JTextField txtNomUsr;
    private javax.swing.JTextField txtUsr;
    // End of variables declaration//GEN-END:variables

     /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objZafParSis);
        }

        public boolean anular()
        {
            return true;
        }

        public void clickAceptar()
        {

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

  }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}



        public void clickAnular()
        {

        }

        public void clickCancelar()
        {

        }

        public void clickConsultar(){
        }

        public void clickEliminar()
        {

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
   }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
     catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}




        public void clickImprimir()
        {

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
  }}catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
}


public void clickInsertar(){
try{
    clnTextos();

    setEditable(true);
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    objTblMod.setDataModelChanged(false);

     cargarDat();

      if(rstCab!=null) {
          rstCab.close();
          rstCab=null;
      }

   }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
 }



private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();


           strSql="select co_bod, tx_nom  from tbm_bod  where co_emp="+objZafParSis.getCodigoEmpresa()+" and st_reg='A' order by co_bod ";

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LINEA, "");
                 vecReg.add(INT_TBL_CHKBOD,  new Boolean( false ) );
                 vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
                 vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
                 vecReg.add(INT_TBL_NATURA, "" );
                 vecReg.add(INT_TBL_CHKPRE,  new Boolean( false ) );
                 
               vecData.add(vecReg);


           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}


  
public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}



public void clickModificar(){
  try{
      
  setEditable(true);

  java.awt.Color colBack;
  colBack = txtCodPrg.getBackground();

  bloquea(txtCodPrg, colBack, false);
  bloquea(txtNomPrg, colBack, false);
  bloquea(txtDesCodTitpDoc, colBack, false);
  bloquea(txtDesLarTipDoc, colBack, false);
  bloquea(txtUsr, colBack, false);
  bloquea(txtNomUsr, colBack, false);

  butTipDoc.setEnabled(false);
  butProg.setEnabled(false);
  butUsr.setEnabled(false);

  objTblMod.setDataModelChanged(false);

  }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
}



    private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }


public void clickSiguiente(){
 try{
   if(rstCab != null ){
       abrirCon();
      if(!rstCab.isLast()) {
               rstCab.next();
               refrescaDatos(CONN_GLO, rstCab);
       }
    CerrarCon();
  }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}




        public void clickVisPreliminar()
        {
        }

        public boolean consultar(){

               return _consultar(FilSql());
        }


private boolean _consultar(String strFil){
  boolean blnRes=false;
  String strSql="";
   try{
     
        abrirCon();
        if(CONN_GLO!=null) {
            STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

           strSql="select co_emp, co_loc, co_tipdoc, co_mnu, co_usr  from  tbr_bodtipdocprgusr " +
           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
           " "+strFil+"  group by co_emp, co_loc, co_tipdoc, co_mnu, co_usr  ORDER BY  co_usr  ";
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




 private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion
    if(!txtCodTipDoc.getText().equals(""))
        sqlFiltro = sqlFiltro + " and co_tipdoc="+txtCodTipDoc.getText();
   
    if(!txtCodPrg.getText().equals(""))
        sqlFiltro = sqlFiltro + " and co_mnu="+txtCodPrg.getText();

     if(!txtCodUsr.getText().equals(""))
        sqlFiltro = sqlFiltro + " and co_usr="+txtCodUsr.getText();

    return sqlFiltro ;
}


 

 public void  clnTextos(){
    strCodTipDoc=""; strDesCodTipDoc=""; strDesLarTipDoc="";
    strCodPrg=""; strNomPrg="";
    strUsr=""; strNomUsr=""; strCodUsr="";

    txtCodTipDoc.setText("");
    txtDesCodTitpDoc.setText("");
    txtDesLarTipDoc.setText("");

    txtCodPrg.setText("");
    txtNomPrg.setText("");

    txtCodUsr.setText("");
    txtUsr.setText("");
    txtNomUsr.setText("");

    objTblMod.removeAllRows();

 }



private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatRec ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc, rstLoc02;
    String strSql="";
    String strAux="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
       
        /**********CARGAR DATOS DE CABEZERA ***************/

       strSql="select a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_Descor, a1.tx_deslar, a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom as usario " +
       " from  tbr_bodtipdocprgusr as a " +
       " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
       " inner join tbm_mnusis as a2 on (a2.co_mnu=a.co_mnu) " +
       " inner join tbm_usr as a3 on (a3.co_usr=a.co_usr)" +
       " where a.co_emp="+rstDatRec.getInt("co_emp")+" and a.co_loc="+rstDatRec.getInt("co_loc")+" " +
       " and a.co_tipdoc= "+rstDatRec.getInt("co_tipdoc")+"  " +
       " and a.co_mnu= "+rstDatRec.getInt("co_mnu")+"  and a.co_usr="+rstDatRec.getInt("co_usr")+" " +
       " group by a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_Descor, a1.tx_deslar, a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom   ";
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

        txtCodTipDoc.setText( rstLoc02.getString("co_tipdoc"));
        txtDesCodTitpDoc.setText( rstLoc02.getString("tx_descor"));
        txtDesLarTipDoc.setText( rstLoc02.getString("tx_deslar"));

        txtCodPrg.setText( rstLoc02.getString("co_mnu"));
        txtNomPrg.setText( rstLoc02.getString("tx_nom"));

        txtCodUsr.setText( rstLoc02.getString("co_usr"));
        txtUsr.setText( rstLoc02.getString("tx_usr"));
        txtNomUsr.setText( rstLoc02.getString("usario"));

      }
      rstLoc02.close();
      rstLoc02=null;

       /***************************************************/


        /**********CARGAR DATOS DE DETALLE ***************/
       vecData = new Vector();

       strSql="select  a4.co_bod, a4.tx_nom,  a.tx_natbod, a.st_reg  " +
       " from  tbm_bod as a4 " +
       " left join tbr_bodtipdocprgusr as a on (a.co_emp=a4.co_emp and a.co_bod=a4.co_bod and a.co_loc="+rstDatRec.getInt("co_loc")+"  " +
       " and a.co_tipdoc= "+rstDatRec.getInt("co_tipdoc")+"  and a.co_mnu= "+rstDatRec.getInt("co_mnu")+"  and a.co_usr="+rstDatRec.getInt("co_usr")+"  ) " +
       " where a4.co_emp="+rstDatRec.getInt("co_emp")+"  and a4.st_reg='A' order by a4.co_bod ";

        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){

               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LINEA, "");
                 vecReg.add(INT_TBL_CHKBOD, new Boolean( (rstLoc.getString("st_reg")==null?false:(rstLoc.getString("st_reg").equals("")?false:true))  ) );
                 vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
                 vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
                 vecReg.add(INT_TBL_NATURA, "" );


                if(rstLoc.getString("tx_natbod")!=null){
                 if(rstLoc.getString("tx_natbod").equals("I"))
                    vecReg.setElementAt(new String("Ingreso"),INT_TBL_NATURA);
                 else if(rstLoc.getString("tx_natbod").equals("E"))
                    vecReg.setElementAt(new String("Egreso"),INT_TBL_NATURA);
                 else if(rstLoc.getString("tx_natbod").equals("A"))
                    vecReg.setElementAt(new String("Ingreso/Egreso"),INT_TBL_NATURA);
                 }


                 vecReg.add(INT_TBL_CHKPRE, new Boolean( (rstLoc.getString("st_reg")==null?false:(rstLoc.getString("st_reg").equals("P")?true:false))  ) );

               vecData.add(vecReg);
       }
        rstLoc.close();
        rstLoc=null;

        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);

       /***************************************************/
        stmLoc.close();
        stmLoc=null;

         strAux="Activo";
         objTooBar.setEstadoRegistro(strAux);

            int intPosRel=rstDatRec.getRow();
            rstDatRec.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatRec.getRow());
            rstDatRec.absolute(intPosRel);

        blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
   catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}





      public boolean eliminar(){

            return true;
      }


/**
 * validad campos requeridos antes de insertar o modificar
 * @return  true si esta todo bien   false   falta algun dato
 */
 private boolean validaCampos(){

   int intExiDatTbl=0;
   int intExiDatPre=0;
   int intExiDatNat=0;
  
  if(txtCodPrg.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    mostrarMsgInf("El campo << Programa >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodPrg.requestFocus();
    return false;
  }

  if(txtCodTipDoc.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    mostrarMsgInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodTipDoc.requestFocus();
    return false;
  }


  if(txtCodUsr.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    mostrarMsgInf("El campo << Usuario >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodUsr.requestFocus();
    return false;
  }


    String strNatura="";
     for(int i=0; i<tblDat.getRowCount(); i++){
       if(tblDat.getValueAt(i, INT_TBL_CHKBOD).toString().equals("true")){
         intExiDatTbl=1;

         strNatura=tblDat.getValueAt(i, INT_TBL_NATURA)==null?"":(tblDat.getValueAt(i, INT_TBL_NATURA).equals("null")?"":tblDat.getValueAt(i, INT_TBL_NATURA).toString());
         if(strNatura.equals("")){
           intExiDatNat=1;
         }

        if(tblDat.getValueAt(i, INT_TBL_CHKPRE).toString().equals("true")){
            intExiDatPre=1;
       }}
     }

    if(intExiDatTbl==0){
        mostrarMsgInf("NO HAY DATOS EN DETALLE INGRESE DATOS.... ");
        return false;
    }

    if(intExiDatNat==1){
        mostrarMsgInf("SELECCIONE LA NATURALEZA DE LA BODEGA.... ");
        return false;
    }

    if(intExiDatPre==0){
        mostrarMsgInf("SELECCIONE LA BODEGA PREDETERMINADO.... ");
        return false;
    }

   return true;
 }



public boolean insertar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  try{
    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();

          if(getVerificaExit(conn)){
           if(insertarCab(conn)){
               conn.commit();
               blnRes=true;
           }else conn.rollback();
          }else conn.rollback();

       stmLoc.close();
       stmLoc=null;
       conn.close();
       conn=null;
    }

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean getVerificaExit(java.sql.Connection conn){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

         strSql="SELECT co_mnu FROM tbr_bodtipdocprgusr WHERE  " +
         " co_emp="+objZafParSis.getCodigoEmpresa()+" and  co_loc="+objZafParSis.getCodigoLocal()+" and  " +
         " co_tipdoc="+txtCodTipDoc.getText()+" and  co_mnu="+txtCodPrg.getText()+" " +
         " and co_usr= "+txtCodUsr.getText()+" ";
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
             mostrarMsgInf("YA EXISTE DATOS INGRESADOS PARA ESTE USUARIO ");
             blnRes=false;
         }
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



private boolean insertarCab(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 String strEst="", strNat="";
 String strNatura="";
 String strCodBod="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

       for(int i=0; i<tblDat.getRowCount(); i++){
       if(tblDat.getValueAt(i, INT_TBL_CHKBOD).toString().equals("true")){
         strEst="A";

         strCodBod=tblDat.getValueAt(i, INT_TBL_CODBOD).toString();

         strNatura=tblDat.getValueAt(i, INT_TBL_NATURA)==null?"":(tblDat.getValueAt(i, INT_TBL_NATURA).equals("null")?"":tblDat.getValueAt(i, INT_TBL_NATURA).toString());
         if(strNatura.equals("Ingreso")){
           strNat="I";
         }else if(strNatura.equals("Egreso")){
           strNat="E";
         }else if(strNatura.equals("Ingreso/Egreso")){
           strNat="A";
         }

         if(tblDat.getValueAt(i, INT_TBL_CHKPRE).toString().equals("true")){
            strEst="P";
         }

         strSql="INSERT INTO tbr_bodtipdocprgusr(co_emp, co_loc, co_tipdoc, co_mnu, co_usr, co_bod, tx_natbod, st_reg) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+txtCodPrg.getText()+" " +
         " ,"+txtCodUsr.getText()+", "+strCodBod+", '"+strNat+"', '"+strEst+"'   ) ";
         stmLoc.executeUpdate(strSql);

       }
     }

      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



public boolean modificar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    if(validaCampos()){

     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
    
        if(modificarCab(conn)){
              conn.commit();
              blnRes=true;
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
 java.sql.Statement stmLoc;
 String strSql="";
 String strEst="", strNat="";
 String strNatura="";
 String strCodBod="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

         strSql="DELETE FROM tbr_bodtipdocprgusr WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and " +
         " co_loc="+objZafParSis.getCodigoLocal()+" and  co_tipdoc="+txtCodTipDoc.getText()+" and  co_mnu="+txtCodPrg.getText()+" " +
         " and co_usr="+txtCodUsr.getText()+"  ";
         stmLoc.executeUpdate(strSql);

     for(int i=0; i<tblDat.getRowCount(); i++){
       if(tblDat.getValueAt(i, INT_TBL_CHKBOD).toString().equals("true")){
         strEst="A";

         strCodBod=tblDat.getValueAt(i, INT_TBL_CODBOD).toString();

         strNatura=tblDat.getValueAt(i, INT_TBL_NATURA)==null?"":(tblDat.getValueAt(i, INT_TBL_NATURA).equals("null")?"":tblDat.getValueAt(i, INT_TBL_NATURA).toString());
         if(strNatura.equals("Ingreso")){
           strNat="I";
         }else if(strNatura.equals("Egreso")){
           strNat="E";
         }else if(strNatura.equals("Ingreso/Egreso")){
           strNat="A";
         }

         if(tblDat.getValueAt(i, INT_TBL_CHKPRE).toString().equals("true")){
            strEst="P";
         }

         strSql="INSERT INTO tbr_bodtipdocprgusr(co_emp, co_loc, co_tipdoc, co_mnu, co_usr, co_bod, tx_natbod, st_reg) " +
         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+txtCodPrg.getText()+" " +
         " ,"+txtCodUsr.getText()+", "+strCodBod+", '"+strNat+"', '"+strEst+"'   ) ";
         stmLoc.executeUpdate(strSql);

       }
     }

      stmLoc.close();
      stmLoc=null;
    blnRes=true;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



        public boolean cancelar()
        {
           boolean blnRes=true;

            try {

                if (rstCab!=null) {
                    rstCab.close();
                    if (STM_GLO!=null){
                        STM_GLO.close();
                        STM_GLO=null;
                    }
                    rstCab=null;

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

        public boolean vistaPreliminar()
        {
            return true;
        }

        public boolean aceptar()
        {
            return true;
        }

        public boolean imprimir()
        {
            return true;
        }

        public boolean beforeInsertar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeConsultar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeModificar()
        {

            return true;
        }

        public boolean beforeEliminar()
        {

            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }

        public boolean afterInsertar()
        {
            objTooBar.setEstado('w');
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            return true;
        }

        public boolean afterEliminar()
        {
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }

        public boolean afterCancelar()
        {
            return true;
        }

    }













}
