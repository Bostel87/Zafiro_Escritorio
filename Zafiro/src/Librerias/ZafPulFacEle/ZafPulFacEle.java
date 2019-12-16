/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafPulFacEle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 * v 0.1
 * @author José Marín M.
 */
public class ZafPulFacEle {
    
   final int puerto = 6010; //PUERTO DE FAC ELE 
   
   //String host = "172.16.8.7";
   String host = "172.16.8.2";
   //String hostSerUno = "172.16.8.7";
   String hostSerUno = "172.16.8.2";
   //String hostSerCatorce = "172.16.8.68";
   String hostSerCatorce = "172.16.8.2";
   Socket so;
   DataOutputStream mensaje;
   DataInputStream entrada;
   
   String strVersion=" v 0.05";

   public void iniciar(){
       try {
           System.out.println("Normal");
          so = new Socket(host, puerto);
          mensaje = new DataOutputStream(so.getOutputStream());
          mensaje.writeInt(1);
          so.close();
      }
      catch (Exception e) {
          System.out.println(e.getMessage());
      }
   }
   
   public void iniciarEnvioPulsoServicio15(String strPrmSer)
   {
      int intPue = 6013; //Puerto de ZafSer15
      
      try {
          System.out.println("Normal");
          so = new Socket(host, intPue);
          mensaje = new DataOutputStream(so.getOutputStream());
          mensaje.writeUTF(strPrmSer);
          so.close();
      }
      catch (Exception e) {
          System.out.println(e.getMessage());
      }
   }
   
    public void iniciarImpresion(String strDat, int intPuerto){
       try {
          
          so = new Socket(hostSerUno,intPuerto);
          mensaje = new DataOutputStream(so.getOutputStream());
          mensaje.writeUTF(strDat);
          so.close();
      }
      catch (Exception e) {
          System.out.println(e.getMessage());
      }
   }
   
	public void iniciarConGui( int intPuerto){
       try {
          
          so = new Socket(hostSerCatorce,intPuerto);
          mensaje = new DataOutputStream(so.getOutputStream());
          mensaje.writeInt(1);
          so.close();
      }
      catch (Exception e) {
          System.out.println(e.getMessage());
      }
   }   
}