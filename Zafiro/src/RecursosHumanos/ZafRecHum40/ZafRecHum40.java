/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package RecursosHumanos.ZafRecHum40;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenDep;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenEmp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * Mantenimiento de las cuentas contables por empleado y por rubro
 * @author Roberto Flores
 * Guayaquil 24/07/2012
 */

public class ZafRecHum40 extends javax.swing.JInternalFrame {


  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
  private ZafTblFilCab objTblFilCab;
  private ZafUtil objUti;
  private ZafTblCelEdiChk zafTblCelEdiChkPla;                             //Editor de Check Box para campo Laborable
  private ZafTblCelRenChk zafTblCelRenChkPla;                             //Renderer de Check Box para campo Laborable
  private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
  
  Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblCol;
  
  private ZafTblCelRenBut objTblCelRenBut;                                //Render: Presentar JButton en JTable.
  private ZafTblCelEdiButVco objTblCelEdiButVcoCta;                       //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCta;                     //Editor: JTextField de consulta en celda.
  
  private ZafThreadGUI objThrGUI;
//  private ZafSelFec objSelFec;
  private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
//  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                                     //PopupMenu: Establecer PeopuMenú en JTable.
  
  private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.

  private static final int INT_TBL_DAT_LINEA = 0;                        //Índice de columna Linea.
  private static final int INT_TBL_DAT_COD_EMP = 1;                      
  private static final int INT_TBL_DAT_NOM_EMP = 2;
  private static final int INT_TBL_DAT_COD_TRA = 3;
  private static final int INT_TBL_DAT_NOM_APE_TRA = 4;
  private static final int INT_TBL_DAT_COD_RUB = 5;
  private static final int INT_TBL_DAT_NOM_RUB = 6;
  private static final int INT_TBL_DAT_COD_CTA = 7;
  private static final int INT_TBL_DAT_NUM_CTA = 8;
  private static final int INT_TBL_DAT_BUT_CTA = 9;
  private static final int INT_TBL_DAT_NOM_CTA = 10;
  private static final int INT_TBL_DAT_EST=11;
  
  

  String strVersion=" v1.0 ";
  
  private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                               

  private int intCodEmp;                                                 //Código de la empresa.
  private boolean blnMod;                                                //Indica si han habido cambios en el documento
  private boolean blnConsDat;
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;
  private ZafTblTot objTblTot;                        //JTable de totales.
  
  private String strCodEmp, strNomEmp;
  private String strCodDep = "";
  private String strDesLarDep = "";
  private String strCodTra = "";
  private String strNomTra = "";
    
  private ZafVenCon vcoEmp;                                   //Ventana de consulta.
  private ZafVenCon vcoDep;                                   //Ventana de consulta.
  private ZafVenCon vcoTra;
  private ZafVenCon vcoCta;                               //Ventana de consulta.
  
  private String strMe;
  private String strAux="";
  
  //ESTA VARIABLE SE CREO PARA Q CONTENGA EL CODTIPDOC PARA EL NUEVO ESQUEMA DE CTAS POR USUARIO
    private int intCodTipDocGlb;
    
    final java.awt.Color colFonCol =new java.awt.Color(255,172,165);
    
    private int intCantRubAct=0;
    
    private ZafPerUsr objPerUsr;
  
    /** Creates new form ZafRecHum40 */
    public ZafRecHum40(Librerias.ZafParSis.ZafParSis obj) {

        try{

            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                txtCodEmp.setEnabled(true);
                txtNomEmp.setEnabled(true);
                butEmp.setEnabled(true);
            }
            
            
            this.setTitle(objZafParSis.getNombreMenu()+"  "+strVersion+" ");
            lblTit.setText(objZafParSis.getNombreMenu());

	    objUti = new ZafUtil();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

	    this.objZafParSis = (ZafParSis) objZafParSis.clone();
            objPerUsr=new ZafPerUsr(objZafParSis);
            
            //Configurar las ZafVenCon.
            configurarVenConCta();
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            
            configuraTbl();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3168)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3169)){
                butGua.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3170)){
                butCer.setVisible(true);
            }
            
            blnMod = false;
            blnConsDat = false;

         }catch (Exception e){ e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); }  /**/
    }

    public class MyTableModelListener implements TableModelListener {
    JTable table;

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    MyTableModelListener(JTable table) {
        this.table = table;
    }

    public void tableChanged(TableModelEvent e) {
        if(!blnConsDat){
            switch (e.getType()) {
              case TableModelEvent.UPDATE:
                  blnMod = true;
                break;
            }
        }
        blnConsDat = false;
    }
}

    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        String strSQL="";
        
        try{
            
        //final int intColFij = 4;
        
        
        //Configurar JTable: Establecer el modelo.
        vecCab=new Vector();
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LINEA,"");
        vecCab.add(INT_TBL_DAT_COD_EMP,"Cód. Emp.");
        vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
        vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
        vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
        vecCab.add(INT_TBL_DAT_COD_RUB,"Cód. Rub.");
        vecCab.add(INT_TBL_DAT_NOM_RUB,"Rubro");
        vecCab.add(INT_TBL_DAT_COD_CTA,"Cód. Cta.");
        vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm. Cta.");
        vecCab.add(INT_TBL_DAT_BUT_CTA,"");
        vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
        vecCab.add(INT_TBL_DAT_EST,"ESTADO");
        
        
        
        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblDat.setModel(objTblMod);
        
        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LINEA);
        
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        //Configurar JTable: Establecer el ancho de las columnas.
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(250);
        tcmAux.getColumn(INT_TBL_DAT_COD_RUB).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(150);
        tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(50);
        
        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_RUB, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);
        
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_DAT_NUM_CTA);
        vecAux.add("" + INT_TBL_DAT_BUT_CTA);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LINEA).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            //objZafDtePckEdi = new ZafDtePckEdi(strFormat);
            //tcmAux.getColumn(INT_TBL_FECHA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormat));
            
            
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_COD_CTA;
            intColTbl[1]=INT_TBL_DAT_NUM_CTA;
            intColTbl[2]=INT_TBL_DAT_NOM_CTA;
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
//            //objTblCelRenLbl.setBackground(Color.WHITE);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            objTblCelRenLblCol = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RUB).setCellRenderer(objTblCelRenLblCol);
            //tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setCellRenderer(objTblCelRenLblCol);
            
            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell=objTblCelRenLblCol.getRowRender();

//                intCantRubAct
                String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
//
                if(str.equals("S")){
                    objTblCelRenLblCol.setBackground(colFonCol);
                    objTblCelRenLblCol.setForeground(java.awt.Color.BLACK);
                }else {
                    objTblCelRenLblCol.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLblCol.setForeground(java.awt.Color.BLACK);
                }
            }
            public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell=objTblCelRenLbl.getRowRender();

//                intCantRubAct
                String str=tblDat.getValueAt(intCell, INT_TBL_DAT_EST).toString();
//
                if(str.equals("S")){
                    objTblCelRenLbl.setBackground(colFonCol);
                    objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                }else {
                    objTblCelRenLbl.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
                }
                
                

            }
        });
            
            //tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLblCol);
            objTblCelEdiTxtVcoCta=new ZafTblCelEdiTxtVco(tblDat, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoCta);
            objTblCelEdiTxtVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
//                    if(strEstCncDia.equals("S")){
//                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>Desconcilie la cuenta en el documento a modificar y vuelva a intentarlo.</HTML>");
////                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
//                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
//                    }
//                    else if(strEstCncDia.equals("B")){
//                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
//                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
//                    }
//                    else{
//                        //Permitir de manera predeterminada la operaciï¿½n.
//                        blnCanOpe=false;
//                        //Generar evento "beforeEditarCelda()".
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
//                        //Permitir/Cancelar la ediciï¿½n de acuerdo a "cancelarOperacion".
//                        if (blnCanOpe)
//                        {
//                            objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
//                        }
//                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
//                    System.out.println();
//                    
//                    if(strEstCncDia.equals("B")){
//
//                    }
//                    else{
//                        //Generar evento "afterEditarCelda()".
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);
//                    }

                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    
                    System.out.println();
                    
//                    intBanNumCta++;

                    //Generar evento "beforeConsultarCuentas()".
//                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON_CTA);
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.setCriterio1(7);
                    if(objZafParSis.getCodigoUsuario()!=1)
                    {
                        strMe="";
                        strMe+=" AND a2.co_tipdoc=" + intCodTipDocGlb + "";
                        strMe+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                        vcoCta.setCondicionesSQL(strMe);
                    }
                    setPuntosCta();

                }

                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    

                }
                

            });

            
            /**************************************************************************************/
            /**************************************************************************************/
            /**************************************************************************************/
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellRenderer(objTblCelRenBut);
            
            
            
            objTblCelEdiButVcoCta=new ZafTblCelEdiButVco(tblDat, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellEditor(objTblCelEdiButVcoCta);
             objTblCelEdiButVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                 public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     
                 }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     
                 }
                  public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      
                  }
                  public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      
                  }
             });
            intColVen=null;
            intColTbl=null;
         
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl=null;

            //Configurar JTable: Modo de operación del JTable.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tcmAux=null;
            blnRes=true;

        }catch(Exception e) {  e.printStackTrace(); blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
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
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
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
        optTod.setBounds(20, 80, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 100, 370, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(50, 120, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
        txtCodEmp.setBounds(150, 120, 60, 20);

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
        txtNomEmp.setBounds(210, 120, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(460, 120, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(50, 140, 100, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(150, 140, 60, 20);

        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(210, 140, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(460, 140, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(50, 160, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(150, 160, 60, 20);

        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(210, 160, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(460, 160, 20, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

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
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Reporte", jPanel1);

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

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(90, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel5.add(butGua);

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(90, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel5.add(butCer);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        //consultarRepEmp();
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed


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

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        //txtCodEmp.selectAll();
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
    
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
}//GEN-LAST:event_txtCodEmpFocusLost

    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        //txtNomEmp.transferFocus();
        txtNomEmp.transferFocus();
        
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
    strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);

}//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
    if (txtNomEmp.isEditable())
        {
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
        }

}//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        java.sql.Connection conn = null;
        
        try{
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null){
                
                conn.setAutoCommit(false);
                if(guardarDat(conn)){
                    conn.commit();
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito");
                    butConActionPerformed(null);
                }else{
                    conn.rollback();
                }
            }
        }catch(java.sql.SQLException Evt) {
                //blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
                //blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{conn.close();}catch(Throwable ignore){}
        }
            
    }//GEN-LAST:event_butGuaActionPerformed

    private boolean guardarDat(java.sql.Connection con){
        
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL="";
        
        try{
            
            if(con!=null){

                stmLoc=con.createStatement();

                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_DAT_LINEA).toString().compareTo("M")==0){

                        Object obj = tblDat.getValueAt(i, INT_TBL_DAT_COD_CTA);
                        String strCo_Cta=null;
                        if(obj!=null){
                            if(obj.toString().compareTo("")!=0){
                                strCo_Cta = tblDat.getValueAt(i, INT_TBL_DAT_COD_CTA).toString();
                            }
                        }
                        
                        strSQL="";
                        strSQL+="UPDATE tbm_suetra SET co_cta = " + strCo_Cta;
                        strSQL+=" WHERE co_emp = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                        strSQL+=" AND co_rub = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_RUB).toString();
                        strSQL+=" AND co_tra = " + tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
                        System.out.println("act_co_cta_rub_tra: " + strSQL);
                        stmLoc.executeUpdate(strSQL);

                    }
                }
            }
        }catch(java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally {
            try{stmLoc.close();}catch(Throwable ignore){}
            try{rstLoc.close();}catch(Throwable ignore){}
        }
        
        return blnRes;
        
    }
    
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         exitForm();
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI==null) {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed

}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed

        if(optTod.isSelected()){
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        //consultarRepDep();
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        // TODO add your handling code here:
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo,String strBusqueda,int tipo){
        
        vcoDep.setTitle("Listado de Departamentos");
        if(vcoDep.buscar(campo, strBusqueda )) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton()==vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
  }}}
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed

        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep=txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep))
            {
                if (txtNomDep.getText().equals(""))
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                }
                else
                {
                    mostrarVenConDep(2);
                }
            }
            else
                txtNomDep.setText(strDesLarDep);
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep=txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        //consultarRepTra();
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
        
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
                txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        strCodTra=txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
//        if (txtMinGra.getText().equals("")) {
//            txtMinGra.setText(" ");
//        }
    }//GEN-LAST:event_tblDatFocusGained

    private class ZafThreadGUI extends Thread
    {
        public void run(){

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            cargarDat();

            tabGen.setSelectedIndex(1);

            objThrGUI=null;
        }
    }

    /**
     * Se encarga de cargar la informacion en la tabla
     * @return  true si esta correcto y false  si hay algun error
     */
    private boolean cargarDat(){

        blnConsDat = true;
        boolean blnRes=false;
        blnMod = false;

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="",sqlFil="";
        Vector vecData;
        Vector vecDataCon;
        
        
        ArrayList<String> lstPla = null;

        try{

            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if(conn!=null){

                stmLoc=conn.createStatement();
                
                strSql="";
                strSql+=" select count(co_rub) as cantidad from tbm_rubrolpag a";
                strSql+=" where a.st_reg like 'A'";
                strSql+="  and a.co_rub in (select distinct co_rub from tbm_rubrolpagemp";
                strSql+=" where co_emp = 1)";
                
                System.out.println("cantidad de rubros activos: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                
                while(rstLoc.next()){
                    intCantRubAct=rstLoc.getInt("cantidad");
                }
                
                
                //vecData = new Vector();
                vecDataCon = new Vector();

                
                String sqlFilEmp = "";

                if(txtCodEmp.getText().compareTo("")!=0){
                    sqlFilEmp+= " and b.co_emp  = " + txtCodEmp.getText() + " ";
                }

                if(txtCodTra.getText().compareTo("")!=0){
                    sqlFilEmp+= " AND a.co_tra  = " + txtCodTra.getText() + " ";
                }

                if(txtCodDep.getText().compareTo("")!=0){
                    sqlFilEmp+= " AND b.co_dep  = " + txtCodDep.getText() + " ";
                }

                

                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    
                    
                    if(objZafParSis.getCodigoUsuario()==1){
                        
                        strSql="";
                        strSql="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra,";
                        strSql+=" f.co_rub,h.tx_nom as tx_nomrub,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep from tbm_tra a";
                        strSql+=" left outer join tbm_traemp b on a.co_tra = b.co_tra";
                        strSql+=" left outer join tbm_dep c on c.co_dep=b.co_dep";
                        strSql+=" inner join tbm_emp d on (d.co_emp=b.co_emp)";
                        //strSql=" left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla)";
                        strSql+=" left outer join tbm_suetra f on (b.co_emp=f.co_emp and a.co_tra=f.co_tra";
                        strSql+=" and f.co_rub in (select distinct co_rub from tbm_rubrolpagemp where co_emp=b.co_emp))";
                        strSql+=" inner join tbm_rubrolpag  h on (h.co_rub=f.co_rub and h.st_reg like 'A')";
                        strSql+=" left outer join tbm_placta g on (b.co_emp=g.co_emp and f.co_cta=g.co_cta)";

                        strSql+=" where a.st_reg like 'A'";
                        strSql+=" " + sqlFilEmp + " ";
                        strSql+=" AND b.co_emp not in (0)";
                        
                        strSql+=" group by d.co_emp, d.tx_nom, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) , f.co_rub,h.tx_nom,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep";
                        //strSql+=" AND b.co_emp = " + objZafParSis.getCodigoEmpresa()";
                        
                    }else{
                        
                        strSql="";
                        strSql="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra,";
                        strSql+=" f.co_rub,h.tx_nom as tx_nomrub,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep from tbm_tra a";
                        strSql+=" left outer join tbm_traemp b on a.co_tra = b.co_tra";
                        strSql+=" left outer join tbm_dep c on c.co_dep=b.co_dep";
                        strSql+=" inner join tbm_emp d on (d.co_emp=b.co_emp)";
                        //strSql=" left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla)";
                        strSql+=" left outer join tbm_suetra f on (b.co_emp=f.co_emp and a.co_tra=f.co_tra";
                        strSql+=" and f.co_rub in (select distinct co_rub from tbm_rubrolpagemp where co_emp=b.co_emp))";
                        strSql+=" inner join tbm_rubrolpag  h on (h.co_rub=f.co_rub and h.st_reg like 'A')";
                        strSql+=" left outer join tbm_placta g on (b.co_emp=g.co_emp and f.co_cta=g.co_cta)";
                        
                        strSql+=" inner join tbr_depprgusr k on(b.co_dep=k.co_dep and k.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objZafParSis.getCodigoUsuario()+ " ";
                        strSql+=" and co_mnu="+objZafParSis.getCodigoMenu()+" )) ";
                        
                        strSql+=" where a.st_reg like 'A'";
                        strSql+=" " + sqlFilEmp + " ";
                        strSql+=" AND b.co_emp not in (0)";
                        strSql+=" group by d.co_emp, d.tx_nom, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) , f.co_rub,h.tx_nom,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep";
                        //strSql+=" AND b.co_emp = " + objZafParSis.getCodigoEmpresa()";
                        
                    }
                }else{

                    if(objZafParSis.getCodigoUsuario()==1){
                        
                        strSql="";
                        strSql="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra,";
                        strSql+=" f.co_rub,h.tx_nom as tx_nomrub,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep from tbm_tra a";
                        strSql+=" left outer join tbm_traemp b on a.co_tra = b.co_tra";
                        strSql+=" left outer join tbm_dep c on c.co_dep=b.co_dep";
                        strSql+=" inner join tbm_emp d on (d.co_emp=b.co_emp)";
                        //strSql=" left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla)";
                        strSql+=" left outer join tbm_suetra f on (b.co_emp=f.co_emp and a.co_tra=f.co_tra";
                        strSql+=" and f.co_rub in (select distinct co_rub from tbm_rubrolpagemp where co_emp=b.co_emp))";
                        strSql+=" inner join tbm_rubrolpag  h on (h.co_rub=f.co_rub and h.st_reg like 'A')";
                        strSql+=" left outer join tbm_placta g on (b.co_emp=g.co_emp and f.co_cta=g.co_cta)";
                        strSql+=" where a.st_reg like 'A'";
                        strSql+=" "+ sqlFilEmp + " ";
                        strSql+=" AND b.co_emp = " + objZafParSis.getCodigoEmpresa();
                        strSql+=" group by d.co_emp, d.tx_nom, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) , f.co_rub,h.tx_nom,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep";
                        
                    }else{
                        
                        strSql="";
                        strSql="select d.co_emp, d.tx_nom as nomemp, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) as nomtra,";
                        strSql+=" f.co_rub,h.tx_nom as tx_nomrub,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep from tbm_tra a";
                        strSql+=" left outer join tbm_traemp b on a.co_tra = b.co_tra";
                        strSql+=" left outer join tbm_dep c on c.co_dep=b.co_dep";
                        strSql+=" inner join tbm_emp d on (d.co_emp=b.co_emp)";
                        //strSql=" left outer join tbm_cabPlaRolPag e on (b.co_emp=e.co_emp and b.co_pla=e.co_pla)";
                        strSql+=" left outer join tbm_suetra f on (b.co_emp=f.co_emp and a.co_tra=f.co_tra";
                        strSql+=" and f.co_rub in (select distinct co_rub from tbm_rubrolpagemp where co_emp=b.co_emp))";
                        strSql+=" inner join tbm_rubrolpag  h on (h.co_rub=f.co_rub and h.st_reg like 'A')";
                        strSql+=" left outer join tbm_placta g on (b.co_emp=g.co_emp and f.co_cta=g.co_cta)";
                        
                        strSql+=" inner join tbr_depprgusr k on(b.co_dep=k.co_dep and k.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objZafParSis.getCodigoUsuario()+ " ";
                        strSql+=" and co_mnu="+objZafParSis.getCodigoMenu()+" )) ";
                        
                        strSql+=" where a.st_reg like 'A'";
                        strSql+=" "+ sqlFilEmp + " ";
                        strSql+=" AND b.co_emp = " + objZafParSis.getCodigoEmpresa();
                        strSql+=" group by d.co_emp, d.tx_nom, a.co_tra, (a.tx_ape || ' ' || a.tx_nom) , f.co_rub,h.tx_nom,f.co_cta,g.tx_codcta,g.tx_deslar,b.co_dep";
                        
                    }
                }

                //strSql+= " order by a.co_tra";
                strSql+=" order by nomtra,co_rub";

                System.out.println("q consul: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);

                
                int intCont=1;
                int intEsPI=1;
                while(rstLoc.next()){

                    java.util.Vector vecReg = new java.util.Vector();

                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getString("co_emp"));
                    
                    vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("nomemp"));
                    vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getString("co_tra"));
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("nomtra"));
                    
                    vecReg.add(INT_TBL_DAT_COD_RUB,rstLoc.getString("co_rub"));
                    vecReg.add(INT_TBL_DAT_NOM_RUB,rstLoc.getString("tx_nomrub"));
                    
                    vecReg.add(INT_TBL_DAT_COD_CTA,rstLoc.getString("co_cta"));
                    vecReg.add(INT_TBL_DAT_NUM_CTA,rstLoc.getString("tx_codcta"));
                    
                    vecReg.add(INT_TBL_DAT_BUT_CTA,"...");
                    vecReg.add(INT_TBL_DAT_NOM_CTA,rstLoc.getString("tx_deslar"));
                    
                    
                    if(intEsPI%2==1){
                        vecReg.add(INT_TBL_DAT_EST,"S");
                    }else{
                        vecReg.add(INT_TBL_DAT_EST,"V");
                    }
                    
                    
                    if(intCont==intCantRubAct){
                       intEsPI++; 
                       intCont=0;
                    }
                    
                    intCont++;
                    vecDataCon.add(vecReg);
                }

                objTblMod.setData(vecDataCon);
                tblDat .setModel(objTblMod);
                
//                if(tblDat.getRowCount()>0){
//                    for(int intFil=0; intFil<tblDat.getRowCount();intFil++){
//                        
//                        *****
//                        
//                    }
//                }
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
                //lblMsgSis.setText("Listo");
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                blnRes=true;
           }}catch(java.sql.SQLException Evt) {  Evt.printStackTrace();blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt) { Evt.printStackTrace();blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
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
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){

            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";

            switch (intCol){

                case INT_TBL_DAT_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Cód. Emp.";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y Apellidos del empleado";
                    break;
                case INT_TBL_DAT_COD_RUB:
                    strMsg="Código del rubro";
                    break;
                case INT_TBL_DAT_NOM_RUB:
                    strMsg="Nombre del rubro";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de la cuenta";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }

    private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
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
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
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

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        String strSQL="";
        try{
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
            arlAncCol.add("414");
            //Armar la sentencia SQL.

//            String strAux = "";
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                strAux = " and a1.co_emp in ("+objParSis.getCodigoEmpresa() +")";
//            }
            
            if(objZafParSis.getCodigoUsuario()==1){
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')" + strAux;
//                strSQL+=" ORDER BY a1.co_emp";
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
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
    private boolean mostrarVenConDep(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                        
                        /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(7))))
                        {
                            
                            if (tooBar.getEstado()=='n')
                            {
                                //strAux=vcoTipDoc.getValueAt(4);
                                //txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            //txtNumDoc1.selectAll();
                            //txtNumDoc1.requestFocus();
                        }*/
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                        
                        /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }*/
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                            /*if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado()=='n')
                                {
                                    strAux=vcoTipDoc.getValueAt(4);
                                    txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                                }
                                intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                                strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                            }
                            else
                            {
                                txtDesCorTipDoc.setText(strDesCorTipDoc);
                            }*/
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                        //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(4))))
                        //{
                            //txtCodTipDoc.setText(vcoDep.getValueAt(1));
                            //txtDesCorTipDoc.setText(vcoDep.getValueAt(2));
                            //txtDesLarTipDoc.setText(vcoDep.getValueAt(3));
                            //if (tooBar.getEstado()=='n')
                            //{
                                //strAux=vcoTipDoc.getValueAt(4);
                                //txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            //}
                            /*intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();*/
                        //}
                        //else
                        //{
                            //txtNomDep.setText(strNomDep);
                        //}
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            //if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoDep.getValueAt(3))))
                            //{
                                
                                txtCodDep.setText(vcoDep.getValueAt(1));
                                txtNomDep.setText(vcoDep.getValueAt(3));
                                
                                //txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                //txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                //if (tooBar.getEstado()=='n')
                                //{
                                    /*strAux=vcoTipDoc.getValueAt(4);
                                    txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));*/
                                //}
                                /*intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                                strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();*/
                            //}
//                            else
//                            {
//                                txtNomDep.setText(strNomDep);
//                            }
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            String strSQL="";
            if(objZafParSis.getCodigoEmpresaGrupo()==objZafParSis.getCodigoEmpresa()){
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSQL+="SELECT distinct a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.tx_tipCta='D'";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
                else
                {
    //                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
    //                strSQL+=" FROM tbm_placta as a1";
    //                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
    //                strSQL+=" WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresa();
    //                strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
    //                strSQL+=" AND a1.tx_tipCta='D'";
    //                strSQL+=" ORDER BY a1.tx_codCta";
                    strSQL+="SELECT distinct a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.tx_tipCta='D'";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
                
            }else{
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.tx_tipCta='D'";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
                else
                {
    //                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
    //                strSQL+=" FROM tbm_placta as a1";
    //                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
    //                strSQL+=" WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresa();
    //                strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
    //                strSQL+=" AND a1.tx_tipCta='D'";
    //                strSQL+=" ORDER BY a1.tx_codCta";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.tx_tipCta='D'";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
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
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            if(objZafParSis.getCodigoUsuario()==1){
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }else{
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objZafParSis.getCodigoUsuario()+" "+
                        //"and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";
                        "and co_mnu="+objZafParSis.getCodigoMenu()+")";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");
            
            
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where a.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            //intColOcu[0]=3;
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void setPuntosCta(){
        strAux=objTblCelEdiTxtVcoCta.getText();
        String strCodCtaOri=strAux;
        String strCodCtaDes="";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta=strCodCtaOri.length();
        int intLonCodCtaMen=intLonCodCta-1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig=intLonCodCta-2;
        if (strCodCtaOri.length()<=1)
            return;
        else{
//            if(  (strCodCtaOri.length() % 2) != 0 ){
                chrCtaOri=strCodCtaOri.charAt(1);
                if(chrCtaOri!='.'){
                    for (int i=0; i < strCodCtaOri.length(); i++){
                        if(i==0){
                            strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            strCodCtaDes=strCodCtaDes+".";
                        }
                        else{
                            if(  (strCodCtaOri.length() % 2) == 0 ){
                                if(((i % 2)==0)&&(i<intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                            else{
                                if(((i % 2)==0)&&(i!=intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                        }
                    }
                    objTblCelEdiTxtVcoCta.setText(strCodCtaDes);
                }
//            }
        }
    }
}