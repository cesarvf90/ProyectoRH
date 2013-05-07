package pe.edu.pucp.proyectorh.model;

public class ColaboradorEquipoTrabajo {
	private String nombreCompleto;
	private String area;
	private String puesto;
	private String anexo;
	private String email;
	private int cantidadSubordinados;
	private ColaboradorEquipoTrabajo[] listaSubordinados;	

	public ColaboradorEquipoTrabajo(String nombreCompleto, 
			String area, String puesto, String anexo, String email,
			int cantidadSubordinados,
			ColaboradorEquipoTrabajo[] listaSubordinados) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.area = area;
		this.puesto = puesto;
		this.anexo = anexo;
		this.email = email;
		this.cantidadSubordinados = cantidadSubordinados;
		this.listaSubordinados = listaSubordinados;		
	}
	
	
	public ColaboradorEquipoTrabajo(String nombreCompleto, 
			String area, String puesto, String anexo, String email,
			int cantidadSubordinados) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.area = area;
		this.puesto = puesto;
		this.anexo = anexo;
		this.email = email;
		this.cantidadSubordinados = cantidadSubordinados;
	}

	


	@Override
	public String toString() {
		return "ColaboradorEquipoTrabajo [nombreCompleto=" + nombreCompleto
				+ ", area=" + area + ", puesto=" + puesto
				+ ", anexo=" + anexo + ", email=" + email
				+ ", cantidadSubordinados=" + cantidadSubordinados + "]";
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
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
