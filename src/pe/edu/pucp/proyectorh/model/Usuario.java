package pe.edu.pucp.proyectorh.model;

/**
 * Clase mapeada contra Usuario DBObject
 * 
 * @author Cesar
 * 
 */
public class Usuario {

	private String ID;
	private String username;
	private String password;

	public Usuario(String iD, String username, String password) {
		super();
		ID = iD;
		this.username = username;
		this.password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
