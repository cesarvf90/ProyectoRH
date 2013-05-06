package pe.edu.pucp.proyectorh.reportes;

public class ObjetivoDTO {
	
	
    public int ID ;

    public String Nombre ;
    public int Peso ;
    public int AvanceFinal ;
    public boolean IsAsignadoAPersona ;
    public int CreadorID ;
    public int TipoObjetivoBSCID ;
    public int ObjetivoPadreID ;
    public int BSCID ;

    public ObjetivoDTO() { }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public int getPeso() {
		return Peso;
	}

	public void setPeso(int peso) {
		Peso = peso;
	}

	public int getAvanceFinal() {
		return AvanceFinal;
	}

	public void setAvanceFinal(int avanceFinal) {
		AvanceFinal = avanceFinal;
	}

	public boolean isIsAsignadoAPersona() {
		return IsAsignadoAPersona;
	}

	public void setIsAsignadoAPersona(boolean isAsignadoAPersona) {
		IsAsignadoAPersona = isAsignadoAPersona;
	}

	public int getCreadorID() {
		return CreadorID;
	}

	public void setCreadorID(int creadorID) {
		CreadorID = creadorID;
	}

	public int getTipoObjetivoBSCID() {
		return TipoObjetivoBSCID;
	}

	public void setTipoObjetivoBSCID(int tipoObjetivoBSCID) {
		TipoObjetivoBSCID = tipoObjetivoBSCID;
	}

	public int getObjetivoPadreID() {
		return ObjetivoPadreID;
	}

	public void setObjetivoPadreID(int objetivoPadreID) {
		ObjetivoPadreID = objetivoPadreID;
	}

	public int getBSCID() {
		return BSCID;
	}

	public void setBSCID(int bSCID) {
		BSCID = bSCID;
	}
    
    

}
