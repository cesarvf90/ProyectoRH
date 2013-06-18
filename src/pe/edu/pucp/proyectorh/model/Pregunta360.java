package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

public class Pregunta360 {
	
	public String TextoPregunta;
	public int ExamenID;
    public int CapacidadID;
    public int Puntuacion;
    public int Peso;
    public int competenciaID;
    public int ID;
    public boolean IsEliminado;
	
	
	public Pregunta360() {
		
	}
	
	 public static ArrayList<Pregunta360> getPreguntasByResult(String result){
	    	ArrayList<Pregunta360> listaPreguntas = new ArrayList<Pregunta360>();
			try {
		    	JSONArray arregloPreguntas = new JSONArray(result);	
		    	for(int i=0;i<arregloPreguntas.length();i++){
					final Gson gson = new Gson();
					final Pregunta360 preg = gson.fromJson(arregloPreguntas.getString(i),Pregunta360.class);
					listaPreguntas.add(preg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return listaPreguntas;
	    }
}
