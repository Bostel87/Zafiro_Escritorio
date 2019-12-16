/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package RecursosHumanos.ZafRecHum14;

import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * Listado de Meriendas
 * @author Roberto Flores
 * Guayaquil 24/08/2012
 */
public class ZafRecHum14 extends javax.swing.JInternalFrame {


  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
  private ZafUtil objUti;
  private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
  private ZafThreadGUI objThrGUI;
  private ZafSelFec objSelFec;
  private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMenú en JTable.
  

  private static final int INT_TBL_LINEA = 0;                            //Índice de columna Linea.
  private static final int INT_TBL_FECHA = 1;                            //Índice de columna Día.
  private static final int INT_TBL_CODTRA = 2;
  private static final int INT_TBL_NOMTRA = 3;
  private static final int INT_TBL_MARSAL = 4;

  String strVersion=" v1.0 ";
  
  private Vector vecCab = new Vector();                                  //Vector que contiene la cabecera del Table.

  private int intCodEmp;                                                 //Código de la empresa.
  private boolean blnMod;                                                //Indica si han habido cambios en el documento
  private boolean blnConsDat;
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;
  private ZafTblTot objTblTot;                        //JTable de totales.

    /** Creates new form ZafRecHum14 */
    public ZafRecHum14(Librerias.ZafParSis.ZafParSis obj) {

        try{

            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
            
            this.setTitle(objZafParSis.getNombreMenu()+"  "+strVersion+" ");
            lblTit.setText(objZafParSis.getNombreMenu());

	    objUti = new ZafUtil();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            configuraTbl();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //*******************************************************************************
            
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
        
        try{

            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_FECHA,"Fecha");
            vecCab.add(INT_TBL_CODTRA,"Código");
            vecCab.add(INT_TBL_NOMTRA,"Empleado");//vecCab.add(INT_TBL_APETRA,"Apellidos");
            vecCab.add(INT_TBL_MARSAL,"Salida");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(260);
            tcmAux.getColumn(INT_TBL_MARSAL).setPreferredWidth(60);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);

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
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
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

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);
        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 130);

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
    }//GEN-LAST:event_formInternalFrameOpened

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:

         cargarRepote(1);
         
//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
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
         exitForm();
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

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:

         cargarRepote(0);

//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
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

        try{

            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if(conn!=null){

                stmLoc=conn.createStatement();
                //vecData = new Vector();
                vecDataCon = new Vector();

                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Búsqueda por rangos
                        sqlFil+=" WHERE a.fe_sol BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" WHERE a.fe_sol<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" WHERE a.fe_sol>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 3: //Todo.
                        break;
                }
                
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    
                    strSql="";
                    strSql=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_sol, ";
                    strSql+=" a.st_aut, a.ho_aut, b.co_tra, c.ho_ent, c.ho_sal, (d.tx_ape || ' ' || d.tx_nom) as empleado from tbm_cabsolhorsupext a";
                    strSql+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                    strSql+=" inner join tbm_cabconasitra c on (a.fe_sol=c.fe_dia and b.co_tra=c.co_tra)";
                    strSql+=" inner join tbm_tra d on (b.co_tra=d.co_tra)";
                    strSql+=" " + sqlFil + " ";
                    strSql+=" and a.st_aut like 'T' ";
                    strSql+=" and (a.ho_aut > '20:00:00' and c.ho_sal> '20:00:00')";
                    strSql+=" and a.co_emp in (1,2,4)";
                    strSql+=" and a.co_tipdoc=191";
                    strSql+=" and a.st_reg like 'A'";
                    strSql+=" and c.ho_sal is not null";
                    strSql+=" order by co_emp, co_loc, co_tipdoc, co_doc, a.fe_sol, empleado";                   
                }else{
                    
                    strSql="";
                    strSql=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_sol, ";
                    strSql+=" a.st_aut, a.ho_aut, b.co_tra, c.ho_ent, c.ho_sal, (d.tx_ape || ' ' || d.tx_nom) as empleado from tbm_cabsolhorsupext a";
                    strSql+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                    strSql+=" inner join tbm_cabconasitra c on (a.fe_sol=c.fe_dia and b.co_tra=c.co_tra)";
                    strSql+=" inner join tbm_tra d on (b.co_tra=d.co_tra)";
                    strSql+=" " + sqlFil + " ";
                    strSql+=" and a.st_aut like 'T' ";
                    strSql+=" and (a.ho_aut > '20:00:00' and c.ho_sal> '20:00:00')";
                    strSql+=" and a.co_emp="+objZafParSis.getCodigoEmpresa();
                    strSql+=" and a.co_tipdoc=191";
                    strSql+=" and a.st_reg like 'A'";
                    strSql+=" and c.ho_sal is not null";
                    strSql+=" order by co_emp, co_loc, co_tipdoc, co_doc, a.fe_sol, empleado";
                }

                System.out.println("q consul: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);

                int intPos1 = 0;

                while(rstLoc.next()){

                    java.util.Vector vecReg = new java.util.Vector();

                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_FECHA, rstLoc.getString("fe_sol"));
                    vecReg.add(INT_TBL_CODTRA,rstLoc.getInt("co_tra"));
                    vecReg.add(INT_TBL_NOMTRA,rstLoc.getString("empleado"));
                    vecReg.add(INT_TBL_MARSAL,rstLoc.getString("ho_sal"));
                    vecDataCon.add(vecReg);
                    
                }

                objTblMod.setData(vecDataCon);
                tblDat .setModel(objTblMod);
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                blnRes=true;
           }}catch(java.sql.SQLException Evt) {  Evt.printStackTrace();blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt) { Evt.printStackTrace();blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }
    
    private String getDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
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
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){

            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";

            switch (intCol){

                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_FECHA:
                    strMsg="Fecha";
                    break;
                case INT_TBL_CODTRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg="Nombres y Apellidos del empleado";
                    break;
                case INT_TBL_MARSAL:
                    strMsg="Hora de salida";
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
                   //google objTooBar.setEnabledVistaPreliminar(true);
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
        String strRutRpt, strNomRpt, strFecHorSer="", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String strSql = "", sqlFil = "", sqlFil2 = "";
        String strSqlDet="";
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
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            //case 415:
                            default:

                                //Inicializar los parametros que se van a pasar al reporte.

                                //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }

                                switch (objSelFec.getTipoSeleccion()){
                                    case 0: //Búsqueda por rangos
                                        sqlFil+=" WHERE a.fe_sol BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        //sqlFil2+=" WHERE x.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 1: //Fechas menores o iguales que "Hasta".
                                        sqlFil+=" WHERE a.fe_sol<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        //sqlFil2+=" WHERE x.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 2: //Fechas mayores o iguales que "Desde".
                                        sqlFil+=" WHERE a.fe_sol>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        //sqlFil2+=" WHERE a.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' ";
                                        break;
                                    case 3: //Todo.
                                        break;
                                }

                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v1.0    ";
                                
                                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){


                                    strSql="";
                                    strSql+="select distinct f.fe_sol from (";
                                    strSql+=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_sol, ";
                                    strSql+=" a.st_aut, a.ho_aut, b.co_tra, c.ho_ent, c.ho_sal, (d.tx_ape || ' ' || d.tx_nom) as empleado from tbm_cabsolhorsupext a";
                                    strSql+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                                    strSql+=" inner join tbm_cabconasitra c on (a.fe_sol=c.fe_dia and b.co_tra=c.co_tra)";
                                    strSql+=" inner join tbm_tra d on (b.co_tra=d.co_tra)";
                                    strSql+=" " + sqlFil + " ";
                                    strSql+=" and a.st_aut like 'T' ";
                                    strSql+=" and (a.ho_aut > '20:00:00' and c.ho_sal> '20:00:00')";
                                    strSql+=" and a.co_emp in (1,2,4)";
                                    strSql+=" and a.co_tipdoc=191";
                                    strSql+=" and a.st_reg like 'A'";
                                    strSql+=" and c.ho_sal is not null";
                                    strSql+=" order by co_emp, co_loc, co_tipdoc, co_doc, a.fe_sol, empleado)f";
                                    
                                    strSqlDet="";
                                    strSqlDet+="select co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_dep, co_mot, fe_sol, ho_sol, st_aut, ho_aut, co_tra, ho_ent, ho_sal, empleado from (select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_sol,";
                                    strSqlDet+=" a.st_aut, a.ho_aut, b.co_tra, c.ho_ent, c.ho_sal, (d.tx_ape || ' ' || d.tx_nom) as empleado from tbm_cabsolhorsupext a";
                                    strSqlDet+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                                    strSqlDet+=" inner join tbm_cabconasitra c on (a.fe_sol=c.fe_dia and b.co_tra=c.co_tra)";
                                    strSqlDet+=" inner join tbm_tra d on (b.co_tra=d.co_tra)";
                                    strSqlDet+=" where a.fe_sol = $P{parFeSol}";
                                    strSqlDet+=" and a.st_aut like 'T'";
                                    strSqlDet+=" and (a.ho_aut > '20:00:00' and c.ho_sal> '20:00:00')";
                                    strSqlDet+=" and a.co_emp in (1,2,4)";
                                    strSqlDet+=" and a.co_tipdoc=191";
                                    strSqlDet+=" and a.st_reg like 'A'";
                                    strSqlDet+=" and c.ho_sal is not null";
                                    strSqlDet+=" order by co_emp, co_loc, co_tipdoc, co_doc, a.fe_sol, empleado)f";
                                    //strSqlDet+=" group by co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_dep, co_mot, fe_sol, ho_sol, st_aut, ho_aut, co_tra, ho_ent, ho_sal, empleado";
                                    
                                    
                                }else{

                                    strSql="";
                                    strSql+="select distinct f.fe_sol from (";
                                    strSql+=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_sol, ";
                                    strSql+=" a.st_aut, a.ho_aut, b.co_tra, c.ho_ent, c.ho_sal, (d.tx_ape || ' ' || d.tx_nom) as empleado from tbm_cabsolhorsupext a";
                                    strSql+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                                    strSql+=" inner join tbm_cabconasitra c on (a.fe_sol=c.fe_dia and b.co_tra=c.co_tra)";
                                    strSql+=" inner join tbm_tra d on (b.co_tra=d.co_tra)";
                                    strSql+=" " + sqlFil + " ";
                                    strSql+=" and a.st_aut like 'T' ";
                                    strSql+=" and (a.ho_aut > '20:00:00' and c.ho_sal> '20:00:00')";
                                    strSql+=" and a.co_emp="+objZafParSis.getCodigoEmpresa();
                                    strSql+=" and a.co_loc="+objZafParSis.getCodigoLocal();
                                    strSql+=" and a.co_tipdoc=191";
                                    strSql+=" and a.st_reg like 'A'";
                                    strSql+=" and c.ho_sal is not null";
                                    strSql+=" order by co_emp, co_loc, co_tipdoc, co_doc, a.fe_sol, empleado)f";
                                    
                                    strSqlDet="";
                                    strSqlDet+="select co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_dep, co_mot, fe_sol, ho_sol, st_aut, ho_aut, co_tra, ho_ent, ho_sal, empleado from (select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.co_dep, a.co_mot, a.fe_sol, a.ho_sol,";
                                    strSqlDet+=" a.st_aut, a.ho_aut, b.co_tra, c.ho_ent, c.ho_sal, (d.tx_ape || ' ' || d.tx_nom) as empleado from tbm_cabsolhorsupext a";
                                    strSqlDet+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                                    strSqlDet+=" inner join tbm_cabconasitra c on (a.fe_sol=c.fe_dia and b.co_tra=c.co_tra)";
                                    strSqlDet+=" inner join tbm_tra d on (b.co_tra=d.co_tra)";
                                    strSqlDet+=" where a.fe_sol = $P{parFeSol}";
                                    strSqlDet+=" and a.st_aut like 'T'";
                                    strSqlDet+=" and (a.ho_aut > '20:00:00' and c.ho_sal> '20:00:00')";
                                    strSqlDet+=" and a.co_emp="+objZafParSis.getCodigoEmpresa();
                                    strSqlDet+=" and a.co_loc="+objZafParSis.getCodigoLocal();
                                    strSqlDet+=" and a.co_tipdoc=191";
                                    strSqlDet+=" and a.st_reg like 'A'";
                                    strSqlDet+=" and c.ho_sal is not null";
                                    strSqlDet+=" order by co_emp, co_loc, co_tipdoc, co_doc, a.fe_sol, empleado)f";
                                    
                                }

                                String[] strMeses={"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
                                String strRango="";
                                
                                Librerias.ZafDate.ZafDatePicker txtFecDoc;
                                
                                //txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                                //txtFecDoc.setHoy();
                                java.util.Calendar objFec = java.util.Calendar.getInstance();
                                //Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                                String strFecDes = objSelFec.getFechaDesde();
                                String fecDes[] = strFecDes.split("/");
                                if(strFecDes!=null){
                                    objFec.set(java.util.Calendar.DAY_OF_MONTH, Integer.valueOf(fecDes[0]));
                                    objFec.set(java.util.Calendar.MONTH, Integer.valueOf(fecDes[1])-1);
                                    objFec.set(java.util.Calendar.YEAR, Integer.valueOf(fecDes[2]));
                                    
                                    strRango+=objUti.formatearFecha(objFec.getTime(),"dd/MMM/yyyy");
                                    
                                }
                                
                                String strFecHas = objSelFec.getFechaHasta();
                                String fecHas[] = strFecHas.split("/");
                                if(strFecHas!=null){
                                    objFec.set(java.util.Calendar.DAY_OF_MONTH, Integer.valueOf(fecHas[0]));
                                    objFec.set(java.util.Calendar.MONTH, Integer.valueOf(fecHas[1])-1);
                                    objFec.set(java.util.Calendar.YEAR, Integer.valueOf(fecHas[2]));
                                    
                                    strRango+=" - " + objUti.formatearFecha(objFec.getTime(),"dd/MMM/yyyy");
                                    
                                }
                                
                                System.out.println("reporte vista: " + strSql);
                                System.out.println("reporte vista detalle: " + strSqlDet);
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strsql", strSql);
                                mapPar.put("strsqldet", strSqlDet);
                                mapPar.put("empresa", objZafParSis.getNombreEmpresa());
                                mapPar.put("rango", strRango);
                                mapPar.put("fecha", datFecAux);
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                
                                break;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}