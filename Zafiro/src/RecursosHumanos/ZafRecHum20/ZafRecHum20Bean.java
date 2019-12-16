
package RecursosHumanos.ZafRecHum20;

import Librerias.ZafMae.ZafMaeBo.Tbm_empBO;
import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_hortraBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_traBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_traempBO;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum20.
 * Envia datos al BO(Business Object)
 * @author Roberto Flores
 * Guayaquil 07/11/2011
 */
public class ZafRecHum20Bean {

    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private ZafRecHum20 zafRecHum20;                            //JInternalFrame manejado
    private Tbm_traempBO tbr_traempBO;                          //Conección a la Base de Datos
    private Tbm_hortraBO tbm_hortraBO;                          //Conección a la Base de Datos
    private Tbm_traBO tbm_traBO;                                //Coneccion a la Base de Datos
    private List<Tbm_tra> lisTbm_tra;                           //Lista con datos de consulta general
    private ZafVenTra zafVenTra;                                //Ventana de consulta de Empleados
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento
    private Tbm_empBO tbm_empBO;                                //Conección a la Base de Datos para obtener las empresas

    public ZafRecHum20Bean(ZafRecHum20 zafRecHum20, ZafParSis zafParSis) {
        try {
            this.zafRecHum20 = zafRecHum20;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbr_traempBO = new Tbm_traempBO(zafParSis);
            tbm_traBO = new Tbm_traBO(zafParSis);
            tbm_empBO = new Tbm_empBO(zafParSis);
            tbm_hortraBO = new Tbm_hortraBO(zafParSis);
            zafUti = new ZafUtil();
            zafVenTra = new ZafVenTra(JOptionPane.getFrameForComponent(zafRecHum20), zafParSis, "Listado de Empleados");
            zafRecHum20.blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        }catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
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
     * @return the docLis
     */
    public DocLis getDocLis() {
        return docLis;
    }

    public List<Tbm_emp> consultarLisGenEmpSinEmpGru(){
        List<Tbm_emp> lisTbm_emp = null;

        try {
            lisTbm_emp = tbm_empBO.consultarLisGenSinEmpGru();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
        }

        return lisTbm_emp;
    }

    public List<Tbm_hortra> consultarLisGenCabHorTra(){
        List<Tbm_hortra> lisTbm_hortra = null;

        try {
            lisTbm_hortra = tbm_traBO.consultarLisGenCabHorTra();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
        }

        return lisTbm_hortra;
    }

    /**
     * Permite visualizar la ventana de consulta de Trabajadores
     * @return Retorna true si no hay errores y false por caso contrario.
     */
    public boolean mostrarVenTra(){
        
        boolean blnRes=true;

        try{
            zafVenTra.limpiar();
            zafVenTra.setCampoBusqueda(0);
            zafVenTra.setVisible(true);

            if (zafVenTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                zafRecHum20.txtCodTra.setText(zafVenTra.getValueAt(1).toString());
                zafRecHum20.txtNomTra.setText(zafVenTra.getValueAt(3).toString() + " " + zafVenTra.getValueAt(4).toString());
                consultarRep();
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
        }

        return blnRes;
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtCodTra true habilita, false deshabilita TxtCodTra
     * @param blnTxtNomTra true habilita, false deshabilita TxtNomTra
     * @param blnTxtNomDes true habilita, false deshabilita TxtNomDes
     * @param blnTxtNomHas true habilita, false deshabilita TxtNomHas
     * @param blnTxtApeDes true habilita, false deshabilita TxtApeDes
     * @param blnTxtApeHas true habilita, false deshabilita TxtApeHas
     */
    public void habilitarCom(boolean blnTxtCodTra, boolean blnTxtNomTra, boolean blnButTra, boolean blnTxtNomDes, boolean blnTxtNomHas, boolean blnTxtApeDes, boolean blnTxtApeHas){
        zafRecHum20.txtCodTra.setEnabled(blnTxtCodTra);
        zafRecHum20.txtNomTra.setEnabled(blnTxtNomTra);
        zafRecHum20.butTra.setEnabled(blnButTra);
        zafRecHum20.txtNomDes.setEnabled(blnTxtNomDes);
        zafRecHum20.txtNomHas.setEnabled(blnTxtNomHas);
        zafRecHum20.txtApeDes.setEnabled(blnTxtApeDes);
        zafRecHum20.txtApeHas.setEnabled(blnTxtApeHas);
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
            if(zafRecHum20.txtCodTra.getText().length()>0){
                comTmp = zafRecHum20.txtCodTra;
                strCom = "Codigo de Empleado";
                Double.parseDouble(zafRecHum20.txtCodTra.getText());
            }
        } catch(NumberFormatException ex){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = strCom+" debe contener sólo números.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum20,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            ((JTextField)comTmp).requestFocus();
            ((JTextField)comTmp).selectAll();
        }

        return blnOk;
    }

    /**
     * Permite sincronizar el contenido de la interfaz de usuario con el contenido del pojo
     */
    private void sincronizarFraPoj(){

        if(lisTbm_tra!=null){
            Vector vecTblRep = new Vector();
            int intNumCol = zafRecHum20.zafTblModRep.getColumnCount();
            int intNumColDin = intNumCol - (ZafRecHum20.INT_REP_NOMTRA+1);

            //muestro las empresas por empleado
            for(Tbm_tra tmp:lisTbm_tra){
                Vector vecRegCon = new Vector();
                vecRegCon.add(ZafRecHum20.INT_REP_LINEA, "");
                vecRegCon.add(ZafRecHum20.INT_REP_CODTRA, tmp.getIntCo_tra());
                vecRegCon.add(ZafRecHum20.INT_REP_APETRA, tmp.getStrTx_ape());
                vecRegCon.add(ZafRecHum20.INT_REP_NOMTRA, tmp.getStrTx_nom());

                //llenar empresas
                List<String> lisTbm_emp = new ArrayList<String>();
                String strTbm_traHorFij = null;
                try {
                    //
                    //lisTbm_emp = tbr_traempBO.consultarLisGenPorCodTra2(tmp.getIntCo_tra(), zafParSis.getCodigoEmpresa());
                    lisTbm_emp = tbr_traempBO.consultarLisGenPorCodTra2(tmp.getIntCo_tra());
                    strTbm_traHorFij = tbr_traempBO.consultarLisGenPorCodTraPorHor(tmp.getIntCo_tra());
                    //recorro las columnas dinamicas
                    for(int i=0;i<intNumColDin;i++){
                        //obtengo cada nombre de empresa de la cabecera
                        String strNomEmp = zafRecHum20.tblRep.getColumnName(ZafRecHum20.INT_REP_NOMTRA+1+i);
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
                        if (strTbm_traHorFij!=null) {
                                if(strTbm_traHorFij.equals("S")){
                                    vecRegCon.setElementAt(true,4);
                            } 
                        }
                        //if(blnChk){
                          //  vecRegCon.setElementAt(blnChk,4);
                        //}
                    }
                } catch (Exception ex) {
                    zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                }
                vecTblRep.add(vecRegCon);
            }
            if(!vecTblRep.isEmpty()){
                zafRecHum20.zafTblModRep.setData(vecTblRep);
                zafRecHum20.tblRep.setModel(zafRecHum20.zafTblModRep);
            }
        }
    }

    public void consultarRep(){

        lisTbm_tra = null;
        int intCodEmp = zafParSis.getCodigoEmpresa();
        
        if(zafParSis.getCodigoEmpresa()==zafParSis.getCodigoEmpresaGrupo()){

            if(zafRecHum20.optTod.isSelected()){
                try {
                    Tbm_tra tbm_trapar = new Tbm_tra();
                    lisTbm_tra = tbm_traBO.consultarLisGen(tbm_trapar);
                } catch (Exception ex) {
                    zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                }
            }else{
                if(zafRecHum20.optCri.isSelected()){
                    try {
                        if (zafRecHum20.txtCodTra.getText().length() > 0 && validarCamNum()) {
                            Tbm_tra tbm_trapar = new Tbm_tra();
                            tbm_trapar.setIntCo_tra(Integer.parseInt(zafRecHum20.txtCodTra.getText()));
                            lisTbm_tra = tbm_traBO.consultarLisGen(tbm_trapar);
                        }else{
                            if(zafRecHum20.txtNomDes.getText().length()>0 || zafRecHum20.txtNomHas.getText().length()>0 || zafRecHum20.txtApeDes.getText().length()>0 || zafRecHum20.txtApeHas.getText().length()>0){
                                lisTbm_tra = tbr_traempBO.consultarLisGenTraDesHas(zafRecHum20.txtNomDes.getText(), zafRecHum20.txtNomHas.getText(), zafRecHum20.txtApeDes.getText(), zafRecHum20.txtApeHas.getText());
                            }
                        }
                    } catch (Exception ex) {
                        zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                    }
                }
            }
        }else{

            if(zafRecHum20.optTod.isSelected()){
                try {
                    Tbm_tra tbm_trapar = new Tbm_tra();
                    lisTbm_tra = tbm_traBO.consultarLisGen(intCodEmp);
                } catch (Exception ex) {
                    zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                }
            }else{
                if(zafRecHum20.optCri.isSelected()){
                    try {
                        if (zafRecHum20.txtCodTra.getText().length() > 0 && validarCamNum()) {
                            Tbm_tra tbm_trapar = new Tbm_tra();
                            tbm_trapar.setIntCo_tra(Integer.parseInt(zafRecHum20.txtCodTra.getText()));
                            lisTbm_tra = tbm_traBO.consultarLisGen(tbm_trapar, intCodEmp);
                        }else{
                            if(zafRecHum20.txtNomDes.getText().length()>0 || zafRecHum20.txtNomHas.getText().length()>0 || zafRecHum20.txtApeDes.getText().length()>0 || zafRecHum20.txtApeHas.getText().length()>0){
                                lisTbm_tra = tbr_traempBO.consultarLisGenTraDesHas(zafRecHum20.txtNomDes.getText(), zafRecHum20.txtNomHas.getText(), zafRecHum20.txtApeDes.getText(), zafRecHum20.txtApeHas.getText(), intCodEmp);
                            }
                        }
                    } catch (Exception ex) {
                        zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                    }
                }
            }
        }

        if(lisTbm_tra!=null){
            sincronizarFraPoj();
            if(zafRecHum20.tblRep.getRowCount()>=1){
                zafRecHum20.tabGen.setSelectedIndex(1);
            }
            if (zafRecHum20.txtCodTra.getText().length() > 0 && validarCamNum()) {
                zafRecHum20.txtNomTra.setText(lisTbm_tra.get(0).getStrTx_ape() + " " + lisTbm_tra.get(0).getStrTx_nom());
            }
        }else{
            zafRecHum20.zafTblModRep.removeAllRows();
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum20,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }
    }
//Aqui es el dato para que grabe los dos sin check bostel
    public void grabarTraEmp(){
        
        boolean blnRes = true;
        int intCo_emp = zafParSis.getCodigoEmpresa();
        
        for(int i=0; i<zafRecHum20.tblRep.getRowCount(); i++){
            
            if(zafRecHum20.tblRep.getValueAt(i, zafRecHum20.INT_REP_LINEA).toString().compareTo("M")==0){
            
                try{
                    int intNumCol2 = zafRecHum20.zafTblModRep.getColumnCount();
                    int intNumColDin2 = intNumCol2 - (ZafRecHum20.INT_REP_NOMTRA+1);
                    int intCodTra = (Integer)zafRecHum20.tblRep.getValueAt(i, ZafRecHum20.INT_REP_CODTRA);
                
                    boolean blnComp=false;
                    for(int j=0;j<intNumColDin2-1;j++){
                                //String strNomHorFijo = zafRecHum20.tblRep.getColumnName(ZafRecHum20.INT_REP_CHKHORFIJ);
                                String strNomHor = zafRecHum20.tblRep.getColumnName(ZafRecHum20.INT_REP_CHKHORFIJ+1+j);
                                boolean blnCheck = (Boolean)zafRecHum20.tblRep.getValueAt(i, ZafRecHum20.INT_REP_CHKHORFIJ+1+j);
                                int intCoHor = tbm_hortraBO.consultarCodHorRep(strNomHor);

                                //si esta chequeado y no existe en la base lo grabo
                                if(blnCheck){
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
                    zafRecHum20.blnMod = false;
                    zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                }catch(Exception ex){
                    blnRes = false;
                    zafRecHum20.blnMod = false;
                    zafUti.mostrarMsgErr_F1(zafRecHum20, ex);
                }
            }
        }

        if(blnRes){

            int intNumRow = zafRecHum20.zafTblModRep.getRowCount();
            int intNumCol = zafRecHum20.zafTblModRep.getColumnCount();
            int intNumColDin = intNumCol - (ZafRecHum20.INT_REP_NOMTRA+1);
                    
            for(int i=0;i<intNumRow;i++){
                for(int j=0;j<intNumColDin-1;j++){
                    boolean blnCheck = (Boolean)zafRecHum20.tblRep.getValueAt(i, ZafRecHum20.INT_REP_CHKHORFIJ+1+j);
                    if(blnCheck){
                        zafRecHum20.tblRep.setValueAt(blnCheck, i, 4);
                    }
                }
            }
            mostrarMsgInf("La operación GUARDAR se realizó con éxito");
           // new RRHHDao(zafUti, zafParSis).callServicio9();//tony
            zafRecHum20.blnMod = false;
        }
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
        oppMsg.showMessageDialog(zafRecHum20,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Listener que indica si han habido cambios en el documento
     */
    public class DocLis implements DocumentListener{
        public void changedUpdate(DocumentEvent evt){
            if(blnActChkMod){
                zafRecHum20.blnMod = true;
                blnActChkMod = false;
            }
        }

        public void insertUpdate(DocumentEvent evt){
            if(blnActChkMod){
                zafRecHum20.blnMod = true;
                blnActChkMod = false;
            }
        }

        public void removeUpdate(DocumentEvent evt){
            if(blnActChkMod){
                zafRecHum20.blnMod = true;
                blnActChkMod = false;
            }
        }
    }
}