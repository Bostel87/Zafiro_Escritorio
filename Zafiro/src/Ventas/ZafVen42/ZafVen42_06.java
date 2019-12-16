

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Ventas.ZafVen42;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
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
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
                 
/**     
 * @author  JoseMario Marin M
 */
public class ZafVen42_06 extends javax.swing.JDialog {
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
     // a1.co_bodRel,a4.tx_nom,  a1.tx_forDes 
    
    private static final int INT_TBL_DAT_COD_BOD_GRP = 1;            //Código de Empresa de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_NOM_BOD = 2;            //Código de Local de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_ENV_CLI = 3;         //Código de Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CLI_RET = 4;      //Descripción Corta Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_COD_CLI = 5;      //Descripción Larga Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
     
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
    private ZafTblCelEdiChk objTblCelEdiChkPre; 
    private ArrayList arlDatRes;
   
        /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
    private final int INT_ARL_COD_EMP=0;
    private final int INT_ARL_COD_LOC=1;
    private final int INT_ARL_COD_COT=2;
    private final int INT_ARL_COD_ITM=3;
    private final int INT_ARL_CAN_COM=4;
    private final int INT_ARL_COD_EMP_EGR=5;
    private final int INT_ARL_COD_BOD_EMP_EGR=6;
    private final int INT_ARL_COD_ITM_EGR=7;
    private final int INT_ARL_CAN_UTI=8;
    private final int INT_ARL_COD_BOD_EGR_GRP=9;

    public ZafVen42_06() {
        
    } 
    
    /** Creates new form pantalla dialogo */
    public ZafVen42_06(Frame parent,  ZafParSis ZafParSis, int CodEmp, int CodLoc, int CodCot, ArrayList arlDatExt) {
        super(parent, true);
        try{ 
           
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          arlDatRes = arlDatExt;
          
            this.objParSis = (ZafParSis) ZafParSis.clone();
            
            intCodEmp=(CodEmp);
            intCodLoc=(CodLoc);
            intCodCot=(CodCot);
         
            
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
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText("Detalle de Despachos ");
            //Configurar Jtable
            configurarTblDat();
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
  
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private Vector  vecAux;
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat(){
        blnAceptar=false;
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_BOD_GRP,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_ENV_CLI,"Env.Cli.");  
            vecCab.add(INT_TBL_DAT_CLI_RET,"Cli.Ret.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cod.Cli.");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_GRP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_ENV_CLI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CLI_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(30);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_ENV_CLI).setCellRenderer(objTblCelRenChk);  // SOLICITAR RESERVA
            tcmAux.getColumn(INT_TBL_DAT_CLI_RET).setCellRenderer(objTblCelRenChk);   // FACTURA AUTOMATICA
            objTblCelRenChk = null;
            
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_ENV_CLI);
            vecAux.add("" + INT_TBL_DAT_CLI_RET);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblDat);
            
            /* Checks de tipo de despacho solo uno a la vez   */
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_ENV_CLI).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(objTblModDat.isChecked(intNumFil,INT_TBL_DAT_CLI_RET)){
                        objTblModDat.setValueAt(false, intNumFil, INT_TBL_DAT_CLI_RET); 
                    }
                }
            });
            
            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CLI_RET).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(objTblModDat.isChecked(intNumFil,INT_TBL_DAT_ENV_CLI)){
                        objTblModDat.setValueAt(false, intNumFil, INT_TBL_DAT_ENV_CLI); 
                    }
                }
            });
           /* Checks de tipo de despacho solo uno a la vez   */
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
             
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
                        
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this, e);     
        }
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
                case INT_TBL_DAT_COD_BOD_GRP:
                    strMsg="Código Bodega";
                    break;                
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Nombre de la Bodega";
                    break;        
                case INT_TBL_DAT_ENV_CLI:
                    strMsg="Se Envia al Cliente";
                    break;        
                case INT_TBL_DAT_CLI_RET:
                    strMsg="Cliente Retira";
                    break;   
                case INT_TBL_DAT_COD_CLI:
                    strMsg="xxxxxxxxxxxxxxxx";
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
        btnAceptar = new javax.swing.JButton();
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

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        panSubBot.add(btnAceptar);

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

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        if(guardarReg()){
            blnAceptar=true;
            MensajeInf("Configuracion guardada con Exito"); 
            this.setVisible(false);
            
        }
        else{
            MensajeInf("Error al guardar la configuracion"); 
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    
    public boolean acepta(){
        return blnAceptar;
    }
    
    private boolean blnAceptar=false;
    
    
    private boolean guardarReg(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                if(guardarConfiguracion(conLoc)){
                    blnRes=true;
                    conLoc.commit();
                    blnAceptar=true;
                }
                else{
                    conLoc.rollback();
                    blnRes=false;
                    blnAceptar=false;
                }
            conLoc.close();
            conLoc=null;
            }
           
        }
        catch (java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean guardarConfiguracion(java.sql.Connection conLoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQL="";
                for(int i=0; i<tblDat.getRowCount(); i++){
                    if (objUti.parseString(objTblModDat.getValueAt(i,0)).equals("M")){    
                        if(objTblModDat.isChecked(i, INT_TBL_DAT_ENV_CLI) || objTblModDat.isChecked(i, INT_TBL_DAT_CLI_RET)){
                            strSQL+=" INSERT INTO tbm_forDesCliBod(co_emp,co_loc,co_cli,co_empRel,co_bodRel,tx_forDes,st_reg,co_usrIng,fe_ing) \n";
                            strSQL+=" VALUES ("+intCodEmp+","+intCodLoc+", "+tblDat.getValueAt(i,INT_TBL_DAT_COD_CLI).toString()+", ";
                            strSQL+=          ""+objParSis.getCodigoEmpresaGrupo()+","+tblDat.getValueAt(i,INT_TBL_DAT_COD_BOD_GRP).toString()+",";
                            if(objTblModDat.isChecked(i, INT_TBL_DAT_ENV_CLI)){
                                strSQL+="'E',";
                            }//E=Enviar al cliente;C=Cliente retira
                            else if(objTblModDat.isChecked(i, INT_TBL_DAT_CLI_RET)){
                                strSQL+="'C',";
                            }
                            strSQL+="'A',"+objParSis.getCodigoUsuario()+",CURRENT_TIMESTAMP );";
                        }
                    }
                }
                 System.out.println("guardarConfiguracion " + strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc = null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
      private void MensajeInf(String strMensaje) {
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        int intAux=-1;
        String strAux="";
        try
        {
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null){
                stm = con.createStatement();      
                for(int i=0;i<arlDatRes.size();i++){
                    if(i==0){
                        intAux=objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EGR_GRP);
                        strAux=""+intAux;
                    }
                    if(intAux!=objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EGR_GRP)){
                        intAux=objUti.getIntValueAt(arlDatRes, i, INT_ARL_COD_BOD_EGR_GRP);
                        strAux+=","+intAux;
                    }
                    
                }
                //Armar la sentencia SQL Las bodegas solicitadas pero que no estan configuradas .
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp, a1.co_loc, a3.tx_nom as tx_nomLoc, a1.co_cli,  \n";
                strSQL+="        a5.co_bodGrp,a6.tx_nom   \n";
                strSQL+=" FROM tbm_cabCotVen as a1   \n";
                strSQL+=" INNER JOIN tbm_emp as a2 ON (a1.co_emp=a2.co_emp)   \n";
                strSQL+=" INNER JOIN tbm_loc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc) \n";
                strSQL+=" INNER JOIN tbr_bodloc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a4.st_reg='P') \n";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a5 ON (a4.co_emp=a5.co_emp AND a4.co_bod=a5.co_bod) \n";
                strSQL+=" INNER JOIN tbm_bod as a6 ON (a5.co_empGrp=a6.co_emp AND a5.co_bodGrp=a6.co_bod)  \n";
                strSQL+=" WHERE  a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" AND \n";
                strSQL+="       a5.co_bodGrp NOT IN (  \n";
                strSQL+="                                SELECT a1.co_bodRel  \n";
                strSQL+="                                FROM tbm_forDesCliBod as a1 \n";
                strSQL+="                                INNER JOIN tbm_cabCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
                strSQL+="                                                                   a1.co_cli=a2.co_cli) \n";
                strSQL+="                                WHERE a2.co_emp="+intCodEmp+" AND a2.co_loc="+intCodLoc+" AND a2.co_cot="+intCodCot+" \n";
                strSQL+="                           ) \n";
                strSQL+=" ORDER BY a6.tx_nom  \n";
                System.out.println("cargarDetReg.ZafVen42_06_configuracionesPorBodegaSegunConfirmacion: "+strSQL);   
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_BOD_GRP, rst.getInt("co_bodGrp"));
                    vecReg.add(INT_TBL_DAT_NOM_BOD, rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_ENV_CLI, "");
                    vecReg.add(INT_TBL_DAT_CLI_RET, "");
                    vecReg.add(INT_TBL_DAT_COD_CLI,rst.getInt("co_cli"));
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
                 
//                butCon.setText("Consultar");
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
    private javax.swing.JButton btnAceptar;
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

