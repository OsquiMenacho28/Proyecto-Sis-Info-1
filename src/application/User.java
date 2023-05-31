package application;

import java.util.ArrayList;

public final class User {
	String userName;
	String password;
	Boolean admin;
	
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
}
