
package Librerias.ZafValCedRuc;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 *
 * @author clainez
 */
public class VerificarId {

    private final int INT_NUMPROECU = 24;
    private final int INT_NUMDIGCED = 10;
    private final int INT_NUMDIGRUC = 13;

    private final String STR_NUMCED = "No. de CÃ©dula:";
    private final String STR_DIGVER = "DÃ­gito Verificador:</font></strong></td><td align=left style='width: 19px'><font size=2>";
    private final String STR_NOMCED = "Nombre del Cedulado:</font></strong></td><td align=left style='width: 370px'><font size=2>";
    private final String STR_FECNAC = "Fecha Nacimiento:</font></strong></td><td align=left style='width: 370px'><font size=2>";
    private final String STR_RESGYE = "Reside en Guayaquil    :</font></strong></td><td align=left style='width: 134px'><font size=2>";
    private final String STR_CONCED = "CondiciÃ³n de CÃ©dula:</font></strong></td><td align=left><font size=2>";
    private final String STR_ESTCIV = "Estado Civil:</font></strong></td><td align=left><font size=2>";
    private final String STR_NOMCON = "Nombre Conyuge:</font></strong></td><td align=left><font size=2>";
    private final String STR_POSFICIND = "Posee Ficha Indice:</font></strong></td><td align=left><font size=2>";
    private final String STR_POSFICDAC = "Posee Ficha DactiloscÃ³pica:</font></strong></td><td align=left><font size=2>";
    private final String STR_SI = "SI";
    
    private final String STR_RAZSOC = "<tr><th>Raz&oacute;n Social:</th><td>";
    private final String STR_RUC = "<tr><th>RUC:</th><td>";
    private final String STR_NOMCOM = "<tr><th>Nombre Comercial:</th><td>";
    private final String STR_STCONRUC = "<tr><th>Estado del Contribuyente en el RUC</th>\n" +
                                        "          \n" +
                                        "          <td>";
    private final String STR_CLACON = "<th>Clase de Contribuyente</th>\n" +
                                        "          \n" +
                                        "          <td>";
    
    private final String STR_TIPCON = "<tr><th>Tipo de Contribuyente</th>\n" +
                                        "          \n" +
                                        "          <td>";
    private final String STR_OBLCON = "<th>Obligado a llevar Contabilidad</th><td>";
    
    private final String STR_ACTECOPRI = "<th>Actividad Econ&oacute;mica Principal</th><td>";
    
    private final String STR_FEINIACT = "<th>Fecha de inicio de actividades</th>\n" +
                                            "          <td>";
    
    private final String STR_FEESACT = "<tr><th>Fecha de cese de actividades</th>\n" +
                                        "          <td>";
    
    private final String STR_FEREIACT = "<th>Fecha reinicio de actividades</th>\n" +
                                        "          <td>";
    
    private final String STR_FEACT = "<tr><th>Fecha actualizaci&oacute;n</th>\n" +
                                        "          <td>";
    
    
    private final String STR_WEBREGCIV = "http://www.corporacionregistrocivil.gov.ec/OnLine/show_cedula.asp";
    private final String STR_WEBSRI = "https://declaraciones.sri.gov.ec/facturacion-internet/consultas/publico/ruc-datos2.jspa";
    private ZafRegCiv regCiv = null;
    private ZafSRIDatos zafSriDat=null;

    public boolean verificarId(String strId) throws TuvalUtilitiesException{
        boolean blnOk=false;

        if(strId.length()==INT_NUMDIGCED){
            blnOk= (verificarCedRegCiv(strId) || verificarCed(strId));
        }else{
            if(strId.length()==INT_NUMDIGRUC){
                int intTerDig = Integer.parseInt(strId.substring(2, 3));
                if(intTerDig<6){
                    blnOk=verificarRucPerNat(strId);
                }else{
                    if(intTerDig==6){
                        blnOk=verificarRucInsPub(strId);
                    }else{
                        if(intTerDig==9){
                            blnOk=verificarRucJurExtSinCed(strId);
                        }
                    }
                }
            }else{
                throw new TuvalUtilitiesException("Longitud de número de cédula incorrecto.");
            }
        }

        return blnOk;
    }

    /**
     * Algoritmo "Módulo 10" valida cédula de identidad de una Persona según dígito verificador.
     *
     * @param strCed Cédula de Identidad
     * @return Retorna true si es correcto o false si no lo es
     * @throws TuvalUtilitiesException
     */
     public boolean verificarCed(String strCed) throws TuvalUtilitiesException {
         boolean blnOk = (strCed.length()==INT_NUMDIGCED);

         if(blnOk){
             blnOk = (Integer.parseInt(strCed.substring(0, 2)) > 0
                    && Integer.parseInt(strCed.substring(0, 2)) <= INT_NUMPROECU
                    && Integer.parseInt(strCed.substring(2, 3)) < 6);

             if (blnOk) {
                 int intSum = 0;
                int[] intCoe = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

                for (int i = 0; i <= 8; i++) {
                    int intTot = Integer.parseInt(strCed.substring(i, i + 1)) * intCoe[i];

                    if (intTot > 9){
                        intTot -= 9;
                    }

                    intSum += intTot;
                }

                int intRes = intSum % 10;
                int intDigVer = intRes>0?(10-intRes):0;

                blnOk = (intDigVer <= Integer.parseInt(strCed.substring(9, 10)));
                if (!blnOk){
                    throw new TuvalUtilitiesException("Número de cédula incorrecto.");
                }
             }else{
                 throw new TuvalUtilitiesException("Número de cédula incorrecto");
             }

         }else{
             throw new TuvalUtilitiesException("Longitud de cédula de identificación incorrecto.");
         }

         return blnOk;
     }
     
     public boolean verificarRUCSRI(String strRuc) throws TuvalUtilitiesException, MalformedURLException, IOException{
         boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);
         
         if(blnOk){
             
             //Se inicia el objeto HTTP
             HttpClient client = new HttpClient();
            client.setStrictMode(true);
                //Se fija el tiempo máximo de espera de la respuesta del servidor
                client.setTimeout(60000);
                //Se fija el tiempo máximo de espera para conectar con el servidor
                client.setConnectionTimeout(5000);
                PostMethod post = null;
                //Se fija la URL sobre la que enviar la petición POST
                post = new PostMethod(STR_WEBSRI);
                //Se fija la codificación de caracteres en la cabecera de la petición
                post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
                //Se crea la lista de parámetros a enviar en la petición POST
                NameValuePair[] parametersList = new NameValuePair[1];
                parametersList[0] = new NameValuePair("ruc", strRuc);

                //Se rellena el cuerpo de la petición POST con los parámetros
                post.setRequestBody(parametersList);
                int httpstatus = 0;
                String response = null;

                try {
                    //Se envía la petición
                    httpstatus = client.executeMethod(post);
                    //Se consigue la respuesta
                    response = post.getResponseBodyAsString();
                }catch(Exception e){
                    //Habra que prever la captura de excepciones
                    //return;
                    e.printStackTrace();
                    return false;
                }finally {
                    //En cualquier caso se cierra la conexión
                    post.releaseConnection();
                }

                //Habra que prever posibles errores en la respuesta del servidor
                if (httpstatus!=200){
                    return false;
                }/*else if(httpstatus==404){
                    throw new TuvalUtilitiesException("Servidor Registro Civil No Encontrado");
                }*/
                else {
                    //Se procesa la respuesta capturada en la cadena
                    //System.out.println(response);
                    //System.out.println(cedula.substring(0,9));
                    Pattern patron = Pattern.compile(strRuc.substring(0,13));
                    Matcher m = patron.matcher(response);

                    if (!m.find()){
                        //System.out.println("La cedula no existe");
                        //return;
                        throw new TuvalUtilitiesException("Número de cédula incorrecto.");
                        //return false;
                    }else{

                        zafSriDat = new ZafSRIDatos();
                        String[] strRazSoc = response.split(STR_RAZSOC);
                        strRazSoc=strRazSoc[1].split("</td></tr>");
                        zafSriDat.setStrRazSoc(strRazSoc[0]);


                        String[] arrStrRuc = response.split(STR_RUC);
                        arrStrRuc=arrStrRuc[1].split("</td></tr>");
                        zafSriDat.setStrRuc(arrStrRuc[0]);

                        String[] arrStrNomCom = response.split(STR_NOMCOM);
                        arrStrNomCom=arrStrNomCom[1].split("</td></tr>");
                        if(arrStrNomCom[0].compareTo("")==0){
                            zafSriDat.setStrNomCom(null);
                        }else{
                            zafSriDat.setStrNomCom(arrStrNomCom[0]);
                        }

                        String[] arrStrStConRuc = response.split(STR_STCONRUC);
                        arrStrStConRuc=arrStrStConRuc[1].split("</td>");
                        if(arrStrStConRuc[0].compareTo("")==0){
                            zafSriDat.setStrStConRuc(null);
                        }else{
                            zafSriDat.setStrStConRuc(arrStrStConRuc[0]);
                        }

                        String[] arrStrClaCon = response.split(STR_CLACON);
                        arrStrClaCon=arrStrClaCon[1].split("</td>");
                        if(arrStrClaCon[0].compareTo("")==0){
                            zafSriDat.setStrClsCon(null);
                        }else{
                            zafSriDat.setStrClsCon(arrStrClaCon[0]);
                        }

                        String[] arrStrTipCon = response.split(STR_TIPCON);
                        arrStrTipCon=arrStrTipCon[1].split("</td>");
                        if(arrStrTipCon[0].compareTo("")==0){
                            zafSriDat.setStrTipCon(null);
                        }else{
                            zafSriDat.setStrTipCon(arrStrTipCon[0]);
                        }

                        String[] arrStrOblCon = response.split(STR_OBLCON);
                        arrStrOblCon=arrStrOblCon[1].split("</td>");
                        if(arrStrOblCon[0].compareTo("")==0){
                            zafSriDat.setStrOblCon(null);
                        }else{
                            zafSriDat.setStrOblCon(arrStrOblCon[0]);
                        }

                        String[] arrStrActEcoPri = response.split(STR_ACTECOPRI);
                        arrStrActEcoPri=arrStrActEcoPri[1].split("</td>");
                        if(arrStrActEcoPri[0].compareTo("")==0){
                            zafSriDat.setStrActEcoPrin(null);
                        }else{
                            zafSriDat.setStrActEcoPrin(arrStrActEcoPri[0]);
                        }

                        String[] arrStrFeIniAct = response.split(STR_FEINIACT);
                        arrStrFeIniAct=arrStrFeIniAct[1].split("</td>");
                        if(arrStrFeIniAct[0].compareTo("")==0){
                            zafSriDat.setStrFeIniAct(null);
                        }else{
                            zafSriDat.setStrFeIniAct(arrStrFeIniAct[0]);
                        }

                        String[] arrStrFeCesAct = response.split(STR_FEESACT);
                        arrStrFeCesAct=arrStrFeCesAct[1].split("\n");
                        if(arrStrFeCesAct[0].compareTo("")==0){
                            zafSriDat.setStrFeCesAct(null);
                        }else{
                            zafSriDat.setStrFeCesAct(arrStrFeCesAct[0]);
                        }

                        String[] arrStrFeReiAct = response.split(STR_FEREIACT);
                        arrStrFeReiAct=arrStrFeReiAct[1].split("\n");
                        if(arrStrFeReiAct[0].compareTo("")==0){
                            zafSriDat.setStrFeReiAct(null);
                        }else{
                            zafSriDat.setStrFeReiAct(arrStrFeReiAct[0]);
                        }

                        String[] arrStrFeAct = response.split(STR_FEACT);
                        arrStrFeAct=arrStrFeAct[1].split("\n");
                        if(arrStrFeAct[0].compareTo("")==0){
                            zafSriDat.setStrFeAct(null);
                        }else{
                            zafSriDat.setStrFeAct(arrStrFeAct[0]);
                        }
                    }
                }
             
         }
         else{
             throw new TuvalUtilitiesException("Número de RUC incorrecto.");
         }
         return blnOk;
     }
     

     public boolean verificarCedRegCiv(String strCed) throws TuvalUtilitiesException {

         boolean blnOk = (strCed.length()==INT_NUMDIGCED);

         if(blnOk){
             blnOk = (Integer.parseInt(strCed.substring(0, 2)) > 0
                    && Integer.parseInt(strCed.substring(0, 2)) <= INT_NUMPROECU
                    && Integer.parseInt(strCed.substring(2, 3)) < 6);

             if (blnOk) {

                //Se inicia el objeto HTTP
                HttpClient client = new HttpClient();
                client.setStrictMode(true);
                //Se fija el tiempo máximo de espera de la respuesta del servidor
                client.setTimeout(60000);
                //Se fija el tiempo máximo de espera para conectar con el servidor
                client.setConnectionTimeout(5000);
                PostMethod post = null;
                //Se fija la URL sobre la que enviar la petición POST
                post = new PostMethod(STR_WEBREGCIV);
                //Se fija la codificación de caracteres en la cabecera de la petición
                post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
                //Se crea la lista de parámetros a enviar en la petición POST
                NameValuePair[] parametersList = new NameValuePair[1];
                parametersList[0] = new NameValuePair("nroced", strCed);

                //Se rellena el cuerpo de la petición POST con los parámetros
                post.setRequestBody(parametersList);
                int httpstatus = 0;
                String response = null;

                try {
                    //Se envía la petición
                    httpstatus = client.executeMethod(post);
                    //Se consigue la respuesta
                    response = post.getResponseBodyAsString();
                }catch(Exception e){
                    //Habra que prever la captura de excepciones
                    //return;
                    return false;
                }finally {
                    //En cualquier caso se cierra la conexión
                    post.releaseConnection();
                }

                //Habra que prever posibles errores en la respuesta del servidor
                if (httpstatus!=200){
                    //return;
                    //throw new TuvalUtilitiesException("Ocurrio un error al intentar conectarse a la pagina del RC");
                    return false;
                }/*else if(httpstatus==404){
                    throw new TuvalUtilitiesException("Servidor Registro Civil No Encontrado");
                }*/
                else {
                    //Se procesa la respuesta capturada en la cadena
                    //System.out.println(response);
                    //System.out.println(cedula.substring(0,9));
                    Pattern patron = Pattern.compile(strCed.substring(0,9));
                    Matcher m = patron.matcher(response);

                    if (!m.find()){
                        //System.out.println("La cedula no existe");
                        //return;
                        throw new TuvalUtilitiesException("Número de cédula incorrecto.");
                        //return false;
                    }else{

                        regCiv = new ZafRegCiv();
                        String [] arrCedula = response.split(STR_NUMCED);
                        regCiv.setStrNumCed(arrCedula[1].substring(49, 58));

                        String [] arrDigVer = response.split(STR_DIGVER);
                        regCiv.setStrDigVer(arrDigVer[1].substring(0,1));

                        /*String [] arrNomCed = response.split(STR_NOMCED);
                        regCiv.setStrNomCed(arrNomCed[1].substring(0,50));*/

                        String [] arrNomCed = response.split(STR_NOMCED);
                        String [] arrNomCed2 = arrNomCed[1].split("</");
                        //regCiv.setStrNomCed(arrNomCed[1].substring(0,50));
                        regCiv.setStrNomCed(arrNomCed2[0]);

                        String [] arrFecNac = response.split(STR_FECNAC);
                        regCiv.setStrFecNac(arrFecNac[1].substring(0,10));

                        String [] arrResGye = response.split(STR_RESGYE);
                        String strResGye = arrResGye[1].substring(0,2);
                        if(strResGye.trim().compareTo(STR_SI)==0){regCiv.setIntResGye(1);}else{regCiv.setIntResGye(0);}

                        String [] arrConCed = response.split(STR_CONCED);
                        regCiv.setStrConCed(arrConCed[1].substring(0,14));
                        if(regCiv.getStrConCed().equals("CEDULA ANULADA")){
                            throw new TuvalUtilitiesException("Número de cédula anulada.");
                        }else if(regCiv.getStrConCed().trim().equals("FALLECIDO")){
                            throw new TuvalUtilitiesException("Número de cédula no válida.");
                        }

                        String [] arrEstCiv = response.split(STR_ESTCIV);
                        regCiv.setStrEstCiv(arrEstCiv[1].substring(0,9));

                        String [] arrNomCon = response.split(STR_NOMCON);
                        if (arrNomCon[1].substring(0,7).compareTo("</font>")!=0){
                            regCiv.setStrNomCon(arrNomCon[1].substring(0,50));
                        }

                        String [] arrPosFic = response.split(STR_POSFICIND);
                        String strPosFic = arrPosFic[1].substring(0,2);
                        if(strPosFic.trim().compareTo(STR_SI)==0){regCiv.setIntFicInd(1);}else{regCiv.setIntFicInd(0);}

                        String [] arrPosDac = response.split(STR_POSFICDAC);
                        String strPosDac = arrPosDac[1].substring(0,2);
                        if(strPosDac.trim().compareTo(STR_SI)==0){regCiv.setIntFicDac(1);}else{regCiv.setIntFicDac(0);}

                        }
                    }

                }else{
                    throw new TuvalUtilitiesException("Número de cédula incorrecto.");
                    //return false;
                }

             }else{
                throw new TuvalUtilitiesException("Longitud de número de cédula incorrecto.");
                //return false;
             }

     return blnOk;

     }

     /**
     * Algoritmo "Módulo 10" valida RUC de Personas Naturales por dígito verificador
     *
     * @param strRuc RUC de Persona Natural
     * @return Retrona true si es correcto y false si no lo es
     * @throws TuvalUtilitiesException
     */
    private boolean verificarRucPerNat(String strRuc) throws TuvalUtilitiesException {
        boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);

        if(blnOk){
            try {
                blnOk = (verificarCedRegCiv(strRuc.substring(0,10)) || verificarCed(strRuc.substring(0, 10)));

                if(!(blnOk && (Integer.parseInt(strRuc.substring(10, 13)) > 0 && Integer.parseInt(strRuc.substring(10, 13)) < 999))){
                    throw new TuvalUtilitiesException("RUC incorrecto");
                }
            } catch (TuvalUtilitiesException ex) {
                throw new TuvalUtilitiesException(ex.getMessage());
            }
        }else{
            throw new TuvalUtilitiesException("Longitud del RUC incorrecto.");
        }

        return blnOk;
    }

    /**
     * Algoritmo "Módulo 11" valida RUC de Personas Jurídicas y Extranjeras sin Cédula por dígito verificador
     *
     * @param strRuc RUC de Persona Jurídica y Extranjera
     * @return Retorna true si es correcto y false si no lo es
     * @throws TuvalUtilitiesException
     */
    private boolean verificarRucJurExtSinCed(String strRuc) throws TuvalUtilitiesException {
        boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);

        if(blnOk){
            blnOk = (Integer.parseInt(strRuc.substring(0, 2)) > 0
                    && Integer.parseInt(strRuc.substring(0, 2)) <= INT_NUMPROECU
                    && Integer.parseInt(strRuc.substring(2, 3)) == 9
                    && Integer.parseInt(strRuc.substring(10, 13)) > 0);

            if(blnOk) {
                int[] intCoe = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
                int intSum = 0;

                for (int i = 0; i <= 8; i++){
                    intSum += Integer.parseInt(strRuc.substring(i, i + 1)) * intCoe[i];
                }

                int intRes = intSum % 11;
                int intDigVer = intRes>0?(11-intRes):0;

                blnOk = (intDigVer == Integer.parseInt(strRuc.substring(9, 10)));

                if (!blnOk){
                    throw new TuvalUtilitiesException("RUC incorrecto.");
                }
            }else{
                throw new TuvalUtilitiesException("RUC incorrecto.");
            }
        }else{
            throw new TuvalUtilitiesException("Longitud del RUC incorrecto.");
        }

        return blnOk;
    }

    /**
     * Algoritmo "Módulo 11" valida RUC de Instituciones Públicas según dígito verificador
     *
     * @param strRuc RUC de Instituciones Públicas
     * @return Retorna true si es correcto y false si no lo es
     * @throws Exception
     */
    private boolean verificarRucInsPub(String strRuc) throws TuvalUtilitiesException {
        boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);

        if(blnOk){
            blnOk = (Integer.parseInt(strRuc.substring(0, 2)) > 0
                    && Integer.parseInt(strRuc.substring(0, 2)) <= INT_NUMPROECU
                    && Integer.parseInt(strRuc.substring(2, 3)) == 6
                    && Integer.parseInt(strRuc.substring(9, 13)) > 0);

            if (blnOk) {
                int[] intCoe = { 3, 2, 7, 6, 5, 4, 3, 2 };
                int intSum = 0;

                for (int i = 0; i <= 7; i++){
                    intSum += Integer.parseInt(strRuc.substring(i, i + 1)) * intCoe[i];
                }

                int intRes = intSum % 11;
                int intDigVer = intRes>0?(11-intRes):0;

                blnOk = (intDigVer == Integer.parseInt(strRuc.substring(8, 9)));

                if(!blnOk){
                    throw new TuvalUtilitiesException("RUC incorrecto.");
                }
            } else{
                throw new TuvalUtilitiesException("RUC incorrecto.");
            }
        }else{
            throw new TuvalUtilitiesException("Longitud del RUC incorrecto");
        }

        return blnOk;
    }

    /*public boolean comprobarConexion(){
        boolean blnRes = false;
        try{
            URL myURL = new URL("http","http://www.corporacionregistrocivil.gov.ec","/OnLine/show_cedula.asp");
            blnRes = true;
        }//catch (MalfornedURLException e){
        catch (MalformedURLException e){
            //si no la encuentra pasa aqui
            blnRes = false;
        }

        return blnRes;
    }*/

    public ZafRegCiv getRegistroCivil(){
        return this.regCiv;
    }
    
    public ZafSRIDatos getSRIDatos(){
        return this.zafSriDat;
    }
}