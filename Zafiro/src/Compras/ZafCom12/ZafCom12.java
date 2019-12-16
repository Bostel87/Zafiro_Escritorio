/*
 * ZafCom12.java
 *
 * Created on 20 de agosto de 2005, 11:38 PM
 */
package Compras.ZafCom12;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafDate.ZafSelectDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;  //**************************
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Javier Ayapata
 */
public class ZafCom12 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                //Línea
    final int INT_TBL_DAT_COD_MAE=1;            //Código maestro del item.
    final int INT_TBL_DAT_COD_ITM=2;            //Código del item.
    final int INT_TBL_DAT_COD_ALT=3;            //Código alterno del item.
    final int INT_TBL_DAT_NOM_ITM=4;            //Nombre del item.
    final int INT_TBL_DAT_NUM_MOV=5;            //Número de movimientos del item.
    final int INT_TBL_DAT_CHK=6;                //Estado de recosteado.
    
    final int INT_DET_COD_EMP=0;                //Código de la empresa.
    final int INT_DET_COD_LOC=1;                //Código del local.
    final int INT_DET_COD_TIP_DOC=2;            //Código del tipo de documento.
    final int INT_DET_COD_DOC=3;                //Código del documento.
    final int INT_DET_COD_REG=4;                //Código del documento.
    final int INT_DET_CAL_COS_UNI=5;            //Calcular costo unitario.
    final int INT_DET_EST_REG=6;                //Estado del registro.
    final int INT_DET_CAN=7;                    //Cantidad.
    final int INT_DET_COS_UNI=8;                //Costo unitario.
    final int INT_DET_POR_DES=9;                //Porcentaje de descuento.
    final int INT_DET_SAL_UNI=10;               //Saldo en unidades.
    final int INT_DET_SAL_VAL=11;               //Saldo en valores.
    final int INT_DET_COS_UNI_DOC=12;           //Costo unitario del item en el documento.
    
    //Variables generales.
    private ZafSelectDate objSelDat;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strAux;
    private ZafVenCon vcoTipDoc; //***************** 
     
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom12(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
    }
    
    
    public void Configura_ventana_consulta(){
        configurarVenConTipDoc();
    }
    
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Descripción");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");
            
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            
            if(objParSis.getCodigoUsuario()==1){
                  Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b left " +
                          " outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                          " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                          " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                          " b.co_mnu ="+objParSis.getCodigoMenu();   //in (14,55,24,45,65)";
            }else{
                 Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE "+
                    "  a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                    " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                    " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                    " AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }
            //Str_Sql="select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbm_cabtipdoc as a where  co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal();
                  
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
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
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtNomDoc = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        txtCodtipdoc = new javax.swing.JTextField();
        optFil = new javax.swing.JCheckBox();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblItm.setText("Tipo Doc:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(36, 8, 64, 20);

        txtNomDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDocActionPerformed(evt);
            }
        });
        txtNomDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDocFocusLost(evt);
            }
        });

        panFil.add(txtNomDoc);
        txtNomDoc.setBounds(148, 8, 208, 21);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });

        panFil.add(butItm);
        butItm.setBounds(356, 8, 20, 20);

        panNomCli.setLayout(null);

        panNomCli.setBorder(new javax.swing.border.TitledBorder("Num. Documento."));
        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 44, 20);

        txtCodAltDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltDesActionPerformed(evt);
            }
        });
        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });

        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(56, 20, 268, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(336, 20, 44, 20);

        txtCodAltHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltHasActionPerformed(evt);
            }
        });
        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });

        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(380, 20, 268, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(28, 76, 660, 52);
        panNomCli.getAccessibleContext().setAccessibleName("");

        txtCodtipdoc.setBackground(new java.awt.Color(255, 255, 255));
        txtCodtipdoc.setEditable(false);
        panFil.add(txtCodtipdoc);
        txtCodtipdoc.setBounds(92, 8, 56, 21);

        optFil.setText("Por Rango de Num. Fac. ");
        optFil.setActionCommand("Por");
        panFil.add(optFil);
        optFil.setBounds(32, 48, 188, 23);

        tabFrm.addTab("General", panFil);

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

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
         if (!configurarFrm())
            exitForm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodAltDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltDesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodAltDesActionPerformed

    private void txtCodAltHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltHasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodAltHasActionPerformed

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
           optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        listaTipdoc("","",0);
    }//GEN-LAST:event_butItmActionPerformed

     private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo){
        vcoTipDoc.setTitle("Listado Tipos de Documentos"); 
        switch (intTipo){
            case 0:
                break;
            case 1:
                vcoTipDoc.setCampoBusqueda(1);
                if (vcoTipDoc.buscar(campo, strDesBusqueda )){ }
                break;
            case 2:
                vcoTipDoc.setCampoBusqueda(2); 
                if (vcoTipDoc.buscar(campo, strDesBusqueda )){ }                
                break;
        }
        vcoTipDoc.cargarDatos();
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
        {
            if(!vcoTipDoc.getValueAt(1).equals("")){
                cargarCabTipoDoc(Integer.parseInt(vcoTipDoc.getValueAt(1)),vcoTipDoc.getValueAt(3));            
            }
        }      
    }
    
    private void cargarCabTipoDoc(int TipoDoc,String nom)
    {
      txtCodtipdoc.setText(""+TipoDoc);
      txtNomDoc.setText(nom);
    }
     
     private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
     }//GEN-LAST:event_txtNomDocFocusLost

    private void txtNomDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusGained
      
    }//GEN-LAST:event_txtNomDocFocusGained

    private void txtNomDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDocActionPerformed
      
    }//GEN-LAST:event_txtNomDocActionPerformed

    public boolean isCamVal(){
        if(txtCodtipdoc.getText().equals("")) {
            String strMsg ="Escoja el tipo de Documento";
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JCheckBox optFil;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodtipdoc;
    private javax.swing.JTextField txtNomDoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelectDate.
            objSelDat=new ZafSelectDate(new javax.swing.JFrame(),"d/m/y");
            panFil.add(objSelDat);
            objSelDat.setBounds(30, 145, 560, 100);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.5");
            lblTit.setText(strAux);
           // arlDat=new ArrayList();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

  
   

    
}
