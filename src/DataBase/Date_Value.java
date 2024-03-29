package DataBase;

import java.sql.Date;

public class Date_Value extends Value {
	private Date value;

	public Date_Value(Date value) {
		super(DataType.DATE_TYPE);
		this.value = value;
	}

	public Date get_value() {
		return value;
	}

	@Override
	public String to_string() {
		return String.valueOf(value);
	}
}