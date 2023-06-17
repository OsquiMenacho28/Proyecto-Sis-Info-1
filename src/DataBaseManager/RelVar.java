package DataBaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class RelVar {
	private HashMap <String, DataType> relvar = new HashMap<>();
	private ArrayList <Integer> PrimaryKey = new ArrayList<>();
	private ArrayList <String> columns = new ArrayList<>();
	private int size;
	
	public RelVar(ArrayList<String> columns,ArrayList<DataType>  types) throws Exception {
		if(columns.size() == types.size()) {
			for(int i = 0; i < columns.size(); i++) {
				this.columns.add(columns.get(i));
				this.relvar.put(columns.get(i),types.get(i));
			}
			this.size = columns.size();
			PrimaryKey.add(0);
		}
		else {
			throw new Exception("Not compatible values");
		}
	}
	
	public RelVar(ArrayList<String> columns,ArrayList<DataType>  types, ArrayList<Integer> PrimaryKey) throws Exception {
		
		if(columns.size() == types.size()) {
			for(int i = 0; i < columns.size(); i++) {
				this.columns.add(columns.get(i));
				this.relvar.put(columns.get(i),types.get(i));
			}
			this.size = columns.size();
			this.PrimaryKey = PrimaryKey;
		}
		else {
			throw new Exception("Not compatible values");
		}
	}
	
	public int get_size() {
		return this.size;
	}
	
	public ArrayList<String> get_columns() {
		return this.columns;
	}

	public DataType get_type(String atribute) {
		return relvar.get(atribute);
	}
	
	public DataType get_type(int atribute) {
		return relvar.get(columns.get(atribute));
	}
	
	
	public String get_pk() {
		return this.columns.get(0);
	}
	
	public boolean is_compatible(RelVar x) {
		if(x.size == this.size) {
	        for (String col : x.get_columns()) {
	            if(x.get_type(col) != this.get_type(col)) {
	            	return false;
	            }
	        }
	        return true;
		}
		return false;
	}
}
