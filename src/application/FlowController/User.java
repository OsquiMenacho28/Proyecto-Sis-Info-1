package application.FlowController;

import java.util.HashMap;

public final class User {
	private String CI;
	private String names;
	private String lastNames;
	private String address;
	private int phone;
	private String userName;
	private String password;
	private Boolean validated;
	private Boolean admin;

	private static HashMap<String, String> users = new HashMap<String, String>() {
		{
			put("User1", "1234");
			put("User2", "3465");
			put("User3", "1010");
		}
	};

	public User(String userName, String password) {
		this(userName, password, false);
	}

	public User(String userName, String password, Boolean admin) {
		this.userName = userName;
		this.password = password;
		this.admin = admin;
		this.validated = false;
	}

	public static User searchDB(String userName, String password) {
		if (users.keySet().contains(userName)) {
			if (users.get(userName).equals(password)) {
				System.out.println("Logged");

				return new User(userName, password, true);
			}
		}
		return null;
	}

	public static void invalidate(User credentials, User user) {
		if (credentials.isAdmin()) {
			user.invalidate();
		}
	}

	private void validate() {
		this.validated = true;
	}

	private void invalidate() {
		this.validated = false;
	}

	public Boolean valid() {
		return validated;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public String getCI() {
		return CI;
	}

	public void setCI(String CI) {
		this.CI = CI;
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
