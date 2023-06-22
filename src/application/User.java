package application;

import java.util.ArrayList;

public final class User {

	private String CI;
	private String userName;
	private String password;
	private String names;
	private String lastNames;
	private String address;
	private int phone;
	private Boolean admin;
	
	final ArrayList<String> users = new ArrayList<String>() ;
	final ArrayList<String> pass = new ArrayList<String>() ;
	
	public User(String userName, String password, Boolean admin) {
		this.userName = userName;
		this.password = password;
		this.admin = admin;
		users.add("User1");
		users.add("User2");
		users.add("User3");
		
		pass.add("1243");
		pass.add("3465");
		pass.add("1010");
	}
	
	public User searchDB (String userName, String password) {
		int ind = users.indexOf(userName);
		System.out.println(ind);
		if(ind >= 0) {
			if(pass.get(ind).equals(password)) {
				System.out.println("Loged");
				return new User(userName, password, true);
			}
		}
		return null;
	}

	public String getCI() {
		return CI;
	}

	public void setCI(String CI) {
		this.CI = CI;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getLastNames() {
		return lastNames;
	}

	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
}
