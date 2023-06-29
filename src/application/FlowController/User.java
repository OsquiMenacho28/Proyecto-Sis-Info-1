package application.FlowController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class User {
	String userName;
	String password;
	Boolean validated;
	Boolean admin;
	
	private static HashMap<String, String> users = new HashMap<String, String>(){
		{
			put("User1", "1243");
			put("User2", "3465");
			put("User3", "1010");
		}
	} ;
	public User(String userName, String password) {
		this(userName, password, false);
	}
	public User(String userName, String password, Boolean admin) {
		this.userName = userName;
		this.password = password;
		this.admin = admin;
		this.validated = false;
	}
	
	public static User searchDB (String userName, String password){
		if(users.keySet().contains(userName)) {
			if(users.get(userName).equals(password)) {
				System.out.println("Logged");

				return new User(userName, password, true);
			}
		}
	return null;
	}
	public static void unvalidate(User credentials, User user){
		if(credentials.isAdmin()){
			user.unvalidate();
		}
	}
	private void validate(){
		this.validated = true;
	}
	private void unvalidate(){
		this.validated = false;
	}
	public Boolean valid(){
		return validated;
	}
	public Boolean isAdmin(){
		return admin;
	}
}
