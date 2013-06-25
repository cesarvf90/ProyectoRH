package pe.edu.pucp.proyectorh.reportes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;

import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Reporte360Grafico extends Fragment{

	private WebView browser;
	int objSelec;
	String userColaborador;
	private Spinner spinnerProceso;
	List<String> lista ;
	List<RProcesosEvaluacion> procesosEval;
	private Button btnSubmit;
	private String nomproceso;
	
	public Reporte360Grafico(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reporte360grafico,
				container, false);
		
		//TextView nombre = (TextView)rootView.findViewById(R.id.nombreUsuarioRep);
		//nombre.setText(LoginActivity.getUsuario().getUsername());
		
		spinnerProceso = (Spinner) rootView.findViewById(R.id.reporte360spinner);
		lista = new ArrayList<String>();
		
			
			
		userColaborador = LoginActivity.getUsuario().getUsername();
		browser = (WebView)rootView.findViewById(R.id.reporte360WebkitGLineal);
		
		btnSubmit = (Button) rootView.findViewById(R.id.reporte360btnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
		    
			      Reporte360Detalle fragment = new  Reporte360Detalle();
			      
			      Bundle argumentos = new Bundle();
			     // argumentos.putInt("AreaSelec", periodoSelec);
			      argumentos.putString("Nomproceso", nomproceso);
	      
			      fragment.setArguments(argumentos);
			      
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
			    }
				  
			  });
		 
			
		/*
        //habilitamos javascript y flash
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");*/
		
		cargarGraficoProcesosEvaluacion(userColaborador);
		//browser.loadUrl("file:///android_asset/Reporte360chart.html");
		
		/*
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reporte360Titulografico);
		textView.setText(titulo);*/
		
		return rootView;
	}
	
	
	
	public void cargarGraficoProcesosEvaluacion(String userColaborador){
		
		if (PersistentHandlerReporte360.buscarArchivo(getActivity(), "reporte360" + userColaborador + ".txt")){
			
			procesosEval=PersistentHandlerReporte360.getProcFromFile(getActivity(), "reporte360" + userColaborador + ".txt");
			if (procesosEval.size()==0){
				 String summary = "<html><body>No se encontraron procesos de evaluacion</body></html>";
				 browser.loadData(summary, "text/html", null);
			}
			else{
				
				ArrayList<String> evaluadores = new ArrayList<String>();
				ArrayList<Integer> notas = new ArrayList<Integer>();				
				
				for (int i=0;i<procesosEval.size();i++){

					if (i==procesosEval.size()-1){
						
						for (int j=0;j<procesosEval.get(i).getNotasParciales().size();j++){
							evaluadores.add(procesosEval.get(i).getNotasParciales().get(j).getTipoEvaluador());
							notas.add(procesosEval.get(i).getNotasParciales().get(j).geNotaParcial());
													
						}
						evaluadores.add("Nota Final");
						notas.add(procesosEval.get(i).notaFinal);					
					}
					lista.add (procesosEval.get(i).procesoNombre);	
					
				}
				
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerProceso.setAdapter(dataAdapter);
				spinnerProceso.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						
						ArrayList<String> evaluadores = new ArrayList<String>();
						ArrayList<Integer> notas = new ArrayList<Integer>();
						int procesoselec=pos;
						nomproceso=procesosEval.get(procesoselec).getProcesoNombre();
						
						for (int i=0;i<procesosEval.size();i++){

							if (i==procesoselec){
								
								for (int j=0;j<procesosEval.get(i).getNotasParciales().size();j++){
									evaluadores.add(procesosEval.get(i).getNotasParciales().get(j).getTipoEvaluador());
									notas.add(procesosEval.get(i).getNotasParciales().get(j).geNotaParcial());
															
								}
								evaluadores.add("Nota Final");
								notas.add(procesosEval.get(i).notaFinal);					
							}								
							
						}
						
						browser.getSettings().setJavaScriptEnabled(true);
						browser.getSettings().setPluginsEnabled(true);
						DataObject data = new DataObject(evaluadores, notas);
									
						InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);				
						
						browser.addJavascriptInterface(intface, "Android");				
						
						browser.loadUrl("file:///android_asset/Reporte360chart.html");

					  }
					
				
					@Override
					  public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					  }
					
				});
				//habilitamos javascript y flash
				browser.getSettings().setJavaScriptEnabled(true);
				browser.getSettings().setPluginsEnabled(true);
				DataObject data = new DataObject(evaluadores, notas);
							
				InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);				
				
				browser.addJavascriptInterface(intface, "Android");				
				
				browser.loadUrl("file:///android_asset/Reporte360chart.html");
			}
		}
		else{
					
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerProcesosEvaluacion + "?userName=" + userColaborador;

			new getProcesos().execute(request);
			
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
		
	}
	
	public class getProcesos extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			try{
			
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			procesosEval = gson.fromJson(result,new TypeToken<List<RProcesosEvaluacion>>(){}.getType());
			/*System.out.println(procesosEval.size());
			System.out.println(procesosEval.get(0).getNotasParciales().size());*/
			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
			PersistentHandlerReporte360.agregarArchivoPersistente(currentDateTimeString + "\n" + result, getActivity(),  "reporte360" + userColaborador + ".txt");
			}
			catch(Exception e){
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Error de datos");
				builder.setMessage("No se pudo recuperar la información.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
				return;
			}
			if (procesosEval.size()==0){
				 String summary = "<html><body>No se encontraron procesos de evaluación</body></html>";
				 browser.loadData(summary, "text/html", null);
			}
			else{
				
				ArrayList<String> evaluadores = new ArrayList<String>();
				ArrayList<Integer> notas = new ArrayList<Integer>();
								
				for (int i=0;i<procesosEval.size();i++){

					if (i==procesosEval.size()-1){
						
						for (int j=0;j<procesosEval.get(i).getNotasParciales().size();j++){
							evaluadores.add(procesosEval.get(i).getNotasParciales().get(j).getTipoEvaluador());
							notas.add(procesosEval.get(i).getNotasParciales().get(j).geNotaParcial());
													
						}
						evaluadores.add("Nota Final");
						notas.add(procesosEval.get(i).notaFinal);					
					}
					lista.add (procesosEval.get(i).procesoNombre);	
					
				}
				
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerProceso.setAdapter(dataAdapter);
				spinnerProceso.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						
						ArrayList<String> evaluadores = new ArrayList<String>();
						ArrayList<Integer> notas = new ArrayList<Integer>();
						int procesoselec=pos;
						nomproceso=procesosEval.get(procesoselec).getProcesoNombre();
						System.out.println("PROCESOOOOO"+nomproceso);
						for (int i=0;i<procesosEval.size();i++){
							
							if (i==procesoselec){
								
								for (int j=0;j<procesosEval.get(i).getNotasParciales().size();j++){
									evaluadores.add(procesosEval.get(i).getNotasParciales().get(j).getTipoEvaluador());
									notas.add(procesosEval.get(i).getNotasParciales().get(j).geNotaParcial());
															
								}
								evaluadores.add("Nota Final");
								notas.add(procesosEval.get(i).notaFinal);					
							}								
							
						}
						
						browser.getSettings().setJavaScriptEnabled(true);
						browser.getSettings().setPluginsEnabled(true);
						DataObject data = new DataObject(evaluadores, notas);
									
						InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);				
						
						browser.addJavascriptInterface(intface, "Android");				
						
						browser.loadUrl("file:///android_asset/Reporte360chart.html");

					  }
					
				
					@Override
					  public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					  }
					
				});
				//habilitamos javascript y flash
				browser.getSettings().setJavaScriptEnabled(true);
				browser.getSettings().setPluginsEnabled(true);
				DataObject data = new DataObject(evaluadores, notas);
							
				InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);				
				
				browser.addJavascriptInterface(intface, "Android");				
				
				browser.loadUrl("file:///android_asset/Reporte360chart.html");
			}
			
		}
	}
	
	
public class DataObject {
		
		String[] evaluad ;		
		int[] notas;
 
		
		public DataObject(ArrayList<String> arrEvaluadores, ArrayList<Integer> arrNotas){
			
			evaluad= new String[arrEvaluadores.size()];
			notas = new int[arrNotas.size()];
			for (int i=0;i<arrEvaluadores.size();i++){
				evaluad[i] = arrEvaluadores.get(i);
				notas[i] = arrNotas.get(i);
			}

			
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

	    
	    public String getData(){
	    	
	    	
	    	Gson gson = new Gson();
	    	 
	    	// convert java object to JSON format,
	    	// and returned as JSON formatted string
	    	String json = gson.toJson(obj);
	    	System.out.println(json);
	    	return json;
	    }

	    
	   
	    
	}
	
	/*
	public class DataObject {
		String[] procesoEval = new String[] { 
				"Proceso de Evaluación 1", "Proceso de Evaluación 2", "Proceso de Evaluación 3", "Proceso de Evaluación 4"};
		
		int[] jefe = new int[] { 10, 13, 14, 16 };
		int[] compañerosPares = new int[] { 13, 15, 18, 16 };
		int[] subordinados = new int[] { 15, 12, 14, 17 };
		int[] nota = new int[] { 16, 14, 17, 16 };
		

	}*/
	
	public class RProcesosEvaluacion
	{
	        private String procesoNombre;
	        private List<RNotaxTipoEvaluador> notasParciales;
	        private int notaFinal;	
	        
	        public String getProcesoNombre() {
				return procesoNombre;
			}
			public void setProcesoNombre(String procesoNombre) {
				this.procesoNombre = procesoNombre;
			}
			
			public int geNotaFinal() {
				return notaFinal;
			}
			public void setNotaFinal(int notaFinal) {
				this.notaFinal = notaFinal;
			}
			public List<RNotaxTipoEvaluador> getNotasParciales() {
				return notasParciales;
			}
			public void setNotasParciales(List<RNotaxTipoEvaluador> notasParciales) {
				this.notasParciales = notasParciales;
			}
						
					
	}
	
	public class RNotaxTipoEvaluador{
		public String tipoEvaluador;
        public int notaParcial;
        
        public String getTipoEvaluador() {
			return tipoEvaluador;
		}
		public void setTipoEvaluador(String tipoEvaluador) {
			this.tipoEvaluador = tipoEvaluador;
		}
		
		public int geNotaParcial() {
			return notaParcial;
		}
		public void setNotaParcial(int notaParcial) {
			this.notaParcial = notaParcial;
		}
				
		
	}
	
}
