package DataBaseManager;

public class Float_Value extends Value {
	private float value;
	public Float_Value(float value) {
		super(DataType.FLOAT_TYPE);
		this.value = value;
	}
	
	public float get_value() {
		return value;
	}

	@Override
	public String to_string() {
		return Float.toString(value);
	}
}
