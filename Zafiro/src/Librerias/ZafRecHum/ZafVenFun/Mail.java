/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafVenFun;

import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * Roberto Flores
 * v1.0
 */
public class Mail {
    
    
    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: cuentaFicticia@tuvalsa.com; cuentaFicticia2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String subject, String strMsj ){
        try {

            String server = "172.16.1.3";
            String userName = "zafiro";
            String password = "#tuv*sis28/10=";
            String fromAddres = "sistemas2@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = "";
            String bcc = "";
            boolean htmlFormat = true;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }

    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: correoFicticio@tuvalsa.com; correoFicticio2@tuvalsa.com
    * @param strCorEleCC - Cuentas de correo a las que se enviara copia del email; estas deben estar separadas por ";". Ej: correoFicticio3@tuvalsa.com; correoFicticio4@tuvalsa.com
    * @param strCorEleCCO - Cuentas de correo a las que se enviara copia oculta del email; estas deben estar separadas por ";". Ej: correoOculto@tuvalsa.com; correoOculto2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String strCorEleCC, String strCorEleCCO, String subject, String strMsj ){
        try {

            String server = "172.16.1.3";
            String userName = "zafiro";
            String password = "#tuv*sis28/10=";
            String fromAddres = "sistemas2@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = (strCorEleCC==null?"":(strCorEleCC.equals("")?"":strCorEleCC));
            String bcc = (strCorEleCCO==null?"":(strCorEleCCO.equals("")?"":strCorEleCCO));
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }

    public boolean sendMailTuvMas(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) {
        boolean blnRes=false;
        try{

            Properties props = System.getProperties();
            props.put("mail.smtp.host", server);

            Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject);

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
    }
    
    static class MyAuthenticator extends Authenticator {
 PasswordAuthentication l = new PasswordAuthentication("zafiro", "#tuv*sis28/10=");
 @Override
 protected PasswordAuthentication getPasswordAuthentication() {
 return l;
 }
 }
    
}



