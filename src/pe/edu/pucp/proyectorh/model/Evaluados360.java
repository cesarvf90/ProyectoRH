package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

public class Evaluados360 {
	public persona evaluado;
	public String Estado;
	public int ID;
	public int ProcesoEnElQueParticipanID;
	
	public class persona{
		public String NombreCompleto;
		public int ID;
		public String Area;
		public String Puesto;
	}
	
	public String toText(){
		return "p="+evaluado.NombreCompleto+ " del a="+evaluado.Area+" y p="+evaluado.Puesto+" con proc="+ProcesoEnElQueParticipanID+" estado="+Estado;
	}

	  public static ArrayList<Evaluados360> getEvaluadosByResult(String result){
	    	ArrayList<Evaluados360> listaEvaluados = new ArrayList<Evaluados360>();
			try {
		    	JSONArray arregloEvaluados = new JSONArray(result);	
		    	for(int i=0;i<arregloEvaluados.length();i++){
					final Gson gson = new Gson();
					final Evaluados360 eval = gson.fromJson(arregloEvaluados.getString(i),Evaluados360.class);
					listaEvaluados.add(eval);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return listaEvaluados;
	    }

}
