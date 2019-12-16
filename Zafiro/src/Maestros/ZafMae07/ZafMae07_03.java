/**
 * ZafMae07_03
 * 
 */
package Maestros.ZafMae07;
import Librerias.ZafValCedRuc.TuvalUtilitiesException;
/**
 *
 * @author Modificated Angie Bajaña
 * Modificated on 26 de Mayo de 2015, 13:49
 * v0.1
===========================================================================================================================================================================================*/
public class ZafMae07_03 
{
    /*variables*/
    public  String strId;                                                    //  Variable que almacena el dato ingresado en tx_ide
    private String strCed;                                                   //  Variable que almacena CEDULA
    private String strRuc;                                                   //  Variable que almacena RUC
    private String strDigVer;                                                //  Variable que almacena digito verificador
    private final int INT_NUMPROECU = 24;                                    //  Asigna la cantidad de PROVINCIAS de ecuador a la variable
    private final int INT_NUMDIGCED = 10;                                    //  Quema la cantidad de digitos de una CEDULA a la variable
    private final int INT_NUMDIGRUC = 13;                                    //  Quema la cantidad de digitos de un RUC a la variable
/*==========================================================================================================================================================================================*/    
    /*=====     verificar que tipo de ID es     =====*/
    public boolean ValidacionId(String strId) throws TuvalUtilitiesException
    {
        boolean blnOk=false;
        
        if(strId.length()==INT_NUMDIGCED)                                       // VERIFICA SI SU CANTIDAD DE DIGITOS CORRESPONDE A UNA CEDULA
        {
            strCed=strId;
            blnOk = ( validarCed(strCed));
        }
        else
        {                                                                       // VERIFICA SI SU CANTIDAD DE DIGITOS CORRESPONDE A UN RUC 
            if(strId.length()==INT_NUMDIGRUC)
            {
                int intTerDig = Integer.parseInt(strId.substring(2, 3));
                
                if(intTerDig<6)                                                 // VERIFICA SI EL 3er DIGITO  ES "<6" --------->> ENVIA A VALIDAR PERSONA NATURAL
                {
                    strRuc=strId;
                    blnOk=validarRucPerNat(strId);
                }
                else
                {
                    if(intTerDig==6)                                            // VERIFICA SI EL 3er DIGITO  ES "=6" --------->> ENVIA A VALIDAR PERSONA PUBLICA
                    {
                        strRuc=strId;
                        blnOk=validarRucInsPub(strId);
                    }
                    else
                    {
                        if(intTerDig==9)                                        // VERIFICA SI EL 3er DIGITO  ES "=9" --------->> ENVIA A VALIDAR PERSONA JURIDICAS Y EXTRANJERAS SIN CEDULAS
                        {
                            strRuc=strId;
                            blnOk=validarRucJurExtSinCed(strId);
                        }
                        else
                        {
                            blnOk= false;
                            throw new TuvalUtilitiesException("Número de identificación incorrecto");
                        }
                    }
                }
            }
            else
            {
                throw new TuvalUtilitiesException("Longitud de número de cédula incorrecto");
            }
        }

        return blnOk;
    }
   
/*=======================================================================================================================================================================================================================*/  
    // ===========================    VERIFICAR QUE EL VALOR INGRESADO EN EL CAMPO IDENTIFICACION  SEA UN NUMERO DE CEDULA VALIDO  ======================================
 
    /**
     * Algoritmo "Módulo 10" valida cédula de identidad de una Persona según dígito verificador.
     *
     * @param strCed Cédula de Identidad
     * @return Retorna true si es correcto o false si no lo es
     * @throws TuvalUtilitiesException
     */
    public boolean validarCed(String strCed) throws TuvalUtilitiesException 
    {
         boolean blnOk = (strCed.length()==INT_NUMDIGCED);

         if(blnOk)
         {
                 blnOk = (Integer.parseInt(strCed.substring(0, 2)) > 0
                         && Integer.parseInt(strCed.substring(0, 2)) <= INT_NUMPROECU
                          && Integer.parseInt(strCed.substring(2, 3)) < 6);

                 if (blnOk) 
                 {
                         int intSum = 0;
                         int[] intCoe = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

                         for (int i = 0; i <= 8; i++)
                         {
                              int intTot = Integer.parseInt(strCed.substring(i, i + 1)) * intCoe[i];

                              if (intTot > 9)
                              {
                                  intTot -= 9;
                              }

                              intSum += intTot;
                         }

                         int intRes = intSum % 10;
                         int intDigVer = intRes>0?(10-intRes):0;

                         blnOk = (intDigVer == Integer.parseInt(strCed.substring(9, 10)));

                         if (!blnOk)
                         {
                            throw new TuvalUtilitiesException("Número de cédula incorrecto");
                         }
                 }
                 else
                 {
                    throw new TuvalUtilitiesException("Número de cédula incorrecto");
                 }
         }
         else
         {
            throw new TuvalUtilitiesException("Longitud de cédula de identificación incorrecto");
         }

         return blnOk;
    }
    
    /*SI EL NUMERO ES VALIDO RETORNARA VERDADERO Y SE PODRA CONTINUAR CON LA INSERCION O MODIFICACION DEL CLIENTE O EL PROVEEDOR */
/*============================================================================================================================================================================================================================*/     
    
    /* VALIDACION DE UN RUC CUYO 3er DIGITO ES UN NUMERO <6 */
    private boolean validarRucPerNat(String strRuc) throws TuvalUtilitiesException 
    {
         boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);

         if(blnOk)
         {
            try
            {
               blnOk = (strRuc.substring(0, 10).length()==INT_NUMDIGCED);
               
               //Si strRuc.substring(0, 2) = 30, es un valor valido ya que 30 es un "Numero reservado para ecuatorianos registrados en el exterior".
               //Caso de RUC 3050382427001 de "ZAMBRANO SORNOZA ALEJANDRO ANDRE" el cual es un RUC valido comprobado en pagina web del SRI.
               blnOk = (Integer.parseInt(strRuc.substring(0, 2)) > 0
                       && (Integer.parseInt(strRuc.substring(0, 2)) <= INT_NUMPROECU || Integer.parseInt(strRuc.substring(0, 2)) == 30)
                       && Integer.parseInt(strRuc.substring(2, 3)) < 6);

               if (blnOk)
               {
                  int intSum = 0;
                  int[] intCoe = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

                  for (int i = 0; i <= 8; i++)
                  {
                     int intTot = Integer.parseInt(strRuc.substring(i, i + 1)) * intCoe[i];

                     if (intTot > 9)
                     {
                        intTot -= 9;
                     }

                     intSum += intTot;
                  }
                   System.out.println(intSum);
                  int intRes = intSum % 10;
                   System.out.println(intRes);
                  int intDigVer = intRes>0?(10-intRes):0;

                  blnOk = (intDigVer == Integer.parseInt(strRuc.substring(9, 10)));
                   System.out.println(strRuc.substring(9, 10));

                  if (!blnOk)
                  {
                     throw new TuvalUtilitiesException("Número de Ruc incorrecto");
                  }
               }
               else                                
               {
                  throw new TuvalUtilitiesException("Número de Ruc incorrecto");
               }

               // VALIDAR QUE LOS ULTIMOS 3 DGITOS DEL NUMERO DE RUC SEAN 001 

               if(!(blnOk && (Integer.parseInt(strRuc.substring(10, 13)) == 001 )) )
               {
                  throw new TuvalUtilitiesException("RUC incorrecto");
               }
            } 
            catch (TuvalUtilitiesException ex)
            {
               throw new TuvalUtilitiesException(ex.getMessage());
            }
         }
         else
         {
            throw new TuvalUtilitiesException("Longitud del RUC incorrecto");
         }

         return blnOk;
    }
    
/*=========================================================================================================================================================================================================================================*/    
     /**                VALIDAR QUE EL NUMERO DE RUC DE UNA PERSONA JURIDICA Y EXTRANJERA EXISTA
     *                                       VALIDA CON CEDULA -----> VALIDA POR EL MODULO 11                *
     * ------------------------------------------------------------------------------------------------------*
     * Algoritmo "Módulo 11" valida RUC de Personas Jurídicas y Extranjeras sin Cédula por dígito verificador
     *
     * @param strRuc RUC de Persona Jurídica y Extranjera
     * @return Retorna true si es correcto y false si no lo es
     * @throws TuvalUtilitiesException
     */
    private boolean validarRucJurExtSinCed(String strRuc) throws TuvalUtilitiesException 
    {
         boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);

         if(blnOk)
         {
            blnOk = (Integer.parseInt(strRuc.substring(0, 2)) > 0
                    && Integer.parseInt(strRuc.substring(0, 2)) <= INT_NUMPROECU
                    && Integer.parseInt(strRuc.substring(2, 3)) == 9
                    && Integer.parseInt(strRuc.substring(10, 13)) == 001);    // validacion 001 

            // && Integer.parseInt(strRuc.substring(10, 13)) > 0);

            if(blnOk)
            {
               int[] intCoe = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
               int intSum = 0;

               for (int i = 0; i <= 8; i++)
               {
                  intSum += Integer.parseInt(strRuc.substring(i, i + 1)) * intCoe[i];
               }

               int intRes = intSum % 11;
               int intDigVer = intRes>0?(11-intRes):0;

               blnOk = (intDigVer == Integer.parseInt(strRuc.substring(9, 10)));

               if (!blnOk)
               {
                  throw new TuvalUtilitiesException("RUC incorrecto");
               }
            }
            else
            {
               throw new TuvalUtilitiesException("RUC incorrecto");
            }
         }
         else
         {
            throw new TuvalUtilitiesException("Longitud del RUC incorrecto");
         }

         return blnOk;
    }
    
/*===============================================================================================================================================================================================================================================*/    
     /**                VALIDAR QUE EL NUMERO DE RUC DE UNA PERSONA JURIDICA Y EXTRANJERA EXISTA
     *                                       VALIDA CON CEDULA -----> VALIDA POR EL MODULO 11                *
     * ------------------------------------------------------------------------------------------------------*
     * Algoritmo "Módulo 11" valida RUC de Instituciones Públicas según dígito verificador
     *
     * @param strRuc RUC de Instituciones Públicas
     * @return Retorna true si es correcto y false si no lo es
     * @throws Exception
     */
    private boolean validarRucInsPub(String strRuc) throws TuvalUtilitiesException 
    {
         boolean blnOk = (strRuc.length()==INT_NUMDIGRUC);
         int intUlt4Dig;
         
         intUlt4Dig = Integer.parseInt(strRuc.substring(9, 13));

         if ( blnOk && !(intUlt4Dig == 1001 || intUlt4Dig == 3001) )
         {
            blnOk = (Integer.parseInt(strRuc.substring(0, 2)) > 0
                    && Integer.parseInt(strRuc.substring(0, 2)) <= INT_NUMPROECU
                    && Integer.parseInt(strRuc.substring(2, 3)) == 6
                    && Integer.parseInt(strRuc.substring(9, 13)) == 0001 );   // validacion 0001

                    //&& Integer.parseInt(strRuc.substring(9, 13)) > 0);      // debe terminar en 0001 un ruc de persona publica 

            if (blnOk) 
            {
               int[] intCoe = { 3, 2, 7, 6, 5, 4, 3, 2 };
               int intSum = 0;

               for (int i = 0; i <= 7; i++)
               {
                  intSum += Integer.parseInt(strRuc.substring(i, i + 1)) * intCoe[i];
               }
           
               int intRes = intSum % 11;
                
               int intDigVer = intRes>0?(11-intRes):0;

               blnOk = (intDigVer == Integer.parseInt(strRuc.substring(8, 9)));
                

               if(!blnOk)
               {
                  throw new TuvalUtilitiesException("RUC incorrecto");
               }
            } 
            else
            {
               throw new TuvalUtilitiesException("RUC incorrecto");
            }
         }
         else if (intUlt4Dig == 1001 || intUlt4Dig == 3001)
         {  //Caso de RUC 0960805711001 de "DIAZ DIAZ HECTOR JULIO" el cual es un RUC valido comprobado en pagina web del SRI.
            //Caso de RUC 0960905123001 de "LUCIO LOPEZ YEISON PAUL" el cual es un RUC valido comprobado en pagina web del SRI.
            blnOk = true;
         }
         else
         {
            throw new TuvalUtilitiesException("Longitud del RUC incorrecto");
         }

         return blnOk;
    }
 
/*==================================================================================================================================================================================================================================================*/    
}