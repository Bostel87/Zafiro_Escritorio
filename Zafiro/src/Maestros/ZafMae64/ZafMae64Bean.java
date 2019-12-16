
package Maestros.ZafMae64;


import Librerias.ZafMae.ZafMaeBo.Tbm_tipdocdigsisBO;
import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;
import Librerias.ZafMae.ZafMaeVen.ZafVenTipDocDig;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafMae64
 * Envia datos al BO(Business Object)
 * @author Carlos Lainez
 * Guayaquil 18/05/2011
 */
public class ZafMae64Bean {
    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_tipdocdigsis tbm_tipdocdigsis;                  //Pojo
    private Tbm_tipdocdigsis tbm_tipdocdigsispar;               //Parametros
    private ZafMae64 zafMae64;                                  //JInternalFrame manejado
    private Tbm_tipdocdigsisBO zafMae64BO;                              //Conección a la Base de Datos
    private List<Tbm_tipdocdigsis> lisTbm_tipdocdigsis;         //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenTipDocDig zafVenTipDocDig;                    //Ventana de consulta "Tipo de Documentos Digitales"
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento

    public ZafMae64Bean(ZafMae64 zafMae64, ZafParSis zafParSis) {
        try {
            this.zafMae64 = zafMae64;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_tipdocdigsis = new Tbm_tipdocdigsis();
            tbm_tipdocdigsispar = new Tbm_tipdocdigsis();
            zafMae64BO = new Tbm_tipdocdigsisBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_tipdocdigsis = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenTipDocDig = new ZafVenTipDocDig(JOptionPane.getFrameForComponent(zafMae64), zafParSis, "Listado de Tipos de Documentos Digitales");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafMae64, ex);
        }
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
     * @param zafMae64
     */
    public void initTooBar(ZafMae64 zafMae64) {
        tooBar = new MiToolBar(zafMae64);
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
            blnOk = (JOptionPane.showConfirmDialog(zafMae64,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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

        if(zafMae64.txtDesLar.getText().length()==0){
            zafMae64.txtDesLar.requestFocus();
            zafMae64.txtDesLar.selectAll();
            strNomCam = "Descripción larga";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafMae64,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
            zafMae64.txtCodTipDocDig.setText(String.valueOf(tbm_tipdocdigsis.getIntCo_tipdocdig()));
        }else{
            zafMae64.txtCodTipDocDig.setText("");
        }
        zafMae64.txtDesCor.setText(tbm_tipdocdigsis.getStrTx_descor());
        zafMae64.txtDesLar.setText(tbm_tipdocdigsis.getStrTx_deslar());
        zafMae64.txaObs.setText(tbm_tipdocdigsis.getStrTx_obs1());
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafMae64.txtCodTipDocDig.getText().length()>0){
            tbm_tipdocdigsis.setIntCo_tipdocdig(Integer.parseInt(zafMae64.txtCodTipDocDig.getText()));
        }else{
            tbm_tipdocdigsis.setIntCo_tipdocdig(0);
        }
        if(zafMae64.txtDesCor.getText().length()>0){
            tbm_tipdocdigsis.setStrTx_descor(zafMae64.txtDesCor.getText());
        }else{
            tbm_tipdocdigsis.setStrTx_descor(null);
        }
        if(zafMae64.txtDesLar.getText().length()>0){
            tbm_tipdocdigsis.setStrTx_deslar(zafMae64.txtDesLar.getText());
        }
        if(zafMae64.txaObs.getText().length()>0){
            tbm_tipdocdigsis.setStrTx_obs1(zafMae64.txaObs.getText());
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;

        try{
            zafVenTipDocDig.limpiar();
            zafVenTipDocDig.setCampoBusqueda(0);
            zafVenTipDocDig.setVisible(true);

            if (zafVenTipDocDig.getSelectedButton()==ZafVenTipDocDig.INT_BUT_ACE){
                tbm_tipdocdigsis.setIntCo_tipdocdig(Integer.parseInt(zafVenTipDocDig.getValueAt(1)));
                zafMae64.txtCodTipDocDig.setText(String.valueOf(tbm_tipdocdigsis.getIntCo_tipdocdig()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafMae64, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodDocDig true habilita, false deshabilita blnTxtCodDocDig
     * @param blnButCodDocDig true habilita, false deshabilita blnButCodDocDig
     * @param blnTxtDesCor true habilita, false deshabilita blnTxtDesCor
     * @param blnTxtDesLar true habilita, false deshabilita blnTxtDesLar
     * @param blnTxaObs true habilita, false deshabilita blnTxaObs
     */
    private void habilitarCom(boolean blnTxtCodDocDig, boolean blnButCodDocDig, boolean blnTxtDesCor, boolean blnTxtDesLar, boolean blnTxaObs){
        zafMae64.txtCodTipDocDig.setEditable(blnTxtCodDocDig);
        zafMae64.butCodTipDocDig.setEnabled(blnButCodDocDig);
        zafMae64.txtDesCor.setEditable(blnTxtDesCor);
        zafMae64.txtDesLar.setEditable(blnTxtDesLar);
        zafMae64.txaObs.setEditable(blnTxaObs);
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
            blnOk=(JOptionPane.showConfirmDialog(zafMae64,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_tipdocdigsis.getStrSt_reg().equals("E")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_tipdocdigsis.getStrSt_reg().equals("I")){
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
            List<Tbm_tipdocdigsis> listmp = null;

            try{
                listmp = zafMae64BO.consultarLisPag(tbm_tipdocdigsispar, intPagAct, 1);
                if(listmp!=null){
                    tbm_tipdocdigsis = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafMae64, ex);
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
            tbm_tipdocdigsis = null;
            tbm_tipdocdigsis = new Tbm_tipdocdigsis();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true, true);
            zafMae64.txtDesCor.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            zafMae64.txtCodTipDocDig.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true, true);
            zafMae64.txtDesCor.requestFocus();
            zafMae64.txtDesCor.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafMae64);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafMae64);
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
                tbm_tipdocdigsis.setStrSt_reg("A");

                zafMae64BO.grabarTipDoc(tbm_tipdocdigsis);
                sincronizarFraPoj();
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafMae64, ex);
            }

            return blnOk;
        }

        public boolean consultar() {
            boolean blnOk = false;

            try {
            	sincronizarPojFra();
                tbm_tipdocdigsispar = (Tbm_tipdocdigsis) tbm_tipdocdigsis.clone();
                lisTbm_tipdocdigsis = zafMae64BO.consultarLisPag(tbm_tipdocdigsispar, 0, 0);
                if(lisTbm_tipdocdigsis!=null){
                    intPagAct = intPagFin = lisTbm_tipdocdigsis.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_tipdocdigsis = lisTbm_tipdocdigsis.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_tipdocdigsis = null;
                }else{
                    tbm_tipdocdigsis = null;
                    tbm_tipdocdigsis = new Tbm_tipdocdigsis();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafMae64,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafMae64, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                zafMae64BO.actualizarTipDoc(tbm_tipdocdigsis);
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafMae64, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
                    tbm_tipdocdigsis.setStrSt_reg("I");
                    zafMae64BO.actualizarTipDoc(tbm_tipdocdigsis);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafMae64,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafMae64, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
                    tbm_tipdocdigsis.setStrSt_reg("E");
                    zafMae64BO.actualizarTipDoc(tbm_tipdocdigsis);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafMae64,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafMae64, ex);
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
                tbm_tipdocdigsis = null;
                tbm_tipdocdigsis = new Tbm_tipdocdigsis();
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
            return validarCamObl();
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            return validarCamObl();
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
            if(tbm_tipdocdigsis!=null){
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
