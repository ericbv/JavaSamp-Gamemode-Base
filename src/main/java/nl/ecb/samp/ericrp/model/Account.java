package nl.ecb.samp.ericrp.model;

public class Account {
	private String Password,Username,Email;
	private int ID;
	public Account(String username, String password, String email, int iD) {
		super();
		Password = password;
		Username = username;
		Email = email;
		ID = iD;
	}
	public static Account load(String username, String password, String email, int iD){
		return new Account(username, password, email, iD);
	}
	public void unload(){
		//TODO add unload code or something
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}


}
