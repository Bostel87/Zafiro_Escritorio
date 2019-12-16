package RecursosHumanos.ZafRecHum02;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_tipconBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipcon;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTipCon;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum02.
 * Envia datos al BO(Business Object)
 * @author Carlos Lainez
 * Guayaquil 29/03/2011
 */
public class ZafRecHum02Bean {
    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_tipcon tbm_tipcon;                              //Pojo
    private Tbm_tipcon tbm_tipconpar;                           //Parametros
    private ZafRecHum02 zafRecHum02;                            //JInternalFrame manejado
    private Tbm_tipconBO zafRecHum02BO;                         //Conección a la Base de Datos
    private List<Tbm_tipcon> lisTbm_tipcon;                     //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenTipCon zafVenTipCon;                          //Ventana de consulta "Tipo de Contrato".
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento

    public ZafRecHum02Bean(ZafRecHum02 zafRecHum02, ZafParSis zafParSis) {
        try {
            this.zafRecHum02 = zafRecHum02;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_tipcon = new Tbm_tipcon();
            tbm_tipconpar = new Tbm_tipcon();
            zafRecHum02BO = new Tbm_tipconBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_tipcon = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenTipCon = new ZafVenTipCon(JOptionPane.getFrameForComponent(zafRecHum02), zafParSis, "Listado de Tipos de Contrato");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
        }
    }

    /**
     * @param blnMod the blnMod to set
     */
    public void setBlnMod(boolean blnMod){
        this.blnMod  = blnMod;
    }

    /**
     * @return the zafParSis
     */
    public ZafParSis getZafParSis() {
        return zafParSis;
    }

    /**
     * @param zafParSis the zafParSis to set
     */
    public void setZafParSis(ZafParSis zafParSis) {
        this.zafParSis = zafParSis;
    }

    /**
     * Inicializa la barra de herramientas
     * @param zafRecHum01
     */
    public void initTooBar(ZafRecHum02 zafRecHum02) {
        tooBar = new MiToolBar(zafRecHum02);
    }

    /**
     * @return the tooBar
     */
    public MiToolBar getTooBar() {
        return tooBar;
    }

    /**
     * @return the docLis
     */
    public DocLis getDocLis() {
        return docLis;
    }

    /**
     * Muestra un panel de consulta para cerrar la ventana
     * @return Retorna true si se desea cerrar o false caso contrario
     */
    public boolean cerrarIntFra(){
        boolean blnOk = true;

        if(blnMod){
            String strMsg = "Existen cambios sin guardar! \nEstá Seguro que desea cerrar este programa?";
            String strTit = "Mensaje del sistema Zafiro";
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum02,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        }

        return blnOk;
    }

    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
        String strNomCam = null;
        boolean blnOk = true;

        if(zafRecHum02.txtDes.getText().length()==0){
            zafRecHum02.txtDes.requestFocus();
            zafRecHum02.txtDes.selectAll();
            strNomCam = "Descripción";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum02,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_tipcon.getIntCo_tipcon()>0){
            zafRecHum02.txtCodTipCon.setText(String.valueOf(tbm_tipcon.getIntCo_tipcon()));
        }else{
            zafRecHum02.txtCodTipCon.setText("");
        }
        zafRecHum02.txtDes.setText(tbm_tipcon.getStrTx_deslar());
        if(tbm_tipcon.getIntNe_mescon()>0){
            zafRecHum02.txtMesCon.setText(String.valueOf(tbm_tipcon.getIntNe_mescon()));
        }else{
            zafRecHum02.txtMesCon.setText("");
        }
        if(tbm_tipcon.getIntNe_diapru()>0){
            zafRecHum02.txtDiaPru.setText(String.valueOf(tbm_tipcon.getIntNe_diapru()));
        }else{
            zafRecHum02.txtDiaPru.setText("");
        }
        zafRecHum02.txaObs.setText(tbm_tipcon.getStrTx_obs1());
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum02.txtCodTipCon.getText().length()>0){
            tbm_tipcon.setIntCo_tipcon(Integer.parseInt(zafRecHum02.txtCodTipCon.getText()));
        }else{
            tbm_tipcon.setIntCo_tipcon(0);
        }
        if(zafRecHum02.txtDes.getText().length()>0){
            tbm_tipcon.setStrTx_deslar(zafRecHum02.txtDes.getText());
        }else{
            tbm_tipcon.setStrTx_deslar(null);
        }
        if(zafRecHum02.txtMesCon.getText().length()>0){
            tbm_tipcon.setIntNe_mescon(Integer.parseInt(zafRecHum02.txtMesCon.getText()));
        }else{
            tbm_tipcon.setIntNe_mescon(0);
        }
        if(zafRecHum02.txtDiaPru.getText().length()>0){
            tbm_tipcon.setIntNe_diapru(Integer.parseInt(zafRecHum02.txtDiaPru.getText()));
        }else{
            tbm_tipcon.setIntNe_mescon(0);
        }
        if(zafRecHum02.txaObs.getText().length()>0){
            tbm_tipcon.setStrTx_obs1(zafRecHum02.txaObs.getText());
        }else{
            tbm_tipcon.setStrTx_obs1(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;

        try{
            zafVenTipCon.limpiar();
            zafVenTipCon.setCampoBusqueda(0);
            zafVenTipCon.setVisible(true);

            if (zafVenTipCon.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_tipcon.setIntCo_tipcon(Integer.parseInt(zafVenTipCon.getValueAt(1)));
                zafRecHum02.txtCodTipCon.setText(String.valueOf(tbm_tipcon.getIntCo_tipcon()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodTipCon true habilita, false deshabilita txtCodTipCon
     * @param blnButCodTipCon true habilita, false deshabilita butCodTipCon
     * @param blnTxtDes true habilita, false deshabilita txtDes
     * @param blnTxtMesCon true habilita, false deshabilita txtMesCon
     * @param blnTxtMesPru true habilita, false deshabilita txtMesPru
     * @param blnTxaObs true habilita, false deshabilita txaObs
     */
    private void habilitarCom(boolean blnTxtCodTipCon, boolean blnButCodTipCon, boolean blnTxtDes, boolean blnTxtMesCon, boolean blnTxtMesPru, boolean blnTxaObs){
        zafRecHum02.txtCodTipCon.setEditable(blnTxtCodTipCon);
        zafRecHum02.butCodTipCon.setEnabled(blnButCodTipCon);
        zafRecHum02.txtDes.setEditable(blnTxtDes);
        zafRecHum02.txtMesCon.setEditable(blnTxtMesCon);
        zafRecHum02.txtDiaPru.setEditable(blnTxtMesPru);
        zafRecHum02.txaObs.setEditable(blnTxaObs);
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
            if(zafRecHum02.txtMesCon.getText().length()>0){
                comTmp = zafRecHum02.txtMesCon;
                strCom = "Meses de contrato";
                Double.parseDouble(zafRecHum02.txtMesCon.getText());
            }
            if(zafRecHum02.txtDiaPru.getText().length()>0){
                comTmp = zafRecHum02.txtDiaPru;
                strCom = "Meses de prueba";
                Double.parseDouble(zafRecHum02.txtDiaPru.getText());
            }
        } catch(NumberFormatException ex){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = strCom+" debe contener sólo números.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum02,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            ((JTextField)comTmp).requestFocus();
            ((JTextField)comTmp).selectAll();
        }

        return blnOk;
    }

    /**
     * Valida que los datos ingresados sean correctos
     * @return Retorna true si los datos son correctos y false caso contrario
     */
    private boolean validarDat(){
        boolean blnOk = true;
        JComponent comTmp = null;
        String strCom = null;

        if(zafRecHum02.txtDiaPru.getText().length()>0){
            if(zafRecHum02.txtMesCon.getText().length()<=0){
                comTmp = zafRecHum02.txtMesCon;
                strCom = "Falta ingresar Meses de contrato.\nLlene el campo y vuelva a intentarlo";
                blnOk = false;
            }else{
                if(Double.parseDouble(zafRecHum02.txtDiaPru.getText())>(30*Double.parseDouble(zafRecHum02.txtMesCon.getText()))){
                    comTmp = zafRecHum02.txtDiaPru;
                    strCom = "Días de prueba no deben ser mayores a los meses de contrato.\nLlene el campo y vuelva a intentarlo";
                    blnOk = false;
                }
            }
        }
        if(!blnOk){
            String strTit = "Mensaje del sistema Zafiro";
            JOptionPane.showMessageDialog(zafRecHum02,strCom,strTit,JOptionPane.INFORMATION_MESSAGE);
            ((JTextField)comTmp).requestFocus();
            ((JTextField)comTmp).selectAll();
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum02,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        }
        if(blnOk){
            blnMod = false;
            blnActChkMod = false;
        }

        return blnOk;
    }

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    public class MiToolBar extends ZafToolBar{

        public MiToolBar(JInternalFrame objIntFra){
            super(objIntFra, zafParSis);
        }

        private void mostrarEstado(){
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if(tbm_tipcon.getStrSt_reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_tipcon.getStrSt_reg().equals("E")){
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                }else{
                    setEstadoRegistro("");
                }
            }
        }

        /**
         * Pagineo de Base. Permite consultar registros página a página según consulta.
         * Optimiza espacio de memoria al tener un registro cargado a la vez.
         * Minimiza el riezgo de dejar conecciones, statements y cursores abiertos.
         */
        private void pagineoBas(){
            List<Tbm_tipcon> listmp = null;

            try{
                listmp = zafRecHum02BO.consultarLisPag(tbm_tipconpar, intPagAct, 1);
                if(listmp!=null){
                    tbm_tipcon = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
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
            tbm_tipcon = null;
            tbm_tipcon = new Tbm_tipcon();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true, true, true);
            zafRecHum02.txtDes.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            zafRecHum02.txtCodTipCon.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true, true, true);
            zafRecHum02.txtDes.requestFocus();
            zafRecHum02.txtDes.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafRecHum02);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafRecHum02);
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        @SuppressWarnings("static-access")
        public boolean insertar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                tbm_tipcon.setStrSt_reg("A");
                zafRecHum02BO.grabarTipCon(tbm_tipcon);
                sincronizarFraPoj();
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
            }

            return blnOk;
        }

        public boolean consultar() {
            boolean blnOk = false;

            try {
            	sincronizarPojFra();
                tbm_tipconpar = (Tbm_tipcon) tbm_tipcon.clone();
                lisTbm_tipcon = zafRecHum02BO.consultarLisPag(tbm_tipconpar, 0, 0);
                if(lisTbm_tipcon!=null){
                    intPagAct = intPagFin = lisTbm_tipcon.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_tipcon = lisTbm_tipcon.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_tipcon = null;
                }else{
                    tbm_tipcon = null;
                    tbm_tipcon = new Tbm_tipcon ();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafRecHum02,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                zafRecHum02BO.actualizarComSec(tbm_tipcon);
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_tipcon.getIntCo_tipcon()>0){
                    tbm_tipcon.setStrSt_reg("E");
                    zafRecHum02BO.actualizarComSec(tbm_tipcon);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum02,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_tipcon.getIntCo_tipcon()>0){
                    tbm_tipcon.setStrSt_reg("I");
                    zafRecHum02BO.actualizarComSec(tbm_tipcon);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum02,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum02, ex);
            }

            return blnOk;
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
                tbm_tipcon = null;
                tbm_tipcon = new Tbm_tipcon();
                sincronizarFraPoj();
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
            return validarCamObl()&&validarCamNum()&&validarDat();
        }

        public boolean beforeConsultar() {
            return validarCamNum();
        }

        public boolean beforeModificar() {
            return validarCamObl()&&validarCamNum()&&validarDat();
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
            if(tbm_tipcon!=null){
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

    /**
     * Listener que indica si han habido cambios en el documento
     */
    public class DocLis implements DocumentListener{
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