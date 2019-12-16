/*
 * ZafImp31.java
 *
 * Created on 27 de marzo del 2018
 */
package Importaciones.ZafImp31;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author sistemas9
 */
public class ZafImp31 extends javax.swing.JInternalFrame
{
    //ArrayList: Pedidos
    private ArrayList arlRegPed, arlDatPed;
    private int INT_ARL_PED_COD_EMP=0;
    private int INT_ARL_PED_COD_LOC=1;
    private int INT_ARL_PED_COD_TIP_DOC=2;
    private int INT_ARL_PED_COD_DOC=3;
    private int INT_ARL_PED_FEC_EMB=4;
    private int INT_ARL_PED_MES=5;
    private int INT_ARL_PED_NUM_PED=6;
    private int INT_ARL_PED_IND_SEL=7;
    private int INT_ARL_PED_COD_CTA_ACT=8;
    private int INT_ARL_PED_COD_NUM_ACT=9;
    private int INT_ARL_PED_COD_NOM_ACT=10;
    private int INT_ARL_PED_COD_CTA_PAS=11;
    private int INT_ARL_PED_COD_NUM_PAS=12;
    private int INT_ARL_PED_COD_NOM_PAS=13;
    private int INT_ARL_PED_COD_IMP=14;
    private int INT_ARL_PED_NOM_IMP=15;

    //ArrayList para consultar Facturas de importaciones
    private ArrayList arlDatConFacImp, arlRegConFacImp;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4;
    private static final int INT_ARL_CON_TXT_USRMOD=5;
    private int intIndiceFacImp=0;        
    
    //Variables 
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private ZafDatePicker dtpFecDoc, dtpFecVctPedEmbImp;
    private ZafRptSis objRptSis;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoTipDoc;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private java.util.Date datFecAux;
    
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private int intCodEmpImpPedEmb, intCodLocImpPedEmb; // la empresa y el local     que realiza la importacion 
    
    private String strFecDocIni;
    private String strEstImpDoc;   
    private String strGloPedEmbSel, strNumDia_Glo;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCtaPadExi;                        //si proviene desde el programa de otros movimientos bancarios pero de grupo.
    private String strSQL, strAux;
    private String strVer=" v0.1 ";
      
    
    /** Crea una nueva instancia de la clase ZafImp31. */
    public ZafImp31(ZafParSis obj)
    {
        try{
            strCtaPadExi="";
            objParSis=(ZafParSis)obj.clone();
            arlDatPed=new ArrayList();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
                agregarDocLis();
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

    /** Crea una nueva instancia de la clase ZafImp31. */
    public ZafImp31(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
        try{

            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            initComponents();

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoMenu(codigoMenu);

            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            consultarReg();
            //objTooBar.setVisible(false);
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana 
            this.setTitle(objParSis.getNombreMenu() + strVer); //Facturas Importaciones
            lblTit.setText(objParSis.getNombreMenu());
            
            //Inicializar objetos.
            objUti=new ZafUtil();  
            objDocLis=new ZafDocLis();
            
            //Configurar ZafDatePicker: Fecha de diario
            dtpFecDoc=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabTipDoc.add(dtpFecDoc);
            dtpFecDoc.setBounds(578, 4, 100, 20);
            
            //Configurar ZafDatePicker: Fecha de vencimiento
            dtpFecVctPedEmbImp=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecVctPedEmbImp.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecVctPedEmbImp.setText("");
            panCabPedImp.add(dtpFecVctPedEmbImp);
            dtpFecVctPedEmbImp.setBounds(578, 16, 100, 20);
            
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            objAsiDia.inicializar();
            
            panDet.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
            
            configurarVenConTipDoc();
            cargarPedidoEmbarcado();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            panCabPedImp.setVisible(true);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() +"";
            }
            else{
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 INNER JOIN tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)  {
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
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
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
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
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
        catch (Exception e)  {
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
                if(objParSis.getCodigoUsuario()==1){
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    if(strCtaPadExi.equals("S"))
                        strSQL+=" AND co_tipdoc=87";
                    else
                        strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    if(strCtaPadExi.equals("S")){
                        strSQL+=" (";
                        strSQL+=" SELECT co_tipDoc";
                        strSQL+=" FROM tbr_tipDocPrg";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND co_tipdoc=87";
                        strSQL+=" )";
                    }
                    else{
                        strSQL+=" (";
                        strSQL+=" SELECT co_tipDoc";
                        strSQL+=" FROM tbr_tipDocUsr";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND st_reg='S'";
                        strSQL+=" )";
                    }
                }
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n'){
                        txtNumDia.setText("" + (rst.getInt("ne_ultDoc")+1));
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra el número del documento asociado al tipo de documento según Importador(Empresa)
     * @return true: Si se pudo mostrar el número del documento.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarNumDocEmpTipDoc(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpImpPedEmb;
                strSQL+=" AND a1.co_loc=" + intCodLocImpPedEmb;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";//(txtCodTipDoc.getText().length()<=0?"0":txtCodTipDoc.getText())
                //System.out.println("mostrarNumDocEmpTipDoc: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    txtNumDia.setText("" + (rst.getInt("ne_ultDoc")+1));
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
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    private boolean cargarPedidoEmbarcado(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_emb";
                strSQL+=" , a1.co_imp, a4.tx_nom AS tx_nomImp, a1.tx_numDoc2";
                strSQL+=" , a1.co_ctaAct, a2.tx_codCta AS tx_numCtaAct, a2.tx_desLar AS tx_nomCtaAct";
                strSQL+=" , a1.co_ctaPas, a3.tx_codCta AS tx_numCtaPas, a3.tx_desLar AS tx_nomCtaPas";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2 ON (a1.co_imp=a2.co_emp AND a1.co_ctaAct=a2.co_cta)";
                strSQL+="	INNER JOIN tbm_plaCta AS a3 ON (a1.co_imp=a3.co_emp AND a1.co_ctaPas=a3.co_cta)";
                strSQL+="	INNER JOIN tbm_emp AS a4 ON(a1.co_imp=a4.co_emp)";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I') AND a2.st_reg='A' AND a3.st_reg='A' AND a4.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_numDoc2";
                //System.out.println("cargarPedidoEmbarcado: " + strSQL);
                rst=stm.executeQuery(strSQL);                
                cboPedEmb.removeAllItems();
                cboPedEmb.addItem("----------");
                arlDatPed.clear();
                for(int i=0;rst.next(); i++){
                    cboPedEmb.addItem("" + rst.getString("tx_numDoc2"));
                    //para saber cual pedido se ha seleccionado
                    arlRegPed=new ArrayList();
                    arlRegPed.add(INT_ARL_PED_COD_EMP,      rst.getString("co_emp"));
                    arlRegPed.add(INT_ARL_PED_COD_LOC,      rst.getString("co_loc"));
                    arlRegPed.add(INT_ARL_PED_COD_TIP_DOC,  rst.getString("co_tipDoc"));
                    arlRegPed.add(INT_ARL_PED_COD_DOC,      rst.getString("co_doc"));
                    arlRegPed.add(INT_ARL_PED_FEC_EMB,      rst.getString("fe_doc"));
                    arlRegPed.add(INT_ARL_PED_MES,          rst.getString("fe_emb"));
                    arlRegPed.add(INT_ARL_PED_NUM_PED,      rst.getString("tx_numDoc2"));
                    arlRegPed.add(INT_ARL_PED_IND_SEL,      i);
                    arlRegPed.add(INT_ARL_PED_COD_CTA_ACT,  rst.getString("co_ctaAct"));
                    arlRegPed.add(INT_ARL_PED_COD_NUM_ACT,  rst.getString("tx_numCtaAct"));
                    arlRegPed.add(INT_ARL_PED_COD_NOM_ACT,  rst.getString("tx_nomCtaAct"));
                    arlRegPed.add(INT_ARL_PED_COD_CTA_PAS,  rst.getString("co_ctaPas"));
                    arlRegPed.add(INT_ARL_PED_COD_NUM_PAS,  rst.getString("tx_numCtaPas"));
                    arlRegPed.add(INT_ARL_PED_COD_NOM_PAS,  rst.getString("tx_nomCtaPas"));
                    arlRegPed.add(INT_ARL_PED_COD_IMP,      rst.getString("co_imp"));
                    arlRegPed.add(INT_ARL_PED_NOM_IMP,      rst.getString("tx_nomImp"));
                    arlDatPed.add(arlRegPed);
                }
                cboPedEmb.setSelectedIndex(0);
                stm.close();
                stm=null;
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
        return blnRes;
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
        panCab = new javax.swing.JPanel();
        panCabTipDoc = new javax.swing.JPanel();
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
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panCabPedImp = new javax.swing.JPanel();
        lblPedEmb = new javax.swing.JLabel();
        cboPedEmb = new javax.swing.JComboBox();
        lblFecVen = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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

        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 115));
        panCab.setLayout(new java.awt.BorderLayout());

        panCabTipDoc.setPreferredSize(new java.awt.Dimension(100, 68));
        panCabTipDoc.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabTipDoc.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 120, 20);
        panCabTipDoc.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 20, 20);

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
        panCabTipDoc.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(110, 4, 80, 20);

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
        panCabTipDoc.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 4, 240, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabTipDoc.add(butTipDoc);
        butTipDoc.setBounds(430, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabTipDoc.add(lblFecDoc);
        lblFecDoc.setBounds(450, 4, 130, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabTipDoc.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 120, 20);
        panCabTipDoc.add(txtCodDoc);
        txtCodDoc.setBounds(110, 24, 80, 20);

        lblNumDia.setText("Número de diario:");
        lblNumDia.setToolTipText("Número de diario");
        panCabTipDoc.add(lblNumDia);
        lblNumDia.setBounds(450, 24, 100, 20);
        panCabTipDoc.add(txtNumDia);
        txtNumDia.setBounds(578, 24, 100, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Número de diario");
        panCabTipDoc.add(lblValDoc);
        lblValDoc.setBounds(450, 44, 130, 20);

        txtValDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValDocFocusLost(evt);
            }
        });
        txtValDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValDocKeyPressed(evt);
            }
        });
        panCabTipDoc.add(txtValDoc);
        txtValDoc.setBounds(578, 44, 100, 20);

        panCab.add(panCabTipDoc, java.awt.BorderLayout.NORTH);

        panCabPedImp.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Pedido por Importación"));
        panCabPedImp.setPreferredSize(new java.awt.Dimension(100, 30));
        panCabPedImp.setLayout(null);

        lblPedEmb.setText("Pedido Embarcado:");
        panCabPedImp.add(lblPedEmb);
        lblPedEmb.setBounds(20, 18, 110, 14);

        cboPedEmb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboPedEmbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cboPedEmbFocusLost(evt);
            }
        });
        panCabPedImp.add(cboPedEmb);
        cboPedEmb.setBounds(134, 16, 170, 20);

        lblFecVen.setText("Fecha de vencimiento:");
        lblFecVen.setToolTipText("Fecha del documento");
        panCabPedImp.add(lblFecVen);
        lblFecVen.setBounds(450, 10, 130, 20);

        panCab.add(panCabPedImp, java.awt.BorderLayout.CENTER);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setPreferredSize(new java.awt.Dimension(100, 190));
        panDet.setLayout(new java.awt.BorderLayout());
        panGen.add(panDet, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
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
            else
            {
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
            else
            {
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
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
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
        catch (Exception e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtValDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValDocKeyPressed
        int intIndSelTrs = -1;
        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            generarDiarioLocal();
            dtpFecVctPedEmbImp.requestFocus();
            for (int j = 0; j < arlDatPed.size(); j++) {
                intIndSelTrs = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
                if (cboPedEmb.getSelectedIndex() == (intIndSelTrs + 1)) {
                    intCodEmpImpPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_IMP);
                    strGloPedEmbSel="Factura Proveedor Importación. Empresa: " + objUti.getStringValueAt(arlDatPed, j, INT_ARL_PED_NOM_IMP) + ", # Pedido: " + objUti.getStringValueAt(arlDatPed, j, INT_ARL_PED_NUM_PED) + ", Fecha Embarque: " + objUti.formatearFecha(objUti.getStringValueAt(arlDatPed, j, INT_ARL_PED_FEC_EMB), "yyyy-MM-dd", "dd/MMM/yyyy") + "";
                    break;
                }
            }
            objAsiDia.setGlosa("" + strGloPedEmbSel);
        }
    }//GEN-LAST:event_txtValDocKeyPressed

    private void txtValDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusLost
        int intIndSelTrs = -1;
        generarDiarioLocal();
        dtpFecVctPedEmbImp.requestFocus();
        for (int j = 0; j < arlDatPed.size(); j++) {
            intIndSelTrs = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
            if (cboPedEmb.getSelectedIndex() == (intIndSelTrs + 1)) {
                intCodEmpImpPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_IMP);
                strGloPedEmbSel="Factura Proveedor Importación. Empresa: " + objUti.getStringValueAt(arlDatPed, j, INT_ARL_PED_NOM_IMP) + ", # Pedido: " + objUti.getStringValueAt(arlDatPed, j, INT_ARL_PED_NUM_PED) + ", Fecha Embarque: " + objUti.formatearFecha(objUti.getStringValueAt(arlDatPed, j, INT_ARL_PED_FEC_EMB), "yyyy-MM-dd", "dd/MMM/yyyy") + "";
                break;
            }
        }
        objAsiDia.setGlosa("" + strGloPedEmbSel);
    }//GEN-LAST:event_txtValDocFocusLost

    private void txtValDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusGained
        if(cboPedEmb.getSelectedIndex()==0){
            cboPedEmb.requestFocus();
            mostrarMsgInf("<HTML>El campo Pedido Embarcado no ha sido seleccionado<BR>Seleccione el Pedido Embarcado y vuelva a intentarlo</HTML>");
        }
    }//GEN-LAST:event_txtValDocFocusGained

    private void cboPedEmbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboPedEmbFocusLost
        if(cboPedEmb.getSelectedIndex()!=0){
            int intIndSelTrs = -1;
            for (int j = 0; j < arlDatPed.size(); j++) {
                intIndSelTrs = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
                if (cboPedEmb.getSelectedIndex() == (intIndSelTrs + 1)) {
                    intCodEmpImpPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_IMP);
                    break;
                }
            }        
            intCodLocImpPedEmb=getCodigoLocalPredeterminado(intCodEmpImpPedEmb);
            mostrarNumDocEmpTipDoc();
            txtValDoc.requestFocus();
            txtValDoc.selectAll();
        }
        else{
            cboPedEmb.requestFocus();
        }
    }//GEN-LAST:event_cboPedEmbFocusLost

    private void cboPedEmbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboPedEmbFocusGained
        if(cboPedEmb.getSelectedIndex()==0)
            cboPedEmb.requestFocus();
    }//GEN-LAST:event_cboPedEmbFocusGained

    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboPedEmb;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecVen;
    private javax.swing.JLabel lblNumDia;
    private javax.swing.JLabel lblPedEmb;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabPedImp;
    private javax.swing.JPanel panCabTipDoc;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDia;
    private javax.swing.JTextField txtValDoc;
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podráa utilizar
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            dtpFecDoc.setText("");
            
            txtCodDoc.setText("");
            txtNumDia.setText("");
            objAsiDia.inicializar();           
            
            dtpFecVctPedEmbImp.setText("");
            cboPedEmb.setSelectedIndex(0);
            txtValDoc.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
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
                        if(objAsiDia.isDiarioCuadrado()){  
                            if(objTooBar.beforeInsertar()){
                                blnRes=objTooBar.insertar();
                            }
                        }
                        else{
                            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                        }
                        break;
                        
                    case 'm': //Modificar
                        if(objAsiDia.isDiarioCuadrado()){
                            if(objTooBar.beforeModificar()){                            
                                blnRes=objTooBar.modificar();
                            }
                        }
                        else{
                            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                        }
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
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegacián
     * que permiten desplazarse al primero, anterior, siguiente y áltimo registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }
        
        public void clickInicio() {
            try{
                if(arlDatConFacImp.size()>0){
                    if(intIndiceFacImp>0){
                        if (blnHayCam || objAsiDia.isDiarioModificado()) {
                            if (isRegPro()) {
                                intIndiceFacImp=0;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceFacImp=0;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        } 

        public void clickAnterior() {
            try{
                if(arlDatConFacImp.size()>0){
                    if(intIndiceFacImp>0){
                        if ( blnHayCam || objAsiDia.isDiarioModificado() ){
                            if (isRegPro()) {
                                intIndiceFacImp--;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceFacImp--;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }

        public void clickSiguiente() {
            try{
                if(arlDatConFacImp.size()>0){
                    if(intIndiceFacImp < arlDatConFacImp.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado()){
                            if (isRegPro()) {
                                intIndiceFacImp++;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceFacImp++;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }
        
        public void clickFin() {
            try{
                if(arlDatConFacImp.size()>0){
                    if(intIndiceFacImp<arlDatConFacImp.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado() ) {
                            if (isRegPro()) {
                                intIndiceFacImp=arlDatConFacImp.size()-1;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceFacImp=arlDatConFacImp.size()-1;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }        
      
        
        public void clickAceptar()
        {
        }

        public void clickAnular()
        {
        }

        public void clickCancelar()
        {
        }

        public void clickConsultar() 
        {
            txtDesCorTipDoc.setEnabled(true);
            txtDesLarTipDoc.setEnabled(true);
            txtCodDoc.setEnabled(true);
            txtDesCorTipDoc.requestFocus();
            mostrarTipDocPre();
        }

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)  {
                    isRegPro();
                }

                limpiarFrm();
                txtCodDoc.setEditable(false);
                dtpFecDoc.setText(objUti.formatearFecha(objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos()),"dd/MM/yyyy"));
                dtpFecVctPedEmbImp.setText("");
                objAsiDia.setEditable(false);

                mostrarTipDocPre();                
                txtNumDia.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
            }
            catch (Exception e)    {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtNumDia.selectAll();
            txtNumDia.requestFocus();
            objAsiDia.setEditable(true);
            cboPedEmb.setEnabled(false);
            
            cargarReg();
            inactivarCampos();
        }

        public void clickEliminar()
        {
        }

        public void clickImprimir()
        {
        }

        public void clickVisPreliminar() 
        {
        }

        public boolean consultar() 
        {
            consultarReg();
            return true;
        }
        
        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }        

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceFacImp<arlDatConFacImp.size()-1){
                    intIndiceFacImp++;
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
                return true;
            }
            return true;
        }

        public boolean anular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        public boolean vistaPreliminar()
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }
        
        public boolean imprimir()
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(0);
                objThrGUIVisPre.start();
            }
            return true;
        }           
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objAsiDia.isDiarioModificado())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
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
        
        public boolean aceptar()
        {
            return true;
        }

        public boolean afterConsultar()
        {
            dtpFecDoc.setEnabled(false);
            return true;
        }
        
        public boolean afterInsertar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean afterModificar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            strFecDocIni=dtpFecDoc.getText();
            return true;
        }
        
        public boolean afterEliminar()
        {
            return true;
        }
        
        public boolean afterAnular()
        {
            return true;
        }
        
        public boolean afterImprimir()
        {
            return true;
        }
        
        public boolean afterVistaPreliminar()
        {
            return true;
        }
        
        public boolean afterAceptar()
        {
            return true;
        }
        
        public boolean afterCancelar()
        {
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }
        
        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;

            return true;
        }
        
        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            return true;
        }
        
        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }
        
        public boolean beforeAnular(){
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                blnRes=false;
            }
            return blnRes;
        }
        
        public boolean beforeImprimir()
        {
            return true;
        }
        
        public boolean beforeVistaPreliminar()
        {
            return true;
        }
        
        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        boolean blnExiNumDiaRep;
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if (!dtpFecVctPedEmbImp.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de vencimiento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha y vuelva a intentarlo.</HTML>");
            dtpFecVctPedEmbImp.requestFocus();
            return false;
        }
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el asiento de diario y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=getTipModFecUsr();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }
                            datFecAux=null;
                        }
                        else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }

                            return true;
                        }
                    }
                    break;
                case 1://no puede cambiarla para nada
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        
        //Validar que el "Cádigo alterno" no se repita.
        if (!txtNumDia.getText().equals(""))
        {   
            if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm')
            {  //n = nuevo; m = modificar
               strNumDia_Glo="";
               blnExiNumDiaRep = validaExiNumAsiDia(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDia.getText());
                
               if (blnExiNumDiaRep == true)
               {  strAux = "<HTML>El número de diario digitado por el usuario <FONT COLOR=\"blue\">(" + txtNumDia.getText() + ")</FONT> ya existe.<BR>";
                  strAux += "Por tanto, se procedió a asignar otro número disponible <FONT COLOR=\"blue\">(" + strNumDia_Glo + ")</FONT>.</HTML>";
                  mostrarMsgInf(strAux);
                  txtNumDia.setText(strNumDia_Glo);
               }
            }
        } 
        
        //Validar que el "Diario está cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que coincida el valor inicial del asiento con el valor actual.
        if (    (!txtCodTipDoc.getText().equals("30")  &&  !txtCodTipDoc.getText().equals("86")  &&  !txtCodTipDoc.getText().equals("87")   &&  !txtCodTipDoc.getText().equals("88")  &&  !txtCodTipDoc.getText().equals("89")  
                &&  !txtCodTipDoc.getText().equals("113")  &&  !txtCodTipDoc.getText().equals("92")   &&  (!txtCodTipDoc.getText().equals("184"))   &&  (!txtCodTipDoc.getText().equals("288")) &&  (!txtCodTipDoc.getText().equals("194"))   )
                && objUti.redondear(objAsiDia.getMontoInicialDebe(), objParSis.getDecimalesMostrar()) != objUti.redondear(objAsiDia.getMontoDebe(), objParSis.getDecimalesMostrar())  )
        {
            mostrarMsgInf("<HTML>El valor inicial del asiento de diario era <FONT COLOR=\"blue\">" + objAsiDia.getMontoInicialDebe() + "</FONT> y actualmente es de <FONT COLOR=\"blue\">" + objAsiDia.getMontoDebe() + "</FONT>.<BR>El valor actual debe coincidir con el valor inicial del asiento de diario.<BR>Cuadre el valor actual con el valor inicial y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }

    private boolean validaExiNumAsiDia(int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
       boolean blnRes = false, blnConBuc;
       int intAux;
       String strNumDiaAux;
       Connection conLoc;
       Statement stmLoc;
       ResultSet rstLoc;
       try
       {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null)
            {  
                stmLoc = conLoc.createStatement();
                blnConBuc = true; //Variable booleana que indica si debe o no continuar en el bucle.
                strNumDiaAux = strNumDia;
                while (blnConBuc)
                {  
                    strSQL =" SELECT co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
                    strSQL+=" FROM tbm_cabdia ";
                    strSQL+=" WHERE st_reg = 'A'";
                    strSQL+=" AND co_emp = " + intCodEmp;
                    strSQL+=" AND co_loc = " + intCodLoc;
                    strSQL+=" AND co_tipdoc = " + intCodTipDoc;
                    strSQL+=" AND tx_numdia = '" + strNumDiaAux + "'";
                    if (objTooBar.getEstado() == 'm') {  //m = modificar.
                       strSQL+=" AND co_dia <> " + txtCodDoc.getText();
                    }
                    rstLoc = stmLoc.executeQuery(strSQL);

                    if (rstLoc.next())
                    {  //Significa que el numero de Diario si existe. Por tanto, se va a obtener un nuevo numero secuencial.
                       blnRes = true;
                       intAux = Integer.parseInt(strNumDiaAux) + 1;
                       strNumDiaAux = Integer.toString(intAux);
                    }
                    else
                    {  //El numero de Diario no existe.
                       blnConBuc = false; //Se debe salir del bucle
                       strNumDia_Glo = strNumDiaAux;
                    }
                    rstLoc.close();
                    rstLoc = null;
                }
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            } 
       }       
       catch(Exception e)
       {
          blnRes = false;
       }
       return blnRes;
    } 
    
    private boolean generarDiarioLocal(){
        boolean blnRes=true;
        int intPedEmbCodCtaAct=-1;
        int intPedEmbCodCtaPas=-1;
        int intIndSelTrs=-1;        
        try{
            //para generar la cuenta en factura de proveedores por importacion
            for (int j = 0; j < arlDatPed.size(); j++) {
                intIndSelTrs = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
                if (cboPedEmb.getSelectedIndex() == (intIndSelTrs + 1)) {
                    intPedEmbCodCtaAct=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_CTA_ACT);
                    intPedEmbCodCtaPas=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_CTA_PAS);
                    break;
                }
            }
            //se genera el diario para factura de proveedores por importacion
            objAsiDia.generarDiario(intCodEmpImpPedEmb, intCodLocImpPedEmb, Integer.parseInt(txtCodTipDoc.getText()), intPedEmbCodCtaAct, txtValDoc.getText(), intPedEmbCodCtaPas, txtValDoc.getText());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private int getCodigoLocalPredeterminado(int empresaBanco){
        int intCodLocPre=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + empresaBanco + "";
                strSQL+=" AND st_reg='P'";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodLocPre=rst.getInt("co_loc");
                }
                stm.close();
                stm=null;
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
    
    private boolean inactivarCampos(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            intTipModDoc=getTipModDocUsr();     
            objAsiDia.setEditable(true);
            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setEnabled(false);
                            dtpFecVctPedEmbImp.setEnabled(false);
                            txtNumDia.setEditable(false);
                            txtNumDia.setBackground(new java.awt.Color(255, 255, 255));
                            objAsiDia.setEditable(false);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else{
                                String strMsj="";
                                strMsj+="<HTML>EL documento no se puede modificar por las siguientes razones:";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios al documento.";
                                strMsj+="</HTML>";
                                mostrarMsgInf(strMsj);
                            }
                        }
                    }
                    break;
                case 1://no puede modificar nada, incluyendo fecha del documento
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        dtpFecDoc.setEnabled(false);
                        dtpFecVctPedEmbImp.setEnabled(false);
                        txtNumDia.setEditable(false);
                        txtNumDia.setBackground(new java.awt.Color(255, 255, 255));
                        objAsiDia.setEditable(false);
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Adminsitrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }
                    
                    break;
                case 2://modificación parcial la modificación de la fecha dependerá de si se cuenta con este permiso
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        if( ! strEstImpDoc.equals("S")){//si el documento no está impreso se puede modificar, la modif. de fecha depende de tbr_tipDocUsr.ne_tipresmodfecdoc
                            dtpFecDoc.setEnabled(true);
                            dtpFecVctPedEmbImp.setEnabled(true);
                        }
                        else{//si el documento está impreso no se permite modificar
                            dtpFecDoc.setEnabled(false);
                            dtpFecVctPedEmbImp.setEnabled(false);
                            txtNumDia.setEditable(false);
                            txtNumDia.setBackground(new java.awt.Color(255, 255, 255));
                            objAsiDia.setEditable(false);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                        }
                    }
                    break;
                case 3://modificación completa, la modificación de la fecha dependerá de si se cuenta con este permiso
                    dtpFecDoc.setEnabled(true);
                    dtpFecVctPedEmbImp.setEnabled(true);
                    break;
                default:
                    break;
            }
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private int getTipModFecUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModFec=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    //System.out.println("getTipModFecUsr: " + strSQL);
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModFec=rstLoc.getInt("ne_tipresmodfecdoc");
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
    }
    
    private int getTipModDocUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModTipDocUsr=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModTipDocUsr=3;
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT ne_tipresmoddoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    //System.out.println("getTipModDocUsr: " + strSQL);
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModTipDocUsr=rstLoc.getInt("ne_tipresmoddoc");
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModTipDocUsr;
    }    

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_ven";
                strSQL+="     , a1.co_usrIng, a5.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                strSQL+=" FROM ((tbm_cabDia AS a1 LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_usrIng=a5.co_usr)";
                strSQL+="        LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc";
                if(objParSis.getCodigoUsuario()!=1){
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_mnu=a4.co_mnu";
                }
                strSQL+=" WHERE a1.co_emp NOT IN ("+objParSis.getCodigoEmpresaGrupo()+")";
                
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" AND a1.co_emp IN(" +objParSis.getCodigoEmpresa()+ ")";
                }
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                if(objParSis.getCodigoUsuario()!=1){
                    strSQL+=" AND a4.co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc =" + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_dia='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if (dtpFecVctPedEmbImp.isFecha())
                    strSQL+=" AND a1.fe_ven='" + objUti.formatearFecha(dtpFecVctPedEmbImp.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_dia =" + strAux + "";
                strAux=txtNumDia.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.tx_numDia LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_dia";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConFacImp = new ArrayList();
                while(rst.next())
                {
                    arlRegConFacImp = new ArrayList();
                    arlRegConFacImp.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegConFacImp.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegConFacImp.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegConFacImp.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_dia"));
                    arlRegConFacImp.add(INT_ARL_CON_TXT_USRING,rst.getString("tx_nomUsrIng"));
                    arlRegConFacImp.add(INT_ARL_CON_TXT_USRMOD,rst.getString("tx_nomUsrMod"));
                    arlDatConFacImp.add(arlRegConFacImp);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConFacImp.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConFacImp.size()) + " registros");
                    intIndiceFacImp=arlDatConFacImp.size()-1;
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
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
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
        try {
            if (cargarCabReg()){
                blnRes=true;
                objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP)
                                        , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC)
                                        , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC)
                                        , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC));
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        int intDbCodEmpPedEmb=-1, intDbCodLocPedEmb=-1, intDbCodTipDocPedEmb=-1, intDbCodDocPedEmb=-1;
        int intArlCodEmpPedEmb=-1, intArlCodLocPedEmb=-1, intArlCodTipDocPedEmb=-1, intArlCodDocPedEmb=-1;
        int intIndPedEmbSel=-1;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia, a1.tx_numDia, a1.fe_dia, a1.st_reg, a1.st_imp, a1.fe_ven";
                strSQL+="      , a3.co_emp AS co_empPedEmb, a3.co_loc AS co_locPedEmb, a3.co_tipDoc AS co_tipDocPedEmb, a3.co_doc AS co_docPedEmb, a1.nd_valDoc";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_cabPedEmbImpCabDia AS a3 ON(a1.co_emp=a3.co_empRel AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_dia=a3.co_diaRel)";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_dia=" + objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC);
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_dia");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_numDia");
                    txtNumDia.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_dia"),"dd/MM/yyyy"));
                    
                    dtpFecVctPedEmbImp.setText(objUti.formatearFecha(rst.getDate("fe_ven"),"dd/MM/yyyy"));
                    intDbCodEmpPedEmb=rst.getInt("co_empPedEmb");
                    intDbCodLocPedEmb=rst.getInt("co_locPedEmb");
                    intDbCodTipDocPedEmb=rst.getInt("co_tipDocPedEmb");
                    intDbCodDocPedEmb=rst.getInt("co_docPedEmb");

                    txtValDoc.setText( "" +  (objUti.redondearBigDecimal(rst.getBigDecimal("nd_valDoc"), objParSis.getDecimalesMostrar()))  );

                    for (int j = 0; j < arlDatPed.size(); j++) {
                        intArlCodEmpPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_EMP);
                        intArlCodLocPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_LOC);
                        intArlCodTipDocPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_TIP_DOC);
                        intArlCodDocPedEmb = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_DOC);
                        intIndPedEmbSel = (objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL) + 1 );

                        if((intDbCodEmpPedEmb==intArlCodEmpPedEmb) && (intDbCodLocPedEmb==intArlCodLocPedEmb)  && (intDbCodTipDocPedEmb==intArlCodTipDocPedEmb)  && (intDbCodDocPedEmb==intArlCodDocPedEmb)  ) {
                            intCodEmpImpPedEmb=objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP);
                            intCodLocImpPedEmb=objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC);
                            cboPedEmb.setSelectedIndex(intIndPedEmbSel);
                            //System.out.println("intCodEmpImpPedEmb:  " + intCodEmpImpPedEmb);
                            break;
                        }
                    }
                    
                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceFacImp+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConFacImp.size()) );            
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
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
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (objAsiDia.insertarDiario(con, intCodEmpImpPedEmb, intCodLocImpPedEmb, Integer.parseInt(txtCodTipDoc.getText()), txtNumDia.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), objUti.parseDate(dtpFecVctPedEmbImp.getText(),"dd/MM/yyyy"), (objUti.redondearBigDecimal(txtValDoc.getText(), objParSis.getDecimalesMostrar()))   )){
                    txtCodDoc.setText("" + objAsiDia.getCodigoDiario());
                    if(insertar_tbrCabPedEmbImpCabDia()){
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar datos de tbm_cabMovInv y tbm_cabDia   en la tabla relacional.
     * @return true: Si se pudo insertar los datos del pedido embarcado y de la factura del proveedor en la tabla relacional
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbrCabPedEmbImpCabDia(){
        boolean blnRes=true;
        int intCodEmpGrp=-1, intCodLocGrp=-1, intCodTipDocGrp=-1, intCodDocGrp=-1, intIndPedEmbSel;
        try{
            if (con!=null){
                stm=con.createStatement();
                for (int j = 0; j < arlDatPed.size(); j++) {
                    intIndPedEmbSel = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
                    if (cboPedEmb.getSelectedIndex() == (intIndPedEmbSel + 1)) {
                        intCodEmpGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_EMP);
                        intCodLocGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_LOC);
                        intCodTipDocGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_TIP_DOC);
                        intCodDocGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_DOC);
                        break;
                    }
                }
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbr_cabPedEmbImpCabDia(co_emp, co_loc, co_tipdoc, co_doc, co_empRel, co_locrel, co_tipdocrel, co_diarel)";
                strSQL+=" VALUES (";
                strSQL+="  " + intCodEmpGrp;    //co_emp
                strSQL+=", " + intCodLocGrp;    //co_loc
                strSQL+=", " + intCodTipDocGrp; //co_tipdoc
                strSQL+=", " + intCodDocGrp;    //co_doc
                strSQL+=", " + intCodEmpImpPedEmb;     //co_emprel
                strSQL+=", " + intCodLocImpPedEmb;     //co_locrel
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdocrel
                strSQL+=", " + txtCodDoc.getText();    //co_diarel
                strSQL+=");";
                System.out.println("insertar_tbrCabPedEmbImpCabDia: " + strSQL);
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
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (objAsiDia.actualizarDiario(con, intCodEmpImpPedEmb, intCodLocImpPedEmb, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDia.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), objUti.parseDate(dtpFecVctPedEmbImp.getText(),"dd/MM/yyyy"), (objUti.redondearBigDecimal(txtValDoc.getText(), objParSis.getDecimalesMostrar()))      ))
                {
                    if(eliminar_tbrCabPedEmbImpCabDia()){
                        if(insertar_tbrCabPedEmbImpCabDia()){
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite eliminar datos de tbm_cabMovInv y tbm_cabDia   en la tabla relacional.
     * @return true: Si se pudo insertar los datos del pedido embarcado y de la factura del proveedor en la tabla relacional
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbrCabPedEmbImpCabDia(){
        boolean blnRes=true;
        int intCodEmpGrp=-1, intCodLocGrp=-1, intCodTipDocGrp=-1, intCodDocGrp=-1, intIndPedEmbSel;
        try{
            if (con!=null){
                stm=con.createStatement();                
                for (int j = 0; j < arlDatPed.size(); j++) {
                    intIndPedEmbSel = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
                    if (cboPedEmb.getSelectedIndex() == (intIndPedEmbSel + 1)) {
                        intCodEmpGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_EMP);
                        intCodLocGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_LOC);
                        intCodTipDocGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_TIP_DOC);
                        intCodDocGrp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_DOC);
                        break;
                    }
                }
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" DELETE FROM tbr_cabPedEmbImpCabDia";
                strSQL+=" WHERE co_emp=" + intCodEmpGrp + "";//co_emp
                strSQL+=" AND co_loc=" + intCodLocGrp + "";//co_loc
                strSQL+=" AND co_tipdoc=" + intCodTipDocGrp + "";//co_tipdoc
                strSQL+=" AND co_doc=" + intCodDocGrp + "";//co_doc
                strSQL+=" AND co_empRel=" + intCodEmpImpPedEmb + "";//co_emprel
                strSQL+=" AND co_locrel=" + intCodLocImpPedEmb + "";//co_locrel
                strSQL+=" AND co_tipdocrel=" + txtCodTipDoc.getText() + "";//co_tipdocrel
                strSQL+=" AND co_diarel=" + txtCodDoc.getText() + "";//co_diarel
                strSQL+=";";
                System.out.println("eliminar_tbrCabPedEmbImpCabDia: " + strSQL);
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
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (objAsiDia.eliminarDiario(con, objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP)
                                                , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC)
                                                , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC)
                                                , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC) ))
                {
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
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (objAsiDia.anularDiario(con, objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP)
                                              , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC)
                                              , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC) 
                                              , objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC) ))
                {
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
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
        boolean blnRes=true;
        Connection conRpt;
        Statement stmRpt;
        String strRutRpt, strNomRpt, strFecHorSer;
        String strSQLRep="", strSQLSubRep="";
        int i, intNumTotRpt;
        try
        {
            conRpt =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conRpt!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 0:
                                default: //Facturas Importaciones    
                                    strSQL ="";
                                    strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia, a1.tx_numDia";
                                    strSQL+="      , a1.fe_dia, a1.st_reg, a1.st_imp, a1.tx_glo, a1.fe_ven, a1.nd_valdoc";
                                    strSQL+="      , a1.co_usrIng, a3.tx_nom AS tx_nomUsrIng, a1.co_usrMod, a4.tx_nom AS tx_nomUsrMod";
                                    strSQL+=" FROM ((tbm_cabDia AS a1 INNER JOIN tbm_usr AS a3 ON a1.co_usrIng=a3.co_usr)";
                                    strSQL+="        INNER JOIN tbm_usr AS a4 ON a1.co_usrMod=a4.co_usr )";
                                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                    strSQL+=" AND a1.co_emp="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a1.co_loc="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a1.co_tipDoc="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a1.co_dia="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC);
                                    strSQLRep = strSQL;
                                    //System.out.println("strSQLRep: " + strSQLRep);

                                    strSQL ="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                                    strSQL+="      , a3.co_cta, a3.tx_codCta, a3.tx_desLar as tx_nomCta,  a2.nd_monDeb, a2.nd_monHab";
                                    strSQL+=" FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2";
                                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia";
                                    strSQL+=" INNER JOIN tbm_plaCta AS a3 ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                    strSQL+=" AND a1.co_emp="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a1.co_loc="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a1.co_tipDoc="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a1.co_dia="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC);
                                    strSQL+=" ORDER BY a2.co_reg";
                                    strSQLSubRep = strSQL;
                                    //System.out.println("strSQLSubRep: " + strSQLSubRep);

                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    mapPar.put("strCamAudRpt", ""  + (objUti.getStringValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_TXT_USRING) +  " / " + objUti.getStringValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v0.1    ");
                                    mapPar.put("codUsrImp", "" + objParSis.getCodigoUsuario());
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP) );
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC) );
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC) );
                                    mapPar.put("codDia", objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC));
                                    mapPar.put("nomEmp", getNomEmpImpPedEmb());
                                    mapPar.put("rucEmp", getRucEmp(objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP)));
                                    mapPar.put("nomCorTipDoc", txtDesCorTipDoc.getText());
                                    mapPar.put("nomLarTipDoc", txtDesLarTipDoc.getText());
                                    mapPar.put("fecAct", "" + strFecHorSer);
                                    mapPar.put("fecDoc", objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                                    mapPar.put("fecVen", objUti.formatearFecha(dtpFecVctPedEmbImp.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                                    mapPar.put("valDoc", txtValDoc.getText());
                                    mapPar.put("numDia", txtNumDia.getText());
                                    mapPar.put("numDia", txtNumDia.getText());
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        
                                    //Actualiza el estado de impresión
                                    stmRpt = conRpt.createStatement();
                                    strSQL ="";
                                    strSQL+=" UPDATE tbm_cabDia";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND co_loc="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND co_tipDoc="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND co_dia="+ objUti.getIntValueAt(arlDatConFacImp, intIndiceFacImp, INT_ARL_CON_COD_DOC);
                                    stmRpt.executeUpdate(strSQL);

                                    stmRpt.close();
                                    stmRpt = null;
                                    conRpt.close();
                                    conRpt=null;

                                    strEstImpDoc = "S";
                                    break;
                            }
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
     * Función que obtiene el RUC de la empresa de acuerdo al código de empresa enviado.
     * @param intCodEmp
     * @return strRucEmp
     */
    private String getRucEmp(int intCodEmp) {
        String strRucEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSQL=" SELECT tx_ruc FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    strRucEmp = rstLoc.getString("tx_ruc");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strRucEmp;
    }    

    /**
     * Función que obtiene el nombre de la empresa Importadora que corresponde al Pedido Embarcado.
     * Para mostrar el nombre en la Factura de Importaciones al Ingresar por Grupo
     * @return strNomEmpImp: Nombre de la Empresa Importadora (Ej.: Tuval , Castek, Dimulti)
     */
    private String getNomEmpImpPedEmb()
    {
        String strNomEmpImp =" ";    
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT co_emp, tx_ruc, tx_nom, tx_dir FROM tbm_emp ";
                strSQL+=" WHERE co_emp="+intCodEmpImpPedEmb;  
                //System.out.println("getNomEmpImpPedEmb: " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if (rstLoc.next())
                {
                    strNomEmpImp= rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        return strNomEmpImp;  
    }
    

    
  
}