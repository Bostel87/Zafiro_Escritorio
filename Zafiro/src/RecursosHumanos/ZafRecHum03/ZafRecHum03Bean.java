//
package RecursosHumanos.ZafRecHum03;
import Librerias.ZafMae.ZafMaeBo.Tbm_ciuBO;
import Librerias.ZafMae.ZafMaeBo.Tbm_docdigsisBO;
import Librerias.ZafMae.ZafMaeBo.Tbm_varBO;
import Librerias.ZafMae.ZafMaePoj.Tbm_ciu;
import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;
import Librerias.ZafMae.ZafMaePoj.Tbm_var;
import Librerias.ZafMae.ZafMaeVen.ZafVenCiu;
import Librerias.ZafMae.ZafMaeVen.ZafVenEstCiv;
import Librerias.ZafMae.ZafMaeVen.ZafVenTipDocDig;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafUtil.ZafFilUti;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_hortraBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_tipfamconBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_traBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_traempBO;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafRecHumPoj.DetDocDig;
import Librerias.ZafRecHum.ZafRecHumPoj.DetFamCon;
import Librerias.ZafRecHum.ZafRecHumPoj.TbhSuetra;
import Librerias.ZafRecHum.ZafRecHumPoj.TbmSuetra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_contra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_docdigtra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipfamcon;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTipFamCon;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.Date;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import Librerias.ZafValCedRuc.VerificarId;
import Librerias.ZafValCedRuc.TuvalUtilitiesException;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafRecHum.ZafVenFun.ZafVenFun;  
import RecursosHumanos.ZafRecHum20.ZafRecHum20;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum03.
 * Envia datos al BO(Business Object)
 * @author Carlos Lainez
 * Guayaquil 01/04/2011
 */
public class ZafRecHum03Bean {
    private ZafParSis objParSis;                                //Parámetros del Sistema
    private ZafUtil objUti;                                     //Utilidades
    private ZafNotCorEle objNotCorEle;                          //Libreria para Notificaciones de Correo. 
    private Tbm_tra tbm_tra;                                    //Pojo de Empleados
    private Tbm_tra tbm_trapar;                                 //Parametros de Consulta para Empleados
    private List<Tbm_contra> lisTbm_contra;                     //Lista de Contactos del Empleado
    private List<Tbm_contra> lisTbm_contrabk;                   //Lista de Contactos del Empleado para verificar cambios posteriores
    private List<Tbm_docdigtra> lisTbm_docdigtra;               //Lista de Documentos Digitales
    private List<Tbm_docdigtra> lisTbm_docdigtrabk;             //Lista de Documentos Digitales para verificar cambios posteriores
    private List<DetFamCon> lisDetFamCon;                       //Lista de Contactos del Empleado
    private List<DetDocDig> lisDetDocDig;                       //Lista de Documentos Digitales
    private ZafRecHum03 objRecHum03;                            //JInternalFrame manejado
    private Tbm_traBO objRecHum03BO;                            //Conección a la Base de Datos
    private Tbm_hortraBO tbm_hortraBO;                          //Conección a la Base de Datos
    private Tbm_traempBO tbr_traempBO;                          //Conección a la Base de Datos
    private Tbm_ciuBO tbm_ciuBO;                                //Conección a la Base de Datos
    private Tbm_varBO tbm_varBO;                                //Conección a la Base de Datos
    private Tbm_tipfamconBO tbm_tipfamconBO;                    //Conección a la Base de Datos
    private List<Tbm_tra> lisTbm_tra;                           //Lista con datos de consulta general
    private MiToolBar objTooBar;                                //Barra de herramientas
    private VerificarId verId;                                  //Verifica cédula correcta según dígito verificador
    private ZafVenTra vcoTra;                                   //Ventana de consulta de Empleados.
    private ZafVenCiu vcoCiu;                                   //Ventana de consulta de Ciudades.
    private ZafVenEstCiv vcoEstCiv;                             //Ventana de consulta de Estado Civil.
    private ZafVenTipFamCon vcoTipFamCon;                       //Ventana de consulta de Tipo de Familiares y Contactos.
    private ZafVenTipDocDig vcoTipDocDig;                       //Ventana de consulta de Tipo de Documentos Digitales
    private ZafDocLis objDocLis;                                //Listener que indica si han habido cambios en el documento
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.    
    private Tbm_ciu tbm_ciu;                                    //Pojo con datos de la ciudad de nacimiento
    private Tbm_var tbm_var;                                    //Pojo con datos del Estado Civil
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento

    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private String strNomPan;                                   //Nombre de la pantalla
    private String strNumCta;                                   //Número de cuenta.
    private int intCodCtaPad;                                   //Código de la cuenta padre.
    private int intCodCta;
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta    
    
    private String strCodSis;
    
    public ZafRecHum03Bean(ZafRecHum03 objRecHum03, ZafParSis zafParSis, String strNomPan) {
        try {
            
            this.objRecHum03 = objRecHum03;
            this.objParSis = (ZafParSis) zafParSis.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            this.strNomPan = strNomPan;
            tbm_tra = new Tbm_tra();
            tbm_trapar = new Tbm_tra();
            objRecHum03BO = new Tbm_traBO(objParSis);
            tbm_hortraBO = new Tbm_hortraBO(objParSis);
            tbr_traempBO = new Tbm_traempBO(objParSis);
            tbm_ciuBO = new Tbm_ciuBO(objParSis);
            tbm_varBO = new Tbm_varBO(objParSis);
            tbm_tipfamconBO = new Tbm_tipfamconBO(objParSis);
            objUti = new ZafUtil();
            lisTbm_tra = null;
            intPagAct = 0;
            intPagFin = 0;
            vcoTra = new ZafVenTra(JOptionPane.getFrameForComponent(objRecHum03), objParSis, "Listado de Empleados");
            vcoCiu = new ZafVenCiu(JOptionPane.getFrameForComponent(objRecHum03), objParSis, "Listado de Ciudades");
            vcoEstCiv = new ZafVenEstCiv(JOptionPane.getFrameForComponent(objRecHum03), objParSis, "Listado de Estado Civil");
            vcoTipFamCon = new ZafVenTipFamCon(JOptionPane.getFrameForComponent(objRecHum03), objParSis, "Listado de Tipo de Familiares y Contactos");
            vcoTipDocDig = new ZafVenTipDocDig(JOptionPane.getFrameForComponent(objRecHum03), objParSis, "Listado de Tipos de Documentos Digitales");
            verId = new VerificarId();
            tbm_ciu = new Tbm_ciu();
            tbm_var = new Tbm_var();
            blnMod = false;
            blnActChkMod = false;
            objDocLis = new ZafDocLis();
            objNotCorEle = new ZafNotCorEle(objParSis);                
        } 
        catch (Exception ex) {
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }
    }

    /**
     * @param blnMod the blnMod to set
     */
    public void setBlnMod(boolean blnMod){
        this.blnMod  = blnMod;
    }

    /**
     * @return the objParSis
     */
    public ZafParSis getZafParSis() {
        return objParSis;
    }

    /**
     * @param zafParSis the objParSis to set
     */
    public void setZafParSis(ZafParSis zafParSis) {
        this.objParSis = zafParSis;
    }

    /**
     * Inicializa la barra de herramientas
     * @param objRecHum03
     */
    public void initTooBar(ZafRecHum03 objRecHum03) {
        objTooBar = new MiToolBar(objRecHum03);
    }

    /**
     * @return the objTooBar
     */
    public MiToolBar getTooBar() {
        return objTooBar;
    }

    /**
     * @return the objDocLis
     */
    public ZafDocLis getDocLis() {
        return objDocLis;
    }

    public ZafTableAdapterButVcoRel getZafTableAdapterButVcoRel(){
        return new ZafTableAdapterButVcoRel();
    }

    public ZafTableAdapterButVcoTipDoc getZafTableAdapterButVcoTipDoc(){
        return new ZafTableAdapterButVcoTipDoc();
    }

    public ZafTableAdapterButFilCho getZafTableAdapterButFilCho(){
        return new ZafTableAdapterButFilCho();
    }

    public ZafTableAdapterButVerDoc getZafTableAdapterButVerDoc(){
        return new ZafTableAdapterButVerDoc();
    }

    public ZafTableAdapterCelEdiTxt getZafTableAdapterCelEdiTxt(){
        return new ZafTableAdapterCelEdiTxt();
    }

    public ZafTblCelRenAdapterBut getZafTblCelRenAdapterBut(){
        return new ZafTblCelRenAdapterBut();
    }

    /**
     * @return the verId
     */
    public VerificarId getVerId() {
        return verId;
    }

    /**
     * @return the vcoTipFamCon
     */
    public ZafVenTipFamCon getZafVenTipFamCon() {
        return vcoTipFamCon;
    }

    /**
     * @return the vcoTipDocDig
     */
    public ZafVenTipDocDig getZafVenTipDocDig() {
        return vcoTipDocDig;
    }

    /**
     * @return the objPerUsr
     */
    public ZafPerUsr getZafPerUsr() {
        return objPerUsr;
    }

    /**
     * @param objPerUsr the objPerUsr to set
     */
    public void setZafPerUsr(ZafPerUsr objPerUsr) {
        this.objPerUsr = objPerUsr;
    }

    /**
     * Muestra un panel de consulta para cerrar la ventana
     * @return Retorna true si se desea cerrar o false caso contrario
     */
    public boolean cerrarIntFra(){
        boolean blnOk = true;
        if(blnMod){
            String strMsg = "<HTML>Existen cambios sin guardar!<BR>Está Seguro que desea cerrar este programa?</HTML>";
            String strTit = "Mensaje del sistema Zafiro";
            blnOk = (JOptionPane.showConfirmDialog(objRecHum03,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        }
        return blnOk;
    }
    
    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamHorTra(){
        boolean blnOk = true;
        String strNomCam = "Horario de Trabajo";
        blnOk = false;
        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">"+strNomCam+"</FONT> es obligatorio.<BR>Ingrese la información solicitada.</HTML>");
        return blnOk;
    }

    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
        String strNomCam = null;
        boolean blnOk = true;

        if(objRecHum03.txtIde.getText().length()==0){
            objRecHum03.txtIde.requestFocus();
            objRecHum03.txtIde.selectAll();
            strNomCam = "Identificación";
        }else{
            if(objRecHum03.txtNomTra.getText().length()==0){
                objRecHum03.txtNomTra.requestFocus();
                objRecHum03.txtNomTra.selectAll();
                strNomCam = "Nombres";
            }else if(objRecHum03.txtApeTra.getText().length()==0){
                objRecHum03.txtApeTra.requestFocus();
                objRecHum03.txtApeTra.selectAll();
                strNomCam = "Apellidos";
            }else if(objRecHum03.txtCodDep.getText().length()==0){
                objRecHum03.txtNomDep.requestFocus();
                objRecHum03.txtNomDep.selectAll();
                strNomCam = "Departamento";
            }else if(objRecHum03.txtCodOfi.getText().length()==0){
                objRecHum03.txtNomOfi.requestFocus();
                objRecHum03.txtNomOfi.selectAll();
                strNomCam = "Oficina";
            }else if(objRecHum03.txtCodCarLab.getText().length()==0){
                objRecHum03.txtCodCarLab.requestFocus();
                objRecHum03.txtNomCarLab.selectAll();
                strNomCam = "Cargo Laboral";
            }else if(objRecHum03.jCmbPla.getSelectedIndex()==0){
                objRecHum03.jCmbPla.requestFocus();
                strNomCam = "Plantilla";
            }
        }

        if(strNomCam!=null){
            blnOk = false;
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">"+strNomCam+"</FONT> es obligatorio.<BR>Ingrese la información solicitada.</HTML>");
            if(strNomCam.equals("Departamento") || strNomCam.equals("Plantilla")){
                objRecHum03.tabGen.setSelectedIndex(3);
            }
        }

        return blnOk;
    }

    public void consultarCiudad(int intCodCiu) throws Exception{
        objRecHum03.txtNomCiu.setText(tbm_ciuBO.consultarCiudad(intCodCiu));
    }

    public void consultarEstadoCivil(int intEstCiv) throws Exception{
        objRecHum03.txtNomEstCiv.setText(tbm_varBO.consultarEstadoCivil(intEstCiv));
    }

    public void consultarEstadoCivil(String strEstCiv) throws Exception{
        String strRes = tbm_varBO.consultarEstadoCivil(strEstCiv);
        if(strRes!=null){
            String[] strArrRes  = strRes.split(";");
            if(strArrRes!=null){
                objRecHum03.txtCodEstCiv.setText(strArrRes[0]);
                objRecHum03.txtNomEstCiv.setText(strArrRes[1]);
            }
        }
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_tra.getIntCo_tra()>0){
            objRecHum03.txtCodTra.setText(String.valueOf(tbm_tra.getIntCo_tra()));
        }else{
            objRecHum03.txtCodTra.setText("");
        }
        objRecHum03.txtIde.setText(tbm_tra.getStrTx_ide());
        objRecHum03.txtNomTra.setText(tbm_tra.getStrTx_nom());
        objRecHum03.txtApeTra.setText(tbm_tra.getStrTx_ape());
        objRecHum03.txtDir.setText(tbm_tra.getStrTx_dir());
        objRecHum03.txtRefDir.setText(tbm_tra.getStrTx_refdir());
        objRecHum03.txtTel1.setText(tbm_tra.getStrTx_tel1());
        objRecHum03.txtTel2.setText(tbm_tra.getStrTx_tel2());
        objRecHum03.txtTel3.setText(tbm_tra.getStrTx_tel3());
        objRecHum03.txtEma.setText(tbm_tra.getStrTx_corele());
        objRecHum03.bgrSex.clearSelection();
        objRecHum03.optSexMas.setSelected((tbm_tra.getStrTx_sex()!=null && tbm_tra.getStrTx_sex().equals("M")));
        objRecHum03.optSexFem.setSelected((tbm_tra.getStrTx_sex()!=null && tbm_tra.getStrTx_sex().equals("F")));
        if(tbm_tra.getDatFe_nac()!=null){
            Calendar calFecNac = Calendar.getInstance();
            calFecNac.setTime(tbm_tra.getDatFe_nac());
            objRecHum03.dtpFecNac.setText(calFecNac.get(Calendar.DAY_OF_MONTH), calFecNac.get(Calendar.MONTH)+1, calFecNac.get(Calendar.YEAR));
        }else{
            objRecHum03.dtpFecNac.setText(null);
        }
        if(tbm_tra.getIntCo_ciunac()>0){
            objRecHum03.txtCodCiu.setText(String.valueOf(tbm_tra.getIntCo_ciunac()));
            objRecHum03.txtNomCiu.setText(tbm_ciu.getStrTx_deslar());
        }else{
            objRecHum03.txtCodCiu.setText("");
            objRecHum03.txtNomCiu.setText("");
        }
        if(tbm_tra.getIntCo_estciv()>0){
            objRecHum03.txtCodEstCiv.setText(String.valueOf(tbm_tra.getIntCo_estciv()));
            objRecHum03.txtNomEstCiv.setText(tbm_var.getStrTx_deslar());
        }else{
            objRecHum03.txtCodEstCiv.setText("");
            objRecHum03.txtNomEstCiv.setText("");
        }
        if(tbm_tra.getIntNe_numhij()>0){
            objRecHum03.txtNumHij.setText(String.valueOf(tbm_tra.getIntNe_numhij()));
        }else{
            objRecHum03.txtNumHij.setText("0");
        }
        
        objRecHum03.txtNumCta.setText(tbm_tra.getStrTx_NumCtaBan());
        
        if(tbm_tra.getStrTx_TipCtaBan()!=null){
            if(tbm_tra.getStrTx_TipCtaBan().equals("A")){
                objRecHum03.jCmbTipCta.setSelectedIndex(1);
            }else if(tbm_tra.getStrTx_TipCtaBan().equals("C")){
                objRecHum03.jCmbTipCta.setSelectedIndex(2);
            }
        }else{
            objRecHum03.jCmbTipCta.setSelectedIndex(0);
        }
        
        if(tbm_tra.getIntCoDep()>0){
            objRecHum03.txtCodDep.setText(String.valueOf(tbm_tra.getIntCoDep()));
            objRecHum03.asignarDepartamento(0);
        }else{
            objRecHum03.txtCodDep.setText("");
            objRecHum03.txtNomDep.setText("");
        }
        
        if(tbm_tra.getIntCoCarLab()>0){
            objRecHum03.txtCodCarLab.setText(String.valueOf(tbm_tra.getIntCoCarLab()));
            objRecHum03.asignarCargoLaboral(0);
        }else{
            objRecHum03.txtCodCarLab.setText("");
            objRecHum03.txtNomCarLab.setText("");
        }
        
        if(tbm_tra.getIntCoOfi()>0){
            objRecHum03.txtCodOfi.setText(String.valueOf(tbm_tra.getIntCoOfi()));
            objRecHum03.asignarOficina(0);
        }else{
            objRecHum03.txtCodOfi.setText("");
            objRecHum03.txtNomOfi.setText("");
        }
        
        if(tbm_tra.getIntCoPla()>0){
            objRecHum03.jCmbPla.setSelectedIndex(tbm_tra.getIntCoPla());
        }else{
            objRecHum03.jCmbPla.setSelectedIndex(0);
        }
        
        if(tbm_tra.getDatFe_IniCon()!=null){
            Calendar calFecIniCon = Calendar.getInstance();
            calFecIniCon.setTime(tbm_tra.getDatFe_IniCon());
            objRecHum03.dtpFecIniCon.setText(calFecIniCon.get(Calendar.DAY_OF_MONTH), calFecIniCon.get(Calendar.MONTH)+1, calFecIniCon.get(Calendar.YEAR));
        }else{
            objRecHum03.dtpFecIniCon.setText(null);
        }
        
        if(tbm_tra.getDatFe_FinPerPru()!=null){
            Calendar calFecFinPerPru = Calendar.getInstance();
            calFecFinPerPru.setTime(tbm_tra.getDatFe_FinPerPru());
            objRecHum03.dtpFecFinPerPru.setText(calFecFinPerPru.get(Calendar.DAY_OF_MONTH),  calFecFinPerPru.get(Calendar.MONTH)+1, calFecFinPerPru.get(Calendar.YEAR));
        }else{
            objRecHum03.dtpFecFinPerPru.setText(null);
        }
        
        if(tbm_tra.getDatFe_FinCon()!=null){
            Calendar calFecFinCon = Calendar.getInstance();
            calFecFinCon.setTime(tbm_tra.getDatFe_FinCon());
            objRecHum03.dtpFecFinCon.setText(calFecFinCon.get(Calendar.DAY_OF_MONTH), calFecFinCon.get(Calendar.MONTH)+1,  calFecFinCon.get(Calendar.YEAR));
        }else{
            objRecHum03.dtpFecFinCon.setText(null);
        }
        
        if(tbm_tra.getDatFe_FinReaCon()!=null){
            Calendar calFecFinReaCon = Calendar.getInstance();
            calFecFinReaCon.setTime(tbm_tra.getDatFe_FinReaCon());
            objRecHum03.dtpFecReaFinCon.setText(calFecFinReaCon.get(Calendar.DAY_OF_MONTH), calFecFinReaCon.get(Calendar.MONTH)+1,  calFecFinReaCon.get(Calendar.YEAR));
        }else{
            objRecHum03.dtpFecReaFinCon.setText(null);
        }
        
        if(tbm_tra.getStrSt_perprucon()==null){
            objRecHum03.jCmbPla.setSelectedIndex(0);
        }else if (tbm_tra.getStrSt_perprucon().compareTo("S")==0){
            objRecHum03.jCmbPla.setSelectedIndex(1);
        }else if (tbm_tra.getStrSt_perprucon().compareTo("N")==0){
            objRecHum03.jCmbPla.setSelectedIndex(2);
        }
        
        if(tbm_tra.getStrSt_fincon()==null){
            objRecHum03.jCmbEstFinCon.setSelectedIndex(0);
        }else if (tbm_tra.getStrSt_perprucon().compareTo("S")==0){
            objRecHum03.jCmbEstFinCon.setSelectedIndex(1);
        }else if (tbm_tra.getStrSt_perprucon().compareTo("N")==0){
            objRecHum03.jCmbEstFinCon.setSelectedIndex(2);
        }
        
        objRecHum03.txaObs1.setText(tbm_tra.getStrTx_obs1());
        objRecHum03.txaObs2.setText(tbm_tra.getStrTx_obs2());
        
        if(tbm_tra.getStrStRecAlm()==null){
            objRecHum03.jCmbRecAlm.setSelectedIndex(1);
        }else{
            
            if(tbm_tra.getStrStRecAlm().compareTo("S")==0){
                objRecHum03.jCmbRecAlm.setSelectedIndex(0);
            }else{
                objRecHum03.jCmbRecAlm.setSelectedIndex(1);
                objRecHum03.jCmbForRec.setSelectedItem("");
            }
        }
        
        if(tbm_tra.getDblNdValAlm()>0){
            objRecHum03.txtValAlm.setText(""+tbm_tra.getDblNdValAlm());
        }else{
            objRecHum03.txtValAlm.setText("");
        }
        
        if(tbm_tra.getStrTxForRecAlm()==null){
            objRecHum03.jCmbForRec.setSelectedIndex(0);
        }else{
            if(tbm_tra.getStrTxForRecAlm().compareTo("R")==0){
                objRecHum03.jCmbForRec.setSelectedIndex(1);
            }else if(tbm_tra.getStrTxForRecAlm().compareTo("C")==0){
                objRecHum03.jCmbForRec.setSelectedIndex(2);
            }
        }
        
        if(lisDetFamCon!=null){
            Vector vecTblCon = new Vector();
            for(DetFamCon tmp:lisDetFamCon){
                Vector vecRegCon = new Vector();
                vecRegCon.add(objRecHum03.INT_CON_LINEA, "");
                vecRegCon.add(objRecHum03.INT_CON_CODTRA, tmp.getTbm_contra().getIntCo_tra());
                vecRegCon.add(objRecHum03.INT_CON_CODREG, tmp.getTbm_contra().getIntCo_reg());
                vecRegCon.add(objRecHum03.INT_CON_NOMCON, tmp.getTbm_contra().getStrTx_nom());
                vecRegCon.add(objRecHum03.INT_CON_CODREL, tmp.getTbm_tipfamcon().getIntCo_tipfamcon());
                vecRegCon.add(objRecHum03.INT_CON_BUTREL, "...");
                vecRegCon.add(objRecHum03.INT_CON_NOMREL, tmp.getTbm_tipfamcon().getStrTx_deslar());
                vecRegCon.add(objRecHum03.INT_CON_TEL1, tmp.getTbm_contra().getStrTx_tel1());
                vecRegCon.add(objRecHum03.INT_CON_TEL2, tmp.getTbm_contra().getStrTx_tel2());
                vecRegCon.add(objRecHum03.INT_CON_CHKCARFAM, !tmp.getTbm_tipfamcon().getStrTx_tipcarfam().equals("N"));
                if(tmp.getTbm_contra().getDatFe_nac()!=null){
                    vecRegCon.add(objRecHum03.INT_CON_FECNAC, objUti.formatearFecha(tmp.getTbm_contra().getDatFe_nac(), "dd/MM/yyyy"));
                }else{
                    vecRegCon.add(objRecHum03.INT_CON_FECNAC, null);
                }
                vecRegCon.add(objRecHum03.INT_CON_CHKEME, tmp.getTbm_contra().getStrSt_llacaseme().equals("S"));
                vecRegCon.add(objRecHum03.INT_CON_OBS, tmp.getTbm_contra().getStrTx_obs1());
                vecRegCon.add(objRecHum03.INT_CON_BUTOBS, "...");

                vecTblCon.add(vecRegCon);
            }
            if(!vecTblCon.isEmpty()){
                objRecHum03.zafTblModCon.setData(vecTblCon);
                objRecHum03.tblCon.setModel(objRecHum03.zafTblModCon);
            }
        }
        if(lisDetDocDig!=null){
            Vector vecTblDoc = new Vector();
            for(DetDocDig tmp:lisDetDocDig){
                Vector vecRegDoc = new Vector();
                vecRegDoc.add(objRecHum03.INT_DOC_LINEA, "");
                vecRegDoc.add(objRecHum03.INT_DOC_CODTRA, tmp.getTbm_docdigtra().getIntCo_tra());
                vecRegDoc.add(objRecHum03.INT_DOC_CODDOC, tmp.getTbm_docdigtra().getIntCo_reg());
                vecRegDoc.add(objRecHum03.INT_DOC_CODTIPDOC, tmp.getTbm_tipdocdigsis().getIntCo_tipdocdig());
                vecRegDoc.add(objRecHum03.INT_DOC_NOMTIPDOC, tmp.getTbm_tipdocdigsis().getStrTx_deslar());
                vecRegDoc.add(objRecHum03.INT_DOC_BUTTIPDOC, "...");
                vecRegDoc.add(objRecHum03.INT_DOC_NOMARCLOC, tmp.getStrArcLoc());
                vecRegDoc.add(objRecHum03.INT_DOC_NOMARC, tmp.getTbm_docdigtra().getStrTx_nomarc());
                vecRegDoc.add(objRecHum03.INT_DOC_BUTBUSARC, "...");
                vecRegDoc.add(objRecHum03.INT_DOC_OBS, tmp.getTbm_docdigtra().getStrTx_obs1());
                vecRegDoc.add(objRecHum03.INT_DOC_BUTOBS, "...");
                vecRegDoc.add(objRecHum03.INT_DOC_BUTVERDOC, "");

                vecTblDoc.add(vecRegDoc);
            }
            if(!vecTblDoc.isEmpty()){
                objRecHum03.zafTblModDoc.setData(vecTblDoc);
                objRecHum03.tblDoc.setModel(objRecHum03.zafTblModDoc);
            }
        }
    }
    
    public List<Tbm_hortra> consultarLisGenCabHorTra(){
        List<Tbm_hortra> lisTbm_hortra = null;
        try {
            lisTbm_hortra = objRecHum03BO.consultarLisGenCabHorTra();
        } catch (Exception ex) {
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }
        return lisTbm_hortra;
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra() throws Exception{
        //Datos del Empleado
        if(objRecHum03.txtCodTra.getText().length()>0){
            tbm_tra.setIntCo_tra(Integer.parseInt(objRecHum03.txtCodTra.getText()));
        }else{
            tbm_tra.setIntCo_tra(0);
        }
        if(objRecHum03.txtIde.getText().length()>0){
            tbm_tra.setStrTx_ide(objRecHum03.txtIde.getText());
        }else{
            tbm_tra.setStrTx_ide(null);
        }
        if(objRecHum03.txtNomTra.getText().length()>0){
            tbm_tra.setStrTx_nom(objRecHum03.txtNomTra.getText());
        }else{
            tbm_tra.setStrTx_nom(null);
        }
        if(objRecHum03.txtApeTra.getText().length()>0){
            tbm_tra.setStrTx_ape(objRecHum03.txtApeTra.getText());
        }else{
            tbm_tra.setStrTx_ape(null);
        }
        if(objRecHum03.txtDir.getText().length()>0){
            tbm_tra.setStrTx_dir(objRecHum03.txtDir.getText());
        }else{
            tbm_tra.setStrTx_dir(null);
        }
        if(objRecHum03.txtRefDir.getText().length()>0){
            tbm_tra.setStrTx_refdir(objRecHum03.txtRefDir.getText());
        }else{
            tbm_tra.setStrTx_refdir(null);
        }
        if(objRecHum03.txtTel1.getText().length()>0){
            tbm_tra.setStrTx_tel1(objRecHum03.txtTel1.getText());
        }else{
            tbm_tra.setStrTx_tel1(null);
        }
        if(objRecHum03.txtTel2.getText().length()>0){
            tbm_tra.setStrTx_tel2(objRecHum03.txtTel2.getText());
        }else{
            tbm_tra.setStrTx_tel2(null);
        }
        if(objRecHum03.txtTel3.getText().length()>0){
            tbm_tra.setStrTx_tel3(objRecHum03.txtTel3.getText());
        }else{
            tbm_tra.setStrTx_tel3(null);
        }
        if(objRecHum03.txtEma.getText().length()>0){
            tbm_tra.setStrTx_corele(objRecHum03.txtEma.getText());
        }else{
            tbm_tra.setStrTx_corele(null);
        }
        if(objRecHum03.optSexMas.isSelected()){
            tbm_tra.setStrTx_sex("M");
        }
        if(objRecHum03.optSexFem.isSelected()){
            tbm_tra.setStrTx_sex("F");
        }
        if(!objRecHum03.dtpFecNac.getText().equals("  /  /    ")){
            tbm_tra.setDatFe_nac(new Date(objUti.formatearFecha(objRecHum03.dtpFecNac.getText(),"dd/MM/yyyy","yyyy/MM/dd")));
        }else{
            tbm_tra.setDatFe_nac(null);
        }
        if(objRecHum03.txtCodCiu.getText().length()>0){
            tbm_tra.setIntCo_ciunac(Integer.parseInt(objRecHum03.txtCodCiu.getText()));
        }else{
            tbm_tra.setIntCo_ciunac(0);
        }
        if(objRecHum03.txtCodEstCiv.getText().length()>0){
            tbm_tra.setIntCo_estciv(Integer.parseInt(objRecHum03.txtCodEstCiv.getText()));
        }else{
            tbm_tra.setIntCo_estciv(0);
        }
        if(objRecHum03.txtNumHij.getText().length()>0){
            tbm_tra.setIntNe_numhij(Integer.parseInt(objRecHum03.txtNumHij.getText()));
        }else{
            tbm_tra.setIntNe_numhij(0);
        }
        if(objRecHum03.txtNumCta.getText().length()>0){
            tbm_tra.setStrTx_NumCtaBan(objRecHum03.txtNumCta.getText());
        }else{
            tbm_tra.setStrTx_NumCtaBan(null);
        }
        if(objRecHum03.jCmbTipCta.getSelectedIndex()>0){
            if(objRecHum03.jCmbTipCta.getSelectedIndex()==1){
                tbm_tra.setStrTx_TipCtaBan("A");
            }else if(objRecHum03.jCmbTipCta.getSelectedIndex()==2){
                tbm_tra.setStrTx_TipCtaBan("C");
            }
        }
        if(objRecHum03.txaObs1.getText().length()>0){
            tbm_tra.setStrTx_obs1(objRecHum03.txaObs1.getText());
        }else{
            tbm_tra.setStrTx_obs1(null);
        }
        if(objRecHum03.txaObs2.getText().length()>0){
            tbm_tra.setStrTx_obs2(objRecHum03.txaObs2.getText());
        }else{
            tbm_tra.setStrTx_obs2(null);
        }       
        
        if(!objRecHum03.dtpFecIniCon.getText().equals("  /  /    ")){
            tbm_tra.setDatFe_IniCon(new Date(objUti.formatearFecha(objRecHum03.dtpFecIniCon.getText(),"dd/MM/yyyy","yyyy/MM/dd")));
        }else{
            tbm_tra.setDatFe_IniCon(null);
        }
        
        if(!objRecHum03.dtpFecFinPerPru.getText().equals("  /  /    ")){
            tbm_tra.setDatFe_FinPerPru(new Date(objUti.formatearFecha(objRecHum03.dtpFecFinPerPru.getText(),"dd/MM/yyyy","yyyy/MM/dd")));
        }else{
            tbm_tra.setDatFe_FinPerPru(null);
        }
        
        if(!objRecHum03.dtpFecFinCon.getText().equals("  /  /    ")){
            tbm_tra.setDatFe_FinCon(new Date(objUti.formatearFecha(objRecHum03.dtpFecFinCon.getText(),"dd/MM/yyyy","yyyy/MM/dd")));
        }else{
            tbm_tra.setDatFe_FinCon(null);
        }
        
        if(!objRecHum03.dtpFecReaFinCon.getText().equals("  /  /    ")){
            tbm_tra.setDatFe_FinReaCon(new Date(objUti.formatearFecha(objRecHum03.dtpFecReaFinCon.getText(),"dd/MM/yyyy","yyyy/MM/dd")));
        }else{
            tbm_tra.setDatFe_FinReaCon(null);
        }
        
        String strStPerPru=null;
        if(objRecHum03.jCmbPla.getSelectedIndex()==0){
            strStPerPru=null;
        }
        else if(objRecHum03.jCmbPla.getSelectedIndex()==1){
            strStPerPru="S";
        }else if(objRecHum03.jCmbPla.getSelectedIndex()==2){
            strStPerPru="N";
        }
            
        tbm_tra.setStrSt_perprucon(strStPerPru);
            
        String strStFinCon=null;
        if(objRecHum03.jCmbEstFinCon.getSelectedIndex()==0){
            strStFinCon=null;
        }
        else if(objRecHum03.jCmbEstFinCon.getSelectedIndex()==1){
            strStFinCon="S";
        }else if(objRecHum03.jCmbEstFinCon.getSelectedIndex()==2){
            strStFinCon="N";
        }
        
        if(objRecHum03.txtCodDep.getText().length()>0){
            tbm_tra.setIntCoDep(Integer.parseInt(objRecHum03.txtCodDep.getText()));
        }else{
            tbm_tra.setIntCoDep(0);
        }
        
        if(objRecHum03.txtCodCarLab.getText().length()>0){
            tbm_tra.setIntCoCarLab(Integer.parseInt(objRecHum03.txtCodCarLab.getText()));
        }else{
            tbm_tra.setIntCoCarLab(0);
        }
        
        if(objRecHum03.txtCodOfi.getText().length()>0){
            tbm_tra.setIntCoOfi(Integer.parseInt(objRecHum03.txtCodOfi.getText()));
        }else{
            tbm_tra.setIntCoOfi(0);
        }
        
        if(objRecHum03.jCmbPla.getSelectedIndex()>0){
            tbm_tra.setIntCoPla(objRecHum03.jCmbPla.getSelectedIndex());
        }else{
            tbm_tra.setIntCoPla(0);
        }
        
        tbm_tra.setStrSt_fincon(strStFinCon);
        
        //Datos de los Contactos
        if(objRecHum03.tblCon.getRowCount()>0 &&
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_NOMCON)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_CODREL)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_TEL1)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_TEL2)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_CHKCARFAM)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_FECNAC)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_CHKEME)!=null) ||
            (objRecHum03.tblCon.getValueAt(0, objRecHum03.INT_CON_OBS)!=null)){

            lisTbm_contra = new ArrayList<Tbm_contra>();
            lisDetFamCon = new ArrayList<DetFamCon>();
            int i=0;
            while((objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_NOMCON)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODREL)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_TEL1)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_TEL2)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CHKCARFAM)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_FECNAC)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CHKEME)!=null) ||
                (objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_OBS)!=null)){

                Tbm_contra tmp = new Tbm_contra();
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODTRA)!=null){
                    tmp.setIntCo_tra((Integer)objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODTRA));
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODREG)!=null){
                    tmp.setIntCo_reg((Integer)objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODREG));
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_NOMCON)!=null){
                    tmp.setStrTx_nom(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_NOMCON).toString());
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODREL)!=null){
                    tmp.setIntCo_tipfamcon((Integer)objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_CODREL));
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_TEL1)!=null){
                    tmp.setStrTx_tel1(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_TEL1).toString());
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_TEL2)!=null){
                    tmp.setStrTx_tel2(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_TEL2).toString());
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_FECNAC)!=null){
                    tmp.setDatFe_nac(new Date(objUti.formatearFecha(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_FECNAC).toString(),"dd/MM/yyyy","yyyy/MM/dd")));
                }
                if(objRecHum03.tblCon.getModel().getValueAt(i, objRecHum03.INT_CON_CHKEME)!=null){
                    tmp.setStrSt_llacaseme((Boolean) objRecHum03.tblCon.getModel().getValueAt(i, objRecHum03.INT_CON_CHKEME)?"S":"N");
                }else{
                    tmp.setStrSt_llacaseme("N");
                }
                if(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_OBS)!=null){
                    tmp.setStrTx_obs1(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_OBS).toString());
                }
                 
                lisTbm_contra.add(tmp);
                lisDetFamCon.add(new DetFamCon());
                
                Tbm_tipfamcon tbm_tipfamcon = new Tbm_tipfamcon();
                tbm_tipfamcon.setIntCo_tipfamcon(tmp.getIntCo_tipfamcon());
                tbm_tipfamcon.setStrTx_deslar(objRecHum03.tblCon.getValueAt(i, objRecHum03.INT_CON_NOMREL).toString());
                //tbm_tipfamcon.setStrTx_tipcarfam((Boolean)objRecHum03.tblCon.getValueAt(i,objRecHum03.INT_CON_CHKCARFAM)?"H":"N");
                String str = tbm_tipfamconBO.consultarTipoCargaFamiliar(tbm_tipfamcon);
                tbm_tipfamcon.setStrTx_tipcarfam(str);
                lisDetFamCon.get(i).setTbm_contra(tmp);
                lisDetFamCon.get(i).setTbm_tipfamcon(tbm_tipfamcon);
                if(tbm_tipfamcon.getStrTx_tipcarfam().equals("H")){
                    if(tmp.getDatFe_nac()==null){
                        objRecHum03.tblCon.getSelectionModel().setSelectionInterval(i, i);
                        objRecHum03.tblCon.editCellAt(i, objRecHum03.INT_CON_FECNAC);
                        throw new Exception("Debe ingresar la Fecha de Nacimiento \npara "+tmp.getStrTx_nom());
                    }
                }
               
                i++;
            }
        }
        //Datos de los Documentos Digitales
        if(objRecHum03.tblDoc.getRowCount()>0 &&
            (objRecHum03.tblDoc.getValueAt(0, objRecHum03.INT_DOC_NOMTIPDOC)!=null) ||
            (objRecHum03.tblDoc.getValueAt(0, objRecHum03.INT_DOC_NOMARCLOC)!=null)){

            lisTbm_docdigtra = new ArrayList<Tbm_docdigtra>();
            lisDetDocDig = new ArrayList<DetDocDig>();
            int i=0;
            while((objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMTIPDOC)!=null) ||
                (objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMARCLOC)!=null)){

                Tbm_docdigtra tmp = new Tbm_docdigtra();
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_CODTRA)!=null){
                    tmp.setIntCo_tra((Integer)objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_CODTRA));
                }
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_CODDOC)!=null){
                    tmp.setIntCo_reg((Integer)objRecHum03.tblDoc.getValueAt(i, ZafRecHum03.INT_DOC_CODDOC));
                }
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_CODTIPDOC)!=null){
                    tmp.setIntCo_tipdocdig((Integer)objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_CODTIPDOC));
                }
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_OBS)!=null){
                    tmp.setStrTx_obs1(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_OBS).toString());
                }
                lisDetDocDig.add(new DetDocDig());
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMARCLOC)!=null){
                    lisDetDocDig.get(i).setStrArcLoc(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMARCLOC).toString());
                }
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMARC)!=null){
                    tmp.setStrTx_nomarc(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMARC).toString());
                }
                
                lisTbm_docdigtra.add(tmp);
                lisDetDocDig.get(i).setTbm_docdigtra(tmp);

                Tbm_tipdocdigsis tbm_tipdocdigsis = new Tbm_tipdocdigsis();
                tbm_tipdocdigsis.setIntCo_tipdocdig(tmp.getIntCo_tipdocdig());
                if(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMTIPDOC)!=null){
                    tbm_tipdocdigsis.setStrTx_deslar(objRecHum03.tblDoc.getValueAt(i, objRecHum03.INT_DOC_NOMTIPDOC).toString());
                }
                
                lisDetDocDig.get(i).setTbm_tipdocdigsis(tbm_tipdocdigsis);

                if(tbm_tipdocdigsis.getStrTx_deslar().length()<=0){
                    throw new Exception("Debe ingresar el tipo de documento digital");
                }
                if(lisDetDocDig.get(i).getStrArcLoc()!=null && lisDetDocDig.get(i).getStrArcLoc().length()<=0){
                    throw new Exception("Debe seleccionar el documento digital");
                }

                i++;
            }
        }
    }
    
    public boolean grabarHorTraEmp(Connection con){
        boolean blnRes = true;
        int intCo_emp = objParSis.getCodigoEmpresa();
        for(int i=0; i<objRecHum03.tblRep.getRowCount(); i++){
            if(objRecHum03.tblRep.getValueAt(i, objRecHum03.INT_REP_LINEA).toString().compareTo("M")==0){
                try{
                    int intNumCol2 = objRecHum03.zafTblModRep.getColumnCount();
                    int intNumColDin2 = intNumCol2 - (objRecHum03.INT_REP_NOMTRA+1);
                    int intCodTra = Integer.parseInt(objRecHum03.txtCodTra.getText().toString());
                    boolean blnComp=false;
                    for(int j=0;j<intNumColDin2-1;j++){
                        String strNomHor = objRecHum03.tblRep.getColumnName(objRecHum03.INT_REP_CHKHORFIJ+1+j);
                        boolean blnCheck = (Boolean)objRecHum03.tblRep.getValueAt(i, objRecHum03.INT_REP_CHKHORFIJ+1+j);
                        int intCoHor = tbm_hortraBO.consultarCodHorRep(strNomHor);
                        if(blnCheck){ //si esta chequeado y no existe en la base lo grabo
                            Tbm_traemp tbm_traemp = new Tbm_traemp();
                            tbm_traemp.setIntCo_emp(intCo_emp);
                            tbm_traemp.setIntCo_tra(intCodTra);
                            tbm_traemp.setStrSt_HorFij("S");
                            tbm_traemp.setIntCo_Hor(intCoHor);
                            tbr_traempBO.actualizarTraEmpCodHor(con,tbm_traemp);
                            j = intNumColDin2;
                            blnComp=true;
                        }
                    }
                    
                    if(!blnComp){
                        Tbm_traemp tbm_traemp = new Tbm_traemp();
                        tbm_traemp.setIntCo_emp(intCo_emp);
                        tbm_traemp.setIntCo_tra(intCodTra);
                        tbm_traemp.setStrSt_HorFij("N");
                        tbm_traemp.setIntCo_Hor(0);
                        tbr_traempBO.actualizarTraEmpCodHor(con,tbm_traemp);
                    }
                    
                }catch(SQLException ex){
                    blnRes = false;
                    ex.printStackTrace();
                    objUti.mostrarMsgErr_F1(objRecHum03, ex);
                }catch(Exception ex){
                    blnRes = false;
                    ex.printStackTrace();
                    objUti.mostrarMsgErr_F1(objRecHum03, ex);
                }
            }else{
                return false;
            }
        }

        if(blnRes){
            int intNumRow = objRecHum03.zafTblModRep.getRowCount();
            int intNumCol = objRecHum03.zafTblModRep.getColumnCount();
            int intNumColDin = intNumCol - (objRecHum03.INT_REP_NOMTRA+1);
                    
            for(int i=0;i<intNumRow;i++){
                for(int j=0;j<intNumColDin-1;j++){
                    boolean blnCheck = (Boolean)objRecHum03.tblRep.getValueAt(i, objRecHum03.INT_REP_CHKHORFIJ+1+j);
                    if(blnCheck){
                        objRecHum03.tblRep.setValueAt(blnCheck, i, 4);
                    }
                }
            }
        }
        return blnRes;    
    }    
    
    public boolean grabarHorTraEmp(){    
        
        boolean blnRes = true;
        int intCo_emp = objParSis.getCodigoEmpresa();
        
        for(int i=0; i<objRecHum03.tblRep.getRowCount(); i++){
            if(objRecHum03.tblRep.getValueAt(i, objRecHum03.INT_REP_LINEA).toString().compareTo("M")==0){
                try{
                    int intNumCol2 = objRecHum03.zafTblModRep.getColumnCount();
                    int intNumColDin2 = intNumCol2 - (ZafRecHum03.INT_REP_NOMTRA+1);
                    int intCodTra = Integer.parseInt(objRecHum03.txtCodTra.getText().toString());
                    boolean blnComp=false;
                    for(int j=0;j<intNumColDin2-1;j++){
                        String strNomHor = objRecHum03.tblRep.getColumnName(objRecHum03.INT_REP_CHKHORFIJ+1+j);
                        boolean blnCheck = (Boolean)objRecHum03.tblRep.getValueAt(i, objRecHum03.INT_REP_CHKHORFIJ+1+j);
                        int intCoHor = tbm_hortraBO.consultarCodHorRep(strNomHor);
                        if(blnCheck){ //si esta chequeado y no existe en la base lo grabo
                            Tbm_traemp tbm_traemp = new Tbm_traemp();
                            tbm_traemp.setIntCo_emp(intCo_emp);
                            tbm_traemp.setIntCo_tra(intCodTra);
                           tbm_traemp.setStrSt_HorFij("S");
                            tbm_traemp.setIntCo_Hor(intCoHor);
                            tbr_traempBO.actualizarTraEmpCodHor(tbm_traemp);
                            j = intNumColDin2;
                            blnComp=true;
                        }
                    }
                    
                    if(!blnComp){
                        Tbm_traemp tbm_traemp = new Tbm_traemp();
                        tbm_traemp.setIntCo_emp(intCo_emp);
                        tbm_traemp.setIntCo_tra(intCodTra);
                        tbm_traemp.setStrSt_HorFij("N");
                        tbm_traemp.setIntCo_Hor(0);
                        tbr_traempBO.actualizarTraEmpCodHor(tbm_traemp);
                    }
                    
                }catch(SQLException ex){
                    blnRes = false;
                    ex.printStackTrace();
                    objUti.mostrarMsgErr_F1(objRecHum03, ex);
                }catch(Exception ex){
                    blnRes = false;
                    ex.printStackTrace();
                    objUti.mostrarMsgErr_F1(objRecHum03, ex);
                }
            }else{
                return false;
            }
        }

        if(blnRes){
            int intNumRow = objRecHum03.zafTblModRep.getRowCount();
            int intNumCol = objRecHum03.zafTblModRep.getColumnCount();
            int intNumColDin = intNumCol - (objRecHum03.INT_REP_NOMTRA+1);
                    
            for(int i=0;i<intNumRow;i++){
                for(int j=0;j<intNumColDin-1;j++){
                    boolean blnCheck = (Boolean)objRecHum03.tblRep.getValueAt(i, objRecHum03.INT_REP_CHKHORFIJ+1+j);
                    if(blnCheck){
                        objRecHum03.tblRep.setValueAt(blnCheck, i, 4);
                    }
                }
            }
        }
        return blnRes;
    }
    
    private void sincronizarFraPojHorTra(){

        if(lisTbm_tra!=null){
            Vector vecTblRep = new Vector();
            int intNumCol = objRecHum03.zafTblModRep.getColumnCount();
            int intNumColDin = intNumCol - (ZafRecHum20.INT_REP_NOMTRA+1);
//            for(Tbm_tra tmp:lisTbm_tra){  //muestro las empresas por empleado
                Vector vecRegCon = new Vector();
                vecRegCon.add(ZafRecHum20.INT_REP_LINEA, "");
                vecRegCon.add(ZafRecHum20.INT_REP_CODTRA, objRecHum03.txtCodTra.getText());
                vecRegCon.add(ZafRecHum20.INT_REP_APETRA, objRecHum03.txtApeTra.getText());
                vecRegCon.add(ZafRecHum20.INT_REP_NOMTRA, objRecHum03.txtNomTra.getText());
                
                List<String> lisTbm_emp = new ArrayList<String>();  //llenar empresas
                try {
                    //lisTbm_emp = tbr_traempBO.consultarLisGenPorCodTra2(tmp.getIntCo_tra(), objParSis.getCodigoEmpresa());
                    lisTbm_emp = tbr_traempBO.consultarLisGenPorCodTra2(Integer.valueOf(objRecHum03.txtCodTra.getText()));
                    //recorro las columnas dinamicas
                    for(int i=0;i<intNumColDin;i++){
                        //obtengo cada nombre de empresa de la cabecera
                        String strNomEmp = objRecHum03.tblRep.getColumnName(ZafRecHum20.INT_REP_NOMTRA+1+i);
                        boolean blnChk = false;
                        if(lisTbm_emp!=null){
                            //comparo nombre de empresa de cabecera con las del empleado
                            for(String tmp2:lisTbm_emp){
                                if(blnChk = strNomEmp.equalsIgnoreCase(tmp2)){
                                    break;
                                }
                            }
                        }
                        vecRegCon.add(ZafRecHum20.INT_REP_NOMTRA+1+i, blnChk);
                        if(blnChk){
                            vecRegCon.setElementAt(blnChk,4);
                        }
                    }
                } catch (Exception ex) {
                    objUti.mostrarMsgErr_F1(objRecHum03, ex);
                }
                vecTblRep.add(vecRegCon);
//            }
            if(!vecTblRep.isEmpty()){
                objRecHum03.zafTblModRep.setData(vecTblRep);
                objRecHum03.tblRep.setModel(objRecHum03.zafTblModRep);
            }
        }
    }
   
    
    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenConDep(){
        boolean blnRes=true;
        try{
            vcoTra.limpiar();
            vcoTra.setCampoBusqueda(0);
            vcoTra.setVisible(true);

            if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_tra.setIntCo_tra(Integer.parseInt(vcoTra.getValueAt(1)));
                objRecHum03.txtCodTra.setText(String.valueOf(tbm_tra.getIntCo_tra()));
                blnRes = objTooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }
        return blnRes;
    }

    /**
     * Permite visualizar la ventana de consulta de Ciudades para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenConCiu(){
        boolean blnRes=true;
        try{
            vcoCiu.limpiar();
            vcoCiu.setCampoBusqueda(0);
            vcoCiu.setVisible(true);
            if (vcoCiu.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_ciu.setIntCo_pai(Integer.parseInt(vcoCiu.getValueAt(1)));
                tbm_ciu.setIntCo_ciu(Integer.parseInt(vcoCiu.getValueAt(2)));
                tbm_ciu.setStrTx_deslar(vcoCiu.getValueAt(3));
                tbm_tra.setIntCo_ciunac(tbm_ciu.getIntCo_ciu());
                objRecHum03.txtCodCiu.setText(String.valueOf(tbm_tra.getIntCo_ciunac()));
                objRecHum03.txtNomCiu.setText(tbm_ciu.getStrTx_deslar());
            }
        }catch (Exception ex){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }
        return blnRes;
    }

    /**
     * Permite visualizar la ventana de consulta de Estado Civil para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenEstCiv(){
        boolean blnRes=true;
        try{
            vcoEstCiv.limpiar();
            vcoEstCiv.setCampoBusqueda(0);
            vcoEstCiv.setVisible(true);
            if (vcoEstCiv.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_var.setIntCo_grp(Integer.parseInt(vcoEstCiv.getValueAt(1)));
                tbm_var.setIntCo_reg(Integer.parseInt(vcoEstCiv.getValueAt(2)));
                tbm_var.setStrTx_deslar(String.valueOf(vcoEstCiv.getValueAt(3)));
                tbm_tra.setIntCo_estciv(tbm_var.getIntCo_reg());
                objRecHum03.txtCodEstCiv.setText(String.valueOf(tbm_var.getIntCo_reg()));
                objRecHum03.txtNomEstCiv.setText(tbm_var.getStrTx_deslar());
            }
        }catch (Exception ex){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }
        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodTra true habilita, false deshabilita txtCodTra
     * @param blnButCodTra true habilita, false deshabilita butCodTra
     * @param blnTxtIde true habilita, false deshabilita txtIde
     * @param blnTxtNomTra true habilita, false deshabilita txtNomTra
     * @param blnTxtApeTra true habilita, false deshabilita txtApeTra
     * @param blnTxtDir true habilita, false deshabilita txtDir
     * @param blnTxtRefDir true habilita, false deshabilita txtRefDir
     * @param blnTxtTel1 true habilita, false deshabilita txtTel1
     * @param blnTxtTel2 true habilita, false deshabilita txtTel2
     * @param blnTxtTel3 true habilita, false deshabilita txtTel3
     * @param blnTxtEma true habilita, false deshabilita txtEma
     * @param blnOptSexMas true habilita, false deshabilita optSexMas
     * @param blnOptSexFem true habilita, false deshabilita optSexFem
     * @param blnLblTxtFecNac true habilita, false deshabilita lblTxtFecNac
     * @param blnTxtCodCiu true habilita, false deshabilita txtCodCiu
     * @param blnButCiu true habilita, false deshabilita butCiu
     * @param blnTxtCodEstCiv true habilita, false deshabilita txtCodEstCiv
     * @param blnTxtNomEstCiv true habilita, false deshabilita txtNomEstCiv
     * @param blnButEstCiv true habilita, false deshabilita butEstCiv
     * @param blnTxtNumHij true habilita, false deshabilita txtNumHij
     * @param blnTxaObs1 true habilita, false deshabilita txaObs1
     * @param blnTxaObs2 true habilita, false deshabilita txaObs2
     */
    private void habilitarCom(boolean blnTxtCodTra, boolean blnButCodTra, boolean blnTxtIde, boolean blnTxtNomTra,
                              boolean blnTxtApeTra, boolean blnTxtDir, boolean blnTxtRefDir, boolean blnTxtTel1,
                              boolean blnTxtTel2, boolean blnTxtTel3, boolean blnTxtEma, boolean blnOptSexMas,
                              boolean blnOptSexFem, boolean blnLblTxtFecNac, boolean blnTxtCodCiu, boolean blnTxtNomCiu,
                              boolean blnButCiu, boolean blnTxtCodEstCiv, boolean blnTxtNomEstCiv, boolean blnButEstCiv,
                              boolean blnTxtNumHij, boolean blnTxaObs1, boolean blnTxaObs2)
    {
        objRecHum03.txtCodTra.setEditable(blnTxtCodTra);
        objRecHum03.butCodTra.setEnabled(blnButCodTra);
        objRecHum03.txtIde.setEditable(blnTxtIde);
        objRecHum03.txtNomTra.setEditable(blnTxtNomTra);
        objRecHum03.txtApeTra.setEditable(blnTxtApeTra);
        objRecHum03.txtDir.setEditable(blnTxtDir);
        objRecHum03.txtRefDir.setEditable(blnTxtRefDir);
        objRecHum03.txtTel1.setEditable(blnTxtTel1);
        objRecHum03.txtTel2.setEditable(blnTxtTel2);
        objRecHum03.txtTel3.setEditable(blnTxtTel3);
        objRecHum03.txtEma.setEditable(blnTxtEma);
        objRecHum03.optSexMas.setEnabled(blnOptSexMas);
        objRecHum03.optSexFem.setEnabled(blnOptSexFem);
        objRecHum03.dtpFecNac.setEnabled(blnLblTxtFecNac);
        objRecHum03.txtCodCiu.setEditable(blnTxtCodCiu);
        objRecHum03.txtNomCiu.setEditable(blnTxtNomCiu);
        objRecHum03.butCiu.setEnabled(blnButCiu);
        objRecHum03.txtCodEstCiv.setEditable(blnTxtCodEstCiv);
        objRecHum03.txtNomEstCiv.setEditable(blnTxtNomEstCiv);
        objRecHum03.butEstCiv.setEnabled(blnButEstCiv);
        objRecHum03.txtNumHij.setEditable(blnTxtNumHij);
        objRecHum03.txaObs1.setEditable(blnTxaObs1);
        objRecHum03.txaObs2.setEditable(blnTxaObs2);
        objRecHum03.dtpFecIniCon.setEnabled(blnLblTxtFecNac);
        objRecHum03.dtpFecFinPerPru.setEnabled(blnLblTxtFecNac);
        objRecHum03.dtpFecFinCon.setEnabled(blnLblTxtFecNac);
        objRecHum03.dtpFecReaFinCon.setEnabled(blnLblTxtFecNac);
    }

    /**
     * Permite consultar en la base de datos si la cédula ya existe
     * @return Retorna true si existe y false caso contrario
     */
    public boolean validarCedRep(){
        boolean blnOk = false;

        try {
            //blnOk = objRecHum03BO.consultarCedRep(tbm_tra.getIntCo_tra(), tbm_tra.getStrTx_ide());
            blnOk = objRecHum03BO.consultarCedRep(tbm_tra.getStrTx_ide());
            if(blnOk){
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Cédula de Identidad ya existe. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                objRecHum03.tabGen.setSelectedIndex(0);
                objRecHum03.txtIde.requestFocus();
                objRecHum03.txtIde.selectAll();
            }
        } catch (Exception ex) {
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }

        return blnOk;
    }

    public boolean validarCedRep(String strCedTra){
        boolean blnOk = false;

        try {
            //blnOk = objRecHum03BO.consultarCedRep(tbm_tra.getIntCo_tra(), tbm_tra.getStrTx_ide());
            blnOk = objRecHum03BO.consultarCedRep(strCedTra);
            if(blnOk){
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Cédula de Identidad ya existe. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                objRecHum03.tabGen.setSelectedIndex(0);
                objRecHum03.txtIde.requestFocus();
                objRecHum03.txtIde.selectAll();
            }
        } catch (Exception ex) {
            objUti.mostrarMsgErr_F1(objRecHum03, ex);
        }

        return blnOk;
    }

    /**
     * Valida que los datos numéricos ingresados sean correctos
     * @return Retorna true si los datos son correctos y false caso contrario
     */
    private boolean validarCamNum(){
        boolean blnOk = true;
        JComponent comTmp = null;
        String strCom = null;
        try{
            if(objRecHum03.txtCodCiu.getText().length()>0){
                comTmp = objRecHum03.txtCodCiu;
                strCom = "Ciudad";
                Double.parseDouble(objRecHum03.txtCodCiu.getText());
            }
            if(objRecHum03.txtCodEstCiv.getText().length()>0){
                comTmp = objRecHum03.txtCodEstCiv;
                strCom = "Estado Civil";
                Double.parseDouble(objRecHum03.txtCodEstCiv.getText());
            }
            if(objRecHum03.txtNumHij.getText().length()>0){
                comTmp = objRecHum03.txtNumHij;
                strCom = "Número de Hijos";
                Double.parseDouble(objRecHum03.txtNumHij.getText());
            }
        } catch(NumberFormatException ex){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = strCom+" debe contener sólo números.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            ((JTextField)comTmp).requestFocus();
            ((JTextField)comTmp).selectAll();
        }

        return blnOk;
    }

    private boolean validarCorEle(){
        boolean blnOk = true;
        if(objRecHum03.txtEma.getText().length()>0){
            blnOk = objRecHum03.txtEma.getText().matches(".+@.+\\.[a-z]+");
            if(!blnOk){
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "<HTML>Formato del correo electrónico incorrecto.<BR>Corrija el campo y vuelva a intentarlo</HTML>";
                JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                objRecHum03.txtEma.requestFocus();
                objRecHum03.txtEma.selectAll();
            }
        }

        return blnOk;
    }

    /**
     * Verifica si existen cambios en el registro actual.
     * Pregunta si desea guardar cambios.
     * @return Retorna true si continua con la accion o false si se detiene.
     */
    private boolean verificarCamReg() {
        boolean blnOk = true;

        if(blnMod){
            String strMsg = "Existen cambios sin guardar! \nEstá Seguro que desea continuar?";
            String strTit = "Mensaje del sistema Zafiro";
            blnOk=(JOptionPane.showConfirmDialog(objRecHum03,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        }
        if(blnOk){
            blnMod = false;
            blnActChkMod = false;
        }

        return blnOk;
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
        oppMsg.showMessageDialog(objRecHum03,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private boolean validaCtaTipCta(){
        if(objRecHum03.txtNumCta.getText().length()==0){
            if(objRecHum03.jCmbTipCta.getSelectedIndex()>0){
                mostrarMsgInf("<HTML>Debe ingresar el <FONT COLOR=\"blue\">Número de cuenta</FONT>.<BR>Ingrese un Número de cuenta y vuelva a intentarlo.</HTML>");
                objRecHum03.tabGen.setSelectedIndex(0);
                return false;
            }
        }
        
        if(objRecHum03.jCmbTipCta.getSelectedIndex()==0){
            if(objRecHum03.txtNumCta.getText().length()>0){
                mostrarMsgInf("<HTML>Debe ingresar el <FONT COLOR=\"blue\">Tipo de cuenta</FONT>.<BR>Seleccione un Tipo de cuenta y vuelva a intentarlo.</HTML>");
                objRecHum03.tabGen.setSelectedIndex(0);
                return false;
            }
        }
        
        return true;
    }
    
    private boolean validaDocumentosDigitales(List<DetDocDig> lisDetDocDig){
        boolean blnRes=false;
        if(lisDetDocDig.size()>0){
            int intComp=0;
            ArrayList<Integer> arrLst = new ArrayList <Integer>();
            if(Integer.valueOf(objRecHum03.txtCodDep.getText())>=26 && Integer.valueOf(objRecHum03.txtCodDep.getText())<=30){
                arrLst.add(1);arrLst.add(2);arrLst.add(3);arrLst.add(4);arrLst.add(5);
                for(DetDocDig tmp:lisDetDocDig){
                    for(Iterator<Integer> itLst = arrLst.iterator();itLst.hasNext();){
                        if(tmp.getTbm_tipdocdigsis().getIntCo_tipdocdig()==itLst.next().intValue()){
                            intComp++;
                        }
                    }
                }
                if(intComp==5){
                    blnRes=true;
                }else{
                    blnRes= false;
                }
            }else{
                arrLst.add(1);arrLst.add(2);arrLst.add(4);arrLst.add(5);
                for(DetDocDig tmp:lisDetDocDig){
                    for(Iterator<Integer> itLst = arrLst.iterator();itLst.hasNext();){
                        if(tmp.getTbm_tipdocdigsis().getIntCo_tipdocdig()==itLst.next().intValue()){
                            intComp++;
                        }
                    }
                }
                
                if(intComp==4){
                    blnRes= true;
                }else{
                    blnRes= false;
                }
                
            }
        }else{
            if(Integer.valueOf(objRecHum03.txtCodDep.getText())>=26 && Integer.valueOf(objRecHum03.txtCodDep.getText())<=30){
                mostrarMsgInf("<HTML>Debe ingresar <FONT COLOR=\"blue\">Documentación Digital</FONT> básica para el guardado del empleado.<BR>Ingrese: Cédula, Certificado de Votación, Hoja de vida, Record Policial, Licencia de Conducir.</HTML>");
                objRecHum03.tabGen.setSelectedIndex(2);
                blnRes= false;
            }else{
                mostrarMsgInf("<HTML>Debe ingresar <FONT COLOR=\"blue\">Documentación Digital</FONT> básica para el guardado del empleado.<BR>Ingrese: Cédula, Certificado de Votación, Hoja de vida, Record Policial.</HTML>");
                objRecHum03.tabGen.setSelectedIndex(2);
                blnRes= false;
            }
        }
        
        return blnRes;
    }
    
    public boolean validaAlmuerzo(){
        if(objRecHum03.jCmbRecAlm.getSelectedIndex()==0){
            if(objRecHum03.jCmbForRec.getSelectedIndex()==1){
                if(objRecHum03.txtValAlm.getText().length()<=0){
                    objRecHum03.tabGen.setSelectedIndex(3);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Valor de Almuerzo</FONT> es obligatorio.<BR>Escriba un valor de almuerzo.</HTML>");
                    objRecHum03.txtValAlm.requestFocus();
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean validaFechaInicioContrato(){
        if (objRecHum03.dtpFecIniCon.getText().equals("  /  /    "))
        {
            objRecHum03.tabGen.setSelectedIndex(3);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Inicio de Contrato</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de inicio de contrato.</HTML>");
            objRecHum03.dtpFecIniCon.requestFocus();
            return false;
        }
        if (objRecHum03.dtpFecFinPerPru.getText().equals("  /  /    "))
        {
            objRecHum03.tabGen.setSelectedIndex(3);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Período de Prueba</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de período de prueba.</HTML>");
            objRecHum03.dtpFecFinPerPru.requestFocus();
            return false;
        }
        return true;
    }
    
    public boolean validaFechaRealFinContrato(){
        if (objRecHum03.dtpFecReaFinCon.getText().equals("  /  /    "))
        {
            objRecHum03.tabGen.setSelectedIndex(3);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Finalización real del contrato</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de inicio de contrato.</HTML>");
            objRecHum03.dtpFecIniCon.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    public class MiToolBar extends ZafToolBar{

        public MiToolBar(JInternalFrame objIntFra){
            super(objIntFra, objParSis);
        }

        private void mostrarEstado(){
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if(tbm_tra.getStrSt_reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_tra.getStrSt_reg().equals("E")){
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                }else{
                    setEstadoRegistro("");
                }
            }
        }

        private void consultarTabRef() throws Exception{
            //Bloque de consulta para la ciudad de nacimiento
            if(tbm_tra.getIntCo_ciunac()>0){
                Tbm_ciu tbm_ciupar = new Tbm_ciu();
                tbm_ciupar.setIntCo_pai(6);//Ecuador
                tbm_ciupar.setIntCo_ciu(tbm_tra.getIntCo_ciunac());
                List<Tbm_ciu> lisTbm_ciu = null;
                lisTbm_ciu = objRecHum03BO.consultarLisTbm_ciu(tbm_ciupar);
                if(lisTbm_ciu!=null){
                    tbm_ciu = lisTbm_ciu.get(0);
                }
            }

            //Bloque de consulta para el estado civil
            if(tbm_tra.getIntCo_estciv()>0){
                Tbm_var tbm_varpar = new Tbm_var();
                tbm_varpar.setIntCo_grp(1);//Grupo de Estado Civil
                tbm_varpar.setIntCo_reg(tbm_tra.getIntCo_estciv());
                List<Tbm_var> lisTbm_var = null;
                lisTbm_var = objRecHum03BO.consultarLisTbm_var(tbm_varpar);
                if(lisTbm_var!=null){
                    tbm_var = lisTbm_var.get(0);
                }
            }

            //Bloque de consulta para el detalle de contactos
            objRecHum03.zafTblModCon.removeAllRows();
            
            objRecHum03.zafTblModRep.removeAllRows();
            Vector vecRegCon = new Vector();
            if(objRecHum03.lisTbm_hortra!=null){
                Vector vecTblCon = new Vector();
    //            for(Tbm_hortra tmp:lisTbm_hortra){

                    vecRegCon.add(objRecHum03.INT_REP_LINEA,"");
                    vecRegCon.add(objRecHum03.INT_REP_CODTRA,"");
                    vecRegCon.add(objRecHum03.INT_REP_APETRA,"");
                    vecRegCon.add(objRecHum03.INT_REP_NOMTRA,"");
                    vecRegCon.add(objRecHum03.INT_REP_CHKHORFIJ,"");
                    for(Tbm_hortra tmp:objRecHum03.lisTbm_hortra){
                        vecRegCon.add(objRecHum03.INT_REP_CHKHORFIJ+1,"");
                    }
    //            }
                vecTblCon.add(vecRegCon);
                if(!vecTblCon.isEmpty()){
                    objRecHum03.zafTblModRep.setData(vecTblCon);
                    objRecHum03.tblRep.setModel(objRecHum03.zafTblModRep);
                }
            }
//            objRecHum03.zafTblModRep.removeAllRows();
            Tbm_contra tbm_contrapar = new Tbm_contra();
            tbm_contrapar.setIntCo_tra(tbm_tra.getIntCo_tra());
            tbm_contrapar.setStrSt_reg("A");
            lisDetFamCon = objRecHum03BO.consultarLisGenDetFamCon(tbm_contrapar);
            if(lisDetFamCon!=null){
                lisTbm_contra = new ArrayList<Tbm_contra>();
                lisTbm_contrabk = new ArrayList<Tbm_contra>();
                for(DetFamCon tmp:lisDetFamCon){
                    lisTbm_contra.add(tmp.getTbm_contra());
                    lisTbm_contrabk.add((Tbm_contra) tmp.getTbm_contra().clone());
                }
            }
            //Bloque de consulta para el detalle de documentos digitales
            objRecHum03.zafTblModDoc.removeAllRows();
            Tbm_docdigtra tbm_docdigtrapar = new Tbm_docdigtra();
            tbm_docdigtrapar.setIntCo_tra(tbm_tra.getIntCo_tra());
            tbm_docdigtrapar.setStrSt_reg("A");
            lisDetDocDig = objRecHum03BO.consultarLisGenDetDocDig(tbm_docdigtrapar);
            if(lisDetDocDig!=null){
                lisTbm_docdigtra = new ArrayList<Tbm_docdigtra>();
                lisTbm_docdigtrabk = new ArrayList<Tbm_docdigtra>();
                for(DetDocDig tmp:lisDetDocDig){
                    lisTbm_docdigtra.add(tmp.getTbm_docdigtra());
                    lisTbm_docdigtrabk.add((Tbm_docdigtra) tmp.getTbm_docdigtra().clone());
                }
            }
            
        }

        /**
         * Pagineo de Base. Permite consultar registros página a página según consulta.
         * Optimiza espacio de memoria al tener un registro cargado a la vez.
         * Minimiza el riezgo de dejar conecciones, statements y cursores abiertos.
         */
        private void pagineoBas(){
            List<Tbm_tra> listmp = null;

            try{
                listmp = objRecHum03BO.consultarLisPag(tbm_trapar, intPagAct, 1, objParSis);
                if(listmp!=null){
                    tbm_tra = listmp.get(0);
                    consultarTabRef();
                    sincronizarFraPoj();
                    lisTbm_tra = listmp;
                    sincronizarFraPojHorTra();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            }
        }

        public void clickInicio() {
            if(intPagAct>0){
                if(verificarCamReg()){
                    intPagAct = 0;
                    pagineoBas();
                }
            }
        }

        public void clickAnterior() {
            if(intPagAct>0){
                if(verificarCamReg()){
                    intPagAct--;
                    pagineoBas();
                }
            }
        }

        public void clickSiguiente() {
            if(intPagAct<intPagFin){
                if(verificarCamReg()){
                    intPagAct++;
                    pagineoBas();
                }
            }
        }

        public void clickFin() {
            if(intPagAct<intPagFin){
                if(verificarCamReg()){
                    intPagAct = intPagFin;
                    pagineoBas();
                }
            }
        }

        public void clickInsertar() {
            tbm_tra = null;
            tbm_tra = new Tbm_tra();
            lisTbm_contra = null;
            lisTbm_docdigtra = null;
            lisTbm_contra = new ArrayList<Tbm_contra>();
            lisTbm_docdigtra = new ArrayList<Tbm_docdigtra>();
            lisDetFamCon = null;
            lisDetDocDig = null;
            lisDetFamCon = new ArrayList<DetFamCon>();
            lisDetDocDig = new ArrayList<DetDocDig>();
            lisTbm_contrabk = null;
            lisTbm_docdigtrabk = null;
            lisTbm_contrabk = new ArrayList<Tbm_contra>();
            lisTbm_docdigtrabk = new ArrayList<Tbm_docdigtra>();
            sincronizarFraPoj();
//            sincronizarFraPojHorTra();
            objRecHum03.optSexMas.setSelected(true);
            objRecHum03.zafTblModCon.removeAllRows();
            objRecHum03.zafTblModDoc.removeAllRows();
            
             objRecHum03.zafTblModRep.removeAllRows();
            Vector vecRegCon = new Vector();
            if(objRecHum03.lisTbm_hortra!=null){
                Vector vecTblCon = new Vector();
    //            for(Tbm_hortra tmp:lisTbm_hortra){
                    vecRegCon.add(objRecHum03.INT_REP_LINEA,"");
                    vecRegCon.add(objRecHum03.INT_REP_CODTRA,"");
                    vecRegCon.add(objRecHum03.INT_REP_APETRA,"");
                    vecRegCon.add(objRecHum03.INT_REP_NOMTRA,"");
                    vecRegCon.add(objRecHum03.INT_REP_CHKHORFIJ,"");
                    for(Tbm_hortra tmp:objRecHum03.lisTbm_hortra){
                        vecRegCon.add(objRecHum03.INT_REP_CHKHORFIJ+1,"");
                    }
    //            }
                vecTblCon.add(vecRegCon);
                if(!vecTblCon.isEmpty()){
                    objRecHum03.zafTblModRep.setData(vecTblCon);
                    objRecHum03.tblRep.setModel(objRecHum03.zafTblModRep);
                }
            }
                    
            habilitarCom(false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, false, true, true, true, true);
            objRecHum03.jPanel1.setVisible(true);
            objRecHum03.txtIde.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            habilitarCom(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, false, true, true, true, true);
            objRecHum03.jPanel1.setVisible(false);
            objRecHum03.txtCodTra.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, false, true, true, true, true);
            objRecHum03.jPanel1.setVisible(false);
//            objRecHum03.tblRep.setVisible(false);
            objRecHum03.txtIde.requestFocus();
            objRecHum03.txtIde.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            objUti.desactivarCom(objRecHum03);
//            objRecHum03.txtCodDep.setEditable(false);
        }

        public void clickAnular() {
            objUti.desactivarCom(objRecHum03);
//            objRecHum03.txtCodDep.setEditable(false);
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
            objRecHum03.jPanel1.setVisible(true);
        }
        
        private Connection obtenerConexionSec(){
            Connection con2=null;
            try{
            
                if ((objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false) {
                    con2 = DriverManager.getConnection("jdbc:postgresql://172.16.8.12:5432/Zafiro2006_BI",objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    //con2 = DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_21_Dic_2018_2",objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    con2.setAutoCommit(false);                
                }else{
                   // con2 = DriverManager.getConnection("jdbc:postgresql://172.16.8.2:5432/dbCosenco",objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    con2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbCosenco_16_Sep_2019",objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    con2.setAutoCommit(false);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return con2;
        }        
        

        public boolean insertar(){
            boolean blnOk = false;
            java.sql.Connection con=null,consec=null;
            java.sql.Statement stmLoc=null;
            try {
                sincronizarPojFra();
                con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if(con!=null){
                    con.setAutoCommit(false);
                    if(validaFechaInicioContrato()){
                        if(validaAlmuerzo()){
                            if(validaDocumentosDigitales(lisDetDocDig)){
//                                if(getVerId().verificarId(tbm_tra.getStrTx_ide())){
                                   if(!validarCedRep()&&validarCorEle()){
                                       tbm_tra.setStrSt_reg("A");
                                       consec=obtenerConexionSec();
                                       //objRecHum03BO.grabarTra(tbm_tra, lisTbm_contra, lisDetDocDig, strNomPan);
                                       objRecHum03BO.grabarTra(con,consec,tbm_tra, lisTbm_contra, lisDetDocDig, strNomPan);
                                       sincronizarFraPoj();
       //                              con.setAutoCommit(false);
                                       stmLoc=con.createStatement();
                                       String strSql="";

//                                       if(grabarHorTraEmp()){
//                                           blnOk = true;
                                       if(grabarHorTraEmp(con)){
                                           blnOk = true;//Se Modifico para prueba Bostel
                                       }else{
                                           validarCamHorTra();
                                           strSql="";
                                           strSql+=" delete from tbm_traemp where co_tra="+objRecHum03.txtCodTra.getText();
                                           strSql+=" and co_emp="+objParSis.getCodigoEmpresa();
                                           stmLoc.executeUpdate(strSql);
                                           strSql="";
                                           strSql+="delete from tbm_tra where co_tra="+objRecHum03.txtCodTra.getText();
                                           stmLoc.executeUpdate(strSql);
                                           //con.commit();
                                           new RRHHDao(objUti, objParSis).callServicio9();
                                           //con.close();
                                           //con=null;
                                           return false;
                                       }

                                       if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                                           String[] strArrFecIniCon=objRecHum03.dtpFecIniCon.getText().split("/");
                                           String strFecIniCon=strArrFecIniCon[2]+"-"+strArrFecIniCon[1]+"-"+strArrFecIniCon[0];
                                           String[] strArrFecFinPerPru=objRecHum03.dtpFecFinPerPru.getText().split("/");
                                           String strFecFinPerPru=strArrFecFinPerPru[2]+"-"+strArrFecFinPerPru[1]+"-"+strArrFecFinPerPru[0];
                                           String[] strArrFeFinCon=objRecHum03.dtpFecFinCon.getText().split("/");
                                           String strFecFinCon=strArrFeFinCon[2]+"-"+strArrFeFinCon[1]+"-"+strArrFeFinCon[0];
                                           String strStRecAlm="";
                                           String strStForRecAlm="";
                                           String strValAlm="";
//                                           if(objRecHum03.jCmbRecAlm.getSelectedIndex()==0){
//                                               
//                                           }else{
//                                               strStRecAlm=null;
//                                               strValAlm=null;
//                                           }
                                           
                                           if(objRecHum03.jCmbRecAlm.getSelectedIndex()==0){
                                               if(objRecHum03.jCmbForRec.getSelectedIndex()==1){
                                                   strStRecAlm="S";
                                                   strStForRecAlm="R";
                                                   strValAlm=objRecHum03.txtValAlm.getText();
                                               }else if(objRecHum03.jCmbForRec.getSelectedIndex()==2){
                                                   strStRecAlm="S";
                                                   strStForRecAlm="C";
                                                   strValAlm=null;
                                               }
                                           }else{
                                               strStRecAlm=null;
                                               strStForRecAlm=null;
                                               strValAlm=null;
                                           }
                                           
                                           strSql="";
                                           strSql+=" update tbm_traemp";
                                           strSql+=" set fe_inicon = " + objUti.codificar(strFecIniCon) + " , ";
                                           strSql+=" fe_finperprucon = " + objUti.codificar(strFecFinPerPru) + " , ";
                                           strSql+=" fe_fincon = " + objUti.codificar(strFecFinCon) + " , ";
                                           strSql+=" co_pla = " + objRecHum03.jCmbPla.getSelectedIndex() + " , ";
                                           strSql+=" co_dep = " + objRecHum03.txtCodDep.getText()+ " , ";
                                           strSql+=" co_car = " + objRecHum03.txtCodCarLab.getText()+ " , ";
                                           strSql+=" co_ofi = " + objRecHum03.txtCodOfi.getText()+ " , ";
                                           strSql+=" st_recalm = " + objUti.codificar(strStRecAlm)+ " , ";
                                           strSql+=" tx_forrecalm = " + objUti.codificar(strStForRecAlm)+ " , ";
                                           strSql+=" nd_valalm = " + objUti.codificar(strValAlm)+ " , ";
                                           strSql+=" fe_ing = current_timestamp"+ " , ";
                                           strSql+=" co_usring = "+objParSis.getCodigoUsuario();
                                           strSql+=" where co_tra = " + tbm_tra.getIntCo_tra();
                                           strSql+=" and co_emp = " + objParSis.getCodigoEmpresa();
                                           stmLoc.executeUpdate(strSql);
                                       }

                                       strSql="";
                                       strSql="INSERT INTO tbm_sueTra(co_emp, co_rub, co_tra, nd_valRub)";
                                       strSql+=" SELECT b1.*, NULL AS nd_valRub";
                                       strSql+=" FROM";
                                       strSql+=" (";
                                       strSql+=" SELECT a1.co_emp, a1.co_rub, a2.co_tra";
                                       strSql+=" FROM tbm_rubRolPagEmp AS a1";
                                       strSql+=" RIGHT OUTER JOIN tbm_traEmp AS a2 ON (a1.co_emp=a2.co_emp)";
                                       strSql+=" WHERE a2.st_reg='A'";
                                       strSql+=" ) AS b1";
                                       strSql+=" LEFT OUTER JOIN tbm_sueTra AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_rub=b2.co_rub AND b1.co_tra=b2.co_tra)";
                                       strSql+=" WHERE b2.co_emp IS NULL";
                                       stmLoc.executeUpdate(strSql);

       //                                sincronizarFraPojHorTra();
       //                                blnOk=actualizarPlantillaEmpleado(con);
                                       if(actualizarPlantillaEmpleado(con)){
                                           if(crearCuentaCobrarEmpleado(con)){
                                               if(enviarCorEle(1)){
                                                   con.commit();
                                                   consec.commit();
                                                   blnOk=true;
                                               }else{
                                                   con.rollback();
                                                   consec.rollback();
                                               }
                                           }else{
                                               blnOk=false;
                                               con.rollback();
                                               consec.rollback();
                                               /*strSql="";
                                               strSql+="delete from tbm_traemp where co_tra="+objRecHum03.txtCodTra.getText();
                                               strSql+=" and co_emp="+objParSis.getCodigoEmpresa();
                                               stmLoc.executeUpdate(strSql);
                                               strSql="";
                                               strSql+="delete from tbm_tra where co_tra="+objRecHum03.txtCodTra.getText();
                                               stmLoc.executeUpdate(strSql);
                                               con.commit();*/
                                           }
                                       }else{
                                           blnOk=false;
                                           con.rollback();
                                           consec.rollback();
                                           /*strSql="";
                                           strSql+="delete from tbm_traemp where co_tra="+objRecHum03.txtCodTra.getText();
                                           strSql+=" and co_emp="+objParSis.getCodigoEmpresa();
                                           stmLoc.executeUpdate(strSql);
                                           strSql="";
                                           strSql+="delete from tbm_tra where co_tra="+objRecHum03.txtCodTra.getText();
                                           stmLoc.executeUpdate(strSql);
                                           con.commit();*/
                                       }
                                       new RRHHDao(objUti, objParSis).callServicio9();
                                   }
//                               }
                           }
                        }
                    }
                }
            } catch (TuvalUtilitiesException ex) {
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = ex.getMessage();
                JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                objRecHum03.txtIde.requestFocus();
                objRecHum03.txtIde.selectAll();
                blnOk=false;
                try{
                    con.rollback();
                    consec.rollback();
                }catch(Throwable ignore){}                
            }catch(SQLException ex){
                try{
                    con.rollback();
                    if(consec!=null){
                        consec.rollback();
                    }
                    objUti.mostrarMsgErr_F1(objRecHum03, ex);
                    blnOk=false;
                }catch(Throwable ignore){}
            
            } catch (Exception ex) {
                try{
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
                con.rollback();
                consec.rollback();
                blnOk=false;
                }catch(Throwable ignore){}
            }finally{
                try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
                try{con.close();con=null;}catch(Throwable ignore){}
            }
            return blnOk;
        }
        
        private boolean crearCuentaCobrarEmpleado(java.sql.Connection con){
            
            boolean blnRes=false;
            
            try{
//                con.setAutoCommit(false);
                if (con!=null)
                {
                    if (insertarCab(con)){
                        if(insertaTbmSalCta(con)){
                            if(insertaTbmSalCtaEfe(con)){
                                if(actualizaTbrCtaTipDocUsr(con)){
//                                    con.commit();
                                    blnRes=true;
                                }else{
                                    con.rollback();
                                }
                        }
                        else
                            con.rollback();
                    }
                    else{
                        con.rollback();
                    }
                }
                else
                    con.rollback();
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
        return blnRes;
        }
        
        
        
        private boolean actualizaTbrCtaTipDocUsr(java.sql.Connection con){
            boolean blnRes=true;
            String strSQL="";
            java.sql.Statement stm = null;
            java.sql.Statement stmAux = null;
            java.sql.ResultSet rst = null;
            
             try
            {
                if (con!=null)
                {
                    ArrayList<Integer> arrCoUsr = obtenerUsuariosContables(con);
                    stm=con.createStatement();
                    
                    if(arrCoUsr.size()>0){
                        
                        for(Iterator<Integer> it = arrCoUsr.iterator(); it.hasNext();){
                            strSQL="";
                            strSQL+="INSERT INTO tbr_ctatipdocusr(";
                            strSQL+=" co_emp, co_loc, co_tipdoc, co_usr, co_cta)";
                            strSQL+=" VALUES (";
                            strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                            strSQL+=" " + objParSis.getCodigoLocal() + ",";
                            strSQL+=" 192 " + ",";
                            strSQL+=" " + it.next().toString() + ",";                            
                            strSQL+=" " + intCodCta + "";
                            strSQL+=" );";
                            //System.out.println("insertTbr_ctatipdocusr: " + strSQL);
                            stm.executeUpdate(strSQL);
                        }
                    }
                }
            }catch (java.sql.SQLException e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }finally{
                try{stm.close();stm=null;}catch(Throwable ignore){}
                try{rst.close();rst=null;}catch(Throwable ignore){}
            }
            
            return blnRes;
        }
        
        private ArrayList<Integer> obtenerUsuariosContables(java.sql.Connection con){
            boolean blnRes=true;
            String strSQL="";
            java.sql.Statement stm = null;
            java.sql.Statement stmAux = null;
            java.sql.ResultSet rst = null;
            ArrayList<Integer> arrCoUsr = null;
            try
            {
                if (con!=null)
                {
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT * FROM tbm_usr \n ";
                    strSQL+="WHERE co_grpusr = 4 \n ";
                    strSQL+="AND st_reg = 'A'";

                    rst=stm.executeQuery(strSQL);
                    arrCoUsr = new ArrayList<Integer>();

                    while(rst.next()){
                        arrCoUsr.add(rst.getInt("co_usr"));
                    }
                }
            }catch (java.sql.SQLException e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }finally{
                try{stm.close();stm=null;}catch(Throwable ignore){}
                try{stmAux.close();stmAux=null;}catch(Throwable ignore){}
                try{rst.close();rst=null;}catch(Throwable ignore){}
            }
            
            return arrCoUsr;
        }
        
        /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection con)
    {
        String strSQL="";
        int intCodEmp, intCodUsr, intUltReg;
        java.sql.Statement stm = null;
        java.sql.Statement stmAux = null;
        java.sql.ResultSet rst = null;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                stmAux=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_cta)";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
//                txtCodSis.setText("" + intUltReg);
                strCodSis=String.valueOf(intUltReg);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                intCodCta=intUltReg;
                
                strSQL="";
                strSQL+="select max(tx_niv5::integer)+1 as secCta";
                strSQL+=" from tbm_placta";
                strSQL+=" where tx_codcta like '1.01.04.01%'";
                strSQL+=" and co_emp="+objParSis.getCodigoEmpresa();
                rst=stmAux.executeQuery(strSQL);
                String strNiv5="";
                if(rst.next()){
                    strNiv5=rst.getString("secCta");
                    strNumCta="1.01.04.01."+strNiv5;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_plaCta (co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+=", tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+=", tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp; //co_emp
                    strSQL+=", " + intUltReg; //co_cta
                    strSQL+=", " + objUti.codificar(""); //tx_codAlt
                    strSQL+=", " + objUti.codificar(objRecHum03.txtApeTra.getText() + " " + objRecHum03.txtNomTra.getText()); //tx_desLar
                    if(objParSis.getCodigoEmpresa()==1){
                        intCodCtaPad=79;
                    }else if(objParSis.getCodigoEmpresa()==2){
                        intCodCtaPad=33;
                    }
                    else if(objParSis.getCodigoEmpresa()==4){
                        intCodCtaPad=77;
                    }
                    strSQL+=", " + intCodCtaPad; //ne_pad
                    strSQL+=", 5 "; //ne_niv
                    strSQL+=", " + objUti.codificar("1"); ; //tx_niv1
                    strSQL+=", " + objUti.codificar("01"); //tx_niv2
                    strSQL+=", " + objUti.codificar("04"); //tx_niv3
                    strSQL+=", " + objUti.codificar("01"); //tx_niv4
                    strSQL+=", " + objUti.codificar(strNiv5); //tx_niv5
                    strSQL+=", " + objUti.codificar(""); //tx_niv6
                    strSQL+=", " + objUti.codificar(strNumCta); //tx_codCta
    //                strSQL+=", " + (optCtaCab.isSelected()?"'C'":"'D'"); //tx_tipCta
    //                strSQL+=", " + (optNatDeu.isSelected()?"'D'":"'H'"); //tx_natCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'D'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
    //                if(chkCtaEfe.isSelected())
    //                    strSQL+=", 'S'"; //st_ctaFluEfe
    //                else
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar("Cuenta creada automáticamente por sistemas cuando ingresa un empleado nuevo."); //tx_obs1
                    strSQL+=", " + objUti.codificar(""); //tx_obs2
                    strSQL+=", '" + "A" + "'"; //st_reg
                    String strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
//                    stm.close();
//                    stm=null;
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
        }finally{
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{stmAux.close();stmAux=null;}catch(Throwable ignore){}
            try{rst.close();rst=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    private boolean insertaTbmSalCta(java.sql.Connection con){
        boolean blnRes=true;
        int intAni=0;
        String strMes="";
        double dblSal=0.00;
        String strSQL="";
        java.sql.Statement stm=null;
        java.sql.ResultSet rst=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" select extract('year' FROM CURRENT_TIMESTAMP) as anio";
                rst=stm.executeQuery(strSQL);
                while (rst.next()){
                    intAni=rst.getInt("anio");
                }
                for(int i=1; i<=12; i++){
                    strMes=(i<=9?"0"+i:""+i);
                    strSQL="";
                    strSQL+="INSERT INTO tbm_salcta(";
                    strSQL+=" co_emp, co_cta, co_per, nd_salcta, st_regrep, ne_pad,tx_codcta,ne_niv,tx_deslar)";
                    strSQL+=" VALUES(";
                    strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                    strSQL+="" + strCodSis + ",";
                    strSQL+="" + intAni + "" + strMes + ",";
                    strSQL+="" + dblSal + ",";
                    strSQL+="'I',";
                    strSQL+=intCodCtaPad;
                    strSQL+=", " + objUti.codificar(strNumCta); //tx_codCta
                    strSQL+=", 5 "; //ne_niv
                    strSQL+=", " + objUti.codificar(objRecHum03.txtApeTra.getText() + " " + objRecHum03.txtNomTra.getText()); //tx_desLar                    
                    strSQL+=")";
                    //System.out.println("SQL DE insertaCuentasTbmSalCta: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
//                stm.close();
//                stm=null;
//                rst.close();
//                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{rst.close();rst=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    private boolean insertaTbmSalCtaEfe(java.sql.Connection con){
        boolean blnRes=true;
        int intAni=0;
        String strMes="";
        double dblSal=0.00;
        String strSQL="";
        java.sql.Statement stm=null;
        java.sql.ResultSet rst=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" select extract('year' FROM CURRENT_TIMESTAMP) as anio";
                rst=stm.executeQuery(strSQL);
                while (rst.next()){
                    intAni=rst.getInt("anio");
                }

                for(int i=1; i<=12; i++){
                    strMes=(i<=9?"0"+i:""+i);
                    strSQL="";
                    strSQL+="INSERT INTO tbm_salctaEfe(";
                    strSQL+=" co_emp, co_cta, co_per, nd_salcta, st_regrep)";//el campo ne_numdiaesp no lo coloque porq tiene default '0'
                    strSQL+=" VALUES(";
                    strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                    strSQL+="" + strCodSis + ",";
                    strSQL+="" + intAni + "" + strMes + ",";
                    strSQL+="" + dblSal + ",";
                    strSQL+="'I'";
                    strSQL+=")";
                    //System.out.println("insertaTbmSalCtaEfe: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
//                stm.close();
//                stm=null;
//                rst.close();
//                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{rst.close();rst=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    private boolean actualizaCtaCobrarEmp(java.sql.Connection con){
        boolean blnRes=true;
        String strSQL="";
        java.sql.Statement stm=null;
        java.sql.ResultSet rst=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL+="update tbm_suetra set co_cta = " + strCodSis;
                strSQL+=" where co_emp = " + objParSis.getCodigoEmpresa();
                strSQL+=" and co_tra = " + objRecHum03.txtCodTra.getText();
                strSQL+=" and co_rub in (9,10)";
                //System.out.println("actualizaTbmSueTra: " + strSQL);
                stm.executeUpdate(strSQL);
            }
        }catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{rst.close();rst=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
      
        private boolean actualizarPlantillaEmpleado(java.sql.Connection con){
            boolean blnRes=true;
            java.sql.Statement stmLoc=null;
            java.sql.ResultSet rstDetPla = null;
            java.sql.Statement stmUpdSueTra = null;
            try{
                
                String strSql="";
                stmLoc=con.createStatement();
                
                strSql+="select * from tbm_detPlaRolPag where co_emp = " + objParSis.getCodigoEmpresa();
                strSql+=" and co_pla = " + objRecHum03.jCmbPla.getSelectedIndex();
                strSql+=" order by co_rub asc";
                rstDetPla = stmLoc.executeQuery(strSql);

                while(rstDetPla.next()){

                    strSql="";
                    strSql+="update tbm_suetra set co_cta = " + rstDetPla.getString("co_cta") + " where co_tra = " + objRecHum03.txtCodTra.getText();
                    strSql+=" and co_rub = " + rstDetPla.getString("co_rub") + " and co_emp = " + rstDetPla.getString("co_emp");
                    stmUpdSueTra = con.createStatement();
                    stmUpdSueTra.executeUpdate(strSql);

                }
            }
            
            catch(Exception ex){
                ex.printStackTrace();
                blnRes=false;
            }finally{
                try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
                try{rstDetPla.close();rstDetPla=null;}catch(Throwable ignore){}
                try{stmUpdSueTra.close();stmUpdSueTra=null;}catch(Throwable ignore){}
            }
            
            return blnRes;
        }

        public boolean consultar() {
            boolean blnOk = false;

            try {
            	sincronizarPojFra();
                tbm_trapar = (Tbm_tra) tbm_tra.clone();
                lisTbm_tra = objRecHum03BO.consultarLisPag(tbm_trapar, 0, 0, objParSis);
                if(lisTbm_tra!=null){
                    intPagAct = intPagFin = lisTbm_tra.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_tra = lisTbm_tra.get(intPagAct);
                    consultarTabRef();
                    sincronizarFraPoj();
                    sincronizarFraPojHorTra();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_tra = null;
                }else{
                    tbm_tra = null;
                    tbm_tra = new Tbm_tra();
                    tbm_ciu = null;
                    tbm_ciu = new Tbm_ciu();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;
            //System.out.println("ZafRecHum03BEAN.modificar");
            try {
                if(validaCtaTipCta()){
                    
                    sincronizarPojFra();
                    try {
                      //  if(getVerId().verificarId(tbm_tra.getStrTx_ide())){  /* José Marín M. 21-Nov-2014*/
                            if(validarCorEle()){
                                objRecHum03BO.actualizarTra(tbm_tra, lisTbm_contra, lisTbm_contrabk, lisDetDocDig, lisTbm_docdigtrabk, strNomPan, objParSis);
                                consultarTabRef();
                                sincronizarFraPoj();
        //////                        /*objRecHum03.tblRep.setValueAt("M", 0, INT_REP_LINEA);
        //////                        if(grabarHorTraEmp()){
                                blnOk = true;
        //////                        }*/
                            }
                        //} /* José Marín M. 21-Nov-2014 */ 
                    }catch (ArrayIndexOutOfBoundsException ex) {
                        if(validarCorEle()){
                                objRecHum03BO.actualizarTra(tbm_tra, lisTbm_contra, lisTbm_contrabk, lisDetDocDig, lisTbm_docdigtrabk, strNomPan, objParSis);
                                consultarTabRef();
                                sincronizarFraPoj();
        //////                        /*objRecHum03.tblRep.setValueAt("M", 0, INT_REP_LINEA);
        //////                        if(grabarHorTraEmp()){
                                blnOk = true;
        //////                        }*/
                            }
                    }
                    
                }
            } catch (TuvalUtilitiesException ex) {
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            } 
            catch (Exception ex) {
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_tra.getIntCo_tra()>0){
                    tbm_tra.setStrSt_reg("E");
                    objRecHum03BO.actualizarTra(tbm_tra, lisTbm_contra, null, lisDetDocDig, null, strNomPan, objParSis);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;
            try {
                if(tbm_tra.getIntCo_tra()>0){
//                    if(validaFechaRealFinContrato()){
                        tbm_tra.setStrSt_reg("I");
                        //objRecHum03BO.actualizarTra(tbm_tra, lisTbm_contra, null, lisDetDocDig, null, strNomPan, objParSis);
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        //System.out.println(objRecHum03.dtpFecReaFinCon.getText());
                        if(objRecHum03.dtpFecReaFinCon.getText().equals("  /  /    ")){
                            objRecHum03.tabGen.setSelectedIndex(3);
                            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Finalización real del contrato</FONT> es obligatorio al momento de anular un empleado.<BR>Escriba o seleccione una Finalización real del contrato y vuelva a intentarlo.</HTML>");
                            objRecHum03.dtpFecReaFinCon.requestFocus();
                            //objRecHum03.dtpFecReaFinCon.**
                            return false;
                        }else{
                            String[] strFeReaFinCon=objRecHum03.dtpFecReaFinCon.getText().split("/");
                            Calendar calFeReaFinCon = Calendar.getInstance();
                            calFeReaFinCon.set(Calendar.YEAR, Integer.valueOf(strFeReaFinCon[2]));
                            calFeReaFinCon.set(Calendar.MONTH, Integer.valueOf(strFeReaFinCon[1])-1);
                            calFeReaFinCon.set(Calendar.DATE, Integer.valueOf(strFeReaFinCon[0]));
                            Date date = calFeReaFinCon.getTime();
                            tbm_tra.setDatFe_FinReaCon(date);
                            objRecHum03BO.anular(tbm_tra, objParSis);
                            Calendar cal = Calendar.getInstance();
                            Librerias.ZafRecHum.ZafVenFun.ZafVenFun zafVenFun = new ZafVenFun(objParSis, objUti, this);
                            java.sql.Connection con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                            if(con!=null){
                                con.setAutoCommit(false);
                                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                                    if(zafVenFun.ZafVenAnlTra(con, objRecHum03.txtIde.getText().toString().trim())){
                                        if(zafVenFun.ZafVenAmtPrgSueAnl(con, (cal.get(Calendar.MONTH)+1), tbm_tra.getIntCo_tra(), objParSis.getCodigoEmpresa()) ){
                                            if(enviarCorEle(2)){
                                                blnOk = true;
                                                con.commit();
                                                con.close();
                                                con=null;
                                            }
                                        }
                                    }
                                }
                            }
                            mostrarEstado();
                        }
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            }

            return blnOk;
        }
        
        private boolean enviarCorEle(int intOp){
            System.out.println("enviarCorEle");
            boolean blnRes=true;
            String strSbjMsg=null;
            int intCodCfgCorEleAdmin=20;  //Notificación de RRHH - Admin
            int intCodCfgCorEleAviso=21;  //Notificación de RRHH: Aviso Entrada/Salida
            int intCodLocGrp=1;           //Código de Local de Grupo
            try{
                //String strCorEleTo="lsc@tuvalsa.com;vicepresidencia@tuvalsa.com;gerenciageneral@tuvalsa.com;contador@tuvalsa.com;administracion@tuvalsa.com";
                //Subject de Correo
                if(intOp==1){
                    strSbjMsg="NOTIFICACIONES RRHH - AVISO DE ENTRADA";
                }
                else if(intOp==2){
                    strSbjMsg="NOTIFICACIONES RRHH - AVISO DE SALIDA";
                }            

                //Mensaje de Correo
                String strMsg = "";
                strMsg+="<HTML> <BODY> <BR/><BR/><TR>Estimados Usuarios,</TR><BR/><BR/><BR/>";
                if(intOp==1){
                    strMsg+="<TR>Se les informa que se ha procedido a realizar el aviso de entrada:</TR><BR/><BR/>";
                }
                else if(intOp==2){
                    strMsg+="<TR>Se les informa que se ha procedido a realizar el aviso de salida:</TR><BR/><BR/>";
                }
                strMsg+="<TABLE BORDER=3 CELLSPACING=5 WIDTH=500> ";
                strMsg+= " <TR> <th><FONT COLOR=\"black\"> EMPLEADO: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.txtApeTra.getText() + " " + objRecHum03.txtNomTra.getText() + " </FONT></th>";
                strMsg+= " <TR> <th><FONT COLOR=\"black\"> EMPRESA: </FONT></th> <th><FONT COLOR=\"black\"> " + objParSis.getNombreEmpresa() + " </FONT></th>";
                strMsg+= " <TR> <th><FONT COLOR=\"black\"> CARGO LABORAL: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.txtNomCarLab.getText() + " </FONT></th>";
                strMsg+= " <TR> <th><FONT COLOR=\"black\"> DEPARTAMENTO: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.txtNomDep.getText() + " </FONT></th>";
                strMsg+= " <TR> <th><FONT COLOR=\"black\"> OFICINA: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.txtNomOfi.getText() + " </FONT></th>";
                strMsg+= " <TR> <th><FONT COLOR=\"black\"> INICIO DE CONTRATO: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.dtpFecIniCon.getText() + " </FONT></th>";
                if(intOp==1){
                    strMsg+= " <TR> <th><FONT COLOR=\"black\"> PERIODO DE PRUEBA: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.dtpFecFinPerPru.getText() + " </FONT></th>";
                }
                else if(intOp==2){
                    strMsg+= " <TR> <th><FONT COLOR=\"black\"> FIN DE CONTRATO: </FONT></th> <th><FONT COLOR=\"black\"> " + objRecHum03.dtpFecReaFinCon.getText() + " </FONT></th>";
                }

                ///////////Corregir tildes/////////
                if(strMsg.indexOf("ó")!=-1){
                    strMsg=strMsg.replace("ó", "o");
                }
                if(strMsg.indexOf("á")!=-1){
                    strMsg=strMsg.replace("á", "a");
                }
                if(strMsg.indexOf("é")!=-1){
                    strMsg=strMsg.replace("é", "e");
                }
                if(strMsg.indexOf("í")!=-1){
                    strMsg=strMsg.replace("í", "i");
                }
                if(strMsg.indexOf("ú")!=-1){
                    strMsg=strMsg.replace("ú", "u");
                }
                ////////////////////////////////////
                if(!objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresaGrupo(), intCodLocGrp, intCodCfgCorEleAviso, strMsg, strSbjMsg )){
                    System.out.println("No se envio correo");
                }
                
                if(!objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresaGrupo(), intCodLocGrp, intCodCfgCorEleAdmin, strMsg, strSbjMsg )){
                    System.out.println("No se envio correo al usuario admin");
                }                
                
            }catch(Exception Evt){
                blnRes = false;
                Evt.printStackTrace();
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
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
            boolean blnOk = verificarCamReg();

            if(blnOk){
                tbm_tra = null;
                tbm_tra = new Tbm_tra();
                lisTbm_contra = null;
                lisTbm_docdigtra = null;
                lisTbm_contra = new ArrayList<Tbm_contra>();
                lisTbm_docdigtra = new ArrayList<Tbm_docdigtra>();
                lisTbm_contrabk = null;
                lisTbm_docdigtrabk = null;
                lisTbm_contrabk = new ArrayList<Tbm_contra>();
                lisTbm_docdigtrabk = new ArrayList<Tbm_docdigtra>();
                tbm_ciu = null;
                tbm_ciu = new Tbm_ciu();
                tbm_var = null;
                tbm_var = new Tbm_var();
                sincronizarFraPoj();
                objRecHum03.zafTblModCon.removeAllRows();
                objRecHum03.zafTblModDoc.removeAllRows();
                objRecHum03.zafTblModRep.removeAllRows();
                Vector vecRegCon = new Vector();
                if(objRecHum03.lisTbm_hortra!=null){
                    Vector vecTblCon = new Vector();
        //            for(Tbm_hortra tmp:lisTbm_hortra){

                        vecRegCon.add(objRecHum03.INT_REP_LINEA,"");
                        vecRegCon.add(objRecHum03.INT_REP_CODTRA,"");
                        vecRegCon.add(objRecHum03.INT_REP_APETRA,"");
                        vecRegCon.add(objRecHum03.INT_REP_NOMTRA,"");
                        vecRegCon.add(objRecHum03.INT_REP_CHKHORFIJ,"");
                        for(Tbm_hortra tmp:objRecHum03.lisTbm_hortra){
                            vecRegCon.add(objRecHum03.INT_REP_CHKHORFIJ+1,"");
                        }
        //            }
                    vecTblCon.add(vecRegCon);
                    if(!vecTblCon.isEmpty()){
                        objRecHum03.zafTblModRep.setData(vecTblCon);
                        objRecHum03.tblRep.setModel(objRecHum03.zafTblModRep);
                    }
                }
//                objRecHum03.zafTblModRep.removeAllRows();
                objRecHum03.dtpFecNac.setText(null);
                objRecHum03.dtpFecIniCon.setText(null);
                objRecHum03.dtpFecFinPerPru.setText(null);
                objRecHum03.dtpFecFinCon.setText(null);
                objRecHum03.dtpFecReaFinCon.setText(null);
            }

            return blnOk;
        }

        @Override
        public boolean beforeClickInsertar(){
            return verificarCamReg();
        }

        @Override
        public boolean beforeClickEliminar(){
            return verificarCamReg();
        }

        @Override
        public boolean beforeClickAnular(){
            return verificarCamReg();
        }

        public boolean beforeInsertar() {
            return validarCamObl()&&validarCamNum();
        }

        public boolean beforeConsultar() {
            return validarCamNum();
        }

        public boolean beforeModificar() {
            return validarCamObl()&&validarCamNum();
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAnular() {
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
            setEstado('w');
            blnMod = false;
            blnActChkMod = false;
            return true;
        }

        public boolean afterConsultar() {
            if(tbm_tra!=null){
                mostrarEstado();
            }
            return true;
        }

        public boolean afterModificar() {
            blnMod = false;
            blnActChkMod = false;
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
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

    }

    public class ZafTableAdapterButVcoRel extends ZafTableAdapter{
        @Override
        public void beforeEdit(ZafTableEvent evt) {}
        @Override
        public void afterEdit(ZafTableEvent evt) {}
        @Override
        public void beforeConsultar(ZafTableEvent evt) {
            vcoTipFamCon.limpiar();
        }
        @Override
        public void afterConsultar(ZafTableEvent evt) {
            if (vcoTipFamCon.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                objRecHum03.tblCon.setValueAt(Integer.parseInt(vcoTipFamCon.getValueAt(1)),objRecHum03.tblCon.getSelectedRow(),ZafRecHum03.INT_CON_CODREL);
                objRecHum03.tblCon.setValueAt(!vcoTipFamCon.getValueAt(3).equals("N"),objRecHum03.tblCon.getSelectedRow(),ZafRecHum03.INT_CON_CHKCARFAM);
            }
        }
        @Override
        public void actionPerformed(ZafTableEvent evt) {}
     }

    public class ZafTableAdapterButVcoTipDoc extends ZafTableAdapter{
        @Override
        public void beforeEdit(ZafTableEvent evt) {}
        @Override
        public void afterEdit(ZafTableEvent evt) {}
        @Override
        public void beforeConsultar(ZafTableEvent evt) {
            vcoTipDocDig.limpiar();
        }
        @Override
        public void afterConsultar(ZafTableEvent evt) {
            if (vcoTipDocDig.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                objRecHum03.tblDoc.setValueAt(Integer.parseInt(vcoTipDocDig.getValueAt(1)),objRecHum03.tblDoc.getSelectedRow(),ZafRecHum03.INT_DOC_CODTIPDOC);
            }
        }
        @Override
        public void actionPerformed(ZafTableEvent evt) {}
     }

    public class ZafTableAdapterButFilCho extends ZafTableAdapter{
        JFileChooser fch = null;

        public ZafTableAdapterButFilCho() {
            fch = new JFileChooser();
        }

        @Override
        public void beforeEdit(ZafTableEvent evt) {}
        @Override
        public void afterEdit(ZafTableEvent evt) {}
        @Override
        public void beforeConsultar(ZafTableEvent evt) {}
        @Override
        public void afterConsultar(ZafTableEvent evt) {}
        @Override
        public void actionPerformed(ZafTableEvent evt) {
            int returnVal = fch.showOpenDialog(objRecHum03);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                objRecHum03.tblDoc.setValueAt(fch.getSelectedFile().getAbsolutePath(),objRecHum03.tblDoc.getSelectedRow(),ZafRecHum03.INT_DOC_NOMARCLOC);
            }
        }
    }

    public class ZafTableAdapterButVerDoc extends ZafTableAdapter{

        @Override
        public void beforeEdit(ZafTableEvent evt) {}
        @Override
        public void afterEdit(ZafTableEvent evt) {}
        @Override
        public void beforeConsultar(ZafTableEvent evt) {}
        @Override
        public void afterConsultar(ZafTableEvent evt) {}
        @Override
        public void actionPerformed(ZafTableEvent evt) {
            String strNomArc = null;
            
            try{
                if(lisDetDocDig!=null && !lisDetDocDig.isEmpty() && objRecHum03.tblDoc.getSelectedRow()<lisDetDocDig.size()){
                    strNomArc = lisDetDocDig.get(objRecHum03.tblDoc.getSelectedRow()).getTbm_docdigtra().getStrTx_nomarc();
                    //new ZafFileUtil().abrirArc(objParSis.getRutDocDig()+strNomArc);
                    String[] args = new String[2];
                    Tbm_docdigsisBO tbm_docdigsisBO = new Tbm_docdigsisBO(objParSis);
                    tbm_docdigsisBO.getRutDocDig(args);
                    String strIp = args[0];
                    //String strRutSer = args[1];
                    String strTmpFolder = System.getProperty("java.io.tmpdir");
                    String strRutSer = "/Zafiro"+args[1];
                    String[] strAuxIpSer=args[0].split("/");
                    //String strIpSer="172.16.1.2";
                    String strIpSer=strAuxIpSer[2];
                    new ZafFilUti().abrirArcSocket(strIpSer, strRutSer, strNomArc, strTmpFolder);
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Abriendo Archivo.\nPresione Aceptar al Completar.";
                    JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No hay documento que mostrar";
                    JOptionPane.showMessageDialog(objRecHum03,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(Exception ex){
                objUti.mostrarMsgErr_F1(objRecHum03, ex);
            }
        }
     }

    public class ZafTableAdapterCelEdiTxt extends ZafTableAdapter{
        @Override
        public void beforeEdit(ZafTableEvent evt) {}
        @Override
        public void afterEdit(ZafTableEvent evt) {}
        @Override
        public void beforeConsultar(ZafTableEvent evt) {}
        @Override
        public void afterConsultar(ZafTableEvent evt) {}
        @Override
        public void actionPerformed(ZafTableEvent evt) {}
    }

    public class ZafTblCelRenAdapterBut extends ZafTblCelRenAdapter{
        @Override
        public void beforeRender(ZafTblCelRenEvent evt){
            switch (objRecHum03.zafTblDocCelRenBut.getColumnRender()){
                case ZafRecHum03.INT_DOC_BUTVERDOC:
                    if(lisDetDocDig!= null && objRecHum03.tblDoc.getValueAt(objRecHum03.zafTblDocCelRenBut.getRowRender(), ZafRecHum03.INT_DOC_NOMTIPDOC)!=null){
                        objRecHum03.zafTblDocCelRenBut.setText("...");
                    }else{
                        objRecHum03.zafTblDocCelRenBut.setText("");
                    }
                    break;
                default :
                    objRecHum03.zafTblDocCelRenBut.setText("...");
                    break;
            }
        }
    }

    /**
     * Listener que indica si han habido cambios en el documento
     */
    public class ZafDocLis implements DocumentListener{
        public void changedUpdate(DocumentEvent evt){
            if(blnActChkMod){
                blnMod = true;
                blnActChkMod = false;
            }
        }

        public void insertUpdate(DocumentEvent evt){
            if(blnActChkMod){
                blnMod = true;
                blnActChkMod = false;
            }
        }

        public void removeUpdate(DocumentEvent evt){
            if(blnActChkMod){
                blnMod = true;
                blnActChkMod = false;
            }
        }
    }
    


}
