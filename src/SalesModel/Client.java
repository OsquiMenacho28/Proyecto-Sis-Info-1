package SalesModel;

import DataBaseManager.Int_Value;
import DataBaseManager.LinkedObject;
import DataBaseManager.String_Value;
import DataBaseManager.Value;

import java.sql.SQLException;

public class Client extends LinkedObject {
	private Int_Value code;
	private String_Value name;
	private Int_Value NIT;
	private int documentTypeCode;

	public Client(String name, int documentTypeCode, int NIT) {
		super();
		this.name = name;
		this.documentTypeCode = documentTypeCode;
		this.NIT = NIT;
	}

	public String getName() {
		return name.get_value();
	}

	public void setName(String name) throws SQLException {
		set(this.name,Value.create(name));
	}

	public int getDocumentTypeCode() {
		return documentTypeCode;
	}

	public void setDocumentTypeCode(int documentTypeCode) {
		this.documentTypeCode = documentTypeCode;
	}

	public int getNIT() {
		return NIT;
	}

	public void setNIT(int NIT) {
		this.NIT = NIT;
	}
}
