package pe.edu.pucp.proyectorh.reportes;

public class ObjetivoDTO {
	
	private int idObjetivo;
	private String descripcion;
	private int numPersonas;
	private int avance;
	private int hijos;
	
	private int peso;
	private boolean esIntermedio;
	private int idPuesto;
	private int idpadre;
	private int idperiodo;
	private int BSCId;
	 
	 
	
	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public boolean isEsIntermedio() {
		return esIntermedio;
	}

	public void setEsIntermedio(boolean esIntermedio) {
		this.esIntermedio = esIntermedio;
	}

	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	public int getIdpadre() {
		return idpadre;
	}

	public void setIdpadre(int idpadre) {
		this.idpadre = idpadre;
	}

	public int getIdperiodo() {
		return idperiodo;
	}

	public void setIdperiodo(int idperiodo) {
		this.idperiodo = idperiodo;
	}

	public int getBSCId() {
		return BSCId;
	}

	public void setBSCId(int bSCId) {
		BSCId = bSCId;
	}

	public int getHijos() {
		return hijos;
	}

	public void setHijos(int hijos) {
		this.hijos = hijos;
	}

	public ObjetivoDTO(){
		
	}

	public int getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(int idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}

	public int getAvance() {
		return avance;
	}

	public void setAvance(int avance) {
		this.avance = avance;
	}
	
    
    

}
