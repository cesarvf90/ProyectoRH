package pe.edu.pucp.proyectorh.model;

import java.util.Date;

public class SolicitudOfertaLaboral {
	private int solicitudID;
	private String area;
	private String cargo;
	private Date fechaRequerimiento;
	private String modoPublicacion;
	private String responsable;
	private String comentarios;
	private String observacion;
	private String estado;
			
	public SolicitudOfertaLaboral() {
		super();
		this.area = "";
		this.cargo = "";
		this.fechaRequerimiento = null;
		this.modoPublicacion = "";
		this.responsable = "";
		this.comentarios = "";
		this.observacion = "";
		this.estado = "";
	}

	public SolicitudOfertaLaboral(int solicitudID, String area, String cargo,
			Date fechaRequerimiento, String modoPublicacion,
			String responsable, String comentarios, String observacion,
			String estado) {
		super();
		this.solicitudID = solicitudID;
		this.area = area;
		this.cargo = cargo;
		this.fechaRequerimiento = fechaRequerimiento;
		this.modoPublicacion = modoPublicacion;
		this.responsable = responsable;
		this.comentarios = comentarios;
		this.observacion = observacion;
		this.estado = estado;
	}

	public int getSolicitudID() {
		return solicitudID;
	}

	public void setSolicitudID(int solicitudID) {
		this.solicitudID = solicitudID;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
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

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return cargo;
	}
	
}
