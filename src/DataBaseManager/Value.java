package DataBaseManager;

import java.sql.Date;
import java.time.LocalDateTime;

public abstract class Value {
	protected DataType type;
	protected Object value;
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

	public static Value create(DataType dataType) {
		if(dataType == DataType.INTEGER_TYPE) return new Int_Value(0);
		if(dataType == DataType.FLOAT_TYPE) return new Float_Value(0f);
		if(dataType == DataType.STRING_TYPE) return new String_Value("");
		if(dataType == DataType.DATE_TYPE) return new Date_Value(Date.valueOf(LocalDateTime.now().toLocalDate()));
		return null;
	}
	public abstract Object get_value();
	public abstract void set_value(Object value);
	public abstract String to_string();
	public DataType get_type() {
		return type;
	};
}