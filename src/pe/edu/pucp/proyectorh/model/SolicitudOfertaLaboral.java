package pe.edu.pucp.proyectorh.model;

import java.util.Date;

public class SolicitudOfertaLaboral {
	private int ID;
	private String area;
	private String puesto;
	private int nroVacantes;
	private int sueldoTentativo;
	private Date fechaRequerimiento;
	private String modoPublicacion;	
	private String responsable;
	private String descripcion;
	private String comentarios;
	
	
	
	public SolicitudOfertaLaboral() {
		super();
	}
	
	public SolicitudOfertaLaboral(int iD, String area, String puesto,
			int nroVacantes, int sueldoTentativo,
			Date fechaRequerimiento, String modoPublicacion,
			String responsable, String descripcion, String comentarios) {
		super();
		this.ID = iD;
		this.area = area;
		this.puesto = puesto;
		this.nroVacantes = nroVacantes;
		this.sueldoTentativo = sueldoTentativo;
		this.fechaRequerimiento = fechaRequerimiento;
		this.modoPublicacion = modoPublicacion;
		this.responsable = responsable;
		this.descripcion = descripcion;
		this.comentarios = comentarios;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public int getNroVacantes() {
		return nroVacantes;
	}
	public void setNroVacantes(int nroVacantes) {
		this.nroVacantes = nroVacantes;
	}
	public int getSueldoTentativo() {
		return sueldoTentativo;
	}
	public void setSueldoTentativo(int sueldoTentativo) {
		this.sueldoTentativo = sueldoTentativo;
	}
	public Date getFechaRequerimiento() {
		return fechaRequerimiento;
	}
	public void setFechaRequerimiento(Date fechaRequerimiento) {
		this.fechaRequerimiento = fechaRequerimiento;
	}
	public String getModoPublicacion() {
		return modoPublicacion;
	}
	public void setModoPublicacion(String modoPublicacion) {
		this.modoPublicacion = modoPublicacion;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	@Override
	public String toString() {
		return "SolicitudOfertaLaboral [ID=" + ID + ", area=" + area
				+ ", puesto=" + puesto + ", nroVacantes=" + nroVacantes
				+ ", sueldoTentativo=" + sueldoTentativo
				+ ", fechaRequerimiento=" + fechaRequerimiento
				+ ", modoPublicacion=" + modoPublicacion + ", responsable="
				+ responsable + ", descripcion=" + descripcion
				+ ", comentarios=" + comentarios + "]";
	}
	
	
	
	
}
