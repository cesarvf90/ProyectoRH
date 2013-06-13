package pe.edu.pucp.proyectorh.model;

public class AvanceDTO {
	public int ID;
	public int Valor;
	public String FechaCreacion;
	public int CreadorID;
	public int ObjetivoID;
	public boolean FueRevisado;
	public int ValorDelJefe;
	public String Comentario;

	public String datos(){
		return "ID="+ID+",Valor="+Valor+",Comentario="+Comentario;
	}
}
