package RecursosHumanos.ZafRecHum37;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Roles de Pago
 * @author Roberto Flores
 * Guayaquil 02/07/2012
*/
public class ZafRecHum37_04 extends javax.swing.JInternalFrame {
  
  private java.sql.Connection CONN_GLO=null;
  private java.sql.Statement  STM_GLO=null;
  private java.sql.ResultSet  rstCab=null;
  
  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  
  private ZafTblTot objTblTot;                                //JTable de totales.
  private ZafTblBus objTblBus;
  private ZafTblCelRenLbl objTblCelRenLbl;  
  private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
  private ZafTblFilCab objTblFilCab;
  private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
  private Librerias.ZafDate.ZafDatePicker txtFecDoc;
  private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
  private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
  private java.util.Date datFecAux;
  
  
  Librerias.ZafUtil.ZafCtaCtb objCtaCtb;                      // Para obtener  los codigos y nombres de ctas ctbles
  private ZafAsiDia objAsiDia;                                
  private int intCodEmp, intCodLoc;                           //Código de la empresa y local.
  
  private String strDesCorTipDoc="";
  private String strDesLarTipDoc="";
  private String strCodTipDoc="";
  private String strDesDep="";

  private Vector vecCab=new Vector();
  private Vector vecDat;
  private ZafVenCon objVenConTipdoc; //*****************
  
  private ZafUtil objUti;
  private mitoolbar objTooBar;
  
  private boolean blnHayCam=false;
 
  private static final int INT_TBL_LINEA=0;
  private static final int INT_TBL_CODTRA=1;
  private static final int INT_TBL_NOMTRA=2;
  private static final int INT_TBL_DIAS_LAB=3;

  
  private String strSQL, strAux;
  
  private String strDesCorCta, strDesLarCta;                  //Contenido del campo al obtener el foco.
  private String strUbiCta, strAutCta;                        //Campos: Ubicacián y Estado de autorizacián de la cuenta.
  
  
  private ZafThreadGUI objThrGUI;
  private ZafRptSis objRptSis;                                //Reportes del Sistema.
  
  private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
  private ZafVenCon vcoCta;                                   //Ventana de consulta "Cuenta".
  
  private int intSig=1;                                       //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
  
  private Connection con, conCab;
  private Statement stm, stmCab;
  private ResultSet rst;//, rstCab;
  
  private ZafVenCon vcoDep;                                   //Ventana de consulta "Tipo de documento".
  
  private Vector vecAux;
    
  static final int INT_TBL_DAT_NUM_TOT_CDI=35;                //Número total de columnas dinámicas.
    
    
  private int intCanIng=0;
  private int intCanEgr=0;
  int intCanColTot=0;
    
  Vector vecTipDoc,vecDetDiario; //Vector que contiene el codigo de tipos de documentos    
    
  private ArrayList<String> arrLst=null;//almacena los rubros del rol de pagos
  private ArrayList<Tbm_traemp> arrLstTbmTraEmp=new ArrayList<Tbm_traemp>();//almacena datos de empleados tbm_traemp
  private ArrayList<String> arrLstAntSue=null;
  private ArrayList<String> arrLstAntBon=null;
  private ArrayList<String> arrLstAntMov=null;
    
  private String strVersion="v1.07 beta 2";
    
   /** Creates new  form */
  public ZafRecHum37_04(Librerias.ZafParSis.ZafParSis obj) {
      try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          initComponents();
          
          /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
          cargarAños();
          
          objUti = new ZafUtil();  

          intCodEmp=objZafParSis.getCodigoEmpresa();
          intCodLoc=objZafParSis.getCodigoLocal();
          
          objTooBar = new mitoolbar(this);
          this.getContentPane().add(objTooBar,"South");
          //objTooBar.setVisibleModificar(false);
          
          objTooBar.agregarSeparador();
          objTooBar.agregarBoton(butArc);
          
          objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

          txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
          txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
          txtFecDoc.setText("");
          PanTabGen.add(txtFecDoc);
          txtFecDoc.setBounds(580, 8, 92, 20);
          //txtFecDoc.setEnabled(false);
          txtFecDoc.setBackground(objZafParSis.getColorCamposObligatorios());
          txtFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
          txtFecDoc.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  txtFecDocActionPerformed(evt);
              }
          });
          
          objAsiDia=new ZafAsiDia(objZafParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
          
          this.setTitle(objZafParSis.getNombreMenu()+ " " + strVersion);
          lblTit.setText(objZafParSis.getNombreMenu()); 
      }catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);e.printStackTrace();}
      catch(Exception e) {objUti.mostrarMsgErr_F1(this, e);e.printStackTrace();}
  }
  
  private boolean cargarAños(){
    boolean blnRes=true;
    java.sql.Connection con = null; 
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null; 
    String strSQL="";

    try{
        con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(con!=null){ 
            
            stmLoc=con.createStatement();
            
            strSQL="select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani desc";
            rstLoc=stmLoc.executeQuery(strSQL);
            while(rstLoc.next()){
                cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
            }
        }
    }catch (java.sql.SQLException Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    } catch (Exception Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
    return blnRes;
  } 
  
    /**
     * Esta función carga el documento especificado.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public void cargarDocumento(Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        intCodEmp=codigoEmpresa.intValue();
        intCodLoc=codigoLocal.intValue();
        txtCodTipDoc.setText(codigoTipoDocumento.toString());
        txtValDoc.setText(codigoDocumento.toString());
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleAnular(false);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
    }
  
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_merIngEgrFisBod");
            arlCam.add("a1.ne_numLin");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Est.Mer.Ing.Egr.Fis.Bod.");
            arlAli.add("Núm.Lín.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("60");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objZafParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=5;
            intColOcu[1]=6;
            intColOcu[2]=7;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            arlCam.add("a1.st_aut");
            arlCam.add("a1.ne_ultnumchq");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            arlAli.add("Autorización");
            arlAli.add("Número de Cheque");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=6;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
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
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objZafParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objZafParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));//THERE
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    //strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
                    objTblMod.setMaxRowsAllowed(Integer.valueOf(rst.getInt("ne_numLin")));
                }
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que sálo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
                strUbiCta="";
                strAutCta="";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" ORDER BY a1.tx_codCta";
                System.out.println("mostrarVenConCta:" + strSQL);

                System.out.println("NEFWNFKWF: " + objTooBar.getEstado());

                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            strAutCta=vcoCta.getValueAt(5);
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            strAutCta=vcoCta.getValueAt(5);
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta=vcoCta.getValueAt(4);
                                strAutCta=vcoCta.getValueAt(5);
                            }
                            else
                            {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            strAutCta=vcoCta.getValueAt(5);
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta=vcoCta.getValueAt(4);
                                strAutCta=vcoCta.getValueAt(5);
                            }
                            else
                            {
                                txtDesLarCta.setText(strDesLarCta);
                            }
                        }
                        break;
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
    
  public void setEditable(boolean editable) {
      if(editable==true) objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
      else objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
  }

  public void abrirCon(){
      try{
          CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      }catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
  }

  public void CerrarCon(){
      try{
          CONN_GLO.close();
          CONN_GLO=null;
      }catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
  }
  
  /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTabla(int intSel)
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            //vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CODTRA,"Código");
            vecCab.add(INT_TBL_NOMTRA,"Empleado");
            vecCab.add(INT_TBL_DIAS_LAB,"Días. Lab.");
            
            /*RUBROS INGRESOS DE MANERA DINAMICA*/
                      
            Connection conIns = null;
            Statement stmLoc = null;
            ResultSet rstLoc = null;
            
            intCanIng=0;
            intCanEgr=0;
            
            try{
                conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                
                
                
                    if(conIns!=null){
                        
                        stmLoc=conIns.createStatement();
                        int intAño = 0;
                        int intMes = 0;
                        System.out.println(objTooBar.getEstado());
                        
                        if(objTooBar.getEstado()=='n'){//insertar
                            
                            strSQL="select case when min(ne_ani) is null then 0 else min(ne_ani) END as ne_anio from tbm_ingegrmentra where st_rolpaggen is null";
                            strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
                            System.out.println("sql ne_ani : "+strSQL);
                            rstLoc=stmLoc.executeQuery(strSQL);

                            if(rstLoc.next()){
                                intAño=rstLoc.getInt("ne_anio");
                            }

                            /**/

                            strSQL="select case when min(ne_mes) is null then 0 else min(ne_mes) END as ne_meso from tbm_ingegrmentra where st_rolpaggen is null";
                            strSQL+=" and ne_per not in (0) and co_emp = " + objZafParSis.getCodigoEmpresa();
                            System.out.println("sql ne_mes : "+strSQL);
                            rstLoc=stmLoc.executeQuery(strSQL);

                            if(rstLoc.next()){
                                intMes=rstLoc.getInt("ne_meso");
                            }
                            
                        }else if (objTooBar.getEstado()=='c' || objTooBar.getEstado()=='j'){


                            intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                            intMes=cboPerMes.getSelectedIndex();
                            
                        }
                        intAño=2012;
                        intMes=12;
                        
                        arrLst = new ArrayList<String>();
                        strSQL="";
                        
                        switch(intSel){
                            
                            case 1: //consulta lo actual
                                //strSQL="select * from tbm_rubrolpag where co_rub in (select distinct co_rub from tbm_ingEgrMenTra) order by co_rub asc";order by tx_tiprub desc,co_rub asc
                                if(objZafParSis.getCodigoMenu()==3138){
                                    strSQL="select * from tbm_rubrolpag";
                                    strSQL+=" where co_rub in (select distinct co_rub from tbm_ingEgrMenTra)";
//                                    strSQL+=" and extract(year from fe_ing)<="+intAño;
//                                    strSQL+=" and extract(month from fe_ing)<="+intMes;
//                                    strSQL+=" and co_grp = 1";
                                    strSQL+=" order by tx_tiprub desc,co_rub asc";
                                }else{
                                    strSQL="select * from tbm_rubrolpag";
                                    strSQL+=" where co_rub in (select distinct co_rub from tbm_ingEgrMenTra)";
//                                    strSQL+=" and extract(year from fe_ing)<="+intAño;
//                                    strSQL+=" and extract(month from fe_ing)<="+intMes;
//                                    strSQL+=" and co_grp = 2";
                                    strSQL+=" order by tx_tiprub desc,co_rub asc";
                                }
                                
                                break;
                                
                            case 2: //consulta rol de pagos almacenados
                            
                                strSQL+="select distinct b.co_rub, b.tx_nom, b.tx_tiprub from tbm_detrolpag a";
                                strSQL+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub)";
                                strSQL+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
                                strSQL+=" and a.co_loc="+objZafParSis.getCodigoLocal();
                                strSQL+=" and a.co_tipdoc="+txtCodTipDoc.getText().toString();
                                strSQL+=" and a.co_doc="+txtCodDoc.getText().toString();
                                strSQL+=" order by b.tx_tiprub desc,b.co_rub asc";

                                break;
                        }
                        
                        
                        stmLoc=conIns.createStatement();
                        rstLoc=stmLoc.executeQuery(strSQL);
                            while(rstLoc.next()){
                                vecCab.add(vecCab.size(),rstLoc.getString("tx_nom"));
                                
                                intCanColTot++;
                                if(rstLoc.getString("tx_tiprub").equals("I")){
                                    intCanIng++;
                                }else{
                                    intCanEgr++;
                                }
                                arrLst.add(rstLoc.getString("co_rub"));
                            }
                    }
            }catch(SQLException ex)
            {
                objUti.mostrarMsgErr_F1(this, ex);
                return false;
            }finally {
                try{stmLoc.close();}catch(Throwable ignore){}
                try{rstLoc.close();}catch(Throwable ignore){}
                try{conIns.close();}catch(Throwable ignore){}
           }
            
            vecCab.add(vecCab.size(),"Ingresos");
            vecCab.add(vecCab.size(),"Egresos");
            vecCab.add(vecCab.size(),"Total");
//            vecCab.add(vecCab.size(),"GI1");
//            vecCab.add(vecCab.size(),"GI2");
            
            System.out.println("tamaño de col : " + vecCab.size());
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
//            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_CUOTA, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_DIAS_LAB).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_NOMTRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_DIAS_LAB).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(vecCab.size()-1, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_CHKTRA);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            /*objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);*/
            
            //cambios realizados a la tabla
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            //objTblCelRenLbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);

            int intP=INT_TBL_DIAS_LAB+1;
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            for (int i=0; i<1; i++)
            {
                for(int x=intP;x<tblDat.getColumnCount();x++){
                    objTblMod.setColumnDataType(x+i, objTblMod.INT_COL_DBL, new Integer(0), null);
                    tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
                    tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLbl);
                    tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
                    
                    
                    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                        int intFilSel;//=tblDat.getSelectedRow();
                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }

                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }
                    });
                }
            }
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intTblCalTotColIni=INT_TBL_DIAS_LAB+1;
            int intCol[]= new int[objTblMod.getColumnCount()-INT_TBL_DIAS_LAB-1];
            int intColPos=0;
            for(int i=intTblCalTotColIni;i<objTblMod.getColumnCount();i++){
                intCol[intColPos]=i;
                intColPos++;
            }

            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   public void Configura_ventana_consulta(){
       configurarVenConTipDoc();
       configurarVenConCta();
       
       configurarTabla(1);
       agregarColTblDat();
   }
   
   private boolean agregarColTblDat()
    {
        
        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        boolean blnRes=true;
        int intCanColIng =0;
        int intCanColEgr=0;

        try
        {

            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();

            for (i=0; i<1; i++)
            {

                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Ingresos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                intCanColIng = INT_TBL_DIAS_LAB+1+intCanIng;
                for(int t=3;t<intCanColIng;t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t+i*INT_TBL_DAT_NUM_TOT_CDI));
                }
                
                intCanColEgr=intCanColIng+intCanEgr;
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Egresos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);


                for(int t=intCanColIng;t<intCanColEgr;t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t+i*INT_TBL_DAT_NUM_TOT_CDI));
                }
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Totales");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                for(int t=intCanColEgr;t<tblDat.getColumnCount();t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t+i*INT_TBL_DAT_NUM_TOT_CDI));
                }
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        butPer = new javax.swing.JButton();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtValDoc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblNumDoc2 = new javax.swing.JLabel();
        cboPer = new javax.swing.JComboBox();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        txtDesCorCta = new javax.swing.JTextField();
        txtCodCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        butArc = new javax.swing.JButton();
        panDetTraDep = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 100));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:"); // NOI18N
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        PanTabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(210, 10, 210, 20);

        butBusTipDoc.setText("jButton1"); // NOI18N
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        PanTabGen.add(butBusTipDoc);
        butBusTipDoc.setBounds(420, 10, 20, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:"); // NOI18N
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(450, 10, 130, 20);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        PanTabGen.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 70, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(450, 30, 140, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Cuenta:"); // NOI18N
        PanTabGen.add(jLabel5);
        jLabel5.setBounds(10, 30, 110, 20);

        butPer.setText(".."); // NOI18N
        butPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPerActionPerformed(evt);
            }
        });
        PanTabGen.add(butPer);
        butPer.setBounds(420, 50, 20, 20);

        lblNumDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc1.setText("Valor del Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc1);
        lblNumDoc1.setBounds(450, 50, 140, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
        });
        PanTabGen.add(txtNumDoc);
        txtNumDoc.setBounds(590, 30, 80, 20);

        txtValDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.setText("0.0");
        PanTabGen.add(txtValDoc);
        txtValDoc.setBounds(590, 50, 80, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Período:"); // NOI18N
        PanTabGen.add(jLabel7);
        jLabel7.setBounds(10, 50, 110, 20);

        lblNumDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc2.setText("Código del Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc2);
        lblNumDoc2.setBounds(450, 70, 140, 20);

        cboPer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Período", "Primera quincena", "Fin de mes" }));
        PanTabGen.add(cboPer);
        cboPer.setBounds(310, 50, 110, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        PanTabGen.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 50, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        PanTabGen.add(cboPerMes);
        cboPerMes.setBounds(210, 50, 100, 20);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesCorCta);
        txtDesCorCta.setBounds(140, 30, 70, 20);

        txtCodCta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodCta);
        txtCodCta.setBounds(108, 30, 32, 20);
        txtCodCta.setVisible(true);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        PanTabGen.add(txtDesLarCta);
        txtDesLarCta.setBounds(210, 30, 210, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        PanTabGen.add(butCta);
        butCta.setBounds(420, 30, 20, 20);

        txtCodTipDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 10, 32, 20);
        //txtCodTipDoc.setVisible(false);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(590, 70, 80, 20);

        butArc.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        butArc.setText("Generar Archivo");
        butArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butArcActionPerformed(evt);
            }
        });
        PanTabGen.add(butArc);
        butArc.setBounds(260, 70, 140, 30);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panDetTraDep.setLayout(new java.awt.BorderLayout());

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panDetTraDep.add(spnTot, java.awt.BorderLayout.SOUTH);

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

        panDetTraDep.add(spnDat, java.awt.BorderLayout.CENTER);

        panTabGen.add(panDetTraDep, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 80));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs3.setViewportView(txtObs1);

        panTxa.add(spnObs3);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs2.setLineWrap(true);
        spnObs4.setViewportView(txtObs2);

        panTxa.add(spnObs4);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panTabGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabGen.addTab("Asiento de diario", panAsiDia);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
         strDesCorTipDoc=txtDesCorTipDoc.getText();
         txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    
    if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
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
        }
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtFecDocActionPerformed(java.awt.event.ActionEvent evt) {                                                
    txtFecDoc.transferFocus();
} 

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
 strDesLarTipDoc=txtDesLarTipDoc.getText();
 txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost

    if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
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
        }
}//GEN-LAST:event_txtDesLarTipDocFocusLost
  
private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
 
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butBusTipDocActionPerformed

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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado()=='n')
                                {
                                    strAux=vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                                }
                                intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            }
                            else
                            {
                                txtDesCorTipDoc.setText(strDesCorTipDoc);
                            }
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado()=='n')
                                {
                                    strAux=vcoTipDoc.getValueAt(4);
                                    txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                                }
                                intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                                txtNumDoc.selectAll();
                                txtNumDoc.requestFocus();
                            }
                            else
                            {
                                txtDesLarTipDoc.setText(strDesLarTipDoc);
                            }
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


private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    // TODO add your handling code here:
     String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {

            if(rstCab!=null) 
               rstCab=null;
            if(STM_GLO!=null)
               STM_GLO=null;

            System.gc();
            dispose();
        }
}//GEN-LAST:event_formInternalFrameClosing

    public void BuscarDep(String campo,String strBusqueda,int tipo){
        
        vcoDep.setTitle("Listado de Departamentos");
        if(vcoDep.buscar(campo, strBusqueda )) {
            txtDesLarCta.setText(vcoDep.getValueAt(3));
        }else{
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton()==vcoDep.INT_BUT_ACE) {
                txtDesLarCta.setText(vcoDep.getValueAt(3));
        }else{
                txtDesLarCta.setText(strDesDep);
  }}}
    
    private void butPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPerActionPerformed

    }//GEN-LAST:event_butPerActionPerformed

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        // TODO add your handling code here:
        txtNumDoc.selectAll();
    }//GEN-LAST:event_txtNumDocFocusGained

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
        txtDesCorCta.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaActionPerformed

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
        strDesCorCta = txtDesCorCta.getText();
        txtDesCorCta.selectAll();
    }//GEN-LAST:event_txtDesCorCtaFocusGained

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)) {
            if (txtDesCorCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
                //objTblMod.removeAllRows();
            } else {
                mostrarVenConCta(1);
            }
        } else {
            txtDesCorCta.setText(strDesCorCta);
        }
    }//GEN-LAST:event_txtDesCorCtaFocusLost

    private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
        txtDesLarCta.transferFocus();
    }//GEN-LAST:event_txtDesLarCtaActionPerformed

    private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
        strDesLarCta = txtDesLarCta.getText();
        txtDesLarCta.selectAll();
    }//GEN-LAST:event_txtDesLarCtaFocusGained

    private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta)) {
            if (txtDesLarCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                //objTblMod.removeAllRows();
            } else {
                mostrarVenConCta(2);
            }
        } else {
            txtDesLarCta.setText(strDesLarCta);
        }
    }//GEN-LAST:event_txtDesLarCtaFocusLost

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void butArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butArcActionPerformed
        // TODO add your handling code here:

        if(generarArchivoRolPago()){
            mostrarMsgInf("Archivo generado correctamente");
        }
        else{
            mostrarMsgInf("Archivo no fue generado correctamente");
        }
    }//GEN-LAST:event_butArcActionPerformed

    private boolean generarArchivoRolPago(){
        
        boolean blnRes=true;
        String strTab="	";
        
        String strCodigoOrientacion="PA";
        String strContraPartida="";//1ERA QUINC MAY 2012;
        
        if (cboPer.getSelectedIndex()==1){
            strContraPartida+="1ERA QUINC ";
        }else if(cboPer.getSelectedIndex()==2){
            strContraPartida+="2DA QUINC ";
        }
        
        if (cboPerMes.getSelectedIndex()>0){
            strContraPartida+=cboPerMes.getSelectedItem().toString().substring(0,3).toUpperCase() + " ";
        }
        
        if (cboPerAAAA.getSelectedIndex()>0){
            strContraPartida+=cboPerAAAA.getSelectedItem().toString();
        }
            
        String strMoneda="USD";
        String strValor="";
        String strFormaPago="CTA";
        String strTipoCuenta="";
        String strNumeroCuenta="";
        String strReferencia=strContraPartida;
        String strTipoIDBeneficiario="C";
        String strNumeroIDClienteBeneficiario="";//cedula
        String strNombreBeneficiario="";
        String strCodigoInstitucionFinanciera="32";
        
        try{
            
        
            String slash = File.separator;
            String strEscArc = "";
            //Prepara Archivo
            SimpleDateFormat fmt = new SimpleDateFormat("dd_MMM_yyyy");
            String strFec = fmt.format(new java.util.Date());
            
            String strQFDM="";
            String strNomEmp=objZafParSis.getNombreEmpresa();
            String strMes=cboPerMes.getSelectedItem().toString().toUpperCase();
            String strAni=cboPerAAAA.getSelectedItem().toString();
            
            if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                strQFDM="1ERA QUINC";
            }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
                strQFDM="2DA QUINC";
            }
            
            
            String strRutArcLogSueldos = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_SUELDOS " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni;
            String strRutArcLogBonos = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+strNomEmp+"/"+"ROLPAG_BONOS " + strNomEmp+" - "+strQFDM + " " + strMes + " " + strAni;
            
            String strRutArcLogSueldosSinCtaBan = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+"ROLPAG_SUELDOS_SIN_CTABAN " + objZafParSis.getNombreEmpresa()+" - "+strFec;
            String strRutArcLogBonosSinCtaBan = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/CASH MANAGEMENT/"+"ROLPAG_BONOS_SIN_CTABAN " + objZafParSis.getNombreEmpresa()+" - "+strFec;
            
            if(!System.getProperty("os.name").equals("Linux")){
                strRutArcLogSueldos = "C:"+slash+strRutArcLogSueldos;
                strRutArcLogBonos = "C:"+slash+strRutArcLogBonos;
            }
            
            File archivoSueldos = new File(strRutArcLogSueldos);
            //File dirSueldos = new File(strRutArcLogSueldos);
            
            File archivoBonos = new File(strRutArcLogBonos);
            //File dirBonos = new File(strRutArcLogBonos);
            
            File archivoSueldosSinCtaBan = new File(strRutArcLogSueldosSinCtaBan);
            File archivoBonosSinCtaBan = new File(strRutArcLogBonosSinCtaBan);
            //File dirSueldosSinCtaBan = new File(strRutArcLogSueldos);
            
            /*File archivoBonos = new File(strRutArcLogBonos);
            File dirBonos = new File(strRutArcLogBonos);*/
            
            archivoSueldos.delete();
            archivoBonos.delete();
            
            archivoSueldosSinCtaBan.delete();
            archivoBonosSinCtaBan.delete();
            
            ArchivosTuval logSueldos = new ArchivosTuval(strRutArcLogSueldos);
            ArchivosTuval logBonos = new ArchivosTuval(strRutArcLogBonos);
            
            ArchivosTuval logSueldosSinCtaBan = new ArchivosTuval(strRutArcLogSueldosSinCtaBan);
            ArchivosTuval logBonosSinCtaBan = new ArchivosTuval(strRutArcLogBonosSinCtaBan);

            System.out.println("-------------------------------------------------------------------ROL DE PAGOS SUELDOS-------------------------------------------------------------------");    
            
            //int intFil=0;
            //**aumentar coincidencia de codigos de trabajo
                for(Iterator<Tbm_traemp> itLstTraEmp=arrLstTbmTraEmp.iterator();itLstTraEmp.hasNext();){

                    String strLog="";
                    Tbm_traemp tbm_traemp = itLstTraEmp.next();
                    int intCodTra=tbm_traemp.getIntCo_tra();
                    strTipoCuenta=tbm_traemp.getStrTx_TipCtaBan();
                    strNumeroCuenta=tbm_traemp.getStrTx_NumCtaBan();
                    
                    for(int intFilRec=0; intFilRec<objTblMod.getRowCount();intFilRec++){
                        
                        
                        if(intCodTra==Integer.parseInt(objTblMod.getValueAt(intFilRec, INT_TBL_CODTRA).toString())){
                            
                            if(validaArchivo(strTipoCuenta,strNumeroCuenta)){

                                if(strTipoCuenta.equals("A")){
                                    strTipoCuenta="AHO";
                                }else{
                                    strTipoCuenta="CTE";
                                }

                                strNumeroIDClienteBeneficiario=tbm_traemp.getStrTx_Ide();
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount()-2).toString());
                                strValor=objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount()-2).toString());

                                strValor=retornaValorStr(strValor);

                                strNombreBeneficiario=tbm_traemp.getStrEmpleado();

                                if(Double.parseDouble(strValor)>0){

                                    strLog=strCodigoOrientacion+strTab+strContraPartida+strTab+strMoneda+strTab+strValor+strTab+strFormaPago+strTab+strTipoCuenta+strTab;
                                    strLog+=strNumeroCuenta+strTab+strReferencia+strTab+strTipoIDBeneficiario+strTab+strNumeroIDClienteBeneficiario+strTab;
                                    strLog+=strNombreBeneficiario+strTab+strCodigoInstitucionFinanciera;
                                    System.out.println(strLog);
                                    logSueldos.println(strLog);
                                    //System.Text.Encoding.Default
                                }

                            //intFil++;

                            }else{

                                strTipoCuenta="XXX";
                                strNumeroCuenta="XXX";


                                strNumeroIDClienteBeneficiario=tbm_traemp.getStrTx_Ide();
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                strValor=objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount()-2).toString());

                                strValor=retornaValorStr(strValor);

                                strNombreBeneficiario=tbm_traemp.getStrEmpleado();

                                if(Double.parseDouble(strValor)>0){
                                    strLog=strCodigoOrientacion+strTab+strContraPartida+strTab+strMoneda+strTab+strValor+strTab+strFormaPago+strTab+strTipoCuenta+strTab;
                                    strLog+=strNumeroCuenta+strTab+strReferencia+strTab+strTipoIDBeneficiario+strTab+strNumeroIDClienteBeneficiario+strTab;
                                    strLog+=strNombreBeneficiario+strTab+strCodigoInstitucionFinanciera;
                                    System.out.println(strLog);
                                    logSueldosSinCtaBan.println(strLog);
                                }
                                
                                intFilRec=objTblMod.getRowCount();
                            
                            }
                        }
                    }
                }
                
                //BONOS
                
                System.out.println("--------------------------------------------------------------------ROL DE PAGOS BONOS--------------------------------------------------------------------");
                
                //intFil=0;
                //int intColPos=0;
                
//                for(int intCol=0; intCol < objTblMod.getColumnCount()-1; intCol++){
//                    if(objTblMod.getColumnName(intCol).toString().trim().equals("Anticipo bono")){
//                        intColPos = intCol;
//                    }
//                }
                
                for(Iterator<Tbm_traemp> itLstTraEmp=arrLstTbmTraEmp.iterator();itLstTraEmp.hasNext();){
                    
                    String strLog="";
                    Tbm_traemp tbm_traemp = itLstTraEmp.next();
                    int intCodTra=tbm_traemp.getIntCo_tra();
                    strTipoCuenta=tbm_traemp.getStrTx_TipCtaBan();
                    strNumeroCuenta=tbm_traemp.getStrTx_NumCtaBan();
                    
                    for(int intFilRec=0; intFilRec<objTblMod.getRowCount();intFilRec++){
                        
                        if(intCodTra==Integer.parseInt(objTblMod.getValueAt(intFilRec, INT_TBL_CODTRA).toString())){
                        
                            if(validaArchivo(strTipoCuenta,strNumeroCuenta)){
                        
                                if(strTipoCuenta.equals("A")){
                                    strTipoCuenta="AHO";
                                }else{
                                    strTipoCuenta="CTE";
                                }

                                strNumeroIDClienteBeneficiario=tbm_traemp.getStrTx_Ide();
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, intColPos).toString());
                                strValor=objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount()-1).toString());

                                strValor=retornaValorStr(strValor);

                                strNombreBeneficiario=tbm_traemp.getStrEmpleado();

                                if(Double.parseDouble(strValor)>0){

                                    strLog=strCodigoOrientacion+strTab+strContraPartida+strTab+strMoneda+strTab+strValor+strTab+strFormaPago+strTab+strTipoCuenta+strTab;
                                    strLog+=strNumeroCuenta+strTab+strReferencia+strTab+strTipoIDBeneficiario+strTab+strNumeroIDClienteBeneficiario+strTab;
                                    strLog+=strNombreBeneficiario+strTab+strCodigoInstitucionFinanciera;
                                    System.out.println(strLog);
                                    logBonos.println(strLog);


                                }
                        
                        //intFil++;
                        
                            }else{
                        
                                strTipoCuenta="XXX";
                                strNumeroCuenta="XXX";


                                strNumeroIDClienteBeneficiario=tbm_traemp.getStrTx_Ide();
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                //strValor=objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-1).toString());
                                strValor=objUti.parseString(objTblMod.getValueAt(intFilRec, objTblMod.getColumnCount()-1).toString());

                                strValor=retornaValorStr(strValor);

                                strNombreBeneficiario=tbm_traemp.getStrEmpleado();

                                if(Double.parseDouble(strValor)>0){
                                    strLog=strCodigoOrientacion+strTab+strContraPartida+strTab+strMoneda+strTab+strValor+strTab+strFormaPago+strTab+strTipoCuenta+strTab;
                                    strLog+=strNumeroCuenta+strTab+strReferencia+strTab+strTipoIDBeneficiario+strTab+strNumeroIDClienteBeneficiario+strTab;
                                    strLog+=strNombreBeneficiario+strTab+strCodigoInstitucionFinanciera;
                                    System.out.println(strLog);
                                    logBonosSinCtaBan.println(strLog);
                                }
                        
                        //intFil++;
                        
                            }
                        }
                    }
                }
        }catch(Exception e){
            blnRes=false; objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
    private String retornaValorStr(String strValor){
        String cadena=strValor;
        String cadena2="";
        int car=cadena.indexOf(".");
        int limite=0;
        if (car>0)
            cadena2=cadena.substring(car+1);

        limite=(cadena2.length() > 0)?cadena2.length():2;


        if(limite%2!=0){
            for (int i =0; i< limite; i++)
                cadena+="0";
        }


        cadena=cadena.replace(".","");
        strValor=cadena;
        return strValor;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaArchivo(String strTipoCuenta, String strNumeroCuenta)
    {
        //Validar el "Tipo de documento".
        if (strTipoCuenta==null)
        {
            return false;
        }
        //Validar el "Departamento".
        if(strNumeroCuenta==null){
            return false;
        }

        return true;
    }
    
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

private void bloquea(javax.swing.JTextField txtFiel,  boolean blnEst){
    java.awt.Color colBack = txtFiel.getBackground();
    txtFiel.setEditable(blnEst);
    txtFiel.setBackground(colBack);
}

public void BuscarTipDoc(String campo,String strBusqueda,int tipo){

  objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);
  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }else{
           txtCodTipDoc.setText(strCodTipDoc); 
           txtDesCorTipDoc.setText(strDesCorTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butArc;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCta;
    private javax.swing.JButton butPer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPer;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumDoc2;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetTraDep;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCta;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

  private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
  public class mitoolbar extends ZafToolBar{
    public mitoolbar(javax.swing.JInternalFrame jfrThis){
        super(jfrThis, objZafParSis);
    }
   

    public boolean anular() {
        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            return false;

        }
        if (strAux.equals("Anulado")) {
            MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
            return false;
        }

        if (!anularReg())
            return false;
        objTooBar.setEstadoRegistro("Anulado");
        blnHayCam=false;
        return true;
    }


private boolean anularReg(){

        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCab())
                {
                    if(actualizarTbmIngEgrMenTra()){
                        
                        if (objAsiDia.anularDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                            con.commit();
                            blnRes=true;
                        }else{
                            con.rollback();
                        }
                    }else{
                        con.rollback();
                    }
                }
                else{
                    con.rollback();
                }
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

    private boolean actualizarTbmIngEgrMenTra(){
        boolean blnRes=true;
        int intNePer=-1;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                
                if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                    intNePer=1;
                }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
                    intNePer=2;
                }
                
                //Armar la sentencia SQL.
                
                strSQL="";
                strSQL="update tbm_ingEgrMenTra set st_rolpaggen = null";
                strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                strSQL+=" and ne_ani = " + cboPerAAAA.getSelectedItem().toString();
                strSQL+=" and ne_mes = " + cboPerMes.getSelectedIndex();
                strSQL+=" and ne_per = " + intNePer;
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

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabrolpag";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objZafParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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

    public void clickAceptar() {
        setEstadoBotonMakeFac();
    }

    public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    rstCab.previous();
                    cargarReg();
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
        }

    public void clickSiguiente()
    {
        try
        {
            if (!rstCab.isLast())
            {
                rstCab.next();
                cargarReg();
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
    }

    public void clickInicio()
    {
        try
        {
            if (!rstCab.isFirst())
            {
                rstCab.first();
                cargarReg();
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
    }

    public void clickFin()
    {
        try
        {
            if (!rstCab.isLast())
            {
                rstCab.last();
                cargarReg();
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
    }

    public void clickAnular() {
            
    }

    public void clickConsultar() {
        clnTextos();
        noEditable(false);
       
//        bloquea(txtNumDoc, false);

    }

    public void clickEliminar() {
//          noEditable(false);
    }

    public void clickInsertar() {
        try{
            clnTextos();
            //noEditable(false);

            
            configurarTabla(1);
            agregarColTblDat();
            
            java.awt.Color colBack;
            colBack = txtValDoc.getBackground();
            txtValDoc.setEditable(false);
            txtValDoc.setBackground(colBack);
//            txtCodDoc.setEditable(false);
            txtCodDoc.setBackground(colBack);

            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                     java.util.Date dateObj =datFecAux;
                     java.util.Calendar calObj = java.util.Calendar.getInstance();
                     calObj.setTime(dateObj);
                     txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                             calObj.get(java.util.Calendar.MONTH)+1     ,
                             calObj.get(java.util.Calendar.YEAR)        );
                     
                     mostrarTipDocPre();
                     
                     /*CARGAR REGISTROS ROLES*/
                     if(cargarRolPer()){
                         if(generaAsiento()){
                             //if(objAsiDia.isDiarioCuadrado()){
                                 if(objZafParSis.getCodigoUsuario()==1){
                                     butArc.setEnabled(true);
                                 }
                             //}
                         }
                     }
                     
                     if(rstCab!=null) {
                         rstCab.close();
                         rstCab=null;
                     }
                     
                     txtFecDoc.setEnabled(true);
                     
            objAsiDia.setEditable(true);
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEnabledVistaPreliminar(true);
        }catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); e.printStackTrace();}
    }

    public void setEstadoBotonMakeFac(){
        switch(getEstado()) {
            case 'l'://Estado 0 => Listo
                break;
            case 'x'://Estado click modificar
                break;
            case 'c'://Estado Consultar
                break;
            case 'y':
                break;
            case 'z':
                break;
            default:
                break;
        }
    }

public boolean eliminar() {

    try
    {
        if (!eliminarReg())
            return false;
        //Desplazarse al siguiente registro si es posible.
        if (!rstCab.isLast())
        {
            rstCab.next();
            cargarReg();
        }
        else
        {
            objTooBar.setEstadoRegistro("Eliminado");
            clnTextos();
        }
        blnHayCam=false;
    }
    catch (java.sql.SQLException e)
    {
        return true;
    }
    return true;
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
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (eliminarDet())
                {
                    if (eliminarCab())
                    {

                        if(actualizarTbmIngEgrMenTra()){
                            
                            if (objAsiDia.eliminarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                            {
                                con.commit();
                                blnRes=true;
                            }else{
                                con.rollback();
                            }
                            
                        }else{
                            con.rollback();
                        }
                    }
                    else{
                        con.rollback();
                    }
                }else{
                    con.rollback();
                }
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
    private boolean eliminarCab()
    {
        boolean blnRes=true;
        int intNePer=-1;

        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                
                if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                    intNePer=1;
                }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
                    intNePer=2;
                }
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE from tbm_cabRolPag";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;

        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE from tbm_detRolPag";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaCampos()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar el "Departamento".
        if(txtCodCta.getText().equals("")){
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta y vuelva a intentarlo.</HTML>");
            txtDesCorCta.requestFocus();
            return false;
        }
        //Validar la "Fecha del Documento".
        if(!txtFecDoc.isFecha()){
            tabGen.setSelectedIndex(0);                 
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de documento y vuelva a intentarlo.</HTML>");
            txtFecDoc.requestFocus();
            return false;
        }
        //Validar el "Número de Documento".
        String str = txtNumDoc.getText();
        if(txtNumDoc.getText().equals("")){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }else{
            if(!objUti.isNumero(txtNumDoc.getText())){
                tabGen.setSelectedIndex(1);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Documento</FONT> contiene formato érroneo.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc.requestFocus();
                return false;
            }
        }

        //Validar el "Año".
        int intPerAAAA=cboPerAAAA.getSelectedIndex();
        if(intPerAAAA<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
            cboPerAAAA.requestFocus();
            return false;
        }
        //Validar el "Mes".
        int intPerMes=cboPerMes.getSelectedIndex();
        if(intPerMes<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes</FONT> es obligatorio.<BR>Seleccione el mes y vuelva a intentarlo.</HTML>");
            cboPerMes.requestFocus();
            return false;
        }
        //Validar el "Período".
        int intPer=cboPer.getSelectedIndex();
        if(intPer<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Periodo</FONT> es obligatorio.<BR>Seleccione el período y vuelva a intentarlo.</HTML>");
            cboPer.requestFocus();
            return false;
        }

        //Validar que el "Diario esté cuadrado".
//        if (!objAsiDia.isDiarioCuadrado())
//        {
//            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
//            return false;
//        }
//        //Validar que el "Monto del diario" sea igual al monto del documento.
//        //if (!objAsiDia.isDocumentoCuadrado(objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-5)), objZafParSis.getDecimalesMostrar())))
//        if (!objAsiDia.isDocumentoCuadrado(txtValDoc.getText()))
//        {
//            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
//            txtValDoc.selectAll();
//            txtValDoc.requestFocus();
//            return false;
//        }
        
        return true;
    }

    public boolean insertar(){
        boolean blnRes=false;

        try{
            if(validaCampos()){
                
                    if(insertarReg())
                        blnRes=true;
            }
        }catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }
    
    public boolean insertarReg(){
        boolean blnRes=false;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="";
        int intCodDoc=0;
        int intNumDoc=0;

        try{
            con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(con!=null){ 
                con.setAutoCommit(false);

                stmLoc=con.createStatement();

//                strSQL="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabrolpag WHERE " +
//                       " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" "+
//                       " and co_tipDoc = "+txtCodTipDoc.getText();
//                rstLoc = stmLoc.executeQuery(strSQL);
//                if(rstLoc.next())
//                    intCodDoc = rstLoc.getInt("co_doc");
//                rstLoc.close();
//                rstLoc=null;
//
//                strSQL="SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc " +
//                       " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText();
//                rstLoc = stmLoc.executeQuery(strSQL);
//                    if(rstLoc.next())
//                        intNumDoc = rstLoc.getInt("numDoc");
//                rstLoc.close();
//                rstLoc=null;

                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                String FecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(),"yyyy/MM/dd");


//                if (insertarCabReg(con,intCodDoc, intNumDoc, strFecha)) { 
                    intCodDoc = Integer.valueOf(txtCodDoc.getText());
                    intNumDoc = Integer.valueOf(txtNumDoc.getText());
                    if (insertarDetReg(con,intCodDoc, intNumDoc, strFecha)) { 

                        if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                            
//                            if(setearAnticipadoFDM(con)){
                            
                                if(actualizarStRolPagGen(con)){

//                                    if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"))){
                                        con.commit();
                                        blnRes = true;
                                        txtCodDoc.setText(""+intCodDoc);
                                        txtNumDoc.setText(""+intNumDoc);
//                                    }else{
//                                        con.rollback();
//                                    }
                                }else{
                                    con.rollback();
                                }
//                            }else{
//                                con.rollback();
//                            }
                        }else{
                            
                            if(actualizarStRolPagGen(con)){

//                                if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"))){
                                    con.commit();
                                    blnRes = true;
                                    txtCodDoc.setText(""+intCodDoc);
                                    txtNumDoc.setText(""+intNumDoc);
//                                }else{
//                                    con.rollback();
//                                }
                            }else{
                                con.rollback();
                            }
                        }
                    }else{
                        con.rollback();
                    }
//                }else{
//                    con.rollback();
//                }
         }
        }catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{rstLoc.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
            try{con.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    public boolean setearAnticipadoFDM(java.sql.Connection con){
        
        boolean blnRes=true;
        String strFecSis="";
        String strSql="";
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                for(int intFil=0; intFil < objTblMod.getRowCount(); intFil++){
                    
                    strSql="";
                    strSql+="update tbm_ingegrmentra set nd_valrub = -"+objUti.parseString(objTblMod.getValueAt(intFil, objTblMod.getColumnCount()-2).toString());
                    strSql+=" where co_emp = "+objZafParSis.getCodigoEmpresa();
                    strSql+=" and co_tra = "+objTblMod.getValueAt(intFil, INT_TBL_CODTRA).toString();
                    strSql+=" and ne_ani = "+cboPerAAAA.getSelectedItem().toString();
                    strSql+=" and ne_mes = "+cboPerMes.getSelectedIndex();
                    strSql+=" and ne_per = 2 ";
                    strSql+=" and co_rub = 24 ";
                    System.out.println("query act anticipo(sueldos) fdm: " + strSql);
                    stmLoc.executeUpdate(strSql);
                    
                    String strAntBon=null;
                    if(arrLstAntBon.get(intFil).toString().compareTo("0.0")!=0){
                        strAntBon=arrLstAntBon.get(intFil).toString();
                        strSql="";
                        strSql+="update tbm_ingegrmentra set nd_valrub = -"+strAntBon;
                        strSql+=" where co_emp = "+objZafParSis.getCodigoEmpresa();
                        strSql+=" and co_tra = "+objTblMod.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and ne_ani = "+cboPerAAAA.getSelectedItem().toString();
                        strSql+=" and ne_mes = "+cboPerMes.getSelectedIndex();
                        strSql+=" and ne_per = 2 ";
                        strSql+=" and co_rub = 25 ";
                        System.out.println("query act anticipo(bono) fdm: " + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                    
                    
                    
                    String strAntMov=null;
                    if(arrLstAntMov.get(intFil).toString().compareTo("0.0")!=0){
                        strAntMov=arrLstAntMov.get(intFil).toString();
                        strSql="";
                        strSql+="update tbm_ingegrmentra set nd_valrub = -"+strAntMov;
                        strSql+=" where co_emp = "+objZafParSis.getCodigoEmpresa();
                        strSql+=" and co_tra = "+objTblMod.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and ne_ani = "+cboPerAAAA.getSelectedItem().toString();
                        strSql+=" and ne_mes = "+cboPerMes.getSelectedIndex();
                        strSql+=" and ne_per = 2 ";
                        strSql+=" and co_rub = 26 ";
                        System.out.println("query act anticipo(movilizacion) fdm: " + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                }
            }
            
        }catch(java.sql.SQLException Evt){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }catch(Exception Evt){ 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, Evt); 
        }finally {
            try{stmLoc.close();}catch(Throwable ignore){}
            try{rstLoc.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
public boolean insertarCabReg(java.sql.Connection con, int intCodDoc, int intNumDoc,String strFecha ){
    boolean blnRes=false;
    String strFecSis="";
    String strSql="";
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;

    try{
        if(con!=null){
            stmLoc=con.createStatement();
              
              strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
              
              int intAAAAPer=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
              int intMesPer=cboPerMes.getSelectedIndex();
              int intPer=cboPer.getSelectedIndex();
              String strStImp="N";
              String strStReg="A";
              
              strSql="INSERT INTO tbm_cabrolpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, nd_valdoc, co_cta, ne_ani, ne_mes, ne_per, st_imp, tx_obs1,";
              strSql+=" tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) VALUES (";
              strSql+= " " + objZafParSis.getCodigoEmpresa() + ",";
              strSql+= " " + objZafParSis.getCodigoLocal() + ",";
              strSql+= " " + txtCodTipDoc.getText() + ",";
              strSql+= " " + intCodDoc + ",";
              strSql+= " '" + strFecha + "',";
              strSql+= " " + txtNumDoc.getText() + ",";
              strSql+= " " + objUti.redondear(objUti.parseDouble(txtValDoc.getText()), objZafParSis.getDecimalesMostrar()) + ",";
              strSql+= " " + txtCodCta.getText() + ",";
              strSql+= " " + intAAAAPer + ",";
              strSql+= " " + intMesPer + ",";
              strSql+= " " + intPer + ",";
              strSql+= " " + objUti.codificar(strStImp) + ",";
              strSql+= " " + objUti.codificar(txtObs1.getText()) + ",";
              strSql+= " " + objUti.codificar(txtObs2.getText()) + ",";
              strSql+= " " + objUti.codificar(strStReg) + ",";
              strSql+= " " + "current_timestamp" + ",";
              strSql+= " " + "null" + ",";
              strSql+= " " + objZafParSis.getCodigoUsuario() + ",";
              strSql+= " " + "null";
              strSql+= ")";
              
              System.out.println("query insert cabecera rol pagos: " + strSql);
              stmLoc.executeUpdate(strSql);
              
              blnRes=true;
        }
    }catch(java.sql.SQLException Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }catch(Exception Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        }
   return blnRes;         
 }
 
public boolean insertarDetReg(java.sql.Connection con, int intCodDoc, int intNumDoc,String strFecha ){
    
    boolean blnRes=true;
    String strSql="";
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;

    try{
        if(con!=null){
            
            stmLoc=con.createStatement();
            
            ArrayList<String> arrLst=null;
            try{

                arrLst = new ArrayList<String>();
                          
                int intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                int intMes=cboPerMes.getSelectedIndex();
                
                strSQL="";
                //strSQL="select * from tbm_rubrolpag where co_rub in (select distinct co_rub from tbm_ingEgrMenTra) order by co_rub asc";
                strSQL+="select * from tbm_rubrolpag";
                strSQL+=" where co_rub in (select distinct co_rub from tbm_ingEgrMenTra)";
//                strSQL+=" and extract(year from fe_ing)<="+intAño;
//                strSQL+=" and extract(month from fe_ing)<="+intMes;
                strSQL+=" order by tx_tiprub desc,co_rub asc";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    arrLst.add(rstLoc.getString("co_rub"));
                }
            }catch(SQLException ex)
            {
                objUti.mostrarMsgErr_F1(this, ex);
                return false;
            }
           
            int intCoReg=0;
            int intPosCoRub=0;
            /*INSERT EN EL DETALLE*/
            for(int i=0; i<tblDat.getRowCount();i++){
                
                    int intPosCol=INT_TBL_DIAS_LAB+1;
//                    int intFilt=(intCanIng+intCanIng);
                    for(int x=0;x<arrLst.size();x++){
                        
                        intCoReg=intCoReg+1;
                        strSql="INSERT INTO tbm_detrolpag(";
                        strSql+=" co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra, co_rub, nd_valrub) VALUES (";
                        strSql+= " " + objZafParSis.getCodigoEmpresa() + ",";
                        strSql+= " " + objZafParSis.getCodigoLocal() + ",";
                        strSql+= " " + txtCodTipDoc.getText() + ",";
                        strSql+= " " + intCodDoc + ",";
                        strSql+= " " + intCoReg + ",";
                        strSql+= " " + objTblMod.getValueAt(i, INT_TBL_CODTRA).toString() + ",";
                        strSql+= " " + arrLst.get(intPosCoRub).toString() + ",";
                        strSql+= " " + objTblMod.getValueAt(i, intPosCol).toString() + ");";
                        System.out.println("query insert detalle rol pagos: " + strSql);
                        stmLoc.executeUpdate(strSql);
                        intPosCol++;
                        intPosCoRub++;
                    }
                    intPosCoRub=0;
            }
        }
    }catch(java.sql.SQLException Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }catch(Exception Evt){ 
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
    }
   return blnRes;         
 }

public boolean actualizarStRolPagGen(java.sql.Connection con){
    boolean blnRes=false;
    String strSql="";
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null;

    try{
        if(con!=null){
            
            stmLoc=con.createStatement();
            
            strSql="update tbm_ingegrmentra set st_rolpaggen='S'";
            strSql+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
            strSql+=" and ne_ani = " + Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
            strSql+=" and ne_mes = " + cboPerMes.getSelectedIndex();
            strSql+=" and ne_per = " + cboPer.getSelectedIndex();
            System.out.println("query actualizacion st_rolpaggen rol pagos: " + strSql);
            stmLoc.executeUpdate(strSql);
            blnRes=true;
        }
    }catch(java.sql.SQLException Evt){ 
        Evt.printStackTrace();
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }catch(Exception Evt){ 
        Evt.printStackTrace();
        blnRes=false;  
        objUti.mostrarMsgErr_F1(this, Evt); 
    }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
    }
   return blnRes;         
 }

   private boolean validarDat(){
      boolean blnRes=true;
      if(txtCodTipDoc.getText().trim().equals("")) { MensajeInf("Ingrese tipo el documento");  return false; }

      return blnRes;
   } 

    public boolean modificar(){
        
        boolean blnRes=false;
        generaAsiento();
//        java.sql.Connection conn = null;
//        java.sql.Statement stmLoc = null;
//        java.sql.ResultSet rstLoc = null; 
//        String strSQL="";
//        
//        try {
//
//            strAux=objTooBar.getEstadoRegistro();
//        
//            if(strAux.equals("Eliminado")) {
//                MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
//                return false;
//            }
//            if(strAux.equals("Anulado")) {
//                MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
//                return false;
//            }
//
//            if (validaCampos()) {
//                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
//                if (conn != null) {
//                    conn.setAutoCommit(false);
//                    stmLoc=con.createStatement();
//                    if (objTblMod.getRowCountChecked(INT_TBL_CHKTRA)<=0) {
//                        
//                        /*VALIDAR QUE LOS EMPLEADOS ESCOGIDOS NO SE ENCUENTREN EN OTRA SOLICITUD*/
//                        //String strFecSol=txtFecSol.getText().toString();
//                        int FechaSol[] = txtFecSol.getFecha(txtFecSol.getText());
//                        String strFechaSol = "#" + FechaSol[2] + "/" + FechaSol[1] + "/" + FechaSol[0] + "#";
//
//                        //strSQL="select * from tbm_cabSolHorSupExt where fe_sol='"+ strFechaSol + "'";
//                        strSQL="select co_tra from tbm_detSolHorSupExt a "+
//                                "where co_doc in (select distinct co_doc from tbm_cabSolHorSupExt where fe_sol='"+strFechaSol+"')";
//                        rstLoc=stmLoc.executeQuery(strSQL);
//
//                        boolean blnExtTraSel=false;
//                        while(rstLoc.next()){
//                            String strCoTraSel=rstLoc.getString("co_tra");
//                            for(int i=0; i<tblDat.getRowCount();i++){
//                                if(tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M")==0){
//
//                                    if(tblDat.getValueAt(i, INT_TBL_CODTRA).toString().equals(strCoTraSel)){
//                                        blnExtTraSel=true;
//                                        i=tblDat.getRowCount();
//                                    }
//
//                                }
//                            }
//                        }
//                        
//                        if(!blnExtTraSel){
//                            if (modificarCab(conn, Integer.parseInt(txtValDoc.getText()))) {
//                                if (modificarDet(conn, Integer.parseInt(txtValDoc.getText()))) {
//                                    conn.commit();
//                                    blnRes = true;
//                                }else{
//                                    conn.rollback();
//                                }
//                            }else {
//                                conn.rollback();
//                            }
//                        }else{
//                            con.rollback();
//                            mostrarMsgInf("HAY EMPLEADOS YA SELECCIONADOS PARA OTRA SOLICITUD");
//                            //blnRes=false;
//                            return false;
//                        }
//                        
//                    }else{
//                        con.rollback();
//                        mostrarMsgInf("NO HA SELECCIONADO EMPLEADOS QUE APLIQUEN A LA SOLICITUD");
//                        //blnRes=false;
//                        return false;
//                    }
//                }
//            }
//        }catch (java.sql.SQLException Evt) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        } catch (Exception Evt) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }finally {
//            try{rstLoc.close();}catch(Throwable ignore){}
//            try{stmLoc.close();}catch(Throwable ignore){}
//            try{conn.close();}catch(Throwable ignore){}
//        }
         
         return blnRes;
                        
    }

 public boolean modificarCab(java.sql.Connection conn, int intCodDoc){
     boolean blnRes=false;
//     java.sql.Statement stmLoc;
//     String strSQL="";
//     String strFechaDoc="";
//     String strFechaSol="";
//      String strFecSis = "";
//     try{
//        if (conn!=null){
//             
//            stmLoc=conn.createStatement();
//            
//            int FechaDoc[] =  txtFecDoc.getFecha(txtFecDoc.getText());
//            strFechaDoc = "#" + FechaDoc[2] + "/"+FechaDoc[1] + "/" + FechaDoc[0] + "#";
//            
//            int FechaSol[] =  txtFecSol.getFecha(txtFecSol.getText());
//            strFechaSol = "#" + FechaSol[2] + "/"+FechaSol[1] + "/" + FechaSol[0] + "#";
//            
//            strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());
//            
//             strSQL="UPDATE tbm_cabSolHorSupExt SET  fe_doc='"+strFechaDoc+"', co_dep="+txtCta.getText()+ ", co_mot="+txtCodMot.getText()+
//                    ", fe_sol='"+strFechaSol+ "' ,ho_sol='"+txtHorSol.getText()+"' , tx_obs1=" + objUti.codificar(txtObs1.getText()) + ", tx_obs2=" + objUti.codificar(txtObs2.getText()) + 
//                    ", fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "+
//                    " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + 
//                    " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
//            stmLoc.executeUpdate(strSQL);
//              
//            stmLoc.close();
//            stmLoc=null;
//            blnRes=true;
//           
//      }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
//       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   return blnRes;         
 }
 
// public boolean modificarDet(java.sql.Connection conn, int intCodDoc){
//     boolean blnRes=false;
//     java.sql.Statement stmLoc;
//     java.sql.ResultSet rstLoc;
//     String strSQL="";
//     
//     try{
//        if (conn!=null){
//             
//            stmLoc=conn.createStatement();
//            
//            int intCom=0;
//              for(int i=0; i<tblDat.getRowCount();i++){
//                
//                if(tblDat.getValueAt(i, INT_TBL_LINEA).toString().compareTo("M")==0){
//                    
//                    intCom++;
//                    
//                    strSQL = "select 1 as flag from tbm_detsolhorsupext where co_emp="+ objZafParSis.getCodigoEmpresa() + " and co_loc ="+ objZafParSis.getCodigoLocal() + " "+
//                            "and co_tipdoc= "+ txtCodTipDoc.getText() + " and co_doc="+intCodDoc+" and co_reg="+tblDat.getValueAt(i, INT_TBL_COREG).toString()+" and co_tra="+tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
//                    rstLoc=stmLoc.executeQuery(strSQL);
//                    int intFlag =0;
//                    if(rstLoc.next()){
//                        intFlag = rstLoc.getInt("flag");//1 si ya existe
//                    }
//                    
//                    if(intFlag==0){
//                        strSQL="INSERT INTO tbm_detsolhorsupext(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_tra) "+
//                                "VALUES ("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+intCodDoc+","+tblDat.getValueAt(i, INT_TBL_COREG).toString()+", "+
//                                tblDat.getValueAt(i, INT_TBL_CODTRA).toString()+")";
//                            stmLoc.executeUpdate(strSQL);
//                            
//                            String st_Aut = "P";
//                            strSQL="INSERT INTO tbm_autSolHorSupExt(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_aut) "+
//                                "VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+intCodDoc+","+tblDat.getValueAt(i, INT_TBL_COREG).toString()+", '"+ 
//                                st_Aut+"')";
//                            stmLoc.executeUpdate(strSQL);
//                    
//                    }else{
//                        if(tblDat.getValueAt(i, INT_TBL_CHKTRA).equals(false)){
//                            strSQL = "delete from tbm_detsolhorsupext where co_emp="+ objZafParSis.getCodigoEmpresa() + " and co_loc ="+ objZafParSis.getCodigoLocal() + " " +
//                                    "and co_tipdoc= "+ txtCodTipDoc.getText() + " and co_doc="+intCodDoc+" and co_reg="+tblDat.getValueAt(i, INT_TBL_COREG).toString()+" and co_tra="+tblDat.getValueAt(i, INT_TBL_CODTRA).toString();
//                            stmLoc.executeUpdate(strSQL);
//                            
//                            strSQL = "delete from tbm_autSolHorSupExt where co_emp="+ objZafParSis.getCodigoEmpresa() + "and co_loc ="+ objZafParSis.getCodigoLocal() + 
//                                    "and co_tipdoc= "+ txtCodTipDoc.getText() + " and co_doc="+intCodDoc+" and co_reg="+tblDat.getValueAt(i, INT_TBL_COREG).toString();
//                            stmLoc.executeUpdate(strSQL);
//                        }
//                    }
//                }
//            }
//            
//            stmLoc.close();
//            stmLoc=null;
//            blnRes=true;
//           
//      }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
//       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
//   return blnRes;         
// }

 private void noEditable(boolean blnEst){
      txtObs1.setEditable(false);
      txtObs2.setEditable(false);

   }

     /**
     * Esta funcián permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    public boolean  clnTextos(){
        
        boolean blnRes=true;
        try
        {
            strCodTipDoc=""; strDesCorTipDoc=""; strDesLarTipDoc="";

            objAsiDia.inicializar();
            
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtFecDoc.setText("");

            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
            txtNumDoc.setText("");

            txtCodDoc.setText("");
            txtValDoc.setText("");

            txtObs1.setText("");
            txtObs2.setText("");

            cboPerAAAA.setSelectedIndex(0);
            cboPerMes.setSelectedIndex(0);
            cboPer.setSelectedIndex(0);

            objTblMod.removeAllRows();
            
            for(int intCol=0;intCol<tblDat.getColumnCount();intCol++){
                objTblTot.setValueAt("", 0, intCol);
            }
            
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    public boolean cancelar() {
        boolean blnRes=true;

        try {
            if (blnHayCam || objTblMod.isDataModelChanged()) {
                if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
                    if (!isRegPro())
                        return false;
                }
            }
            if (rstCab!=null) {
                rstCab.close();
                if (STM_GLO!=null){
                    STM_GLO.close();
                    STM_GLO=null;
                }
                rstCab=null;

            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        clnTextos();
        blnHayCam=false;

        return blnRes;
    }

    public boolean aceptar() {
        return true;
    }

    public boolean afterAceptar() {
        return true;
    }

    public boolean afterAnular() {
        return true;
    }

    public boolean afterCancelar() {

        return true;
    }

    public boolean afterConsultar() {

        return true;
    }

    public boolean afterEliminar() {
        return true;
    }

    public boolean afterImprimir() {
        return true;
    }

    public boolean afterInsertar() {
        this.setEstado('w');

        return true;
    }

    public boolean afterModificar() {

       this.setEstado('w');

        return true;
    }

    public boolean afterVistaPreliminar() {
        return true;
    }

      /**
 * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
 * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
 * seleccionando una de las opciones que se presentan.
 */
private int mostrarMsgCon(String strMsg) {
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
}

      /**
 * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
 * de tipo texto para poder determinar si su contenido a cambiado o no.
 */
private boolean isRegPro() {
    boolean blnRes=true;
    String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
    strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
    switch (mostrarMsgCon(strAux)) {
        case 0: //YES_OPTION
            switch (objTooBar.getEstado()) {
                case 'n': //Insertar
                    blnRes=objTooBar.insertar();
                    break;
                case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                    break;
            }
            break;
        case 1: //NO_OPTION
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            blnRes=true;
            break;
        case 2: //CANCEL_OPTION
            blnRes=false;
            break;
    }
    return blnRes;
}

    public boolean consultar() {
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
//       return _consultar(FilSql());
        consultarReg();
        return true;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    strSQL+="select * from tbm_cabRolPag a1";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL+=" AND a5.co_mnu=" + objZafParSis.getCodigoMenu();
                    strSQL+=" AND a1.st_reg<>'E'";
                }

                strAux=txtDesCorTipDoc.getText();
                if (!strAux.equals("")){
                    strSQL+=" AND a1.co_tipdoc=" + strAux;
                }
                    
                if (txtFecDoc.isFecha()){
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objZafParSis.getFormatoFechaBaseDatos()) + "'";
                }
                    
                strAux=txtCodDoc.getText();
                if (!strAux.equals("")){
                    strSQL+=" AND a1.co_doc=" + strAux;
                }
                    
                if (cboPerAAAA.getSelectedIndex()>0){
                    strSQL+=" AND a1.ne_ani=" + cboPerAAAA.getSelectedIndex();
                }
                    
                if (cboPerMes.getSelectedIndex()>0){
                    strSQL+=" AND a1.ne_mes=" + cboPerMes.getSelectedIndex();
                }
                    
                if (cboPer.getSelectedIndex()>0){
                    strSQL+=" AND a1.ne_per=" + cboPer.getSelectedIndex();
                }
                    
                strSQL+=" ORDER BY a1.co_emp, a1.co_doc";
                
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    clnTextos();
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
        boolean blnRes=true;
        try
        {
            
            
            
            if (cargarCabReg())
            {
                
                configurarTabla(2);
                agregarColTblDat();
                
                if (cargarDetReg())
                {
                    objAsiDia.consultarDiario(intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
//                    generaAsiento();
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        String strSQL="";
        java.util.Date datFecDoc;
        java.util.Date datPriCuo;
        
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                
                strSQL="select a.*,b.tx_descor as descortipdoc, b.co_tipdoc , b.tx_deslar as deslartipdoc,c.co_cta, c.tx_codCta as codcta,c.tx_deslar as txdeslarcta  from tbm_cabRolPag a";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS b ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS c ON (a.co_emp=c.co_emp AND a.co_cta=c.co_cta)";
                strSQL+=" where a.co_emp = " + rstCab.getString("co_emp");
                strSQL+=" AND a.co_loc = " + rstCab.getString("co_loc");
                strSQL+=" AND a.co_tipdoc = " + rstCab.getString("co_tipdoc");
                strSQL+=" AND a.co_doc=" + rstCab.getString("co_doc");
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    
                    strAux=rst.getString("co_tipdoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("descortipdoc");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("deslartipdoc");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_cta");
                    txtCodCta.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("codcta");
                    txtDesCorCta.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("txdeslarcta");
                    txtDesLarCta.setText((strAux==null)?"":strAux);
                    
                    
                    datFecDoc=rst.getDate("fe_doc");
                    txtFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                    
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    txtValDoc.setText("" + objUti.redondear(rst.getDouble("nd_valdoc"),objZafParSis.getDecimalesMostrar()));
                    
                    cboPerAAAA.setSelectedItem(rst.getString("ne_ani"));
                    cboPerMes.setSelectedIndex(rst.getInt("ne_mes"));
                    cboPer.setSelectedIndex(rst.getInt("ne_per"));
                    
                    strAux=rst.getString("tx_obs1");
                    txtObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txtObs2.setText((strAux==null)?"":strAux);
                    
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
                    clnTextos();
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
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
//            configurarTabla();
//            agregarColTblDat();
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {

        String strSQL;
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 


        try
        {

            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {

                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmLoc=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                strSQL="";
                /*strSQL="select distinct co_tra from tbm_detrolpag";
                strSQL+=" where co_emp = " + rstCab.getString("co_emp");
                strSQL+=" and co_loc = " + rstCab.getString("co_loc");
                strSQL+=" and co_tipdoc = " + rstCab.getString("co_tipdoc");
                strSQL+=" and co_doc = " + rstCab.getString("co_doc");
                strSQL+=" order by co_tra asc";*/
                strSQL+="select distinct a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_detrolpag a";
                strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra and a.co_emp="+rstCab.getString("co_emp")+")";
                strSQL+=" where a.co_emp = " + rstCab.getString("co_emp");
                strSQL+=" and a.co_loc = " + rstCab.getString("co_loc");
                strSQL+=" and a.co_tipdoc = " + rstCab.getString("co_tipdoc");
                strSQL+=" and a.co_doc = " + rstCab.getString("co_doc");
                //strSQL+=" and a.co_tra = 2" ;
                //strSQL+=" and co_tra = " + rstCab.getString("co_tra");
                strSQL+=" order by empleado asc";
                System.out.println("sql detalle : "+strSQL);
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat = new Vector();
//                vecDat.clear();
                //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                int intPosCol=INT_TBL_DIAS_LAB+1;
                int intCont=0;
                
                
                while(rst.next()){

                        double dblTotIng=0;
                        double dblTotEgr=0;
                        double dblBono=0;

                        strSQL="select a.*,(b.tx_ape || ' ' || b.tx_nom) as nomcom,c.tx_tiprub from tbm_detrolpag a";
                        strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                        strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub)";
                        strSQL+=" where a.co_emp = " + rst.getString("co_emp");
                        strSQL+=" and a.co_loc = " + rst.getString("co_loc");
                        strSQL+=" and a.co_tipdoc = " + rst.getString("co_tipdoc");
                        strSQL+=" and a.co_doc = " + rst.getString("co_doc");
                        strSQL+=" and a.co_tra = " + rst.getString("co_tra");
                        strSQL+=" order by c.tx_tiprub desc,a.co_rub asc";
                        
                        System.out.println("query obt rob rol pag : " + strSQL);
                        rstLoc=stmLoc.executeQuery(strSQL);

                        if(rstLoc.next()){


                            Vector vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA,"");
                            vecReg.add(INT_TBL_CODTRA,rstLoc.getString("co_tra"));
                            vecReg.add(INT_TBL_NOMTRA,rstLoc.getString("nomcom"));
                            vecReg.add(INT_TBL_DIAS_LAB,"");
                            vecReg.add(intPosCol,rstLoc.getDouble("nd_valrub"));
                            dblTotIng=dblTotIng+objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            intPosCol++;

                            while (rstLoc.next())
                            {
                                vecReg.add(intPosCol,rstLoc.getDouble("nd_valrub"));
                                if(rstLoc.getString("tx_tiprub").equals("I")){
                                    if(rstLoc.getInt("co_rub")==6){
                                        dblBono=rstLoc.getDouble("nd_valrub");
                                    }
                                    dblTotIng+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                }else if(rstLoc.getString("tx_tiprub").equals("E")){
                                    dblTotEgr+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                }
                                
                                intCont++;
                                intPosCol++;
                            }

                            vecReg.add(vecReg.size(),dblTotIng);
                            intPosCol++;
                            vecReg.add(vecReg.size(),dblTotEgr);
                            vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblTotIng+dblTotEgr), objZafParSis.getDecimalesMostrar()));
                            vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble((dblTotIng+dblTotEgr)-dblBono), objZafParSis.getDecimalesMostrar()));
                            vecReg.add(vecReg.size(),objUti.redondear("", objZafParSis.getDecimalesMostrar()));
                            vecReg.add(vecReg.size(),objUti.redondear("", objZafParSis.getDecimalesMostrar()));
                            vecDat.add(vecReg);
                        }
                        intPosCol=INT_TBL_DIAS_LAB+1;
                    }
                
                //Asignar vectores al modelo.

                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, evt);
        }finally {
        try{rst.close();}catch(Throwable ignore){}
        try{stm.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    

    private void mostrarEstado(String strStReg){
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if(strStReg.equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(strStReg.equals("E")){
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                }else{
                    setEstadoRegistro("");
                }
            }
        }


    public void clickModificar(){
    //  setEditable(true);
    //
    //  java.awt.Color colBack;
    //  colBack = txtValDoc.getBackground();
    //
    // this.setEnabledConsultar(false);
    //
    // objTblMod.setDataModelChanged(false);
    // blnHayCam=false;
        modificar();
    }


public boolean vistaPreliminar(){
    if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.setIndFunEje(1);
        objThrGUI.start();
    }
    return true;
}


public boolean imprimir(){
    if (objThrGUI==null)
    {

        objThrGUI=new ZafThreadGUI();
        objThrGUI.setIndFunEje(0);
        objThrGUI.start();
    }
    return true;
}


    public void clickImprimir(){
    }
    public void clickVisPreliminar(){
    }

    public void clickCancelar(){

    }

    public void cierraConnections(){

    }


    public boolean beforeAceptar() {
        return true;
    }
    public boolean beforeAnular() {
        return true;
    }
    public boolean beforeCancelar() {
        return true;
    }
    public boolean beforeConsultar() {
        return true;
    }
    public boolean beforeEliminar() {
        return true;
    }
    public boolean beforeImprimir() {

        return true;
    }
    public boolean beforeInsertar() {
        return true;
    }
    public boolean beforeModificar() {
        return true;
    }
    public boolean beforeVistaPreliminar() {

        return true;
    }
}

  private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
      public void mouseMoved(java.awt.event.MouseEvent evt) {
          int intCol=tblDat.columnAtPoint(evt.getPoint());
          String strMsg="";
          switch (intCol) {
              case INT_TBL_LINEA:
                  strMsg="";
                  break;
              case INT_TBL_CODTRA:
                  strMsg="Código del empleado";
                  break;
              case INT_TBL_NOMTRA:
                  strMsg="Nombres y apellidos del empleado";
                  break;
              case INT_TBL_DIAS_LAB:
                  strMsg="Días Laborados";
                  break;
              default:
                  strMsg="";
                  break;
          }
          tblDat.getTableHeader().setToolTipText(strMsg);
      }
  }
  
   /*
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
        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }

        /*
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

    /*
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
        String strRutRpt="", strNomRpt="", strFecHorSer = "", strCamAudRpt = "";
        String strNomEmp="", strPago="", strPeriodo="", strImpresión="";
        int i, intNumTotRpt;
        boolean blnRes=true;
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
                        
                        int intCodRep = Integer.parseInt(objRptSis.getCodigoReporte(i));
                        
                        int intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                        int intMes=cboPerMes.getSelectedIndex();
                        
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            default:

                            
                            case 433:
                                
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                                //Inicializar los parametros que se van a pasar al reporte.
                                
                                strNomEmp=objZafParSis.getNombreEmpresa();
                                strPago=cboPer.getSelectedItem().toString();
                                strPeriodo=cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();
                                
                                //Obtener la fecha y hora del servidor.
                                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                    datFecAux=null;
                                }
                                
                                strSQL="select a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_traemp a";
                                strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra) and a.co_emp="+objZafParSis.getCodigoEmpresa();
                                strSQL+=" where a.st_reg='A' order by empleado";
                                
                                break;
                                
                                
                            case 434:
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                                //Inicializar los parametros que se van a pasar al reporte.
                                
                                strNomEmp=objZafParSis.getNombreEmpresa();
                                strPago=cboPer.getSelectedItem().toString();
                                strPeriodo=cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();
                                
                                //Obtener la fecha y hora del servidor.
                                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                    datFecAux=null;
                                }
                                
                                strSQL="";
                                strSQL+="select a.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado";
                                strSQL+=" from tbm_ingegrmentra a ";
                                strSQL+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub) ";
                                strSQL+=" inner join tbm_tra c on (a.co_tra=c.co_tra) ";
                                strSQL+=" inner join tbm_traemp d on (c.co_tra=d.co_tra and d.co_emp=a.co_emp) ";
                                strSQL+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
                                strSQL+=" and a.ne_ani="+intAño;
                                strSQL+=" and a.ne_mes="+intMes;
                                strSQL+=" and a.ne_per=2 ";
                                strSQL+=" and a.co_rub in(6) and ";
                                strSQL+=" round((select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x ";
                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub) ";
                                strSQL+=" where x.co_tra = a.co_tra and x.ne_ani="+intAño;
                                strSQL+=" and x.ne_mes=a.ne_mes and x.ne_per=1 and x.co_rub=a.co_rub and x.co_emp=a.co_emp) +  ";
                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub) ";
                                strSQL+=" where x.co_tra = a.co_tra and x.ne_ani="+intAño;
                                strSQL+=" and x.ne_mes=a.ne_mes and x.ne_per=2 and x.co_rub=a.co_rub and x.co_emp=a.co_emp) , 2) is not null ";
                                strSQL+=" order by empleado";
                                
                                break;


                            case 435:
                                
                                
                                
                                
                                 strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                                //Inicializar los parametros que se van a pasar al reporte.
                                
                                strNomEmp=objZafParSis.getNombreEmpresa();
                                strPago=cboPer.getSelectedItem().toString();
                                strPeriodo=cboPerMes.getSelectedItem().toString() + " " + cboPerAAAA.getSelectedItem().toString();
                                
                                //Obtener la fecha y hora del servidor.
                                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                    datFecAux=null;
                                }
                                
                                
                                /*QUERY ROL DE MOVILIZACION*/
                                
////                                strSQL="";
////                                strSQL+="select a.co_tra, (c.tx_ape || ' '  || c.tx_nom) as empleado, b.tx_nom,";
////                                strSQL+=" round((select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_tra = a.co_tra";
////                                strSQL+=" and x.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and x.ne_mes=a.ne_mes";
////                                strSQL+=" and x.ne_per=1";
////                                strSQL+=" and x.co_rub=a.co_rub";
////                                strSQL+=" ),2) as nd_valmovpriqui,";
////
////
////                                strSQL+=" round((select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_tra = a.co_tra";
////                                strSQL+=" and x.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and x.ne_mes=a.ne_mes";
////                                strSQL+=" and x.ne_per=2";
////                                strSQL+=" and x.co_rub=a.co_rub";
////                                strSQL+=" ),2) as nd_valmovsegqui,";
////
////                                strSQL+=" round((select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_tra = a.co_tra";
////                                strSQL+=" and x.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and x.ne_mes=a.ne_mes";
////                                strSQL+=" and x.ne_per=1";
////                                strSQL+=" and x.co_rub=a.co_rub";
////                                strSQL+=" ) + ";
////
////                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_tra = a.co_tra";
////                                strSQL+=" and x.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and x.ne_mes=a.ne_mes";
////                                strSQL+=" and x.ne_per=2";
////                                strSQL+=" and x.co_rub=a.co_rub";
////                                strSQL+=" ) , 2) as nd_valmovtotal";
////
////                                
////                                strSQL+=" from tbm_ingegrmentra a";
////                                strSQL+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub)";
////                                strSQL+=" inner join tbm_tra c on (a.co_tra=c.co_tra)";
////                                strSQL+=" inner join tbm_traemp d on (c.co_tra=d.co_tra and d.co_emp=a.co_tra)";
////                                strSQL+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
////                                strSQL+=" and a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and a.ne_mes="+cboPerMes.getSelectedItem().toString();
////                                strSQL+=" and a.ne_per=2";
////                                strSQL+=" and a.co_rub in(7)";
////                                strSQL+=" and round((select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_tra = a.co_tra";
////                                strSQL+=" and x.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and x.ne_mes=a.ne_mes";
////                                strSQL+=" and x.ne_per=1";
////                                strSQL+=" and x.co_rub=a.co_rub";
////                                strSQL+=" ) + ";
////                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
////                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
////                                strSQL+=" where x.co_tra = a.co_tra";
////                                strSQL+=" and x.ne_ani="+cboPerAAAA.getSelectedItem().toString();
////                                strSQL+=" and x.ne_mes=a.ne_mes";
////                                strSQL+=" and x.ne_per=2";
////                                strSQL+=" and x.co_rub=a.co_rub";
////                                strSQL+=" ) , 2) is not null";
////                                strSQL+=" order by empleado,a.co_rub";
                                strSQL="";
                                strSQL+="select a.co_tra, (c.tx_ape || ' '  || c.tx_nom) as empleado";
                                strSQL+=" from tbm_ingegrmentra a";
                                strSQL+=" inner join tbm_rubrolpag b on (a.co_rub=b.co_rub)";
                                strSQL+=" inner join tbm_tra c on (a.co_tra=c.co_tra)";
                                strSQL+=" inner join tbm_traemp d on (c.co_tra=d.co_tra and d.co_emp=a.co_emp)";
                                strSQL+=" where a.co_emp="+objZafParSis.getCodigoEmpresa();
                                strSQL+=" and a.ne_ani="+intAño;
                                strSQL+=" and a.ne_mes="+intMes;
                                strSQL+=" and a.ne_per=2";
                                strSQL+=" and a.co_rub in(7)";
                                strSQL+=" and round(";
                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
                                strSQL+=" where x.co_tra = a.co_tra";
                                strSQL+=" and x.ne_ani="+intAño;
                                strSQL+=" and x.ne_mes=a.ne_mes";
                                strSQL+=" and x.ne_per=1";
                                strSQL+=" and x.co_rub=a.co_rub";
                                strSQL+=" and x.co_emp=a.co_emp";
                                strSQL+=" ) + ";
                                strSQL+=" (select sum(nd_valrub) as nd_valRubIng from tbm_ingegrmentra x";
                                strSQL+=" inner join tbm_rubrolpag y on (y.tx_tiprub='I' and x.co_rub=y.co_rub)";
                                strSQL+=" where x.co_tra = a.co_tra";
                                strSQL+=" and x.ne_ani="+intAño;
                                strSQL+=" and x.ne_mes=a.ne_mes";
                                strSQL+=" and x.ne_per=2";
                                strSQL+=" and x.co_rub=a.co_rub";
                                strSQL+=" and x.co_emp=a.co_emp";
                                strSQL+=" ) , 2) is not null";
                                strSQL+=" order by empleado";
                            
                                
                        }
                        
                        System.out.println("vista previa: " + strSQL);
                        java.util.Map mapPar=new java.util.HashMap();
                        mapPar.put("strsql", strSQL );
                        mapPar.put("co_emp", objZafParSis.getCodigoEmpresa() );
                        mapPar.put("ne_ani", intAño );
                        mapPar.put("ne_mes", intMes );
                        mapPar.put("empresa", strNomEmp );
                        mapPar.put("pago", strPago );
                        mapPar.put("periodo", strPeriodo );
                        mapPar.put("impresion", strFecHorSer );
                        mapPar.put("strCamAudRpt", strCamAudRpt);
                        mapPar.put("SUBREPORT_DIR", strRutRpt);

//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                        
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


    //public boolean cargarRolPer(boolean blnAct){
    public boolean cargarRolPer(){
        
        boolean blnRes=true;
        //java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="";

        try{
        
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                
                int intNePer=0;
                arrLstAntSue = new ArrayList<String>();
                arrLstAntBon = new ArrayList<String>();
                arrLstAntMov = new ArrayList<String>();
                Calendar calendario = new GregorianCalendar();
            
            
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmLoc=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
                strSQL="select case when min(ne_per) is null then -1 else min(ne_per) END as ne_pero from tbm_ingegrmentra where st_rolpaggen is null and ne_per not in (0)";
                strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
                System.out.println("sql ne_per : "+strSQL);
                rst=stm.executeQuery(strSQL);
            
                if(rst.next()){
                    //intNePer=rst.getInt("ne_pero");
                    intNePer=2;
                }
            
                /**/
                int intAño = 0;
                strSQL="select case when min(ne_ani) is null then 0 else min(ne_ani) END as ne_anio from tbm_ingegrmentra where st_rolpaggen is null";
                strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
                System.out.println("sql ne_ani : "+strSQL);
                rst=stm.executeQuery(strSQL);
            
                if(rst.next()){
                    intAño=rst.getInt("ne_anio");
                }
            
            /**/
            int intMes = 0;
            strSQL="select case when min(ne_mes) is null then 0 else min(ne_mes) END as ne_meso from tbm_ingegrmentra where st_rolpaggen is null";
            strSQL+=" and ne_per not in (0) and co_emp = " + objZafParSis.getCodigoEmpresa();
            System.out.println("sql ne_mes : "+strSQL);
            rst=stm.executeQuery(strSQL);
            
            if(rst.next()){
                intMes=rst.getInt("ne_meso");
            }
            
            intNePer=1;
            intMes=12;
            
            cboPerAAAA.setSelectedItem(String.valueOf(intAño));
            cboPerMes.setSelectedIndex(intMes);
            cboPer.setSelectedIndex(intNePer);
            
            strSQL="select distinct a.co_tra, b.tx_ide, (b.tx_ape || ' ' || b.tx_nom) as empleado, c.tx_numCtaBan, c.tx_tipCtaBan  from tbm_ingEgrMenTra a ";
            strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
            strSQL+=" inner join tbm_traemp c on (b.co_tra=c.co_tra  and a.co_emp=c.co_emp  and c.st_reg='A')";
            strSQL+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
            strSQL+=" and ne_ani = " + intAño;
            strSQL+=" and ne_mes = " + intMes;
            strSQL+=" and ne_per = " + intNePer;
            strSQL+=" order by empleado asc";
            
            System.out.println("sql cargar rol per : "+strSQL);
            rst=stm.executeQuery(strSQL);
            //Limpiar vector de datos.
            vecDat = new Vector();

            //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
            int intPosCol=INT_TBL_DIAS_LAB+1;
            int intCont=0;
                
            
            while(rst.next()){
                
                Tbm_traemp tbm_traemp = new Tbm_traemp();
                tbm_traemp.setIntCo_tra(rst.getInt("co_tra"));
                tbm_traemp.setStrTx_Ide(rst.getString("tx_ide"));
                tbm_traemp.setStrEmpleado(rst.getString("empleado"));
                tbm_traemp.setStrTx_TipCtaBan(rst.getString("tx_tipCtaBan"));
                tbm_traemp.setStrTx_NumCtaBan(rst.getString("tx_numCtaBan"));
                arrLstTbmTraEmp.add(tbm_traemp);
                
                strSQL="select a.*,(b.tx_ape || ' ' || b.tx_nom) as nomcom,c.tx_tiprub,c.co_grp from tbm_ingEgrMenTra a";
                strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub)";
                strSQL+=" where co_emp = " + objZafParSis.getCodigoEmpresa();
                strSQL+=" and a.co_tra = " + rst.getString("co_tra");
                strSQL+=" and ne_ani = " + intAño;
                strSQL+=" and ne_mes = " + intMes;
                strSQL+=" and ne_per = " + intNePer;
//                if(objZafParSis.getCodigoMenu()==3138){
//                    strSQL+=" and c.co_grp = 1";
//                }else{
//                    strSQL+=" and c.co_grp = 2";
//                }
                strSQL+=" order by c.tx_tiprub desc,a.co_rub";
                System.out.println("query obt rob rol pag : " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                
                double dblTotIng=0;
                double dblTotEgr=0;
                double dblTotEgrSue=0;
                double dblTotEgrBon=0;
                double dblTotEgrMov=0;
                double dblGI1=0;
                double dblGI2=0;
                double dblBon=0;
                double dblMov=0;
                
                if(rstLoc.next()){
                
                
                    Vector vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODTRA,rstLoc.getString("co_tra"));
                    vecReg.add(INT_TBL_NOMTRA,rstLoc.getString("nomcom"));
                    vecReg.add(INT_TBL_DIAS_LAB,"");
                    vecReg.add(intPosCol,rstLoc.getDouble("nd_valrub"));
                    dblTotIng+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                    dblGI1+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                    intPosCol++;
                    
                    while (rstLoc.next())
                    {
                        vecReg.add(intPosCol,rstLoc.getDouble("nd_valrub"));
                        if(rstLoc.getString("tx_tiprub").equals("I")){
                            
                            if(rstLoc.getInt("co_rub")==6){
                                dblBon+=rstLoc.getDouble("nd_valrub");
                                arrLstAntBon.add(String.valueOf(objUti.redondear(objUti.parseDouble(dblBon), objZafParSis.getDecimalesMostrar())));
                            }
                            if(rstLoc.getInt("co_rub")==7){
                                dblMov+=rstLoc.getDouble("nd_valrub");
                                arrLstAntMov.add(String.valueOf(objUti.redondear(objUti.parseDouble(dblMov), objZafParSis.getDecimalesMostrar())));
                            }
                            
                            if(rstLoc.getInt("co_grp")==1){
                                dblGI1+=rstLoc.getDouble("nd_valrub");
                            }else if (rstLoc.getInt("co_grp")==2){
                                dblGI2+=rstLoc.getDouble("nd_valrub");
                            }

                            dblTotIng+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            
                        }else if(rstLoc.getString("tx_tiprub").equals("E")){

                            //acumula el total de egresos para obtener la diferencia que sería el valor a recibir
                            dblTotEgr+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                            System.out.println(rstLoc.getString("co_rub"));
                            
                            if(rstLoc.getString("co_rub").compareTo("25")==0){
                                dblTotEgrBon+= objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                dblTotEgr-=dblTotEgrBon;
                            }
                            if(rstLoc.getString("co_rub").compareTo("26")==0){
                                dblTotEgrMov+= objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                dblTotEgr-=dblTotEgrMov;
                            }
                        }

                        intCont++;
                        intPosCol++;
                    }
                
                    vecReg.add(vecReg.size(),dblTotIng);
                    intPosCol++;
                    vecReg.add(vecReg.size(),dblTotEgr);
                    vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblTotIng+dblTotEgr+dblTotEgrBon+dblTotEgrMov), objZafParSis.getDecimalesMostrar()));//Diferencia
//                    vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblGI1)+dblTotEgr, objZafParSis.getDecimalesMostrar()));//Arch. Sueldos
//                    vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblGI2)+dblTotEgrBon+dblTotEgrMov, objZafParSis.getDecimalesMostrar()));//Arch. Bono+Movilizacion
                    
                    System.out.println(vecReg.size());
                    if(vecReg.size()!=30){
                        System.out.println(vecReg.size());
                    }
                    //if((dblTotIng+dblTotEgr+dblTotEgrBon+dblTotEgrMov)>0){
                        vecDat.add(vecReg);
                        arrLstAntSue.add(String.valueOf(objUti.redondear(objUti.parseDouble(dblGI1)+dblTotEgr, objZafParSis.getDecimalesMostrar())));
                    //}
                }
                intPosCol=INT_TBL_DIAS_LAB+1;
            }
            
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            objTblTot.calcularTotales();
            calcularValorDocumento();
            vecDat.clear();
            }
    }catch(java.lang.ArrayIndexOutOfBoundsException Evt){
        objUti.mostrarMsgErr_F1(this, Evt);
        Evt.printStackTrace();
        blnRes = false;
    }catch (java.sql.SQLException Evt) {
        objUti.mostrarMsgErr_F1(this, Evt);
        Evt.printStackTrace();
        blnRes = false;
    } catch (Exception Evt) {
        objUti.mostrarMsgErr_F1(this, Evt);
        Evt.printStackTrace();
        blnRes = false;
    }finally {
        try{rst.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stm.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
        
    }
    return blnRes;
    }
    
    private void calcularValorDocumento(){
        
        double dblTot=objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-1)), objZafParSis.getDecimalesMostrar());
        txtValDoc.setText(String.valueOf(dblTot));
    }
    
public static java.sql.Time SparseToTime (String hora) throws Exception{
        int h, m, s=0;
        h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
        m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
        return (new java.sql.Time(h,m,s));
    }

    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
     private boolean generaAsiento(){
        
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        
        BigDecimal bgdDebe, bgdHaber;
        int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE   = 5; //5) Debe. 
        int INT_VEC_HABER  = 6; //6) Haber. 
        int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO    = 8;
        
        int intColFij=INT_TBL_DIAS_LAB;
        int intColDin=INT_TBL_DIAS_LAB;
        
        try
        {
        
            objAsiDia.inicializar();
            
            if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
             else vecDetDiario.removeAllElements();
            
            
            java.util.Vector vecReg = new Vector();
            
            String strMes=cboPerMes.getSelectedItem().toString().toUpperCase();
            if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                if(objZafParSis.getCodigoMenu()==3138){
                    objAsiDia.setGlosa("ROL DE PAGOS - 1ERA QUINCENA "+strMes+"/2012");
                }else{
                    objAsiDia.setGlosa("ROL DE BONOS Y MOVILIZACIÓN - 1ERA QUINCENA "+strMes+"/2012");
                }
                
                mostrarCtaPre(1);
            }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
                if(objZafParSis.getCodigoMenu()==3138){
                    objAsiDia.setGlosa("ROL DE PAGOS - 2DA QUINCENA "+strMes+"/2012");
                }else{
                    objAsiDia.setGlosa("ROL DE BONOS Y MOVILIZACIÓN - 1ERA QUINCENA "+strMes+"/2012");
                }
                
                mostrarCtaPre(2);
            }
            
            
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
            
                stm=con.createStatement();
                stmLoc=con.createStatement();

                if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                    
                    /*segmentar personas que se realizan anticipos con cheque..*/
                    //int intPer=cboPer.getSelectedIndex();
                    
                    String strCoCtaDeb="";
                    String strNumCtaDeb="";
                    String strDesLarCtaDeb="";
                    
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctadeb";
                    strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a2.st_reg='A'";
                    rst=stm.executeQuery(strSQL);
                    
                    if(rst.next()){
                        strCoCtaDeb=rst.getString("co_cta");
                        strNumCtaDeb=rst.getString("tx_codcta");
                        strDesLarCtaDeb=rst.getString("tx_deslar");
                    }
                    
                    String strSql="";
                    BigDecimal bgdTotNInc=BigDecimal.ZERO;//controlar valores que no incluyen en bancos
                    BigDecimal bgddblTot=BigDecimal.ZERO;
                    
                    for(int intFil=0; intFil<tblDat.getRowCount(); intFil++){

                        bgdTotNInc=BigDecimal.ZERO;
                        strSql="";
                        strSql+="select tx_numctaban from tbm_traemp";
                        strSql+=" where co_tra="+tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and co_emp="+objZafParSis.getCodigoEmpresa();
                        strSql+=" and tx_numctaban is null";
                        System.out.println("comp: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if(rstLoc.next()){
                            if(objZafParSis.getCodigoMenu()==3138){
                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                            }else{
                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                            }
                            
                            bgddblTot=bgddblTot.add(bgdTotNInc);

                            vecReg = new Vector();
                            vecReg.add(INT_LINEA, null);
                            vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
                            vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
                            vecReg.add(INT_VEC_BOTON, null);
                            vecReg.add(INT_VEC_NOMCTA,  strDesLarCtaDeb);

                            vecReg.add(INT_VEC_DEBE, bgdTotNInc);
                            vecReg.add(INT_VEC_HABER, new BigDecimal(0));
                            System.err.println(tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString());
                            vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada" );
                            vecReg.add(INT_VEC_NUEVO, null);
                            vecDetDiario.add(vecReg);

                        }
                    }
                    
                    //cuenta debe.. anticipos.. diferencia de las personas que si tienen cuenta bancaria...
                    
                    BigDecimal bgdValDoc= new  BigDecimal(txtValDoc.getText());
                    bgdValDoc=bgdValDoc.subtract(bgddblTot);

                    vecReg=new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
                    vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA,  strDesLarCtaDeb);

                    vecReg.add(INT_VEC_DEBE, bgdValDoc);
                    vecReg.add(INT_VEC_HABER, new BigDecimal(0));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);
                    
                    //cuenta bancos...haber
                    
                    vecReg=new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, txtCodCta.getText());
                    vecReg.add(INT_VEC_NUMCTA, txtDesCorCta.getText());
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA,  txtDesLarCta.getText());

                    vecReg.add(INT_VEC_DEBE, new Double(0));
                    //vecReg.add(INT_VEC_HABER, txtValDoc.getText());objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-5)), objZafParSis.getDecimalesMostrar());
                    vecReg.add(INT_VEC_HABER, txtValDoc.getText());
                    //objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-5)), objZafParSis.getDecimalesMostrar());
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);

                    objAsiDia.setDetalleDiario(vecDetDiario);
                }

                else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){


                        for(Iterator<String> itLstRub = arrLst.iterator();itLstRub.hasNext();){

                            String strCodRub = itLstRub.next();
                            intColDin=intColDin+1;

                            String strSql="";
                            strSql+="select distinct a.co_cta,b.tx_deslar,b.tx_codcta,b.co_emp,c.tx_tiprub from tbm_suetra a ";
                            strSql+=" inner join tbm_placta b on (a.co_emp=b.co_emp and a.co_cta=b.co_cta)";
                            strSql+=" inner join tbm_rubrolpag c on (c.co_rub="+strCodRub+")";
                            strSql+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSql+=" and a.co_rub = " + strCodRub;
                            strSql+=" and a.co_cta is not null";
                            strSql+=" order by tx_codcta asc";
                            System.out.println("asiento diario ctas rub "+ strCodRub + "   ---->  " + strSql);
                            rst=stm.executeQuery(strSql);

                            while (rst.next()){


                                bgdDebe=BigDecimal.ZERO;
                                vecReg = new Vector();
                                vecReg.add(INT_LINEA, null);
                                vecReg.add(INT_VEC_CODCTA, rst.getString("co_cta"));
                                vecReg.add(INT_VEC_NUMCTA, rst.getString("tx_codcta"));
                                vecReg.add(INT_VEC_BOTON, null);
                                vecReg.add(INT_VEC_NOMCTA,  rst.getString("tx_deslar"));

                                int intFil=0;
                                for(intFil=0; intFil<tblDat.getRowCount(); intFil++){


                                    strSql="select * from tbm_suetra where co_emp = " + rst.getString("co_emp");
                                    strSql+=" and co_rub = " + strCodRub;
                                    strSql+=" and co_cta = " + rst.getString("co_cta");
                                    strSql+=" and co_tra = " + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                                    System.out.println("comprob si cod_tra: " + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString() + " pertenece a la cta : " + rst.getString("co_cta") + "   --------->  "+strSql);
                                    rstLoc = stmLoc.executeQuery(strSql);

                                    if(rstLoc.next()){
                                        bgdDebe=bgdDebe.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, intColDin).toString(), objZafParSis.getDecimalesMostrar()));
                                    }
                                }

                                System.out.println("intFil: " + intFil);

                                if(bgdDebe.signum()==1){
                                    vecReg.add(INT_VEC_DEBE, bgdDebe);
                                    vecReg.add(INT_VEC_HABER, new Double(0));
                                    vecReg.add(INT_VEC_REF, null);
                                    vecReg.add(INT_VEC_NUEVO, null);
                                    vecDetDiario.add(vecReg);
                                }else if (bgdDebe.signum()==-1){
                                    vecReg.add(INT_VEC_DEBE, new Double(0));
                                    vecReg.add(INT_VEC_HABER, bgdDebe.multiply(BigDecimal.valueOf(-1)));
                                    vecReg.add(INT_VEC_REF, null);
                                    vecReg.add(INT_VEC_NUEVO, null);
                                    vecDetDiario.add(vecReg);
                                }
                            }
                        }

                        String strSql="";
                        BigDecimal bgdTotNInc=BigDecimal.ZERO;//controlar valores que no incluyen en bancos
                        BigDecimal bgddblTot=BigDecimal.ZERO;

                        for(int intFil=0; intFil<tblDat.getRowCount(); intFil++){

                            bgdTotNInc=BigDecimal.ZERO;
                            strSql="";
                            strSql+="select tx_numctaban from tbm_traemp";
                            strSql+=" where co_tra="+tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                            strSql+=" and co_emp="+objZafParSis.getCodigoEmpresa();
                            strSql+=" and tx_numctaban is null";
                            System.out.println("comp: " + strSql);
                            rstLoc = stmLoc.executeQuery(strSql);

                            if(rstLoc.next()){

                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, 30).toString(), objZafParSis.getDecimalesMostrar()));
                                bgddblTot=bgddblTot.add(bgdTotNInc);

                                vecReg = new Vector();
                                vecReg.add(INT_LINEA, null);
                                vecReg.add(INT_VEC_CODCTA, txtCodCta.getText());
                                vecReg.add(INT_VEC_NUMCTA, txtDesCorCta.getText());
                                vecReg.add(INT_VEC_BOTON, null);
                                vecReg.add(INT_VEC_NOMCTA,  txtDesLarCta.getText());

                                vecReg.add(INT_VEC_DEBE, new Double(0));
                                vecReg.add(INT_VEC_HABER, bgdTotNInc);
                                vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada" );
                                vecReg.add(INT_VEC_NUEVO, null);
                                vecDetDiario.add(vecReg);
                            }
                        }


                        //BigDecimal bgdValDoc= new  BigDecimal(txtValDoc.getText());
                        //bgdValDoc=bgdValDoc.subtract(bgddblTot);

                        vecReg=new Vector();
                        vecReg.add(INT_LINEA, null);
                        vecReg.add(INT_VEC_CODCTA, txtCodCta.getText());
                        vecReg.add(INT_VEC_NUMCTA, txtDesCorCta.getText());
                        vecReg.add(INT_VEC_BOTON, null);
                        vecReg.add(INT_VEC_NOMCTA,  txtDesLarCta.getText());

                        vecReg.add(INT_VEC_DEBE, new Double(0));
                        vecReg.add(INT_VEC_HABER, txtValDoc.getText());
                        //vecReg.add(INT_VEC_HABER, objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-3)), objZafParSis.getDecimalesMostrar()));
                        vecReg.add(INT_VEC_REF, null);
                        vecReg.add(INT_VEC_NUEVO, null);
                        vecDetDiario.add(vecReg);

                        objAsiDia.setDetalleDiario(vecDetDiario);
                
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
            blnRes=false;
//            System.exit(0);
        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//            e.printStackTrace();
//            System.exit(0);
//        }
        
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
            blnRes=false;
//            System.exit(0);
            
        }finally {
            try{rst.close();rst=null;}catch(Throwable ignore){}
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        return blnRes;
      
    }
      private boolean mostrarCtaPre(int intPer)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                switch (intPer){
                    case 1:
                        
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                        strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctahab";
                        strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                        strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                        strSQL+=" AND a2.st_reg='A'";

                        
                        break;
                        
                    case 2:
                                               
                        
                        strSQL="";
                        strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                        strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                        strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                        strSQL+=" AND a2.st_reg='S'";
                        
                }
                
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtDesLarCta.setText(rst.getString("tx_desLar"));
                    switch (intPer){
                        case 2:
                            strUbiCta=rst.getString("tx_ubiCta");
                            break;
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