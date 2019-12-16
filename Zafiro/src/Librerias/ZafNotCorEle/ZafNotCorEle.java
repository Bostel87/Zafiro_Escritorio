
package Librerias.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringTokenizer;
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
 * @author Sistemas6
 */
public class ZafNotCorEle 
{
    private ZafParSis objParSis; 
    private String strSQL=""; 
    private String server="",userName="", password="",fromAddres="",toAddres="",copyAddres="",puerto="", subject="";
    
    private String strVer=" v0.1.3";
     
     public ZafNotCorEle(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
        }
        catch (CloneNotSupportedException e){
            //System.out.println("ZafNotCorEle: " + e);
        }
    }    
    
    public boolean enviarNotificacionCorreoElectronico(int codigoEmpresa, int codigoLocal, int codigoNotificacion, String message ){
        boolean blnRes=false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        boolean htmlFormat = true;
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_not, tx_dirsercor, ne_puesercor, tx_corelerem";
                strSQL+="      , tx_coreleclarem, tx_coreledes, tx_corelecop, tx_asu, tx_men, tx_obs1, tx_obs2 ";
                strSQL+=" FROM tbm_notSisCorEleLoc";
                strSQL+=" WHERE st_reg = 'A'";
                strSQL+=" AND co_emp="+codigoEmpresa+" AND co_loc="+codigoLocal+" AND co_not="+codigoNotificacion+"";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    server = rstLoc.getString("tx_dirSerCor");
                    userName = rstLoc.getString("tx_corEleRem");   //"zafiro@tuvalsa.com"
                    password = rstLoc.getString("tx_corEleClaRem");
                    fromAddres = rstLoc.getString("tx_corEleRem");
                    toAddres = rstLoc.getString("tx_corEleDes");
                    copyAddres = rstLoc.getString("tx_corEleCop")==null?"":rstLoc.getString("tx_corEleCop");
                    puerto = rstLoc.getString("ne_pueSerCor");
                    subject = rstLoc.getString("tx_asu");
                    if(enviarCorreo(server, puerto, userName, password, fromAddres, toAddres, copyAddres, htmlFormat, subject, message)){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException ex) {
            System.err.println("ERROR " + ex.toString());
            blnRes=false;
        }
        catch (Exception e) {  
            System.err.println("ERROR "+ e.toString());
            blnRes=false;
        } 
        return blnRes;
    }
    

    public boolean enviarNotificacionCorreoElectronicoConAsunto(int codigoEmpresa, int codigoLocal, int codigoNotificacion, String message, String subject ){
        boolean blnRes=false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        boolean htmlFormat = true;
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_not, tx_dirsercor, ne_puesercor, tx_corelerem";
                strSQL+="      , tx_coreleclarem, tx_coreledes, tx_corelecop, tx_asu, tx_men, tx_obs1, tx_obs2 ";
                strSQL+=" FROM tbm_notSisCorEleLoc";
                strSQL+=" WHERE st_reg = 'A'";
                strSQL+=" AND co_emp="+codigoEmpresa+" AND co_loc="+codigoLocal+" AND co_not="+codigoNotificacion+"";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    server = rstLoc.getString("tx_dirSerCor");
                    userName = rstLoc.getString("tx_corEleRem"); // "zafiro@tuvalsa.com"
                    password = rstLoc.getString("tx_corEleClaRem");
                    fromAddres = rstLoc.getString("tx_corEleRem");
                    toAddres = rstLoc.getString("tx_corEleDes");
                    copyAddres = rstLoc.getString("tx_corEleCop")==null?"":rstLoc.getString("tx_corEleCop");
                    puerto = rstLoc.getString("ne_pueSerCor");
                    if(enviarCorreo(server, puerto, userName, password, fromAddres, toAddres, copyAddres, htmlFormat, subject, message)){
                        blnRes=true;
                    } 
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException ex) {
            System.err.println("ERROR " + ex.toString());
            blnRes=false;
        }
        catch (Exception e) {  
            System.err.println("ERROR "+ e.toString());
            blnRes=false;
        } 
        return blnRes;
    }    
     
    private boolean enviarCorreo(String server, String puerto, String userName, String password, String fromAddress, String toAddress, String ccAddress,   boolean htmlFormat, String subject, String body) {
        boolean blnRes=false;
        try{
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", server);
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.user", userName);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", puerto);
            props.put("mail.smtp.auth", "true");  

            // Get session
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() { 
                        return new PasswordAuthentication(userName, password);
                    }
                }
            );

            session.setDebug(true);

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
    
    
    
    
     
}
