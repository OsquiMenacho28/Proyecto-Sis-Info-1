package DataBaseManager;

public class String_Value extends Value {
	private String value;

	public String_Value(String value) {
		super(DataType.STRING_TYPE);
		this.value = value;
	}
	
	public String get_value() {
		return value;
	}

	public void set_value(Object value){
		this.value = (String) value;
	};
	@Override
	public String to_string() {
		return "'" + value + "'";
	}

	@Override
	public Object getValue() {
		return value;
	}
}