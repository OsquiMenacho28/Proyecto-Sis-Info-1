package DataBaseManager;

public class Float_Value extends Value {
	private Float value;
	public Float_Value(float value) {
		super(DataType.FLOAT_TYPE);
		this.value = value;
	}
	
	public Float get_value() {
		return value;
	}

	public void set_value(Object value){
		this.value = (Float) value;
	};

	@Override
	public String to_string() {
		return Float.toString(value);
	}

	@Override
	public Object getValue() {
		return value;
	}
}
