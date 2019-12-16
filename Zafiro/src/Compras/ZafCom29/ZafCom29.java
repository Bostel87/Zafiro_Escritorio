/*
 * ZafCom01_02.java
 *
 * Created on May 12, 2009, 12:34 PM
 */

package Compras.ZafCom29;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;

/**
 *
 * @author  jayapata
 */
public class ZafCom29 extends javax.swing.JInternalFrame {
     Librerias.ZafParSis.ZafParSis objZafParSis;
      private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
      private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
      Librerias.ZafUtil.UltDocPrint objUltDocPrint;
       private java.util.Date datFecAux;  
       Librerias.ZafInventario.ZafInvItm objInvItm;

       Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;

     ZafUtil objUti;
     Vector vecEmp1;
     Vector vecEmp2;
     
     Vector vecBod1;
     Vector vecBod2;
     
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_SELITM   = 1 ;
    final int INT_TBL_CODITM   = 2 ;
    final int INT_TBL_ITMALT   = 3 ;
    final int INT_TBL_DESITM   = 4 ;
    final int INT_TBL_UNIDAD   = 5 ;
    final int INT_TBL_STKACT   = 6 ;
    final int INT_TBL_PREVTA   = 7 ;
    final int INT_TBL_CANMOV   = 8 ;
    final int INT_TBL_COSUNI   = 9 ;
    final int INT_TBL_CODITM1   = 10 ;
    final int INT_TBL_CODITM2   = 11 ;
    final int INT_TBL_CANVEN    = 12 ;
    final int INT_TBL_SALDOS    = 13 ;
   
    
    int intEstConEmp=0;
    int iniCodEmpComRecosteo=0;
        

    StringBuffer stbDocRelEmpLoc;
    StringBuffer stbDocRelEmpRem;
    int intDocRelEmpLoc=0;


    int intTipSelBod=0;
    int intTipSelCarBod1=0;
    int intTipSelCarBod2=0;

    int intCodLocDevCompra=0;
    int intCodTipDocDevCom=0;
    int intCodDocDevCom=0;

    int intCodLocDevven=0;
    int intCodTipDocDevven=0;
    int intCodDocDevven=0;




    boolean blnEstCon=false;

    private boolean blnMarTodCanTblDat=true;

    java.util.ArrayList arlItmRec;

    /** Creates new form ZafCom01_02 */
    public ZafCom29(Librerias.ZafParSis.ZafParSis obj) {
       try{  
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        
                
                
        objUti = new ZafUtil();
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
        
        initComponents();
        
      
                            
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }       
       
    }
    
    
    
    
    private void configurar(){
       try{
           
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");            
            vecCab.add(INT_TBL_SELITM,"..");    
            vecCab.add(INT_TBL_CODITM,"CóodItm");
            vecCab.add(INT_TBL_ITMALT,"Cód.Item");
            vecCab.add(INT_TBL_DESITM,"Descripcion");            
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_STKACT,"StkaAct");            
            vecCab.add(INT_TBL_PREVTA,"Pre.vta");
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_COSUNI,"cosuni");
            vecCab.add(INT_TBL_CODITM1,"coditm1");
            vecCab.add(INT_TBL_CODITM2,"coditm2");
            vecCab.add(INT_TBL_CANVEN, "Can.Ven");
            vecCab.add(INT_TBL_SALDOS, "Saldos");


                    
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
  
            lbltit.setText(""+ objZafParSis.getNombreMenu() );
            this.setTitle( ""+ objZafParSis.getNombreMenu()+" v 1.3 ");
        
          
            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_STKACT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREVTA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_SALDOS, objTblMod.INT_COL_DBL, new Integer(0), null);


            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tblDat.getTableHeader().setReorderingAllowed(false);


            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);         
            tcmAux.getColumn(INT_TBL_SELITM).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_STKACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREVTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            
            
              //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_SELITM);
            vecAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;


            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODITM1);
            arlColHid.add(""+INT_TBL_CODITM2);
            arlColHid.add(""+INT_TBL_CANVEN);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;


            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_SELITM).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                   
                }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                   int intNumFil = tblDat.getSelectedRow();

                   if(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true") ){
                     String strCanven= (tblDat.getValueAt(intNumFil, INT_TBL_STKACT)==null?"":tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString());
                     tblDat.setValueAt( strCanven, intNumFil, INT_TBL_CANMOV);
                    
                   }else
                       tblDat.setValueAt( "0", intNumFil, INT_TBL_CANMOV);
                     
                }
            }); 
            
            
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREVTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_SALDOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            

    

            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                 if(intNumFil >= 0 ) {
                     
                 if(!(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true"))){
                     MensajeInf("No esta Seleccionado el item. \n Seleccione y vuelva a intentarlo. "); tblDat.setValueAt("0", intNumFil, INT_TBL_CANMOV);
                     objTblCelEdiTxt.setCancelarEdicion(true);
                 }
                 }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 int intNumFil = tblDat.getSelectedRow();
                 if(intNumFil >= 0 ) {
                     
                 if(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true")){
                     
                  double dlbCan =  Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_STKACT)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString()));
                  double dlbCanCom = Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_CANMOV)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString()));
                  
               
                  if(dlbCanCom > dlbCan){
                     MensajeInf("La cantidad es mayor a la existente. ");
                     tblDat.setValueAt("0", intNumFil, INT_TBL_CANMOV);
                     
                  }
                  
                 }else{ MensajeInf("No esta Seleccionado el item. \n Seleccione y vuelva a intentarlo. "); 
                 tblDat.setValueAt("0", intNumFil, INT_TBL_CANMOV);
                
                 }
                   
                 }           
                    
                }
            });
            

             //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });

            
           
            

          new ZafTblOrd(tblDat);
          new ZafTblBus(tblDat);
         


        vecEmp1 =  new  java.util.Vector();
        String sql = "select co_emp , tx_nom from tbm_emp where co_emp IN( 1,2,4) ";
        objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
        objZafParSis.getClaveBaseDatos(), sql, empresa, vecEmp1);
        empresa.setSelectedIndex(0);
       
        
       
       
         setEditable(true);
          new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
        
        }catch(Exception e) {
           
            objUti.mostrarMsgErr_F1(this,e);
        }
       
    }
    











        /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

              if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_SELITM){
              if (blnMarTodCanTblDat){
                   //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        String strCanven= (tblDat.getValueAt(i, INT_TBL_STKACT)==null?"":tblDat.getValueAt(i, INT_TBL_STKACT).toString());
                        tblDat.setValueAt( strCanven, i, INT_TBL_CANMOV);
                        tblDat.setValueAt( new Boolean(true), i, INT_TBL_SELITM);
                       

                    }
                    blnMarTodCanTblDat=false;
               }else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        tblDat.setValueAt("0", i, INT_TBL_CANMOV);
                        tblDat.setValueAt( new Boolean(false), i, INT_TBL_SELITM);
                       
                
                    }
                    blnMarTodCanTblDat=true;
                }
            }


        }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);     }
    }



















    
   
    public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
       
   }
  
    
  
    
    
    
private void MensajeInf(String strMensaje){
 javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
 String strTit;
 strTit="Mensaje del sistema Zafiro";
 obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
}

    
    

    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lbltit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        empresa = new javax.swing.JComboBox();
        empresa1 = new javax.swing.JComboBox();
        bodega = new javax.swing.JComboBox();
        bodega1 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtcodaltdes = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtcodalthas = new javax.swing.JTextField();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        radDev = new javax.swing.JRadioButton();
        radCom = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

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

        lbltit.setText("jLabel1");
        jPanel1.add(lbltit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(100, 200));
        panDat.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Empresa Origen:");
        panDat.add(jLabel2);
        jLabel2.setBounds(0, 10, 120, 15);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Local:");
        panDat.add(jLabel3);
        jLabel3.setBounds(0, 30, 90, 15);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Empresa Destino:");
        panDat.add(jLabel4);
        jLabel4.setBounds(360, 10, 120, 15);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel5.setText("Local:");
        panDat.add(jLabel5);
        jLabel5.setBounds(360, 30, 110, 15);

        empresa.setFont(new java.awt.Font("SansSerif", 0, 11));
        empresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                empresaItemStateChanged(evt);
            }
        });
        empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresaActionPerformed(evt);
            }
        });
        panDat.add(empresa);
        empresa.setBounds(100, 10, 156, 20);

        empresa1.setFont(new java.awt.Font("SansSerif", 0, 11));
        empresa1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                empresa1ItemStateChanged(evt);
            }
        });
        empresa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresa1ActionPerformed(evt);
            }
        });
        panDat.add(empresa1);
        empresa1.setBounds(460, 10, 156, 20);

        bodega.setFont(new java.awt.Font("SansSerif", 0, 11));
        bodega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bodegaActionPerformed(evt);
            }
        });
        panDat.add(bodega);
        bodega.setBounds(100, 30, 210, 20);

        bodega1.setFont(new java.awt.Font("SansSerif", 0, 11));
        panDat.add(bodega1);
        bodega1.setBounds(460, 30, 200, 20);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel7.setLayout(null);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel6.setText("Desde:");
        jPanel7.add(jLabel6);
        jLabel6.setBounds(30, 20, 40, 20);

        txtcodaltdes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodaltdesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodaltdesFocusLost(evt);
            }
        });
        jPanel7.add(txtcodaltdes);
        txtcodaltdes.setBounds(70, 20, 110, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel7.setText("Hasta:");
        jPanel7.add(jLabel7);
        jLabel7.setBounds(250, 20, 31, 15);

        txtcodalthas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodalthasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodalthasFocusLost(evt);
            }
        });
        jPanel7.add(txtcodalthas);
        txtcodalthas.setBounds(290, 20, 110, 20);

        panDat.add(jPanel7);
        jPanel7.setBounds(20, 110, 510, 50);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11));
        optTod.setSelected(true);
        optTod.setText("Todos las Items");
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
        panDat.add(optTod);
        optTod.setBounds(0, 60, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11));
        optFil.setText("Sólo los Items que cumplan el criterio seleccionado");
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
        panDat.add(optFil);
        optFil.setBounds(0, 80, 330, 20);

        jPanel3.add(panDat, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(null);

        buttonGroup2.add(radDev);
        radDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radDev.setSelected(true);
        radDev.setText("Tratar de hacer decoluciones");
        jPanel5.add(radDev);
        radDev.setBounds(0, 10, 260, 20);

        buttonGroup2.add(radCom);
        radCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radCom.setText("Hacer compras y ventas");
        jPanel5.add(radCom);
        radCom.setBounds(0, 30, 260, 20);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel3);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

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

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Datos", jPanel6);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("General");

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 35));
        jPanel2.setLayout(new java.awt.BorderLayout());

        panBut.setLayout(new java.awt.BorderLayout());

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panSubBot.add(jButton1);

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panSubBot.add(jButton2);

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Procesar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCan.setText("Cerrar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        jPanel2.add(panBut, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void empresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_empresaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_empresaItemStateChanged

    private void empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresaActionPerformed
        // TODO add your handling code here:
           
        String strAux="";
         intTipSelCarBod1=0;
             if(empresa.getSelectedIndex()>=0){
                 
                 
                    vecEmp2 =  new  java.util.Vector();
                    String sql = "select co_emp , tx_nom from tbm_emp where co_emp not IN( 0,3,"+vecEmp1.elementAt(empresa.getSelectedIndex())+" ) ";
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos(), sql, empresa1, vecEmp2);
                    empresa1.setSelectedIndex(0);
        
                  if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("1"))  
                    strAux =" and co_bod in (1,10,8,7,3,12 ) ";
                  if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("2"))  
                    strAux =" and co_bod in ( 9,3,4,1,5,12 ) ";
                  if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("4"))  
                    strAux =" and co_bod in  ( 1,2,7,9,3 ,11 ) ";
                    
                 
                    vecBod1 =  new  java.util.Vector();
                    sql = "select co_bod, tx_nom from tbm_bod where co_emp="+vecEmp1.elementAt(empresa.getSelectedIndex())+" "+strAux+" order by co_bod ";
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                            objZafParSis.getClaveBaseDatos(), sql, bodega, vecBod1);
                    
                    bodega.setSelectedIndex(0);

                    intTipSelCarBod1=1;
             }
           
         selecBod();
        
    }//GEN-LAST:event_empresaActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        
        configurar();
     
        intEstConEmp=1;
        intTipSelBod=1;
        intTipSelCarBod1=1;
        intTipSelCarBod2=1;
         selecBod();
         
    }//GEN-LAST:event_formInternalFrameOpened

    private void empresa1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_empresa1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_empresa1ItemStateChanged

    private void empresa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresa1ActionPerformed
        // TODO add your handling code here:
       String strAux="";
       intTipSelCarBod2=0;
        if(empresa1.getSelectedIndex()>=0){
            
                  if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))  
                    strAux =" and co_bod in (1, 10,8,7,3,12 ) ";
                  if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))  
                    strAux =" and co_bod in ( 9,3,4,1,5,12 ) ";
                  if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))  
                    strAux =" and co_bod in  ( 1,2,7,9,3,11  ) ";
             
            
                    vecBod2 =  new  java.util.Vector();
                    String sql = "select co_bod, tx_nom from tbm_bod where co_emp="+vecEmp2.elementAt(empresa1.getSelectedIndex())+" "+strAux+" order by co_bod ";
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                            objZafParSis.getClaveBaseDatos(), sql, bodega1, vecBod2);
                    
                    bodega1.setSelectedIndex(0);

                   intTipSelCarBod2=1;
        }
       


        selecBod();
        
        
    }//GEN-LAST:event_empresa1ActionPerformed

    private void bodegaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bodegaActionPerformed
        // TODO add your handling code here:


    selecBod();
       
    
    }//GEN-LAST:event_bodegaActionPerformed



private void selecBod(){


    


   if( intTipSelBod == 1 ){
       if( (intTipSelCarBod1==1) && (intTipSelCarBod2==1) ){


       if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("1")){
            if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(1);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(1);
             }else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("7")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(4);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(0);
             }
             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("8")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(0);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(3);
             }
            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("10")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(2);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(4);
             }
             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("12")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(5);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(5);
             }
     }



     if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("2")){
            if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(3);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(3);
             }else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("3")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(0);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(1);
             }
             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("4")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(4);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(4);
             }
            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("9")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(2);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(0);
             }
             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("12")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(5);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4"))
                    bodega1.setSelectedIndex(5);
             }
     }
      


     if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("4")){
            if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(2);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(4);
             }else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("2")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(0);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(1);
             }
             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("7")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(3);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(0);
             }
            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("9")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(4);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(2);
             }
             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("11")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1"))
                    bodega1.setSelectedIndex(5);
                else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2"))
                    bodega1.setSelectedIndex(5);
             }
     }


  }}

}


 private void cargarDat(){
     
          
    java.sql.Connection conn;
    java.sql.ResultSet rstLoc;
    java.sql.Statement stmLoc;
    String strAuxFil="";
    try{       
       conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());                                                                       
       if(conn != null){
          stmLoc=conn.createStatement();
                
        if(bodega.getSelectedIndex()>=0){

             Vector vecData = new Vector();

             if (txtcodaltdes.getText().length()>0 || txtcodalthas.getText().length()>0)
             strAuxFil=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtcodaltdes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtcodalthas.getText().replaceAll("'", "''").toLowerCase() + "%')";




             String strAux = ",CASE WHEN (" +
             " (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1))  IN (" +
             " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                 " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
             " ))) THEN 'S' ELSE 'N' END  as isterL";

             String sql = " select *   from ( " +
             " select sal.nd_salven, a1.tx_codalt, a1.tx_nomitm, a2.tx_descor, a.nd_stkact , a1.nd_prevta1,  a3.co_itmmae, a1.nd_cosuni " +
             " ,a4.co_itm as coitm1,  a5.co_itm as coitm2  "+strAux+" from tbm_invbod as a " +
             " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) " +
             " left join tbm_var as a2 on (a2.co_reg = a1.co_uni ) " +
             " inner join tbm_equinv as a3 on (a3.co_emp=a1.co_emp and a3.co_itm=a1.co_itm) "+  
             " inner join tbm_equinv as a4 on (a4.co_itmmae=a3.co_itmmae and a4.co_emp="+vecEmp1.elementAt(empresa.getSelectedIndex())+") " +
             " inner join tbm_equinv as a5 on (a5.co_itmmae=a3.co_itmmae and a5.co_emp="+vecEmp2.elementAt(empresa1.getSelectedIndex())+") " +       
             "  LEFT JOIN tbm_invmovempgrp AS sal ON ( sal.co_emp="+vecEmp2.elementAt(empresa1.getSelectedIndex())+" and sal.co_itm=a5.co_itm and sal.co_emprel=a.co_emp and sal.co_itmrel=a4.co_itm) "+
             " where a.co_emp="+vecEmp1.elementAt(empresa.getSelectedIndex())+" and a.co_bod="+vecBod1.elementAt(bodega.getSelectedIndex())+"   and a.nd_stkact > 0 " +
             " "+strAuxFil+" "+
             " order by a1.tx_codalt " +
             " ) as  x where isterL='S' ";
             System.out.println(""+ sql );
             rstLoc=stmLoc.executeQuery(sql);
             while(rstLoc.next()) {
               java.util.Vector vecReg = new java.util.Vector();
                      
                 vecReg.add(INT_TBL_LINEA, "");
                 vecReg.add(INT_TBL_SELITM, new Boolean(false) );  
                 vecReg.add(INT_TBL_CODITM,  rstLoc.getString("co_itmmae") );
                 vecReg.add(INT_TBL_ITMALT, rstLoc.getString("tx_codalt") );
                 vecReg.add(INT_TBL_DESITM, rstLoc.getString("tx_nomitm") );
                 vecReg.add(INT_TBL_UNIDAD, rstLoc.getString("tx_descor") );
                 vecReg.add(INT_TBL_STKACT, rstLoc.getString("nd_stkact") );
                 vecReg.add(INT_TBL_PREVTA, rstLoc.getString("nd_prevta1") );   
                 vecReg.add(INT_TBL_CANMOV, "0" );
                 vecReg.add(INT_TBL_COSUNI, rstLoc.getString("nd_cosuni") );
                 vecReg.add(INT_TBL_CODITM1, rstLoc.getString("coitm1") );
                 vecReg.add(INT_TBL_CODITM2, rstLoc.getString("coitm2") );
                 vecReg.add(INT_TBL_CANVEN, "0" );
                 vecReg.add(INT_TBL_SALDOS, rstLoc.getString("nd_salven") );

                 vecData.add(vecReg);          
             }
             rstLoc.close();
             rstLoc=null;
             stmLoc.close();
             stmLoc=null;
             
             objTblMod.setData(vecData);
             tblDat .setModel(objTblMod); 
        }
        
          conn.close();
          conn=null;
        
        
      }}catch(java.sql.SQLException Evt)  {  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){    objUti.mostrarMsgErr_F1(this, Evt);   }                       
      
    
     
 }   
    
    
    
private boolean generaCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc,  int intCodBod, int intCodEmpCom, int intCodBodDes ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc;
  StringBuffer stbins;
  String strSql="";
  int intCodDoc=0;
  int numItmDoc=12;
  int intContador=1;
  String strCodItm="";
  
  double dblCosUni=0;
  double dblPreVta=0;
  double dblCan=0;
  double dlbDesVta=35;
  double dblCosUniVen=0;
  double dblValMarRel =0;
  int intEst=0;
  int intEstStk=0;
  int intTipCli=3;
   try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();

      iniCodEmpComRecosteo = intCodEmp;
     
      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;


      String strConfDevVen="F";
      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      String strEstGuiRem="S";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod, intCodEmpCom,  intCodBodDes )){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
        strEstGuiRem="N";
        strConfDevVen="P";
      }

 
      stbins=new StringBuffer();
      
      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();
      
      for(int intNumFil=0; intNumFil< tblDat.getRowCount(); intNumFil++){
        if(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true")){

          dblCosUniVen= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString() );
          dblPreVta= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString() );
         
          strCodItm= tblDat.getValueAt(intNumFil, INT_TBL_CODITM2).toString();

          
                  /*********************************************************/

                   if(dblCosUniVen > 0 ){  // TRABAJA CON COSTO

                      dblValMarRel=objInvItm._getMargenComVenRel(intCodEmpCom, intCodEmp);


                      dblPreVta= dblCosUniVen*dblValMarRel;
                      dblCosUni= dblCosUniVen*dblValMarRel;
                      dlbDesVta= 0;

                   }else{ //  TRABAJA CON PRECIO DE VENTA

                    if(dblPreVta > 0 )
                      dblCosUni  =  dblPreVta - ( dblPreVta * ( dlbDesVta / 100 ) );

                   }

                  /*********************************************************/




          if(radCom.isSelected())
           dblCan= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString() );
          else
            dblCan= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_CANVEN).toString() );
          
         if(dblCan > 0 ){

            arlItmRec.add( strCodItm );
       
            strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
            " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom,st_reg " +//<==Campos que aparecen en la parte inferior del 1er Tab
            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_nombodorgdes ) " +
            " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intContador+", " +
            " '"+((tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString()))+"' "+
            " ,'"+((tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString()))+"' "+
            ", "+strCodItm+" "+
            ", "+strCodItm+" "+
            ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_DESITM)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_DESITM).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_DESITM).toString()))+"' "+
            ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_UNIDAD)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_UNIDAD).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_UNIDAD).toString()))+"' "+
            ", "+intCodBod+", "+dblCan+", "+(dblCan*dblCosUni)+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblCosUni+", 0, 'S', 'A'"+
            " ,"+(dblCan*dblCosUni)+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+" " +
            " ,'"+bodega.getSelectedItem().toString()+"' ) ; ";
            stbins.append(strSql);
            intEst=1;

             strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom+"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
             " co_itm="+strCodItm+" AND co_emprel="+intCodEmpCom;
             stmLoc.executeUpdate(strSql);

            objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), intCodBod, dblCan, 1, "N", strMerIngEgr, "I", 1);
            intEstStk=1;    
        
         if(intContador == numItmDoc){
          
             if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2  , strEstGuiRem, strConfDevVen   )){
                 stmLoc.executeUpdate(stbins.toString());
             }else {
                 return false;
             }
             
              strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
              " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
              rstLoc = stmLoc.executeQuery(strSql);
              if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
              rstLoc.close();
              rstLoc=null;
             
              stbins=null;
              stbins=new StringBuffer();
              intEst=0;
              intContador=0;
              
         }
            
         
         intContador++;
          
      }}}
      
      
      
      if(intEst == 1){
             if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2 , strEstGuiRem, strConfDevVen   )){
                 stmLoc.executeUpdate(stbins.toString());
             }else {
                 return false;
             }
             
              stbins=null;
              intEst=0;
         }
            
      
      if(intEstStk==1){
          
           if(!objInvItm.ejecutaActStock(conn, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli))
             return false;
           
      }
       objInvItm.limpiarObjeto();
      
      
           blnRes=true;   
   
    stmLoc.close();
    stmLoc=null;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           
   
    
    
    
private boolean generaVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc,  int intCodBod, int intCodEmpCom, int intCodBodDes ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc;
  StringBuffer stbins;
  String strSql="";
  int intCodDoc=0;
  int numItmDoc=12;
  int intContador=1;
  String strCodItm="";
  double dblCosUni=0;
  double dblPreVta=0;
  double dblCan=0;
  double dlbDesVta=35;
  double dblCosUniVen=0;
  double dblValMarRel=0;
  int intEst=0;
  int intEstStk=0;
  int intTipCli=3;
   try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
   
     
      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;


      String strConfDevVen="F";
      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      String strEstGuiRem="S";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod, intCodEmpCom,  intCodBodDes )){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
        strEstGuiRem="N";
        strConfDevVen="P";
      }


 
      stbins=new StringBuffer();
      
      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();
      

      for(int intNumFil=0; intNumFil< tblDat.getRowCount(); intNumFil++){
        if(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true")){

          dblCosUniVen= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString() );

          dblPreVta= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString() );
          strCodItm= tblDat.getValueAt(intNumFil, INT_TBL_CODITM1).toString();



                  /*********************************************************/

                   if(dblCosUniVen > 0 ){  // TRABAJA CON COSTO

                      dblValMarRel=objInvItm._getMargenComVenRel(intCodEmp, intCodEmpCom);

                      dblPreVta= dblCosUniVen*dblValMarRel;
                      dblCosUni= dblCosUniVen*dblValMarRel;
                      dlbDesVta= 0;

                   }else{ //  TRABAJA CON PRECIO DE VENTA

                    if(dblPreVta > 0 )
                      dblCosUni  =  dblPreVta - ( dblPreVta * ( dlbDesVta / 100 ) );

                   }

                  /*********************************************************/

      

          if(radCom.isSelected())
           dblCan= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString() );
          else
           dblCan= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_CANVEN).toString() );




          if(dblCan > 0 ){


            strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
            " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom,st_reg " +//<==Campos que aparecen en la parte inferior del 1er Tab
            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_nombodorgdes ) " +
            " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intContador+", " +
            " '"+((tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString()))+"' "+
            " ,'"+((tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString()))+"' "+
            ", "+strCodItm+" "+
            ", "+strCodItm+" "+
            ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_DESITM)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_DESITM).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_DESITM).toString()))+"' "+
            ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_UNIDAD)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_UNIDAD).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_UNIDAD).toString()))+"' "+
            ", "+intCodBod+", "+(dblCan*-1)+", "+(dblCan*dblCosUni)+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblPreVta+", "+dlbDesVta+", 'S', 'A'"+
            " ,"+(dblCan*dblCosUni)+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+" " +
            " ,'"+bodega1.getSelectedItem().toString()+"' ) ; ";
            stbins.append(strSql);
            intEst=1;


           strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
           " co_itm="+strCodItm+" AND co_emprel="+intCodEmpCom;
           stmLoc.executeUpdate(strSql);

            objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), intCodBod, dblCan, -1, "N", strMerIngEgr, "E", 1);
            intEstStk=1;    
        
         if(intContador == numItmDoc){
          
             if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 1 , strEstGuiRem, "A"   )){
                 stmLoc.executeUpdate(stbins.toString());
             }else {
                 return false;
             }
             
              strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
              " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
              rstLoc = stmLoc.executeQuery(strSql);
              if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
              rstLoc.close();
              rstLoc=null;
             
              stbins=null;
              stbins=new StringBuffer();
              intEst=0;
              intContador=0;
              
         }
            
         
         intContador++;
          
      }}}
      
      
      
      if(intEst == 1){
             if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 1 , strEstGuiRem , "A"  )){
                 stmLoc.executeUpdate(stbins.toString());
             }else {
                 return false;
             }
             
              stbins=null;
              intEst=0;
         }
            
      
      if(intEstStk==1){
          
           if(!objInvItm.ejecutaActStock(conn, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli))
             return false;
           
      }
       objInvItm.limpiarObjeto();
      
      
           blnRes=true;   
   
    stmLoc.close();
    stmLoc=null;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           
   


    


  
   
private boolean generaVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov , String strEstGuiRem, String strConfDevVen ){
  boolean blnRes=false;  
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDoc=0;
  String strCui="";
  try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();


 System.out.println(" INGRESA 2"  );


//       intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(conn, intCodEmp);
//       intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(conn);
      
      
      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
      
     if(TipMov==1){
       if(intCodEmpCom==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
       
       if(intCodEmpCom==2){
          if(intCodEmp==1){
              if(intCodTipDoc==126){
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              } else if(intCodTipDoc==166){
                  strCui="SANTO DOMINGO ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
              }
              else{ strCui="QUITO ";
                 strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603"; }
          }  
          if(intCodEmp==4){ 
              if(intCodTipDoc==126){
                  strCui="MANTA ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498"; 
              }else if(intCodTipDoc==166){
                  strCui="SANTO DOMINGO ";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
              }
              else{ strCui="QUITO ";
              strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";  }
       }}
       if(intCodEmpCom==4){
          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
        }
    }
      
    if(TipMov==2){
       if(intCodEmp==1){
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603";  
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  
       }
       if(intCodEmp==2){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  
       }
       if(intCodEmp==4){
          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";  
       }
    }
      
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         
          intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);
         
          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"'," +
          "'"+strCui+"','', "+intNumDoc+" , 0,"+
          " "+intNumDoc+", '' ,'' , 0 ,0 ,0, 0 , 1 ,'Contado', '"+strFecSistema+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+strConfDevVen+"', '"+strEstGuiRem+"' )";


           if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
               stbDocRelEmpLoc.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
           intDocRelEmpLoc=1;

           System.out.println(" intDocRelEmpLoc  --> "+ intDocRelEmpLoc  );

          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
      
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}           

    



private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
  int intUltNumDoc=0;
  java.sql.Statement stmLoc; 
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){ 
      stmLoc=conn.createStatement();
  
     strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc; 
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");
   
    rstLoc.close();
    rstLoc=null; 
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
   catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
 return intUltNumDoc;
}


private boolean validar(){
 boolean blnRes=true;
  for(int intNumFil=0; intNumFil< tblDat.getRowCount(); intNumFil++){
    if(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true")){
        
       double dblPreVta= Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_PREVTA)==null?"0":tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString()) );
        
       if(dblPreVta <= 0){
          MensajeInf("el item "+tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString()+" no tiene precio de venta. ");
          blnRes=false;
          break;
       }

       double dblCan= Double.parseDouble( (tblDat.getValueAt(intNumFil, INT_TBL_CANMOV)==null?"0":tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString()) );
        
       if(dblCan <= 0){
          MensajeInf("el item "+tblDat.getValueAt(intNumFil, INT_TBL_ITMALT).toString()+" no tiene cantidad para la venta. ");
          blnRes=false;
          break;
       }     
       
   }}
 
  return blnRes;
}

    
    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:

       if(validar()){
          reliazarDevVenCom();
       }
        
    }//GEN-LAST:event_butAceActionPerformed


private void reliazarDevVenCom(){
  java.sql.Connection conn;
  try{
   conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
   if(conn != null){
     conn.setAutoCommit(false);

      stbDocRelEmpLoc=new StringBuffer();
      intDocRelEmpLoc=0;
      arlItmRec = new java.util.ArrayList();

      arlItmRec.clear();
      iniCodEmpComRecosteo=0;

     if(radDev.isSelected()){

       if( realizaDevVenCom(conn)){
         if( realizarComprasVentas(conn) ){
           
             conn.commit();
             conn.setAutoCommit(true);

             //System.out.println("--> "+stbDocRelEmpLoc.toString() );
             asignaSecEmpGrp(conn,  stbDocRelEmpLoc);

             recosterarItm(conn);
             
             MensajeInf(" SE REALIZO CON EXITO. ");
             cargarDat();

       }else conn.rollback();
      }else conn.rollback();

    } else {
    if(radCom.isSelected()){

       if( realizarComprasVentas(conn) ){
    
             conn.commit();
             conn.setAutoCommit(true);

             //System.out.println("--> "+stbDocRelEmpLoc.toString() );
             asignaSecEmpGrp(conn,  stbDocRelEmpLoc);

             recosterarItm(conn);

             MensajeInf(" SE REALIZO CON EXITO. ");
             cargarDat();

       }else conn.rollback();

    }
    }



     arlItmRec=null;
     stbDocRelEmpLoc=null;

     conn.close();
     conn=null;

 }}catch(java.sql.SQLException Evt)  {  objUti.mostrarMsgErr_F1(this, Evt); }
   catch(Exception Evt){    objUti.mostrarMsgErr_F1(this, Evt);   }

}



private void recosterarItm(java.sql.Connection conn){
 try{
  if(conn != null){

      java.util.Date datFecHoy =objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      String strFecHoy = objUti.formatearFecha(datFecHoy, "dd/MM/yyyy");

       for(int i=0;i<arlItmRec.size();i++) {

         System.out.println(" RECOSTERAR ITM  " + arlItmRec.get(i) );
         objUti.recostearItm2009DesdeFecha(this, objZafParSis, conn,  iniCodEmpComRecosteo, arlItmRec.get(i).toString(), strFecHoy, "dd/MM/yyyy");

       }

  }}catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt);     }
}




    double Glo_dblTotDevVen=0;
    double Glo_dblTotDevCom=0;
    double Glo_dblCanFalDevVen=0;
    double Glo_dblCanFalDevCom=0;
    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;


private boolean realizaDevVenCom(java.sql.Connection conn){
 boolean blnRes=false;
 double dblCan=0;
 String strCodItm="";
 java.sql.Statement stmLoc;
 try{
     if(conn != null){

        stmLoc=conn.createStatement();

        String strCodEmpOri =  vecEmp1.elementAt(empresa.getSelectedIndex()).toString();
        String strCodEmpDes =  vecEmp2.elementAt(empresa1.getSelectedIndex()).toString();
        int intCodBodDes   =  Integer.parseInt( vecBod2.elementAt(bodega1.getSelectedIndex()).toString() );

        String strNomBodOri =  bodega.getSelectedItem().toString();
        String strNomBodDes =  bodega1.getSelectedItem().toString();



 for(int intNumFil=0; intNumFil< tblDat.getRowCount(); intNumFil++){
  if(tblDat.getValueAt(intNumFil, INT_TBL_SELITM).toString().equals("true")){

    strCodItm= tblDat.getValueAt(intNumFil, INT_TBL_CODITM2).toString();
    dblCan= Double.parseDouble( tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString() );
    Glo_dblTotDevVen=0;
    Glo_dblTotDevCom=0;


   if(dblCan > 0 ){
       Glo_dblCanFalDevCom=dblCan;

       tblDat.setValueAt( ""+Glo_dblCanFalDevCom,   intNumFil, INT_TBL_CANVEN);

       if ( devvencom(conn, strCodEmpOri, strCodEmpDes,  strCodItm, vecBod1.elementAt(bodega.getSelectedIndex()).toString(), intCodBodDes ) ){
         if( Glo_dblTotDevVen==Glo_dblTotDevCom){

             blnRes=true;
             tblDat.setValueAt( ""+Glo_dblCanFalDevCom,   intNumFil, INT_TBL_CANVEN);

         } else{ blnRes=false; break; }
       }else { blnRes=false; break; }

   }}}
     stmLoc.close();
     stmLoc=null;
    
 }}catch(java.sql.SQLException Evt)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
   catch(Exception Evt){   blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);   }
 return blnRes;
}


private boolean devvencom(java.sql.Connection conn, String strCodEmpOri, String strCodEmpDes, String strCodItm, String strCodBod, int intCodBodDes  ){
  boolean blnRes=true;
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc;
  String strSql="";
  double dblStkAct=0;
  double dblSal=0;
  int intCodCliOri=0;
  int intCodCliDes=0;

   try{
     if(conn != null){

      stmLoc=conn.createStatement();

      if(strCodEmpOri.equals("1")){
        if(strCodEmpDes.equals("2")) { intCodCliOri=603;  intCodCliDes=2854; }
        if(strCodEmpDes.equals("4")) { intCodCliOri=1039; intCodCliDes=3117; }
      }
      if(strCodEmpOri.equals("2")){
        if(strCodEmpDes.equals("1")) { intCodCliOri=2854; intCodCliDes=603; }
        if(strCodEmpDes.equals("4")) { intCodCliOri=789;  intCodCliDes=498; }
      }
      if(strCodEmpOri.equals("4")){
        if(strCodEmpDes.equals("1")) { intCodCliOri=3117; intCodCliDes=1039; }
        if(strCodEmpDes.equals("2")) { intCodCliOri=498;  intCodCliDes=789; }
      }

      strSql=" SELECT a.co_emp, a.co_itm, a.co_emprel, a.co_itmrel,  a.nd_salven, a1.co_bod,  a1.nd_stkact FROM tbm_invmovempgrp  as a " +
      " inner join tbm_invbod as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) " +
      " where a.co_emp="+strCodEmpDes+" and a.co_itm="+strCodItm+" and a.co_emprel="+strCodEmpOri+" AND a.nd_salven < 0 AND  a1.nd_stkact > 0   and a1.co_bod="+ strCodBod;

      System.out.println("-->  "+strSql);

      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

          stbDatDevCom=new StringBuffer();
          stbDatDevVen=new StringBuffer();
          intDatDevCom=0;
          intDatDevVen=0;

          dblSal=rstLoc.getDouble("nd_salven");
          dblStkAct=rstLoc.getDouble("nd_stkact");

          _devCompra( conn, rstLoc.getInt("co_emprel"),  intCodCliOri,  rstLoc.getInt("co_itmrel") );
          _devVenta( conn,  rstLoc.getInt("co_emp"),  intCodCliDes,  rstLoc.getInt("co_itm")  );

          //System.out.println(""+ stbDatDevCom.toString());
          //System.out.println(""+ stbDatDevVen.toString());

           if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){
             if(  _RealizarDevol(conn,  dblStkAct , rstLoc.getInt("co_bod") , intCodBodDes , intCodCliOri , intCodCliDes  ) ){
                  blnRes=true;
           }}
 
         stbDatDevCom=null;
         stbDatDevVen=null;

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
      catch(Exception Evt){ blnRes=false;   objUti.mostrarMsgErr_F1(this, Evt);   }
  return blnRes;
}


private void _devCompra(java.sql.Connection conn, int intCodEmp,  int intCodCli,  int intCodItm  ){
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc;
  String strSql="";
  int intCodTipDoc=2;
  int intRegSec=0;
  try{
    if(conn != null){

      stmLoc=conn.createStatement();

      strSql="select * FROM (" +
      "  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  a.fe_doc,  a.nd_poriva, "+
      " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, a1.nd_can, a1.nd_preuni,  "+
      " a1.co_bod, a1.nd_candev, ( abs(a1.nd_can) - abs(case when a1.nd_candev is null then 0 else a1.nd_candev end )) as saldo  "+
      " FROM tbm_cabmovinv  AS a  "+
      " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)  "+
      " WHERE a.co_emp="+intCodEmp+"  "+
      " AND a.co_tipdoc="+intCodTipDoc+" AND a.co_cli="+intCodCli+" "+
      " AND a.fe_doc>='2008-01-01' and a.st_reg not in ('E','I') and a1.co_itm="+intCodItm+"  "+
      " ORDER BY a.fe_Doc, a.co_doc  "+
      " ) as x  where saldo <> 0   ";

       System.out.println(""+ strSql );

      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

             intRegSec++;
             if(intDatDevCom==1) stbDatDevCom.append(" UNION ALL ");
             stbDatDevCom.append(" SELECT "+intRegSec+" AS regsec,  "+rstLoc.getInt("co_emp")+" AS co_emp, "+rstLoc.getInt("co_loc")+" AS co_loc, " +
             " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc, "+rstLoc.getInt("co_doc")+" AS co_doc, "+rstLoc.getInt("co_reg")+" AS co_reg, "+rstLoc.getDouble("nd_poriva")+" AS nd_poriva, " +
             " "+rstLoc.getInt("co_bod")+" AS co_bod, "+rstLoc.getDouble("saldo")+" AS saldo, " +
             " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt2," +
             " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
             " "+rstLoc.getString("co_itm")+" AS co_itm, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni, "+rstLoc.getString("nd_can")+" AS nd_can,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni  ");

             intDatDevCom=1;
      }
      rstLoc.close();
      rstLoc=null;

      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt)  {  objUti.mostrarMsgErr_F1(this, Evt); }
      catch(Exception Evt){    objUti.mostrarMsgErr_F1(this, Evt);   }
}

private void _devVenta(java.sql.Connection conn, int intCodEmp,  int intCodCli,  int intCodItm  ){
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc;
  String strSql="";
  int intCodTipDoc=1;
  int intRegSec=0;
  try{
    if(conn != null){

      stmLoc=conn.createStatement();

      strSql="select * FROM (" +
      "  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  a.fe_doc, a.nd_poriva,  "+
      " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, a1.nd_can, a1.nd_preuni,  "+
      " a1.co_bod, a1.nd_candev, ( abs(a1.nd_can) - abs(case when a1.nd_candev is null then 0 else a1.nd_candev end )) as saldo  "+
      " FROM tbm_cabmovinv  AS a  "+
      " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)  "+
      " WHERE a.co_emp="+intCodEmp+"  "+
      " AND a.co_tipdoc="+intCodTipDoc+" AND a.co_cli="+intCodCli+" "+
      " AND a.fe_doc>='2008-01-01' and a.st_reg not in ('E','I') and a1.co_itm="+intCodItm+"  "+
      " ORDER BY a.fe_Doc, a.co_doc  "+
      " ) as x  where saldo <> 0   ";

      System.out.println(""+ strSql );
      
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

             intRegSec++;
             if(intDatDevVen==1) stbDatDevVen.append(" UNION ALL ");
             stbDatDevVen.append(" SELECT "+intRegSec+" AS regsec1,  "+rstLoc.getInt("co_emp")+" AS co_emp1, "+rstLoc.getInt("co_loc")+" AS co_loc1, " +
             " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc1, "+rstLoc.getInt("co_doc")+" AS co_doc1, "+rstLoc.getInt("co_reg")+" AS co_reg1, "+rstLoc.getDouble("nd_poriva")+" AS nd_poriva1, " +
             " "+rstLoc.getInt("co_bod")+" AS co_bod1, "+rstLoc.getDouble("saldo")+" AS saldo1, " +
             " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc1, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt1, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt21," +
             " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
             " "+rstLoc.getString("co_itm")+" AS co_itm1, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni1, "+rstLoc.getString("nd_can")+" AS nd_can1,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni1   ");

             intDatDevVen=1;
      }
      rstLoc.close();
      rstLoc=null;

      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt)  {  objUti.mostrarMsgErr_F1(this, Evt); }
      catch(Exception Evt){    objUti.mostrarMsgErr_F1(this, Evt);   }
}

private boolean _RealizarDevol(java.sql.Connection conn,   double dlbStkBobDevCom , int intCodBod , int intCodBodDes, int  intCodCliOri , int intCodCliDes  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 double dlbCanDev=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT * FROM ( " +
    " SELECT * FROM ( "+stbDatDevCom.toString()+" ) AS x "+
    " LEFT JOIN ( "+stbDatDevVen.toString()+" ) AS x1 ON(x1.regsec1=x.regsec) " +
    " ) AS x  WHERE  saldo1=saldo ";
    System.out.println("Paso 2  : "+ strSql );
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       if(Glo_dblCanFalDevCom > 0 ){
        if( dlbStkBobDevCom > 0 ){

         if(rstLoc.getDouble("saldo") >= Glo_dblCanFalDevCom ) {

            if( dlbStkBobDevCom <= Glo_dblCanFalDevCom ) dlbCanDev=dlbStkBobDevCom;
             else dlbCanDev = Glo_dblCanFalDevCom;

            Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
         }else{

             if( dlbStkBobDevCom <= rstLoc.getDouble("saldo") )  dlbCanDev=dlbStkBobDevCom;
             else dlbCanDev = rstLoc.getDouble("saldo");

            Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
         }

       
         intCodLocDevCompra=0;
         intCodTipDocDevCom=0;
         intCodDocDevCom=0;

         intCodLocDevven=0;
         intCodTipDocDevven=0;
         intCodDocDevven=0;


      if( generaDevCom( conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_reg")
        , rstLoc.getDouble("nd_poriva"),  dlbCanDev,  rstLoc.getDouble("nd_pordes"), rstLoc.getDouble("nd_cosuni"), rstLoc.getDouble("nd_can")
        , intCodBod, rstLoc.getString("tx_codalt"), rstLoc.getString("tx_codalt2") , rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"),
          rstLoc.getInt("co_itm"), rstLoc.getInt("co_emp1"), intCodCliOri , intCodCliDes, intCodBodDes   ) ) {

         if( generaDevVen( conn, rstLoc.getInt("co_emp1"), rstLoc.getInt("co_loc1"), rstLoc.getInt("co_tipdoc1"), rstLoc.getInt("co_doc1"), rstLoc.getInt("co_reg1")
        , rstLoc.getDouble("nd_poriva1"),  dlbCanDev,  rstLoc.getDouble("nd_pordes1"), rstLoc.getDouble("nd_preuni1"), rstLoc.getDouble("nd_can1")
        , intCodBodDes, rstLoc.getString("tx_codalt1"), rstLoc.getString("tx_codalt21") , rstLoc.getString("tx_nomitm1"), rstLoc.getString("tx_unimed1"),
          rstLoc.getInt("co_itm1"), rstLoc.getInt("co_emp"),  intCodCliOri , intCodCliDes , intCodBod  ) ) {


             if(ingresarEnTblRelacional(conn, rstLoc.getInt("co_emp"),intCodLocDevCompra, intCodTipDocDevCom, intCodDocDevCom, 1 , rstLoc.getInt("co_emp1"), intCodLocDevven
                , intCodTipDocDevven, intCodDocDevven, 1 ) ) {



             blnRes=true;



              }else{  blnRes=false; break; }
               
           }else{  blnRes=false; break; }
      }else{  blnRes=false; break; }




       }}
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}







private boolean ingresarEnTblRelacional(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg
        , int intCodEmp1, int intCodLoc1, int intCodTipDoc1, int intCodDoc1, int intCodReg1  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql = "INSERT INTO tbr_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep  ) " +
      " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+", 'A' " +
      " ,"+intCodEmp1+", "+intCodLoc1+", "+intCodTipDoc1+", "+intCodDoc1+", "+intCodReg1+", 'P' ) ";
      stmLoc.executeUpdate(strSql);

    stmLoc.close();
    stmLoc=null;
    blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}






private boolean generaDevVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg
       , double bldivaEmp,double dblCan, double dblPorDes,  double dblCosUni, double dblCanOc , int intCodBod, String strCodAlt, String strCodAlt2
       , String strNomItm, String strUniMed, int intCodItm, int  intCodEmpCom,  int intCodCliOri , int  intCodCliDes, int intCodBodDes ){
   boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDocDev=0;
  int intCodTipDocDev=3;
  int intCodLocDev=0;
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      if(intCodEmp==1)  intCodLocDev=5;
      if(intCodEmp==2)  intCodLocDev=5;
      if(intCodEmp==4)  intCodLocDev=2;
      
      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLocDev+" AND co_tipDoc="+intCodTipDocDev;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDocDev=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;
      objInvItm.limpiarObjeto();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
       double  dlbSub =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );
       double dlbValIva=  dlbSub * (bldivaEmp/100);

       dlbSub=objUti.redondear(dlbSub,2);
       dlbValIva=objUti.redondear(dlbValIva,2);


       double dlbTot=  dlbSub + dlbValIva;


       dlbTot=objUti.redondear(dlbTot,2);



       Glo_dblTotDevVen += dlbSub;

   
      int intTipMov=1;

       datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
       String strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

      String strConfDevVen="F";
      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      String strEstGuiRem="S";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod, intCodEmpCom,  intCodBodDes )){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
        strEstGuiRem="N";
        strConfDevVen="P";
      }
       
    
       if( generaDevVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodLocDev, intCodTipDocDev, intCodDocDev, dlbSub,  dlbValIva,  dlbTot, strConfDevVen , datFecAux, strFecSistema, strEstGuiRem ) ){
          if( generaDevVenDet( conn, intCodEmp, intCodLocDev, intCodTipDocDev,  intCodDocDev,  intCodBod,  intCodTipDoc,
            intCodDoc,  intCodReg,   strCodAlt, strCodAlt2,  strNomItm,  strUniMed,  dblCan,  intCodItm,  intTipMov,  intCodEmpCom,
            dblCosUni,  dblPorDes,  dblCanOc, intCodLoc  , strIngEgrFisBod  ) ) {
           if( generaAsientoDevVen( conn,  intCodEmp,  intCodLocDev,  intCodTipDocDev,  intCodDocDev, datFecAux, strFecSistema, dlbTot,  dlbSub,  dlbValIva,  dblValDes,  intCodCliDes ) ) {

              if(actualizaStockDev( conn, null, intCodEmp, intCodBod , dblCan, 1, intCodItm, strMerIngEgr  ,"I" )){

              blnRes=true;
       
       }}}}

  
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}


private boolean generaDevVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodLocDev, int intCodTipDocDev, int intCodDocDev, double dlbSub, double dlbValIva, double dlbTot, String strConfDevVen, java.util.Date datFecDoc, String strFecSis, String strEstGuiRem ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDocDev=0;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

    
       strSql=" select * from tbm_cabmovinv where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
       rstLoc = stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
            intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLocDev, intCodTipDocDev);


         intCodLocDevven=intCodLocDev;
         intCodTipDocDevven=intCodTipDocDev;
         intCodDocDevven=intCodDocDev;


          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLocDev+", "+
          intCodTipDocDev+", "+intCodDocDev+", '"+datFecDoc+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nomcli")+"','"+rstLoc.getString("tx_dircli")+"','"+rstLoc.getString("tx_ruc")+ "','"+rstLoc.getString("tx_telcli")+"','"+
          " ','',"+intNumDocDev+" , "+rstLoc.getString("ne_numdoc")+","+
          "  '' ,'' , "+dlbSub+" , "+rstLoc.getString("nd_porIva")+" , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSis+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSis+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+strConfDevVen+"', 'N' )";

          strSql +=" ; INSERT INTO tbr_cabmovinv( co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, co_docrel, st_regrep, co_emprel )" +
          " VALUES("+intCodEmp+", "+intCodLocDev+", "+intCodTipDocDev+", "+intCodDocDev+", 'A', "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 'I', "+intCodEmp+" ) ";

          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLocDev+" and co_tipdoc="+intCodTipDocDev;
          stmLoc.executeUpdate(strSql);

           strSql = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
          " values("+intCodEmp+","+intCodLocDev+","+intCodTipDocDev+","+intCodDocDev+", 1 ,"+
          "0,'"+datFecAux+"',"+dlbTot+","+0+","+0+",'N','N','N','A','I')";
          stmLoc.executeUpdate(strSql);
          

          if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
             stbDocRelEmpLoc.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLocDev+" AS COLOC , "+intCodTipDocDev+" AS COTIPDOC, "+intCodDocDev+" AS CODOC ");
          intDocRelEmpLoc=1;


      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

private boolean generaDevVenDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanFac, int intCodLocDev, String strmeringegrfisbod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );


        //if(StrEstConfDevVen.equals("F")) strmeringegrfisbod="N";

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom,st_reg " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_candev, nd_canorg, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S', 'A'"+
        " ,"+dblTotal+", 'I' , '"+strmeringegrfisbod+"', 0 ,"+dblCosUni+", "+(dblCan*intTipMov)+", abs("+dblCanFac+") " +
        " , '"+bodega.getSelectedItem().toString()+"'  ) ";
        stmLoc.executeUpdate(strSql);
        //stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven+"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
        //stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDev+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc;
        stmLoc.executeUpdate(strSql);
        //stbDevTemp.append( strSql +" ; " );


      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}

public boolean generaAsientoDevVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDia,  java.util.Date datFecDoc, String strFecSis
        , double dblTotal, double dblSubtotal, double dblIva, double dblTotDes,int intCodCli){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
   if(conn != null){
       stmLoc=conn.createStatement();

 int intCodCtaTuvCas=2690;
 int intCodCtaTuvDim=2691;
 int intCodCtaCasTuv=1186;
 int intCodCtaCasDim=1187;
 int intCodCtaDimTuv=2128;
 int intCodCtaDimCas=2129;
 
 int intCodCtaSub=0;
 int intCodCtaIva=0;
 int intCodCtaTot=0;
 int intCodCtaDes=0;

 
 if(intCodEmp==1){
     intCodCtaSub=1434;
     intCodCtaIva=1030;
     intCodCtaDes=1434;

   if(intCodCli==603){   // castek
     intCodCtaTot=intCodCtaTuvCas;
   }if(intCodCli==1039){  // dimulti
     intCodCtaTot=intCodCtaTuvDim;
 }}

 if(intCodEmp==2){
     intCodCtaSub=551;
     intCodCtaIva=224;
     intCodCtaDes=551;

   if(intCodCli==2854){   // tuval
     intCodCtaTot=intCodCtaCasTuv;
   }if(intCodCli==789){  // dimulti
     intCodCtaTot=intCodCtaCasDim;
 }}

if(intCodEmp==4){
     intCodCtaSub=1373;
     intCodCtaIva=977;
     intCodCtaDes=1373;
     
   if(intCodCli==3117){   // tuval
      intCodCtaTot=intCodCtaDimTuv;
   }if(intCodCli==498){  // castek
     intCodCtaTot=intCodCtaDimCas;
 }}


  strSql="INSERT INTO tbm_cabdia(co_emp, co_loc, co_tipdoc, co_dia, fe_dia, st_reg , fe_ing , co_usring, st_regrep, st_imp, st_usrmodasidiapre, st_asidiareg  ) " +
  " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+",'"+datFecDoc+"','A', '"+strFecSis+"', "+objZafParSis.getCodigoUsuario()+" " +
  " ,'I', 'N', 'N', 'S' )  ; ";

  if((dblTotDes+dblSubtotal)>0){
       strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
       " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 1,  "+intCodCtaSub+", "+objUti.redondear(dblTotDes+dblSubtotal,2)+" " +
       " , 0, 'I' ) ; ";
  }

  if( dblIva > 0){
     strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
     " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 2,  "+intCodCtaIva+", "+objUti.redondear(dblIva,2)+" " +
     " , 0, 'I' )  ; ";
  }

  if( dblTotal > 0){
     strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
     " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 3,  "+intCodCtaTot+", 0,  "+objUti.redondear(dblTotal,2)+" " +
     " , 'I' ) ; ";
  }

  if( dblTotDes > 0){
       strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
       " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 4,  "+intCodCtaDes+", 0, "+objUti.redondear(dblTotDes,2)+" " +
       " , 'I' ) ; ";
  }


  System.out.println("--> "+strSql);

  stmLoc.executeUpdate(strSql);

  stmLoc.close();
  stmLoc=null;
  blnRes=true;
 
}}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



public boolean generaAsientoDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDia,  java.util.Date datFecDoc, String strFecSis
        , double dblTotal, double dblSubtotal, double dblIva, int intBodOri, int intCodCli){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="", strSql2="";
 try{
   if(conn != null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

 int intCodCtaTuvCas=2692;
 int intCodCtaTuvDim=2693;
 int intCodCtaCasTuv=1188;
 int intCodCtaCasDim=1189;
 int intCodCtaDimTuv=2130;
 int intCodCtaDimCas=2131;

 int intCodCtaBod=0;
 int intCodCtaIva=0;
 int intCodCtaTot=0;

        strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodOri+" ";
        rstLoc=stmLoc01.executeQuery(strSql2);
        if(rstLoc.next()){
            intCodCtaBod= rstLoc.getInt("co_ctaexi");
        }
        rstLoc.close();
        rstLoc=null;



 if(intCodEmp==1){

        
//        if(intBodOri==1)  intCodCtaBod=407;
//        if(intBodOri==3)  intCodCtaBod=2434;
//        if(intBodOri==4)  intCodCtaBod=2435;
//        if(intBodOri==5)  intCodCtaBod=2499;
//        if(intBodOri==6)  intCodCtaBod=2500;
//        if(intBodOri==7)  intCodCtaBod=2501;
//        if(intBodOri==8)  intCodCtaBod=2502;
//        if(intBodOri==9)  intCodCtaBod=2503;
//        if(intBodOri==10)  intCodCtaBod=2504;
//        if(intBodOri==12)  intCodCtaBod=2639;

     intCodCtaIva=584;

   if(intCodCli==603){   // castek
     intCodCtaTot=intCodCtaTuvCas;
   }if(intCodCli==1039){  // dimulti
     intCodCtaTot=intCodCtaTuvDim;
 }}

 if(intCodEmp==2){

       
//        if(intBodOri==1)  intCodCtaBod=74;
//        if(intBodOri==3)  intCodCtaBod=75;
//        if(intBodOri==4)  intCodCtaBod=1025;
//        if(intBodOri==5)  intCodCtaBod=1092;
//        if(intBodOri==6)  intCodCtaBod=1093;
//        if(intBodOri==7)  intCodCtaBod=1121;
//        if(intBodOri==8)  intCodCtaBod=1122;
//        if(intBodOri==9)  intCodCtaBod=1123;
//        if(intBodOri==10)  intCodCtaBod=1124;
//        if(intBodOri==12)  intCodCtaBod=1160;

     intCodCtaIva=94;
     
   if(intCodCli==2854){   // tuval
     intCodCtaTot=intCodCtaCasTuv;
   }if(intCodCli==789){  // dimulti
     intCodCtaTot=intCodCtaCasDim;
 }}

if(intCodEmp==4){

//        if(intBodOri==1)  intCodCtaBod=383;
//        if(intBodOri==2)  intCodCtaBod=381;
//        if(intBodOri==3)  intCodCtaBod=1992;
//        if(intBodOri==4)  intCodCtaBod=1993;
//        if(intBodOri==5)  intCodCtaBod=2031;
//        if(intBodOri==6)  intCodCtaBod=2032;
//        if(intBodOri==7)  intCodCtaBod=2034;
//        if(intBodOri==8)  intCodCtaBod=2035;
//        if(intBodOri==9)  intCodCtaBod=2036;
//        if(intBodOri==11)  intCodCtaBod=2101;

     intCodCtaIva=545;
   
   if(intCodCli==3117){   // tuval
      intCodCtaTot=intCodCtaDimTuv;
   }if(intCodCli==498){  // castek
     intCodCtaTot=intCodCtaDimCas;
 }}


  strSql="INSERT INTO tbm_cabdia(co_emp, co_loc, co_tipdoc, co_dia, fe_dia, st_reg , fe_ing , co_usring, st_regrep, st_imp, st_usrmodasidiapre, st_asidiareg  ) " +
  " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+",'"+datFecDoc+"','A', '"+strFecSis+"', "+objZafParSis.getCodigoUsuario()+" " +
  " ,'I', 'N', 'N', 'S' )  ; ";

  if((dblSubtotal)>0){
       strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
       " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 1,  "+intCodCtaBod+", 0, "+objUti.redondear(dblSubtotal,2)+" " +
       " , 'I' ) ; ";
  }

  if( dblIva > 0){
     strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
     " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 2,  "+intCodCtaIva+", 0, "+objUti.redondear(dblIva,2)+" " +
     " , 'I' )  ; ";
  }

  if( dblTotal > 0){
     strSql+="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab, st_regrep )" +
     " VALUES("+intCodEmp+","+intCodLoc+","+intCodTipDoc+","+intCodDia+", 3,  "+intCodCtaTot+",  "+objUti.redondear(dblTotal,2)+" " +
     " , 0, 'I' ) ; ";
  }


 // System.out.println("--> "+strSql);

  stmLoc.executeUpdate(strSql);

  stmLoc.close();
  stmLoc=null;
  stmLoc01.close();
  stmLoc01=null;
  blnRes=true;

}}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



  

private boolean generaDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg
       , double bldivaEmp,double dblCan, double dblPorDes,  double dblCosUni, double dblCanOc , int intCodBod, String strCodAlt, String strCodAlt2
       , String strNomItm, String strUniMed, int intCodItm, int  intCodEmpCom, int intCodCliOri , int intCodCliDes, int intCodBodDes ){

  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDocDev=0;
  int intCodTipDocDev=4;
  int intCodLocDev=0;
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      if(intCodEmp==1)  intCodLocDev=5;
      if(intCodEmp==2)  intCodLocDev=5;
      if(intCodEmp==4)  intCodLocDev=2;

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLocDev+" AND co_tipDoc="+intCodTipDocDev;
      rstLoc = stmLoc.executeQuery(strSql);

      if(rstLoc.next()) intCodDocDev=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;
      objInvItm.limpiarObjeto();

      double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );
      double dlbValIva=  dlbSub * (bldivaEmp/100);


      
      dlbSub = objUti.redondear(dlbSub,2);
      dlbValIva = objUti.redondear(dlbValIva,2);

      double dlbTot=  dlbSub + dlbValIva;

      dlbTot = objUti.redondear(dlbTot,2);
      
      

      Glo_dblTotDevCom += dlbSub;

      dlbSub=dlbSub*-1;
      dlbValIva=dlbValIva*-1;
      dlbTot=dlbTot*-1;

     
     int intTipMov=-1;

      datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
      String strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

      String strMerIngEgr="E";
      String strIngEgrFisBod="N";
      String strEstGuiRem="S";
      if(verificaIngEgrFisBod(intCodEmp, intCodBod, intCodEmpCom,  intCodBodDes )){
        strMerIngEgr="S";
        strIngEgrFisBod="S";
        strEstGuiRem="N";
      }



     if( generaDevComCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodLocDev, intCodTipDocDev, intCodDocDev, dlbSub,  dlbValIva,  dlbTot, strEstGuiRem , datFecAux, strFecSistema ) ){
      if( generaDevComDet( conn, intCodEmp, intCodLocDev, intCodTipDocDev,  intCodDocDev,  intCodBod,  intCodTipDoc,
            intCodDoc,  intCodReg,   strCodAlt, strCodAlt2,  strNomItm,  strUniMed,  dblCan,  intCodItm,  intTipMov,  intCodEmpCom,
            dblCosUni,  dblPorDes,  dblCanOc, intCodLoc,  strEstGuiRem, strIngEgrFisBod  ) ) {
         if( generaAsientoDevCom( conn,  intCodEmp,  intCodLocDev,  intCodTipDocDev,  intCodDocDev, datFecAux, strFecSistema, dlbTot*-1,  dlbSub*-1,  dlbValIva*-1,  intCodBod,  intCodCliOri ) ) {

          if(actualizaStockDev( conn , null, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgr, "E" )){

          blnRes=true;
        }
     }}}


    objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}


private boolean generaDevComCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodLocDev, int intCodTipDocDev, int intCodDocDev, double dlbSub, double dlbValIva, double dlbTot, String strEstGuiRem,  java.util.Date datFecDoc, String strFecSis  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDocDev=0;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       strSql=" select * from tbm_cabmovinv where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" ";
       rstLoc = stmLoc.executeQuery(strSql);
       if(rstLoc.next()){

          intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLocDev, intCodTipDocDev);


         intCodLocDevCompra=intCodLocDev;
         intCodTipDocDevCom=intCodTipDocDev;
         intCodDocDevCom=intCodDocDev;



          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLocDev+", "+
          intCodTipDocDev+", "+intCodDocDev+", '"+datFecDoc+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nomcli")+"','"+rstLoc.getString("tx_dircli")+"','"+rstLoc.getString("tx_ruc")+ "','"+rstLoc.getString("tx_telcli")+"','"+
          " ','',"+intNumDocDev+" , "+rstLoc.getString("ne_numdoc")+","+
          "  '' ,'' , "+dlbSub+" ,"+rstLoc.getString("nd_porIva")+" , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSis+"', "+
          objZafParSis.getCodigoUsuario()+", '"+strFecSis+"', "+objZafParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', 'P', 'N' )";
          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLocDev+" and co_tipdoc="+intCodTipDocDev;
          stmLoc.executeUpdate(strSql);

          strSql = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
          " values("+intCodEmp+","+intCodLocDev+","+intCodTipDocDev+","+intCodDocDev+", 1 ,"+
          "0,'"+datFecAux+"',"+dlbTot+","+0+","+0+",'N','N','N','A','I')";
          stmLoc.executeUpdate(strSql);


          if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
             stbDocRelEmpLoc.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLocDev+" AS COLOC , "+intCodTipDocDev+" AS COTIPDOC, "+intCodDocDev+" AS CODOC ");
          intDocRelEmpLoc=1;
           

          blnRes=true;
     }
     rstLoc.close();
     rstLoc=null;
     
      stmLoc.close();
      stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}
// nd_candev
private boolean generaDevComDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanOc , int intCodLocDevCom, String strEstGuiRem, String strIngEgrFisBod  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesBaseDatos() );

       
        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom,st_reg " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_canorg, nd_candev, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblTotal*intTipMov)+","+dblCosUni+", "+dblPorDes+", 'S', 'A'"+
        " ,"+(dblTotal*intTipMov)+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+", "+dblCanOc+", "+(dblCan*intTipMov)+" " +
        ", '"+bodega1.getSelectedItem().toString()+"' ) ";
        stmLoc.executeUpdate(strSql);
        //stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
       // stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDevCom+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc;
        stmLoc.executeUpdate(strSql);
        //stbDevTemp.append( strSql +" ; " );

      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}







private boolean actualizaStockDev(java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodBod,  double dblCan, int intTipMov, int intCodItm, String strMerIngEgr, String str_MerIEFisBod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  int intTipCli=3;
  double dlbCanMov=0.00;
  try{
      stmLoc=conn.createStatement();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

      dlbCanMov=dblCan;

      objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, intTipMov, "N", strMerIngEgr, str_MerIEFisBod, 1);

      
      if(!objInvItm.ejecutaActStock(conn, intTipCli))
          return false;

       if(connRemota!=null){
            System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
            return false;
       }
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}








private boolean verificaIngEgrFisBod(int intCodEmpOri, int intCodBodOri, int intCodEmpDes, int intCodBodDes ){
 boolean blnRes=true;
 try{

     System.out.println(" -> "+intCodEmpOri+" -> "+intCodBodOri+" -> "+intCodEmpDes+" -> "+intCodBodDes);

     
  if(intCodEmpOri==1){
   if(intCodBodOri==1){   // CALIFORMIA
     if(intCodEmpDes==2){ if( intCodBodDes==3 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==2 ) blnRes=false; }
    }

    if(intCodBodOri==8){  // QUITO
     if(intCodEmpDes==2){ if( intCodBodDes==1 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==7 ) blnRes=false; }
    }

    if(intCodBodOri==7){  // VIA DAULE
     if(intCodEmpDes==2){ if( intCodBodDes==9 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==1 ) blnRes=false; }
    }

    if(intCodBodOri==10){  // MANTA
     if(intCodEmpDes==2){ if( intCodBodDes==4 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==9 ) blnRes=false; }
    }

    if(intCodBodOri==12){  // SANTO DOMINGO
     if(intCodEmpDes==2){ if( intCodBodDes==12 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==11 ) blnRes=false; }
    }

   }



  if(intCodEmpOri==2){
   if(intCodBodOri==3){
     if(intCodEmpDes==1){ if( intCodBodDes==1 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==2 ) blnRes=false; }
    }

   if(intCodBodOri==1){
     if(intCodEmpDes==1){ if( intCodBodDes==8 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==7 ) blnRes=false; }
    }

   if(intCodBodOri==9){
     if(intCodEmpDes==1){ if( intCodBodDes==7 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==1 ) blnRes=false; }
    }

   if(intCodBodOri==4){
     if(intCodEmpDes==1){ if( intCodBodDes==10 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==9 ) blnRes=false; }
    }

    if(intCodBodOri==12){
     if(intCodEmpDes==1){ if( intCodBodDes==12 ) blnRes=false; }
     if(intCodEmpDes==4){ if( intCodBodDes==11 ) blnRes=false; }
    }
   }




  if(intCodEmpOri==4){
   if(intCodBodOri==2){
     if(intCodEmpDes==1){ if( intCodBodDes==1 ) blnRes=false; }
     if(intCodEmpDes==2){ if( intCodBodDes==3 ) blnRes=false; }
    }
    if(intCodBodOri==7){
     if(intCodEmpDes==1){ if( intCodBodDes==8 ) blnRes=false; }
     if(intCodEmpDes==2){ if( intCodBodDes==1 ) blnRes=false; }
    }

   if(intCodBodOri==1){
     if(intCodEmpDes==1){ if( intCodBodDes==7 ) blnRes=false; }
     if(intCodEmpDes==2){ if( intCodBodDes==9 ) blnRes=false; }
    }

    if(intCodBodOri==9){
     if(intCodEmpDes==1){ if( intCodBodDes==10 ) blnRes=false; }
     if(intCodEmpDes==2){ if( intCodBodDes==4 ) blnRes=false; }
    }

    if(intCodBodOri==11){
     if(intCodEmpDes==1){ if( intCodBodDes==12 ) blnRes=false; }
     if(intCodEmpDes==2){ if( intCodBodDes==12 ) blnRes=false; }
    }
   }

 }catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}











private boolean realizarComprasVentas(java.sql.Connection conn ){
    boolean blnRes=false;
    java.sql.ResultSet rstLoc;
    java.sql.Statement stmLoc;

   
    try{
       if(conn != null){
            stmLoc=conn.createStatement();

            int intCodEmpVen=0;
            int intCodLocVen=0;
            int intCodTipDocVen=0;


            int intCodEmpCom=0;
            int intCodLocCom=0;
            int intCodTipDocCom=0;

       

           if( vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("1") ){  //Tuval
               intCodEmpVen=1;
               intCodLocVen=4;

             if( vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2") ){
                   intCodTipDocCom=128; // FACCOMTU
                   intCodEmpCom=2;
                  if( vecBod2.elementAt(bodega1.getSelectedIndex()).toString().equals("4") ){
                     intCodLocCom=4;
                     intCodTipDocVen=126;  // FACVENCM
                  }if( vecBod2.elementAt(bodega1.getSelectedIndex()).toString().equals("1") ){
                     intCodLocCom=1;
                     intCodTipDocVen=125;  // FACVENCQ
                  }
                  if( vecBod2.elementAt(bodega1.getSelectedIndex()).toString().equals("12") ){
                     intCodLocCom=6;
                     intCodTipDocVen=166;  // FACVENCS
                  }
             }

             if( vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4") ){
                   intCodTipDocCom=128; // FACCOMTU
                   intCodEmpCom=4;
                   intCodLocCom=1;
                    String sql="select co_loc from tbm_loc where co_emp=4 and st_reg='P'";
                   rstLoc=stmLoc.executeQuery(sql);
                   if(rstLoc.next()){
                       intCodLocCom=rstLoc.getInt("co_loc");
                   }
                   rstLoc.close();
                   rstLoc=null;
                   intCodTipDocVen=127;  // FACVENDIM
             }


          }




           if( vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("2") ){  //CASTEK
               intCodEmpVen=2;


               if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("4") )
                  intCodLocVen=4;
               else
                  intCodLocVen=1;


             if( vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1") ){
                 intCodTipDocVen=124;
                 intCodEmpCom=1;
                 intCodLocCom=4;

                 if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("4") ){
                    intCodTipDocCom=130; // FACCOMCM
                 }if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1") || vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("3")  ){
                    intCodTipDocCom=129;  // FACCOMCQ
                  }
                 if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("12") ){
                    intCodTipDocCom=165;  // FACCOMCS
                  }
             }

             if( vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4") ){
                   intCodEmpCom=4;
                   intCodLocCom=1;
                   String sql="select co_loc from tbm_loc where co_emp=4 and st_reg='P'";
                   rstLoc=stmLoc.executeQuery(sql);
                   if(rstLoc.next()){
                       intCodLocCom=rstLoc.getInt("co_loc");
                   }
                   rstLoc.close();
                   rstLoc=null;

                   intCodTipDocVen=127;  // FACVENDIM

                  if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("4") )
                    intCodTipDocCom=130; // FACCOMCM
                   if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1") || vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("9") )
                    intCodTipDocCom=129;  // FACCOMCQ
                   if( vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("12") )
                    intCodTipDocCom=166;  // FACCOMCS


             }


          }




           if( vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("4") ){  //DIMULTI
               intCodEmpVen=4;
               intCodLocVen=1;

               String sql="select co_loc from tbm_loc where co_emp=4 and st_reg='P'";
               rstLoc=stmLoc.executeQuery(sql);
               if(rstLoc.next()){
                   intCodLocVen=rstLoc.getInt("co_loc");
               }
               rstLoc.close();
               rstLoc=null;

             if( vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1") ){
                 intCodTipDocVen=124;
                 intCodEmpCom=1;
                 intCodLocCom=4;
                    intCodTipDocCom=131; // FACCOMDIM
              }



             if( vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2") ){
                   intCodTipDocCom=131; // FACCOMDIM
                   intCodEmpCom=2;
                  if( vecBod2.elementAt(bodega1.getSelectedIndex()).toString().equals("4") ){
                     intCodLocCom=4;
                     intCodTipDocVen=126;  // FACVENCM
                  } if( vecBod2.elementAt(bodega1.getSelectedIndex()).toString().equals("1") ){
                     intCodLocCom=1;
                     intCodTipDocVen=125;  // FACVENCQ
                   }
                   if( vecBod2.elementAt(bodega1.getSelectedIndex()).toString().equals("12") ){
                     intCodLocCom=6;
                     intCodTipDocVen=166;  // FACVENCS
                   }
             }

          }


           

    

           if( generaVen(conn, intCodEmpVen,  intCodLocVen, intCodTipDocVen,  Integer.parseInt( vecBod1.elementAt(bodega.getSelectedIndex()).toString() ),  intCodEmpCom, Integer.parseInt( vecBod2.elementAt(bodega1.getSelectedIndex()).toString() )  ) ){
             if( generaCom(conn, intCodEmpCom,  intCodLocCom, intCodTipDocCom,  Integer.parseInt( vecBod2.elementAt(bodega1.getSelectedIndex()).toString() ),  intCodEmpVen, Integer.parseInt( vecBod1.elementAt(bodega.getSelectedIndex()).toString() ) ) ){

               blnRes=true;

            }else {  System.out.println("Mal...");  blnRes=false;  }
           }else {   System.out.println("Mal..."); blnRes=false; }





         

       stmLoc.close();
       stmLoc=null;

      }}catch(java.sql.SQLException Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){   blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);   }
    return  blnRes;
    }


  


    
private boolean asignaSecEmpGrp(java.sql.Connection connLoc,  StringBuffer stbDocRelSec ){
  boolean blnRes=false;
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc, stmLoc01;
  String strSql="";
  int intSecEmp=0,intSecGrp=0;
  try{
   if(connLoc!=null){
     stmLoc=connLoc.createStatement();
     stmLoc01=connLoc.createStatement();
     strSql="SELECT * FROM( "+stbDocRelSec.toString()+" ) AS x";
     //System.out.println(" -------->   "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        intSecEmp=objUltDocPrint.getNumSecDoc(connLoc, rstLoc.getInt("coemp") );
        intSecGrp=objUltDocPrint.getNumSecDoc(connLoc, objZafParSis.getCodigoEmpresaGrupo() );

         strSql="UPDATE tbm_cabmovinv SET ne_SecEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+" WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" " +
         " AND co_tipdoc="+rstLoc.getInt("cotipdoc")+" AND  co_doc="+rstLoc.getInt("codoc")+"";
         stmLoc01.executeUpdate(strSql);
     }
     rstLoc.close();
     rstLoc=null;

    stmLoc.close();
    stmLoc=null;
    stmLoc01.close();
    stmLoc01=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  return blnRes;
}







    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        blnEstCon=false;
        if(validarBod()){
           blnEstCon=true;
        }


        if(blnEstCon){

        cargarDat();
        
        this.jTabbedPane1.setSelectedIndex(1);

      

        empresa.setEnabled(false);
        bodega.setEnabled(false);   
        empresa1.setEnabled(false);
        bodega1.setEnabled(false);   
        jButton1.setEnabled(false); 

        radCom.setEnabled(false);
        radDev.setEnabled(false);
        optTod.setEnabled(false);
        optFil.setEnabled(false);
        txtcodaltdes.setEnabled(false);
        txtcodalthas.setEnabled(false); 
        
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed




private boolean validarBod(){
 boolean blnRes=true;
 int intSelBod=0;
 intSelBod= bodega1.getSelectedIndex();

       if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("1")){
            if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==1)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()  ); }
               } else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                   if(!(intSelBod==1))  { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("7")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==4))  { blnRes=false; msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                   if(!(intSelBod==0)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("8")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                    if(!(intSelBod==0)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                    if(!(intSelBod==3)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("10")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==2)) { blnRes=false; msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                  if(!(intSelBod==4)) { blnRes=false; msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
            }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("12")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==5))  { blnRes=false; msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
              }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                  if(!(intSelBod==5)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

              else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("3")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==3))  { blnRes=false; msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
              }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                  if(!(intSelBod==2)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}
    
     }





     if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("2")){
            if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                  if(!(intSelBod==3)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                  if(!(intSelBod==3)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
              }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("3")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                   if(!(intSelBod==0)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                   if(!(intSelBod==1)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("4")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                    if(!(intSelBod==4)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                    if(!(intSelBod==4)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("9")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                   if(!(intSelBod==2)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                   if(!(intSelBod==0)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("12")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                  if(!(intSelBod==5)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
            }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                   if(!(intSelBod==5)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("5")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                  if(!(intSelBod==1)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
            }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("4")){
                   if(!(intSelBod==2)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}
     }



     if(vecEmp1.elementAt(empresa.getSelectedIndex()).toString().equals("4")){
            if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("1")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                  if(!(intSelBod==2)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==4)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }}

              else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("2")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                   if(!(intSelBod==0)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==1)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
              }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("7")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                    if(!(intSelBod==3)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                  if(!(intSelBod==0)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

            else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("9")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                   if(!(intSelBod==4)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==2)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

             else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("11")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                   if(!(intSelBod==5)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==5)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

              else if(vecBod1.elementAt(bodega.getSelectedIndex()).toString().equals("3")){
               if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("1")){
                   if(!(intSelBod==1)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
               }else if(vecEmp2.elementAt(empresa1.getSelectedIndex()).toString().equals("2")){
                   if(!(intSelBod==3)) { blnRes=false;  msnBod( empresa.getItemAt(empresa.getSelectedIndex()).toString(), bodega.getItemAt(bodega.getSelectedIndex()).toString(), empresa1.getItemAt(empresa1.getSelectedIndex()).toString(), bodega1.getItemAt(bodega1.getSelectedIndex()).toString()   ); }
             }}

     }




  return blnRes;
}




private void msnBod(String StrNomEmpOri, String strNomBodOri, String StrNomEmpDes, String strNomBodDes ){
   blnEstCon=true;
        String strMsg2 = "Se enviara de "+StrNomEmpOri+"-"+strNomBodOri+"  a  "+StrNomEmpDes+"-"+strNomBodDes+" \n  ¿Está Seguro que desea continuar?";
        javax.swing.JOptionPane oppMsg2=new javax.swing.JOptionPane();
        String strTit2="Mensaje del sistema Zafiro";
        if(oppMsg2.showConfirmDialog(this,strMsg2,strTit2,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 1 ) {
            blnEstCon=false;
        }

 
}







    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        empresa.setEnabled(true);
        bodega.setEnabled(true);   
        empresa1.setEnabled(true);
        bodega1.setEnabled(true);   
        jButton1.setEnabled(true); 

        optTod.setEnabled(true);
        optFil.setEnabled(true);
        radDev.setEnabled(true);
        radCom.setEnabled(true);
        txtcodaltdes.setEnabled(true);
        txtcodalthas.setEnabled(true);
  

        objTblMod.removeAllRows();      
        this.jTabbedPane1.setSelectedIndex(0);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtcodaltdesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodaltdesFocusGained
        // TODO add your handling code here:
        txtcodaltdes.selectAll();
}//GEN-LAST:event_txtcodaltdesFocusGained

    private void txtcodaltdesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodaltdesFocusLost
        // TODO add your handling code here:

        if (txtcodaltdes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtcodalthas.getText().length()==0)
                txtcodalthas.setText(txtcodaltdes.getText());
        }
}//GEN-LAST:event_txtcodaltdesFocusLost

    private void txtcodalthasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodalthasFocusGained
        // TODO add your handling code here:
        txtcodalthas.selectAll();
}//GEN-LAST:event_txtcodalthasFocusGained

    private void txtcodalthasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodalthasFocusLost
        // TODO add your handling code here:
        if ( txtcodalthas.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtcodalthasFocusLost

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {



            txtcodaltdes.setText("");
            txtcodalthas.setText("");




        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:


     

        txtcodaltdes.setText("");
        txtcodalthas.setText("");

    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_optFilActionPerformed
    
    
    
    
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        dispose();
    }
}
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox bodega;
    private javax.swing.JComboBox bodega1;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox empresa;
    private javax.swing.JComboBox empresa1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbltit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JRadioButton radCom;
    private javax.swing.JRadioButton radDev;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtcodaltdes;
    private javax.swing.JTextField txtcodalthas;
    // End of variables declaration//GEN-END:variables
    
}
