/**
 * 
 */
package Importaciones.ZafImp05;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp05_01 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable   
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_TIP_DOC=3;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    static final int INT_TBL_DAT_COD_DOC=6;
    static final int INT_TBL_DAT_NUM_DOC=7;
    static final int INT_TBL_DAT_NUM_PED=8;
    static final int INT_TBL_DAT_BUT=9;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut objTblCelRenBut;
    
    private Vector vecReg, vecDat, vecCab, vecAux;
    private String strSQL, strAux;
    private int intCodEmpIngImp;
    private int intCodLocIngImp;
    private int intCodTipDocIngImp;
    private int intCodDocIngImp;
    private int intExiVarReg;
    private int intCodEmpNotPed;
    private int intCodLocNotPed;
    private int intCodTipDocNotPed;
    private int intCodDocNotPed;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp05_01(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            configurarFrm();
            
            strAux="Pedido embarcado(Listado de notas de pedido)";
            this.setTitle(strAux);
            lblTit.setText(strAux);
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(10);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_BUT,"");
            
            objUti=new ZafUtil();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);            
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(30);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
//            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);

            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE MAESTRO DE CLIENTES
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        callProgramaNotaPedido(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panRpt.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 30));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-548)/2, (screenSize.height-321)/2, 548, 321);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        dispose();
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
   
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    public int getNumeroRegistros() {
        return intExiVarReg;
    }

    public void setNumeroRegistros(int intExiVarReg) {
        this.intExiVarReg = intExiVarReg;
    }
    
    public int getCodigoEmpresaIngresoImportacion() {
        return intCodEmpIngImp;
    }
    public void setCodigoEmpresaIngresoImportacion(int intCodEmpIngImp) {
        this.intCodEmpIngImp = intCodEmpIngImp;
    }    
    
    public int getCodigoLocalIngresoImportacion() {
        return intCodLocIngImp;
    }
    public void setCodigoLocalIngresoImportacion(int intCodLocIngImp) {
        this.intCodLocIngImp = intCodLocIngImp;
    }            
    
    public int getCodigoTipoDocumentoIngresoImportacion() {
        return intCodTipDocIngImp;
    }
    public void setCodigoTipoDocumentoIngresoImportacion(int intCodTipDocIngImp) {
        this.intCodTipDocIngImp = intCodTipDocIngImp;
    }
    
    public int getCodigoDocumentoIngresoImportacion() {
        return intCodDocIngImp;
    }
    public void setCodigoDocumentoIngresoImportacion(int intCodDocIngImp) {
        this.intCodDocIngImp = intCodDocIngImp;
    }
    
    public int getCodigoEmpresaNotaPedido() {
        return intCodEmpNotPed;
    }
    public void setCodigoEmpresaNotaPedido(int intCodEmpNotPed) {
        this.intCodEmpNotPed = intCodEmpNotPed;
    }       
    
    public int getCodigoLocalNotaPedido() {
        return intCodLocNotPed;
    }
    public void setCodigoLocalNotaPedido(int intCodLocNotPed) {
        this.intCodLocNotPed = intCodLocNotPed;
    }    
    
    public int getCodigoTipoDocumentoNotaPedido() {
        return intCodTipDocNotPed;
    }
    public void setCodigoTipoDocumentoNotaPedido(int intCodTipDocNotPed) {
        this.intCodTipDocNotPed = intCodTipDocNotPed;
    }
    
    public int getCodigoDocumentoNotaPedido() {
        return intCodDocNotPed;
    }
    public void setCodigoDocumentoNotaPedido(int intCodDocNotPed) {
        this.intCodDocNotPed = intCodDocNotPed;
    }
    
    private void callProgramaNotaPedido(int fila){
        try{
            //se carga el programa Nota de Pedido
            Importaciones.ZafImp32.ZafImp32 objImp_32=new Importaciones.ZafImp32.ZafImp32(objParSis, Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString()), Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString()), Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString())  );
            this.getParent().add(objImp_32,javax.swing.JLayeredPane.DEFAULT_LAYER);
            objImp_32.show();
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }    

    public boolean cargarDocumentos(){
        boolean blnRes=true;
        intExiVarReg=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="      , a7.tx_desCor, a7.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+="      , a5.co_tipDoc AS co_tipDocIngImp, a5.co_doc AS co_docIngImp";
                strSQL+=" FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locrel AND a2.co_tipdoc=a3.co_tipDocrel AND a2.co_doc=a3.co_docrel AND a2.co_reg=a3.co_regrel";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a5";
                strSQL+=" ON a4.co_emp=a5.co_empRelPedEmbImp AND a4.co_loc=a5.co_locRelPedEmbImp AND a4.co_tipDoc=a5.co_tipDocRelPedEmbImp AND a4.co_doc=a5.co_docRelPedEmbImp";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a7";
                strSQL+=" ON a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc";
                strSQL+=" WHERE a4.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                strSQL+=" AND a5.co_emp=" + intCodEmpIngImp + " AND a5.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a5.co_tipDoc=" + intCodTipDocIngImp + " AND a5.co_doc=" + intCodDocIngImp + "";
                strSQL+=" GROUP BY a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc";
                strSQL+=" 	 , a7.tx_desCor, a7.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" 	 , a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" ORDER BY a1.co_doc, a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc";
                System.out.println("SQL cargarDocumentos: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,             "");
                    vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numDoc2"));
                    vecReg.add(INT_TBL_DAT_BUT,             null);
                    vecDat.add(vecReg);
                    intExiVarReg++;
                    if(intExiVarReg==1){
                        intCodEmpNotPed=rst.getInt("co_emp");
                        intCodLocNotPed=rst.getInt("co_loc");
                        intCodTipDocNotPed=rst.getInt("co_tipDoc");
                        intCodDocNotPed=rst.getInt("co_doc");
                    }

                }
                if(intExiVarReg!=1){
                    intCodEmpNotPed=0;
                    intCodLocNotPed=0;
                    intCodTipDocNotPed=0;
                    intCodDocNotPed=0;
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
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        

   

    

    
}