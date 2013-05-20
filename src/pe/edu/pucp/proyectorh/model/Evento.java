package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class Evento {

	private String nombre;
	private String fechaInicio;
	private String fechaFin;
	private String tipo;
	private String estado;
	private Colaborador creador;
	private ArrayList<Colaborador> invitados;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Colaborador getCreador() {
		return creador;
	}

	public void setCreador(Colaborador creador) {
		this.creador = creador;
	}

	public ArrayList<Colaborador> getInvitados() {
		return invitados;
	}

	public void setInvitados(ArrayList<Colaborador> invitados) {
		this.invitados = invitados;
	}

}
