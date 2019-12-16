package Librerias.ZafUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Abre un socket con el servidor para bajar o subir archivos
 * @author Carlos Lainez
 * Guayaquil 14/06/2011
 */
public class ZafSer06Cli {
    private final int DEFAULT_PORT = 6666;

    /**
     * Permite subir archivos al servidor
     * @param filFue Archivo a subir
     * @param strRutDes Ruta destino del servidor Ej: /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/
     * @param strNewNomArc Nombre con el que se va a grabar el archivo en el servidor Ej: ZafRecHum03_c1_c2_t1_s1.doc
     * @param strHost Ip o nombre del servidor Ej: 172.16.1.1
     * @throws Exception
     */
    public void exportar(File filFue, String strIp, String strRutSer, String strNewNomArc) throws Exception {
        Socket socketCliente = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        FileInputStream fis = null;

        try {
            socketCliente = new Socket(strIp, DEFAULT_PORT);
            dos = new DataOutputStream(socketCliente.getOutputStream());
            dis = new DataInputStream(socketCliente.getInputStream());
            fis = new FileInputStream(filFue);

            System.out.println("Enviando id cliente");
            String strUserName = System.getProperty("user.name");
            dos.writeUTF(strUserName!=null?strUserName:"Cliente");

            System.out.println("Enviando Ruta Destino");
            dos.writeUTF(strRutSer);

            System.out.println("Enviando Nombre de Archivo");
            dos.writeUTF(strNewNomArc);

            System.out.println("Enviando Tipo de Peticion");
            dos.writeUTF("W");

            System.out.println("Enviando archivo");
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) > 0){
                dos.write(buf, 0, len);
                dos.flush();
            }
            
        } catch (ConnectException ex) {
            throw new Exception("Servidor no levantado. "+ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally{
            try{fis.close();}catch(Throwable ignore){}
            try{dos.close();}catch(Throwable ignore){}
            try{dis.close();}catch(Throwable ignore){}
            try{socketCliente.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Permite bajar archivos desde el servidor al cliente en una carpeta temporal generalmente
     * @param strRutDes Ruta del archivo en el servidor Ej: /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/
     * @param strNomArc Nombre del archivo en el servidor Ej: ZafRecHum03_c1_c2_t1_s1.doc
     * @param strHost Ip o nombre del servidor Ej: 172.16.1.1
     * @param strTmpFolder Carpeta local en donde se guardara el archivo
     * @throws Exception
     */
    public void importar(String strRutDes, String strNomArc, String strHost, String strTmpFolder) throws Exception {
        Socket socketCliente = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        FileOutputStream fos = null;

        try {
            socketCliente = new Socket(strHost, DEFAULT_PORT);
            dos = new DataOutputStream(socketCliente.getOutputStream());
            dis = new DataInputStream(socketCliente.getInputStream());

            System.out.println("Enviando id cliente");
            String strUserName = System.getProperty("user.name");
            dos.writeUTF(strUserName!=null?strUserName:"<<DESCONOCIDO>>");

            System.out.println("Enviando Ruta Destino");
            dos.writeUTF(strRutDes);

            System.out.println("Enviando Nombre de Archivo");
            dos.writeUTF(strNomArc);

            System.out.println("Enviando Tipo de Peticion");
            dos.writeUTF("R");

            System.out.println("Importando archivo en carpeta temporal");

            fos = new FileOutputStream(new File(strTmpFolder+File.separator+strNomArc));
            System.out.println("Espacio creado");

            int len;
            byte[] buf = new byte[1024];

            /*while ((len = dis.read(buf)) > 0) {
                fos.write(buf, 0, len);
                fos.flush();
            }*/
            len = dis.read(buf);
            if(len > 0){
                do{
                    fos.write(buf, 0, len);
                    fos.flush();
                }while((len = dis.read(buf)) > 0);
                System.out.println("Archivo recibido");
            }else{
                System.out.println("Archivo no encontrado");
                throw new Exception("Archivo no encontrado!Comunicar a Sistemas.");
            }
        } catch (ConnectException ex) {
            throw new Exception("Servidor no levantado. "+ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally{
            try{fos.close();}catch(Throwable ignore){}
            try{dos.close();}catch(Throwable ignore){}
            try{dis.close();}catch(Throwable ignore){}
            try{socketCliente.close();}catch(Throwable ignore){}
        }
    }
}
