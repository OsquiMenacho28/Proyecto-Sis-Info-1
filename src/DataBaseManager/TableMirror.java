package DataBaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableMirror {
	private DBManager manager;
	private String name;
	private RelVar relvar;
	private ArrayList<RowMirror> rows = new ArrayList<>();
	
	public TableMirror(DBManager manager, String name, RelVar relvar) throws Exception {
		this.manager = manager;
		this.name = name;
		this.relvar = relvar;
		fill();
	}
	
	public void fill() throws Exception {
	    try {
	        ResultSet rs = manager.runQuery("select * from " + name);
	        while (rs.next()) {
	            ArrayList<Value> values = new ArrayList<Value>();
	            for (int i = 0; i < relvar.get_size(); i++) {
	                Value val = null;
	                if (relvar.get_type(i) == DataType.FLOAT_TYPE) {
	                    val = new Float_Value(rs.getFloat(i+1));
	                } else if (relvar.get_type(i) == DataType.DATE_TYPE) {
	                    val = new Date_Value(rs.getDate(i+1));
	                } else if (relvar.get_type(i) == DataType.INTEGER_TYPE) {
	                    val = new Int_Value(rs.getInt(i+1));
	                } else if (relvar.get_type(i) == DataType.STRING_TYPE) {
	                    val = new String_Value(rs.getString(i+1));
	                }
	                values.add(val);
	            }
	            RowMirror row = new RowMirror(this, relvar, values);
	            rows.add(row);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        System.err.println("An error occurred while executing the SQL query: " + e);
	        e.printStackTrace();
	        throw e;
	    }
	}

	public RowMirror contains(RowMirror row){
		RowMirror res = null;
		for(RowMirror savedRow :  this.rows){
			if(savedRow.get_pk_val().equals(row.get_pk_val())){
				res = savedRow;
				break;
			}
		}
		return res;
	}

	public void add(RowMirror row) throws Exception {
		RelVar rrelvar = row.get_relvar(); 
		if(rrelvar.is_compatible(this.relvar)) {
			RowMirror savedRow = contains(row);
			if(!savedRow.equals(null)){
				this.remove(savedRow);
				savedRow.deactivate();
			}
			String aux = "";
			String aux1 = "";
			ArrayList <String> attributes = row.get_relvar().get_columns();
			for(int i = 0; i < row.get_relvar().get_size(); i++) {
				aux += row.get_value(attributes.get(i)).to_string() + ", ";
			}
			for(int i = 0; i < row.get_relvar().get_size(); i++) {
				aux1 += attributes.get(i) + ", ";
			}
			aux = aux.substring(0, aux.length() - 2);
			aux1 = aux1.substring(0, aux1.length() - 2);
			//System.out.println("insert into " + name + " (" + aux1 + ") values (" + aux + ")");
			manager.updateQuery("insert into " + name + " (" + aux1 + ") values (" + aux + ")");

			rows.add(row);
			row.setTable(this);
			row.activate();
		}
		else {
			throw new Exception("Not compatible");
		}
	}
	
	public void remove(RowMirror row) throws SQLException {
		rows.remove(row);
		
		String pk = relvar.get_columns().get(0);
		//System.out.println("delete from " + name + " where " + pk + " = " + row.get_value(pk).to_string());
		manager.updateQuery("delete from " + name + " where " + pk + " = " + row.get_value(pk).to_string());
	}
	
	public void update(RowMirror row, String atri) throws SQLException {
		manager.updateQuery("update " + name + " set " + atri +  " = " + row.get_value(atri).to_string() + " where " + relvar.get_pk() + " = " + row.get_pk_val());
	}
	
	public RowMirror getRow(int i) {
		return rows.get(i);
	}
	
	public RelVar getRelVar() {
		return this.relvar;
	}

	@Override
	public String toString() {
		String res = this.name + "\n";
		for(String atribute : relvar.get_columns()) {
			res += atribute + "\t\t";
		}
		res += "-----------------------------------------------------------------------------------\n";
		
		for(RowMirror row : rows) {
			res += row.toString() + "\n";
		}
		res += "-----------------------------------------------------------------------------------\n";
		return "TableMirror [" + res + "]";
	}
}
