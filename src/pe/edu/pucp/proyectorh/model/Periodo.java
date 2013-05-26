package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Periodo {
    public String Nombre;
    public Date FechaInicio;
    public Date FechaFin;

    public int BSCID;
    public int id;

    public Periodo(String nombre,int BSCID) {
    	this.Nombre = nombre;    	
    	this.BSCID = BSCID;
    	System.out.println("CREADO OK");
    }

    public static ArrayList<Periodo> getPeriodosByResult(String result){
    	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
		try {
	    	JSONArray arregloPeriodos = new JSONArray(result);	
	    	for(int i=0;i<arregloPeriodos.length();i++){
				JSONObject periodoJSON;
				periodoJSON = arregloPeriodos.getJSONObject(i);
				Periodo per = new Periodo(periodoJSON.getString("Nombre"),periodoJSON.getInt("BSCID"));
				listaPeriodos.add(per);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listaPeriodos;
    }
}
