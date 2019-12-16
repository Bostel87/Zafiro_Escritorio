
package Librerias.ZafValCedRuc;

/**
 *
 * @author clainez
 * @version 1.0
 * 04/04/2011
 */
public class TuvalUtilitiesException extends Exception {
    private String strErr;

    public TuvalUtilitiesException() {
        super();
        strErr = "Desconocido";
    }

    public TuvalUtilitiesException(String strErr) {
        super(strErr);
        this.strErr = strErr;
    }

    public TuvalUtilitiesException(String strErr, Throwable thrwbl) {
        super(strErr, thrwbl);
        this.strErr = strErr;
    }

    public TuvalUtilitiesException(Throwable thrwbl) {
        super(thrwbl);
    }

    public String getStrErr() {
        return strErr;
    }

}
