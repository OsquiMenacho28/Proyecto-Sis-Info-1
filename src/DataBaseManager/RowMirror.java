package DataBaseManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RowMirror {
	protected RelVar relvar;
	protected TableMirror table = null;

	protected boolean active = false;
	protected HashMap<String, Value> record = new HashMap<>();


	public RowMirror(RelVar rel, ArrayList<Value> values) throws Exception {
		this.relvar = rel;

		if(rel.get_size() == values.size()) {
			for(int i = 0; i < rel.get_size(); i++) {
				String colname = rel.get_columns().get(i);
				Value val = values.get(i);
				if(rel.get_type(colname) == val.get_type()) {
					record.put(colname,val);
				}
				else {
					throw new Exception("Not compatible values");
				}
			}
		}
	}
	public RowMirror(TableMirror table, RelVar rel, ArrayList<Value> values) throws Exception {
		this(rel, values);
		setTable(table);
		this.activate();
	}

	public RowMirror(RelVar rel, Value... values) throws Exception {
		this.relvar = rel;

		if(rel.get_size() == values.length) {
			for(int i = 0; i < rel.get_size(); i++) {
				String colname = rel.get_columns().get(i);
				Value val = values[i];
				if(rel.get_type(colname) == val.get_type()) {
					record.put(colname,val);
				}
				else {
					throw new Exception("Not compatible values");
				}
			}
		}
	}


	public RowMirror(TableMirror table, RelVar rel, Value... values) throws Exception {
		this(rel, values);
		this.setTable(table);
		this.activate();
	}
	
	public Value get_value(String col) {
		return record.get(col);
	}
	
	public RelVar get_relvar(){
		return this.relvar;
	}
	
	public String get_pk_val() {
		return record.get(relvar.get_pk()).to_string();
	}

	public HashMap<String, Value> getRecord() {
		return record;
	}

	public void edit(String col, String val1) throws SQLException {
		String_Value val = Value.create(val1);
		if (col == get_pk_val()) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}
	
	public void edit(String col, int val2) throws SQLException {
		Int_Value val = Value.create(val2);
		if (col == get_pk_val()) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}
	
	public void edit(String col, float val3) throws SQLException {
		Float_Value val = Value.create(val3);
		if (col == get_pk_val()) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}
	
	public void edit(String col, Date val4) throws SQLException {
		Date_Value val = Value.create(val4);
		if (col == get_pk_val()) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}

	public void edit(String col, Value val) throws SQLException {
		if (col == get_pk_val()) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}
	}
	
	private void update(String col) throws SQLException {
		if(active) {
			this.table.update(this, col);
		}
	}
	
	public void activate() throws Exception {
		if(!this.table.equals(null)){
			if(!this.table.contains(this).equals(null)){
				this.active = true;
			}
			else{
				throw new Exception("Not in table");
			}
		}
		else{
			throw new Exception("Invalid table");
		}
	}

	public void deactivate(){
		this.active = false;
	}

	public void setTable(TableMirror tm) throws Exception {
		if(tm.getRelVar().is_compatible(relvar)){
			this.table = tm;
		}
	}
	public TableMirror getTable(){
		return this.table;
	}

	public ArrayList<Value> getValues(){
		return new ArrayList<Value>(record.values());
	}

	public ArrayList<String> getColumns(){
		return relvar.get_columns();
	}
	public boolean isActive(){
		return active;
	}
	@Override
	public String toString() {
		String res = "";
		for(String atribute : relvar.get_columns()) {
			res += record.get(atribute).to_string()+ "\t\t";
		}
		return "Row[" + res + "]";
	}
}
