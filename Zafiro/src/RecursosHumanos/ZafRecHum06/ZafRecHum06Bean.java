
package RecursosHumanos.ZafRecHum06;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_hortraBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenHorTra;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum06.
 * Envia datos al BO(Business Object)
 * @author Roberto Flores
 * Guayaquil 06/09/2011
 */
public class ZafRecHum06Bean {

    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_hortra tbm_hortra;                              //Pojo
    private Tbm_hortra tbm_hortrapar;                           //Parametros
    private ZafRecHum06 zafRecHum06;                            //JInternalFrame manejado
    private Tbm_hortraBO Tbm_hortraBO;                          //Conecci�n a la Base de Datos
    private List<Tbm_hortra> lisTbm_hortra;                     //Lista con datos de consulta general
    private int intPagAct;                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                      //Ultimo indice de los registros de la consulta
    private ZafVenHorTra zafVenHorTra;                          //Ventana de consulta "Horarios de Trabajo".
    private MiToolBar tooBar;                                   //Barra de herramientas
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private boolean blnLimpTab;                                 //Indica si el JTable debe de swer limpiado.
    private DocLis objDocLis;                                   //Listener que indica si han habido cambios en el documento
    private String[][] tabInf = null;                           //Informacion que se encuentra en la tabla horaria de trabajo (Lunes a Domingo)

    public ZafRecHum06Bean(ZafRecHum06 zafRecHum06, ZafParSis zafParSis) {
        try {
            this.zafRecHum06 = zafRecHum06;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbm_hortra = new Tbm_hortra();
            tbm_hortrapar = new Tbm_hortra();
            Tbm_hortraBO = new Tbm_hortraBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbm_hortra = null;
            intPagAct = 0;
            intPagFin = 0;
            zafVenHorTra = new ZafVenHorTra(JOptionPane.getFrameForComponent(zafRecHum06), zafParSis, "Horarios de Trabajo");
            blnMod = false;
            blnActChkMod = false;
            blnLimpTab = false;
            objDocLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
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
        zafRecHum06.txtNomHor.getDocument().addDocumentListener(objDocLis);
        zafRecHum06.txtMinGra.getDocument().addDocumentListener(objDocLis);
        zafRecHum06.txaObs.getDocument().addDocumentListener(objDocLis);
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
     * @param zafRecHum06
     */
    public void initTooBar(ZafRecHum06 zafRecHum06) {
        tooBar = new MiToolBar(zafRecHum06);
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum06,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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

        if(zafRecHum06.txtNomHor.getText().length()==0){
            zafRecHum06.txtNomHor.requestFocus();
            zafRecHum06.txtNomHor.selectAll();
            strNomCam = "Nombre del Horario";
        }

        if(strNomCam!=null){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "El campo <<"+strNomCam+">> es obligatorio.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum06,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){
        if(tbm_hortra.getIntCo_hor()>0){
            zafRecHum06.txtCodHor.setText(String.valueOf(tbm_hortra.getIntCo_hor()));
        }else{
            zafRecHum06.txtCodHor.setText("");
        }

        if(tbm_hortra.getStrTx_nom()!=null){
            zafRecHum06.txtNomHor.setText(String.valueOf(tbm_hortra.getStrTx_nom()));
        }else{
            zafRecHum06.txtNomHor.setText("");
        }

        if(tbm_hortra.getStrTx_minGra()!=null){
            zafRecHum06.txtMinGra.setText(String.valueOf(tbm_hortra.getStrTx_minGra()));
        }else{
            zafRecHum06.txtMinGra.setText("");
        }

        String[][] strTbl_entsaldet = tbm_hortra.getStrTbl_entsaldet();
        if(blnLimpTab){
            if(strTbl_entsaldet!=null){
                
                if(strTbl_entsaldet[0][0]!=null){
                    if(strTbl_entsaldet[0][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[0][0].substring(0, 5),0, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",0, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",0, 2);
                }
                
                if(strTbl_entsaldet[0][1]!=null){
                    if(strTbl_entsaldet[0][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[0][1].substring(0, 5),0, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",0, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",0, 3);
                }
                
                if(strTbl_entsaldet[0][2]!=null){
                    if(strTbl_entsaldet[0][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[0][2].substring(0, 5),0, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",0, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",0, 4);
                }
                
                if(strTbl_entsaldet[1][0]!=null){
                    if(strTbl_entsaldet[1][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[1][0].substring(0, 5),1, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",1, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",1, 2);
                }

                if(strTbl_entsaldet[1][1]!=null){
                    if(strTbl_entsaldet[1][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[1][1].substring(0, 5),1, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",1, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",1, 3);
                }

                if(strTbl_entsaldet[1][2]!=null){
                    if(strTbl_entsaldet[1][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[1][2].substring(0, 5),1, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",1, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",1, 4);
                }

                if(strTbl_entsaldet[2][0]!=null){
                    if(strTbl_entsaldet[2][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[2][0].substring(0, 5),2, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",2, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",2, 2);
                }
                
                if(strTbl_entsaldet[2][1]!=null){
                    if(strTbl_entsaldet[2][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[2][1].substring(0, 5),2, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",2, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",2, 3);
                }
                
                if(strTbl_entsaldet[2][2]!=null){
                    if(strTbl_entsaldet[2][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[2][2].substring(0, 5),2, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",2, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",2, 4);
                }
                
                if(strTbl_entsaldet[3][0]!=null){
                    if(strTbl_entsaldet[3][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[3][0].substring(0, 5),3, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",3, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",3, 2);
                }
                
                if(strTbl_entsaldet[3][1]!=null){
                    if(strTbl_entsaldet[3][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[3][1].substring(0, 5),3, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",3, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",3, 3);
                }
                
                if(strTbl_entsaldet[3][2]!=null){
                    if(strTbl_entsaldet[3][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[3][2].substring(0, 5),3, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",3, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",3, 4);
                }

                if(strTbl_entsaldet[4][0]!=null){
                    if(strTbl_entsaldet[4][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[4][0].substring(0, 5),4, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",4, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",4, 2);
                }

                if(strTbl_entsaldet[4][1]!=null){
                    if(strTbl_entsaldet[4][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[4][1].substring(0, 5),4, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",4, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",4, 3);
                }
                
                if(strTbl_entsaldet[4][2]!=null){
                    if(strTbl_entsaldet[4][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[4][2].substring(0, 5),4, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",4, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",4, 4);
                }
                
                if(strTbl_entsaldet[5][0]!=null){
                    if(strTbl_entsaldet[5][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[5][0].substring(0, 5),5, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",5, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",5, 2);
                }

                if(strTbl_entsaldet[5][1]!=null){
                    if(strTbl_entsaldet[5][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[5][1].substring(0, 5),5, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",5, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",5, 3);
                }
                
                if(strTbl_entsaldet[5][2]!=null){
                    if(strTbl_entsaldet[5][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[5][2].substring(0, 5),5, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",5, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",5, 4);
                }
                
                if(strTbl_entsaldet[6][0]!=null){
                    if(strTbl_entsaldet[6][0].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[6][0].substring(0, 5),6, 2);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",6, 2);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",6, 2);
                }
                
                if(strTbl_entsaldet[6][1]!=null){
                    if(strTbl_entsaldet[6][1].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[6][1].substring(0, 5),6, 3);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",6, 3);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",6, 3);
                }
                
                if(strTbl_entsaldet[6][2]!=null){
                    if(strTbl_entsaldet[6][2].compareTo("")!=0){
                        zafRecHum06.tblRep.setValueAt(strTbl_entsaldet[6][2].substring(0, 5),6, 4);
                    }else{
                        zafRecHum06.tblRep.setValueAt("",6, 4);
                    }
                }else{
                    zafRecHum06.tblRep.setValueAt("",6, 4);
                }
            }else{
                zafRecHum06.tblRep.setValueAt("",0, 2);
                zafRecHum06.tblRep.setValueAt("",0, 3);
                zafRecHum06.tblRep.setValueAt("",0, 4);
                zafRecHum06.tblRep.setValueAt("",1, 2);
                zafRecHum06.tblRep.setValueAt("",1, 3);
                zafRecHum06.tblRep.setValueAt("",1, 4);
                zafRecHum06.tblRep.setValueAt("",2, 2);
                zafRecHum06.tblRep.setValueAt("",2, 3);
                zafRecHum06.tblRep.setValueAt("",2, 4);
                zafRecHum06.tblRep.setValueAt("",3, 2);
                zafRecHum06.tblRep.setValueAt("",3, 3);
                zafRecHum06.tblRep.setValueAt("",3, 4);
                zafRecHum06.tblRep.setValueAt("",4, 2);
                zafRecHum06.tblRep.setValueAt("",4, 3);
                zafRecHum06.tblRep.setValueAt("",4, 4);
                zafRecHum06.tblRep.setValueAt("",5, 2);
                zafRecHum06.tblRep.setValueAt("",5, 3);
                zafRecHum06.tblRep.setValueAt("",5, 4);
                zafRecHum06.tblRep.setValueAt("",6, 2);
                zafRecHum06.tblRep.setValueAt("",6, 3);
                zafRecHum06.tblRep.setValueAt("",6, 4);
            }
        }

        if(tbm_hortra.getStrTxa_obs1()!=null){
            zafRecHum06.txaObs.setText(String.valueOf(tbm_hortra.getStrTxa_obs1()));
        }else{
            zafRecHum06.txaObs.setText("");
        }
    }

    /**
     * Permite sincronizar el contenido del pojo con el contenido de la interfaz de usuario
     */
    private void sincronizarPojFra(){
        if(zafRecHum06.txtCodHor.getText().length()>0){
            tbm_hortra.setIntCo_hor(Integer.parseInt(zafRecHum06.txtCodHor.getText()));
        }else{
            tbm_hortra.setIntCo_hor(0);
        }

        if(zafRecHum06.txtNomHor.getText().length()>0){
            tbm_hortra.setStrTx_nom(zafRecHum06.txtNomHor.getText());
        }else{
            tbm_hortra.setStrTx_nom(null);
        }

        if(zafRecHum06.txtMinGra.getText().length()>0){
            tbm_hortra.setStrTx_minGra(zafRecHum06.txtMinGra.getText());
        }else{
            tbm_hortra.setStrTx_minGra(null);
        }

        tabInf = new String[7][3];
        tabInf[0][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(0, 2));
        tabInf[0][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(0, 3));
        tabInf[0][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(0, 4));

        tabInf[1][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(1, 2));
        tabInf[1][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(1, 3));
        tabInf[1][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(1, 4));

        tabInf[2][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(2, 2));
        tabInf[2][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(2, 3));
        tabInf[2][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(2, 4));

        tabInf[3][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(3, 2));
        tabInf[3][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(3, 3));
        tabInf[3][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(3, 4));

        tabInf[4][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(4, 2));
        tabInf[4][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(4, 3));
        tabInf[4][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(4, 4));

        tabInf[5][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(5, 2));
        tabInf[5][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(5, 3));
        tabInf[5][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(5, 4));

        tabInf[6][0] = String.valueOf(zafRecHum06.tblRep.getValueAt(6, 2));
        tabInf[6][1] = String.valueOf(zafRecHum06.tblRep.getValueAt(6, 3));
        tabInf[6][2] = String.valueOf(zafRecHum06.tblRep.getValueAt(6, 4));
        tbm_hortra.setStrTbl_entsaldet(tabInf);

        if(zafRecHum06.txaObs.getText().length()>0){
            tbm_hortra.setStrTxa_obs1(zafRecHum06.txaObs.getText());
        }else{
            tbm_hortra.setStrTxa_obs1(null);
        }
    }

    /**
     * Permite visualizar la ventana de consulta para seleccionar un registro de la base de datos.
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenCon(){
        boolean blnRes=true;
        
        try{
            zafVenHorTra.limpiar();
            zafVenHorTra.setCampoBusqueda(0);
            zafVenHorTra.setVisible(true);

            if (zafVenHorTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                tbm_hortra.setIntCo_hor(Integer.parseInt(zafVenHorTra.getValueAt(1)));
                zafRecHum06.txtCodHor.setText(String.valueOf(tbm_hortra.getIntCo_hor()));
                blnRes = tooBar.consultar();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
            ex.printStackTrace();
        }
        return blnRes;
    }

    /**
     * Permite consultar en la base de datos si el código de cargo ya existe.
     * @return Retorna true si existe o false en caso contrario.
     */
    private boolean repetidoCodCar(){
        boolean blnOk = false;

        try {
            blnOk = Tbm_hortraBO.consultarCodHorRep(tbm_hortra.getStrTx_nom(), tbm_hortra.getIntCo_hor());
            if(blnOk){
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Nombre de Campo ya existe. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(zafRecHum06,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                zafRecHum06.txtNomHor.requestFocus();
                zafRecHum06.txtNomHor.selectAll();
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
            ex.printStackTrace();
        }
        return blnOk;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodHor true habilita, false deshabilita txtCodHor
     * @param blnButHorTra true habilita, false deshabilita butHorTra
     * @param blnTxtNomHor true habilita, false deshabilita txtNomHor
     * @param blnTxaObs true habilita, false deshabilita txaObs
     */
    private void habilitarCom(boolean blnTxtCodHor, boolean blnButHorTra, boolean blnTxtNomHor, boolean blnTxaObs){
        zafRecHum06.txtCodHor.setEditable(blnTxtCodHor);
        zafRecHum06.butHorTra.setEnabled(blnButHorTra);
        zafRecHum06.txtNomHor.setEditable(blnTxtNomHor);
        zafRecHum06.txaObs.setEditable(blnTxaObs);
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
            blnOk=(JOptionPane.showConfirmDialog(zafRecHum06,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
            if(tbm_hortra.getStrSt_reg().equals("I")){
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            }else{
                if(tbm_hortra.getStrSt_reg().equals("E")){
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
            List<Tbm_hortra> listmp = null;

            try{
                listmp = Tbm_hortraBO.consultarLisPag(tbm_hortrapar, intPagAct, 1);
                if(listmp!=null){
                    tbm_hortra = listmp.get(0);
                    sincronizarFraPoj();
                    mostrarEstado();
                    setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                }
            }catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
                ex.printStackTrace();
            }
            zafRecHum06.tblRep.clearSelection();
        }

        /**
         * Pagineo de Memoria. Permite recorrer una lista cargada previamente con los datos de la consulta.
         * Minimiza el riesgo de dejar conecciones, statements y cursores abiertos.
         * El espacio ocupado en memoria depende de la cantidad de registros que retorna la consulta.
         */
        private void pagineoMem(){
            tbm_hortra = lisTbm_hortra.get(intPagAct);
            sincronizarFraPoj();
            mostrarEstado();
            setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickInicio() {
            if(intPagAct>0){
                if(verificarCamReg()){
                    intPagAct = 0;
                    pagineoBas();
                }
            }
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickAnterior() {
            if(intPagAct>0){
                if(verificarCamReg()){
                    intPagAct--;
                    pagineoBas();
                }
            }
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickSiguiente() {
            if(intPagAct<intPagFin){
                if(verificarCamReg()){
                    intPagAct++;
                    pagineoBas();
                }
            }
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickFin() {
            if(intPagAct<intPagFin){
                if(verificarCamReg()){
                    intPagAct = intPagFin;
                    pagineoBas();
                }
            }
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickInsertar() {
            tbm_hortra = null;
            tbm_hortra = new Tbm_hortra();
            sincronizarFraPoj();
            habilitarCom(false, false, true, true);
            zafRecHum06.txtNomHor.requestFocus();
            blnActChkMod = true;
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickConsultar() {
            zafRecHum06.txtCodHor.requestFocus();
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickModificar() {
            habilitarCom(false, false, true, true);
            zafRecHum06.txtNomHor.requestFocus();
            zafRecHum06.txtNomHor.selectAll();
            blnActChkMod = true;
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickEliminar() {
            zafRecHum06.tblRep.clearSelection();
            zafUti.desactivarCom(zafRecHum06);
        }

        public void clickAnular() {
            zafRecHum06.tblRep.clearSelection();
            zafUti.desactivarCom(zafRecHum06);
        }

        public void clickImprimir() {
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickVisPreliminar() {
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickAceptar() {
            zafRecHum06.tblRep.clearSelection();
        }

        public void clickCancelar() {
            zafRecHum06.tblRep.clearSelection();
        }

        public boolean insertar() {
            boolean blnOk = false;

            try {
                sincronizarPojFra();
                tbm_hortra.setStrSt_reg("A");
                if(!repetidoCodCar()){
                    Tbm_hortraBO.grabarHorTra(tbm_hortra);
                    sincronizarFraPoj();
                    blnOk = true;
                }
            } catch (Exception ex) {
                zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
                ex.printStackTrace();
            }
            zafRecHum06.tblRep.clearSelection();
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
        oppMsg.showMessageDialog(zafRecHum06,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean consultar() {
        
        boolean blnOk = false;
            
        try {
            sincronizarPojFra();
            tbm_hortrapar = (Tbm_hortra) tbm_hortra.clone();
            lisTbm_hortra = Tbm_hortraBO.consultarLisPag(tbm_hortrapar, 0, 0);
            if(lisTbm_hortra!=null){
                intPagAct = intPagFin = lisTbm_hortra.size()-1;
                setPosicionRelativa("" + (intPagAct+1) + " / " + (intPagFin+1));
                tbm_hortra = lisTbm_hortra.get(intPagAct);
                blnLimpTab = true;
                sincronizarFraPoj();
                mostrarEstado();
                blnOk = true;

                //Seteado a nulo para que desocupe memoria y de la consulta se encarga el pagineo de base
                lisTbm_hortra = null;
            }else{
                tbm_hortra = null;
                tbm_hortra = new Tbm_hortra();
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                JOptionPane.showMessageDialog(zafRecHum06,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
            ex.printStackTrace();
        }
        zafRecHum06.tblRep.clearSelection();
        return blnOk;
    }

    public boolean modificar() {
        boolean blnOk = false;

        try {
            sincronizarPojFra();

            if(!repetidoCodCar()){
                Tbm_hortraBO.actualizarHorTra(tbm_hortra,1);
                blnOk = true;
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
            ex.printStackTrace();
        }
        zafRecHum06.tblRep.clearSelection();
        return blnOk;
    }

    public boolean eliminar() {
        boolean blnOk = false;

        try {
            if(tbm_hortra.getIntCo_hor()>0){
                tbm_hortra.setStrSt_reg("E");
                Tbm_hortraBO.actualizarHorTra(tbm_hortra,2);
                mostrarEstado();
                blnOk = true;
            }else{
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(zafRecHum06,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
            ex.printStackTrace();
        }
        zafRecHum06.tblRep.clearSelection();
        return blnOk;
    }

    public boolean anular() {
        boolean blnOk = false;

        try {
            if(tbm_hortra.getIntCo_hor()>0){
                tbm_hortra.setStrSt_reg("I");
                Tbm_hortraBO.actualizarHorTra(tbm_hortra,2);
                mostrarEstado();
                blnOk = true;
            }else{
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Seleccione un registro válido. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(zafRecHum06,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum06, ex);
            ex.printStackTrace();
        }
        zafRecHum06.tblRep.clearSelection();
        return blnOk;
    }

    public boolean imprimir() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean vistaPreliminar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean aceptar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean cancelar() {
        boolean blnOk = verificarCamReg();

        if(blnOk){
            tbm_hortra = null;
            tbm_hortra = new Tbm_hortra();
            blnLimpTab = true;
            sincronizarFraPoj();
        }
        zafRecHum06.tblRep.clearSelection();
        return blnOk;
    }

    @Override
    public boolean beforeClickInsertar(){
        zafRecHum06.tblRep.clearSelection();
        return verificarCamReg();
    }

    @Override
    public boolean beforeClickEliminar(){
        zafRecHum06.tblRep.clearSelection();
        return verificarCamReg();
    }

    @Override
    public boolean beforeClickAnular(){
        zafRecHum06.tblRep.clearSelection();
        return verificarCamReg();
    }

    public boolean beforeInsertar() {
        zafRecHum06.tblRep.clearSelection();
        return validarCamObl();//&&validarCamNum();
    }

    public boolean beforeConsultar() {
        //return validarCamNum();
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean beforeModificar() {
        return validarCamObl();//&&validarCamNum();
    }

    public boolean beforeEliminar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean beforeAnular() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean beforeImprimir() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean beforeVistaPreliminar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean beforeAceptar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean beforeCancelar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterInsertar() {
        setEstado('w');
        blnMod = false;
        blnActChkMod = false;
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterConsultar() {
        if(tbm_hortra!=null){
            mostrarEstado();
        }
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterModificar() {
        blnMod = false;
        blnActChkMod = false;
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterEliminar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterAnular() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterImprimir() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterVistaPreliminar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterAceptar() {
        zafRecHum06.tblRep.clearSelection();
        return true;
    }

    public boolean afterCancelar() {
        zafRecHum06.tblRep.clearSelection();
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