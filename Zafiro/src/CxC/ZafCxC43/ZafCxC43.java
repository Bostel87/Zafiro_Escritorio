/*
 * ZafCxC43.java
 *
 * Created on 21 de Noviembre de 2007, 14:26 PM 
 * LISTADO DE RECEPCION DE EFECTIVO PENDIENTE DE PROCESAR
 * DESARROLLADO POR DARIO CARDENAS EL 22/ABR/2008
 * MODIFICADO EL DIA 22/ABR/2008
 */
package CxC.ZafCxC43;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import Librerias.ZafPerUsr.ZafPerUsr;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;                                                       
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;//para visualizar una ventana de programa desde una columna de botones
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;

/**
 *
 * @author  DARIO CARDENAS
 */
public class ZafCxC43 extends javax.swing.JInternalFrame 
{

    
    /* Declaracion de CONSTANTES*/ 
    final int INT_TBL_LINEA          =0;            //LINEA DE NUMEROS DE REGISTROS EN LA TABLA///
    final int INT_TBL_DAT_SEL        =1;            //CASILLA DE SELECCION
    final int INT_TBL_COD_EMP        =2;            //LINEA DE BOTONES PARA MOSTRAR DATOS DE LA FACTURA    
    final int INT_TBL_COD_LOC        =3;            //CODIGO DE CLIENTE
    final int INT_TBL_COD_TIP_DOC    =4;            //CODIGO DEL TIPO DE DOCUMENTO    
    final int INT_TBL_NOM_TIP_DOC    =5;            //NOMBRE DEL TIPO DE DOCUMENTO
    final int INT_TBL_COD_DOC        =6;            //CODIGO DEL DOCUMENTO
    final int INT_TBL_NUM_DOC        =7;            //NUMERO DE LA FACTURA O DOCUMENTO
    final int INT_TBL_FEC_DOC        =8;            //FECHA DEL DOCUMENTO
    final int INT_TBL_VAL_DOC        =9;            //VALOR DEL DOCUMENTO
    final int INT_TBL_EST_DOC        =10;           //ESTADO DEL DOCUMENTO
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
    private String CodEmp, NomEmp;
   // private java.util.Date datFecAux;
    private java.util.Date datFecSer, datFecVen, datFecAux;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    
    private ZafPerUsr objPerUsr;
    private ZafSelFec objSelFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblBus objTblBus;                        //Editor de bï¿½squeda.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux, strAux1;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strNomEmp,strCodTipDoc, strDesCorTipDoc, strDesLarTipDoc, strCodEmpTipDoc, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, k=0, p=0, CODEMP=0;
    private ZafVenCon vcoCli, vcoEmp, vcoTipDoc;                   //Ventana de consulta.
    private Vector vecAux, vecAuxDat;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private String strEstDoc;
    private String strVer=" v0.2";
    private ZafTblCelRenLbl objTblCelRenValDoc;
    private ZafTblOrd objTblOrd;
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC43(ZafParSis obj) 
    {
//        initComponents();
//        //Inicializar objetos.
//        objParSis=obj;
//        //butTipDoc.setVisible(false);
        try
        {  
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();

            if (!configurarFrm())
                exitForm();
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
        panCon = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEstDoc = new javax.swing.JLabel();
        cboEstDoc = new javax.swing.JComboBox();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 80));

        javax.swing.GroupLayout panFecDocLayout = new javax.swing.GroupLayout(panFecDoc);
        panFecDoc.setLayout(panFecDocLayout);
        panFecDocLayout.setHorizontalGroup(
            panFecDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 685, Short.MAX_VALUE)
        );
        panFecDocLayout.setVerticalGroup(
            panFecDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        panCon.add(panFecDoc, java.awt.BorderLayout.PAGE_START);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los Documentos");
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
        optTod.setBounds(10, 50, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los Documentos que cumplan el criterio seleccionado");
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
        optFil.setBounds(10, 70, 400, 20);

        lblEstDoc.setText("Estado de Analisis:");
        panFil.add(lblEstDoc);
        lblEstDoc.setBounds(10, 160, 130, 15);
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(130, 150, 160, 24);

        lblEmp.setText("Empresa:");
        panFil.add(lblEmp);
        lblEmp.setBounds(10, 20, 70, 15);

        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(70, 20, 30, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(100, 20, 200, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(300, 20, 20, 20);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblTipDoc.setText("Tipo de documento:");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(10, 100, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(120, 100, 20, 20);

        txtDesCorTipDoc.setMaximumSize(null);
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 100, 56, 20);

        txtDesLarTipDoc.setMaximumSize(null);
        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 100, 460, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(660, 100, 20, 20);

        panCon.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panCon);

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

        butImp.setText("Imprimir");
        butImp.setToolTipText("Imprime Documento");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:
       
        objThrGUIRpt=null;
//        mostrarMsgInf("<HTML> La FunciÃ³n <FONT COLOR=\"blue\"> ---IMPRIMIR--- </FONT> todavÃ­a <FONT COLOR=\"blue\"> NO </FONT> esta implementada en el sistema...  </HTML>");        
        if((objTblMod.getRowCountTrue()>0))
        {
//            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
//            {
                
                
                //if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_SEL))
                {
                    strEstDoc = objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_EST_DOC));
                    if(strEstDoc.equals("A"))
                    {
                        
                        if (objThrGUIRpt==null)
                        {
                            objThrGUIRpt=new ZafThreadGUIRpt();
                            objThrGUIRpt.setIndFunEje(1);
                            objThrGUIRpt.start();
                        }
                    }
                    else
                    {

                        mostrarMsgInf("<HTML> El Registro SELECCIONADO <FONT COLOR=\"blue\"> NO SE PUEDE IMPRIMIR </FONT>,  todavia no esta PROCESADO </HTML>");
//                        break;
                    }
                }
                else
                {
                    mostrarMsgInf("<HTML> <FONT COLOR=\"blue\"> NO HA SELECCIONADO </FONT> registros a Imprimir... </HTML>");
//                    break;
                }

//            }
        }
        else
            mostrarMsgInf("<HTML> Para realizar dicha accion, primero se debe CONSULTAR... </HTML>");
        
//        mostrarMsgInf("<HTML> La FunciÃ³n <FONT COLOR=\"blue\"> ---IMPRIMIR--- </FONT> todavÃ­a <FONT COLOR=\"blue\"> NO </FONT> esta implementada en el sistema...  </HTML>");
            
    }//GEN-LAST:event_butImpActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        k=0;
        p=0;
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

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected())
        {
            //            txtCodEmp.setText("");
            //            txtDesLarEmp.setText("");
            //            txtCodTipDoc.setText("");
            //            txtDesLarTipDoc.setText("");
            //            txtCodEmpTipDoc.setText("");
            //            txtDesLarEmpTipDoc.setText("");
        }
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
        txtCodTipDoc.requestFocus();
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
                strCodEmp=txtCodEmp.getText();
                txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
                if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
                {
                        if (txtCodEmp.getText().equals(""))
                        {
                                txtCodEmp.setText("");
                                txtNomEmp.setText("");
                            }
                        else
                        {
                                mostrarVenConEmp(1);
                            }
                    }
                else
                txtCodEmp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
                strNomEmp=txtNomEmp.getText();
                txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
                if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
                {
                        if (txtNomEmp.getText().equals(""))
                        {
                                txtCodEmp.setText("");
                                txtNomEmp.setText("");
                            }
                        else
                        {
                                mostrarVenConEmp(2);
                            }
                    }
                else
                txtNomEmp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        // TODO add your handling code here:
                mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        // TODO add your handling code here:
                strDesCorTipDoc=txtDesCorTipDoc.getText();
                txtDesCorTipDoc.selectAll();
                optFil.setSelected(true);
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.

                if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                {
                        if (txtDesCorTipDoc.getText().equals(""))
                        {
                                txtCodTipDoc.setText("");
                                txtDesLarTipDoc.setText("");
                            }
                        else
                        {
                                mostrarVenConTipDoc(1);
                            }
                    }
                else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        // TODO add your handling code here:
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
                strDesLarTipDoc=txtDesLarTipDoc.getText();
                txtDesLarTipDoc.selectAll();
                optFil.setSelected(true);
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
                if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
                {
                        if (txtDesLarTipDoc.getText().equals(""))
                        {
                                txtCodTipDoc.setText("");
                                txtDesCorTipDoc.setText("");
                            }
                        else
                        {
                                mostrarVenConTipDoc(2);
                            }
                    }
                else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
                optFil.setSelected(true);
                mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblEstDoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomEmp;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        int intCodEmp=0, intCodEmpGrp=0;
        try
        {
            //Inicializar objetos.
//            objUti=new ZafUtil();
//            strAux=objParSis.getNombreMenu();
//            this.setTitle(strAux + " V 0.3 ");
//            lblTit.setText(strAux);
            
            //titulo de la ventana
//            this.setTitle(objParSis.getNombreMenu()+ " V 0.1");
//            lblTit.setText(""+objParSis.getNombreMenu());
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            CODEMP = intCodEmpGrp;

            
            lblEmp.setVisible(false);
            txtCodEmp.setVisible(false);
            txtNomEmp.setVisible(false);
            butEmp.setVisible(false);
            txtCodTipDoc.setVisible(false);
            
//             //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(1045)) //Consultar
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(1046)) //Cerrar
            {
                butCer.setVisible(false);
            }
            
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(false);
            objSelFecDoc.setCheckBoxChecked(true);
            objSelFecDoc.setTitulo("Fecha del documento");
            objSelFecDoc.setFlechaIzquierdaSeleccionada(true);
            
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtNomEmp.setVisible(true);
                butEmp.setVisible(true);
            }
            
            //Configurar Ventana de Consulta de Cliente//
            configurarVenConTipDoc();
            
            //Configurar Ventana de Consulta de Empresa//
            configurarVenConEmp();
            
            //Configurar el combo "Estado de registro".//
            cboEstDoc.addItem("O = Transitorio Todavia");
            cboEstDoc.addItem("A = Documento Procesados");
            cboEstDoc.setSelectedIndex(0);
            
            //Configurar el Objeto de Tipo Reporte///
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();
    
//            vecCab.add(INT_TBL_DAT_LIN,"");///0
//            vecCab.add(INT_TBL_DAT_SEL,"");///1
//            vecCab.add(INT_TBL_DAT_COD_EMP,"Cï¿½d.Emp.");///2
//            vecCab.add(INT_TBL_DAT_COD_SOL,"Cod.Sol.");///3
//            vecCab.add(INT_TBL_DAT_FEC_SOL,"Fecha");///4
//            vecCab.add(INT_TBL_DAT_COD_CLI,"Cï¿½d.Cli.");///5
//            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");///6
//            vecCab.add(INT_TBL_DAT_EST_ANA,"Estado");///7
//            vecCab.add(INT_TBL_DAT_BOT_PAN,"...");///8
            
            /*Configurar Vector de Cabecera para los datos de las Columnas*/
            vecCab.add(INT_TBL_LINEA,"");//0
            vecCab.add(INT_TBL_DAT_SEL,"");///1
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");//2
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc.");//3
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc.");//4
            vecCab.add(INT_TBL_NOM_TIP_DOC,"Nom.Tip.Doc.");//5
            vecCab.add(INT_TBL_COD_DOC,"Cod.Doc.");//6
            vecCab.add(INT_TBL_NUM_DOC,"Num.Doc.");//7
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");//8
            vecCab.add(INT_TBL_VAL_DOC,"Val.Doc.");//9
            vecCab.add(INT_TBL_EST_DOC,"Est.Doc.");//10
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            objTblEdi=new ZafTblEdi(tblDat);
            setEditable(true);
                        
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();            
            tblDat.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(20);//0
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SEL).setPreferredWidth(30);///1
            tblDat.getColumnModel().getColumn(INT_TBL_COD_EMP).setPreferredWidth(60);//2
            tblDat.getColumnModel().getColumn(INT_TBL_COD_LOC).setPreferredWidth(60);//3
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(30);//4
            tblDat.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setPreferredWidth(100);//5
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(40);//6
            tblDat.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);//7
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_DOC).setPreferredWidth(110);//8
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_DOC).setPreferredWidth(100);//9
            tblDat.getColumnModel().getColumn(INT_TBL_EST_DOC).setPreferredWidth(30);//10

            //Configurar JTable: Ocultar columnas del sistema.
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setResizable(false);
            
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setResizable(false);
            
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAuxDat=new Vector();
            vecAux.add("" + INT_TBL_DAT_SEL);///1
            objTblMod.setColumnasEditables(vecAux);
            vecAuxDat = vecAux;
            vecAux=null;
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBus=new ZafTblBus(tblDat);
            //Orden de tabla cuando se da click en la cabecera
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_EST_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenValDoc=new ZafTblCelRenLbl();
            objTblCelRenValDoc.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenValDoc.setTipoFormato(objTblCelRenValDoc.INT_FOR_NUM);
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_DOC).setCellRenderer(objTblCelRenValDoc);
            objTblCelRenValDoc.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            //metodo de renderizador de la columna de seleccion//
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelRenChkMain=null;
            
            objTblCelEdiChkMain=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellEditor(objTblCelEdiChkMain);            
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    ///System.out.println("afterEdit - DESPUES -- SE SELECCIONO CELDA");
                    
                    if(objTblCelEdiChkMain.isChecked())
                    {
//                        System.out.println("---if desde el chek main---");
                        k++;
                      
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_SEL);
                        k--;
//                        System.out.println("---se desactivado k registros--- " + k);
                    }
                    
                }
            });
                        
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
    
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            
            if(intCodUsr==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                ///System.out.println("---Query Ventana consulta usuario ADMIN:--configurarVenConTipDoc()-- "+strSQL);
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                ///System.out.println("---Query Ventana consulta usuario VARIOS:--configurarVenConTipDoc()-- "+strSQL);
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Empresa".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
//            //Listado de campos.
//            ArrayList arlCam=new ArrayList();
//            arlCam.add("a1.co_emp");
//            arlCam.add("a1.tx_nom");
//            //Alias de los campos.
//            ArrayList arlAli=new ArrayList();
//            arlAli.add("Código");
//            arlAli.add("Nombre");
//            //Ancho de las columnas.
//            ArrayList arlAncCol=new ArrayList();
//            arlAncCol.add("50");
//            arlAncCol.add("414");
//            //Armar la sentencia SQL.
//
//            if(objParSis.getCodigoUsuario()==1){
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')";
//                strSQL+=" ORDER BY a1.co_emp";
//            }
//            else{
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')";
//                strSQL+=" ORDER BY a1.co_emp";
//            }
//            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empresas", strSQL, arlCam, arlAli, arlAncCol);
//            arlCam=null;
//            arlAli=null;
//            arlAncCol=null;
//            //Configurar columnas.
//            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_ruc, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_emp AS a1";
            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
            strSQL+=" AND a1.st_reg NOT IN('I','E')";
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoEmp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    
    
    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatReg()
    {
        int intCodEmp, intCodLoc, intCodTipDoc, intCanReg=0, intCodMnu, intCodUsr;
        boolean blnRes=true;
        //double dblValPnd=0.00;
        try
        {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                intCodMnu=objParSis.getCodigoMenu();
                intCodUsr=objParSis.getCodigoUsuario();
                
                if (con!=null)
                {
                    stm=con.createStatement();
                    
                    //Obtener la condiciï¿½n.
                    //strAuxTmp="";                  
                    
                     switch (objSelFecDoc.getTipoSeleccion()) 
                    {
                        case 0: //Búsqueda por rangos
                          strAux1=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux1=" AND a1.fe_dia<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                           strAux1=" AND a1.fe_dia>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.                            
                            strAux1 = " AND a1.fe_dia>='" + objUti.formatearFecha("01/01/1500", objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                            break;
                    }
                        //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT COUNT(*) ";
                        strSQL+=" FROM (";
                        strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, sum(a2.nd_mondeb) as ValDoc, a1.st_reg as EstDoc ";
                        strSQL+="  FROM tbm_cabdia AS a1";
                        strSQL+="  INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                        //strSQL+="  INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE ";
                        //@Bostel
                        //Confirmamos si entramos por grupo 
                        if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                           
                            CodEmp = txtCodEmp.getText();
                            NomEmp = txtNomEmp.getText();
                            if(CodEmp.equals("")){
//                               mostrarMsgInf("El Campo Empresa Debe Contener Datos");
//                              butEmp.requestFocus();
//                              return false;
                                lblMsgSis.setText("Cargando datos...");
                                 strSQL+="  a1.co_emp in (1,2,4)" ; 
                            //strSQL+="  AND a1.co_loc=" + 4;
                            
                            if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                            if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }                         
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        strSQL+=" ) AS A";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        
                          if (intCanReg==-1)
                          return false;
                        ///////////////////////////////////////////////////////////////////////////////////////////////////
                        
                         strSQL="";
                        strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, sum(a2.nd_mondeb) as ValDoc, a1.st_reg as EstDoc ";
                        strSQL+="  FROM tbm_cabdia AS a1";
                        strSQL+="  INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                        //strSQL+="  INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE ";
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            
                            strSQL+="  a1.co_emp in (1,2,4)" ; 
//                        strSQL+="  AND a1.co_loc=" + 4;
                        //strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                             if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        
                        
                        rst=stm.executeQuery(strSQL);
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        
                        pgrSis.setMinimum(0);
                        pgrSis.setMaximum(intCanReg);
                        pgrSis.setValue(0);
            
                        for(int i=0;rst.next();i++)
                        {                 
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_DAT_SEL,"");///1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("tx_deslar"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("co_dia"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("tx_numdia"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_dia"), "dd-MM-yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("ValDoc")));////9
                            vecReg.add(INT_TBL_EST_DOC, rst.getString("EstDoc"));////10
                            
                            vecDat.add(vecReg);
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
                        
                        if (intCanReg==tblDat.getRowCount())
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros.");
                        else
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        return true;
                        }

                            }
                            if(Integer.parseInt(CodEmp)  == 1){//Tuval
                                 lblMsgSis.setText("Cargando datos...");
                            strSQL+="  a1.co_emp=" + Integer.parseInt(CodEmp); 
                            strSQL+="  AND a1.co_loc=" + 4;
                            
                            if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                            if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        strSQL+=" ) AS A";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        
                          if (intCanReg==-1)
                          return false;
                        ///////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        strSQL="";
                        strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, sum(a2.nd_mondeb) as ValDoc, a1.st_reg as EstDoc ";
                        strSQL+="  FROM tbm_cabdia AS a1";
                        strSQL+="  INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                        //strSQL+="  INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE ";
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            
                            strSQL+="  a1.co_emp=" + CodEmp;
                        strSQL+="  AND a1.co_loc=" + 4;
                        //strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                            if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        
                        
                        rst=stm.executeQuery(strSQL);
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        
                        pgrSis.setMinimum(0);
                        pgrSis.setMaximum(intCanReg);
                        pgrSis.setValue(0);
            
                        for(int i=0;rst.next();i++)
                        {                 
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_DAT_SEL,"");///1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("tx_deslar"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("co_dia"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("tx_numdia"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_dia"), "dd-MM-yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("ValDoc")));////9
                            vecReg.add(INT_TBL_EST_DOC, rst.getString("EstDoc"));////10
                            
                            vecDat.add(vecReg);
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
                        
                        if (intCanReg==tblDat.getRowCount())
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros.");
                        else
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        return true;
       
                        }
                                         
                            }else if (Integer.parseInt(CodEmp) == 4){//Dimulti
                                lblMsgSis.setText("Cargando datos...");
                            strSQL+="  a1.co_emp=" + Integer.parseInt(CodEmp); 
                            strSQL+="  AND a1.co_loc in (3,10,12,13)";
                            
                             if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                            if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        strSQL+=" ) AS A";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        
                          if (intCanReg==-1)
                          return false;
                        ///////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        strSQL="";
                        strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, sum(a2.nd_mondeb) as ValDoc, a1.st_reg as EstDoc ";
                        strSQL+="  FROM tbm_cabdia AS a1";
                        strSQL+="  INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                        //strSQL+="  INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE ";
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            
                            strSQL+="  a1.co_emp=" + Integer.parseInt(CodEmp); 
                            strSQL+="  AND a1.co_loc in (3,10,12,13)";
                        //strSQL+="  AND a4.co_mnu=" + intCodMnu;
                         if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                            if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                     
                        rst=stm.executeQuery(strSQL);
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        
                        pgrSis.setMinimum(0);
                        pgrSis.setMaximum(intCanReg);
                        pgrSis.setValue(0);
                        
                         for(int i=0;rst.next();i++)
                        {                 
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_DAT_SEL,"");///1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("tx_deslar"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("co_dia"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("tx_numdia"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_dia"), "dd/MM/yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("ValDoc")));////9
                            vecReg.add(INT_TBL_EST_DOC, rst.getString("EstDoc"));////10
                            
                            vecDat.add(vecReg);
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
                        
                        if (intCanReg==tblDat.getRowCount())
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros.");
                        else
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        return true;
                        
                        }
                        }else if (Integer.parseInt(CodEmp) == 2){//Castek
                            lblMsgSis.setText("Cargando datos...");
                             strSQL+="  a1.co_emp=" + Integer.parseInt(CodEmp); 
                            strSQL+="  AND a1.co_loc in (1,4,6,10,11,12,13,14)";
                           // strSQL+="  AND a1.co_loc in (" + 1 + "," + 4 + "," + 6 + "," + 10 + ","+ 11+","+12+","+13+","+14+ ")";
                            if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                             if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1; 
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        strSQL+=" ) AS A";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        
                          if (intCanReg==-1)
                          return false;
                        ///////////////////////////////////////////////////////////////////////////////////////////////////
                         strSQL="";
                        strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, sum(a2.nd_mondeb) as ValDoc, a1.st_reg as EstDoc ";
                        strSQL+="  FROM tbm_cabdia AS a1";
                        strSQL+="  INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                        //strSQL+="  INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE ";
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            
                            strSQL+="  a1.co_emp=" + Integer.parseInt(CodEmp); 
                           strSQL+="  AND a1.co_loc in (1,4,6,10,11,12,13,14)";
                        //strSQL+="  AND a4.co_mnu=" + intCodMnu;
                         if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                             if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                        strSQL+= strAux1; 
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        
                                                   
                        rst=stm.executeQuery(strSQL);
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        
                        pgrSis.setMinimum(0);
                        pgrSis.setMaximum(intCanReg);
                        pgrSis.setValue(0);
                        
                        
                        for(int i=0;rst.next();i++)
                        {                 
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_DAT_SEL,"");///1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("tx_deslar"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("co_dia"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("tx_numdia"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_dia"), "dd/MM/yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("ValDoc")));////9
                            vecReg.add(INT_TBL_EST_DOC, rst.getString("EstDoc"));////10
                            
                            vecDat.add(vecReg);
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
                        
                        if (intCanReg==tblDat.getRowCount())
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros.");
                        else
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        return true;
                        }
                        }
                       //@Bostel
                    }else{
                             lblMsgSis.setText("Cargando datos...");
                            strSQL+="  a1.co_emp=" + intCodEmp;
                            strSQL+="  AND a1.co_loc=" + intCodLoc;
                        }
                      
                        ///strSQL+="  AND a4.co_mnu=" + intCodMnu;
                         if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                             if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                         strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        strSQL+=" ) AS A";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);

                        if (intCanReg==-1)
                            return false;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        strSQL="";
                        strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, sum(a2.nd_mondeb) as ValDoc, a1.st_reg as EstDoc ";
                        strSQL+="  FROM tbm_cabdia AS a1";
                        strSQL+="  INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                       // strSQL+="  INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE ";
                        strSQL+="  a1.co_emp=" + intCodEmp;
                        strSQL+="  AND a1.co_loc=" + intCodLoc;
                        //strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        if(optTod.isSelected())
                        {
                            strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                        }
                        if(optFil.isSelected())
                        {
                             if(txtDesCorTipDoc.getText().equals("")){
                                strSQL+="  AND a1.co_tipdoc in (92,184,288)";
                            }else{
                                strSQL+="  AND a1.co_tipdoc = " + txtCodTipDoc.getText();
                            }              
                        }
                        
                        if(cboEstDoc.getSelectedIndex()==0)
                            strSQL+="  AND a1.st_reg IN ('O')";
                        if(cboEstDoc.getSelectedIndex()==1)
                            strSQL+="  AND a1.st_reg IN ('A')";
                        
                         strSQL+= strAux1;
                        strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a3.tx_deslar, a1.co_dia, a1.tx_numdia, a1.fe_dia, a1.st_reg";                        
                        strSQL+="  ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia ";
                        
                        rst=stm.executeQuery(strSQL);
                        
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                       
                        pgrSis.setMinimum(0);
                        pgrSis.setMaximum(intCanReg);
                        pgrSis.setValue(0);
            
                        for(int i=0;rst.next();i++)
                        {                 
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_DAT_SEL,"");///1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("tx_deslar"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("co_dia"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("tx_numdia"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_dia"), "dd/MM/yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("ValDoc")));////9
                            vecReg.add(INT_TBL_EST_DOC, rst.getString("EstDoc"));////10
                            
                            vecDat.add(vecReg);
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
                        
                        if (intCanReg==tblDat.getRowCount())
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros.");
                        else
                            lblMsgSis.setText("Se encontraron " + intCanReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
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
    
    
    
    
    
    
    
//    /**
//     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean cargarDetReg()
//    {
//        int intCodEmp, intCodLoc, intNumTotReg, i;
//        
//        int intNumDia; 
//        String strFecSis, strFecIni;
//        int intFecIni[];
//        int intFecFin[];//para calcular los dias entre fechas
//        
//        double dblSub, dblIva;
//        
//        
//        String strEstAnaSolCre="";
//        
//        boolean blnRes=true;
//        
//        try
//        { 
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
//            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
//            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                stm=con.createStatement();
//                //Obtener la fecha del servidor.
//                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecSer==null)
//                    return false;
//                datFecAux=datFecSer;
//                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//                
//                ///System.out.println("---fecha--datFecAux "+ datFecAux);
//                ///System.out.println("---fecha--strFecSis "+ strFecSis);
//
//                //Obtener la condiciï¿½n.
//                strAux="";
//                
//                //Condicion para filtro por cliente//
//                if(intCodEmp==0)
//                {
//                    if (txtCodEmp.getText().length()>0)
//                        strAux+=" AND a1.co_emp = " + txtCodEmp.getText();
//                }
//                else
//                {
//                    strAux+=" AND a1.co_emp = " + intCodEmp;
//                }
//                
//                //Condicion para filtro por cliente//
////                if (txtCodCli.getText().length()>0)
////                    strAux+=" AND a1.co_cli = " + txtCodCli.getText();
////                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
////                    strAux+=" AND ((LOWER(a2.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a2.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
//                
//                //Condicion para Estado de Analisis//
//                if(cboEstAna.getSelectedIndex()==0)
//                {
//                    strAux+=" AND a1.st_anasol NOT IN ('A')";
//                }
//                if(cboEstAna.getSelectedIndex()==1)
//                {
//                    strAux+=" AND a1.st_anasol IN ('N')";
//                }
//                if(cboEstAna.getSelectedIndex()==2)
//                {
//                    strAux+=" AND a1.st_anasol IN ('P')";
//                }
//
//                //Obtener el nï¿½mero total de registros.
//                strSQL="";
//                strSQL+="SELECT COUNT(*)";
//                strSQL+=" FROM (";
//                strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
//                strSQL+=" FROM tbm_solcre AS a1";
//                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
//                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp;
//                strSQL+=" WHERE a1.st_reg IN ('A')";                
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
//                strSQL+=" ) AS b1";
//                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                if (intNumTotReg==-1)
//                    return false;
//
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT a1.co_emp, a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom, a1.st_anasol ";
//                strSQL+=" FROM tbm_solcre AS a1";
//                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
//                ///strSQL+=" WHERE a1.co_emp = " + intCodEmp;
//                strSQL+=" WHERE a1.st_reg IN ('A')";                
//                strSQL+=strAux;
//                strSQL+=" ORDER BY a1.co_emp, a1.co_sol ";
//                
//                System.out.println("---Query de cargarDetReg(): "+ strSQL);
//                
//                rst=stm.executeQuery(strSQL);
//                
//                //Limpiar vector de datos.
//                vecDat.clear();
//                //Obtener los registros.
//                lblMsgSis.setText("Cargando datos...");
//                pgrSis.setMinimum(0);
//                pgrSis.setMaximum(intNumTotReg);
//                pgrSis.setValue(0);
//                i=0;
//                
//                while (rst.next())
//                {
//                    if (blnCon)
//                    {
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_LIN,"");///0
//                        vecReg.add(INT_TBL_DAT_SEL,"");///1                        
//                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));///2
//                        vecReg.add(INT_TBL_DAT_COD_SOL,rst.getString("co_sol"));///3
//                        vecReg.add(INT_TBL_DAT_FEC_SOL,rst.getString("fe_sol"));///4
//                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));///5
//                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));///6
//                        vecReg.add(INT_TBL_DAT_EST_ANA,rst.getString("st_anasol"));///7
//                        vecReg.add(INT_TBL_DAT_BOT_PAN,"");///8
//                        vecDat.add(vecReg);
//                        i++;
//                        pgrSis.setValue(i);
//                        
//                        strEstAnaSolCre = rst.getString("st_anasol");
//                        
//                        if(strEstAnaSolCre.equals("P"))
//                        {
//                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_SEL);
//                            p++;
//                            ///System.out.println("---existen p reg.act.sel. "+ p);
//                        }
//                    }
//                    else
//                    {
//                        break;
//                    }
//                    
//                }///fin del while///
//                
//                rst.close();
//                stm.close();
//                con.close();
//                rst=null;
//                stm=null;
//                con=null;
//                
//                //Asignar vectores al modelo.
//                objTblMod.setData(vecDat);
//                tblDat.setModel(objTblMod);
//                vecDat.clear();
//                
//                if (intNumTotReg==tblDat.getRowCount())
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
//                else
//                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sï¿½lo se procesaron " + tblDat.getRowCount() + ".");
//                pgrSis.setValue(0);
//                butCon.setText("Consultar");
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
//    private boolean actualizarSolCre()
//    {
//        boolean blnRes=true;
//        int i=0, x=0;
//        String strEstAnaSol="";
//        try
//        {
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            con.setAutoCommit(false);
//            if (con!=null)
//            {
//                stm=con.createStatement();
//                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
//                ///System.out.println("---ENTRO EN LA FUNCION actualizarSolCre()---");
//                ///System.out.println("---fecha--- strFecSis--- " + strFecSis);
//                
//                for (i=0; i<objTblMod.getRowCountTrue(); i++)
//                {
//                    strEstAnaSol = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_EST_ANA));
//
//                    if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
//                    {
//                        ///System.out.println("---en reg. sel. strEstAnaSol---: " + strEstAnaSol + " ---i = " + i);
//
//                        if(strEstAnaSol.equals("N"))
//                        {
//                            strEstAnaSol = "P";
//
//                            ///System.out.println("---el nuevo strEstAnaSol es ---" + strEstAnaSol);
//
//                            //Armar la sentencia SQL.
//                            strSQL="";
//                            strSQL+="UPDATE tbm_solcre";
//                            strSQL+=" SET ";
//                            strSQL+=" st_anasol = '" + strEstAnaSol + "'";
//                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
//                            strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
//                            System.out.println("---SQL del update tbm_solcre---: " + strSQL);
//                            stm.executeUpdate(strSQL);
//
//
//                            //Armar la sentencia SQL.
//                            strSQL="";
//                            strSQL+="UPDATE tbm_cli";
//                            strSQL+=" SET ";
//                            strSQL+=" fe_ultActDat = '" + strFecSis + "'";
//                            ///strSQL+=" fe_ultActDat = '" + objUti.formatearFecha(datFecAux,"yyyy/MM/dd") + "'";
//                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
//                            strSQL+=" AND co_cli = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CLI));
//                            System.out.println("---SQL del update tbm_cli---: " + strSQL);
//                            stm.executeUpdate(strSQL);
//                        }
//                    }
//                    else
//                    {
//                        ///System.out.println("---else no hay reg seleccionado ---");
//                        if(strEstAnaSol.equals("P"))
//                        {
//                            strEstAnaSol = "N";
//
//                            ///System.out.println("---el nuevo strEstAnaSol es ---" + strEstAnaSol);
//
//                            //Armar la sentencia SQL.
//                            strSQL="";
//                            strSQL+="UPDATE tbm_solcre";
//                            strSQL+=" SET ";
//                            strSQL+=" st_anasol = '" + strEstAnaSol + "'";
//                            strSQL+=" WHERE co_emp = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP));;
//                            strSQL+=" AND co_sol = " + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SOL));
//                            System.out.println("---SQL del update tbm_solcre---: " + strSQL);
//                            stm.executeUpdate(strSQL);
//                        }
//                    }
//                }
//                con.commit();
//                blnRes=true;
//
//                stm.close();
//                stm=null;
//            }
//            else
//            {
//                System.out.println("---esta falso en con!=null---");
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
    
//    private boolean abrirFrm()
//    {
//        boolean blnRes=true;
//        try
//        {
//            if(tabFrm.getSelectedIndex()==1)
//            {
//                ///System.out.println("ABRIR_FRM Tab Propietarios - Accionistas...");
//                //////////////PARA LLAMAR A LA VENTANA DE CONTACTOS ////////
//                if(!(tblDat.getSelectedColumn()==-1))
//                {
//                    if(!(tblDat.getSelectedRow()==-1))
//                    {
//                        strAux="CxC.ZafCxC32.ZafCxC32";
//                        if (!strAux.equals(""))
//                            invocarClase(strAux);
//                    }
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
        
    
//    private boolean invocarClase(String clase)
//    {
//        boolean blnRes=true;
//        int intcodemp=0;
//        try
//        {
//            System.out.println("---FUNCION INVOCAR CLASE DESDE CXC47...");
//            ///System.out.println("El estado CONSULTAR es... " + c);
//            ///System.out.println("El estado MODIFICAR es... " + m);
//            intcodemp = objParSis.getCodigoEmpresa();
//            
//            ////TAB DE REPORTES////
//            if(tabFrm.getSelectedIndex()==1)
//            {
//                ///System.out.println("--1.-INVOCAR_CLASE Tab Propietarios - Accionistas...");
//                //Obtener el constructor de la clase que se va a invocar.
//                Class objVen=Class.forName(clase);
//                Class objCla[]=new Class[4];
//                objCla[0]=objParSis.getClass();         ///OBJ ZAFPARSIS///
//                objCla[1]=new Integer(0).getClass();    ///INT CODEMP///
//                objCla[2]=new Integer(0).getClass();    ///INT CODCLI///                
//                objCla[3]=new Integer(0).getClass();    ///INT CODSOLCRE///
//                
//                java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
//                
//                //Inicializar el constructor que se obtuvo.
//                Object objObj[]=new Object[4];
//                
//                ///Para verificar si ultima linea del Jtable esta vacia o en modo de Insercion///
//                if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL)==null )
//                {
//                    System.out.println("---ENTRO POR LA FILA VACIA DESDE CXC47...");
////                    objObj[0]=objParSis;
////                    objObj[1]=new Integer(intcodemp);
////                    objObj[2]=new Integer(Integer.parseInt(txtCodCli.getText()));
////                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
//                }
//                else
//                {
//                    System.out.println("---ENTRO POR LA FILA CON DATOS DESDE CXC47...");
////                    objObj[0]=objParSis;
////                    objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_EMP).toString());
////                    objObj[2]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_CLI).toString());                    
////                    objObj[3]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SOL).toString());
//                }
//                
//                javax.swing.JInternalFrame ifrVen;
//                ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
//                this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
//                ifrVen.show();
//            }
//        }
//        catch (ClassNotFoundException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (NoSuchMethodException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (SecurityException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (InstantiationException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (IllegalAccessException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (IllegalArgumentException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (java.lang.reflect.InvocationTargetException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
    /**
     * Esta funciï¿½n muestra un mensaje informativo al usuario. Se podrï¿½a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta funciï¿½n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta funciï¿½n muestra un mensaje de error al usuario. Se podrï¿½a utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    
    
    /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));///txtCodTipDoc   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
//                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        
//                        mostrarCtaPre();
//                        mostrarSaldoCta();

                    }
                    break;
                case 1: //Bï¿½squeda directa por "Descripciï¿½n corta".
                    if (vcoTipDoc.buscar("a1.tx_descor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
//                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//                        
//                        mostrarCtaPre();
//                        mostrarSaldoCta();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
//                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//                            
//                            mostrarCtaPre();
//                            mostrarSaldoCta();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoTipDoc.buscar("a1.tx_deslar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
//                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//                        
//                        mostrarCtaPre();
//                        mostrarSaldoCta();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
//                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
//                            
//                            mostrarCtaPre();
//                            mostrarSaldoCta();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                default:
                    txtDesCorTipDoc.requestFocus();
                    break;
                    
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   
    /**
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDatReg())
            ///if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
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
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
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
                case 0: //Botï¿½n "Imprimir".
                    ///objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    ///objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botï¿½n "Vista Preliminar".
                    ///objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    ///objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }
        
        /**
         * Esta funciï¿½n establece el indice de la funciï¿½n a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta funciï¿½n sirve para determinar
         * la funciï¿½n que debe ejecutar el Thread.
         * @param indice El indice de la funciï¿½n a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
    /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer, strBan="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
//        String CodCli = txtCodCli.getText();
//        String NomCli = txtDesLarCli.getText();
        String NomUsrFun = rtnNomUsrSis();
        String NomUsr = objParSis.getNombreUsuario();
        String strCodEmp = String.valueOf(intCodEmp);
        String strCodLoc = String.valueOf(intCodLoc);
        
        
//        STRBAN="";
//        
//        STRBAN=FilSql();       
        
        
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.show();
            
            //Inicializar los parametros que se van a pasar al reporte.
            java.util.Map mapPar=new java.util.HashMap();
            
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd HH:mm:ss");
                datFecAux=null;
                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 102:
                                    
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    
//                                    System.out.println("COD_EMP: " + strCodEmp);                                    
//                                    System.out.println("COD_CLI: " + CodCli);
//                                    System.out.println("NOM_CLI: " + NomCli);
//                                    System.out.println("NOM_USR: " + NomUsr);                                  
//                                    //////////////////
//                                    System.out.println("FEC_SER: " + strFecHorSer);
//                                    System.out.println("DIR_RPT: " + strRutRpt);
//                                    System.out.println("NOM_RPT: " + strNomRpt);
////                                    System.out.println("STRBAN: " + STRBAN);///STRBAN
//                                    /////////////////
                                    
//                                    mapPar.put("CodEmp", ""+strCodEmp);
//                                    mapPar.put("CodLoc", ""+strCodLoc);
//                                    mapPar.put("CodTipDoc", ""+strCodTipDoc);
//                                    mapPar.put("CodDia", ""+strCodDia);
//                                    mapPar.put("CodCli", ""+CodCli);
//                                    mapPar.put("NomCli", ""+NomCli);
//                                    mapPar.put("FecImp", ""+strFecHorSer);
//                                    mapPar.put("NomUsr", ""+NomUsrFun);
//                                    mapPar.put("DirRpt", ""+strRutRpt);
//                                    mapPar.put("Ban", ""+STRBAN);
                                    
                                    for (i=0; i<objTblMod.getRowCountTrue(); i++)
                                    {
                                        if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                                        {
                                            mapPar.put("CodEmp", "" + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_EMP)));
                                            mapPar.put("CodLoc", "" + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_LOC)));
                                            mapPar.put("CodTipDoc", "" + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC)));
                                            mapPar.put("CodDia", "" + objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_DOC)));
                                            mapPar.put("FecImp", ""+strFecHorSer);
                                            mapPar.put("NomUsr", ""+NomUsrFun);
                                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        }
                                    }

                                    
                            break;
                            
//                            case 261:
//                                    System.out.println("---ENTRO EN EL REPORTE...menu 261...");
//                                    strRutRpt=objRptSis.getRutaReporte(i);
//                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    
//                                    System.out.println("COD_EMP: " + strCodEmp);
//                                    System.out.println("COD_CLI: " + CodCli);
//                                    System.out.println("NOM_CLI: " + NomCli);
//                                    System.out.println("NOM_USR: " + NomUsr);                                   
//                                    //////////////////
//                                    System.out.println("FEC_SER: " + strFecHorSer);
//                                    System.out.println("DIR_RPT: " + strRutRpt);
//                                    System.out.println("NOM_RPT: " + strNomRpt);
////                                    System.out.println("STRBAN: " + STRBAN);///STRBAN
//                                    /////////////////
//
//                                    
//                                    mapPar.put("CodEmp", ""+strCodEmp);
//                                    mapPar.put("CodCli", ""+CodCli);
//                                    mapPar.put("NomCli", ""+NomCli);
//                                    mapPar.put("FecImp", ""+strFecHorSer);
//                                    mapPar.put("DirRpt", ""+strRutRpt);
//                                    mapPar.put("Ban", ""+STRBAN);
                                    
//                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
//                            break;
                        }
                    }
                }
            }
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
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
//                case INT_TBL_DAT_LIN:
//                    strMsg="";
//                    break;
//                case INT_TBL_DAT_SEL:
//                    strMsg="";
//                    break;
//                case INT_TBL_DAT_COD_EMP:
//                    strMsg="Cï¿½digo de la empresa";
//                    break;
//                case INT_TBL_DAT_COD_SOL:
//                    strMsg="Cï¿½digo de la Solicitud de Credito";
//                    break;
//                case INT_TBL_DAT_FEC_SOL:
//                    strMsg="Fecha de la Solicitud de Credito";
//                    break;                    
//                case INT_TBL_DAT_COD_CLI:
//                    strMsg="Cï¿½digo del cliente";
//                    break;
//                case INT_TBL_DAT_NOM_CLI:
//                    strMsg="Nombre del cliente";
//                    break;
//                case INT_TBL_DAT_EST_ANA:
//                    strMsg="Estado de Solicitud";
//                    break;
//                case INT_TBL_DAT_BOT_PAN:
//                    strMsg="Boton para Visualizar el Panel de Control";
//                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    protected String rtnNomUsrSis()
    {
	java.sql.Connection conUltRegDoc;
        java.sql.Statement stmUltRegDoc;
        java.sql.ResultSet rstUltRegDoc;
        String strSQL, stRegRep="";
        int intAux=0;
        int codusr= objParSis.getCodigoUsuario();

        try
        {
               conUltRegDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
               conUltRegDoc.setAutoCommit(false);
                    if (conUltRegDoc!=null)
                    {                        
                        stmUltRegDoc=conUltRegDoc.createStatement();
                        strSQL="";
                        strSQL+="SELECT co_usr, tx_usr, tx_pwd, tx_nom";
                        strSQL+=" FROM tbm_usr AS a1";
                        strSQL+=" WHERE a1.co_usr= " + codusr;
                        strSQL+=" AND a1.st_usrSis='S'";
                        strSQL+=" AND a1.st_reg='A'";
                        System.out.println("Query para el mostrar usuarios del sistema es: " + strSQL);
                        rstUltRegDoc=stmUltRegDoc.executeQuery(strSQL);
                        
                        if (rstUltRegDoc.next())
                        {
                            stRegRep = rstUltRegDoc.getString("tx_nom");
                        }
                        System.out.println("El nombre del usuario es: " + stRegRep);
                        
                    }
                }
                catch (java.sql.SQLException e)
                {
                    objUti.mostrarMsgErr_F1(this, e);
                }
                catch (Exception e)
                {
                    objUti.mostrarMsgErr_F1(this, e);
                }  
        return stRegRep;
    }
    
}