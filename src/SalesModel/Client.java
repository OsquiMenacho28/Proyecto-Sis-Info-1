package SalesModel;

public class Client {
	String name;
	int NIT;
	
	
	
	public Client(String name, int nIT) {
	
		this.name = name;
		this.NIT = nIT;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the nIT
	 */
	public int getNIT() {
		return NIT;
	}
	/**
	 * @param nIT the nIT to set
	 */
	public void setNIT(int nIT) {
		NIT = nIT;
	}
	
}
