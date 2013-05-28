package pe.edu.pucp.proyectorh.model;

/**
 * Simular a RespuestaDTO
 * https://github.com/a20012251/KendoDP2/blob/master/KendoDP2/Areas/Reclutamiento/Models/Respuesta.cs#L47
 * @author Cesar
 *
 */
public class Respuesta {

	private String ID;
	private String comentario;
	private int puntaje;
	private int evaluacion;
	private int funcionID;

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getFuncionID() {
		return funcionID;
	}

	public void setFuncionID(int funcionID) {
		this.funcionID = funcionID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}

}
