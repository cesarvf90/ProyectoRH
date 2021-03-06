package pe.edu.pucp.proyectorh.model;

/**
 * Clase utilizada para recibir la respuesta del servicio de login
 * 
 * @author Cesar
 * 
 */
public class RespuestaLogin {
	private String respuesta;
	private Usuario usuario;

	public RespuestaLogin() {
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}