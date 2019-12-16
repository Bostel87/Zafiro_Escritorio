package Librerias.ZafTreeTable;
import java.util.Vector;

/**
 * Crea un Modelo para un TreeTable que recibe los nombres de las columnas y el vector con los datos tal como si se trabajara con un DefaultTableModel para el Jtable
 * @see <strong>Como usar el zafTreeTableModel</strong><pre>
 *
 *        Creando un zafJTreeTable con un zafTreeTableModel enviando los datos desde un vector
 *
 *                String[] ColumnNames = { "Numero Fac", "Fecha", "Monto", "Destinatario"};
 *
 * 	        zafJTreeTable treeTable = new zafJTreeTable(new ZafTreeTableModel(ColumnNames, VecTbl), "d:\\info_st_obj.gif");
 *
 *
 *        Cargando los datos en el Vector  VecTbl para enviarlos al constructor del zafTreeTableModel
 *
 *
 *
 *
 *             try
 * 		{
 * 			con=DriverManager.getConnection("jdbc:odbc:idiprueba","","");
 *
 * 			if (con!=null)
 * 			{
 *                                stm=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 *
 *                                sSQL= "select * from pedidos order by idcliente";
 *
 *                                rst=stm.executeQuery(sSQL);
 *                                VecTbl = new Vector();
 *
 *                                int Var_Cols = rst.getMetaData().getColumnCount();
 *
 *                                boolean eof=rst.next();
 *
 *                                VecTbl.addElement("p");
 *                                VecTbl.addElement("Clientes");
 *                                String curCli="";
 *                                String curReg="";
 *                                double dblTotal = 0 , dblParcial = 0;
 *
 *                                while(eof){
 *
 *                                    Vector VecNod = new Vector();
 *                                    curCli = rst.getString("idcliente").toString();
 *
 *                                    rst.refreshRow();
 *
 *                                    VecNod.addElement("p");
 *                                    VecNod.addElement(curCli);
 *                                    dblParcial = 0;
 *                                    while(eof && rst.getString("idcliente").equals(curCli)){
 *
 *                                         Vector VecReg = new Vector();
 *                                         double Mivalor = rst.getDouble("Cargo");
 *                                             dblParcial = dblParcial + Mivalor;
 *                                             VecReg.addElement("h");
 *                                             VecReg.addElement(rst.getString("idpedido"));
 *                                             VecReg.addElement(rst.getString("FechaPedido"));
 *                                             VecReg.addElement(Mivalor+"");
 *                                             VecReg.addElement(rst.getString("Destinatario"));
 *
 *                                         VecNod.addElement(VecReg);
 *                                         eof=rst.next();
 *                                       }
 *                                             Vector VecDatPadre = new Vector();
 *                                             VecDatPadre.addElement("");
 *                                             VecDatPadre.addElement(dblParcial+"");
 *                                             VecDatPadre.addElement("");
 *                                      dblTotal = dblTotal + dblParcial;
 *                                      VecNod.addElement(VecDatPadre);
 *                                      VecTbl.addElement(VecNod);
 *                                }
 *
 *                                      Vector VecDatPadre = new Vector();
 *                                      VecDatPadre.addElement("");
 *                                      VecDatPadre.addElement(dblTotal+"");
 *                                      VecDatPadre.addElement("");
 *                                      VecTbl.addElement(VecDatPadre);
 *
 *                                    Vector VecColNom = new Vector();
 *
 *
 *                                    rst.close();
 * 			}
 * 		}
 * 		catch(SQLException Evt)
 *                {
 * 			System.out.println(Evt.toString());
 * 		}
 *
 *                catch(Exception Evt)
 * 		{
 * 			System.out.println(Evt.toString());
 * 		}
 *
 * </pre>
 */
public class ZafTreeTableModel extends AbstractTreeTableModel implements TreeTableModel {

    // Nombre de las Columnas 
    /**
     * Contiene un arreglo de String con los nombres de las columnas a presentar en la Tabla
     */    
    static protected String[]  cNames ;
    // Tipos de las columnas
    /**
     * Contiene un arreglo de Clases con los tipos de clase ke se van  a presentar en la tabla
     */    
    static protected Class[]  cTypes = {TreeTableModel.class, Object.class, Object.class, Object.class};
    
    /**
     * Contruye un modelo de tipo default para ser presentado en un zafJTreeTable
     * @param _cNames Un arreglo de String con los nombres de las columnas
     * @param _Data Un Vector que contiene los datos para el JTreeTable<pre>
     *     El vector debe tener la siguiente forma:
     *     - Si el contenido del vector representa una Hoja, entonces debe tener como 1er
     *     elemento un String con la letra "h" y los siguientes elementos seran Strings
     *     con los datos a presentar en cada columna
     *
     *     - Si el Vector representa un Padre (Carpeta), entonces debe tener como 1er elemento
     *     un String con la letra "p", el siguiente elemento del vector padre tendra un string
     *     con el nombre a presentar en el icono del padre (carpeta), los siguientes elementos
     *     del vector deberan ser los hijos de esa carpeta o otros padres que estaran dentro de
     *     este padre. Por ultimo luego de haber ingresado todos los elementos del Vector padre
     *     el elemnto final a ingresar es un vector con los datos a presentar en la fila del padre
     *     con los datos de cada columna desde la 1 hasta el numero maximo de columnas en la tabla
     *
     * </pre>
     */    
    public ZafTreeTableModel(String[] _cNames, java.util.Vector _Data){ 
        super(  new vecNode( _Data ) ); 
        cNames = _cNames;
    }

    /*
     * Metodos por conveniencia
     */

    protected java.util.Vector getNode(Object node) {
	vecNode vecNodex = ((vecNode)node); 
	return vecNodex.getNode();       
    }

    protected Object[] getChildren(Object node) {
	vecNode vecNodex = ((vecNode)node); 
	return vecNodex.getChildren(); 
    }

    //
    // The TreeModel interface
    //

    public int getChildCount(Object node) { 
	Object[] children = getChildren(node); 
	return (children == null) ? 0 : children.length;
    }

    public Object getChild(Object node, int i) { 
	return getChildren(node)[i]; 
    }

    /*
     *  interface TreeTableNode 
     */

    public int getColumnCount() {
	return cNames.length;
    }

    public String getColumnName(int column) {
	return cNames[column];
    }

    public Class getColumnClass(int column) {
	return cTypes[column];
    }
 
    public Object getValueAt(Object node, int column) {
        java.util.Vector vecNodex = getNode(node); 
        java.util.Vector vecDatPadre = new java.util.Vector();
        String tipo = vecNodex.elementAt(0).toString();
        if(tipo.equals("p")){
            vecDatPadre = (java.util.Vector) vecNodex.elementAt(vecNodex.size()-1);
        }
            

        try {
	    switch(column) {
	    case 0:
		return vecNodex.elementAt(1).toString();
	    default:
		return tipo.equals("h") ?  vecNodex.elementAt(column+1) : vecDatPadre.elementAt(column-1).toString();
	    }
	}
	catch  (SecurityException se){ }
        return null; 
    }
}

/* 
 * Clase vecNode. 
 * Utilzada para guardar los datos de un nodo ya sea hoja o node
 * y presentar en el jtreetable.
 */
class vecNode { 
    java.util.Vector vecNodo; 
    Object[] children; 

    public vecNode(java.util.Vector vecNodo) { 
	this.vecNodo = vecNodo; 
    }

    /**
     * Retorna el String a presentar en la hoja del arbol.
     */
    public String toString() { 
        return vecNodo.elementAt(1).toString();
    }

    public java.util.Vector getNode() {
	return vecNodo; 
    }

    /**
     * Retorna los Hijos del Node.
     */
    protected Object[] getChildren() {
	if (children != null) {
	    return children; 
	}
	try {
            
            String tipo = vecNodo.elementAt(0).toString();
            
	     if(!tipo.equals("h")){
                children = new vecNode[vecNodo.size()-3]; 
                
		for(int i = 2; i < vecNodo.size()-1; i++) {
		    java.util.Vector childNode = (java.util.Vector)vecNodo.elementAt(i) ; 
		    children[i-2] = new vecNode(childNode);
	           }   
                
	     }
            
	}catch (SecurityException se) { }
        return children; 
    }
}