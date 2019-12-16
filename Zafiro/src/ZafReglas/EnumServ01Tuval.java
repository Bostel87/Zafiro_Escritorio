/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

/**
 *
 * @author usuario
 */
public enum EnumServ01Tuval {
    
    EMPGRP("1"),
    BODGRP("1"),
    EMPPRENUMGUIA("1"),
    STRINGCONEXION("jdbc:postgresql://172.16.8.7:5432/Zafiro2006_18_Feb_2015"),
    USUARIOCONEXION("2C055DFEB76E2FDDE5645B646BBCE417"),
    CLAVECONEXION("DB70AA4FD4BF789A079C083E3F9B4156"),
    RUTAREPORTE1("/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_08.jasper"),
    RUTAREPORTE2("/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper"),
    
    //RUTAREPORTE1("D:/Users/sistemas5/Documents/zafirosvn/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_08.jasper"),
    NOMIMPR("SamsungSCX-3400"),
    NOMIMPR2("od_califormia"),   
    IMPBODEGA2("S"),
    BODGRPTUVALINMACONSA("15"),    
    CODIGOEMPPRENUMGUIATUVALINMACONSA("1"),
    CODIGOLOCPRENUMGUIATUVALINMACONSA("10"),
    LOCPPRENUMGUIA("4");
    
    private String valor="";
    
    EnumServ01Tuval(String valor){
        this.valor=valor;
    }
    
     public String getValor() { return valor; }
    
}
