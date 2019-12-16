/*
 * ZafMae.java
 *
 * Created on 27 de Junio de 2019 
 */
package Maestros.ZafMae74;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenCon.ZafVenConItm01;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet; 
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Mario
 */
public class ZafMae74 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_2=4;                   //Código del item (Alterno 2).
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_CHK_SER=6;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_UNI=7;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_DAT_DEC_UNI=8;                     //Descripción corta de la unidad de medida.
    
    static final int INT_TBL_DAT_CDI_AUT=9;
    
    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_TBL_DAT_NUM_TOT_CES=7;         //Número total de columnas estáticas.
    
    private static final int INT_TBL_DAT_NUM_TOT_CDI=5;         //Número de columnas dinámicas por usuario. ( codGrupo nomGrupo nombreLargo boton CodUsrDueCla)
    private static final int INT_TBL_DAT_FAC_NUM_CDI=1;         //Factor para calcular total de columnas dinámicas. 
     
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
     
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
     
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
     
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafVenConItm01 objVenConItm01;                //Librerio Filtro Ing
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    
    private String strSql, strConSQL, strAux, strVersion="v 0.05";
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                       //Contenido del campo al obtener el foco.
    
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    
    
    private int intNumColEst; 
   
    /** Crea una nueva instancia de la clase ZafCom18. */
    public ZafMae74(ZafParSis obj){
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {  //AKI SE VALIDA QUE SOLO SE PUEDA ABRIR POR G
                initComponents();
                configurarFrm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
           
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFil1 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        chkMosDesLar = new javax.swing.JCheckBox();
        panUsr = new javax.swing.JPanel();
        panLoc = new javax.swing.JPanel();
        spnUsr = new javax.swing.JScrollPane();
        tblUsr = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        newGrupo = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panFil1.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil1.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil1.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        chkMosDesLar.setSelected(true);
        chkMosDesLar.setText("Mostrar las dos descripciones del grupo");
        panFil1.add(chkMosDesLar);
        chkMosDesLar.setBounds(10, 120, 280, 23);

        panFil.add(panFil1, java.awt.BorderLayout.CENTER);

        panUsr.setPreferredSize(new java.awt.Dimension(100, 170));
        panUsr.setLayout(new java.awt.BorderLayout());

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Usuarios"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

        tblUsr.setModel(new javax.swing.table.DefaultTableModel(
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
        spnUsr.setViewportView(tblUsr);

        panLoc.add(spnUsr, java.awt.BorderLayout.CENTER);

        panUsr.add(panLoc, java.awt.BorderLayout.CENTER);

        panFil.add(panUsr, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panFil);

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

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        newGrupo.setText("Clasificación");
        newGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGrupoActionPerformed(evt);
            }
        });
        panBot.add(newGrupo);

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (actualizarDet())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()){
            objVenConItm01.limpiar();
//            txtCodItm.setText("");
//            txtCodAlt.setText("");
//            txtNomItm.setText("");
//            txtCodAltDes.setText("");
//            txtCodAltHas.setText("");
//            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if(objTblModUsr.isCheckedAnyRow(INT_TBL_USR_CHK)){
            if (objTblMod.isDataModelChanged()){
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?, existen modificaciones en la tabla... ")==0){
                    if (butCon.getText().equals("Consultar")){
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
                }
            }
            else{
                if (butCon.getText().equals("Consultar")){
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
            }
        }
        else{
            mostrarMsgInf("No ha seleccionado ningun usuario...");
        }
        
        
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void newGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGrupoActionPerformed
        // TODO add your handling code here:
        if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?, existen modificaciones en la tabla... ")==0){
                if(crearNuevoGrupo()){
                    limpiarDetFrm();
                    butCon.doClick();
                }   
            }
        }
        else{
            if(crearNuevoGrupo()){
                limpiarDetFrm();
                butCon.doClick();
            }
        }
    }//GEN-LAST:event_newGrupoActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JCheckBox chkMosDesLar;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JButton newGrupo;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panUsr;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnUsr;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblUsr;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            
              //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItm01(objParSis, "a1.", optFil);
            panFil1.add(objVenConItm01);
            objVenConItm01.setBounds(24, 44, 656, 20);
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
            //txtCodItm.setVisible(false);
             
            //Configurar las ZafVenCon.
            configurarVenConItm();
            configurarTblUsr();
            cargarUsr();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(20);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_CHK_SER,"Servicio");
            vecCab.add(INT_TBL_DAT_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
 
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
//            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilEli[];

                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilEli=tblDat.getSelectedRows();
                    //System.out.println("intFilEli: " + intFilEli.length);
                }
                
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    System.out.println("afterClick: " + intFilEli.length); //no comentar esta linea
                    boolean encuentra=false;
                     
                    if (objTblPopMnu.isClickPegar()){
                        intFilEli=tblDat.getSelectedRows();
                        for(int intCol=intColIni;intCol<objTblMod.getColumnCount();intCol=intCol+INT_TBL_DAT_NUM_TOT_CDI){ /*JM: Busco el vendedor en las dinamicas */
                            if (tblDat.isColumnSelected(intCol+1)){ /* Se la columna seleccionada es el texto en las dinamicas */
                                for (int i=0; i<intFilEli.length; i++){
                                    if(buscarGrupo(intFilEli[i], intCol+1)){
                                        System.out.println("Encontrado!");
                                        encuentra=true;
                                    }
                                    else{
                                        encuentra=false;
                                        objTblMod.setValueAt("" , intFilEli[i], intCol  );
                                        objTblMod.setValueAt("", intFilEli[i], intCol+1 );
                                    }
                                }
                            }
                        }
                        if(encuentra==false){
                            mostrarMsgInf("No existe ese grupo, en esta clasificacion");
                        }
                    }
                }
                 
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(47);
             
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_UNI, tblDat);
             
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_LIB);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
 
            
            
 
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;
             
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEst=objTblMod.getColumnCount();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
     
    
    
    
    /**
     * Esta función permite limpiar el detalle del formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    public void limpiarDetFrm(){
       objTblMod.removeAllRows();
       objTblMod.setDataModelChanged(false);
       eliminaColumnasAdicionadas();
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if(eliminaColumnasAdicionadas()) {
                if(obtenerColumnasAdicionar()) {
                    if(agregarColTblDat()) {
                        lblMsgSis.setText("Cargando datos...");
                        if(setDatosItemsVSClasificaciones()){
                            objTblMod.initRowsState();
                            blnRes=true;
                             
                        }
                    }
                }
            }
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false); 
             
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que generqa las filas de clientes segun el filtro
     */ 
    private boolean setDatosItemsVSClasificaciones(){
        boolean blnRes=true; 
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCodItm="ERROR", strAux2="";
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                //Obtener la condición.
                strConSQL="";
             //-----------------------------------------------------------------------------------------------------------
                   if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
//                if (txtCodItm.getText().length()>0)
//                    strConSQL+=" AND a1.co_itm=" + txtCodItm.getText();
//                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
//                    strConSQL+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                if (txtCodAltItmTer.getText().length()>0)
//                {
//                    strConSQL+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
//                }
 //--------------------------------------------------------------------------------------------------------------------------------------               
                //Obtener los locales a consultar.
                int intNumFilTblBod=objTblModUsr.getRowCountTrue();
                int i=0;
                strAux="";
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModUsr.isChecked(j, INT_TBL_USR_CHK)){
                        if (i==0)
                            strAux+=" (a5.co_usrDueCla=" + objTblModUsr.getValueAt(j, INT_TBL_USR_COD)+ ")";
                        else
                            strAux+=" OR (a5.co_usrDueCla=" + objTblModUsr.getValueAt(j, INT_TBL_USR_COD) +  ")";
                        i++;
                    }
                }
                if (!strAux.equals("")){
                    strAux2=" AND (" + strAux + ")";
                }
                
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.st_ser, a1.co_uni, a2.tx_desCor as tx_desCorUni,  \n";
                strSql+="        a5.co_claModInv, a5.tx_desCor, a5.tx_desLar as tx_desClasificacion , a4.co_grp, a4.tx_desCor as tx_desCorGrupo, a4.tx_desLar as tx_desLarGrupo \n";
                strSql+=" FROM tbm_inv as a1  \n";
                strSql+=" LEFT OUTER JOIN tbm_var as a2  ON (a1.co_uni=a2.co_reg) \n";
                strSql+=" LEFT OUTER JOIN tbr_grpClaModInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) \n";
                strSql+=" LEFT OUTER JOIN tbm_grpClaModInv as a4 ON (a3.co_emp=a4.co_emp AND a3.co_grp=a4.co_grp)  \n";
                strSql+=" LEFT OUTER JOIN tbm_claModInv as a5 ON (a4.co_emp=a5.co_emp AND a4.co_claModInv=a5.co_claModInv "+strAux2+" )  \n";
                strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" and a1.st_reg='A' and a1.st_ser='N'   \n";
                strSql+=strConSQL;
                strSql+=" ORDER BY a1.tx_codAlt \n";
                System.out.println("setDatosItemsVSClasificaciones \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(!(strCodItm.equals(rstLoc.getString("co_itm"))) ){
                        strCodItm = rstLoc.getString("co_itm"); 
                        objTblMod.insertRow();
                    }
                    
                    objTblMod.setValueAt( "", (objTblMod.getRowCount()-1) , INT_TBL_DAT_LIN );
                    objTblMod.setValueAt( rstLoc.getString("co_emp"),(objTblMod.getRowCount()-1), INT_TBL_DAT_COD_EMP);
                    objTblMod.setValueAt( rstLoc.getString("co_itm"),(objTblMod.getRowCount()-1), INT_TBL_DAT_COD_SIS);
                    
                    objTblMod.setValueAt( rstLoc.getString("tx_codAlt"),(objTblMod.getRowCount()-1), INT_TBL_DAT_COD_ALT);
                    objTblMod.setValueAt( rstLoc.getString("tx_codAlt2"),(objTblMod.getRowCount()-1), INT_TBL_DAT_COD_ALT_2);
                    objTblMod.setValueAt( rstLoc.getString("tx_nomItm"),(objTblMod.getRowCount()-1), INT_TBL_DAT_NOM_ITM);
                    if(rstLoc.getString("st_ser").equals("N")){
                        objTblMod.setValueAt( false,(objTblMod.getRowCount()-1), INT_TBL_DAT_CHK_SER);
                    }
                    else{
                        objTblMod.setValueAt( true,(objTblMod.getRowCount()-1), INT_TBL_DAT_CHK_SER);
                    }
                    objTblMod.setValueAt( rstLoc.getString("co_uni"),(objTblMod.getRowCount()-1), INT_TBL_DAT_COD_UNI);
                    objTblMod.setValueAt( rstLoc.getString("tx_desCorUni"),(objTblMod.getRowCount()-1), INT_TBL_DAT_DEC_UNI);
                    
                    
                    //Columnas Dinámicas (Debe ser la misma clasificacion y el mismo grupo ahi pongo dato del grupo en la columna de la clasificacion)
                    for(int j=(INT_TBL_DAT_CDI_AUT); j< objTblMod.getColumnCount(); j=j+INT_TBL_DAT_NUM_TOT_CDI){
                        if(rstLoc.getInt("co_claModInv")==Integer.parseInt(objTblMod.getColumnName(j))){
                            objTblMod.setValueAt(rstLoc.getString("co_grp") , (objTblMod.getRowCount()-1) , (j) );
                            objTblMod.setValueAt(rstLoc.getString("tx_desCorGrupo") , (objTblMod.getRowCount()-1) , (j+1) );
                            objTblMod.setValueAt(rstLoc.getString("tx_desLarGrupo") , (objTblMod.getRowCount()-1) , (j+2) );
                            objTblMod.setValueAt("" , (objTblMod.getRowCount()-1) , (j+3) );
                        }
                        else{
                            objTblMod.setValueAt(""+objTblMod.getValueAt((objTblMod.getRowCount()-1) , (j))==null?"":objTblMod.getValueAt((objTblMod.getRowCount()-1) , (j))  , (objTblMod.getRowCount()-1) , (j) );
                            objTblMod.setValueAt(""+objTblMod.getValueAt((objTblMod.getRowCount()-1) , (j+1))==null?"":objTblMod.getValueAt((objTblMod.getRowCount()-1) , (j+1))  , (objTblMod.getRowCount()-1) , (j+1) );
                            objTblMod.setValueAt(""+objTblMod.getValueAt((objTblMod.getRowCount()-1) , (j+1))==null?"":objTblMod.getValueAt((objTblMod.getRowCount()-1) , (j+2))  , (objTblMod.getRowCount()-1) , (j+2) );
                            objTblMod.setValueAt("" , (objTblMod.getRowCount()-1) , (j+3) );
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e){
            blnRes=false;
             
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){        
            blnRes=false;         
             
            objUti.mostrarMsgErr_F1(this, e);        
        }
        return blnRes;
        
        
        
    }     
    
    
     
    /**
     * aki carga todas las clasificaciones de items que tengas
     * @return 
     */ 
    private boolean obtenerColumnasAdicionar(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                //Obtener los locales a consultar.
                int intNumFilTblBod=objTblModUsr.getRowCountTrue();
                int i=0;
                strAux="";
                strConSQL="";
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModUsr.isChecked(j, INT_TBL_USR_CHK)){
                        if (i==0)
                            strAux+=" (a1.co_usrDueCla=" + objTblModUsr.getValueAt(j, INT_TBL_USR_COD)+ ")";
                        else
                            strAux+=" OR (a1.co_usrDueCla=" + objTblModUsr.getValueAt(j, INT_TBL_USR_COD) +  ")";
                        i++;
                    }
                }
                if (!strAux.equals("")){
                    strConSQL+=" AND (" + strAux + ")";
                }
                
                /* Agrega columnas automáticamente de acuerdo a la cantidad de usuarios. */
                strSql="";
                strSql+=" SELECT COUNT(a1.co_claModInv) as cantidadClasificaciones   \n";
                strSql+=" FROM tbm_claModInv as a1 \n";
                strSql+=" WHERE co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.st_reg='A' \n";
                strSql+="      "+strConSQL;
                System.out.println("obtenerColumnasAdicionar 1 " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()) 
                {
                    intNumColGrpUsrAdi=rstLoc.getInt("cantidadClasificaciones");
                }
                rstLoc.close();                         
                rstLoc=null;

                /* Nombres de Usuarios */ 
                strSql ="";
                strSql+=" SELECT a1.co_emp, a1.co_claModInv, a1.tx_desCor, a1.tx_desLar,a1.co_usrDueCla  \n";
                strSql+=" FROM tbm_claModInv as a1  \n";
                strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo() + " AND a1.st_reg='A' ";
                strSql+="      "+strConSQL;
                strSql+=" ORDER BY a1.co_emp, a1.co_claModInv \n";
                 System.out.println("obtenerColumnasAdicionar 2 " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                arlDatCla =new ArrayList();
                while(rstLoc.next()){
                    arlRegCla= new ArrayList();
                    arlRegCla.add(INT_ARL_DAT_CLA_COD_CLA,rstLoc.getString("co_claModInv"));
                    arlRegCla.add(INT_ARL_DAT_CLA_NOM_CLA,rstLoc.getString("tx_desCor"));
                    arlRegCla.add(INT_ARL_DAT_CLA_NOM_LAR_CLA,rstLoc.getString("tx_desLar"));
                    arlRegCla.add(INT_ARL_DAT_CLA_BTN_CLA,"...");
                    arlRegCla.add(INT_ARL_DAT_CLA_USR, rstLoc.getString("co_usrDueCla"));
                    arlDatCla.add(arlRegCla);
                }
                rstLoc.close();                         
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (Exception e){         
            blnRes = false;     
            objUti.mostrarMsgErr_F1(this, e);      
        }
        return blnRes;
   }
    
    private ArrayList arlDatCla, arlRegCla;
    private static final int INT_ARL_DAT_CLA_COD_CLA=0;
    private static final int INT_ARL_DAT_CLA_NOM_CLA=1;
    private static final int INT_ARL_DAT_CLA_NOM_LAR_CLA=2;
    private static final int INT_ARL_DAT_CLA_BTN_CLA=3;
    private static final int INT_ARL_DAT_CLA_USR=4;
    
    
     
   
     
    
    /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intNumFil, intCodCla;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=true;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                conLoc.setAutoCommit(false);
                stmLoc=conLoc.createStatement();
                //Obtener la fecha del servidor.
                 
                intNumFil=objTblMod.getRowCountTrue();
                       
                for (int intFil=0; intFil<intNumFil;intFil++){/*JM: Todas las filas */
                    if (objUti.parseString(objTblMod.getValueAt(intFil,0)).equals("M")){/*JM: Las filas modificadas */
                        //Armar la sentencia SQL.
                        for(int intCol=INT_TBL_DAT_CDI_AUT;intCol<objTblMod.getColumnCount();intCol=intCol+INT_TBL_DAT_NUM_TOT_CDI){ /*JM: Busco el vendedor en las dinamicas */
                            if(objTblMod.getValueAt(intFil,intCol)!=null){
                                if(objTblMod.getValueAt(intFil,intCol).toString().length()>0){
                                    intCodCla = Integer.parseInt(objTblMod.getColumnName(intCol));
                                    strSql="";
                                    strSql+=" SELECT a1.* ";
                                    strSql+=" FROM tbr_grpClaModInv as a1 \n";
                                    strSql+=" INNER JOIN tbm_grpClaModInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp ) \n";
                                    strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_itm="+objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_SIS)+" AND   \n ";
                                    strSql+="       a2.co_claModInv="+intCodCla;
                                    System.out.println("1 " + strSql);
                                    rstLoc = stmLoc.executeQuery(strSql);
                                    if(rstLoc.next()){
                                        strSql="";
                                        strSql+=" UPDATE tbr_grpClaModInv SET co_grp="+objTblMod.getValueAt(intFil,intCol)+", co_usrMod="+objParSis.getCodigoUsuario()+", ";
                                        strSql+=" fe_ultMod=CURRENT_TIMESTAMP ";
                                        strSql+=" WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_grp="+rstLoc.getInt("co_grp")+" AND co_itm="+rstLoc.getInt("co_itm")+";  ";
                                        System.out.println("2 " + strSql);
                                        stmLoc.executeUpdate(strSql);
                                    }
                                    else{
                                        strSql="";
                                        strSql+=" INSERT INTO tbr_grpClaModInv (co_emp, co_grp, co_itm, st_reg, fe_ing, co_usrIng) ";
                                        strSql+=" VALUES ( "+objParSis.getCodigoEmpresaGrupo()+", "+objTblMod.getValueAt(intFil,intCol)+", "+objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_SIS)+", ";
                                        strSql+=" 'A', CURRENT_TIMESTAMP, "+objParSis.getCodigoUsuario()+" ) ; ";
                                        System.out.println("3 " + strSql);
                                        stmLoc.executeUpdate(strSql);
                                    }
                                    rstLoc.close();
                                    rstLoc=null;
                                }
                            }
                            
                        }
                    }
                }
                
                stmLoc.close();
                stmLoc=null;
                 
                if(blnRes){
                    conLoc.commit();
                }
                else{
                    conLoc.rollback();
                }
                conLoc.close();
                conLoc=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
 
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
//    private boolean mostrarVenConItm(int intTipBus)
//    {
//        boolean blnRes=true;
//        try
//        {
//            switch (intTipBus)
//            {
//                case 0: //Mostrar la ventana de consulta.
//                    vcoItm.setCampoBusqueda(1);
//                    vcoItm.setVisible(true);
//                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                    {
//                        txtCodItm.setText(vcoItm.getValueAt(1));
//                        txtCodAlt.setText(vcoItm.getValueAt(2));
//                        txtNomItm.setText(vcoItm.getValueAt(3));
//                    }
//                    break;
//                case 1: //Búsqueda directa por "Codigo alterno".
//                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
//                    {
//                        txtCodItm.setText(vcoItm.getValueAt(1));
//                        txtCodAlt.setText(vcoItm.getValueAt(2));
//                        txtNomItm.setText(vcoItm.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoItm.setCampoBusqueda(1);
//                        vcoItm.setCriterio1(11);
//                        vcoItm.cargarDatos();
//                        vcoItm.setVisible(true);
//                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodItm.setText(vcoItm.getValueAt(1));
//                            txtCodAlt.setText(vcoItm.getValueAt(2));
//                            txtNomItm.setText(vcoItm.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtCodAlt.setText(strCodAlt);
//                        }
//                    }
//                    break;
//                case 2: //Búsqueda directa por "Nombre del item".
//                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText())){
//                        txtCodItm.setText(vcoItm.getValueAt(1));
//                        txtCodAlt.setText(vcoItm.getValueAt(2));
//                        txtNomItm.setText(vcoItm.getValueAt(3));
//                    }
//                    else{
//                        vcoItm.setCampoBusqueda(2);
//                        vcoItm.setCriterio1(11);
//                        vcoItm.cargarDatos();
//                        vcoItm.setVisible(true);
//                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodItm.setText(vcoItm.getValueAt(1));
//                            txtCodAlt.setText(vcoItm.getValueAt(2));
//                            txtNomItm.setText(vcoItm.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomItm.setText(strNomItm);
//                        }
//                    }
//                    break;
//            }
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
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
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            limpiarDetFrm();
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="Para crear una nueva Clasificacion click en la ultima columna... ";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código Empresa";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_2:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CHK_SER:
                    strMsg="Servicio";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
 
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
     /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--){
                objTblMod.removeColumn(tblDat, i);                
            }
        }
        catch(Exception e){  
             
            objUti.mostrarMsgErr_F1(this, e);  
            blnRes=false;      
        }
        return blnRes;
    
    }
    private int intNumColGrpUsrAdi=0;
    private int intColIni, intColFin; 
    private ZafTblCelRenLbl objTblCelRenLblNom;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelEdiButGen objTblCelEdiBut;
    
    private ZafTblCelEdiTxt objTblCelEdiLblNom;    //Editor: TextField en celda.
    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo a la cantidad de usuarios".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;  // CODIGO GRUPO
        javax.swing.table.TableColumn tbc1;  // DESCRIPCION CORTA DEL GRUPO 
        javax.swing.table.TableColumn tbc1_1; // DESCRIPCION LARGA DEL GRUPO 
        javax.swing.table.TableColumn tbc2; // BOTON 
        javax.swing.table.TableColumn tbc3; // USUARIO DUEÑO
        try{
            intColIni=objTblMod.getColumnCount();
            int intIndColGrp =(intColIni*INT_TBL_DAT_FAC_NUM_CDI); //Obtengo Indice de la Columna.
            
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_EMP);
            arlColHid.add(""+INT_TBL_DAT_COD_SIS);
            arlColHid.add(""+INT_TBL_DAT_COD_UNI);
            Vector vecAux=new Vector();
   
            for(int i=0; i<(intNumColGrpUsrAdi  /* *INT_TBL_DAT_NUM_TOT_CDI */); i++){ //Cantidad de usuarios*Número de columnas dinámicas por usuario.
                //Creo las columna dinamica.  
                tbc=new javax.swing.table.TableColumn(intIndColGrp);           /* Codigo del grupo */      
                tbc.setHeaderValue(objUti.getStringValueAt(arlDatCla, i, INT_ARL_DAT_CLA_COD_CLA));
                arlColHid.add(""+intIndColGrp);//columnas ocultas
                
                intIndColGrp++;
                tbc1=new javax.swing.table.TableColumn((intIndColGrp));    /* Nombre del grupo */                   
                tbc1.setHeaderValue(objUti.getStringValueAt(arlDatCla, i, INT_ARL_DAT_CLA_NOM_CLA));
                vecAux.add("" + (intIndColGrp)); //Configurar JTable: Establecer columnas editables.
                
                intIndColGrp++;
                tbc1_1=new javax.swing.table.TableColumn((intIndColGrp));    /* Nombre del grupo */                   
                tbc1_1.setHeaderValue(objUti.getStringValueAt(arlDatCla, i, INT_ARL_DAT_CLA_NOM_LAR_CLA));
                if(!chkMosDesLar.isSelected()){  /* NO esta seleccionado entonces oculta esto */
                    arlColHid.add(""+intIndColGrp);
                }
                
                intIndColGrp++;        
                tbc2=new javax.swing.table.TableColumn((intIndColGrp));     /* Boton para elegir el grupo */                 
                tbc2.setHeaderValue(objUti.getStringValueAt(arlDatCla, i, INT_ARL_DAT_CLA_BTN_CLA));               
                vecAux.add("" + (intIndColGrp)); //Configurar JTable: Establecer columnas editables.
                
                intIndColGrp++;
                tbc3=new javax.swing.table.TableColumn(intIndColGrp);           /* JM Codigo del Usuario, para validaciones */      
                tbc3.setHeaderValue(objUti.getStringValueAt(arlDatCla, i, INT_ARL_DAT_CLA_USR));
                arlColHid.add(""+intIndColGrp);//columnas ocultas 
                
                intIndColGrp++;
                 
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                tbc.setResizable(false);
                tbc1.setPreferredWidth(100);
                tbc1.setResizable(true);
                tbc1_1.setPreferredWidth(150);
                tbc1_1.setResizable(false);
                tbc2.setPreferredWidth(30);
                tbc2.setResizable(false);
                tbc3.setPreferredWidth(30);
                tbc3.setResizable(false);
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                objTblMod.addColumn(tblDat, tbc1);
                objTblMod.addColumn(tblDat, tbc1_1);
                objTblMod.addColumn(tblDat, tbc2);
                objTblMod.addColumn(tblDat, tbc3);
                
            }
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat); 
 
            arlColHid=null;
            objTblMod.addColumnasEditables(vecAux);
            
            objTblCelRenBut=new ZafTblCelRenBut();
            objTblCelEdiBut = new ZafTblCelEdiButGen();
            
            objTblCelRenLblNom=new ZafTblCelRenLbl();
            objTblCelEdiLblNom =new ZafTblCelEdiTxt(tblDat);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            for(int j=0;j<vecAux.size();j=j+2){
                //Agregue hacer que la columna donde pondre el codigo de los vendedores aparezca no salia sin eso :(  JM 
                tcmAux.getColumn((Integer.parseInt(vecAux.get(j).toString())) ).setCellRenderer(objTblCelRenLblNom);
                tcmAux.getColumn((Integer.parseInt(vecAux.get(j).toString())) ).setCellEditor(objTblCelEdiLblNom); 
                 
                //Establecer: Botones 
                tcmAux.getColumn(Integer.parseInt(vecAux.get(j).toString())+2).setCellRenderer(objTblCelRenBut);
                tcmAux.getColumn(Integer.parseInt(vecAux.get(j).toString())+2).setCellEditor(objTblCelEdiBut);
            }
            vecAux=null;
            objTblCelRenBut=null;
           
            objTblCelEdiLblNom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                String strAntes="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTblMod.getValueAt(intFilSel,intColSel)!=null){
                            if(objTblMod.getValueAt(intFilSel,intColSel).toString().length()>0){
                                strAntes=objTblMod.getValueAt(intFilSel,intColSel).toString();
                            }
                        }
                    }
                    else{
                        if(objTblMod.getValueAt(intFilSel,intColSel)!=null){
                            if(objTblMod.getValueAt(intFilSel,intColSel).toString().length()>0){
                                strAntes=objTblMod.getValueAt(intFilSel,intColSel).toString();
                            }
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if(objParSis.getCodigoUsuario()!=1){
                        if( Integer.parseInt(objTblMod.getColumnName(intColSel+2).toString())==objParSis.getCodigoUsuario() ){
                            if(objTblMod.getValueAt(intFilSel,intColSel).toString().length()>0){
                                if(buscarGrupo(intFilSel, intColSel)){
                                    System.out.println("Encontrado... ");
                                }
                                else{
                                    if(mostrarVentanaGrupos ( intFilSel, intColSel+1 )){  /* JM: por que estas en la columna del texto y le envias la columna del boton */

                                    }
                                    else{
                                        objTblMod.setValueAt(strAntes, intFilSel, intColSel); 
                                    }
                                }
                            }
                        }
                        else{
                            MensajeInf("No es posible hacer cambios en una clasificación que no le corresponde...");
                            objTblMod.setValueAt(strAntes, intFilSel, intColSel); 
                        }
                    }
                    else{
                        if(objTblMod.getValueAt(intFilSel,intColSel).toString().length()>0){
                            if(buscarGrupo(intFilSel, intColSel)){
                                System.out.println("Encontrado... ");
                            }
                            else{
                                if(mostrarVentanaGrupos ( intFilSel, intColSel+1 )){  /* JM: por que estas en la columna del texto y le envias la columna del boton */

                                }
                                else{
                                    objTblMod.setValueAt(strAntes, intFilSel, intColSel); 
                                }
                            }
                        }
                    }
                    
                    
                }
            });
 
            
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                int intFilSel, intColSel;
                boolean blnUsar=true;
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if(objParSis.getCodigoUsuario()!=1){
                        if( Integer.parseInt(objTblMod.getColumnName(intColSel+1).toString())==objParSis.getCodigoUsuario()  ){
                            if(mostrarVentanaGrupos ( intFilSel, intColSel )){

                            }
                        }
                        else{
                            MensajeInf("No es posible hacer cambios en una clasificación que no le corresponde...");
                        }
                    }
                    else{
                        if(mostrarVentanaGrupos ( intFilSel, intColSel )){

                        }
                    }
                }
            });
            
            
            
  
            intColFin=objTblMod.getColumnCount();           
        }
        catch(Exception e){
            blnRes=false;
            System.out.println("xxxx " + e.toString());
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private void MensajeInf(String strMensaje) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    private boolean buscarGrupo(int FilSel, int ColSel){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strGrupo, strClasificacion;
        try{
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null){
                stmLoc = conLoc.createStatement();
                strGrupo = objTblMod.getValueAt(FilSel,ColSel).toString();
                strClasificacion = objTblMod.getColumnName(ColSel-1);
                strSql="";
                strSql+=" SELECT a1.co_grp, a1.tx_desCor, a1.tx_desLar \n";
                strSql+=" FROM tbm_grpClaModInv as a1 \n";
                strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND  ";
                strSql+=" LOWER(CAST( a1.tx_desCor AS VARCHAR)) LIKE '"+ strGrupo.replaceAll("'", "''").toLowerCase() + "' AND ";
                strSql+=" a1.co_claModInv="+strClasificacion;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                    objTblMod.setValueAt("M", FilSel , INT_TBL_DAT_LIN );  // Para que se sepa que se modifico algo
                    objTblMod.setValueAt(rstLoc.getString("co_grp") , FilSel, ColSel-1 );
                    objTblMod.setValueAt(rstLoc.getString("tx_desCor") , FilSel, ColSel );
                    objTblMod.setValueAt(rstLoc.getString("tx_desLar") , FilSel, ColSel+1 );
                }
                 
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
         catch(Exception e){
            blnRes=false;
            System.out.println("xxxx " + e.toString());
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Los parametros que le llegan siempre son columna del boton donde esta
     * @param CodFilSel
     * @param CodColSel
     * @return 
     */
     
    
    private boolean mostrarVentanaGrupos(int CodFilSel, int CodColSel){
        int intCodCla, intCodItm;
        boolean blnRes=false;
        try {
            intCodCla = Integer.parseInt(objTblMod.getColumnName(CodColSel-3));
            intCodItm = Integer.parseInt(objTblMod.getValueAt(CodFilSel, INT_TBL_DAT_COD_SIS).toString());
            
            ZafMae74_01 obj = new ZafMae74_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, true, intCodCla, intCodItm);
//            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            
            if(obj.acepta()){
                objTblMod.setValueAt("M", CodFilSel , INT_TBL_DAT_LIN );  // Para que se sepa que se modifico algo
                objTblMod.setValueAt(obj.strCodGrp, CodFilSel , CodColSel-3 );
                objTblMod.setValueAt(obj.strNomGru, CodFilSel, CodColSel-2 );
                objTblMod.setValueAt(obj.strNomLarGru, CodFilSel, CodColSel-1 );
                obj=null;
                blnRes=true;
            }
            else{
                obj=null;
            }
            
            
        } 
        catch (Exception Evt) {    
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }
     
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("340");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSql="";
            strSql+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSql+=" FROM tbm_inv AS a1";
            strSql+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSql+=" AND a1.st_reg='A'";
            strSql+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean crearNuevoGrupo(){
        boolean blnRes=false;
        String strNomClasificacion="";
        try{
            strNomClasificacion= (javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el nombre de la nueva clasificación: ", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE)).toString();
            if(strNomClasificacion != null) { //Aceptar
                if(insertaClasificacion(strNomClasificacion)){
                   blnRes=true;
                }
            }
            else{ //Cancelar
                
                return false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean insertaClasificacion(String NomClasificacion){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intMax=-1;
        try{
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                conLoc.setAutoCommit(false);
                stmLoc=conLoc.createStatement();
                
                strSql=" SELECT CASE WHEN MAX(co_claModInv) IS NULL THEN 0 ELSE MAX(co_claModInv) END + 1 as MAX FROM tbm_claModInv WHERE co_emp="+objParSis.getCodigoEmpresaGrupo()+"  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intMax = rstLoc.getInt("MAX");
                }
                rstLoc.close();
                rstLoc=null;
                
                strSql="";
                strSql+=" INSERT INTO tbm_claModInv (co_emp,co_claModInv, tx_desCor, tx_desLar, st_reg, fe_ing, co_usrIng, co_usrDueCla) ";
                strSql+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", "+intMax+", "+objUti.codificar(NomClasificacion)+", '', 'A', CURRENT_TIMESTAMP, "+objParSis.getCodigoUsuario()+",   ";
                strSql+=" "+objParSis.getCodigoUsuario()+"  ); ";
                stmLoc.executeUpdate(strSql);
                
                stmLoc.close();
                stmLoc=null;
                
                if(blnRes){
                    conLoc.commit();
                }
                else{
                    conLoc.rollback();
                }
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     //Constantes: Columnas del JTable:
    static final int INT_TBL_USR_LIN=0;                          
    static final int INT_TBL_USR_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_USR_COD=2;                    
    static final int INT_TBL_USR_USR=3;                      
    static final int INT_TBL_USR_NOM=4;                     
    private ZafTblMod objTblModUsr;
    private boolean blnMarTodChkTblUsr=true;                    //Marcar todas las casillas de verificación del JTable de Usuarios.
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    
    /**
     * Esta función configura el JTable "tblUsr".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblUsr()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_USR_LIN,"");
            vecCab.add(INT_TBL_USR_CHK,"");
            vecCab.add(INT_TBL_USR_COD,"Cód.Usr.");
            vecCab.add(INT_TBL_USR_USR,"Usuario");
            vecCab.add(INT_TBL_USR_NOM,"Nom.Usu.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModUsr=new ZafTblMod();
            objTblModUsr.setHeader(vecCab);
            tblUsr.setModel(objTblModUsr);
            //Configurar JTable: Establecer tipo de selección.
            tblUsr.setRowSelectionAllowed(true);
            tblUsr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblUsr);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblUsr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblUsr.getColumnModel();
            tcmAux.getColumn(INT_TBL_USR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_USR_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_USR_COD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_USR_USR).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_USR_NOM).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_USR_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblUsr.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblUsr.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblUsr.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblUsrMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_USR_CHK);
            objTblModUsr.setColumnasEditables(vecAux);
            vecAux=null;
 
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblUsr);
            tcmAux.getColumn(INT_TBL_USR_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_USR_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_USR_COD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblUsr);
            tcmAux.getColumn(INT_TBL_USR_CHK).setCellEditor(objTblCelEdiChk);
            
            //Configurar JTable: Establecer el modo de operación.
            objTblModUsr.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblUsr.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_USR_LIN:
                    strMsg="";
                    break;
                case INT_TBL_USR_COD:
                    strMsg="Código del Usuario";
                    break;
                case INT_TBL_USR_USR:
                    strMsg="Usuario";
                    break;
                case INT_TBL_USR_NOM:
                    strMsg="Nombre del Usuario";
                    break;
                 
                default:
                    strMsg="";
                    break;
            }
            tblUsr.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblUsrMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModUsr.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblUsr.columnAtPoint(evt.getPoint())==INT_TBL_USR_CHK)
            {
                if (blnMarTodChkTblUsr){
                    for (i=0; i<intNumFil; i++){
                        objTblModUsr.setChecked(true, i, INT_TBL_USR_CHK);
                    }
                    blnMarTodChkTblUsr=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModUsr.setChecked(false, i, INT_TBL_USR_CHK);
                    }
                    blnMarTodChkTblUsr=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    
    
    /**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarUsr(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_mnu, a2.co_usr, a2.tx_usr, a2.tx_nom \n";
                strSql+=" FROM tbr_perUsr as a1  \n";
                strSql+=" INNER JOIN tbm_usr as a2 ON (a1.co_usr=a2.co_usr) \n";
                strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" and a1.co_loc="+objParSis.getCodigoLocal()+" and a1.co_mnu="+objParSis.getCodigoMenu()+" and a2.st_reg='A'    \n";
                rstLoc=stmLoc.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                while (rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_USR_LIN,"");
                    if(rstLoc.getInt("co_usr")==objParSis.getCodigoUsuario()){ /* JM: Por defecto solo se marcarian las casillas del usuario que esta ingresando */
                        vecReg.add(INT_TBL_USR_CHK,Boolean.TRUE);
                    }
                    else{
                        vecReg.add(INT_TBL_USR_CHK,Boolean.FALSE);
                    }
                    vecReg.add(INT_TBL_USR_COD,rstLoc.getString("co_usr"));
                    vecReg.add(INT_TBL_USR_USR,rstLoc.getString("tx_usr"));
                    vecReg.add(INT_TBL_USR_NOM,rstLoc.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                //Asignar vectores al modelo.
                objTblModUsr.setData(vecDat);
                tblUsr.setModel(objTblModUsr);
                vecDat.clear();
                blnMarTodChkTblUsr=false;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
}