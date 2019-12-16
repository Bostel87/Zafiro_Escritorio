/*
 * ZafCxC52.java
 *
 * Created on 05 de Noviembre de 2008, 14:26 PM
 * INDICE DE REPORTES - CARTAS DE AVISO A CLIENTES
 * DESARROLLADO POR DARIO CARDENAS EL 25/FEB/2009
 * 
 */
package CxC.ZafCxC52;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafRptSis.ZafRptSis;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;//para visualizar una ventana de programa desde una columna de botones
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;

/**
 *
 * @author  DARIO CARDENAS
 */
public class ZafCxC52 extends javax.swing.JInternalFrame 
{
//    //Constantes: Columnas del JTable:
//    final int INT_TBL_DAT_LIN=0;                        //Lï¿½nea.
//    final int INT_TBL_DAT_SEL=1;                        //Seleccion.
//    final int INT_TBL_DAT_COD_EMP=2;                    //Cï¿½digo de la empresa.
//    final int INT_TBL_DAT_COD_SOL=3;                    //Codigo de Solicitud.
//    final int INT_TBL_DAT_FEC_SOL=4;                    //Fecha de Solicitud.
//    final int INT_TBL_DAT_COD_CLI=5;                    //Codigo del cliente.
//    final int INT_TBL_DAT_NOM_CLI=6;                    //Nombre del cliente.
//    final int INT_TBL_DAT_EST_ANA=7;                    //Estado de Analisis de Solicitud de Credito.
//    final int INT_TBL_DAT_BOT_PAN=8;                    //Boton de Panel de Control.
    
    /* Declaracion de CONSTANTES*/ 
    final int INT_TBL_LINEA          =0;            //LINEA DE NUMEROS DE REGISTROS EN LA TABLA///
    final int INT_TBL_SEL            =1;            //CASILLA DE SELECCION
    final int INT_TBL_COD_EMP        =2;            //CODIGO DE LA EMPRESA
    final int INT_TBL_COD_SOL        =3;            //CODIGO DE SOLICITUD
    final int INT_TBL_FEC_SOL        =4;            //FECHA DE SOLICITUD
    final int INT_TBL_COD_CLI        =5;            //CODIGO DEL CLIENTE
    final int INT_TBL_NOM_CLI        =6;            //NOMBRE DEL CLIENTE
    final int INT_TBL_FEC_ULT_ACT_DAT=7;            //FECHA DE ULTIMA ACTUALIZACION DE DATOS
    final int INT_TBL_MES_PRO_ACT_DAT=8;            //NUMERO DE MES PROXIMA ACTUALIZACION DE DATOS
    final int INT_TBL_FEC_PRO_ACT_DAT=9;            //FECHA DE PROXIMA ACTUALIZACION DE DATOS
    final int INT_TBL_BOT_PAN        =10;            //BOTON PARA ACCEDER AL PANEL DE CONTROL
    
    //Variables
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc;
    private ZafUtil objAux;
   // private java.util.Date datFecAux;
    private java.util.Date datFecSer, datFecVen, datFecAux;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private ZafParSis objParSis, objZafParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
//    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblBus objTblBus;                        //Editor de bï¿½squeda.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux, STRFECCON="", STRBAN="";
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strNomEmp,strCodTipDoc, strDesCorTipDoc, strDesLarTipDoc, strCodEmpTipDoc, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, k=0, p=0, CODEMP=0, intButCon=0;
    private ZafVenCon vcoCli, vcoEmp, vcoTipDoc;                   //Ventana de consulta.
    private Vector vecAux, vecAuxDat;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;
    private ZafTblCelEdiBut objTblCelEdiButCli;
    private ZafTblCelRenBut objTblCelRenButCli;
    
    java.util.Calendar objFec = java.util.Calendar.getInstance();
    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC52(ZafParSis obj) 
    {
//        initComponents();
//        //Inicializar objetos.
//        objParSis=obj;
//        //butTipDoc.setVisible(false);
        try
        {  
            initComponents();
            //Inicializar objetos.
            objParSis=obj;
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
        panFil = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblAte = new javax.swing.JLabel();
        txtAte = new javax.swing.JTextField();
        lblDep = new javax.swing.JLabel();
        txtDep = new javax.swing.JTextField();
        lblFecConver = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        butLimpiar = new javax.swing.JButton();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
        panFil.add(lblCli);
        lblCli.setBounds(24, 20, 70, 15);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(120, 20, 66, 20);

        txtNomCli.setMaximumSize(null);
        txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panFil.add(txtNomCli);
        txtNomCli.setBounds(188, 20, 420, 20);

        butCli.setFont(new java.awt.Font("SansSerif", 1, 12));
        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(610, 20, 24, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("Atencion:");
        lblAte.setPreferredSize(new java.awt.Dimension(110, 15));
        panFil.add(lblAte);
        lblAte.setBounds(24, 60, 70, 15);

        txtAte.setMaximumSize(null);
        txtAte.setPreferredSize(new java.awt.Dimension(70, 20));
        panFil.add(txtAte);
        txtAte.setBounds(120, 60, 488, 20);

        lblDep.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDep.setText("Departamento:");
        lblDep.setPreferredSize(new java.awt.Dimension(110, 15));
        panFil.add(lblDep);
        lblDep.setBounds(24, 100, 90, 15);

        txtDep.setMaximumSize(null);
        txtDep.setPreferredSize(new java.awt.Dimension(70, 20));
        panFil.add(txtDep);
        txtDep.setBounds(120, 100, 488, 20);

        lblFecConver.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecConver.setText("Fecha Conversación:");
        lblFecConver.setPreferredSize(new java.awt.Dimension(110, 15));
        panFil.add(lblFecConver);
        lblFecConver.setBounds(24, 140, 130, 15);

        tabFrm.addTab("Filtro", panFil);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butVisPre.setText("Vista Preliminar");
        butVisPre.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
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

        butLimpiar.setText("Limpiar");
        butLimpiar.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butLimpiar.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpiarActionPerformed(evt);
            }
        });
        panBot.add(butLimpiar);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        k=0;
        p=0;
        
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Vista Preliminar...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
//                intButCon=2;
            }
        }
        
}//GEN-LAST:event_butVisPreActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
//                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
//            optFil.setSelected(true);
        }
        
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
//                txtNomCli.setText("");
//                objTblModAcc.removeAllRows();
            } else {
                mostrarVenConCli(2);
//                if (txtCodCli.getText().equals("")) {
//                    objTblModAcc.removeAllRows();
//                }
//                mostrarDatCli();
            }
        } else
            txtNomCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
//            optFil.setSelected(true);
        }
}//GEN-LAST:event_butCliActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:
        
        k=0;
        p=0;
        
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Vista Preliminar...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
//                intButCon=2;
            }
        }
}//GEN-LAST:event_butImpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        
        dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y"); 
        dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        dtpFecDoc.setText("");
        panFil.add(dtpFecDoc);
        dtpFecDoc.setBounds(140, 135, 120, 20);
    }//GEN-LAST:event_formInternalFrameOpened

    private void butLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpiarActionPerformed
        // TODO add your handling code here:
        txtCodCli.setText("");
        txtNomCli.setText("");
        txtAte.setText("");
        txtDep.setText("");
        dtpFecDoc.setText("");
        STRFECCON = "";
}//GEN-LAST:event_butLimpiarActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butLimpiar;
    private javax.swing.JButton butVisPre;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDep;
    private javax.swing.JLabel lblFecConver;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtDep;
    private javax.swing.JTextField txtNomCli;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        int intCodEmp=0, intCodEmpGrp=0;
        try
        {            
            ////titulo de la ventana////
            this.setTitle(objParSis.getNombreMenu()+ " V 0.4");
            lblTit.setText(""+objParSis.getNombreMenu());
            
            ////configurar los campoos Obligatorios////
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            //Obtener la fecha y hora del servidor.//
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            
            String strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
            
            System.out.println("---FEC_DIA_HOY ES strFecHorSer: " + strFecHorSer);
            datFecAux=null;
            
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            CODEMP = intCodEmpGrp;
            
            System.out.println("---intCodEmp--- " + intCodEmp);
            System.out.println("---CODEMPGRP--- " + CODEMP);
            
            ////Configurar Ventana de Consulta de Cliente////
            configurarVenConCli();
            
            ////Configurar Ventana de Consulta de Empresa////
            configurarVenConEmp();
            
                        
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();

            
            /*Configurar Vector de Cabecera para los datos de las Columnas*/
            vecCab.add(INT_TBL_LINEA,"");//0
            vecCab.add(INT_TBL_SEL,"");//1
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");//2
            vecCab.add(INT_TBL_COD_SOL,"Cod.Sol.");//3
            vecCab.add(INT_TBL_FEC_SOL,"Fec.Sol.");//4
            vecCab.add(INT_TBL_COD_CLI,"Cod.Cli.");//5
            vecCab.add(INT_TBL_NOM_CLI,"Nom.Cli.");//6
            vecCab.add(INT_TBL_FEC_ULT_ACT_DAT,"Fec.Ult.Act.Dat.");///7
            vecCab.add(INT_TBL_MES_PRO_ACT_DAT,"Mes.Pro.Act.Dat.");///8
            vecCab.add(INT_TBL_FEC_PRO_ACT_DAT,"Fec.Pro.Act.Dat.");///9
            vecCab.add(INT_TBL_BOT_PAN,"...");//10
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            objTblCelRenLbl=null;
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
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("Direccion");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");

            /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                System.out.println("---QUERY PARA CONCLI---: " + strSQL);
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM  tbr_cliloc AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.st_cli='S' AND a2.st_reg='A'";
                strSQL+=" ORDER BY a2.tx_nom";
                System.out.println("---QUERY PARA CONCLI POR LOCAL---: " + strSQL);
            }
            
/*Ultimo query para consultar Clientes*/            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            if (objParSis.getCodigoMenu()==319)
//                strSQL+=" AND a1.st_cli='S'";
//            else
//                strSQL+=" AND a1.st_prv='S'";
//            strSQL+=" ORDER BY a1.tx_nom";
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("Direccion");
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
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
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
        double dblValSal=0.00;
        String strFecActSer="";
        try
        {
//            butVisPre.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
            
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strFecActSer=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
                ////strFecActSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
                STRFECCON = "'" + strFecActSer + "'";
                
                System.out.println("---cargarDatReg()---strFecActSer---: " + strFecActSer);
                 datFecAux=null;
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                intCodMnu=objParSis.getCodigoMenu();
                intCodUsr=objParSis.getCodigoUsuario();
                
                if (con!=null)
                {
                    stm=con.createStatement();
                    
                        //Obtener la condicion Auxiliar.
                        strAux="";
                        
                        ////////Condicion para filtro por cliente
                        if (txtCodCli.getText().length()>0)
                            strAux+=" AND a1.co_cli=" + txtCodCli.getText();

                        ////////Condicion para filtro por cliente en un rango especifico
//                        if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
//                            strAux+=" AND ((LOWER(a2.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a2.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                    
/* 

SELECT a3.co_cli, a3.tx_ide, a3.tx_nom, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal
FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cli AS A3
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc
AND a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli
AND a1.co_emp=1
AND a1.co_loc=4
AND a3.st_cli='S'
AND a1.st_reg IN ('A','R','C','F')
AND a2.st_reg IN ('A','C')
AND a1.co_cli=2530
GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom
HAVING SUM(a2.mo_pag+a2.nd_abo)<>0
ORDER BY a3.tx_nom

 */                    

////////////                        //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
////////////                        strSQL="";
////////////                        strSQL+=" SELECT COUNT(*) ";
////////////                        strSQL+=" FROM (";
////////////                        strSQL+="  SELECT a3.co_cli, a3.tx_ide, a3.tx_nom, a3.tx_dir, a3.tx_tel, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal ";
////////////                        strSQL+="  FROM tbm_cabMovInv as a1 ";
////////////                        strSQL+="  INNER JOIN tbm_pagMovInv as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
////////////                        strSQL+="  INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli) ";
////////////                        strSQL+="  INNER JOIN tbm_cabtipdoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
////////////
////////////                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
////////////                        {
////////////                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
////////////                            strSQL+=strAux;
////////////                            strSQL+=" AND a3.st_cli='S' AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";
////////////                            strSQL+=" AND a2.nd_monchq IS NULL AND a4.tx_natDoc='E' ";
////////////                            strSQL+=" AND a2.fe_ven < '" + strFecActSer + "'";
////////////                            strSQL+=" GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom, a3.tx_dir, a3.tx_tel ";
////////////                            strSQL+=" HAVING SUM(a2.mo_pag+a2.nd_abo)<>0 ";
////////////                            strSQL+=" ORDER BY a3.tx_nom ";
////////////                        }
////////////                        else
////////////                        {
////////////                            strSQL+=" INNER JOIN tbr_cliloc AS a5 ON (a3.co_emp=a5.co_emp and a3.co_cli=a5.co_cli)";
////////////                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
////////////                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
////////////                            strSQL+=strAux;
////////////                            strSQL+=" AND a3.st_cli='S' AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";
////////////                            strSQL+=" AND a2.nd_monchq IS NULL AND a4.tx_natDoc='E' ";
////////////                            strSQL+=" AND a2.fe_ven < '" + strFecActSer + "'";
////////////                            strSQL+=" GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom, a3.tx_dir, a3.tx_tel ";
////////////                            strSQL+=" HAVING SUM(a2.mo_pag+a2.nd_abo)<>0 ";
////////////                            strSQL+=" ORDER BY a3.tx_nom ";
////////////                        }
////////////
////////////                        strSQL+=" ) AS A";
////////////
//////////////                        System.out.println("---COUNT---Estoy en la Funcion cargarDatReg()--: " + strSQL);
////////////
////////////                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
////////////                        if (intCanReg==-1)
////////////                            return false;
                        
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        
                        strSQL="";
                        strSQL+="  SELECT a3.co_cli, a3.tx_ide, a3.tx_nom, a3.tx_dir, a3.tx_tel, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal ";
                        strSQL+="  FROM tbm_cabMovInv as a1 ";
                        strSQL+="  INNER JOIN tbm_pagMovInv as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                        strSQL+="  INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp and a1.co_cli=a3.co_cli) ";
                        strSQL+="  INNER JOIN tbm_cabtipdoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                        {
                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=strAux;
                            strSQL+=" AND a3.st_cli='S' AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";
                            strSQL+=" AND a2.nd_monchq IS NULL AND a4.tx_natDoc='E' ";
                            strSQL+=" AND a2.fe_ven < '" + strFecActSer + "'";
                            strSQL+=" GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom, a3.tx_dir, a3.tx_tel ";
                            strSQL+=" HAVING SUM(a2.mo_pag+a2.nd_abo)<>0 ";
                            strSQL+=" ORDER BY a3.tx_nom ";
                        }
                        else
                        {
                            strSQL+=" INNER JOIN tbr_cliloc AS a5 ON (a3.co_emp=a5.co_emp and a3.co_cli=a5.co_cli)";
                            strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                            strSQL+=" AND a3.co_loc = " + intCodLoc + "";
                            strSQL+=strAux;
                            strSQL+=" AND a3.st_cli='S' AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";
                            strSQL+=" AND a2.nd_monchq IS NULL AND a4.tx_natDoc='E' ";
                            strSQL+=" AND a2.fe_ven < '" + strFecActSer + "'";
                            strSQL+=" GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom, a3.tx_dir, a3.tx_tel ";
                            strSQL+=" HAVING SUM(a2.mo_pag+a2.nd_abo)<>0 ";
                            strSQL+=" ORDER BY a3.tx_nom ";
                        }                        
                        
                        ///System.out.println("---Estoy en la Funcion cargarDetRegOpc2--Para las que NO SON RETENCIONES: " + strSQL);   
                        System.out.println("---Estoy en la Funcion cargarDatReg()--: " + strSQL);   
                        
                        rst=stm.executeQuery(strSQL);  
                        
                        ////for(int i=0;rst.next();i++)
                        if(rst.next())
                        {
                            dblValSal = Double.parseDouble(rst.getString("nd_sal"));
                        }                    
                        
                        System.out.println("---dblValSal---cargarDatReg--: " + dblValSal);
                        
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;                        
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
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
//    private class ZafThreadGUI extends Thread
//    {
//        public void run()
//        {
//            if (!cargarDatReg())
//            ///if (!cargarDetReg())
//            {
//                //Inicializar objetos si no se pudo cargar los datos.
//                lblMsgSis.setText("Listo");
//                pgrSis.setValue(0);
//                butVisPre.setText("Consultar");
//            }
//            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
////            if (tblDat.getRowCount()>0)
////            {
////                tabFrm.setSelectedIndex(1);
////                tblDat.setRowSelectionInterval(0, 0);
////                tblDat.requestFocus();
////            }
//            objThrGUI=null;
//        }
//    }
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            cargarDatReg();
            
            switch (intIndFun)
            {
                case 0: //Botï¿½n "Imprimir".
                    ///objTooBar.setEnabledImprimir(false);
                    generarRpt(0);
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
        String strRutRpt, strNomRpt, strFecHorSer, strBan="", strFecActSer="", Observacion="", strFecConver="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String strSubRpt;
        
        int intCodEmp = objParSis.getCodigoEmpresa();
        int intCodLoc = objParSis.getCodigoLocal();
        String NomEmp = objParSis.getNombreEmpresa();
        String CodCli = txtCodCli.getText();
        String NomCli = txtNomCli.getText();
        String strAte = txtAte.getText();
        String strDep = txtDep.getText();
        String NomUsr = objParSis.getNombreUsuario();
        String NomUsrFun = rtnNomUsrSis();
        String strCodEmp = String.valueOf(intCodEmp);
        String strCodLoc = String.valueOf(intCodLoc);
        String strFecHorSerNew = "";
        String strNumCtaEmp="", strNomEmp="";
        String strEmailEmp="";
        
        ///System.out.println("---COD_EMP: " + strCodEmp);///
        
        
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.show();
            
            /////Inicializar los parametros que se van a pasar al reporte./////
            java.util.Map mapPar=new java.util.HashMap();
            
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
                System.out.println(" ");
                //Obtener la fecha y hora del servidor.//
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                ///strFecHorSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd HH:mm:ss");///
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd' de 'MMMMM' del 'yyyy");////original
                
                strFecActSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd");

                String strFecDia=objUti.formatearFecha(datFecAux, "dd");
                System.out.println("---strFecDia: " + strFecDia);

                String strFecMes=objUti.formatearFecha(datFecAux, "MM");
                System.out.println("---strFecMes: " + strFecMes);
                if(strFecMes.equals("01"))
                    strFecMes = "Enero";
                if(strFecMes.equals("02"))
                    strFecMes = "Febrero";
                if(strFecMes.equals("03"))
                    strFecMes = "Marzo";
                if(strFecMes.equals("04"))
                    strFecMes = "Abril";
                if(strFecMes.equals("05"))
                    strFecMes = "Mayo";
                if(strFecMes.equals("06"))
                    strFecMes = "Junio";
                if(strFecMes.equals("07"))
                    strFecMes = "Julio";
                if(strFecMes.equals("08"))
                    strFecMes = "Agosto";
                if(strFecMes.equals("09"))
                    strFecMes = "Septiembre";
                if(strFecMes.equals("10"))
                    strFecMes = "Octubre";
                if(strFecMes.equals("11"))
                    strFecMes = "Noviembre";
                if(strFecMes.equals("12"))
                    strFecMes = "Diciembre";
                System.out.println("---NUEVO--- strFecMes: " + strFecMes);
                String strFecAnio=objUti.formatearFecha(datFecAux, "yyyy");
                System.out.println("---strFecAnio: " + strFecAnio);

                strFecHorSer = "" + strFecDia + " de " + strFecMes + " del " + strFecAnio + "";
                System.out.println("---NUEVO--- strFecHorSer: " + strFecHorSer);

                datFecAux=null;
                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                System.out.println("---intNumTotRpt: " + intNumTotRpt);


                if(intCodEmp==1)
                {
                    strNumCtaEmp="12006315920";
                    strEmailEmp="cobranzas@tuvalsa.com, cobranzas2@tuvalsa.com";
                }

                if(intCodEmp==2)
                {
                    strNumCtaEmp="12006315947";
                    strEmailEmp="cobranzas@castek.ec";
                }

                if(intCodEmp==4)
                {
                    strNumCtaEmp="12006315890";
                    strEmailEmp="cobranzas@dimulti.com";
                }

                strNomEmp = " Cta.Aho. " + strNumCtaEmp + " a nombre de " + NomEmp;
                System.out.println("---strNomEmp: " + strNomEmp);
                System.out.println("---strEmailEmp: " + strEmailEmp);
                
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 328:
                                    System.out.println(" ");
                                    System.out.println("---ENTRO EN EL REPORTE...RPT-328...1er.AVISO---");
                                    System.out.println("---i: " + i);
                                    
                                    strFecConver=dtpFecDoc.getText();
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    /////strSubRpt=objRptSis.getRutaReporte(92);///
                                    System.out.println("---objParSis.getDirectorioSistema(): " + objParSis.getDirectorioSistema());
                                    strSubRpt= objParSis.getDirectorioSistema() + "/Reportes/CxC/ZafCxC52/";
                                    
                                    System.out.println("COD_EMP: " + strCodEmp);
                                    System.out.println("COD_LOC: " + strCodLoc);
                                    System.out.println("COD_CLI: " + CodCli);
                                    System.out.println("NOM_CLI: " + NomCli);
                                    System.out.println("NOM_USR: " + NomUsr);
                                    System.out.println("strAte: " + strAte);
                                    System.out.println("strDep: " + strDep);
                                    System.out.println("NOM_USRFUN: " + NomUsrFun);
                                    System.out.println("FEC_SER: " + strFecHorSer);
                                    System.out.println("DIR_RPT: " + strRutRpt);
                                    System.out.println("NOM_RPT: " + strNomRpt);
                                    System.out.println("STRFECCON: " + STRFECCON);
                                    System.out.println("strFecConver: " + strFecConver);
                                    System.out.println("REPORT_DIR: " + strSubRpt);

                                    System.out.println("strFecHorSer: " + strFecHorSer);
                                    System.out.println("strFecActSer: " + strFecActSer);
                                    ////////////////////////////////////////////////////
                                    
                                    mapPar.put("CodEmp", "" + strCodEmp);
                                    mapPar.put("NomEmp", "" + strNomEmp);
                                    mapPar.put("CodLoc", "" + strCodLoc);
                                    mapPar.put("CodCli", "" + CodCli);
                                    mapPar.put("NomCli", "" + NomCli);
                                    mapPar.put("FecImp", "" + strFecHorSer);
                                    mapPar.put("Obser",  "" + Observacion);
                                    mapPar.put("NomUsr", "" + NomUsrFun);
                                    mapPar.put("Ban", ""+STRBAN);
                                    mapPar.put("Ate", "" + strAte);
                                    mapPar.put("Dep", "" + strDep);
                                    mapPar.put("DirRpt", "" + strRutRpt);
                                    mapPar.put("FecCon", "" + STRFECCON);
                                    mapPar.put("FecAco", "" + strFecConver);
                                    mapPar.put("REPORT_DIR", "" + strSubRpt);
                                    mapPar.put("EmailEmp", "" + strEmailEmp);

                                    ///////////////////////////////////////////////////
                                    
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                            break;
                            
                            case 346:
                                    System.out.println(" ");
                                    System.out.println("---ENTRO EN EL REPORTE...RPT-346...2do.AVISO---CON ESTADO D CUENTA---");
                                    System.out.println("---i: " + i);

                                    strFecConver=dtpFecDoc.getText();
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    ///strSubRpt=objRptSis.getRutaReporte(92);
                                    System.out.println("---objParSis.getDirectorioSistema(): " + objParSis.getDirectorioSistema());
                                    strSubRpt= objParSis.getDirectorioSistema() + "/Reportes/CxC/ZafCxC52/";
                                                                        
                                    System.out.println("COD_EMP: " + strCodEmp);
                                    System.out.println("COD_LOC: " + strCodLoc);
                                    System.out.println("COD_CLI: " + CodCli);
                                    System.out.println("NOM_CLI: " + NomCli);
                                    System.out.println("NOM_USR: " + NomUsr);
                                    System.out.println("strAte: " + strAte);
                                    System.out.println("strDep: " + strDep);
                                    System.out.println("NOM_USRFUN: " + NomUsrFun);
                                    System.out.println("FEC_SER: " + strFecHorSer);
                                    System.out.println("DIR_RPT: " + strRutRpt);
                                    System.out.println("NOM_RPT: " + strNomRpt);
                                    System.out.println("STRFECCON: " + STRFECCON);
                                    System.out.println("strFecConver: " + strFecConver);
                                    System.out.println("REPORT_DIR: " + strSubRpt);
                                    
                                    ///////////////////////////////////////////

                                    mapPar.put("CodEmp", "" + strCodEmp);
                                    mapPar.put("NomEmp", "" + strNomEmp);
                                    mapPar.put("CodLoc", "" + strCodLoc);
                                    mapPar.put("CodCli", "" + CodCli);
                                    mapPar.put("NomCli", "" + NomCli);
                                    mapPar.put("FecImp", "" + strFecHorSer);
                                    mapPar.put("Obser",  "" + Observacion);
                                    mapPar.put("NomUsr", "" + NomUsrFun);
                                    mapPar.put("Ban", ""+STRBAN);
                                    mapPar.put("Ate", "" + strAte);
                                    mapPar.put("Dep", "" + strDep);
                                    mapPar.put("DirRpt", "" + strRutRpt);
                                    mapPar.put("FecCon", "" + STRFECCON);
                                    mapPar.put("FecAco", "" + strFecConver);
                                    mapPar.put("REPORT_DIR", "" + strSubRpt);
                                    mapPar.put("EmailEmp", "" + strEmailEmp);

                                    //////////////////////////////////////////
                                    
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                            break;
                            
                            case 347:
                                    System.out.println(" ");
                                    System.out.println("---ENTRO EN EL REPORTE...RPT-347...AVISO DE CONFIRMACIÓN--- ");
                                    System.out.println("---dtpFecDoc.getText(): " + dtpFecDoc.getText());
                                    
                                    ///if(dtpFecDoc.getText().equals(""))///
                                    if(!dtpFecDoc.isFecha())
                                    {
                                        System.out.println("---EN BLANCO--- dtpFecDoc.getText(): " + dtpFecDoc.getText());
                                        tabFrm.setSelectedIndex(0);
                                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Conversación</FONT> es obligatorio.<BR>Escoja la fecha de conversación y vuelva a intentarlo.</HTML>");
                                        dtpFecDoc.requestFocus();
                                        break;
                                    }
                                    else
                                    {                
                                        
                                        System.out.println("---i: " + i);
                                        strFecConver=dtpFecDoc.getText();
                                        strRutRpt=objRptSis.getRutaReporte(i);
                                        strNomRpt=objRptSis.getNombreReporte(i);
                                        ///strSubRpt=objRptSis.getRutaReporte(92);
                                        System.out.println("---objParSis.getDirectorioSistema(): " + objParSis.getDirectorioSistema());
                                        strSubRpt= objParSis.getDirectorioSistema() + "/Reportes/CxC/ZafCxC52/";

                                        System.out.println("COD_EMP: " + strCodEmp);
                                        System.out.println("COD_LOC: " + strCodLoc);
                                        System.out.println("COD_CLI: " + CodCli);
                                        System.out.println("NOM_CLI: " + NomCli);
                                        System.out.println("NOM_USR: " + NomUsr);
                                        System.out.println("strAte: " + strAte);
                                        System.out.println("strDep: " + strDep);
                                        System.out.println("NOM_USRFUN: " + NomUsrFun);
                                        System.out.println("FEC_SER: " + strFecHorSer);
                                        System.out.println("DIR_RPT: " + strRutRpt);
                                        System.out.println("NOM_RPT: " + strNomRpt);
                                        System.out.println("STRFECCON: " + STRFECCON);
                                        System.out.println("strFecConver: " + strFecConver);
                                        System.out.println("REPORT_DIR: " + strSubRpt);

                                        //////////////////////////////////////////
                                        
                                        mapPar.put("CodEmp", "" + strCodEmp);
                                        mapPar.put("NomEmp", "" + strNomEmp);
                                        mapPar.put("CodLoc", "" + strCodLoc);
                                        mapPar.put("CodCli", "" + CodCli);
                                        mapPar.put("NomCli", "" + NomCli);
                                        mapPar.put("FecImp", "" + strFecHorSer);
                                        mapPar.put("Obser",  "" + Observacion);
                                        mapPar.put("NomUsr", "" + NomUsrFun);
                                        mapPar.put("Ban", ""+STRBAN);
                                        mapPar.put("Ate", "" + strAte);
                                        mapPar.put("Dep", "" + strDep);
                                        mapPar.put("DirRpt", "" + strRutRpt);
                                        mapPar.put("FecCon", "" + STRFECCON);
                                        mapPar.put("FecAco", "" + strFecConver);
                                        mapPar.put("REPORT_DIR", "" + strSubRpt);
                                        mapPar.put("EmailEmp", "" + strEmailEmp);

                                        //////////////////////////////////////////

                                        objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    }
                            break;
                            
                            case 348:

                                    System.out.println(" ");
                                    System.out.println("---ENTRO EN EL REPORTE...RPT-348...3er.AVISO---CON ESTADO D CUENTA---");
                                    System.out.println("---i: " + i);

                                    strFecConver=dtpFecDoc.getText();
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);                                    
                                    System.out.println("---objParSis.getDirectorioSistema(): " + objParSis.getDirectorioSistema());
                                    strSubRpt= objParSis.getDirectorioSistema() + "/Reportes/CxC/ZafCxC52/";
                                    
                                    System.out.println("COD_EMP: " + strCodEmp);
                                    System.out.println("COD_LOC: " + strCodLoc);
                                    System.out.println("COD_CLI: " + CodCli);
                                    System.out.println("NOM_CLI: " + NomCli);
                                    System.out.println("NOM_USR: " + NomUsr);
                                    System.out.println("strAte: " + strAte);
                                    System.out.println("strDep: " + strDep);
                                    System.out.println("NOM_USRFUN: " + NomUsrFun);
                                    System.out.println("FEC_SER: " + strFecHorSer);
                                    System.out.println("DIR_RPT: " + strRutRpt);
                                    System.out.println("NOM_RPT: " + strNomRpt);
                                    System.out.println("STRFECCON: " + STRFECCON);
                                    System.out.println("strFecConver: " + strFecConver);
                                    System.out.println("REPORT_DIR: " + strSubRpt);
                                    
                                    ///////////////////////////////////////////

                                    mapPar.put("CodEmp", "" + strCodEmp);
                                    mapPar.put("NomEmp", "" + strNomEmp);
                                    mapPar.put("CodLoc", "" + strCodLoc);
                                    mapPar.put("CodCli", "" + CodCli);
                                    mapPar.put("NomCli", "" + NomCli);
                                    mapPar.put("FecImp", "" + strFecHorSer);
                                    mapPar.put("Obser",  "" + Observacion);
                                    mapPar.put("NomUsr", "" + NomUsrFun);
                                    mapPar.put("Ban", ""+STRBAN);
                                    mapPar.put("Ate", "" + strAte);
                                    mapPar.put("Dep", "" + strDep);
                                    mapPar.put("DirRpt", "" + strRutRpt);
                                    mapPar.put("FecCon", "" + STRFECCON);
                                    mapPar.put("FecAco", "" + strFecConver);
                                    mapPar.put("REPORT_DIR", "" + strSubRpt);
                                    mapPar.put("EmailEmp", "" + strEmailEmp);

                                    //////////////////////////////////////////
                                    
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                            break;
                            
                            case 349:

                                    System.out.println(" ");
                                    System.out.println("---ENTRO EN EL REPORTE...RPT-349...4to.AVISO---");
                                    System.out.println("---i: " + i);

                                    strFecConver=dtpFecDoc.getText();
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    
                                    System.out.println("---objParSis.getDirectorioSistema(): " + objParSis.getDirectorioSistema());
                                    strSubRpt= objParSis.getDirectorioSistema() + "/Reportes/CxC/ZafCxC52/";
                                    
                                    System.out.println("COD_EMP: " + strCodEmp);
                                    System.out.println("COD_LOC: " + strCodLoc);
                                    System.out.println("COD_CLI: " + CodCli);
                                    System.out.println("NOM_CLI: " + NomCli);
                                    System.out.println("NOM_USR: " + NomUsr);
                                    System.out.println("strAte: " + strAte);
                                    System.out.println("strDep: " + strDep);
                                    System.out.println("NOM_USRFUN: " + NomUsrFun);
                                    System.out.println("FEC_SER: " + strFecHorSer);
                                    System.out.println("DIR_RPT: " + strRutRpt);
                                    System.out.println("NOM_RPT: " + strNomRpt);
                                    System.out.println("STRFECCON: " + STRFECCON);
                                    System.out.println("strFecConver: " + strFecConver);
                                    System.out.println("REPORT_DIR: " + strSubRpt);
                                    
                                    ///////////////////////////////////////////
//                                    mapPar.put("CodCli", ""+CodCli);
//                                    mapPar.put("FecImp", ""+strFecHorSer);
//                                    mapPar.put("NomUsr", ""+NomUsrFun);
//                                    mapPar.put("Ate", ""+strAte);
//                                    mapPar.put("Dep", ""+strDep);
//                                    mapPar.put("DirRpt", ""+strRutRpt);
//                                    mapPar.put("FecCon", ""+STRFECCON);
//                                    mapPar.put("FecAco", ""+strFecConver);
                                    //////////////////////////////////////////
                                    
//                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                            break;
                            
                            
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
                        stmUltRegDoc=conUltRegDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        strSQL="";
                        strSQL+="SELECT co_usr, tx_usr, tx_pwd, tx_nom";
                        strSQL+=" FROM tbm_usr AS a1";
                        strSQL+=" WHERE a1.co_usr= " + codusr;
                        strSQL+=" AND a1.st_usrSis='S'";
                        strSQL+=" AND a1.st_reg='A'";
                        System.out.println("---Query para el mostrar usuarios del sistema es: " + strSQL);
                        rstUltRegDoc=stmUltRegDoc.executeQuery(strSQL);
                        
                        if (rstUltRegDoc.next())
                        {
                            stRegRep = rstUltRegDoc.getString("tx_nom");
                        }
                        
                        System.out.println("---El nombre del usuario es: " + stRegRep);
                        
                        conUltRegDoc.close();
                        conUltRegDoc = null;
                        
                        stmUltRegDoc.close();
                        stmUltRegDoc = null;
                        
                        rstUltRegDoc.close();
                        rstUltRegDoc = null;
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