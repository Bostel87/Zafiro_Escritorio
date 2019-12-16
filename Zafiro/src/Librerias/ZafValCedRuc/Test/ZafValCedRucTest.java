/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafValCedRuc.Test;

//import Compras.ZafCom01.ZafCom01_xXx;
import Librerias.ZafValCedRuc.TuvalUtilitiesException;
import Librerias.ZafValCedRuc.VerificarId;
import Librerias.ZafValCedRuc.ZafSRIDatos;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 * @author clainez
 */
public class ZafValCedRucTest {

    public static void main(String[] args) throws TuvalUtilitiesException, MalformedURLException, IOException {
        // TODO code application logic here

        //String strCed = "0921808158";
        //String strCed = "0921828158";
        //String strCed = "0920126901";
        //String strCed = "0920126901";
        //String strCed = "0905279485";
        //String strCed = "0909096695";
        //String strCed = "0907613806";
        //String strCed = "0919791541";
        //String strCed = "0907613806";
        //String strCed = "1303244055";
        //String strCed = "090600859-4";
        //String strCed = "0920402674";
        //String strCed = "1305019000";
        //String strCed = "1717722753";
        //String strCed = "0922484985";
        //String strCed = "0102015625";
        String strCed = "0911417558";
        //ZafCom01 zafCom01 = new ZafCom01_xXx(null);
        //zafCom01.
        

        VerificarId verificarId = new VerificarId();
//        boolean esCorrecta = verificarId.verificarId(strCed);
        ZafSRIDatos zafSRIDatos = null;
        boolean esCorrecta = verificarId.verificarRUCSRI("0920126901001");
        zafSRIDatos = verificarId.getSRIDatos();
        if(zafSRIDatos==null){
            System.out.println("RUC NO EXISTE!!!");
        }else{
            System.out.println("Razón Social: " + zafSRIDatos.getStrRazSoc());
            System.out.println("RUC: " + zafSRIDatos.getStrRuc());
            System.out.println("Nombre Comercial: " + zafSRIDatos.getStrNomCom());
            System.out.println("Estado del Contribuyente en el RUC: " + zafSRIDatos.getStrStConRuc());
            System.out.println("Clase de Contribuyente: " + zafSRIDatos.getStrClsCon());
            System.out.println("Tipo de Contribuyente: " + zafSRIDatos.getStrTipCon());
            System.out.println("Obligado a llevar Contabilidad: " + zafSRIDatos.getStrOblCon());
            System.out.println("Actividad Económica Principal: " + zafSRIDatos.getStrActEcoPrin());
            System.out.println("Fecha de inicio de actividades: " + zafSRIDatos.getStrFeIniAct());
            System.out.println("Fecha de cese de actividades: " + zafSRIDatos.getStrFeCesAct());
            System.out.println("Fecha reinicio de actividades: " + zafSRIDatos.getStrFeReiAct());
            System.out.println("Fecha actualización: " + zafSRIDatos.getStrFeAct());
        }
//        System.out.println("CEDULA " + strCed + "  " + esCorrecta);

    }
}
