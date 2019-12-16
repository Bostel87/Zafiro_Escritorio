package RecursosHumanos.ZafRecHum01;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_comsecBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_comsec;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenComSec;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum01.
 * Envia datos al BO(Business Object)
 * @author Carlos Lainez
 * Guayaquil 24/03/2011
 */
public class ZafRecHum01Bean {

    private ZafParSis zafParSis;                               //Parámetros del Sistema
    private ZafUtil zafUti;                                    //Utilidades
    private Tbm_comsec tbm_comsec;                             //Pojo
    private Tbm_comsec tbm_comsecpar;                          //Parametros
    private ZafRecHum01 zafRecHum01;                           //JInternalFrame manejado
    private Tbm_comsecBO zafRecHum01BO;                        //Conecci�n a la Base de Datos
    private List<Tbm_comsec> lisTbm_comsec;                    //Lista con datos de consulta general
    private int intPagAct;                                     //Indice actual de los registros de la consulta
    private int intPagFin;                                     //Ultimo indice de los registros de la consulta
    private ZafVenComSec zafVenComSec;                         //Ventana de consulta "Comisiones Sectoriales".
    private MiToolBar tooBar;                                  //Barra de herramientas
    private boolean blnMod;                                    //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                              //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                     //Listener que indica si han habido cambios en el documento

    public ZafRecHum01Bean(ZafRecHum01 zafRecHum01, ZafParSis zafParSis) {
        try {
            this.zafRecHum01 = zafRecHum01;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_comsec = new Tbm_comsec();
            tbm_comsecpar = new Tbm_comsec();
            zafRecHum01BO = new Tbm_comsecBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_comsec = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenComSec = new ZafVenComSec(JOptionPane.getFrameForComponent(zafRecHum01), zafParSis, "Listado de Comisiones Sectoriales");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
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
    public void initTooBar(ZafRecHum01 zafRecHum01) {
        tooBar = new MiToolBar(zafRecHum01);
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum01,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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

        if(zafRecHum01.txtNomCar.getText().length()==0){
            zafRecHum01.txtNomCar.requestFocus();
            zafRecHum01.txtNomCar.selectAll();
            strNomCam = "Nombre del Cargo";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum01,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_comsec.getIntCo_comsec()>0){
            zafRecHum01.txtCodCom.setText(String.valueOf(tbm_comsec.getIntCo_comsec()));
        }else{
            zafRecHum01.txtCodCom.setText("");
        }
    	zafRecHum01.txtNomCar.setText(tbm_comsec.getStrTx_nomcomsec());
    	zafRecHum01.txaObs.setText(tbm_comsec.getStrTx_obs1());
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum01.txtCodCom.getText().length()>0){
            tbm_comsec.setIntCo_comsec(Integer.parseInt(zafRecHum01.txtCodCom.getText()));
        }else{
            tbm_comsec.setIntCo_comsec(0);
        }
        if(zafRecHum01.txtNomCar.getText().length()>0){
            tbm_comsec.setStrTx_nomcomsec(zafRecHum01.txtNomCar.getText());
        }else{
            tbm_comsec.setStrTx_nomcomsec(null);
        }
        if(zafRecHum01.txaObs.getText().length()>0){
            tbm_comsec.setStrTx_obs1(zafRecHum01.txaObs.getText());
        }else{
            tbm_comsec.setStrTx_obs1(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;
        
        try{
            zafVenComSec.limpiar();
            zafVenComSec.setCampoBusqueda(0);
            zafVenComSec.setVisible(true);

            if (zafVenComSec.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_comsec.setIntCo_comsec(Integer.parseInt(zafVenComSec.getValueAt(1)));
                zafRecHum01.txtCodCom.setText(String.valueOf(tbm_comsec.getIntCo_comsec()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodCom true habilita, false deshabilita txtCodCom
     * @param blnButComSec true habilita, false deshabilita butComSec
     * @param blnTxtCodCar true habilita, false deshabilita txtCodCar
     * @param blnTxtNomCar true habilita, false deshabilita txtNomCar
     * @param blnTxtMinSec true habilita, false deshabilita txtMinSec
     * @param blnTxaObs true habilita, false deshabilita txaObs
     */
    private void habilitarCom(boolean blnTxtCodCom, boolean blnButComSec, boolean blnTxtCodCar, boolean blnTxtNomCar, boolean blnTxtMinSec, boolean blnTxaObs){
        zafRecHum01.txtCodCom.setEditable(blnTxtCodCom);
        zafRecHum01.butComSec.setEnabled(blnButComSec);
        zafRecHum01.txtNomCar.setEditable(blnTxtNomCar);
        zafRecHum01.txaObs.setEditable(blnTxaObs);
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum01,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_comsec.getStrSt_reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_comsec.getStrSt_reg().equals("E")){
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
            List<Tbm_comsec> listmp = null;

            try{
                listmp = zafRecHum01BO.consultarLisPag(tbm_comsecpar, intPagAct, 1);
                if(listmp!=null){
                    tbm_comsec = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
            }
        }

        /**
         * Pagineo de Memoria. Permite recorrer una lista cargada previamente con los datos de la consulta.
         * Minimiza el riezgo de dejar conecciones, statements y cursores abiertos.
         * El espacio ocupado en memoria depende de la cantidad de registros que retorna la consulta.
         */
        /*private void pagineoMem(){
            tbm_comsec = lisTbm_comsec.get(intPagAct);
            sincronizarFraPoj();
            mostrarEstado();
            setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
        }*/

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
            tbm_comsec = null;
            tbm_comsec = new Tbm_comsec();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true, true, true);
            zafRecHum01.txtNomCar.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            zafRecHum01.txtCodCom.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true, true, true);
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafRecHum01);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafRecHum01);
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
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                tbm_comsec.setStrSt_reg("A");
                zafRecHum01BO.grabarComSec(tbm_comsec);
                sincronizarFraPoj();
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
            }

            return blnOk;
        }

        public boolean consultar() {
            boolean blnOk = false;
            
            try {
            	sincronizarPojFra();
                tbm_comsecpar = (Tbm_comsec) tbm_comsec.clone();
                lisTbm_comsec = zafRecHum01BO.consultarLisPag(tbm_comsecpar, 0, 0);
                if(lisTbm_comsec!=null){
                    intPagAct = intPagFin = lisTbm_comsec.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_comsec = lisTbm_comsec.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_comsec = null;
                }else{
                    tbm_comsec = null;
                    tbm_comsec = new Tbm_comsec();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafRecHum01,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                zafRecHum01BO.actualizarComSec(tbm_comsec);
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_comsec.getIntCo_comsec()>0){
                    tbm_comsec.setStrSt_reg("E");
                    zafRecHum01BO.actualizarComSec(tbm_comsec);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum01,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_comsec.getIntCo_comsec()>0){
                    tbm_comsec.setStrSt_reg("I");
                    zafRecHum01BO.actualizarComSec(tbm_comsec);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum01,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum01, ex);
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
                tbm_comsec = null;
                tbm_comsec = new Tbm_comsec();
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
            if(tbm_comsec!=null){
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