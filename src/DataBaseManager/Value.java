package DataBaseManager;

import java.sql.Date;

public class Value {
	DataType type;
	
	public Value(DataType type) {
		this.type = type;
	}
	
	public static Int_Value create(int val) {
		return new Int_Value(val);
	}
	
	public static Float_Value create(float val) {
		return new Float_Value(val);
	}
	
	public static Date_Value create(Date val) {
		return new Date_Value(val);
	}
	
	public static String_Value create(String val) {
		return new String_Value(val);
	}
	
	public DataType get_type() {
		return type;
	};
	
	public String to_string() {
		return "";
	}
}