package SalesModel;

public class Client {
	private String name;
	private int documentTypeCode;
	private int NIT;

	public Client(String name, int documentTypeCode, int NIT) {
		this.name = name;
		this.documentTypeCode = documentTypeCode;
		this.NIT = NIT;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
