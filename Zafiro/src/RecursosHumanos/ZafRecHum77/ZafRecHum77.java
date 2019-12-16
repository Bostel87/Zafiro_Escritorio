/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package RecursosHumanos.ZafRecHum77;

import Librerias.ZafMae.ZafMaeVen.ZafVenEstCiv;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenCiu;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenOfi;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Roberto Flores
 * Guayaquil 24/01/2014
 */
public class ZafRecHum77 extends javax.swing.JInternalFrame {

  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private ZafUtil objUti; /**/
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private ZafTblCelEdiTxt objTblCelEdiTxt;                                          //Editor: JTextField en celda.
  private ZafThreadGUI objThrGUI;
  private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
  private ZafTblTot objTblTot;                                //JTable de totales.

  private final int INT_TBL_LINEA=0;                    // NUMERO DE LINEAS
  private final int INT_TBL_CHKSEL=1;                   // SELECCIONAR
  private final int INT_TBL_CODEMP=2;                   // CODIGO DE EMPRESA
  private final int INT_TBL_EMP=3;                      // NOMBRE DE LA EMPRESA
  private final int INT_TBL_CODTRA=4;                   // CODIGO DEL EMPLEADO
  private final int INT_TBL_NOMTRA=5;                   // NOMBRE DEL EMPLEADO
  private final int INT_TBL_VALTOTEGR_MESANT=6;         // VALOR EGRESO MES ANTERIOR
  private final int INT_TBL_VALTOTEGR_MESACT=7;         // VALOR EGRESO MES ACTUAL
  private final int INT_TBL_VALTOTEGR_MESACT_FIL=8;     // VALOR EGRESO MES ACTUAL

  private String strVersion=" v1.1";
  private String strNomRub="";
  private String strCodRub="";  
  
  private Vector vecCab=new Vector();
  private Vector vecDat;
  
  private int intCanReg=0;
  
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;
  
  private ZafVenCon objVenConRub;
  private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.

    /** Creates new form ZafRecHum77 */
    public ZafRecHum77(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
              this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();

              initComponents();
              
              /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
              cargarAños();

              this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
              lblTit.setText(objZafParSis.getNombreMenu());  /**/
              
              objUti = new ZafUtil(); /**/
         }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }

    private boolean cargarAños(){
    boolean blnRes=true;
    java.sql.Connection con = null; 
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null; 
    String strSQL="";

    try{
        con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(con!=null){ 
            
            stmLoc=con.createStatement();
            
            strSQL="select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani desc";
            rstLoc=stmLoc.executeQuery(strSQL);
            while(rstLoc.next()){
                cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
            }
        }
    }catch (java.sql.SQLException Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    } catch (Exception Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
    return blnRes;
  } 

     /**
      * Carga ventanas de consulta y configuracion de la tabla
      */

    public void Configura_ventana_consulta(){
        configurarVenConRub();
        ConfigurarTabla();
        agregarColTblDat();
    }
    
    private boolean configurarVenConRub(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_rub");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Rubro");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            
            //Armar la sentencia SQL.
            String strSQL="SELECT a.co_rub , a.tx_nom , e.tx_usr  FROM "+"\n";
            strSQL+="tbm_rubrolpag AS a "+"\n";
            strSQL+="INNER JOIN tbm_rubrolpagemp b ON (b.co_emp="+objZafParSis.getCodigoEmpresa()+" AND b.co_rub=a.co_rub) "+"\n";
            strSQL+="INNER JOIN tbr_rubRolPagLocPrg c ON (c.co_emp=b.co_emp AND c.co_loc="+objZafParSis.getCodigoLocal()+" AND c.co_mnu="+objZafParSis.getCodigoMenu()+" AND c.co_rub=b.co_rub) "+"\n";
            strSQL+="INNER JOIN tbr_rubRolPagLocPrgUsr d ON (d.co_emp=c.co_emp AND d.co_loc=c.co_loc AND d.co_mnu=c.co_mnu AND d.co_rub=c.co_rub AND d.co_usr="+objZafParSis.getCodigoUsuario()+") "+"\n";
            strSQL+="INNER JOIN tbm_usr e ON (e.co_usr=d.co_usr)";
            
            objVenConRub=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            objVenConRub.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{

        //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKSEL,"");
        vecCab.add(INT_TBL_CODEMP,"Cód.Emp.");
        vecCab.add(INT_TBL_EMP,"Empresa");
        vecCab.add(INT_TBL_CODTRA,"Código");
        vecCab.add(INT_TBL_NOMTRA,"Empleado");
        vecCab.add(INT_TBL_VALTOTEGR_MESANT,"Val.Tot.Egr.");
        vecCab.add(INT_TBL_VALTOTEGR_MESACT,"Val.Tot.Egr.");
        vecCab.add(INT_TBL_VALTOTEGR_MESACT_FIL,"");
        
	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
        objTblMod.setColumnDataType(INT_TBL_VALTOTEGR_MESANT, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VALTOTEGR_MESACT, objTblMod.INT_COL_DBL, new Integer(0), null);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);
        
	//Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        //objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKSEL);
        vecAux.add("" + INT_TBL_VALTOTEGR_MESACT);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
         ZafMouMotAda objMouMotAda=new ZafMouMotAda();
         tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(260);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESANT).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESACT).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESACT_FIL).setPreferredWidth(25);

        /* Aqui se agrega las columnas que van a ser ocultas*/
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_EMP);
        arlColHid.add(""+INT_TBL_VALTOTEGR_MESACT_FIL);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;
        
        //Configurar JTable: Renderizar celdas.
        Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();
//        Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
        
        
        Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);

         objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

            int intFil=0;

            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            }        
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intFil=tblDat.getSelectedRow();
                if((Boolean)objTblMod.getValueAt(intFil, INT_TBL_CHKSEL)){
                    objTblMod.setValueAt(objTblMod.getValueAt(intFil, INT_TBL_VALTOTEGR_MESANT), intFil, INT_TBL_VALTOTEGR_MESACT);
                }
            }
         });
        
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_CODTRA).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;
        
      
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESANT).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESACT).setCellRenderer(objTblCelRenLbl);
        
        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESANT).setCellEditor(objTblCelEdiTxt);
        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        });
            
        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_VALTOTEGR_MESACT).setCellEditor(objTblCelEdiTxt);
        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        });
        
        //Configurar JTable: Establecer el ordenamiento en la tabla.
        //objTblOrd=new ZafTblOrd(tblDat);
                
        //Configurar JTable: Editor de búsqueda.
        objTblBus=new ZafTblBus(tblDat);
        
        int intCol[]={INT_TBL_VALTOTEGR_MESANT , INT_TBL_VALTOTEGR_MESACT};
        
        objTblTot=new ZafTblTot(spnTot, spnTot, tblDat, tblTot, intCol);
        objTblTot.setValueAt("Total:", 0, 1);
        
        tcmAux=null;
        setEditable(true);

        blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

private boolean agregarColTblDat(){

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();

            for (i=0; i<1; i++){
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_LINEA+i*INT_TBL_DAT_NUM_TOT_CDI));
                

                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CHKSEL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODEMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODTRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_NOMTRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Mes anterior");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_VALTOTEGR_MESANT+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Mes actual");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_VALTOTEGR_MESACT+i*INT_TBL_DAT_NUM_TOT_CDI));
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panCabDepPrgUsr = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomRub = new javax.swing.JTextField();
        txtCodRub = new javax.swing.JTextField();
        butRub = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        panDetDepPrgUsr = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCerr1 = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(473, 537));
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panCabDepPrgUsr.setPreferredSize(new java.awt.Dimension(100, 60));
        panCabDepPrgUsr.setLayout(null);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Rubro:"); // NOI18N
        panCabDepPrgUsr.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        txtNomRub.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomRubActionPerformed(evt);
            }
        });
        txtNomRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomRubFocusLost(evt);
            }
        });
        panCabDepPrgUsr.add(txtNomRub);
        txtNomRub.setBounds(190, 10, 470, 20);

        txtCodRub.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRubActionPerformed(evt);
            }
        });
        txtCodRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRubFocusLost(evt);
            }
        });
        panCabDepPrgUsr.add(txtCodRub);
        txtCodRub.setBounds(120, 10, 70, 20);

        butRub.setText(".."); // NOI18N
        butRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRubActionPerformed(evt);
            }
        });
        panCabDepPrgUsr.add(butRub);
        butRub.setBounds(660, 10, 20, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Período:"); // NOI18N
        panCabDepPrgUsr.add(jLabel7);
        jLabel7.setBounds(10, 30, 110, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panCabDepPrgUsr.add(cboPerAAAA);
        cboPerAAAA.setBounds(120, 30, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cboPerMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerMesActionPerformed(evt);
            }
        });
        panCabDepPrgUsr.add(cboPerMes);
        cboPerMes.setBounds(190, 30, 100, 20);

        panGenTabGen.add(panCabDepPrgUsr, java.awt.BorderLayout.NORTH);

        panDetDepPrgUsr.setLayout(new java.awt.BorderLayout());

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

        panDetDepPrgUsr.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGenTabGen.add(panDetDepPrgUsr, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 20));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panGenTabGen.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panGenTabGen);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butCerr1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr1.setText("Guardar");
        butCerr1.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerr1ActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr1);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDocActionPerformed(java.awt.event.ActionEvent evt) {
    } 
    
    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        // TODO add your handling code here:
         exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
    }//GEN-LAST:event_butConActionPerformed

    private void txtNomRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomRubActionPerformed
        // TODO add your handling code here:
        txtNomRub.transferFocus();
    }//GEN-LAST:event_txtNomRubActionPerformed

    private void txtNomRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRubFocusGained
        // TODO add your handling code here:
        strNomRub=txtNomRub.getText();
        txtNomRub.selectAll();
    }//GEN-LAST:event_txtNomRubFocusGained

    private void txtNomRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRubFocusLost
        // TODO add your handling code here:
        if (!txtNomRub.getText().equalsIgnoreCase(strNomRub)) {
            if (txtNomRub.getText().equals("")) {
                txtCodRub.setText("");
                txtNomRub.setText("");
            }else
            BuscarRub("a.tx_nom",txtNomRub.getText(),1);
        }else
        txtNomRub.setText(strNomRub);
    }//GEN-LAST:event_txtNomRubFocusLost

    private void txtCodRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRubActionPerformed
        // TODO add your handling code here:
        txtCodRub.transferFocus();
    }//GEN-LAST:event_txtCodRubActionPerformed

    private void txtCodRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRubFocusGained
        // TODO add your handling code here:
        strCodRub=txtCodRub.getText();
        txtCodRub.selectAll();
    }//GEN-LAST:event_txtCodRubFocusGained

    private void txtCodRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRubFocusLost
        // TODO add your handling code here:
        if (!txtCodRub.getText().equalsIgnoreCase(strCodRub)) {
            if (txtCodRub.getText().equals("")) {
                txtCodRub.setText("");
                txtNomRub.setText("");
            }else
            BuscarRub("a.co_rub",txtCodRub.getText(),0);
        }else
        txtCodRub.setText(strCodRub);
    }//GEN-LAST:event_txtCodRubFocusLost

    private void butRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRubActionPerformed
        // TODO add your handling code here:
        objVenConRub.setTitle("Listado de Rubros");
        objVenConRub.setCampoBusqueda(1);
        objVenConRub.show();
        if (objVenConRub.getSelectedButton()==objVenConRub.INT_BUT_ACE) {
            txtCodRub.setText(objVenConRub.getValueAt(1));
            txtNomRub.setText(objVenConRub.getValueAt(2));
            strCodRub=objVenConRub.getValueAt(1);
            strNomRub=objVenConRub.getValueAt(2);
        }
    }//GEN-LAST:event_butRubActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    
    private void butCerr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerr1ActionPerformed
        // TODO add your handling code here:
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(!verificarPeriodo()){
                    if(validarCamObl()){
                        if(guardarDat()){
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                        }else{
                            mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                        }
                    }
                    
                }else{
                        mostrarMsgInf("Egreso programado masivo ya fue realizado para el período seleccionado.");
                    }
            }
       } else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butCerr1ActionPerformed

        
        
    private boolean verificarPeriodo(){
        
    
        boolean blnRes=false;
        for(int intFil=0;intFil<objTblMod.getRowCount();intFil++){
            if(objTblMod.getValueAt(intFil, INT_TBL_VALTOTEGR_MESACT_FIL).toString().compareTo("S")==0){
                return true;
            }
        }
        return blnRes;
    }
        
    private boolean guardarDat(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strTx_tipEgrPrg ="D";
        String strTx_tipCuo = "D";
        String strNeNumCuo = "1"; //registo activo
        String strSt_Reg = "A"; //registo activo
        String strSql="";
        
        try{
            con=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      
            if(con!=null){
                
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                
                for(int i=0; i<objTblMod.getRowCount();i++){

                    if(objTblMod.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M")==0){
                        
                        if((Boolean)objTblMod.getValueAt(i, INT_TBL_CHKSEL)){
                            
                            strSql="";
                            int intCoEgr=0;
                            String strSQL="SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_egr  FROM tbm_cabingegrprgtra WHERE " +
                                    " co_emp="+objZafParSis.getCodigoEmpresa();
                             rstLoc = stmLoc.executeQuery(strSQL);
                             if(rstLoc.next())
                                 intCoEgr = rstLoc.getInt("co_egr");
                             rstLoc.close();

                            strSql+="INSERT INTO tbm_cabingegrprgtra (co_emp , co_egr , co_tra , co_rub , fe_doc , "+"\n";
                            strSql+="tx_tipegrprg , fe_pricuo , fe_ultcuo , nd_valegrprg , ne_numcuo , tx_tipcuo , st_reg , fe_ing , co_usring ) "+"\n";
                            strSql+="VALUES ( "+"\n";
                            strSql+=objZafParSis.getCodigoEmpresa() +" , \n";
                            strSql+=intCoEgr +" , \n";
                            strSql+=objTblMod.getValueAt(i, INT_TBL_CODTRA).toString() +" , \n";
                            strSql+=txtCodRub.getText() +" , \n";
                            strSql+="current_date" +" , \n";
                            strSql+=objUti.codificar(strTx_tipEgrPrg) +" , \n";
                            String strFePriCuo=cboPerAAAA.getSelectedItem().toString()+"-"+cboPerMes.getSelectedIndex()+"-"+"01";
                            strSql+=objUti.codificar(strFePriCuo) +" , \n";
                            strSql+=objUti.codificar(strFePriCuo) +" , \n";
                            strSql+=objTblMod.getValueAt(i, INT_TBL_VALTOTEGR_MESACT).toString() +" , \n";
                            strSql+=objUti.codificar(strNeNumCuo) +" , \n";
                            strSql+=objUti.codificar(strTx_tipCuo) +" , \n";
                            strSql+=objUti.codificar(strSt_Reg) +" , \n";
                            strSql+="current_timestamp" +" , \n";
                            strSql+=objZafParSis.getCodigoUsuario() +" );";
                            System.out.println("insertTbmCabIngEgrPrgTra: " + strSql);
                            stmLoc.executeUpdate(strSql);

                            strSql="";
                            strSql+="INSERT INTO tbm_detingegrprgtra (co_emp , co_egr , co_reg , fe_cuo , nd_valcuo , st_reg)"+"\n";
                            strSql+="VALUES ( "+"\n";
                            strSql+=objZafParSis.getCodigoEmpresa() +" , \n";
                            strSql+=intCoEgr +" , 1 , \n";
                            strSql+=objUti.codificar(strFePriCuo) +" , \n";
                            strSql+=objTblMod.getValueAt(i, INT_TBL_VALTOTEGR_MESACT).toString() +" , \n";
                            strSql+=objUti.codificar(strSt_Reg) +" );";
                            System.out.println("insertTbmDetIngEgrPrgTra:" + strSql);
                            stmLoc.executeUpdate(strSql);
                        }
                    }
                }
            }
            if(blnRes){
                con.commit();
                new RRHHDao(objUti, objZafParSis).callServicio9();
            }
            
        }catch(java.sql.SQLException Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
        try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
        }
    return blnRes;
}
    
    private void cboPerMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPerMesActionPerformed

    public void BuscarRub(String campo,String strBusqueda,int tipo){
        
        objVenConRub.setTitle("Listado de Rubros");
        if(objVenConRub.buscar(campo, strBusqueda )) {
            txtCodRub.setText(objVenConRub.getValueAt(1));
            txtNomRub.setText(objVenConRub.getValueAt(2));
        }else{
            objVenConRub.setCampoBusqueda(tipo);
            objVenConRub.cargarDatos();
            objVenConRub.show();
            if (objVenConRub.getSelectedButton()==objVenConRub.INT_BUT_ACE) {
                txtCodRub.setText(objVenConRub.getValueAt(1));
                txtNomRub.setText(objVenConRub.getValueAt(2));
        }else{
                txtCodRub.setText(strCodRub);
                txtNomRub.setText(strNomRub);
  }}}

private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if(validarCamObl()){
                if(cargarDat()){
                    tabGen.setSelectedIndex(1);
                }
        }else{
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
            }
        objThrGUI=null;
    }
}

    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
//        String strNomCam = null;
        boolean blnOk = true;

        if(cboPerAAAA.getSelectedItem().toString().compareTo("Año")==0){
            cboPerAAAA.requestFocus();
            cboPerAAAA.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Año</FONT> no ha sido seleccionado.<BR>Seleccione un año y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(cboPerMes.getSelectedItem().toString().compareTo("Mes")==0){
            cboPerMes.requestFocus();
            cboPerMes.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Mes</FONT> no ha sido seleccionado.<BR>Seleccione un mes y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        return blnOk;
    }
    
    private void MensajeValidaCampo(String strNomCampo){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        obj.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public int obtenerCantidadRegistros(){
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="", sqlAux="";
        int intCantReg=0;
        try{
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
        
      if(conn!=null){
          
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

          strSql = "select a.co_tra, (b.tx_nom || ' ' || b.tx_ape) as nomCom from tbm_cabconasitra a " +
                   "inner join tbm_tra b on (a.co_tra=b.co_tra) "+
                   "inner join tbm_traemp c on (a.co_tra=c.co_tra and c.st_recAlm like 'S') "+sqlAux + " and a.ho_ent is not null " + 
                   "order by b.co_tra";

           System.out.println(strSql);
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
               intCantReg++;
           }
           
           rstLoc.close();
           rstLoc=null;

           stmLoc.close();
           stmLoc=null;
           conn.close();
           conn=null;
      }
        }catch(java.sql.SQLException Evt) { Evt.printStackTrace(); objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { Evt.printStackTrace();  objUti.mostrarMsgErr_F1(this, Evt);  }
        intCanReg=intCantReg;
        return intCanReg;
    }
    
/**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn = null;
  java.sql.Statement stmLoc = null;
  java.sql.ResultSet rstLoc = null;
  String strSql="";
  
  java.util.Vector vecReg = null;
  
   try{
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
          stmLoc=conn.createStatement();
          int intMesPas=cboPerMes.getSelectedIndex()-1;
          if(intMesPas==0){
              intMesPas=12;
          }
          int intMesAct=cboPerMes.getSelectedIndex();
          strSql+="SELECT b.co_emp , g.tx_nom as empresa , d1.co_rub , b.co_tra , (a.tx_ape || ' ' || a.tx_nom) as empleado ,\n" +
                "sum(e.nd_valcuo) as nd_valCuoMA , e1.nd_valcuo as nd_valCuoAct ,\n" +
                "case (e1.nd_valcuo is null) when true then '' else 'S' end as fil\n" +
                "FROM\n" +
                "tbm_tra a\n" +
                "INNER JOIN tbm_traemp b ON (b.co_emp="+objZafParSis.getCodigoEmpresa()+" AND b.co_tra=a.co_tra AND b.st_reg='A')\n" +
                "INNER JOIN tbm_emp c ON (c.co_emp=b.co_emp)\n" +
                "LEFT OUTER JOIN tbm_cabingegrprgtra d ON (d.co_emp = b.co_emp AND d.co_tra = b.co_tra AND d.co_rub = "+txtCodRub.getText()+" AND d.fe_ing BETWEEN \n" +
                "(select fe_des from tbm_feccorrolpag WHERE co_emp = b.co_emp AND ne_ani = "+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes = "+intMesPas+" AND ne_per = 1 )\n" +
                "AND (select fe_has from tbm_feccorrolpag WHERE co_emp = b.co_emp AND ne_ani = "+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes = "+intMesPas+" AND ne_per = 2) )\n" +
                "LEFT OUTER JOIN tbm_detingegrprgtra e ON (e.co_emp = b.co_emp AND e.co_egr = d.co_egr AND e.st_reg='A')\n" +
                "LEFT OUTER JOIN tbm_cabingegrprgtra d1 ON (d1.co_emp = b.co_emp AND d1.co_tra = b.co_tra AND d1.co_rub = "+txtCodRub.getText()+" AND d1.fe_ing BETWEEN \n" +
                "(select fe_des from tbm_feccorrolpag WHERE co_emp = b.co_emp AND ne_ani = "+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes = "+intMesAct+" AND ne_per = 1 )\n" +
                "AND (select fe_has from tbm_feccorrolpag WHERE co_emp = b.co_emp AND ne_ani = "+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes = "+intMesAct+" AND ne_per = 2) )\n" +
                "LEFT OUTER JOIN tbm_detingegrprgtra e1 ON (e1.co_emp = b.co_emp AND e1.co_egr = d1.co_egr AND e1.st_reg='A')\n" +
                "INNER JOIN tbm_emp g ON (g.co_emp = b.co_emp)\n" +
                "GROUP BY b.co_emp , empresa , d1.co_rub , b.co_tra , empleado , e1.nd_valcuo\n" +
                "ORDER BY EMPLEADO";
                System.out.println("strSql: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                
                vecDat = new Vector();
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CHKSEL,Boolean.FALSE);
                    vecReg.add(INT_TBL_CODEMP,rstLoc.getInt("co_emp"));
                    vecReg.add(INT_TBL_EMP,rstLoc.getString("empresa"));
                    vecReg.add(INT_TBL_CODTRA,rstLoc.getString("co_tra"));
                    vecReg.add(INT_TBL_NOMTRA,rstLoc.getString("empleado"));
                    vecReg.add(INT_TBL_VALTOTEGR_MESANT,rstLoc.getDouble("nd_valCuoMA"));
                    vecReg.add(INT_TBL_VALTOTEGR_MESACT,rstLoc.getDouble("nd_valCuoAct"));
                    vecReg.add(INT_TBL_VALTOTEGR_MESACT_FIL,rstLoc.getString("fil"));
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                pgrSis.setIndeterminate(false);
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                objTblTot.calcularTotales();
                vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
   finally {
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            try{conn.close();conn=null;}catch(Throwable ignore){}
        }
//    System.gc();
    return blnRes;
}

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
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCerr1;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butRub;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCabDepPrgUsr;
    private javax.swing.JPanel panDetDepPrgUsr;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodRub;
    private javax.swing.JTextField txtNomRub;
    // End of variables declaration//GEN-END:variables




private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    
     int intCol=tblDat.columnAtPoint(evt.getPoint());
     String strMsg="";

    switch (intCol){

        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_CHKSEL:
            strMsg="Seleccionar";
            break;
        case INT_TBL_CODEMP:
            strMsg="Código de la empresa";
            break;
        case INT_TBL_EMP:
            strMsg="Nombre de la empresa";
            break;
        case INT_TBL_CODTRA:
            strMsg="Código del empleado";
            break;
        case INT_TBL_NOMTRA:
            strMsg="Nombres y apellidos del empleado";
            break;
        case INT_TBL_VALTOTEGR_MESANT:
            strMsg="Valor total del egreso programado";
            break;
        case INT_TBL_VALTOTEGR_MESACT:
            strMsg="Valor total del egreso programado";
            break;
        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}
}