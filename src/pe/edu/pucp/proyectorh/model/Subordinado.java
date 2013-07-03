package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Subordinado {
	public int ID;
	public String NombreCompleto;
	
	   public static ArrayList<Subordinado> getSubordinadosByResult(String result){
	    	ArrayList<Subordinado> listaSubordinados = new ArrayList<Subordinado>();
			try {
				
				JSONObject rpta = new JSONObject(result);				
				JSONObject data = (JSONObject) rpta.get("data");
				JSONArray arregloSubordinados =  (JSONArray) data.get("losEmpleadosQueLeReportan");	
		    
				for(int i=0;i<arregloSubordinados.length();i++){
					final Gson gson = new Gson();
					final Subordinado sub = gson.fromJson(arregloSubordinados.getString(i),Subordinado.class);
					listaSubordinados.add(sub);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return listaSubordinados;
	    }
}
