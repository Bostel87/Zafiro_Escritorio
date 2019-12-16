
package Librerias.ZafUtil;

/**
 *
 * @author clainez
 * @version 1.0
 * 24/05/2011
 */
public class TuvalFileException extends Exception {
    private String strErr;

    public TuvalFileException() {
        super();
        strErr = "Desconocido";
    }

    public TuvalFileException(String strErr) {
        super(strErr);
        this.strErr = strErr;
    }

    public TuvalFileException(String strErr, Throwable thrwbl) {
        super(strErr, thrwbl);
        this.strErr = strErr;
    }

    public TuvalFileException(Throwable thrwbl) {
        super(thrwbl);
    }

    public String getStrErr() {
        return strErr;
    }

}
