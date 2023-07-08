package SalesModel;

import DataBaseManager.*;

import java.sql.SQLException;

public class Client extends LinkedObject {
	private Int_Value code = new Int_Value();
	private String_Value name = new String_Value();
	private Int_Value NIT = new Int_Value();
	private static final int documentTypeCode = 1;

	public Client(ClientsList list,int code, String name, int NIT) throws Exception {
		super(ClientsList.clientRV,
				Value.create(code),
				Value.create(name),
				Value.create(NIT));
		defineBind();
		link();
	}

	public Client(RowMirror row) throws Exception {
		super(row);
		defineBind();
		link();
	}

	protected void defineBind(){
		bind("ID_CLIENTE", code);
		bind("NOMBRE", name);
		bind("NIT", NIT);
	}

	public int getCode(){
		return code.get_value();
	}
	public String getName() {
		return name.get_value();
	}

	public int getDocumentTypeCode() {
		return documentTypeCode;
	}

	public int getNIT() {
		return NIT.get_value();
	}

	public void setNIT(int NIT) throws SQLException {
		set(this.NIT, Value.create(NIT));
	}

	public void setName(String name) throws SQLException {
		set(this.name, Value.create(name));
	}
}
