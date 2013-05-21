package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class OfertaLaboral {

	private String ID;
	private int puestoID;
	private Puesto puesto;
	private int estado;
	private ArrayList<Postulante> postulantes;
	private String solicitante;
	private String fechaRequerimiento;
	private String fechaUltimaEntrevista;
	private String faseActual;
	private int numeroPostulantes;

	public OfertaLaboral() {
		super();
	}

	public OfertaLaboral(Puesto puesto, ArrayList<Postulante> postulantes,
			String solicitante, String fechaRequerimiento,
			String fechaUltimaEntrevista, String faseActual) {
		this.puesto = puesto;
		this.postulantes = postulantes;
		this.solicitante = solicitante;
		this.fechaRequerimiento = fechaRequerimiento;
		this.fechaUltimaEntrevista = fechaUltimaEntrevista;
		this.faseActual = faseActual;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFaseActual() {
		return faseActual;
	}

	public void setFaseActual(String faseActual) {
		this.faseActual = faseActual;
	}

	public String getFechaUltimaEntrevista() {
		return fechaUltimaEntrevista;
	}

	public void setFechaUltimaEntrevista(String fechaUltimaEntrevista) {
		this.fechaUltimaEntrevista = fechaUltimaEntrevista;
	}

	public String getFechaRequerimiento() {
		return fechaRequerimiento;
	}

	public void setFechaRequerimiento(String fechaRequerimiento) {
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

	public int getNumeroPostulantes() {
		return numeroPostulantes;
	}

	public void setNumeroPostulantes(int numeroPostulantes) {
		this.numeroPostulantes = numeroPostulantes;
	}

	@Override
	public String toString() {
		return puesto.toString();
	}

}
