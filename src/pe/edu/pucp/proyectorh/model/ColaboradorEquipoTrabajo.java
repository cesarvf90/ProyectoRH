package pe.edu.pucp.proyectorh.model;

public class ColaboradorEquipoTrabajo {
	private String id;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String area;
	private String puesto;
	private String anexo;
	private String email;
	private int cantidadSubordinados;
	private ColaboradorEquipoTrabajo[] listaSubordinados;	

	
	public ColaboradorEquipoTrabajo(String id, String nombres,
			String apellidoPaterno, String apellidoMaterno, String area,
			String puesto, String anexo, String email) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.area = area;
		this.puesto = puesto;
		this.anexo = anexo;
		this.email = email;
	}


	@Override
	public String toString() {
		return "ColaboradorEquipoTrabajo [id=" + id + ", nombres=" + nombres
				+ ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
				+ apellidoMaterno + ", area=" + area + ", puesto=" + puesto
				+ ", anexo=" + anexo + ", email=" + email + "]";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNombres() {
		return nombres;
	}


	public void setNombres(String nombres) {
		this.nombres = nombres;
	}





	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ColaboradorEquipoTrabajo[] getlistaSubordinados() {
		return listaSubordinados;
	}

	public void setlistaSubordinados(
			ColaboradorEquipoTrabajo[] listaSubordinados) {
		this.listaSubordinados = listaSubordinados;
	}

	public int getCantidadSubordinados() {
		return cantidadSubordinados;
	}

	public void setCantidadSubordinados(int cantidadSubordinados) {
		this.cantidadSubordinados = cantidadSubordinados;
	}
}
