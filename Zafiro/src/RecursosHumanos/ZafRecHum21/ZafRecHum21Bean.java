package RecursosHumanos.ZafRecHum21;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_carlabBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_comsecBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_carlab;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_comsec;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenCarLab;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenComSec;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum21.
 * Envia datos al BO(Business Object)
 * @author Roberto Flores
 * Guayaquil 06/09/2011
 */
public class ZafRecHum21Bean {

    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_carlab tbm_carlab;                              //Pojo
    private Tbm_carlab tbm_carlabpar;                           //Parametros
    private ZafRecHum21 zafRecHum21;                            //JInternalFrame manejado
    private Tbm_carlabBO tbm_carlabBO;                          //Conecci�n a la Base de Datos
    private List<Tbm_carlab> lisTbm_carlab;                     //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenCarLab zafVenCarLab;                          //Ventana de consulta "Cargos Laborales".
    private ZafVenComSec zafVenComSec;                          //Ventana de consulta "Comisiones Sectoriales".
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis objDocLis;                                   //Listener que indica si han habido cambios en el documento
    public boolean blnEsCon;                                    //Indica si realiza operacion de Inserción o de Consulta

    public ZafRecHum21Bean(ZafRecHum21 zafRecHum21, ZafParSis zafParSis) {
        try {
            this.zafRecHum21 = zafRecHum21;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_carlab = new Tbm_carlab();
            tbm_carlabpar = new Tbm_carlab();
            tbm_carlabBO = new Tbm_carlabBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_carlab = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenCarLab = new ZafVenCarLab(JOptionPane.getFrameForComponent(zafRecHum21), zafParSis, "Cargos Laborales");
            zafVenComSec = new ZafVenComSec(JOptionPane.getFrameForComponent(zafRecHum21), zafParSis, "Listado de Comisiones Sectoriales");
            blnMod = false;
            blnActChkMod = false;
            blnEsCon = false;
            objDocLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
            ex.printStackTrace();
        }
    }

    /**
     * @param blnMod the blnMod to set
     */
    public void setBlnMod(boolean blnMod){
        this.blnMod  = blnMod;
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    public void agregarDocLis()
    {
        zafRecHum21.txtNomCar.getDocument().addDocumentListener(objDocLis);
        zafRecHum21.txtCoComSec.getDocument().addDocumentListener(objDocLis);
        zafRecHum21.txtCodComSec.getDocument().addDocumentListener(objDocLis);
        zafRecHum21.txtNomCarComSec.getDocument().addDocumentListener(objDocLis);
        zafRecHum21.txtMinSec.getDocument().addDocumentListener(objDocLis);
        zafRecHum21.txaObs.getDocument().addDocumentListener(objDocLis);
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
     * @param zafRecHum21
     */
    public void initTooBar(ZafRecHum21 zafRecHum21) {
        tooBar = new MiToolBar(zafRecHum21);
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
        return objDocLis;
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum21,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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

        if(zafRecHum21.txtNomCar.getText().length()==0){
            zafRecHum21.txtNomCar.requestFocus();
            zafRecHum21.txtNomCar.selectAll();
            strNomCam = "Nombre del Horario";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum21,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_carlab.getIntCo_Car()>0){
            zafRecHum21.txtCodCar.setText(String.valueOf(tbm_carlab.getIntCo_Car()));
        }else{
            zafRecHum21.txtCodCar.setText("");
        }

        if(tbm_carlab.getStrTx_NomCar()!=null){
            zafRecHum21.txtNomCar.setText(String.valueOf(tbm_carlab.getStrTx_NomCar()));
        }else{
            zafRecHum21.txtNomCar.setText("");
        }

        if(tbm_carlab.getIntCo_ComSec()>0){
            zafRecHum21.txtCoComSec.setText(String.valueOf(tbm_carlab.getIntCo_ComSec()));
        }else{
            zafRecHum21.txtCoComSec.setText("");
        }

        if(tbm_carlab.getStrTx_NomComSec()!=null){
            zafRecHum21.txtComSecNom.setText(String.valueOf(tbm_carlab.getStrTx_NomComSec()));
        }else{
            zafRecHum21.txtComSecNom.setText("");
        }

        /**/
        if(tbm_carlab.getStrTx_CodComSec()!=null){
            zafRecHum21.txtCodComSec.setText(String.valueOf(tbm_carlab.getStrTx_CodComSec()));
        }else{
            zafRecHum21.txtCodComSec.setText("");
        }
        //strTx_NomCarComSec
        if(tbm_carlab.getStrTx_NomCarComSec()!=null){
            zafRecHum21.txtNomCarComSec.setText(String.valueOf(tbm_carlab.getStrTx_NomCarComSec()));
        }else{
            zafRecHum21.txtNomCarComSec.setText("");
        }

        if(tbm_carlab.getBigNd_MinSec()!=null){
            zafRecHum21.txtMinSec.setText("" + zafUti.redondearBigDecimal(tbm_carlab.getBigNd_MinSec(), zafParSis.getDecimalesBaseDatos()));
        }else{
            zafRecHum21.txtMinSec.setText("");
        }

        if(tbm_carlab.getStrTx_Obs1()!=null){
            zafRecHum21.txaObs.setText(String.valueOf(tbm_carlab.getStrTx_Obs1()));
        }else{
            zafRecHum21.txaObs.setText("");
        }
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum21.txtCodCar.getText().length()>0){
            tbm_carlab.setIntCo_Car(Integer.parseInt(zafRecHum21.txtCodCar.getText()));
        }else{
            tbm_carlab.setIntCo_Car(0);
        }

        if(zafRecHum21.txtNomCar.getText().length()>0){
            tbm_carlab.setStrTx_NomCar(zafRecHum21.txtNomCar.getText());
        }else{
            tbm_carlab.setStrTx_NomCar(null);
        }

        if(zafRecHum21.txtCoComSec.getText().length()>0){
            tbm_carlab.setIntCo_ComSec(Integer.parseInt(zafRecHum21.txtCoComSec.getText()));
        }else{
            tbm_carlab.setIntCo_ComSec(0);
        }

        if(zafRecHum21.txtComSecNom.getText().length()>0){
            tbm_carlab.setStrTx_NomComSec(zafRecHum21.txtComSecNom.getText());
        }else{
            tbm_carlab.setStrTx_NomComSec(null);
        }

        //StrTx_CodComSec()
        if(zafRecHum21.txtCodComSec.getText().length()>0){
            tbm_carlab.setStrTx_CodComSec(zafRecHum21.txtCodComSec.getText());
        }else{
            tbm_carlab.setStrTx_CodComSec(null);
        }
        //StrTx_NomCarComSec
        if(zafRecHum21.txtNomCarComSec.getText().length()>0){
            tbm_carlab.setStrTx_NomCarComSec(zafRecHum21.txtNomCarComSec.getText());
        }else{
            tbm_carlab.setStrTx_NomCarComSec(null);
        }

        if(zafRecHum21.txtMinSec.getText().length()>0){
            tbm_carlab.setBigNd_MinSec(new BigDecimal(zafRecHum21.txtMinSec.getText()));
        }else{
            tbm_carlab.setBigNd_MinSec(null);
        }

        if(zafRecHum21.txaObs.getText().length()>0){
            tbm_carlab.setStrTx_Obs1(zafRecHum21.txaObs.getText());
        }else{
            tbm_carlab.setStrTx_Obs1(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;
        
        try{
            zafVenCarLab.limpiar();
            zafVenCarLab.setCampoBusqueda(0);
            zafVenCarLab.setVisible(true);

            if (zafVenCarLab.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_carlab.setIntCo_Car(Integer.parseInt(zafVenCarLab.getValueAt(1)));
                zafRecHum21.txtCodCar.setText(String.valueOf(tbm_carlab.getIntCo_Car()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
            ex.printStackTrace();
        }
        return blnRes;
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenConComSec(){
        boolean blnRes=true;

        try{
            zafVenComSec.limpiar();
            zafVenComSec.setCampoBusqueda(0);
            zafVenComSec.setVisible(true);

            if (zafVenComSec.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_carlab.setIntCo_ComSec(Integer.parseInt(zafVenComSec.getValueAt(1)));
                tbm_carlab.setStrTx_NomCar(zafVenComSec.getValueAt(2));
                zafRecHum21.txtCoComSec.setText(String.valueOf(tbm_carlab.getIntCo_ComSec()));
                zafRecHum21.txtComSecNom.setText(String.valueOf(tbm_carlab.getStrTx_NomCar()));
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
        }

        return blnRes;
    }

    public void consultarComisionSectorial(int intComSec) throws Exception{

        Tbm_comsecBO tbm_comsecBO = new Tbm_comsecBO(zafParSis);
        Tbm_comsec tbm_comsec = new Tbm_comsec();
        tbm_comsec.setIntCo_comsec(intComSec);
        List<Tbm_comsec> listTbm_ComSec = tbm_comsecBO.consultarLisGen(tbm_comsec);
        if(listTbm_ComSec!=null){
            zafRecHum21.txtComSecNom.setText(listTbm_ComSec.get(0).getStrTx_nomcomsec());
        }
        
    }

    /**
     * Permite consultar en la base de datos si el código de cargo ya existe.
     * @return Retorna true si existe o false en caso contrario.
     */
    private boolean repetidoCodCar(){
        boolean blnOk = false;

        try {
            blnOk = tbm_carlabBO.consultarCodNomCarLab(tbm_carlab.getStrTx_NomCar(), tbm_carlab.getIntCo_Car());
            if(blnOk){
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Nombre de Campo ya existe. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(zafRecHum21,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                zafRecHum21.txtNomCar.requestFocus();
                zafRecHum21.txtNomCar.selectAll();
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
            ex.printStackTrace();
        }
        return blnOk;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodCar true habilita, false deshabilita txtCodHor
     * @param blnButCarLab true habilita, false deshabilita butCarLab
     * @param blnTxtNomCar true habilita, false deshabilita txtNomCar
     * @param blnTxtCoComSec true habilita, false deshabilita txtCoComSec
     * @param blnTxtComSecNom true habilita, false deshabilita txtComSecNom
     * @param blnButComSec true habilita, false deshabilita butComSec
     * @param blnTxtCodComSec true habilita, false deshabilita txtCodComSec
     * @param blnTxtNomCarComSec true habilita, false deshabilita txtCodComSec
     * @param blnTxtMinSec true habilita, false deshabilita txtMinSec
     * @param blnTxaObs true habilita, false deshabilita txaObs
     */
    private void habilitarCom(boolean blnTxtCodCar, boolean blnButCarLab, boolean blnTxtNomCar, boolean blnTxtCoComSec, boolean blnTxtComSecNom,
            boolean blnButComSec, boolean blnTxtCodComSec, boolean blnTxtNomCarComSec, boolean blnTxtMinSec, boolean blnTxaObs){
        zafRecHum21.txtCodCar.setEditable(blnTxtCodCar);
        zafRecHum21.butCarLab.setEnabled(blnButCarLab);
        zafRecHum21.txtNomCar.setEditable(blnTxtNomCar);
        zafRecHum21.txtCoComSec.setEditable(blnTxtCoComSec);
        zafRecHum21.txtComSecNom.setEditable(blnTxtComSecNom);
        zafRecHum21.butComSec.setEnabled(blnButComSec);
        zafRecHum21.txtCodComSec.setEditable(blnTxtCodComSec);
        zafRecHum21.txtNomCarComSec.setEditable(blnTxtNomCarComSec);
        zafRecHum21.txtMinSec.setEditable(blnTxtMinSec);
        zafRecHum21.txaObs.setEditable(blnTxaObs);
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum21,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_carlab.getStrSt_Reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_carlab.getStrSt_Reg().equals("E")){
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
         * Minimiza el riesgo de dejar conecciones, statements y cursores abiertos.
         */
        private void pagineoBas(){
            List<Tbm_carlab> listmp = null;

            try{
                listmp = tbm_carlabBO.consultarLisPag(tbm_carlabpar, intPagAct, 1);
                if(listmp!=null){
                    tbm_carlab = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
                ex.printStackTrace();
            }
        }

        /**
         * Pagineo de Memoria. Permite recorrer una lista cargada previamente con los datos de la consulta.
         * Minimiza el riesgo de dejar conecciones, statements y cursores abiertos.
         * El espacio ocupado en memoria depende de la cantidad de registros que retorna la consulta.
         */
        private void pagineoMem(){
            tbm_carlab = lisTbm_carlab.get(intPagAct);
            sincronizarFraPoj();
            mostrarEstado();
            setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
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
            tbm_carlab = null;
            tbm_carlab = new Tbm_carlab();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true, false, true, true, true, true, true);
            zafRecHum21.txtNomCar.requestFocus();
            blnActChkMod = true;
            blnEsCon = false;
        }

        public void clickConsultar() {
            zafRecHum21.txtCodCar.requestFocus();
            blnEsCon = true;
            habilitarCom(true, true, true, true, false, true, true, true, true, true);
        }

        public void clickModificar() {
            //habilitarCom(false, false, true, true);
            habilitarCom(true, true, true, true, true, true, true, true, true, true);
            zafRecHum21.txtNomCar.requestFocus();
            zafRecHum21.txtNomCar.selectAll();
            blnActChkMod = true;
        }

        public void clickEliminar() {
            zafUti.desactivarCom(zafRecHum21);
        }

        public void clickAnular() {
            zafUti.desactivarCom(zafRecHum21);
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
                tbm_carlab.setStrSt_Reg("A");
                if(!repetidoCodCar()){
                //if(!repetidoCodCar() && formatoTabOk()){
                    tbm_carlabBO.grabar(tbm_carlab);
                    sincronizarFraPoj();
                    blnOk = true;
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
                ex.printStackTrace();
            }

            return blnOk;
        }

     /*
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
        oppMsg.showMessageDialog(zafRecHum21,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


        public boolean consultar() {
            boolean blnOk = false;
            
            try {
            	sincronizarPojFra();
                tbm_carlabpar = (Tbm_carlab) tbm_carlab.clone();
                lisTbm_carlab = tbm_carlabBO.consultarLisPag(tbm_carlabpar, 0, 0);
                if(lisTbm_carlab!=null){
                    intPagAct = intPagFin = lisTbm_carlab.size()-1;
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                    tbm_carlab = lisTbm_carlab.get(intPagAct);
                    sincronizarFraPoj();
                    mostrarEstado();
                    blnOk = true;

                    //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                    lisTbm_carlab = null;
                }else{
                    tbm_carlab = null;
                    tbm_carlab = new Tbm_carlab();
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(zafRecHum21,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
                ex.printStackTrace();
            }

            return blnOk;
        }

        public boolean modificar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                //if(!repetidoCodCar() && formatoTabOk()){
                if(!repetidoCodCar()){
                    tbm_carlabBO.actualizar(tbm_carlab);
                    blnOk = true;
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
                ex.printStackTrace();
            }

            return blnOk;
        }

        public boolean eliminar() {
            boolean blnOk = false;

            try {
                if(tbm_carlab.getIntCo_Car()>0){
                    tbm_carlab.setStrSt_Reg("E");
                    tbm_carlabBO.actualizar(tbm_carlab);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum21,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
                ex.printStackTrace();
            }

            return blnOk;
        }

        public boolean anular() {
            boolean blnOk = false;

            try {
                if(tbm_carlab.getIntCo_Car()>0){
                    tbm_carlab.setStrSt_Reg("I");
                    tbm_carlabBO.actualizar(tbm_carlab);
                    mostrarEstado();
                    blnOk = true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(zafRecHum21,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum21, ex);
                ex.printStackTrace();
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
                tbm_carlab = null;
                tbm_carlab = new Tbm_carlab();
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
            return validarCamObl();//&&validarCamNum();
        }

        public boolean beforeConsultar() {
            //return validarCamNum();
            return true;
        }

        public boolean beforeModificar() {
            return validarCamObl();//&&validarCamNum();
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
            if(tbm_carlab!=null){
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