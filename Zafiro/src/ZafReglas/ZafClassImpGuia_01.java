/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ZafReglas;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author jayapata
 */
public class ZafClassImpGuia_01 {


String strCorEleTo="sistemas4@tuvaulsa.com";
String strEnvCorEle="S";  // S = envia mail  N = no envia mail

    public ZafClassImpGuia_01(String strCorEleToRec ) {

        strCorEleTo  = strCorEleToRec;
       

    }



    



public void enviarCorreo(String strMsExcp){
  try
    {

        String server = "mail.tuvalsa.com";
        String userName = "zafiro";
        String password = "#tuv*sis28/10=";
        String fromAddres = "zafiro@tuvalsa.com";
        String toAddres = strCorEleTo;

        String cc = "";
        String bcc = "";
        boolean htmlFormat = false;
        String subject = "SafSer01";
        String body = strMsExcp;

        if(strEnvCorEle.equals("S")){
             sendMailTuv(server, userName, password, fromAddres, toAddres, cc, bcc,
                     htmlFormat, subject, body);

        }


        }
        catch (Exception e) {  e.printStackTrace();    }


      }



    public void sendMail(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String mensajeBady)
    {

        Properties properties = System.getProperties();
        properties.put("mail.smtps.host", server);
        properties.put("mail.smtps.auth", "true");
        Session ses  = Session.getInstance(properties);

        ses.setDebug(true);

        try{

            MimeMessage msg = new MimeMessage(ses);

            msg.setFrom(new InternetAddress(fromAddress));

            if (toAddress != null)
            {
               msg.addRecipients(Message.RecipientType.TO, toAddress);
            }


             msg.setSubject(subject);

             MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
             cuerpoCorreo.setText(mensajeBady);

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            msg.setContent(multipart);

            msg.saveChanges();

            Transport tr = ses.getTransport("smtps");
            tr.connect(server,userName, password);
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        }

        catch(MessagingException e)
        {
            e.printStackTrace();
        }



    }



 public boolean sendMailTuv(String server, String userName, String password, String fromAddress, String toAddress, String cc, String bcc, boolean htmlFormat, String subject, String body)
   {
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

            // Set the to address
            message.addRecipient(Message.RecipientType.TO,
            new InternetAddress(toAddress));

            // Set the subject
            message.setSubject(subject);


                MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
                //cuerpoCorreo.setText("Estado de Cuenta ...");
                 cuerpoCorreo.setContent(body, "text/html");


                //java.io.File archivo = new java.io.File("C://Estado_Cuenta.pdf");

               // MimeBodyPart  adjuntoCorreo = new MimeBodyPart();
               // adjuntoCorreo.attachFile(archivo);

                    MimeMultipart multipart= new MimeMultipart();
                    multipart.addBodyPart(cuerpoCorreo);
                   // multipart.addBodyPart(adjuntoCorreo);

                    message.setContent(multipart);

                    message.saveChanges();


            // Set the content
           // message.setText("Welcome");

            // Send message
            Transport.send(message);

           blnRes=true;

       }catch(MessagingException e){ blnRes=false;  e.printStackTrace(); }

      return blnRes;
    }




static class MyAuthenticator extends Authenticator {
 PasswordAuthentication l = new PasswordAuthentication("zafiro", "#tuv*sis28/10=");
 protected PasswordAuthentication getPasswordAuthentication() {
 return l;
 }
}





}
