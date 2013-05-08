package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.Date;

public class OfertaLaboral {

	private int puestoID;
	private Puesto puesto;
	private int estado;
	private ArrayList<Postulante> postulantes;
	private String solicitante;
	private Date fechaRequerimiento;
	private Date fechaUltimaEntrevista;
	private String faseActual;

	public OfertaLaboral(Puesto puesto, ArrayList<Postulante> postulantes,
			String solicitante, Date fechaRequerimiento,
			Date fechaUltimaEntrevista, String faseActual) {
		this.puesto = puesto;
		this.postulantes = postulantes;
		this.solicitante = solicitante;
		this.fechaRequerimiento = fechaRequerimiento;
		this.fechaUltimaEntrevista = fechaUltimaEntrevista;
		this.faseActual = faseActual;
	}

	public String getFaseActual() {
		return faseActual;
	}

	public void setFaseActual(String faseActual) {
		this.faseActual = faseActual;
	}

	public Date getFechaUltimaEntrevista() {
		return fechaUltimaEntrevista;
	}

	public void setFechaUltimaEntrevista(Date fechaUltimaEntrevista) {
		this.fechaUltimaEntrevista = fechaUltimaEntrevista;
	}

	public Date getFechaRequerimiento() {
		return fechaRequerimiento;
	}

	public void setFechaRequerimiento(Date fechaRequerimiento) {
		this.fechaRequerimiento = fechaRequerimiento;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public int getPuestoID() {
		return puestoID;
	}

	public void setPuestoID(int puestoID) {
		this.puestoID = puestoID;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public ArrayList<Postulante> getPostulantes() {
		return postulantes;
	}

	public void setPostulantes(ArrayList<Postulante> postulantes) {
		this.postulantes = postulantes;
	}

	@Override
	public String toString() {
		return puesto.toString();
	}

}
