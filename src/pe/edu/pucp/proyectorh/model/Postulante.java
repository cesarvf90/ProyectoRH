package pe.edu.pucp.proyectorh.model;

public class Postulante {

	private String ID;
	private String nombres;
	private String apellidos;
	private String tipoDocumento;
	private String numeroDocumento;
	private String curriculumVitae;
	private String centroEstudios;
	private String gradoAcademico;
	private String correoElectronico;

	public Postulante() {
	}

	public Postulante(String nombres, String apellidos) {
		this.nombres = nombres;
		this.apellidos = apellidos;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getCurriculumVitae() {
		return curriculumVitae;
	}

	public void setCurriculumVitae(String curriculumVitae) {
		this.curriculumVitae = curriculumVitae;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios;
	}

	public String getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(String gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@Override
	public String toString() {
		return nombres + " " + apellidos;
	}

}
