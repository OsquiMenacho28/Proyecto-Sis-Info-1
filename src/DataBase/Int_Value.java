package DataBase;

public class Int_Value extends Value {
	private int value;

	public Int_Value(int value) {
		super(DataType.INTEGER_TYPE);
		this.value = value;
	}
	
	public int get_value() {
		return value;
	}

	@Override
	public String to_string() {
	    return Integer.toString(value);
	}
}