

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Ventas.ZafVen42;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
                 
/**     
 * @author  Jota
 */
public class ZafVen42_05 extends javax.swing.JDialog {
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
    
    private static final int INT_TBL_DAT_CODITM = 1;            //Código de Empresa de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CODALT = 2;            //Código de Local de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CAN = 3;         //Código de Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_PREUNI = 4;      //Descripción Corta Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD = 5;      //Descripción Larga Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
     
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
     
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblMod objTblModDat;                                             //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat;                                       //ToolTipText en TableHeader.
    
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;
    
    private int intCodEmp,intCodLoc,intCodCot,intCodPrv; 
    
    private String strVersion="v0.1";                                           //Versión del Programa.  
   private java.awt.Frame Frame;
   

    public ZafVen42_05() {
        
    } 
    
    /** Creates new form pantalla dialogo */
    public ZafVen42_05(Frame parent,  ZafParSis ZafParSis, int CodEmp, int CodLoc, int CodCot, int CodPrv) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          
          
            this.objParSis = (ZafParSis) ZafParSis.clone();
            
            intCodEmp=(CodEmp);
            intCodLoc=(CodLoc);
            intCodCot=(CodCot);
            intCodPrv=(CodPrv);
            
            initComponents();
            if (!configurarFrm()) 
                exitForm();
            
            consultar();
            

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


 /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
          
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText("Detalle de ítems ");
            
            //Configurar Jtable
            configurarTblDat();
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
  
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_DAT_LIN,"");
            
            vecCab.add(INT_TBL_DAT_CODITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_CODALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");  
            vecCab.add(INT_TBL_DAT_PREUNI,"Pre.Uni.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD,"...");
            
             
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PREUNI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD).setPreferredWidth(30);
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD, tblDat);
      
           
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
            
             
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
           
            
            
            
//            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PREUNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CODALT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
                        
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
     
    /**
     * ToolTips de Jtable: Datos.
     */
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODITM:
                    strMsg="Código Item";
                    break;                
                case INT_TBL_DAT_CODALT:
                    strMsg="Código Alterno";
                    break;        
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;        
                case INT_TBL_DAT_PREUNI:
                    strMsg="Precio Unitario";
                    break;   
                case INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD:
                    strMsg="Descripción larga del tipo de documento";
                    break;                    
                
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
     
     
     private void consultar() {
        if (objThrGUI == null){
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
     }
     
     
     
//    /**
//     * Esta clase crea un hilo que permite manipular la interface gráfica de
//     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
//     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
//     * internamente. Es decir a medida que se llevan a cabo los procesos se
//     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
//     * un JProgressBar con lo cual el usuario estaría informado en todo momento
//     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
//     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
//     * proceso.
//     */
    private class ZafThreadGUI extends Thread
    {
        @Override
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
//                butCon.setText("Consultar");
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
    
    
    private void exitForm() 
    {
        dispose();
    } 
 

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpInsGenFac = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 48));
        panTit.setLayout(null);

        lblTit.setText("titulo");
        panTit.add(lblTit);
        lblTit.setBounds(210, 7, 300, 14);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

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

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cerrar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-641)/2, (screenSize.height-470)/2, 641, 470);
    }// </editor-fold>//GEN-END:initComponents

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     exitForm();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
     
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
         
}//GEN-LAST:event_formWindowOpened

    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null){
                stm = con.createStatement();      
                strSQL ="";
                strSQL+=" SELECT co_itm, tx_codAlt, nd_can, nd_preUni \n";
                strSQL+=" FROM tbm_detCotVen \n";
                strSQL+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" AND \n";
                strSQL+="       co_prv="+intCodPrv+" \n";
                System.out.println("cargarDetReg.ZafVen42_05.CODIGOS_L: "+strSQL);   
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CODITM, rst.getInt("co_itm"));
                    vecReg.add(INT_TBL_DAT_CODALT, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_CAN, rst.getDouble("nd_can"));
                    vecReg.add(INT_TBL_DAT_PREUNI, rst.getDouble("nd_preUni"));
                    vecReg.add(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD,"");
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpInsGenFac;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

     
    
     
    
}

