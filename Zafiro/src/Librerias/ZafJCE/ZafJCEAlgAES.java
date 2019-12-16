/*
 * ZafJCEAlgAES.java
 *
 * Created on 09 de diciembre de 2009, 11:09 AM
 * v0.1
 */

package Librerias.ZafJCE;

/**
 *
 * @author Eddye Lino
 */
public class ZafJCEAlgAES
{

    /**
     * Crea una nueva instancia de la clase ZafJCEAlgAES.
     */
    public ZafJCEAlgAES()
    {
        
    }

    /**
     * Esta función obtiene el texto desencriptado correspondiente al texto encriptado especificado.
     * Las cadenas de texto recibidas deben estar en el sistema hexadecimal.
     * @param strClaHex La cadena hexadecimal que contiene la clave que se se utilizará para desencriptar el texto encriptado.
     * @param strVecIniHex La cadena hexadecimal que contiene el vector de inicialización utilizado para encriptar el texto.
     * @param strTexEncHex La cadena hexadecimal que contiene el texto encriptado que se quiere desencriptar.
     * @return El texto desencriptado correspondiente al texto encriptado especificado.
     * <BR>Nota.- Si hay un error al desencriptar la función devolverá <I>null</I>.
     */
    public String desencriptar(String strClaHex, String strVecIniHex, String strTexEncHex)
    {
        byte[] bytClaPub;
        byte[] bytTexSinEnc;
        byte[] bytTexEnc;
        String strRes=null;
        try
        {
            //Obtener la clave pública (convertir de Hexadecimal a byte[]).
            bytClaPub=getArrBytCadHex(strClaHex);
            //Crear el objeto SecretKeySpec que contendrá la clave que se utilizará para encriptar el texto.
            javax.crypto.spec.SecretKeySpec objSecKeySpe=new javax.crypto.spec.SecretKeySpec(bytClaPub, "AES");
            //Crear el objeto Cipher (AES=Algoritmo, CBC=Model, PKCS5Padding=Padding).
            javax.crypto.Cipher objCip=javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
            objCip.init(javax.crypto.Cipher.DECRYPT_MODE, objSecKeySpe, new javax.crypto.spec.IvParameterSpec(getArrBytCadHex(strVecIniHex)));
            //Obtener el texto encriptado (convertir de Hexadecimal a byte[]).
            bytTexEnc=getArrBytCadHex(strTexEncHex);
            bytTexSinEnc=objCip.doFinal(bytTexEnc);
            strRes=new String(bytTexSinEnc);
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
            strRes=null;
        }
        catch (javax.crypto.NoSuchPaddingException e)
        {
            strRes=null;
        }
        catch (java.security.InvalidKeyException e)
        {
            strRes=null;
        }
        catch (javax.crypto.IllegalBlockSizeException e)
        {
            strRes=null;
        }
        catch (javax.crypto.BadPaddingException e)
        {
            strRes=null;
        }
        catch (java.security.InvalidAlgorithmParameterException e)
        {
            strRes=null;
        }
        catch (Exception e)
        {
            strRes="";
        }
        return strRes;
    }

    /**
     * Esta función obtiene la cadena hexadecimal que representa al arreglo de bytes especificado.
     * @param bytDat El arreglo de bytes que contiene los datos que se desean convertir a una cadena hexadecimal.
     * @return La cadena hexadecimal que representa al arreglo de bytes especificado.
     * <BR>Nota.- Si hay un error al obtener la cadena hexadecimal la función devolverá una cadena vacía.
     */
    private String getCadHexArrByt(byte[] bytDat)
    {
        String strRes="";
        try
        {
            //Crear el objeto BigInteger sin signo(-1=negativo, 0=Zero, 1=Positivo).
            java.math.BigInteger objBigInt=new java.math.BigInteger(1, bytDat);
            //Formatear el BigInteger a hexadecimal.
            strRes=objBigInt.toString(16);
        }
        catch (NumberFormatException e)
        {
            strRes="";
        }
        catch (Exception e)
        {
            strRes="";
        }
        return strRes.toUpperCase();
    }

    /**
     * Esta función obtiene el arreglo de bytes que representa a la cadena hexadecimal especificada.
     * @param strCadHex La cadena hexadecimal que contiene los datos que se desean convertir a un arreglo de bytes.
     * @return El arreglo de bytes que representa a la cadena hexadecimal especificada.
     * <BR>Nota.- Si hay un error al obtener arreglo de bytes la función devolverá <I>null</I>.
     */
    private byte[] getArrBytCadHex(String strCadHex)
    {
        byte[] bytRes;
        try
        {
            //Para pasar una cadena Hexadecimal a un arreglo de bytes se utiliza el método "BigInteger.toByteArray()". Pero
            //debido a que dicho método agrega un bit para el signo hay que validar que el tamaño del arreglo sea el adecuado.
            //Por ejemplo: había ocasiones donde la cadena recibida representaba un arreglo de 16 bits pero al utilizar el método
            //"toByteArray()" se obtenía un arreglo de 17 bits.
            //Solución 1: NO funciona sólo hacer esto.
            //bytRes=new java.math.BigInteger(strCadHex, 16).toByteArray(); //16=Hexadecimal.
            //Solución 2: SI funciona.
            java.math.BigInteger objBigInt=new java.math.BigInteger(strCadHex, 16); //16=Hexadecimal.
            byte[] bytAux=objBigInt.toByteArray();
            if ((bytAux.length%2)==0)
            {
                bytRes=new byte[bytAux.length];
                bytRes=bytAux;
            }
            else
            {
                bytRes=new byte[bytAux.length-1];
                //Copio el arreglo auxiliar al arreglo que se va a devolver desde la posición 1 ya que la posición 0 contiene el signo.
                System.arraycopy(bytAux, 1, bytRes, 0, bytAux.length-1);
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            bytRes=null;
        }
        catch (ArrayStoreException e)
        {
            bytRes=null;
        }
        catch (NullPointerException e)
        {
            bytRes=null;
        }
        catch (Exception e)
        {
            bytRes=null;
        }
        return bytRes;
    }

}
