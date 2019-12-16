package Librerias.ZafPulFacEle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente 
{
	String str="1-4-laser_cpXXX";
        final int puerto=6010;
        String host ="172.16.8.4";
        Socket so;
        DataOutputStream mensaje;
	    
        public Cliente() {
            initCliente();
        }

        public void initCliente(){
            try {
                so=new Socket(host, puerto);
                mensaje=new DataOutputStream(so.getOutputStream());
                //mensaje.writeUTF(str);
                mensaje.writeInt(1);
                so.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
	
	public static void main(String[] args) {
            Cliente oc=new Cliente();

	}

}
