package pe.edu.pucp.proyectorh.model;

/**
 * Similar a EvaluacionXFaseXPostulacionDTO
 * https://github.com/a20012251/KendoDP2/blob/master/KendoDP2/Areas/Reclutamiento/Models/EvaluacionXFaseXPostulacion.cs#L56
 * 
 * @author Cesar
 * 
 */
public class Evaluacion {

	private int ID;
	private String fechaInicio;
	private String fechaFin;
	private int puntaje;
	private boolean flagAprobado;
	private String comentarios;
	private String observaciones;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public boolean isFlagAprobado() {
		return flagAprobado;
	}

	public void setFlagAprobado(boolean flagAprobado) {
		this.flagAprobado = flagAprobado;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
