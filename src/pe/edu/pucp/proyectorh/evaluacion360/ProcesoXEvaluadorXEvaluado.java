package pe.edu.pucp.proyectorh.evaluacion360;

import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;

public class ProcesoXEvaluadorXEvaluado {
	
	private Colaborador elEvaluado;
	private Colaborador evaluador;
	
	private ProcesoEvaluacion360 elProcesoEnQueParticipan;
	private String estado;

	private int laCalificacion;

	public ProcesoXEvaluadorXEvaluado() {
		super();
	}

	public ProcesoXEvaluadorXEvaluado(Colaborador elEvaluado,
			Colaborador evaluador,
			ProcesoEvaluacion360 elProcesoEnQueParticipan, String estado,
			int laCalificacion) {
		super();
		this.elEvaluado = elEvaluado;
		this.evaluador = evaluador;
		this.elProcesoEnQueParticipan = elProcesoEnQueParticipan;
		this.estado = estado;
		this.laCalificacion = laCalificacion;
	}

	public Colaborador getElEvaluado() {
		return elEvaluado;
	}

	public void setElEvaluado(Colaborador elEvaluado) {
		this.elEvaluado = elEvaluado;
	}

	public Colaborador getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(Colaborador evaluador) {
		this.evaluador = evaluador;
	}

	public ProcesoEvaluacion360 getElProcesoEnQueParticipan() {
		return elProcesoEnQueParticipan;
	}

	public void setElProcesoEnQueParticipan(
			ProcesoEvaluacion360 elProcesoEnQueParticipan) {
		this.elProcesoEnQueParticipan = elProcesoEnQueParticipan;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getLaCalificacion() {
		return laCalificacion;
	}

	public void setLaCalificacion(int laCalificacion) {
		this.laCalificacion = laCalificacion;
	}

	@Override
	public String toString() {
		return "ProcesoXEvaluadorXEvaluado [elEvaluado=" + elEvaluado
				+ ", evaluador=" + evaluador + ", elProcesoEnQueParticipan="
				+ elProcesoEnQueParticipan + ", estado=" + estado
				+ ", laCalificacion=" + laCalificacion + "]";
	}
	
	
	

}
