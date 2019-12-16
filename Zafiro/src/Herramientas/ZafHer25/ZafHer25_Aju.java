/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas.ZafHer25;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Sistemas 5
 */
public class ZafHer25_Aju {
    List listResul=null;

    public List getListResul() {
        return listResul;
    }
    
    public List generarDatos(Connection cnx, int intEmp/*, List lisRem*/){
        String strQry="";
        List listCont=null;
        List listDatos=null;
        try{
            Statement stm=cnx.createStatement();
            strQry= " SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n"+
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a1.nd_cosUni, a2.nd_stkAct*a1.nd_cosUni as tot \n" +
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_candis as stock, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a2.nd_candis as disp ,a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " FROM tbm_inv AS a1 \n" +
                    " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                    " LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n" +
                    //" WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_stkAct>0 \n" +
                    " WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_candis>0 \n" +
                    //" AND (a2.nd_stkAct*a1.nd_cosUni)>0"+
                    " AND (a2.nd_candis*a1.nd_cosUni)>0"+
                    //" ORDER BY (a2.nd_stkAct*a1.nd_cosUni) DESC";
                    " ORDER BY (a2.nd_candis*a1.nd_cosUni) DESC";
                    //" LIMIT 10";
            ResultSet rstAju=stm.executeQuery(strQry);
            listCont=new ArrayList();
            while(rstAju.next()){
                listDatos =new ArrayList();
                listDatos.add(0, rstAju.getInt("co_itm"));
                listDatos.add(1, rstAju.getString("tx_codAlt"));
                listDatos.add(2, rstAju.getString("tx_nomItm"));
                listDatos.add(3, rstAju.getString("tx_uniMed"));
                listDatos.add(4, rstAju.getInt("co_bod"));
                listDatos.add(5, rstAju.getBigDecimal("stock"));
                //listDatos.add(6, rstAju.getBigDecimal("disp"));
                listDatos.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatos.add(7, rstAju.getBigDecimal("tot").setScale(2, RoundingMode.HALF_UP));
                listDatos.add(8, 0);
                listDatos.add(9, rstAju.getBigDecimal("disp"));
                listDatos.add(10,"");
                listCont.add(listDatos);
            }
            //System.out.println("Aqui antes de salir");
            rstAju.close();
            stm.close();
            //listCont.removeAll(lisRem);
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
        return listCont;
    }    
    
    
    public List generarDatos(Connection cnx, int intEmp, int  intCodBod){
        String strQry="";
        List listCont=null;
        List listDatos=null;
        try{
            Statement stm=cnx.createStatement();
            strQry= " SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n"+
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a1.nd_cosUni, a2.nd_stkAct*a1.nd_cosUni as tot \n" +
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_candis as stock, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a2.nd_candis as disp ,a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " FROM tbm_inv AS a1 \n" +
                    " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                    " LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n" +
                    //" WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_stkAct>0 \n" +
                    " WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod="+intCodBod+" AND a2.nd_candis>0 \n" +
                    //" AND (a2.nd_stkAct*a1.nd_cosUni)>0"+
                    " AND (a2.nd_candis*a1.nd_cosUni)>0"+
                    //" ORDER BY (a2.nd_stkAct*a1.nd_cosUni) DESC";
                    " ORDER BY (a2.nd_candis*a1.nd_cosUni) DESC";
                    //" LIMIT 10";
            ResultSet rstAju=stm.executeQuery(strQry);
            listCont=new ArrayList();
            while(rstAju.next()){
                listDatos =new ArrayList();
                listDatos.add(0, rstAju.getInt("co_itm"));
                listDatos.add(1, rstAju.getString("tx_codAlt"));
                listDatos.add(2, rstAju.getString("tx_nomItm"));
                listDatos.add(3, rstAju.getString("tx_uniMed"));
                listDatos.add(4, rstAju.getInt("co_bod"));
                listDatos.add(5, rstAju.getBigDecimal("stock"));
                //listDatos.add(6, rstAju.getBigDecimal("disp"));
                listDatos.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatos.add(7, rstAju.getBigDecimal("tot").setScale(2, RoundingMode.HALF_UP));
                listDatos.add(8, 0);
                listDatos.add(9, rstAju.getBigDecimal("disp"));
                listDatos.add(10,"");
                listCont.add(listDatos);
            }
            //System.out.println("Aqui antes de salir");
            rstAju.close();
            stm.close();
            //listCont.removeAll(lisRem);
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
        return listCont;
    }       
    
    public List obtenerCostosUnitarios(double dif,Connection cnx, int intEmp){
        String strQry="";
        List listDatosCstUni=null;
        List listGrpCstUni=null;
        try{
            Statement stm=cnx.createStatement();
            strQry= " SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n"+
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a1.nd_cosUni, a2.nd_stkAct*a1.nd_cosUni as tot \n" +
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_candis as stock, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a2.nd_candis as disp , a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " FROM tbm_inv AS a1 \n" +
                    " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                    " LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n" +
                    //" WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_stkAct>0 \n" +
                    " WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_candis>0 \n" +
                    " AND a1.nd_cosuni<= "+ dif +
                    //" AND (a2.nd_stkAct*a1.nd_cosUni)>0"+                    
                    " AND (a2.nd_candis*a1.nd_cosUni)>0"+                    
                    " ORDER BY a1.nd_cosUni DESC";                    
            ResultSet rstAju=stm.executeQuery(strQry);
            listGrpCstUni=new ArrayList();
            while(rstAju.next()){
                listDatosCstUni =new ArrayList();
                listDatosCstUni.add(0, rstAju.getInt("co_itm"));
                listDatosCstUni.add(1, rstAju.getString("tx_codAlt"));
                listDatosCstUni.add(2, rstAju.getString("tx_nomItm"));
                listDatosCstUni.add(3, rstAju.getString("tx_uniMed"));
                listDatosCstUni.add(4, rstAju.getInt("co_bod"));
                listDatosCstUni.add(5, rstAju.getBigDecimal("stock"));                
                /*listDatosCstUni.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatosCstUni.add(7, rstAju.getBigDecimal("tot"));*/
                listDatosCstUni.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatosCstUni.add(7, rstAju.getBigDecimal("tot"));  
                listDatosCstUni.add(8, rstAju.getBigDecimal("disp"));
                listGrpCstUni.add(listDatosCstUni);
            }
            //System.out.println("Aqui antes de salir");
            rstAju.close();
            stm.close();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
        return listGrpCstUni;
    }   
    
    public List obtenerCostosUnitarios2(BigDecimal dif, Connection cnx, int intEmp){
        String strQry="";
        List listGrpCstUni=null;
        List listDatosCstUni=null;
        try{
            Statement stm=cnx.createStatement();
            strQry= " SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n"+
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a1.nd_cosUni, a2.nd_stkAct*a1.nd_cosUni as tot \n" +
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_candis as stock, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a2.nd_candis as disp, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " FROM tbm_inv AS a1 \n" +
                    " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                    " LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n" +
                    //" WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_stkAct>0 \n" +
                    " WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_candis>0 \n" +
                    " AND a1.nd_cosuni<= "+ dif +
                    //" AND (a2.nd_stkAct*a1.nd_cosUni)>0"+
                    " AND (a2.nd_candis*a1.nd_cosUni)>0"+
                    " ORDER BY a1.nd_cosUni DESC";                    
            ResultSet rstAju=stm.executeQuery(strQry);
            listGrpCstUni=new ArrayList();
            while(rstAju.next()){
                listDatosCstUni =new ArrayList();
                listDatosCstUni.add(0, rstAju.getInt("co_itm"));
                listDatosCstUni.add(1, rstAju.getString("tx_codAlt"));
                listDatosCstUni.add(2, rstAju.getString("tx_nomItm"));
                listDatosCstUni.add(3, rstAju.getString("tx_uniMed"));
                listDatosCstUni.add(4, rstAju.getInt("co_bod"));
                listDatosCstUni.add(5, rstAju.getBigDecimal("stock"));
                //listDatosCstUni.add(6, rstAju.getBigDecimal("disp"));
/*                listDatosCstUni.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatosCstUni.add(7, rstAju.getBigDecimal("tot"));
                listDatosCstUni.add(8, 0);*/
                listDatosCstUni.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatosCstUni.add(7, rstAju.getBigDecimal("tot"));
                listDatosCstUni.add(8, 0);
                listDatosCstUni.add(9, rstAju.getBigDecimal("disp"));
                listDatosCstUni.add(10,"");
                listGrpCstUni.add(listDatosCstUni);
            }
            rstAju.close();
            stm.close();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
        return listGrpCstUni;
    }
    
    
    public List obtenerCostosUnitarios2(BigDecimal dif, Connection cnx, int intEmp, int intCodBod){
        String strQry="";
        List listGrpCstUni=null;
        List listDatosCstUni=null;
        try{
            Statement stm=cnx.createStatement();
            strQry= " SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n"+
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a1.nd_cosUni, a2.nd_stkAct*a1.nd_cosUni as tot \n" +
                    //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_candis as stock, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_stkAct as stock, a2.nd_candis as disp, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                    " FROM tbm_inv AS a1 \n" +
                    " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                    " LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n" +
                    //" WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_stkAct>0 \n" +
                    " WHERE a1.co_emp="+intEmp+" AND a1.st_reg='A' AND a2.co_bod="+intCodBod+" AND a2.nd_candis>0 \n" +
                    " AND a1.nd_cosuni<= "+ dif +
                    //" AND (a2.nd_stkAct*a1.nd_cosUni)>0"+
                    " AND (a2.nd_candis*a1.nd_cosUni)>0"+
                    " ORDER BY a1.nd_cosUni DESC";                    
            ResultSet rstAju=stm.executeQuery(strQry);
            listGrpCstUni=new ArrayList();
            while(rstAju.next()){
                listDatosCstUni =new ArrayList();
                listDatosCstUni.add(0, rstAju.getInt("co_itm"));
                listDatosCstUni.add(1, rstAju.getString("tx_codAlt"));
                listDatosCstUni.add(2, rstAju.getString("tx_nomItm"));
                listDatosCstUni.add(3, rstAju.getString("tx_uniMed"));
                listDatosCstUni.add(4, rstAju.getInt("co_bod"));
                listDatosCstUni.add(5, rstAju.getBigDecimal("stock"));
                //listDatosCstUni.add(6, rstAju.getBigDecimal("disp"));
/*                listDatosCstUni.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatosCstUni.add(7, rstAju.getBigDecimal("tot"));
                listDatosCstUni.add(8, 0);*/
                listDatosCstUni.add(6, rstAju.getBigDecimal("nd_cosUni"));
                listDatosCstUni.add(7, rstAju.getBigDecimal("tot"));
                listDatosCstUni.add(8, 0);
                listDatosCstUni.add(9, rstAju.getBigDecimal("disp"));
                listDatosCstUni.add(10,"");
                listGrpCstUni.add(listDatosCstUni);
            }
            rstAju.close();
            stm.close();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
        return listGrpCstUni;
    }    
    
    private boolean obtenerDiferenciaxStk(BigDecimal bigDif, Connection cnx, int intEmp, int intCodItm){
        boolean booDifCero=false;
        List listCstUni=obtenerCostosUnitarios2(bigDif,cnx, intEmp);// obtiene los costos unitarios menores a la diferencia existentes
        Iterator itCstUnt=listCstUni.iterator();
        List lstCstUnt=null;
        BigDecimal bigCstUnt=BigDecimal.ZERO, bigTot=BigDecimal.ZERO;        
        BigDecimal bigCantReg=BigDecimal.ZERO;
        while(itCstUnt.hasNext() && !booDifCero){
            lstCstUnt=(List)itCstUnt.next();
            bigCstUnt=(BigDecimal)lstCstUnt.get(6);
            //bigCantReg=(BigDecimal)lstCstUnt.get(5);
            bigCantReg=(BigDecimal)lstCstUnt.get(9);
            BigDecimal bigCant=BigDecimal.ONE;
            bigTot=BigDecimal.ZERO;
            if(intCodItm!=(Integer)lstCstUnt.get(0)){//Para que no tome el item que esta seleccionado antes de entrar a este metodo
                while((bigTot.compareTo(bigDif)==-1) && bigCant.compareTo(bigCantReg)<=0 ){
                    bigTot=bigCstUnt.multiply(bigCant);
                    bigTot=bigTot.setScale(2, RoundingMode.HALF_UP);
                    bigCant=bigCant.add(BigDecimal.ONE);
                }
                if(bigTot.compareTo(bigDif)==0){
                    //lstCstUnt.set(5, bigCant.subtract(BigDecimal.ONE));
                    lstCstUnt.set(7, bigDif);
                    lstCstUnt.set(8, bigCant.subtract(BigDecimal.ONE));
                    if(!verificaExistenciaListado((String)lstCstUnt.get(1))){
                        listResul.add(lstCstUnt);  
                        booDifCero=true;
                    }
                    //booDifCero=true;
                }
            }
        }
        return booDifCero;
    }
    
    private boolean obtenerDiferenciaxStk(BigDecimal bigDif, Connection cnx, int intEmp, int intCodItm, int intCodBod){
        boolean booDifCero=false;
        List listCstUni=obtenerCostosUnitarios2(bigDif,cnx, intEmp, intCodBod);// obtiene los costos unitarios menores a la diferencia existentes
        Iterator itCstUnt=listCstUni.iterator();
        List lstCstUnt=null;
        BigDecimal bigCstUnt=BigDecimal.ZERO, bigTot=BigDecimal.ZERO;        
        BigDecimal bigCantReg=BigDecimal.ZERO;
        while(itCstUnt.hasNext() && !booDifCero){
            lstCstUnt=(List)itCstUnt.next();
            bigCstUnt=(BigDecimal)lstCstUnt.get(6);
            //bigCantReg=(BigDecimal)lstCstUnt.get(5);
            bigCantReg=(BigDecimal)lstCstUnt.get(9);
            BigDecimal bigCant=BigDecimal.ONE;
            bigTot=BigDecimal.ZERO;
            if(intCodItm!=(Integer)lstCstUnt.get(0)){
                while((bigTot.compareTo(bigDif)==-1) && bigCant.compareTo(bigCantReg)<=0 ){
                    bigTot=bigCstUnt.multiply(bigCant);
                    bigTot=bigTot.setScale(2, RoundingMode.HALF_UP);
                    bigCant=bigCant.add(BigDecimal.ONE);
                }
                if(bigTot.compareTo(bigDif)==0){
                    //lstCstUnt.set(5, bigCant.subtract(BigDecimal.ONE));
                    lstCstUnt.set(7, bigDif);
                    lstCstUnt.set(8, bigCant.subtract(BigDecimal.ONE));
                    if(!verificaExistenciaListado((String)lstCstUnt.get(1))){
                        listResul.add(lstCstUnt);                
                        booDifCero=true;
                    }
                }
            }
        }
        return booDifCero;
    }    
    
    private boolean obtenerDiferenciasxMerg(BigDecimal bigDif, Connection cnx, int intEmp){
        boolean booDifCero=false;
        List listCstUni=obtenerCostosUnitarios2(bigDif,cnx,intEmp);// obtiene los costos unitarios menores a la diferencia existentes
        Iterator itCstUnt=listCstUni.iterator();
        List lstCstUnt=null;
        BigDecimal bigCstUnt=BigDecimal.ZERO, bigTot=BigDecimal.ZERO;        
        BigDecimal bigCantReg=BigDecimal.ZERO;
        //BigDecimal bigDif=new BigDecimal(dblDif).setScale(2,RoundingMode.HALF_UP);
        while(itCstUnt.hasNext() && !booDifCero){
            lstCstUnt=(List)itCstUnt.next();
            bigCstUnt=(BigDecimal)lstCstUnt.get(6);
            //bigCantReg=(BigDecimal)lstCstUnt.get(5);
            bigCantReg=(BigDecimal)lstCstUnt.get(9);
            int intCant=1;
            //BigDecimal bigTot=BigDecimal.ZERO;
            bigTot=BigDecimal.ZERO;
            while((bigTot.compareTo(bigDif)==-1) && new BigDecimal(intCant).compareTo(bigCantReg)<=0 && !booDifCero){
                bigTot=bigCstUnt.multiply(new BigDecimal(intCant)) /** intCant*/;
                //bigTot= new BigDecimal(dblTot);
                bigTot=bigTot.setScale(2, RoundingMode.HALF_UP);  
                //BigDecimal bigDifAux=bigTot.subtract(bigDif);
                BigDecimal bigDifAux=bigDif.subtract(bigTot);
                bigDifAux=bigDifAux.setScale(2, RoundingMode.HALF_UP);
                if(bigDifAux.compareTo(BigDecimal.ZERO) > 0){
                    List lisCstUniAux=obtenerCostosUnitarios2(bigDifAux, cnx, intEmp);
                    Iterator itCstUntAux=lisCstUniAux.iterator();
                    while(itCstUntAux.hasNext() && !booDifCero){
                        List lstCstUntAux=(List)itCstUntAux.next();
                        BigDecimal bigCstUntAux= ((BigDecimal)lstCstUntAux.get(6));
                        BigDecimal bigResiduo=bigDifAux.remainder(bigCstUntAux);
                        BigDecimal bigStk= ((BigDecimal)lstCstUntAux.get(5));
                        if(bigResiduo.compareTo(BigDecimal.ZERO)==0){
                            BigDecimal bigCant=bigDifAux.divide(bigCstUntAux,RoundingMode.HALF_UP);
                            if (bigCant.compareTo(bigStk)<=0 && esEntero(bigCant)){
                                lstCstUntAux.set(5, bigCant);
                                lstCstUntAux.set(7, bigDifAux);
                                booDifCero=true;
                                lstCstUnt.set(5, intCant);
                                lstCstUnt.set(7, bigTot);
                                listResul.add(lstCstUnt);
                                listResul.add(lstCstUntAux);
                            }
                        }
                    }
                }else if(bigDifAux.compareTo(BigDecimal.ZERO) == 0){
                    //LA DIFERENCIA YA ES CERO
                    lstCstUnt.set(5, intCant);
                    lstCstUnt.set(7, bigTot);
                    listResul.add(lstCstUnt);
                    booDifCero=true;
                    //LA DIFERENCIA YA ES CERO
                }  
                intCant++;
            }
        }
        return booDifCero;
    }
    
    public boolean esEntero(BigDecimal bigDecimal) {
        return bigDecimal.setScale(0, RoundingMode.HALF_UP).compareTo(bigDecimal) == 0;
    }      


    public boolean calcularAjuste(Connection cnx, BigDecimal bgdSal, int intEmp){
        boolean booRetAju=false;
        BigDecimal bigValRef=bgdSal;
        BigDecimal bigAcum=BigDecimal.ZERO;
        BigDecimal bigDif=BigDecimal.ZERO;
        Iterator itLisCont=generarDatos(cnx, intEmp).iterator();
        listResul=new ArrayList();
        boolean booFinBusDes=false;
        
        while (itLisCont.hasNext() && !booFinBusDes){
            List lisReg=(List)itLisCont.next();
            //BigDecimal bigCant=((BigDecimal)lisReg.get(5));
            BigDecimal bigCant=((BigDecimal)lisReg.get(9));
            BigDecimal bigCostUni= ((BigDecimal)lisReg.get(6));
            bigAcum = bigAcum.add(((BigDecimal)lisReg.get(7)));
            bigAcum=bigAcum.setScale(2, RoundingMode.HALF_UP);
            if(bigAcum.compareTo(bigValRef)>0){
                bigAcum= bigAcum.subtract( ((BigDecimal)lisReg.get(7)).setScale(2, RoundingMode.HALF_UP));
                BigDecimal bigTot=BigDecimal.ZERO;
                BigDecimal BigAux=bigAcum.add(((BigDecimal)lisReg.get(7)));
                while(BigAux.compareTo(bigValRef) > 0 && bigCant.compareTo(BigDecimal.ZERO)>0){ //Disminuye la cantidad hasta que el total sea menor o igual al total
                    bigCant=bigCant.subtract(BigDecimal.ONE);                    
                    bigTot=bigCant.multiply(bigCostUni);
                    BigAux=bigTot.add(bigAcum);//+dblAcum;  
                }
                if(bigCant.compareTo(BigDecimal.ZERO)>0 ){
                   //bigAcum=bigAcum.add(bigTot).setScale(2, RoundingMode.HALF_UP);
                   lisReg.set(7, bigTot.setScale(2, RoundingMode.HALF_UP));
                   //lisReg.set(5, bigCant);
                   lisReg.set(8, bigCant);
                   if(!verificaExistenciaListado((String)lisReg.get(1))){
                        bigAcum=bigAcum.add(bigTot).setScale(2, RoundingMode.HALF_UP);
                        listResul.add(lisReg);
                   }
                   booFinBusDes=true;
                }
            }else if(bigAcum.compareTo(bigValRef)==0){
                lisReg.set(8, bigCant);
                if(!verificaExistenciaListado((String)lisReg.get(1))){
                    listResul.add(lisReg);
                    booFinBusDes=true;
                }                
            }else{
                lisReg.set(8, bigCant);
                if(!verificaExistenciaListado((String)lisReg.get(1))){
                    listResul.add(lisReg);
                }
            }            
        }
        
        
        bigDif=bigValRef.subtract(bigAcum);   //Obtener Diferencia
        Iterator itLisCont2=generarDatos(cnx, intEmp).iterator();
        while(itLisCont2.hasNext() &&  bigDif.compareTo(BigDecimal.ZERO) > 0 ){
            List lisRegCie=(List)itLisCont2.next();
            BigDecimal bigCstTot= (BigDecimal)lisRegCie.get(7);
            bigCstTot=bigCstTot.setScale(2, RoundingMode.HALF_UP);
            if(bigCstTot.compareTo(bigDif) <= 0){
                
                if(!verificaExistenciaListado((String)lisRegCie.get(1))){
                    listResul.add(lisRegCie);
                    bigAcum=bigAcum.add(bigCstTot).setScale(2, RoundingMode.HALF_UP);
                    bigDif=bigValRef.subtract(bigAcum).setScale(2, RoundingMode.HALF_UP);
                    //lisRegCie.set(8, lisRegCie.get(5));
                    lisRegCie.set(8, lisRegCie.get(9));
                    if(bigDif.compareTo(new BigDecimal(100)) < 0){
                        lisRegCie.set(10, "X");
                    }
                    //lisRegCie.set(8, lisRegCie.get(5));
                    
                }
                
            }            
        }
        
        /*DIFERENCIA ES MAYOR A CERO Y DIFERENCIA ES MENOR A 0.10 CTVS. 
        BUSCA LOS COSTOS UNITARIOS MENORES A LA DIFERENCIA QUE
        SUMANDOLOS DEN EL TOTAL DE LA DIFERENCIA*/
        bigDif=bigDif.setScale(2, RoundingMode.HALF_UP);
        if (bigDif.compareTo(BigDecimal.ZERO)>0 && bigDif.compareTo(new BigDecimal(0.10)) < 0){
            List listGrpCstUni2 =obtenerCostosUnitarios2(bigDif,cnx, intEmp);
            boolean booCosUni=false;
            BigDecimal bigTotAux=BigDecimal.ZERO;
            for(int x=0;(x<listGrpCstUni2.size() && !booCosUni);x++) {
                bigTotAux=BigDecimal.ZERO;
                for(int y=x+1;(y<listGrpCstUni2.size() && !booCosUni );y++) {
                    List lstExt=(List)listGrpCstUni2.get(x);
                    List lstInt=(List)listGrpCstUni2.get(y);
                    BigDecimal bigCstInt =  ((BigDecimal)lstInt.get(6)).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal bigCstExt =  ((BigDecimal)lstExt.get(6)).setScale(2, RoundingMode.HALF_UP);
                    bigTotAux=bigCstInt.add(bigCstExt).setScale(2, RoundingMode.HALF_UP);
                    if(bigDif.compareTo(bigTotAux)==0){
                        //bigAcum=bigAcum.add(bigTotAux).setScale(2, RoundingMode.HALF_UP);
                        //bigDif=bigValRef.subtract(bigAcum);
                        //lstInt.set(5, 1);
                        lstInt.set(7,bigCstInt);
                        lstInt.set(8, new BigDecimal(1));
                        //if(!verificaExistenciaListado((String)lstInt.get(1))){
                        //listResul.add(lstInt);
                        //}
                        //lstExt.set(5, 1);                        
                        lstExt.set(7,bigCstExt);
                        lstExt.set(8, new BigDecimal(1));
                        if(!verificaExistenciaListado((String)lstInt.get(1))){
                            if(!verificaExistenciaListado((String)lstExt.get(1))){
                                bigAcum=bigAcum.add(bigTotAux).setScale(2, RoundingMode.HALF_UP);
                                bigDif=bigValRef.subtract(bigAcum);                                
                                listResul.add(lstInt);
                                listResul.add(lstExt);
                                booCosUni=true;
                            }
                        }                        
                    }
                }   
            }            
        }

        /*ELIMINO DEL ARRAY DE RESULTADOS Y DEL ACUMULADO LOS ITEMS QUE HE MARCADO Y QUE ME PROVOCAN UNA DIFERENCIA MENOR A 60*/
        List listTemp=new ArrayList();
        //if (dblDif>0 && dblDif < 0.10){            
        if (bigDif.compareTo(BigDecimal.ZERO)>0 /*&& bigDif.compareTo(new BigDecimal(0.10)) < 0*/){
            Iterator itLisCont3=listResul.iterator();
            while(itLisCont3.hasNext()){
                List lisRegCie=(List)itLisCont3.next();
                if(((String)lisRegCie.get(10)).equalsIgnoreCase("X")){
                    listTemp.add(lisRegCie);
                    bigAcum=bigAcum.subtract((BigDecimal)lisRegCie.get(7));
                    bigAcum=bigAcum.setScale(2, RoundingMode.HALF_UP);
                    bigDif= bigValRef.subtract(bigAcum);
                }
            }
            listResul.removeAll(listTemp);


            
            /*DESPUES DE QUITAR LOS ITEMS DEL LIST DE RESULTADOS DEBIDO A QUE LA DIFERENCIA ERA MUY CORTA 
             LA DIFERENCIA SE VA A DIVIDIR ENTRE TRES*/
            BigDecimal bigPropMenCien=BigDecimal.ZERO;
            boolean booDifCstTot=false;
            List listCstUni=null;
            bigPropMenCien=bigDif.divide(new BigDecimal(3), RoundingMode.HALF_UP);
            listCstUni=obtenerCostosUnitarios2(bigPropMenCien,cnx, intEmp);
            Iterator itCstUnt2=listCstUni.iterator();
            List lstCstUnt2=null;
            BigDecimal bigCstUnt2=BigDecimal.ZERO;
            BigDecimal bigCstTot2=BigDecimal.ZERO;
            BigDecimal bigDif2=BigDecimal.ZERO;
            while(itCstUnt2.hasNext() && !booDifCstTot){
                lstCstUnt2=(List)itCstUnt2.next();
                bigCstUnt2=(BigDecimal)lstCstUnt2.get(6);
                //if(((BigDecimal)lstCstUnt2.get(5)).compareTo(( new BigDecimal(2))) >=0){
                if(((BigDecimal)lstCstUnt2.get(9)).compareTo(( new BigDecimal(2))) >=0){
                    bigCstTot2=bigCstUnt2.multiply(new BigDecimal(2));
                    bigCstTot2=bigCstTot2.setScale(2, RoundingMode.HALF_UP);
                    bigDif2=bigDif.subtract(bigCstTot2);
                    bigAcum=bigAcum.setScale(2, RoundingMode.HALF_UP);
                    //if (dblDif2>0){                        
                    if (bigDif2.compareTo(BigDecimal.ZERO)>0){
                        booDifCstTot=obtenerDiferenciaxStk(bigDif2.setScale(2, RoundingMode.HALF_UP),cnx,intEmp, (Integer)lstCstUnt2.get(0));
                        /*if(!booDifCstTot){
                            booDifCstTot=obtenerDiferenciasxMerg(bigDif2);
                        }*/
                    }
                }
            }
            if(booDifCstTot){
                //lstCstUnt2.set(5, new BigDecimal(2.0d));
                lstCstUnt2.set(7, bigCstTot2);
                lstCstUnt2.set(8, new BigDecimal(2.0d));
                if(!verificaExistenciaListado((String)lstCstUnt2.get(1))){
                    listResul.add(lstCstUnt2);
                    bigDif=BigDecimal.ZERO;
                }
                
            }
            //}
        }
        if(bigDif.compareTo(BigDecimal.ZERO)==0){
            booRetAju=true;
            //sintetizaLista();
            //booRetAju=verificarListadoFinal(intEmp, cnx);
        }
        //imprimirResultados(listResul,cnx);
        //booRetAju=false;
        return booRetAju;
        //return false;
    }
    
    
    public boolean calcularAjuste(Connection cnx, BigDecimal bgdSal, int intEmp, int intCodBod){
        boolean booRetAju=false;
        BigDecimal bigValRef=bgdSal;
        BigDecimal bigAcum=BigDecimal.ZERO;
        BigDecimal bigDif=BigDecimal.ZERO;
        Iterator itLisCont=generarDatos(cnx, intEmp, intCodBod).iterator();
        listResul=new ArrayList();
        boolean booFinBusDes=false;
        
        while (itLisCont.hasNext() && !booFinBusDes){
            List lisReg=(List)itLisCont.next();
            //BigDecimal bigCant=((BigDecimal)lisReg.get(5));
            BigDecimal bigCant=((BigDecimal)lisReg.get(9));
            BigDecimal bigCostUni= ((BigDecimal)lisReg.get(6));
            bigAcum = bigAcum.add(((BigDecimal)lisReg.get(7)));
            bigAcum=bigAcum.setScale(2, RoundingMode.HALF_UP);
            if(bigAcum.compareTo(bigValRef)>0){
                bigAcum= bigAcum.subtract( ((BigDecimal)lisReg.get(7)).setScale(2, RoundingMode.HALF_UP));
                BigDecimal bigTot=BigDecimal.ZERO;
                BigDecimal BigAux=bigAcum.add( ((BigDecimal)lisReg.get(7)));
                while(BigAux.compareTo(bigValRef) > 0 && bigCant.compareTo(BigDecimal.ZERO)>0){
                    bigCant=bigCant.subtract(BigDecimal.ONE);                    
                    bigTot=bigCant.multiply(bigCostUni);
                    BigAux=bigTot.add(bigAcum);//+dblAcum;  
                }
                if(bigCant.compareTo(BigDecimal.ZERO)>0){
                   //bigAcum=bigAcum.add(bigTot).setScale(2, RoundingMode.HALF_UP);
                   lisReg.set(7, bigTot.setScale(2, RoundingMode.HALF_UP));
                   //lisReg.set(5, bigCant);
                   lisReg.set(8, bigCant);
                   if(!verificaExistenciaListado((String)lisReg.get(1))){
                       bigAcum=bigAcum.add(bigTot).setScale(2, RoundingMode.HALF_UP);
                       listResul.add(lisReg);
                   }
                   booFinBusDes=true;
                }
            }else if(bigAcum.compareTo(bigValRef)==0){
                lisReg.set(8, bigCant);
                if(!verificaExistenciaListado((String)lisReg.get(1))){
                    listResul.add(lisReg);
                    booFinBusDes=true;
                }
                //booFinBusDes=true;
            }else{
                lisReg.set(8, bigCant);
                if(!verificaExistenciaListado((String)lisReg.get(1))){
                    listResul.add(lisReg);
                }
            }            
        }
        
        
        bigDif=bigValRef.subtract(bigAcum);   //Obtener Diferencia
        Iterator itLisCont2=generarDatos(cnx, intEmp, intCodBod).iterator();
        while(itLisCont2.hasNext() &&  bigDif.compareTo(BigDecimal.ZERO) > 0 ){
            List lisRegCie=(List)itLisCont2.next();
            BigDecimal bigCstTot= (BigDecimal)lisRegCie.get(7);
            bigCstTot=bigCstTot.setScale(2, RoundingMode.HALF_UP);
            if(bigCstTot.compareTo(bigDif) <= 0){
                //bigAcum=bigAcum.add(bigCstTot).setScale(2, RoundingMode.HALF_UP);
                //bigDif=bigValRef.subtract(bigAcum).setScale(2, RoundingMode.HALF_UP);
                //lisRegCie.set(8, lisRegCie.get(5));
                lisRegCie.set(8, lisRegCie.get(9));
//                if(bigDif.compareTo(new BigDecimal(100)) < 0){
//                    lisRegCie.set(10, "X");
//                }
                //lisRegCie.set(8, lisRegCie.get(5));
                if(!verificaExistenciaListado((String)lisRegCie.get(1))){
                    bigAcum=bigAcum.add(bigCstTot).setScale(2, RoundingMode.HALF_UP);
                    bigDif=bigValRef.subtract(bigAcum).setScale(2, RoundingMode.HALF_UP);                    
                    listResul.add(lisRegCie);
                }
                
                if(bigDif.compareTo(new BigDecimal(100)) < 0){
                    lisRegCie.set(10, "X");
                }
                
            }            
        }
        
        /*DIFERENCIA ES MAYOR A CERO Y DIFERENCIA ES MENOR A 0.10 CTVS.*/
        bigDif=bigDif.setScale(2, RoundingMode.HALF_UP);
        if (bigDif.compareTo(BigDecimal.ZERO)>0 && bigDif.compareTo(new BigDecimal(0.10)) < 0){
            List listGrpCstUni2 =obtenerCostosUnitarios2(bigDif,cnx, intEmp, intCodBod);
            boolean booCosUni=false;
            BigDecimal bigTotAux=BigDecimal.ZERO;
            for(int x=0;(x<listGrpCstUni2.size() && !booCosUni);x++) {
                bigTotAux=BigDecimal.ZERO;
                for(int y=x+1;(y<listGrpCstUni2.size() && !booCosUni );y++) {
                    List lstExt=(List)listGrpCstUni2.get(x);
                    List lstInt=(List)listGrpCstUni2.get(y);
                    BigDecimal bigCstInt =  ((BigDecimal)lstInt.get(6)).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal bigCstExt =  ((BigDecimal)lstExt.get(6)).setScale(2, RoundingMode.HALF_UP);
                    bigTotAux=bigCstInt.add(bigCstExt).setScale(2, RoundingMode.HALF_UP);
                    if(bigDif.compareTo(bigTotAux)==0){
//                        bigAcum=bigAcum.add(bigTotAux).setScale(2, RoundingMode.HALF_UP);
//                        bigDif=bigValRef.subtract(bigAcum);
                        //lstInt.set(5, 1);
                        lstInt.set(7,bigCstInt);
                        lstInt.set(8, new BigDecimal(1));
//                        if(!verificaExistenciaListado((String)lstInt.get(1))){
//                            listResul.add(lstInt);
//                        }
                        //lstExt.set(5, 1);                        
                        lstExt.set(7,bigCstExt);
                        lstExt.set(8, new BigDecimal(1));
                        if(!verificaExistenciaListado((String)lstExt.get(1))){
                            if(!verificaExistenciaListado((String)lstInt.get(1))){
                                bigAcum=bigAcum.add(bigTotAux).setScale(2, RoundingMode.HALF_UP);
                                bigDif=bigValRef.subtract(bigAcum);
                                listResul.add(lstExt);
                                booCosUni=true;
                            }
                        }
//                        booCosUni=true;
                    }
                }   
            }            
        }
        

        /*ELIMINO DEL ARRAY DE RESULTADOS Y DEL ACUMULADO LOS ITEMS QUE HE MARCADO Y QUE ME PROVOCAN UNA DIFERENCIA MENOR A 60*/
        List listTemp=new ArrayList();
        //if (dblDif>0 && dblDif < 0.10){            
        if (bigDif.compareTo(BigDecimal.ZERO)>0 ){
            Iterator itLisCont3=listResul.iterator();
            while(itLisCont3.hasNext()){
                List lisRegCie=(List)itLisCont3.next();
                if(((String)lisRegCie.get(10)).equalsIgnoreCase("X")){
                    listTemp.add(lisRegCie);
                    bigAcum=bigAcum.subtract((BigDecimal)lisRegCie.get(7));
                    //listResul.remove(lisRegCie);
                    bigAcum=bigAcum.setScale(2, RoundingMode.HALF_UP);
                    bigDif= bigValRef.subtract(bigAcum);
                    //dblDif=dblValRef-dblAcum;
                }
            }
            listResul.removeAll(listTemp);


            
            /*DESPUES DE QUITAR LOS ITEMS DEL LIST DE RESULTADOS DEBIDO A QUE LA DIFERENCIA ERA MUY CORTA 
            EL LA DIFERENCIA SE VA A DIVIDIR ENTRE TRES*/
            BigDecimal bigPropMenCien=BigDecimal.ZERO;
            boolean booDifCstTot=false;
            List listCstUni=null;
            //if(dblDif<=100){
                //dblPropMenCien=dblDif/3;
                bigPropMenCien=bigDif.divide(new BigDecimal(3), RoundingMode.HALF_UP);
                listCstUni=obtenerCostosUnitarios2(bigPropMenCien,cnx, intEmp, intCodBod);
                Iterator itCstUnt2=listCstUni.iterator();
                List lstCstUnt2=null;
                BigDecimal bigCstUnt2=BigDecimal.ZERO;
                BigDecimal bigCstTot2=BigDecimal.ZERO;
                BigDecimal bigDif2=BigDecimal.ZERO;
                while(itCstUnt2.hasNext() && !booDifCstTot){
                    lstCstUnt2=(List)itCstUnt2.next();
                    bigCstUnt2=(BigDecimal)lstCstUnt2.get(6);
                    //if(((BigDecimal)lstCstUnt2.get(5)).compareTo(( new BigDecimal(2))) >=0){
                    if(((BigDecimal)lstCstUnt2.get(9)).compareTo(( new BigDecimal(2))) >=0){
                        bigCstTot2=bigCstUnt2.multiply(new BigDecimal(2));
                        bigCstTot2=bigCstTot2.setScale(2, RoundingMode.HALF_UP);
                        bigDif2=bigDif.subtract(bigCstTot2);
                        bigAcum=bigAcum.setScale(2, RoundingMode.HALF_UP);
                        //if (dblDif2>0){                        
                        if (bigDif2.compareTo(BigDecimal.ZERO)>0){
                            booDifCstTot=obtenerDiferenciaxStk(bigDif2.setScale(2, RoundingMode.HALF_UP),cnx,intEmp, (Integer)lstCstUnt2.get(0), intCodBod);
                            /*if(!booDifCstTot){
                                booDifCstTot=obtenerDiferenciasxMerg(bigDif2);
                            }*/
                        }
                    }
                }
                if(booDifCstTot){
                    //lstCstUnt2.set(5, new BigDecimal(2.0d));
                    lstCstUnt2.set(7, bigCstTot2);
                    lstCstUnt2.set(8, new BigDecimal(2.0d));
                    if(!verificaExistenciaListado((String)lstCstUnt2.get(1))){
                        listResul.add(lstCstUnt2);
                        bigDif=BigDecimal.ZERO;
                    }
//                    bigDif=BigDecimal.ZERO;
                }
            //}
        }
        if(bigDif.compareTo(BigDecimal.ZERO)==0){
            booRetAju=true;
            //sintetizaLista();
            //booRetAju=verificarListadoFinal(intEmp, cnx);
        }
        //imprimirResultados(listResul,cnx);
        //booRetAju=false;
        return booRetAju;
        //return false;
    }    
    
    
    private void sintetizaLista(){
        
        for(int i=0;i<listResul.size();i++){
            List lstReg=(List)listResul.get(i);
            String strCod=(String)lstReg.get(1);
            for(int j=i+1;j<listResul.size();j++){
                List lstReg2=(List)listResul.get(j);
                String strCod2=(String)lstReg2.get(1);
                if(strCod.equalsIgnoreCase(strCod2)){
                    System.out.println("Tx alterno " + strCod2);
                    lstReg2.set(8, ((BigDecimal)lstReg2.get(8)).add((BigDecimal)lstReg.get(8)));
                    BigDecimal bigCst=((BigDecimal)lstReg2.get(8)).multiply((BigDecimal)lstReg2.get(6));
                    bigCst=bigCst.setScale(2, RoundingMode.HALF_UP);
                    lstReg2.set(7, bigCst);
                    listResul.remove(i);
                }
            }
        }
    }
    
    public boolean verificaStk(int intCodItm, int intCodEmp, BigDecimal bigCan, Connection cnx){
        Statement stm=null;  
        String strSQL="";
        ResultSet rs=null;
        boolean booRet=true;
        try{
            strSQL=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm \n" +
                            //" a3.tx_desLar AS tx_uniMed, a2.co_bod, a2.nd_candis as stock, a1.nd_cosUni, a2.nd_candis*a1.nd_cosUni as tot \n" +
                            " FROM tbm_inv AS a1 \n" +
                            " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                            " LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n" +
                            " WHERE a1.co_emp="+intCodEmp+" AND a1.st_reg='A' AND a2.co_bod=15 AND a2.nd_candis>0 \n" +
                            " AND A1.CO_ITM IN (" + intCodItm +")\n" +
                            " AND a2.nd_candis >= (" + bigCan +")\n" +
                            " AND (a2.nd_candis*a1.nd_cosUni)>0 ";
            stm=cnx.createStatement();
            rs=stm.executeQuery(strSQL);
            if(!rs.next()){
                booRet=false;
            }
            
        }catch(Exception ex){
           ex.printStackTrace();
        }
        return booRet;
    
    }
    
    private boolean verificarListadoFinal(int intCodEmp, Connection cnx){
        boolean booRet=true;
        for(int i=0;i<listResul.size();i++){
            List lstReg=(List)listResul.get(i);
            int intCodItm=(Integer)lstReg.get(0);//CODITEM
            BigDecimal bigCan=(BigDecimal)lstReg.get(8);
            if(!verificaStk(intCodItm, intCodEmp, bigCan, cnx)){
                booRet=false;
                break;
            }
        }
        return booRet;        
    }
    
    
    private boolean verificaExistenciaListado(String strCodAlt){
        boolean booRetorno=false;
        for(int i=0;i<listResul.size();i++){
            List lstReg=(List)listResul.get(i);
            String strtxtAlt=(String)lstReg.get(1);//CODITEM
            if(strtxtAlt.equalsIgnoreCase(strCodAlt)){
                booRetorno= true;
                break;
            }else{
                booRetorno=false;
                //break;
            }                      
        }
        return booRetorno;
    }

//    public static void main(String[] args){
//        ZafCon64_Aju objAju= new ZafCon64_Aju();
//        Connection con=null;
//        try{
//            Class.forName("org.postgresql.Driver");
//            con=DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_27_Mar_2017", "postgres", "*szlsc*");
//        
//            //objAju.verificaStk(16566, 4, new BigDecimal(10), con);
//            objAju.verificaStk(19309, 1, new BigDecimal(2), con);
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
    
    
}
