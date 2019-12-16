/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RecursosHumanos.ZafRecHum24;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenDep;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenEmp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.PreparedStatement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Roberto Flores
 * Guayaquil 05/03/2012
 */
public class ZafRecHum24 extends javax.swing.JInternalFrame {


  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
  private ZafUtil objUti;
  private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
  private ZafVenTra zafVenTra;                                            //Ventana de consulta de Empleados
  private ZafVenDep zafVenDep;                                            //Ventana de consulta de Departamentos
  private ZafVenEmp zafVenEmp;                                            //Ventana de consulta de Empresas
  private ZafThreadGUI objThrGUI;
  private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
  private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PeopuMenú en JTable.
  
  private static final int INT_TBL_LINEA = 0;                             //Índice de columna Linea.
  private static final int INT_TBL_COEMP = 1;
  private static final int INT_TBL_NOMEMP = 2;
  private static final int INT_TBL_CODTRA = 3;
  private static final int INT_TBL_NOMTRA = 4;
  private static final int INT_TBL_CODDEP = 5;
  private static final int INT_TBL_DESCORDEP = 6;
  private static final int INT_TBL_BUTDESDEP = 7;
  private static final int INT_TBL_DESLARDEP = 8;

  String strVersion=" v1.01 ";
  
  private Vector vecCab = new Vector();                                   //Vector que contiene la cabecera del Table.

  private int intCodEmp;                                                  //Código de la empresa.

  private boolean blnMod;                                                 //Indica si han habido cambios en el documento
  private boolean blnConsDat;
  
   private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;

    /** Creates new form ZafRecHum24 */
    public ZafRecHum24(Librerias.ZafParSis.ZafParSis obj) {

        try{

            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+"  v1.1 ");
            lblTit.setText(objZafParSis.getNombreMenu());
            
            

	    objUti = new ZafUtil();

	    this.objZafParSis = (ZafParSis) objZafParSis.clone();

            configuraTbl();

            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

            zafVenTra = new ZafVenTra(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados");
            zafVenDep = new ZafVenDep(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Departamentos");
            zafVenEmp = new ZafVenEmp(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empresas");

            blnMod = false;
            blnConsDat = false;

         }catch (Exception e){ e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); } 
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

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configuraTbl(){

        boolean blnRes=false;

           try{

               //Configurar JTable: Establecer el modelo.
               vecCab.clear();
               vecCab.add(INT_TBL_LINEA,"");
               vecCab.add(INT_TBL_COEMP,"Cód. Emp.");
               vecCab.add(INT_TBL_NOMEMP,"Empresa");
               vecCab.add(INT_TBL_CODTRA,"Cód. Emp.");
               vecCab.add(INT_TBL_NOMTRA,"Empleado");
               vecCab.add(INT_TBL_CODDEP,"Cód. Dep.");
               vecCab.add(INT_TBL_DESCORDEP,"Dep.");
               vecCab.add(INT_TBL_BUTDESDEP,"...");
               vecCab.add(INT_TBL_DESLARDEP,"Departamento");

               objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
               objTblMod.setHeader(vecCab);
               tblDat.setModel(objTblMod);

               //Configurar JTable: Establecer tipo de selección.
               tblDat.setRowSelectionAllowed(true);
               tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
               new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

               //Configurar JTable: Establecer el menú de contexto.
               objTblPopMnu=new ZafTblPopMnu(tblDat);
               
               //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
               tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
               javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

               //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
               objMouMotAda=new ZafMouMotAda();
               tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

               intCodEmp = objZafParSis.getCodigoEmpresa();

               //Configurar JTable: Ocultar columnas del sistema.
               objTblMod.addSystemHiddenColumn(INT_TBL_COEMP, tblDat);
               objTblMod.addSystemHiddenColumn(INT_TBL_CODDEP, tblDat);

               Vector vecAux=vecAux=new Vector();
               vecAux.add("" + INT_TBL_DESCORDEP);
               vecAux.add("" + INT_TBL_BUTDESDEP);
               objTblMod.setColumnasEditables(vecAux);
               vecAux=null;

              //Configurar JTable: Editor de la tabla.
              objTblEdi=new ZafTblEdi(tblDat);

              //Configurar JTable: Renderizar celdas.
              objTblCelRenLbl=new ZafTblCelRenLbl();
              objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);

              //Tamaño de las celdas
              tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
              tcmAux.getColumn(INT_TBL_COEMP).setPreferredWidth(25);
              tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(80);
              tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(40);
              tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(280);
              tcmAux.getColumn(INT_TBL_CODDEP).setPreferredWidth(25);
              tcmAux.getColumn(INT_TBL_DESCORDEP).setPreferredWidth(50);
              tcmAux.getColumn(INT_TBL_BUTDESDEP).setPreferredWidth(30);
              tcmAux.getColumn(INT_TBL_DESLARDEP).setPreferredWidth(150);

              new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
              tblDat.getTableHeader().setReorderingAllowed(false);

              Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
              tcmAux.getColumn(INT_TBL_BUTDESDEP).setCellRenderer(zafTblDocCelRenBut);
              ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTDESDEP, "Justificación") {
              public void butCLick() {
                  int intSelFil = tblDat.getSelectedRow();
                  String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DESCORDEP) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DESCORDEP).toString());

                  zafVenDep.limpiar();
                  zafVenDep.setCampoBusqueda(0);
                  zafVenDep.setVisible(true);
                  if (zafVenDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                      tblDat.setValueAt(zafVenDep.getValueAt(1),intSelFil, INT_TBL_CODDEP);
                      tblDat.setValueAt(zafVenDep.getValueAt(2),intSelFil, INT_TBL_DESCORDEP);
                      tblDat.setValueAt(zafVenDep.getValueAt(3),intSelFil, INT_TBL_DESLARDEP);
                  }
              }
              };

              Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
              Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
              objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
              objTblCelRenChk=null;
              objTblCelEdiChk=null;
              
              //Configurar JTable: Establecer el ordenamiento en la tabla.
                objTblOrd=new ZafTblOrd(tblDat);
              
              //Configurar JTable: Editor de búsqueda.
              objTblBus=new ZafTblBus(tblDat);
              
              objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
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
        txtDesCorDep2 = new javax.swing.JTextField();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panCab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtCodDep = new javax.swing.JTextField();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        butDep = new javax.swing.JButton();
        butEmp = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        butDep1 = new javax.swing.JButton();
        butDep2 = new javax.swing.JButton();
        txtCodDep2 = new javax.swing.JTextField();
        txtNomDep2 = new javax.swing.JTextField();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        txtDesCorDep2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesCorDep2.setEnabled(false);
        txtDesCorDep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorDep2ActionPerformed(evt);
            }
        });
        txtDesCorDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorDep2FocusLost(evt);
            }
        });

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
        optFil.setBounds(10, 50, 370, 20);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panCab.add(jLabel4);
        jLabel4.setBounds(40, 80, 100, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panCab.add(jLabel5);
        jLabel5.setBounds(40, 100, 100, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panCab.add(jLabel6);
        jLabel6.setBounds(40, 120, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.setEnabled(false);
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
        panCab.add(txtCodTra);
        txtCodTra.setBounds(140, 120, 60, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.setEnabled(false);
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
        panCab.add(txtCodDep);
        txtCodDep.setBounds(140, 100, 60, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.setEnabled(false);
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
        panCab.add(txtCodEmp);
        txtCodEmp.setBounds(140, 80, 60, 20);

        txtNomEmp.setEditable(false);
        txtNomEmp.setEnabled(false);
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
        panCab.add(txtNomEmp);
        txtNomEmp.setBounds(200, 80, 250, 20);

        txtNomDep.setEditable(false);
        txtNomDep.setEnabled(false);
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
        panCab.add(txtNomDep);
        txtNomDep.setBounds(200, 100, 250, 20);

        txtNomTra.setEditable(false);
        txtNomTra.setEnabled(false);
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
        panCab.add(txtNomTra);
        txtNomTra.setBounds(200, 120, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.setEnabled(false);
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panCab.add(butTra);
        butTra.setBounds(450, 120, 20, 20);

        butDep.setText(".."); // NOI18N
        butDep.setEnabled(false);
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panCab.add(butDep);
        butDep.setBounds(450, 100, 20, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.setEnabled(false);
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panCab.add(butEmp);
        butEmp.setBounds(450, 80, 20, 20);

        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 160);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(560, 30));
        jPanel2.setPreferredSize(new java.awt.Dimension(560, 40));
        jPanel2.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Departamento:"); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 10, 100, 20);

        butDep1.setText("Asignar"); // NOI18N
        butDep1.setEnabled(false);
        butDep1.setPreferredSize(new java.awt.Dimension(20, 25));
        butDep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDep1ActionPerformed(evt);
            }
        });
        jPanel2.add(butDep1);
        butDep1.setBounds(460, 10, 100, 20);

        butDep2.setText(".."); // NOI18N
        butDep2.setEnabled(false);
        butDep2.setPreferredSize(new java.awt.Dimension(20, 25));
        butDep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDep2ActionPerformed(evt);
            }
        });
        jPanel2.add(butDep2);
        butDep2.setBounds(440, 10, 20, 20);

        txtCodDep2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep2.setEnabled(false);
        txtCodDep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDep2ActionPerformed(evt);
            }
        });
        txtCodDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDep2FocusLost(evt);
            }
        });
        jPanel2.add(txtCodDep2);
        txtCodDep2.setBounds(110, 10, 60, 20);

        txtNomDep2.setEditable(false);
        txtNomDep2.setEnabled(false);
        txtNomDep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDep2ActionPerformed(evt);
            }
        });
        txtNomDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDep2FocusLost(evt);
            }
        });
        jPanel2.add(txtNomDep2);
        txtNomDep2.setBounds(170, 10, 270, 19);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

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
        scrTbl.setViewportView(tblDat);

        jPanel1.add(scrTbl, java.awt.BorderLayout.CENTER);

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

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        consultarRepEmp();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    public void consultarRepTra(){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                if(conn!=null && txtCodTra.getText().compareTo("")!=0){

                    stmLoc=conn.createStatement();

                    strSql="SELECT tx_ape , tx_nom from tbm_tra where co_tra =  " + txtCodTra.getText() + " and st_reg like 'A'";
                    rstLoc=stmLoc.executeQuery(strSql);

                    if(rstLoc.next()){
                        txtNomTra.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                        txtNomTra.setHorizontalAlignment(2);
                        txtNomTra.setEnabled(false);
                    }else{
                        mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                        txtNomTra.setText("");
                        txtCodTra.setText("");
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

    public void consultarRepEmp(){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                if(conn!=null && txtCodEmp.getText().compareTo("")!=0){

                    stmLoc=conn.createStatement();

                    strSql="SELECT co_emp , tx_nom from tbm_emp where co_emp =  " + txtCodEmp.getText() + " and st_reg like 'A' and co_emp not in (0) ";
                    rstLoc=stmLoc.executeQuery(strSql);

                    if(rstLoc.next()){
                        txtNomEmp.setText(rstLoc.getString("tx_nom"));
                        txtNomEmp.setHorizontalAlignment(2);
                        txtNomEmp.setEnabled(false);
                    }else{
                        mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                        txtNomEmp.setText("");
                        txtCodEmp.setText("");
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

    public void consultarRepDep(int op){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                
                
                if(conn!=null && (txtCodDep2.getText().compareTo("")!=0 || txtCodDep.getText().compareTo("")!=0)){

                    stmLoc=conn.createStatement();

                    switch(op){
                        case 1:
                            strSql="SELECT co_dep,tx_deslar,tx_descor from tbm_dep where co_dep =  " + txtCodDep.getText() + "and st_reg like 'A'";
                            break;
                        case 2:
                            strSql="SELECT co_dep,tx_deslar,tx_descor from tbm_dep where co_dep =  " + txtCodDep2.getText();
                            break;
                    }

                    rstLoc=stmLoc.executeQuery(strSql);

                    if(rstLoc.next()){
                        switch(op){
                            case 1:
                                txtNomDep.setText(rstLoc.getString("tx_deslar"));
                                txtNomDep.setHorizontalAlignment(2);
                                txtNomDep.setEnabled(false);
                                break;
                            case 2:
                                txtNomDep2.setText(rstLoc.getString("tx_deslar"));
                                txtDesCorDep2.setText(rstLoc.getString("tx_descor"));
                                txtNomDep2.setHorizontalAlignment(2);
                                txtNomDep2.setEnabled(false);
                                break;
                        }
                        
                    }else{
                        mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                        switch(op){
                            case 1:
                                txtNomDep.setText("");
                                txtCodDep.setText("");
                            case 2:
                                txtNomDep2.setText("");
                                txtCodDep2.setText("");
                                txtDesCorDep2.setText("");
                        }
                        
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

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed

        try{
            zafVenEmp.limpiar();
            zafVenEmp.setCampoBusqueda(0);
            zafVenEmp.setVisible(true);

            if (zafVenEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                txtCodEmp.setText(String.valueOf(zafVenEmp.getValueAt(1)));
                txtNomEmp.setText(String.valueOf(zafVenEmp.getValueAt(2)));
            }
        }catch (Exception ex){
            objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_butEmpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
         //exitForm();
        if(blnMod){
            String strMsg = "Existen cambios sin guardar! \nEstá Seguro que desea cerrar este programa?";
            String strTit = "Mensaje del sistema Zafiro";
            boolean blnOk = (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
            if(blnOk){
                dispose();
            }
        }else{
             String strTit, strMsg;
             strTit="Mensaje del sistema Zafiro";
             strMsg="¿Está seguro que desea cerrar este programa?";
             if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
             {
                 dispose();
             }
         }
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed

        guardarDat();

        butConActionPerformed(evt);
    }//GEN-LAST:event_butGuaActionPerformed


private boolean guardarDat(){
    boolean blnRes=false;
    java.sql.Connection conn;
    PreparedStatement stmLoc = null;
    String strSql="";
    boolean blnMReg = false;
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
       conn.setAutoCommit(false);

      for(int i=0; i<tblDat.getRowCount(); i++){

          if(tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M")==0){

              blnMReg = true;

              String strCoDep = tblDat.getValueAt(i,INT_TBL_CODDEP).toString();
              if(strCoDep.compareTo("")==0){
                  strCoDep = "null";
              }
              
              strSql= "UPDATE tbm_traemp SET co_dep = " + strCoDep+ " "+
                                "WHERE co_tra = "+tblDat.getValueAt(i, INT_TBL_CODTRA)+ " "+
                                "and co_emp = "+tblDat.getValueAt(i, INT_TBL_COEMP);

              stmLoc = conn.prepareStatement(strSql);
              stmLoc.executeUpdate();
          }
      }

       if(stmLoc!=null){
           stmLoc.close();
           stmLoc=null;
       }

       conn.commit();
       conn.close();
       conn=null;
       blnRes=true;
       blnMod = false;

  }}catch(java.sql.SQLException Evt) {
            //try {
                //conn.rollback();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                Evt.printStackTrace();
            //} catch (SQLException ex) {
                //Logger.getLogger(ZafRecHum19.class.getName()).log(Level.SEVERE, null, ex);
            //}
  }catch(Exception Evt) {
            //try {
                //conn.rollback();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
                Evt.printStackTrace();
            //} catch (SQLException ex) {
                //Logger.getLogger(ZafRecHum19.class.getName()).log(Level.SEVERE, null, ex);
            //}
 }


 if(blnMReg){
     if(blnRes){
         mostrarMsgInf("La operación GUARDAR se realizó con éxito");
     }
 }else{
        mostrarMsgInf("No se han realizado cambios para que sean guardados");
        //butGua.setEnabled(false);
 }

 return blnRes;
}

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if(optFil.isSelected()){
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                txtCodEmp.setEnabled(true);
                butEmp.setEnabled(true);
                txtCodEmp.requestFocus();
            }else{
                txtCodEmp.setEnabled(false);
                butEmp.setEnabled(false);
                txtCodDep.requestFocus();
            }
            txtCodDep.setEnabled(true);
            txtCodTra.setEnabled(true);
            butDep.setEnabled(true);
            butTra.setEnabled(true);
            
        }
}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed

        if(optTod.isSelected()){
            txtCodEmp.setEnabled(false);
            butEmp.setEnabled(false);
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setEnabled(false);
            butDep.setEnabled(false);
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setEnabled(false);
            butTra.setEnabled(false);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        consultarRepDep(1);
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDepFocusLost

    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        try{
            zafVenDep.limpiar();
            zafVenDep.setCampoBusqueda(0);
            zafVenDep.setVisible(true);

            if (zafVenDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                txtCodDep.setText(String.valueOf(zafVenDep.getValueAt(1)));
                txtNomDep.setText(String.valueOf(zafVenDep.getValueAt(3)));
            }
        }catch (Exception ex){
            objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        consultarRepTra();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        try{
            zafVenTra.limpiar();
            zafVenTra.setCampoBusqueda(0);
            zafVenTra.setVisible(true);

            if (zafVenTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                txtCodDep2.setText(String.valueOf(zafVenTra.getValueAt(1)));
                txtNomTra.setText(String.valueOf(zafVenTra.getValueAt(3) + " " + zafVenTra.getValueAt(4)));
            }
        }catch (Exception ex){
            objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_butTraActionPerformed

    private void butDep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDep1ActionPerformed
        
        //int[] filasSelec = jTable1.getSelectedRows();
        int[] intSel = tblDat.getSelectedRows();
        System.out.println();
        if(txtCodDep2.getText().compareTo("")==0){
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Antes de asignar debe de seleccionar un departamento";
            JOptionPane.showMessageDialog(ZafRecHum24.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }else{

            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "¿Está seguro que desea asignar el departamento a todos los empleados seleccionados?";
            //JOptionPane.showMessageDialog(ZafRecHum24.this,strMen,strTit,JOptionPane.YES_NO_CANCEL_OPTION);

            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            if (oppMsg.showConfirmDialog(this,strMen,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                for(int i = 0; i < intSel.length; i++){
                tblDat.setValueAt(txtCodDep2.getText(), intSel[i], INT_TBL_CODDEP);
                tblDat.setValueAt(txtDesCorDep2.getText(), intSel[i], INT_TBL_DESCORDEP);
                tblDat.setValueAt(txtNomDep2.getText(), intSel[i], INT_TBL_DESLARDEP);
                }
            }
        }
    }//GEN-LAST:event_butDep1ActionPerformed

    private void butDep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDep2ActionPerformed
        try{
            zafVenDep.limpiar();
            zafVenDep.setCampoBusqueda(0);
            zafVenDep.setVisible(true);

            if (zafVenDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                txtCodDep2.setText(String.valueOf(zafVenDep.getValueAt(1)));
                txtDesCorDep2.setText(String.valueOf(zafVenDep.getValueAt(2)));
                txtNomDep2.setText(String.valueOf(zafVenDep.getValueAt(3)));
            }
        }catch (Exception ex){
            objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_butDep2ActionPerformed

    private void txtCodDep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDep2ActionPerformed
        consultarRepDep(2);
    }//GEN-LAST:event_txtCodDep2ActionPerformed

    private void txtCodDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDep2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDep2FocusGained

    private void txtCodDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDep2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDep2FocusLost

    private void txtDesCorDep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorDep2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorDep2ActionPerformed

    private void txtDesCorDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorDep2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorDep2FocusGained

    private void txtDesCorDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorDep2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorDep2FocusLost

    private void txtNomDep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDep2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomDep2ActionPerformed

    private void txtNomDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDep2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomDep2FocusGained

    private void txtNomDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDep2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomDep2FocusLost

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

    java.sql.Connection conn;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="",sqlFil="";
    Vector vecData;
    Vector vecDataCon;

    try{

        conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

        if(conn!=null){

            stmLoc=conn.createStatement();
            vecData = new Vector();
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
            
            /*if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSql = " select * from tbm_tra a " +
                         "left join tbm_dep b on a.co_dep=b.co_dep "+
                         "left join tbm_traemp c on a.co_tra = c.co_tra " +
                         " where a.st_reg like 'A' " + sqlFilEmp;
            }else{
                strSql = " select * from tbm_tra a " +
                         "left join tbm_dep b on a.co_dep=b.co_dep "+
                         "inner join tbm_traemp c on a.co_tra = c.co_tra " +
                         " where a.st_reg like 'A' " + sqlFilEmp+ " AND c.co_emp = " + objZafParSis.getCodigoEmpresa();
            }

            strSql+= " order by a.co_tra";*/
            
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSql = " select d.co_emp, d.tx_nom as nomemp, a.co_tra, a.tx_nom, a.tx_ape, b.co_dep, b.co_jef, c.tx_descor, c.tx_deslar, b.co_emp , (select (tx_nom || ' ' || tx_ape) from tbm_tra "+
                        " where co_tra = b.co_jef) as nomcomjef from tbm_tra a "+
                        " left join tbm_traemp b on a.co_tra = b.co_tra "+
                        "left join tbm_dep c on c.co_dep=b.co_dep "+
                        "inner join tbm_emp d on (d.co_emp=b.co_emp)  "+
                        "where b.st_reg like 'A'"
                        
                        + sqlFilEmp;
            }else{
                strSql = " select d.co_emp, d.tx_nom as nomemp, a.co_tra, a.tx_nom, a.tx_ape, b.co_dep, b.co_jef, c.tx_descor, c.tx_deslar,b.co_emp , (select (tx_nom || ' ' || tx_ape) from tbm_tra "+
                        " where co_tra = b.co_jef) as nomcomjef from tbm_tra a "+
                        " left join tbm_traemp b on a.co_tra = b.co_tra "+
                        "left join tbm_dep c on c.co_dep=b.co_dep "+
                        "inner join tbm_emp d on (d.co_emp=b.co_emp)  "+
                        "where b.st_reg like 'A'"
                        
                        + sqlFilEmp+ " AND b.co_emp = " + objZafParSis.getCodigoEmpresa();
            }

            //strSql+= " order by a.co_tra";
            strSql+= " order by (a.tx_ape ||  ' '  || a.tx_nom)";
            
            
            
            System.out.println("query ejecutado: "+strSql);
            rstLoc=stmLoc.executeQuery(strSql);

            int intPos1 = 0;

            while(rstLoc.next()){

                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA,"");
                vecReg.add(INT_TBL_COEMP, rstLoc.getString("co_emp") );
                vecReg.add(INT_TBL_NOMEMP, rstLoc.getString("nomemp") );
                vecReg.add(INT_TBL_CODTRA, rstLoc.getString("co_tra") );
                vecReg.add(INT_TBL_NOMTRA,rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                vecReg.add(INT_TBL_CODDEP,rstLoc.getString("co_dep"));
                vecReg.add(INT_TBL_DESCORDEP,rstLoc.getString("tx_descor"));
                vecReg.add(INT_TBL_BUTDESDEP,"...");
                vecReg.add(INT_TBL_DESLARDEP,rstLoc.getString("tx_deslar"));

                vecDataCon.add(vecReg);
                intPos1++;
            }

            if(vecDataCon.size()>0){
                txtCodDep2.setEnabled(true);
                butDep2.setEnabled(true);
                butDep1.setEnabled(true);
            }

            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;
            conn.close();
            conn=null;
            lblMsgSis.setText("Listo");
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
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butDep1;
    private javax.swing.JButton butDep2;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodDep2;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDesCorDep2;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomDep2;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables


private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
    public void mouseMoved(java.awt.event.MouseEvent evt){

        int intCol=tblDat.columnAtPoint(evt.getPoint());
        String strMsg="";

        switch (intCol){

            case INT_TBL_LINEA:
                strMsg="";
                break;
            case INT_TBL_NOMEMP:    
                strMsg="Nombre de la empresa";
                break;
            case INT_TBL_CODTRA:
                strMsg="Código del empleado";
                break;
            case INT_TBL_NOMTRA:
                strMsg="Nombres y Apellidos del empleado";
                break;
            case INT_TBL_CODDEP:
                strMsg="Código del departamento";
                break;
            case INT_TBL_DESCORDEP:
                strMsg="Descripción corta del departamento";
                break;
            case INT_TBL_BUTDESDEP:
                strMsg="";
                break;
            case INT_TBL_DESLARDEP:
                strMsg="Descripción larga del departamento";
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
}