package pe.edu.pucp.proyectorh.model;

import pe.edu.pucp.proyectorh.utils.Constante;

/**
 * Clase Colaborador mapeada contra ColaboradorDTO de la aplicacion web
 * 
 * @author Cesar
 * 
 */
public class Colaborador {

	private String nombres;
	private String apellidos;
	private String area;
	private String puesto;
	private String email;
	private String anexo;
	private String fechaIngreso;
	private String fechaNacimiento;
	private String correoElectronico;
	private String telefono;
	private String cenrtoEstudios;

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

	public String getCenrtoEstudios() {
		return cenrtoEstudios;
	}

	public void setCenrtoEstudios(String cenrtoEstudios) {
		this.cenrtoEstudios = cenrtoEstudios;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@Override
	public String toString() {
		return nombres + Constante.ESPACIO_VACIO + apellidos;
	}
}