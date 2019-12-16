
package RecursosHumanos.ZafRecHum05;

import Librerias.ZafMae.ZafMaeBo.Tbm_empBO;
import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_traempBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenTra;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Bean manejador del JInternalFrame ZafRecHum05.
 * Envia datos al BO(Business Object)
 * @author Carlos Lainez
 * Guayaquil 17/06/2011
 */
public class ZafRecHum05Bean {
    
    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private ZafRecHum05 zafRecHum05;                            //JInternalFrame manejado
    private Tbm_traempBO tbr_traempBO;                          //Conección a la Base de Datos
    private List<Tbm_tra> lisTbm_tra;                           //Lista con datos de consulta general
    private ZafVenTra zafVenTra;                                //Ventana de consulta de Empleados
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnActChkMod;                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private DocLis docLis;                                      //Listener que indica si han habido cambios en el documento
    private Tbm_empBO tbm_empBO;                                //Conección a la Base de Datos para obtener las empresas

    public ZafRecHum05Bean(ZafRecHum05 zafRecHum05, ZafParSis zafParSis) {
        try {
            this.zafRecHum05 = zafRecHum05;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            tbr_traempBO = new Tbm_traempBO(zafParSis);
            tbm_empBO = new Tbm_empBO(zafParSis);
            zafUti = new ZafUtil();
            zafVenTra = new ZafVenTra(JOptionPane.getFrameForComponent(zafRecHum05), zafParSis, "Listado de Empleados");
            blnMod = false;
            blnActChkMod = false;
            docLis = new DocLis();
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum05, ex);
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
            zafUti.mostrarMsgErr_F1(zafRecHum05, ex);
        }

        return lisTbm_emp;
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
            blnOk = (JOptionPane.showConfirmDialog(zafRecHum05,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        }

        return blnOk;
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
                zafRecHum05.txtCodTra.setText(zafVenTra.getValueAt(1).toString());
                zafRecHum05.txtNomTra.setText(zafVenTra.getValueAt(3).toString() + " " + zafVenTra.getValueAt(4).toString());
            }
        }catch (Exception ex){
            blnRes=false;
            zafUti.mostrarMsgErr_F1(zafRecHum05, ex);
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
        zafRecHum05.txtCodTra.setEnabled(blnTxtCodTra);
        zafRecHum05.txtNomTra.setEnabled(blnTxtNomTra);
        zafRecHum05.butTra.setEnabled(blnButTra);
        zafRecHum05.txtNomDes.setEnabled(blnTxtNomDes);
        zafRecHum05.txtNomHas.setEnabled(blnTxtNomHas);
        zafRecHum05.txtApeDes.setEnabled(blnTxtApeDes);
        zafRecHum05.txtApeHas.setEnabled(blnTxtApeHas);
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
            if(zafRecHum05.txtCodTra.getText().length()>0){
                comTmp = zafRecHum05.txtCodTra;
                strCom = "Codigo de Empleado";
                Double.parseDouble(zafRecHum05.txtCodTra.getText());
            }
        } catch(NumberFormatException ex){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = strCom+" debe contener sólo números.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(zafRecHum05,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
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
            int intNumCol = zafRecHum05.zafTblModRep.getColumnCount();
            int intNumColDin = intNumCol - (ZafRecHum05.INT_REP_NOMTRA+1);

            //muestro las empresas por empleado
            for(Tbm_tra tmp:lisTbm_tra){
                Vector vecRegCon = new Vector();
                vecRegCon.add(ZafRecHum05.INT_REP_LINEA, "");
                vecRegCon.add(ZafRecHum05.INT_REP_CODTRA, tmp.getIntCo_tra());
                vecRegCon.add(ZafRecHum05.INT_REP_APETRA, tmp.getStrTx_ape());
                vecRegCon.add(ZafRecHum05.INT_REP_NOMTRA, tmp.getStrTx_nom());

                //llenar empresas
                List<Tbm_emp> lisTbm_emp = new ArrayList<Tbm_emp>();
                try {
                    lisTbm_emp = tbr_traempBO.consultarLisGenPorCodTra(tmp.getIntCo_tra());

                    //recorro las columnas dinamicas
                    for(int i=0;i<intNumColDin;i++){
                        //obtengo cada nombre de empresa de la cabecera
                        String strNomEmp = zafRecHum05.tblRep.getColumnName(ZafRecHum05.INT_REP_NOMTRA+1+i);
                        boolean blnChk = false;
                        if(lisTbm_emp!=null){
                            //comparo nombre de empresa de cabecera con las del empleado
                            for(Tbm_emp tmp2:lisTbm_emp){
                                if(blnChk = strNomEmp.equalsIgnoreCase(tmp2.getStrTx_nom())){
                                    break;
                                }
                            }
                        }
                        vecRegCon.add(ZafRecHum05.INT_REP_NOMTRA+1+i, blnChk);
                    }
                } catch (Exception ex) {
                    zafUti.mostrarMsgErr_F1(zafRecHum05, ex);
                }
                vecTblRep.add(vecRegCon);
            }
            if(!vecTblRep.isEmpty()){
                zafRecHum05.zafTblModRep.setData(vecTblRep);
                zafRecHum05.tblRep.setModel(zafRecHum05.zafTblModRep);
            }
        }
    }

    public void consultarRep(){
        
        lisTbm_tra = null;
        
        java.sql.Connection conn = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{

            conn=java.sql.DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            
            if(conn!=null){
            
                stmLoc=conn.createStatement();
                
                if(zafRecHum05.optTod.isSelected()){

                    if(zafParSis.getCodigoEmpresa()==zafParSis.getCodigoEmpresaGrupo())
                    {                        
                         strSql = "select distinct a.co_tra, a.tx_nom, a.tx_ape from tbm_tra a "+ /*razs*/
                                  "inner join tbm_traemp b on (a.co_tra  = b.co_tra) "+
                                  "and b.st_reg like 'A' ";                            

                    }else{

                        strSql = "select distinct a.co_tra, a.tx_nom, a.tx_ape from tbm_tra a "+
                                 "inner join tbm_traemp b on (a.co_tra  = b.co_tra) "+
                                 "where b.co_emp = " + zafParSis.getCodigoEmpresa() + " and b.st_reg like 'A' ";
                                
                    }
                }else{

                    if(zafRecHum05.optCri.isSelected()){

                        if(zafParSis.getCodigoEmpresa()==zafParSis.getCodigoEmpresaGrupo()){
                            
                            if (zafRecHum05.txtCodTra.getText().length() > 0 && validarCamNum()) {
                                
                                strSql = "select distinct co_tra, tx_nom, tx_ape from tbm_tra where co_tra = " + zafRecHum05.txtCodTra.getText() + " and st_reg like 'A'";// order by co_tra";
                                
                            }else{
                                
                                if(zafRecHum05.txtNomDes.getText().length()>0 || zafRecHum05.txtNomHas.getText().length()>0 || zafRecHum05.txtApeDes.getText().length()>0 || zafRecHum05.txtApeHas.getText().length()>0){
                                    
                                    strSql = "select * from tbm_tra ";
                                    strSql += "where ('"+zafRecHum05.txtNomDes.getText()+"' is null or (lower(tx_nom) between lower('"+zafRecHum05.txtNomDes.getText()+"') and lower('"+zafRecHum05.txtNomHas.getText()+"')) or lower(tx_nom) like '"+zafRecHum05.txtNomDes.getText()+"%') ";
                                    strSql += "and ('"+zafRecHum05.txtApeDes.getText()+"' is null or (lower(tx_ape) between lower('"+zafRecHum05.txtApeDes.getText()+"') and lower('"+zafRecHum05.txtApeHas.getText()+"')) or lower(tx_ape) like '"+zafRecHum05.txtApeDes.getText()+"%') ";
                                    strSql += "and st_reg like 'A'";// order by co_tra";
                                    
                                }
                            }
                        }else{
                            
                            if (zafRecHum05.txtCodTra.getText().length() > 0 && validarCamNum()) {
                                
                                strSql ="select distinct a.co_tra, a.tx_nom, a.tx_ape from tbm_tra a "+
                                        "inner join tbm_traemp b on a.co_tra  = b.co_tra "+
                                        "where b.co_emp = " + zafParSis.getCodigoEmpresa() + " and b.st_reg like 'A' and a.co_tra = " + zafRecHum05.txtCodTra.getText() + " ";
                                        //"order by a.co_tra";
                                
                            }else{
                                
                                if(zafRecHum05.txtNomDes.getText().length()>0 || zafRecHum05.txtNomHas.getText().length()>0 || zafRecHum05.txtApeDes.getText().length()>0 || zafRecHum05.txtApeHas.getText().length()>0){
                                    
                                    strSql = "select * from tbm_tra a ";
                                    strSql += "inner join tbm_traemp b on a.co_tra  = b.co_tra ";
                                    strSql += "where ('"+zafRecHum05.txtNomDes.getText()+"' is null or (lower(a.tx_nom) between lower('"+zafRecHum05.txtNomDes.getText()+"') and lower('"+zafRecHum05.txtNomHas.getText()+"')) or lower(a.tx_nom) like '"+zafRecHum05.txtNomDes.getText()+"%') ";
                                    strSql += "and ('"+zafRecHum05.txtApeDes.getText()+"' is null or (lower(a.tx_ape) between lower('"+zafRecHum05.txtApeDes.getText()+"') and lower('"+zafRecHum05.txtApeHas.getText()+"')) or lower(a.tx_ape) like '"+zafRecHum05.txtApeDes.getText()+"%') ";
                                    strSql += "and b.st_reg like 'A' and b.co_emp = "+zafParSis.getCodigoEmpresa();//+" order by a.co_tra";
                                    
                                }
                            }
                        }
                    }
                }
                strSql+=" order by tx_ape, tx_nom";
                //System.out.println("consulta realizada: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);

                if(rstLoc.next()){

                    lisTbm_tra = new ArrayList<Tbm_tra>();

                    do{

                        Tbm_tra tbm_tra = new Tbm_tra();
                        tbm_tra.setIntCo_tra(rstLoc.getInt("co_tra"));
                        tbm_tra.setStrTx_nom(rstLoc.getString("tx_nom"));
                        tbm_tra.setStrTx_ape(rstLoc.getString("tx_ape"));
                        lisTbm_tra.add(tbm_tra);
                        
                        if (zafRecHum05.txtCodTra.getText().length() > 0 && validarCamNum()) {
                            zafRecHum05.txtNomTra.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                        }
                    }while(rstLoc.next());
                }
            }
        
            rstLoc.close();
            rstLoc=null;

            stmLoc.close();
            stmLoc=null;
            conn.close();
            conn=null;
        }catch(java.sql.SQLException Evt) { Evt.printStackTrace();  zafUti.mostrarMsgErr_F1(zafRecHum05, Evt);  }
        catch(Exception Evt) { Evt.printStackTrace();  zafUti.mostrarMsgErr_F1(zafRecHum05, Evt);  }
        System.gc();
            
        if(lisTbm_tra!=null){
            sincronizarFraPoj();
            if(zafRecHum05.tblRep.getRowCount()>=1){
                zafRecHum05.tabGen.setSelectedIndex(1);
            }
        }else{
            zafRecHum05.zafTblModRep.removeAllRows();
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
            zafRecHum05.txtCodTra.setText("");
            zafRecHum05.txtNomTra.setText("");
            JOptionPane.showMessageDialog(zafRecHum05,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public void grabarTraEmp(){

        boolean blnRes = false;
        try{
            //recorro filas de los empleados
            int intNumRow = zafRecHum05.zafTblModRep.getRowCount();
            for(int i=0;i<intNumRow;i++){
                int intNumCol = zafRecHum05.zafTblModRep.getColumnCount();
                int intNumColDin = intNumCol - (ZafRecHum05.INT_REP_NOMTRA+1);
                int intCodTra = (Integer)zafRecHum05.tblRep.getValueAt(i, ZafRecHum05.INT_REP_CODTRA);

                //recorro empresas por empleado
                for(int j=0;j<intNumColDin;j++){
                    String strNomEmp = zafRecHum05.tblRep.getColumnName(ZafRecHum05.INT_REP_NOMTRA+1+j);
                    boolean blnCheck = (Boolean)zafRecHum05.tblRep.getValueAt(i, ZafRecHum05.INT_REP_NOMTRA+1+j);

                    //consultar empresa por nombre
                    List<Tbm_emp> lisTbm_emp = tbm_empBO.consultarLisGenPorNomEmp(strNomEmp);
                    //consulto si existe empresa asignada al empleado
                    List<Tbm_emp> lisTbm_empExiste = new ArrayList<Tbm_emp>();
                    lisTbm_empExiste = tbr_traempBO.consultarLisGenEmpPorCodTraNomEmp(intCodTra, strNomEmp);
                    if(lisTbm_empExiste==null){
                        //si esta chequeado y no existe en la base lo grabo
                        if(blnCheck){
                            
                            Tbm_traemp tmp = new Tbm_traemp();
                            tmp.setIntCo_emp(lisTbm_emp.get(0).getIntCo_emp());
                            tmp.setIntCo_tra(intCodTra);
                            tbr_traempBO.grabarTraEmp(tmp);
                        }
                    }else{
                        //si existe en la base y se ha quitado el visto se elimina
                        if(!blnCheck){
                            tbr_traempBO.eliminarTraEmp(lisTbm_emp.get(0).getIntCo_emp(), intCodTra);
                        }
                    }
                }
            }
            blnRes = true;
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum05, ex);
        }
        
        if(blnRes){
            mostrarMsgInf("La operación GUARDAR se realizó con éxito");
            zafRecHum05.blnMod = false;
        }
        /*}else{
            mostrarMsgInf("No se han realizado cambios para que sean guardados");
            //zafRecHum05.butGua.setEnabled(false);
        }*/
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
        oppMsg.showMessageDialog(zafRecHum05,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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