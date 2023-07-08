package DataBaseManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
	
	public Integer get_pk_val() {
		return ((Int_Value)record.get(relvar.get_pk())).get_value();
	}

	public HashMap<String, Value> getRecord() {
		return record;
	}

	public void edit(String col, String val1) throws SQLException {
		String_Value val = Value.create(val1);
		if (Objects.equals(col, get_pk_val())) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}
	
	public void edit(String col, int val2) throws SQLException {
		Int_Value val = Value.create(val2);
		if (Objects.equals(col, get_pk_val())) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}
	
	public void edit(String col, float val3) throws SQLException {
		Float_Value val = Value.create(val3);
		if (Objects.equals(col, get_pk_val())) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}
	
	public void edit(String col, Date val4) throws SQLException {
		Date_Value val = Value.create(val4);
		if (Objects.equals(col, get_pk_val())) {
			return;
		}
		if (relvar.get_type(col) == val.get_type()) {
			record.put(col, val);
			update(col);
		}		
	}

	public void edit(String col, Value val) throws SQLException {
		if (Objects.equals(col, get_pk_val())) {
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
		if(this.table != null){
			if(this.table.contains(this) != null){
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

	public void add(TableMirror table) throws Exception {
		setTable(table);
		this.table.add(this);
	}

	public void add() throws Exception {
		if(this.table != null){
			add(this.table);
		}
	}

	public void deactivate() throws SQLException {
		this.table.remove(this);
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
		StringBuilder res = new StringBuilder();
		for(String atribute : relvar.get_columns()) {
			res.append(record.get(atribute).to_string()).append("\t\t");
		}
		return "Row[" + res + "]";
	}
}
