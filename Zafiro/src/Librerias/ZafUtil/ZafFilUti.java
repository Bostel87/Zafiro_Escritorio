
package Librerias.ZafUtil;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

/**
 * Contiene metodos para el manejo de archivos
 * @author Roberto Flores
 * Guayaquil 24/05/2011
 */
public class ZafFilUti {

    /**
     * Copia un archivo hacia un directorio existente.
     * En el caso de clientes linux se debe crear la carpeta raiz tal cual consta en el servidor(Ej: /Zafiro)
     * en donde se montara la ruta remota haciendo asi esa ruta parte del file system.
     * En el caso de clientes windows no hay que configurar nada.
     * @param strRutFueNomArc Ruta completa del archivo a copiar incluyendo el nombre original del archivo Ej: /home/clainez/Documents/Estandares/Colores.doc
     * @param strRutDes Ruta completa del directorio destino Ej: (Linux) /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval ;(Windows) //172.16.1.7/Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/
     * @param strNewNomArc Nombre con el que se grabara el archivo Ej: ZafRecHum03_c1_c2_t1_s1.doc
     * @throws TuvalFileException
     * @throws IOException
     */
    /*public void copiarArcDir(String strRutFueNomArc, String strRutDes, String strNewNomArc) throws TuvalFileException {
        File filFue = null;
        File filDes = null;
        InputStream in = null;
        OutputStream out = null;
        byte[] buf = new byte[1024];

        try{
            filFue = new File(strRutFueNomArc);
            //String strNomArc = filFue.getName();
            //String strExt = strNomArc.substring(strNomArc.lastIndexOf("."), strNomArc.length());
            filDes = new File(strRutDes+strNewNomArc);//+strExt);
            in = new FileInputStream(filFue);
            out = new FileOutputStream(filDes);
            buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
        } catch(FileNotFoundException ex){
            throw new TuvalFileException(ex.getMessage());
        } catch(IOException ex){
            throw new TuvalFileException(ex.getMessage());
        } finally {
            try {in.close();}catch(Throwable ignore){}
            try {out.close();}catch(Throwable ignore){}
        }
    }*/

    /**
     * Copia un archivo hacia un directorio existente.
     * En el caso de clientes linux se debe crear la carpeta raiz tal cual consta en el servidor(Ej: /Zafiro)
     * en donde se montara la ruta remota(Ej: //172.16.1.7/Zafiro) haciendo asi esa ruta parte del file system.
     * En el caso de clientes windows no hay que configurar nada.
     * @param strRutFueNomArc Ruta completa del archivo a copiar incluyendo el nombre original del archivo Ej: /home/clainez/Documents/Estandares/Colores.doc
     * @param strRutDes Ruta completa del directorio destino Ej: (Linux) /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/ ;(Windows) //172.16.1.7/Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/
     * @param strNewNomArc Nombre con el que se grabara el archivo Ej: ZafRecHum03_c1_c2_t1_s1.doc
     * @throws TuvalFileException
     */
    public void subirArc(String strRutFueNomArc, String strRutDes, String strNewNomArc) throws TuvalFileException {
        File filFue = null;
        File filDes = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            filFue = new File(strRutFueNomArc);
            filDes = new File(strRutDes+strNewNomArc);
            inChannel = new FileInputStream(filFue).getChannel();
            outChannel = new FileOutputStream(filDes).getChannel();
            //inChannel.transferTo(0, inChannel.size(),outChannel);//replaced with code bellow
            //ini
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size) {
                position +=
                inChannel.transferTo(position, maxCount, outChannel);
            }
            //fin
        }catch(FileNotFoundException ex){
            throw new TuvalFileException(ex.getMessage());
        }catch (IOException ex) {
            throw new TuvalFileException(ex.getMessage());
        }
        finally {
            try {inChannel.close();}catch(Throwable ignore){}
            try {outChannel.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Abre cualquier archivo con su programa ejecutable ya asociado por el sistema operativo
     * @param strRutNomArc Ruta completa del archivo y el nombre Ej: /tmp/ZafRecHum03_c1_c2_t1_s1.doc ; /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/ZafRecHum03_c1_c2_t1_s1.doc
     * @throws TuvalFileException
     */
    public void abrirArc(String strRutNomArc) throws TuvalFileException{
        try{
            
//            if (!Desktop.isDesktopSupported()){
//                String strTit = "Mensaje del sistema Zafiro";
//                String strMen = "El visor no esta disponible por el momento.\nFavor comunicar a Sistemas";
//                JOptionPane.showMessageDialog(null,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
//            }
//            boolean blnRes = Desktop.isDesktopSupported();
            if(Desktop.isDesktopSupported()){
                File fil = new File(strRutNomArc);
                Desktop des = Desktop.getDesktop();
                des.open(fil);
            }else{
                if(System.getProperty("os.name").equals("Linux")){
                    final Process process = Runtime.getRuntime().exec("kde-open " + strRutNomArc);
                }else if(System.getProperty("os.name").equals("Windows")){
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "El visor para Windows OS no esta disponible por el momento.\nFavor comunicar a Sistemas";
                    JOptionPane.showMessageDialog(null,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }catch(UnsupportedOperationException ex){
            throw new TuvalFileException(ex.getMessage());
        }
        catch(IOException ex){
            throw new TuvalFileException(ex.getMessage());
        }
    }

    /**
     * Permite subir archivos al servidor mediante sockets.
     * @param strRutNomArc Ruta origen completa del archivo a copiar incluyendo el nombre original del archivo Ej: /home/clainez/Documents/Estandares/Colores.doc
     * @param strHost Ip o nombre del servidor Ej: 172.16.1.1
     * @param strRutSer Ruta destino del servidor Ej: /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/
     * @param strNewNomArc Nombre con el que se va a grabar el archivo en el servidor Ej: ZafRecHum03_c1_c2_t1_s1.doc
     * @throws TuvalFileException
     */
    public void subirArcSocket(String strRutNomArc, String strHost, String strRutSer, String strNewNomArc) throws TuvalFileException {
        File filFue = null;

        try {
            filFue = new File(strRutNomArc);
            ZafSer06Cli zafSer06Cli = new ZafSer06Cli();
            zafSer06Cli.exportar(filFue, strHost, strRutSer, strNewNomArc);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new TuvalFileException(ex.getMessage());
        }
    }

    /**
     * Permite bajar el archivo desde el servidor al cliente mediante sockets para posteriormente abrirlo con su programa asociado
     * @param strHost Ip o nombre del servidor Ej: 172.16.1.1
     * @param strRutSer Ruta donde se encuentra el archivo en el servidor Ej: /Zafiro/Digitales/RecursosHumanos/ZafRecHum03/Tuval/
     * @param strNomArc Nombre del archivo en el servidor Ej: ZafRecHum03_c1_c2_t1_s1.doc
     * @param strTmpFolder Ruta de la carpeta local donde se guardara el archivo Ej: /tmp
     * @throws TuvalFileException
     */
    public void abrirArcSocket(String strHost, String strRutSer, String strNomArc, String strTmpFolder) throws TuvalFileException{
        try{
            ZafSer06Cli zafSer06Cli = new ZafSer06Cli();
            zafSer06Cli.importar(strRutSer, strNomArc, strHost, strTmpFolder);
            abrirArc(strTmpFolder+File.separator+strNomArc);
        }catch(Exception ex){
            throw new TuvalFileException(ex.getMessage());
        }
    }

}
