package DataBaseManager;

public class Int_Value extends Value {
	private Integer value;

	public Int_Value(int value) {
		super(DataType.INTEGER_TYPE);
		this.value = value;
	}
	
	public Integer get_value() {
		return value;
	}

	public void set_value(Object value){
		this.value = (Integer) value;
	};

	@Override
	public String to_string() {
	    return Integer.toString(value);
	}
}