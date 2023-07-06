package SalesModel;

import DataBaseManager.*;

import java.sql.SQLException;

public class Client extends LinkedObject {
	private Int_Value code;
	private String_Value name;
	private Int_Value NIT;
	private static final int documentTypeCode = 1;

	public Client(ClientsList list, String name, int documentTypeCode, int NIT) throws Exception {
		super(ClientsList.clientRV,
				Value.create(list.getNewPK()),
				Value.create(name),
				Value.create(NIT));
		bindDefinition();
		link();
	}

	public Client(RowMirror row) throws Exception {
		super(row);
		bindDefinition();
		link();
	}

	private void bindDefinition(){
		bind("id_cliente", code);
		bind("nombre", name);
		bind("nit", NIT);
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
