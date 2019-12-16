/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package RecursosHumanos.ZafRecHum10;

import Librerias.ZafMae.ZafMaeVen.ZafVenEstCiv;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenCiu;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenOfi;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Roberto Flores
 */
public class ZafRecHum10 extends javax.swing.JInternalFrame {

  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private ZafUtil objUti; /**/
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private ZafTblCelEdiChk zafTblCelEdiChk;                                         //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChk;                                         //Renderer de Check Box para campo Laborable
  private Librerias.ZafDate.ZafDatePicker txtFec;
  private ZafThreadGUI objThrGUI;
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
  
  private ZafVenOfi zafVenOfi;
  
  private ZafVenCiu objVenConCiu;                                     //Ventana de consulta de Ciudad.
  private ZafVenEstCiv zafVenEstCiv;                          //Ventana de consulta de Estado Civil.
  private ZafVenCon objVenConUsr;
  
  private ZafVenCon objVenConOfi;

  private Vector vecSex;

  private static final int INT_TBL_LINEA=0;         // NUMERO DE LINEAS
  private static final int INT_TBL_COD_EMP=1;
  private static final int INT_TBL_NOM_EMP=2;
  private static final int INT_TBL_COD_TRA=3;       // CODIGO DEL EMPLEADO
  private static final int INT_TBL_EMP=4;           // NOMBRE DEL EMPLEADO
  private static final int INT_TBL_CHK_COM=5;           
  

  private static String strVersion=" v1.03 ";

  private Vector vecCab=new Vector();
  private java.util.Vector vecCoTra = new java.util.Vector();
  private javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  
  private String strSqlLoc="";
  private String strNomOfi = "";
  private String strCodOfi="";
  
  private int intCanReg=0;
  
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;

    /** Creates new form ZafRecHum15 */
    public ZafRecHum10(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
              this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();

              initComponents();

              this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
              lblTit.setText(objZafParSis.getNombreMenu());  /**/

              txtFec = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
              txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFec.setPreferredSize(new java.awt.Dimension(70, 20));
              //txtFec.setText("");
              panFil.add(txtFec);
              txtFec.setBounds(120, 40, 92, 20);
              //txtFecDoc.setEnabled(false);
              //txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFec.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
              txtFec.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      txtFecActionPerformed(evt);
                  }

                private void txtFecActionPerformed(ActionEvent evt) {
                    txtFec.transferFocus();
                }
              });
              
              objUti = new ZafUtil(); /**/
              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

              zafVenOfi = new ZafVenOfi(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Oficinas");
             
         }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }



     /**
      * Carga ventanas de consulta y configuracion de la tabla
      */

    public void Configura_ventana_consulta(){

        configurarVenConOficinas();
        ConfigurarTabla();
    }

private boolean configurarVenConOficinas() {
      //strAliCamSenSQL  
      boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_ofi");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("110");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL="select a.co_ofi, a.tx_nom  from tbm_ofi as a" +
                    " where a.st_reg not in ('I','E') order by a.tx_nom";
            objVenConOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{

        //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_COD_EMP,"Cód. Emp.");
        vecCab.add(INT_TBL_NOM_EMP,"Empresa");
        vecCab.add(INT_TBL_COD_TRA,"Código");
        vecCab.add(INT_TBL_EMP,"Empleado");
        vecCab.add(INT_TBL_CHK_COM,"Comida");

	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        //objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());


        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
         ZafMouMotAda objMouMotAda=new ZafMouMotAda();
         tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
         
         //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_COD_TRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COD_EMP).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_SUG).setCellRenderer(objTblCelRenLbl);
            //objTblCelRenLbl=null;

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_COD_EMP).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_NOM_EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_COD_TRA).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_EMP).setPreferredWidth(270);
        tcmAux.getColumn(INT_TBL_CHK_COM).setPreferredWidth(50);
        
        zafTblCelRenChk = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHK_COM).setCellRenderer(zafTblCelRenChk);

        zafTblCelEdiChk = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHK_COM).setCellEditor(zafTblCelEdiChk);

//         Aqui se agrega las columnas que van
//             ha hacer ocultas

        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_COD_EMP);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;


        //Configurar JTable: Establecer el ordenamiento en la tabla.
        objTblOrd=new ZafTblOrd(tblDat);
                
        //Configurar JTable: Editor de búsqueda.
        objTblBus=new ZafTblBus(tblDat);
        
        
        tcmAux=null;
        setEditable(true);

        blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
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
        lblTipDoc = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodOfi = new javax.swing.JTextField();
        txtNomOfi = new javax.swing.JTextField();
        butBusOfi = new javax.swing.JButton();
        optTipNAdmin = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optTipAdmin = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Fecha:"); // NOI18N
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(10, 40, 120, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Tipo:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(50, 120, 110, 20);

        txtCodOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodOfiActionPerformed(evt);
            }
        });
        txtCodOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusLost(evt);
            }
        });
        panFil.add(txtCodOfi);
        txtCodOfi.setBounds(160, 100, 92, 20);

        txtNomOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOfiActionPerformed(evt);
            }
        });
        txtNomOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusLost(evt);
            }
        });
        panFil.add(txtNomOfi);
        txtNomOfi.setBounds(250, 100, 210, 20);

        butBusOfi.setText("jButton1"); // NOI18N
        butBusOfi.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusOfiActionPerformed(evt);
            }
        });
        panFil.add(butBusOfi);
        butBusOfi.setBounds(460, 100, 20, 20);

        optTipNAdmin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTipNAdmin.setText("No administrativo");
        optTipNAdmin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTipNAdminItemStateChanged(evt);
            }
        });
        optTipNAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTipNAdminActionPerformed(evt);
            }
        });
        panFil.add(optTipNAdmin);
        optTipNAdmin.setBounds(270, 120, 130, 20);

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
        optFil.setBounds(10, 77, 370, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Oficina:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(50, 100, 110, 20);

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
        optTod.setBounds(10, 60, 330, 20);

        optTipAdmin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTipAdmin.setText("Administrativo");
        optTipAdmin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTipAdminItemStateChanged(evt);
            }
        });
        optTipAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTipAdminActionPerformed(evt);
            }
        });
        panFil.add(optTipAdmin);
        optTipAdmin.setBounds(160, 120, 130, 20);

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

        butVisPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVisPre.setText("Vista Preliminar");
        butVisPre.setPreferredSize(new java.awt.Dimension(90, 23));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        jPanel5.add(butVisPre);

        butImp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(90, 23));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        jPanel5.add(butImp);

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

        setBounds(0, 0, 600, 400);
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
        txtFec.transferFocus();
    } 
    
    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:

         cargarRepote(1);
         
//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum07/ZafRecHum07.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum07/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum07/ZafRecHum07.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum07/ZafCxC59_01.jrxml";
//                }
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
//
//                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
//                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
//
//
//                 switch (objSelFec.getTipoSeleccion())
//                 {
//                    case 0: //Búsqueda por rangos
//                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 1: //Fechas menores o iguales que "Hasta".
//                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 2: //Fechas mayores o iguales que "Desde".
//                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 3: //Todo.
//                        break;
//                 }
//
//                  if(!(txtCodLoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
//                  }else{
//                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
//                  }
//
//                  if(!(txtCodTipDoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
//                  }
//
//
//                 if(!(txtCodUsr.getText().equals(""))){
//                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
//                 }
//
//
//                 //Obtener la fecha y hora del servidor.
//                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux!=null){
//                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                      datFecAux=null;
//                    }
//                    strNomUsr=objZafParSis.getNombreUsuario();
//
//                   Map parameters = new HashMap();
//
//                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
//                   parameters.put("strAux",  sqlAux );
//                   parameters.put("nomusr", strNomUsr );
//                   parameters.put("fecimp", strFecHorSer );
//                   parameters.put("SUBREPORT", subReport);
//
//                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
//                  JasperViewer.viewReport(report, false);
//
//                  conIns.close();
//                  conIns=null;
//
//        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
//          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }

        

    }//GEN-LAST:event_butVisPreActionPerformed

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

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:

         cargarRepote(0);

//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum07/ZafRecHum07.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum07/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum07/ZafRecHum07.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum07/ZafCxC59_01.jrxml";
//                }
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
//
//                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
//                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
//
//
//                 switch (objSelFec.getTipoSeleccion())
//                 {
//                    case 0: //Búsqueda por rangos
//                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 1: //Fechas menores o iguales que "Hasta".
//                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 2: //Fechas mayores o iguales que "Desde".
//                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 3: //Todo.
//                        break;
//                 }
//
//                  if(!(txtCodLoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
//                  }else {
//                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
//                  }
//
//                  if(!(txtCodTipDoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
//                  }
//
//
//                 if(!(txtCodUsr.getText().equals(""))){
//                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
//                 }
//
//
//                   //Obtener la fecha y hora del servidor.
//                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux!=null){
//                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                      datFecAux=null;
//                    }
//                    strNomUsr=objZafParSis.getNombreUsuario();
//
//                   Map parameters = new HashMap();
//
//                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
//                   parameters.put("strAux",  sqlAux );
//                   parameters.put("nomusr", strNomUsr );
//                   parameters.put("fecimp", strFecHorSer );
//                   parameters.put("SUBREPORT", subReport);
//
//                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
//                  JasperManager.printReport(report,false);
//
//                  conIns.close();
//                  conIns=null;
//
//        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
//          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }



    }//GEN-LAST:event_butImpActionPerformed

    
    private void txtCodOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodOfiActionPerformed

        txtCodOfi.transferFocus();
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";

        try {

            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

            if (conn != null && (txtCodOfi.getText().compareTo("") != 0)) {

                stmLoc = conn.createStatement();
                strSql = "SELECT co_ofi,tx_nom from tbm_ofi where co_ofi =  " + txtCodOfi.getText()+" and st_reg  like 'A'";
                rstLoc = stmLoc.executeQuery(strSql);

                if (rstLoc.next()) {

                    txtNomOfi.setText(rstLoc.getString("tx_nom"));
                    txtNomOfi.setHorizontalAlignment(2);
                    //txtNomDep.setEnabled(false);
                } else {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomOfi.setText("");
                    txtCodOfi.setText("");
                }

                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//GEN-LAST:event_txtCodOfiActionPerformed

    private void txtCodOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusGained
        // TODO add your handling code here:
        strCodOfi = txtCodOfi.getText();
        txtCodOfi.selectAll();
    }//GEN-LAST:event_txtCodOfiFocusGained

    private void txtCodOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusLost

    }//GEN-LAST:event_txtCodOfiFocusLost

    private void txtNomOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOfiActionPerformed
        // TODO add your handling code here:
        txtNomOfi.transferFocus();
    }//GEN-LAST:event_txtNomOfiActionPerformed

    private void txtNomOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusGained
        // TODO add your handling code here:
        strNomOfi = txtNomOfi.getText();
        txtNomOfi.selectAll();
    }//GEN-LAST:event_txtNomOfiFocusGained

    private void txtNomOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusLost
        // TODO add your handling code here:
        if (!txtNomOfi.getText().equalsIgnoreCase(strNomOfi)) {
            if (txtNomOfi.getText().equals("")) {
                txtCodOfi.setText("");
                txtNomOfi.setText("");
            } else {
                BuscarOfi("a.tx_nom", txtNomOfi.getText(), 1);
            }
        } else {
            txtNomOfi.setText(strNomOfi);
        }
    }//GEN-LAST:event_txtNomOfiFocusLost

    public void BuscarOfi(String campo,String strBusqueda,int tipo){
        objVenConOfi.setTitle("Listado de Oficinas");
        if(objVenConOfi.buscar(campo, strBusqueda )) {
            txtCodOfi.setText(objVenConOfi.getValueAt(1));
            txtNomOfi.setText(objVenConOfi.getValueAt(2));
        }else{
            objVenConOfi.setCampoBusqueda(tipo);
            objVenConOfi.cargarDatos();
            objVenConOfi.show();
            if (objVenConOfi.getSelectedButton()==objVenConOfi.INT_BUT_ACE) {
                txtCodOfi.setText(objVenConOfi.getValueAt(1));
                txtNomOfi.setText(objVenConOfi.getValueAt(2));
        }else{
                txtCodOfi.setText(strCodOfi);
                txtNomOfi.setText(strNomOfi);
  }}}
    
    private void butBusOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusOfiActionPerformed
        //ZafVenOfi.setTitle("Listado de Oficinas");
        zafVenOfi.setCampoBusqueda(1);
        zafVenOfi.show();
        if (zafVenOfi.getSelectedButton() == zafVenOfi.INT_BUT_ACE) {
            txtCodOfi.setText(zafVenOfi.getValueAt(1));
            txtNomOfi.setText(zafVenOfi.getValueAt(2));
        }}
        
//GEN-LAST:event_butBusOfiActionPerformed

    private void optTipNAdminItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTipNAdminItemStateChanged

    }//GEN-LAST:event_optTipNAdminItemStateChanged

    private void optTipNAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTipNAdminActionPerformed
        txtCodOfi.setText("");
        txtNomOfi.setText("");
        optFil.setSelected(Boolean.TRUE);
        optTipAdmin.setSelected(Boolean.FALSE);
        optTod.setSelected(false);
    }//GEN-LAST:event_optTipNAdminActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
           txtCodOfi.setText("");
           txtNomOfi.setText("");
           optTipAdmin.setSelected(false);
           optTipNAdmin.setSelected(false);
           optFil.setSelected(true);
           optTod.setSelected(false);
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        txtCodOfi.setText("");
           txtNomOfi.setText("");
           optTipAdmin.setSelected(false);
           optTipNAdmin.setSelected(false);
           optFil.setSelected(false);
           optTod.setSelected(true);
    }//GEN-LAST:event_optTodActionPerformed

    private void optTipAdminItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTipAdminItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optTipAdminItemStateChanged

    private void optTipAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTipAdminActionPerformed
        // TODO add your handling code here:
        optFil.setSelected(Boolean.TRUE);
        optTod.setSelected(Boolean.FALSE);
        optTipNAdmin.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_optTipAdminActionPerformed

private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

//        if(validarCamObl()){
            if(cargarDat()){
                tabGen.setSelectedIndex(1);
            }
        else{
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
        String strNomCam = null;
        boolean blnOk = true;

        
        if(!txtFec.isFecha()){
            txtFec.requestFocus();
            txtFec.selectAll();
            MensajeValidaCampo("Fecha");
            return false;
        }
        
        if(txtCodOfi.getText().length()==0){
            txtCodOfi.requestFocus();
            txtCodOfi.selectAll();
            MensajeValidaCampo("Oficina");
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
          
          if(!(txtCodOfi.getText().equals(""))){
              sqlAux+=" AND b.co_ofi="+txtCodOfi.getText()+" ";
          }
          
          String strAux2="";
          if(optFil.isSelected()){

              if(optTipAdmin.isSelected() && optTipNAdmin.isSelected()){
                  strAux2+=" AND (b.tx_tipTra is null or b.tx_tipTra='A')";
              }else{
                  if(optTipAdmin.isSelected()){
                      strAux2+=" AND b.tx_tipTra = 'A' ";
                  }
                  if(optTipNAdmin.isSelected()){
                      strAux2+=" AND b.tx_tipTra IS NULL ";
                  }
              }
          }
          
          String strArrFec[]=txtFec.getText().split("/");
          String strFecha=strArrFec[2] + "-" + strArrFec[1] + "-" +strArrFec[0];
          
          strSql="SELECT c.co_emp,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                    "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon\n" +
                    "from tbm_tra a\n" +
                    "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                    "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                    "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                    "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                    "LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi)\n" +
                    "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                    "AND ((d.st_recAlm='S'\n" +
                    "AND d.tx_forRecAlm='C'\n" +
                    "AND d.ho_ent is not null)\n" +
                    "or d.st_autAlm='S')\n" +
                    " "  + sqlAux  + "\n" +
                    " "  + strAux2  + "\n" +
                    " UNION\n" +
                    "SELECT c.co_emp,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                    "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon\n" +
                    "from tbm_tra a\n" +
                    "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                    "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                    "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                    "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                    "LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi)\n" +
                    "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                    "AND d.tx_forRecAlm='C'\n" +
                    "AND d.ho_ent is not null\n" +
                    " "  + sqlAux  + "\n" +
                    " "  + strAux2  + "\n" +
                    " ORDER BY co_emp, empleado";

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
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", sqlAux="";
   try{
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
          
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

          if(!(txtCodOfi.getText().equals(""))){
              sqlAux+=" AND b.co_ofi="+txtCodOfi.getText()+" ";
          }
          String strAux2="";
          if(optFil.isSelected()){
              
              if(optTipAdmin.isSelected() && optTipNAdmin.isSelected()){
                  strAux2+=" AND (b.tx_tipTra is null or b.tx_tipTra='A')";
              }else{
                  if(optTipAdmin.isSelected()){
                      strAux2+=" AND b.tx_tipTra = 'A' ";
                  }
                  if(optTipNAdmin.isSelected()){
                      strAux2+=" AND b.tx_tipTra IS NULL ";
                  }
              }
          }

          String strArrFec[]=txtFec.getText().split("/");
            String strFecha=strArrFec[2] + "-" + strArrFec[1] + "-" +strArrFec[0];
          
          strSql="SELECT c.co_emp,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                    "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon\n" +
                    "from tbm_tra a\n" +
                    "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                    "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                    "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                    "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                    "LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi)\n" +
                    "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                    "AND ((d.st_recAlm='S'\n" +
                    "AND d.tx_forRecAlm='C'\n" +
                    "AND d.ho_ent is not null)\n" +
                    "or d.st_autAlm='S')\n" +
                    " "  + sqlAux  + "\n" +
                    " "  + strAux2  + "\n" +
                    " UNION\n" +
                    "SELECT c.co_emp,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                    "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon\n" +
                    "from tbm_tra a\n" +
                    "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                    "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                    "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                    "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                    "LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi)\n" +
                    "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                    "AND d.tx_forRecAlm='C'\n" +
                    "AND d.ho_ent is not null\n" +
                    " "  + sqlAux  + "\n" +
                    " "  + strAux2  + "\n" +
                    " ORDER BY co_emp, empleado";
                  

           System.out.println(strSql);
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA, "");
                vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp") );
                vecReg.add(INT_TBL_NOM_EMP, rstLoc.getString("empresa") );
                vecReg.add(INT_TBL_COD_TRA, rstLoc.getString("co_tra") );
                vecReg.add(INT_TBL_EMP, rstLoc.getString("empleado") );
                vecReg.add(INT_TBL_CHK_COM, Boolean.TRUE );
                vecData.add(vecReg);
                intCanReg++;

           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

           lblMsgSis.setText("Listo");
           pgrSis.setValue(0);
           pgrSis.setIndeterminate(false);

           stmLoc.close();
           stmLoc=null;
           conn.close();
           conn=null;
           blnRes=true;

   }}catch(java.sql.SQLException Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
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
    private javax.swing.JButton butBusOfi;
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTipAdmin;
    private javax.swing.JRadioButton optTipNAdmin;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodOfi;
    private javax.swing.JTextField txtNomOfi;
    // End of variables declaration//GEN-END:variables




private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    
     int intCol=tblDat.columnAtPoint(evt.getPoint());
     String strMsg="";

    switch (intCol){

        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_COD_EMP:
            strMsg="Código de la empresa";
            break;
        case INT_TBL_NOM_EMP:
            strMsg="Nombre de la empresa";
            break;            
        case INT_TBL_COD_TRA:
            strMsg="Código del empleado";
            break;
        case INT_TBL_EMP:
            strMsg="Nombres y apellidos del empleado";
            break;
        case INT_TBL_CHK_COM:
            strMsg="Lo recibe en \"Comida\"";
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



private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRpt();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
    }
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                   // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                  //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                 //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   // objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
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
        String strRutRpt, strNomRpt, strNomUsr="",  strFecHorSer="", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String sqlAux="", strSql = "";
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {

                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        
                        if(!(txtCodOfi.getText().equals(""))){
                            sqlAux+=" AND b.co_ofi="+txtCodOfi.getText()+" ";
                        }
                        
                        String strAux2="";
                        if(optFil.isSelected()){

                            if(optTipAdmin.isSelected() && optTipNAdmin.isSelected()){
                                strAux2+=" AND (b.tx_tipTra is null or b.tx_tipTra='A')";
                            }else{
                                if(optTipAdmin.isSelected()){
                                    strAux2+=" AND b.tx_tipTra = 'A' ";
                                }
                                if(optTipNAdmin.isSelected()){
                                    strAux2+=" AND b.tx_tipTra IS NULL ";
                                }
                            }
                        }

                       
                        
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            //case 415:
                            default:

                                 //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }
                                
                                String[] arrFecConAlm = txtFec.getText().split("/");
                                String strFecConAlm = arrFecConAlm[2] + "-" + arrFecConAlm[1] + "-" + arrFecConAlm[0];
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                               
                                obtenerCantidadRegistros();

                                 String strArrFec[]=txtFec.getText().split("/");
                                 String strFecha=strArrFec[2] + "-" + strArrFec[1] + "-" +strArrFec[0];
                                
                                strSql= " select distinct co_ofi, case (oficina is null) when true then 'Otros' else oficina end as oficina, tx_tiptra, \n"+
                                        " case (tx_tiptra='A') when true then 'Administración' else 'Ventas' end as tipoEmpleado \n"+
                                        " from ( \n"+
                                        "SELECT c.co_emp,b.co_ofi,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                                        "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon, f.tx_nom as oficina , b.tx_tiptra\n" +
                                        "from tbm_tra a\n" +
                                        "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                                        "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                                        "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                                        "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                                        "LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi)\n" +
                                        "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                                        "AND ((d.st_recAlm='S'\n" +
                                        "AND d.tx_forRecAlm='C'\n" +
                                        "AND d.ho_ent is not null)\n" +
                                        "or d.st_autAlm='S')\n" +
                                        " "  + sqlAux  + "\n" +
                                        " "  + strAux2  + "\n" +
                                        " UNION\n" +
                                        "SELECT c.co_emp,b.co_ofi,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                                        "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon, f.tx_nom as oficina , b.tx_tiptra\n" +
                                        "from tbm_tra a\n" +
                                        "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                                        "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                                        "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                                        "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                                        "LEFT OUTER JOIN tbm_ofi f on (f.co_ofi=b.co_ofi)\n" +
                                        "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                                        "AND d.tx_forRecAlm='C'\n" +
                                        "AND d.ho_ent is not null\n" +
                                        " "  + sqlAux  + "\n" +
                                        " "  + strAux2  + "\n" +
                                        " ORDER BY co_emp, empleado) \n"+
                                        " AS x";
                                System.out.println("reporte: " + strSql);
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("sql", strSql );
                                mapPar.put("empresa", objZafParSis.getNombreEmpresa() );
                                mapPar.put("cantotreg", intCanReg );
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("fecha", datFecAux);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("fechaconalm", strFecConAlm);
                                
                                /*java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("strAux",  sqlAux );
                                mapPar.put("nomusr", strNomUsr );
                                mapPar.put("fecimp", strFecHorSer );
                                mapPar.put("SUBREPORT_DIR", strRutRpt );
                                mapPar.put("strCamAudRpt", "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                */

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}