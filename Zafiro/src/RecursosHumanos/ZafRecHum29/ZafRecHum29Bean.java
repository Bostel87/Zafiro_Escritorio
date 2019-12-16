package RecursosHumanos.ZafRecHum29;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_MotHorSupExtBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_MotHorSupExt;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotHorSupExt;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum29.
 * Envia datos al BO(Business Object)
 * @author Roberto Flores
 * Guayaquil 24/03/2012
 */
public class ZafRecHum29Bean {
    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_MotHorSupExt tbm_motHorSupExt;                  //Pojo
    private Tbm_MotHorSupExt tbm_motHorSupExtpar;               //Parametros
    private ZafRecHum29 zafRecHum29;                            //JInternalFrame manejado
    private Tbm_MotHorSupExtBO tbm_motHorSupExtBO;              //Conección a la Base de Datos
    private List<Tbm_MotHorSupExt> lisTbm_motHorSupExt;         //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenMotHorSupExt zafVenMotHorSupExt;              //Ventana de consulta "Motivos de horas suplementarias/extraordinarias".
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento

    public ZafRecHum29Bean(ZafRecHum29 zafRecHum29, ZafParSis zafParSis) {
        try {
            this.zafRecHum29 = zafRecHum29;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_motHorSupExt = new Tbm_MotHorSupExt();
            tbm_motHorSupExtpar = new Tbm_MotHorSupExt();
            tbm_motHorSupExtBO = new Tbm_MotHorSupExtBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_motHorSupExt = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenMotHorSupExt = new ZafVenMotHorSupExt(JOptionPane.getFrameForComponent(zafRecHum29), zafParSis, "Listado de Motivos de horas suplementarias/extraordinarias");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
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
    public void initTooBar(ZafRecHum29 zafRecHum29) {
        tooBar = new MiToolBar(zafRecHum29);
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum29,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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

        if(zafRecHum29.txtDesCorSupExt.getText().length()==0){
            zafRecHum29.txtDesCorSupExt.requestFocus();
            zafRecHum29.txtDesCorSupExt.selectAll();
            strNomCam += "Descripción Corta";
            blnDesCor = true;
        }

        if(zafRecHum29.txtDesLarSupExt.getText().length()==0){
            zafRecHum29.txtDesLarSupExt.requestFocus();
            zafRecHum29.txtDesLarSupExt.selectAll();
            if(blnDesCor){
                strNomCam = " y ";
            }
            strNomCam += "Descripción Larga";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum29,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }
        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_motHorSupExt.getIntCo_mot()>0){
            zafRecHum29.txtCodSupExt.setText(String.valueOf(tbm_motHorSupExt.getIntCo_mot()));
        }else{
            zafRecHum29.txtCodSupExt.setText("");
        }

        if(tbm_motHorSupExt.getStrTx_DesCor()!=null){
            zafRecHum29.txtDesCorSupExt.setText(String.valueOf(tbm_motHorSupExt.getStrTx_DesCor()));
        }else{
            zafRecHum29.txtDesCorSupExt.setText("");
        }
        
        if(tbm_motHorSupExt.getStrTx_DesLar()!=null){
            zafRecHum29.txtDesLarSupExt.setText(String.valueOf(tbm_motHorSupExt.getStrTx_DesLar()));
        }else{
            zafRecHum29.txtDesLarSupExt.setText("");
        }
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum29.txtCodSupExt.getText().length()>0){
            tbm_motHorSupExt.setIntCo_mot(Integer.parseInt(zafRecHum29.txtCodSupExt.getText()));
        }else{
            tbm_motHorSupExt.setIntCo_mot(0);
        }
        if(zafRecHum29.txtDesCorSupExt.getText().length()>0){
            tbm_motHorSupExt.setStrTx_DesCor(zafRecHum29.txtDesCorSupExt.getText());
        }else{
            tbm_motHorSupExt.setStrTx_DesCor(null);
        }
        if(zafRecHum29.txtDesLarSupExt.getText().length()>0){
            tbm_motHorSupExt.setStrTx_DesLar(zafRecHum29.txtDesLarSupExt.getText());
        }else{
            tbm_motHorSupExt.setStrTx_DesLar(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;

        try{
            zafVenMotHorSupExt.limpiar();
            zafVenMotHorSupExt.setCampoBusqueda(0);
            zafVenMotHorSupExt.setVisible(true);

            if (zafVenMotHorSupExt.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                zafRecHum29.txtCodSupExt.setText(zafVenMotHorSupExt.getValueAt(1));
                zafRecHum29.txtDesCorSupExt.setText(zafVenMotHorSupExt.getValueAt(2));
                zafRecHum29.txtDesLarSupExt.setText(zafVenMotHorSupExt.getValueAt(3));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodSupExt true habilita, false deshabilita txtCodSupExt
     * @param blnButCodSupExt true habilita, false deshabilita butCodTipCon
     * @param blnTxtDesCor true habilita, false deshabilita txtDesCor
     * @param txtDesLarSupExt true habilita, false deshabilita txtDesCor
     */
    private void habilitarCom(boolean blnTxtCodSupExt, boolean blnButCodSupExt, boolean blnTxtDesCor, boolean blnTxtDesLar){
        zafRecHum29.txtCodSupExt.setEditable(blnTxtCodSupExt);
        zafRecHum29.butCodSupExt.setEnabled(blnButCodSupExt);
        zafRecHum29.txtDesCorSupExt.setEditable(blnTxtDesCor);
        zafRecHum29.txtDesLarSupExt.setEditable(blnTxtDesLar);
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum29,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_motHorSupExt.getStrSt_Reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_motHorSupExt.getStrSt_Reg().equals("E")){
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
            List<Tbm_MotHorSupExt> listmp = null;

            try{
                listmp = tbm_motHorSupExtBO.consultarLisPag(tbm_motHorSupExtpar, intPagAct, 1);
                if(listmp!=null){
                    tbm_motHorSupExt = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
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
            tbm_motHorSupExt = null;
            tbm_motHorSupExt = new Tbm_MotHorSupExt();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true);
            zafRecHum29.txtDesCorSupExt.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            zafRecHum29.txtCodSupExt.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true);
            zafRecHum29.txtDesCorSupExt.requestFocus();
            zafRecHum29.txtDesCorSupExt.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafRecHum29);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafRecHum29);
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
                tbm_motHorSupExt.setStrSt_Reg("A");
                tbm_motHorSupExtBO.grabar(tbm_motHorSupExt);
                sincronizarFraPoj();
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
            }

            return blnOk;
        }

        public boolean consultar() {
            boolean blnOk = false;

            try {
            	sincronizarPojFra();
                tbm_motHorSupExtpar = (Tbm_MotHorSupExt) tbm_motHorSupExt.clone();
                lisTbm_motHorSupExt = tbm_motHorSupExtBO.consultarLisPag(tbm_motHorSupExtpar, 0, 0);
                if(lisTbm_motHorSupExt!=null){
                    intPagAct = intPagFin = lisTbm_motHorSupExt.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_motHorSupExt = lisTbm_motHorSupExt.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_motHorSupExt = null;
                }else{
                    tbm_motHorSupExt = null;
                    tbm_motHorSupExt = new Tbm_MotHorSupExt();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafRecHum29,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                tbm_motHorSupExtBO.actualizar(tbm_motHorSupExt);
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_motHorSupExt.getIntCo_mot()>0){
                    tbm_motHorSupExt.setStrSt_Reg("E");
                    tbm_motHorSupExtBO.actualizar(tbm_motHorSupExt);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum29,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_motHorSupExt.getIntCo_mot()>0){
                    tbm_motHorSupExt.setStrSt_Reg("I");
                    tbm_motHorSupExtBO.actualizar(tbm_motHorSupExt);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum29,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum29, ex);
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
                tbm_motHorSupExt = null;
                tbm_motHorSupExt = new Tbm_MotHorSupExt();
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
            if(tbm_motHorSupExt!=null){
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