/*
 * ZafTblPopMnu.java
 *
 * Created on 24 de julio de 2005, 12:35 AM
 * v0.5
 */

package  Contabilidad.ZafCon47;

import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent;
import javax.swing.event.EventListenerList;

/**
 * Esta clase crea un menú de contexto para un JTable. Este menú permite realizar
 * muchas operaciones como por ejemplo: copiar, seleccionar fila, insertar filas,
 * indicar el tipo de selección, etc.
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblPopMnu" es la instancia de "ZafTblPopMnu".
 * <PRE>
 *            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
 *               public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
 *                   if (objTblPopMnu.isClickInsertarFila())
 *                   {
 *                       System.out.println("beforeClick: isClickInsertarFila");
 *                       //Cancelar la edición cuado sea necesario.
 *                       if (tblDat.getSelectedRow()==1)
 *                           objTblPopMnu.cancelarClick();
 *                   }
 *                   else if (objTblPopMnu.isClickEliminarFila())
 *                   {
 *                       System.out.println("beforeClick: isClickEliminarFila");
 *                   }
 *               }
 *               public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
 *                   if (objTblPopMnu.isClickInsertarFila())
 *                   {
 *                       //Escriba aquí el código que se debe realizar luego de insertar la fila.
 *                       System.out.println("afterClick: isClickInsertarFila");
 *                   }
 *                   else if (objTblPopMnu.isClickEliminarFila())
 *                   {
 *                       System.out.println("afterClick: isClickEliminarFila");
 *                       javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
 *                   }
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * <BR>Esta clase sólo hace uso de los métodos "beforeClick" y "afterClick".
 * @author  Eddye Lino
 */
public class ZafTblPopMnuFluPer extends javax.swing.JPopupMenu
{
    //Constantes:
    private static final int INT_BEF_CLI=0;             /**Antes de editar: Indica "Before click".*/
    private static final int INT_AFT_CLI=1;             /**Después de editar: Indica "After click".*/
    
    private static final int INT_MNU_COR=0;             /**Menú en el que se dió click: Indica "Cortar".*/
    private static final int INT_MNU_COP=1;             /**Menú en el que se dió click: Indica "Copiar".*/
    private static final int INT_MNU_PEG=2;             /**Menú en el que se dió click: Indica "Pegar".*/
   
    private static final int INT_MNU_MOD_FIL=3;         /**Menú en el que se dió click: Indica "Seleccionar fila".*/
    
    
    private static final int INT_MNU_SEL_FIL=4;         /**Menú en el que se dió click: Indica "Seleccionar fila".*/
    private static final int INT_MNU_SEL_COL=5;         /**Menú en el que se dió click: Indica "Seleccionar columna".*/
    private static final int INT_MNU_SEL_TOD=6;         /**Menú en el que se dió click: Indica "Seleccionar todo".*/
    private static final int INT_MNU_INS_FIL=7;         /**Menú en el que se dió click: Indica "Insertar fila".*/
    private static final int INT_MNU_INS_VAR_FIL=8;     /**Menú en el que se dió click: Indica "Insertar filas".*/
    private static final int INT_MNU_ELI_FIL=9;         /**Menú en el que se dió click: Indica "Eliminar fila(s)".*/
    private static final int INT_MNU_BOR_CON=10;         /**Menú en el que se dió click: Indica "Borrar contenido".*/
    private static final int INT_MNU_OCU_COL=11;        /**Menú en el que se dió click: Indica "Ocultar columna".*/
    private static final int INT_MNU_MCO_TOD_COL=12;    /**Menú en el que se dió click: Indica "Mostrar columna: Todas las columnas".*/
    private static final int INT_MNU_AJU_ANC_COL=13;    /**Menú en el que se dió click: Indica "Ajustar ancho de columnas automáticamente".*/
    private static final int INT_MNU_TSE_CEL=14;        /**Menú en el que se dió click: Indica "Tipo de selección: Selección por celda".*/
    private static final int INT_MNU_TSE_FIL=15;        /**Menú en el que se dió click: Indica "Tipo de selección: Selección por fila".*/
    private static final int INT_MNU_TSE_COL=16;        /**Menú en el que se dió click: Indica "Tipo de selección: Selección por columna".*/
    
    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    protected EventListenerList objEveLisLis=new EventListenerList();
    private javax.swing.JTable tblDat;
    private ZafTblMod objTblMod;
    private javax.swing.JMenuItem mnuCor, mnuCop, mnuPeg;
    private javax.swing.JMenuItem mnuModFil, mnuSelFil, mnuSelCol, mnuSelTod;
    private javax.swing.JMenuItem mnuInsFil, mnuInsVarFil, mnuEliFil, mnuBorCon;
    private javax.swing.JMenuItem mnuOcuCol;
    private javax.swing.JMenu mnuMosCol;
    private javax.swing.JMenuItem mnuMosTodCol;
    private javax.swing.JMenuItem mnuAjuAncCol;
    private javax.swing.JMenu mnuTipSel;
    private javax.swing.JMenuItem mnuTipSelCel, mnuTipSelFil, mnuTipSelCol;
    private int intOpcSelMnu;
    private boolean blnCanCli;                  //Determina si se debe cancelar el click.
    
    /** Crea una nueva instancia de la clase ZafTblPopMnu. */
    public ZafTblPopMnuFluPer(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        blnCanCli=false;
        objTblMod=(ZafTblMod)tblDat.getModel();
        //Crear los menúes.
        mnuCor=new javax.swing.JMenuItem("Cortar");
        mnuCor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnuCor.setEnabled(false);
        mnuCop=new javax.swing.JMenuItem("Copiar");
        mnuCop.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuPeg=new javax.swing.JMenuItem("Pegar");
        mnuPeg.setEnabled(false);
        mnuPeg.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        
        mnuModFil=new javax.swing.JMenuItem("Modificar fila");
                
        mnuSelFil=new javax.swing.JMenuItem("Seleccionar fila");
        mnuSelCol=new javax.swing.JMenuItem("Seleccionar columna");
        mnuSelTod=new javax.swing.JMenuItem("Seleccionar todo");
        mnuInsFil=new javax.swing.JMenuItem("Insertar fila");
        mnuInsVarFil=new javax.swing.JMenuItem("Insertar filas...");
        mnuEliFil=new javax.swing.JMenuItem("Eliminar fila(s)");
        mnuBorCon=new javax.swing.JMenuItem("Borrar contenido");
        mnuBorCon.setEnabled(false);
        mnuOcuCol=new javax.swing.JMenuItem("Ocultar columna");
        mnuMosCol=new javax.swing.JMenu("Mostrar columna");
        mnuMosTodCol=new javax.swing.JMenuItem("Todas las columnas");
        mnuAjuAncCol=new javax.swing.JMenuItem("Ajustar ancho de columnas automáticamente");
        mnuTipSel=new javax.swing.JMenu("Tipo de selección");
        mnuTipSelCel=new javax.swing.JMenuItem("Selección por celda");
        mnuTipSelFil=new javax.swing.JMenuItem("Selección por fila");
        mnuTipSelCol=new javax.swing.JMenuItem("Selección por columna");
        //Adicionar los menúes.
        this.add(mnuCor);
        this.add(mnuCop);
        this.add(mnuPeg);
        this.addSeparator();
        this.add(mnuModFil);
        this.add(mnuSelFil);
        this.add(mnuSelCol);
        this.add(mnuSelTod);
        this.addSeparator();
        this.add(mnuInsFil);
        this.add(mnuInsVarFil);
        this.add(mnuEliFil);
        this.add(mnuBorCon);
        this.addSeparator();
        this.add(mnuOcuCol);
        mnuMosCol.add(mnuMosTodCol);
        mnuMosCol.addSeparator();
        this.add(mnuMosCol);
        this.addSeparator();
        this.add(mnuAjuAncCol);
        this.addSeparator();
        mnuTipSel.add(mnuTipSelCel);
        mnuTipSel.add(mnuTipSelFil);
        mnuTipSel.add(mnuTipSelCol);
        this.add(mnuTipSel);
        //Adicionar los listener.
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseReleased(java.awt.event.MouseEvent evt) {
//                tblDatMouseReleased(evt);
//            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseReleased(evt);
            }
        });
        mnuCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCorActionPerformed(evt);
            }
        });
        mnuCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCopActionPerformed(evt);
            }
        });
        mnuPeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPegActionPerformed(evt);
            }
        });
       
        
         mnuModFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuModFilActionPerformed(evt);
            }
        });
        
        mnuSelFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelFilActionPerformed(evt);
            }
        });
        mnuSelCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelColActionPerformed(evt);
            }
        });
        mnuSelTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelTodActionPerformed(evt);
            }
        });
        mnuInsFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsFilActionPerformed(evt);
            }
        });
        mnuInsVarFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsVarFilActionPerformed(evt);
            }
        });
        mnuEliFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEliFilActionPerformed(evt);
            }
        });
        mnuBorCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBorConActionPerformed(evt);
            }
        });
        mnuOcuCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOcuColActionPerformed(evt);
            }
        });
        mnuMosTodCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMosTodColActionPerformed(evt);
            }
        });
        mnuAjuAncCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAjuAncColActionPerformed(evt);
            }
        });
        mnuTipSelCel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTipSelCelActionPerformed(evt);
            }
        });
        mnuTipSelFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTipSelFilActionPerformed(evt);
            }
        });
        mnuTipSelCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTipSelColActionPerformed(evt);
            }
        });
    }
    
    /**
     * Esta función muestra el JPopupMenu.
     */
    private void tblDatMouseReleased(java.awt.event.MouseEvent evt)
    {
//        if (evt.isPopupTrigger())
//            this.show(evt.getComponent(),evt.getX(),evt.getY());
        if(evt.getButton()>=2)
            this.show(evt.getComponent(),evt.getX(),evt.getY());
    }
    
    /**
     * Esta función corta las celdas seleccionadas al portapapeles.
     */
    private void mnuCorActionPerformed(java.awt.event.ActionEvent evt)
    {
        int intFilSel[], intColSel[], i, j;
        Object objAux;
        try
        {
            intOpcSelMnu=INT_MNU_COR;
            //Permitir de manera predeterminada el click.
            blnCanCli=false;
            //Generar evento "beforeClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
            //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
            if (!blnCanCli)
            {
                StringBuffer stb=new StringBuffer();
                intFilSel=tblDat.getSelectedRows();
                intColSel=tblDat.getSelectedColumns();
                //Obtener el detalle que se va a enviar al Portapapeles,
                for (i=0; i<intFilSel.length; i++)
                {
                    for (j=0; j<intColSel.length; j++)
                    {
                        objAux=objTblMod.getValueAt(intFilSel[i], intColSel[j]);
                        //Adicionar "TAB" y "Enter" a la cadena que se va a pasar al Portapapeles.
                        if (j<intColSel.length-1)
                            stb.append((objAux==null?"":objAux.toString()) + "\t");
                        else
                            stb.append((objAux==null?"":objAux.toString()) + "\n");
                        objTblMod.setValueAt(null, intFilSel[i], intColSel[j]);
                    }
                }
                //Pasar cadena al Portapapeles.
                getToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(stb.toString()), null);
                //Liberar recursos.
                intFilSel=null;
                intColSel=null;
                stb=null;
                objAux=null;
                //Generar evento "afterClick()".
                fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
            }
        }
        catch (java.awt.HeadlessException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (IllegalStateException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (Exception e)
        {
            System.out.println("Excepción: " + e.toString());
        }
    }
    
    /**
     * Esta función copia las celdas seleccionadas al portapapeles.
     */
    private void mnuCopActionPerformed(java.awt.event.ActionEvent evt)
    {
        int intFilSel[], intColSel[], i, j;
        Object objAux;
        try
        {
            intOpcSelMnu=INT_MNU_COP;
            //Permitir de manera predeterminada el click.
            blnCanCli=false;
            //Generar evento "beforeClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
            //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
            if (!blnCanCli)
            {
                StringBuffer stb=new StringBuffer();
                seleccionarCel();
                intFilSel=tblDat.getSelectedRows();
                intColSel=tblDat.getSelectedColumns();
                //Preguntar si también se desea copiar la cabecera del JTable al Portapapeles.
                if (javax.swing.JOptionPane.showConfirmDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat),"¿Desea copiar también la cabecera de la tabla?","Mensaje del sistema Zafiro",javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                {
                    //Obtener la cabecera que se va a enviar al Portapapeles,
                    for (j=0; j<intColSel.length; j++)
                    {
                        //Adicionar "TAB" y "Enter" a la cadena que se va a pasar al Portapapeles.
                        if (j<intColSel.length-1)
                            stb.append(objTblMod.getColumnName(intColSel[j]) + "\t");
                        else
                            stb.append(objTblMod.getColumnName(intColSel[j]) + "\n");
                    }
                }
                //Obtener el detalle que se va a enviar al Portapapeles,
                for (i=0; i<intFilSel.length; i++)
                {
                    for (j=0; j<intColSel.length; j++)
                    {
                        objAux=objTblMod.getValueAt(intFilSel[i], intColSel[j]);
                        //Adicionar "TAB" y "Enter" a la cadena que se va a pasar al Portapapeles.
                        if (j<intColSel.length-1)
                            stb.append((objAux==null?"":objAux.toString()) + "\t");
                        else
                            stb.append((objAux==null?"":objAux.toString()) + "\n");
                    }
                }
                //Pasar cadena al Portapapeles.
                getToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(stb.toString()), null);
                //Liberar recursos.
                intFilSel=null;
                intColSel=null;
                stb=null;
                objAux=null;
                //Generar evento "afterClick()".
                fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
            }
        }
        catch (java.awt.HeadlessException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (IllegalStateException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (Exception e)
        {
            System.out.println("Excepción: " + e.toString());
        }
    }
    
    /**
     * Esta función pega el contenido del portapapeles al JTable.
     */
    private void mnuPegActionPerformed(java.awt.event.ActionEvent evt)
    {
        String strDat, strFil[];
        int intFilSel, intColSel, i, j;
        java.util.StringTokenizer stkDat;
        try
        {
            intOpcSelMnu=INT_MNU_PEG;
            //Permitir de manera predeterminada el click.
            blnCanCli=false;
            //Generar evento "beforeClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
            //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
            if (!blnCanCli)
            {
                intFilSel=tblDat.getSelectedRow();
                intColSel=tblDat.getSelectedColumn();
                java.awt.datatransfer.Transferable objTra=getToolkit().getSystemClipboard().getContents(null);
                if (objTra!=null && objTra.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor))
                {
                    strDat=(String)objTra.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
                    stkDat=new java.util.StringTokenizer(strDat,"\n",false);
                    for (i=0; stkDat.hasMoreTokens(); i++)
                    {
                        strFil=stkDat.nextToken().split("\t");
                        for (j=0; j<strFil.length; j++)
                        {
                            objTblMod.setValueAt(strFil[j], i + intFilSel, j + intColSel);
                        }
                    }
                }
                //Liberar recursos.
                strDat=null;
                strFil=null;
                stkDat=null;
                //Generar evento "afterClick()".
                fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
            }
        }
        catch (java.awt.HeadlessException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (IllegalStateException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (java.io.IOException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (java.awt.datatransfer.UnsupportedFlavorException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (Exception e)
        {
            System.out.println("Excepción: " + e.toString());
        }
    }
    
    /**
     * Esta función selecciona la fila(s) actual(es).
     */
    private void mnuSelFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_SEL_FIL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount()-1);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    
    
    
    
       private void mnuModFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_MOD_FIL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount()-1);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    
    
    
    
    /**
     * Esta función selecciona la columna(s) actual(es).
     */
    private void mnuSelColActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_SEL_COL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.setRowSelectionInterval(0, tblDat.getRowCount()-1);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función selecciona todas las celdas de la tabla.
     */
    private void mnuSelTodActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_SEL_TOD;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.selectAll();
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función inserta una fila en blanco en la posición seleccionada.
     */
    private void mnuInsFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        int intFilSel;
        intOpcSelMnu=INT_MNU_INS_FIL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            intFilSel=tblDat.getSelectedRow();
            objTblMod.insertRow(intFilSel);
            //Seleccionar la fila seleccionada.
            tblDat.setRowSelectionInterval(intFilSel, intFilSel);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función inserta varias filas en blanco en la posición seleccionada.
     */
    private void mnuInsVarFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        int intFilSel, intNumFilIns;
        intOpcSelMnu=INT_MNU_INS_VAR_FIL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            intFilSel=((intFilSel=tblDat.getSelectedRow())==-1?0:intFilSel);
            try
            {
                intNumFilIns=Integer.parseInt(javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el número de filas a insertar:", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e)
            {
                intNumFilIns=0;
            }
            objTblMod.insertRows(intNumFilIns, intFilSel);
            //Seleccionar la fila seleccionada.
            tblDat.setRowSelectionInterval(intFilSel, intFilSel);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función elimina las filas seleccionadas.
     */
    private void mnuEliFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        int i, intFilSel[];
        intOpcSelMnu=INT_MNU_ELI_FIL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            intFilSel=tblDat.getSelectedRows();
            for (i=0; i<intFilSel.length; i++)
                objTblMod.removeRow(intFilSel[i]-i);
            intFilSel=null;
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función borra el contenido de las celdas seleccionadas.
     */
    private void mnuBorConActionPerformed(java.awt.event.ActionEvent evt)
    {
        int intFilSel[], intColSel[], i, j;
        intOpcSelMnu=INT_MNU_BOR_CON;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            intFilSel=tblDat.getSelectedRows();
            intColSel=tblDat.getSelectedColumns();
            for (i=0; i<intFilSel.length; i++)
            {
                for (j=0; j<intColSel.length; j++)
                {
                    objTblMod.setValueAt(null, intFilSel[i], intColSel[j]);
                }
            }
            intFilSel=null;
            intColSel=null;
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función oculta la columna seleccionada.
     */
    private void mnuOcuColActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_OCU_COL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            System.out.println("Ocultar columna");
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función muestra todas las columnas ocultas.
     */
    private void mnuMosTodColActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_MCO_TOD_COL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            System.out.println("Mostrar todas las columnas");
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función ajusta el ancho de todas las columnas automáticamente.
     */
    private void mnuAjuAncColActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_AJU_ANC_COL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            System.out.println("Ajustar ancho de columnas automáticamente");
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función establece que el tipo de selección sea por celdas.
     */
    private void mnuTipSelCelActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_TSE_CEL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.setCellSelectionEnabled(true);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función establece que el tipo de selección sea por filas.
     */
    private void mnuTipSelFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_TSE_FIL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.setRowSelectionAllowed(true);
            tblDat.setColumnSelectionAllowed(false);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }

    /**
     * Esta función establece que el tipo de selección sea por columnas.
     */
    private void mnuTipSelColActionPerformed(java.awt.event.ActionEvent evt)
    {
        intOpcSelMnu=INT_MNU_TSE_COL;
        //Permitir de manera predeterminada el click.
        blnCanCli=false;
        //Generar evento "beforeClick()".
        fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_BEF_CLI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (!blnCanCli)
        {
            tblDat.setRowSelectionAllowed(false);
            tblDat.setColumnSelectionAllowed(true);
            //Generar evento "afterClick()".
            fireTblPopMnuListener(new ZafTblPopMnuEvent(this), INT_AFT_CLI);
        }
    }
    
    /**
     * Esta función selecciona las celdas de un JTable de acuerdo al tipo de selección.
     * El tipo de selección permite que se selecionen todas las celdas de una fila, de una columna
     * o ciertas celdas en particular. Esta selección es aparente ya que cuando se utilizan las
     * funciones "getSelectedRows()" y "getSelectedColumns()" para determinar las celdas seleccionadas
     * sólo se devuelve como celda seleccionada la celda actual cuando en realidad están selecionadas
     * todas las celdas de una columna(s) en particular o todas las celdas de una fila(s) en particular
     * Los métodos que permiten cortar, copiar, etc. utilizan dichos métodos para determinar las celdas
     * seleccionadas. Es por eso que antes de cortar, copiar, etc. se utiliza éste método para seleccionar
     * las celdas que deben estar seleccionadas de acuerdo al tipo de selección de celdas que tiene
     * establecido el JTable.
     * @return true: Si se pudieron seleccionar las celdas.
     * <BR>false: En el caso contrario.
     */
    private boolean seleccionarCel()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getRowSelectionAllowed())
            {
                if (tblDat.getColumnSelectionAllowed())
                {
                    //Selección por celda.
                }
                else
                {
                    //Selección por fila.
                    tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount()-1);
                }
            }
            else
            {
                if (tblDat.getColumnSelectionAllowed())
                {
                    //Selección por columna.
                    tblDat.setRowSelectionInterval(0, tblDat.getRowCount()-1);
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función determina si la opción "Cortar" está habilitada.
     * @return true: Si la opción "Cortar" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCortarEnabled()
    {
        return mnuCor.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Cortar".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Cortar".
     */
    public void setCortarEnabled(boolean habilitado)
    {
        mnuCor.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Cortar" está visible.
     * @return true: Si la opción "Cortar" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isCortarVisible()
    {
        return mnuCor.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Cortar".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Cortar".
     */
    public void setCortarVisible(boolean visible)
    {
        mnuCor.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Copiar" está habilitada.
     * @return true: Si la opción "Copiar" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCopiarEnabled()
    {
        return mnuCop.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Copiar".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Copiar".
     */
    public void setCopiarEnabled(boolean habilitado)
    {
        mnuCop.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Copiar" está visible.
     * @return true: Si la opción "Copiar" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isCopiarVisible()
    {
        return mnuCop.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Copiar".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Copiar".
     */
    public void setCopiarVisible(boolean visible)
    {
        mnuCop.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Pegar" está habilitada.
     * @return true: Si la opción "Pegar" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isPegarEnabled()
    {
        return mnuPeg.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Pegar".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Pegar".
     */
    public void setPegarEnabled(boolean habilitado)
    {
        mnuPeg.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Pegar" está visible.
     * @return true: Si la opción "Pegar" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isPegarVisible()
    {
        return mnuPeg.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Pegar".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Pegar".
     */
    public void setPegarVisible(boolean visible)
    {
        mnuPeg.setVisible(visible);
    }

    /**
     * Esta función determina si la opción "Seleccionar fila" está habilitada.
     * @return true: Si la opción "Seleccionar fila" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isSeleccionarFilaEnabled()
    {
        return mnuSelFil.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Seleccionar fila".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Seleccionar fila".
     */
    public void setSeleccionarFilaEnabled(boolean habilitado)
    {
        mnuSelFil.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Seleccionar fila" está visible.
     * @return true: Si la opción "Seleccionar fila" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isSeleccionarFilaVisible()
    {
        return mnuSelFil.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Seleccionar fila".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Seleccionar fila".
     */
    public void setSeleccionarFilaVisible(boolean visible)
    {
        mnuSelFil.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Seleccionar columna" está habilitada.
     * @return true: Si la opción "SeleccionarColumna" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isSeleccionarColumnaEnabled()
    {
        return mnuSelCol.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Seleccionar columna".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Seleccionar columna".
     */
    public void setSeleccionarColumnaEnabled(boolean habilitado)
    {
        mnuSelCol.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Seleccionar columna" está visible.
     * @return true: Si la opción "SeleccionarColumna" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isSeleccionarColumnaVisible()
    {
        return mnuSelCol.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Seleccionar columna".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Seleccionar columna".
     */
    public void setSeleccionarColumnaVisible(boolean visible)
    {
        mnuSelCol.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Seleccionar todo" está habilitada.
     * @return true: Si la opción "Seleccionar todo" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isSeleccionarTodoEnabled()
    {
        return mnuSelTod.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Seleccionar todo".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Seleccionar todo".
     */
    public void setSeleccionarTodoEnabled(boolean habilitado)
    {
        mnuSelTod.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Seleccionar todo" está visible.
     * @return true: Si la opción "Seleccionar todo" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isSeleccionarTodoVisible()
    {
        return mnuSelTod.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Seleccionar todo".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Seleccionar todo".
     */
    public void setSeleccionarTodoVisible(boolean visible)
    {
        mnuSelTod.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Insertar fila" está habilitada.
     * @return true: Si la opción "Insertar fila" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isInsertarFilaEnabled()
    {
        return mnuInsFil.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Insertar fila".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Insertar fila".
     */
    public void setInsertarFilaEnabled(boolean habilitado)
    {
        mnuInsFil.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Insertar fila" está visible.
     * @return true: Si la opción "Insertar fila" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isInsertarFilaVisible()
    {
        return mnuInsFil.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Insertar fila".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Insertar fila".
     */
    public void setInsertarFilaVisible(boolean visible)
    {
        mnuInsFil.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Insertar filas" está habilitada.
     * @return true: Si la opción "Insertar filas" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isInsertarFilasEnabled()
    {
        return mnuInsVarFil.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Insertar filas".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Insertar filas".
     */
    public void setInsertarFilasEnabled(boolean habilitado)
    {
        mnuInsVarFil.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Insertar filas" está visible.
     * @return true: Si la opción "Insertar filas" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isInsertarFilasVisible()
    {
        return mnuInsVarFil.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Insertar filas".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Insertar filas".
     */
    public void setInsertarFilasVisible(boolean visible)
    {
        mnuInsVarFil.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Eliminar fila" está habilitada.
     * @return true: Si la opción "Eliminar fila" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isEliminarFilaEnabled()
    {
        return mnuEliFil.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Eliminar fila".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Eliminar fila".
     */
    public void setEliminarFilaEnabled(boolean habilitado)
    {
        mnuEliFil.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "EliminarFila" está visible.
     * @return true: Si la opción "Eliminar fila" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isEliminarFilaVisible()
    {
        return mnuEliFil.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Eliminar fila".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Eliminar fila".
     */
    public void setEliminarFilaVisible(boolean visible)
    {
        mnuEliFil.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Borrar contenido" está habilitada.
     * @return true: Si la opción "Borrar contenido" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isBorrarContenidoEnabled()
    {
        return mnuBorCon.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Borrar contenido".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Borrar contenido".
     */
    public void setBorrarContenidoEnabled(boolean habilitado)
    {
        mnuBorCon.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Borrar contenido" está visible.
     * @return true: Si la opción "Borrar contenido" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isBorrarContenidoVisible()
    {
        return mnuBorCon.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Borrar contenido".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Borrar contenido".
     */
    public void setBorrarContenidoVisible(boolean visible)
    {
        mnuBorCon.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Ocultar columna" está habilitada.
     * @return true: Si la opción "Ocultar columna" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isOcultarColumnaEnabled()
    {
        return mnuOcuCol.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Ocultar columna".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Ocultar columna".
     */
    public void setOcultarColumnaEnabled(boolean habilitado)
    {
        mnuOcuCol.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Ocultar columna" está visible.
     * @return true: Si la opción "Ocultar columna" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isOcultarColumnaVisible()
    {
        return mnuOcuCol.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Ocultar columna".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Ocultar columna".
     */
    public void setOcultarColumnaVisible(boolean visible)
    {
        mnuOcuCol.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Mostrar columna" está habilitada.
     * @return true: Si la opción "Mostrar columna" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isMostrarColumnaEnabled()
    {
        return mnuMosCol.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Mostrar columna".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Mostrar columna".
     */
    public void setMostrarColumnaEnabled(boolean habilitado)
    {
        mnuMosCol.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Mostrar columna" está visible.
     * @return true: Si la opción "MostrarColumna" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isMostrarColumnaVisible()
    {
        return mnuMosCol.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Mostrar columna".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Mostrar columna".
     */
    public void setMostrarColumnaVisible(boolean visible)
    {
        mnuMosCol.setVisible(visible);
    }
    
    /**
     * Esta función determina si la opción "Tipo de xelección" está habilitada.
     * @return true: Si la opción "Tipo de xelección" está habilitada.
     * <BR>false: En el caso contrario.
     */
    public boolean isTipoSeleccionEnabled()
    {
        return mnuTipSel.isEnabled();
    }
    
    /**
     * Esta función establece si se debe habilitar la opción "Tipo de xelección".
     * @param habilitado Valor booleano que determina si se debe habilitar la opción "Tipo de xelección".
     */
    public void setTipoSeleccionEnabled(boolean habilitado)
    {
        mnuTipSel.setEnabled(habilitado);
    }
    
    /**
     * Esta función determina si la opción "Tipo de xelección" está visible.
     * @return true: Si la opción "Tipo de xelección" está visible.
     * <BR>false: En el caso contrario.
     */
    public boolean isTipoSeleccionVisible()
    {
        return mnuTipSel.isVisible();
    }
    
    /**
     * Esta función establece si se debe hacer visible la opción "Tipo de xelección".
     * @param visible Valor booleano que determina si se debe hacer visible la opción "Tipo de xelección".
     */
    public void setTipoSeleccionVisible(boolean visible)
    {
        mnuTipSel.setVisible(visible);
    }
    
    /**
     * Esta función determina si se dió click en "Cortar".
     * @return true: Si se dió click en "Cortar".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickCortar()
    {
        if (intOpcSelMnu==INT_MNU_COR)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Copiar".
     * @return true: Si se dió click en "Copiar".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickCopiar()
    {
        if (intOpcSelMnu==INT_MNU_COP)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Pegar".
     * @return true: Si se dió click en "Pegar".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickPegar()
    {
        if (intOpcSelMnu==INT_MNU_PEG)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Seleccionar fila".
     * @return true: Si se dió click en "Seleccionar fila".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickSeleccionarFila()
    {
        if (intOpcSelMnu==INT_MNU_SEL_FIL)
            return true;
        else
            return false;
    }
    
    
    
    
     public boolean isClickModificarFila()
    {
        if (intOpcSelMnu==INT_MNU_MOD_FIL)
            return true;
        else
            return false;
    }
     
     
    
    
    
    /**
     * Esta función determina si se dió click en "Seleccionar columna".
     * @return true: Si se dió click en "Seleccionar columna".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickSeleccionarColumna()
    {
        if (intOpcSelMnu==INT_MNU_SEL_COL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Seleccionar todo".
     * @return true: Si se dió click en "Seleccionar todo".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickSeleccionarTodo()
    {
        if (intOpcSelMnu==INT_MNU_SEL_TOD)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Insertar fila".
     * @return true: Si se dió click en "Insertar fila".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickInsertarFila()
    {
        if (intOpcSelMnu==INT_MNU_INS_FIL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Insertar filas".
     * @return true: Si se dió click en "Insertar filas".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickInsertarFilas()
    {
        if (intOpcSelMnu==INT_MNU_INS_VAR_FIL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Eliminar fila(s)".
     * @return true: Si se dió click en "Eliminar fila(s)".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickEliminarFila()
    {
        if (intOpcSelMnu==INT_MNU_ELI_FIL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Borrar contenido".
     * @return true: Si se dió click en "Borrar contenido".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickBorrarContenido()
    {
        if (intOpcSelMnu==INT_MNU_BOR_CON)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Ocultar columna".
     * @return true: Si se dió click en "Ocultar columna".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickOcultarColumna()
    {
        if (intOpcSelMnu==INT_MNU_OCU_COL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Mostrar columna: Todas las columnas".
     * @return true: Si se dió click en "Mostrar columna: Todas las columnas".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickMostrarColumnaTodasColumnas()
    {
        if (intOpcSelMnu==INT_MNU_MCO_TOD_COL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Ajustar ancho de columnas automáticamente".
     * @return true: Si se dió click en "Ajustar ancho de columnas automáticamente".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickAjustarAnchoColumnasAutomaticamente()
    {
        if (intOpcSelMnu==INT_MNU_AJU_ANC_COL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Tipo de selección: Selección por celda".
     * @return true: Si se dió click en "Tipo de selección: Selección por celda".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickTipoSeleccionSeleccionCelda()
    {
        if (intOpcSelMnu==INT_MNU_TSE_CEL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Tipo de selección: Selección por fila".
     * @return true: Si se dió click en "Tipo de selección: Selección por fila".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickTipoSeleccionSeleccionFila()
    {
        if (intOpcSelMnu==INT_MNU_TSE_FIL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función determina si se dió click en "Tipo de selección: Selección por columna".
     * @return true: Si se dió click en "Tipo de selección: Selección por columna".
     * <BR>false: En el caso contrario.
     */
    public boolean isClickTipoSeleccionSeleccionColumna()
    {
        if (intOpcSelMnu==INT_MNU_TSE_COL)
            return true;
        else
            return false;
    }
    
    /**
     * Esta función cancela el evento click generado sobre un menú del "ZafTblPopMnu".
     */
    public void cancelarClick()
    {
        blnCanCli=true;
    }
    
    /**
     * Esta función adiciona el listener que controlará los eventos del tipo click que se generen sobre cada menú del "ZafTblPopMnu".
     * @param listener El objeto que implementa los métodos de la interface "ZafTblPopMnuListener".
     */
    public void addTblPopMnuListener(ZafTblPopMnuListener listener)
    {
        objEveLisLis.add(ZafTblPopMnuListener.class, listener);
    }

    /**
     * Esta función remueve el listener que controlará los eventos del tipo click que se generen sobre cada menú del "ZafTblPopMnu".
     * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
     */
    public void removeTblPopMnuListener(ZafTblPopMnuListener listener)
    {
        objEveLisLis.remove(ZafTblPopMnuListener.class, listener);
    }
    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafTblPopMnuEvent".
     * @param metodo El método que será invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_CLI</TD><TD>Invoca al métod "beforeClick" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_CLI</TD><TD>Invoca al métod "afterClick" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireTblPopMnuListener(ZafTblPopMnuEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafTblPopMnuListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_CLI:
                        ((ZafTblPopMnuListener)obj[i+1]).beforeClick(evt);
                        break;
                    case INT_AFT_CLI:
                        ((ZafTblPopMnuListener)obj[i+1]).afterClick(evt);
                        break;
                }
            }
        }
    }
    
}
