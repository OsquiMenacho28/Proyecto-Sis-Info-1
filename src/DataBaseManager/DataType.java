package DataBaseManager;

public class DataType{
	public static DataType STRING_TYPE = new DataType("STRING_TYPE");
	public static DataType INTEGER_TYPE = new DataType("INTEGR_TYPE");
	public static DataType FLOAT_TYPE = new DataType("FLOAT_TYPE");
	public static DataType DATE_TYPE = new DataType("DATE_TYPE");
	public static DataType OTHER_DATATYPE = new DataType("OTHER_DATATYPE");
	
	String type;
	
	private DataType(String name){
		this.type = name;
	}
}