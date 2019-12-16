/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CxC.ZafCxC09;


import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


/**
 *
 * @author jayapata
 */
public class ZafInt extends JTextField {
    /**
     * Holds value of property maxValue.
     */
    private int maxValue;

    /**
     * Holds value of property minValue.
     */
    private int minValue;

    /**
     * Holds value of property maxLength.
     */
    private int maxLength;

    /** Crea una nueva instancia de JIntegerTextField */
    public ZafInt() {
        this(20);
    }

    public ZafInt(int length) {
        this(length, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Crea un JTextField que alojará un número entero de un tamaño fijo. No
     * se permite insertar otro caracter que no sea un entero. Además el número
     * sólo puede pertenecer a un rango de valores especificados por los
     * parámetros min y max.
     * @param length número de dígitos
     * @param min Mínimo valor que podrá tener el entero
     * @param max Míximo valor que podrá tener el entero
     */
    public ZafInt(int length, int min, int max) {
        super();
        setMaxValue(max);
        setMinValue(min);

        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(new FixedSizeFilter(this));
    }

    public static void main(String[] as) {
        javax.swing.JFrame frame = new javax.swing.JFrame("JIntegerTextField");
        frame.getContentPane().add(new ZafInt(10,1,2));
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 200, 100);
        frame.setVisible(true);
    }
  
    /**
     * Getter for property maxValue.
     * @return Value of property maxValue.
     */
    public int getMaxValue() {
        return this.maxValue;
    }

    /**
     * Setter for property maxValue.
     * @param maxValue New value of property maxValue.
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Getter for property minValue.
     * @return Value of property minValue.
     */
    public int getMinValue() {
        return this.minValue;
    }

    /**
     * Setter for property minValue.
     * @param minValue New value of property minValue.
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * Getter for property maxLen.
     * @return Value of property maxLen.
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     * Setter for property maxLen.
     * @param maxLen New value of property maxLen.
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getValue() {
        return Integer.parseInt(this.getText());
    }

    private class FixedSizeFilter extends DocumentFilter {
        ZafInt parent;

        public FixedSizeFilter(ZafInt parent) {
            this.parent = parent;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String str, AttributeSet attr) throws BadLocationException {
            String oldText = fb.getDocument()
                               .getText(0, fb.getDocument().getLength());
            replace(fb, offset, 0, str, attr);

            String text = fb.getDocument()
                            .getText(0, fb.getDocument().getLength());

            try {
                int newValue = Integer.parseInt(text);

                if ((newValue < parent.getMinValue()) ||
                        (newValue > parent.getMaxValue())) {
                    fb.getDocument().remove(0, text.length());
                    fb.getDocument().insertString(0, oldText, attr);
                }
            }
            catch (NumberFormatException nfe) {
                return;
            }
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset,
            int length, String str, AttributeSet attrs)
            throws BadLocationException {
            String oldText = fb.getDocument()
                               .getText(0, fb.getDocument().getLength());

            try {
                Integer.parseInt(str);
            }
            catch (NumberFormatException nfe) {
                return;
            }

            int newLength = fb.getDocument().getLength() - length +
                str.length();

            if (newLength <= parent.getMaxValue()) {
                fb.replace(offset, length, str, attrs);

                String text = fb.getDocument()
                                .getText(0, fb.getDocument().getLength());

                try {
                    int newValue = Integer.parseInt(text);
                    String strMin = "" + parent.getMinValue();

                    if ((newValue < parent.getMinValue()) &&
                            strMin.startsWith(text)) {
                        fb.getDocument().remove(0, text.length());
                        fb.getDocument().insertString(0, strMin, attrs);
                        parent.select(strMin.length() - 1, strMin.length());

                        return;
                    }

                    if (((parent.getMaxLength() < 0) ||
                            (newLength <= parent.getMaxLength())) &&
                            ((newValue < parent.getMinValue()) ||
                            (newValue > parent.getMaxValue()))) {
                        fb.getDocument().remove(0, text.length());
                        fb.getDocument().insertString(0, oldText, attrs);
                    }
                }
                catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    return;
                }
            }
        }
    }
}

