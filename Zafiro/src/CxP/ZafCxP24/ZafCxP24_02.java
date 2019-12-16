/*
 * ZafCxP24_02.java
 *
 * Created on 04 de abril del 2018
 */
package CxP.ZafCxP24;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import java.math.BigDecimal;
import java.util.Vector;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author  sistemas9
 */
public class ZafCxP24_02 extends javax.swing.JDialog
{
    //Constantes
    private static int INT_LINEA        = 0; //0) Línea: Se debe asignar una cadena vacía o null.
    private static int INT_VEC_CODCTA   = 1; //1) Código de la cuenta (Sistema).
    private static int INT_VEC_NUMCTA   = 2; //2) Número de la cuenta (Preimpreso).
    private static int INT_VEC_BOTON    = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null.
    private static int INT_VEC_NOMCTA   = 4; //4) Nombre de la cuenta.
    private static int INT_VEC_DEBE     = 5; //5) Debe.
    private static int INT_VEC_HABER    = 6; //6) Haber.
    private static int INT_VEC_REF      = 7; //7) Referencia: Se debe asignar una cadena vacía o null
    private static int INT_VEC_EST_CON  = 8; //8) Referencia: Se debe asignar una cadena vacía o null
    
    public static final int INT_BUT_CAN=0;                      /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;                      /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    private ZafDocLis objDocLis;
    private ZafCxP24 objCon64;
    private java.util.Date datFecAux;
    
    private String strOpcTipTrs;                             //si es arancel 88, si es exterior 87
    private String strDesCorTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strSQL, strAux;
    private boolean blnHayCam;                              //Determina si hay cambios en el formulario.
    
    private ArrayList arlDatDetDiaTrs;
    
    private Vector vecReg, vecDat;
    private BigDecimal bdeValCtaBanHab;
    private int intButSelDlg;                                //Botón seleccionado en el JDialog.
    private int intCodLocRef;

    /** Crea una nueva instancia de la clase ZafCxP24_02. */
    public ZafCxP24_02(java.awt.Frame parent, boolean modal, ZafParSis obj, int codigoEmpresa, int codigoLocal, String strOpcTrsSel){
        super(parent, modal);
        try{
            objParSis=(ZafParSis)obj.clone();
            objParSis.setCodigoEmpresa(codigoEmpresa);
            objParSis.setCodigoLocal(getCodigoLocalPredeterminado(codigoEmpresa));
            objCon64= new ZafCxP24(objParSis);
            strOpcTipTrs=strOpcTrsSel;
            intCodLocRef=codigoLocal;
            vecDat=new Vector();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            lblTit.setText("Transferencia bancaria al exterior...");
            intButSelDlg=INT_BUT_CAN;
            arlDatDetDiaTrs=new ArrayList();

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
            //Configurar ZafDatePicker:
            dtpFecDoc=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(534, 4, 148, 20);
            dtpFecDoc.setEnabled(false);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objAsiDia=new ZafAsiDia(objParSis);

            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    objAsiDia.setIntChgBusCri(1);

                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });

            objDocLis=new ZafDocLis();
            this.setTitle(objParSis.getNombreMenu() + objCon64.strVer);
            lblTit.setText(objParSis.getNombreMenu());
            panGenDet.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtNumDia.setEditable(false);
            mostrarTipDocPre();
        }
        catch(Exception e)
        {
            blnRes=false;
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Código, Descripción corta, Descripción larga, Ultimo documento";
            strCam="a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.e_ultDoc";
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() +"";
            }
            else{
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de tipos de documentos");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                        txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                        strAux=objVenCon.GetCamSel(4);
                        txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (objVenCon.buscar("LOWER(a1.tx_desCor) LIKE '" + txtDesCorTipDoc.getText().toLowerCase() + "'"))
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                        txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesCorTipDoc.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                            txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                            txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                            strAux=objVenCon.GetCamSel(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            txtNumDia.selectAll();
                            txtNumDia.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (objVenCon.buscar("LOWER(a1.tx_desLar) LIKE '" + txtDesLarTipDoc.getText().toLowerCase() + "'"))
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                        txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                        strAux=objVenCon.GetCamSel(4);
                        txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesLarTipDoc.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                            txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                            txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                            strAux=objVenCon.GetCamSel(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            txtNumDia.selectAll();
                            txtNumDia.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" (";
                strSQL+=" SELECT co_tipDoc";
                strSQL+=" FROM tbr_tipDocPrg";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                if(strOpcTipTrs.equals("T"))
                    strSQL+=" AND co_tipdoc=87";//transferencia del exterior
                else if(strOpcTipTrs.equals("A"))
                    strSQL+=" AND co_tipdoc=88";//transferencia arancelaria
                strSQL+=" GROUP BY co_tipDoc";
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private int getCodigoLocalPredeterminado(int empresaBanco){
        int intCodLocPre=-1;
        Statement stmLocPre;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stmLocPre=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + empresaBanco + "";
                strSQL+=" AND st_reg='P'";
                rst=stmLocPre.executeQuery(strSQL);
                if(rst.next()){
                    intCodLocPre=rst.getInt("co_loc");
                }
                stmLocPre.close();
                stmLocPre=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodLocPre;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDia = new javax.swing.JLabel();
        txtNumDia = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Título de la ventana");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 66));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(80, 4, 32, 20);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(112, 4, 60, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(172, 4, 220, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(392, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(416, 4, 120, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 110, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(112, 24, 148, 20);

        lblNumDia.setText("Número de diario:");
        lblNumDia.setToolTipText("Número de diario");
        panGenCab.add(lblNumDia);
        lblNumDia.setBounds(416, 24, 120, 20);
        panGenCab.add(txtNumDia);
        txtNumDia.setBounds(534, 24, 148, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());
        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(0, 40));
        panBar.setLayout(null);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBar.add(butAce);
        butAce.setBounds(475, 10, 100, 25);

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBar.add(butCan);
        butCan.setBounds(575, 10, 100, 25);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else   {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if(valorHaberCuadradoInicial()){
            if(objAsiDia.isDiarioCuadrado()){
                intButSelDlg=INT_BUT_ACE;
                arlDatDetDiaTrs=objAsiDia.getDetalleDiarioTransferenciaDiario();
                dispose();
            }
            else{
                mostrarMsgInf("<HTML>El asiento de diario está descuadrado.<BR>Verifique el asiento de diario ingresado y vuelva a intentarlo.</HTML>");
            }
        }
        else{
            mostrarMsgInf("<HTML>La suma del haber ha cambiado.<BR>No se puede colocar mas cuentas al haber.<BR>Verifique el asiento de diario ingresado y vuelva a intentarlo.</HTML>");
        }
        objParSis.setCodigoLocal(intCodLocRef);
    }//GEN-LAST:event_butAceActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                intButSelDlg=INT_BUT_CAN;
            }
            arlDatDetDiaTrs=objAsiDia.getDetalleDiarioTransferenciaDiario();
            dispose();
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        objParSis.setCodigoLocal(intCodLocRef);
        dispose();

    }//GEN-LAST:event_butCanActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDia;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDia;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
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
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDia.getDocument().addDocumentListener(objDocLis);
    }   

    private boolean valorHaberCuadradoInicial(){
        boolean blnRes=false;
        BigDecimal bdeMonHabUlt=new BigDecimal("0");
        try{
            bdeMonHabUlt=objUti.redondearBigDecimal(new BigDecimal("" + objAsiDia.getMontoHaber()), objParSis.getDecimalesMostrar());
            if(bdeMonHabUlt.compareTo(bdeValCtaBanHab)==0){
                blnRes=true;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public void setCodigoEmpresaTransferencia(int codigoEmpresaBancaria){
        objParSis.setCodigoEmpresa(codigoEmpresaBancaria);
    }

    public void setValorCuentaBancaria(BigDecimal valorTransferencia){
        bdeValCtaBanHab=valorTransferencia;
    }

    public void setDetalleTransferencia(String codigoCuenta, String numeroCuenta, String nombreCuenta){
        objAsiDia.inicializar();
        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA, codigoCuenta);
        vecReg.add(INT_VEC_NUMCTA, numeroCuenta); //para probar//
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, nombreCuenta); //para probar//
        vecReg.add(INT_VEC_DEBE, new BigDecimal("0"));
        vecReg.add(INT_VEC_HABER, objUti.redondearBigDecimal(bdeValCtaBanHab, objParSis.getDecimalesMostrar()));
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_EST_CON, "B");
        vecDat.add(vecReg);
        System.out.println("setDetalleTransferencia");
    }

    public void setDetalleTransferenciaCuentaContrapartida(String codigoCuenta, String numeroCuenta, String nombreCuenta, BigDecimal valorDebe){
        vecReg = new java.util.Vector();
        vecReg.add(INT_LINEA, null);
        vecReg.add(INT_VEC_CODCTA, codigoCuenta);
        vecReg.add(INT_VEC_NUMCTA, numeroCuenta); //para probar//
        vecReg.add(INT_VEC_BOTON, null);
        vecReg.add(INT_VEC_NOMCTA, nombreCuenta); //para probar//
        vecReg.add(INT_VEC_DEBE, valorDebe);
        vecReg.add(INT_VEC_HABER, new BigDecimal("0"));
        vecReg.add(INT_VEC_REF, null);
        vecReg.add(INT_VEC_EST_CON, "");
        vecDat.add(vecReg);
        System.out.println("setDetalleTransferenciaCuentaContrapartida");
    }

    public void setDetalleTransferenciaVector(){
        objAsiDia.setDetalleDiario(vecDat);
        objAsiDia.setEditable(true);
        System.out.println("setDetalleTransferenciaVector");
    }

    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_CAN).
     * <LI>1: Click en el botón Aceptar (INT_BUT_ACE).
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()
    {
        return intButSelDlg;
    }

    /**
     * Esta función obtiene las filas que han sido seleccionadas por el usuario en el JTable.
     * @return Un arreglo de enteros que contiene la(s) fila(s) seleccionadas.
     */
    public int[] getFilasSeleccionadas()
    {
        int i=0, j=0;
        int intRes[];
        intRes=new int[objAsiDia.getDetalleDiarioTransferenciaDiario().size()];
        j=0;
        for (i=0; i<(objAsiDia.getDetalleDiarioTransferenciaDiario().size()); i++){
            intRes[j]=i;
            j++;
        }
        return intRes;
    }

    public ArrayList getDetalleDiarioTransferenciaCon02_02() {
        return arlDatDetDiaTrs;
    }

    public void setDetalleDiarioTransferenciaCon02_02(ArrayList arlDatDetDiaTrs) {
        this.arlDatDetDiaTrs = arlDatDetDiaTrs;
    }

    public String getGlosaTransferenciaExterior() {
        return objAsiDia.getGlosa();
    }




}