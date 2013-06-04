package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

	private int ID;
	private String nombre;
	private String fechaInicio;
	private String fechaFin;
	private int estadoID;
	private String estado;
	private int creadorID;
	private Colaborador creador;
	private int tipoEventoID;
	private String tipoEvento;
	private ArrayList<Colaborador> invitados;
	private Date dateInicio;
	private Date dateFin;
	private String lugar;

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

	public Date getDateInicio() {
		return dateInicio;
	}

	public void setDateInicio(Date dateInicio) {
		this.dateInicio = dateInicio;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getTipoEventoID() {
		return tipoEventoID;
	}

	public void setTipoEventoID(int tipoEventoID) {
		this.tipoEventoID = tipoEventoID;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

}
