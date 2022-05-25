package ManagerApp;

public class Credential {
	public String Servicename;
	public String Username;
	public String Password;

	public Credential(String Servicename, String Username, String Password) {
		this.Servicename = Servicename;
		this.Username = Username;
		this.Password = Password;
	}

	public String getServicename() {
		return Servicename;
	}

	public String getUsername() {
		return Username;
	}

	public String getPassword() {
		return Password;
	}

}
