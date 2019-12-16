//http://www.multitrabajos.com.ec/empleos/administrador-de-telefonia-guayaquil-big-cola-1000377304.html
package RecursosHumanos.ZafRecHum17;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_callabBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_hortraBO;
import Librerias.ZafRecHum.ZafRecHumBo.Tbm_traempBO;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_hortraDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_callab;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Bean manejador del JInternalFrame zafRecHum17.
 * Envia datos al BO(Business Object)
 * @author Roberto Flores
 * Guayaquil 15/09/2011
 */
public class ZafRecHum17Bean {

    private Tbm_hortraDAOInterface tbm_hortraDAOInterface;

    private ZafParSis zafParSis;                                //Parámetros del Sistema
    private ZafUtil zafUti;                                     //Utilidades
    private Tbm_traemp tbr_traemp;                              //Pojo
    private Tbm_traemp tbr_traemppar;                           //Parametros
    private ZafRecHum17 zafRecHum17;                            //JInternalFrame manejado
    private Tbm_traempBO tbr_traempBO;                          //Conección a la Base de Datos
    private Tbm_tra tbm_tra;                                    //Pojo de Empleados
    private List<Tbm_traemp> lisTbr_traemp;                     //Lista con datos de consulta general
    private Tbm_callabBO tbm_callabBO;                          //Coneccion a la Base de Datos
    private Tbm_hortraBO Tbm_hortraBO;                          //Coneccion a la Base de Datos
    private boolean blnMod;                                     //Indica si han habido cambios en el documento
    private ZafUtil objUti;

    public ZafRecHum17Bean(ZafRecHum17 zafRecHum17, ZafParSis zafParSis) {
        try {
            this.zafRecHum17 = zafRecHum17;
            this.zafParSis = (ZafParSis) zafParSis.clone();
            this.objUti=new ZafUtil();
            tbr_traemp = new Tbm_traemp();
            tbr_traemppar = new Tbm_traemp();
            tbm_callabBO = new Tbm_callabBO(zafParSis);
            Tbm_hortraBO = new Tbm_hortraBO(zafParSis);
            zafUti = new ZafUtil();
            lisTbr_traemp = null;
            blnMod = false;
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum17, ex);
            ex.printStackTrace();
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
     * Setea el año en txtAño luego de consultar el maximo de fechas en la base de datos.
     * Si no existen fechas en la base de datos setea el año en curso.
     */
    public void setearFecha(){

        try {
            Date dateMaxAño = tbm_callabBO.consultaAño();
            if(dateMaxAño==null){
                GregorianCalendar gFechaActual = new GregorianCalendar();
                gFechaActual.setTime( new java.util.Date() );
                String mes1 = Integer.toString(gFechaActual.get(GregorianCalendar.YEAR));
                zafRecHum17.txtAño.setText(mes1);
            }else{
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateMaxAño);
                zafRecHum17.txtAño.setText(String.valueOf(cal.get(1)+1));
            }
        } catch (Exception ex) {
            zafUti.mostrarMsgErr_F1(zafRecHum17, ex);
            ex.printStackTrace();
        }
    }

    /**
     * Habilita o deshabilita los componentes funcionales de la pantalla
     * @param blnTxtAño true habilita, false deshabilita TxtAño
     */
    public void habilitarCom(boolean blnTxtAño){
        zafRecHum17.txtAño.setEnabled(blnTxtAño);
    }

    public void procesarCalendarioLaboral() throws SQLException, Exception {

        ArrayList<Tbm_callab> arrTbm_callab = null;
        Connection con = null;
        PreparedStatement preSta = null;
        String strSql = "";
        Date datFecHoy = null;
        boolean res = false;
        java.sql.Statement stmLoc = null;

        try{

            tbm_hortraDAOInterface = (Tbm_hortraDAOInterface) Tbm_hortraBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_hortraDAO").newInstance();
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){

                con.setAutoCommit(false);

                List<Tbm_hortra> lisTbm_hortra = tbm_hortraDAOInterface.consultar(con);

                if(lisTbm_hortra!=null){


                    arrTbm_callab = new ArrayList<Tbm_callab>();
                    Calendar cal = Calendar.getInstance();;
                    int intDiasValidosIniciales;
                    int intMesesValidosIniciales;
                    int intCodHor;

                    String formato="yyyy";
                    SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
                    datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                    int intAño = Integer.parseInt(zafRecHum17.txtAño.getText());
                    int intRecAño = 0;
                    
                    int[] intMeses = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                    cal.set(Integer.parseInt(zafRecHum17.txtAño.getText()), intMeses[0], 1);

                    for(int intRecLisTbm = 0; intRecLisTbm < lisTbm_hortra.size(); intRecLisTbm++){

                        Tbm_hortra tbm_hortra = lisTbm_hortra.get(intRecLisTbm);

                        for(intRecAño = intAño; intRecAño <= Integer.parseInt(zafRecHum17.txtAño.getText()); intRecAño++){

                            formato="MM";
                            dateFormat = new SimpleDateFormat(formato);
                            intMesesValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));

                            formato="dd";
                            dateFormat = new SimpleDateFormat(formato);
                            intDiasValidosIniciales = Integer.parseInt(dateFormat.format(datFecHoy));//Tbm_hortraBO.consultaMesesValidosIniciales();
                            intCodHor = tbm_hortra.getIntCo_hor(); //Tbm_hortraBO.consultaCodHor();
                            
                            int total = 0;
                            int tamStrTbl = tbm_hortra.getStrTbl_entsaldet().length;

                            //armo los horarios
                            String[][] horarios = tbm_hortra.getStrTbl_entsaldet();
                            for(int i = 0; i < tamStrTbl; i++){
                                horarios[i][0] = tbm_hortra.getStrTbl_entsaldet()[i][0];
                                horarios[i][1] = tbm_hortra.getStrTbl_entsaldet()[i][1];
                                horarios[i][2] = tbm_hortra.getStrTbl_entsaldet()[i][2];
                            }

                            int intMesMin = 0;
                            int intMesMax = intMeses[intMeses.length-1];
                            int intDiaMin = 1;

                            for(int intMes = intMesMin; intMes <= intMesMax; intMes++){
                                
                                int intDiaMax = cal.getActualMaximum(cal.DATE);
                                cal.set(intRecAño, intMes, intDiaMin);

                                for(int intDia = intDiaMin; intDia <= intDiaMax; intDia++){

                                    Tbm_callab tmp = new Tbm_callab();
                                    cal.set(intRecAño, intMes, intDia);
                                    intDiaMax = cal.getActualMaximum(cal.DATE);
                                    datFecHoy = cal.getTime();
                                    System.out.println(datFecHoy.toString());
                                    int day = datFecHoy.getDay();
                                    if(day==0){day=6;}else{
                                        day--;
                                    }
                                    
                                    tmp.setDteFe_Dia(datFecHoy);
                                    tmp.setIntCo_Hor(intCodHor);
                                    tmp.setStrSt_DiaLab("S");
                                    tmp.setStrHo_Ent(horarios[day][0]);
                                    tmp.setStrHo_Sal(horarios[day][1]);
                                    tmp.setStrMin_GraEnt(horarios[day][2]);
                                    arrTbm_callab.add(tmp);
                                    total++;
                                }
                                
                                intDiaMin = 1;
                                //System.out.println(total);
                                total = 0;
                            }
                            
                            cal.set(intRecAño, intMeses[0], 1);
                            datFecHoy = cal.getTime();
                        }
                    }

                    DateFormat sdf = new SimpleDateFormat("hh:mm");
                    Date date = null;
                    
                    

                    for(Iterator<Tbm_callab> it = arrTbm_callab.iterator(); it.hasNext();){

                        Tbm_callab tbm_callab = it.next();

                        /*strSql = "insert into tbm_callab(fe_dia, co_hor, st_dialab, ho_ent, ho_sal, ho_mingraent, tx_obs1) ";
                        strSql += "values(?,?,?,?,?,?,?)";*/
                        
                        
                        String strHoEnt = tbm_callab.getStrHo_Ent();
                        if(strHoEnt==null || strHoEnt.compareTo("")==0){
                            strHoEnt="null";
                        }else{
                            strHoEnt="'"+strHoEnt+"'";
                        }
                        
                        String strHoSal = tbm_callab.getStrHo_Sal();
                        if(strHoSal==null || strHoSal.compareTo("")==0){
                            strHoSal="null";
                        }else{
                            strHoSal="'"+strHoSal+"'";
                        }
                        
                        String strMinGra = tbm_callab.getStrMin_GraEnt();
                        if(strMinGra==null || strMinGra.compareTo("")==0){
                            strMinGra="null";
                        }else{
                            strMinGra="'"+strMinGra+"'";
                        }
                        
                        String strObs = tbm_callab.getStrTx_Obs1();
                        if(strObs!=null){
                            strObs = "'"+strObs+"'";
                        }else{
                            strObs="null";
                        }

                        String strFecha = objUti.formatearFecha(tbm_callab.getDteFe_Dia(),  "yyyy-MM-dd");

                        strSql = "insert into tbm_callab(fe_dia, co_hor, st_dialab, ho_ent, ho_sal, ho_mingraent, tx_obs1) ";
                        strSql += "values('"+strFecha+"',"+tbm_callab.getIntCo_Hor()+",'"+tbm_callab.getStrSt_DiaLab()+"',"+strHoEnt+","+
                                strHoSal+","+strMinGra+","+strObs+")";
                        stmLoc = con.createStatement();

                        stmLoc.executeUpdate(strSql);

                        /*preSta = con.prepareStatement(strSql);

                        if(tbm_callab.getDteFe_Dia()!=null){
                            date = tbm_callab.getDteFe_Dia();
                            preSta.setDate(1, new java.sql.Date(date.getTime()));
                        }else{
                            preSta.setNull(1, Types.DATE);
                        }
                        if(tbm_callab.getIntCo_Hor()>0){
                            preSta.setInt(2, tbm_callab.getIntCo_Hor());
                        }else{
                            preSta.setNull(2, Types.INTEGER);
                        }
                        if(tbm_callab.getStrSt_DiaLab()!=null){
                            preSta.setString(3, tbm_callab.getStrSt_DiaLab());
                        }else{
                            preSta.setNull(3, Types.VARCHAR);
                        }
                        if(tbm_callab.getStrHo_Ent()==null || tbm_callab.getStrHo_Ent().compareTo("")==0){
                            preSta.setTime(4, null);
                        }else{
                            date = sdf.parse(tbm_callab.getStrHo_Ent());
                            preSta.setTimestamp(4, new Timestamp(date.getTime()));
                        }
                        if(tbm_callab.getStrHo_Sal()==null || tbm_callab.getStrHo_Sal().compareTo("")==0){
                            preSta.setTime(5, null);
                        }else{
                            date = sdf.parse(tbm_callab.getStrHo_Sal());
                            preSta.setTimestamp(5, new Timestamp(date.getTime()));
                        }
                        if(tbm_callab.getStrMin_GraEnt()==null || tbm_callab.getStrMin_GraEnt().compareTo("")==0){
                            preSta.setTime(6, null);
                        }else{
                            date = sdf.parse(tbm_callab.getStrMin_GraEnt());
                            preSta.setTimestamp(6, new Timestamp(date.getTime()));
                        }
                        if(tbm_callab.getStrTx_Obs1()!=null){
                            preSta.setString(7, tbm_callab.getStrTx_Obs1());
                        }else{
                            preSta.setNull(7, Types.VARCHAR);
                        }

                        preSta.executeUpdate();*/

                    }
                    
                    res = true;
                }else{
                    mostrarMsgInf("No hay disponible Horarios de Trabajo para realizar este proceso");
                }
            }

            if(res){
                con.commit();
                setearFecha();
                mostrarMsgInf("La operación PROCESAR se realizó con éxito");
            }else{
                mostrarMsgInf("La operación PROCESAR no se realizó correctamente");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            zafUti.mostrarMsgErr_F1(zafRecHum17, ex);
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            zafUti.mostrarMsgErr_F1(zafRecHum17, ex);
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
            con.close();
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
        oppMsg.showMessageDialog(zafRecHum17,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un panel de consulta para cerrar la ventana
     * @return Retorna true si se desea cerrar o false caso contrario
     */
    public boolean cerrarIntFra(){
        boolean blnOk = true;
        String strMsg = "Está Seguro que desea cerrar este programa?";
        String strTit = "Mensaje del sistema Zafiro";
        blnOk = (JOptionPane.showConfirmDialog(zafRecHum17,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        return blnOk;
    }
}
