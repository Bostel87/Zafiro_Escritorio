/*
 * ZafCom74.java
 *
 * Created on March 15, 2012
 */
package Compras.ZafCom74;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom74 extends javax.swing.JInternalFrame
{
    //Variables del sistema
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafVenCon vcoCarPag;
    private ZafDocLis objDocLis;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private java.util.Date datFecAux;
    private String strSQL, strAux;
    private boolean blnHayCam;
    
    //ArrayList
    private ArrayList arlDatCom74_01;
    final int INT_ARL_DAT_COD_CAR_PAG=0;
    final int INT_ARL_DAT_NOM_CAR_PAG=1;
    final int INT_ARL_DAT_DESCOR_TIP_CAR_PAG=2;
    final int INT_ARL_DAT_DESLAR_TIP_CAR_PAG=3;
    final int INT_ARL_DAT_UBI_CAR_PAG=4;
    
    //ArrayList para consultar
    private ArrayList arlDatConCarPag, arlRegConCarPag;
    private static final int INT_ARL_CON_CAR_PAG_COD=0;  
    private int intIndiceCarPag=0;
    
    //ArrayList para combo
    private ArrayList arlDatCboTipCar, arlRegCboTipCar;
    private static final int INT_ARL_CBO_TIP_CAR_IND=0;  
    private static final int INT_ARL_CBO_TIP_CAR_DESCOR=1;  
    private static final int INT_ARL_CBO_TIP_CAR_DESLAR=2;  
    
    /** Creates new form ZafCom74 */
    public ZafCom74(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                configurarFrm();
                agregarDocLis();
                arlDatCom74_01=new ArrayList();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.2");
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            //Se Quita opción de poder modificar: Esto se hará para evitar que en el programa DxP se escoja un cargo y luego ese cargo sea modificado por otro.
            objTooBar.setVisibleModificar(false);
            panBar.add(objTooBar);
            objDocLis=new ZafDocLis();

            txtCodCarPag.setBackground(objParSis.getColorCamposSistema());
            txtNomCarPag.setBackground(objParSis.getColorCamposObligatorios());
            txtUbi.setEditable(false);
            
            configurarCboTipCar();
            configurarVenConCarPag();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    /**
     * Función que configura el combo con los tipos de cargos.
     * Cada vez que sea necesario un nuevo tipo de cargo,
     * solo se agrega en ésta función y lo demás estará configurable.
     * @return 
     */
    private boolean configurarCboTipCar() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de Tipo de Cargo".
            arlDatCboTipCar = new ArrayList();
           
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 0);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "F");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Flete");
            arlDatCboTipCar.add(arlRegCboTipCar);
            
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 1);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "A");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Arancel");
            arlDatCboTipCar.add(arlRegCboTipCar);
            
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 2);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "G");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Gasto");
            arlDatCboTipCar.add(arlRegCboTipCar);

            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 3);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "I");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Iva");
            arlDatCboTipCar.add(arlRegCboTipCar);
            
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 4);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "P");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Pago al exterior");
            arlDatCboTipCar.add(arlRegCboTipCar);
                       
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 5);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "V");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Valor de la factura");
            arlDatCboTipCar.add(arlRegCboTipCar);
            
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 6);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "M");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Impuestos");
            arlDatCboTipCar.add(arlRegCboTipCar);
            
            arlRegCboTipCar = new ArrayList();
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_IND, 7);
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESCOR, "O");
            arlRegCboTipCar.add(INT_ARL_CBO_TIP_CAR_DESLAR, "Otros");
            arlDatCboTipCar.add(arlRegCboTipCar);  
            
            //Se llena el combo
            for(int i=0; i<arlDatCboTipCar.size();i++)
            {
                cboTipCar.addItem(objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESLAR));
            }
            cboTipCar.setSelectedIndex(0);             
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Cargos a Pagar".
     */
    private boolean configurarVenConCarPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_carPag");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_carPag, a1.tx_nom";
            strSQL+=" FROM tbm_carPagImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.ne_ubi";

            vcoCarPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Cargos a Pagar", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCarPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCarPag(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCarPag.setCampoBusqueda(1);
                    vcoCarPag.show();
                    if (vcoCarPag.getSelectedButton() == vcoCarPag.INT_BUT_ACE) {
                        txtCodCarPag.setText(vcoCarPag.getValueAt(1));
                        txtNomCarPag.setText(vcoCarPag.getValueAt(2));
                    }
                    break;
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFrmFil = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        lblCodCarPag = new javax.swing.JLabel();
        txtCodCarPag = new javax.swing.JTextField();
        butCarPag = new javax.swing.JButton();
        lblNomCarPag = new javax.swing.JLabel();
        txtNomCarPag = new javax.swing.JTextField();
        lblTipCar = new javax.swing.JLabel();
        cboTipCar = new javax.swing.JComboBox();
        lblObs1 = new javax.swing.JLabel();
        lblUbi = new javax.swing.JLabel();
        txtUbi = new javax.swing.JTextField();
        butUbi = new javax.swing.JButton();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFrmFil.setLayout(new java.awt.BorderLayout());

        panFil.setLayout(null);

        lblCodCarPag.setText("Código:");
        panFil.add(lblCodCarPag);
        lblCodCarPag.setBounds(10, 8, 110, 20);
        panFil.add(txtCodCarPag);
        txtCodCarPag.setBounds(160, 6, 90, 20);

        butCarPag.setText("...");
        butCarPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarPagActionPerformed(evt);
            }
        });
        panFil.add(butCarPag);
        butCarPag.setBounds(250, 5, 22, 23);

        lblNomCarPag.setText("Nombre:");
        panFil.add(lblNomCarPag);
        lblNomCarPag.setBounds(10, 30, 140, 20);
        panFil.add(txtNomCarPag);
        txtNomCarPag.setBounds(160, 28, 400, 20);

        lblTipCar.setText("Tipo de cargo:");
        panFil.add(lblTipCar);
        lblTipCar.setBounds(10, 54, 130, 20);
        panFil.add(cboTipCar);
        cboTipCar.setBounds(160, 50, 210, 20);

        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación1:");
        panFil.add(lblObs1);
        lblObs1.setBounds(10, 96, 100, 20);

        lblUbi.setText("Ubicación:");
        panFil.add(lblUbi);
        lblUbi.setBounds(10, 74, 110, 20);
        panFil.add(txtUbi);
        txtUbi.setBounds(160, 71, 90, 20);

        butUbi.setText("...");
        butUbi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUbiActionPerformed(evt);
            }
        });
        panFil.add(butUbi);
        butUbi.setBounds(250, 70, 22, 23);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panFil.add(spnObs1);
        spnObs1.setBounds(160, 92, 400, 150);

        panFrmFil.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panFrmFil);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void butCarPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarPagActionPerformed
        mostrarVenConCarPag(0);
    }//GEN-LAST:event_butCarPagActionPerformed

    private void butUbiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUbiActionPerformed
        if(arlDatCom74_01.size()<=0) //Primera vez que se va a determinar la ubicación.
        {
            ZafCom74_01 objCom74_01=new ZafCom74_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis
                                                    , Integer.parseInt(txtCodCarPag.getText().equals("")?"0":txtCodCarPag.getText())
                                                    , txtNomCarPag.getText(), cboTipCar.getSelectedItem().toString()
                                                    , Integer.parseInt(txtUbi.getText().equals("")?"0":txtUbi.getText())
                                                    , objTooBar.getEstado(), arlDatCboTipCar);
            objCom74_01.setVisible(true);

            arlDatCom74_01=objCom74_01.getArregloDatosCargosPagar();
            if(objCom74_01.intButPre!=2){
                txtUbi.setText(""+objCom74_01.getUbicacionCargoPagarNuevo());
            }
            objCom74_01=null;
        }
        else{ //Cuando ya se ha modificado ubicación de cargo.
            ZafCom74_01 objCom74_01=new ZafCom74_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt(txtCodCarPag.getText().equals("")?"0":txtCodCarPag.getText()));
            objCom74_01.setArregloDatosCargosPagar(arlDatCom74_01);
            objCom74_01.setVisible(true);
                       
            if(objCom74_01.intButPre!=2){
                txtUbi.setText(""+objCom74_01.getUbicacionCargoPagarNuevo());
            }
            objCom74_01=null;
        }
    }//GEN-LAST:event_butUbiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCarPag;
    private javax.swing.JButton butUbi;
    private javax.swing.JComboBox cboTipCar;
    private javax.swing.JLabel lblCodCarPag;
    private javax.swing.JLabel lblNomCarPag;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTipCar;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUbi;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panFrmFil;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCodCarPag;
    private javax.swing.JTextField txtNomCarPag;
    private javax.swing.JTextField txtUbi;
    // End of variables declaration//GEN-END:variables
 
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
        
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodCarPag.getDocument().addDocumentListener(objDocLis);
        txtNomCarPag.getDocument().addDocumentListener(objDocLis);
    } 
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodCarPag.setText("");
            txtNomCarPag.setText("");
            cboTipCar.setSelectedIndex(0);
            txtUbi.setText("");
            txaObs1.setText("");
            arlDatCom74_01.clear();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() 
        {
            try{
                if(arlDatConCarPag.size()>0){
                    if(intIndiceCarPag>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCarPag=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCarPag=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() 
        {
            try{
                if(arlDatConCarPag.size()>0){
                    if(intIndiceCarPag>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCarPag--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCarPag--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() 
        {
            try{
                 if(arlDatConCarPag.size()>0){
                    if(intIndiceCarPag < arlDatConCarPag.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCarPag++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCarPag++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() 
        {
            try{
                 if(arlDatConCarPag.size()>0){
                    if(intIndiceCarPag<arlDatConCarPag.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCarPag=arlDatConCarPag.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCarPag=arlDatConCarPag.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickInsertar() {
            try{
                if (blnHayCam){
                    isRegPro();
                }
                limpiarFrm();
                txtCodCarPag.setEditable(false);
                txtNomCarPag.selectAll();
                txtNomCarPag.requestFocus();

                //Inicializar las variables que indican cambios.
                blnHayCam=false;                
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {            
        }

        public void clickModificar() {
            txtCodCarPag.setEditable(false);
            butCarPag.setEnabled(false);
            txtUbi.setEnabled(false);
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() 
        {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceCarPag<arlDatConCarPag.size()-1){
                    intIndiceCarPag++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            return true;
        }

        public boolean anular() 
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                blnRes=false;
            }
            return blnRes;
        }

        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El grupo está ELIMINADO.\nNo es posible anular un grupo eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El grupo ya está ANULADO.\nNo es posible anular un grupo anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            consultarReg();
            objTooBar.setEstado('w');
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            cargarReg();
            objTooBar.setEstado('w');
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        
    }//Fin ToolBar.

    
        
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        if (txtNomCarPag.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">nombre</FONT> es obligatorio.<BR>Escriba un nombre y vuelva a intentarlo.</HTML>");
            txtNomCarPag.requestFocus();
            return false;
        }
        if (txtUbi.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">ubicación</FONT> es obligatorio.<BR>Seleccione la ubicación y vuelva a intentarlo.</HTML>");
            butUbi.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_carPag";
                strSQL+=" FROM tbm_carPagImp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E')";

                strAux=txtCodCarPag.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_carPag=" + strAux.replaceAll("'", "''") + "";

                strAux=txtNomCarPag.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND LOWER(a1.tx_nom) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                strSQL+=" ORDER BY a1.co_carPag";
                //System.out.println("consultarReg: " + strSQL);
                
                rst=stm.executeQuery(strSQL);
                arlDatConCarPag = new ArrayList();
                while(rst.next()){
                    arlRegConCarPag = new ArrayList();
                    arlRegConCarPag.add(INT_ARL_CON_CAR_PAG_COD,rst.getInt("co_carPag"));
                    arlDatConCarPag.add(arlRegConCarPag);
                }
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConCarPag.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConCarPag.size()) + " registros");
                    intIndiceCarPag=arlDatConCarPag.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg()){
                blnRes=true;
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_carPag, a1.tx_nom, a1.tx_tipCarPag, a1.tx_obs1, a1.st_reg, a1.ne_ubi";
                strSQL+=" FROM tbm_carPagImp AS a1";
                strSQL+=" WHERE a1.co_carPag=" + objUti.getIntValueAt(arlDatConCarPag, intIndiceCarPag, INT_ARL_CON_CAR_PAG_COD);
                strSQL+=" AND a1.st_reg NOT IN('E')";
                //System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_carPag");
                    txtCodCarPag.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_nom");
                    txtNomCarPag.setText((strAux==null)?"":strAux);
                    
                    //Se selecciona en el combo de acuerdo al tipo de cargo.
                    strAux=rst.getString("tx_tipCarPag");
                    for(int i=0; i<arlDatCboTipCar.size();i++)
                    {
                        if(strAux.equals(objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESCOR)))
                        {
                            cboTipCar.setSelectedIndex(objUti.getIntValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_IND));  
                            break;
                        }
                    }
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));

                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("ne_ubi");
                    txtUbi.setText((strAux==null)?"":strAux);
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCarPag+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConCarPag.size()) );
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
    
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertar_tbmCarPagImp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagImp(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_carPag)";
                strSQL+=" FROM tbm_carPagImp AS a1";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodCarPag.setText("" + intUltReg);

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_carpagimp(";
                strSQL+=" co_carpag, tx_nom, tx_tipcarpag, tx_obs1, st_reg, fe_ing, fe_ultmod,";
                strSQL+=" co_usring, co_usrmod, ne_ubi)";
                strSQL+=" VALUES (";
                strSQL+="" + txtCodCarPag.getText(); //co_carpag
                strSQL+=", " + objUti.codificar(txtNomCarPag.getText()) + ""; //tx_nom
                for(int i=0; i<arlDatCboTipCar.size();i++)
                {
                    if(cboTipCar.getSelectedIndex() == objUti.getIntValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_IND) )
                    {
                        strSQL+=", '"+objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESCOR)+"'";
                        break;
                    }
                }                
                
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + ""; //tx_obs1
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+="," + txtUbi.getText() + "";//ne_ubi
                strSQL+=");";

                //actualizar los cambios de cargos a pagar ya ingresados, pero que se les cambio el orden de ubicacion
                String strCodCarPagNew="";
                for(int k=0; k<arlDatCom74_01.size(); k++){
                    strSQL+="UPDATE tbm_carpagimp";
                    strCodCarPagNew=objUti.getStringValueAt(arlDatCom74_01, k, INT_ARL_DAT_COD_CAR_PAG).toString();
                    if(strCodCarPagNew.equals("")){
                        strSQL+=" SET ne_ubi=" + txtUbi.getText() + "";
                        strSQL+=" WHERE co_carpag=" + txtCodCarPag.getText() + "";
                    }
                    else{
                        strSQL+=" SET ne_ubi=" + objUti.getStringValueAt(arlDatCom74_01, k, INT_ARL_DAT_UBI_CAR_PAG) + "";
                        strSQL+=" WHERE co_carpag=" + objUti.getStringValueAt(arlDatCom74_01, k, INT_ARL_DAT_COD_CAR_PAG) + "";
                    }
                    
                    strSQL+=";";
                }
                //System.out.println("insertar: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizar_tbmCarPagImp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCarPagImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();

                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_carPagImp";
                strSQL+=" SET tx_nom=" + objUti.codificar(txtNomCarPag.getText()) + "";
                for(int i=0; i<arlDatCboTipCar.size();i++)
                {
                    if(cboTipCar.getSelectedIndex() == objUti.getIntValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_IND) )
                    {
                        strSQL+=", tx_tipCarPag='"+objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESCOR)+"'";
                        break;
                    }
                }
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", ne_ubi=" +  objUti.codificar(txtUbi.getText()) + "";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_carPag=" + txtCodCarPag.getText() +"";

                //System.out.println("actualizar_tbmCarPagImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anular_tbmCarPagImp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCarPagImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_carPagImp";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_carPag=" + objUti.getIntValueAt(arlDatConCarPag, intIndiceCarPag, INT_ARL_CON_CAR_PAG_COD);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (eliminar_tbmCarPagImp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCarPagImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_carPagImp";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_carPag=" + objUti.getIntValueAt(arlDatConCarPag, intIndiceCarPag, INT_ARL_CON_CAR_PAG_COD);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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