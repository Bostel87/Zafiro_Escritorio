/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package RecursosHumanos.ZafRecHum07;

import Librerias.ZafMae.ZafMaeVen.ZafVenEstCiv;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenCiu;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.JasperManager;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.view.JasperViewer;
//import java.util.Map;
//import java.util.HashMap;

/**
 *
 * @author Roberto Flores
 */
public class ZafRecHum07 extends javax.swing.JInternalFrame {

  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private ZafUtil objUti; /**/
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private ZafThreadGUI objThrGUI;
  private java.util.Date datFecAux; 
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                           //PopupMenu: Establecer PeopuMenú en JTable.
  
  private ZafVenCiu objVenConCiu;                                     //Ventana de consulta de Ciudad.
  private ZafVenEstCiv zafVenEstCiv;                          //Ventana de consulta de Estado Civil.
  private ZafVenCon objVenConUsr;

  private Vector vecSex;

  private final int INT_TBL_LINEA=0;            // NUMERO DE LINEAS
  private final int INT_TBL_EMPRESA=1;            // NUMERO DE LINEAS
  private final int INT_TBL_CODEMP=2;           // CODIGO DEL EMPLEADO
  private final int INT_TBL_IDEMP=3;            // IDENTIFICACIÓN DEL EMPLEADO
  private final int INT_TBL_EMPLEADO=4;         // APELLIDO DEL EMPLEADO
  private final int INT_TBL_ESTADO=5;         // ESTADO DEL EMPLEADO
  private final int INT_TBL_DEPARTAMENTO=6;     // DEPARTAMENTO AL QUE PERTENECE
  private final int INT_TBL_OFICINA=7;          // OFICINA A LA QUE PERTENECE
  private final int INT_TBL_FECINICON=8;        // FECHA DE INICIO DE CONTRATO DEL EMPLEADO
  private final int INT_TBL_FECNACEMP=9;        // FECHA NACIMIENTO DEL EMPLEADO
  private final int INT_TBL_TEL1EMP=10;          // TELEFONO1 DEL EMPLEADO
  private final int INT_TBL_TEL2EMP=11;          // TELEFONO2 DEL EMPLEADO
  private final int INT_TBL_TEL3EMP=12;          // TELEFONO3 DEL EMPLEADO
  private final int INT_TBL_EMAEMP=13;           // EMAIL DEL EMPLEADO
  private final int INT_TBL_NUMHIJEMP=14;       // NUMERO DE HIJOS DEL EMPLEADO

  private String strVersion=" v1.04 ";
  
  private String strCodTipDoc="";
  private String strDesCodTipDoc="";
  private String strDesLarTipDoc="";
  private String strCodLoc="";
  private String strDesLoc="";
  private String strCodUsr="";
  private String strUsr="";

  private Vector vecCab=new Vector();
  private java.util.Vector vecCoTra = new java.util.Vector();
  private javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  
  private String strSqlLoc="";
  
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;

    /** Creates new form ZafRecHum07 */
    public ZafRecHum07(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
              this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();


              vecSex=new Vector();
              vecSex.add("");
              vecSex.add("M");
              vecSex.add("F");

              initComponents();

              this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
              lblTit.setText(objZafParSis.getNombreMenu());  /**/

              objUti = new ZafUtil(); /**/
              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

         }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }



     /**
 * Carga ventanas de consulta y configuracion de la tabla
 */
public void Configura_ventana_consulta(){

    ConfigurarTabla();

}

private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{

        //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_EMPRESA,"Empresa");
        vecCab.add(INT_TBL_CODEMP,"Código");
        vecCab.add(INT_TBL_IDEMP,"Identificación");
        vecCab.add(INT_TBL_EMPLEADO,"Empleado");
        vecCab.add(INT_TBL_ESTADO,"Estado");
        vecCab.add(INT_TBL_DEPARTAMENTO,"Departamento");
        vecCab.add(INT_TBL_OFICINA,"Oficina");
        vecCab.add(INT_TBL_FECINICON,"Fec.Ini.Con.");        
        vecCab.add(INT_TBL_FECNACEMP,"Fec. Nac.");
        vecCab.add(INT_TBL_TEL1EMP,"Teléfono 1");
        vecCab.add(INT_TBL_TEL2EMP,"Teléfono 2");
        vecCab.add(INT_TBL_TEL3EMP,"Teléfono 3");
        vecCab.add(INT_TBL_EMAEMP,"Email");
        vecCab.add(INT_TBL_NUMHIJEMP,"Núm. Hij.");

	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());


        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        //tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
         ZafMouMotAda objMouMotAda=new ZafMouMotAda();
         tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_EMPRESA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_IDEMP).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_EMPLEADO).setPreferredWidth(220);
        tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DEPARTAMENTO).setPreferredWidth(120);
        tcmAux.getColumn(INT_TBL_OFICINA).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_FECINICON).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_FECNACEMP).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_TEL1EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_TEL2EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_TEL3EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_EMAEMP).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMHIJEMP).setPreferredWidth(60);
        
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
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txtCodEstCiv = new javax.swing.JTextField();
        txtDesLarEstCiv = new javax.swing.JTextField();
        butEstCiv = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        txtDesCiu = new javax.swing.JTextField();
        butCiu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNumHij = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboSex = new javax.swing.JComboBox();
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
        optTod.setBounds(10, 30, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
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
        optFil.setBounds(10, 50, 330, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Estado civil:"); // NOI18N
        panFil.add(jLabel3);
        jLabel3.setBounds(40, 90, 100, 20);

        txtCodEstCiv.setEnabled(false);
        txtCodEstCiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEstCivActionPerformed(evt);
            }
        });
        txtCodEstCiv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEstCivFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEstCivFocusLost(evt);
            }
        });
        panFil.add(txtCodEstCiv);
        txtCodEstCiv.setBounds(140, 90, 60, 20);

        txtDesLarEstCiv.setEnabled(false);
        txtDesLarEstCiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarEstCivActionPerformed(evt);
            }
        });
        txtDesLarEstCiv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarEstCivFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarEstCivFocusLost(evt);
            }
        });
        panFil.add(txtDesLarEstCiv);
        txtDesLarEstCiv.setBounds(200, 90, 200, 20);

        butEstCiv.setText(".."); // NOI18N
        butEstCiv.setEnabled(false);
        butEstCiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEstCivActionPerformed(evt);
            }
        });
        panFil.add(butEstCiv);
        butEstCiv.setBounds(400, 90, 20, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Ciudad:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 70, 100, 20);

        txtCodCiu.setEnabled(false);
        txtCodCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCiuActionPerformed(evt);
            }
        });
        txtCodCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusLost(evt);
            }
        });
        panFil.add(txtCodCiu);
        txtCodCiu.setBounds(140, 70, 60, 20);

        txtDesCiu.setEnabled(false);
        txtDesCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCiuActionPerformed(evt);
            }
        });
        txtDesCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCiuFocusLost(evt);
            }
        });
        panFil.add(txtDesCiu);
        txtDesCiu.setBounds(200, 70, 200, 20);

        butCiu.setText(".."); // NOI18N
        butCiu.setEnabled(false);
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panFil.add(butCiu);
        butCiu.setBounds(400, 70, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Número de hijos:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 130, 110, 20);

        txtNumHij.setEnabled(false);
        txtNumHij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumHijActionPerformed(evt);
            }
        });
        txtNumHij.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumHijFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumHijFocusLost(evt);
            }
        });
        panFil.add(txtNumHij);
        txtNumHij.setBounds(140, 130, 60, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Sexo:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 110, 80, 20);

        cboSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Masculino", "Femenino" }));
        cboSex.setEnabled(false);
        panFil.add(cboSex);
        cboSex.setBounds(140, 110, 260, 20);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodEstCivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEstCivActionPerformed
        // TODO add your handling code here:
        txtCodEstCiv.transferFocus();
}//GEN-LAST:event_txtCodEstCivActionPerformed

    private void txtCodEstCivFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEstCivFocusGained
        // TODO add your handling code here:
        strDesCodTipDoc=txtCodEstCiv.getText();
        txtCodEstCiv.selectAll();
}//GEN-LAST:event_txtCodEstCivFocusGained

    private void txtCodEstCivFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEstCivFocusLost
        // TODO add your handling code here:

        if (!txtCodEstCiv.getText().equalsIgnoreCase(strDesCodTipDoc)) {
            if (txtCodEstCiv.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtCodEstCiv.setText("");
                txtDesLarEstCiv.setText("");
            }//else
                //BuscarTipDoc("a.tx_descor",txtCodEstCiv.getText(),1);
        }//else
            //txtCodEstCiv.setText(strDesCodTipDoc);
}//GEN-LAST:event_txtCodEstCivFocusLost

    private void txtDesLarEstCivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarEstCivActionPerformed
        // TODO add your handling code here:
        txtDesLarEstCiv.transferFocus();
}//GEN-LAST:event_txtDesLarEstCivActionPerformed

    private void txtDesLarEstCivFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarEstCivFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarEstCiv.getText();
        txtDesLarEstCiv.selectAll();
}//GEN-LAST:event_txtDesLarEstCivFocusGained

    private void txtDesLarEstCivFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarEstCivFocusLost
        // TODO add your handling code here:
        if (!txtDesLarEstCiv.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarEstCiv.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtCodEstCiv.setText("");
                txtDesLarEstCiv.setText("");
            }//else
                //BuscarTipDoc("a.tx_deslar",txtDesLarEstCiv.getText(),2);
        }//else
            //txtDesLarEstCiv.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarEstCivFocusLost

    private void butEstCivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEstCivActionPerformed
        boolean blnRes=true;

        zafVenEstCiv = new ZafVenEstCiv(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Estado Civil");
        try{
            zafVenEstCiv.limpiar();
            zafVenEstCiv.setCampoBusqueda(0);
            zafVenEstCiv.setVisible(true);

            if (zafVenEstCiv.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                txtCodEstCiv.setText(zafVenEstCiv.getValueAt(2));
                txtDesLarEstCiv.setText(zafVenEstCiv.getValueAt(3));
            }
        }catch (Exception ex){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, ex);
        }

        //return blnRes;
}//GEN-LAST:event_butEstCivActionPerformed

    private void txtCodCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCiuActionPerformed
        // TODO add your handling code here:
        //txtCodCiu.transferFocus();



    }//GEN-LAST:event_txtCodCiuActionPerformed

    public void consultarRep(){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                if(conn!=null && txtCodCiu.getText().compareTo("")!=0){

                    stmLoc=conn.createStatement();

                    strSql="SELECT tx_deslar from tbm_ciu where co_ciu =  " + txtCodCiu.getText();
                    rstLoc=stmLoc.executeQuery(strSql);

                    if(rstLoc.next()){
                        txtDesCiu.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                        txtDesCiu.setHorizontalAlignment(2);
                        txtDesCiu.setEnabled(false);
                    }else{
                        mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                        txtDesCiu.setText("");
                        txtDesCiu.setText("");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    conn.close();
                    conn=null;
                }
            }catch(java.sql.SQLException Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            catch(Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
    }

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

    private void txtCodCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodCiu.getText();
        txtCodCiu.selectAll();
    }//GEN-LAST:event_txtCodCiuFocusGained

    private void txtCodCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusLost
        // TODO add your handling code here:
         if (!txtCodCiu.getText().equalsIgnoreCase(strCodLoc)) {
            if (txtCodCiu.getText().equals("")) {
                txtCodCiu.setText("");
                txtDesCiu.setText("");
            }
        }
    }//GEN-LAST:event_txtCodCiuFocusLost

    private void txtDesCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCiuActionPerformed
        // TODO add your handling code here:
        txtDesCiu.transferFocus();
    }//GEN-LAST:event_txtDesCiuActionPerformed

    private void txtDesCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCiuFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCiuFocusGained

    private void txtDesCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCiuFocusLost
        // TODO add your handling code here:
        if (!txtDesCiu.getText().equalsIgnoreCase(strDesLoc)) {
            if (txtDesCiu.getText().equals("")) {
                txtCodCiu.setText("");
                txtDesCiu.setText("");
            }
        }
    }//GEN-LAST:event_txtDesCiuFocusLost

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed

        boolean blnRes=true;
        objVenConCiu = new ZafVenCiu(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Ciudades");

        try{
            objVenConCiu.limpiar();
            objVenConCiu.setCampoBusqueda(0);
            objVenConCiu.setVisible(true);

            if (objVenConCiu.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                /*tbm_comsec.setIntCo_comsec(Integer.parseInt(zafVenComSec.getValueAt(1)));*/
                txtCodCiu.setText(String.valueOf(objVenConCiu.getValueAt(1)));
                txtDesCiu.setText(String.valueOf(objVenConCiu.getValueAt(3)));
            }
        }catch (Exception ex){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, ex);
        }

        //return blnRes;
    }//GEN-LAST:event_butCiuActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

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

    private void txtNumHijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumHijActionPerformed
        // TODO add your handling code here:
         txtNumHij.transferFocus();
    }//GEN-LAST:event_txtNumHijActionPerformed

    private void txtNumHijFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumHijFocusGained
        // TODO add your handling code here:
        strCodUsr=txtNumHij.getText();
        txtNumHij.selectAll();
    }//GEN-LAST:event_txtNumHijFocusGained

    private void txtNumHijFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumHijFocusLost
        // TODO add your handling code here:
         if (!txtNumHij.getText().equalsIgnoreCase(strCodUsr)) {
            if (txtNumHij.getText().equals("")) {
                txtNumHij.setText("");
                //txtusr.setText("");

            }else
                BuscarUsr("a.co_usr",txtNumHij.getText(),0);
        }else
            txtNumHij.setText(strCodUsr);

    }//GEN-LAST:event_txtNumHijFocusLost

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if(optFil.isSelected()){
            txtCodCiu.setEnabled(true);
            txtDesCiu.setEnabled(true);
            butCiu.setEnabled(true);
            txtCodEstCiv.setEnabled(true);
            txtDesLarEstCiv.setEnabled(true);
            butEstCiv.setEnabled(true);
            cboSex.setEnabled(true);
            txtNumHij.setEnabled(true);
        }
}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:

        if(optTod.isSelected()){
            txtCodCiu.setEnabled(false);
            txtDesCiu.setEnabled(false);
            butCiu.setEnabled(false);
            txtCodEstCiv.setEnabled(false);
            txtDesLarEstCiv.setEnabled(false);
            butEstCiv.setEnabled(false);
            cboSex.setEnabled(false);
            txtNumHij.setEnabled(false);
            txtCodCiu.setText("");
            txtDesCiu.setText("");
            txtCodEstCiv.setText("");
            txtDesCiu.setText("");
            txtDesLarEstCiv.setText("");
            txtNumHij.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {

            txtCodCiu.setText("");
            txtDesCiu.setText("");
            strCodLoc="";
            strDesLoc="";

            txtCodTipDoc.setText("");
            txtCodEstCiv.setText("");
            txtDesLarEstCiv.setText("");
            strCodTipDoc="";
            strDesCodTipDoc="";
            strDesLarTipDoc="";

        }
}//GEN-LAST:event_optTodItemStateChanged

private class ZafThreadGUI extends Thread{
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

          if(!(txtCodCiu.getText().equals(""))){
              sqlAux+=" AND a.co_ciunac="+txtCodCiu.getText()+" ";
          }

          if(!(txtCodEstCiv.getText().equals(""))){
              sqlAux+=" AND a.co_estciv = "+txtCodEstCiv.getText()+" ";
          }

          //jCboSex.
          if (cboSex.getSelectedIndex()>0)
                    sqlAux+=" AND a.tx_sex = '" + vecSex.get(cboSex.getSelectedIndex()) + "'";

          if(!(txtNumHij.getText().equals(""))){
              sqlAux+=" AND a.ne_numhij="+txtNumHij.getText()+" ";
          }


           if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){


               strSql="select c.*,d.tx_nom as empresa, e.tx_deslar as departamento, f.tx_nom as oficina, b.st_reg from (SELECT a.co_tra, a.tx_ide, a.tx_nom, a.tx_ape, a.fe_nac, a.tx_tel1, a.tx_tel2, a.tx_tel3, a.tx_corele, a.ne_numhij,b.co_emp, b.co_dep, b.co_ofi, b.fe_inicon FROM tbm_tra as a "+
                      "inner join tbm_traemp b on (a.co_tra = b.co_tra) " + " " +
                      "WHERE a.st_reg like 'A' " + sqlAux + ") c " +
                      "inner join tbm_emp d on (c.co_emp = d.co_emp) "+
                       " left outer join tbm_dep e on (e.co_dep = c.co_dep) "+
                    " left outer join tbm_ofi f on (f.co_ofi = c.co_ofi)"+
                    " inner join tbm_traemp b on (b.co_tra=c.co_tra)"+
                    " order by d.tx_nom,(c.tx_ape || ' ' || c.tx_nom)";

           }else{

              strSql="select c.*,d.tx_nom as empresa, e.tx_deslar as departamento, f.tx_nom as oficina, b.st_reg from (SELECT a.co_tra, a.tx_ide, a.tx_nom, a.tx_ape, a.fe_nac, a.tx_tel1, a.tx_tel2, a.tx_tel3, a.tx_corele, a.ne_numhij,b.co_emp, b.co_dep, b.co_ofi, b.fe_inicon FROM tbm_tra as a "+
                      "inner join tbm_traemp b on (a.co_tra = b.co_tra) and b.co_emp = " + objZafParSis.getCodigoEmpresa() + " " +
                      "WHERE a.st_reg like 'A' " + sqlAux + ") c " +
                      "inner join tbm_emp d on (c.co_emp = d.co_emp) "+
                       " left outer join tbm_dep e on (e.co_dep = c.co_dep) "+
                    " left outer join tbm_ofi f on (f.co_ofi = c.co_ofi)"+
                    " inner join tbm_traemp b on (b.co_tra=c.co_tra)"+
                    " order by d.tx_nom,(c.tx_ape || ' ' || c.tx_nom)";
           }

//           strSql+=" left outer join tbm_dep e on (e.co_dep = c.co_dep) "+
//                " left outer join tbm_ofi f on (f.co_ofi = c.co_ofi)"+
//                " order by (c.tx_ape || ' ' || c.tx_nom)";
           System.out.println(strSql);
           /*vecCoTra = new java.util.Vector();
            vecCoTra.add(rstLoc.getString("co_tra"));*/
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA, "");
                vecReg.add(INT_TBL_EMPRESA, rstLoc.getString("empresa"));
                vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_tra") );
                vecReg.add(INT_TBL_IDEMP, rstLoc.getString("tx_ide") );
                vecReg.add(INT_TBL_EMPLEADO, rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom") );
                
                if(rstLoc.getString("st_reg").compareTo("A")==0){
                    vecReg.add(INT_TBL_ESTADO, "ACTIVO" );
                }else if (rstLoc.getString("st_reg").compareTo("I")==0){
                    vecReg.add(INT_TBL_ESTADO, "INACTIVO" );
                }
                
//                vecReg.add(INT_TBL_NOMEMP, rstLoc.getString("tx_nom") );
                vecReg.add(INT_TBL_DEPARTAMENTO, rstLoc.getString("departamento") );
                vecReg.add(INT_TBL_OFICINA, rstLoc.getString("oficina") );
                vecReg.add(INT_TBL_FECINICON, rstLoc.getString("fe_inicon") );
                vecReg.add(INT_TBL_FECNACEMP, rstLoc.getString("fe_nac") );
                vecReg.add(INT_TBL_TEL1EMP, rstLoc.getString("tx_tel1") );
                vecReg.add(INT_TBL_TEL2EMP, rstLoc.getString("tx_tel2") );
                vecReg.add(INT_TBL_TEL3EMP, rstLoc.getString("tx_tel3") );
                vecReg.add(INT_TBL_EMAEMP, rstLoc.getString("tx_corele") );
                vecReg.add(INT_TBL_NUMHIJEMP, rstLoc.getString("ne_numhij") );
                vecData.add(vecReg);
                

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

 public void BuscarUsr(String campo,String strBusqueda,int tipo){
  objVenConUsr.setTitle("Listado de Usuario");
  if(objVenConUsr.buscar(campo, strBusqueda )) {
      txtNumHij.setText(objVenConUsr.getValueAt(1));
      //txtusr.setText(objVenConUsr.getValueAt(2));
      strCodUsr=objVenConUsr.getValueAt(1);
      optFil.setSelected(true);

  }else{
        objVenConUsr.setCampoBusqueda(tipo);
        objVenConUsr.cargarDatos();
        objVenConUsr.show();
        if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
           txtNumHij.setText(objVenConUsr.getValueAt(1));
           //txtusr.setText(objVenConUsr.getValueAt(2));
           strCodUsr=objVenConUsr.getValueAt(1);
           optFil.setSelected(true);
         }else{
           txtNumHij.setText(strCodUsr);
           //txtusr.setText(strUsr);

  }}}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEstCiv;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboSex;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodEstCiv;
    private javax.swing.JTextField txtDesCiu;
    private javax.swing.JTextField txtDesLarEstCiv;
    private javax.swing.JTextField txtNumHij;
    // End of variables declaration//GEN-END:variables




private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    
     int intCol=tblDat.columnAtPoint(evt.getPoint());
     String strMsg="";

    switch (intCol){

        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_CODEMP:
            strMsg="Código del empleado";
            break;
        case INT_TBL_IDEMP:
            strMsg="Identificación del empleado";
            break;
        case INT_TBL_EMPLEADO:
            strMsg="Apellidos del empleado";
            break;
        case INT_TBL_ESTADO:
        strMsg="Estado del empleado";
        break;
        case INT_TBL_DEPARTAMENTO:
            strMsg="Departamento";
            break;
        case INT_TBL_OFICINA:
            strMsg="Oficina";
            break;
        case INT_TBL_FECINICON:
            strMsg="Fecha de inicio de contrato";
            break;
        case INT_TBL_FECNACEMP:
            strMsg="Fecha de nacimiento";
            break;
        case INT_TBL_TEL1EMP:
            strMsg="Teléfono 1";
            break;
        case INT_TBL_TEL2EMP:
            strMsg="Teléfono 2";
            break;
        case INT_TBL_TEL3EMP:
            strMsg="Teléfono 3";
            break;
        case INT_TBL_EMAEMP:
            strMsg="Email";
            break;
        case INT_TBL_NUMHIJEMP:
            strMsg="Número de hijos";
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
                        
                        //Inicializar los parametros que se van a pasar al reporte.

                        if(!(txtCodCiu.getText().equals(""))){
                            sqlAux+=" AND a.co_ciunac="+txtCodCiu.getText()+" ";
                        }

                        if(!(txtCodEstCiv.getText().equals(""))){
                            sqlAux+=" AND a.co_estciv = "+txtCodEstCiv.getText()+" ";
                        }

                        if (cboSex.getSelectedIndex()>0)
                            sqlAux+=" AND a.tx_sex = '" + vecSex.get(cboSex.getSelectedIndex()) + "'";

                        if(!(txtNumHij.getText().equals(""))){
                            sqlAux+=" AND a.ne_numhij="+txtNumHij.getText()+" ";
                        }

                        //Obtener la fecha y hora del servidor.
                        datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                        if (datFecAux!=null){
                            strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                            datFecAux=null;
                        }
                        
                        
                        strRutRpt=objRptSis.getRutaReporte(i);
                        strNomRpt=objRptSis.getNombreReporte(i);
                        strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                                
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 415:
                            //default:

                                

                                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){

                                    strSql="SELECT a.co_tra, a.tx_ide, a.tx_nom, a.tx_ape, a.fe_nac, a.tx_tel1, a.tx_tel2, a.tx_tel3, a.tx_corele, a.ne_numhij, '" + objZafParSis.getNombreEmpresa() + "' as empresa, current_date as fecha, '" + strCamAudRpt + "' as strCamAudRpt "+
                                           " , null as co_emp FROM tbm_tra as a "+
                                           "WHERE a.st_reg like 'A' " + sqlAux +
                                           "order by co_tra";
                                }else{

                                    strSql="select c.*,d.tx_nom as empresa, current_date as fecha, '" + strCamAudRpt + "' as strCamAudRpt from (SELECT a.co_tra, a.tx_ide, a.tx_nom, a.tx_ape, a.fe_nac, a.tx_tel1, a.tx_tel2, a.tx_tel3, a.tx_corele, a.ne_numhij,b.co_emp FROM tbm_tra as a "+
                                               "inner join tbm_traemp b on (a.co_tra = b.co_tra) and b.co_emp = " + objZafParSis.getCodigoEmpresa() + " " +
                                               "WHERE a.st_reg like 'A' " + sqlAux + ") c " +
                                               //"WHERE a.st_reg='A' and a.st_reg not in ('E','I') " + sqlAux + ") c " +
                                               "inner join tbm_emp d on (c.co_emp = d.co_emp) "+
                                               "order by co_tra";

                                }
                                    
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("sql", strSql );
                                
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
                                case 416:

                                    /*strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";*/

                                    if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                                        
                                        strSql="select c.*,'prueba' as empresa, current_date as fecha, "+
                                               "'admin      20/mar/2012 12:51:39      RecursosHumanos.ZafRecHum07.ZafRecHum07      ZafRptRecHum07_01.jasper v0.1    ' as strCamAudRpt from ( "+
                                               "SELECT a.co_tra, a.tx_ide, a.tx_nom, a.tx_ape, a.fe_nac, a.tx_tel1, a.tx_tel2, a.tx_tel3, a.tx_corele, a.ne_numhij, null as co_emp FROM tbm_tra as a "+
                                               "WHERE a.st_reg='A' and a.st_reg not in ('E','I') ) c "+
                                               "order by co_tra";
                                    }else{

                                        strSql = "select c.co_tra,d.tx_nom as empresa, current_date as fecha, '" + strCamAudRpt + "' as strCamAudRpt from (SELECT a.co_tra,b.co_emp FROM tbm_tra as a "+
                                                 "inner join tbm_traemp b on (a.co_tra = b.co_tra) and b.co_emp = " + objZafParSis.getCodigoEmpresa() + " " +
                                                 "WHERE a.st_reg='A' and a.st_reg not in ('E','I') " + sqlAux + ")  c " +
                                                 "inner join tbm_emp d on (c.co_emp = d.co_emp) order by co_tra";

                                    }

                                    mapPar=new java.util.HashMap();
                                    mapPar.put("sql", strSql );
                                    mapPar.put("SUBREPORT_DIR", strRutRpt );
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
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