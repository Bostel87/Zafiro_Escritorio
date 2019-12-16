
package RecursosHumanos.ZafRecHum04;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_tipfamconBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipfamcon;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTipFamCon;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum04.
 * Envia datos al BO(Business Object)
 * @author Carlos Lainez
 * Guayaquil 15/04/2011
 */
public class ZafRecHum04Bean {
    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_tipfamcon tbm_tipfamcon;                        //Pojo
    private Tbm_tipfamcon tbm_tipfamconpar;                     //Parametros
    private ZafRecHum04 zafRecHum04;                            //JInternalFrame manejado
    private Tbm_tipfamconBO zafRecHum04BO;                        //Conección a la Base de Datos
    private List<Tbm_tipfamcon> lisTbm_tipfamcon;               //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenTipFamCon zafVenTipFamCon;                    //Ventana de consulta "Tipos de Familiares y contactos".
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento

    public ZafRecHum04Bean(ZafRecHum04 zafRecHum04, ZafParSis zafParSis) {
        try {
            this.zafRecHum04 = zafRecHum04;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_tipfamcon = new Tbm_tipfamcon();
            tbm_tipfamconpar = new Tbm_tipfamcon();
            zafRecHum04BO = new Tbm_tipfamconBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_tipfamcon = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenTipFamCon = new ZafVenTipFamCon(JOptionPane.getFrameForComponent(zafRecHum04), zafParSis, "Listado de Tipos de Familiares y Contactos");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
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
    public void initTooBar(ZafRecHum04 zafRecHum04) {
        tooBar = new MiToolBar(zafRecHum04);
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum04,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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

        if(zafRecHum04.txtDes.getText().length()==0){
            zafRecHum04.txtDes.requestFocus();
            zafRecHum04.txtDes.selectAll();
            strNomCam = "Descripción";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum04,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
            zafRecHum04.txtCod.setText(String.valueOf(tbm_tipfamcon.getIntCo_tipfamcon()));
        }else{
            zafRecHum04.txtCod.setText("");
        }
        zafRecHum04.txtDes.setText(tbm_tipfamcon.getStrTx_deslar());
        zafRecHum04.bgrCalUti.clearSelection();
        zafRecHum04.optNo_CarFam.setSelected((tbm_tipfamcon.getStrTx_tipcarfam()!=null && tbm_tipfamcon.getStrTx_tipcarfam().equals("N")));
        zafRecHum04.optSi_CarFamMen.setSelected((tbm_tipfamcon.getStrTx_tipcarfam()!=null && tbm_tipfamcon.getStrTx_tipcarfam().equals("H")));
        zafRecHum04.optSi_CarFamMay.setSelected((tbm_tipfamcon.getStrTx_tipcarfam()!=null && tbm_tipfamcon.getStrTx_tipcarfam().equals("C")));
        zafRecHum04.txaObs.setText(tbm_tipfamcon.getStrTx_obs1());
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum04.txtCod.getText().length()>0){
            tbm_tipfamcon.setIntCo_tipfamcon(Integer.parseInt(zafRecHum04.txtCod.getText()));
        }else{
            tbm_tipfamcon.setIntCo_tipfamcon(0);
        }
        if(zafRecHum04.txtDes.getText().length()>0){
            tbm_tipfamcon.setStrTx_deslar(zafRecHum04.txtDes.getText());
        }else{
            tbm_tipfamcon.setStrTx_deslar(null);
        }
        if(zafRecHum04.optNo_CarFam.isSelected()){
            tbm_tipfamcon.setStrTx_tipcarfam("N");
        }
        if(zafRecHum04.optSi_CarFamMen.isSelected()){
            tbm_tipfamcon.setStrTx_tipcarfam("H");
        }
        if(zafRecHum04.optSi_CarFamMay.isSelected()){
            tbm_tipfamcon.setStrTx_tipcarfam("C");
        }
        if(zafRecHum04.txaObs.getText().length()>0){
            tbm_tipfamcon.setStrTx_obs1(zafRecHum04.txaObs.getText());
        }else{
            tbm_tipfamcon.setStrTx_obs1(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;

        try{
            zafVenTipFamCon.limpiar();
            zafVenTipFamCon.setCampoBusqueda(0);
            zafVenTipFamCon.setVisible(true);

            if (zafVenTipFamCon.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_tipfamcon.setIntCo_tipfamcon(Integer.parseInt(zafVenTipFamCon.getValueAt(1)));
                zafRecHum04.txtCod.setText(String.valueOf(tbm_tipfamcon.getIntCo_tipfamcon()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCod true habilita, false deshabilita blnTxtCod
     * @param blnTxtDes true habilita, false deshabilita blnTxtDes
     * @param blnOptNo_CarFam true habilita, false deshabilita blnOptNo_CarFam
     * @param blnOptSi_CarFamMen true habilita, false deshabilita blnOptSi_CarFamMen
     * @param blnOptSi_CarFamMay true habilita, false deshabilita blnOptSi_CarFamMay
     * @param blnTxaObs true habilita, false deshabilita blnTxaObs
     */
    private void habilitarCom(boolean blnTxtCod, boolean blnButCod, boolean blnTxtDes, boolean blnOptNo_CarFam, boolean blnOptSi_CarFamMen, boolean blnOptSi_CarFamMay, boolean blnTxaObs){
        zafRecHum04.txtCod.setEditable(blnTxtCod);
        zafRecHum04.butCod.setEnabled(blnButCod);
        zafRecHum04.txtDes.setEditable(blnTxtDes);
        zafRecHum04.optNo_CarFam.setEnabled(blnOptNo_CarFam);
        zafRecHum04.optSi_CarFamMen.setEnabled(blnOptSi_CarFamMen);
        zafRecHum04.optSi_CarFamMay.setEnabled(blnOptSi_CarFamMay);
        zafRecHum04.txaObs.setEditable(blnTxaObs);
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum04,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_tipfamcon.getStrSt_reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_tipfamcon.getStrSt_reg().equals("E")){
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
            List<Tbm_tipfamcon> listmp = null;

            try{
                listmp = zafRecHum04BO.consultarLisPag(tbm_tipfamconpar, intPagAct, 1);
                if(listmp!=null){
                    tbm_tipfamcon = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
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
            tbm_tipfamcon = null;
            tbm_tipfamcon = new Tbm_tipfamcon();
            sincronizarFraPoj();
            zafRecHum04.optNo_CarFam.setSelected(true);
            habilitarCom(false, false, true, true, true, true, true);
            zafRecHum04.txtDes.requestFocus();
            blnActChkMod = true;
        }

        public void clickConsultar() {
            zafRecHum04.txtCod.requestFocus();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true, true, true, true);
            zafRecHum04.txtDes.requestFocus();
            zafRecHum04.txtDes.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafRecHum04);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafRecHum04);
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
                tbm_tipfamcon.setStrSt_reg("A");

                zafRecHum04BO.grabarTipFamCon(tbm_tipfamcon);
                sincronizarFraPoj();
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
            }

            return blnOk;
        }

        public boolean consultar() {
            boolean blnOk = false;

            try {
            	sincronizarPojFra();
                tbm_tipfamconpar = (Tbm_tipfamcon) tbm_tipfamcon.clone();
                lisTbm_tipfamcon = zafRecHum04BO.consultarLisPag(tbm_tipfamconpar, 0, 0);
                if(lisTbm_tipfamcon!=null){
                    intPagAct = intPagFin = lisTbm_tipfamcon.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_tipfamcon = lisTbm_tipfamcon.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_tipfamcon = null;
                }else{
                    tbm_tipfamcon = null;
                    tbm_tipfamcon = new Tbm_tipfamcon ();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafRecHum04,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                zafRecHum04BO.actualizarTipFamCon(tbm_tipfamcon);
                blnOk = true;
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
                    tbm_tipfamcon.setStrSt_reg("E");
                    zafRecHum04BO.actualizarTipFamCon(tbm_tipfamcon);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum04,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
                    tbm_tipfamcon.setStrSt_reg("I");
                    zafRecHum04BO.actualizarTipFamCon(tbm_tipfamcon);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum04,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum04, ex);
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
                tbm_tipfamcon = null;
                tbm_tipfamcon = new Tbm_tipfamcon();
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
            if(tbm_tipfamcon!=null){
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
