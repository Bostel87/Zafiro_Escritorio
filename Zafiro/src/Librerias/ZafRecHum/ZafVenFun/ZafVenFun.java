/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafVenFun;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.Provisiones;
import Librerias.ZafUtil.ZafUtil;
import RecursosHumanos.ZafRecHum09.ZafRecHum09;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Vector;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Roberto Flores
 * Guayaquil, 08 de Abril del 2013
 */
public class ZafVenFun {
    
    private ZafParSis objParSis;
    private ZafUtil objUti; 
    private Object obj;
    
    private Vector vecDat, vecCab, vecReg;
    private String strVer="v1.05";
    
    public ZafVenFun(ZafParSis objParSis, ZafUtil objUti, Object obj){
        this.objParSis=objParSis;
        this.objUti=objUti;
        this.obj=obj;
    }
    
    public ZafVenFun(ZafParSis objParSis, ZafUtil objUti){
        this.objParSis=objParSis;
        this.objUti=objUti;
    }
    
    public boolean ZafVenAmtPrgSue(Connection con, int intRecValMesOrgAux, int intCoTra, int intCoEmp, double dblBonAct, double dblMovAct) throws Exception{
        String[] arrVar={"=","+","-","+","-","+","-","+","-","+","-","+"};
        java.sql.Statement stmLoc=null;
        java.sql.Statement stmLocAux=null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstLocAux = null;
        String strFeIniCon=null;
        String strFeFinPerPruCon=null;
        String strFeFinCon=null;
        String strFeReaFinCon=null;
        boolean blnRes=false;
        String strSql="";

        try{
            if(con!=null){
                con.setAutoCommit(false);
                stmLoc = con.createStatement();
                stmLocAux = con.createStatement();
                
                int intArrDiasMes[] = new int[12];
                double dblValBonMes[] = new double[13];
                double dblValMovMes[] = new double[13];
                
                double dblValPrgBonMes[] = new double[13];
                double dblValPrgMovMes[] = new double[13];
                
                double dblTotBon=0;
                double dblTotMov=0;
                
                double dblTotBonPagado=0;
                double dblTotMovPagado=0;
                
                java.util.Calendar CalVal = null;
                int intCon = 0;
                CalVal =  java.util.Calendar.getInstance();
                CalVal.set(java.util.Calendar.YEAR,CalVal.get(Calendar.MONTH));
                CalVal.set(java.util.Calendar.MONTH,12);
                intArrDiasMes[intCon++]=CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

                for(int intMes=1;intMes<=11;intMes++){
                    CalVal =  java.util.Calendar.getInstance();
                    CalVal.set(java.util.Calendar.MONTH,intMes-1);
                    intArrDiasMes[intCon++]=CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                }

                for(int intFil=0; intFil<1;intFil++){

                        strSql="";
                        strSql+="select a.co_emp, a.co_tra,a.fe_inicon, a.fe_finperprucon,a.fe_fincon,a.st_perprucon,";
                        strSql+=" a.st_fincon,a.fe_reafincon from tbm_traemp a";
                        strSql+=" where a.co_tra="+intCoTra;
                        strSql+=" and a.co_emp="+intCoEmp;

                        System.out.println("consulta reg sel: " + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);

                        vecDat = new Vector();
                        java.util.Vector vecReg = null;

                        if(rstLoc.next()){
                            
                            strFeIniCon=rstLoc.getString("fe_inicon");
                            strFeFinPerPruCon=rstLoc.getString("fe_finperprucon");
                            strFeFinCon=rstLoc.getString("fe_fincon");
                            strFeReaFinCon=rstLoc.getString("fe_reafincon");
                            
                        }
                        
                        int intAñoInicial=0;
                        int intMesInicial=0;
                        Calendar calAct = Calendar.getInstance();
                        
                        dblValBonMes = new double[13];
                        dblValMovMes = new double[13];
                        
                        if(strFeIniCon==null){

                            for(int intRecAsg=0; intRecAsg<dblValBonMes.length;intRecAsg++){
                                dblValBonMes[intRecAsg]=dblBonAct;
                                dblValMovMes[intRecAsg]=dblMovAct;
                            }
                        }else{
                            
                            String[] strArrFeIniCon=strFeIniCon.split("-");
                            intAñoInicial=Integer.valueOf(strArrFeIniCon[0]);
                            intMesInicial=Integer.valueOf(strArrFeIniCon[1]);
                            int intDiaInicial=Integer.valueOf(strArrFeIniCon[2]);
                            int intCanDiasInicialesTrabajados=(intArrDiasMes[intMesInicial-1]-intDiaInicial)+1;
                            
                            int intFlgMesesPas=0;
                            
                            
                            if(intAñoInicial>=calAct.get(Calendar.YEAR)){
                            
                                if(intMesInicial>0){
                                    for(int i=0;i<intMesInicial;i++){
                                        dblValBonMes[i]=0;
                                        dblValMovMes[i]=0;
                                        intFlgMesesPas++;
                                    }
                                }
                            
                                for(int intRecAsg=intFlgMesesPas; intRecAsg<dblValBonMes.length;intRecAsg++){
                                    
                                    if(intRecAsg==intMesInicial){
                                        
                                        dblValBonMes[intRecAsg]=  objUti.redondear ( ((dblBonAct/intArrDiasMes[intRecAsg])*intCanDiasInicialesTrabajados) , 2 );
                                        dblValMovMes[intRecAsg]=  objUti.redondear ( ((dblBonAct/intArrDiasMes[intRecAsg])*intCanDiasInicialesTrabajados) , 2 );
                                    }else{
                                        dblValBonMes[intRecAsg]=dblBonAct;
                                        dblValMovMes[intRecAsg]=dblMovAct;
                                    }
                                }
                                
                            }else{
                                
                                for(int intRecAsg=intFlgMesesPas; intRecAsg<dblValBonMes.length;intRecAsg++){
                                    dblValBonMes[intRecAsg]=dblBonAct;
                                    dblValMovMes[intRecAsg]=dblMovAct;
                                }
                                
                            }
                            
                            
                            if(strFeReaFinCon==null){

                            }else{
//                                caso: empleado sale de la empresa, no se valida, liquidacion de haberes = NULL
                                String[] strArrFeRealFinCon=strFeReaFinCon.split("-");
                                int intAñoFinal=Integer.valueOf(strArrFeRealFinCon[0]);
                                int intMesFinal=Integer.valueOf(strArrFeRealFinCon[1]);
                                int intDiaFinal=Integer.valueOf(strArrFeRealFinCon[2]);
                                int intCanDiasFinalTrabajados=intDiaFinal;
                                int intCanAñosTrabajados=intAñoFinal-intAñoInicial;
                                int intCanMesesTrabajados=intMesFinal-intMesInicial;
//                                
                                int intFlgMesFinTra=0;
                                
                                 for(int intRecAsg=intFlgMesesPas; intRecAsg<dblValBonMes.length;intRecAsg++){
                                    
                                    if(intRecAsg==intMesFinal){
                                        dblValBonMes[intMesFinal]=  0;
                                        dblValMovMes[intMesFinal]=  0;
                                    }else if (intRecAsg>intMesInicial){
                                        dblValBonMes[intRecAsg]=0;
                                        dblValMovMes[intRecAsg]=0;
                                    }
                                }
                            }
                        }
                       
                       dblValPrgBonMes = new double[13];
                       dblValPrgMovMes = new double[13];
                       double dblResCalcBon=0;
                       double dblResCalcMov=0;
                       double dblPorVar=15.00;
                       /*OBTENER PORCENTAJE A PAGAR*/
                       
                       boolean blnMesAnt=false;
                       

                       for(int intRecValMesOrg=intRecValMesOrgAux;intRecValMesOrg<dblValBonMes.length;intRecValMesOrg++){
                           dblTotBon=0;
                           dblTotMov=0;
                           
                           
                           if(intRecValMesOrg>0){
                               if(!blnMesAnt){
                                   int intCan=intRecValMesOrg;
                                   dblTotBonPagado+=dblValBonMes[0]*intCan;
                                   dblTotMovPagado+=dblValMovMes[0]*intCan;
                                   blnMesAnt=true;
                               }
                           }
                            /*
                        * UNA VEZ OBTENIDO LA SECUENCIA "Bono de acuerdo al mes"
                        * GENERAR LA PROGRAMACION DE LOS RUBROS
                       */
//                      suma de todo
                        for(int intRecValTotMes=0;intRecValTotMes<=dblValBonMes.length-1;intRecValTotMes++){
                            dblTotBon+=dblValBonMes[intRecValTotMes];
                            dblTotMov+=dblValMovMes[intRecValTotMes];
                        }
                           
                           System.out.println("mes: " + intRecValMesOrg);
                           
                           if(intRecValMesOrg==0){
                               dblValPrgBonMes[intRecValMesOrg]=dblValBonMes[intRecValMesOrg];
                               dblTotBonPagado+=dblValBonMes[intRecValMesOrg];
                               
                               strSql="";
                               strSql+="select * from tbm_ingEgrMenPrgTra";
                               strSql+=" where co_emp="+intCoEmp;
                               strSql+=" and co_tra="+intCoTra;
                               strSql+=" and co_rub=6";
                               strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                               strSql+=" and ne_mes=12";
                               rstLoc=stmLocAux.executeQuery(strSql);
                               if(!rstLoc.next()){
                                    strSql="";
                                    
                                    strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                    strSql+= intCoEmp + " , ";
                                    strSql+= intCoTra + " , ";
                                    strSql+=  " 6 , ";
                                    strSql+=  calAct.get(Calendar.YEAR)-1 + " , ";
                                    strSql+=  " 12 , ";
                                    strSql+= dblValBonMes[intRecValMesOrg] + " , ";
                                    strSql+= dblPorVar + " , ";
                                    strSql+=  dblValBonMes[intRecValMesOrg] + " ) ";
                                    stmLoc.executeUpdate(strSql);
                               }else{
                                   strSql="";
                                   strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValBonMes[intRecValMesOrg];
                                   strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=6";
                                    strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                                    strSql+=" and ne_mes=12";
                                    stmLoc.executeUpdate(strSql);
                               }
                               
                               dblValPrgMovMes[intRecValMesOrg]=dblValMovMes[intRecValMesOrg];
                               dblTotMovPagado+=dblValMovMes[intRecValMesOrg];
                               
                               strSql="";
                               strSql+="select * from tbm_ingEgrMenPrgTra";
                               strSql+=" where co_emp="+intCoEmp;
                               strSql+=" and co_tra="+intCoTra;
                               strSql+=" and co_rub=7";
                               strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                               strSql+=" and ne_mes=12";
                               rstLoc=stmLocAux.executeQuery(strSql);
                               if(!rstLoc.next()){
                                   strSql="";
                                   strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                   strSql+= intCoEmp + " , ";
                                   strSql+= intCoTra + " , ";
                                   strSql+=  " 7 , ";
                                   strSql+=  calAct.get(Calendar.YEAR)-1 + " , ";
                                   strSql+=  " 12 , ";
                                   strSql+= dblValMovMes[intRecValMesOrg] + " , ";
                                   strSql+= dblPorVar + " , ";
                                   strSql+=  dblValMovMes[intRecValMesOrg] + " ) ";
                                   stmLoc.executeUpdate(strSql);
                               }else{
                                   strSql="";
                                   strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValMovMes[intRecValMesOrg];
                                   strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=7";
                                    strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                                    strSql+=" and ne_mes=12";
                                    stmLoc.executeUpdate(strSql);
                               }
                           }
                           else{

                               if(intRecValMesOrg==dblValPrgBonMes.length-1){
//                                   decimo tercer sueldo
                                   dblValPrgBonMes[intRecValMesOrg]= objUti.redondear( (dblTotBon-dblTotBonPagado),2);
                                   
                                   strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=6";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes=13";
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 6 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  " 13 , ";
                                            strSql+= dblValBonMes[intRecValMesOrg] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgBonMes[intRecValMesOrg] + " ) ";
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgBonMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=6";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes=13";
                                            stmLoc.executeUpdate(strSql);
                                    }
                                   /*....AQUI AL PARECER ESTA EL PROBLEMA....*/
                                   dblValPrgMovMes[intRecValMesOrg]= objUti.redondear( ( dblTotMov-dblTotMovPagado),2);
                                   
                                   strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=7";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes=13";
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 7 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  " 13 , ";
                                            strSql+= dblValMovMes[intRecValMesOrg] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgMovMes[intRecValMesOrg] + " ) ";
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgMovMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=7";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes=13";
                                            stmLoc.executeUpdate(strSql);
                                    }
                                    dblTotBonPagado=0;
                                    dblTotMovPagado=0;
                                    blnMesAnt=false;
                               }else{
                                   
                                   System.out.println("la ultima vez que ingresa..." + intRecValMesOrg);
                                   
                                   dblResCalcBon=dblValBonMes[intRecValMesOrg-1];
                                   dblResCalcMov=dblValMovMes[intRecValMesOrg-1];

                                   if(dblValPrgBonMes[intRecValMesOrg-1]>=dblValBonMes[intRecValMesOrg-1]){
                                        double dblBonGen=  (Math.random()*dblValPrgBonMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcBon=dblResCalcBon+dblBonGen;/*CASO NEG*/
                                        if(dblResCalcBon<0){
                                            dblResCalcBon=0;
                                        }
                                        dblValPrgBonMes[intRecValMesOrg]=objUti.redondear( dblResCalcBon , 2);
                                        dblTotBonPagado+=objUti.redondear( dblResCalcBon , 2);
                                        
                                    }else{
                                        double dblBonGen= -1 * (Math.random()*dblValPrgBonMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcBon=dblResCalcBon+dblBonGen;
                                        if(dblResCalcBon<0){
                                            dblResCalcBon=0;
                                        }
                                        dblValPrgBonMes[intRecValMesOrg]=objUti.redondear( dblResCalcBon , 2);
                                        dblTotBonPagado+=objUti.redondear( dblResCalcBon , 2);
                                    }
                                   
                                   strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=6";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes="+intRecValMesOrg;
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 6 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  intRecValMesOrg + " , ";
                                            strSql+= dblValBonMes[intRecValMesOrg] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgBonMes[intRecValMesOrg] + " ) ";
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgBonMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=6";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes="+intRecValMesOrg;
                                            stmLoc.executeUpdate(strSql);
                                    }
                                   

                                    if(dblValPrgMovMes[intRecValMesOrg-1]>=dblValMovMes[intRecValMesOrg-1]){
                                        double dblMovGen= -1 * (Math.random()*dblValPrgMovMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcMov=dblResCalcMov+dblMovGen;
                                        if(dblResCalcMov<0){
                                            dblResCalcMov=0;
                                        }
                                        dblValPrgMovMes[intRecValMesOrg]=objUti.redondear( dblResCalcMov , 2);
                                        dblTotMovPagado+=objUti.redondear( dblResCalcMov , 2);
                                    }else{
                                        double dblMovGen= (Math.random()*dblValPrgMovMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcMov=dblResCalcMov+dblMovGen;
                                        if(dblResCalcMov<0){
                                            dblResCalcMov=0;
                                        }
                                        dblValPrgMovMes[intRecValMesOrg]=objUti.redondear( dblResCalcMov , 2);
                                        dblTotMovPagado+=objUti.redondear( dblResCalcMov , 2);
                                    }
                                    
                                    strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=7";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes="+intRecValMesOrg;
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 7 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  intRecValMesOrg + " , ";
                                            strSql+= dblValMovMes[intRecValMesOrg] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgMovMes[intRecValMesOrg] + " ) ";
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgMovMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=7";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes="+intRecValMesOrg;
                                            stmLoc.executeUpdate(strSql);
                                    }
                                    
                               }
                           }
                       }
                }
                blnRes=true;
            }
            con.commit();
        }
        finally {
            try{stmLoc.close(); stmLoc=null;}catch(Throwable ignore){}
            try{stmLocAux.close(); stmLocAux=null;}catch(Throwable ignore){}
            try{rstLoc.close(); rstLoc=null;}catch(Throwable ignore){}
            try{rstLocAux.close(); rstLocAux=null;}catch(Throwable ignore){}
        }

        return blnRes;
    }
    
    public boolean ZafVenAnlTra(Connection con, String strCed) throws Exception{
        boolean blnRes=false;
        java.sql.Statement stmLoc=null;
        try{
            String strSql="";
            if(con!=null){
                con.setAutoCommit(false);
                stmLoc = con.createStatement();
                strSql="UPDATE tbm_usr SET st_reg = 'I' WHERE tx_ced = " + objUti.codificar(strCed);
                stmLoc.executeUpdate(strSql);
                blnRes = true;
            }
        }
        finally {
            try{stmLoc.close(); stmLoc=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }

    public boolean ZafVenAmtPrgSueAnl(Connection con, int intRecValMesOrgAux, int intCoTra, int intCoEmp) throws Exception{

        String[] arrVar={"=","+","-","+","-","+","-","+","-","+","-","+"};
//        java.sql.Connection con = null;
        java.sql.Statement stmLoc=null;
        java.sql.Statement stmLocAux=null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstLocAux = null;
        String strFeIniCon=null;
        String strFeFinPerPruCon=null;
        String strFeFinCon=null;
        String strFeReaFinCon=null;
        boolean blnRes=false;
        String strSql="";
        
        boolean blnUltMesGen=false;

        try{
            
            if(con!=null){
                con.setAutoCommit(false);
                stmLoc = con.createStatement();
                stmLocAux = con.createStatement();
                
                int intArrDiasMes[] = new int[12];
                double dblValBonMes[] = new double[13];
                double dblValMovMes[] = new double[13];
                
                double dblValPrgBonMes[] = new double[13];
                double dblValPrgMovMes[] = new double[13];
                
                double dblTotBon=0;
                double dblTotMov=0;
                
                double dblTotBonPagado=0;
                double dblTotMovPagado=0;
                
                java.util.Calendar CalVal = null;
                int intCon = 0;
                CalVal =  java.util.Calendar.getInstance();
                CalVal.set(java.util.Calendar.YEAR,CalVal.get(Calendar.MONTH));
                CalVal.set(java.util.Calendar.MONTH,12);
                intArrDiasMes[intCon++]=CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

                for(int intMes=1;intMes<=11;intMes++){
                    CalVal =  java.util.Calendar.getInstance();
                    CalVal.set(java.util.Calendar.MONTH,intMes-1);
                    if(intCon==intRecValMesOrgAux){
                        Calendar cal = Calendar.getInstance();
                        intArrDiasMes[intCon++]=cal.get(Calendar.DATE);
                    }else{
                        intArrDiasMes[intCon++]=CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                    }
                }

                for(int intFil=0; intFil<1;intFil++){
                        
                        double dblBon=0;
                        double dblMov=0;
                        
                        strSql="";
                        strSql+="select a.co_emp, a.co_tra,a.fe_inicon, a.fe_finperprucon,a.fe_fincon,a.st_perprucon,";
                        strSql+=" a.st_fincon,a.fe_reafincon from tbm_traemp a";
                        strSql+=" where a.co_tra="+intCoTra;
                        strSql+=" and a.co_emp="+intCoEmp;

                        System.out.println("consulta reg sel: " + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);

                        vecDat = new Vector();
                        java.util.Vector vecReg = null;

                        if(rstLoc.next()){
                            strFeIniCon=rstLoc.getString("fe_inicon");
                            strFeFinPerPruCon=rstLoc.getString("fe_finperprucon");
                            strFeFinCon=rstLoc.getString("fe_fincon");
                            strFeReaFinCon=rstLoc.getString("fe_reafincon");
                        }
                        
                        
                        strSql="";
                        strSql+="select * from tbm_suetra ";
                        strSql+="where co_rub in (6,7) ";
                        strSql+="and co_tra = " + intCoTra;
                        strSql+="and co_emp = " + intCoEmp;
                        
                        System.out.println("consulta reg sel: " + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);
                        
                        while(rstLoc.next()){
                            
                            if(rstLoc.getString("co_rub").compareTo("6")==0){
                                dblBon=rstLoc.getDouble("nd_valrub");
                            }else{
                                dblMov=rstLoc.getDouble("nd_valrub");
                            }
                        }
                        
                        int intAñoInicial=0;
                        int intMesInicial=0;
                        Calendar calAct = Calendar.getInstance();
                        
                        dblValBonMes = new double[13];
                        dblValMovMes = new double[13];
                        
                        if(strFeIniCon==null){

                            for(int intRecAsg=0; intRecAsg<dblValBonMes.length;intRecAsg++){
                                dblValBonMes[intRecAsg]=dblBon;
                                dblValMovMes[intRecAsg]=dblMov;
                            }
                        }else{
                            
                            String[] strArrFeIniCon=strFeIniCon.split("-");
                            intAñoInicial=Integer.valueOf(strArrFeIniCon[0]);
                            intMesInicial=Integer.valueOf(strArrFeIniCon[1]);
                            int intDiaInicial=Integer.valueOf(strArrFeIniCon[2]);
                            int intCanDiasInicialesTrabajados=(intArrDiasMes[intMesInicial-1]-intDiaInicial)+1;
                            
                            int intFlgMesesPas=0;
                            
                            if(intAñoInicial>=calAct.get(Calendar.YEAR)){
                            
                                if(intMesInicial>0){
                                    for(int i=0;i<intMesInicial;i++){
                                        dblValBonMes[i]=0;
                                        dblValMovMes[i]=0;
                                        intFlgMesesPas++;
                                    }
                                }
                            
                                for(int intRecAsg=intFlgMesesPas; intRecAsg<dblValBonMes.length;intRecAsg++){
                                    
                                    if(intRecAsg==intMesInicial){
                                        
                                        dblValBonMes[intRecAsg]=  objUti.redondear ( ((dblBon/intArrDiasMes[intRecAsg])*intCanDiasInicialesTrabajados) , 2 );
                                        dblValMovMes[intRecAsg]=  objUti.redondear ( ((dblBon/intArrDiasMes[intRecAsg])*intCanDiasInicialesTrabajados) , 2 );
                                    }else{
                                        dblValBonMes[intRecAsg]=dblBon;
                                        dblValMovMes[intRecAsg]=dblMov;
                                    }
                                }
                                
                            }else{
                                for(int intRecAsg=intFlgMesesPas; intRecAsg<dblValBonMes.length;intRecAsg++){
                                    dblValBonMes[intRecAsg]=dblBon;
                                    dblValMovMes[intRecAsg]=dblMov;
                                }
                            }
                            
                            if(strFeReaFinCon==null){


                            }else{
//                                caso: empleado sale de la empresa, no se valida, liquidacion de haberes = NULL
                                String[] strArrFeRealFinCon=strFeReaFinCon.split("-");
                                int intAñoFinal=Integer.valueOf(strArrFeRealFinCon[0]);
                                int intMesFinal=Integer.valueOf(strArrFeRealFinCon[1]);
                                int intDiaFinal=Integer.valueOf(strArrFeRealFinCon[2]);
                                int intCanDiasFinalTrabajados=intDiaFinal;
                                int intCanAñosTrabajados=intAñoFinal-intAñoInicial;
                                int intCanMesesTrabajados=intMesFinal-intMesInicial;
//                                
                                int intFlgMesFinTra=0;
                                
                                 for(int intRecAsg=intFlgMesesPas; intRecAsg<dblValBonMes.length;intRecAsg++){
                                    
                                    if(intRecAsg==intMesFinal){
                                        dblValBonMes[intMesFinal]=  0;
                                        dblValMovMes[intMesFinal]=  0;
                                    }else if (intRecAsg>intMesInicial){
                                        dblValBonMes[intRecAsg]=0;
                                        dblValMovMes[intRecAsg]=0;
                                    }
                                }
                            }
                        }                        
                        
                       dblValPrgBonMes = new double[13];
                       dblValPrgMovMes = new double[13];
                       double dblResCalcBon=0;
                       double dblResCalcMov=0;
                       double dblPorVar=15.00;
                       boolean blnMesAnt=false;
                       

                       for(int intRecValMesOrg=intRecValMesOrgAux;intRecValMesOrg<dblValBonMes.length;intRecValMesOrg++){
                           dblTotBon=0;
                           dblTotMov=0;
                           
                           
                           if(intRecValMesOrg>0){
                               if(!blnMesAnt){
                                   int intCan=intRecValMesOrg;
                                   dblTotBonPagado+=dblValBonMes[0]*intCan;
                                   dblTotMovPagado+=dblValMovMes[0]*intCan;
                                   blnMesAnt=true;
                               }
                           }
                            /*
                        * UNA VEZ OBTENIDO LA SECUENCIA "Bono de acuerdo al mes"
                        * GENERAR LA PROGRAMACION DE LOS RUBROS
                       */
//                      suma de todo
                        for(int intRecValTotMes=0;intRecValTotMes<=intRecValMesOrgAux;intRecValTotMes++){
                            dblTotBon+=dblValBonMes[intRecValTotMes];
                            dblTotMov+=dblValMovMes[intRecValTotMes];
                        }

                           System.out.println("mes: " + intRecValMesOrg);
                           
                           if(intRecValMesOrg==0){
                               dblValPrgBonMes[intRecValMesOrg]=dblValBonMes[intRecValMesOrg];
                               dblTotBonPagado+=dblValBonMes[intRecValMesOrg];
                               
                               strSql="";
                               strSql+="select * from tbm_ingEgrMenPrgTra";
                               strSql+=" where co_emp="+intCoEmp;
                               strSql+=" and co_tra="+intCoTra;
                               strSql+=" and co_rub=6";
                               strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                               strSql+=" and ne_mes=12";
                               rstLoc=stmLocAux.executeQuery(strSql);
                               if(!rstLoc.next()){
                                    strSql="";
                                    
                                    strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                    strSql+= intCoEmp + " , ";
                                    strSql+= intCoTra + " , ";
                                    strSql+=  " 6 , ";
                                    strSql+=  calAct.get(Calendar.YEAR)-1 + " , ";
                                    strSql+=  " 12 , ";
                                    strSql+= dblValBonMes[intRecValMesOrg] + " , ";
                                    strSql+= dblPorVar + " , ";
                                    strSql+=  dblValBonMes[intRecValMesOrg] + " ) ";
                                    stmLoc.executeUpdate(strSql);
                               }else{
                                   strSql="";
                                   strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValBonMes[intRecValMesOrg];
                                   strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=6";
                                    strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                                    strSql+=" and ne_mes=12";
                                    stmLoc.executeUpdate(strSql);
                               }
                               
                               dblValPrgMovMes[intRecValMesOrg]=dblValMovMes[intRecValMesOrg];
                               dblTotMovPagado+=dblValMovMes[intRecValMesOrg];
                               
                               strSql="";
                               strSql+="select * from tbm_ingEgrMenPrgTra";
                               strSql+=" where co_emp="+intCoEmp;
                               strSql+=" and co_tra="+intCoTra;
                               strSql+=" and co_rub=7";
                               strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                               strSql+=" and ne_mes=12";
                               rstLoc=stmLocAux.executeQuery(strSql);
                               if(!rstLoc.next()){
                                   strSql="";
                                   strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                   strSql+= intCoEmp + " , ";
                                   strSql+= intCoTra + " , ";
                                   strSql+=  " 7 , ";
                                   strSql+=  calAct.get(Calendar.YEAR)-1 + " , ";
                                   strSql+=  " 12 , ";
                                   strSql+= dblValMovMes[intRecValMesOrg] + " , ";
                                   strSql+= dblPorVar + " , ";
                                   strSql+=  dblValMovMes[intRecValMesOrg] + " ) ";
                                   stmLoc.executeUpdate(strSql);
                               }else{
                                   strSql="";
                                   strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[intRecValMesOrg]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValMovMes[intRecValMesOrg];
                                   strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=7";
                                    strSql+=" and ne_ani="+(calAct.get(Calendar.YEAR)-1);
                                    strSql+=" and ne_mes=12";
                                    stmLoc.executeUpdate(strSql);
                               }
                           }
                           else{

                               if(intRecValMesOrg==dblValPrgBonMes.length-1){
                                   dblValPrgBonMes[intRecValMesOrg]= objUti.redondear( (dblTotBonPagado/12),2);
                                   
                                   strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=6";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes=13";
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 6 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  " 13 , ";
                                            strSql+= dblValBonMes[0] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgBonMes[intRecValMesOrg] + " ) ";
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[0]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgBonMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=6";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes=13";
                                            stmLoc.executeUpdate(strSql);
                                    }
                                    
                                   dblValPrgMovMes[intRecValMesOrg]= objUti.redondear( ( dblTotMovPagado/12),2);
                                   
                                   strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=7";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes=13";
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 7 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  " 13 , ";
                                            strSql+= dblValMovMes[0] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgMovMes[intRecValMesOrg] + " ) ";
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[0]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgMovMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=7";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes=13";
                                            stmLoc.executeUpdate(strSql);
                                    }
                                    dblTotBonPagado=0;
                                    dblTotMovPagado=0;
                                    blnMesAnt=false;
                               }else{
                                   
                                   if(!blnUltMesGen){
                                       
                                       int intCanDiasMax = CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                                   
                                   System.out.println("la ultima vez que ingresa..." + intRecValMesOrg);
                                   
                                   dblResCalcBon=dblValBonMes[intRecValMesOrg-1];
                                   dblResCalcMov=dblValMovMes[intRecValMesOrg-1];

                                   if(dblValPrgBonMes[intRecValMesOrg-1]>=dblValBonMes[intRecValMesOrg-1]){
                                        double dblBonGen=  (Math.random()*dblValPrgBonMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcBon=dblResCalcBon+dblBonGen;/*CASO NEG*/
                                        if(dblResCalcBon<0){
                                            dblResCalcBon=0;
                                        }
                                        
                                        dblValPrgBonMes[intRecValMesOrg]=objUti.redondear( ((dblResCalcBon/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                        dblTotBonPagado+=objUti.redondear( ((dblResCalcBon/intCanDiasMax)*intArrDiasMes[intRecValMesOrg-1]) , 2);
                                        
                                    }else{
                                        double dblBonGen= -1 * (Math.random()*dblValPrgBonMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcBon=dblResCalcBon+dblBonGen;
                                        if(dblResCalcBon<0){
                                            dblResCalcBon=0;
                                        }
                                        dblValPrgBonMes[intRecValMesOrg]=objUti.redondear( ((dblResCalcBon/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                        dblTotBonPagado+=objUti.redondear( ((dblResCalcBon/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                    }
                                   
                                   strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=6";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes="+intRecValMesOrg;
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 6 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  intRecValMesOrg + " , ";
                                            strSql+= dblValBonMes[0] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgBonMes[intRecValMesOrg] + " ) ";
                                            System.out.println("mes anl empleado: " + strSql);
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[0]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgBonMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=6";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes="+intRecValMesOrg;
                                              System.out.println("mes anl empleado: " + strSql);
                                            stmLoc.executeUpdate(strSql);
                                    }
                                   

                                    if(dblValPrgMovMes[intRecValMesOrg-1]>=dblValMovMes[intRecValMesOrg-1]){
                                        double dblMovGen= -1 * (Math.random()*dblValPrgMovMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcMov=dblResCalcMov+dblMovGen;
                                        if(dblResCalcMov<0){
                                            dblResCalcMov=0;
                                        }
                                        dblValPrgMovMes[intRecValMesOrg]=objUti.redondear( ((dblResCalcMov/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                        dblTotMovPagado+=objUti.redondear( ((dblResCalcMov/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                    }else{
                                        double dblMovGen= (Math.random()*dblValPrgMovMes[intRecValMesOrg-1])*dblPorVar/100;
                                        dblResCalcMov=dblResCalcMov+dblMovGen;
                                        if(dblResCalcMov<0){
                                            dblResCalcMov=0;
                                        }
                                        dblValPrgMovMes[intRecValMesOrg]=objUti.redondear( ((dblResCalcMov/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                        dblTotMovPagado+=objUti.redondear( ((dblResCalcMov/intCanDiasMax)*intArrDiasMes[intRecValMesOrg]) , 2);
                                    }
                                    
                                    strSql="";
                                    strSql+="select * from tbm_ingEgrMenPrgTra";
                                    strSql+=" where co_emp="+intCoEmp;
                                    strSql+=" and co_tra="+intCoTra;
                                    strSql+=" and co_rub=7";
                                    strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                    strSql+=" and ne_mes="+intRecValMesOrg;
                                    rstLoc=stmLocAux.executeQuery(strSql);
                                    if(!rstLoc.next()){
                                            strSql="";
                                            strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                            strSql+= intCoEmp + " , ";
                                            strSql+= intCoTra + " , ";
                                            strSql+=  " 7 , ";
                                            strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                            strSql+=  intRecValMesOrg + " , ";
                                            strSql+= dblValMovMes[0] + " , ";
                                            strSql+= dblPorVar + " , ";
                                            strSql+=  dblValPrgMovMes[intRecValMesOrg] + " ) ";
                                            System.out.println("mes anl empleado: " + strSql);
                                            stmLoc.executeUpdate(strSql);
                                    }else{
                                        strSql="";
                                        strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[0]+ " , nd_porvar = "+dblPorVar+", nd_valrubprg = "+dblValPrgMovMes[intRecValMesOrg];
                                        strSql+=" where co_emp="+intCoEmp;
                                            strSql+=" and co_tra="+intCoTra;
                                            strSql+=" and co_rub=7";
                                            strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                            strSql+=" and ne_mes="+intRecValMesOrg;
                                            System.out.println("mes anl empleado: " + strSql);
                                            stmLoc.executeUpdate(strSql);
                                    }
                                    blnUltMesGen=true;
                               }else{
                                       
                                       strSql="";
                                        strSql+="select * from tbm_ingEgrMenPrgTra";
                                        strSql+=" where co_emp="+intCoEmp;
                                        strSql+=" and co_tra="+intCoTra;
                                        strSql+=" and co_rub=6";
                                        strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                        strSql+=" and ne_mes="+intRecValMesOrg;
                                        rstLoc=stmLocAux.executeQuery(strSql);
                                        if(!rstLoc.next()){
                                                strSql="";
                                                strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                                strSql+= intCoEmp + " , ";
                                                strSql+= intCoTra + " , ";
                                                strSql+=  " 6 , ";
                                                strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                                strSql+=  intRecValMesOrg + " , ";
                                                strSql+=  dblValBonMes[0] + " , ";
                                                strSql+= " 0 , ";
                                                strSql+=  " 0 ) ";
                                                System.out.println("mes anl empleado: " + strSql);
                                                stmLoc.executeUpdate(strSql);
                                        }else{
                                            strSql="";
                                            strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValBonMes[0]+" , nd_porvar = 0 , nd_valrubprg = 0";
                                            strSql+=" where co_emp="+intCoEmp;
                                                strSql+=" and co_tra="+intCoTra;
                                                strSql+=" and co_rub=6";
                                                strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                                strSql+=" and ne_mes="+intRecValMesOrg;
                                                System.out.println("mes anl empleado: " + strSql);
                                                stmLoc.executeUpdate(strSql);
                                        }
                                       
                                       strSql="";
                                        strSql+="select * from tbm_ingEgrMenPrgTra";
                                        strSql+=" where co_emp="+intCoEmp;
                                        strSql+=" and co_tra="+intCoTra;
                                        strSql+=" and co_rub=7";
                                        strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                        strSql+=" and ne_mes="+intRecValMesOrg;
                                        rstLoc=stmLocAux.executeQuery(strSql);
                                        if(!rstLoc.next()){
                                                strSql="";
                                                strSql+="insert into tbm_ingegrmenprgtra values ( ";
                                                strSql+= intCoEmp + " , ";
                                                strSql+= intCoTra + " , ";
                                                strSql+=  " 7 , ";
                                                strSql+=  calAct.get(Calendar.YEAR) + " , ";
                                                strSql+=  intRecValMesOrg + " , ";
                                                strSql+= dblValMovMes[0] + " , ";
                                                strSql+= " 0 , ";
                                                strSql+=  " 0 ) ";
                                                System.out.println("mes anl empleado: " + strSql);
                                                stmLoc.executeUpdate(strSql);
                                        }else{
                                            strSql="";
                                            strSql+="update tbm_ingegrmenprgtra set nd_valrub = "+dblValMovMes[0]+" , nd_porvar = 0, nd_valrubprg = 0";
                                            strSql+=" where co_emp="+intCoEmp;
                                                strSql+=" and co_tra="+intCoTra;
                                                strSql+=" and co_rub=7";
                                                strSql+=" and ne_ani="+calAct.get(Calendar.YEAR);
                                                strSql+=" and ne_mes="+intRecValMesOrg;
                                                System.out.println("mes anl empleado: " + strSql);
                                                stmLoc.executeUpdate(strSql);
                                        }
                                   }
                               }
                           }
                       }
                }
                blnRes=true;
            }
            con.commit();
        }
        finally {
            try{stmLoc.close(); stmLoc=null;}catch(Throwable ignore){}
            try{stmLocAux.close(); stmLocAux=null;}catch(Throwable ignore){}
            try{rstLoc.close(); rstLoc=null;}catch(Throwable ignore){}
            try{rstLocAux.close(); rstLocAux=null;}catch(Throwable ignore){}
        }

        return blnRes;
        
    }
    
    public ArrayList<Provisiones> cuentasProvisiones(int intCoEmp, int intCoGrp){
        
        ArrayList<Provisiones> arrProv=null;
        
        try{
            
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = null;
            
            boolean blnIsCosenco=false;
            blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO")> -1) ? true: false;
            
            if(intCoGrp==1){
                
                if(intCoEmp==1){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_iess_tuval.xml"));
                }else if(intCoEmp==2){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_iess_castek.xml"));
                }else if (intCoEmp==4){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_iess_dimulti.xml"));
                }
            }else{
                //Validacion para cosenco tony
                if (!blnIsCosenco) {
                if(intCoEmp==1){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_bm_tuval.xml"));
                }
                else if(intCoEmp==2){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_bm_castek.xml"));
                }
                else if (intCoEmp==4){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_bm_dimulti.xml"));
                } 
                }else{
                       if(intCoEmp==1){
                    doc = docBuilder.parse (new File("C:\\Zafiro\\Temp\\RecursosHumanos\\Provisiones\\provisiones_bm_cosenco.xml"));
                }
             
                }
                
            }
            
            doc.getDocumentElement ().normalize ();
            System.out.println ("El elemento raíz es " + doc.getDocumentElement().getNodeName());

            NodeList listaCuentas = doc.getElementsByTagName("provision");
            int totalCuentasProvisiones = listaCuentas.getLength();

            System.out.println("Número total de provisiones tuval : " + totalCuentasProvisiones);
            arrProv = new ArrayList<Provisiones>();
            for (int i = 0; i < totalCuentasProvisiones ; i++) {
                
                Node provision = listaCuentas.item(i);
                if (provision.getNodeType() == Node.ELEMENT_NODE){
                    
                    Provisiones tmpProv = new Provisiones();
                    Element elemento = (Element) provision;
                
                    System.out.println("Nombre : "  + getTagValue("nombre",elemento ));tmpProv.setNombre(getTagValue("nombre",elemento ));
                    System.out.println("Cuenta : "  + getTagValue("co_cta",elemento ));tmpProv.setIntCoCta(Integer.parseInt(getTagValue("co_cta",elemento )));
                    System.out.println("Numero cuenta : "  + getTagValue("num_cta",elemento ));tmpProv.setStrNumCta(getTagValue("num_cta",elemento ));
                    System.out.println("Tipo Cuenta : "  + getTagValue("tipo_cuenta",elemento ));tmpProv.setIntTipCta(Integer.parseInt(getTagValue("tipo_cuenta",elemento )));
                    System.out.println("Plantilla : "  + getTagValue("plantilla",elemento ));tmpProv.setIntCoPla(Integer.parseInt(getTagValue("plantilla",elemento )));
                    System.out.println("Valor Base : "  + getTagValue("ndvalbase",elemento ));tmpProv.setNdValBase(Double.parseDouble(getTagValue("ndvalbase",elemento )));
                    arrProv.add(tmpProv);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return arrProv;
    }
    
    public static String getTagValue(String tag, Element elemento) {
        NodeList lista = elemento.getElementsByTagName(tag).item(0).getChildNodes();
        Node valor = (Node) lista.item(0);
        return valor.getNodeValue();
    }
    
    public ArrayList<String> obtenerDiasAtrasoEmpleado(Connection con, ZafRecHum09 objRecHum09, int intCoTra, String strFeDesde, String strFeHasta){
        
        String strSql="";
        java.sql.Statement stmLoc=null;
        java.sql.ResultSet rstLoc = null;
        ArrayList<String> arrLisAtrTra=null;

        try{
            if(con!=null){
                
                stmLoc = con.createStatement();
                strSql="";
                strSql+="select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, g.ho_ent as horaEntrada, g.ho_sal as horaSalida, g.ho_mingraent as minutosGrac, c.ho_ent, " + "\n";
                strSql+="c.ho_sal, st_jusatr, fe_jusatr,  st_jussalade, fe_jussalade, f.co_jef," + "\n";
                strSql+="(case (g.ho_ent > c.ho_ent) when true then (g.ho_ent-c.ho_ent) else null end) as minutosAdelantadoEntrada, " + "\n";
                strSql+="(case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) as minutosAtrasadoEntrada, " + "\n";
                strSql+="(case (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else null end) as minutosAntesSalida, " + "\n";
                strSql+="(case (g.ho_sal< c.ho_sal) when true then (c.ho_sal-g.ho_sal) else null end) as minutosDespuesSalida, " + "\n";
                strSql+="case (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))>((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then (c.ho_sal-g.ho_sal)-(c.ho_ent - g.ho_ent) else null end as totalFavor, " + "\n";
                strSql+="case st_jusatr is null and (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))<((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then (c.ho_ent - g.ho_ent)-(c.ho_sal-g.ho_sal) else null end as totalContra , f.co_emp ," + "\n";
                strSql+="case (st_jusatr is null and (g.ho_ent < c.ho_ent)) when true then (c.ho_ent-g.ho_ent) else '00:00:00' end" + "\n";
                strSql+="+" + "\n";
                strSql+="(case st_jussalade is null and (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else '00:00:00' end)" + "\n";
                strSql+="as p1" + "\n";
                strSql+="FROM ( " + "\n";
                strSql+="select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg, " + "\n";
                strSql+="a.fe_jusatr, a.st_jussalade, a.fe_jussalade from tbm_cabconasitra a  " + "\n";
                strSql+="inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c  " + "\n";
                strSql+="inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A')  " + "\n";
                strSql+="left outer join tbm_dep d on d.co_dep=f.co_dep   " + "\n";
                strSql+="inner join tbm_callab g on(f.co_hor=g.co_hor and g.fe_dia=c.fe_dia) " + "\n";
                strSql+="WHERE c.fe_dia BETWEEN "+objUti.codificar(strFeDesde) + " AND " + objUti.codificar(strFeHasta)  + "\n";
                strSql+="AND not(c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null) " + "\n";
                strSql+="AND c.co_tra not in (4,6,8,19,24,27,20,31,32,33,37,39,41,42,45,46,80,81) " + "\n";
                strSql+="and (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) is not null" + "\n";
                strSql+="and (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) > g.ho_mingraent" + "\n";
                strSql+="and c.co_tra in ("+intCoTra+")" + "\n";
                strSql+="order by c.fe_dia, c.co_tra" + "\n";
                
                System.out.println("strSql : " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                
                if(rstLoc.next()){
                    arrLisAtrTra=new ArrayList<String>();
                    do{
                        arrLisAtrTra.add(rstLoc.getString("fe_dia"));
                    }while(rstLoc.next());
                }
            }    
        }catch(Exception e){
                e.printStackTrace();
                arrLisAtrTra=null;
                objUti.mostrarMsgErr_F1(objRecHum09, e);
        }finally {
            try{stmLoc.close(); stmLoc=null;}catch(Throwable ignore){}
            try{rstLoc.close(); rstLoc=null;}catch(Throwable ignore){}
        }
        return arrLisAtrTra;
    }
    
    public ArrayList<String> obtenerDiasMIEmpleado(Connection con, ZafRecHum09 objRecHum09, int intCoTra, String strFeDesde, String strFeHasta){
        
        String strSql="";
        java.sql.Statement stmLoc=null;
        java.sql.ResultSet rstLoc = null;
        ArrayList<String> arrLisAtrTra=null;

        try{
            if(con!=null){
                
                stmLoc = con.createStatement();
                strSql="";
                strSql+="select * from ("+" \n ";
                strSql+="select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "+" \n ";
                strSql+="(case c.st_jusfal when 'S' then true else false end) as chkJusFal, "+" \n ";
                strSql+="c.tx_obsjusfal, g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,"+" \n ";
                strSql+="(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade, f.co_emp from ( "+" \n ";
                strSql+="select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,a.tx_obsjusfal,b.st_reg "+" \n ";
                strSql+="from tbm_cabconasitra a "+" \n ";
                strSql+="inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c "+" \n ";
                strSql+="inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_reg='A') "+" \n ";
                strSql+="left outer join tbm_dep d on d.co_dep=f.co_dep  "+" \n ";
                strSql+="inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))   "+" \n ";
                strSql+="WHERE c.fe_dia BETWEEN "+objUti.codificar(strFeDesde) +" AND "+objUti.codificar(strFeHasta) +" \n ";
                strSql+="AND (c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)"+" \n ";
                strSql+="UNION "+" \n ";
                strSql+="select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, c.ho_ent, c.ho_sal, (case c.st_jusatr when 'S' then true else false end) as chkJusAtr, c.tx_obsjusatr, "+" \n ";
                strSql+="(case c.st_jusfal when 'S' then true else false end) as chkJusFal,"+" \n ";
                strSql+="c.tx_obsjusfal, g.ho_ent as ho_entHorTra, g.ho_sal as ho_salHorTra, g.ho_mingraent as ho_mingraentHorTra,"+" \n ";
                strSql+="(case c.ho_sal < g.ho_sal when true then (g.ho_sal-c.ho_sal) else null end) as hor_ade,f.co_emp "+" \n ";
                strSql+="from ("+" \n ";
                strSql+="select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,a.tx_obsjusfal,b.st_reg "+" \n ";
                strSql+="from tbm_cabconasitra a "+" \n ";
                strSql+="inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c "+" \n ";
                strSql+="inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N'  and f.st_reg='A') "+" \n ";
                strSql+="left outer join tbm_dep d on d.co_dep=f.co_dep  "+" \n ";
                strSql+="left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))   "+" \n ";
                strSql+="WHERE c.fe_dia BETWEEN "+objUti.codificar(strFeDesde) +" AND "+objUti.codificar(strFeHasta) +" \n ";
                strSql+="AND (c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)"+" \n ";
                strSql+="order by fe_dia asc,tx_ape, tx_nom ) p"+" \n ";
                strSql+="WHERE p.co_tra = "+intCoTra+" \n ";
                
                System.out.println("strSql : " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                
                if(rstLoc.next()){
                    arrLisAtrTra=new ArrayList<String>();
                    do{
                        arrLisAtrTra.add(rstLoc.getString("fe_dia"));
                    }while(rstLoc.next());
                }
            }    
        }catch(Exception e){
                e.printStackTrace();
                arrLisAtrTra=null;
                objUti.mostrarMsgErr_F1(objRecHum09, e);
        }finally {
            try{stmLoc.close(); stmLoc=null;}catch(Throwable ignore){}
            try{rstLoc.close(); rstLoc=null;}catch(Throwable ignore){}
        }
        return arrLisAtrTra;
    }
    
    public String directorioArchivo(FileNameExtensionFilter objFilNamExt)
    {
        String strArc=null;
        try
        {
            JFileChooser objFilCho=new JFileChooser();
            objFilCho.setDialogTitle("Guardar");
            objFilCho.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(!System.getProperty("os.name").equals("Linux")){
                objFilCho.setCurrentDirectory(new File("C:\\"));
            }else{
                objFilCho.setCurrentDirectory(new File("/tmp"));
            }
            
            objFilCho.setFileFilter(objFilNamExt);
            if (objFilCho.showSaveDialog((JInternalFrame)obj)==JFileChooser.APPROVE_OPTION)
            {
                strArc=objFilCho.getSelectedFile().getPath();
                //Si no tiene la extensión "txt" agregarsela.
                if (!strArc.toLowerCase().endsWith("."+objFilNamExt.getExtensions()[0]))
                    strArc+="."+objFilNamExt.getExtensions()[0];
            }
            else
            {
                System.out.println("El usuario canceló");
            }
        }
        catch(Exception e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        return strArc;
    } 
}