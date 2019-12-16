/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sistemas4
 */
public class LogServicios {
    
    public void escribe(String dato){
    
         Calendar fecha=Calendar.getInstance();
        int Hora = fecha.get(Calendar.HOUR_OF_DAY);
        int Min = fecha.get(Calendar.MINUTE);
        int Sec = fecha.get(Calendar.SECOND);
        
        Date m=new Date();
        
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yy");
        String fus=s.format(m);
        
        String su=String.valueOf(Hora)+":"+String.valueOf(Min)+":"+String.valueOf(Sec);
      
        File f=new File(fus+"-"+"envios.log");
        try {
            FileWriter fw=new FileWriter(f,true);
            
            fw.write(fus+"-"+su+"-"+dato+"\n");
            fw.append("\r\n");
 
            BufferedWriter bw=new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            System.out.println(salida);
            salida.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
        
        
    }//fin escribe
    
  
}
