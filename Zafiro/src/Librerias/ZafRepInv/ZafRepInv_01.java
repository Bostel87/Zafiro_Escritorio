/** 
 * ZafRepInv_01.java
 *
 * Created on 28 de junio de 2005, 16:01
   */

package Librerias.ZafRepInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafRepInv.ZafRepInv_02;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/** 
 *
 * @author  jsalazar
   */
public class ZafRepInv_01 extends javax.swing.JDialog {
   private ZafParSis objZafParSis;
   private ZafUtil objUti;   
   private ZafRepInv_01 objRepDlg;
   public  ZafRepInv_02 objRepData;
   private Connection con,conAux;
   private Statement stm,stmAux;
   private ResultSet rst,rstAux;   
   private ZafColNumerada objColNum;
   private ZafPopupMenu objPopMnu;
   private ZafTableModel objTblMod;
   private ZafThreadGUI objThrGUI;
   private ZafMouMotAda objMouMotAda;
   private boolean blnViveHiloCon = false;
   private Vector vecRegSel,vecRepDat=null;
   
   
   String strAux,strBusqueda;
   String strSQL;
   Vector vecCab,vecDat,vecReg;
   Vector vecConfDoc;    
   boolean blnCon;
   String strDesItm;
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                //Línea en blanco
    final int INT_TBL_DAT_COD_EMP=1;            //Codigo Empresa.   
    final int INT_TBL_DAT_NOM_EMP=2;            //Descripcion Empresa.
    final int INT_TBL_DAT_COD_LOC=3;            //Codigo de local  
    final int INT_TBL_DAT_COD_BOD=4;            //Codigo de Bodega.        
    final int INT_TBL_DAT_NOM_BOD=5;            //Nombre de Bodega.
    final int INT_TBL_DAT_COD_ITM=6;            //Codigo de Item.
    final int INT_TBL_DAT_DSC_STK=7;            //Stock Actual.
    final int INT_TBL_DAT_DSC_CAN=8;            //Unidad de medida.    
    final int INT_TBL_DAT_DSC_PRE=9;            //Precio del articulo (incluye calculo de margen de utilidad)
    final int INT_TBL_DAT_DSC_TOT=10;            //Total por bodega empresa (precio * cantidad)
    final int STK_ACT=0;  
    // Constantes del Vector de Configuracion de documentos
    final int INT_CONF_REP_NEW = 0;
    final int INT_CONF_CAL_COS = 1;
    final int INT_CONF_GRA_ORD = 2;
    final int INT_CONF_GRA_FAC = 3;
    final int INT_CONF_GRA_TEM = 4;
    final int INT_CONF_PER_MOD = 5;
    final int INT_CONF_VER_DLG = 6;    

    // Constantes del vector de reposicion
    final int INT_DAT_COD_BOD = 0;
    final int INT_DAT_COD_ITM = 1;
    final int INT_DAT_STK_CAN = 2;
    final int INT_DAT_NUM_REG = 3;

    
    /**  Creates new form ZafRepInv_01   */
    /**  Construtor parametrizado 
     * Utilizo par indicando que es una variable del parametro.
     * Se recibe el objeto ZafParSis,  el codigo de la bodega en el local,
     * el codigo del item, y la cantidad solicitada.
       */

   public ZafRepInv_01(java.awt.Frame parent, ZafParSis parobjZafParSis , Vector vecparConfDoc, Vector vecparRegSel, Vector vecparData) {
        super(parent, true);
        initComponents();
        objUti = new ZafUtil();
        objZafParSis = parobjZafParSis;        
        objRepDlg = this;
        if (vecparConfDoc!=null)vecConfDoc = vecparConfDoc;        
        else exitForm();
        vecRegSel  = vecparRegSel;        
        vecRepDat  = vecparData;
        if (!configurarDialog())
            exitForm();    
    }

    private boolean configurarDialog()
    {        
        boolean blnRes=true;
        try{                        
            strAux=objZafParSis.getNombreMenu();
            this.setTitle(strAux);
            this.setResizable(false);
            txtCodItm.setText(ObtenerDescripItem("tx_codalt"));
            txtDesItm.setText(ObtenerDescripItem("tx_nomitm"));
            txtCodItm.setBackground(objZafParSis.getColorCamposSistema());
            txtDesItm.setBackground(objZafParSis.getColorCamposSistema());            
            txtCodItm.enable(false);
            txtDesItm.enable(false);
            txtCanSol.setText(""+ Double.parseDouble(vecRegSel.get(INT_DAT_STK_CAN).toString()));
            txtCanSol.enable(false);                       
            txtCanEmp.setText(""+getCanBodEmp());
            txtCanEmp.enable(false);            
            txtCanSal.setText(""+getCanSolicitadaPendiente());
            txtCanSal.setBackground(objZafParSis.getColorCamposSistema());
            txtCanSal.enable(false);
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cod.Loc");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Cod.Bod");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Item");
            vecCab.add(INT_TBL_DAT_DSC_STK,"Stock");
            vecCab.add(INT_TBL_DAT_DSC_CAN,"Cantidad");            
            vecCab.add(INT_TBL_DAT_DSC_PRE,"Precio U.");
            vecCab.add(INT_TBL_DAT_DSC_TOT,"Subtotal");            
            
            objTblMod=new ZafTableModel();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);         
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);          
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(125);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(160);            
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_STK).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_CAN).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_PRE).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_TOT).setPreferredWidth(70);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_CAN).setCellEditor(new EditorCol_SolCan());   
            //tblFacDet.getColumnModel().getColumn(INT_TBL_DAT_DSC_CAN).setCellEditor(new EditorCol_Double());
            //Configurar JTable: Ocultar columnas del sistema.
            //local proveedor
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);                         
            //bodega proveedor
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BOD).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BOD).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BOD).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BOD).setResizable(false);                         
            // cod_item
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);                         
            
            if (!(Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue()))
            {                 
                //oculto columna precio
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_PRE).setWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_PRE).setMaxWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_PRE).setMinWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_PRE).setPreferredWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_PRE).setResizable(false);           
                // oculto columna subtotal
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_TOT).setWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_TOT).setMaxWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_TOT).setMinWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_TOT).setPreferredWidth(0);
                tblDat.getColumnModel().getColumn(INT_TBL_DAT_DSC_TOT).setResizable(false);                           
            }else{
                this.setSize(this.getSize().width + 140 , this.getSize().height);                
                spnTab.setSize(spnTab.getSize().width + 130, spnTab.getSize().height);
                tblDat.setSize(tblDat.getSize().width + 140 , tblDat.getSize().height); 
                
            }
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Centrar columnas.
            //GeneraTablaRep();
            blnCon=true;
            if(!cargarBodegaReposicion()) {
                vecRepDat = null;
                blnRes=false;    
            }
            if (Double.parseDouble(txtCanSal.getText())==0) butGua.setEnabled(true);
            else butGua.setEnabled(false);            
            if (!(Boolean.valueOf(vecConfDoc.get(INT_CONF_PER_MOD).toString()).booleanValue()))  {
               butCon.setEnabled(false);
               butGua.setEnabled(false);
            }            
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;

    }    
/**  Cerrar la aplicación.   */
    private void exitForm() 
    {
        dispose();
    }    
      
    /**  This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        txtCanSal = new javax.swing.JTextField();
        spnTab = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        lblTit = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        lblRes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtDesItm = new javax.swing.JTextField();
        lblEmp1 = new javax.swing.JLabel();
        txtCanSol = new javax.swing.JTextField();
        txtCanEmp = new javax.swing.JTextField();
        lblItm1 = new javax.swing.JLabel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        txtCanSal.setFont(new java.awt.Font("MS Sans Serif", 0, 12));
        getContentPane().add(txtCanSal);
        txtCanSal.setBounds(460, 300, 70, 20);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Línea", "Cod.Emp", "NomEmp", "Bodega", "Stock", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnTab.setViewportView(tblDat);

        getContentPane().add(spnTab);
        spnTab.setBounds(10, 110, 530, 180);

        lblTit.setFont(new java.awt.Font("Verdana", 1, 14));
        lblTit.setText("BackOrder de inventario intercompa\u00f1ias");
        getContentPane().add(lblTit);
        lblTit.setBounds(130, 0, 320, 15);

        jPanel4.setLayout(null);

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel4.add(lblMsgSis);
        lblMsgSis.setBounds(0, 10, 390, 20);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 370, 340, 30);

        jPanel6.setLayout(null);

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setStringPainted(true);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis);
        pgrSis.setBounds(10, 4, 180, 10);

        getContentPane().add(jPanel6);
        jPanel6.setBounds(350, 380, 200, 20);

        lblRes.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblRes.setText("Saldo:");
        getContentPane().add(lblRes);
        lblRes.setBounds(410, 303, 40, 15);

        jPanel1.setLayout(null);

        jPanel1.setBorder(new javax.swing.border.TitledBorder("Inventario Solicitado"));
        jLabel1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        jLabel1.setText("Articulo:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 42, 50, 15);

        jPanel1.add(txtCodItm);
        txtCodItm.setBounds(60, 40, 70, 21);

        jPanel1.add(txtDesItm);
        txtDesItm.setBounds(132, 40, 180, 21);

        lblEmp1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblEmp1.setText("Cantidad Solicitada:");
        jPanel1.add(lblEmp1);
        lblEmp1.setBounds(335, 22, 110, 15);

        txtCanSol.setFont(new java.awt.Font("MS Sans Serif", 0, 12));
        jPanel1.add(txtCanSol);
        txtCanSol.setBounds(450, 20, 70, 21);

        txtCanEmp.setFont(new java.awt.Font("MS Sans Serif", 0, 12));
        jPanel1.add(txtCanEmp);
        txtCanEmp.setBounds(450, 50, 70, 21);

        lblItm1.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblItm1.setText("Bodega Local:");
        jPanel1.add(lblItm1);
        lblItm1.setBounds(335, 52, 90, 15);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 20, 530, 80);

        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });

        getContentPane().add(butCon);
        butCon.setBounds(230, 340, 90, 25);

        butGua.setText("Aceptar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });

        getContentPane().add(butGua);
        butGua.setBounds(350, 340, 80, 25);

        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        getContentPane().add(butCer);
        butCer.setBounds(460, 340, 73, 25);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-562)/2, (screenSize.height-430)/2, 562, 430);
    }//GEN-END:initComponents
    public Vector getData()
    {
       return vecRepDat;
    }
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        try{
            lblMsgSis.setText("Guardado Listo...");
            pgrSis.setMinimum(0);
            pgrSis.setMaximum(tblDat.getRowCount());
            pgrSis.setValue(0);                            
            GuardaTablaVector();
            lblMsgSis.setText("Listo...");
            butGua.setEnabled(false);
        }catch(Exception e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
    }//GEN-LAST:event_butGuaActionPerformed
    public void GuardaTablaVector()
    {
        System.out.println("Guardando vector...");
        try{            

            Vector vecRepTmp = vecRepDat;
            vecRepDat = new Vector();
            for (int j=0; j<tblDat.getRowCount();j++)
            {
                if (Double.parseDouble(tblDat.getValueAt(j,INT_TBL_DAT_DSC_CAN).toString())>0)
                {
                    ZafRepInv_02 objRepDatTmp = new ZafRepInv_02();
                    //El numero de registro se carga en el momento de almacenar
                    //objRepDatTmp.setOriCodReg(Integer.parseInt(vecRegSel.get(INT_DAT_NUM_REG).toString())); 

                    if (vecRepTmp!=null){
                        for (int k=0;k<vecRepTmp.size();k++)
                        {
                            ZafRepInv_02 objTmp = (ZafRepInv_02)vecRepTmp.get(k);
                            if (Integer.parseInt(tblDat.getValueAt(j,INT_TBL_DAT_COD_EMP).toString()) == objTmp.getDatCodEmp() && Integer.parseInt(tblDat.getValueAt(j,INT_TBL_DAT_COD_LOC).toString())== objTmp.getDatCodLoc())
                            {
                                objRepDatTmp.setDatCodCot(objTmp.getDatCodCot());
                                objRepDatTmp.setDatCotCom(objTmp.getDatCotCom());
                            }
                        }
                    }
                    objRepDatTmp.setOriCodItm(Integer.parseInt(vecRegSel.get(INT_DAT_COD_ITM).toString()));
                    objRepDatTmp.setOriCodBod(Integer.parseInt(vecRegSel.get(INT_DAT_COD_BOD).toString()));
                    objRepDatTmp.setOriStkCan(Double.parseDouble(vecRegSel.get(INT_DAT_STK_CAN).toString()));
                    objRepDatTmp.setDatCodEmp(Integer.parseInt(tblDat.getValueAt(j,INT_TBL_DAT_COD_EMP).toString()));
		    objRepDatTmp.setDatCodLoc(Integer.parseInt(tblDat.getValueAt(j,INT_TBL_DAT_COD_LOC).toString()));
                    objRepDatTmp.setDatCodBod(Integer.parseInt(tblDat.getValueAt(j,INT_TBL_DAT_COD_BOD).toString()));
                    objRepDatTmp.setDatCodItm(Integer.parseInt(tblDat.getValueAt(j,INT_TBL_DAT_COD_ITM).toString()));
                    objRepDatTmp.setDatStkCan(Double.parseDouble(tblDat.getValueAt(j,INT_TBL_DAT_DSC_CAN).toString()));
                    objRepDatTmp.setDatDscPre(Double.parseDouble(tblDat.getValueAt(j,INT_TBL_DAT_DSC_PRE).toString()));
                    vecRepDat.add(objRepDatTmp);
                }
                pgrSis.setValue(j+1);                
            }   
        }catch(Exception e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }           
    }
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void GeneraTablaRep()
    {
        blnCon=true;
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();                
                
            }            
        }
        else
        {
            blnCon=false;
        }        
    }//GEN-LAST:event_butConActionPerformed
    
    /** 
     * @param args the command line arguments
       */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblEmp1;
    private javax.swing.JLabel lblItm1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblRes;
    private javax.swing.JLabel lblTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnTab;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCanEmp;
    private javax.swing.JTextField txtCanSal;
    private javax.swing.JTextField txtCanSol;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDesItm;
    // End of variables declaration//GEN-END:variables

    /** 
     * Metodo para para consultar el codigo alterno o descripcion de la tabla inventario
       */
    private String ObtenerDescripItem(String strparcam)
    {
        String strDatItem="";
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                
                stm=con.createStatement();
                strSQL ="";
                strSQL =" select " + strparcam + " from tbm_inv where co_emp = " + objZafParSis.getCodigoEmpresa() + "and co_itm = " + Integer.parseInt(vecRegSel.get(INT_DAT_COD_ITM).toString()) ;
                rst=stm.executeQuery(strSQL);
                while (rst.next()){
                    strDatItem =rst.getString(strparcam);
                }
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }

      return strDatItem;
    }
    
    /** 
     * Este metodo devuleve el codigo del item equivalente para las empresas proveedoras.
     *
       */
    private int ObtenerMaeEqui()
    {
        int intItem=0;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                
                stm=con.createStatement();
                strSQL ="";
                strSQL =" select co_itmmae from tbm_equinv where co_emp = " + objZafParSis.getCodigoEmpresa() + "and co_itm = " + Integer.parseInt(vecRegSel.get(INT_DAT_COD_ITM).toString()) ;
                System.out.println ("SQL_01:" + strSQL);
                rst=stm.executeQuery(strSQL);
                while (rst.next()){
                    intItem = Integer.parseInt(rst.getString("co_itmmae"));
                }
                if (intItem == 0) MensajeError("Sistema Zafiro.- Item Equivalente","No existe item equivalente.");
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }

      return intItem;
    }
    
    /**  
     * Metodo que carga los valores a la tabla de datos con las existencias, precios y subtotales a asignar
     * Este metodo aplica a documentos (cotizacion o facturas ) nuevas. No para visualizar ni modificar.
     *
       */
    public boolean cargarBodegaReposicion()
    {
        int intNumTotReg=0, i;        
        boolean blnRes  = true;
        boolean blnFind = false;
        try
        {
            
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            //intCodEmp=objZafParSis.getCodigoEmpresa(); pendiente           
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                
                stm=con.createStatement();
                if (!ExisteBodegaReposicion()){                    
                    MensajeError("Sistema Zafiro.- Reposicion","No existen bodegas asociadas para reposicion con " + objZafParSis.getNombreEmpresa());
                    return false;
                }
                /***Armar Query consulta***/
                int intItmmae = ObtenerMaeEqui();
                if (intItmmae>0)
                {
                    strSQL="";
                    strSQL=" select count(*) as num from tbm_equinv where co_itmmae = " + intItmmae;
                    rst=stm.executeQuery(strSQL);
                    while (rst.next()){
                        intNumTotReg = Integer.parseInt(rst.getString("num"));
                    }
                }else{
                    System.out.println("No existe item equivalente.");
                    return false;
                }    
                strSQL ="";                               
        	strSQL =" select cabpol.co_emppro as co_emp, emp.tx_nom  as tx_nomemp,  cabpol.co_locpro, loc.tx_nom as tx_nomloc, ";
                strSQL+=" co_bodpro as co_bod, bod.tx_nom as tx_nombod,invbod.co_itm, invbod.nd_stkact , ne_pri , nd_maruti, tx_apl  ";
                strSQL+=" from tbm_emp as emp  left outer join tbm_cabpolrep as cabpol on (emp.co_emp=cabpol.co_emppro ) ";
            	strSQL+=" left outer join tbm_detpolrep  as pol on (cabpol.co_empcli=pol.co_empcli and cabpol.co_loccli = pol.co_loccli and cabpol.co_emppro=pol.co_emppro and cabpol.co_locpro = pol.co_locpro )  ";
	        strSQL+=" left outer join tbm_loc as loc on ( pol.co_emppro = loc.co_emp and  pol.co_locpro = loc.co_loc) ";
        	strSQL+=" left outer join tbm_bod as bod on (loc.co_emp = bod.co_emp and pol.co_bodpro = bod.co_bod)  ";
	        strSQL+=" left outer join tbr_bodloc as bodloc on (bod.co_emp=bodloc.co_emp and  loc.co_loc = bodloc.co_loc and  bod.co_bod  = bodloc.co_bod) ";
        	strSQL+=" left outer join tbm_invbod  as invbod on ( bodloc.co_emp = invbod.co_emp   and bodloc.co_bod = invbod.co_bod)    ";                          
		strSQL+=" left outer join tbm_inv as inv on (invbod.co_emp = inv.co_emp and invbod.co_itm = inv.co_itm ) ";
                strSQL+=" left outer join tbm_equinv as equ on (inv.co_emp = equ.co_emp and inv.co_itm=equ.co_itm ) ";
                strSQL+=" where cabpol.co_empcli =" + objZafParSis.getCodigoEmpresa() + " and cabpol.co_loccli  = " + objZafParSis.getCodigoLocal() + "  and invbod.nd_stkact >0 and co_itmmae = " + intItmmae;
	        //strSQL+=" and invbod.co_itm in (select co_itm from tbm_equinv where co_itmmae = " + intItmmae + " and co_itm<>" + Integer.parseInt(vecRegSel.get(INT_DAT_COD_ITM).toString()) + ")  ";
		strSQL+=" order by ne_pri ";
                //System.out.println("\nSQL_01: " + strSQL);                                          
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                System.out.println("Cargando datos...");
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);                
                while (rst.next())
                {
                    if (blnCon)
                    {
                        Vector vecReg=new Vector();                        
                        //lo que se presenta en el reporte
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP,rst.getString("tx_nomemp"));                     
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_locpro")); 
                        vecReg.add(INT_TBL_DAT_COD_BOD,rst.getString("co_bod"));                        
                        vecReg.add(INT_TBL_DAT_NOM_BOD,rst.getString("tx_nombod"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_DSC_STK,rst.getString("nd_stkact"));      
                        /** 
                         * Este if valida si tiene Datos el vector para cargar una reposicion
                         *  Sino tiene (es igual a null) entonces carga como nuevo .
                         *  Caso contrario recorre el vector y verificando inf.
                           */
                        if (vecRepDat==null){
                            vecReg.add(INT_TBL_DAT_DSC_CAN,"0");                        
                            if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue())  {
                                vecReg.add(INT_TBL_DAT_DSC_PRE, String.valueOf(getCalculaCostoItm(Integer.parseInt(rst.getString("co_emp")),Integer.parseInt(rst.getString("co_itm")),rst.getString("tx_apl"), Double.parseDouble(rst.getString("nd_maruti")))));                                                    
                                if (Double.parseDouble(vecReg.get(INT_TBL_DAT_DSC_PRE).toString())==0.0)
                                    blnRes=false;
//                                System.out.println(vecReg.get(INT_TBL_DAT_DSC_PRE).toString());
                            }else {
                                vecReg.add(INT_TBL_DAT_DSC_PRE,"0");
                            }
                            vecReg.add(INT_TBL_DAT_DSC_TOT,"0");                        
                        }else{
                            i=0;
                            blnFind = false;
                            while(i<vecRepDat.size() && !(blnFind))
                            {
                               objRepData = (ZafRepInv_02) vecRepDat.get(i);
                               /** 
                                * Este if es cuando el vector reposicion si tiene datos
                                * Si la empresa , local y bodega son iguales a los datos de politicas y del vector
                                * carga los datos 
                                * Caso contrario encera las columnas de la tabla y calcula costo
                                *
                                * System.out.println ("obj: "+ objRepData.getDatCodEmp()+ " loc " + objRepData.getDatCodLoc() +" bod "+ objRepData.getDatCodBod());
                                * System.out.println ("tab: "+ rst.getString("co_emp")+" loc "+ rst.getString("co_locpro")+ "bod " +rst.getString("co_bod"));
                                  */
                               if (objRepData.getDatCodEmp() == Integer.parseInt(rst.getString("co_emp")) && objRepData.getDatCodLoc() ==  Integer.parseInt(rst.getString("co_locpro"))  && objRepData.getDatCodBod() ==  Integer.parseInt(rst.getString("co_bod")) )
                               {                                   
                                   blnFind = true;
                                   vecReg.add(INT_TBL_DAT_DSC_CAN, String.valueOf(objRepData.getDatStkCan()));                                                                           
                                   if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue() && objRepData.getDatDscPre()==0)  {
                                        vecReg.add(INT_TBL_DAT_DSC_PRE, String.valueOf(getCalculaCostoItm(Integer.parseInt(rst.getString("co_emp")),Integer.parseInt(rst.getString("co_itm")),rst.getString("tx_apl"), Double.parseDouble(rst.getString("nd_maruti")))));                                                    
                                        if (Double.parseDouble(vecReg.get(INT_TBL_DAT_DSC_PRE).toString())==0.0)
                                            blnRes=false;
//                                        System.out.println(vecReg.get(INT_TBL_DAT_DSC_PRE).toString());
                                    }else {
                                        vecReg.add(INT_TBL_DAT_DSC_PRE,String.valueOf(objRepData.getDatDscPre()));
                                    }
                               }
                               i++;
                            }
                            if (!(blnFind)){
                                   vecReg.add(INT_TBL_DAT_DSC_CAN,"0");
                                    if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue())  {
                                        vecReg.add(INT_TBL_DAT_DSC_PRE, String.valueOf(getCalculaCostoItm(Integer.parseInt(rst.getString("co_emp")),Integer.parseInt(rst.getString("co_itm")),rst.getString("tx_apl"), Double.parseDouble(rst.getString("nd_maruti")))));                                                    
                                        if (Double.parseDouble(vecReg.get(INT_TBL_DAT_DSC_PRE).toString())==0.0)
                                            blnRes=false;
//                                        System.out.println(vecReg.get(INT_TBL_DAT_DSC_PRE).toString());
                                    }else {
                                        vecReg.add(INT_TBL_DAT_DSC_PRE,"0");
                                    }                                                                                      
                            }
                            vecReg.add(INT_TBL_DAT_DSC_TOT, String.valueOf(Double.parseDouble(vecReg.get(INT_TBL_DAT_DSC_CAN).toString())*Double.parseDouble(vecReg.get(INT_TBL_DAT_DSC_PRE).toString())));                                                    
                        }
                        if (blnRes) vecDat.add(vecReg);
                        pgrSis.setValue(pgrSis.getValue()+1);
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                // Verifica que el vector de datos de reposicion tenga datos para cargar un vector sugerido
                // basado en las politicas de reposicion.
                if (vecRepDat==null || getCanSolicitadaPendiente()!=0 ){
                   blnRes=getReposicionSugerida();
                }
                txtCanSal.setText(""+getCanSolicitadaPendiente());
                // Verifica si el JDialog es oculto para almacenar la reposicion sugerida o almacenada
                if (!(Boolean.valueOf(vecConfDoc.get(INT_CONF_VER_DLG).toString()).booleanValue()) && blnRes)
                    GuardaTablaVector();                
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");                
                //fin                
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println("SQLException: " + e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch (Exception e)
        {
            blnRes=false;        
            System.out.println("Exception: " + e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
        
    }
    /** 
     * Metodo que carga en la tabla la cantidad sugerida para cada bodega segun su prioridad y stock disponible
     * Ademas calcula el subtotal del precio por la cantidad sugerida hasta dejar en saldo cero.
       */
    private boolean getReposicionSugerida()
    {
        boolean blnCanSug=false;        
        try{
             double dblCanPed = Double.parseDouble(txtCanSol.getText())-objUti.redondeo(Double.parseDouble(txtCanEmp.getText()),0);
             int j=0;
             while(j<tblDat.getRowCount() && dblCanPed>0)
             {
                if (Double.parseDouble(tblDat.getValueAt(j, INT_TBL_DAT_DSC_STK).toString())<dblCanPed)
                {
                    tblDat.setValueAt(tblDat.getValueAt(j, INT_TBL_DAT_DSC_STK).toString(), j, INT_TBL_DAT_DSC_CAN);
                    dblCanPed -=Double.parseDouble(tblDat.getValueAt(j, INT_TBL_DAT_DSC_STK).toString());
                }else{
                    tblDat.setValueAt(String.valueOf(dblCanPed), j, INT_TBL_DAT_DSC_CAN);
                    dblCanPed =0;
                }
                if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue()){
                    tblDat.setValueAt(String.valueOf(Double.parseDouble(tblDat.getValueAt(j, INT_TBL_DAT_DSC_CAN).toString())*Double.parseDouble(tblDat.getValueAt(j, INT_TBL_DAT_DSC_PRE).toString())),j,INT_TBL_DAT_DSC_TOT);
                }
                j++;
             }             
             txtCanSal.setText(""+getCanSolicitadaPendiente());   
             if (Double.parseDouble(objRepDlg.txtCanSal.getText())==0) butGua.setEnabled(true);
             else butGua.setEnabled(false);
             
             if (dblCanPed>0) {
                 if (objUti.redondeo(Double.parseDouble(txtCanSol.getText())-dblCanPed,0)>0){
                    if (MensajeQuestion("Sistemas Zafiro", "No es posible completar pedido. \n Faltan: "+ dblCanPed+"\n¿Desea solicitar "+ objUti.redondeo(Double.parseDouble(txtCanSol.getText())-dblCanPed,0) +" disponibles? ")== javax.swing.JOptionPane.YES_OPTION) blnCanSug=true;
                    else blnCanSug=false;
                 }else{
                    blnCanSug=false;
                    MensajeError("Sistemas Zafiro", "No es posible completar pedido. \n Faltan: "+ dblCanPed);
                 }
                   
             }
             else
                blnCanSug=true;  
        } catch (Exception e)   {
            blnCanSug=false;  
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnCanSug;

    }
    /** 
     * Metodo que devuelve el valor del costo asignado en las politicas de reposicion
     * El precio unitario 1,2,3 o el costo + el margen de utilidad.
     *
     */
    private double getCalculaCostoItm(int intparCodEmp, int intparCodItm,String strparCampo, double dblparPor)
    {
        String strCampo ="";
        double dblCosItm = 0;
        strSQL = "";
        try{
            conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());         
            if (conAux!=null){
                stmAux=conAux.createStatement();                       
                lblMsgSis.setText("Calculando costos...");
                if (strparCampo.equals("CU"))     strCampo = "nd_cosuni";
                else if (strparCampo.equals("UC"))strCampo = "nd_cosult";
                else if (strparCampo.equals("P1"))strCampo = "nd_prevta1";
                else if (strparCampo.equals("P2"))strCampo = "nd_prevta2";
                else if (strparCampo.equals("P3"))strCampo = "nd_prevta3";
                strSQL  = " select  nd_cosult as costo, " + strCampo +" from tbm_inv";
                strSQL += " where co_emp = " + intparCodEmp + " and co_itm = " + intparCodItm ;
//                System.out.println("SQL:" + strSQL);
                rstAux = stmAux.executeQuery(strSQL);
                while (rstAux.next()){                    
                    if (Double.parseDouble(rstAux.getString(strCampo))>0)
                        dblCosItm = Double.parseDouble(rstAux.getString(strCampo)) + (Double.parseDouble(rstAux.getString(strCampo))*(dblparPor/100));
                    else if (Double.parseDouble(rstAux.getString("costo"))>0)
                        dblCosItm = Double.parseDouble(rstAux.getString("costo")) + (Double.parseDouble(rstAux.getString(strCampo))*(dblparPor/100));
                    else{
                        MensajeError("Sistemas Zafiro.- BackOrder", "Item no tiene costo para realizar reposicion");
                        dblCosItm = 0;                    
                    }
                }
                rstAux.close();
                stmAux.close();
                conAux.close();            
            }
        } catch (java.sql.SQLException e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)   {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return dblCosItm;
    }
    /** 
     * Metodo verifica si en la cabecera de politicas de reposicion existe alguna para esa empresa - local
     *
     */
    private boolean ExisteBodegaReposicion()
    {
        boolean blnExiBod = false;
        strSQL = "";
        try{
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());         
            if (con!=null){
                stm=con.createStatement();       
                strSQL  = " select  count(*) as num from tbm_cabpolrep ";
                strSQL += " where co_empcli = " + objZafParSis.getCodigoEmpresa() + " and co_loccli = " + objZafParSis.getCodigoLocal() ;                
                rst = stm.executeQuery(strSQL);
                while (rst.next()){
                    if (Integer.parseInt(rst.getString("num"))>0) blnExiBod=true;
                }
                rst.close();
                stm.close();
                con.close();            
            }
        } catch (java.sql.SQLException e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)   {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnExiBod;
    }
    
    /** 
     * metodo que devuelve un valor double con la cantidad en la bodega de la empresa proveedora
     * Asignada en la tabla de reposicion (detalle)
     *
     */
    private double getCanBodEmp()
    {
        double dblCanBod = 0;
        strSQL = "";
        try{
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());         
            if (con!=null){
                stm=con.createStatement();       
                strSQL = " select nd_stkact from tbm_invbod ";
                strSQL += " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_bod = " + Integer.parseInt(vecRegSel.get(INT_DAT_COD_BOD).toString()) + " and co_itm = " + Integer.parseInt(vecRegSel.get(INT_DAT_COD_ITM).toString()) ;                
                rst = stm.executeQuery(strSQL);
                while (rst.next()){
                    dblCanBod  = Double.parseDouble(rst.getString("nd_stkact"));
                }
                rst.close();
                stm.close();
                con.close();            
            }
        } catch (java.sql.SQLException e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)   {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return dblCanBod;
    }
    
    /** 
     * Metodo devuelve la cantidad pendiente por asignar un proveedor 
     * Muestra el saldo 
     *
     */
    private double getCanSolicitadaPendiente()
    {
        double dblSaldo=0, dblCanSol=0;        
        for (int i=0;i<tblDat.getRowCount();i++)
        {
            dblCanSol += Double.parseDouble(tblDat.getValueAt(i,INT_TBL_DAT_DSC_CAN)==null || tblDat.getValueAt(i,INT_TBL_DAT_DSC_CAN)==""?"0":tblDat.getValueAt(i,INT_TBL_DAT_DSC_CAN).toString());
        }
        dblSaldo = Double.parseDouble(txtCanSol.getText())-Double.parseDouble(txtCanEmp.getText())-dblCanSol;        
        return dblSaldo;
    }
    
    private void MensajeError(String strTit,String strMsg){
        javax.swing.JOptionPane objMsgErr=new javax.swing.JOptionPane();
        objMsgErr.showMessageDialog(this,strMsg,strTit, javax.swing.JOptionPane.ERROR_MESSAGE);        
    }
    
    private void MensajeInf(String strTit,String strMsg){
        javax.swing.JOptionPane objMsgErr=new javax.swing.JOptionPane();
        objMsgErr.showMessageDialog(this,strMsg,strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);        
    }
    private int MensajeQuestion(String strTit,String strMsg){
        javax.swing.JOptionPane objMsgErr=new javax.swing.JOptionPane();
        return objMsgErr.showConfirmDialog(this,strMsg,strTit, javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.INFORMATION_MESSAGE);        
    }

    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarBodegaReposicion())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }else{
                vecRepDat = null;
            }                
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {                
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
class EditorCol_SolCan extends javax.swing.DefaultCellEditor {
        
        final javax.swing.JTextField myText;
        private boolean daEnter= false;
        
        EditorCol_SolCan() {
            super(new javax.swing.JCheckBox());
            
            myText = new javax.swing.JTextField();
            this.editorComponent = myText;
            
            myText.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    fireEditingStopped();                    
                    objRepDlg.tblDat.clearSelection();
                }
            });
        }
        
        
        protected void fireEditingStopped() {
          super.fireEditingStopped();                                 
          try{
            if (objUti.isNumero(objRepDlg.tblDat.getValueAt(objRepDlg.tblDat.getSelectedRow(),objRepDlg.tblDat.getSelectedColumn()).toString())){
                double dblexibod = Double.parseDouble(objRepDlg.tblDat.getValueAt(objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_STK).toString()) ,
                       dblexisol = Double.parseDouble(objRepDlg.tblDat.getValueAt(objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_CAN).toString());
//                if (objRepDlg.getCanSolicitadaPendiente()>=0 && dblexibod>=dblexisol){        pendiente validacion exceso en reposicion      
                  if (dblexibod>=dblexisol){              
                    objRepDlg.txtCanSal.setText(""+objRepDlg.getCanSolicitadaPendiente()); 
                    if (Double.parseDouble(objRepDlg.txtCanSal.getText())<=0) objRepDlg.butGua.setEnabled(true);
                    else objRepDlg.butGua.setEnabled(false);             
                    if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue())
                    {                       
                        if (Double.parseDouble(objRepDlg.tblDat.getValueAt(objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_PRE).toString())>0){
                            double dblsubtot= dblexisol * Double.parseDouble(objRepDlg.tblDat.getValueAt(objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_PRE).toString());
                            objRepDlg.tblDat.setValueAt(new String(""+dblsubtot),objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_TOT);
                        }
                    }
                    objRepDlg.tblDat.setValueAt(new String(""+dblexisol),objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_CAN);
                                                           
                }else{
                    if (dblexibod<dblexisol){                    
                        objRepDlg.MensajeError("Sistema Zafiro", "No puede solicitar esa cantidad más de lo existente en bodega");
                    } else {
                        objRepDlg.MensajeError("Sistema Zafiro", "No puede reservar más de lo solicitado");                
                    }
                    objRepDlg.tblDat.setValueAt("0",objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_CAN);                
                    if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue()){
                        objRepDlg.tblDat.setValueAt("0",objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_TOT);                
                    }
                    objRepDlg.txtCanSal.setText(""+ objRepDlg.getCanSolicitadaPendiente());
                    if (Double.parseDouble(objRepDlg.txtCanSal.getText())<=0) objRepDlg.butGua.setEnabled(true);
                    else objRepDlg.butGua.setEnabled(false);                                        
                }
            }else{
                 objRepDlg.MensajeError("Sistema Zafiro", "Error en el ingreso de cantidades");                
                 objRepDlg.tblDat.setValueAt("0",objRepDlg.tblDat.getSelectedRow(),objRepDlg.tblDat.getSelectedColumn());                
                 if (Boolean.valueOf(vecConfDoc.get(INT_CONF_CAL_COS).toString()).booleanValue()){
                     objRepDlg.tblDat.setValueAt("0",objRepDlg.tblDat.getSelectedRow(),INT_TBL_DAT_DSC_TOT);                
                 }
                 objRepDlg.txtCanSal.setText(""+ objRepDlg.getCanSolicitadaPendiente());                
                 if (Double.parseDouble(objRepDlg.txtCanSal.getText())<=0)  objRepDlg.butGua.setEnabled(true);
                 else objRepDlg.butGua.setEnabled(false);                 
            }
          } catch (Exception e)   {
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
          }
        }
        public Object getCellEditorValue() {
           Double dbl = new Double(objUti.redondeo(Double.parseDouble((myText.getText().trim().equals(""))?"0":myText.getText()),6));
           return dbl;
        }
       
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            
            if(value == null)
                ((javax.swing.JTextField) editorComponent).setText("0");
            else
                ((javax.swing.JTextField) editorComponent).setText(value.toString());
            ((javax.swing.JTextField) editorComponent).setFont( new java.awt.Font("SansSerif", 0, 11));
            return editorComponent;
        }
        
    }    

    private class ZafTableModel extends javax.swing.table.AbstractTableModel
    {
        private Vector vecCabMod;
        private Vector vecDatMod;
        private java.util.Hashtable htbEstFil;
        
        public ZafTableModel()
        {
            vecCabMod=new Vector();
            vecDatMod=new Vector();
            htbEstFil=new java.util.Hashtable();
        }

        public int getRowCount()
        {
            return vecDatMod.size();
        }
        
        public int getColumnCount()
        {
            return vecCabMod.size();
        }
        
        public Object getValueAt(int row, int col)
        {
            Vector vecAux=(Vector)vecDatMod.elementAt(row);
            return vecAux.elementAt(col);
        }
        
        public String getColumnName(int col)
        {
            return vecCabMod.elementAt(col).toString();
        }

        public void setHeader(Vector cabecera)
        {
            vecCabMod=cabecera;
        }

        public void setData(Vector datos)
        {
            vecDatMod=datos;
            fireTableDataChanged();
        }

        public void setValues(Vector cabecera, Vector datos)
        {
            vecCabMod=cabecera;
            vecDatMod=datos;
            fireTableDataChanged();
        }
        
        public void setValueAt(Object valor, int row, int col)
        {
            Vector vecAux=(Vector)vecDatMod.elementAt(row);
            vecAux.setElementAt(valor, col);
            //Almacenar el estado de la fila.
            htbEstFil.put("" + row, "M");
            fireTableCellUpdated(row, col);
        }
        
        public boolean isCellEditable(int row, int col)
        {
            boolean blnCelBlo = false;
            switch (col)
            {
               case INT_TBL_DAT_LIN:
                    blnCelBlo = false;
                    break;
                case INT_TBL_DAT_COD_EMP:
                    blnCelBlo = false;
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    blnCelBlo = false;
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    blnCelBlo = false;
                    break;
                case INT_TBL_DAT_DSC_STK:
                    blnCelBlo = false;
                    break;                
                case INT_TBL_DAT_DSC_CAN:
                    switch (Integer.parseInt(vecConfDoc.get(INT_CONF_REP_NEW).toString()))
                    {
                        case 1: //Nuevo
                            blnCelBlo = true;
                            break;
                        case 2://Modificacion
                            if (Boolean.valueOf(vecConfDoc.get(INT_CONF_PER_MOD).toString()).booleanValue())  
                                blnCelBlo = true;
                            else
                                blnCelBlo = false;
                            break;
                        case 3://Consulta
                            blnCelBlo = false;
                            break;
                    }
                    break;                
                case INT_TBL_DAT_DSC_PRE:
                    blnCelBlo = false;
                    break;                
                case INT_TBL_DAT_DSC_TOT:
                    blnCelBlo = false;
                    break;                
            }
            return blnCelBlo;
        }

        public Class getColumnClass(int col)
        {
            Object obj=getValueAt(0, col);
            if (obj==null)
            {
                return Object.class;
            }
            else
            {
                return obj.getClass();
            }
        }

        public boolean intercambiarFilas(int origen, int destino)
        {
            if ( (origen>=0) && (origen<=(getRowCount()-1)) && (destino>=0) && (destino<=(getRowCount()-1)) )
            {
                Vector vecAux=new Vector();
                vecAux=(Vector)vecDat.get(destino);
                vecDat.set(destino, (Vector)vecDat.get(origen));
                vecDat.set(origen, vecAux);
                vecAux=null;
                fireTableDataChanged();
                return true;
            }
            return false;
        }

        public char getEstadoFila(int row)
        {
            char chrRes='O';
            try
            {
                chrRes=htbEstFil.get("" + row).toString().charAt(0);
            }
            catch (NullPointerException e)
            {
                chrRes='O';
            }
            catch (Exception e)
            {
                chrRes='O';
            }
            return chrRes;
        }        
        public void inicializarEstadoFilas()
        {
            htbEstFil.clear();
        }
        
        public int getNumeroFilasAlteradas()
        {
            return htbEstFil.size();
        }
        
        public java.util.Enumeration getFilasAlteradas()
        {
            return htbEstFil.keys();
        }
    }
   
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Codigo Empresa";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Razon Social Empresa";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Bodega";
                    break;
                case INT_TBL_DAT_DSC_STK:
                    strMsg="Stock Actual Bodega";
                    break;
                case INT_TBL_DAT_DSC_CAN:
                    strMsg="Cantidad Solicitada";
                    break;
                case INT_TBL_DAT_DSC_PRE:
                    strMsg="Precio de Compra";
                    break;
                case INT_TBL_DAT_DSC_TOT:
                    strMsg="SubTotal";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    } 

   
}
