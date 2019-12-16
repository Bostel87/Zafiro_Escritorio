package RecursosHumanos.ZafRecHum23;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_depBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_dep;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenDep;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum23.
 * Envia datos al BO(Business Object)
 * @author Roberto Flores
 * Guayaquil 21/11/2011
 */
public class ZafRecHum23Bean {
    
    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_dep tbm_dep;                                    //Pojo
    private Tbm_dep tbm_deppar;                                 //Parametros
    private ZafRecHum23 zafRecHum23;                            //JInternalFrame manejado
    private Tbm_depBO tbm_depBO;                                //Conección a la Base de Datos
    private List<Tbm_dep> lisTbm_dep;                           //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenDep zafVenDep;                                //Ventana de consulta "Tipo de Contrato".
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento

    public ZafRecHum23Bean(ZafRecHum23 zafRecHum23, ZafParSis zafParSis) {
        try {
            this.zafRecHum23 = zafRecHum23;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_dep = new Tbm_dep();
            tbm_deppar = new Tbm_dep();
            tbm_depBO = new Tbm_depBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_dep = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenDep = new ZafVenDep(JOptionPane.getFrameForComponent(zafRecHum23), zafParSis, "Listado de Departamentos");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
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
    public void initTooBar(ZafRecHum23 zafRecHum23) {
        tooBar = new MiToolBar(zafRecHum23);
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum23,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
        boolean blnDesCor = false;

        if(zafRecHum23.txtDesCor.getText().length()==0){
            zafRecHum23.txtDesCor.requestFocus();
            zafRecHum23.txtDesCor.selectAll();
            strNomCam += "Descripción Corta";
            blnDesCor = true;
        }

        if(zafRecHum23.txtDesLar.getText().length()==0){
            zafRecHum23.txtDesLar.requestFocus();
            zafRecHum23.txtDesLar.selectAll();
            if(blnDesCor){
                strNomCam = " y ";
            }
            strNomCam += "Descripción Larga";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum23,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_dep.getIntCo_Dep()>0){
            zafRecHum23.txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
        }else{
            zafRecHum23.txtCodDep.setText("");
        }

        if(tbm_dep.getStrTx_DesCor()!=null){
            zafRecHum23.txtDesCor.setText(String.valueOf(tbm_dep.getStrTx_DesCor()));
        }else{
            zafRecHum23.txtDesCor.setText("");
        }
        
        if(tbm_dep.getStrTx_DesLar()!=null){
            zafRecHum23.txtDesLar.setText(String.valueOf(tbm_dep.getStrTx_DesLar()));
        }else{
            zafRecHum23.txtDesLar.setText("");
        }
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum23.txtCodDep.getText().length()>0){
            tbm_dep.setIntCo_Dep(Integer.parseInt(zafRecHum23.txtCodDep.getText()));
        }else{
            tbm_dep.setIntCo_Dep(0);
        }
        if(zafRecHum23.txtDesCor.getText().length()>0){
            tbm_dep.setStrTx_DesCor(zafRecHum23.txtDesCor.getText());
        }else{
            tbm_dep.setStrTx_DesCor(null);
        }
        if(zafRecHum23.txtDesLar.getText().length()>0){
            tbm_dep.setStrTx_DesLar(zafRecHum23.txtDesLar.getText());
        }else{
            tbm_dep.setStrTx_DesLar(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;

        try{
            zafVenDep.limpiar();
            zafVenDep.setCampoBusqueda(0);
            zafVenDep.setVisible(true);

            if (zafVenDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_dep.setIntCo_Dep(Integer.parseInt(zafVenDep.getValueAt(1)));
                zafRecHum23.txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodDep true habilita, false deshabilita txtCodDep
     * @param blnButCodDep true habilita, false deshabilita butCodDep
     * @param blnTxtDesCor true habilita, false deshabilita txtDesCor
     * @param blnTxtDesLar true habilita, false deshabilita txtDesLar
     */
    private void habilitarCom(boolean blnTxtCodDep, boolean blnButCodDep, boolean blnTxtDesCor, boolean blnTxtDesLar){
        zafRecHum23.txtCodDep.setEditable(blnTxtCodDep);
        zafRecHum23.butCodDep.setEnabled(blnButCodDep);
        zafRecHum23.txtDesCor.setEditable(blnTxtDesCor);
        zafRecHum23.txtDesCor.setEditable(blnTxtDesLar);
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum23,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_dep.getStrSt_Reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_dep.getStrSt_Reg().equals("E")){
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
            List<Tbm_dep> listmp = null;

            try{
                listmp = tbm_depBO.consultarLisPag(tbm_deppar, intPagAct, 1);
                if(listmp!=null){
                    tbm_dep = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
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
            tbm_dep = null;
            tbm_dep = new Tbm_dep();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true);
            zafRecHum23.txtDesCor.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            zafRecHum23.txtCodDep.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true);
            zafRecHum23.txtDesCor.requestFocus();
            zafRecHum23.txtDesCor.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafRecHum23);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafRecHum23);
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
                tbm_dep.setStrSt_Reg("A");

                tbm_depBO.grabar(tbm_dep);
                sincronizarFraPoj();
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
            }

            return blnOk;
        }

        public boolean consultar() {
            boolean blnOk = false;

            try {
            	sincronizarPojFra();
                tbm_deppar = (Tbm_dep) tbm_dep.clone();
                lisTbm_dep = tbm_depBO.consultarLisPag(tbm_deppar, 0, 0);
                if(lisTbm_dep!=null){
                    intPagAct = intPagFin = lisTbm_dep.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_dep = lisTbm_dep.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_dep = null;
                }else{
                    tbm_dep = null;
                    tbm_dep = new Tbm_dep ();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafRecHum23,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                tbm_depBO.actualizar(tbm_dep);
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_dep.getIntCo_Dep()>0){
                    tbm_dep.setStrSt_Reg("E");
                    tbm_depBO.actualizar(tbm_dep);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum23,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_dep.getIntCo_Dep()>0){
                    tbm_dep.setStrSt_Reg("I");
                    tbm_depBO.actualizar(tbm_dep);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum23,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum23, ex);
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
                tbm_dep = null;
                tbm_dep = new Tbm_dep();
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
            return validarCamObl();//&&validarCamNum()&&validarDat();
        }

        public boolean beforeConsultar() {
            return true;//validarCamNum();
        }

        public boolean beforeModificar() {
            return validarCamObl();//&&validarCamNum()&&validarDat();
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
            if(tbm_dep!=null){
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