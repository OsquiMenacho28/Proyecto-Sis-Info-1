package application;

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

	public int getNIT() {
		return NIT;
	}

	public void setNIT(int nIT) {
		NIT = nIT;
	}

	public int getDocumentTypeCode(){
		return this.documentTypeCode;
	}

	public void setDocumentTypeCode(int documentTypeCode){
		this.documentTypeCode = documentTypeCode;
	}
}
