package DataBase;

public class Float_Value extends Value {
	private float value;
	public Float_Value(float value) {
		super(DataType.FLOAT_TYPE);
		this.value = value;
		// TODO Auto-generated constructor stub
	}
	
	public float get_value() {
		return value;
	}

	@Override
	public String to_string() {
		return Float.toString(value);
	}
}
