
package Librerias.ZafCorEle;
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
 * @author sistemas6
 */
public class ZafCorEle 
{
    private ZafParSis objParSis;
    
    private int intCodLocGrp=1;
    private int intCodCfgCorEleCfgGen=28;
    
    private String server="", userName="", password="", fromAddres="", puerto="";
    private String strSQL="";
    
    private String strVer=" v0.5";
    
    public ZafCorEle(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
        }
        catch (CloneNotSupportedException e){ }
    }     
    
    /**
    * FUNCION ENVIA CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param toAddres - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: cuentaFicticia@tuvalsa.com; cuentaFicticia2@tuvalsa.com
    * @param subject - Asunto
    * @param message - Cuerpo del mensaje de correo. 
    */
    public boolean enviarCorreoMasivo(String toAddres, String subject, String message ){
        boolean blnRes=false;
        boolean htmlFormat = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;        
        String CC = "", CCO = "";        
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_not, tx_dirsercor, ne_puesercor, tx_corelerem";
                strSQL+="      , tx_coreleclarem, tx_coreledes, tx_corelecop, tx_asu, tx_men, tx_obs1, tx_obs2 ";
                strSQL+=" FROM tbm_notSisCorEleLoc";
                strSQL+=" WHERE st_reg = 'A'";
                strSQL+=" AND co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND co_loc="+intCodLocGrp+" AND co_not="+intCodCfgCorEleCfgGen+"";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    server = rstLoc.getString("tx_dirSerCor");
                    userName = rstLoc.getString("tx_corEleRem"); // "zafiro@tuvalsa.com"
                    password = rstLoc.getString("tx_corEleClaRem");
                    fromAddres = rstLoc.getString("tx_corEleRem");
                    //toAddres = rstLoc.getString("tx_corEleDes");
                    CC = rstLoc.getString("tx_corEleCop")==null?"":rstLoc.getString("tx_corEleCop");
                    puerto = rstLoc.getString("ne_pueSerCor");
                    if(sendMailTuvMas(server, userName, password, fromAddres, toAddres, CC, CCO, htmlFormat, subject, message)){
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
    

    /**
    * FUNCION ENVIA CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param toAddres - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: correoFicticio@tuvalsa.com; correoFicticio2@tuvalsa.com
    * @param strCorEleCC - Cuentas de correo a las que se enviara copia del email; estas deben estar separadas por ";". Ej: correoFicticio3@tuvalsa.com; correoFicticio4@tuvalsa.com
    * @param strCorEleCCO - Cuentas de correo a las que se enviara copia oculta del email; estas deben estar separadas por ";". Ej: correoOculto@tuvalsa.com; correoOculto2@tuvalsa.com
    * @param subject - Asunto
    * @param message - Cuerpo del mensaje de correo. 
    */
    public boolean enviarCorreoMasivo(String toAddres, String strCorEleCC, String strCorEleCCO, String subject, String message ){
        boolean blnRes=false;
        boolean htmlFormat = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;          
        String CC = (strCorEleCC==null?"":(strCorEleCC.equals("")?"":strCorEleCC));
        String CCO = (strCorEleCCO==null?"":(strCorEleCCO.equals("")?"":strCorEleCCO));
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, co_loc, co_not, tx_dirsercor, ne_puesercor, tx_corelerem";
                strSQL+="      , tx_coreleclarem, tx_coreledes, tx_corelecop, tx_asu, tx_men, tx_obs1, tx_obs2 ";
                strSQL+=" FROM tbm_notSisCorEleLoc";
                strSQL+=" WHERE st_reg = 'A'";
                strSQL+=" AND co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND co_loc="+intCodLocGrp+" AND co_not="+intCodCfgCorEleCfgGen+"";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    server = rstLoc.getString("tx_dirSerCor");
                    userName = rstLoc.getString("tx_corEleRem"); // "zafiro@tuvalsa.com"
                    password = rstLoc.getString("tx_corEleClaRem");
                    fromAddres = rstLoc.getString("tx_corEleRem");
                    //toAddres = rstLoc.getString("tx_corEleDes");
                    //CC = rstLoc.getString("tx_corEleCop")==null?"":rstLoc.getString("tx_corEleCop");
                    puerto = rstLoc.getString("ne_pueSerCor");
                    if(sendMailTuvMas(server, userName, password, fromAddres, toAddres, CC, CCO, htmlFormat, subject, message)){
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

    public boolean sendMailTuvMas(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) 
    {
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
    
    
    
}
