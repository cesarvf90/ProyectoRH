package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.reclutamiento.MenuOfertasLaboralesTerceraFase.ObtencionOfertas;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.Constante;

/**
 * Clase Colaborador mapeada contra ColaboradorDTO de la aplicacion web
 * 
 * @author Cesar
 * 
 */
public class Colaborador {

	private String id;
	private String nombres;
	private String apellidos;
	private String areaID;
	private String area;
	private String puesto;
	private String puestoID;
	private String anexo;
	private Date fechaIngreso;
	private Date fechaNacimiento;
	private String correoElectronico;
	private String telefono;
	private String centroEstudios;
	private String direccion;
	private String paisID;
	private String tipoDocumentoID;
	private String numeroDocumento;
	private String imagenColaborador;
	private String Sueldo;
	private String resumenEjecutivo;
	private String estadoColaboradorID;
	
	public static ArrayList<String> consultarColaboradoresDelServidorDeProduccion()
	{
		String direccionDeDestino = "http://10.0.2.2:2642/Evaluacion360/GestorDatosDeColaboradores/consultarSusCompanerosPares?deEsteColaborador=23";

		
		new UnaConsultaDeDatos().execute(direccionDeDestino);
		
		ArrayList<String> empleadosConSusDatos = new ArrayList<String>();
		
		empleadosConSusDatos.add("Todos los empleados");
		
		return empleadosConSusDatos;
		
	}

	public static class UnaConsultaDeDatos extends AsyncCall {
		@Override
		protected void onPostExecute(String loQueRespondio) {
			System.out.println("Recibido: " + loQueRespondio.toString());
		}
	}	
	
	public static ArrayList<String> tomarPrestadoDataDePrueba() {
		
		String rchavez = "Chávez Alcántara, Rodrigo";
		String crios = "Ríos Montesinos, Carmen";
		String surteaga = "Urteaga Gonzáles, Sammantha";
		String ccamino = "Camino Prades, Carla";
		String abustamante = "Bustamante Ferda, Alejandra";
		String areas = "Reas Fernández, Antonnella";
		
		ArrayList<String> losSupuestosSubordinados = new ArrayList<String>();
		
		losSupuestosSubordinados.add(rchavez);
		losSupuestosSubordinados.add(crios);
		losSupuestosSubordinados.add(surteaga);
		losSupuestosSubordinados.add(ccamino);
		losSupuestosSubordinados.add(abustamante);
		losSupuestosSubordinados.add(areas);
		
		return losSupuestosSubordinados;
		
	}
	
	public Colaborador() {
	}

	public Colaborador(String nombres, String apellidos, String area,
			String puesto) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.area = area;
		this.puesto = puesto;
	}

	public Colaborador(String nombres, String apellidos, String area,
			String puesto, Date fechaIngreso, Date fechaNacimiento,
			String correoElectronico, String telefono) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.area = area;
		this.puesto = puesto;
		this.fechaIngreso = fechaIngreso;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaID() {
		return areaID;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getPuestoID() {
		return puestoID;
	}

	public void setPuestoID(String puestoID) {
		this.puestoID = puestoID;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPaisID() {
		return paisID;
	}

	public void setPaisID(String paisID) {
		this.paisID = paisID;
	}

	public String getTipoDocumentoID() {
		return tipoDocumentoID;
	}

	public void setTipoDocumentoID(String tipoDocumentoID) {
		this.tipoDocumentoID = tipoDocumentoID;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getImagenColaborador() {
		return imagenColaborador;
	}

	public void setImagenColaborador(String imagenColaborador) {
		this.imagenColaborador = imagenColaborador;
	}

	public String getSueldo() {
		return Sueldo;
	}

	public void setSueldo(String sueldo) {
		Sueldo = sueldo;
	}

	public String getResumenEjecutivo() {
		return resumenEjecutivo;
	}

	public void setResumenEjecutivo(String resumenEjecutivo) {
		this.resumenEjecutivo = resumenEjecutivo;
	}

	public String getEstadoColaboradorID() {
		return estadoColaboradorID;
	}

	public void setEstadoColaboradorID(String estadoColaboradorID) {
		this.estadoColaboradorID = estadoColaboradorID;
	}

	@Override
	public String toString() {
		return nombres + Constante.ESPACIO_VACIO + apellidos;
	}
}