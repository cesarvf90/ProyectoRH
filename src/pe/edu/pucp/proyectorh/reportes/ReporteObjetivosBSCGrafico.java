package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ReporteObjetivosBSCGrafico extends Fragment{
	
	private WebView browser;
	int objSelec;
	int modo;
	String nomArch;

	
	public ReporteObjetivosBSCGrafico(){
		
	}
	
	
	
	public class DataObject {
		String[] personas;
		
		
		int[] avance; 
		
		public DataObject(String[] personas , int[] avance){
			this.personas = personas;
			this.avance = avance;
		}

	}
	
	public class InterfaceChartLineal {
	    Context mContext;
	    DataObject obj;
	    /** Instantiate the interface and set the context */
	    InterfaceChartLineal(Context c, DataObject o) {
	        mContext = c;
	        obj = o;
	    }

	    /* Show a toast from the web page 
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }*/
	    
	    public String getData(){
	    	
	    	
	    	Gson gson = new Gson();
	    	 
	    	// convert java object to JSON format,
	    	// and returned as JSON formatted string
	    	String json = gson.toJson(obj);
	    	System.out.println(json);
	    	return json;
	    }

	    
	    public String getTitulo(){

	    	return "Cumplimiento";
	    }
	    
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.reportebsc4grafico,
				container, false);

		
		browser = (WebView)rootView.findViewById(R.id.reportebscWebkitGLineal);
		
		String titulo = getArguments().getString("titulo");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscTitulografico);
		textView.setText(titulo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		objSelec= getArguments().getInt("idObjetivo");
		
		modo = getArguments().getInt("modo");
		nomArch = getArguments().getString("archivo");
		
		System.out.println("objetivo: " + objSelec);
		System.out.println("modo: " + modo);
		if (modo==0) System.out.println("archivo: " + nomArch);
		
		cargarPersonasAvance(objSelec);
		
		return rootView;
	}
	
	public void cargarPersonasAvance(int idObj){
		
		if (modo==1){
			//MODO ONLINE
		
			if (ConnectionManager.connect(getActivity())) {
				// construir llamada al servicio
				String request = ReporteServices.obtenerAvanceXPersona + "?idObjetivo=" + idObj;
	
				new getAvances().execute(request);
				
			} else {
				// Se muestra mensaje de error de conexion con el servicio
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Error de conexión");
				builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
			}
		}
		else{
			//MODO OFFLINE
			ArrayList<ObjetivoDTO> objetivosArch = PersistentHandler.getObjFromFile(getActivity(), nomArch);
			List<ObjetivosXPersonaRDTO> Personas = new ArrayList<ObjetivosXPersonaRDTO>();
			for (int i=0;i<objetivosArch.size();i++){
				if (objetivosArch.get(i).getIdpadre()==idObj){
					
					//este objetivo es el de personaxobjetivo, se muestran sus hijos
					
					//validacion si colaborador es null no mostrar
					if (objetivosArch.get(i).getColaboradorNombre()!=null){
						ObjetivosXPersonaRDTO persona = new ObjetivosXPersonaRDTO();
						
						System.out.println(objetivosArch.get(i).getColaboradorNombre());
						persona.setNombreColaborador(objetivosArch.get(i).getColaboradorNombre());
						
						int promedio=0;
						int cantidad=0;
						
						//obtener avance de hijos
						for (int j=0;j<objetivosArch.size();j++){
							if (objetivosArch.get(j).getIdpadre()==objetivosArch.get(i).getIdObjetivo()){
								promedio += objetivosArch.get(j).getAvance();
								cantidad++;
							}
								
						}
						
						if (cantidad>0) promedio =promedio /cantidad;
						persona.setAvance(promedio);
						Personas.add(persona);
						
					}
					
				}
			}
			
			String [] nombres = new String[Personas.size()];
			int[] avances = new int[Personas.size()];
			
			for (int i=0;i<Personas.size();i++){
				nombres[i] = Personas.get(i).getNombreColaborador();
				avances[i] = Personas.get(i).getAvance();
			}
			
			//habilitamos javascript y flash
			browser.getSettings().setJavaScriptEnabled(true);
			browser.getSettings().setPluginsEnabled(true);
			
			DataObject data = new DataObject(nombres, avances);
			
			InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
			
			browser.addJavascriptInterface(intface, "Android");
			
			
			browser.loadUrl("file:///android_asset/ReporteBSCperspectivabarchart.html");
			
		}
	}
	
	public class getAvances extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<ObjetivosXPersonaRDTO> Personas = gson.fromJson(result,new TypeToken<List<ObjetivosXPersonaRDTO>>(){}.getType());
			
			String [] nombres = new String[Personas.size()];
			int[] avances = new int[Personas.size()];
			
			for (int i=0;i<Personas.size();i++){
				nombres[i] = Personas.get(i).getNombreColaborador();
				avances[i] = Personas.get(i).getAvance();
			}
			
			//habilitamos javascript y flash
			browser.getSettings().setJavaScriptEnabled(true);
			browser.getSettings().setPluginsEnabled(true);
			
			DataObject data = new DataObject(nombres, avances);
			
			InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
			
			browser.addJavascriptInterface(intface, "Android");
			
			
			browser.loadUrl("file:///android_asset/ReporteBSCperspectivabarchart.html");
			
			
		}
	}
	
	public class ObjetivosXPersonaRDTO
	   {
	        private int avance;      
	        private String nombreColaborador;
	        
			public int getAvance() {
				return avance;
			}
			public void setAvance(int avance) {
				this.avance = avance;
			}
			public String getNombreColaborador() {
				return nombreColaborador;
			}
			public void setNombreColaborador(String nombreColaborador) {
				this.nombreColaborador = nombreColaborador;
			}
	}
	
	

}
