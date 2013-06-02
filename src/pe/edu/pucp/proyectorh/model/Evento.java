package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class Evento {

	private int ID;
	private String nombre;
	private String fechaInicio;
	private String fechaFin;
	private String tipo;
	private int estadoID;
	private String estado;
	private int creadorID;
	private String creador;
	private ArrayList<Colaborador> invitados;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

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

	public int getEstadoID() {
		return estadoID;
	}

	public void setEstadoID(int estadoID) {
		this.estadoID = estadoID;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getCreadorID() {
		return creadorID;
	}

	public void setCreadorID(int creadorID) {
		this.creadorID = creadorID;
	}

	public String getCreador() {
		return creador;
	}

	public void setCreador(String creador) {
		this.creador = creador;
	}

	public ArrayList<Colaborador> getInvitados() {
		return invitados;
	}

	public void setInvitados(ArrayList<Colaborador> invitados) {
		this.invitados = invitados;
	}

}
