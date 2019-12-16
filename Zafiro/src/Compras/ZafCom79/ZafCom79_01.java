/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras.ZafCom79;

import Compras.ZafCom94.ZafCom94;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author Alex Morán
 */
public class ZafCom79_01 extends javax.swing.JDialog 
{
   //Constantes.
    //Columnas del JTable: Bodegas.
    private static final int INT_TBL_LIN   =0;
    private static final int INT_TBL_CHKBOD=1;
    private static final int INT_TBL_CODBOD=2;
    private static final int INT_TBL_NOMBOD=3;
    
    //Jtable: TblDat
    private static final int INT_TBL_DAT_LIN=0;                          //Línea
    private static final int INT_TBL_DAT_CODSEG = 1;                     //Código de Seguimiento.
    private static final int INT_TBL_DAT_CODEMPSOLTRA=2;                 //Código de Empresa.
    private static final int INT_TBL_DAT_CODLOCSOLTRA=3;                 //Código de Local.
    private static final int INT_TBL_DAT_CODTIPDOCSOLTRA=4;              //Código de Tipo de Documento.
    private static final int INT_TBL_DAT_CODDOCSOLTRA=5;                 //Código de Documento.
    private static final int INT_TBL_DAT_DESCORTIPDOCSOLTRA=6;           //Descripción corta de Tipo de Documento.
    private static final int INT_TBL_DAT_DESLARTIPDOCSOLTRA=7;           //Descripción larga de Tipo de Documento.
    private static final int INT_TBL_DAT_NUMDOCSOLTRA=8;                 //Número de Documento.
    private static final int INT_TBL_DAT_BUTCONSOLTRA=9;                 //Botón de Solicitud de Transferencia.
    
    private static final int INT_TBL_DAT_NOMLOCMOVINV=10;                //Local.
    private static final int INT_TBL_DAT_CODEMPMOVINV=11;                //Código de Empresa.
    private static final int INT_TBL_DAT_CODLOCMOVINV=12;                //Código de Local.
    private static final int INT_TBL_DAT_CODTIPDOCMOVINV=13;             //Código de Tipo de Documento.
    private static final int INT_TBL_DAT_CODDOCMOVINV=14;                //Código de Documento.
    private static final int INT_TBL_DAT_DESCORTIPDOCMOVINV=15;          //Descripción corta de Tipo de Documento.
    private static final int INT_TBL_DAT_DESLARTIPDOCMOVINV=16;          //Descripción larga de Tipo de Documento.
    private static final int INT_TBL_DAT_NUMDOCMOVINV=17;                //Número de Documento.
    private static final int INT_TBL_DAT_MOTTRAMOVINV=18;                //Motivo de Traslado.
    private static final int INT_TBL_DAT_FECDOCMOVINV=19;                //Fecha de Documento.
    private static final int INT_TBL_DAT_CODBODORGMOVINV=20;             //Código de Bodega Origen.
    private static final int INT_TBL_DAT_NOMBODORGMOVINV=21;             //Nombre de Bodega Origen.
    private static final int INT_TBL_DAT_CODBODDESMOVINV=22;             //Código de Bodega Destino.
    private static final int INT_TBL_DAT_NOMBODDESMOVINV=23;             //Nombre de Bodega Destino.
    private static final int INT_TBL_DAT_CODREGMOVINV=24;                //Código de Registro.
    private static final int INT_TBL_DAT_CODITM=25;                      //Código de Item.
    private static final int INT_TBL_DAT_CODALTITM=26;                   //Código alterno de Item.
    private static final int INT_TBL_DAT_CODALTDOS=27;                   //Código alterno 2 de Item.
    private static final int INT_TBL_DAT_NOMITM=28;                      //Nombre de Item.
    private static final int INT_TBL_DAT_CANMOVINV=29;                   //Cantidad de Guía de Remisión.
    private static final int INT_TBL_DAT_UNIMED=30;                      //Unidad de Medida.
    private static final int INT_TBL_DAT_CANINGBOD=31;                   //Cantidad por Ingresar a Bodega.
    private static final int INT_TBL_DAT_CANINGDES=32;                   //Cantidad por Ingresar a Despacho.
    private static final int INT_TBL_DAT_DIASINCONF=33;                  //Días sin Confirmar. 
    private static final int INT_TBL_DAT_ESTDIA=34;                      //Estado.
    private static final int INT_TBL_DAT_OBS=35;                         //Observación: Utilizada para Mostrar la SOTRIN.
    private static final int INT_TBL_DAT_NOMCLIPRV=36;                   //Nombre de Proveedor.
    private static final int INT_TBL_DAT_ESTCONINV=37;                   //Estado de Confirmacion de Documento de Ingreso.
    private static final int INT_TBL_DAT_BUTCONDOCINV=38;                //Botón de Documento de Inventario.

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblMod objTblMod, objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChkBod;
    private ZafTblCelEdiChk objTblCelEdiChkBod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenButConSolTraInv;  
    private ZafTblCelRenLbl objTblCelRenLblColSolTraInv, objTblCelRenLblColCenSol, objTblCelRenLblColSolTraInvNum;
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafCom94 objReg = null;
    private ZafCom79 objZafCom79;
    private String strSQL;
    private Vector vecDat, vecCab, vecReg;
    private final Color colFonCol =new Color(255,0,0);
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnMarTodTblBod=true;
    private boolean blnBloqueo = false;  
    private int intSqlBod=1;
    
    /**
     * Creates new form ZafCom79_01: Ventana Emergente
     * Se utiliza para el bloqueo del sistema por confirmaciones de ingreso pendientes.
     */
    public ZafCom79_01(java.awt.Frame parent, boolean modal, ZafParSis obj) 
    {
        super(parent, modal);
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objZafCom79 = new  Compras.ZafCom79.ZafCom79(objParSis);
            initComponents();
            configurarFrm();  
            consultar();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panReg = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 445));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de ingresos físicos de mercadería pendientes de confirmar...");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);
        tabFrm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabFrmStateChanged(evt);
            }
        });

        panFil.setLayout(null);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodega"));
        panBod.setPreferredSize(new java.awt.Dimension(294, 100));
        panBod.setRequestFocusEnabled(false);
        panBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(10, 10, 630, 90);

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
        ));
        tblDat.setAutoscrolls(false);
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panReg.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Registro", panReg);

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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 8, Short.MAX_VALUE)
                    .addComponent(panFrm, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(panFrm, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );

        getAccessibleContext().setAccessibleParent(this);

        setSize(new java.awt.Dimension(700, 445));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
       consultar();
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        if(blnBloqueo)
        {
            butCer.setEnabled(false);
        }
        else
        {
            dispose();
        }
            
    }//GEN-LAST:event_butCerActionPerformed

    private void tabFrmStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabFrmStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tabFrmStateChanged
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panReg;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objTblCelRenLblColSolTraInv = new ZafTblCelRenLbl();
            objTblCelRenLblColSolTraInvNum = new ZafTblCelRenLbl();
            
            this.setTitle("Listado de ingresos físicos de mercadería pendientes de confirmar..." + objZafCom79.strVersion);
            lblTit.setText("Listado de ingresos físicos de mercadería pendientes de confirmar..." + objZafCom79.strVersion);
            
            //Configuraciones Varias.
            configurarTblDatSolTraInv();
            configurarTblBod();
            cargarBod();
            
            //Se oculta panel de Confirmación de Registros.
            //tabFrm.remove(panReg);
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    private boolean configurarTblDatSolTraInv()
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras        
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_CODEMPSOLTRA,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCSOLTRA,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCSOLTRA,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCSOLTRA,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOCSOLTRA,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOCSOLTRA,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOCSOLTRA,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_BUTCONSOLTRA,"...");
            vecCab.add(INT_TBL_DAT_NOMLOCMOVINV,"Local");
            vecCab.add(INT_TBL_DAT_CODEMPMOVINV,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCMOVINV,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCMOVINV,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCMOVINV,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOCMOVINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOCMOVINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOCMOVINV,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_MOTTRAMOVINV,"Mot.Tra.");
            vecCab.add(INT_TBL_DAT_FECDOCMOVINV,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODBODORGMOVINV,"Cód.Bod.Ori.");
            vecCab.add(INT_TBL_DAT_NOMBODORGMOVINV,"Bod.Ori.");
            vecCab.add(INT_TBL_DAT_CODBODDESMOVINV,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOMBODDESMOVINV,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_CODREGMOVINV,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_CODITM,"Cód.Itm.");        
            vecCab.add(INT_TBL_DAT_CODALTITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_CODALTDOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOMITM,"Nom.Itm.");
            vecCab.add(INT_TBL_DAT_CANMOVINV,"Can.Tra.");
            vecCab.add(INT_TBL_DAT_UNIMED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_CANINGBOD,"Can.Ing.Bod.");
            vecCab.add(INT_TBL_DAT_CANINGDES,"Can.Ing.Des.");
            vecCab.add(INT_TBL_DAT_DIASINCONF,"Días");
            vecCab.add(INT_TBL_DAT_ESTDIA,"");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_NOMCLIPRV,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_ESTCONINV,"Est.Con.Inv.");
            vecCab.add(INT_TBL_DAT_BUTCONDOCINV,"...");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod); 
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAdaTblSolTraInv objMouMotAda2=new ZafMouMotAdaTblSolTraInv();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda2);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel(); 
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Establece tamaño.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCSOLTRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOCMOVINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_MOTTRAMOVINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORGMOVINV).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODDESMOVINV).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_DAT_CANMOVINV).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNIMED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CANINGBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CANINGDES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIASINCONF).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_ESTDIA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(80);            
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIPRV).setPreferredWidth(120);    
            tcmAux.getColumn(INT_TBL_DAT_ESTCONINV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setPreferredWidth(20);
            
            //Columnas Ocultas
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMPSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUMDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUTCONSOLTRA, tblDat);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMPMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODBODORGMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODBODDESMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODREGMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CANINGDES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ESTDIA, tblDat);
            
            if(objParSis.getCodigoUsuario()!= 1)
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ESTCONINV, tblDat);

            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrpSolTra=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpSolTra.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColGrpSolTra=new ZafTblHeaColGrp(" SOLICITUDES DE TRANSFERENCIAS ");
            objTblHeaColGrpSolTra.setHeight(16);
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA));

            ZafTblHeaColGrp objTblHeaColGrpTraInv=new ZafTblHeaColGrp(" MOVIMIENTOS DE INVENTARIO ");
            objTblHeaColGrpTraInv.setHeight(16);
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_MOTTRAMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODORGMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODORGMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODDESMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODDESMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODREGMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODITM));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODALTITM));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODALTDOS));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMITM));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CANMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_UNIMED));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CANINGBOD));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CANINGDES));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DIASINCONF));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_ESTDIA));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_OBS));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMCLIPRV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_ESTCONINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV));

            objTblHeaGrpSolTra.addColumnGroup(objTblHeaColGrpSolTra);
            objTblHeaGrpSolTra.addColumnGroup(objTblHeaColGrpTraInv);
            objTblHeaColGrpSolTra=null;
            objTblHeaColGrpTraInv=null;
            
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTCONSOLTRA);
            vecAux.add("" + INT_TBL_DAT_BUTCONDOCINV);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Establecer Botón.
            objTblCelRenButConSolTraInv=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA).setCellRenderer(objTblCelRenButConSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setCellRenderer(objTblCelRenButConSolTraInv);
            
            objTblCelRenButConSolTraInv.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButConSolTraInv.getColumnRender())
                    {
                        case INT_TBL_DAT_BUTCONSOLTRA:
                            if(objTblMod.getValueAt(objTblCelRenButConSolTraInv.getRowRender(), INT_TBL_DAT_CODEMPSOLTRA).toString().equals(""))
                               objTblCelRenButConSolTraInv.setText("");
                            else
                               objTblCelRenButConSolTraInv.setText("...");
                        break;
                                                    
                        case INT_TBL_DAT_BUTCONDOCINV:
                            if (objTblMod.getValueAt(objTblCelRenButConSolTraInv.getRowRender(), INT_TBL_DAT_CODEMPMOVINV).toString().equals("")) 
                               objTblCelRenButConSolTraInv.setText("");
                            else
                               objTblCelRenButConSolTraInv.setText("...");
                        break;
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setCellEditor(objTblCelEdiButGen);
           
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUTCONSOLTRA:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMPSOLTRA).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUTCONDOCINV:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMPMOVINV).toString().equals("")))
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                break;

                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUTCONSOLTRA:
                                cargarSolTraInv   (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMPSOLTRA).toString(), 
                                                   tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOCSOLTRA).toString(),
                                                   tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOCSOLTRA).toString(),
                                                   tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOCSOLTRA).toString()
                                                   );
                                break;
                            case INT_TBL_DAT_BUTCONDOCINV:
                                cargarVentanaMovInv(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMPMOVINV).toString(), 
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOCMOVINV).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOCMOVINV).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOCMOVINV).toString()
                                                    );
                                break;

                        }
                    }
                }
            });           
            
            //Establecer Tabla Editable.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);                  
            
            //Configurar Jtable: Colores            
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCSOLTRA).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_MOTTRAMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODBODORGMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORGMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODBODDESMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODDESMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CANMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_UNIMED).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DIASINCONF).setCellRenderer(objTblCelRenLblColSolTraInv);            
            tcmAux.getColumn(INT_TBL_DAT_ESTDIA).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_OBS).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIPRV).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_ESTCONINV).setCellRenderer(objTblCelRenLblColSolTraInv);   
            
            objTblCelRenLblColSolTraInv.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColSolTraInv.getRowRender();
                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColSolTraInv.setBackground(colFonCol);
                        objTblCelRenLblColSolTraInv.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColSolTraInv.setBackground(Color.WHITE);
                        objTblCelRenLblColSolTraInv.setForeground(Color.BLACK);
                    }
                }
            });
            
            
            //Formato Cantidades
            tcmAux.getColumn(INT_TBL_DAT_CANMOVINV).setCellRenderer(objTblCelRenLblColSolTraInvNum);
            tcmAux.getColumn(INT_TBL_DAT_CANINGBOD).setCellRenderer(objTblCelRenLblColSolTraInvNum);
            tcmAux.getColumn(INT_TBL_DAT_CANINGDES).setCellRenderer(objTblCelRenLblColSolTraInvNum);
            
            objTblCelRenLblColSolTraInvNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColSolTraInvNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblColSolTraInvNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblColSolTraInvNum.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColSolTraInvNum.getRowRender();

                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColSolTraInvNum.setBackground(colFonCol);
                        objTblCelRenLblColSolTraInvNum.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColSolTraInvNum.setBackground(Color.WHITE);
                        objTblCelRenLblColSolTraInvNum.setForeground(Color.BLACK);
                    }
                }
            });

            
            //Formato Código Alterno 2.
            objTblCelRenLblColCenSol = new ZafTblCelRenLbl();
            objTblCelRenLblColCenSol.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setCellRenderer(objTblCelRenLblColCenSol);
            
            objTblCelRenLblColCenSol.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColCenSol.getRowRender();

                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColCenSol.setBackground(colFonCol);
                        objTblCelRenLblColCenSol.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColCenSol.setBackground(Color.WHITE);
                        objTblCelRenLblColCenSol.setForeground(Color.BLACK);
                    }
                }
            });
            
            
            tcmAux=null;
            blnRes = true;

        } 
        catch (Exception e) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaTblSolTraInv extends java.awt.event.MouseMotionAdapter 
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
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código Seguimiento";
                    break;  
                case INT_TBL_DAT_CODEMPSOLTRA:
                    strMsg="Código de la empresa de la solicitud";
                    break;
                case INT_TBL_DAT_CODLOCSOLTRA:
                    strMsg="Código del local de la solicitud";
                    break;
                case INT_TBL_DAT_CODTIPDOCSOLTRA:
                    strMsg="Código del tipo de documento de la solicitud";
                    break;
                case INT_TBL_DAT_CODDOCSOLTRA:
                    strMsg="Código del documento de la solicitud";
                    break;
                case INT_TBL_DAT_DESCORTIPDOCSOLTRA:
                    strMsg="Descripción corta del tipo de documento de la solicitud";
                    break;
                case INT_TBL_DAT_DESLARTIPDOCSOLTRA:
                    strMsg="Descripción larga del tipo de documento de la solicitud";
                    break;
                case INT_TBL_DAT_NUMDOCSOLTRA:
                    strMsg="Número de Documento de la solicitud";
                    break;
                case INT_TBL_DAT_BUTCONSOLTRA:
                    strMsg="Botón Consulta Solicitud de Transferencia";
                    break;
                case INT_TBL_DAT_NOMLOCMOVINV:
                    strMsg="Local en que se almacena el documento";
                    break;
                case INT_TBL_DAT_CODEMPMOVINV:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOCMOVINV:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOCMOVINV:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_CODDOCMOVINV:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOCMOVINV:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DESLARTIPDOCMOVINV:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUMDOCMOVINV:
                    strMsg="Número de Documento";
                    break;
                case INT_TBL_DAT_MOTTRAMOVINV:
                    strMsg="Motivo de Movimiento de Inventario";
                    break;
                case INT_TBL_DAT_FECDOCMOVINV:
                    strMsg="Fecha de Documento";
                    break;    
                case INT_TBL_DAT_CODBODORGMOVINV:
                    strMsg="Código de Bodega Origen";
                    break;
                case INT_TBL_DAT_NOMBODORGMOVINV:
                    strMsg="Nombre de Bodega Origen";
                    break;
                case INT_TBL_DAT_CODBODDESMOVINV:
                    strMsg="Código de Bodega Destino";
                    break;
                case INT_TBL_DAT_NOMBODDESMOVINV:
                    strMsg="Nombre de Bodega Destino";
                    break;
                case INT_TBL_DAT_CODREGMOVINV:
                    strMsg="Código de Registro";
                    break;
                case INT_TBL_DAT_CODITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_CODALTITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_CODALTDOS:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOMITM:
                    strMsg="Nombre del item";
                    break;   
                case INT_TBL_DAT_CANMOVINV:
                    strMsg="Cantidad de Transferencia";
                    break;
                case INT_TBL_DAT_UNIMED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CANINGBOD:
                    strMsg="Cantidad por Ingresar a Bodega";
                    break;
                case INT_TBL_DAT_CANINGDES:
                    strMsg="Cantidad por Ingresar a Despacho";
                    break;
                case INT_TBL_DAT_DIASINCONF:
                    strMsg="Días sin confirmar (Laboralmente)";
                    break;
                case INT_TBL_DAT_ESTDIA:
                    strMsg="Estado";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación";
                    break;
                case INT_TBL_DAT_NOMCLIPRV:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_ESTCONINV:
                    strMsg="Estado de Confirmacion de Inventario";
                    break;
                case INT_TBL_DAT_BUTCONDOCINV:
                    strMsg="Botón Consulta Documento de Inventario";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Movimiento de Inventario*/">
    private void cargarVentanaMovInv(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        int intCodEmp=Integer.valueOf(strCodEmp);
        int intCodLoc=Integer.valueOf(strCodLoc);
        int intCodTipDoc=Integer.valueOf(strCodTipDoc);
        int intCodDoc=Integer.valueOf(strCodDoc);
        
        Compras.ZafCom20.ZafCom20 obj = new Compras.ZafCom20.ZafCom20(objParSis, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc) ;
        panReg.add(obj,java.awt.BorderLayout.CENTER);
        obj.show();
    }
    //</editor-fold>
    
    
    private void cargarSolTraInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try 
        {
            Compras.ZafCom91.ZafCom91 obj1 = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
            panReg.add(obj1,java.awt.BorderLayout.CENTER);
            obj1.show();
        } 
        catch (Exception Evt) {        objUti.mostrarMsgErr_F1(this, Evt);     }
    }
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* Bodegas */">
    private boolean configurarTblBod()
    {
        boolean blnRes = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_LIN, "");
        vecCab.add(INT_TBL_CHKBOD, "");
        vecCab.add(INT_TBL_CODBOD, "Cód.Bod.");
        vecCab.add(INT_TBL_NOMBOD, "Bodega");

        objTblModBod = new ZafTblMod();
        objTblModBod.setHeader(vecCab);
        tblBod.setModel(objTblModBod);

        tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblBod.getColumnModel();
        tblBod.getTableHeader().setReorderingAllowed(false);
        
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(380);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_CHKBOD);
        objTblModBod.setColumnasEditables(vecAux);
        vecAux = null;
        
        //Configurar JTable: Establecer la fila de cabecera.
        objTblFilCab=new ZafTblFilCab(tblBod);
        tcmAux.getColumn(INT_TBL_LIN).setCellRenderer(objTblFilCab);
        
        //Configurar JTable: Editor de la tabla.
        ZafTblEdi objZafTblEdi = new ZafTblEdi(tblBod);

        objTblCelRenChkBod = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChkBod);
        objTblCelRenChkBod = null;

        objTblCelEdiChkBod = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChkBod);
        objTblCelEdiChkBod.addTableEditorListener(new ZafTableAdapter() 
        {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
            {
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
            {
            }
        });

        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                tblDatMouseClickedBod(evt);
            }
        });

        objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);

        return blnRes;
    }

    private boolean cargarBod()
    {
        boolean blnRes=true;
        intSqlBod=1;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
          
                //Armar la sentencia SQL.
                strSQL=objZafCom79.getQueryBodegas(intSqlBod, true);
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN,"");
                    vecReg.add(INT_TBL_CHKBOD,true);
                    vecReg.add(INT_TBL_CODBOD, rst.getString("co_bod") );
                    vecReg.add(INT_TBL_NOMBOD, rst.getString("tx_nom") );
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                //blnMarTodTblBod=false;
            }
        }
        catch (java.sql.SQLException e)  {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    private void tblDatMouseClickedBod(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        try 
        {
            intNumFil = tblBod.getRowCount();
            
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 1 && tblBod.columnAtPoint(evt.getPoint()) == INT_TBL_CHKBOD) 
            {
                if (blnMarTodTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        tblBod.setValueAt(new Boolean(false), i, INT_TBL_CHKBOD);
                    }
                    blnMarTodTblBod = false;
                }
                else 
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        tblBod.setValueAt(new Boolean(true), i, INT_TBL_CHKBOD);
                    }
                    blnMarTodTblBod = true;
                }
            }
        }
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    //</editor-fold>
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBodOri, i, j;
        
        intNumFilTblBodOri=objTblModBod.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblBodOri; j++)
        {
            if (objTblModBod.isChecked(j, INT_TBL_CHKBOD))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            System.out.println("El usuario no tiene acceso a las bodegas por el local ingresado.");
            //mostrarMsgInf("<HTML>Debe seleccionar obligatoriamente una bodega.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tblBod.requestFocus();
            //tabFrm.setSelectedIndex(0);
            return false;
        }
        
        return true;
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
        @Override
        public void run()
        {
            if (!cargarDetReg(sqlConFil()))
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
            
            if (blnBloqueo) 
            {
                butCer.setEnabled(false);
            } 
            else 
            {
                butCer.setEnabled(true);                
            }
            
        }
    }
    
    
    /* Filtro Consulta*/
    public String sqlConFil() 
    {
        String sqlFil=" AND ( b.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND b.co_bodGrp IN ( "+getQueryBodegasNotificacion()+" ) )";
        //System.out.println("Filtro.ZafCom79_01: "+sqlFil);
        return sqlFil;
    }
    
   
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL = getQueryItemsPendientesNotificacion();
                
                //System.out.println("ZafCom79_01.cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN," ");
                        vecReg.add(INT_TBL_DAT_CODSEG, rst.getString("co_seg"));
                        vecReg.add(INT_TBL_DAT_CODEMPSOLTRA, rst.getString("CodEmpSol")==null?"":rst.getString("CodEmpSol")  );
                        vecReg.add(INT_TBL_DAT_CODLOCSOLTRA, rst.getString("CodLocSol")  );
                        vecReg.add(INT_TBL_DAT_CODTIPDOCSOLTRA, rst.getString("CodTipDocSol")  );
                        vecReg.add(INT_TBL_DAT_CODDOCSOLTRA, rst.getString("CodDocSol")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCSOLTRA, rst.getString("DesCorSol")  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCSOLTRA, rst.getString("DesLarSol")  );
                        vecReg.add(INT_TBL_DAT_NUMDOCSOLTRA, rst.getString("NumDocSol")  );
                        vecReg.add(INT_TBL_DAT_BUTCONSOLTRA, "" );  
                        
                        vecReg.add(INT_TBL_DAT_NOMLOCMOVINV, rst.getString("tx_nom") );
                        vecReg.add(INT_TBL_DAT_CODEMPMOVINV, rst.getString("CodEmpMovInv")==null?"":rst.getString("CodEmpMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODLOCMOVINV, rst.getString("CodLocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODTIPDOCMOVINV, rst.getString("CodTipDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODDOCMOVINV, rst.getString("CodDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCMOVINV, rst.getString("DesCorMovInv")  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCMOVINV, rst.getString("DesLarMovInv")  );
                        vecReg.add(INT_TBL_DAT_NUMDOCMOVINV, rst.getString("NumDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_MOTTRAMOVINV, rst.getString("MotTra")  );
                        vecReg.add(INT_TBL_DAT_FECDOCMOVINV, rst.getString("FecDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODBODORGMOVINV, rst.getString("CodBodOrg")  );
                        vecReg.add(INT_TBL_DAT_NOMBODORGMOVINV,rst.getString("NomBodOrg")  );
                        vecReg.add(INT_TBL_DAT_CODBODDESMOVINV, rst.getString("CodBodGrpDes")  );
                        vecReg.add(INT_TBL_DAT_NOMBODDESMOVINV,rst.getString("NomBodDes")  );
                        vecReg.add(INT_TBL_DAT_CODREGMOVINV,  rst.getString("CodRegMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODITM, rst.getString("co_itm")  );
                        vecReg.add(INT_TBL_DAT_CODALTITM, rst.getString("tx_CodAlt")  );
                        vecReg.add(INT_TBL_DAT_CODALTDOS, rst.getString("tx_codAlt2")==null?"":rst.getString("tx_codAlt2").equals("null")?"":rst.getString("tx_codAlt2") );
                        vecReg.add(INT_TBL_DAT_NOMITM, rst.getString("tx_nomItm")  );
                        vecReg.add(INT_TBL_DAT_CANMOVINV, rst.getString("nd_Can")  );
                        vecReg.add(INT_TBL_DAT_UNIMED, rst.getString("tx_uniMed")  );
                        vecReg.add(INT_TBL_DAT_CANINGBOD, rst.getString("CanPenIngBod")  );
                        vecReg.add(INT_TBL_DAT_CANINGDES, rst.getString("CanPenIngBod")  );
                        //vecReg.add(INT_TBL_DAT_DIASINCONF, rst.getString("diasSinConf")  );
                        vecReg.add(INT_TBL_DAT_DIASINCONF, rst.getString("diasLab")  ); // Dias Laborales sin Confirmar
                        vecReg.add(INT_TBL_DAT_ESTDIA, rst.getString("estDia")  );
                        vecReg.add(INT_TBL_DAT_OBS, rst.getString("tx_obs")  );
                        vecReg.add(INT_TBL_DAT_NOMCLIPRV, rst.getString("NomCliPrv")  );
                        vecReg.add(INT_TBL_DAT_ESTCONINV, rst.getString("EstConInv")  );
                        vecReg.add(INT_TBL_DAT_BUTCONDOCINV, "" );
                        
                        vecDat.add(vecReg);
                        
                        if (rst.getString("bloqueo").equalsIgnoreCase("S")) 
                        {                            
                            blnBloqueo=true;
                        }
                        
                    }
                    else 
                    {
                        break;
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
                vecDat.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
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


    public String getQueryItemsPendientesNotificacion() 
    {
        strSQL=objZafCom79.getQueryItemsPendientes(sqlConFil());
        strSQL+= " WHERE z.diasPen <0 ";
        return strSQL;
    }
    
    private String getQueryBodegasNotificacion() 
    {
        String strBod="";
        intSqlBod=0;
        strBod=objZafCom79.getQueryBodegas(intSqlBod, true);
        return strBod;
    }
    
    private void consultar()
    {
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
                blnCon = true;
                blnBloqueo = false;
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else 
            {
                blnCon = false;
            }
        } 
    }
    
    

}

