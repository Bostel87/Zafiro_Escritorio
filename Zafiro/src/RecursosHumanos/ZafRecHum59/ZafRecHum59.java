package RecursosHumanos.ZafRecHum59;

import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Maestros.ZafMae07.ZafMae07_01;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Autorización de almuerzos
 * @author Roberto Flores
 * Guayaquil 26/04/2013
 */
public class ZafRecHum59 extends javax.swing.JInternalFrame {

    private static final int INT_TBL_DAT_LINEA =0;                                      //Índice de columna Linea.
    private static final int INT_TBL_DAT_COD_EMP=1;                                     //Índice de columna Día.
    private static final int INT_TBL_DAT_EMP=2;                                         //Indice de columna Codigo de Horario.
    private static final int INT_TBL_DAT_COD=3;                                         //Indice de columna Nombre de Horario.
    private static final int INT_TBL_DAT_NOM_TRA=4;                                     //Indice de columna ES LABORABLE.
    private static final int INT_TBL_DAT_CHK_AUT=5;                                     //Indice de columna Entrada.
    private static final int INT_TBL_DAT_OBS=6;                                         //Indice de columna Salida.
    private static final int INT_TBL_DAT_BUT_OBS =7;                                    //Indice de columna Minutos de Gracia.
    private static final int INT_TBL_DAT_NDVALALMCON =8;                                    //Indice de columna Minutos de Gracia.

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                                        //Editor: Editor del JTable.
    private ZafUtil objUti;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                                         //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                                         //Renderer de Check Box para campo Laborable
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;                                                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                                  //PopupMenu: Establecer PeopuMenú en JTable.

    private ZafTblCelRenLbl objTblCelRenLbl;                                            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                            //Editor: JTextField en celda.

    Vector vecCab=new Vector();                                                         //Vector que contiene la cabecera del Table.

    private boolean blnMod;                                                             //Indica si han habido cambios en el documento
    private boolean blnConsDat;
    
    private Librerias.ZafDate.ZafDatePicker txtFec;
    
    /** Creates new form pantalla1 */
    public ZafRecHum59(Librerias.ZafParSis.ZafParSis obj) {
        try{
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            
            txtFec = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
              txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFec.setPreferredSize(new java.awt.Dimension(70, 20));
              //txtFec.setText("");
              panCab.add(txtFec);
              txtFec.setBounds(120, 40, 92, 20);
              //txtFecDoc.setEnabled(false);
              //txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFec.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
              txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFec.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      txtFecActionPerformed(evt);
                  }

                private void txtFecActionPerformed(ActionEvent evt) {
                    txtFec.transferFocus();
                }
              });
	    
	    
            this.setTitle(objZafParSis.getNombreMenu()+"  v 1.0 ");
            lblTit.setText(objZafParSis.getNombreMenu());
            
	    objUti = new ZafUtil();
	    
            configuraTbl();
            tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));
            
            blnMod = false;
            blnConsDat = false;

	 }catch (CloneNotSupportedException e){
               objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        vecCab.add(INT_TBL_DAT_LINEA,"");
        vecCab.add(INT_TBL_DAT_COD_EMP,"Cód. Emp.");
        vecCab.add(INT_TBL_DAT_EMP,"Empresa");
        vecCab.add(INT_TBL_DAT_COD,"Código");
        vecCab.add(INT_TBL_DAT_NOM_TRA,"Empleado");
        vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
        vecCab.add(INT_TBL_DAT_OBS,"Observación");
        vecCab.add(INT_TBL_DAT_BUT_OBS,"...");
        vecCab.add(INT_TBL_DAT_NDVALALMCON,"Val. Alm.");

        objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);
            
        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LINEA);
    	
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
	    
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_DAT_CHK_AUT);
        vecAux.add("" + INT_TBL_DAT_BUT_OBS);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblDat);
            
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
        
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_DAT_COD).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_SUG).setCellRenderer(objTblCelRenLbl);
        //objTblCelRenLbl=null;

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_DAT_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DAT_EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_COD).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_NOM_TRA).setPreferredWidth(360);
        tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);

        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(zafTblCelRenChkLab);

        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(zafTblCelEdiChkLab);
             
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NDVALALMCON, tblDat);
            
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(zafTblDocCelRenBut);
        ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_OBS, "Observación") {
            public void butCLick() {
                int intSelFil = tblDat.getSelectedRow();
                String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS).toString());
                ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum59.this), true, strObs);
                zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_OBS);
                    }
            }
        };
            
        Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
        Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        objTblCelRenChk=null;
        objTblCelEdiChk=null; 
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

        blnRes=true;
    }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public static java.sql.Time SparseToTime(String hora){
    int h, m, s;

    h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
    m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
    s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
    return (new java.sql.Time(h,m,s));
}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        panBot = new javax.swing.JPanel();
        panButPanBut = new javax.swing.JPanel();
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

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilTabGen.setLayout(new java.awt.BorderLayout());

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

        panFilTabGen.add(scrTbl, java.awt.BorderLayout.CENTER);

        panCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panCab.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Fecha:"); // NOI18N
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(10, 38, 120, 20);

        panFilTabGen.add(panCab, java.awt.BorderLayout.PAGE_START);

        tabGen.addTab("General", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panButPanBut.add(butCon);

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(79, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panButPanBut.add(butGua);

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(79, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panButPanBut.add(butCer);

        panBot.add(panButPanBut, java.awt.BorderLayout.EAST);

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

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed

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
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

private class ZafThreadGUI extends Thread
    {
     public void run(){

         lblMsgSis.setText("Obteniendo datos...");
         pgrSis.setIndeterminate(true);

         consultarDat();

         objThrGUI=null;
    }
}
    
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
    
        java.sql.Connection conn = null;
        try{
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null){
                conn.setAutoCommit(false);
                if(guardarDat(conn)){
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito");
                    blnMod = false;
                    conn.commit();
                }else{
                    conn.rollback();
                }
            }
        }catch(java.sql.SQLException Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
       }catch(Exception Evt) {
           Evt.printStackTrace();
           objUti.mostrarMsgErr_F1(this, Evt);
      }finally {
             try{conn.close();conn=null;}catch(Throwable ignore){}
         }
    }//GEN-LAST:event_butGuaActionPerformed

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

private boolean guardarDat(Connection conn){
 boolean blnRes=true;
 
    Statement stmLoc = null;
    ResultSet rstLoc = null;
 String strSql="";
 boolean blnMReg = false;
 try{

     stmLoc=conn.createStatement();
     Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            
    String patron = "dd/MM/yyyy";
    SimpleDateFormat formato = new SimpleDateFormat(patron);
    System.out.println(formato.format(datFecAux));
    String strSplit[] = formato.format(datFecAux).split("/");
    Calendar calObjAux = java.util.Calendar.getInstance();
    calObjAux.set(Integer.valueOf(strSplit[2]), Integer.valueOf(strSplit[1])-1, Integer.valueOf(strSplit[0]));
    java.util.Date datFecAuxSis=calObjAux.getTime();

    java.util.Calendar calObj = java.util.Calendar.getInstance();
    int Fecha[] =  txtFec.getFecha(txtFec.getText());
    //String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
    calObj.set(Fecha[2], Fecha[1]-1, Fecha[0]);
    java.util.Date datFecSolIng=calObj.getTime();

    long dif = datFecAuxSis.getTime() - datFecSolIng.getTime(); 
      
    if(dif>=0){
        tabGen.setSelectedIndex(0);
        System.out.println("Dias entre fechas: " + dif / 86400000L); // 3600 * 24 * 1000 
        mostrarMsgInf("<HTML>No se permite la autorización de <FONT COLOR=\"blue\">Almuerzos</FONT> pasados.</HTML>");
        txtFec.requestFocus();
        return false;
    }else{
        
        String strArrFeDia[]=txtFec.getText().split("/");
        String strFeDia=strArrFeDia[2] + "-" + strArrFeDia[1] + "-" + strArrFeDia[0];
        
        for(int i=0; i<tblDat.getRowCount(); i++){
          
          if(tblDat.getValueAt(i, INT_TBL_DAT_LINEA).toString().compareTo("M")==0){
              blnMReg=true;
              if(tblDat.getValueAt(i, INT_TBL_DAT_CHK_AUT).equals(true)){
                  
                  strSql="";
                  strSql+="select * from tbm_cabconasitra";
                  strSql+=" where fe_dia = " + objUti.codificar(strFeDia);
                  strSql+=" and  co_tra = " + tblDat.getValueAt(i, INT_TBL_DAT_COD).toString();
                  rstLoc=stmLoc.executeQuery(strSql);
                  boolean blnExi=false;
                  if(rstLoc.next()){
                      blnExi=true;
                  }
                  
                  if(!blnExi){
                      
                      strSql="";
                      strSql="insert into tbm_cabconasitra (co_tra, fe_dia, nd_valalm, st_autalm, tx_obsautalm, fe_autalm, co_usrautalm, tx_comautalm)";
                      strSql+=" values (";
                      strSql+=" " + tblDat.getValueAt(i, INT_TBL_DAT_COD).toString() + " , ";
                      strSql+=" " + objUti.codificar(strFeDia) + " , ";
                      strSql+=" " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_NDVALALMCON)) + " , ";
                      strSql+=" 'S' , ";
                      strSql+=" " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                      strSql+=" current_timestamp , ";
                      strSql+=" " + objZafParSis.getCodigoUsuario() + " , ";
                      strSql+=" " + objUti.codificar(objZafParSis.getDireccionIP()) + " ); ";
                      System.out.println("insertTbm_cabconasitra: " + strSql);
                      stmLoc.executeUpdate(strSql);
                      
                  }else{
                      
                      strSql="";
                      strSql+="update tbm_cabconasitra set nd_valalm = " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_NDVALALMCON)) + " , ";
                      strSql+=" st_autalm = 'S' , ";
                      strSql+=" tx_obsautalm = " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                      strSql+=" fe_autalm = current_timestamp , ";
                      strSql+=" co_usrautalm = " + objZafParSis.getCodigoUsuario() + " , ";
                      strSql+=" tx_comautalm = " + objUti.codificar(objZafParSis.getDireccionIP()) + " ";
                      strSql+=" WHERE co_tra = " + tblDat.getValueAt(i, INT_TBL_DAT_COD).toString()+ " ";
                      strSql+=" AND fe_dia = " + objUti.codificar(strFeDia) + " ";
                      System.out.println("updateTbm_cabconasitra: " + strSql);
                      stmLoc.executeUpdate(strSql);
                      
                  }
              }
          }
        }
    }
 }catch(java.sql.SQLException Evt) {
       blnRes = false;
       Evt.printStackTrace();
       objUti.mostrarMsgErr_F1(this, Evt);
  }catch(Exception Evt) {
      blnRes = false;
      Evt.printStackTrace();
      objUti.mostrarMsgErr_F1(this, Evt);
 }finally {
        try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
    }

 if(!blnMReg){
     mostrarMsgInf("No se han realizado cambios para que sean guardados");
     blnRes=false;
 }
    

 return blnRes;
}

private boolean consultarDat(){
    blnConsDat = true;
    boolean blnRes=false;
    java.sql.Connection conn;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="",sqlFil="";
    Vector vecData;
    try{
        conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(conn!=null){
            stmLoc=conn.createStatement();
            vecData = new Vector();
            String strArrFec[]=txtFec.getText().split("/");
            String strFecha=strArrFec[2] + "-" + strArrFec[1] + "-" +strArrFec[0];
            
            strSql="SELECT c.co_emp,c.tx_nom as empresa,e.fe_dia,b.co_tra, (a.tx_ape || ' ' || a.tx_nom) as empleado, false as blnAut, d.tx_forRecAlm,\n" +
                    "d.nd_valAlm, d.st_autAlm, d.tx_obsAutAlm, d.fe_autAlm, d.co_usrAutAlm, d.tx_comAutAlm, b.nd_valalm as ndValAlmCon\n" +
                    "from tbm_tra a\n" +
                    "INNER JOIN tbm_traemp b on (b.co_tra=a.co_tra and b.st_reg='A')\n" +
                    "INNER JOIN tbm_emp c on (c.co_emp=b.co_emp)\n" +
                    "INNER JOIN tbm_calciu e on (e.co_ciu=1)\n" +
                    "LEFT OUTER JOIN tbm_cabconasitra d on (d.co_tra=b.co_tra and d.fe_dia=e.fe_dia)\n" +
                    "WHERE e.fe_dia="+objUti.codificar(strFecha)+"\n" +
                    "AND b.st_recAlm='S'\n" +
                    "AND b.tx_forRecAlm='C'\n" +
                    "ORDER BY c.co_emp, empleado";
            System.out.println(""+strSql);
            rstLoc=stmLoc.executeQuery(strSql);
            
            int i = 0;
            while(rstLoc.next()){

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LINEA,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_DAT_EMP, rstLoc.getString("empresa") );
                    vecReg.add(INT_TBL_DAT_COD, rstLoc.getString("co_tra") );
                    vecReg.add(INT_TBL_DAT_NOM_TRA, rstLoc.getString("empleado") );
                    if(rstLoc.getString("st_autAlm")!=null){
                        if(rstLoc.getString("st_autAlm").compareTo("S")==0){
                            vecReg.add(INT_TBL_DAT_CHK_AUT, Boolean.TRUE );
                        }else{
                            vecReg.add(INT_TBL_DAT_CHK_AUT, Boolean.FALSE );
                        }
                    }else{
                        vecReg.add(INT_TBL_DAT_CHK_AUT, Boolean.FALSE );
                    }
                    
                    
                    vecReg.add(INT_TBL_DAT_OBS, rstLoc.getString("tx_obsAutAlm") );
                    vecReg.add(INT_TBL_DAT_BUT_OBS, ".." );
                    vecReg.add(INT_TBL_DAT_NDVALALMCON, rstLoc.getString("nd_valAlm") );
                    vecData.add(vecReg);

            }

            objTblMod.setData(vecData);
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
        }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panButPanBut;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilTabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
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
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_CHK_AUT:
                    strMsg="Autorizar";
                    break; 
                case INT_TBL_DAT_OBS:
                    strMsg="Observación de la autorización";
                    break;
                case INT_TBL_DAT_BUT_OBS:
                    strMsg="Observación de la autorización";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}